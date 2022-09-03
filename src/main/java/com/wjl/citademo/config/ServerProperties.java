package com.wjl.citademo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class ServerProperties {

    private String nodeRpcAddress;

    private String contractAddress;

    private String privateKey;

}
