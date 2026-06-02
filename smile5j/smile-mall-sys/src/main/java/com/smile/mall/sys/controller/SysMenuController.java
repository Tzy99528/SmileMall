package com.smile.mall.sys.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smile.mall.sys.service.SysMenuService;

import java.util.Map;

/**
 * 处理系统相关的 http 请求
 */
@RestController
@RequestMapping("/sys/menu")
@AllArgsConstructor
public class SysMenuController
{
    // 用于处理菜单的业务逻辑
    private final SysMenuService sysMenuService;

    @GetMapping("/nav")
    public ServerResponseEntity<Map<Object, Object>> nav()
    {
        List<SysMenu> menuList = sysMenuService.listMenuByUserId(SecurityUtils.getSysUser().getUserId());

        return ServerResonseEntity.success(MapUtil.builder().put("menuList", menuList).put("authorities", SecurityUtils.getSysUser().getAuthorities()).build());
    }
}
