package com.xc.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xc.base.model.PageParams;
import com.xc.base.model.PageResult;
import com.xc.content.mapper.CourseBaseMapper;
import com.xc.content.mapper.CourseCategoryMapper;
import com.xc.content.mapper.CourseMarketMapper;
import com.xc.content.model.dto.AddCourseDto;
import com.xc.content.model.dto.CourseBaseInfoDto;
import com.xc.content.model.dto.QueryCourseParamsDto;
import com.xc.content.model.po.CourseBase;
import com.xc.content.model.po.CourseCategory;
import com.xc.content.model.po.CourseMarket;
import com.xc.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 课程管理Service实现
 *
 * @author: LJ
 * @create: 2023/2/4
 */
@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper courseCategoryMapper;


    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams params, QueryCourseParamsDto queryCourseParamsDto) {

        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //拼接查询条件
        //根据课程名称模糊查询  name like '%名称%'
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()),CourseBase::getName,queryCourseParamsDto.getCourseName());

        //根据课程审核状态
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus());

        //根据课程发布状态
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()),CourseBase::getStatus,queryCourseParamsDto.getPublishStatus());

        //分页参数
        Page<CourseBase> page = new Page<>(params.getPageNo(), params.getPageSize());

        //分页查询E page 分页参数, @Param("ew") Wrapper<T> queryWrapper 查询条件
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);

        List<CourseBase> items = pageResult.getRecords();
        //总记录数
        long total = pageResult.getTotal();

        //准备返回数据 List<T> items, long counts, long page, long pageSize
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(items, total, params.getPageNo(), params.getPageSize());


        return courseBasePageResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto) {
        //对参数进行合法性的校验
        //合法性校验
        if (StringUtils.isBlank(addCourseDto.getName())) {
            throw new RuntimeException("课程名称为空");
        }

        if (StringUtils.isBlank(addCourseDto.getMt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(addCourseDto.getSt())) {
            throw new RuntimeException("课程分类为空");
        }

        if (StringUtils.isBlank(addCourseDto.getGrade())) {
            throw new RuntimeException("课程等级为空");
        }

        if (StringUtils.isBlank(addCourseDto.getTeachmode())) {
            throw new RuntimeException("教育模式为空");
        }

        if (StringUtils.isBlank(addCourseDto.getUsers())) {
            throw new RuntimeException("适应人群为空");
        }

        if (StringUtils.isBlank(addCourseDto.getCharge())) {
            throw new RuntimeException("收费规则为空");
        }

        //对数据进行封装，调用mapper进行数据持久化
        CourseBase courseBase = new CourseBase();
        //将传入addCourseDto的数据设置到 courseBase对象
//        courseBase.setName(addCourseDto.getName());
//        courseBase.setMt(addCourseDto.getMt());
//        courseBase.setSt(addCourseDto.getSt());
        //将addCourseDto中和courseBase属性名一样的属性值拷贝到courseBase
        BeanUtils.copyProperties(addCourseDto,courseBase);
        //设置机构id
        courseBase.setCompanyId(companyId);
        //创建时间
        courseBase.setCreateDate(LocalDateTime.now());
        //审核状态默认为未提交
        courseBase.setAuditStatus("202002");
        //发布状态默认为未发布
        courseBase.setStatus("203001");
        //课程基本表插入一条记录
        int insert = courseBaseMapper.insert(courseBase);
        //获取课程id
        Long courseId = courseBase.getId();
        CourseMarket courseMarket = new CourseMarket();

        //将addCourseDto中和courseMarket属性名一样的属性值拷贝到courseMarket
        BeanUtils.copyProperties(addCourseDto,courseMarket);

        courseMarket.setId(courseId);
        //校验如果课程为收费，价格必须输入
        String charge = addCourseDto.getCharge();
        if(charge.equals("201001")){//收费
            if(courseMarket.getPrice() == null || courseMarket.getPrice().floatValue()<=0){
                throw new RuntimeException("课程为收费但是价格为空");
            }
        }

        //向课程营销表插入一条记录
        int insert1 = courseMarketMapper.insert(courseMarket);

        if(insert<=0|| insert1<=0){
            throw new RuntimeException("添加课程失败");
        }

        //组装要返回的结果
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(courseId);
        return courseBaseInfo;
    }
    /**
     * 根据课程id查询课程的基本和营销信息
     * @param courseId 课程id
     * @return 课程的信息
     */
    private CourseBaseInfoDto getCourseBaseInfo(Long courseId) {

        //基本信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);

        //营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);

        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);

        //根据课程分类的id查询分类的名称
        String mt = courseBase.getMt();
        String st = courseBase.getSt();

        CourseCategory mtCategory = courseCategoryMapper.selectById(mt);
        CourseCategory stCategory = courseCategoryMapper.selectById(st);
        if(mtCategory!=null){
            //分类名称
            String mtName = mtCategory.getName();
            courseBaseInfoDto.setMtName(mtName);
        }
        if(stCategory!=null){
            //分类名称
            String stName = stCategory.getName();
            courseBaseInfoDto.setStName(stName);
        }

        return courseBaseInfoDto;
    }
}
