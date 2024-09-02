package com.whai.blog.controller.pub;


import com.whai.blog.model.BlogComment;
import com.whai.blog.service.IBlogCommentService;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.HttpStatus;
import com.whai.blog.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j //自动生成log private
public class PubCommentController {

    @Autowired
    IBlogCommentService commentService;

    @ApiOperation("喜欢某个评论")
    @PostMapping("/likeComment")
    public AjaxResult likeComment(@ApiParam("comment的id") @RequestParam("commentId") String commentId) {
        return AjaxResult.success("", commentService.likeComment(commentId));
    }

    @ApiOperation("新增评论")
    @PostMapping("/addComment")
    public AjaxResult addComment(@ApiParam("comment内容") BlogComment comment) {
        if (StringUtils.isNull(comment)){
            return new AjaxResult(HttpStatus.BAD_REQUEST, "请输入评论内容信息");
        }

        boolean save = commentService.saveComment(comment);

        AjaxResult result;
        if (save){
            result = AjaxResult.success( "评论成功");
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "评论失败");
            log.error(result.toString());
        }
        return result;

    }
}
