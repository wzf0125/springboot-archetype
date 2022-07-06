# Springboot 快速开发脚手架

>   @author quanta

>   @date 2022/6/22

## 框架介绍

>   基于[猫兄](https://github.com/LeslieLeung)的脚手架项目[Springboot easy](https://github.com/LeslieLeung)再开发



> 使用时需要配置的地方

- 需要修改包名的地方
  - 启动类ArchetypeApplication中的```@MapperScan```
  - exception下的异常捕获类GlobalExceptionHandler中的```@ControllerAdvice```
  - log包下的RequestLogAspect ```@Pointcut```
- 根据需要可删除的部分
  - WxMaConfig & WxMaProperties 提供微信小程序的功能 无需对接小程序可删除
  - QcloudConfig & QcloudProperties 无需使用腾讯云储存桶功能时可删除
  - OSSConfig & OSSProperties & OSSUtils 无需使用阿里云对象存储功能时可删除
- 代码生成器部分
  - CodeGenerator 根据注释填入对应信息后直接运行即可一键生成controller service mapper entity
  - 生成的entity需要手动调整一些属性的类型 
    - 比如数据库中的tinyint会被设置为boolean
    - 还有时间类型

> 功能介绍

-   代码生成器
    -   在CodeGenerator类中输入数据库等配置即可一键生成controller service mapper entity
    -   注意: 建议在使用完后删除/注释调代码防止后续误触导致覆盖已写好的代码 (可以顺便移除生成所需的依赖)

- 基于redis的多级权限管理
    - 利用权限拦截注解RequiredPermission中的参数进行对应权限拦截
    - 具体流程
    - 请求进入拦截器
        - 拦截器判断是否放行
            - 是=>直接放行
            - 否=>进入拦截器 => 判断是否携带Token
                - 否=>拒绝访问
                - 是 =>通过Token从redis中获取uid和role
                    - 获取不到 (用户未登录)=>拒绝访问
                    - 将uid和role塞入请求头 后续可通过BaseController中的getUid()或getRole()
                        - 进行权限验证 判断类和方法上的注解
                            - 都不允许=>拒绝访问
                            - 允许=>放行
    - ![](https://974500760-1303995467.cos.ap-guangzhou.myqcloud.com/PicGo/%E8%84%9A%E6%89%8B%E6%9E%B6%E5%8E%9F%E7%90%86%20(1).png)



-   全局异常捕获&告警
    -   利用微信企业微信机器人告警
    -   当线上/测试环境出现异常时 通过企业微信机器人发送信息告警





