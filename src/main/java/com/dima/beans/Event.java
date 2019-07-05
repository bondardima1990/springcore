package com.dima.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope("prototype")
public class Event {

    private static final AtomicInteger AUTO_ID = new AtomicInteger(0);

    public static boolean isDay(int start, int end) {
        LocalTime localTime = LocalTime.now();
        return localTime.getHour() > start && localTime.getHour() < end;
    }

    public static void initAutoId(int id) {
        AUTO_ID.set(id);
    }

    private int id;
    private String msg;

    @Value("#{new java.util.Date()}")
    private Date date;

    @Value("#{T(java.text.DateFormat).getDateTimeInstance()}")
    private DateFormat dateFormat;

    public Event() {
        this.id = AUTO_ID.getAndIncrement();
    }

    public Event(Date date, DateFormat dateFormat) {
        this();
        this.date = date;
        this.dateFormat = dateFormat;
    }

    public Event(Integer id, Date date, String msg) {
        this.id = id;
        this.date = date;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", msg=" + msg + ", date=" + (dateFormat != null ? dateFormat.format(date) : date) + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Event other = (Event) o;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
