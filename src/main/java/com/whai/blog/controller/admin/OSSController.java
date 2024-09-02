package com.whai.blog.controller.admin;

import com.whai.blog.service.IMinioService;
import com.whai.blog.utils.AjaxResult;
import io.minio.errors.*;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
@RequestMapping("/oss")
public class OSSController {
    @Resource
    private IMinioService iMinioService;

    @PostMapping("upload")
    public AjaxResult upload(
            @ApiParam(value = "file文件上传",required = true)
            @RequestPart(name = "file",required = true)
            MultipartFile file
    ) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        iMinioService.uploadFile(file, null);
        return AjaxResult.success();
    }
}
