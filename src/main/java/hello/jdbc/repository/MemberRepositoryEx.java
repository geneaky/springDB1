package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import java.sql.SQLException;

public interface MemberRepositoryEx {

    Member save(Member member) throws Exception;
    Member findById(String memberid) throws Exception;
    void update(String memberId, int money) throws Exception;
    void delete(String memberId) throws Exception;

}
