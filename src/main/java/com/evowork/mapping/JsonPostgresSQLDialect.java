package com.evowork.mapping;

import org.hibernate.dialect.PostgreSQL9Dialect;

import java.sql.Types;

public class JsonPostgresSQLDialect extends PostgreSQL9Dialect {

    public JsonPostgresSQLDialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "json");
    }
}
