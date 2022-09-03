package com.wjl.citademo.rest;

import cn.hutool.core.util.ReflectUtil;
import com.citahub.cita.abi.TypeReference;
import com.citahub.cita.abi.datatypes.DynamicArray;
import com.citahub.cita.abi.datatypes.Function;
import com.citahub.cita.abi.datatypes.Type;
import com.citahub.cita.abi.datatypes.Utf8String;
import com.citahub.cita.abi.datatypes.generated.Uint256;
import com.citahub.cita.protocol.CITAj;
import com.citahub.cita.protocol.account.Account;
import com.citahub.cita.protocol.account.CompiledContract;
import com.citahub.cita.protocol.core.DefaultBlockParameterName;
import com.citahub.cita.protocol.core.RemoteCall;
import com.citahub.cita.protocol.core.Request;
import com.citahub.cita.protocol.core.methods.response.*;
import com.citahub.cita.tx.TransactionManager;
import com.citahub.cita.utils.Numeric;
import com.citahub.cita.utils.TypedAbi;
import com.wjl.citademo.codegen.Abi;
import com.wjl.citademo.config.ServerProperties;
import io.reactivex.disposables.Disposable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RequiredArgsConstructor
@RestController
public class IndexController {

    private final Account account;

    private final CITAj citaj;

    private final ServerProperties serverProperties;

    private final Abi abi;

    private final TransactionManager transactionManager;

    @PostMapping("/records/deploy")
    public Object deploy() throws Exception {
        CompiledContract contract = new CompiledContract(Abi.ABI);
        ReflectUtil.setFieldValue(contract, "bin", Abi.BINARY);
        String data = contract.getBin();
        AppSendTransaction appSendTransaction = transactionManager
                .sendTransaction("", data, 3000000L, String.valueOf(System.currentTimeMillis()), getBlockLimit(citaj).longValue(),
                        getVersion(citaj), getChainId(citaj), "");
        AppSendTransaction.SendTransactionResult sendTransactionResult = appSendTransaction.getSendTransactionResult();
        return sendTransactionResult;
    }


    @GetMapping("/records/latest")
    public Object getLatestRecord() throws IOException {
        Function function = new Function("getList", Collections.emptyList(), Collections.singletonList(new TypeReference<DynamicArray<Uint256>>() {
        }));
        return account.appCall(serverProperties.getContractAddress(), function, Collections.singletonList(TypedAbi.ArgRetType.newDynamicArray(BigInteger.class)));
    }

    @GetMapping("/records/latest/{category}")
    public Object getLatestRecord(@PathVariable("category") Long category) throws Exception {
        RemoteCall<String> stringRemoteCall = abi.get(BigInteger.valueOf(category));
        return stringRemoteCall.send();
    }

    @PostMapping("/records")
    public Object addRecord(String data) throws Exception {
        /*RemoteCall<TransactionReceipt> add = abi.add(data, BigInteger.valueOf(System.currentTimeMillis()),
                300000L,
                String.valueOf(System.currentTimeMillis()),
                getBlockLimit(citaj).longValue(),
                getVersion(citaj),
                getChainId(citaj),
                ""
        );
        TransactionReceipt send = add.send();
        return send;*/


        Function function = new Function("add", Arrays.asList(new Utf8String(data), new Uint256(System.currentTimeMillis())), Collections.emptyList());
        AppSendTransaction transaction = (AppSendTransaction) account.sendTransaction(serverProperties.getContractAddress(), function, String.valueOf(System.currentTimeMillis()), 3000000L, getVersion(citaj), getChainId(citaj), "");

        String hash = transaction.getSendTransactionResult().getHash();
        log.info("send transaction success, txHash is {}", hash);

        new Thread(() -> {
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            while (atomicBoolean.get()) {
                Request<?, AppTransaction> appTransactionRequest = citaj.appGetTransactionByHash(hash);
                appTransactionRequest.flowable().subscribe(appTx -> {
                    Transaction tx = appTx.getTransaction();
                    if (tx == null) {
                        log.info("tx is null");
                    } else {
                        atomicBoolean.set(false);
                        BigInteger blockNumber = tx.getBlockNumber();
                        log.info("send to chain success, blockNumber is {}", blockNumber);
                    }
                });
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        return transaction;
    }

    int getVersion(CITAj service) {
        AppMetaData appMetaData = null;
        try {
            appMetaData = service.appMetaData(DefaultBlockParameterName.PENDING).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appMetaData.getAppMetaDataResult().getVersion();
    }

    BigInteger getChainId(CITAj service) {
        AppMetaData appMetaData = null;
        try {
            appMetaData = service.appMetaData(DefaultBlockParameterName.PENDING).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appMetaData.getAppMetaDataResult().getChainId();
    }

    BigInteger getBlockLimit(CITAj service) {
        try {
            return service.appBlockNumber().send().getBlockNumber().add(BigInteger.valueOf(80));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
