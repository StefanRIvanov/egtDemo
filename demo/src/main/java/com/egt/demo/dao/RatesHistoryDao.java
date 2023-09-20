package com.egt.demo.dao;

import com.egt.demo.model.RatesHistory;

public interface RatesHistoryDao {

    boolean save(RatesHistory rates);

    RatesHistory get(String requestId);
}
