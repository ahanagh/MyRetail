package com.myretail.repositories;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.myretail.models.Product;

@Repository
public class ProductCatalogDBRepository implements CatalogRepository<Product>{
	private final static Logger logger = LoggerFactory.getLogger(ProductCatalogDBRepository.class);

    @Autowired
    MongoTemplate mongoTemplate;
    

    public List<Product> searchProductCatalog(String text) {
    	String encodedText = text.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
    	logger.info("Name after removal of special characters passed for searching:{}",encodedText);
        return mongoTemplate.find(Query.query(new Criteria()
                        .orOperator(Criteria.where("id").is(text),
                                    Criteria.where("encodedName").is(encodedText))
                        ), Product.class);
    }
    
	@Override
	public Product save(Product product) {
		product.setId(product.getId());
		product.setEncodedName(product.getName().toLowerCase().replaceAll("[^a-zA-Z0-9]", ""));
		mongoTemplate.save(product);
		return product;
	}
	
	
	@Override
	public Product delete(String id) {
		return mongoTemplate.findAndRemove(Query.query(Criteria.where("id").is(id)), Product.class);
	}
	
	@Override
	public Product update(Product product) { 
		Product originalProduct = mongoTemplate.findOne(Query.query(Criteria.where("id").is(product.getId())),Product.class);
		if(originalProduct != null) {
			originalProduct.setCurrentPrice(product.getCurrentPrice());
			return mongoTemplate.findAndReplace(Query.query(Criteria.where("id").is(product.getId())), originalProduct);
		}
		return originalProduct;
	}
		
	
}