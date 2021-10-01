package com.paulius.server.service;

import com.paulius.server.enumeration.Status;
import com.paulius.server.model.ServerModel;
import com.paulius.server.repo.ServerRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService{

    private final ServerRepo serverRepo;

    @Override
    public ServerModel create(ServerModel server) {
        log.info("Saving new server: {}",server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public ServerModel ping(String ipAddress) throws IOException {
        log.info("Pinging server ip: {}", ipAddress);
        ServerModel serverModel = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        serverModel.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepo.save(serverModel);
        return serverModel;
    }

    @Override
    public Collection<ServerModel> list(int limit) {
        log.info("Fetching all Servers");
        return serverRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public ServerModel get(Long id) {
        log.info("Fetching Server by Id: {}",id);
        return serverRepo.findById(id).get();
    }

    @Override
    public ServerModel update(ServerModel server) {
        log.info("Updating server: {}",server);
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by id: {}", id);
        serverRepo.deleteById(id);
        return Boolean.TRUE;

    }

    private String setServerImageUrl() {
        String[] imageNames = {"server1.png","server2.png","server3.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image" +imageNames[new Random().nextInt(3)]).toUriString();

    }
}
