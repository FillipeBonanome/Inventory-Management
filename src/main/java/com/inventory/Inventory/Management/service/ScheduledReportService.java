package com.inventory.Inventory.Management.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduledReportService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendScheduledReport(Long userId) {
        rabbitTemplate.convertAndSend("reportQueue", userId);
    }

}
