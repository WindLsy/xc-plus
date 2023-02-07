# xc-plus
一个仿照MOOC的B2B2C在线教育平台-学成在线

## 第一天

### 第一天内容

1. 了解项目业务，以及技术架构

2. 创建父工程(parent)、基础工程(base)。

3. 学习git与maven细节问题

4. 数据库环境搭建 

5. 内容管理模块
   
    - PO：与数据库中的类型相对应
    
    - DTO：专门用来传输数据的，数据传输对象
    - VO：前端传过来的数据，如果前端比较单一可以直接使用DTO
    
    ![image-20230203235228074](https://qnyun.linshiyou.cn/images/image-20230203235228074.png)

### 第一天开发中遇到的问题

#### 1. git 版本冲突问题

- 本地文件版本与目标分支中版本不同的时候进行文件合并就会出现版本冲突
- 解决方式：手动合并确定最后版本即可。

#### 2. maven依赖版本冲突问题

- 可以通过exclusions排除依赖
- 使用父工程锁定版本号<dependencyManagement>

#### 3. MySQL

- MySQL常见存储引擎及区别
  - InnoDB
    - 支持事务、聚簇索引：索引和数据放在同一个文件中、支持表锁、行锁：并发度更高
  - MyISAM 
    - 不支持事务、非聚簇索引：索引和数据文件放在不同的文件中、表锁
- 建表的注意事项
  - `三大范式`
  - 存储引擎：一般默认InnoDB
  - 固定长度字段推荐使用char，速度更快
  - 长文本：text或者longtext
  - 二进制数据、图片：blob、longblob
  - 对金额字段建议使用DECIMAL
  - 主键字段建议使用自然主键，不要有业务意义，建议使用int unsigned类型，特殊场景使用bigint类
    型。
  - 如果要存储text、blob字段建议单独建一张表，
  - 设置字段默认值，比如：状态、创建时间等。
  - 注意字段的约束，比如：非空、唯一、主键等

#### 4.  SpringBoot常用的注解

- @RestController = @ResponseBody + @Controller：实现rest接口开发，返回json数据，

- @RequestMapping 定义接口地址，可以标记在类上也可以标记在方法上，支持http的post、put、get
  等方法。也可以单独使用@PostMapping 定义post接口，其他请求方法类似。

- @RequestBody 定义在方法上，用于将json串转成java对象。使用在前端出过来的请求参数中

- @PathVarible 接收请求路径中占位符的值，一般用于restful风格的url中

  

### 项目的开发流程

- 产品人员设计产品原型。
- 讨论需求。
- 分模块设计接口。
- 生成接口文档。
- 将接口文档给到前端人员，前后端分离开发。
- 开发完毕进行测试。
- 测试完毕发布项目，由运维人员进行部署安装。



## 第二天

### 第二天内容

1. 数据字典
2. 课程查询
3. MySQL树形查询
   - 递归查询
4. 新增课程

### 第二天开发中遇到的问题

#### 1. 浏览器同源策略

- 同源策略是浏览器的一种安全机制，只要协议、注意、端口有一个不同，就不满足同源。

- 需要跨域处理，才能被浏览器解析。

- 跨域常见处理：

  - 添加响应头：Access-Control-Allow-Origin：*

    - 在spring中可以在controller类上使用注解`@CrossOrigin`解决跨域问题

  - 通过nginx代理跨域：由于服务端之间没有跨域，浏览器通过nginx去访问跨域地址。

    ![image-20230204221200083](https://qnyun.linshiyou.cn/images/image-20230204221200083.png)

#### 2. 编译之后的文件之中没有xml文件

- 需要在maven中添加如下内容

- ```xml
  <build>
      <!--编译打包过虑配置-->
      <resources>
          <resource>
              <directory>src/main/resources</directory>
              <filtering>true</filtering>
              <includes>
                  <include>**/*</include>
              </includes>
          </resource>
          <resource>
              <directory>src/main/java</directory>
              <includes>
                  <include>**/*.xml</include>
              </includes>
          </resource>
      </resources>
      <plugins>
  </build>
  ```

#### 3. Spring中事务失效场景

- Spring有8中事务失效场景

  1. 抛出检查异常导致事务不能正确回滚

  2. 业务方法内自己 try-catch 异常导致事务不能正确回滚
  3. aop 切面顺序导致导致事务不能正确回滚
  4. 非 public 方法导致的事务失效
  5. 父子容器导致的事务失效
  6. 调用本类方法不经过代理
  7. @Transactional 没有保证原子行为
  8. @Transactional 方法导致的 synchronized 失效

#### 4. Mybatis分页拦截器

- Mybatis分页拦截器实现
- 工作原理
  - 首先分页参数放到ThreadLocal中，拦截执行的sql
  - 根据数据库类型添加对应的分页语句重写sql将之转换为两条Sql语句：查询总数和分页查询
    - 例如：(select * from table where a) 转换为 (select count(*) from table where a)和(select * from tablewhere a limit ,)计算出total总条数、pageNum当前第几页、pageSize每页大小和当前页的数据，是否为首页，是否为尾页，总页数等。

#### 5. 查询一个树形表的方法

- 当层级固定时可以用表的自链接进行查询。
- 如果想灵活查询每个层级可以使用mysql递归方法，使用`with recursive`实现。

#### 6. MyBatis的ResultType和ResultMap的区别？

- ResultType：指定映射类型，只要查询的字段名和类型的属性名匹配可以自动映射。
- ResultMap：自定义映射规则，当查询的字段名和映射类型的属性不匹配时可以通过ResultMap自定义
  映射规则，也可以实现一对多、一对一映射。

#### 7. MyBatis中#{} 和 ${} 有什么区别？

- ${} 用于在动态 sql中拼接字符串，可能导致sql注入。
- #{}是标记一个占位符，可以防止sql注入。

## 第三天

### 第三天内容

1. 异常处理
2. JSR303校验
   - 分组校验
3. 修改课程
4. 课程计划管理 
5. 新增修改课程计划

### 第三天开发中遇到的问题

#### 1. 系统如何处理异常

- 我们自定义一个统一的异常处理器去捕获并处理异常。使用控制器增加注解@RestControllerAdvice和异常处理注解@ExceptionHandler来实现。
- 处理自定义异常
  - 程序在编写代码时根据校验结果主动抛出自定义异常类对象，抛出异常时指定详细的异常信息，异常处
    理器捕获异常信息记录异常日志并响应给用户。
- 处理未知异常
  - 接口执行过程中的一些运行时异常也会由异常处理器统一捕获，记录异常日志，统一响应给用户500错
    误。在异常处理器中还可以针对某个异常类型进行单独处理。
- 异常处理最好在接口层面做

#### 2. 请求参数的合法性校验如何做？

使用基于JSR303的校验框架实现，SpringBoot提供了JSR-303的支持，它就是spring-boot-starter-validation，它包括了很多校验规则，只需要在模型类中通过注解指定校验规则，在controller方法上开启校验。

## 第四天

### 第四天内容

1. 媒资管理模块
2. Nacos
3. 网关：gateway
4. 分布式文件系统
5. 上传图片

### 第四天开发中遇到的问题

#### 1. 网关的作用

- 路由转发
- 权限控制
- 限流
- 微服务有多个实例的时候，可以通过负载均衡算法进行路由

#### 2. Nacos

- 服务发现与注册中心，
  - namespace：用于区分环境、比如：开发环境、测试环境、生产环境。
  - group：用于区分应用项目，比如：xuecheng-plus项目、xuecheng2.0项目
- 配置管理中心
  - 统一管理，方便配置
  - 方便环境切换
- 共享配置和扩展配置的区别
  - 优先级不同
  - 其次可以更加方便的区分，扩展配置更加特别。
- 配置文件优先级问题
  - 项目应用名配置文件 > 扩展配置文件 > 共享配置文件 > 本地配置文件
  - 可以通过配置将本地配置文件的优先级变成最高的。

#### 3. 分布式文件系统，为甚选用MinIO

- 一个计算机无法存储海量的文件，通过网络将若干计算机组织起来共同去存储海量的文件，去接收海量用户的请求，这些组织起来的计算机通过网络进行通信。
- 常见的分布式文件系统：
  - NFS
  - GFS：谷歌开发的分块存储
  - HDFS
  - 云厂商的对象存储
- MinIO是一种轻量级的去中心化分布式文件系统

## 第五天

### 第五天内容

1. 上传图片优化
2. 断点续传
3. 上传视频
   1. 上传分块
   2. 下载分块
   3. 合并分块

### 第五天开发中遇到的问题

#### 1. Spring事务

- 通过代理对象控制事务
- 事务失效（共有8种场景）