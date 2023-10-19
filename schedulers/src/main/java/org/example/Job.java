package org.example;

import java.time.LocalDateTime;

public class Job implements IWork{

    private LocalDateTime nextExecutionTime = LocalDateTime.now();
    private IRunNotSafeAction action;
    private IScheduleWork scheduler;
    private IProvideNextExecutionTime nextExecutionTimeProvider;
    private IHandleExceptions errorHandler = (ex)->{};
    private ICompleteTasks singleActionCompleted=()->{};
    private ICompleteTasks onCompleted=()->{};



    public Job(IRunNotSafeAction action, IScheduleWork scheduler){
        this.action=action;
        this.scheduler=scheduler;
    }

    @Override
    public IWork useExecutionTimeProvider(IProvideNextExecutionTime provider) {
         nextExecutionTimeProvider=provider;
         nextExecutionTime = nextExecutionTimeProvider.provideNextExecutionTime();
         return this;
    }

    @Override
    public IWork onError(IHandleExceptions errorHandler) {
        this.errorHandler=errorHandler;
        return this;
    }

    @Override
    public IWork onSingleActionCompleted(ICompleteTasks completedAction) {
        singleActionCompleted=completedAction;
        return this;
    }

    @Override
    public IWork onCompleted(ICompleteTasks completeAction) {
        this.onCompleted=completeAction;
        return this;
    }

    @Override
    public void schedule() {
        scheduler.getJobs().add(this);
    }


    @Override
    public void execute() {
        if(nextExecutionTime==null) return;
        if(nextExecutionTime.isAfter(LocalDateTime.now()))return;
        try{
            action.executeNotSafeAction();
            singleActionCompleted.complete();
        }catch (Exception ex){
            errorHandler.handle(ex);
        }
        finally {
            if(nextExecutionTimeProvider==null) return;
            nextExecutionTime = nextExecutionTimeProvider.provideNextExecutionTime();
            if(nextExecutionTime == null)
                onCompleted.complete();
        }
    }
}
