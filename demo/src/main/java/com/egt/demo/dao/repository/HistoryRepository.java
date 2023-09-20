package com.egt.demo.dao.repository;

import com.egt.demo.model.RatesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface HistoryRepository extends JpaRepository<RatesHistory, String> {

    @Query(value = "SELECT request_id, client_id, service_name, time FROM rates.request_history WHERE request_id = ?1", nativeQuery = true)
    RatesHistory findRecordIdByRequestId(String id);

}
