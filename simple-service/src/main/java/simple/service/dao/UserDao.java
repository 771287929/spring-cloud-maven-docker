package simple.service.dao;

import java.util.List;

import simple.service.model.User;


public interface UserDao {

	List<User> findAll();
}
