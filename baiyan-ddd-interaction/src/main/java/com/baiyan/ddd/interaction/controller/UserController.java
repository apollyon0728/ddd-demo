package com.baiyan.ddd.interaction.controller;

import com.baiyan.ddd.application.ability.user.cmd.CreateUserAbilityCommand;
import com.baiyan.ddd.application.command.UserApplicationService;
import com.baiyan.ddd.application.command.cmd.user.UpdateUserCommand;
import com.baiyan.ddd.application.query.UserQueryApplicationService;
import com.baiyan.ddd.application.query.model.user.dto.UserPageDTO;
import com.baiyan.ddd.base.model.query.KeywordQuery;
import com.baiyan.ddd.base.model.result.BaseResult;
import com.baiyan.ddd.base.model.result.Page;
import com.baiyan.ddd.base.model.result.PageResult;
import com.baiyan.ddd.base.model.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户管理web接口
 *
 * @author baiyan
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * 用户应用服务 (My: 属于应用层)
     */
    @Autowired
    UserApplicationService userApplicationService;

    /**
     * 用户查询应用服务
     */
    @Autowired
    UserQueryApplicationService userQueryApplicationService;

    /**
     * 创建用户能力
     *
     * @param command 创建用户能力的命令对象
     * @return 创建结果对象
     */
    @PostMapping
    public Result<Object> create(@RequestBody @Valid CreateUserAbilityCommand command){
        userApplicationService.create(command);
        return Result.ok(BaseResult.INSERT_SUCCESS);
    }

    /**
     * 更新用户名称
     * @param command 更新用户命令对象
     * @return 结果对象
     */
    @PutMapping
    public Result<Object> updateUserName(@RequestBody @Valid UpdateUserCommand command){
        userApplicationService.updateUserName(command);
        return Result.ok(BaseResult.UPDATE_SUCCESS);
    }

    /**
     * 删除指定ID的用户
     * @param id 用户ID
     * @return 删除结果
     * @throws Exception 异常信息
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id){
        userApplicationService.delete(id);
        return Result.ok(BaseResult.DELETE_SUCCESS);
    }

    /**
     * 查询用户信息
     * @param query 关键字查询对象
     * @return 用户分页结果对象
     */
    @GetMapping
    public PageResult<UserPageDTO> query(KeywordQuery query){
        Page<UserPageDTO> users = userQueryApplicationService.userPage(query);
        return PageResult.ok(users);
    }

}
