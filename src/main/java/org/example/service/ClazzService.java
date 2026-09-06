package org.example.service;

import org.example.pojo.Clazz;
import org.example.pojo.ClazzQueryParam;
import org.example.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    Clazz findById(Integer id);

    void update(Clazz clazz);
}
