package com.canvasbids.userservice.dao;

import com.canvasbids.userservice.entity.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRequestDao extends JpaRepository<ConnectionRequest, String> {
}
