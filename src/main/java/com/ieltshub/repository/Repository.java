package com.ieltshub.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * @author tuannd
 * @date 10/03/2016
 * @since 1.0
 */
interface Repository {
    /**
     * get single result
     * @param nativeSql
     * @param parameters
     * @return
     * @since 1.0
     */
    String getSingleResult(StringBuilder nativeSql, Map<? extends String, ? extends Object> parameters);

    /**
     * get single result
     * @param nativeSql
     * @param parameters
     * @return
     * @since 1.0
     */
    String getSingleResult(String nativeSql, Map<? extends String, ? extends Object> parameters);

    /**
     * get single result
     * @param nativeSql
     * @return
     * @since 1.0
     */
    String getSingleResult(StringBuilder nativeSql);

    /**
     * get single result
     * @param nativeSql
     * @return
     * @since 1.0
     */
    String getSingleResult(String nativeSql);

    /**
     * get list entity with parameter
     * @param clazz
     * @param nativeSql
     * @param parameters
     * @return
     * @since 1.0
     */
    <T> List<T> getEntities(Class<T> clazz, String nativeSql, Map<? extends String, ? extends Object> parameters);

    /**
     * get list entity without parameter
     * @param clazz
     * @param nativeSql
     * @return
     * @since 1.0
     */
    <T> List<T> getEntities(Class<T> clazz, String nativeSql);

    /**
     * get list entity with parameter
     * @param clazz
     * @param nativeSql
     * @param parameters
     * @return
     * @since 1.0
     */
    <T> List<T> getEntities(Class<T> clazz, StringBuilder nativeSql, Map<? extends String, ? extends Object> parameters);

    /**
     * get list entity without parameter
     * @param clazz
     * @param nativeSql
     * @return
     * @since 1.0
     */
    <T> List<T> getEntities(Class<T> clazz, StringBuilder nativeSql);

    /**
     * get entity by native sql
     * @param clazz
     * @param nativeSql
     * @param parameters
     * @return
     * @since 1.0
     */
    <T> T getEntity(Class<T> clazz, String nativeSql, Map<? extends String, ? extends Object> parameters);

    /**
     * get entity by native sql
     * @param clazz
     * @param nativeSql
     * @param parameters
     * @return
     * @since 1.0
     */
    <T> T getEntity(Class<T> clazz, StringBuilder nativeSql, Map<? extends String, ? extends Object> parameters);

    /**
     * get custom object by native sql with parameters<br/>
     * <b>Remember to call resultSetExtractor.next() before get data</b>
     *
     * @param nativeSql
     * @param namedParameters
     * @param resultSetExtractor
     * @param <T>
     * @return
     * @since 1.0
     */
    <T> T getObject(String nativeSql, SqlParameterSource namedParameters, ResultSetExtractor<T> resultSetExtractor);

    /**
     * get custom object by native sql<br/>
     * <b>Remember to call resultSetExtractor.next() before get data</b>
     *
     * @param nativeSql
     * @param resultSetExtractor
     * @param <T>
     * @return
     */
    <T> T getObject(String nativeSql, ResultSetExtractor<T> resultSetExtractor);

    /**
     * get custom object by native sql with parameters<br/>
     * <b>Remember to call resultSetExtractor.next() before get data</b>
     *
     * @param nativeSql
     * @param namedParameters
     * @param resultSetExtractor
     * @param <T>
     * @return
     * @since 1.0
     */
    <T> T getObject(StringBuilder nativeSql, SqlParameterSource namedParameters, ResultSetExtractor<T> resultSetExtractor);

    /**
     * get custom object by native sql<br/>
     * <b>Remember to call resultSetExtractor.next() before get data</b>
     *
     * @param nativeSql
     * @param resultSetExtractor
     * @param <T>
     * @return
     * @since 1.0
     */
    <T> T getObject(StringBuilder nativeSql, ResultSetExtractor<T> resultSetExtractor);

    /**
     * get list non-entity object<br/>
     * <b>Not need to call rowMapper.next()</b>
     *
     * @param nativeSql
     * @param namedParameters
     * @param rowMapper
     * @return
     * @since 1.0
     */
    <T> List<T> getObjects(String nativeSql, SqlParameterSource namedParameters, RowMapper<T> rowMapper);

    /**
     * get list non-entity object<br/>
     * <b>Not need to call rowMapper.next()</b>
     *
     * @param nativeSql
     * @param rowMapper
     * @return
     * @since 1.0
     */
    <T> List<T> getObjects(String nativeSql, RowMapper<T> rowMapper);

    /**
     * get list non-entity object<br/>
     * <b>Not need to call rowMapper.next()</b>
     *
     * @param nativeSql
     * @param namedParameters
     * @param rowMapper
     * @return
     * @since 1.0
     */
    <T> List<T> getObjects(StringBuilder nativeSql, SqlParameterSource namedParameters, RowMapper<T> rowMapper);

    /**
     * get list non-entity object<br/>
     * <b>Not need to call rowMapper.next()</b>
     *
     * @param nativeSql
     * @param rowMapper
     * @return
     * @since 1.0
     */
    <T> List<T> getObjects(StringBuilder nativeSql, RowMapper<T> rowMapper);

    /**
     * execute query
     * @param nativeSql
     * @since 1.0
     */
    void executeQuery(StringBuilder nativeSql);

    /**
     * execute query
     * @param nativeSql
     * @param parameters
     * @since 1.0
     */
    void executeQuery(StringBuilder nativeSql, Map<String, Object> parameters);

    /**
     * execute query
     * @param nativeSql
     * @since 1.0
     */
    void executeQuery(String nativeSql);

    /**
     * execute query
     * @param nativeSql
     * @param parameters
     * @since 1.0
     */
    void executeQuery(String nativeSql, Map<String, Object> parameters);
}
