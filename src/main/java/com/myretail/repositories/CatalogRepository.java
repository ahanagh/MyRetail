package com.myretail.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository<T> {

	T save(T t);

    T delete(String id);

    T update(T t);

}
