package com.zzqnxx.dao.impl;

import com.zzqnxx.common.Penguin;
import com.zzqnxx.dao.AdminDao;
import com.zzqnxx.model.Admin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("adminDao")
public class AdminDaoImpl implements AdminDao {

    private static Log LOG = LogFactory.getLog(AdminDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean updatePassword(int id, String password) {
        StringBuilder sb = new StringBuilder(String.format("update %s set password = ? " +
                "where id = ? ", Penguin.TABLE_EXAM_ADMIN));
        return jdbcTemplate.update(sb.toString(), password, id) > 0;
    }

    @Override
    public Admin queryAdminByUsernameAndPassword(String username, String password) {
        StringBuilder sb = new StringBuilder(String.format("select %s from %s " +
                "where username = ? and password = ? ", Penguin.TABLE_EXAM_TEACHER));
        Admin admin = jdbcTemplate.queryForObject(sb.toString(),
                new Object[]{username, password}, new AdminRowMapper());
        return admin;
    }

    @Override
    public Admin queryAdminByUsername(String username) {
        StringBuilder sb = new StringBuilder(String.format("select %s from %s " +
                "where username = ? ", Penguin.TABLE_EXAM_TEACHER));
        Admin admin = jdbcTemplate.queryForObject(sb.toString(),
                new Object[]{username}, new AdminRowMapper());
        return admin;
    }

    private Admin warpAdmin(ResultSet resultSet) {
        try {
            Admin admin = new Admin();
            admin.setId(resultSet.getInt("id"));
            admin.setUsername(resultSet.getString("username"));
            admin.setPassword(resultSet.getString("password"));
            admin.setName(resultSet.getString("name"));
            admin.setLevel(resultSet.getInt("level"));
            admin.setCreateTime(resultSet.getTimestamp("create_time"));
            return admin;
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
        return null;
    }

    private class AdminRowMapper implements RowMapper<Admin> {
        @Override
        public Admin mapRow(ResultSet resultSet, int i) throws SQLException {
            Admin admin = warpAdmin(resultSet);
            return admin;
        }
    }
}
