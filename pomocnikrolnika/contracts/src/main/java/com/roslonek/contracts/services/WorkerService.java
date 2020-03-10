package com.roslonek.contracts.services;

import com.roslonek.contracts.entities.Worker;

import java.util.List;

public interface WorkerService {
    void deactivateAllWorkers(long idi);
    List<Worker> searchWorker(long userId, String search);

    void printAllBeginning(String startDate, String endDate, List<Worker> workers);

    void contracts(Worker worker, String startDate, String endDate);

    void settlement(Worker worker, String endDate);


}
