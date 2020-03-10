package com.roslonek.contracts.repositories;

import com.roslonek.contracts.entities.UserDetails;
import com.roslonek.contracts.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {


    @Query("from Worker where userId=:userId and active=:active")
    List<Worker> findWorkers(@Param("userId") int number, @Param("active") boolean state);
}
