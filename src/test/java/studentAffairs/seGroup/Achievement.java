package studentAffairs.seGroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Achievement  
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
      this.setSEStudent(null);
      this.setSolutions(null);
      this.setSEClass(null);

   }












   public static final String PROPERTY_id = "id";

   private String id;

   public String getId()
   {
      return id;
   }

   public Achievement setId(String value)
   {
      if (value == null ? this.id != null : ! value.equals(this.id))
      {
         String oldValue = this.id;
         this.id = value;
         firePropertyChange("id", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_solutions = "solutions";

   private Solution solutions = null;

   public Solution getSolutions()
   {
      return this.solutions;
   }

   public Achievement setSolutions(Solution value)
   {
      if (this.solutions != value)
      {
         Solution oldValue = this.solutions;
         if (this.solutions != null)
         {
            this.solutions = null;
            oldValue.setAchievement(null);
         }
         this.solutions = value;
         if (value != null)
         {
            value.setAchievement(this);
         }
         firePropertyChange("solutions", oldValue, value);
      }
      return this;
   }



   public static final String PROPERTY_sEClass = "sEClass";

   private SEClass sEClass = null;

   public SEClass getSEClass()
   {
      return this.sEClass;
   }

   public Achievement setSEClass(SEClass value)
   {
      if (this.sEClass != value)
      {
         SEClass oldValue = this.sEClass;
         if (this.sEClass != null)
         {
            this.sEClass = null;
            oldValue.setAchievements(null);
         }
         this.sEClass = value;
         if (value != null)
         {
            value.setAchievements(this);
         }
         firePropertyChange("sEClass", oldValue, value);
      }
      return this;
   }


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getId());
      result.append(" ").append(this.getState());


      return result.substring(1);
   }

   public static final String PROPERTY_state = "state";

   private String state;

   public String getState()
   {
      return state;
   }

   public Achievement setState(String value)
   {
      if (value == null ? this.state != null : ! value.equals(this.state))
      {
         String oldValue = this.state;
         this.state = value;
         firePropertyChange("state", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_sEStudent = "sEStudent";

   private SEStudent sEStudent = null;

   public SEStudent getSEStudent()
   {
      return this.sEStudent;
   }

   public Achievement setSEStudent(SEStudent value)
   {
      if (this.sEStudent != value)
      {
         SEStudent oldValue = this.sEStudent;
         if (this.sEStudent != null)
         {
            this.sEStudent = null;
            oldValue.setAchievements(null);
         }
         this.sEStudent = value;
         if (value != null)
         {
            value.setAchievements(this);
         }
         firePropertyChange("sEStudent", oldValue, value);
      }
      return this;
   }



}