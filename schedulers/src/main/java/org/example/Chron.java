package org.example;

import java.time.Duration;
import java.time.LocalDateTime;

public class Chron {

    private LocalDateTime start = LocalDateTime.now();
    private LocalDateTime end;
    private int maxCount =-1;
    private Duration interval = Duration.ofSeconds(1);
    private int counter;

    private Chron(){}

    public static Chron builder(){
        return new Chron();
    }

    public Chron setStartTime(LocalDateTime start) {
        this.start = start;
        return this;
    }

    public Chron setEndDate(LocalDateTime end) {
        this.end= end;
        return this;
    }

    public Chron setMaxExecutionTimes(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    public Chron setIntervalDuration(Duration interval) {
        this.interval = interval;
        return this;
    }

    public IProvideNextExecutionTime buildNextTimeExecutionProvider() {

        return ()->getNextTime();
    }

    private LocalDateTime getNextTime() {
        if(end!=null && end.isBefore(start))
            return null;
        if(maxCount>0 && counter>maxCount) return null;
        if(counter==0){
            counter++;
            return start;
        }
        counter++;
        start=start.plus(interval);
        return start;
    }
}
