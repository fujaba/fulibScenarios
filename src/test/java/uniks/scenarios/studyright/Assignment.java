package uniks.scenarios.studyright;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Assignment 
{

   public static final String PROPERTY_topic = "topic";

   private String topic;

   public String getTopic()
   {
      return topic;
   }

   public Assignment setTopic(String value)
   {
      if (value == null ? this.topic != null : ! value.equals(this.topic))
      {
         String oldValue = this.topic;
         this.topic = value;
         firePropertyChange("topic", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_points = "points";

   private double points;

   public double getPoints()
   {
      return points;
   }

   public Assignment setPoints(double value)
   {
      if (value != this.points)
      {
         double oldValue = this.points;
         this.points = value;
         firePropertyChange("points", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_students = "students";

   private Student students = null;

   public Student getStudents()
   {
      return this.students;
   }

   public Assignment setStudents(Student value)
   {
      if (this.students != value)
      {
         Student oldValue = this.students;
         if (this.students != null)
         {
            this.students = null;
            oldValue.withoutDone(this);
         }
         this.students = value;
         if (value != null)
         {
            value.withDone(this);
         }
         firePropertyChange("students", oldValue, value);
      }
      return this;
   }



   public static final String PROPERTY_room = "room";

   private Room room = null;

   public Room getRoom()
   {
      return this.room;
   }

   public Assignment setRoom(Room value)
   {
      if (this.room != value)
      {
         Room oldValue = this.room;
         if (this.room != null)
         {
            this.room = null;
            oldValue.withoutAssignments(this);
         }
         this.room = value;
         if (value != null)
         {
            value.withAssignments(this);
         }
         firePropertyChange("room", oldValue, value);
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
      this.setStudents(null);
      this.setRoom(null);

   }


}