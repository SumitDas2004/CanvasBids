package com.canvasbids.bid.dao;

import com.canvasbids.bid.entity.Bid;
import com.canvasbids.bid.entity.BidId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BidDao extends JpaRepository<Bid, BidId> {
    @Query("UPDATE Bid b SET b.amount=?2 WHERE b.id=?1")
    void updateBid(BidId id, long amount);
}
