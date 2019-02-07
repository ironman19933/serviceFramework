package com.abhinav.learn_spring.models.repositories;

import com.abhinav.learn_spring.models.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomBaseRepository<M extends BaseEntity> extends JpaRepository<M, Long>, JpaSpecificationExecutor<M> {
}
