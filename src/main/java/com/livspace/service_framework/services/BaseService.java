package com.livspace.service_framework.services;

import com.livspace.service_framework.exceptions.ServiceException;
import com.livspace.service_framework.models.SearchOperator;
import com.livspace.service_framework.models.SearchSpecification;
import com.livspace.service_framework.models.entities.BaseEntity;
import com.livspace.service_framework.models.entries.BaseEntry;
import com.livspace.service_framework.models.repositories.BaseRepository;
import com.livspace.service_framework.utils.SearchHelper;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public abstract class BaseService<Entity extends BaseEntity, Entry extends BaseEntry> {

    public JpaRepository<Entity, Long> repository;

    public BaseRepository<Entity> baseRepository;

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
            throw new ServiceException("Record not found");
        }
        entity = getRepository().save(convertToEntity(entry, entity));
        return convertToEntry(entity, null);
    }

    @Transactional(readOnly = true)
    public List<Entry> search(String filters, Integer page, Integer fetchSize, String sortBy, String sortOrder) throws ServiceException {
        Map<SearchOperator, Map<String, String>> searchParams = SearchHelper.parseSearchParams(filters);
        Pageable pageable = SearchHelper.getPageRequest(page, fetchSize, sortBy, sortOrder);
        Page<Entity> resultPages = baseRepository.findAll(new SearchSpecification<>(searchParams), pageable);
        return resultPages.getContent()
                .parallelStream()
                .map(x -> convertToEntry(x, null))
                .collect(Collectors.toList());
    }
}
