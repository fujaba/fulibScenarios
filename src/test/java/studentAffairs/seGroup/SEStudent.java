package studentAffairs.seGroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class SEStudent  
{

   public SEStudent setName(String value)
   {
      if (value == null ? this.name != null : ! value.equals(this.name))
      {
         String oldValue = this.name;
         this.name = value;
         firePropertyChange("name", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_studentId = "studentId";

   private String studentId;

   public String getStudentId()
   {
      return studentId;
   }

   public SEStudent setStudentId(String value)
   {
      if (value == null ? this.studentId != null : ! value.equals(this.studentId))
      {
         String oldValue = this.studentId;
         this.studentId = value;
         firePropertyChange("studentId", oldValue, value);
      }
      return this;
   }


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
      this.setAchievements(null);
      this.setSEGroup(null);

   }



   public static final String PROPERTY_name = "name";

   private String name;

   public String getName()
   {
      return name;
   }



   public static final String PROPERTY_sEGroup = "sEGroup";

   private SEGroup sEGroup = null;

   public SEGroup getSEGroup()
   {
      return this.sEGroup;
   }

   public SEStudent setSEGroup(SEGroup value)
   {
      if (this.sEGroup != value)
      {
         SEGroup oldValue = this.sEGroup;
         if (this.sEGroup != null)
         {
            this.sEGroup = null;
            oldValue.withoutStudents(this);
         }
         this.sEGroup = value;
         if (value != null)
         {
            value.withStudents(this);
         }
         firePropertyChange("sEGroup", oldValue, value);
      }
      return this;
   }


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getName());
      result.append(" ").append(this.getStudentId());


      return result.substring(1);
   }

   public static final String PROPERTY_achievements = "achievements";

   private Achievement achievements = null;

   public Achievement getAchievements()
   {
      return this.achievements;
   }

   public SEStudent setAchievements(Achievement value)
   {
      if (this.achievements != value)
      {
         Achievement oldValue = this.achievements;
         if (this.achievements != null)
         {
            this.achievements = null;
            oldValue.setSEStudent(null);
         }
         this.achievements = value;
         if (value != null)
         {
            value.setSEStudent(this);
         }
         firePropertyChange("achievements", oldValue, value);
      }
      return this;
   }



}