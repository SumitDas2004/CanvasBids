package com.canvasbids.bid.service;

import com.canvasbids.bid.dao.BidDao;
import com.canvasbids.bid.dto.BidRequestDTO;
import com.canvasbids.bid.entity.Bid;
import com.canvasbids.bid.entity.BidId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BidService {
    @Autowired
    BidDao bidDao;

    public void doBid(BidRequestDTO request, String username){
        Optional<Bid> bid = bidDao.findById(BidId.builder().postId(request.getPostId()).userId(username).build());
        if(bid.isEmpty()){
            createBid(request, username);
        }else updateBid(request, username);
    }

    private void updateBid(BidRequestDTO request, String username) {
        bidDao.updateBid(BidId.builder().userId(username).postId(request.getPostId()).build(), request.getAmount());
    }

    private void createBid(BidRequestDTO request, String username) {
        Bid bid = Bid.builder()
                .name(request.getName())
                .picture(request.getPicture())
                .amount(request.getAmount())
                .postId(request.getPostId())
                .build();
        bid.setUserId(username);
        bidDao.save(bid);
    }
}
