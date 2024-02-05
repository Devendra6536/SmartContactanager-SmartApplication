package com.smart.dao;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entities.FakeUser;

public interface FakeUserRepository extends JpaRepository<FakeUser,Integer> {

}
