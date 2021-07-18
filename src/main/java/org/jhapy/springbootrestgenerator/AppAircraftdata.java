package org.jhapy.springbootrestgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AppAircraftdata {

	private final static String sourceFolder = "/Users/clavaud/Development/DevArea/eFlightMgt/eflightmgt-app-referencedata-server/src/main/java/";
	private final static String mainPackage 			= "com/eflightmgt/referencedata";

	private final static String remoteServiceName = "app-aircraftmgt-server";
	private final static String clientPackage 		= "com.eflightmgt.frontend.client.aircraftdata";

	private final static String mapperPackage 		= "com.eflightmgt.referencedata.converter.aircraftdata";
	private final static String endpointPackage 	= "com.eflightmgt.referencedata.endpoint.aircraftdata";
	private final static String servicePackage 		= "com.eflightmgt.referencedata.service.aircraftdata";
	private final static String repositoryPackage = "com.eflightmgt.referencedata.repository.relationaldb.aircraftdata";
	private final static String domainPackage 		= "com.eflightmgt.referencedata.domain.relationaldb.aircraftdata";

	private final static String dtoPackage 				= "com.eflightmgt.dto.domain.aircraftdata";

	private final static String restServicePrefix = "/api/aircraftdata";

	//private final static String crudServiceName = "GenericCrudRelationalService";
	private final static String crudServiceName 	= "CrudRelationalService";
	//private final static String saveQuery = "GenericSaveQuery";
	private final static String saveQuery 				= "SaveQuery";


	private static List<String> entityNames = new ArrayList<String>();

	public static void main(String[] args) {
		// Uncomment this line for adding manuel model name
		// entityNames.add("MyModel");

		// Use this method for auto parsing model classes
		parseEntityNamesFromFolder("domain/relationaldb/aircraftdata");
		parseEntities("/aircraftdata");
	}

	private static void parseEntityNamesFromFolder(String modelFolder) {
		try {
			var dir = new File(sourceFolder.concat(mainPackage).concat(File.separator + modelFolder));
			File[] directoryListing = dir.listFiles();
			if (directoryListing != null) {
				for (File child : directoryListing) {
					if ( child.isFile() ) {
						String temp = child.getName().replace(".java", "");
						entityNames.add(temp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseEntities(String subDir) {
		for (String entityName : entityNames) {
			String mapperTemplate = Templates.getMapperTemplate(mapperPackage, dtoPackage,domainPackage, entityName);
			createFile("converter" + subDir, entityName + "Converter.java", mapperTemplate);

			String repositoryTemplate = Templates.getRepositoryTemplate(repositoryPackage,domainPackage, entityName);
			createFile("repository/relationaldb" + subDir, entityName + "Repository.java", repositoryTemplate);

			String serviceTemplate = Templates.getServiceTemplate(servicePackage, domainPackage, crudServiceName, entityName);
			createFile("service" + subDir, entityName + "Service.java", serviceTemplate);

			String serviceImplTemplate = Templates.getServiceImplTemplate(servicePackage, repositoryPackage,domainPackage, entityName);
			createFile("service" + subDir, entityName + "ServiceImpl.java", serviceImplTemplate);

			String endpointTemplate = Templates.getEndpointTemplate(endpointPackage, servicePackage,mapperPackage, dtoPackage, domainPackage, saveQuery, restServicePrefix,entityName);
			createFile("endpoint" + subDir, entityName + "Endpoint.java", endpointTemplate);

			// String clientTemplate = Templates.getClientTemplate(clientPackage, remoteServiceName, restServicePrefix,dtoPackage, entityName);
			// createFile("client" + subDir, entityName + "Service.java", clientTemplate);

		}
	}

	private static void createFile(String folderName, String fileName, String fileTemplate) {
		PrintWriter writer;

		try {
			writer = new PrintWriter(sourceFolder + mainPackage + "/" + folderName + "/" + fileName, "UTF-8");
			writer.print(fileTemplate);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
