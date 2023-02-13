package com.xc.media.service;

import com.xc.media.model.po.MediaProcess;

import java.util.List;

/**
 * @author LJ
 * @create 2023/2/13
 */
public interface MediaFileProcessService {


    /**
     *
     * 媒资管理 进程
     *
     * @param shardIndex 分片序号
     * @param shardTotal 分片总数
     * @param count      获取记录数
     * @return java.util.List<com.xuecheng.media.model.po.MediaProcess>
     * @description 获取待处理任务
     * @author Mr.M
     * @date 2022/9/14 14:49
     */
    List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count);


    /**
     * @param taskId   任务id
     * @param status   任务状态
     * @param fileId   文件id
     * @param url      url
     * @param errorMsg 错误信息
     * @return void
     * @description 保存任务结果
     * @author Mr.M
     * @date 2022/10/15 11:29
     */
    void saveProcessFinishStatus(Long taskId, String status, String fileId, String url, String errorMsg);




}
