package com.example.shardingjdbcdemo.model;

public class OrderItem {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_item_1.id
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_item_1.order_id
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    private Integer orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_item_1.pruduct_name
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    private String pruductName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_item_1.num
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    private Integer num;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_item_1.user_id
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    private Integer userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_item_1.id
     *
     * @return the value of t_order_item_1.id
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_item_1.id
     *
     * @param id the value for t_order_item_1.id
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_item_1.order_id
     *
     * @return the value of t_order_item_1.order_id
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_item_1.order_id
     *
     * @param orderId the value for t_order_item_1.order_id
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_item_1.pruduct_name
     *
     * @return the value of t_order_item_1.pruduct_name
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    public String getPruductName() {
        return pruductName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_item_1.pruduct_name
     *
     * @param pruductName the value for t_order_item_1.pruduct_name
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    public void setPruductName(String pruductName) {
        this.pruductName = pruductName == null ? null : pruductName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_item_1.num
     *
     * @return the value of t_order_item_1.num
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    public Integer getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_item_1.num
     *
     * @param num the value for t_order_item_1.num
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_order_item_1.user_id
     *
     * @return the value of t_order_item_1.user_id
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_order_item_1.user_id
     *
     * @param userId the value for t_order_item_1.user_id
     *
     * @mbg.generated Wed Oct 28 14:22:20 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}