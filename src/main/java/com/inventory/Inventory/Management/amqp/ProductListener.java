package com.inventory.Inventory.Management.amqp;

import com.inventory.Inventory.Management.domain.User;
import com.inventory.Inventory.Management.dto.ProductDTO;
import com.inventory.Inventory.Management.dto.UserDTO;
import com.inventory.Inventory.Management.service.MailService;
import com.inventory.Inventory.Management.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductListener {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @RabbitListener(queues = "stock.low")
    public void readLowStockMessages(@Payload ProductDTO productDTO) {

        UserDTO user = userService.getUser(productDTO.userId());
        String subjectText = """
                Product %s is at critical number
                """.formatted(productDTO.name());
        String message = """
                    Your product %s is at critical number,
                    here is your product info:
                    %s
                    %s
                    %s
                    $ %s
                """.formatted(productDTO.name(), productDTO.name(), productDTO.description(), productDTO.quantity(), productDTO.price());
        
        mailService.sendTextMail(user.email(), subjectText, message);
    }

}
