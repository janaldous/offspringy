package com.janaldous.offspringy.util;



public interface IEntityBusiness<T> extends IEntityBusinessBasic<T> {
	
	T create(T t) throws InvalidEntityException;
	
}
