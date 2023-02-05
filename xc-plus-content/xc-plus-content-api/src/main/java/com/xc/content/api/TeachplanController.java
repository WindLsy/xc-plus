package com.xc.content.api;

import com.xc.content.model.dto.SaveTeachplanDto;
import com.xc.content.model.dto.TeachplanDto;
import com.xc.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程计划关联
 *
 * @author LJ
 * @create 2023/2/5
 */
@Slf4j
@Api(value = "课程计划接口",tags = "课程计划接口")
@RestController
public class TeachplanController {
    @Autowired
    TeachplanService teachplanService;

    @ApiOperation("课程计划查询")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){
        return teachplanService.findTeachplayTree(courseId);
    }

    @ApiOperation("课程计划新增查询")
    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody SaveTeachplanDto dto){
        teachplanService.saveTeachplan(dto);
    }
}
