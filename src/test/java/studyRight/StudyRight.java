package studyRight;

import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

public class StudyRight  
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
      this.setStudent(null);

   }
   


   public Student init(){ 
      Student carli = new Student();
      carli.setName("carli");
      carli.setUni(this);
      carli.setCredits(0);
      carli.setMotivation(65);

      Room math = new Room();
      math.setTopic("math");
      math.setCredits(23);
      math.setStudents(carli);

      java.util.ArrayList<String> taskList = new java.util.ArrayList<String>();
      taskList.add("integrals");
      taskList.add("series");
      taskList.add("matrices");

      java.util.ArrayList<Double> pointsList = new java.util.ArrayList<Double>();
      pointsList.add((double) 5);
      pointsList.add((double) 20);
      pointsList.add((double) 10);

      java.util.ArrayList<Assignment> assignmentList = new java.util.ArrayList<>();
      for (int i = 0; i < taskList.size(); i++ ) {
         Assignment assignmentTmp = new Assignment();
         assignmentTmp.setTask(taskList.get(i));
         assignmentTmp.setPoints(pointsList.get(i));
         assignmentTmp.setRoom(math);

         assignmentList.add(assignmentTmp);
      }

      return carli;
   }


   public static final String PROPERTY_student = "student";

   private Student student = null;

   public Student getStudent()
   {
      return this.student;
   }

   public StudyRight setStudent(Student value)
   {
      if (this.student != value)
      {
         Student oldValue = this.student;
         if (this.student != null)
         {
            this.student = null;
            oldValue.setUni(null);
         }
         this.student = value;
         if (value != null)
         {
            value.setUni(this);
         }
         firePropertyChange("student", oldValue, value);
      }
      return this;
   }




   public void work(){ 
      // hello world
   }


   public static final String PROPERTY_credits = "credits";

   private double credits;

   public double getCredits()
   {
      return credits;
   }

   public StudyRight setCredits(double value)
   {
      if (value != this.credits)
      {
         double oldValue = this.credits;
         this.credits = value;
         firePropertyChange("credits", oldValue, value);
      }
      return this;
   }
}