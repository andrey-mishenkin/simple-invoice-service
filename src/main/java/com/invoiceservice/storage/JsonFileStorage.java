package com.invoiceservice.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonFileStorage<T> implements Storage<T> {

    @Autowired
    protected ObjectMapper objectMapper;
    protected String configFile;

    @Override
    public T read(Class<T> clazz, T defaultValue) throws Exception {
        
        try {
            T newValue =  objectMapper.readValue(new File(configFile), clazz);
            return newValue;
        } catch (FileNotFoundException e) {
            // Create file if not found yet
            write(defaultValue);
        }
        return defaultValue;
    }

    @Override
    public synchronized void write(Object entity) throws Exception {
        // Check path and create if doesn't exists
        File file = new File(configFile);
        String absPath = file.getAbsolutePath();
        File path = new File(absPath.substring(0, absPath.lastIndexOf(File.separator)));
        if (!path.exists()) {
            Files.createDirectories(path.toPath());
        }
        objectMapper.writeValue(file, entity);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }
}
