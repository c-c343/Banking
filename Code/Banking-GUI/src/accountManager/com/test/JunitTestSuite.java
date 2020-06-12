package accountManager.com.test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   AccountTest.class,
   CreateAgentTest.class,
   AccountControllerTest.class,
   AgentControllerTest.class,
   AgentControllerFinalTest.class,
   WithDrawControllerTest.class
})

public class JunitTestSuite {   
}  