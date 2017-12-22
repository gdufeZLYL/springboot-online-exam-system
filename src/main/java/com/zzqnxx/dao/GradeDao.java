package com.zzqnxx.dao;

import com.zzqnxx.model.Grade;

import java.util.List;

public interface GradeDao {

    /**
     * 添加学生成绩
     * @param grade
     * @return
     */
    int insertGrade(Grade grade);

    /**
     * 删除学生成绩
     * @param id
     * @return
     */
    boolean deleteGrade(int id);

    /**
     * 更新学生成绩
     * @param grade
     * @return
     */
    boolean updateGrade(Grade grade);

    /**
     * 查询指定学生的成绩记录数量
     * @param sId
     * @return
     */
    int queryCountBySId(int sId);

    /**
     * 查询指定学生的成绩
     * @param sId
     * @return
     */
    List<Grade> queryBySId(int sId, int offset, int limit);

    /**
     * 查询指定试卷的成绩记录数量
     * @param paperId
     * @return
     */
    int queryCountByPaperId(int paperId);

    /**
     * 查询指定试卷的成绩
     * @param paperId
     * @param offset
     * @param limit
     * @return
     */
    List<Grade> queryByPaperId(int paperId, int offset,
                               int limit);

    /**
     * 模糊查询指定学生指定试卷的成绩记录数量
     * @param paperId
     * @param studentId
     * @param studentName
     * @return
     */
    int queryCountByPaperId2StuId2StuName(int paperId, String studentId, String studentName);

    /**
     * 模糊查询指定学生指定试卷的成绩
     * @param paperId
     * @param studentId
     * @param studentName
     * @param offset
     * @param limit
     * @return
     */
    List<Grade> queryByPaperId2StuId2StuName(int paperId, String studentId, String studentName, int offset, int limit);

    /**
     * 根据考生主键ID集合和试卷主键ID集合获取成绩数量
     * @param sIds
     * @param paperIds
     * @return
     */
    int queryCountBySIdsAndPIds(List<Integer> sIds, List<Integer> paperIds);

    /**
     * 根据考生主键ID集合和试卷主键ID集合获取成绩
     * @param sIds
     * @param paperIds
     * @param offset
     * @param limit
     * @return
     */
    List<Grade> queryBySIdsAndPIds(List<Integer> sIds, List<Integer> paperIds, int offset, int limit);
}
