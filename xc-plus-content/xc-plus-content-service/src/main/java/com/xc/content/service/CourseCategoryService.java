package com.xc.content.service;

import com.xc.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * 课程分类操作相关的service
 *
 * @author: LJ
 * @create: 2023/2/4
 */
public interface CourseCategoryService {

    /**
     * 课程分类查询
     * @param id 根结点id
     * @return 根结点下边的所有子结点
     */
    List<CourseCategoryTreeDto>  queryTreeNodes(String id);
}
