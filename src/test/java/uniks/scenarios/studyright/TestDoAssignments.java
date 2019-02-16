package uniks.scenarios.studyright;

import org.fulib.FulibTools;
import org.junit.Test;

public class TestDoAssignments
{
   @Test
   public void testScenario1()
   {
       University studyRight = new University();

       Student carliBob = new Student()
                .setName("Carli Bob")
                .setMotivationpoints(42.0)
                .setCreditpoints(0.0);

       Student alice = new Student()
                .setName("Alice")
                .setMotivationpoints(84.0)
                .setCreditpoints(0.0);

       Room mathRoom = new Room()
                .setTopic("math")
                .setCredits(23);

       Room modelingRoom = new Room()
                .setTopic("modeling")
                .setCredits(42);

       Assignment integrals = new Assignment()
                .setTopic("integrals 101")
                .setPoints(10);

       Assignment series = new Assignment()
                .setTopic("series 101")
                .setPoints(8);

       Assignment matrices = new Assignment()
                .setTopic("matrices 101")
                .setPoints(6);

       StudyRightUtils utils = new StudyRightUtils();

       studyRight.withStudents(carliBob, alice);
       studyRight.withRooms(mathRoom, modelingRoom);
       carliBob.withDone(integrals, series);
       mathRoom.withAssignments(integrals, series, matrices);
       mathRoom.withStudents(carliBob, alice);

       FulibTools.objectDiagrams().dumpPng("doc/studyRight/Scenario1.png", studyRight);

   }
}