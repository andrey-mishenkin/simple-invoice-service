package com.invoiceservice.storage;

/**
 * Storage interface for type T
 *
 */
public interface Storage<T> {
    public void write(T entity) throws Exception;
    T read(Class<T> clazz, T defaultValue) throws Exception;
}
