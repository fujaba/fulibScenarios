package studentAffairs.seGroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class SEGroup  
{

   protected PropertyChangeSupport listeners = null;

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (listeners != null)
      {
         listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public boolean addPropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners == null)
      {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(listener);
      return true;
   }

   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (listeners == null)
      {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(propertyName, listener);
      return true;
   }

   public boolean removePropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(listener);
      }
      return true;
   }

   public boolean removePropertyChangeListener(String propertyName,PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(propertyName, listener);
      }
      return true;
   }



   public void removeYou()
   {
      this.setSEMan(null);

      this.withoutClasses(this.getClasses().clone());


      this.withoutStudents(this.getStudents().clone());


   }
























   public static final String PROPERTY_head = "head";

   private String head;

   public String getHead()
   {
      return head;
   }

   public SEGroup setHead(String value)
   {
      if (value == null ? this.head != null : ! value.equals(this.head))
      {
         String oldValue = this.head;
         this.head = value;
         firePropertyChange("head", oldValue, value);
      }
      return this;
   }

   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getHead());


      return result.substring(1);
   }


   public static final java.util.ArrayList<SEClass> EMPTY_classes = new java.util.ArrayList<SEClass>()
   { @Override public boolean add(SEClass value){ throw new UnsupportedOperationException("No direct add! Use xy.withClasses(obj)"); }};


   public static final String PROPERTY_classes = "classes";

   private java.util.ArrayList<SEClass> classes = null;

   public java.util.ArrayList<SEClass> getClasses()
   {
      if (this.classes == null)
      {
         return EMPTY_classes;
      }

      return this.classes;
   }

   public SEGroup withClasses(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withClasses(i);
            }
         }
         else if (item instanceof SEClass)
         {
            if (this.classes == null)
            {
               this.classes = new java.util.ArrayList<SEClass>();
            }
            if ( ! this.classes.contains(item))
            {
               this.classes.add((SEClass)item);
               ((SEClass)item).setSEGroup(this);
               firePropertyChange("classes", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public SEGroup withoutClasses(Object... value)
   {
      if (this.classes == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutClasses(i);
            }
         }
         else if (item instanceof SEClass)
         {
            if (this.classes.contains(item))
            {
               this.classes.remove((SEClass)item);
               ((SEClass)item).setSEGroup(null);
               firePropertyChange("classes", item, null);
            }
         }
      }
      return this;
   }


   public static final java.util.ArrayList<SEStudent> EMPTY_students = new java.util.ArrayList<SEStudent>()
   { @Override public boolean add(SEStudent value){ throw new UnsupportedOperationException("No direct add! Use xy.withStudents(obj)"); }};


   public static final String PROPERTY_students = "students";

   private java.util.ArrayList<SEStudent> students = null;

   public java.util.ArrayList<SEStudent> getStudents()
   {
      if (this.students == null)
      {
         return EMPTY_students;
      }

      return this.students;
   }

   public SEGroup withStudents(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withStudents(i);
            }
         }
         else if (item instanceof SEStudent)
         {
            if (this.students == null)
            {
               this.students = new java.util.ArrayList<SEStudent>();
            }
            if ( ! this.students.contains(item))
            {
               this.students.add((SEStudent)item);
               ((SEStudent)item).setSEGroup(this);
               firePropertyChange("students", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public SEGroup withoutStudents(Object... value)
   {
      if (this.students == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutStudents(i);
            }
         }
         else if (item instanceof SEStudent)
         {
            if (this.students.contains(item))
            {
               this.students.remove((SEStudent)item);
               ((SEStudent)item).setSEGroup(null);
               firePropertyChange("students", item, null);
            }
         }
      }
      return this;
   }


   public static final String PROPERTY_sEMan = "sEMan";

   private SEMan sEMan = null;

   public SEMan getSEMan()
   {
      return this.sEMan;
   }

   public SEGroup setSEMan(SEMan value)
   {
      if (this.sEMan != value)
      {
         SEMan oldValue = this.sEMan;
         if (this.sEMan != null)
         {
            this.sEMan = null;
            oldValue.setRoot(null);
         }
         this.sEMan = value;
         if (value != null)
         {
            value.setRoot(this);
         }
         firePropertyChange("sEMan", oldValue, value);
      }
      return this;
   }



}