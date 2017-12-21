package com.zzqnxx.dao;

import com.zzqnxx.model.Teacher;

public interface TeacherDao {

    /**
     * 添加教师信息
     * @param teacher
     * @return
     */
    int insertTeacher(Teacher teacher);

    /**
     * 删除教师信息(By id)
     * @param id
     * @return
     */
    int deleteTeacher(int id);

    /**
     * 更新教师信息
     * @param id
     * @param password
     * @return
     */
    int updateTeacher(int id, String password);

    /**
     * 查询教师(By teacherId and password)
     * @param teacherId
     * @param password
     * @return
     */
    Teacher selectTeacherByTcIdAndPwd(String teacherId,
                                      String password);

    /**
     * 查询教师(By teacherId)
     * @param teacherId
     * @return
     */
    Teacher selectTeacherByTcId(String teacherId);
}
