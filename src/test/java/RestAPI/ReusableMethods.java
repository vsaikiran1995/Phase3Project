package RestAPI;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ReusableMethods {

    String URL = "http://localhost:3000/employees/";
	Response response;
	
	

	public Response getEmployees() {
		Response response = RestAssured.given()
				                       .baseUri(URL)
				                       .when()
				                       .get();
		
		return response;
	}

	public Response postEmployee(String name, String salary) {
		HashMap<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("name", name);
		requestBody.put("salary", salary);
		
		Response response = RestAssured.given()
				                       .baseUri(URL)
				                       .when()
				                       .contentType(ContentType.JSON)
				                       .accept(ContentType.JSON)
				                       .body(requestBody).post();
		
		return response;
	}
	
	public Response updateEmployee(int id,String name,String salary) {
		HashMap<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("name", name);
		requestBody.put("salary", salary);
		Response response = RestAssured.given()
				                       .baseUri(URL+id)
				                       .when()
				                       .contentType(ContentType.JSON)
				                       .accept(ContentType.JSON)
				                       .body(requestBody).put();
		
		return response;
		
	}
	
	public Response getSingleEmployee(int empId) {
		RestAssured.baseURI = URL+empId;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		return response;
		
	}
	
	public Response deleteEmployee(int empId) {
		RestAssured.baseURI = URL+empId;
		RequestSpecification request = RestAssured.given();
		Response response = request.delete();
		return response;
	}
}
