package com.atm;

import java.io.Serializable;

public class ArchibusStagingLocation implements Serializable {

	@Column(name="ARCHIBUS_LOCID")
	private String archibusLocid;
	
	@Column(name="EM_ID")
	private String emId;
	
	@Column(name="FLOOR_NO")
	private String floorNo;
	
	@Column(name="ROOM")
	private String room;
	
	@Column(name="REGION_NAME")
	private String regionName;
	
	@Column(name="BUILDING")
	private String building;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="ZIPCODE")
	private String zipcode;
	
	@Column(name="SITE_NAME")
	private String siteName;
	
	@Column(name="CITY_NAME")
	private String cityName;
	
	@Column(name="STATE_NAME")
	private String stateName;
	
	@Column(name="COUNTRY_NAME")
	private String countryName;
	
	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name="MODIFIED_DT")
	private String modifiedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="CREATED_DT")
	private String createdDate;
	
	@Column(name="PROCESSING_STATUS")
	private String processingStatus;
	
	@Column(name="PROCESSED_DT")
	private String processedDate;
	
}
