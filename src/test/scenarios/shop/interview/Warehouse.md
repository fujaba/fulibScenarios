# Scenario Italy delivers Shoes.

  There is a Warehouse.
  There are Areas with name fast area, middle area 
  and slow area.
  The Warehouse has areas and is warehouse of fast area, middle area 
  and slow area.
  There are Places with name f1, f2, f3, m1, m2, s1.
  Fast area has places and is area of f1, f2, f3.
  Middle area has places m1 and m2.
  Slow area has places s1.
  
![Warehouse](wareHouseAndPlaces.svg)

There is a Producer with name Italy. 
There are Products with name High Heels, Nike Airs.  

There are Palette with id eu100, eu200, eu333
and with quantity 100, 150, 150.

High Heels has palettes eu200, eu333.
Nike Airs has palettes eu100.

There is an Arrival with id mondayArrival and with palettes
eu100, eu200, eu333 and with producer Italy. 

![MondayArrival](Arrival.svg)

## operations

We call addStock on Warehouse with arrival mondayArrival.
// AddStock takes High Heels and all other products of palettes of mondayArrival 
// and addStock adds High Heels to products of Warehouse. 
// AddStock takes eu100 and all other palettes of mondayArrival and 
// addStock calls findPlace with palette eu100. 
// AddStock calls findPlace with palettes of mondayArrival.

// FindPlace checks that place of eu100 is empty and 
// findPlace takes f1 or some other from places of areas of warehouse  
// and findPlace writes f1 into place of eu100. 



## GUI
  There is a WebApp with id ForkLiftGuide 
  and with description Fork Lift Guide.
  There is a Page with id addSupplyPage 
  and with description "New Supply | button Overview".
  ForkLiftGuide has content addSupplyPage.
  There are Content with id pIdIn, productIn, itemsIn, placeIn 
  and with description "input palette id?", "input product?"
  , "input number of items?", "input place?".
  There is a Content with id addPaletteButton and with description "button submit".
  AddSupplyPage has content pIdIn, productIn, itemsIn, placeIn, addPaletteButton.
  ![ForkLiftGuide](step03.html)
  We write "eu100" into value of pIdIn.
  ![ForkLiftGuide](step04.html)
  We write Sneakers into value of productIn. 
  ![ForkLiftGuide](step05.html)
  We write "50" into value of itemsIn. 
  ![ForkLiftGuide](step06.html)
  We write "f2" into value of placeIn. 
  ![ForkLiftGuide](step07.html)
  
  
![ForkLiftGuide](step03-07.mockup.html)

![Warehouse](Tables.tables.html)
![Warehouse](Overview.yaml)
