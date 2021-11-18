package com.payconiq.service.impl;

import com.payconiq.exception.StockException;
import com.payconiq.model.Stock;
import com.payconiq.repository.StockRepository;
import com.payconiq.service.StockService;
import com.payconiq.test.StockTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit Test class having all unit test case
 *
 */
@SpringBootTest(classes = {StockServiceImpl.class, StockTestConfiguration.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class StockServiceImplTest {

    @Autowired
    StockService stockService;


    /**
     *  Test case for creating stock
     */
    @Test
    public void createStockTest()
    {
        Stock s=new Stock();

        s.setCurrentPrice(8.5d);
        s.setName("demostock");
        s.setLastUpdate(OffsetDateTime.now());

        Stock createdStock=stockService.createStock(s);
        assertNotNull(createdStock);
        assertEquals("demostock",createdStock.getName());
        assertNotNull(createdStock.getId());
        assertEquals(8.5d,createdStock.getCurrentPrice());
        assertNotNull(createdStock.getLastUpdate());
    }

    /**
     * Test case for getting all stock
     */
    @Test
    public void getAllStock_Test()
    {
     List<Stock> stockList=stockService.getListOfStock();
     assertNotNull(stockList);
     assertEquals(4,stockList.size());
     stockList.forEach(stock->{
         assertNotNull(stock.getId());
         assertNotNull(stock.getName());
         assertNotNull(stock.getCurrentPrice());
         assertNotNull(stock.getLastUpdate());
     });
    }

    /**
     * test case for getting the stock details
     * @throws StockException
     */
    @Test
    public void getDetailsStock_Test() throws StockException {
        Stock s=stockService.getStockDetails(3L);
        assertNotNull(s);
        assertEquals(3,s.getId());
        assertEquals("DEMOSTOCK3",s.getName());
        assertEquals(54.55,s.getCurrentPrice());
        assertNotNull(s.getLastUpdate());
    }

    /**
     * Test case for updating the stock details
     * @throws StockException
     */
    @Test
    public void updateStockDetails_Test() throws StockException {
        Stock s = new Stock();
        s.setId(3);
        s.setCurrentPrice(43.45);
        Stock updatedStock = stockService.updateStock(s);
        assertNotNull(updatedStock);
    }

    /**
     * Test case for deleting stock
     * @throws StockException
     */
    @Test
    public void deleteStock_Test() throws StockException {
        stockService.deleteStock(1L);
        StockException exception=assertThrows(StockException.class,()->stockService.getStockDetails(1L));
        assertEquals("STOCK_NOT_EXIST",exception.getErrorCode());
        assertEquals("Stock Details Not Found",exception.getErrorMessage());
    }
}


