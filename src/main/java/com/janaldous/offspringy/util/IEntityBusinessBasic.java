package com.janaldous.offspringy.util;

public interface IEntityBusinessBasic<T> {
	
	T update(T t) throws InvalidEntityException, EntityNotFoundException;
	
	void delete(Long id);
	
	T read(Long id) throws EntityNotFoundException;
	
	boolean exists(Long id);
	
}
