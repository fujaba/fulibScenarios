
# Register

Achievement e.g. alicePMDone, 
 + id e.g. A1, alicePMDone
 + sEStudent one SEStudent cf. achievements
 + solutions one Solution cf. achievement


Assignment e.g. partyAppScenarios, 
 + topic e.g. Composite, PartyApp Scenarios
 + points e.g. 10, 12
 + seClass one SEClass cf. assignments
 + solutions many Solution cf. assignment


SEClass e.g. modeling, designPattern, 
 + topic e.g. Design Pattern, Modeling, OO Modeling, design pattern
 + start e.g. 2019-4
 + assignments many Assignment cf. seClass
 + sEGroup one SEGroup cf. classes


SEGroup e.g. sEGroup, 
 + head e.g. Albert
 + classes many SEClass cf. sEGroup
 + students many SEStudent cf. sEGroup
 + sEMan one SEMan cf. root


SEMan e.g. 
 + root one SEGroup cf. sEMan


SEStudent e.g. alice, bob, 
 + name e.g. Alice, Bob
 + studentId e.g. m23, m42
 + achievements many Achievement cf. sEStudent
 + sEGroup one SEGroup cf. students


Solution e.g. easyDone, 
 + id e.g. easyDone
 + text e.g. 42
 + achievement one Achievement cf. solutions
 + assignment one Assignment cf. solutions



