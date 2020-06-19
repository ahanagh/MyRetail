package com.myretail.models;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Product {
	@Id
	private ObjectId _id;
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("encoded_name")
	private String encodedName;
	
	@JsonProperty("current_price")
	private Price currentPrice;
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEncodedName() {
		return encodedName;
	}
	public void setEncodedName(String encodedName) {
		this.encodedName = encodedName;
	}
	public Price getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Price currentPrice) {
		this.currentPrice = currentPrice;
	}
	@Override
	public String toString() {
		return "ProductCatalog [_id=" + _id + ", id=" + id + ", name=" + name + ", encodedName=" + encodedName
				+ ", currentPrice=" + currentPrice + "]";
	}
	
	
}
