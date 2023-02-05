package com.xc.content.model.dto;

import lombok.Data;

/**
 * 修改课程Dto
 *
 * @author LJ
 * @create 2023/2/5
 */
@Data
public class EditCourseDto extends AddCourseDto {

    //课程id
    private Long id;
}
