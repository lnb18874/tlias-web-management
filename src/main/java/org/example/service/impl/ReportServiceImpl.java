package org.example.service.impl;

import org.example.mapper.ReportMapper;
import org.example.pojo.JobOption;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;


    @Override
    public JobOption getEmpJobData() {

    }
}
