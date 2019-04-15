package studyRight;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.fulib.FulibTools;
import org.junit.Test;

public class TestinitScenario
{
   @Test
   public void testinitScenario()
   {
       StudyRight studyRight = new StudyRight();
       Student carli = studyRight.init();
       assertThat(carli.getMotivation(), equalTo((double) 65));

       FulibTools.objectDiagrams().dumpPng("doc/studyRight/studyRightInit.png", carli);


   }
}