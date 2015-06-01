package com.easylife;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by cpe on 01/06/2015.
 */
public class LogTest {

    private static final Log logger = LogFactory.getLog("LogTest") ;

    @BeforeTest
    public void getLogConfiguration() {
        if(logger.isDebugEnabled()) logger.debug("Debug is enabled");
        final String[] attributeNames = LogFactory.getFactory().getAttributeNames();
        for (int i = 0; i < attributeNames.length; i++) {
            System.out.println(attributeNames[i] + LogFactory.getFactory().getAttribute(attributeNames[i])) ;
        }

        System.out.println("Log configuration printed") ;
    }

    @Test
    public void testInfo() {
        logger.info("I am an info level message");
    }

    @Test
    public void testDebug() {
        logger.debug("I am an debug level message") ;
    }
}
