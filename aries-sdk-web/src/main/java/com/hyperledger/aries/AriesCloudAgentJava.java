package com.hyperledger.aries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AriesCloudAgentJava {
    public static void main(final String args[]) {
        SpringApplication.run(AriesCloudAgentJava.class, args);
    }
}
