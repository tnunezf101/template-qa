package com.template.selenium.utils;

import com.template.selenium.model.EnvironmentConfig;
import org.apache.commons.beanutils.BeanUtils;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class FileReaderUtil {

    private static FileReaderUtil fileReaderUtil;
    private Properties properties;
    private static final String propertyFilePath = "src/test/resources/config/environment.properties";




    public FileReaderUtil(String path) {

        BufferedReader reader;
        try {
            reader = new BufferedReader(new java.io.FileReader(path));
            properties = new Properties();
            properties.load(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe o la ruta es errónea " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al abrir al archivo " + e.getMessage());
        }

    }


    public static FileReaderUtil getInstance() {
        return fileReaderUtil == null ? fileReaderUtil = new FileReaderUtil(propertyFilePath) : fileReaderUtil;
    }


    public EnvironmentConfig getConfiguration() {
        Map<String, String> map = new HashMap<String, String>();
        EnvironmentConfig bean = new EnvironmentConfig();

        for (String name : properties.stringPropertyNames()) {
            map.put(name, properties.getProperty(name));
        }

        try {
            BeanUtils.populate(bean, map);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bean;

    }

}