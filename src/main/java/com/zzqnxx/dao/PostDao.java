package com.zzqnxx.dao;

import com.zzqnxx.model.Post;

import java.util.List;

public interface PostDao {

    /**
     * 添加公告
     * @param post
     * @return
     */
    int insertPost(Post post);

    /**
     * 更新公告
     * @param post
     * @return
     */
    boolean updatePost(Post post);

    /**
     * 获取所有公告列表
     * @return
     */
    List<Post> queryAllPost();
}
