package cucumber;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/cucumber/features"}
		,glue={"cucumber.steps"}
	    ,plugin={
	            "json:H:/Carleton/COMP5104/A2/cucumber.json",
	            "junit:H:/Carleton/COMP5104/A2/cucumber.xml"}
		)

public class TestRunner {

}