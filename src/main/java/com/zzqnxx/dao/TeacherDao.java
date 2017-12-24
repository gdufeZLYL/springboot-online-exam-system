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
     * 删除教师信息
     * @param id
     * @return
     */
    boolean deleteTeacher(int id);

    /**
     * 更新密码
     * @param id
     * @param password
     * @return
     */
    boolean updatePassword(int id, String password);

    /**
     * 查询教师
     * @param username
     * @param password
     * @return
     */
    Teacher queryTeacherByUsernameAndPassword(String username,
                                      String password);

    /**
     * 查询教师
     * @param username
     * @return
     */
    Teacher queryTeacherByUsername(String username);
}
