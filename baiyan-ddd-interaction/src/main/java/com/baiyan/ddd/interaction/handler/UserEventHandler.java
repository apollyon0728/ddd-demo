package com.baiyan.ddd.interaction.handler;

import com.baiyan.ddd.base.model.result.Result;
import com.baiyan.ddd.domain.aggregate.user.event.UserDeleteEvent;
import com.baiyan.ddd.domain.share.event.NeedSaveEventResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 用户事件处理
 *
 * @author baiyan
 */
@Component
@Slf4j
public class UserEventHandler {

    /**
     * 处理用户删除事件的方法
     *
     * @param event 用户删除事件
     * @return 处理结果对象
     * @throws Exception 如果处理过程中发生异常
     */
    @TransactionalEventListener(fallbackExecution = true)
    @NeedSaveEventResult
    public Result<Object> handleEvent(UserDeleteEvent event) {
        try {
            log.info("用户删除后，后续执行强相关的链式调用逻辑");
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

}

/**
 * NeedSaveEventHandlerAspect 对 添加@NeedSaveEventResult 做了AOP处理， 处理 UserDeleteEvent 事件
 * {@link com.baiyan.ddd.infrastructure.event.NeedSaveEventHandlerAspect}
 */





