package com.codehows.zegozero.listener;

import com.codehows.zegozero.entity.System_time;
import com.codehows.zegozero.repository.SystemTimeRepository;
import com.codehows.zegozero.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class ServerStartListener {

    @Autowired
    private SystemTimeRepository serverStartTimeRepository;

    @Autowired
    private TimeService timeService;

    @EventListener(ApplicationReadyEvent.class)
    public void logServerStartTime() {


        if(timeService.getTimeFromDB() == null) {
            System_time serverStartTime = new System_time();
            serverStartTime.setTime(new Date());
            serverStartTimeRepository.save(serverStartTime);
            System.out.println("서버 시작 시간: " + serverStartTime.getTime());
        }
    }
}
