package com.engeto.project3.clientmanagement.config;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class Writer implements ItemWriter<ClientInfo> {

    @Override
    public void write(Chunk<? extends ClientInfo> chunk) throws Exception {

    }
}
