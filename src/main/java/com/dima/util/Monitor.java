package com.dima.util;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class Monitor implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.println(applicationEvent.getClass().getSimpleName() + " > " + applicationEvent.getSource().toString());
    }
}
