package com.inventory.Inventory.Management.amqp;

import com.inventory.Inventory.Management.domain.User;
import com.inventory.Inventory.Management.repository.UserRepository;
import com.inventory.Inventory.Management.service.ScheduledReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportScheduler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduledReportService scheduledReportService;

    @Scheduled(cron = "18 0 0 * * ?")
    public void scheduleReportGeneration() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> {

        });
    }

}
