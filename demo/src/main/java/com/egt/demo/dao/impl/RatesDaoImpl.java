package com.egt.demo.dao.impl;

import com.egt.demo.dao.RatesDao;
import com.egt.demo.dao.repository.RatesRepository;
import com.egt.demo.model.Rates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RatesDaoImpl implements RatesDao {

    @Autowired(required = true)
    RatesRepository ratesRepository;

    @Override
    public boolean save(Rates rates) {
        ratesRepository.save(rates);
        return false;
    }

    @Override
    public Map getLatestByCurrency(String currency) {
        return ratesRepository.findLatestRecordByCurrency(currency);
    }

    @Override
    public List getRatesByPeriodAndCurrency(int period, String currency) {
        return ratesRepository.getRatesByPeriodAndCurrency(period, currency);
    }
}
