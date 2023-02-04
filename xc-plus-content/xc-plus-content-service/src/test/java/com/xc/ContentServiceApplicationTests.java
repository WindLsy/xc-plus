package com.xc;

import com.xc.base.model.PageParams;
import com.xc.base.model.PageResult;
import com.xc.content.mapper.CourseBaseMapper;
import com.xc.content.model.dto.QueryCourseParamsDto;
import com.xc.content.model.po.CourseBase;
import com.xc.content.service.CourseBaseInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * 测试
 *
 * @author: LJ
 * @create: 2023/2/4
 */
@SpringBootTest
public class ContentServiceApplicationTests {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseBaseInfoService courseBaseInfoService;



    @Test
    void testCourseBaseMapper() {
        CourseBase courseBase = courseBaseMapper.selectById(22);
        Assertions.assertNotNull(courseBase);
    }
    @Test
    void testCourseBaseInfoService() {
        PageParams pageParams = new PageParams();
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams, new QueryCourseParamsDto());
        System.out.println(courseBasePageResult);
    }


}
