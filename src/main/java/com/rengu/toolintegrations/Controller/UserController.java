package com.rengu.toolintegrations.Controller;

import com.rengu.toolintegrations.Entity.ResultEntity;
import com.rengu.toolintegrations.Entity.UserEntity;
import com.rengu.toolintegrations.Service.UserActionLogService;
import com.rengu.toolintegrations.Service.UserService;
import com.rengu.toolintegrations.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @program: OperationsManagementSuiteV3
 * @author: hanchangming
 * @create: 2018-08-22 17:13
 **/


@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final UserActionLogService userActionLogService;

    @Autowired
    public UserController(UserService userService, UserActionLogService userActionLogService) {
        this.userService = userService;
        this.userActionLogService = userActionLogService;
    }

    // 保存普通用户
    @PostMapping(value = "/user")
    public ResultEntity saveDefaultUser(UserEntity userEntity) {
        return ResultUtils.build(userService.saveDefaultUser(userEntity));
    }

    //保存管理员用户
    @PostMapping(value = "/admin")
    @PreAuthorize(value = "hasRole('admin')")
    public ResultEntity saveAdminUser(UserEntity userEntity) {
        return ResultUtils.build(userService.saveAdminUser(userEntity));
    }

    // 删除用户
    @DeleteMapping(value = "/{userId}")
    @PreAuthorize(value = "hasRole('admin')")
    public ResultEntity deleteUserById(@PathVariable(value = "userId") String userId) {
        return ResultUtils.build(userService.deleteUserById(userId));
    }

    // 根据Id修改密码
    @PatchMapping(value = "/{userId}/password")
    public ResultEntity updatePasswordById(@PathVariable(value = "userId") String userId, @RequestParam(value = "password") String password) {
        return ResultUtils.build(userService.updateUserPasswordById(userId, password));
    }

    // 根据Id升级用户
    @PatchMapping(value = "/{userId}/upgrade")
    @PreAuthorize(value = "hasRole('admin')")
    public ResultEntity userUpgradeById(@PathVariable(value = "userId") String userId) {
        return ResultUtils.build(userService.userUpgradeById(userId));
    }

    // 根据Id降级用户
    @PatchMapping(value = "/{userId}/degrade")
    @PreAuthorize(value = "hasRole('admin')")
    public ResultEntity userDegradeById(@PathVariable(value = "userId") String userId) {
        return ResultUtils.build(userService.userDegradeById(userId));
    }

    // 根据id查询用户
    @GetMapping(value = "/{userId}")
    public ResultEntity getUserById(@PathVariable(value = "userId") String userId) {
        return ResultUtils.build(userService.getUserById(userId));
    }

    // 查询所有用户
    @GetMapping
    @PreAuthorize(value = "hasRole('admin')")
    public ResultEntity getUsers(@PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResultUtils.build(userService.getUsers(pageable));
    }

    // 根据Id查询用户操作日志
    @GetMapping(value = "/{userId}/useractionlogs")
    public ResultEntity getUserActionLogsByUsername(@PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable(value = "userId") String userId) {
        return ResultUtils.build(userActionLogService.getUserActionLogsByUsername(pageable, userService.getUserById(userId).getUsername()));
    }

}
