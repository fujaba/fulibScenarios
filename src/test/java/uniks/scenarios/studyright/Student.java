package uniks.scenarios.studyright;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Student 
{

   public static final String PROPERTY_name = "name";

   private String name;

   public String getName()
   {
      return name;
   }

   public Student setName(String value)
   {
      if (value == null ? this.name != null : ! value.equals(this.name))
      {
         String oldValue = this.name;
         this.name = value;
         firePropertyChange("name", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_motivationpoints = "motivationpoints";

   private double motivationpoints;

   public double getMotivationpoints()
   {
      return motivationpoints;
   }

   public Student setMotivationpoints(double value)
   {
      if (value != this.motivationpoints)
      {
         double oldValue = this.motivationpoints;
         this.motivationpoints = value;
         firePropertyChange("motivationpoints", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_creditpoints = "creditpoints";

   private double creditpoints;

   public double getCreditpoints()
   {
      return creditpoints;
   }

   public Student setCreditpoints(double value)
   {
      if (value != this.creditpoints)
      {
         double oldValue = this.creditpoints;
         this.creditpoints = value;
         firePropertyChange("creditpoints", oldValue, value);
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

   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getName());


      return result.substring(1);
   }

   public void removeYou()
   {
   }


}