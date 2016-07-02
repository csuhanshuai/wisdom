package com.cnst.wisdom.model.bean;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author pengjingnag.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class CreateTimeEntity
{
    /**
     * nanos : 0
     * time : 1453184260000
     * minutes : 17
     * seconds : 40
     * hours : 14
     * month : 0
     * timezoneOffset : -480
     * year : 116
     * day : 2
     * date : 19
     */

    private int nanos;
    private long time;
    private int minutes;
    private int seconds;
    private int hours;
    private int month;
    private int timezoneOffset;
    private int year;
    private int day; //周几
    private int date;//几号

    public String getShowDate()
    {
        return year +1900+"-"+month+1+"-"+date;
    }

    public void setShowDate(String showDate)
    {
        try{
            String[] temp = showDate.split("-");
            this.year = Integer.valueOf(temp[0]) - 1900;
            this.month = Integer.valueOf(temp[1]) - 1;
            this.day = Integer.valueOf(temp[2]);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void setNanos(int nanos)
    {
        this.nanos = nanos;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public void setMinutes(int minutes)
    {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds)
    {
        this.seconds = seconds;
    }

    public void setHours(int hours)
    {
        this.hours = hours;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public void setTimezoneOffset(int timezoneOffset)
    {
        this.timezoneOffset = timezoneOffset;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public void setDate(int date)
    {
        this.date = date;
    }

    public int getNanos()
    {
        return nanos;
    }

    public long getTime()
    {
        return time;
    }

    public int getMinutes()
    {
        return minutes;
    }

    public int getSeconds()
    {
        return seconds;
    }

    public int getHours()
    {
        return hours;
    }

    public int getMonth()
    {
        return month;
    }

    public int getTimezoneOffset()
    {
        return timezoneOffset;
    }

    public int getYear()
    {
        return year;
    }

    public int getDay()
    {
        return day;
    }

    public int getDate()
    {
        return date;
    }
}
