package com.whai.blog.controller.pub;

import com.google.code.kaptcha.Producer;
import com.whai.blog.component.redis.RedisCache;
import com.whai.blog.constant.CacheConstants;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.Base64;
import com.whai.blog.utils.IdUtils;
import com.whai.blog.utils.StringUtils;
import com.whai.blog.utils.exception.CaptchaException;
import com.whai.blog.utils.exception.CaptchaExpireException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class CaptchaController {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;


    @GetMapping("/captchaImage")
    public AjaxResult getCode(){
        AjaxResult result = AjaxResult.success("获取验证码成功");

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码

        String capText = captchaProducerMath.createText();
        capStr = capText.substring(0, capText.lastIndexOf("@"));
        code = capText.substring(capText.lastIndexOf("@") + 1);
        image = captchaProducerMath.createImage(capStr);


        //两分钟过期
        String uuid = IdUtils.simpleUUID();
        redisCache.setCacheObject(CacheConstants.CAPTCHA_CODE_KEY + uuid, code, 2, TimeUnit.MINUTES);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }
        result.put("uuid", uuid);
        result.put("img", Base64.encode(os.toByteArray()));

        return result;
    }

    /**
     * 验证验证码是否正确
     *
     * @return
     */
    @PostMapping("/verifyCode")
    public AjaxResult verifyCode(String verifyCode, String uuid) throws CaptchaExpireException {
        AjaxResult result = AjaxResult.success("验证成功");
        try {
            validateCaptcha(verifyCode, uuid);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("验证码失败:" + e.getMessage());
        }


        return result;
    }

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String code, String uuid) throws CaptchaExpireException {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");

        //获取缓存
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException("错误验证码");
        }
    }





}
