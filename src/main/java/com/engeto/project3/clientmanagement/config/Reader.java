package com.engeto.project3.clientmanagement.config;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class Reader extends JdbcCursorItemReader<ClientInfo> implements ItemReader<ClientInfo> {

    public Reader(@Autowired DataSource primaryDataSource) {
        setDataSource(primaryDataSource);
        setSql("SELECT * FROM client_management.client_info");
        setFetchSize(100);
        setRowMapper(new ClientRowMapper());
    }

}
