# spring boot in action demo

## Chapter 2 - 自动配置

spring-boot使用起步依赖和自动配置，自动拼装了bean

- pom.xml中，增加了lombok的依赖，在IDEA中使用时需安装lombok插件。使用@Data修饰的类，可以自动设置所有的getter和setter方法
- pom.xml中，增加了spring-boot-starter-actuator依赖，application.properties中，添加了management相关的配置，使actuator生效
- 因为ClassPath中有H2依赖，所以spring-boot会自动创建H2相关的Bean。但是这个bean在http://localhost:8080/actuator/beans中不显示。JPA实现（Hibernate）会用到它
- spring data jpa引入了Hibernate，所以会创建Hibernate相关bean
- spring data jpa会自动会接口创建service实现
- 由于有数据库操作，默认会创建HikaliCP的数据库连接池bean
- 由于有Thymeleaf，所以会配置Thymeleaf为Spring MVC的视图，并自动解析/src/resources/templates中的模板
- 由于依赖了spring-boot-starter-web，所以会自动配置DispacherServlet并启用Spring MVC，并自动扫描/src/resources/static中网页
- 自动启用嵌入式tomcat

## Chapter 3 - 自定义配置 - Security

在Chapter 2代码的基础上，只是添加上spring-boot-startere-security的依赖，就可以在进入http://localhost:8080/readingList/asss时，添加上安全验证，跳转到一个默认的登陆页面。用户名是user，会在启动日志中打印一个随机密码

- 由于spring-boot-starter-data-jpa和书上的版本不一样，所以SecurityConfig中的代码有些不同
- 依赖hibernate之后，会自动创建@Entity对应的表，但是不会执行schema.sql和data.sql。需在application.properties中配置spring.jpa.hibernate.ddl-auto=none，关闭hibernate自动创建表
- SecurityConfig中是Spring Security的相关配置
  - configure(HttpSecurity http): 对每个请求（URL）进行细粒度安全控制
    - spring security默认启用CSRF保护，POST时必须验证token。 https://blog.csdn.net/t894690230/article/details/52404105
  - configure(AuthenticationManagerBuilder auth): 用来进行用户认证（用户名、密码登陆验证）
  
设置自定义参数:

- @ConfigurationProperties(prefix = "amazon") // spring boot已经自动添加了@EnableConfigurationProperties注解
  - 添加@ConfigurationProperties注解后IDEA会提示错误，没关系，不用管，IDEA版本问题。参考https://stackoverflow.com/questions/42839126/configurationproperties-spring-boot-configuration-annotation-processor-not-foun


## Chapter 4 - test

集成测试

- MockMvcWebTests测试类
- @RunWith(SpringJUnit4ClassRunner.class)需要pom.xml中包含spring-boot-starter-test，且没有exclusion junit-vintage-engine
- @WebAppConfiguration: 启动web mvc测试
- 使用MockMvc对web进行测试
- Hibernate使用import.sql插入数据
- 如果配置spring.jpa.hibernate.ddl-auto=none，则需要自己写SQL脚本来创建表和插入数据，且需要创建Book所用的HIBERNATE_SEQUENCE

安全测试：

- pom.xml中添加org.springframework.security:spring-security-test
- 使用@WithUserDetails测试时，需要把SecurityConfig.java中的UserDetailsService设置为一个单独的Bean

其它配置：

- Profile:
  - 通过在application.properties中配置spring.profiles.active=dev，使用application-dev.properties中的配置启动spring boot
  - application-{label}.properties
  - spring.profiles.active={label}
- Thymeleaf:
  - readingList.html中，xmlns:th="http://thymeleaf.org"，注意这里没有www，这样这个thymeleaf页就不会有红线了
