package com.xc.media.service;

import com.xc.base.model.PageParams;
import com.xc.base.model.PageResult;
import com.xc.base.model.RestResponse;
import com.xc.media.model.dto.QueryMediaParamsDto;
import com.xc.media.model.dto.UploadFileParamsDto;
import com.xc.media.model.dto.UploadFileResultDto;
import com.xc.media.model.po.MediaFiles;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description 媒资文件管理业务类
 * @author Mr.M
 * @date 2022/9/10 8:55
 * @version 1.0
 */
public interface MediaFileService {

   /**
    * @description 媒资文件查询方法
    * @param pageParams 分页参数
    * @param queryMediaParamsDto 查询条件
    * @return com.xuecheng.base.model.PageResult<com.xuecheng.media.model.po.MediaFiles>
    * @author Mr.M
    * @date 2022/9/10 8:57
   */
   PageResult<MediaFiles> queryMediaFiels(Long companyId,PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);

   /**
    * @description 上传文件的通用接口
    * @param companyId  机构id
    * @param uploadFileParamsDto  文件信息
    * @param bytes  文件字节数组
    * @param folder 桶下边的子目录
    * @param objectName 对象名称
    * @return com.xuecheng.media.model.dto.UploadFileResultDto
    * @author Mr.M
    * @date 2022/10/13 15:51
    */
   UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, byte[] bytes, String folder, String objectName);


    /**
     * 将文件信息添加到文件表
     * @param companyId  机构id
     * @param fileMd5  文件md5值
     * @param uploadFileParamsDto  上传文件的信息
     * @param bucket  桶
     * @param objectName 对象名称
     * @return
     *
     */
    MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName);

    /**
     * @description 检查文件是否存在
     * @param fileMd5 文件的md5
     * @return com.xuecheng.base.model.RestResponse<java.lang.Boolean> false不存在，true存在
     * @author Mr.M
     * @date 2022/9/13 15:38
     */
    RestResponse<Boolean> checkFile(String fileMd5);

    /**
     * @description 检查分块是否存在
     * @param fileMd5  文件的md5
     * @param chunkIndex  分块序号
     * @return com.xuecheng.base.model.RestResponse<java.lang.Boolean> false不存在，true存在
     * @author Mr.M
     * @date 2022/9/13 15:39
     */
    RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

    /**
     * @description 上传分块
     * @param fileMd5  文件md5
     * @param chunk  分块序号
     * @param bytes  文件字节
     * @return com.xuecheng.base.model.RestResponse
     * @author Mr.M
     * @date 2022/9/13 15:50
     */
    RestResponse uploadChunk(String fileMd5,int chunk,byte[] bytes);


    /**
     * @description 合并分块
     * @param companyId  机构id
     * @param fileMd5  文件md5
     * @param chunkTotal 分块总和
     * @param uploadFileParamsDto 文件信息
     * @return com.xuecheng.base.model.RestResponse
     * @author Mr.M
     * @date 2022/9/13 15:56
     */
    RestResponse mergechunks(Long companyId,String fileMd5,int chunkTotal,UploadFileParamsDto uploadFileParamsDto);
}
