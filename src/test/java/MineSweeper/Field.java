package MineSweeper;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Field  
{

   public Field setId(String value)
   {
      if (value == null ? this.id != null : ! value.equals(this.id))
      {
         String oldValue = this.id;
         this.id = value;
         firePropertyChange("id", oldValue, value);
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

      result.append(" ").append(this.getId());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setParent(null);
      this.setField(null);

      this.withoutNeighbors(this.getNeighbors().clone());


   }



   public static final String PROPERTY_id = "id";

   private String id;

   public String getId()
   {
      return id;
   }

   public static final String PROPERTY_number = "number";

   private double number;

   public double getNumber()
   {
      return number;
   }

   public Field setNumber(double value)
   {
      if (value != this.number)
      {
         double oldValue = this.number;
         this.number = value;
         firePropertyChange("number", oldValue, value);
      }
      return this;
   }


   public static final java.util.ArrayList<Field> EMPTY_Neighbors = new java.util.ArrayList<Field>()
   { @Override public boolean add(Field value){ throw new UnsupportedOperationException("No direct add! Use xy.withNeighbors(obj)"); }};


   public static final String PROPERTY_Neighbors = "Neighbors";

   private java.util.ArrayList<Field> Neighbors = null;

   public java.util.ArrayList<Field> getNeighbors()
   {
      if (this.Neighbors == null)
      {
         return EMPTY_Neighbors;
      }

      return this.Neighbors;
   }

   public Field withNeighbors(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withNeighbors(i);
            }
         }
         else if (item instanceof Field)
         {
            if (this.Neighbors == null)
            {
               this.Neighbors = new java.util.ArrayList<Field>();
            }
            if ( ! this.Neighbors.contains(item))
            {
               this.Neighbors.add((Field)item);
               ((Field)item).setField(this);
               firePropertyChange("Neighbors", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public Field withoutNeighbors(Object... value)
   {
      if (this.Neighbors == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutNeighbors(i);
            }
         }
         else if (item instanceof Field)
         {
            if (this.Neighbors.contains(item))
            {
               this.Neighbors.remove((Field)item);
               ((Field)item).setField(null);
               firePropertyChange("Neighbors", item, null);
            }
         }
      }
      return this;
   }


   public static final String PROPERTY_field = "field";

   private Field field = null;

   public Field getField()
   {
      return this.field;
   }

   public Field setField(Field value)
   {
      if (this.field != value)
      {
         Field oldValue = this.field;
         if (this.field != null)
         {
            this.field = null;
            oldValue.withoutNeighbors(this);
         }
         this.field = value;
         if (value != null)
         {
            value.withNeighbors(this);
         }
         firePropertyChange("field", oldValue, value);
      }
      return this;
   }



   public static final String PROPERTY_parent = "parent";

   private MineSweeper parent = null;

   public MineSweeper getParent()
   {
      return this.parent;
   }

   public Field setParent(MineSweeper value)
   {
      if (this.parent != value)
      {
         MineSweeper oldValue = this.parent;
         if (this.parent != null)
         {
            this.parent = null;
            oldValue.withoutFields(this);
         }
         this.parent = value;
         if (value != null)
         {
            value.withFields(this);
         }
         firePropertyChange("parent", oldValue, value);
      }
      return this;
   }



}