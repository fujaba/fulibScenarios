package studentAffairs.seGroup;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.fulib.FulibTools;
import org.junit.Test;

public class TestSEGroupPMScenario
{
   @Test
   public void testSEGroupPMScenario()
   {
       SEGroup sEGroup = new SEGroup();
       sEGroup.setHead("Albert");

       SEStudent alice = new SEStudent()
                .setName("Alice")
                .setStudentId("m42");
       SEStudent bob = new SEStudent()
                .setName("Bob")
                .setStudentId("m23");
       SEClass modeling = new SEClass()
                .setTopic("Modeling")
                .setStart("2019-4");
       SEClass designPattern = new SEClass()
                .setTopic("Design Pattern")
                .setStart("2019-4");
       Assignment partyAppScenarios = new Assignment()
                .setTopic("PartyApp Scenarios")
                .setPoints(10);
       Achievement alicePMDone = new Achievement()
                .setId("alicePMDone");
       Solution easyDone = new Solution()
                .setId("easyDone")
                .setText("42");
       sEGroup.withClasses(modeling, designPattern);
       sEGroup.withStudents(alice, bob);
       partyAppScenarios.setSeClass(modeling);
       partyAppScenarios.withSolutions(easyDone);
       alice.setAchievements(alicePMDone);
       alicePMDone.setSolutions(easyDone);

   }
}