package com.payconiq.controller;


import com.payconiq.exception.StockException;
import com.payconiq.model.Stock;
import com.payconiq.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class responsible to take request and give response to the user
 */

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin
public class StockController {

   private final StockService stockService;

    /**
     * public constructor to auto wire require dependencies
     *
     * @param stockService reference of StockService
     */
    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Method to get list of Stock
     *
     * @return list of stock already created
     */


    @GetMapping
    public ResponseEntity<List<Stock>> getListOfStock()
    {
        return new ResponseEntity<>(stockService.getListOfStock(), HttpStatus.OK);
    }

    /**
     * method to create stock
     *
     * @param stock refence of Stock(details of stock that need to be created)
     * @return created Stock details
     */
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock)
    {
        return new ResponseEntity<>(stockService.createStock(stock), HttpStatus.CREATED);

    }

    /**
     * method to get details of the stock
     *
     * @param id stock id
     * @return details of the stock
     */
    @GetMapping("{id}")
    public ResponseEntity<Stock> getStockDetails(@PathVariable("id") Long id) throws StockException {
        return new ResponseEntity<>(stockService.getStockDetails(id), HttpStatus.OK);

    }

    /**
     * method to update the stock
     *
     * @param stock stock details that need to be updated
     * @return Stock details after updated
     */
    @PutMapping("{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id,@RequestBody Stock stock) throws StockException {
       stock.setId(id);
       return new ResponseEntity<>(stockService.updateStock(stock),HttpStatus.OK);
    }

    /**
     * method to delete the stock
     *
     * @param id id of the stock
     * @return No Content with status code 204
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable("id") Long id) throws StockException {
        stockService.deleteStock(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
