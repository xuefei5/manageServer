package com.simplemall.account.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simplemall.micro.serv.common.bean.Result;
import com.simplemall.micro.serv.common.bean.account.SysUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
public interface ISysUserService extends IService<SysUser> {

	/**
	 * 重置密码
	 *
	 * @param username
	 * @param oldpassword
	 * @param newpassword
	 * @param confirmpassword
	 * @return
	 */
	public Result<?> resetPassword(String username, String oldpassword, String newpassword, String confirmpassword);

	/**
	 * 修改密码
	 *
	 * @param sysUser
	 * @return
	 */
	public Result<?> changePassword(SysUser sysUser);

	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	public boolean deleteUser(String userId);

	/**
	 * 批量删除用户
	 * @param userIds
	 * @return
	 */
	public boolean deleteBatchUsers(String userIds);

	public SysUser getUserByName(String username);

	/**
	 * 添加用户和用户角色关系
	 * @param user
	 * @param roles
	 */
	public void addUserWithRole(SysUser user, String roles);


	/**
	 * 修改用户和用户角色关系
	 * @param user
	 * @param roles
	 */
	public void editUserWithRole(SysUser user, String roles);

	/**
	 * 获取用户的授权角色
	 * @param username
	 * @return
	 */
	public List<String> getRole(String username);


	/**
	 * 根据部门Id查询
	 * @param
	 * @return
	 */
	public IPage<SysUser> getUserByDepId(Page<SysUser> page, String departId, String username);

	/**
	 * 根据部门Ids查询
	 * @param
	 * @return
	 */
	public IPage<SysUser> getUserByDepIds(Page<SysUser> page, List<String> departIds, String username);


	/**
	 * 根据部门 Id 和 QueryWrapper 查询
	 *
     * @param page
     * @param departId
     * @param queryWrapper
     * @return
     */
    public IPage<SysUser> getUserByDepartIdAndQueryWrapper(Page<SysUser> page, String departId, QueryWrapper<SysUser> queryWrapper);


	/**
	 * 根据角色Id查询
	 * @param
	 * @return
	 */
	public IPage<SysUser> getUserByRoleId(Page<SysUser> page, String roleId, String username);

	/**
	 * 通过用户名获取用户角色集合
	 *
	 * @param username 用户名
	 * @return 角色集合
	 */
	Set<String> getUserRolesSet(String username);

	/**
	 * 通过用户名获取用户权限集合
	 *
	 * @param username 用户名
	 * @return 权限集合
	 */
	Set<String> getUserPermissionsSet(String username);

	/**
	 * 根据手机号获取用户名和密码
	 */
	public SysUser getUserByPhone(String phone);


	/**
	 * 根据邮箱获取用户
	 */
	public SysUser getUserByEmail(String email);


	/**
	   * 校验用户是否有效
	 * @param sysUser
	 * @return
	 */
	Result checkUserIsEffective(SysUser sysUser);

	/**
	 * 查询被逻辑删除的用户
	 */
	List<SysUser> queryLogicDeleted();

	/**
	 * 查询被逻辑删除的用户（可拼装查询条件）
	 */
	List<SysUser> queryLogicDeleted(LambdaQueryWrapper<SysUser> wrapper);

	/**
	 * 还原被逻辑删除的用户
	 */
	boolean revertLogicDeleted(List<String> userIds, SysUser updateEntity);

	/**
	 * 彻底删除被逻辑删除的用户
	 */
	boolean removeLogicDeleted(List<String> userIds);

    /**
     * 更新手机号、邮箱空字符串为 null
     */
    @Transactional(rollbackFor = Exception.class)
    boolean updateNullPhoneEmail();

	/**
	 * 根据部门Ids查询
	 * @param
	 * @return
	 */
	List<SysUser> queryByDepIds(List<String> departIds, String username);
}
