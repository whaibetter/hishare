package com.whai.blog.controller.pub;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whai.blog.component.redis.RedisCache;
import com.whai.blog.constant.CacheConstants;
import com.whai.blog.model.Message;
import com.whai.blog.service.IMessageService;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.HttpStatus;
import com.whai.blog.utils.ServletUtils;
import com.whai.blog.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class PubMessageController {

    private final Logger logger = Logger.getLogger(PubMessageController.class);

    @Autowired
    IMessageService messageService;

    @Autowired
    RedisCache redisCache;


    @PostMapping("/addMessage")
    @ApiOperation("新增留言")
    public AjaxResult addMessage(@ApiParam("留言") @RequestParam("messageContent") String message){
        if (StringUtils.isNull(message)){
            return new AjaxResult(HttpStatus.BAD_REQUEST, "请输入message内容");
        }

        Message saveMessage = new Message();
        saveMessage.setMessageIp(ServletUtils.getIpAddress());
        saveMessage.setMessageContent(message);

        boolean save = messageService.saveMessage(saveMessage);
        AjaxResult result;
        if (save){
            result = AjaxResult.success( "留言成功");
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "留言失败");
            logger.error(result.toString());
        }
        return result;

    }

    /**
     * 留言列表
     * @param page 第几页
     * @return
     */
    @GetMapping("/messageList/{page}")
    @ApiOperation("留言列表")
    public AjaxResult getMessageList(@ApiParam("页码") @PathVariable Integer page) {


        JSONObject cacheObject = redisCache.getCacheObject(CacheConstants.MESSAGE_CACHE_KEY + page);
        if (cacheObject!=null){
            return new AjaxResult(HttpStatus.SUCCESS, "success", cacheObject);
        }


        messageService.synchronizedMessageCache(page);

        Page<Message> messageIPage = new Page<Message>(page, 15);
        QueryWrapper<Message> select = new QueryWrapper<Message>();
        Page<Message> message = messageService.page(messageIPage, select);
        return new AjaxResult(HttpStatus.SUCCESS, "success", message);
    }

    @ApiOperation("喜欢某个留言")
    @PatchMapping("likeMessage")
    public AjaxResult likeBlog(@ApiParam("留言的id") @RequestParam String messageId) {
        return new AjaxResult(HttpStatus.SUCCESS, "", messageService.likeMessage(messageId));
    }
}
