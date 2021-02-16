package io.akash.openbanking.accountaggregator.accountdiscovery.helper.util;

import java.time.Instant;

import org.springframework.stereotype.Component;


/**
 * This class contains any generic operations pertaining to the use of date.
 * 
 * @author 
 * @version 1.0
 * @since   2019-03-27
 * 
 **/

@Component
public class DateUtil {

    /**
     * @return current timestamp
     */
    public static String getCurrentTimeStamp(){
           Instant instant=Instant.now();
           String timeStamp=instant.toString();
           return timeStamp;
    }
}
