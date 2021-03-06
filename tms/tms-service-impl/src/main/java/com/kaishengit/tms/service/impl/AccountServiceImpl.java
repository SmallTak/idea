package com.kaishengit.tms.service.impl;

import com.kaishengit.tms.entity.*;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.mapper.AccountLoginLogMapper;
import com.kaishengit.tms.mapper.AccountMapper;
import com.kaishengit.tms.mapper.AccountRolesMapper;
import com.kaishengit.tms.service.AccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 系统账号的业务类
 * @Author Reich
 * @Date: 2018/4/12 19:27
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountLoginLogMapper accountLoginLogMapper;

    @Autowired
    private AccountRolesMapper accountRolesMapper;


    /**
     * @param accountMobile 登陆员工的账号
     * @param password      登陆员工密码
     * @param accountIp     登录员工的ip
     * @return 若登录成则返回account对象  失败返回null
     * @Author Reich
     * @Date: 2018/4/12 20:59
     */
    @Override
    public Account login(String accountMobile, String password, String accountIp) {
        //根据手机号查找对应的用户
        AccountExample accountExample = new AccountExample();
        //设置查询条件
        accountExample.createCriteria().andAccountMobileEqualTo(accountMobile);

        List<Account> accountList = accountMapper.selectByExample(accountExample);

        Account account = null;
        if (accountList != null && !accountList.isEmpty()){

            account = accountList.get(0);
            //判断用户密码是否正确
            if (account.getAccountPassword().equals(DigestUtils.md5Hex(password))){
                //判断用户状态
                if (Account.STATE_NORMAL.equals(account.getAccountState())) {
                    //添加用户登录日志
                    AccountLoginLog accountLoginLog = new AccountLoginLog();
                    accountLoginLog.setAccountId(account.getId());
                    accountLoginLog.setLoginIp(accountIp);
                    accountLoginLog.setLoginTime(new Date());
                    accountLoginLogMapper.insert(accountLoginLog);

                    logger.info("{}：登陆系统",account);
                    return account;

                }else if (Account.STATE_LOCKED.equals(account.getAccountState())){
                    throw new ServiceException("用户被锁定");
                }else {
                    throw new ServiceException("用户被禁用");
                }
            } else {
                throw new ServiceException("账号或者密码不匹配");
            }

        }else {
            throw new ServiceException("账号或者密码错误");
        }
    }

    /**
     * 新建账户
     *
     * @param account  账号对象
     * @param rolesIds  账号拥有的角色id
     * @Author Reich
     * @Date: 2018/4/16 19:34
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveAccount(Account account, Integer[] rolesIds) {
        //设置账号创建的时间
        account.setCreateTime(new Date());
        String password;
        if (account.getAccountMobile().length() <= 6){
            password = account.getAccountMobile();
        }else {
            password = account.getAccountMobile().substring(6);
        }

        //对用户密码进行md5加密
        password = DigestUtils.md5Hex(password);
        account.setAccountPassword(password);
        //设置账号默认状态
        account.setAccountState(Account.STATE_NORMAL);
        accountMapper.insertSelective(account);
        System.out.println(account);
        //添加用户和角色关系
        if (rolesIds != null){
            for (Integer rolesId : rolesIds){
                AccountRolesKey accountRolesKey = new AccountRolesKey();
                accountRolesKey.setAccountId(account.getId());
                accountRolesKey.setRolesId(rolesId);
                accountRolesMapper.insertSelective(accountRolesKey);
            }
        }
    }

    /**
     * 搜索框
     * 根据UI传来的查询参数查询所有账号并加载对应的角色列表
     * @param objectMap
     * @Author Reich
     * @Date: 2018/4/16 21:09
     */
    @Override
    public List<Account> findAllAccountWithRolesByQueryParam(Map<String, Object> objectMap) {

        return accountMapper.findAllAccountWithRolesByQueryParam(objectMap);
    }

    /**
     * 根据id查找账户
     *
     * @param id
     * @Author Reich
     * @Date: 2018/4/16 22:21
     */
    @Override
    public Account findByAccountId(Integer id) {

        Account account = accountMapper.selectByPrimaryKey(id);
        return account;
    }

    /**
     * 更新账户
     *
     * @param account
     * @param rolesIds
     * @Author Reich
     * @Date: 2018/4/16 22:55
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateAccount(Account account, Integer[] rolesIds) {
        //添加账号的修改时间
        account.setUpdateTime(new Date());
        accountMapper.updateByPrimaryKeySelective(account);

        //删除原有的账号-角色关系
        AccountRolesExample accountRolesExample = new AccountRolesExample();
        accountRolesExample.createCriteria().andAccountIdEqualTo(account.getId());
        accountRolesMapper.deleteByExample(accountRolesExample);

        //新增账号-角色关系
        if(rolesIds != null) {
            for (Integer rolesId : rolesIds) {
                AccountRolesKey accountRolesKey = new AccountRolesKey();
                accountRolesKey.setRolesId(rolesId);
                accountRolesKey.setAccountId(account.getId());
                accountRolesMapper.insertSelective(accountRolesKey);
            }
        }

        logger.info("修改账号 {}",account);
    }

    /**
     * 根据账户id删除账户及角色引用关系
     *
     * @param id
     * @Author Reich
     * @Date: 2018/4/17 10:41
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delAccountById(Integer id) {
        //删除用户和角色的关联关系
        AccountRolesExample accountRolesExample = new AccountRolesExample();
        accountRolesExample.createCriteria().andAccountIdEqualTo(id);
        accountRolesMapper.deleteByExample(accountRolesExample);

        //删除用户和登录日志关联表
        AccountLoginLogExample accountLoginLogExample = new AccountLoginLogExample();
        accountLoginLogExample.createCriteria().andAccountIdEqualTo(id);
        accountLoginLogMapper.deleteByExample(accountLoginLogExample);

        Account account = accountMapper.selectByPrimaryKey(id);
        //删除用户
        accountMapper.deleteByPrimaryKey(id);
        logger.info("删除用户{}",account);

    }

    /**
     * 通过手机号查找用户
     *
     * @Author Reich
     * @Date: 2018/4/17 19:10
     */
    @Override
    public Account findAccountByMobile(String userMobile) {

        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andAccountMobileEqualTo(userMobile);

        List<Account> accountList = accountMapper.selectByExample(accountExample);
        if (accountList != null){
            return accountList.get(0);
        }
        return null;
    }

    /**
     * 保存登录日志
     *
     * @param accountLoginLog
     * @Author Reich
     * @Date: 2018/4/17 19:15
     */
    @Override
    public void saveAccountLoginLog(AccountLoginLog accountLoginLog) {

        accountLoginLogMapper.insert(accountLoginLog);
    }


}
