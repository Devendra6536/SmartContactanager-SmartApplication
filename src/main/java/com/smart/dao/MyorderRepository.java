package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entities.MyOrder;

public interface MyorderRepository extends JpaRepository<MyOrder, Long> {

}
