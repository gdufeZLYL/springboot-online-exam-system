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
    int deleteStudent(int id);

    /**
     * 更新学生信息
     * @param id
     * @param password
     * @return
     */
    int updatePassword(int id, String password);

    /**
     * 更新考生信息
     * @param student
     * @return
     */
    int updateStudent(Student student);

    /**
     * 查询学生(By studentId and password)
     * @param studentId
     * @param password
     * @return
     */
    Student selectStudentByStuIdAndPwd(String studentId, String password);

    /**
     * 查询学生(By studentId)
     * @param studentId
     * @return
     */
    Student selectStudentByStuId(String studentId);

    /**
     * 获取学生数量
     * @return
     */
    int queryAllCount(String studentId, String studentName, String className);

    /**
     * 分页获取学生列表
     * @param studentId
     * @param studentName
     * @param className
     * @return
     */
    List<Student> queryStuByStuIdAndStuNameAndClassName(String studentId, String studentName,
                                                        String className, int offset,
                                                        int limit);

    /**
     * 根据学号,姓名,班级名称获取学生信息列表
     * @param studentId
     * @param studentName
     * @param className
     * @return
     */
    List<Student> queryByStuIdAndStuNameAndClassName(String studentId,
                                                     String studentName,
                                                     String className);
}
