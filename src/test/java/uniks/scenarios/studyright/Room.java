package uniks.scenarios.studyright;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Room 
{

   public static final String PROPERTY_topic = "topic";

   private String topic;

   public String getTopic()
   {
      return topic;
   }

   public Room setTopic(String value)
   {
      if (value == null ? this.topic != null : ! value.equals(this.topic))
      {
         String oldValue = this.topic;
         this.topic = value;
         firePropertyChange("topic", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_credits = "credits";

   private double credits;

   public double getCredits()
   {
      return credits;
   }

   public Room setCredits(double value)
   {
      if (value != this.credits)
      {
         double oldValue = this.credits;
         this.credits = value;
         firePropertyChange("credits", oldValue, value);
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

      result.append(" ").append(this.getTopic());


      return result.substring(1);
   }

   public void removeYou()
   {
   }


}