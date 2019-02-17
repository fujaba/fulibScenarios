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


   public static final String PROPERTY_motivationPoints = "motivationPoints";

   private String motivationPoints;

   public String getMotivationPoints()
   {
      return motivationPoints;
   }

   public Student setMotivationPoints(String value)
   {
      if (value == null ? this.motivationPoints != null : ! value.equals(this.motivationPoints))
      {
         String oldValue = this.motivationPoints;
         this.motivationPoints = value;
         firePropertyChange("motivationPoints", oldValue, value);
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


   public static final java.util.ArrayList<Assignment> EMPTY_done = new java.util.ArrayList<Assignment>()
   { @Override public boolean add(Assignment value){ throw new UnsupportedOperationException("No direct add! Use xy.withDone(obj)"); }};


   public static final String PROPERTY_done = "done";

   private java.util.ArrayList<Assignment> done = null;

   public java.util.ArrayList<Assignment> getDone()
   {
      if (this.done == null)
      {
         return EMPTY_done;
      }

      return this.done;
   }

   public Student withDone(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withDone(i);
            }
         }
         else if (item instanceof Assignment)
         {
            if (this.done == null)
            {
               this.done = new java.util.ArrayList<Assignment>();
            }
            if ( ! this.done.contains(item))
            {
               this.done.add((Assignment)item);
               ((Assignment)item).setStudents(this);
               firePropertyChange("done", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Student withoutDone(Object... value)
   {
      if (this.done == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutDone(i);
            }
         }
         else if (item instanceof Assignment)
         {
            if (this.done.contains(item))
            {
               this.done.remove((Assignment)item);
               ((Assignment)item).setStudents(null);
               firePropertyChange("done", item, null);
            }
         }
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



   public static final String PROPERTY_uni = "uni";

   private University uni = null;

   public University getUni()
   {
      return this.uni;
   }

   public Student setUni(University value)
   {
      if (this.uni != value)
      {
         University oldValue = this.uni;
         if (this.uni != null)
         {
            this.uni = null;
            oldValue.withoutStudents(this);
         }
         this.uni = value;
         if (value != null)
         {
            value.withStudents(this);
         }
         firePropertyChange("uni", oldValue, value);
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
      result.append(" ").append(this.getMotivationPoints());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setRoom(null);
      this.setUni(null);

      this.withoutDone(this.getDone().clone());


   }


}