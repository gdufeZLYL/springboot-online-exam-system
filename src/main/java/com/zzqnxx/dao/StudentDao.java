package com.zzqnxx.dao;

import com.zzqnxx.model.Student;

import java.util.List;

public interface StudentDao {
    /**
     * 添加学生信息
     * @param student
     * @return
     */
    int insertStudent(Student student);

    /**
     * 删除学生信息(By id)
     * @param id
     * @return
     */
    boolean deleteStudent(int id);

    /**
     * 更新学生信息
     * @param id
     * @param password
     * @return
     */
    boolean updatePassword(int id, String password);

    /**
     * 更新考生信息
     * @param student
     * @return
     */
    boolean updateStudent(Student student);

    /**
     * 查询学生
     * @param username
     * @param password
     * @return
     */
    Student queryStudentByUsernameAndPassword(String username, String password);

    /**
     * 查询学生
     * @param username
     * @return
     */
    Student queryStudentByUsername(String username);

    /**
     * 获取学生数量
     * @return
     */
    int queryAllCount(String username, String name, String className);

    /**
     * 分页获取学生列表
     * @param username
     * @param name
     * @param className
     * @return
     */
    List<Student> queryStudentByUsernameAndNameAndClassName(String username, String name,
                                                        String className, int page,
                                                        int num);

    /**
     * 根据学号,姓名,班级名称获取学生信息列表
     * @param username
     * @param name
     * @param className
     * @return
     */
    List<Student> queryStudentByUsernameAndNameAndClassName(String username,
                                                     String name,
                                                     String className);
}
