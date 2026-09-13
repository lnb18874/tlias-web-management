package org.example.service.impl;

import org.example.mapper.EmpMapper;
import org.example.mapper.ReportMapper;
import org.example.mapper.StudentMapper;
import org.example.pojo.JobOption;
import org.example.pojo.StudentData;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;


    @Override
    public JobOption getEmpJobData() {
        List<Map<String,Object>> list=empMapper.countEmpJobData();
        List<Object> jobList = list.stream().map(dataMap->dataMap.get("pos")).toList();
        List<Object> dataList=list.stream().map(dataMap->dataMap.get("total")).toList();
        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map> getGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public StudentData getStudentCountData() {
        List<Map<String,Object>> list=studentMapper.countStudentData();
        List<Object> clazzList=list.stream().map(dataMap->dataMap.get("clz")).toList();
        List<Object> dataList=list.stream().map(dataMap->dataMap.get("cnt")).toList();
        return new StudentData(clazzList,dataList);
    }

    @Override
    public List<Map> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
    }
}
