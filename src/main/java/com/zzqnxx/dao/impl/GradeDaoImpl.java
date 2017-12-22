package com.zzqnxx.dao.impl;

import com.zzqnxx.common.Penguin;
import com.zzqnxx.dao.GradeDao;
import com.zzqnxx.model.Grade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GradeDaoImpl implements GradeDao {

    private static Log LOG = LogFactory.getLog(GradeDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertGrade(Grade grade) {
        String sql = String.format("insert into %s " +
                " (s_id, paper_id, score, single_score, mul_score, other_score, answer_json) " +
                " values (?, ?, ?, ?, ?, ?, ?) ", Penguin.TABLE_EXAM_GRADE);
        int gradeId = jdbcTemplate.update(sql, grade.getsId(), grade.getPaperId(), grade.getScore(),
                grade.getSingleScore(), grade.getMulScore(), grade.getOtherScore(),
                grade.getAnswerJson());
        return gradeId;
    }

    @Override
    public boolean deleteGrade(int id) {
        String sql = String.format("delete from %s where id = ? ", Penguin.TABLE_EXAM_GRADE);
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public boolean updateGrade(Grade grade) {
        String sql = String.format("update %s set s_id = ?, paper_id = ?, score = ?, " +
                "single_score = ?, mul_score = ?, other_score = ?, " +
                "answer_json = ? where id = ? ", Penguin.TABLE_EXAM_GRADE);
        return jdbcTemplate.update(sql, grade.getsId(), grade.getPaperId(),
                grade.getScore(), grade.getSingleScore(), grade.getMulScore(),
                grade.getOtherScore(), grade.getAnswerJson(), grade.getId()) > 0;
    }

    @Override
    public int queryCountBySId(int sId) {
        String sql = String.format("select count(1) as count from %s " +
                "where s_id = ? ", Penguin.TABLE_EXAM_GRADE);
        List<Object> param = new ArrayList<>();
        param.add(sId);
        Integer count = jdbcTemplate.queryForObject(sql, param.toArray(), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer count = resultSet.getInt("count");
                return count;
            }
        });
        return count;
    }

    @Override
    public List<Grade> queryBySId(int sId, int page, int num) {
        StringBuilder sb = new StringBuilder(String.format("select a.id, a.s_id, a.paper_id, a.score, " +
                "a.single_score, a.mul_score, a.other_score, a.create_time, " +
                "a.answer_json, b.paper_name from %s a, %s b " +
                "where s_id = ? and a.paper_id = b.id ", Penguin.TABLE_EXAM_GRADE, Penguin.TABLE_EXAM_PAPER));
        sb.append("order by a.create_time desc limit " + num * (page - 1) + "," + num);
        List<Object> param = new ArrayList<>();
        param.add(sId);
        List<Grade> grades = jdbcTemplate.query(sb.toString(), param.toArray(), new GradeRowMapper());
        return grades;
    }

    @Override
    public int queryCountByPaperId(int paperId) {
        return 0;
    }

    @Override
    public List<Grade> queryByPaperId(int paperId, int offset, int limit) {
        return null;
    }

    @Override
    public int queryCountByPaperId2StuId2StuName(int paperId, String studentId, String studentName) {
        return 0;
    }

    @Override
    public List<Grade> queryByPaperId2StuId2StuName(int paperId, String studentId, String studentName, int offset, int limit) {
        return null;
    }

    @Override
    public int queryCountBySIdsAndPIds(List<Integer> sIds, List<Integer> paperIds) {
        return 0;
    }

    @Override
    public List<Grade> queryBySIdsAndPIds(List<Integer> sIds, List<Integer> paperIds, int offset, int limit) {
        return null;
    }

    private Grade warpGrade(ResultSet resultSet) {
        try {
            Grade grade = new Grade();
            grade.setId(resultSet.getInt("id"));
            grade.setsId(resultSet.getInt("s_id"));
            grade.setPaperId(resultSet.getInt("paper_id"));
            grade.setScore(resultSet.getInt("score"));
            grade.setSingleScore(resultSet.getInt("single_score"));
            grade.setMulScore(resultSet.getInt("mul_score"));
            grade.setOtherScore(resultSet.getInt("other_score"));
            grade.setCreateTime(resultSet.getTimestamp("create_time"));
            grade.setAnswerJson(resultSet.getString("answer_json"));
            return grade;
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
        return null;
    }

    private class GradeRowMapper implements RowMapper<Grade> {
        @Override
        public Grade mapRow(ResultSet resultSet, int i) throws SQLException {
            Grade grade = warpGrade(resultSet);
            return grade;
        }
    }

    private class GradeCountRowMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            Integer count = resultSet.getInt("count");
            return count;
        }
    }
}
