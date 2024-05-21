package com.canvasbids.bid.dao;

import com.canvasbids.bid.entity.Bid;
import com.canvasbids.bid.entity.BidId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BidDao extends JpaRepository<Bid, BidId> {
    @Modifying
    @Transactional
    @Query("UPDATE Bid b SET b.amount=?2 WHERE b.id=?1")
    void updateBid(BidId id, long amount);

    @Query("SELECT b FROM Bid b WHERE b.id=?1")
    List<Bid> getBids(BidId id, PageRequest pageRequest);

    @Modifying
    @Transactional
    @Query("UPDATE Bid b SET b.isWinner = true WHERE b.id=?1")
    void declareWinner(BidId bidId);
}
