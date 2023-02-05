package com.xc.content.service;

import com.xc.base.model.PageParams;
import com.xc.base.model.PageResult;
import com.xc.content.model.dto.AddCourseDto;
import com.xc.content.model.dto.CourseBaseInfoDto;
import com.xc.content.model.dto.EditCourseDto;
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


    /**
     * 新增课程
     * @param companyId 培训结构ID
     * @param addCourseDto 新增课程的信息
     * @return
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * 只能修改自己机构的课程
     * @param companyId 机构id
     * @param dto 修改课程信息
     * @return
     */
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);
}
