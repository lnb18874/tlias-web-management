package org.example.service;

import org.example.pojo.JobOption;
import org.example.pojo.StudentData;

import java.util.List;
import java.util.Map;

public interface ReportService {
    JobOption getEmpJobData();

    List<Map> getGenderData();

    StudentData getStudentCountData();

    List<Map> getStudentDegreeData();
}
