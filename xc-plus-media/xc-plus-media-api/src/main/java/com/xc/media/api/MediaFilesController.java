package com.xc.media.api;

import com.xc.base.exception.XueChengPlusException;
import com.xc.base.model.PageParams;
import com.xc.base.model.PageResult;
import com.xc.media.model.dto.QueryMediaParamsDto;
import com.xc.media.model.dto.UploadFileParamsDto;
import com.xc.media.model.dto.UploadFileResultDto;
import com.xc.media.model.po.MediaFiles;
import com.xc.media.service.MediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description 媒资文件管理接口
 * @author Mr.M
 * @date 2022/9/6 11:29
 * @version 1.0
 */
 @Api(value = "媒资文件管理接口",tags = "媒资文件管理接口")
 @RestController
public class MediaFilesController {


    @Autowired
    MediaFileService mediaFileService;


    @ApiOperation("媒资列表查询接口")
    @PostMapping("/files")
    public PageResult<MediaFiles> list(PageParams pageParams, @RequestBody QueryMediaParamsDto queryMediaParamsDto){
        Long companyId = 1232141425L;
        return mediaFileService.queryMediaFiels(companyId,pageParams,queryMediaParamsDto);

    }


    /**
     * 上传图片接口（文件）
     * @param filedata 文件数据
     * @param folder 桶下边的子目录
     * @param objectName 对象名称
     * @return
     */
    @RequestMapping(value = "/upload/coursefile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UploadFileResultDto upload(@RequestPart("filedata") MultipartFile filedata,
                                      @RequestParam(value = "folder",required=false) String folder,
                                      @RequestParam(value= "objectName",required=false) String objectName) {

        Long companyId = 1232141425L;
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        String contentType = filedata.getContentType();
        uploadFileParamsDto.setContentType(contentType);
        //文件大小
        uploadFileParamsDto.setFileSize(filedata.getSize());
//        assert contentType != null;
        if (contentType.contains("image")) {
            //是个图片
            uploadFileParamsDto.setFileType("001001");
        } else {
            uploadFileParamsDto.setFileType("001003");
        }
        //文件名称
        uploadFileParamsDto.setFilename(filedata.getOriginalFilename());
        UploadFileResultDto uploadFileResultDto = null;
        try {
            uploadFileResultDto = mediaFileService.uploadFile(companyId, uploadFileParamsDto, filedata.getBytes(), folder, objectName);
        } catch (Exception e) {
            XueChengPlusException.cast("上传文件过程中出错");
        }

        return uploadFileResultDto;

    }



}
