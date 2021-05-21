package medical.education.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import spring.backend.library.exception.BaseException;

@Aspect
@Component
public class Endpoint {

  @AfterThrowing("execution(* spring.backend.library.service.AbstractBaseService.*(..))")
  public Object execute() throws Throwable {
    throw new BaseException("ConstraintViolationException");
  }

}
