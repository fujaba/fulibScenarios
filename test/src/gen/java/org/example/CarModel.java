package org.example;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.classmodel.Clazz;

public class CarModel implements ClassModelDecorator
{
   @Override
   public void decorate(ClassModelManager m)
   {
      // class SuperCar extends Car

      Clazz car = m.haveClass("Car");
      Clazz superCar = m.haveClass("SuperCar");
      superCar.setSuperClass(car);
   }
}
