package com.zzqnxx.dao.impl;

import com.zzqnxx.common.Penguin;
import com.zzqnxx.dao.StudentDao;
import com.zzqnxx.model.Student;
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

@Repository("studentDao")
public class StudentDaoImpl implements StudentDao {

    private static Log LOG = LogFactory.getLog(StudentDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertStudent(Student student) {
        StringBuilder sb = new StringBuilder(String.format("insert into %s " +
                "(student_id, student_name, gender, id_card, password, profession, class_name) " +
                "values (?, ?, ?, ?, ?, ?, ?)", Penguin.TABLE_EXAM_STUDENT));
        int id = jdbcTemplate.update(sb.toString(), student.getStudentId(), student.getStudentName(),
                student.getGender(), student.getIdCard(), student.getPassword(), student.getProfession(),
                student.getClassName());
        return id;
    }

    @Override
    public boolean deleteStudent(int id) {
        StringBuilder sb = new StringBuilder(String.format("delete from %s where id = ? ",
                Penguin.TABLE_EXAM_STUDENT));
        return jdbcTemplate.update(sb.toString(), id) > 0;
    }

    @Override
    public boolean updatePassword(int id, String password) {
        StringBuilder sb = new StringBuilder(String.format("update %s " +
                "set password = ? where id = ? ", Penguin.TABLE_EXAM_STUDENT));
        return jdbcTemplate.update(sb.toString(), password, id) > 0;
    }

    @Override
    public boolean updateStudent(Student student) {
        StringBuilder sb = new StringBuilder(String.format("update %s " +
                "set student_id = ?, student_name = ?, gender = ?, id_card = ?, " +
                "password = ?, profession = ?, class_name = ? where id = ? ", Penguin.TABLE_EXAM_STUDENT));
        return jdbcTemplate.update(sb.toString(), student.getStudentId(), student.getStudentName(),
                student.getGender(), student.getIdCard(), student.getPassword(),
                student.getProfession(), student.getClassName(), student.getId()) > 0;
    }

    @Override
    public Student selectStudentByStuIdAndPwd(String studentId, String password) {
        StringBuilder sb = new StringBuilder(String.format("select * " +
                "from %s where student_id = ? and password = ? ", Penguin.TABLE_EXAM_STUDENT));
        Student student = jdbcTemplate.queryForObject(sb.toString(),
                new Object[]{studentId, password}, new StudentRowMapper());
        return student;
    }

    @Override
    public Student selectStudentByStuId(String studentId) {
        StringBuilder sb = new StringBuilder(String.format("select * from %s where student_id = ? ", Penguin.TABLE_EXAM_STUDENT));
        Student student = jdbcTemplate.queryForObject(sb.toString(),
                new Object[]{studentId}, new StudentRowMapper());
        return student;
    }

    @Override
    public int queryAllCount(String studentId, String studentName, String className) {
        StringBuilder sb = new StringBuilder(String.format("select count(1) as count " +
                "from %s where 1 = 1", Penguin.TABLE_EXAM_STUDENT));
        List<Object> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(studentId)) {
            sb.append(" and student_id = ? ");
            param.add(studentId);
        }
        if (StringUtils.isNotEmpty(studentName)) {
            sb.append(" and student_name like ? ");
            param.add("%"+studentName+"%");
        }
        if (StringUtils.isNotEmpty(className)) {
            sb.append(" and class_name like ? ");
            param.add("%"+className+"%");
        }
        Integer count = jdbcTemplate.queryForObject(sb.toString(), param.toArray(), new StudentCountRowMapper());
        return count;
    }

    @Override
    public List<Student> queryStuByStuIdAndStuNameAndClassName(String studentId, String studentName, String className, int page, int num) {
        StringBuilder sb = new StringBuilder(String.format("select * " +
                "from %s where 1 = 1", Penguin.TABLE_EXAM_STUDENT));
        List<Object> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(studentId)) {
            sb.append(" and student_id = ? ");
            param.add(studentId);
        }
        if (StringUtils.isNotEmpty(studentName)) {
            sb.append(" and student_name like ? ");
            param.add("%"+studentName+"%");
        }
        if (StringUtils.isNotEmpty(className)) {
            sb.append(" and class_name like ? ");
            param.add("%"+className+"%");
        }
        sb.append(" order by student_id limit "+num*(page-1)+", "+num);
        List<Student> students = jdbcTemplate.query(sb.toString(), param.toArray(), new StudentRowMapper());
        return students;
    }

    @Override
    public List<Student> queryByStuIdAndStuNameAndClassName(String studentId, String studentName, String className) {
        StringBuilder sb = new StringBuilder(String.format("select * " +
                "from %s where 1 = 1", Penguin.TABLE_EXAM_STUDENT));
        List<Object> param = new ArrayList<>();
        if (StringUtils.isNotEmpty(studentId)) {
            sb.append(" and student_id = ? ");
            param.add(studentId);
        }
        if (StringUtils.isNotEmpty(studentName)) {
            sb.append(" and student_name like ? ");
            param.add("%"+studentName+"%");
        }
        if (StringUtils.isNotEmpty(className)) {
            sb.append(" and class_name like ? ");
            param.add("%"+className+"%");
        }
        List<Student> students = jdbcTemplate.query(sb.toString(), param.toArray(), new StudentRowMapper());
        return students;
    }

    private Student warpStudent(ResultSet resultSet) {
        try {
            Student student = new Student();
            student.setId(resultSet.getInt("id"));
            student.setStudentId(resultSet.getString("student_id"));
            student.setStudentName(resultSet.getString("student_name"));
            student.setGender(resultSet.getString("gender"));
            student.setIdCard(resultSet.getString("id_card"));
            student.setPassword(resultSet.getString("password"));
            student.setProfession(resultSet.getString("profession"));
            student.setClassName(resultSet.getString("class_name"));
            student.setCreateTime(resultSet.getTimestamp("create_time"));
            return student;
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
        return null;
    }

    private class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student student = warpStudent(resultSet);
            return student;
        }
    }

    private class StudentCountRowMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            Integer count = resultSet.getInt("count");
            return count;
        }
    }
}
