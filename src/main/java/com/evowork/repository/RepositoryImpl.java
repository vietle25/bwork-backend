package com.evowork.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * NotificationSchedule repository
 * @author tuannd
 * @date 15/10/2016
 * @since 1.0
 */
@org.springframework.stereotype.Repository
class RepositoryImpl implements Repository {
	@PersistenceContext
	protected EntityManager entityManager;

	protected NamedParameterJdbcTemplate jdbcTemplate;

	static private Pattern queryParameterPattern = Pattern.compile("(\\[(\\w+)\\])?(\\w+)");

	@Autowired
	protected void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public String getSingleResult(StringBuilder nativeSql, Map<? extends String, ? extends Object> parameters) {
		return getSingleResult(nativeSql.toString(), parameters);
	}

	@Override
	public String getSingleResult(String nativeSql, Map<? extends String, ?> parameters) {
		Query query = prepareQuery(nativeSql, parameters);
		Object result = query.getSingleResult();
		return result != null ? result.toString() : null;
	}

	@Override
	public String getSingleResult(StringBuilder nativeSql) {
		return getSingleResult(nativeSql, null);
	}

	@Override
	public String getSingleResult(String nativeSql) {
		return getSingleResult(nativeSql, null);
	}

	@Override
	public <T> List<T> getEntities(Class<T> clazz, String nativeSql, Map<? extends String, ? extends Object> parameters) {
		Query query = prepareQuery(clazz, nativeSql, parameters);
		return query.getResultList();
	}

	private <T> Query prepareQuery(Class<T> clazz, String nativeSql, Map<? extends String, ? extends Object> parameters) {
		Query query = clazz != null ? createQuery(clazz, nativeSql) : createQuery(nativeSql);
		if (parameters != null) {
			fillQueryParameter(query, parameters);
		}
		return query;
	}

	private <T> Query prepareQuery(String nativeSql, Map<? extends String, ? extends Object> parameters) {
		return prepareQuery(null, nativeSql, parameters);
	}

	private void fillQueryParameter(Query query, Map<? extends String, ? extends Object> parameters) {
		parameters.forEach((key, value) -> {
            /*Matcher matcher = queryParameterPattern.matcher(key);
            if (matcher.find() && StringUtility.isNotEmpty(matcher.group(1))) {
                String dataType = matcher.group(2);
                String actualKey = matcher.group(3);
                switch (dataType) {
                    case "date":
                        query.setParameter(actualKey, (Date) value, TemporalType.DATE);
                        break;
                    case "timestamp":
                        query.setParameter(actualKey, (Timestamp) value, TemporalType.TIMESTAMP);
                        break;
                }
            } else {
                query.setParameter(key, value);
            }*/
			query.setParameter(key, value);
		});
	}

	@Override
	public <T> List<T> getEntities(Class<T> clazz, String nativeSql) {
		Query query = createQuery(clazz, nativeSql);
		return query.getResultList();
	}

	@Override
	public <T> List<T> getEntities(Class<T> clazz, StringBuilder nativeSql, Map<? extends String, ?> parameters) {
		return this.getEntities(clazz, nativeSql.toString(), parameters);
	}

	@Override
	public <T> List<T> getEntities(Class<T> clazz, StringBuilder nativeSql) {
		return this.getEntities(clazz, nativeSql.toString());
	}

	private <T> Query createQuery(Class<T> clazz, String nativeSql) {
		return entityManager.createNativeQuery(nativeSql, clazz);
	}

	private <T> Query createQuery(String nativeSql) {
		return entityManager.createNativeQuery(nativeSql);
	}

	@Override
	public <T> T getEntity(Class<T> clazz, String nativeSql, Map<? extends String, ?> parameters) {
		Query query = prepareQuery(clazz, nativeSql, parameters);
		List results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			return (T) results.get(0);
		}
		return null;
	}

	@Override
	public <T> T getEntity(Class<T> clazz, StringBuilder nativeSql, Map<? extends String, ?> parameters) {
		return this.getEntity(clazz, nativeSql.toString(), parameters);
	}

	@Override
	public <T> T getObject(String nativeSql, SqlParameterSource namedParameters, ResultSetExtractor<T> resultSetExtractor) {
		return this.jdbcTemplate.query(nativeSql, namedParameters, resultSetExtractor);
	}

	@Override
	public <T> T getObject(String nativeSql, ResultSetExtractor<T> resultSetExtractor) {
		return this.jdbcTemplate.query(nativeSql, resultSetExtractor);
	}

	@Override
	public <T> T getObject(StringBuilder nativeSql, SqlParameterSource namedParameters, ResultSetExtractor<T> resultSetExtractor) {
		return getObject(nativeSql.toString(), namedParameters, resultSetExtractor);
	}

	@Override
	public <T> T getObject(StringBuilder nativeSql, ResultSetExtractor<T> resultSetExtractor) {
		return getObject(nativeSql.toString(), resultSetExtractor);
	}

	@Override
	public <T> List<T> getObjects(String nativeSql, SqlParameterSource namedParameters, RowMapper<T> rowMapper) {
		return this.jdbcTemplate.query(nativeSql, namedParameters, rowMapper);
	}

	@Override
	public <T> List<T> getObjects(String nativeSql, RowMapper<T> rowMapper) {
		return jdbcTemplate.query(nativeSql, rowMapper);
	}

	@Override
	public <T> List<T> getObjects(StringBuilder nativeSql, SqlParameterSource namedParameters, RowMapper<T> rowMapper) {
		return this.getObjects(nativeSql.toString(), namedParameters, rowMapper);
	}

	@Override
	public <T> List<T> getObjects(StringBuilder nativeSql, RowMapper<T> rowMapper) {
		return this.getObjects(nativeSql.toString(), rowMapper);
	}

	@Override
	public void executeQuery(StringBuilder nativeSql) {
		this.executeQuery(nativeSql.toString(), null);
	}

	@Override
	public void executeQuery(StringBuilder nativeSql, Map<String, Object> parameters) {
		this.executeQuery(nativeSql.toString(), parameters);
	}

	@Override
	public void executeQuery(String nativeSql) {
		this.executeQuery(nativeSql, null);
	}

	@Override
	public void executeQuery(String nativeSql, Map<String, Object> parameters) {
		this.jdbcTemplate.update(nativeSql, parameters != null ? parameters : new HashMap<String, Object>());
	}
}