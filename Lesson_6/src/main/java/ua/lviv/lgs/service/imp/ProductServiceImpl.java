package ua.lviv.lgs.service.imp;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import ua.lviv.lgs.dao.ProductDao;
import ua.lviv.lgs.dao.imp.ProductDaoImp;
import ua.lviv.lgs.domain.Product;
import ua.lviv.lgs.service.ProductService;

public class ProductServiceImpl implements ProductService {

	public static Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);
	private ProductDao productDao;
	private static ProductService productServiceImpl;

	private ProductServiceImpl() {
			try {
				productDao = new ProductDaoImp();
			} catch (ClassNotFoundException | SQLException e) {
				LOGGER.error(e);
			}
	}
	
	public static ProductService getProductService() {
		if (productServiceImpl == null) {
			productServiceImpl = new ProductServiceImpl();
		}
		return productServiceImpl;
	}

	@Override
	public Product create(Product t) {
		return productDao.create(t);
	}

	@Override
	public Product read(Integer id) {
		return productDao.read(id);
	}

	@Override
	public Product update(Product t) {
		return productDao.update(t);
	}

	@Override
	public void delete(Integer id) {
		productDao.delete(id);
	}

	@Override
	public List<Product> readAll() {
		return productDao.readAll();
	}
	
}
