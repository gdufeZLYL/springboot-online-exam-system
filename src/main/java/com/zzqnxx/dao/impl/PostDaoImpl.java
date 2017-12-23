package com.zzqnxx.dao.impl;

import com.zzqnxx.common.Penguin;
import com.zzqnxx.dao.PostDao;
import com.zzqnxx.model.Post;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("postDao")
public class PostDaoImpl implements PostDao {

    private static Log LOG = LogFactory.getLog(PostDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertPost(Post post) {
        StringBuilder sb = new StringBuilder(String.format("insert into %s (title, content, author) " +
                "values (?, ?, ?)", Penguin.TABLE_EXAM_POST));
        int postId = jdbcTemplate.update(sb.toString(), post.getTitle(),
                post.getContent(), post.getAuthor());
        return postId;
    }

    @Override
    public boolean updatePost(Post post) {
        StringBuilder sb = new StringBuilder(String.format("update %s " +
                "set title = ?, content = ?, author = ? " +
                "where id = ? ", Penguin.TABLE_EXAM_POST));
        return jdbcTemplate.update(sb.toString(), post.getTitle(), post.getContent(), post.getAuthor()) > 0;
    }

    @Override
    public List<Post> queryAllPost() {
        StringBuilder sb = new StringBuilder(String.format("select * " +
                "from %s order by create_time desc ", Penguin.TABLE_EXAM_POST));
        List<Post> posts = jdbcTemplate.query(sb.toString(), new PostRowMapper());
        return posts;
    }

    private Post warpPost(ResultSet resultSet) {
        try {
            Post post = new Post();
            post.setId(resultSet.getInt("id"));
            post.setTitle(resultSet.getString("title"));
            post.setContent(resultSet.getString("content"));
            post.setAuthor(resultSet.getString("author"));
            post.setCreateTime(resultSet.getTimestamp("create_time"));
            return post;
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
        return null;
    }

    private class PostRowMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet resultSet, int i) throws SQLException {
            Post post = warpPost(resultSet);
            return post;
        }
    }
}
