package com.miti.meeti.MitiExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MitiService{
    private ScheduledExecutorService scheduledExecutorService;
    public MitiService(int i){
        scheduledExecutorService = Executors.newScheduledThreadPool(i);
    }
    public void schedule (Runnable thread, long initialDelay, long period, TimeUnit timeunit){
        scheduledExecutorService.scheduleWithFixedDelay(thread,initialDelay,period,timeunit);
    }
    public void shutdown(){
        scheduledExecutorService.shutdown();
    }
}
