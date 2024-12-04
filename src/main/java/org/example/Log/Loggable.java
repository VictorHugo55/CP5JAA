package org.example.Log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Loggable <T> {
    Logger LOGGER = LogManager.getLogger(Loggable.class);

    default void logInfo(String mensagem){
        LOGGER.info(mensagem);
    }

    default void logErro(T mensagem){
        LOGGER.error(mensagem);
    }
}
