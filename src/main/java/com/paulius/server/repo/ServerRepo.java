package com.paulius.server.repo;

import com.paulius.server.model.ServerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepo extends JpaRepository<ServerModel,Long> {
    ServerModel findByIpAddress(String ipAddress);

}
