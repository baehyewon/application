package com.example.hyenee.listviewpractice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class AlarmUtil {

    public static void setAlarm(Context context, int AlarmId, String Name, String Phone, String Birthday, String Message, int year, int month, int day, int hour, int minute)
    {
        // AlarmManager 호출
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance( );  // 현재 날짜/시간 등의 각종 정보 얻기

        /*
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        */

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 00);

        /*
        if(hour < 12)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
            */

        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+09:00"));

        gregorianCalendar.set(year, month, day, hour, minute, 00);

        Intent intent = new Intent(context, AlarmActivity.class);

        intent.setAction("Alarm");
        intent.addCategory("setAlarm");

        intent.putExtra("alarmId", AlarmId);
        intent.putExtra("name", Name);
        intent.putExtra("phone", Phone);
        intent.putExtra("birthday", Birthday);
        intent.putExtra("message", Message);

        PendingIntent pi = PendingIntent.getActivity(context, AlarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        manager.setRepeating(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }

    public static void setSms(Context context, int AlarmId, String Name, String Phone, String Message, int year, int month, int day)
    {
        // AlarmManager 호출
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance( );  // 현재 날짜/시간 등의 각종 정보 얻기

        /*
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        */

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        //당일 00시로 셋팅
        calendar.set(Calendar.HOUR, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);

        /*
        if(hour < 12)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
            */

        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+09:00"));

        //당일 00시로 셋팅
        gregorianCalendar.set(year, month, day, 00, 00, 00);

        Intent intent = new Intent(context, SmsActivity.class);

        intent.setAction("Sms");
        intent.addCategory("setSms");

        intent.putExtra("alarmId", AlarmId);
        intent.putExtra("name", Name);
        intent.putExtra("phone", Phone);
        intent.putExtra("message", Message);

        PendingIntent pi = PendingIntent.getActivity(context, AlarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        manager.setRepeating(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }

    public static void unregisterAlarm(Context context, int AlarmID)
    {
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.setAction("Alarm");
        intent.addCategory("setAlarm");

        PendingIntent pi = PendingIntent.getActivity(context, AlarmID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        pi.cancel();
        alarmManager.cancel(pi);
    }

    public static void unregisterSms(Context context, int AlarmID)
    {
        Intent intent = new Intent(context, SmsActivity.class);
        intent.setAction("Sms");
        intent.addCategory("setSms");

        PendingIntent pi = PendingIntent.getActivity(context, AlarmID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        pi.cancel();
        alarmManager.cancel(pi);
    }}
