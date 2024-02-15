package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entities.TestData;

public interface TestDataRepository extends JpaRepository<TestData,Integer> {

}
