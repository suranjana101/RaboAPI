package com.qa.servingapicontent;

import java.util.HashMap;
import java.util.List;
import io.restassured.RestAssured;
import java.nio.file.*;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * 
 * @author suranjana bora
 *
 */
public class RestAPIHelperClass {
	static RequestSpecification request;
	Response response;

	/**
	 * GET METHOD- Getting details
	 * 
	 * @param URL
	 * @param contentType
	 * @param token
	 * @param log
	 * @param basepath
	 * @return
	 */
	public static Response getHttpRestCall(String BASEURL, String contentType) {
		RequestSpecification request = RestAssured.given();
		Response resp = request.contentType(contentType).when().get(BASEURL);
		return resp;
	}

	/**
	 * DELETE METHOD -Deleting details
	 * 
	 * @param BASEURL
	 * @param contentType
	 * @return
	 */

	public static Response deleteHttpRestCall(String BASEURL, String contentType) {
		RequestSpecification request = RestAssured.given();
		Response resp = request.contentType(contentType).when().delete(BASEURL);
		return resp;
	}

	/**
	 * POST METHOD-Post details
	 * 
	 * @param BASEURL
	 * @param contentType
	 * @param body
	 * @return
	 */

	public static Response postHttpRestCall(String BASEURL, String contentType, String body) {
		RequestSpecification request = RestAssured.given();
		Response resp = request.contentType(contentType).body(body).when().post(BASEURL);
		return resp;
	}

	/**
	 * PUT METHOD-Put details
	 * 
	 * @param BASEURL
	 * @param contentType
	 * @param body
	 * @return
	 */
	public static Response putHttpRestCall(String BASEURL, String contentType, String body) {
		RequestSpecification request = RestAssured.given();
		Response resp = request.contentType(contentType).body(body).when().put(BASEURL);
		return resp;
	}

	/**
	 * this method used to get rest call using query param
	 * 
	 * @param URL
	 * @param contentType
	 * @param token
	 * @param log
	 * @param basepath
	 * @param queryParams
	 * @return
	 */
	public static Response getHttpRestCallwithQueryParam(String URL, String contentType,
			HashMap<String, String> queryParams) {
		RequestSpecification request = RestAssured.given();
		Response resp = request.contentType(contentType).when().log().all().queryParams(queryParams).get(URL);
		return resp;
	}

	// generic method for response
	/**
	 * This method is used to get status code
	 * 
	 * @param response
	 * @return
	 */
	public static int getStatusCode(Response response) {
		return response.getStatusCode();
	}

	/**
	 * this method is used to get header value
	 * 
	 * @param response
	 * @param headerName
	 * @return
	 */
	public static String getHeaderValue(Response response, String headerName) {
		return response.getHeader(headerName);
	}

	/**
	 * this method is used to get header count
	 * 
	 * @param response
	 * @return
	 */
	public int getHeaderCount(Response response) {
		Headers headers = response.getHeaders();
		return headers.size();
	}

	/**
	 * this method get header response
	 * 
	 * @param response
	 * @return
	 */
	public static List<Header> getresponseHeaders(Response response) {
		Headers headers = response.getHeaders();
		List<Header> headerlist = headers.asList();
		return headerlist;
	}

	/**
	 * This method will give preety response
	 * 
	 * @param response
	 */

	public static void getResponsePrint(Response response) {
		response.prettyPrint();
	}

	/**
	 * this method will extract data from JSON by passing response
	 * 
	 * @param response
	 * @return
	 */
	public static JsonPath getJsonPath(Response response) {
		return response.jsonPath();
	}

	/**
	 * Reading the file as a string - method
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}

}
