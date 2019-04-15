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
         fList.add(fieldTmp);
      }

            for (double d = f1; d <= f2; d++) {
         f2.withNeighbors(d);
      }

   }
}