package com.xc.content.model.dto;

import lombok.Data;

/**
 * 请求参数DTO
 *
 * @author: LJ
 * @create: 2023/2/3
 */
@Data
public class QueryCourseParamsDto {

    //审核状态
    private String auditStatus;
    //课程名称
    private String courseName;
    //发布状态
    private String publishStatus;
}
