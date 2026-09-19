package org.example.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.anno.LogOperation;
import org.example.mapper.OperateLogMapper;
import org.example.pojo.OperateLog;
import org.example.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class OperationLogAspect {
    @Autowired
    private OperateLogMapper operateLogMapper;

    //切入点表达式中的变量名必须与通知方法参数名一致；参数名不能叫 log，否则会与 @Slf4j 生成的日志字段冲突
    @Around("@annotation(logOperation)")//环绕通知
    public Object around(ProceedingJoinPoint joinPoint, LogOperation logOperation) throws Throwable {
        //记录开始时间
        long startTime=System.currentTimeMillis();
        //执行方法
        Object result=joinPoint.proceed();
        //当前时间
        long endTime=System.currentTimeMillis();
        //方法执行耗时
        long costTime=endTime-startTime;

        //构建日志对象，保存日志；记录日志失败不能影响业务方法的正常返回
        try {
            OperateLog operateLog = new OperateLog();
            operateLog.setOperateEmpId(getCurrentUserId());
            operateLog.setOperateTime(java.time.LocalDateTime.now());
            operateLog.setClassName(joinPoint.getTarget().getClass().getName());
            operateLog.setMethodName(joinPoint.getSignature().getName());
            //getArgs() 返回的是 Object[]，直接 toString 只会得到 [Ljava.lang.Object;@xxx，需用 Arrays.toString
            operateLog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
            //方法返回 void 时 result 为 null，避免空指针
            operateLog.setReturnValue(result == null ? null : result.toString());
            operateLog.setCostTime(costTime);
            operateLogMapper.insert(operateLog);
        } catch (Exception e) {
            log.error("操作日志记录失败", e);
        }
        return result;
    }

    /**
     * 从 ThreadLocal 中获取当前登录用户 id（由 TokenInterceptor 在 preHandle 中写入）
     * 返回类型必须为 Integer，直接返回 int 会在取不到值时因自动拆箱抛 NPE
     */
    private Integer getCurrentUserId(){
        Integer empId = CurrentHolder.getCurrentId();
        if (empId == null) {
            log.warn("未能从 ThreadLocal 中获取当前登录用户，请确认请求头 token 是否有效");
        }
        return empId;
    }


}
