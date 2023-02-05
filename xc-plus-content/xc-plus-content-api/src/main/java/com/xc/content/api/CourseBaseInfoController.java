package com.xc.content.api;

import com.xc.base.exception.ValidationGroups;
import com.xc.base.model.PageParams;
import com.xc.base.model.PageResult;
import com.xc.content.model.dto.AddCourseDto;
import com.xc.content.model.dto.CourseBaseInfoDto;
import com.xc.content.model.dto.EditCourseDto;
import com.xc.content.model.dto.QueryCourseParamsDto;
import com.xc.content.model.po.CourseBase;
import com.xc.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


    /**
     * @Validated 校验这个实体类
     * @param addCourseDto
     * @return
     */
    @ApiOperation("新增课程")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(ValidationGroups.Inster.class) AddCourseDto addCourseDto){

        //获取当前用户所属培训机构的id
        Long companyId = 22L;

        //调用service
        CourseBaseInfoDto courseBase = courseBaseInfoService.createCourseBase(companyId, addCourseDto);
        return courseBase;
    }

    /**
     * 查询修改前的课程信息
     * @param courseId
     * @return
     */
    @ApiOperation("查询修改前的课程信息")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){

        return courseBaseInfoService.getCourseBaseInfo(courseId);

    }

    @ApiOperation("修改的课程信息")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody EditCourseDto dto){

        Long companyId =1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId,dto);
    }






}
