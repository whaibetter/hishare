package com.whai.blog.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author whai
 * @since 2022-11-08
 */
@TableName("home")
@ApiModel(value = "Home对象", description = "")
public class Home implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主页版次")
    @TableId
    private Integer homeId;

    @ApiModelProperty("文件标题")
    private String homeTitle;

    @ApiModelProperty("页面，按需导入")
    private String homeHtml;

    @ApiModelProperty("状态")
    private Integer homeStatus;


    @Override
    public String toString() {
        return "Home{" +
                "homeId=" + homeId +
                ", homeTitle='" + homeTitle + '\'' +
                ", homeHtml='" + homeHtml + '\'' +
                ", homeStatus=" + homeStatus +
                '}';
    }

    public Integer getHomeStatus() {
        return homeStatus;
    }

    public void setHomeStatus(Integer homeStatus) {
        this.homeStatus = homeStatus;
    }

    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }
    public String getHomeTitle() {
        return homeTitle;
    }

    public void setHomeTitle(String homeTitle) {
        this.homeTitle = homeTitle;
    }
    public String getHomeHtml() {
        return homeHtml;
    }

    public void setHomeHtml(String homeHtml) {
        this.homeHtml = homeHtml;
    }

}
