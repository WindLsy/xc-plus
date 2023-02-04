package com.xc.content.service;

import com.xc.base.model.PageParams;
import com.xc.base.model.PageResult;
import com.xc.content.model.dto.QueryCourseParamsDto;
import com.xc.content.model.po.CourseBase;

/**
 * 课程管理Service
 *
 * @author: LJ
 * @create: 2023/2/4
 */
public interface CourseBaseInfoService {

    /**
     * 课程查询接口
     * @param params 分页查询
     * @param queryCourseParamsDto 查询条件
     * @return
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams params, QueryCourseParamsDto queryCourseParamsDto);
}
