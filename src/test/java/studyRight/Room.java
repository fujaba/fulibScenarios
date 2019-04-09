package studyRight;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Room  
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
      this.setAssignment(null);
      this.setStudents(null);

   }







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


   public static final String PROPERTY_students = "students";

   private Student students = null;

   public Student getStudents()
   {
      return this.students;
   }

   public Room setStudents(Student value)
   {
      if (this.students != value)
      {
         Student oldValue = this.students;
         if (this.students != null)
         {
            this.students = null;
            oldValue.setRoom(null);
         }
         this.students = value;
         if (value != null)
         {
            value.setRoom(this);
         }
         firePropertyChange("students", oldValue, value);
      }
      return this;
   }



   public static final String PROPERTY_assignment = "assignment";

   private Assignment assignment = null;

   public Assignment getAssignment()
   {
      return this.assignment;
   }

   public Room setAssignment(Assignment value)
   {
      if (this.assignment != value)
      {
         Assignment oldValue = this.assignment;
         if (this.assignment != null)
         {
            this.assignment = null;
            oldValue.setRoom(null);
         }
         this.assignment = value;
         if (value != null)
         {
            value.setRoom(this);
         }
         firePropertyChange("assignment", oldValue, value);
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


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getTopic());


      return result.substring(1);
   }

}