package com.abhinav.learn_spring.services;

import com.abhinav.learn_spring.exceptions.ServiceException;
import com.abhinav.learn_spring.models.SearchSpecification;
import com.abhinav.learn_spring.models.entities.BaseEntity;
import com.abhinav.learn_spring.models.entries.BaseEntry;
import com.abhinav.learn_spring.models.repositories.CustomBaseRepository;
import com.abhinav.learn_spring.utils.SearchHelper;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional(readOnly = true)
    public List<Entity> searchCustom(String filters, Integer page, Integer fetchSize, String sortBy, String sortOrder) throws ServiceException {
        Map<String, Map<String, String>> searchParams = SearchHelper.parseSearchParams(filters);
        Sort sort = null;
        if (sortOrder.equals("ASC")) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, sortBy));
        }
        if (sortOrder.equals("DESC")) {
            sort = new Sort(new Sort.Order(Sort.Direction.DESC, sortBy));
        }
        Pageable pageable = new PageRequest(page, fetchSize, sort);
        Page<Entity> resultPages = customBaseRepository.findAll(new SearchSpecification<>(searchParams), pageable);
        return resultPages.getContent();
    }
}
