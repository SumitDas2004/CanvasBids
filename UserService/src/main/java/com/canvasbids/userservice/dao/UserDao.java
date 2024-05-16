package com.canvasbids.userservice.dao;

import com.canvasbids.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends JpaRepository<User, String> {
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.picture=?1 WHERE u.email=?2")
    void updatePicture(String picture, String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.picture=?1 WHERE u.email=?2")
    void updateName(String name, String email);
}
