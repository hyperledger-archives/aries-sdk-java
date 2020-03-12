package com.hyperledger.aries;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@ContextConfiguration(classes = {AriesCloudAgentJava.class})
@SpringBootTest(webEnvironment= DEFINED_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test.properties")
public class AriesTestSteps {
    @LocalServerPort
    private int randomServerPort;

    private static int staticRandomServerPort;

    @Before
    public void setup(){
        staticRandomServerPort = this.randomServerPort;
    }

    @After
    public void cleanup() {
    }

    @Given("^The web module has compiled properly$")
    public void the_web_module_has_compiled_properly() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^Aries Web SDK Spring Boot Application should be up$")
    public void aries_Web_SDK_Spring_Boot_Application_should_be_up() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }


}
