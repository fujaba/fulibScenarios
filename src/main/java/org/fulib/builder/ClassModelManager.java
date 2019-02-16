package org.fulib.builder;

import org.fulib.Fulib;
import org.fulib.StrUtil;
import org.fulib.classmodel.AssocRole;
import org.fulib.classmodel.Attribute;
import org.fulib.classmodel.ClassModel;
import org.fulib.classmodel.Clazz;
import org.fulib.yaml.EventSource;
import org.fulib.yaml.Yamler;

import java.util.LinkedHashMap;
import java.util.function.Consumer;

import static org.fulib.builder.ClassModelBuilder.*;

/**
 * ClassModelbuilder is used to create fulib class models that are input for
 * fulib code generation {@link Fulib#generator()}.<br>
 * Typical usage:
 * <pre>
 * <!-- insert_code_fragment: ClassModelBuilder -->
      ClassModelBuilder mb = Fulib.classModelBuilder(packageName);

      ClassBuilder universitiy = mb.buildClass( "University").buildAttribute("name", mb.STRING);
      * <!-- end_code_fragment:  -->
 * </pre>
 *
 */
public class ClassModelManager implements IModelManager
{
   public static final String THE_CLASS_MODEL = "theClassModel";
   public static final String HAVE_PACKAGE_NAME = "havePackageName";
   public static final String HAVE_MAIN_JAVA_DIR = "haveMainJavaDir";
   public static final String HAVE_CLASS = "haveClass";
   public static final String HAVE_ATTRIBUTE = "haveAttribute";
   public static final String CLASS_NAME = "className";
   public static final String ATTR_NAME = "attrName";
   public static final String ATTR_TYPE = "attrType";
   public static final String HAVE_ROLE = "haveRole";
   public static final String SRC_CLASS_NAME = "srcClassName";
   public static final String TGT_CLASS_NAME = "tgtClassName";
   public static final String TGT_CARDINALITY = "tgtCardinality";

   private ClassModel classModel;
   private ModelEventManager mem;


   @Override
   public void initConsumers(LinkedHashMap<String, Consumer<LinkedHashMap<String, String>>> consumerMap)
   {
      if (consumerMap == null)
      {
         consumerMap = new LinkedHashMap<>();

         consumerMap.put(ClassModelManager.HAVE_PACKAGE_NAME, map -> {
            String packageName = map.get(ClassModel.PROPERTY_packageName);
            havePackageName(packageName);
         });

         consumerMap.put(ClassModelManager.HAVE_MAIN_JAVA_DIR, map -> {
            String sourceFolder = map.get(ClassModel.PROPERTY_mainJavaDir);
            haveMainJavaDir(sourceFolder);
         });

         consumerMap.put(ClassModelManager.HAVE_CLASS, map -> {
            String name = map.get(Clazz.PROPERTY_name);
            haveClass(name);
         });

         consumerMap.put(ClassModelManager.HAVE_ATTRIBUTE, map -> {
            String className = map.get(ClassModelManager.CLASS_NAME);
            String attrName = map.get(ClassModelManager.ATTR_NAME);
            String attrType = map.get(ClassModelManager.ATTR_TYPE);

            Clazz clazz = haveClass(className);
            haveAttribute(clazz, attrName, attrType);
         });

         // haveRole(Clazz srcClass, String attrName, Clazz tgtClass, int size)
         consumerMap.put(ClassModelManager.HAVE_ROLE, map -> {
            String srcClassName = map.get(ClassModelManager.SRC_CLASS_NAME);
            String attrName = map.get(ClassModelManager.ATTR_NAME);
            String tgtClassName = map.get(ClassModelManager.TGT_CLASS_NAME);
            String sizeName = map.get(ClassModelManager.TGT_CARDINALITY);

            Clazz srcClazz = haveClass(srcClassName);
            Clazz tgClazz = haveClass(tgtClassName);
            int size = Integer.valueOf(sizeName);

            haveRole(srcClazz, attrName, tgClazz, size);
         });

      }
   }


   /**
    * ClassModelManager is used to manage fulib class models that are input for
    * fulib code generation {@link Fulib#generator()}. Managed class models may
    * be merged from multiple inputs<br>
    * Typical usage:
    * <pre>
    * <!-- insert_code_fragment: ClassModelBuilder -->
      ClassModelBuilder mb = Fulib.classModelBuilder(packageName);

      ClassBuilder universitiy = mb.buildClass( "University").buildAttribute("name", mb.STRING);
    * <!-- end_code_fragment:  -->
    * </pre>
    */
   public ClassModelManager(ModelEventManager classModelEventManager)
   {
      mem = classModelEventManager;

      this.classModel = new ClassModel()
            .setDefaultPropertyStyle(POJO)
            .setDefaultRoleType(COLLECTION_ARRAY_LIST);
   }


   public ClassModel getClassModel()
   {
      return classModel;
   }



   public ClassModelManager havePackageName(String packagename)
   {
      String oldPackageName = classModel.getPackageName();

      if (StrUtil.stringEquals(oldPackageName, packagename)) return this;

      classModel.setPackageName(packagename);

      LinkedHashMap<String, String> event = new LinkedHashMap<>();
      event.put(EventSource.EVENT_TYPE, HAVE_PACKAGE_NAME);
      event.put(EventSource.EVENT_KEY, Yamler.encapsulate(THE_CLASS_MODEL + "_" + ClassModel.PROPERTY_packageName));
      event.put(ClassModel.PROPERTY_packageName, Yamler.encapsulate(packagename));
      mem.append(event);

      return this;
   }



   public ClassModelManager haveMainJavaDir(String sourceFolder)
   {
      String mainJavaDir = classModel.getMainJavaDir();

      if (StrUtil.stringEquals(mainJavaDir, sourceFolder)) return this;

      classModel.setMainJavaDir(sourceFolder);

      LinkedHashMap<String, String> event = new LinkedHashMap<>();
      event.put(EventSource.EVENT_TYPE, HAVE_MAIN_JAVA_DIR);
      event.put(EventSource.EVENT_KEY, Yamler.encapsulate(THE_CLASS_MODEL + "_" + ClassModel.PROPERTY_mainJavaDir));
      event.put(ClassModel.PROPERTY_mainJavaDir, Yamler.encapsulate(sourceFolder));
      mem.append(event);

      return this;
   }



   public Clazz haveClass(String className)
   {
      Clazz clazz = this.classModel.getClazz(className);

      if (clazz != null)  return clazz; //============================

      clazz = new ClassBuilder(this.classModel, className).getClazz();

      LinkedHashMap<String, String> event = new LinkedHashMap<>();
      event.put(EventSource.EVENT_TYPE, HAVE_CLASS);
      event.put(EventSource.EVENT_KEY, Yamler.encapsulate(className));
      event.put(Clazz.PROPERTY_name, Yamler.encapsulate(className));
      mem.append(event);

      return clazz;
   }



   public Attribute haveAttribute(Clazz clazz, String attrName, String attrType)
   {
      Attribute attr = clazz.getAttribute(attrName);

      if (attr != null && attr.getType().equals(attrType)) return attr; //==============================

      if (attr == null)
      {
         ClassModelBuilder.checkValidJavaId(attrName);
         if (clazz.getAttribute(attrName) != null
               || clazz.getRole(attrName) != null)
            throw new IllegalArgumentException("duplicate attribute / role name");

         attr = new Attribute();
         attr.setName(attrName);
         attr.setClazz(clazz);
         attr.setPropertyStyle(clazz.getPropertyStyle());
      }

      attr.setType(attrType);

      LinkedHashMap<String, String> event = new LinkedHashMap<>();
      event.put(EventSource.EVENT_TYPE, HAVE_ATTRIBUTE);
      event.put(EventSource.EVENT_KEY, Yamler.encapsulate(clazz.getName() + "." + attrName));
      event.put(CLASS_NAME, Yamler.encapsulate(clazz.getName()));
      event.put(ATTR_NAME, Yamler.encapsulate(attrName));
      event.put(ATTR_TYPE, Yamler.encapsulate(attrType));
      mem.append(event);



      return attr;
   }

   public AssocRole haveRole(Clazz srcClass, String attrName, Clazz tgtClass, int size)
   {
      AssocRole role = srcClass.getRole(attrName);

      if (role != null && role.getOther().getClazz() == tgtClass && role.getCardinality() >= size) return role; //=========

      if (role == null)
      {
         role = new AssocRole()
               .setClazz(srcClass)
               .setName(attrName)
               .setCardinality(size)
               .setPropertyStyle(srcClass.getPropertyStyle())
               .setRoleType(srcClass.getModel().getDefaultRoleType());

         AssocRole otherRole = new AssocRole()
               .setClazz(tgtClass)
               .setName(StrUtil.downFirstChar(srcClass.getName()))
               .setCardinality(ClassModelBuilder.ONE)
               .setPropertyStyle(tgtClass.getPropertyStyle())
               .setRoleType(tgtClass.getModel().getDefaultRoleType());

         role.setOther(otherRole);
      }

      int maxSize = Math.max(role.getCardinality(), size);
      role.setClazz(srcClass)
            .setCardinality(maxSize);

      LinkedHashMap<String, String> event = new LinkedHashMap<>();
      event.put(EventSource.EVENT_TYPE, HAVE_ROLE);
      event.put(EventSource.EVENT_KEY, Yamler.encapsulate(srcClass.getName() + "." + attrName));
      event.put(SRC_CLASS_NAME, Yamler.encapsulate(srcClass.getName()));
      event.put(ATTR_NAME, Yamler.encapsulate(attrName));
      event.put(TGT_CLASS_NAME, Yamler.encapsulate(tgtClass.getName()));
      event.put(TGT_CARDINALITY, "" + maxSize);
      mem.append(event);

      return role;
   }
}
