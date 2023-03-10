package com.abhinav.DoctorAssignment.springbootcucumber.cucumberglue;

import com.abhinav.DoctorAssignment.entities.Doctor;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class CucumberMySteps {

    ResponseEntity<String> lastResponse;

    @When("the client calls endpoint to get all doctors {string}")
    public void whenClientCallsGetAllDoctors(String url) {
        try {
            lastResponse = new RestTemplate().exchange("http://localhost:8081" + url, HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            httpClientErrorException.printStackTrace();
        }
    }

    @Then("response status code is {int}")
    public void thenStatusCodee(int expected) {
        Assertions.assertNotNull(lastResponse);
        Assertions.assertNotNull(lastResponse.getStatusCode());
        assertThat("status code is" + expected, lastResponse.getStatusCodeValue() == expected);
    }

    @When("the client calls endpoint to create doctor {string}")
    public void whenClientCallsCreateDoctor(String url) {
        try {
            lastResponse = new RestTemplate().exchange("http://localhost:8081" + url, HttpMethod.POST,  new HttpEntity<>(Doctor.builder().id(100).build()), String.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            httpClientErrorException.printStackTrace();
        }
    }
}
