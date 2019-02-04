package com.abhinav.learn_spring.services;

import com.abhinav.learn_spring.exceptions.ServiceException;
import com.abhinav.learn_spring.models.entities.BaseEntity;
import com.abhinav.learn_spring.models.entries.BaseEntry;
import lombok.Getter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Getter
public abstract class BaseService<Entity extends BaseEntity, Entry extends BaseEntry> {

    public CrudRepository<Entity, Long> repository;

    protected abstract Entry convertToEntry(Entity entity, Entry entry);

    protected abstract Entity convertToEntity(Entry entry, Entity entity);

    @Transactional(readOnly = true)
    public Entry find(Long id) {
        return convertToEntry(getRepository().findOne(id), null);
    }

    @Transactional(rollbackFor = Exception.class)
    public Entry save(Entry entry) {
        Entity newEntity = convertToEntity(entry, null);
        newEntity = getRepository().save(newEntity);
        return convertToEntry(newEntity, null);
    }

    @Transactional(rollbackFor = Exception.class)
    public Entry update(Entry entry, Long id) throws ServiceException {
        Entity entity = getRepository().findOne(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException("Data not found");
        }
        entity = getRepository().save(convertToEntity(entry, entity));
        return convertToEntry(entity, null);
    }
}
