package com.baiyan.ddd.application.command.cmd.user;

import com.baiyan.ddd.base.model.ddd.Command;
import com.baiyan.ddd.domain.aggregate.user.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 修改用户指令
 * @author baiyan
 */
@Data
public class UpdateUserCommand implements Command {

    @NotNull(message = "{user.id.is.null}")
    private Long userId;

    /**
     * 用户名
     */
    @NotBlank(message = "{user.userName.is.blank}")
    private String userName;

    /**
     * 用户真实名称
     */
    @NotBlank(message = "{user.realName.is.blank}")
    private String realName;

    /**
     * 用户手机号
     */
    @NotBlank(message = "{user.phone.is.blank}")
    private String phone;

    /**
     * 省
     */
    @NotBlank(message = "{user.province.is.blank}")
    private String province;

    /**
     * 市
     */
    @NotBlank(message = "{user.city.is.blank}")
    private String city;

    /**
     * 区
     */
    @NotBlank(message = "{user.county.is.blank}")
    private String county;

    /**
     * 角色
     */
    @Size(min = 1,message = "{user.role.id.is.empty}")
    private List<Long> roles;

    /**
     * 单位id
     */
    @NotNull(message = "{unit.id.is.null}")
    private Long unitId;


    /**
     * 构建出需要修改的用户
     *
     * @param command
     * @return
     */
    public User toUser(UpdateUserCommand command){
        User user = User.builder()
                .id(command.getUserId())
                .userName(command.getUserName())
                .realName(command.getRealName())
                .phone(command.getPhone())
                .build();
        user.bindUnit(command.getUnitId());
        user.bindRole(command.getRoles());
        user.bindAddress(command.getProvince(),command.getCity(),command.getCounty());
        return user;
    }
}
