package studentAffairs.seGroup;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.fulib.FulibTools;
import org.junit.Test;

public class TestCallScenario
{
   @Test
   public void testCallScenario()
   {
       SEMan sEMan = new SEMan();
       SEGroup sEGroup = new SEGroup();
       SEStudent alice = new SEStudent()
                .setName("Alice")
                .setStudentId("m42");
       SEClass modeling = new SEClass()
                .setTopic("Modeling");
       sEMan.setRoot(sEGroup);
       alice.setSEGroup(sEGroup);
       modeling.setSEGroup(sEGroup);

       FulibTools.objectDiagrams().dumpPng("doc/studentAffairs/seGroup/registerStudentSetup.png", sEMan);

       Achievement a2 = sEMan.registerStudent(alice, modeling);
       assertThat(alice.getAchievements(), equalTo(a2));
       assertThat(a2.getState(), equalTo("registered"));
       assertThat(modeling.getAchievements(), equalTo(a2));

       FulibTools.objectDiagrams().dumpPng("doc/studentAffairs/seGroup/registerStudentResult.png", sEMan);


   }
}