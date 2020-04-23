package com.atguigu.util;

public class TimeSpan {
    public long timespan;
    private long years;
    private long months;
    private long days;

    public long getYears() {
        return timespan / (365 * 24 * 60 * 60 * 1000);
    }

    public long getMonths() {
        return timespan / (30 * 24 * 60 * 60 * 1000);
    }

    public long getDays() {
        return timespan / (24 * 60 * 60 * 1000);
    }
}