package org.example;

import java.util.List;

public interface IScheduleWork {
    IWork forAction(IRunNotSafeAction action);
    List<IWork> getJobs();

}
