package com.zzqnxx.dao.impl;

import com.zzqnxx.common.Penguin;
import com.zzqnxx.dao.GradeDao;
import com.zzqnxx.model.Grade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public int deleteGrade(int id) {
        return 0;
    }

    @Override
    public int updateGrade(Grade grade) {
        return 0;
    }

    @Override
    public int queryCountBySId(int sId) {
        return 0;
    }

    @Override
    public List<Grade> queryBySId(int sId, int offset, int limit) {
        return null;
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
}
