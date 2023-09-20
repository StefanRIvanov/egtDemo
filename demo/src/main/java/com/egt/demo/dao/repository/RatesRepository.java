package com.egt.demo.dao.repository;

import com.egt.demo.model.Rates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface RatesRepository extends JpaRepository<Rates, Integer> {

    @Query(value = "SELECT rate FROM rates.rates WHERE updated_at = (SELECT MAX(updated_at) FROM rates.rates) AND currency = :currency", nativeQuery = true)
    Map findLatestRecordByCurrency(String currency);

    @Query(value = "SELECT rate FROM rates.rates WHERE updated_at > date_sub(now(), interval :period hour) AND currency = :currency", nativeQuery = true)
    List getRatesByPeriodAndCurrency(int period, String currency);
}
