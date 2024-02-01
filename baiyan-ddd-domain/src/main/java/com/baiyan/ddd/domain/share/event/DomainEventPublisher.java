package com.baiyan.ddd.domain.share.event;

/**
 * 领域事件发布接口
 * @author baiyan
 *
 * GPT:`领域事件是业务过程的一部分，当某个业务规则被触发时，一个领域事件就会被发布。
 * 领域事件通常用于解耦业务逻辑和事件发布者，同时允许其他系统或服务订阅这些事件并作出响应`。
 */
public interface DomainEventPublisher {

    /**
     * 发布事件
     *
     * @param event event
     * my: 这里event就是一个自定义的泛型，自己起的名字
     */
    <EVENT extends BaseDomainEvent> void publish(EVENT event);

    /**
     * 发布事件并保存
     *
     * @param event event
     */
    <EVENT extends BaseDomainEvent> void publishAndSave(EVENT event);

}
