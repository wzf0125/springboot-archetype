package com.quanta.archetype.controller;

import com.quanta.archetype.bean.JsonResponse;
import com.quanta.archetype.constants.Roles;
import com.quanta.archetype.interceptor.RequiredPermission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/demo")
@RestController
@RequiredPermission({Roles.ROLE_USER}) // 权限拦截最低仅限用户
public class DemoController extends BaseController{

    @RequestMapping("/HelloWorld")
    public JsonResponse<Object> helloWorld() {
        return JsonResponse.success("Hello_World");
    }

    /**
     * 三级管理员
     */
    @RequestMapping("/thirdAdmin")
    @RequiredPermission({Roles.ROLE_THIRD_ADMIN})
    public JsonResponse<Object> thirdAdmin() {
        return JsonResponse.success();
    }

    /**
     * 二级管理员
     */
    @RequestMapping("/secondAdmin")
    @RequiredPermission({Roles.ROLE_SECOND_ADMIN})
    public JsonResponse<Object> secondAdmin() {
        return JsonResponse.success();
    }

    /**
     * 一级管理员
     */
    @RequestMapping("/superAdmin")
    @RequiredPermission({Roles.ROLE_SUPER_ADMIN})
    public JsonResponse<Object> superAdmin() {
        return JsonResponse.success();
    }

    /**
     * 顶级管理员
     */
    @RequestMapping("/admin")
    @RequiredPermission({Roles.ROLE_THIRD_ADMIN, Roles.ROLE_SECOND_ADMIN, Roles.ROLE_SUPER_ADMIN})
    public JsonResponse<Object> admin() {
        return JsonResponse.success();
    }

}
