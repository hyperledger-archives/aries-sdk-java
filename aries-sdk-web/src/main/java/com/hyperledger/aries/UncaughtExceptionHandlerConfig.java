package com.hyperledger.aries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class UncaughtExceptionHandlerConfig {
    
    private static final Logger LOG = LoggerFactory.getLogger(UncaughtExceptionHandlerConfig.class);

    @EventListener(ApplicationReadyEvent.class)
    public void setDefaultHandler() {
        LOG.info("Setting uncaught error handler.");
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> LOG.error("Uncaught error occurred: ", e));
    }
    
}
