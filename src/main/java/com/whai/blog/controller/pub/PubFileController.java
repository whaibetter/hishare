package com.whai.blog.controller.pub;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whai.blog.model.FileMain;
import com.whai.blog.service.IFileDownloadService;
import com.whai.blog.service.IFileMainService;
import com.whai.blog.utils.AjaxResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
@RequestMapping("/file")
public class PubFileController {

    @Autowired
    IFileMainService fileService;

    @Autowired
    IFileDownloadService fileDownloadService;


    @ApiOperation("下载文件")
    @GetMapping("download")
    public void download(@ApiParam("文件的id") @RequestParam("fileId")String fileId,HttpServletResponse response) throws IOException {

        FileMain fileMain = fileService.getById(fileId);

        //2. 根据路径删除文件
        String property = System.getProperty("user.dir");
        property += fileMain.getFileLocation().substring(4);

        File file = new File(property);

        // 获取文件名
        String filename = file.getName();
        // 获取文件后缀名
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

        FileInputStream fileInputStream = new FileInputStream(file);

        InputStream fis = new BufferedInputStream(fileInputStream);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();



// 清空response
        response.reset();
        // 设置response的Header
        response.setCharacterEncoding("UTF-8");
        //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
        //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
        // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        // 告知浏览器文件的大小
        response.addHeader("Content-Length", "" + file.length());
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        outputStream.write(buffer);
        outputStream.flush();



    }



    /**
     * 博客列表
     *
     * @param page 第几页
     * @return
     */
    @GetMapping("fileList/{page}")
    @ResponseBody
    @ApiOperation("文件列表")
//    @Log(value = "获取file",BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult getFileList(@ApiParam(value = "页码", defaultValue = "1") @PathVariable Integer page) {

        //分页 每页显示8条数据
        Page<FileMain> fileIPage = new Page<FileMain>(page, 15);
        //条件筛选
        QueryWrapper<FileMain> select = new QueryWrapper<FileMain>();
        Page<FileMain> file = fileService.page(fileIPage, select);



        return AjaxResult.success("success", file);
    }



}
