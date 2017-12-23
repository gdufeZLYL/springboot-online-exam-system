package com.zzqnxx.dao.impl;

import com.zzqnxx.common.Penguin;
import com.zzqnxx.dao.TeacherDao;
import com.zzqnxx.model.Teacher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("teacherDao")
public class TeacherDaoImpl implements TeacherDao {

    private static Log LOG = LogFactory.getLog(TeacherDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertTeacher(Teacher teacher) {
        StringBuilder sb = new StringBuilder(String.format("insert into %s " +
                "(teacher_id, password, teacher_name) " +
                "values (?, ?, ?)", Penguin.TABLE_EXAM_TEACHER));
        int id = jdbcTemplate.update(sb.toString(), teacher.getTeacherId(), teacher.getPassword(),
                teacher.getTeacherName());
        return id;
    }

    @Override
    public boolean deleteTeacher(int id) {
        StringBuilder sb = new StringBuilder(String.format("delete from %s " +
                "where id = ? ", Penguin.TABLE_EXAM_TEACHER));
        return jdbcTemplate.update(sb.toString(), id) > 0;
    }

    @Override
    public boolean updateTeacher(int id, String password) {
        StringBuilder sb = new StringBuilder(String.format("update %s set password = ? " +
                "where id = ? ", Penguin.TABLE_EXAM_TEACHER));
        return jdbcTemplate.update(sb.toString(), password, id) > 0;
    }

    @Override
    public Teacher selectTeacherByTcIdAndPwd(String teacherId, String password) {
        StringBuilder sb = new StringBuilder(String.format("select %s from %s " +
                "where teacher_id = ? and password = ? ", Penguin.TABLE_EXAM_TEACHER));
        Teacher teacher = jdbcTemplate.queryForObject(sb.toString(),
                new Object[]{teacherId, password}, new TeacherRowMapper());
        return teacher;
    }

    @Override
    public Teacher selectTeacherByTcId(String teacherId) {
        StringBuilder sb = new StringBuilder(String.format("SELECT %s from %s " +
                "where teacher_id = ? ", Penguin.TABLE_EXAM_TEACHER));
        Teacher teacher = jdbcTemplate.queryForObject(sb.toString(),
                new Object[]{teacherId}, new TeacherRowMapper());
        return teacher;
    }

    private Teacher warpTeacher(ResultSet resultSet) {
        try {
            Teacher teacher = new Teacher();
            teacher.setId(resultSet.getInt("id"));
            teacher.setTeacherId(resultSet.getString("teacher_id"));
            teacher.setPassword(resultSet.getString("password"));
            teacher.setTeacherName(resultSet.getString("teacher_name"));
            teacher.setCreateTime(resultSet.getTimestamp("create_time"));
            return teacher;
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
        return null;
    }

    private class TeacherRowMapper implements RowMapper<Teacher> {
        @Override
        public Teacher mapRow(ResultSet resultSet, int i) throws SQLException {
            Teacher teacher = warpTeacher(resultSet);
            return teacher;
        }
    }
}
