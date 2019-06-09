package hr.fer.tel.project.mreznikviz.MrezniKvizService;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.annotations.NamedNativeQueries;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("SELECT id FROM User where userName = ?1")
    Long findByUserName(String userName);

    @Transactional
    @Modifying
    @Query("UPDATE User SET score = score + ?1 WHERE userName = ?2")
    Integer updateScore(Long score, String userName);

   @Query("SELECT u FROM User u where userName = ?1 and password = ?2")
    User loginUser(String userName, String password);

}