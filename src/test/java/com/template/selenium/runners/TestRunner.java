package com.template.selenium.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features",
		glue = {"com.template.selenium.steps"},
		stepNotifications = true,
		tags= "(not @ignore) and (not @wip)",
		//tags= "@wip",
		plugin = {
				"pretty",
				"html:target/cucumber-reports/html/CucumberReport.html",
				"json:target/cucumber-reports/cucumber.json"}
)
public class TestRunner {
}