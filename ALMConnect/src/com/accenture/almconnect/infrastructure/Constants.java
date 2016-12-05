package com.accenture.almconnect.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.accenture.almconnect.infrastructure.EntityDescriptor;

public class Constants {

	private Constants() {
	}

	public static final String HOST = "10.58.176.175";
	public static final String PORT = "8080";
	public static final String USERNAME = "TR358126";
	public static final String PASSWORD = "qwe01poi";
	public static final String DOMAIN = "AUXILIARES";
	public static final String PROJECT = "AUTOM_ACC";
	public static final boolean VERSIONED = true;

	/**
	 * Supports running tests correctly on both versioned and non-versioned
	 * projects.
	 * 
	 * @return true if entities of entityType support versioning
	 */
	public static boolean isVersioned(String entityType, final String domain, final String project) throws Exception {

		RestConnector con = RestConnector.getInstance();
		String descriptorUrl = con
				.buildUrl("rest/domains/" + domain + "/projects/" + project + "/customization/entities/" + entityType);

		String descriptorXml = con.httpGet(descriptorUrl, null, null).toString();
		EntityDescriptor descriptor = EntityMarshallingUtils.marshal(EntityDescriptor.class, descriptorXml);

		boolean isVersioned = descriptor.getSupportsVC().getValue();

		return isVersioned;
	}

	public static String generateFieldXml(String field, String value) {
		return "<Field Name=\"" + field + "\"><Value>" + value + "</Value></Field>";
	}

	/**
	 * This string used to create new "requirement" type entities.
	 */
	public static final String entityToPostName = "req" + Double.toHexString(Math.random());
	public static final String entityToPostFieldName = "type-id";
	public static final String entityToPostFieldValue = "1";

	public static final  String entityToPostFormat = "<Entity Type=\"entityType\">" + "<Fields>";
			

	public static final String entityToPostXml = String.format(entityToPostFormat, "name", entityToPostName,
			entityToPostFieldName, entityToPostFieldValue);

	public static final CharSequence entityToPostFieldXml = generateFieldXml(Constants.entityToPostFieldName,
			Constants.entityToPostFieldValue);
	
	
	
	
}