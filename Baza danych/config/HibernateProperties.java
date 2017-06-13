package com.lenda.marc.config;

import org.springframework.core.env.Environment;

import java.util.Properties;


public class HibernateProperties {
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_AUTO_DETECTION="hibernate.archive.autodetection";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_IMPORT = "hibernate.hbm2ddl.import_files";

    private final Properties properties;

    HibernateProperties(Environment env) {
        properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_AUTO_DETECTION, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_AUTO_DETECTION));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_AUTO, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_AUTO));
        properties.put(PROPERTY_NAME_HIBERNATE_IMPORT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_IMPORT));
    }

    Properties toProperties() {
        return properties;
    }
}
