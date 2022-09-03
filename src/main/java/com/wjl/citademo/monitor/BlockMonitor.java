package com.wjl.citademo.monitor;

import com.citahub.cita.protocol.CITAj;
import com.citahub.cita.protocol.account.Account;
import com.citahub.cita.protocol.core.DefaultBlockParameter;
import com.citahub.cita.protocol.core.Request;
import com.citahub.cita.protocol.core.methods.response.*;
import com.wjl.citademo.codegen.Abi;
import com.wjl.citademo.config.ServerProperties;
import com.wjl.citademo.utils.AddressUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlockMonitor implements InitializingBean {

    private final CITAj citaj;

    private final ServerProperties serverProperties;

    private final Abi abi;

    private final Account account;

    private BigInteger current;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("appFilter started");

        AppBlockNumber send = citaj.appBlockNumber().send();
        current = send.getBlockNumber();

        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (blockChanged()) {
                        monitor();
                        TimeUnit.SECONDS.sleep(2);
                    }
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.setName("Block-Monitor-Thread");
        thread.start();
    }

    private boolean blockChanged() throws IOException {
        AppBlockNumber send = citaj.appBlockNumber().send();
        BigInteger blockNumber = send.getBlockNumber();
        if (current.compareTo(blockNumber) == 0) {
            return false;
        }
        current = blockNumber;
        return true;
    }

    private void monitor() throws Exception {
        log.info("sync new block: {}", current);
        Request<?, AppBlock> appBlockRequest = citaj.appGetBlockByNumber(DefaultBlockParameter.valueOf(current), true);
        AppBlock appBlock = appBlockRequest.send();
        AppBlock.Block block = appBlock.getBlock();
        List<AppBlock.TransactionObject> transactions = block.getBody().getTransactions();
        for (AppBlock.TransactionObject transaction : transactions) {
            Transaction tx = transaction.get();
            log.info("new block with tx: {}", tx);
            parseHash(tx.getHash());
        }
    }

    private void parseHash(String txHash) throws Exception {
        log.info("parse txHash: {}", txHash);
        Request<?, AppGetTransactionReceipt> appGetTransactionReceiptRequest = citaj.appGetTransactionReceipt(txHash);
        AppGetTransactionReceipt send = appGetTransactionReceiptRequest.send();
        TransactionReceipt result = send.getResult();

        log.info("txReceipt: {}", result);

        List<Log> logs = result.getLogs();
        Optional<Log> interested = Optional.ofNullable(logs)
                .orElseGet(Collections::emptyList)
                .stream().filter(txLog -> AddressUtils.equals(serverProperties.getContractAddress(), txLog.getAddress())).findAny();

        if (interested.isPresent()) {
            List<Abi.RecordedEventResponse> recordedEvents = abi.getRecordedEvents(result);
            for (Abi.RecordedEventResponse recordedEvent : recordedEvents) {
                log.info("contract changed sender {}, text: {}, time: {}", recordedEvent._sender, new String(recordedEvent._text), recordedEvent._time);
            }
        }
    }
}
