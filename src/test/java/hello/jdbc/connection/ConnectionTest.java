package hello.jdbc.connection;

import static hello.jdbc.connection.ConnectionConst.*;
import static hello.jdbc.connection.ConnectionConst.PASSWORD;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Slf4j
public class ConnectionTest {

    @Test
    public void driverManager() throws Exception {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
    }

    @Test
    public void dataSourceDriverManager() throws Exception {
        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME,
            PASSWORD);

        useDataSource(dataSource);
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        Connection con3 = dataSource.getConnection();
        Connection con4 = dataSource.getConnection();
        Connection con5 = dataSource.getConnection();
        Connection con6 = dataSource.getConnection();
        Connection con7 = dataSource.getConnection();
        Connection con8 = dataSource.getConnection();
        Connection con9 = dataSource.getConnection();
        Connection con10 = dataSource.getConnection();

        con1.close();
        con3.close();
        con6.close();
        con10.close();
    }

    @Test
    public void dataSourceConnectionPool() throws Exception {
        //커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);
        Thread.sleep(1000);
        Connection connection1 = dataSource.getConnection();
        Connection connection2 = dataSource.getConnection();
        Connection connection3 = dataSource.getConnection();
        Connection connection4 = dataSource.getConnection();
        log.info("1 connection={}, class={}", connection1, connection1.getClass());
        log.info("2 connection={}, class={}", connection2, connection2.getClass());
        log.info("3 connection={}, class={}", connection3, connection3.getClass());
        log.info("4 connection={}, class={}", connection4, connection4.getClass());

        /*
        * connection pool에서 커넥션 제거하고 다시 생성했을 때
        * connection id가 몇번 부터 채워질까 봤더니
        * 랜덤하게 채워지네
        * */
    }
}
