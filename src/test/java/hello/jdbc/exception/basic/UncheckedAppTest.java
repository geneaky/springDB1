package hello.jdbc.exception.basic;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.ConnectException;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UncheckedAppTest {

    @Test
    public void unchecked() throws Exception {
        Controller controller = new Controller();
        assertThatThrownBy(() -> {
            controller.request();
        }).isInstanceOf(RuntimeSqlException.class);
    }

    @Test
    public void printEx() throws Exception {
        Controller controller = new Controller();
        try {
            controller.request();
        }catch (Exception e) {
            log.info("ex",e);
        }
    }

    static class Controller {
        Service service = new Service();

        public void request() {
            service.logic();
        }
    }

    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {
        public void call() {
            throw new RuntimeConnectionException("연결 실패");
        }
    }

    static class Repository {
        public void call() {
            try {
                runSQL();
            } catch (SQLException e) {
                throw new RuntimeSqlException(e);
            }
        }

        public void runSQL () throws SQLException {
            throw new SQLException("ex");
        }
    }

    static class RuntimeConnectionException extends RuntimeException {

        public RuntimeConnectionException(String message) {
            super(message);
        }
    }

    static class RuntimeSqlException extends RuntimeException {

        public RuntimeSqlException(Throwable cause) {
            super(cause);
        }

    }

}
