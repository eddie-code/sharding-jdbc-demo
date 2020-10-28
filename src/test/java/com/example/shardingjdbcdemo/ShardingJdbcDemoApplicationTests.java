package com.example.shardingjdbcdemo;

import com.example.shardingjdbcdemo.dao.AreaMapper;
import com.example.shardingjdbcdemo.dao.OrderItemMapper;
import com.example.shardingjdbcdemo.dao.OrderMapper;
import com.example.shardingjdbcdemo.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ShardingJdbcDemoApplicationTests {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testOrder() {
        Order order = new Order();

        order.setUserId(20);
        order.setOrderId(4);
        order.setOrderAmount(BigDecimal.TEN);
        order.setOrderStatus(1);

        //  配置 t_order 分库策略
        System.out.println("ds" + order.getUserId() % 2);

        //  配置 t_order 分表策略
        System.out.println("t_order_" + order.getOrderId() % 2 + 1);

        orderMapper.insert(order);
    }

    @Test
    public void testSelectOrder() {
        OrderExample orderExample = new OrderExample();
        // select id, order_amount, order_status, user_id from t_order WHERE ( id = 4 and user_id = 20 )
        orderExample.createCriteria().andOrderIdEqualTo(4).andUserIdEqualTo(20);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        orders.forEach(item -> System.out.println(item.getOrderId() + "---" + item.getUserId()));

    }

    @Test
    public void testGlobal() {
        Area area = new Area();
        area.setId(2);
        area.setName("深圳");
        areaMapper.insert(area);
    }

    @Test
    public void  testSelectGlobal() {
        AreaExample areaExample = new AreaExample();
        areaExample.createCriteria().andIdEqualTo(2);
        List<Area> areas = areaMapper.selectByExample(areaExample);
        System.out.println("area.size() = " + areas.size());
    }

    @Test
    public void testOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(2);
        orderItem.setOrderId(1);
        orderItem.setPruductName("测试商品");
        orderItem.setNum(1);
        orderItem.setUserId(19);
        orderItemMapper.insert(orderItem);
    }

    @Test
    public void testMsOrder() {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUserIdEqualTo(20).andOrderIdEqualTo(4);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        for (int i = 0; i < 10; i++) {
            orders.forEach(item->{
                System.out.println("================");
                System.out.println("userId = " + item.getUserId());
                System.out.println("orderId = " + item.getOrderId());
                // 通过修改从库的金额，在遍历出来，发现全部是100.00.证明都是在从库读取数据的，并非是随机
                System.out.println("amount = " + item.getOrderAmount());
            });
        }
    }

}
