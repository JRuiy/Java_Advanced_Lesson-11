package ua.lviv.lgs.service.imp;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ua.lviv.lgs.dao.UserDao;
import ua.lviv.lgs.dao.imp.UserDaoImp;
import ua.lviv.lgs.domain.User;
import ua.lviv.lgs.service.UserService;

public class UserServiceImpl implements UserService {

	public static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
	private UserDao userDao;
	private static UserService userServiceImpl;

	private UserServiceImpl() {
		try {
			userDao = new UserDaoImp();
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error(e);
		}
	}
	
	public static UserService getUserService() {
		if (userServiceImpl == null) {
			userServiceImpl = new UserServiceImpl();
		}
		return userServiceImpl;
	}

	@Override
	public User create(User t) {
		return userDao.create(t);
	}

	@Override
	public User read(Integer id) {
		return userDao.read(id);
	}

	@Override
	public User update(User t) {
		return null;
	}

	@Override
	public void delete(Integer id) {
		userDao.delete(id);
	}

	@Override
	public List<User> readAll() {
		return userDao.readAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}

}
