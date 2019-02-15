package uniks.scenarios.studyright;

import org.junit.Test;

public class TestDoAssignments
{
   @Test
   public void testScenario1()
   {
       Student Karli = new Student()
                .setName("Karli")
                .setMotivationpoints(42.0)
                .setCreditpoints(0.0);

       Room themathroom = new Room()
                .setTopic("math")
                .setCredits(23);


   }
}