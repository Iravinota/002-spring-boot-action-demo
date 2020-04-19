# spring boot in action demo

## Chapter 2

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