package com.xc.content.model.dto;

import com.xc.content.model.po.CourseCategory;
import lombok.Data;

import java.util.List;

/**
 * 课程分类DTO
 *
 * @author LJ
 * @create 2023/2/4
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory {
    private List childrenTreeNodes;
}

