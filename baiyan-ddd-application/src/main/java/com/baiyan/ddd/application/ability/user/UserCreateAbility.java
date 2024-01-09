package com.baiyan.ddd.application.ability.user;

import com.baiyan.ddd.application.ability.share.AbilityContext;
import com.baiyan.ddd.application.ability.share.BaseAbility;
import com.baiyan.ddd.application.ability.user.cmd.CreateUserAbilityCommand;
import com.baiyan.ddd.base.model.result.Result;
import com.baiyan.ddd.base.util.ValidationUtil;
import com.baiyan.ddd.domain.aggregate.role.model.Role;
import com.baiyan.ddd.domain.aggregate.role.repository.RoleRepository;
import com.baiyan.ddd.domain.aggregate.user.event.UserCreateEvent;
import com.baiyan.ddd.domain.aggregate.user.model.User;
import com.baiyan.ddd.domain.aggregate.user.repository.UserRepository;
import com.baiyan.ddd.domain.aggregate.user.service.UserDomainService;
import com.baiyan.ddd.domain.share.event.DomainEventPublisher;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 创建用户能力
 *
 * @author baiyan
 */
@Service
public class UserCreateAbility extends BaseAbility<CreateUserAbilityCommand, Void> {

    /**
     * 角色仓储接口
     */
    @Autowired
    RoleRepository roleRepository;

    /**
     * 用户仓储接口
     */
    @Autowired
    UserRepository userRepository;

    /**
     * 领域事件发布接口
     */
    @Autowired
    DomainEventPublisher domainEventPublisher;

    /**
     * 用户领域服务
     */
    @Autowired
    UserDomainService userDomainService;

    private final static String ROLE_INFO_KEY = "roleInfo";

    /**
     * 校验处理方法，用于检查创建用户的能力命令
     *
     * @param command 创建用户的能力命令
     * @throws IllegalArgumentException 如果用户名已存在或角色不存在
     */
    @Override
    public void checkHandler(CreateUserAbilityCommand command) throws IllegalArgumentException {
        //校验用户名不存在
        ValidationUtil.isTrue(Objects.isNull(userRepository.byUserName(command.getUserName())), "user.user.name.is.exist");

        //校验角色存在
        List<Role> roles = roleRepository.listByIds(command.getRoles());
        ValidationUtil.isTrue(CollectionUtils.isNotEmpty(roles) &&
                        Objects.equals(roles.size(), command.getRoles().size()),
                "user.role.is.not.exist");

        AbilityContext.putValue(ROLE_INFO_KEY, roles);
    }

    /**
     * 检查幂等性方法
     *
     * @param command 创建用户能力命令对象
     * @return 无响应结果
     */
    @Override
    public Result<Void> checkIdempotent(CreateUserAbilityCommand command) {

        //在这里进行幂等处理判断

        return Result.success(null);
    }

    /**
     * 执行创建用户能力的方法
     *
     * FIXME 演示领域服务使用
     *
     * @param command 创建用户能力的命令对象
     * @return 返回结果对象
     * @throws Exception 可能抛出的异常
     */
    @Override
    public Result<Void> execute(CreateUserAbilityCommand command) {

        //工厂创建用户
        User user = command.toUser();

        //执行用户新增相关业务逻辑
        user.printCreate();

        // FIXME 仅仅为了演示领域服务使用，这没必要这么做，能力点已经是一个比较原子的业务逻辑点了
        //  理论上有了能力层之后直接可以砍掉领域服务层
        List<Role> roles = AbilityContext.getValue(ROLE_INFO_KEY);
        //  需要用户角色两个聚合完成用户聚合的原子化逻辑, 根据用户关联的角色打印出标签
        userDomainService.printTag(user, roles);

        //存储用户
        User save = userRepository.save(user);

        //发布用户新建的领域事件
        domainEventPublisher.publish(new UserCreateEvent(save));

        return Result.success(null);
    }

}
