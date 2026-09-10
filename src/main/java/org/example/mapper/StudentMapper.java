package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Student;
import org.example.pojo.StudentQueryParam;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<Student> list(StudentQueryParam studentQueryParam);

    void insert(Student student);

    @Select("SELECT * FROM student WHERE id = #{id}")
    Student getById(Integer id);

    void update(Student student);

//    @Delete("DELETE FROM student WHERE id = #{id}")
//    void deleteById(Integer id);

    void deleteByIds(Integer[] ids);
}
