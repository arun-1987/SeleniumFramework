package com.selenium.ui.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {



    /**
     * Function to get the current date
     * @param type
     * @return
     */
    public String getTodayDate(String type){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now;
        if(type.equalsIgnoreCase("today")){
            now = LocalDateTime.now();
        }else if(type.equalsIgnoreCase("Weekly")){
            now = LocalDateTime.now().plusDays(7);
        }else if(type.equalsIgnoreCase("Every Other Week")){
            now = LocalDateTime.now().plusDays(14);
        }else if(type.equalsIgnoreCase("Monthly")){
            now = LocalDateTime.now().plusDays(31);
        }
        else {
            now = LocalDateTime.now().plusDays(16);
        }
        return dtf.format(now).toString();
    }

    /**
     * Function to get the current date
     * @param type
     * @return
     */
    public String getTodayDate(String type,String pattern){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now;
        if(type.equalsIgnoreCase("today")){
            now = LocalDateTime.now();
        }else {
            now = LocalDateTime.now().plusMonths(1);
        }
        return dtf.format(now).toString();
    }


    /**
     *
     * @param no
     * @return
     */
    public String getTodayDatePlus(int no){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now;
        now = LocalDateTime.now().plusDays(no);
        return dtf.format(now).toString();
    }

    /**
     *
     * @param no
     * @return
     */
    public String getTodayDatePlus(int no,String pattern){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now;
        now = LocalDateTime.now().plusDays(no);
        return dtf.format(now).toString();
    }

    /**
     *
     * @return
     */
    public int getTodayDay(){
        return LocalDateTime.now().getDayOfYear();
    }

}
