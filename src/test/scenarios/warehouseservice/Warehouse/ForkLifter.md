# Scenario new supply. 

There is a WarehouseService with name WHServer. 

There is a WebApp with id ForkLifterApp and with description Forklift Guide. 

We call init on WHServer. 

Init creates a WebApp with id ForkLifterApp and with description Forklift Guide. 
Init writes ForkLifterApp into appRoot of WHServer.
Init creates a Page with id addSupplyPage and with description "New Supply | button Tasks".
Init writes addSupplyPage into content of ForkLifterApp.
Init creates a Content with id lotId and with description "input lot id?".
Init creates a Content with id productName and with description "input product?".
Init creates a Content with id lotSize and with description "input lot size?".
Init creates a Content with id addLotToStoreButton and with description "button add".
Init writes lotId, productName, lotSize, addLotToStoreButton into content of addSupplyPage.
Init writes "addToStock lotId productName lotSize" into action of addLotToStoreButton.
 