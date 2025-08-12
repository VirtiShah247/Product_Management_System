package service;

import java.util.*;
import entities.*;
import exceptions.*;
import utility.FileExists;

public class ProductServicesImpl implements ProductService {

	//
	public String addProduct(Product prod) throws ProductException {
		Map<Integer, Product> products = new LinkedHashMap<>();
		products.put(prod.getId(), prod);
		try {
			FileExists.writeProduct(products, true);
			return "Product added successfully";
		} catch (ProductException e) {
			throw new ProductException(e.getMessage());
		}

	}

	//
	public void viewAllProducts() throws ProductException {
		Map<Integer, Product> pData = FileExists.readProduct();
		if(pData != null && !pData.isEmpty()){
			for (Map.Entry<Integer, Product> product : pData.entrySet()) {
				String[] productParts = product.getValue().toString().split(" ");
				int id = Integer.parseInt(productParts[0]);
				String name = productParts[1];
				int qty = Integer.parseInt(productParts[2]);
				double price = Double.parseDouble(productParts[3]);
				String category = productParts[4];
				System.out.println("Product- " + id);
				System.out.println("Name- " + name);
				System.out.println("Category- " + category);
				System.out.println("Quantity- " + qty);
				System.out.println("Price- " + price);
				System.out.println("\n");
			}
		}
		else{
			throw new ProductException("Product list is empty");
		}
		
	}

	//
	public void deleteProduct(int id) throws ProductException {

		Map<Integer, Product> pData = FileExists.readProduct();
		if (pData != null && !pData.isEmpty()) {
			if (pData.containsKey(id)) {
				pData.remove(id);
				FileExists.writeProduct(pData,false);
				System.out.println("Product deleted successfully");

			} else {
				throw new ProductException("Product not found");
			}
		} else {
			throw new ProductException("Product list is empty");
		}

	}

	//
	public String updateProduct(int id, Product prod) throws ProductException {
		Map<Integer, Product> pData = FileExists.readProduct();
		if (pData != null && !pData.isEmpty()) {
			if (pData.containsKey(id)) {
				pData.put(id, prod);
				FileExists.writeProduct(pData,false);
				return "Product has successfully updated";
			} else {
				throw new ProductException("Product not found");
			}

		} else {
			throw new ProductException("Product list is empty");
		}

	}

}
