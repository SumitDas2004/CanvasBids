package com.canvasbids.bid.service;

import com.canvasbids.bid.dao.BidDao;
import com.canvasbids.bid.dao.PostFeignDao;
import com.canvasbids.bid.dao.UserFeignDao;
import com.canvasbids.bid.dto.BidRequestDTO;
import com.canvasbids.bid.entity.Bid;
import com.canvasbids.bid.entity.BidId;
import com.canvasbids.bid.exception.InvalidBidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BidService {
    @Autowired
    BidDao bidDao;

    @Autowired
    UserFeignDao userFeignDao;

    @Autowired
    PostFeignDao postFeignDao;

    public void doBid(BidRequestDTO request, String username) throws InvalidBidException {
        //Verifies if the bid is valid. i.e. there is an ongoing bid and bid amount is more than the minimumBidValue.
        if(!postFeignDao.isValidBid(request.getPostId(), request.getAmount()))throw new InvalidBidException();

        Optional<Bid> bid = bidDao.findById(BidId.builder().postId(request.getPostId()).userId(username).build());
        if(bid.isEmpty())
            createBid(request, username);
        else
            updateBid(request, username);

        postFeignDao.updateTimer(request.getPostId());
    }

    public List<Bid> getBid(int offset, int size, String postId, String username){
        return bidDao.getBids(BidId.builder().userId(username).postId(postId).build(), PageRequest.of(offset, size).withSort(Sort.Direction.DESC, "amount"));
    }

    private void updateBid(BidRequestDTO request, String username) {
        bidDao.updateBid(BidId.builder().userId(username).postId(request.getPostId()).build(), request.getAmount());
    }

    private void createBid(BidRequestDTO request, String username) {
        Bid bid = toBid(request, username);
        bid.setUserId(username);
        bidDao.save(bid);
    }

    private Bid toBid(BidRequestDTO request, String username){
        Map<String, String> nameAndPicture = userFeignDao.getNameAndPicture(username);
        String name = nameAndPicture.get("name");
        String picture = nameAndPicture.get("picture");

        return Bid.builder()
                .name(name)
                .picture(picture)
                .postId(request.getPostId())
                .amount(request.getAmount())
                .build();
    }

    public void declareWinner(BidId bidId){
        bidDao.declareWinner(bidId);
    }

}
