# Scenario Italy delivers Shoes.

There is a Warehouse.
There are Areas with name fast area, middle area, slow.
The Warehouse has areas fast area, middle area, slow.

There are Places with name f1, f2, f3, m1, m2, m3.
Fast area has places f1, f2, f3.
Middle area has places m1, m2, m3.

There is a Producer with name Italy. 
There are Products with name High Heels, Nike Airs.  

There are Palette with id eu100
and with quantity 100.
There are Palette with id eu200
and with quantity 150.
There are Palette with id  eu333
and with quantity  150.

High Heels has palettes eu200, eu333.
Nike Airs has palettes eu100.

There is a Position with id p2
and with posProduct High Heels
and with palettes eu200, eu333.

There is a Position with id p1
and with posProduct Nike Airs
and with palettes eu100.

There is an Intake with id in42  
and with producer Italy 
and with positions p1, p2.

The Warehouse has intake in42.

We call intakePalette on Warehouse with palette eu100 
and with place f1.
IntakePalette writes eu100 into content of f1.

We call intakePalette on Warehouse with palette eu200 
and with place f2.

## GUI
There is a WebApp with id ForkLiftGuide 
and with description "Fork Lift Guide".
There is a Page with id addSupplyPage 
and with description "New Supply | button Tasks".
ForkLiftGuide has content addSupplyPage.
There is a Content with id lotId 
and with description "input palette id?".
There is a Content with id placeId 
and with description "input place?".
There is a Content with id addLotToStoreButton and with description "button submit".
AddSupplyPage has content lotId, placeId, addLotToStoreButton.
![ForkLiftGuide](step04.html)

We writes "eu100" into value of lotId.
![ForkLiftGuide](step04.html)

We writes "f1" into value of placeId.
![ForkLiftGuide](step04.html)
![ForkLiftGuide](step04.mockup.html)

![Warehouse](Tables.tables.html)
![Warehouse](Overview.yaml)
