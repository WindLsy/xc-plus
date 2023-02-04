package com.xc.content.api;

import com.xc.base.model.PageParams;
import com.xc.base.model.PageResult;
import com.xc.content.model.dto.QueryCourseParamsDto;
import com.xc.content.model.po.CourseBase;
import com.xc.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程查询接口
 *
 * @author: LJ
 * @create: 2023/2/4
 */
@Api(value = "课程管理接口",tags = "课程管理接口")
@RestController
public class CourseBaseInfoController {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams params, @RequestBody QueryCourseParamsDto queryCourseParamsDto){

        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(params, queryCourseParamsDto);

        return  courseBasePageResult;
    }

}
