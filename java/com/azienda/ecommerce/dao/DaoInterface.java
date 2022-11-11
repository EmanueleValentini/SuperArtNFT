package com.azienda.ecommerce.dao;

import java.util.List;

public interface DaoInterface<T> {

	public T create(T t);

	public List<T> retrieve ();

	public T update(T t);

	public void delete(T t);

}
