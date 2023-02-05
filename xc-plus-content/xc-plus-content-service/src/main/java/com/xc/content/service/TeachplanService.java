package com.xc.content.service;

import com.xc.content.model.dto.SaveTeachplanDto;
import com.xc.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * 课程计划
 *
 * @author LJ
 * @create 2023/2/5
 */
public interface TeachplanService {

    List<TeachplanDto> findTeachplayTree(Long courseId);



    /**
     * 保存课程计划(新增/修改)
     * @param dto
     */
    void saveTeachplan(SaveTeachplanDto dto);
}
