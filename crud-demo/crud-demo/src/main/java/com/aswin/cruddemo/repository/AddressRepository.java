package com.aswin.cruddemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aswin.cruddemo.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
