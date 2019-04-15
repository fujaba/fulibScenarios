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

      this.withoutTaskLog(this.getTaskLog().clone());


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
            oldValue.withoutStudents(this);
         }
         this.room = value;
         if (value != null)
         {
            value.withStudents(this);
         }
         firePropertyChange("room", oldValue, value);
      }
      return this;
   }

   public static final java.util.ArrayList<Assignment> EMPTY_taskLog = new java.util.ArrayList<Assignment>()
   { @Override public boolean add(Assignment value){ throw new UnsupportedOperationException("No direct add! Use xy.withTaskLog(obj)"); }};


   public static final String PROPERTY_taskLog = "taskLog";

   private java.util.ArrayList<Assignment> taskLog = null;

   public java.util.ArrayList<Assignment> getTaskLog()
   {
      if (this.taskLog == null)
      {
         return EMPTY_taskLog;
      }

      return this.taskLog;
   }

   public Student withTaskLog(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withTaskLog(i);
            }
         }
         else if (item instanceof Assignment)
         {
            if (this.taskLog == null)
            {
               this.taskLog = new java.util.ArrayList<Assignment>();
            }
            if ( ! this.taskLog.contains(item))
            {
               this.taskLog.add((Assignment)item);
               ((Assignment)item).setStudent(this);
               firePropertyChange("taskLog", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Student withoutTaskLog(Object... value)
   {
      if (this.taskLog == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutTaskLog(i);
            }
         }
         else if (item instanceof Assignment)
         {
            if (this.taskLog.contains(item))
            {
               this.taskLog.remove((Assignment)item);
               ((Assignment)item).setStudent(null);
               firePropertyChange("taskLog", item, null);
            }
         }
      }
      return this;
   }


   public void work(){ 
      Room room = this.getRoom();
      double credits = room.getCredits();
      double pointSum = 0;
      int maxTaskNumber = room.getAssignments().size();
      int position = 1;
      for ( ; position <= maxTaskNumber; position++) {
         Assignment currentTask = room.getAssignments().get(position-1);
         pointSum = pointSum + currentTask.getPoints();
         if (pointSum <= credits) {
            continue;
         }
         if (pointSum >= credits) {
            this.setCredits(this.getCredits() + credits);
            break;
         }
      }
      for (int i = 1; i <= position; i++) {
         this.withTaskLog(room.getAssignments().get(i-1));
      }
   }
}