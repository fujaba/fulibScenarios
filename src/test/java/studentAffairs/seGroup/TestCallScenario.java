package studentAffairs.seGroup;

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

       sEMan.registerStudent(alice, modeling);

   }
}