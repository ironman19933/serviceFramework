package com.abhinav.learn_spring.services;

import com.abhinav.learn_spring.models.entities.BaseEntity;
import com.abhinav.learn_spring.models.entries.BaseEntry;
import com.abhinav.learn_spring.models.repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseService<Entity extends BaseEntity, Entry extends BaseEntry> {

    public CrudRepository<Entity, Long> baseRepository;

    public CrudRepository<Entity, Long> getBaseRepository() {
        return baseRepository;
    }

    protected abstract Entry convertToEntry(Entity entity);

    protected abstract Entity convertToEntity(Entry entry);

    public Entry find(Long id) {
        return convertToEntry(getBaseRepository().findOne(id));
    }
}
