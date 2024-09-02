package com.whai.blog.service.impl;

import com.whai.blog.component.redis.RedisCache;
import com.whai.blog.constant.CacheConstants;
import com.whai.blog.service.ISignInService;
import com.whai.blog.utils.SecurityCustomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class SignInServiceImpl implements ISignInService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public void signIn() {
        int dayOfMonth = LocalDateTime.now().getDayOfMonth();
        signIn(dayOfMonth);
    }

    @Override
    public void signIn(Integer day) {
        String userName = SecurityCustomUtils.getUserName();
        // 签到
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        log.info("Date: {}  User: {} 签到成功", now, userName);
        // Key 格式  user_sign:root:202310
        String key = CacheConstants.USER_SIGN_KEY + userName + keySuffix;
        // 获取当前是这个月第几天

        redisCache.setCacheBitMap(key, day, true);
    }


    @Override
    public Map<String, Set<Integer>> userSignStatus() {

        Collection<String> keys = redisCache.keys(CacheConstants.USER_SIGN_KEY + "*");

        HashMap<String, Set<Integer>> userSignInStatusMap = new HashMap<>();

        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            // 获取user_sign:root:202310 中的root字段
            String userName = key.substring(CacheConstants.USER_SIGN_KEY.length() , key.indexOf(":", CacheConstants.USER_SIGN_KEY.length() + 1));
            // 获取某个userName的redis签到记录
            Set<Integer> signInDayByKey = this.getSignInDayByKey(key);
            userSignInStatusMap.put(userName, signInDayByKey);
        }
        return userSignInStatusMap;
    }

    @Override
    public Set<Integer> mySignStatus() {
        String key = CacheConstants.USER_SIGN_KEY + SecurityCustomUtils.getUserName() + LocalDateTime.now().format(DateTimeFormatter.ofPattern(":yyyyMM"));
        // 获取某个我的redis签到记录
        return this.getSignInDayByKey(key);
    }

    /**
     * 获取签到的天数
     * @param key user_sign:root:202310
     * @return
     */
    public Set<Integer> getSignInDayByKey(String key) {
        LocalDateTime now = LocalDateTime.now();
        int dayOfMonth = now.getDayOfMonth();

        List<Long> list = redisCache.bitField(
                key,
                BitFieldSubCommands.create().get(
                        BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth + 1)).valueAt(0)
        );

        Long aLong = list.get(0);
        /**
         * 如今天是dayofmonth=10
         * 已经签到了 9 10 两天，使用  BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth + 1)).valueAt(0)
         *      等价于 bitfield key get u11 0  输出为 3(二进制11)
         *          9 10
         *          1 1
         */
        // 将Long转为2进制char列表 1010 8
        Set<Integer> signInDaySet = new HashSet<>();
        char[] chars = Long.toBinaryString(aLong).toCharArray();

        for (int i = chars.length - 1; i >= 0; i--) {
            //如果char[i}内的值==1，标识那天为签到的
            if (Character.getNumericValue(chars[i]) == 1) {
                signInDaySet.add(dayOfMonth - chars.length + i + 1);
            }
        }
        log.info("用户:{} 已经签到了的日期:{}", key, signInDaySet);
        return signInDaySet;
    }

}
