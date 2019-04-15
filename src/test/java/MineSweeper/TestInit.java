package MineSweeper;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.fulib.FulibTools;
import org.junit.Test;

public class TestInit
{
   @Test
   public void testInit()
   {
       MineSweeper ms = new MineSweeper()
                .setId("ms");
       ms.init();

   }
}