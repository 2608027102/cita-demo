package com.wjl.citademo.config;

import com.citahub.cita.crypto.Credentials;
import com.citahub.cita.protocol.CITAj;
import com.citahub.cita.protocol.account.Account;
import com.citahub.cita.protocol.http.HttpService;
import com.citahub.cita.tx.RawTransactionManager;
import com.citahub.cita.tx.TransactionManager;
import com.wjl.citademo.codegen.Abi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class CitaConfig {

    @Bean
    public CITAj citaJ(ServerProperties serverProperties) {
        return CITAj.build(new HttpService(serverProperties.getNodeRpcAddress()));
    }

    @Bean
    public Account account(ServerProperties serverProperties, CITAj citAj) {
        return new Account(serverProperties.getPrivateKey(), citAj);
    }

    @Bean
    public TransactionManager transactionManager(CITAj citAj, ServerProperties serverProperties) {
        return new RawTransactionManager(citAj, Credentials.create(serverProperties.getPrivateKey()));
    }

    @Bean
    public Abi abi(ServerProperties serverProperties, CITAj citAj, TransactionManager transactionManager) throws IOException {
        return Abi.load(serverProperties.getContractAddress(), citAj, transactionManager);
    }
}
