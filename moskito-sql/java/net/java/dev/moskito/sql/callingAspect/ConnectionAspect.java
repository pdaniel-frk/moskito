package net.java.dev.moskito.sql.callingAspect;

import net.java.dev.moskito.sql.util.QueryProducer;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Aspect used to intercept  SQL query calls.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 2:14 PM
 */
@Aspect
public class ConnectionAspect {
    /**
     * List of jdbc calls for interception.
     */
    private static final String JDBC_CALLS = "  (call(java.sql.PreparedStatement java.sql.Connection.prepareStatement(String)) " +
            "|| call(java.sql.CallableStatement java.sql.Connection.prepareCall(String))" +
            "|| call(java.sql.PreparedStatement java.sql.Connection.prepareStatement(String, String[]))" +
            "|| call(java.sql.PreparedStatement java.sql.Connection.prepareStatement(String, int))" +
            "|| call(java.sql.PreparedStatement java.sql.Connection.prepareStatement(String, int[]))" +
            "|| call(java.sql.PreparedStatement java.sql.Connection.prepareStatement(String, int, int))" +
            "|| call(java.sql.PreparedStatement java.sql.Connection.prepareStatement(String, int, int, int))" +
            "|| call(java.sql.CallableStatement java.sql.Connection.prepareCall(String, int, int))" +
            "|| call(java.sql.CallableStatement java.sql.Connection.prepareCall(String, int, int, int))" +
            "|| call(java.sql.ResultSet java.sql.Statement.executeQuery(String))" +
            "|| call(int java.sql.Statement.executeUpdate(String))" +
            "|| call(int java.sql.Statement.executeUpdate(String, int))" +
            "|| call(int java.sql.Statement.executeUpdate(String, int[]))" +
            "|| call(int java.sql.Statement.executeUpdate(String, String[]))" +
            "|| call(boolean java.sql.Statement.execute(String))" +
            "|| call(boolean java.sql.Statement.execute(String,int))" +
            "|| call(boolean java.sql.Statement.execute(String,int[]))" +
            "|| call(boolean java.sql.Statement.execute(String,String[]))" +
            "|| call(void java.sql.Statement.addBatch(String))" +
            ")" +
            "&& args(smt) && !within(net.java.dev.moskito.sql.callingAspect.ConnectionAspect)";
    /**
     * JDBC statement execution time.
     */
    private long callTime;

    private QueryProducer queryProducer;

    public ConnectionAspect() {
        queryProducer = new QueryProducer();
    }

    /**
     * Method executed before any jdbc call executed.
     *
     * @param smt sql statement
     */
    @Before(JDBC_CALLS)
    public void beforeQueryCall(String smt) {
        System.out.println(smt);
        callTime = System.currentTimeMillis();
        queryProducer.beforeQuery(smt);
    }

    /**
     * Method executed after any jdbc call executed.
     *
     * @param smt sql statement
     */
    @After(JDBC_CALLS)
    public void afterQueryCall(String smt) {
        queryProducer.afterQuery(smt);
    }

    /**
     * Method executed after any jdbc call executed.
     *
     * @param smt sql statement
     */
    @AfterThrowing(JDBC_CALLS)
    public void afterThrowingQueryCall(String smt) {
        callTime = System.currentTimeMillis() - callTime;
        queryProducer.failedQuery(smt);

    }
}
