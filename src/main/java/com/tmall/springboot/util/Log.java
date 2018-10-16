package com.tmall.springboot.util;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

public class Log {
    public static  Logger logger = org.apache.log4j.Logger.getLogger(Log.class);
    static {
        PropertyConfigurator.configure("/Users/cloud/Desktop/frontEndTestCode/java_tmall/tmall_java/src/main/resources/log4j.properties");
    }

}
