package com.kaishengit.tms.service;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.AccountLoginLog;

import java.util.List;
import java.util.Map;

/**
 * 系统账号的业务类
 * @Author Reich
 * @Date: 2018/4/12 19:27
 */
public interface AccountService {

    /**
     * @param accountMobile 登陆员工的账号
     * @param password  登陆员工密码
     * @param accountIp 登录员工的ip
     * @Author Reich
     * @Date: 2018/4/12 20:59
     */
    Account login(String accountMobile, String password, String accountIp);

    /**
     * 新建账户
     * @Author Reich
     * @Date: 2018/4/16 19:34
     */
    void saveAccount(Account account, Integer[] rolesIds);

    /**搜索框
     *根据UI传来的查询参数查询所有账号并加载对应的角色列表
     * @Author Reich
     * @Date: 2018/4/16 21:09
     */
    List<Account> findAllAccountWithRolesByQueryParam(Map<String, Object> objectMap);

    /**
     * 根据id查找账户
     * @Author Reich
     * @Date: 2018/4/16 22:21
     */
    Account findByAccountId(Integer id);

    /**更新账户
     *
     * @Author Reich
     * @Date: 2018/4/16 22:55
     */
    void updateAccount(Account account, Integer[] rolesIds);

    /**
     * 根据账户id删除账户及角色引用关系
     * @Author Reich
     * @Date: 2018/4/17 10:41
     */
    void delAccountById(Integer id);

    /**通过手机号查找用户
     *
     * @Author Reich
     * @Date: 2018/4/17 19:10
     */
    Account findAccountByMobile(String userMobile);

    /** 保存登录日志
     *
     * @Author Reich
     * @Date: 2018/4/17 19:15
     */
    void saveAccountLoginLog(AccountLoginLog accountLoginLog);
}
