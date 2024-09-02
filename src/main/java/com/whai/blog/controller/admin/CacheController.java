package com.whai.blog.controller.admin;

import com.whai.blog.constant.CacheConstants;
import com.whai.blog.model.system.SysCache;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin/cache")
public class CacheController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 缓存列表
     */
    private final static List<SysCache> caches = new ArrayList<SysCache>();
    {
        caches.add(new SysCache(CacheConstants.LOGIN_TOKEN_KEY, "用户信息"));
        caches.add(new SysCache(CacheConstants.SYS_CONFIG_KEY, "配置信息"));
        caches.add(new SysCache(CacheConstants.SYS_DICT_KEY, "数据字典"));
        caches.add(new SysCache(CacheConstants.CAPTCHA_CODE_KEY, "验证码"));
        caches.add(new SysCache(CacheConstants.REPEAT_SUBMIT_KEY, "防重提交"));
        caches.add(new SysCache(CacheConstants.RATE_LIMIT_KEY, "限流处理"));
        caches.add(new SysCache(CacheConstants.PWD_ERR_CNT_KEY, "密码错误次数"));
        caches.add(new SysCache(CacheConstants.BLOGS_LIST, "blogs缓存"));
        caches.add(new SysCache(CacheConstants.LOGIN_USER_KEY, "登录用户缓存"));
    }

    @GetMapping("getInfo")
    @ApiOperation("获取redis Cache缓存信息")
    public AjaxResult getInfo() throws Exception {
        Properties info = (Properties) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.info();
            }
        });

        /**
         * commandstats部分提供基于命令类型的统计，包含调用次数，这些命令消耗的总CPU时间，以及每个命令执行所消耗的平均CPU。
         * 对于每一个命令类型，添加以下行：
         * cmdstat_XXX: calls=XXX,usec=XXX,usec_per_call=XXX
         */
        Properties commandStats = (Properties) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.info("commandstats");
            }
        });


        Object dbSize = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        // 下面这一串 是获取到各个类型的命令的数量

        Set<String> strings = commandStats.stringPropertyNames();
        for (String key : strings) {
            Map<String, String> data = new HashMap<>(2);
            // 获取key对应的values
            String property = commandStats.getProperty(key);

            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            //StringUtils.substringBetween 获取在两个参数中间的那一部分。
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        }
//        strings.forEach(key -> {
//            Map<String, String> data = new HashMap<>(2);
//            // 获取key对应的values
//            String property = commandStats.getProperty(key);
//
//            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
//            //StringUtils.substringBetween 获取在两个参数中间的那一部分。
//            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
//            pieList.add(data);
//        });
        result.put("commandStats", pieList);
        return AjaxResult.success(result);
    }

    /**
     *
     * @param cacheName login_tokens:
     * @param cacheKey login_tokens:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ....
     * @return
     */
    @ApiOperation("获取redis Cache缓存")
    @GetMapping("/getValue")
    public AjaxResult getCacheValue(@RequestParam String cacheName, @RequestParam String cacheKey)
    {
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        SysCache sysCache = new SysCache(cacheName, cacheKey, cacheValue);
        return AjaxResult.success(sysCache);
    }

    @GetMapping("/getNames")
    @ApiOperation("获取redis Cache缓存列表")
    public AjaxResult cache()
    {
        return AjaxResult.success(caches);
    }

    @GetMapping("/getKeys/{cacheName}")
    @ApiOperation("获取redis Cache列表")
    public AjaxResult getCacheKeys(@PathVariable String cacheName)
    {
        Set<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        return AjaxResult.success(cacheKeys);
    }


    @DeleteMapping("/clearCacheName/{cacheName}")
    @ApiOperation("根据缓存Name清除缓存")
    public AjaxResult clearCacheName(@PathVariable String cacheName)
    {
        Collection<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        redisTemplate.delete(cacheKeys);
        return AjaxResult.success();
    }

    @DeleteMapping("/clearCacheKey/{cacheKey}")
    @ApiOperation("根据缓存Key清除缓存")
    public AjaxResult clearCacheKey(@PathVariable String cacheKey)
    {
        Boolean delete = redisTemplate.delete(cacheKey);
        return delete?AjaxResult.success():AjaxResult.error();
    }

    @DeleteMapping("/clearCacheAll")
    @ApiOperation("清除所有缓存")
    public AjaxResult clearCacheAll()
    {
        Collection<String> cacheKeys = redisTemplate.keys("*");
        redisTemplate.delete(cacheKeys);
        return AjaxResult.success();
    }


}
