/*
 This stored procedure will check the Product table for any stocks that are at 3 or less inventory size and restock that
 inventory to an amount provided to the stored procedure, calculating the total expected price for the restock based on
 individual product price.
 */
 create PROC dbo.CalculateRestockPrices @RestockAmount int as
    set NOCOUNT ON;
     select prod.id,
            prod.sku,
            prod.description,
            (@RestockAmount - prod.inventoryStock) as missingStock,
            ((@RestockAmount - prod.inventoryStock) * prod.price) as restockPrice
     from Products prod
     where prod.inventoryStock < 3;
