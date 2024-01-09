package com.baiyan.ddd.infrastructure.event;

import com.baiyan.ddd.domain.share.event.BaseDomainEvent;
import com.baiyan.ddd.domain.share.event.DomainEventRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 域事件仓储，保存已发生的领域事件，用于事件溯源 - 实现类
 * @author baiyan
 */
@Repository
public class DomainEventRepositoryImpl implements DomainEventRepository {

    @Override
    public BaseDomainEvent load(Long id) {
        return null;
    }

    @Override
    public List<BaseDomainEvent> loadByDomainId(String domainId) {
        return null;
    }

    @Override
    public void save(BaseDomainEvent baseDomainEvent) {

    }

    @Override
    public void update(BaseDomainEvent baseDomainEvent) {

    }
}
