package com.example.shardingjdbcdemo.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author eddie.lee
 * @blog blog.eddilee.cn
 * @ProjectName sharding-jdbc-demo
 * @Package com.example.shardingjdbcdemo.sharding
 * @ClassName MySharding
 * @description
 * @date created in 2020-11-02 15:36
 * @modified by
 */
public class MySharding implements PreciseShardingAlgorithm<Long> {

    /**
     * @param collection    获取到的表名
     * @param shardingValue 获取到的主键和生成好的ID值
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> shardingValue) {
        Long id = shardingValue.getValue();

        // 取余
        long mode = id.hashCode() % collection.size();
        String[] strings = collection.toArray(new String[0]);
        // mode 是复数，需要通过math取绝对值
        mode = Math.abs(mode);
        // mode=1  = t_order_2的表
        System.out.println("mode=" + mode);
        System.out.println(strings[0] + "========" + strings[1]);

        return strings[(int) mode];
    }

}
