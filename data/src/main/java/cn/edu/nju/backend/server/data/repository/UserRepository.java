package cn.edu.nju.backend.server.data.repository;

import cn.edu.nju.backend.server.data.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

/**
 * Created by zhongyq on 17/2/27.
 */
@Repository
@Table(name="user")
@Qualifier("userRepository")
public interface UserRepository extends CrudRepository<User,Long> {
    public User findOne(Long id);

    @Query("select t from User t where t.username=:name")
    public User findUserByName(@Param("name") String name);
}
