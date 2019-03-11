package studentAffairs.seGroup;

import org.fulib.FulibTools;
import org.junit.Test;

public class TestSEGroupDPScenario
{
   @Test
   public void testSEGroupDPScenario()
   {
       SEStudent alice = new SEStudent()
                .setName("Alice")
                .setStudentId("m42");

       SEStudent bob = new SEStudent()
                .setName("Bob")
                .setStudentId("m23");

       SEGroup sEGroup = new SEGroup();

       sEGroup.setHead("Albert");

       SEClass modeling = new SEClass()
                .setTopic("OO Modeling")
                .setStart("2019-4");

       SEClass pattern = new SEClass()
                .setTopic("design pattern")
                .setStart("2019-4");

       Assignment composite = new Assignment()
                .setTopic("Composite")
                .setPoints(12);

       sEGroup.withStudents(alice, bob);
       sEGroup.withClasses(modeling, pattern);
       composite.setSeClass(pattern);

   }
}