package org.example;

import java.util.ArrayList;
import java.util.List;

public class Scheduler implements IScheduleWork{

    private List<IWork> jobs = new ArrayList<>();

    private static Scheduler instance;

    static { instance=new Scheduler(); }
    private Scheduler(){}

    public static Scheduler getInstance() {
        return instance;
    }

    @Override
    public IWork forAction(IRunNotSafeAction action) {
        return new Job(action, this);
    }

    @Override
    public List<IWork> getJobs() {
        return jobs;
    }
}
