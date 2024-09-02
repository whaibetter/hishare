package com.whai.blog.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whai.blog.model.Message;
import com.whai.blog.service.impl.MessageServiceImpl;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.HttpStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  管理员进行留言板的管理
 */
@RestController
@RequestMapping("/admin/message")
public class MessageController {


    private static Logger logger = Logger.getLogger(MessageController.class);

    @Autowired
    MessageServiceImpl messageService;


    /**
     * 留言列表
     * @param page 第几页
     * @return
     */
    @GetMapping("/messageList/{page}")
    @ApiOperation("留言列表")
    public AjaxResult getMessageList(@ApiParam("页码") @PathVariable Integer page) {

        //分页 每页显示8条数据
        Page<Message> messageIPage = new Page<Message>(page, 15);
        //条件筛选
        QueryWrapper<Message> select = new QueryWrapper<Message>();
        Page<Message> message = messageService.page(messageIPage, select);

        return new AjaxResult(HttpStatus.SUCCESS, "success", message);
    }




    /**
     * 逻辑删除
     * @param messageId
     * @return
     */
    @ApiOperation("删除或者恢复message标签")
    @DeleteMapping("/deleteOrRecoverMessage")
    public AjaxResult deleteMessage(@RequestParam("messageId") String messageId) {

        Message select = messageService.getById(messageId);
        boolean delete = false;
        if (select.getMessageDelete() == 0) {
            select.setMessageDelete(1);
        } else {
            select.setMessageDelete(0);
        }
        delete = messageService.updateById(select);
        //逻辑删除


        AjaxResult result;
        if (delete){
            result = new AjaxResult(HttpStatus.SUCCESS, "删除message成功");
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "删除message失败");
            logger.error(result.toString());
        }
        return result;
    }







}
