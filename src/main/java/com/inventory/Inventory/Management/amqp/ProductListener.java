package com.inventory.Inventory.Management.amqp;

import com.inventory.Inventory.Management.dto.ProductDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProductListener {

    @RabbitListener(queues = "stock.low")
    public void readLowStockMessages(@Payload ProductDTO productDTO) {
        System.out.println("Message received: " + productDTO);
    }

}
