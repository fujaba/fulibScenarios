
# Register

Achievement e.g. a2, alicePMDone, 
 + id e.g. A2, alicePMDone
 + state e.g. registered
 + sEStudent one SEStudent cf. achievements e.g. 
 + solutions one Solution cf. achievement e.g. easyDone


Assignment e.g. composite, partyAppScenarios, 
 + topic e.g. Composite, PartyApp Scenarios
 + points e.g. 10, 12
 + seClass one SEClass cf. assignments e.g. modeling, pattern
 + solutions many Solution cf. assignment e.g. easyDone


SEClass e.g. modeling, pattern, designPattern, 
 + topic e.g. Design Pattern, Modeling, OO Modeling, design pattern
 + start e.g. 2019-4
 + assignments many Assignment cf. seClass e.g. 
 + sEGroup one SEGroup cf. classes e.g. sEGroup


SEGroup e.g. sEGroup, 
 + head e.g. Albert
 + classes many SEClass cf. sEGroup e.g. designPattern, modeling, pattern
 + students many SEStudent cf. sEGroup e.g. alice, bob
 + sEMan one SEMan cf. root e.g. 


SEMan e.g. sEMan, 
 + root one SEGroup cf. sEMan e.g. sEGroup


SEStudent e.g. alice, ali, bob, 
 + name e.g. Ali, Alice, Bob, nameList.get(i)
 + studentId e.g. m20, m23, m42, studentIdList.get(i)
 + achievements one Achievement cf. sEStudent e.g. alicePMDone
 + sEGroup one SEGroup cf. students e.g. sEGroup


Solution e.g. easyDone, 
 + id e.g. easyDone
 + text e.g. 42
 + achievement one Achievement cf. solutions e.g. 
 + assignment one Assignment cf. solutions e.g. 



