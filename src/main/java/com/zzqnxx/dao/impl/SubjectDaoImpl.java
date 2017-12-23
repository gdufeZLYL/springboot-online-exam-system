package com.zzqnxx.dao.impl;

import com.zzqnxx.common.Penguin;
import com.zzqnxx.dao.SubjectDao;
import com.zzqnxx.model.Grade;
import com.zzqnxx.model.Paper;
import com.zzqnxx.model.Subject;
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

@Repository("subjectDao")
public class SubjectDaoImpl implements SubjectDao {

    private static Log LOG = LogFactory.getLog(SubjectDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertSubject(Subject subject) {
        String sql = String.format("insert into %s (title, option_a, option_b, option_c, " +
                "option_d, answer, parse, title_type, paper_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)", Penguin.TABLE_EXAM_SUBJECT);
        int subjectId = jdbcTemplate.update(sql, subject.getTitle(), subject.getOptionA(),
                subject.getOptionB(), subject.getOptionC(), subject.getOptionC(),
                subject.getOptionD(), subject.getAnswer(), subject.getParse(),
                subject.getTitleType(), subject.getPaperId());
        return subjectId;

    }

    @Override
    public boolean deleteSubject(int id) {
        String sql = String.format("delete from %s where id = ? ", Penguin.TABLE_EXAM_SUBJECT);
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public boolean updateSubject(Subject subject) {
        String sql = String .format("update %s set title = ?, option_a = ?, option_b = ?, " +
                "option_c = ?, option_d = ?, answer = ?, parse = ?, title_type = ?, " +
                "paper_id = ? where id = ? ", Penguin.TABLE_EXAM_SUBJECT);
        return jdbcTemplate.update(sql, subject.getTitle(), subject.getOptionA(), subject.getOptionB(),
                subject.getOptionC(), subject.getOptionD(), subject.getAnswer(), subject.getParse(),
                subject.getTitleType(), subject.getPaperId(), subject.getId()) > 0;
    }

    @Override
    public int queryAllCount() {
        String sql = String.format("select count(1) as count from %s", Penguin.TABLE_EXAM_SUBJECT);
        Integer count = jdbcTemplate.queryForObject(sql, new SubjectCountRowMapper());
        return count;
    }

    @Override
    public List<Subject> queryAll(int page, int num) {
        StringBuilder sb = new StringBuilder(String.format("select a.*,b.paper_name from %s a, %s b " +
                "where a.paper_id = b.id ", Penguin.TABLE_EXAM_SUBJECT, Penguin.TABLE_EXAM_PAPER));
        sb.append(" order by a.id limit "+num*(page-1)+", "+num);
        List<Subject> subjects = jdbcTemplate.query(sb.toString(), new SubjectAndPaperRowMapper());
        return subjects;
    }

    @Override
    public List<Subject> queryByPaperId2TitleType(int paperId, int titleType) {
        StringBuilder sb = new StringBuilder(String.format("select * from %s where paper_id = ? and title_type = ? ", Penguin.TABLE_EXAM_SUBJECT));
        List<Subject> subjects = jdbcTemplate.query(sb.toString(), new Object[]{paperId, titleType}, new SubjectRowMapper());
        return subjects;
    }

    @Override
    public int queryCountByTitle(String title) {
        StringBuilder sb = new StringBuilder(String.format("select count(1) as count from %s where title like ? ", Penguin.TABLE_EXAM_SUBJECT));
        Integer count = jdbcTemplate.queryForObject(sb.toString(), new Object[]{title}, new SubjectCountRowMapper());
        return count;
    }

    @Override
    public List<Subject> queryByTitle(String title, int page, int num) {
        StringBuilder sb= new StringBuilder(String.format("select a.*, b.paper_name " +
                "from %s a, %s b where title like ? and a.paper_id = b.id ", Penguin.TABLE_EXAM_SUBJECT, Penguin.TABLE_EXAM_PAPER));
        sb.append(" order by a.id limit "+num*(page-1)+", "+num);
        List<Subject> subjects = jdbcTemplate.query(sb.toString(), new Object[]{title}, new SubjectAndPaperRowMapper());
        return subjects;
    }

    @Override
    public int queryCountByTitleAndPaperName(String title, String paperName) {
        StringBuilder sb = new StringBuilder(String.format("select count(1) as count " +
                "from %s a, %s b where 1 = 1 and a.paper_id = b.id ", Penguin.TABLE_EXAM_SUBJECT, Penguin.TABLE_EXAM_PAPER));
        List<Object> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(title)) {
            sb.append(" and a.title like ? ");
            param.add(title);
        }
        if (StringUtils.isNotEmpty(paperName)) {
            sb.append(" and b.paper_name like ? ");
            param.add(paperName);
        }
        Integer count = jdbcTemplate.queryForObject(sb.toString(), param.toArray(), new SubjectCountRowMapper());
        return count;
    }

    @Override
    public List<Subject> queryByTitleAndPaperName(String title, String paperName, int page, int num) {
        StringBuilder sb = new StringBuilder(String.format("select a.*, b.paper_name " +
                "from %s a, %s b where 1 = 1 and a.paper_id = b.id ", Penguin.TABLE_EXAM_SUBJECT, Penguin.TABLE_EXAM_PAPER));
        List<Object> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(title)) {
            sb.append(" and a.title like ? ");
            param.add(title);
        }
        if (StringUtils.isNotEmpty(paperName)) {
            sb.append(" and b.paper_name like ? ");
            param.add(paperName);
        }
        sb.append("order by a.create_time desc limit "+num*(page-1)+", "+num);
        List<Subject> subjects = jdbcTemplate.query(sb.toString(), param.toArray(), new SubjectAndPaperRowMapper());
        return subjects;
    }

    private Subject warpSubject(ResultSet resultSet) {
        try {
            Subject subject = new Subject();
            subject.setId(resultSet.getInt("id"));
            subject.setOptionA(resultSet.getString("option_a"));
            subject.setOptionB(resultSet.getString("option_b"));
            subject.setOptionC(resultSet.getString("option_c"));
            subject.setOptionD(resultSet.getString("option_d"));
            subject.setAnswer(resultSet.getString("answer"));
            subject.setParse(resultSet.getString("parse"));
            subject.setTitleType(resultSet.getInt("title_type"));
            subject.setPaperId(resultSet.getInt("paper_id"));
            return subject;
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
        return null;
    }

    private class SubjectAndPaperRowMapper implements RowMapper<Subject> {
        @Override
        public Subject mapRow(ResultSet resultSet, int i) throws SQLException {
            Subject subject = warpSubject(resultSet);
            Paper paper = new Paper();
            paper.setPaperName(resultSet.getString("paper_name"));
            subject.setPaper(paper);
            return subject;
        }
    }

    private class SubjectRowMapper implements RowMapper<Subject> {
        @Override
        public Subject mapRow(ResultSet resultSet, int i) throws SQLException {
            Subject subject = warpSubject(resultSet);
            return subject;
        }
    }

    private class SubjectCountRowMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            Integer count = resultSet.getInt("count");
            return count;
        }
    }
}
