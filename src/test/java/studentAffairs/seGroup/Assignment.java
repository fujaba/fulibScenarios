package studentAffairs.seGroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Assignment  
{

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


   public static final String PROPERTY_seClass = "seClass";

   private SEClass seClass = null;

   public SEClass getSeClass()
   {
      return this.seClass;
   }

   public Assignment setSeClass(SEClass value)
   {
      if (this.seClass != value)
      {
         SEClass oldValue = this.seClass;
         if (this.seClass != null)
         {
            this.seClass = null;
            oldValue.withoutAssignments(this);
         }
         this.seClass = value;
         if (value != null)
         {
            value.withAssignments(this);
         }
         firePropertyChange("seClass", oldValue, value);
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
      this.setSeClass(null);

      this.withoutSolutions(this.getSolutions().clone());


   }



   public static final String PROPERTY_topic = "topic";

   private String topic;

   public String getTopic()
   {
      return topic;
   }

   public static final java.util.ArrayList<Solution> EMPTY_solutions = new java.util.ArrayList<Solution>()
   { @Override public boolean add(Solution value){ throw new UnsupportedOperationException("No direct add! Use xy.withSolutions(obj)"); }};


   public static final String PROPERTY_solutions = "solutions";

   private java.util.ArrayList<Solution> solutions = null;

   public java.util.ArrayList<Solution> getSolutions()
   {
      if (this.solutions == null)
      {
         return EMPTY_solutions;
      }

      return this.solutions;
   }

   public Assignment withSolutions(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withSolutions(i);
            }
         }
         else if (item instanceof Solution)
         {
            if (this.solutions == null)
            {
               this.solutions = new java.util.ArrayList<Solution>();
            }
            if ( ! this.solutions.contains(item))
            {
               this.solutions.add((Solution)item);
               ((Solution)item).setAssignment(this);
               firePropertyChange("solutions", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Assignment withoutSolutions(Object... value)
   {
      if (this.solutions == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutSolutions(i);
            }
         }
         else if (item instanceof Solution)
         {
            if (this.solutions.contains(item))
            {
               this.solutions.remove((Solution)item);
               ((Solution)item).setAssignment(null);
               firePropertyChange("solutions", item, null);
            }
         }
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