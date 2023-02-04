package com.xc.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xc.content.model.dto.CourseCategoryTreeDto;
import com.xc.content.model.po.CourseCategory;

import java.util.List;

/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 * @author itcast
 */
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    List<CourseCategoryTreeDto> selectTreeNodes(String id);
}
