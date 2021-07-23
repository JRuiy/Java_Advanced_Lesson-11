package ua.lviv.lgs.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.lviv.lgs.dao.UserDao;
import ua.lviv.lgs.domain.User;
import ua.lviv.lgs.utils.ConnectionUtils;

public class UserDaoImp implements UserDao {

	private static String READ_ALL = "select * from user";
	private static String CREATE = "insert into user(`email`, `firstName`, `lastName`, `role`) values (?,?,?,?)";
	private static String READ_BY_ID = "select * from user where id =?";
	private static String UPDATE_BY_ID = "update user set email=?, firstName = ?, lastName = ?, role = ? where id = ?";
	private static String DELETE_BY_ID = "delete from user where id=?";
	
	private Connection connection;
	private PreparedStatement preparedStatement;
	
	public UserDaoImp() throws ClassNotFoundException, SQLException {
		connection = ConnectionUtils.openConnection();
	}
	
	@Override
	public User create(User user) {
		try {
			preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getRole());
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			user.setId(resultSet.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
	@Override
	public User read(Integer id) {
		User user = null;
		try {
			preparedStatement = connection.prepareStatement(READ_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
				Integer userId = result.getInt("id");
				String email = result.getString("email");
				String firstName = result.getString("firstName");
				String lastName = result.getString("lastName");
				String role = result.getString("role");
				user = new User(userId, email, firstName, lastName, role);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	@Override
	public User update(User user) {
		try {
			preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getRole());
			preparedStatement.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	@Override
	public void delete(Integer id) {
		try {
			preparedStatement = connection.prepareStatement(DELETE_BY_ID);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<User> readAll() {
		List<User> listOfUser = new ArrayList<User>();
		try {
			preparedStatement = connection.prepareStatement(READ_ALL);
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Integer userId = result.getInt("id");
				String email = result.getString("email");
				String firstName = result.getString("firstName");
				String lastName = result.getString("lastName");
				String role = result.getString("role");
				listOfUser.add(new User(userId, email, firstName, lastName, role));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listOfUser;
	}
	
	

}
