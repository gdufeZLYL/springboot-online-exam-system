package com.zzqnxx.dao.impl;

import com.zzqnxx.common.Penguin;
import com.zzqnxx.dao.PaperDao;
import com.zzqnxx.model.Paper;
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

@Repository("paperDao")
public class PaperDaoImpl implements PaperDao {

    private static Log LOG = LogFactory.getLog(PaperDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertPaper(Paper paper) {
        String sql = String.format("insert into %s (paper_name) values (?) ", Penguin.TABLE_EXAM_PAPER);
        int paperId = jdbcTemplate.update(sql, paper.getPaperName());
        return paperId;
    }

    @Override
    public boolean deletePaper(int id) {
        String sql = String.format("delete from %s where id = ? ", Penguin.TABLE_EXAM_PAPER);
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public boolean updatePaper(int id, String paperName) {
        String sql = String.format("update %s SET paper_name = ? WHERE id = ? ", Penguin.TABLE_EXAM_PAPER);
        return jdbcTemplate.update(sql, paperName, id) > 0;
    }

    @Override
    public List<Paper> queryByPaperName(String paperName) {
        StringBuilder sb = new StringBuilder(String.format("select * from %s where 1 = 1 ", Penguin.TABLE_EXAM_PAPER));
        List<Object> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(paperName)) {
            sb.append(" and paper_name like ? ");
            param.add("%"+paperName+"%");
        }
        List<Paper> papers = jdbcTemplate.query(sb.toString(), param.toArray(), new PaperRowMapper());
        return papers;
    }

    @Override
    public int queryAllCount() {
        String sql = String.format("select count(1) as count from %s ", Penguin.TABLE_EXAM_PAPER);
        Integer count = jdbcTemplate.queryForObject(sql, new PaperCountRowMapper());
        return count;
    }

    @Override
    public List<Paper> queryAll(int page, int num) {
        StringBuilder sb = new StringBuilder(String.format("select * from %s ", Penguin.TABLE_EXAM_PAPER));
        sb.append(" order by id limit "+num*(page-1)+", "+num);
        List<Paper> papers = jdbcTemplate.query(sb.toString(), new PaperRowMapper());
        return papers;
    }

    @Override
    public boolean checkPaperId(int paperId) {
        String sql = String.format("select count(1) as count from %s where id = ? ", Penguin.TABLE_EXAM_PAPER);
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{paperId}, new PaperCountRowMapper());
        return count > 0;
    }

    @Override
    public int queryCountByIdAndName(String id, String paperName) {
        StringBuilder sb = new StringBuilder(String.format("select count(1) as count from %s where 1 = 1",
                Penguin.TABLE_EXAM_PAPER));
        List<Object> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(id)) {
            sb.append(" and id = ? ");
            param.add(id);
        }
        if (StringUtils.isNotEmpty(paperName)) {
            sb.append(" and paper_name like ? ");
            param.add("%"+paperName+"%");
        }
        Integer count = jdbcTemplate.queryForObject(sb.toString(), param.toArray(),
                new PaperCountRowMapper());
        return count;
    }

    @Override
    public List<Paper> queryByIdAndName(String id, String paperName, int page, int num) {
        StringBuilder sb = new StringBuilder(String.format("select * from %s where 1 = 1",
                Penguin.TABLE_EXAM_PAPER));
        List<Object> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(id)) {
            sb.append(" and id = ? ");
            param.add(id);
        }
        if (StringUtils.isNotEmpty(paperName)) {
            sb.append(" and paper_name like ? ");
            param.add("%"+paperName+"%");
        }
        sb.append(" order by id limit "+num*(page-1)+", "+num);
        List<Paper> papers = jdbcTemplate.query(sb.toString(), param.toArray(), new PaperRowMapper());
        return papers;
    }

    private Paper warpPaper(ResultSet resultSet) {
        try {
            Paper paper = new Paper();
            paper.setId(resultSet.getInt("id"));
            paper.setPaperName(resultSet.getString("paper_name"));
            paper.setCreateTime(resultSet.getTimestamp("create_time"));
            return paper;
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
        return null;
    }

    private class PaperRowMapper implements RowMapper<Paper> {
        @Override
        public Paper mapRow(ResultSet resultSet, int i) throws SQLException {
            Paper paper = warpPaper(resultSet);
            return paper;
        }
    }

    private class PaperCountRowMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            Integer count = resultSet.getInt("count");
            return count;
        }
    }
}
