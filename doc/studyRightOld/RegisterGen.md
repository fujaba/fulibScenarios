
# Register

Assignment e.g. integrals, series, matrices, 
 + topic e.g. integrals 101, series 101, matrices 101, 
 + points e.g. 10, 8, 6, 
 + students one Student cf. done
 + room one Room cf. assignments


Room e.g. mathRoom, modelingRoom, 
 + topic e.g. math, modeling, 
 + credits e.g. 23, 42, 
 + assignments many Assignment cf. room
 + students many Student cf. room
 + doors many Room cf. doors
 + uni one University cf. rooms


Student e.g. carliBob, alice, 
 + name e.g. Carli Bob, Alice, 
 + motivationPoints e.g. 42.0, 84.0, 
 + creditPoints e.g. 0.0, 0.0, 
 + done many Assignment cf. students
 + room one Room cf. students
 + uni one University cf. students


StudyRightUtils e.g. utils, 

University e.g. studyRight, 
 + rooms many Room cf. uni
 + students many Student cf. uni



