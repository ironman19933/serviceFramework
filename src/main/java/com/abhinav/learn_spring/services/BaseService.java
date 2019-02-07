package com.abhinav.learn_spring.services;

import com.abhinav.learn_spring.exceptions.ServiceException;
import com.abhinav.learn_spring.models.SearchSpecification;
import com.abhinav.learn_spring.models.entities.BaseEntity;
import com.abhinav.learn_spring.models.entries.BaseEntry;
import com.abhinav.learn_spring.models.repositories.CustomBaseRepository;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Getter
public abstract class BaseService<Entity extends BaseEntity, Entry extends BaseEntry> {

    public JpaRepository<Entity, Long> repository;

    public CustomBaseRepository<Entity> customBaseRepository;

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

    public List<Entity> search(String filters) {
        Map<String, HashSet<String>> filers = getFilters(filters);
        return customBaseRepository.findAll(new SearchSpecification<>(filers));
    }

    public static Map<String, HashSet<String>> getFilters(String filters) {
        Map<String, HashSet<String>> map = new HashMap<>();
        if (filters == null)
            return map;
        try {
            String[] filterArray = filters.split(";");
            for (String filter : filterArray) {
                String[] splitFilter = filter.split(":");
                HashSet<String> filterVals = new HashSet<>();
                filterVals.addAll(Arrays.asList(splitFilter[1].split(",")));
                map.put(splitFilter[0], filterVals);
            }
        } catch (Exception ignored) {
        }
        return map;
    }
}
