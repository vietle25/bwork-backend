package com.ieltshub.entity;

import com.ieltshub.enumeration.StatusType;
import com.ieltshub.enumeration.converter.StatusTypeConverter;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "AppConfig")
@TypeDefs({
		@TypeDef(
				name="string-array",
				typeClass = StringArrayType.class
		)
})
public class AppConfig {
	
	@Id
	@SequenceGenerator(name = "app_config_id_seq", sequenceName = "app_config_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_config_id_seq")
	@Column(name = "id", updatable = false)
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "for_web")
	private Boolean forWeb;
	
	@Column(name = "for_mobile")
	private Boolean forMobile;

	@Column(name = "numeric_value")
	private Double numericValue;
	
	@Column(name = "text_value")
	private String textValue;

	@Column(name = "status")
	@Convert(converter = StatusTypeConverter.class)
	private StatusType status;
	
	@Column(name = "date_time_wotz_value")
	private Timestamp dateTimeWotzValue;
	
	@Column(name = "date_time_value")
	private Timestamp dateTimeValue;

	@Type(type = "string-array")
	@Column(name="text_arr_value", columnDefinition = "text[]")
	private String[] textArrValue;

	@ManyToOne()
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getForWeb() {
		return forWeb;
	}

	public void setForWeb(Boolean forWeb) {
		this.forWeb = forWeb;
	}

	public Boolean getForMobile() {
		return forMobile;
	}

	public void setForMobile(Boolean forMobile) {
		this.forMobile = forMobile;
	}

	public Double getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(Double numericValue) {
		this.numericValue = numericValue;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public Timestamp getDateTimeWotzValue() {
		return dateTimeWotzValue;
	}

	public void setDateTimeWotzValue(Timestamp dateTimeWotzValue) {
		this.dateTimeWotzValue = dateTimeWotzValue;
	}

	public Timestamp getDateTimeValue() {
		return dateTimeValue;
	}

	public void setDateTimeValue(Timestamp dateTimeValue) {
		this.dateTimeValue = dateTimeValue;
	}

	public String[] getTextArrValue() {
		return textArrValue;
	}

	public void setTextArrValue(String[] textArrValue) {
		this.textArrValue = textArrValue;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
