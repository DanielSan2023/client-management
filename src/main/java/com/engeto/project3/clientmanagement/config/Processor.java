package com.engeto.project3.clientmanagement.config;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<ClientInfo, ClientInfo> {

    @Override
    public ClientInfo process(ClientInfo item) throws Exception {
        return null;
    }
}
