package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Emp;
import org.example.pojo.EmpQueryParam;
import org.example.pojo.PageResult;
import org.example.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.service.EmpService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("分页查询员工数据，参数：{}",empQueryParam);
        PageResult<Emp> pageResult=empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("新增员工数据：{}",emp);
        empService.save(emp);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteByIds(@RequestParam List<Integer>ids){
        log.info("删除员工数据：{}",ids);
        empService.deleteByIds(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("查询员工数据：{}",id);
        Emp emp=empService.getInfo(id);
        return Result.success(emp);
    }

    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工数据：{}",emp);
        empService.update(emp);
        return Result.success();
    }

    @GetMapping("/list")//查询所有员工
    public Result list(){
        log.info("查询所有员工数据");
        List<Emp> list=empService.list();
        return Result.success(list);
    }
}
