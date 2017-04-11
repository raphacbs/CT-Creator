/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;


/**
 *
 * @author raphael.da.silva
 */
public class FunctiosDates {
    

    public static Date stringToDate(String stringDate) {
        Date date = null;
        try {
            SimpleDateFormat sdf
                    = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt_BR"));
            date = sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
            
        }
        return date;
    }

    public static int deductDates(Date initialDate, Date finalDate) {
        if (initialDate == null || finalDate == null) {
            return 0;
        }
        int days = (int) ((finalDate.getTime() - initialDate.getTime()) / (24 * 60 * 60 * 1000));
        return (days > 0 ? days : 0);
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf
                = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt_BR"));
        String dateFormated = sdf.format(date);
        return dateFormated;
    }
    
    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf
                = new SimpleDateFormat(format, new Locale("pt_BR"));
        String dateFormated = sdf.format(date);
        return dateFormated;
    }

    public static Date clearHour(Date date) {
        return (stringToDate(dateToString(date)));
    }

    public static int getQtdWorkingDays(Date initialDate, Date finalDate) {
        int workingDays = 0;
        int totalDays = deductDates(initialDate, finalDate);
        //tah eu sei que getDay, getYar e getMonth sao deprecated, eu coloquei so pra teste.
        Calendar calendar = new GregorianCalendar(finalDate.getYear(), finalDate.getMonth(), finalDate.getDay());
        for (int i = 0; i <= totalDays; i++) {
            if (!(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && !(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                workingDays++;
            }
            calendar.add(Calendar.DATE, 1);
        }
        return workingDays;
    }
    
    public static List<Date> getWorkingDays(Date initialDate, Date finalDate) {
        List<Date> listDates = new ArrayList<Date>();      
       
        int totalDays = deductDates(initialDate, finalDate);
        //tah eu sei que getDay, getYar e getMonth sao deprecated, eu coloquei so pra teste.
//        Calendar calendar = new GregorianCalendar(finalDate.getYear(), finalDate.getMonth(), finalDate.getDay());
        Calendar calendar = new GregorianCalendar();
		//Setando o calendar com a Data Inicial
	calendar.setTime(initialDate);
        for (int i = 0; i <= totalDays; i++) {
            if (!(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && !(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                System.out.println("data "+ calendar.get(Calendar.DAY_OF_WEEK));
                System.out.println(calendar.getTime());
                listDates.add(calendar.getTime());
            }
            calendar.add(Calendar.DATE, 1);
        }
        return listDates;
    }
    
    public static boolean finishDateBiggerThenStartDate(Date start, Date finish){
       if(start.before(finish)){
           return true;
       }else{
           return false;
       }
    }
    
    public static String getDateAcutualString(){
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
	   //get current date time with Date()
	   Date date = new Date();
	   System.out.println(dateFormat.format(date));
	  
	   //get current date time with Calendar()
	   Calendar cal = Calendar.getInstance();
	   return dateFormat.format(cal.getTime());

    }
    
    public static Date getDateActual(){
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
	   //get current date time with Date()
	   Date date = new Date();
	   System.out.println(dateFormat.format(date));
	  
	   //get current date time with Calendar()
	   Calendar cal = Calendar.getInstance();
	   return cal.getTime();

    }
}
