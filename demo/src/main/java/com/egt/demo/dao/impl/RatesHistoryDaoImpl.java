package com.egt.demo.dao.impl;

import com.egt.demo.dao.repository.HistoryRepository;
import com.egt.demo.dao.RatesHistoryDao;
import com.egt.demo.model.RatesHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RatesHistoryDaoImpl implements RatesHistoryDao {

    @Autowired(required = true)
    HistoryRepository ratesReposiory;

    @Override
    public boolean save(RatesHistory ratesHistory) {
        ratesReposiory.save(ratesHistory);
        return false;
    }

    @Override
    public RatesHistory get(String requestId) {
        return ratesReposiory.findRecordIdByRequestId(requestId);
    }
}
