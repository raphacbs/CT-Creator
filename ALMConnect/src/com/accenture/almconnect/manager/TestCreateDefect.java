package com.accenture.almconnect.manager;

import java.util.HashMap;
import java.util.Map;

import com.accenture.almconnect.infrastructure.Entity;
import com.accenture.almconnect.infrastructure.Entity.Fields;
import com.accenture.almconnect.infrastructure.Entity.Fields.Field;
import com.accenture.almconnect.infrastructure.EntityMarshallingUtils;

public class TestCreateDefect {
	
	public static final String HOST = "10.58.176.175";
	public static final String PORT = "8080";
	public static final String USERNAME = "TR358126";
	public static final String PASSWORD = "qwe01poi";
	public static final String DOMAIN = "AUXILIARES";
	public static final String PROJECT = "AUTOM_ACC";
	public static final boolean VERSIONED = true;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String testSetId = "";
		
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("name", "101");
//		map.put("user-template-05", "4");
//		map.put("user-template-01", "4");
//		map.put("severity", "2-Medium");
//		map.put("detected-in-rcyc", "1025");
//		map.put("detected-by", "TR358126");
//		map.put("creation-time", "2016-09-27");
//		map.put("dev-comments", "Defeito aberto via RFT");
//		map.put("user-template-02", "BLL");
//		map.put("user-template-03", "BLL");
//		map.put("user-template-04", "2016-09-27");
//		map.put("user-template-11", "1");
//		map.put("user-template-05", "BLL");
//		map.put("user-template-09", "BLL - IBM");
//		map.put("user-template-01", " ");
//		map.put("status", "New");
//		map.put("description", "Erro ao executar o comando vbj teste");
//		
		ManagerEntity managerEntity = new ManagerEntity(HOST, PORT, DOMAIN, PROJECT, USERNAME, PASSWORD);
		
//		managerEntity.createEntity("defect", map);

//		Entity testSet = managerEntity.getEntity("test-set","101");
//		
//		for (Field field : testSet.getFields().getField()) {
//			System.out.println(field.getName());
//			if(field.getName().equals("cycle-id")){
//				testSetId = field.getName();
//				break;
//			}
//		}		
		
		Map<String, String> mapTestInstace = new HashMap<String, String>();
		mapTestInstace.put("cycle-id", "101");
		mapTestInstace.put("user-template-05", "4");
		mapTestInstace.put("user-template-01", "4");
		String testInstance = managerEntity.getXMLEntity("test-instance",mapTestInstace);
		
		System.out.println("Test Instance: "+testInstance);
		
		String testInstanceId = getFieldValue("id", testInstance);
		
//		Entity testInstanceEntity = managerEntity.getEntity("test-instance",testInstanceId);
		
		
		String testcycleId ="";
		String testId = getFieldValue("test-id", testInstance);
		String cycleId = getFieldValue("cycle-id", testInstance);
		
//		for(Field field : testInstanceEntity.getFields().getField()){
//			switch (field.getName()) {
//			case "test-id":
//				testId = field.getValue().get(0);
//				break;
//				
//			case "cycle-id":
//				cycleId = field.getValue().get(0);
//				break;
//			default:
//				break;
//			}
//		}
		
		Map<String, String> fieldsRun = new HashMap<String, String>();
		
		fieldsRun.put("test-instance", testInstanceId);
		fieldsRun.put("name", "Run_RFT_18_10_2016");
		fieldsRun.put("owner", USERNAME);
		fieldsRun.put("subtype-id", "hp.qc.run.MANUAL");
		fieldsRun.put("status", "Passed");
		fieldsRun.put("cycle-id", cycleId);
		fieldsRun.put("test-id", testId);

		
		System.out.println(managerEntity.createEntity("run", fieldsRun));
		
		
		
	}
	
	private static String getFieldValue(String field, String xml){
		String id = "";
		xml = xml.substring(xml.indexOf("<Field Name=\""+field+"\">"));
		
		xml = xml.substring(xml.indexOf("<Field Name=\""+field+"\">"), xml.indexOf("</Value>"));
		
		id = xml.replace("<Field Name=\""+field+"\"><Value>","");
		
		id = id.trim();
		return id;
	}

}


