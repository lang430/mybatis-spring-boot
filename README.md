## 项目依赖
```xml
<!--mybatis-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.1</version>
</dependency>
<!--mapper-->
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
    <version>1.2.4</version>
</dependency>
<!--pagehelper-->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.2.3</version>
</dependency>
```
## Spring DevTools 配置
感谢[emf1002](https://github.com/emf1002)提供的解决方案。

在使用 DevTools 时，通用Mapper经常会出现 class x.x.A cannot be cast to x.x.A。

同一个类如果使用了不同的类加载器，就会产生这样的错误，所以解决方案就是让通用Mapper和实体类使用相同的类加载器即可。

DevTools 默认会对 IDE 中引入的所有项目使用 restart 类加载器，对于引入的 jar 包使用 base 类加载器，因此只要保证通用Mapper的jar包使用 restart
类加载器即可。

在 `src/main/resources` 中创建 META-INF 目录，在此目录下添加 spring-devtools.properties 配置，内容如下：
```properties
restart.include.mapper=/mapper-[\\w-\\.]+jar
restart.include.pagehelper=/pagehelper-[\\w-\\.]+jar
```
使用这个配置后，就会使用 restart 类加载加载 include 进去的 jar 包。

## 集成 MyBatis Generator
通过 Maven 插件集成的，所以运行插件使用下面的命令：
>mvn mybatis-generator:generate

Mybatis Geneator 详解:
>http://blog.csdn.net/isea533/article/details/42102297

## application.properties 配置
```properties
#mybatis
mybatis.type-aliases-package=tk.mybatis.springboot.model
mybatis.mapper-locations=classpath:mapper/*.xml

#mapper
#mappers 多个接口时逗号隔开
mapper.mappers=tk.mybatis.springboot.util.MyMapper
mapper.not-empty=false
mapper.identity=MYSQL

#pagehelper
pagehelper.helperDialect=mysql
pagehelper.reasonable=true
pagehelper.supportMethodsArguments=true
pagehelper.params=count=countSql
```


## MyBatis工具 http://www.mybatis.tk

- 推荐使用 Mybatis 通用 Mapper3 https://github.com/abel533/Mapper

- 推荐使用 Mybatis 分页插件 PageHelper https://github.com/pagehelper/Mybatis-PageHelper

