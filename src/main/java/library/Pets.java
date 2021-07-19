package library;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Pets {
	private static String uri = "https://petstore.swagger.io/v2/";

	public static PetPojo postPet(String name, List<String> photoUrl) throws Exception {
		RestAssured.baseURI = uri;
		PetPojo pets = new PetPojo(name, photoUrl);
		ObjectMapper mapper = new ObjectMapper();
		// serialization
		String json = mapper.writeValueAsString(pets);

		RequestSpecification request = RestAssured.given().log().all();
		request.body(json);
		request.contentType("application/json");
		request.accept("application/json");
		Response response = request.post(uri + "pet");
		// deserialization
		PetPojo petsResponse = mapper.readValue(response.body().asString(), PetPojo.class);
		return petsResponse;
	}

	public static int uploadImage(long petId, String file) throws Exception {
		RestAssured.baseURI = uri;
		RequestSpecification request = RestAssured.given().log().all();
		request.given().multiPart("file", new File(file));
		Response response = request.post(uri + "pet/" + petId + "/uploadImage");
		return response.getStatusCode();
	}

	public static List<PetPojo> findByStatus(String status) throws Exception {
		RestAssured.baseURI = uri;
		ObjectMapper mapper = new ObjectMapper();
		RequestSpecification request = RestAssured.given().log().all();
		request.contentType("application/json");
		request.accept("application/json");
		Response response = request.get(uri + "pet/findByStatus?status=" + status);
		List<PetPojo> petsResponse = Arrays.asList(mapper.readValue(response.body().asString(), PetPojo[].class));
		return petsResponse;
	}
}
