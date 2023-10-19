package org.example;

import java.net.http.WebSocket;

public interface IWork {
    IWork useExecutionTimeProvider(IProvideNextExecutionTime provider);

    IWork onError(IHandleExceptions errorHandler);

    IWork onSingleActionCompleted(ICompleteTasks completedAction);

    IWork onCompleted(ICompleteTasks completeAction);

    void schedule();

    void execute();
}
