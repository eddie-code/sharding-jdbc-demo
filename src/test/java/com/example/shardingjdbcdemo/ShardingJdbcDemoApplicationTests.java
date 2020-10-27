package com.example.shardingjdbcdemo;

import com.example.shardingjdbcdemo.dao.OrderMapper;
import com.example.shardingjdbcdemo.model.Order;
import com.example.shardingjdbcdemo.model.OrderExample;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ShardingJdbcDemoApplicationTests {

    @Resource
    private OrderMapper orderMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testOrder() {
        Order order = new Order();

        order.setUserId(20);
        order.setId(4);
        order.setOrderAmount(BigDecimal.TEN);
        order.setOrderStatus(1);

        //  配置 t_order 分库策略
        System.out.println("ds" + order.getUserId() % 2);

        //  配置 t_order 分表策略
        System.out.println("t_order_" + order.getId() % 2 + 1);

        orderMapper.insert(order);
    }

    @Test
    public void testSelectOrder() {
        OrderExample orderExample = new OrderExample();
        // select id, order_amount, order_status, user_id from t_order WHERE ( id = 4 and user_id = 20 )
        orderExample.createCriteria().andIdEqualTo(4).andUserIdEqualTo(20);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        orders.forEach(item -> System.out.println(item.getId() + "---" + item.getUserId()));

    }

}
