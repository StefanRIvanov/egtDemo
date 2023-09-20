package com.egt.demo.dao;

import com.egt.demo.model.Rates;

import java.util.List;
import java.util.Map;

public interface RatesDao {

    boolean save(Rates rates);

    Map getLatestByCurrency(String currency);

    List getRatesByPeriodAndCurrency(int period, String currency);
}
