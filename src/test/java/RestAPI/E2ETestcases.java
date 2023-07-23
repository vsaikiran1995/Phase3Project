package RestAPI;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class E2ETestcases extends ReusableMethods{
	public static int employeeId;
	Response response;
	int numberOfEmployees;
	String employeeName;

	@Test
	public void test() {
		
		//***************get employees***************
	    response = getEmployees();
		System.out.println("Response body is "+response.getBody().asString());
		//validating ResponseCode as 200
		Assert.assertEquals(response.statusCode(), 200);
		//Validating count of employees
		numberOfEmployees = response.jsonPath().getList("employees").size();
		Assert.assertEquals(numberOfEmployees, 3);
		
		System.out.println("===============================");
		
	    //*************post employee*******************
		response = postEmployee("John","75000");
		Assert.assertEquals(response.statusCode(), 201);
		JsonPath jpath = response.jsonPath();
	    employeeId = jpath.get("id");
		System.out.println("id "+employeeId);
		//Validating name coming as John
		employeeName = response.jsonPath().getString("name");
		Assert.assertEquals(employeeName, "John");
		//Validating employee count is 4 now
		response = getEmployees();
		numberOfEmployees = response.jsonPath().getList("employees").size();
		Assert.assertEquals(numberOfEmployees, 4);
		System.out.println(employeeId);
		System.out.println("Response body is "+response.getBody().asString());
		
		System.out.println("=================================");
        //****************update employee*******************
		response = updateEmployee(employeeId,"Tom","85000");
		System.out.println("Response body is "+response.getBody().asString());
		
		//validating ResponseCode as 200
		Assert.assertEquals(response.statusCode(), 200);
		//Validating name coming as Tom
		employeeName = response.jsonPath().getString("name");
		Assert.assertEquals(employeeName, "Tom");
		//Validate John is no longer available
		Assert.assertNotEquals(response.jsonPath().getString("name"), "John");
	    System.out.println("==================================");
		
		// ************Get single employee with id*****************
		
		
		response = getSingleEmployee(employeeId);
		System.out.println("Single employee using Id");
		System.out.println("Response body is "+response.getBody().asString());
		//validating ResponseCode as 200
		Assert.assertEquals(response.statusCode(), 200);
		//validate name as Tom
		employeeName = response.jsonPath().getString("name");
		Assert.assertEquals(employeeName, "Tom");
		
		System.out.println("=======================");
		
		//***************Delete employee with id********************
		
		
		response = deleteEmployee(employeeId);
		Assert.assertEquals(response.statusCode(), 200);
		response = getSingleEmployee(employeeId);
		Assert.assertNotEquals(response.jsonPath().getString("name"), "Tom");
		
		System.out.println("===========employee delete successful===================");
		
		//*************Get Single employee with empId**************
		response = getSingleEmployee(employeeId);
		Assert.assertEquals(response.statusCode(), 404);
		System.out.println("get single employee assertion is verified");
		
		// ****************Get all employees**************
		response = getEmployees();
		Assert.assertEquals(response.statusCode(), 200);
		numberOfEmployees = response.jsonPath().getList("employees").size();
		Assert.assertEquals(numberOfEmployees,3);
		
		System.out.println("Successfully verified numberOfEmoloyees after CRUD operations");
		
	}

	
}
