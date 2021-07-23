package ua.lviv.lgs.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.lviv.lgs.dao.ProductDao;
import ua.lviv.lgs.domain.Product;
import ua.lviv.lgs.utils.ConnectionUtils;


public class ProductDaoImp implements ProductDao {

	private static String READ_ALL = "select * from product";
	private static String CREATE = "insert into product(`name`, `description`, `price`) values (?,?,?)";
	private static String READ_BY_ID = "select * from product where id =?";
	private static String UPDATE_BY_ID = "update product set name=?, description = ?, price = ? where id = ?";
	private static String DELETE_BY_ID = "delete from product where id=?";
	
	public static Logger LOGGER = Logger.getLogger(ProductDaoImp.class);
	
	private Connection connection;
	private PreparedStatement preparedStatement;

	public ProductDaoImp() throws ClassNotFoundException, SQLException {
		connection = ConnectionUtils.openConnection();
	}
	
	@Override
	public Product create(Product product) {
		try {
			preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setDouble(3, product.getPrice());
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			product.setId(resultSet.getInt(1));
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new IllegalStateException(e);
		}

		return product;
	}
	@Override
	public Product read(Integer id) {
		Product product = null;
		try {
			preparedStatement = connection.prepareStatement(READ_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
				Integer productId = result.getInt("id");
				String name = result.getString("name");
				String descriprion = result.getString("descriprion");
				Double price = result.getDouble("price");
				product = new Product(productId, name, descriprion, price);
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new IllegalStateException(e);
		}
		return product;
	}
	@Override
	public Product update(Product product) {
		try {
			preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setDouble(3, product.getPrice());
			preparedStatement.setInt(4, product.getId());
			preparedStatement.executeUpdate();	
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new IllegalStateException(e);
		}
		
		return product;
	}
	@Override
	public void delete(Integer id) {
		try {
			preparedStatement = connection.prepareStatement(DELETE_BY_ID);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new IllegalStateException(e);
		}
	}
	@Override
	public List<Product> readAll() {
		List<Product> listOfProduct = new ArrayList<Product>();
		try {
			preparedStatement = connection.prepareStatement(READ_ALL);
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Integer productId = result.getInt("id");
				String name = result.getString("name");
				String descriprion = result.getString("descriprion");
				Double price = result.getDouble("price");
				listOfProduct.add(new Product(productId, name, descriprion, price));
			}
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new IllegalStateException(e);
		}
		
		return listOfProduct;
	}
	
	

}
