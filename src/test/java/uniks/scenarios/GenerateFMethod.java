package uniks.scenarios;

import org.fulib.Fulib;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.classmodel.Clazz;

public class GenerateFMethod
{
   public static void main(String[] args)
   {
      ClassModelBuilder mb = Fulib.classModelBuilder(Clazz.class.getPackage().getName());

      mb.buildClass("FMethod")
            .buildAttribute("name", mb.STRING)
            .buildAttribute("returnType", mb.STRING)
            .buildAttribute("methodBody", mb.STRING)
            .buildAttribute("className", mb.STRING)
            .buildAttribute("packageName", mb.STRING)
            .buildAttribute("javaSrcDir", mb.STRING);

      Fulib.generator().generate(mb.getClassModel());
   }
}
