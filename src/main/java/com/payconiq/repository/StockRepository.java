package com.payconiq.repository;

import com.payconiq.model.Stock;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

/**
 * Stock repository to interact with the database
 */
@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {


    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    @Query(" select s from Stock s where s.id = :id")
    Optional<Stock>  findStockWithLock(@Param("id") Long id);
}
