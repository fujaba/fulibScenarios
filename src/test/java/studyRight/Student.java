package studyRight;

import org.junit.Test;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

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
      this.setRoom(null);
      this.setUni(null);

   }



   public static final String PROPERTY_uni = "uni";

   private StudyRight uni = null;

   public StudyRight getUni()
   {
      return this.uni;
   }

   public Student setUni(StudyRight value)
   {
      if (this.uni != value)
      {
         StudyRight oldValue = this.uni;
         if (this.uni != null)
         {
            this.uni = null;
            oldValue.setStudent(null);
         }
         this.uni = value;
         if (value != null)
         {
            value.setStudent(this);
         }
         firePropertyChange("uni", oldValue, value);
      }
      return this;
   }



   public static final String PROPERTY_room = "room";

   private Room room = null;

   public Room getRoom()
   {
      return this.room;
   }

   public Student setRoom(Room value)
   {
      if (this.room != value)
      {
         Room oldValue = this.room;
         if (this.room != null)
         {
            this.room = null;
            oldValue.setStudents(null);
         }
         this.room = value;
         if (value != null)
         {
            value.setStudents(this);
         }
         firePropertyChange("room", oldValue, value);
      }
      return this;
   }



   public static final String PROPERTY_credits = "credits";

   private double credits;

   public double getCredits()
   {
      return credits;
   }

   public Student setCredits(double value)
   {
      if (value != this.credits)
      {
         double oldValue = this.credits;
         this.credits = value;
         firePropertyChange("credits", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_motivation = "motivation";

   private double motivation;

   public double getMotivation()
   {
      return motivation;
   }

   public Student setMotivation(double value)
   {
      if (value != this.motivation)
      {
         double oldValue = this.motivation;
         this.motivation = value;
         firePropertyChange("motivation", oldValue, value);
      }
      return this;
   }


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getName());


      return result.substring(1);
   }


   public void work(){ 
      double pointSum = 0;
      if (pointSum <= 23) {
         pointSum = 42;
      }
      Room room = this.getRoom();
      double credits = room.getCredits();
      java.util.ArrayList<Assignment> tasks = room.getAssignments();
      int tasks_i = 1;
      for ( ; tasks_i <= tasks.size(); tasks_i++) {
         pointSum = pointSum + tasks.get(tasks_i).getPoints();
      }
   }
}