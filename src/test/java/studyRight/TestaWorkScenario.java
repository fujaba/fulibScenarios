package studyRight;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.fulib.FulibTools;
import org.junit.Test;

public class TestaWorkScenario
{
   @Test
   public void testaWorkScenario()
   {
       StudyRight studyRight = new StudyRight();
       Student carli = studyRight.init();
       carli.work();

   }
}