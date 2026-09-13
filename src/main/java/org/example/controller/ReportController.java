package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.JobOption;
import org.example.pojo.Result;
import org.example.pojo.StudentData;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

//    统计各个职位的员工人数
    @RequestMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("统计各个职位的员工人数");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

//    统计性别信息
    @RequestMapping("/empGenderData")
    public Result getEmpGenderData(){
        log.info("统计性别信息");
        List<Map>genderList=reportService.getGenderData();
        return Result.success(genderList);
    }

    @GetMapping("/studentCountData")
    public Result getStudentCountData(){
        log.info("统计学生人数");
        //返回的是一个数组
        StudentData studentCount = reportService.getStudentCountData();
        return Result.success(studentCount);
    }

    @GetMapping("/studentDegreeData")
    public Result getStudentDegreeData(){
        log.info("统计学生学历信息");
        //返回的是一个数组
        List<Map> studentDegree = reportService.getStudentDegreeData();
        return Result.success(studentDegree);
    }
}

