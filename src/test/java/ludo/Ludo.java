package ludo;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class Ludo  
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
      this.setPlayer(null);

   }


   public void init(){ 
      java.util.ArrayList<String> nameList = new java.util.ArrayList<String>();
      nameList.add("Alice");
      nameList.add("Bob");

      java.util.ArrayList<Player> playerList = new java.util.ArrayList<>();
      for (int i = 0; i < nameList.size(); i++ ) {
         Player playerTmp = new Player();
         playerTmp.setName(nameList.get(i));
         playerTmp.setGame(this);
         playerList.add(playerTmp);
      }

   }


   public static final String PROPERTY_player = "player";

   private Player player = null;

   public Player getPlayer()
   {
      return this.player;
   }

   public Ludo setPlayer(Player value)
   {
      if (this.player != value)
      {
         Player oldValue = this.player;
         if (this.player != null)
         {
            this.player = null;
            oldValue.setGame(null);
         }
         this.player = value;
         if (value != null)
         {
            value.setGame(this);
         }
         firePropertyChange("player", oldValue, value);
      }
      return this;
   }
}