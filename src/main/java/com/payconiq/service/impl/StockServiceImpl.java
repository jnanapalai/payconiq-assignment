package com.payconiq.service.impl;

import com.payconiq.exception.StockException;
import com.payconiq.model.Stock;
import com.payconiq.repository.StockRepository;
import com.payconiq.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private EntityManager entityManager;

    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<Stock> getListOfStock() {
        return stockRepository.findAll();
    }

    @Override
    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock getStockDetails(Long id) throws StockException {

      Optional<Stock> stockDetails= stockRepository.findById(id);
      if(stockDetails.isEmpty())
      {
          throw new StockException("Stock Details Not Found","STOCK_NOT_EXIST");
      }
      return stockDetails.get();
    }

    @Override
    @Transactional(timeout = 300)
    public Stock updateStock(Stock s) throws StockException {


                Optional<Stock> stockFromDB = getStockFromDB(s.getId());
                if (stockFromDB.isEmpty()) {
                    throw new StockException("Stock Details Not Found", "STOCK_NOT_EXIST");
                }
                Stock stock = stockFromDB.get();
                stock.setCurrentPrice(s.getCurrentPrice());
                stockRepository.save(stock);
                return stock;
    }

    @Override
    @Transactional(timeout = 300)
    public void deleteStock(Long id) throws StockException {


        Optional<Stock> stockFromDB=getStockFromDB(id);
        if(stockFromDB.isEmpty())
        {
            throw new StockException("Stock Details Not Found","STOCK_NOT_EXIST");
        }
        stockRepository.deleteById(id);
    }

    private  Optional<Stock> getStockFromDB(Long id)
    {
        return stockRepository.findStockWithLock(id);
    }
}
