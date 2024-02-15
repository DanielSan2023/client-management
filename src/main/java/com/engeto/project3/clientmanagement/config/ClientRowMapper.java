package com.engeto.project3.clientmanagement.config;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<ClientInfo> {

    @Override
    public ClientInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        ClientInfo client = new ClientInfo();

        client.setId(rs.getLong("id"));
        client.setClientName(rs.getString("client_name"));
        client.setCompanyName(rs.getString("company_name"));
        client.setAddress(rs.getString("address"));
        client.setEmail(rs.getString("email"));

        return client;
    }
}