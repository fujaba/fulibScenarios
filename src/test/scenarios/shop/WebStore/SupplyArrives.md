# Scenario Italiy delivers Shoes. 

There is the Warehouse51.

There is the WarehouseService WHService with theWarehouse Warehouse51. 

There is a ForkliftDriver with name Alice.

![WHService, Alice](step01.svg)

There is a ForkLifterApp with id ForkLiftGuide and with description "Fork Lift Guide". 
There is a Page with id addSupplyPage and with description "add new lot".
ForkLiftGuide has content addSupplyPage. 
There is a Content with id lotId and with description "input lot id?".
There is a Content with id productName and with description "input product?".
There is a Content with id lotSize and with description "input lot size?".
There is a Content with id addLotToStoreButton and with description "button add".
AddSupplyPage has content lotId, productName, lotSize, addLotToStoreButton.

![ForkLiftGuide](step02.html)


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
and with theWarehouse Warehouse51 // we need access to attributes. 
.

// AddToStore creates Lot newLot with id "lot1" from newLotId. 
AddToStore creates Lot. 
//  with id newLotId // writing "lot1" does not refer to newLotId automatically. 
// and with lotSize 20. 
AddToStore writes newLotId into id of lot. 
AddToStore writes newLotSize into lotSize of lot. 

AddToStore calls buildProduct with newProductName "Cloud Sneakers"
and with theWarehouse theWarehouse // we need access to attributes. 
.

// As WHService has no "Cloud Sneakers" in name of products of theWarehouse, 
BuildProduct creates a WarehouseProduct with id CloudSneakers and with name "Cloud Sneakers"
and with warehouse51 theWarehouse. 
// BuildProduct writes CloudSneakers into products of theWarehouse. // error setProducts instead of withProducts
BuildProduct answers with CloudSneakers. // into newProduct. 

AddToStore writes cloudSneakers into WarehouseProduct of lot.
AddToStore answers with lot.  

![WHService, Alice](step05.svg)

// We need something like sub headers or comments. 
// Second lot
We write "lot2" into value of lotId. 
![ForkLiftGuide](step06.html)



![ForkLiftGuide](step00.mockup.html)

