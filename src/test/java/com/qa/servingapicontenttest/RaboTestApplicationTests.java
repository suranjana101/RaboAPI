
package com.qa.servingapicontenttest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.Assert.*;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.qa.servingapicontent.RestAPIHelperClass;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import io.restassured.response.Response;
import java.io.File;
import java.util.HashMap;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * 
 * @author suranjana bora
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootConfiguration
public class RaboTestApplicationTests extends RestAPIHelperClass {
	ClassLoader classLoader = getClass().getClassLoader();
	private String ServerUrl = "http://localhost:";
	private final String CONTENT_TYPE = "application/json";
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

	@Test
	public void contextLoads() {
	}

	@Description("Logs employee into the system and verify the api  response with valid and invalid inputs")
	@ParameterizedTest
	@CsvSource({ "/v2/employee/login,200,successful operation,employee1,pwd",
			"/v2/employee/login,400,Invalid employeename/password supplied,11,pwd" })
	public void get_Login(String path, int statuscode, String message, String username, String password)
			throws Exception {
		wireMockRule.start();
		MappingBuilder mappingBuilder = get(
				urlEqualTo("/v2/employee/login?password=" + password + "&employeename=" + username));

		/* 1. Stub the endpoint */

		wireMockRule.stubFor(mappingBuilder.willReturn(
				aResponse().withStatus(statuscode).withHeader("Content-Type", CONTENT_TYPE).withBody(message)));

		/* 2. Make a request */

		HashMap<String, String> queryparams = new HashMap<String, String>();
		queryparams.put("employeename", username);
		queryparams.put("password", password);
		Response response = getHttpRestCallwithQueryParam(ServerUrl + wireMockRule.port() + path, CONTENT_TYPE,
				queryparams);
		System.out.println("Response is " + response.getBody().asString());

		/* 3. Verify */
		assertNotNull(response);
		assertEquals(response.getStatusCode(), statuscode);
		assertEquals(response.getBody().asString(), message);
		wireMockRule.stop();

	}

	@Description("Create employee with valid and invalid inputs and verify the api response ")
	@ParameterizedTest
	@CsvSource({ "/v2/employee,200,json/post_createemployee_response.json,employee1",
			"/v2/employee,400,json/post_createemployee_response.json,3443" })
	public void post_CreateEmployeeName(String path, int statuscode, String srcFile, String name) throws Exception {
		wireMockRule.start();
		File file = new File(classLoader.getResource(srcFile).getFile());
		String absolutePath = file.getAbsolutePath();
		String jsondata = readFileAsString(absolutePath);
		JSONObject modifiedjson = new JSONObject(jsondata);
		modifiedjson.remove("employeename");
		modifiedjson.put("employeename", name);

		/* 1. Stub the endpoint */
		wireMockRule.stubFor(post(urlEqualTo(path)).willReturn(aResponse().withStatus(statuscode)
				.withHeader("Content-Type", CONTENT_TYPE).withBody(modifiedjson.toString())));

		/* 2. Make a request */
		Response response = postHttpRestCall(ServerUrl + wireMockRule.port() + path, CONTENT_TYPE,
				modifiedjson.toString());
		System.out.println("Response is " + response.getBody().asString());

		/* 3. Verify */
		assertNotNull(response);
		assertEquals(response.getStatusCode(), statuscode);
		assertEquals(getHeaderValue(response, "Content-Type"), CONTENT_TYPE);
		assertEquals(modifiedjson.toString(), response.getBody().asString());
		wireMockRule.stop();

	}

	@Description("Get employee by employee name by giving valid and invalid inputs and verify the api response ")
	@ParameterizedTest
	@CsvSource({ "/v2/employee/,200,json/get_employeename_response.json,6,emp",
			"/v2/employee/,400,json/get_employeename_response.json,4,400",
			"/v2/employee1,404,json/get_employeename_response.json,4, " })
	public void get_EmployeeName(String path, int statuscode, String srcFile, int headerCount, String name)
			throws Exception {
		wireMockRule.start();
		File file = new File(classLoader.getResource(srcFile).getFile());
		String absolutePath = file.getAbsolutePath();
		String jsondata = readFileAsString(absolutePath);
		JSONObject modifiedjson = new JSONObject(jsondata);
		modifiedjson.remove("employeename");
		modifiedjson.put("employeename", name);

		/* 1. Stub the endpoint */
		wireMockRule.stubFor(get(urlPathMatching(path + name)).willReturn(aResponse().withStatus(statuscode)
				.withHeader("Content-Type", CONTENT_TYPE).withBody(modifiedjson.toString())));

		/* 2. Make a request */
		Response response = getHttpRestCall(ServerUrl + wireMockRule.port() + path + name, CONTENT_TYPE);
		System.out.println("Response is " + response.getBody().asString());

		/* 3. Verify */
		assertNotNull(response);
		assertEquals(response.getStatusCode(), statuscode);
		assertEquals(getHeaderValue(response, "Content-Type"), CONTENT_TYPE);
		assertEquals(getHeaderCount(response), headerCount);
		assertEquals(modifiedjson.toString(), response.getBody().asString());
		wireMockRule.stop();

	}

	@Description("Updated Employee with valid and invalid inputs and verify the api response ")
	@ParameterizedTest
	@CsvSource({ "/v2/employee/,200,json/post_createemployee_response.json,employee1",
			"/v2/employee/,400,json/get_employeename_response.json,404,employee400",
			"/v2/employee1,404,json/get_employeename_response.json,4," })
	public void put_UpdateEmployeeName(String path, int statuscode, String srcFile, String name) throws Exception {
		wireMockRule.start();
		File file = new File(classLoader.getResource(srcFile).getFile());
		String absolutePath = file.getAbsolutePath();
		String jsondata = readFileAsString(absolutePath);
		JSONObject modifiedjson = new JSONObject(jsondata);
		modifiedjson.remove("employeename");
		modifiedjson.put("employeename", name);

		/* 1. Stub the endpoint */
		wireMockRule.stubFor(put(urlPathMatching(path + name)).willReturn(aResponse().withStatus(statuscode)
				.withHeader("Content-Type", CONTENT_TYPE).withBody(modifiedjson.toString())));

		/* 2. Make a request */
		Response response = putHttpRestCall(ServerUrl + wireMockRule.port() + path + name, CONTENT_TYPE,
				modifiedjson.toString());
		System.out.println("Response is " + response.getBody().asString());

		/* 3. Verify */
		assertNotNull(response);
		assertEquals(response.getStatusCode(), statuscode);
		assertEquals(getHeaderValue(response, "Content-Type"), CONTENT_TYPE);
		assertEquals(modifiedjson.toString(), response.getBody().asString());
		wireMockRule.stop();

	}

	@Description("Delete Employee by adding  valid and invalid inputs and verify the api response ")
	@ParameterizedTest
	@CsvSource({ "/v2/employee/,200,employee11,Success operation", "/v2/employee/,400,58,Invalid employeename supplied",
			"/v2/employee/,404,,employee not found" })

	public void delete_DeleteEmployeeName(String path, int statuscode, String name, String message) throws Exception {
		wireMockRule.start();

		/* 1. Stub the endpoint */
		wireMockRule.stubFor(delete(urlPathMatching(path + name)).willReturn(
				aResponse().withStatus(statuscode).withHeader("Content-Type", CONTENT_TYPE).withBody(message)));

		/* 2. Make a request */
		Response response = deleteHttpRestCall(ServerUrl + wireMockRule.port() + path + name, CONTENT_TYPE);
		System.out.println("Response is " + response.getBody().asString());

		/* 3. Verify */
		assertNotNull(response);
		assertEquals(response.getStatusCode(), statuscode);
		assertEquals(getHeaderValue(response, "Content-Type"), CONTENT_TYPE);
		assertEquals(message, response.getBody().asString());
		wireMockRule.stop();

	}

}
