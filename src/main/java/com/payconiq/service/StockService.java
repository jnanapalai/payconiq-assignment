package com.payconiq.service;

import com.payconiq.exception.StockException;
import com.payconiq.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Service class for various stock operation execution
 */

public interface StockService {

    /**
     * Method to get the list of stock
     *
     * @return list of Stock
     */
    public List<Stock> getListOfStock();

    /**
     * Method to create stock
     *
     * @param stock details of the stock
     * @return Stock created stock details
     */
    public Stock createStock(Stock stock);

    /**
     * Method to get the details of the stock
     *
     * @param id stock id
     * @return Stock details of the stock
     */
    public Stock getStockDetails(Long id) throws StockException;

    /**
     * Update the stock details
     *
     * @param stock Stock details that need to be updated
     * @return updated stock details
     */
    public Stock updateStock(Stock stock) throws StockException;

    /**
     * Delete the stock
     *
     * @param id id of the stock that need to be updated
     */
    public void deleteStock(Long id) throws StockException;

}
