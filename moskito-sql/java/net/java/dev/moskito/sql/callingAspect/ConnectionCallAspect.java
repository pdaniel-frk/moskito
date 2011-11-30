package net.java.dev.moskito.sql.callingAspect;

import net.java.dev.moskito.sql.util.QueryProducer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect used to intercept  SQL query calls.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 2:14 PM
 */
@Aspect
public class ConnectionCallAspect {

    @Pointcut("within(net.java.dev.moskito.sql.callingAspect.MatcherValueDAO) " +
            "&& !initialization(net.java.dev.moskito.sql.callingAspect.MatcherValueDAO.new(..))\n" +
            "          && !preinitialization(net.java.dev.moskito.sql.callingAspect.MatcherValueDAO.new(..))\n" +
            "          && !handler(*)")
    public void connectionService() {
    }

    @Around("connectionService()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        return retVal;
    }
}
