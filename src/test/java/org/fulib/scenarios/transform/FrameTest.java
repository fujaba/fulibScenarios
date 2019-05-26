package org.fulib.scenarios.transform;

import org.junit.Test;

import static org.junit.Assert.*;

public class FrameTest
{

   @Test
   public void keyMatches()
   {
      assertTrue(Frame.keyMatches("", ""));
      assertTrue(Frame.keyMatches("a:1,", ""));
      assertTrue(Frame.keyMatches("", "a:1,"));
      assertTrue(Frame.keyMatches("a:1,", "a:1,")); // obvious
      assertTrue(Frame.keyMatches("a:1,", "b:2,")); // disjoint keys ok
      assertTrue(Frame.keyMatches("a:1,b:2,", "a:1,")); // only one key...
      assertTrue(Frame.keyMatches("a:1,b:2,", "b:2,")); // ...has to match
      assertTrue(Frame.keyMatches("a:1,b:2,", "a:1,b:2,")); // obvious
      assertTrue(Frame.keyMatches("a:1,b:2,", "b:2,a:1,")); // order does not matter

      assertFalse(Frame.keyMatches("a:1,", "a:2,")); // different value not ok
      assertFalse(Frame.keyMatches("a:1,b:2,", "a:2,")); // even if other key present
   }
}
