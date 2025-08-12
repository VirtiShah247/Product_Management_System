package service;

import java.util.*;
import entities.*;
import exceptions.*;


public interface ProductService {

	public String addProduct(Product pro) throws ProductException;

	public void viewAllProducts() throws ProductException;

	public void deleteProduct(int id) throws ProductException;

	public String updateProduct(int id, Product prod) throws ProductException;

	
	
}
