# Scenario Italiy delivers Shoes. 

There is the Warehouse51.
There are PalettePlace with id p23x42 and p24x42 and p25x42 
and with row 42 
and with column 23 and 24 and 25.
Warehouse51 has places p23x42 and p24x42 and p25x42. 

There is the WarehouseService WHService with theWarehouse Warehouse51. 

There is a ForkliftDriver with name Alice.

![WHService, Alice](step01.svg)

There is a ForkLifterApp with id ForkLiftGuide and with description "Fork Lift Guide". 
There is a Page with id addSupplyPage and with description "New Supply | button Tasks".
ForkLiftGuide has content addSupplyPage. 
There is a Content with id lotId and with description "input lot id?".
There is a Content with id productName and with description "input product?".
There is a Content with id lotSize and with description "input lot size?".
There is a Content with id addLotToStoreButton and with description "button add".
AddSupplyPage has content lotId, productName, lotSize, addLotToStoreButton.


There is a Page with id tasksPage and with description "button New Supply | Tasks".
There are Element with id lotIdElem, productNameElem and with text "lot42" and "Sandalettes". 
There is a Content with id lotLineByElems and with elements lotIdElem, productNameElem. 
// TasksPage has content lotLineByElems.
// ForkLiftGuide has content TasksPage. 


// Alice writes "lot1" into value of lotId.
We write "lot1" into value of lotId. 
![ForkLiftGuide](step03.html)

We write "Cloud Sneakers" into value of productName. 
We write "20" into value of lotSize.
![ForkLiftGuide](step04.html)

// Alice calls action on addLotToStoreButton. 
// We call addToStore on WarehouseService. ==> class String  
// We call addToStore on WHService with newLotId "lot1" from value of lotId.
We call addToStore on WHService with newLotId "lot1"
and with newProductName "Cloud Sneakers" and with newLotSize 20 
and with theWarehouse Warehouse51. // we need access to attributes. 


// AddToStore creates Lot newLot with id "lot1" from newLotId. 
AddToStore creates Lot. 
//  with id newLotId // writing "lot1" does not refer to newLotId automatically. 
// and with lotSize 20. 
AddToStore writes "lot1" from newLotId into id of lot. 
AddToStore writes newLotSize into lotSize of lot. 

AddToStore calls buildProduct with newProductName "Cloud Sneakers"
and with theWarehouse theWarehouse. // we need access to attributes. 


// As WHService has no "Cloud Sneakers" in name of products of theWarehouse, 
BuildProduct creates a WarehouseProduct with id CloudSneakers and with name "Cloud Sneakers"
and with warehouse51 theWarehouse. 
// BuildProduct writes CloudSneakers into products of theWarehouse. // error setProducts instead of withProducts
BuildProduct answers with CloudSneakers. // into newProduct. 

AddToStore writes cloudSneakers into WarehouseProduct of lot.
AddToStore answers with lot.  
We write p23x42 into place of lot. 
![WHService, Alice](step05.svg)



// we add a store at place task for the new lot
There is a Content with id lot1Line and with description "lot1 | Cloud Sneakers | 20 | p23x42 | button Done".
We write lot1Line into content of tasksPage.
// We write tasksPage into content of ForkLiftGuide.
![ForkLiftGuide](step06.html)

// We need something like sub headers or comments. 
// Second lot

We write "" into value of lotId. 
We write addSupplyPage into content of ForkLiftGuide.
![ForkLiftGuide](step07.html)

We write "lot2" into value of lotId. 
We write "Ground Boots" into value of productName.
![ForkLiftGuide](step08.html)

We call addToStore on WHService with newLotId "lot2"
and with newProductName "Ground Boots" and with newLotSize 20 
and with theWarehouse Warehouse51. // we need access to attributes. 
![WHService, Alice](step09.svg)

There is a Content with id lot2Line and with description "lot2 | Ground Boots | 20 | p24x42 | button Done".
We write lot2Line into content of tasksPage.
We write tasksPage into content of ForkLiftGuide.
![ForkLiftGuide](step10.html)



![WHService, Alice](step09.tables.html)


![ForkLiftGuide](step00.mockup.html)

