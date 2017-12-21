package com.zzqnxx.dao;

import com.zzqnxx.model.Subject;

import java.util.List;

public interface SubjectDao {

    /**
     * 添加试题信息
     * @param subject
     * @return
     */
    int insertSubject(Subject subject);

    /**
     * 删除试题信息
     * @param id
     * @return
     */
    int deleteSubject(int id);

    /**
     * 更新试题信息
     * @param subject
     * @return
     */
    int updateSubject(Subject subject);

    /**
     * 获取试题信息数量
     * @return
     */
    int queryAllCount();

    /**
     * 分页获取试题信息
     * @param offset
     * @param limit
     * @return
     */
    List<Subject> queryAll(int offset, int limit);

    /**
     * 获取试题信息(By paperId and titleType)
     * @param paperId
     * @return
     */
    List<Subject> queryByPaperId2TitleType(int paperId, int titleType);

    /**
     * 分页模糊查询匹配题目的试题数量
     * @param title
     * @return
     */
    int queryCountByTitle(String title);

    /**
     * 分页模糊查询匹配题目的试题
     * @param title
     * @param offset
     * @param limit
     * @return
     */
    List<Subject> queryByTitle(String title, int offset,
                               int limit);

    /**
     * 分页模糊查询匹配题目的试题数量
     * @param title
     * @return
     */
    int queryCountByTitleAndPaperName(String title, String paperName);

    /**
     * 分页模糊查询匹配题目的试题
     * @param title
     * @param offset
     * @param limit
     * @return
     */
    List<Subject> queryByTitleAndPaperName(String title, String paperName,
                                           int offset, int limit);
}
