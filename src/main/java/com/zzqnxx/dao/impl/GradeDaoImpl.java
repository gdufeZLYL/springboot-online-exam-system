package com.zzqnxx.dao.impl;

import com.zzqnxx.common.Penguin;
import com.zzqnxx.dao.GradeDao;
import com.zzqnxx.model.Grade;
import com.zzqnxx.model.Paper;
import com.zzqnxx.model.Student;
import com.zzqnxx.utils.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
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

@Repository("gradeDao")
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
        StringBuilder sb = new StringBuilder(String.format("select a.*, b.paper_name from %s a, %s b " +
                "where s_id = ? and a.paper_id = b.id ", Penguin.TABLE_EXAM_GRADE, Penguin.TABLE_EXAM_PAPER));
        sb.append("order by a.create_time desc limit " + num * (page - 1) + "," + num);
        List<Object> param = new ArrayList<>();
        param.add(sId);
        List<Grade> grades = jdbcTemplate.query(sb.toString(), param.toArray(), new RowMapper<Grade>() {
            @Override
            public Grade mapRow(ResultSet resultSet, int i) throws SQLException {
                Grade grade = warpGrade(resultSet);
                Paper paper = new Paper();
                paper.setPaperName(resultSet.getString("paper_name"));
                grade.setPaper(paper);
                return grade;
            }
        });
        return grades;
    }

    @Override
    public int queryCountByPaperId(int paperId) {
        String sql = String.format("select count(1) as count from %s where paper_id = ? ",
                Penguin.TABLE_EXAM_GRADE);
        List<Object> param = new ArrayList<>();
        param.add(paperId);
        Integer count = jdbcTemplate.queryForObject(sql, param.toArray(), new GradeCountRowMapper());
        return count;
    }

    @Override
    public List<Grade> queryByPaperId(int paperId, int page, int num) {
        StringBuilder sb = new StringBuilder(String.format("select a.*, " +
                "b.student_name ,b.profession from %s a, %s b " +
                "where a.paper_id = ? and a.s_id = b.id ", Penguin.TABLE_EXAM_GRADE, Penguin.TABLE_EXAM_STUDENT));
        sb.append("order by a.create_time desc limit " + num * (page - 1) + "," + num);
        List<Object> param = new ArrayList<>();
        param.add(paperId);
        List<Grade> grades = jdbcTemplate.query(sb.toString(), param.toArray(), new GradeAndStudentRowMapper());
        return grades;
    }

    @Override
    public int queryCountByPaperId2StuId2StuName(int paperId, String studentId, String studentName) {
        StringBuilder sb = new StringBuilder(String.format("select count(1) as count " +
                "from %s a, %s b " +
                "where a.paper_id = ? AND a.s_id = b.id ",
                Penguin.TABLE_EXAM_GRADE, Penguin.TABLE_EXAM_STUDENT));
        List<Object> param = new ArrayList<>();
        param.add(paperId);
        if (StringUtils.isNotEmpty(studentId)) {
            sb.append(" and b.student_id = ? ");
            param.add(studentId);
        }
        if (StringUtils.isNotEmpty(studentName)) {
            sb.append(" and b.student_name like ? ");
            param.add("%"+studentName+"%");
        }
        Integer count = jdbcTemplate.queryForObject(sb.toString(), param.toArray(),
                new GradeCountRowMapper());
        return count;
    }

    @Override
    public List<Grade> queryByPaperId2StuId2StuName(int paperId, String studentId, String studentName, int page, int num) {
        StringBuilder sb = new StringBuilder(String.format("select a.*, b.student_name, b.profession from %s a, %s b " +
                        "where a.paper_id = ? AND a.s_id = b.id ",
                Penguin.TABLE_EXAM_GRADE, Penguin.TABLE_EXAM_STUDENT));
        List<Object> param = new ArrayList<>();
        param.add(paperId);
        if (StringUtils.isNotEmpty(studentId)) {
            sb.append(" and b.student_id = ? ");
            param.add(studentId);
        }
        if (StringUtils.isNotEmpty(studentName)) {
            sb.append(" and b.student_name like ? ");
            param.add("%"+studentName+"%");
        }
        sb.append("order by a.create_time desc limit "+num*(page-1)+", "+num);
        List<Grade> grades = jdbcTemplate.query(sb.toString(), param.toArray(),
                new GradeAndStudentRowMapper());
        return grades;
    }

    @Override
    public int queryCountBySIdsAndPIds(List<Integer> sIds, List<Integer> paperIds) {
        StringBuilder sb = new StringBuilder(String.format("select count(1) as count from %s " +
                "WHERE 1 = 1", Penguin.TABLE_EXAM_GRADE));
        if (sIds != null && sIds.size() > 0) {
            sb.append(String.format(" and s_id in (%s) ",
                    CollectionUtil.list2String(sIds, ", ")));
        } else {
            sb.append(" and 1 = 0 ");
        }
        if (paperIds != null && paperIds.size() > 0) {
            sb.append(String.format(" and paper_id in (%s) ",
                    CollectionUtil.list2String(paperIds, ", ")));
        } else {
            sb.append(" and 1 = 0 ");
        }
        Integer count = jdbcTemplate.queryForObject(sb.toString(), new GradeCountRowMapper());
        return count;
    }

    @Override
    public List<Grade> queryBySIdsAndPIds(List<Integer> sIds, List<Integer> paperIds, int page, int num) {
        StringBuilder sb = new StringBuilder(String.format("select * from %s " +
                "WHERE 1 = 1", Penguin.TABLE_EXAM_GRADE));
        if (sIds != null && sIds.size() > 0) {
            sb.append(String.format(" and s_id in (%s) ",
                    CollectionUtil.list2String(sIds, ", ")));
        } else {
            sb.append(" and 1 = 0 ");
        }
        if (paperIds != null && paperIds.size() > 0) {
            sb.append(String.format(" and paper_id in (%s) ",
                    CollectionUtil.list2String(paperIds, ", ")));
        } else {
            sb.append(" and 1 = 0 ");
        }
        List<Grade> grades = jdbcTemplate.query(sb.toString(), new GradeRowMapper());
        return grades;
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

    private class GradeAndStudentRowMapper implements RowMapper<Grade> {
        @Override
        public Grade mapRow(ResultSet resultSet, int i) throws SQLException {
            Grade grade = warpGrade(resultSet);
            Student student = new Student();
            student.setStudentName(resultSet.getString("student_name"));
            student.setProfession(resultSet.getString("profession"));
            grade.setStudent(student);
            return grade;
        }
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
