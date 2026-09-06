package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;

import java.util.List;

@Mapper
public interface ClazzMapper {

    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    @Select("select * from clazz where id = #{id}")
    Clazz findById(Integer id);

    @Update("update clazz set name=#{name},room=#{room},begin_date=#{beginDate},end_date=#{endDate}," +
            "update_time=#{updateTime},master_id=#{masterId},subject=#{subject} where id=#{id}")
    void update(Clazz clazz);

    @Delete("delete from clazz where id=#{id}")
    void deleteById(Integer id);

    @Select("select count(*) from student where clazz_id=#{clazzId}")
    Integer countStudentByClazzId(Integer clazzId);
}
