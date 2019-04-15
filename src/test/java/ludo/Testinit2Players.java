package ludo;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.fulib.FulibTools;
import org.junit.Test;

public class Testinit2Players
{
   @Test
   public void testinit2Players()
   {
       Ludo ludo = new Ludo();
       ludo.init();

   }
}