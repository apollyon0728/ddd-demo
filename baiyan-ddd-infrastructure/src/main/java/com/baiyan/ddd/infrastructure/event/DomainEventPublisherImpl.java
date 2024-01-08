package com.baiyan.ddd.infrastructure.event;

import com.baiyan.ddd.base.util.GsonUtil;
import com.baiyan.ddd.domain.share.event.BaseDomainEvent;
import com.baiyan.ddd.domain.share.event.DomainEventPublisher;
import com.baiyan.ddd.domain.share.event.DomainEventRepository;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


/**
 * @author baiyan
 */
@Component
@Slf4j
public class DomainEventPublisherImpl implements DomainEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private DomainEventRepository domainEventRepository;


    /**
     * 发布事件的方法
     * @param event 事件对象
     * 发布事件的方法，用于将事件发布到应用程序的事件发布者
     * @param event 事件对象，必须继承自BaseDomainEvent
     * @throws IllegalArgumentException 如果事件为空，则抛出此异常
     */
    @Override
    public <EVENT extends BaseDomainEvent> void publish(EVENT event) {
        log.info("发布事件,event:{}", GsonUtil.gsonToString(event));
        applicationEventPublisher.publishEvent(event);
    }

    /**
     * 保存并发布事件
     * @param event 事件对象
     * @param <EVENT> 事件类型
     * @throws IllegalArgumentException 如果事件对象为空
     */
    @Override
    public <EVENT extends BaseDomainEvent> void publishAndSave(EVENT event) {
        log.info("保存并发布事件,event:{}", GsonUtil.gsonToString(event));

        List<BaseDomainEvent> baseDomainEventList = domainEventRepository.loadByDomainId(event.getDomainId());
        if (CollectionUtils.isNotEmpty(baseDomainEventList)) {

            boolean anyMatch = baseDomainEventList.stream().anyMatch(e -> Objects.equals(e.getId(), event.getId()));
            if (anyMatch) {
                applicationEventPublisher.publishEvent(event);
                return;
            }
        }

        domainEventRepository.save(event);
        applicationEventPublisher.publishEvent(event);
    }

}
