package com.xc.content.model.dto;

import com.xc.content.model.po.Teachplan;
import com.xc.content.model.po.TeachplanMedia;

import java.util.List;

/**
 * 关联媒资信息
 *
 * @author LJ
 * @create 2023/2/5
 */
public class TeachplanDto extends Teachplan {

    //课程计划关联的媒资信息
    TeachplanMedia teachplanMedia;

    //子目录
    List<TeachplanDto> teachPlanTreeNodes;
}
