package studentAffairs.seGroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class SEClass  
{

   public SEClass setTopic(String value)
   {
      if (value == null ? this.topic != null : ! value.equals(this.topic))
      {
         String oldValue = this.topic;
         this.topic = value;
         firePropertyChange("topic", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_start = "start";

   private String start;

   public String getStart()
   {
      return start;
   }

   public SEClass setStart(String value)
   {
      if (value == null ? this.start != null : ! value.equals(this.start))
      {
         String oldValue = this.start;
         this.start = value;
         firePropertyChange("start", oldValue, value);
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
      result.append(" ").append(this.getStart());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setSEGroup(null);

      this.withoutAssignments(this.getAssignments().clone());


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

   public SEClass withAssignments(Object... value)
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
               ((Assignment)item).setSeClass(this);
               firePropertyChange("assignments", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public SEClass withoutAssignments(Object... value)
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
               ((Assignment)item).setSeClass(null);
               firePropertyChange("assignments", item, null);
            }
         }
      }
      return this;
   }


   public static final String PROPERTY_topic = "topic";

   private String topic;

   public String getTopic()
   {
      return topic;
   }



   public static final String PROPERTY_sEGroup = "sEGroup";

   private SEGroup sEGroup = null;

   public SEGroup getSEGroup()
   {
      return this.sEGroup;
   }

   public SEClass setSEGroup(SEGroup value)
   {
      if (this.sEGroup != value)
      {
         SEGroup oldValue = this.sEGroup;
         if (this.sEGroup != null)
         {
            this.sEGroup = null;
            oldValue.withoutClasses(this);
         }
         this.sEGroup = value;
         if (value != null)
         {
            value.withClasses(this);
         }
         firePropertyChange("sEGroup", oldValue, value);
      }
      return this;
   }



}