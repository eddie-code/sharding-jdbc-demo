[TOC]

# sharding-jdbc-demo


博客地址：
> https://blog.eddilee.cn

## MySQL

### 一、数据库

|id|ip|db|
|-------|-------|-------|
|1|192.168.8.246|MySQL|
|2|192.168.8.247|MySQL|

### 二、准备DDL

192.168.8.246
```sql
CREATE DATABASE sharding_order;

CREATE TABLE `t_order_1` (
  `id` int NOT NULL,
  `order_amount` decimal(10,2) NOT NULL,
  `order_status` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `t_order_2` (
  `id` int NOT NULL,
  `order_amount` decimal(10,2) NOT NULL,
  `order_status` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

192.168.8.247
```sql
CREATE DATABASE shard_order;

CREATE TABLE `t_order_1` (
  `id` int NOT NULL,
  `order_amount` decimal(10,2) NOT NULL,
  `order_status` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `t_order_2` (
  `id` int NOT NULL,
  `order_amount` decimal(10,2) NOT NULL,
  `order_status` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

## Code

> Github: https://github.com/eddie-code/sharding-jdbc-demo

### 一、创建项目
SpringBoot

### 二、Maven依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>sharding-jdbc-demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>sharding-jdbc-demo</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.3</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/usage/sharding/spring-namespace/ -->
        <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>sharding-jdbc-spring-namespace</artifactId>
            <version>4.0.0-RC2</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <!--      mybatis生成器      -->
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>8.0.17</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>

</project>
```

### 三、ShardingJdbc - Spring命名空间方式 (原始Spring项目使用)

> 官方： https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/usage/sharding/spring-namespace/

3.1、MyBatis生成代码

![图片.png](https://qiniuyun.eddilee.cn/%E5%9B%BE%E7%89%87_1603776602639.png?imageView2/0/q/75%7Cwatermark/2/text/YmxvZy5lZGRpbGVlLmNu/font/5a6L5L2T/fontsize/360/fill/IzAwMDAwMA==/dissolve/100/gravity/Center/dx/10/dy/10)

3.2、sharding-jdbc.xml 配置 Spring-namespace
```json
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:sharding="http://shardingsphere.apache.org/schema/shardingsphere/sharding"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                        http://www.springframework.org/schema/beans/spring-beans.xsd
                        http://shardingsphere.apache.org/schema/shardingsphere/sharding
                        http://shardingsphere.apache.org/schema/shardingsphere/sharding/sharding.xsd
                        ">
    <!-- MySQL数据源 -->
    <bean id="ds0" class="com.zaxxer.hikari.HikariDataSource" destroy-method="close">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver" />
        <property name="username" value="eddie" />
        <property name="password" value="Abc@123456" />
        <property name="jdbcUrl" value="jdbc:mysql://192.168.8.246/sharding_order?serverTimezone=Asia/Shanghai&amp;useSSL=false"/>
    </bean>
    <bean id="ds1" class="com.zaxxer.hikari.HikariDataSource" destroy-method="close">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver" />
        <property name="username" value="eddie" />
        <property name="password" value="Abc@123456" />
        <property name="jdbcUrl" value="jdbc:mysql://192.168.8.247/shard_order?serverTimezone=Asia/Shanghai&amp;useSSL=false"/>
    </bean>

    <!-- sharding-jdbc数据源 -->
    <sharding:data-source id="sharding-data-source">
        <!-- 多个数据源用逗号分割 -->
        <sharding:sharding-rule data-source-names="ds0,ds1">
            <sharding:table-rules>
                <!-- 逻辑表名称 logic-table="t_order" 对应Mapper.xml里面的表名 -->
                <!-- 数据节点 actual-data-nodes="ds$->{0..1}.t_order_$->{1..2}"  -->
                <!-- 数据库分片策略 database-strategy-ref="databaseStrategy" -->
                <!-- 数据表分片策略 table-strategy-ref="tableStrategy"  -->
                <sharding:table-rule logic-table="t_order"
                                     actual-data-nodes="ds$->{0..1}.t_order_$->{1..2}"
                                     database-strategy-ref="databaseStrategy"
                                     table-strategy-ref="tableStrategy"
                />
            </sharding:table-rules>
        </sharding:sharding-rule>
    </sharding:data-source>

    <!-- 数据库分片规则 -->
    <sharding:inline-strategy id="databaseStrategy"
                              sharding-column="user_id"
                              algorithm-expression="ds$->{user_id % 2}"
    />

    <!-- 数据表分片规则 -->
    <!-- id取模，可能会出现寻找到 t_order_0 的表，但实际上是没有的，所以 +1  -->
    <sharding:inline-strategy id="tableStrategy"
                              sharding-column="id"
                              algorithm-expression="t_order_$->{id % 2 + 1}"
    />

    <!-- 设置 mybatis 数据源 -->
    <bean class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="sharding-data-source"/>
        <property name="mapperLocations" value="classpath*:/mybatis/*.xml" />
    </bean>

</beans>
```

3.3、 单元测试
```json
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
```
3.4、 根据分片规则 

![图片.png](https://qiniuyun.eddilee.cn/%E5%9B%BE%E7%89%87_1603776878937.png?imageView2/0/q/75%7Cwatermark/2/text/YmxvZy5lZGRpbGVlLmNu/font/5a6L5L2T/fontsize/360/fill/IzAwMDAwMA==/dissolve/100/gravity/Center/dx/10/dy/10)

> 数据库分片规则: algorithm-expression="ds$->{user_id % 2}"  # 19%2=1 既是 ds1 <br/>
数据表分片规则: algorithm-expression="t_order_$->{id % 2 + 1}"  # 1%2+1=2 既是 t_order_2

### 四、ShardingJdbc - SpringBoot Starter 方式

> https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/usage/sharding/spring-boot-starter/

4.1、 加入依赖

```json
        <!-- https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/usage/sharding/spring-namespace/ -->
<!--        <dependency>-->
<!--            <groupId>org.apache.shardingsphere</groupId>-->
<!--            <artifactId>sharding-jdbc-spring-namespace</artifactId>-->
<!--            <version>4.0.0-RC2</version>-->
<!--        </dependency>-->

        <!-- https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/usage/sharding/spring-boot-starter/ -->
        <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
            <version>4.0.0-RC2</version>
        </dependency>
```

4.2 屏蔽启动类的注解

> //@ImportResource("classpath*:sharding-jdbc.xml")

4.3 application.properties 配置

```json
####################################################
#
#  博客：blog.eddilee.cn
#  备注：为了方便查看，使用 properties 而不是 yml 格式
#
####################################################

# 配置真实数据源
spring.shardingsphere.datasource.names=ds0,ds1

# 配置第 1 个数据源
spring.shardingsphere.datasource.ds0.type=com.zaxxer.hikari.HikariDataSource
spring.shardingsphere.datasource.ds0.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.ds0.jdbcUrl=jdbc:mysql://192.168.8.246/sharding_order
spring.shardingsphere.datasource.ds0.username=eddie
spring.shardingsphere.datasource.ds0.password=Abc@123456

# 配置第 2 个数据源
spring.shardingsphere.datasource.ds1.type=com.zaxxer.hikari.HikariDataSource
spring.shardingsphere.datasource.ds1.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.ds1.jdbcUrl=jdbc:mysql://192.168.8.247/shard_order
spring.shardingsphere.datasource.ds1.username=eddie
spring.shardingsphere.datasource.ds1.password=Abc@123456

# 配置 t_order 表规则
spring.shardingsphere.sharding.tables.t_order.actual-data-nodes=ds$->{0..1}.t_order$->{1..2}

# 配置 t_order 分库策略
spring.shardingsphere.sharding.tables.t_order.database-strategy.inline.sharding-column=user_id
spring.shardingsphere.sharding.tables.t_order.database-strategy.inline.algorithm-expression=ds$->{user_id % 2}

# 配置 t_order 分表策略
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.sharding-column=id
spring.shardingsphere.sharding.tables.t_order.table-strategy..inline..algorithm-expression=t_order_$->{id % 2 + 1}

# 配置 mybatis
mybatis.mapper-locations=/mybatis/*.xml
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl

# 设置日志日期格式
logging.pattern.dateformat=yyyy-MM-dd HH:mm:ss.SSS
```

4.4 单元测试

```json
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
```
