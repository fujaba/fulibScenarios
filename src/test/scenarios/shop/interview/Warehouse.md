# Scenario Italy delivers Shoes.

  There is a Warehouse.
  There are Areas with name fast area 
  and slow area.
  The Warehouse has areas fast area 
  and slow area.
  There are Places with name f1, f2, f3, s1.
  Fast area has places f1, f2, f3.
  Slow area has places s1.
  
![Warehouse](wareHouseAndPlaces.svg)

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


## operations
  We call newPalette with pId eu100
  and with pName "Sneakers" and with items 50
  and with place f2.

NewPalette creates a Palette with id eu100
<!--and with product pName-->
<!--and with items items-->
and with place place.
NewPalette writes eu100 into result.
NewPalette answers with result.  
  
 
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
![ForkLiftGuide](step03.html)
We writes "eu100" into value of lotId.
![ForkLiftGuide](step04.html)
We writes "f1" into value of placeId.
![ForkLiftGuide](step05.html)

There is a Page with id storeId 
and with description "Palette | Product | Number of Items | Place".
There is a Content with id paletteLine1
and with description "eu100 | Sneakers | 50 | f1".
We write paletteLine1 into content of storeId.
We write storeId into content of ForkLiftGuide.
![ForkLiftGuide](step06.html)


![ForkLiftGuide](step00-06.mockup.html)

![Warehouse](Tables.tables.html)
![Warehouse](Overview.yaml)
