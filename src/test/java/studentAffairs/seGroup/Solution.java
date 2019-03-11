package studentAffairs.seGroup;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Solution  
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
      this.setAchievement(null);
      this.setAssignment(null);

   }












   public static final String PROPERTY_id = "id";

   private String id;

   public String getId()
   {
      return id;
   }

   public Solution setId(String value)
   {
      if (value == null ? this.id != null : ! value.equals(this.id))
      {
         String oldValue = this.id;
         this.id = value;
         firePropertyChange("id", oldValue, value);
      }
      return this;
   }


   public static final String PROPERTY_text = "text";

   private String text;

   public String getText()
   {
      return text;
   }

   public Solution setText(String value)
   {
      if (value == null ? this.text != null : ! value.equals(this.text))
      {
         String oldValue = this.text;
         this.text = value;
         firePropertyChange("text", oldValue, value);
      }
      return this;
   }



   public static final String PROPERTY_achievement = "achievement";

   private Achievement achievement = null;

   public Achievement getAchievement()
   {
      return this.achievement;
   }

   public Solution setAchievement(Achievement value)
   {
      if (this.achievement != value)
      {
         Achievement oldValue = this.achievement;
         if (this.achievement != null)
         {
            this.achievement = null;
            oldValue.setSolutions(null);
         }
         this.achievement = value;
         if (value != null)
         {
            value.setSolutions(this);
         }
         firePropertyChange("achievement", oldValue, value);
      }
      return this;
   }



   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getId());
      result.append(" ").append(this.getText());


      return result.substring(1);
   }

   public static final String PROPERTY_assignment = "assignment";

   private Assignment assignment = null;

   public Assignment getAssignment()
   {
      return this.assignment;
   }

   public Solution setAssignment(Assignment value)
   {
      if (this.assignment != value)
      {
         Assignment oldValue = this.assignment;
         if (this.assignment != null)
         {
            this.assignment = null;
            oldValue.withoutSolutions(this);
         }
         this.assignment = value;
         if (value != null)
         {
            value.withSolutions(this);
         }
         firePropertyChange("assignment", oldValue, value);
      }
      return this;
   }



}