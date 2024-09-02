package com.whai.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author whai
 * @since 2022-10-29
 */
@ApiModel(value = "Message对象", description = "")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;



    @TableId(value = "message_id",type = IdType.ASSIGN_UUID)
    @ApiModelProperty("留言id")
    private String messageId;

    @ApiModelProperty("ip地址")
    private String messageIp;

    @ApiModelProperty("留言时间")
    private Date messageTime;

    @ApiModelProperty("cookie")
    private String messageCookie;

    @ApiModelProperty("标记喜欢")
    private Integer messageLike;

    @ApiModelProperty("父id")
    private String messageFatherId;

    @ApiModelProperty("逻辑删除。0表示正常，1表示删除")
//    @TableLogic
    private Integer messageDelete;

    @ApiModelProperty(value = "留言内容",required = true)
    private String messageContent;


    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }



    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    public String getMessageIp() {
        return messageIp;
    }

    public void setMessageIp(String messageIp) {
        this.messageIp = messageIp;
    }
    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }
    public String getMessageCookie() {
        return messageCookie;
    }

    public void setMessageCookie(String messageCookie) {
        this.messageCookie = messageCookie;
    }
    public Integer getMessageLike() {
        return messageLike;
    }

    public void setMessageLike(Integer messageLike) {
        this.messageLike = messageLike;
    }
    public String getMessageFatherId() {
        return messageFatherId;
    }

    public void setMessageFatherId(String messageFatherId) {
        this.messageFatherId = messageFatherId;
    }
    public Integer getMessageDelete() {
        return messageDelete;
    }

    public void setMessageDelete(Integer messageDelete) {
        this.messageDelete = messageDelete;
    }


    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", messageIp='" + messageIp + '\'' +
                ", messageTime=" + messageTime +
                ", messageCookie='" + messageCookie + '\'' +
                ", messageLike=" + messageLike +
                ", messageFatherId='" + messageFatherId + '\'' +
                ", messageDelete=" + messageDelete +
                ", messageContent='" + messageContent + '\'' +
                '}';
    }
}
