package com.abhinav.learn_spring.services;

import com.abhinav.learn_spring.models.entities.BaseEntity;
import com.abhinav.learn_spring.models.entries.BaseEntry;

public abstract class BaseService<Entity extends BaseEntity, Entry extends BaseEntry> {
    public abstract Entry convertToEntry(Entity entity);

    public abstract Entity convertToEntity(Entry entry);
}
