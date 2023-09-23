package com.sillimfive.mymap.config;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Locale;

public class CustomNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {

    private static String ROADMAP_CLASS = "RoadMap";
    private static String ROADMAP_FIELD = "roadMap";
    private static String PHYSICAL_NAME = "Roadmap";

    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {

        return super.toPhysicalTableName(convert(logicalName), jdbcEnvironment);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        return super.toPhysicalColumnName(convert(logicalName), jdbcEnvironment);
    }

    private Identifier convert(Identifier logicalName) {
        if(logicalName == null) return logicalName;

        String name = logicalName.getText();
        if(name.indexOf(ROADMAP_CLASS) != -1) name = name.replace(ROADMAP_CLASS, PHYSICAL_NAME);
        else if(name.indexOf(ROADMAP_FIELD) != -1) name = name.replace(ROADMAP_FIELD, PHYSICAL_NAME);

        return new Identifier(name, logicalName.isQuoted());
    }
}
