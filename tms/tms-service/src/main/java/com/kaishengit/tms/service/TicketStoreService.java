package com.kaishengit.tms.service;

import com.kaishengit.tms.entity.*;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TicketStoreService {

    /**查找所有的代理年票客户
     *
     * @Author Reich
     * @Date: 2018/4/19 20:24
     */
    List<TicketStroe> findAllTicketStrop();


    /**根据代理人id删除代理人
     *
     * @Author Reich
     * @Date: 2018/4/19 21:16
     */
    void delTicketStroe(Integer id);


    /**根据id查找TicletStroe
     *
     * @Author Reich
     * @Date: 2018/4/19 22:22
     */
    TicketStroe findTicketStroeById(Integer id);

    /**更新ticketStroe
     *
     * @Author Reich
     * @Date: 2018/4/19 22:47
     */
    void updateTicketStroe(Integer id, TicketStroe ticketStroe);

    /**搜索  分页
     *
     * @Author Reich
     * @Date: 2018/4/20 10:22
     */
    PageInfo<TicketStroe> findAllTicketStropByPageNo(Integer pageNo, Map<String, Object> queryParam);

    /**通过id查找stroeAccount对象
     *
     * @Author Reich
     * @Date: 2018/4/20 12:21
     */
    StroeAccount findStroeAccountById(Integer id);

    /**根据id禁用账号
     *
     * @Author Reich
     * @Date: 2018/4/20 14:28
     */
    void prohibitedTicketStroeById(Integer id);

    /**新增代理营业执照和身份信息
     *
     * @Author Reich
     * @Date: 2018/4/21 16:16
     */
    void saveNewTicketStorp(TicketStroe ticketStroe);

    /**通过手机号查找代理人
     *
     * @Author Reich
     * @Date: 2018/4/25 20:49
     */
    StroeAccount findAccountByMobile(String userMobile);

    /**保存代理登录星系
     *
     * @Author Reich
     * @Date: 2018/4/25 21:08
     */
    void saveAccountLoginLog(StroeLoginLog stroeLoginLog);

    /**通过id查找当前登录代理的销售信息
     *
     * @Author Reich
     * @Date: 2018/4/27 13:07
     */
    Map<String,Long> countTicketByStateStroeAccountId(Integer id);

    /**保存客户购票信息
     *
     * @Author Reich
     * @Date: 2018/4/27 21:29
     */
    void saveTicketCustomer(Customer customer, TicketStroe ticketStroe, String ticketNum, BigDecimal ticketPrice);
}
