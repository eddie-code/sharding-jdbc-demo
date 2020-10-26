package com.example.shardingjdbcdemo;

import com.example.shardingjdbcdemo.dao.OrderMapper;
import com.example.shardingjdbcdemo.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class ShardingJdbcDemoApplicationTests {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testOrder() {
        Order order = new Order();

        order.setUserId(19);
        order.setId(1);
        order.setOrderAmount(BigDecimal.TEN);
        order.setOrderStatus(1);
        orderMapper.insert(order);
    }
}
