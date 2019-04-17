package MineSweeper;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class MineSweeper  
{

   public static final String PROPERTY_id = "id";

   private String id;

   public String getId()
   {
      return id;
   }

   public MineSweeper setId(String value)
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
      this.withoutFields(this.getFields().clone());


   }


   public static final java.util.ArrayList<Field> EMPTY_fields = new java.util.ArrayList<Field>()
   { @Override public boolean add(Field value){ throw new UnsupportedOperationException("No direct add! Use xy.withFields(obj)"); }};


   public static final String PROPERTY_fields = "fields";

   private java.util.ArrayList<Field> fields = null;

   public java.util.ArrayList<Field> getFields()
   {
      if (this.fields == null)
      {
         return EMPTY_fields;
      }

      return this.fields;
   }

   public MineSweeper withFields(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withFields(i);
            }
         }
         else if (item instanceof Field)
         {
            if (this.fields == null)
            {
               this.fields = new java.util.ArrayList<Field>();
            }
            if ( ! this.fields.contains(item))
            {
               this.fields.add((Field)item);
               ((Field)item).setParent(this);
               firePropertyChange("fields", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }



   public MineSweeper withoutFields(Object... value)
   {
      if (this.fields == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutFields(i);
            }
         }
         else if (item instanceof Field)
         {
            if (this.fields.contains(item))
            {
               this.fields.remove((Field)item);
               ((Field)item).setParent(null);
               firePropertyChange("fields", item, null);
            }
         }
      }
      return this;
   }



   public void init(){ 
      java.util.ArrayList<String> idList = new java.util.ArrayList<String>();
      for (int i = 1; i <= 9; i++) {
         idList.add("f"+i);
      }

      java.util.ArrayList<Integer> numberList = new java.util.ArrayList<Integer>();
      for (int i = 1; i <= 9; i++) {
         numberList.add(i);
      }

      java.util.ArrayList<Field> fList = new java.util.ArrayList<>();
      for (int i = 0; i < idList.size(); i++ ) {
         Field fieldTmp = new Field();
         fieldTmp.setId(idList.get(i));
         fieldTmp.setNumber(numberList.get(i));
         fieldTmp.setParent(this);
         fList.add(fieldTmp);
      }

      double result = 42;
      java.util.ArrayList<Field> tmpFieldValueList = new java.util.ArrayList<>();
      for (int i = 1; i <= 2; i++) {
         tmpFieldValueList.add(fList.get(i-1));
      }
      for (int i = 7; i <= 8; i++) {
         tmpFieldValueList.add(fList.get(i-1));
      }
      tmpFieldValueList.add(fList.get(4-1));
      java.util.ArrayList<Field> tmpFieldTargetList = new java.util.ArrayList<>();
      for (int i = 2; i <= 3; i++) {
         tmpFieldTargetList.add(fList.get(i-1));
      }
      for (int i = 8; i <= 9; i++) {
         tmpFieldTargetList.add(fList.get(i-1));
      }
      tmpFieldTargetList.add(fList.get(5-1));
      for (int i = 1; i <= tmpFieldTargetList.size(); i++) {
         tmpFieldTargetList.get(i-1).withNeighbors(tmpFieldValueList.get(i-1));
      }

      java.util.ArrayList<Integer> tmpIntValueList = new java.util.ArrayList<>();
      for (int i = 10; i <= 20; i++) {
         tmpIntValueList.add(i);
      }
      // list to single value to be done 
   }
}