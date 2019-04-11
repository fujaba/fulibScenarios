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
      this.setStudents(null);

      this.withoutAssignments(this.getAssignments().clone());


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

   public static final java.util.ArrayList<Assignment> EMPTY_assignments = new java.util.ArrayList<Assignment>()
   { @Override public boolean add(Assignment value){ throw new UnsupportedOperationException("No direct add! Use xy.withAssignments(obj)"); }};


   public static final String PROPERTY_assignments = "assignments";

   private java.util.ArrayList<Assignment> assignments = null;

   public java.util.ArrayList<Assignment> getAssignments()
   {
      if (this.assignments == null)
      {
         return EMPTY_assignments;
      }

      return this.assignments;
   }

   public Room withAssignments(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withAssignments(i);
            }
         }
         else if (item instanceof Assignment)
         {
            if (this.assignments == null)
            {
               this.assignments = new java.util.ArrayList<Assignment>();
            }
            if ( ! this.assignments.contains(item))
            {
               this.assignments.add((Assignment)item);
               ((Assignment)item).setRoom(this);
               firePropertyChange("assignments", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Room withoutAssignments(Object... value)
   {
      if (this.assignments == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutAssignments(i);
            }
         }
         else if (item instanceof Assignment)
         {
            if (this.assignments.contains(item))
            {
               this.assignments.remove((Assignment)item);
               ((Assignment)item).setRoom(null);
               firePropertyChange("assignments", item, null);
            }
         }
      }
      return this;
   }


}