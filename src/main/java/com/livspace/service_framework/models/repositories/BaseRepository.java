package com.livspace.service_framework.models.repositories;

import com.livspace.service_framework.models.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BaseRepository<M extends BaseEntity> extends JpaRepository<M, Long>, JpaSpecificationExecutor<M>, QuerydslPredicateExecutor<M> {
}
