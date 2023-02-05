package com.xc.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xc.content.mapper.TeachplanMapper;
import com.xc.content.model.dto.SaveTeachplanDto;
import com.xc.content.model.dto.TeachplanDto;
import com.xc.content.model.po.Teachplan;
import com.xc.content.service.TeachplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LJ
 * @create 2023/2/5
 */
@Slf4j
@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Override
    public List<TeachplanDto> findTeachplayTree(Long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto dto) {

        Long id = dto.getId();

        Teachplan teachplan = teachplanMapper.selectById(id);

        if(teachplan==null){
            teachplan = new Teachplan();
            BeanUtils.copyProperties(dto,teachplan);
            //找到同级课程计划的数量
            int count = getTeachplanCount(dto.getCourseId(), dto.getParentid());
            //新课程计划的值
            teachplan.setOrderby(count+1);

            teachplanMapper.insert(teachplan);

        }else{
            BeanUtils.copyProperties(dto,teachplan);
            //更新
            teachplanMapper.updateById(teachplan);

        }
    }

    private int getTeachplanCount(Long courseId, Long parentid) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId);
        queryWrapper.eq(Teachplan::getParentid,parentid);
        Integer count = teachplanMapper.selectCount(queryWrapper);

        return count;
    }
}
