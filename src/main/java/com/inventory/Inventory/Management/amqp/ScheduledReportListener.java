package com.inventory.Inventory.Management.amqp;

import com.inventory.Inventory.Management.domain.User;
import com.inventory.Inventory.Management.infra.exceptions.UserException;
import com.inventory.Inventory.Management.repository.UserRepository;
import com.inventory.Inventory.Management.service.SpreadsheetReportService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ScheduledReportListener {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpreadsheetReportService spreadsheetReportService;

    @RabbitListener(queues = "reportQueue")
    public void readScheduledReport(@Payload Long userId) {
        Optional<User> existingUser = userRepository.findById(userId);

        if (existingUser.isEmpty()) {
            throw new UserException("User not found");
        }

        spreadsheetReportService.generateSpreadsheetProductReport(userId);

    }

}
