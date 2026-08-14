
package com.smile.mall.sys.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import com.smile.mall.common.response.ServerResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.smile.mall.common.annotation.SysLog;
import com.smile.mall.common.exception.SmileMallBindException;
import com.smile.mall.security.admin.util.SecurityUtils;
import com.smile.mall.sys.constant.Constant;
import com.smile.mall.sys.constant.MenuType;
import com.smile.mall.sys.model.SysMenu;
import com.smile.mall.sys.service.SysMenuService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 处理系统菜单相关的 HTTP 请求
 * @author lgh
 */
@RestController
@RequestMapping("/sys/menu")
@AllArgsConstructor
public class SysMenuController{

	// 用于处理菜单的业务逻辑
	private final SysMenuService sysMenuService;

	@GetMapping("/nav")
	@Operation(summary = "获取用户所拥有的菜单和权限" , description = "通过登陆用户的userId获取用户所拥有的菜单和权限")
	public ServerResponseEntity<Map<Object, Object>> nav(){
		List<SysMenu> menuList = sysMenuService.listMenuByUserId(SecurityUtils.getSysUser().getUserId());

		return ServerResponseEntity.success(MapUtil.builder().put("menuList", menuList).put("authorities", SecurityUtils.getSysUser().getAuthorities()).build());
	}

	/**
	 * 获取菜单页面的表格数据（包括菜单和按钮）
	 * @return
	 */
	@GetMapping("/table")
	public ServerResponseEntity<List<SysMenu>> table(){
		// 调用sysMenuService.listMenuAndBtn获取所有菜单和按钮的列表
		List<SysMenu> sysMenuList = sysMenuService.listMenuAndBtn();
		return ServerResponseEntity.success(sysMenuList);
	}

	/**
	 * 获取用户所拥有的菜单（不包括按钮），用于新建或修改角色时获取菜单信息
	 */
	@GetMapping("/list")
	@Operation(summary = "获取用户所拥有的菜单(不包括按钮)" , description = "通过登陆用户的userId获取用户所拥有的菜单和权限")
	public ServerResponseEntity<List<SysMenu>> list(){
		List<SysMenu> sysMenuList= sysMenuService.listSimpleMenuNoButton();
		return ServerResponseEntity.success(sysMenuList);
	}

	/**
	 * 获取所有根菜单（一级菜单）
	 */
	@GetMapping("/listRootMenu")
	public ServerResponseEntity<List<SysMenu>> listRootMenu(){
		//查询列表数据
		List<SysMenu> menuList = sysMenuService.listRootMenu();

		return ServerResponseEntity.success(menuList);
	}

	/**
	 * 根据父菜单ID获取子菜单
	 */
	@GetMapping("/listChildrenMenu")
	public ServerResponseEntity<List<SysMenu>> listChildrenMenu(Long parentId){
		//查询列表数据
		List<SysMenu> menuList = sysMenuService.listChildrenMenuByParentId(parentId);

		return ServerResponseEntity.success(menuList);
	}

	/**
	 * 根据菜单ID获取菜单信息
	 */
	@GetMapping("/info/{menuId}")
	@PreAuthorize("@pms.hasPermission('sys:menu:info')")
	public ServerResponseEntity<SysMenu> info(@PathVariable("menuId") Long menuId){
		SysMenu menu = sysMenuService.getById(menuId);
		return ServerResponseEntity.success(menu);
	}

	/**
	 * 保存菜单
	 */
	@SysLog("保存菜单")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys:menu:save')")
	public ServerResponseEntity<Void> save(@Valid @RequestBody SysMenu menu){
		// 调用verifyForm方法验证菜单数据的合法性
		verifyForm(menu);
		sysMenuService.save(menu);
		return ServerResponseEntity.success();
	}

	/**
	 * 修改
	 */
	@SysLog("修改菜单")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys:menu:update')")
	public ServerResponseEntity<String> update(@Valid @RequestBody SysMenu menu){
		//数据校验
		verifyForm(menu);

		if(menu.getType() == MenuType.MENU.getValue()){
			if(StrUtil.isBlank(menu.getUrl())){
				return ServerResponseEntity.showFailMsg("菜单URL不能为空");
			}
		}
		sysMenuService.updateById(menu);

		return ServerResponseEntity.success();
	}

	/**
	 * 删除
	 */
	@SysLog("删除菜单")
	@DeleteMapping("/{menuId}")
	@PreAuthorize("@pms.hasPermission('sys:menu:delete')")
	public ServerResponseEntity<String> delete(@PathVariable Long menuId){
		// 如果菜单ID小于等于系统菜单最大ID（常量Constant.SYS_MENU_MAX_ID），则不允许删除（系统菜单）
		if(menuId <= Constant.SYS_MENU_MAX_ID){
			return ServerResponseEntity.showFailMsg("系统菜单，不能删除");
		}
		// 检查是否有子菜单或按钮，如果有则不允许删除
		List<SysMenu> menuList = sysMenuService.listChildrenMenuByParentId(menuId);
		if(menuList.size() > 0){
			return ServerResponseEntity.showFailMsg("请先删除子菜单或按钮");
		}

		//调用 sysMenuService.deleteMenuAndRoleMenu 删除菜单及与之关联的角色菜单关系
		sysMenuService.deleteMenuAndRoleMenu(menuId);

		return ServerResponseEntity.success();
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenu menu){

		// 如果是菜单类型（MenuType.MENU），则URL不能为空
		if(menu.getType() == MenuType.MENU.getValue()){
			if(StrUtil.isBlank(menu.getUrl())){
				throw new SmileMallBindException("菜单URL不能为空");
			}
		}

		// 菜单不能设置自己为父菜单
		if(Objects.equals(menu.getMenuId(), menu.getParentId())){
			throw new SmileMallBindException("自己不能是自己的上级");
		}

		//上级菜单类型
		int parentType = MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}

		// 如果当前菜单是目录或菜单类型，则上级菜单必须是目录类型
		if(menu.getType() == MenuType.CATALOG.getValue() ||
				menu.getType() == MenuType.MENU.getValue()){
			if(parentType != MenuType.CATALOG.getValue()){
				throw new SmileMallBindException("上级菜单只能为目录类型");
			}
			return ;
		}

		// 如果当前菜单是按钮，则上级菜单必须是菜单类型
		if(menu.getType() == MenuType.BUTTON.getValue()){
			if(parentType != MenuType.MENU.getValue()){
				throw new SmileMallBindException("上级菜单只能为菜单类型");
			}
		}
	}
}
