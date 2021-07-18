package org.jhapy.springbootrestgenerator;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Templates {

	public static String getEndpointTemplate(String endpointPackage, String servicePackage, String mapperPackage, String dtoPackage, String domainPackage, String saveQuery, String restServicePrefix, String entityName) {
		String fileTemplate = getFile("templates/endpoint.tmpl");
		fileTemplate = fileTemplate.replace("{{endpointPackage}}", endpointPackage);
		fileTemplate = fileTemplate.replace("{{servicePackage}}", servicePackage);
		fileTemplate = fileTemplate.replace("{{mapperPackage}}", mapperPackage);
		fileTemplate = fileTemplate.replace("{{entityName}}", entityName);
		fileTemplate = fileTemplate.replace("{{domainPackage}}", domainPackage);
		fileTemplate = fileTemplate.replace("{{serviceName}}", entityName.toLowerCase());
		fileTemplate = fileTemplate.replace("{{dtoPackage}}", dtoPackage);
		fileTemplate = fileTemplate.replace("{{saveQuery}}", saveQuery);
		fileTemplate = fileTemplate.replace("{{restServicePrefix}}", restServicePrefix);

		return fileTemplate;
	}

	public static String getServiceTemplate(String servicePackage, String domainPackage, String crudServiceName, String entityName) {
		String fileTemplate = getFile("templates/service.tmpl");
		fileTemplate = fileTemplate.replace("{{servicePackage}}", servicePackage);
		fileTemplate = fileTemplate.replace("{{domainPackage}}", domainPackage);
		fileTemplate = fileTemplate.replace("{{crudServiceName}}", crudServiceName);
		fileTemplate = fileTemplate.replace("{{entityName}}", entityName);

		return fileTemplate;
	}

	public static String getServiceImplTemplate(String servicePackage, String repositoryPackage,String domainPackage, String entityName) {
		String fileTemplate = getFile("templates/serviceImpl.tmpl");
		fileTemplate = fileTemplate.replace("{{servicePackage}}", servicePackage);
		fileTemplate = fileTemplate.replace("{{repositoryPackage}}", repositoryPackage);
		fileTemplate = fileTemplate.replace("{{domainPackage}}", domainPackage);
		fileTemplate = fileTemplate.replace("{{entityName}}", entityName);
		fileTemplate = fileTemplate.replace("{{repositoryName}}", entityName.toLowerCase());

		return fileTemplate;
	}

	public static String getMapperTemplate(String mapperPackage,  String dtoPackage,String domainPackage, String entityName) {
		String fileTemplate = getFile("templates/mapper.tmpl");
		fileTemplate = fileTemplate.replace("{{mapperPackage}}", mapperPackage);
		fileTemplate = fileTemplate.replace("{{dtoPackage}}", dtoPackage);
		fileTemplate = fileTemplate.replace("{{domainPackage}}", domainPackage);
		fileTemplate = fileTemplate.replace("{{entityName}}", entityName);

		return fileTemplate;
	}

	public static String getRepositoryTemplate(String repositoryPackage,String domainPackage, String entityName) {
		String fileTemplate = getFile("templates/repository.tmpl");
		fileTemplate = fileTemplate.replace("{{repositoryPackage}}", repositoryPackage);
		fileTemplate = fileTemplate.replace("{{domainPackage}}", domainPackage);
		fileTemplate = fileTemplate.replace("{{entityName}}", entityName);

		return fileTemplate;
	}

	public static String getClientTemplate(String clientPackage,String remoteServiceName, String restServicePrefix, String dtoPackage, String entityName) {
		String fileTemplate = getFile("templates/client.tmpl");
		fileTemplate = fileTemplate.replace("{{clientPackage}}", clientPackage);
		fileTemplate = fileTemplate.replace("{{remote-service-name}}", remoteServiceName);
		fileTemplate = fileTemplate.replace("{{dtoPackage}}", dtoPackage);
		fileTemplate = fileTemplate.replace("{{serviceName}}", entityName.toLowerCase());
		fileTemplate = fileTemplate.replace("{{restServicePrefix}}", restServicePrefix);
		fileTemplate = fileTemplate.replace("{{entityName}}", entityName);
		return fileTemplate;
	}

	private static String getFile(String fileName) {
		var result = new StringBuilder("");
		var classLoader = Templates.class.getClassLoader();
		var file = new File(classLoader.getResource(fileName).getFile());

		try (var scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();
	}
}
