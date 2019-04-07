package studentAffairs.seGroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class SEMan  
{

   public static final String PROPERTY_root = "root";

   private SEGroup root = null;

   public SEGroup getRoot()
   {
      return this.root;
   }

   public SEMan setRoot(SEGroup value)
   {
      if (this.root != value)
      {
         SEGroup oldValue = this.root;
         if (this.root != null)
         {
            this.root = null;
            oldValue.setSEMan(null);
         }
         this.root = value;
         if (value != null)
         {
            value.setSEMan(this);
         }
         firePropertyChange("root", oldValue, value);
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
      this.setRoot(null);

   }


   public Achievement registerStudent(SEStudent student, SEClass seClass){ 
      Achievement a2 = new Achievement();
      a2.setId("A2");
      a2.setSEStudent(student);
      a2.setState("registered");

      seClass.setAchievements(a2);
      return a2;
   }
}