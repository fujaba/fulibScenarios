package org.example2;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.classmodel.Clazz;

public class PlaneModel implements ClassModelDecorator
{
   @Override
   public void decorate(ClassModelManager m)
   {
      Clazz plane = m.haveClass("Plane");
   }
}
