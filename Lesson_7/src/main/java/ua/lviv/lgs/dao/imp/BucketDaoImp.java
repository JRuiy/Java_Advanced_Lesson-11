package ua.lviv.lgs.dao.imp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.lviv.lgs.dao.BucketDao;
import ua.lviv.lgs.domain.Bucket;
import ua.lviv.lgs.utils.ConnectionUtils;


public class BucketDaoImp implements BucketDao {

	private static String READ_ALL = "select * from bucket";
	private static String CREATE = "insert into bucket(`user_id`, `product_id`, `purchase_date`) values (?,?,?)";
	private static String READ_BY_ID = "select * from bucket where id =?";
	private static String DELETE_BY_ID = "delete from bucket where id=?";

	public static Logger LOGGER = Logger.getLogger(BucketDaoImp.class);
	
	private Connection connection;
	private PreparedStatement preparedStatement;

	public BucketDaoImp() throws ClassNotFoundException, SQLException {
		connection = ConnectionUtils.openConnection();
	}

	@Override
	public Bucket create(Bucket bucket) {

		try {
			preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, bucket.getUserId());
			preparedStatement.setInt(2, bucket.getProductId());
			preparedStatement.setDate(3, new Date(bucket.getPurchaseDate().getTime()));
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			bucket.setId(resultSet.getInt(1));
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new IllegalStateException(e);
		}

		return bucket;
	}

	
	@Override
	public Bucket read(Integer id) {
		Bucket bucket = null;
		try {
			preparedStatement = connection.prepareStatement(READ_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
				int bucketId = result.getInt("id");
				int userId = result.getInt("user_id");
				int productId = result.getInt("product_id");
				Date purchaseDate = result.getDate("purchase_date");
			bucket = new Bucket(bucketId, userId, productId, purchaseDate);
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new IllegalStateException(e);
		}
		return bucket;
	}

	@Override
	public Bucket update(Bucket t) {
		throw new IllegalStateException("there is no update for bucket");
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
	public List<Bucket> readAll() {
		List<Bucket> listOfBucket = new ArrayList<Bucket>();
		try {
			preparedStatement = connection.prepareStatement(READ_ALL);
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				int bucketId = result.getInt("id");
				int userId = result.getInt("user_id");
				int productId = result.getInt("product_id");
				Date purchaseDate = result.getDate("purchase_date");
				listOfBucket.add(new Bucket(bucketId, userId, productId, purchaseDate));
			}
		} catch (SQLException e) {
			LOGGER.error(e);
			throw new IllegalStateException(e);
		}
		
		return listOfBucket;

	}

}
