package com.kjs.customer;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerBean, Long> {

	  Optional<CustomerBean> findByReference(String reference);
}
