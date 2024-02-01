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
 * 领域事件发布接口 实现类
 * @author baiyan
 *
 * GPT：
 * `领域事件是业务过程的一部分，当某个业务规则被触发时，一个领域事件就会被发布。
 * 领域事件通常用于解耦业务逻辑和事件发布者，同时允许其他系统或服务订阅这些事件并作出响应`。
 *
 * #### 优点
 * 1. **解耦**：通过发布领域事件，可以将事件的发布者与订阅者分离，降低系统的耦合度。
 * 2. **事件驱动架构**：允许构建事件驱动的系统，使得各个部分可以独立地工作，并在必要时进行集成。
 * 3. **业务语义**：领域事件具有明确的业务含义，有助于保持代码的业务相关性。
 * 4. **可审计性**：通过记录和追踪领域事件，可以方便地审计系统的行为和业务过程。
 */
@Component
@Slf4j
public class DomainEventPublisherImpl implements DomainEventPublisher {

    /**
     * 封装事件发布功能的接口。
     * 用作 的超级接口 ApplicationContext
     *
     * {@link org.springframework.context.ApplicationEventPublisher}
     */
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 领域事件仓储，保存已发生的领域事件，用于事件溯源
     */
    @Autowired
    private DomainEventRepository domainEventRepository;


    /**
     * 发布事件的方法
     * @param event 事件对象
     * 发布事件的方法，用于将事件发布到应用程序的事件发布者
     * @param event 事件对象，必须继承自BaseDomainEvent
     * @throws IllegalArgumentException 如果事件为空，则抛出此异常
     *
     * GPT: 在Spring中，可以使用`@EventListener`注解来定义监听器方法 (本项目没有搜到)
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
