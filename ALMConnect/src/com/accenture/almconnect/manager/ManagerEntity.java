package com.accenture.almconnect.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.accenture.almconnect.infrastructure.Assert;
import com.accenture.almconnect.infrastructure.Constants;
import com.accenture.almconnect.infrastructure.Entity;
import com.accenture.almconnect.infrastructure.EntityMarshallingUtils;
import com.accenture.almconnect.infrastructure.Response;
import com.accenture.almconnect.infrastructure.RestConnector;

public class ManagerEntity {

	private String host;
	private String port;
	private String domain;
	private String project;
	private String password;
	private String username;
	private RestConnector con;
	private AuthenticateLoginLogoutExample login;

	public ManagerEntity(String host, String port, String domain, String project, String username, String password) {
		this.host = host;
		this.port = port;
		this.domain = domain;
		this.project = project;
		this.password = password;
		this.username = username;

		con = RestConnector.getInstance().init(new HashMap<String, String>(), "http://" + host + ":" + port + "/qcbin",
				domain, project);

	}

	public String createEntity(String entityType, Map<String, String> fields) throws Exception {

		login();

		String xmlPost = getEntityToPostXml(entityType, fields);

		String entityUrl = con.buildEntityCollectionUrl(entityType);

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");

		// As can be seen in the implementation below, creating an entity
		// is simply posting its xml into the correct collection.
		Response response = con.httpPost(entityUrl, xmlPost.getBytes(), requestHeaders);
		System.out.println("Reponce serve: " + response.toString());
		Exception failure = response.getFailure();
		if (failure != null) {
			throw failure;
		}

		/*
		 * Note that we also get the xml of the newly created entity. at the
		 * same time we get the url where it was created in a location response
		 * header.
		 */
		response.getResponseHeaders().get("Location").iterator().next();

		logout();

		return entityUrl;

	}

	public Entity getEntity(String entityType, String id) throws Exception {

		login();

		String urlEntity = con.buildEntityCollectionUrl(entityType);

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");

		String listFromCollectionAsXml = "";

		String urlOfResourceWeWantToRead = con.buildUrl("rest/server");

		Response serverResponse = con.httpGet(urlOfResourceWeWantToRead, null, requestHeaders);

		serverResponse = con.httpGet(urlEntity + "/"+id, null, requestHeaders);

		listFromCollectionAsXml = serverResponse.toString();

		Entity entity = EntityMarshallingUtils.marshal(Entity.class, listFromCollectionAsXml);

		logout();

		return entity;
	}

	public String getXMLEntity(String entityType, Map<String, String> fields) throws Exception {
		login();

		String urlEntity = con.buildEntityCollectionUrl(entityType);

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");

		StringBuilder query = new StringBuilder();

		boolean first = true;
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			if (first) {
				if (isDigit(entry.getValue())) {
					query.append("query={" + entry.getKey() + "[" + entry.getValue() + "]");
				} else {
					query.append("query={" + entry.getKey() + "['" + entry.getValue() + "']");
				}

				first = false;
			} else {
				if (isDigit(entry.getValue())) {
					query.append(";" + entry.getKey() + "[" + entry.getValue() + "]");
				} else {
					query.append(";" + entry.getKey() + "['" + entry.getValue() + "']");
				}

			}
			query.append("}");
		}

		query.append("&page-size=1");

		System.out.println(query);

		String listFromCollectionAsXml = con.httpGet(urlEntity, query.toString(), requestHeaders).toString();

		logout();

		return listFromCollectionAsXml;
	}

	public boolean login() throws Exception {
		login = new AuthenticateLoginLogoutExample();

		boolean loginResult = login.login(username, password);
		Assert.assertTrue("failed to login", loginResult);
		return loginResult;
	}

	public boolean logout() throws Exception {
		if (login != null) {
			return login.logout();
		} else {
			return false;
		}

	}

	private String updadeEntity() {
		return null;
	}

	private String getEntityToPostXml(String entityType, Map<String, String> fields) {
		// + "</Fields>" + "</Entity>";
		String entityToPostFormat = Constants.entityToPostXml;
		entityToPostFormat = entityToPostFormat.replace("entityType", entityType);

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			entityToPostFormat = entityToPostFormat + Constants.generateFieldXml(entry.getKey(), entry.getValue());
			System.out.println(entry.getKey() + "/" + entry.getValue());
		}

		entityToPostFormat = entityToPostFormat + "</Fields>" + "</Entity>";

		return entityToPostFormat;
	}

	private boolean isDigit(String s) {
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (ch < 48 || ch > 57)
				return false;
		}
		return true;
	}
        
        
        public void create(){
            
        }
}
