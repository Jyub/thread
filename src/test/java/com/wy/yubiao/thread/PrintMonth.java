package com.wy.yubiao.thread;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/8/2 15:30
 * @description: 打印月日历
 */
public class PrintMonth {

    public static void main(String[] args) {
        LocalDate date = LocalDate.now () ;
        int month = date.getMonthValue() ;
        int today = date.getDayOfMonth () ;
        date = date . minusDays ( today - 1 ) ; // Set to start of month
        DayOfWeek weekday = date . getDayOfWeek ( ) ;
        int value = weekday.getValue();
        System . out . println ( "Mon\tTue\tWed\tThu\tFri\tSat\tSun " ) ;
        for ( int i = 1 ; i < value ; i++ )
            System . out . print ( "\t");
        while ( date.getMonthValue() == month )
        {
            if ( date . getDayOfMonth ( ) == today )
                System.err.printf( "%2d " , date.getDayOfMonth ()) ;
            else
                System . out .printf ( "%2d " , date.getDayOfMonth ()) ;
            System . out . print ("\t") ;
            date = date . plusDays (1) ;
            if ( date . getDayOfWeek ( ) . getValue ( ) == 1 ) System . out . println ( ) ;
        }
        if ( date . getDayOfWeek() . getValue() != 1 ) System . out . println();

        Date date1 =new Date();
        date1.setTime(date1.getTime()-100000);
    }
}
