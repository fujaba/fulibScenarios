package org.fulib;

import org.fulib.builder.ClassModelBuilder;
import org.fulib.classmodel.AssocRole;
import org.fulib.classmodel.Clazz;

public class MultiAttributes
{
   public static void buildMultiAttribute(Clazz clazz, String name, String elementType)
   {
      buildMultiAttribute(clazz, name, "java.util.ArrayList", elementType);
   }

   public static void buildMultiAttribute(Clazz clazz, String name, String collectionType, String elementType)
   {
      final Clazz fakeClass = new Clazz().setName(elementType);

      final AssocRole fakeRole = new AssocRole();
      fakeRole.setName(null);
      fakeRole.setCardinality(1);
      fakeRole.setClazz(fakeClass);

      final AssocRole role = new AssocRole();
      role.setClazz(clazz);
      role.setName(name);
      role.setCardinality(ClassModelBuilder.MANY);
      role.setOther(fakeRole);
      role.setRoleType(collectionType.concat("<%s>"));
   }
}
