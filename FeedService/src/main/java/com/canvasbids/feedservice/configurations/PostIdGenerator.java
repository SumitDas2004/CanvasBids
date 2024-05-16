package com.canvasbids.feedservice.configurations;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostIdGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        String prefix = "#post:";
        int base=1000000;
        try (Connection connection = session.getSession().getJdbcConnectionAccess().obtainConnection()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(id) FROM post");
            if(rs.next()){
                int cnt = rs.getInt(1);
                base+=cnt;
            }
            return prefix+base;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
