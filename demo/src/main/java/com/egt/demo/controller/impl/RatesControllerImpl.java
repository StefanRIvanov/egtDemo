package com.egt.demo.controller.impl;

import com.egt.demo.controller.RatesController;
import com.egt.demo.service.RatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class RatesControllerImpl implements RatesController {

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RatesService ratesService;

    @Override
    @Scheduled(cron = "${cron.rates}")
    public void saveRates() {
        try{
            ratesService.save();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Unable to save rates!");
        }
    }
}
