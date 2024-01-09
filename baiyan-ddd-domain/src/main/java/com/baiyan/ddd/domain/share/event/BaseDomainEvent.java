package com.baiyan.ddd.domain.share.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 领域事件基类
 * @author baiyan

在给定的代码片段中，BaseDomainEvent 是一个抽象类，
它使用了泛型 <T>。泛型在这里的作用是为 BaseDomainEvent 提供一个类型参数，使得该类能够处理不同类型的数据。

具体来说，泛型 <T> 允许你在创建 BaseDomainEvent 的实例时为其指定一个具体的类型。
这样，你可以为每个事件定义一个特定类型的数据，从而使事件处理更加灵活和可重用。

泛型在这里的主要作用是增加了代码的灵活性和可重用性。通过使用泛型，你可以创建更加通用和可扩展的事件处理机制，而无需为每种类型的事件定义一个新的类。

 例如：
public class UserUpdateEvent extends BaseDomainEvent<User> {

public class UserDeleteEvent extends BaseDomainEvent<Long> {

 */
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseDomainEvent<T> implements Serializable {

    private static final long serialVersionUID = 1465328245048581896L;

    /**
     * 幂等键:即为当前事件的id
     */
    private String id;

    /**
     * 领域对象id
     */
    private String domainId;

    /**
     * 事件状态
     */
    private EventStatusEnum eventStatus;

    /**
     * 事件类型
     */
    private DomainEventEnum eventType;

    /**
     * 业务发生时间
     */
    private LocalDateTime occurredOn;

    /**
     * 领域事件数据
     */
    private T data;

    public BaseDomainEvent(String domainId, String id, DomainEventEnum eventType, EventStatusEnum eventStatus, LocalDateTime occurredOn, T data) {
        this.domainId = domainId;
        this.id = id;
        this.eventType = eventType;
        this.eventStatus = eventStatus;
        this.data = data;
        this.occurredOn = occurredOn;
    }

    /**
     * 修改时间状态为成功
     */
    public void handleSuccess() {
        this.eventStatus = EventStatusEnum.SUCCESS;
    }

    /**
     * 修改事件状态为失败
     */
    public void handleFailed() {
        this.eventStatus = EventStatusEnum.FAILED;
    }

}
