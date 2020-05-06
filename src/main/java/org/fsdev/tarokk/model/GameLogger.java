package org.fsdev.tarokk.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameLogger {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Lazy
    @Autowired
    private SimpMessagingTemplate broker;

    public void log(String message, Object... params) {
        String log = String.format(message, params);
        logger.info("LOG: " + log);
        broker.convertAndSend("/game/log", log);
    }

}
