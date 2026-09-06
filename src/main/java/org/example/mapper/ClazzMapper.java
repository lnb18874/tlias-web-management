package org.example.mapper;

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
}
