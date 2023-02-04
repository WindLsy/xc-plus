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
   - 递归

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

- 