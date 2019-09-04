package com.zws.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.rolling.RollingFileAppender;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
public class LoggerBuilder {

    private static final Map<String,Logger> container = new HashMap<>();
    public Logger getLogger(String name) {
        Logger logger = container.get(name);
        if(logger != null) {
            return logger;
        }
        synchronized (LoggerBuilder.class) {
            logger = container.get(name);
            if(logger != null) {
                return logger;
            }
            logger = build(name);
            container.put(name,logger);
        }
        return logger;
    }




    private static Logger build(String name) {
        RollingFileAppender errorAppender =new AppenderTest().getAppender(name,Level.ERROR);
        RollingFileAppender infoAppender =new AppenderTest().getAppender(name,Level.INFO);
        RollingFileAppender warnAppender =new AppenderTest().getAppender(name,Level.WARN);
        RollingFileAppender debugAppender =new AppenderTest().getAppender(name,Level.DEBUG);
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger("FILE-" + name);
        //设置不向上级打印信息
        logger.setAdditive(false);
        logger.addAppender(errorAppender);
        logger.addAppender(infoAppender);
        logger.addAppender(warnAppender);
        logger.addAppender(debugAppender);

        return logger;
    }
}

