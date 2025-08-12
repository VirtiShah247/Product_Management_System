package service;

import java.util.*;
import java.io.*;
import java.time.LocalDate;
import entities.*;
import exceptions.*;
import utility.FileExists;


public class CustomerServiceImpl implements CustomerService {

	public void signUp(Customer cus) throws DuplicateDataException, CustomerException {
		Map<String, Customer> cData = FileExists.readCustomer();
		if (cData != null && !cData.isEmpty() && cData.containsKey(cus.getEmail())) {
			throw new DuplicateDataException("Customer already exists , please login");	
		}
		else {
			cData.put(cus.getEmail(),cus);
			FileExists.writeCustomer(cData, false);
			System.out.println("Customer successfully sign up");
		}

	}

	//
	public boolean login(String email,String password) throws InvalidDetailsException, CustomerException {
		Map<String, Customer> cData = FileExists.readCustomer();
		if (cData != null && !cData.isEmpty()) {
			if (cData.containsKey(email)) {
				String salt = cData.get(email).getSalt();
				if (cData.get(email).verifyPassword(password, salt)) {
					return true;
				} else {
					throw new CustomerException("Passwords do not match");
				}
			} else {
				throw new CustomerException("Email do not match");
			}
			
		} else {
			throw new InvalidDetailsException("You have not sign up yet, please signup");
		}

	}


	//
	public boolean buyProduct(int id, int qty, String email, List<Transaction> transactions)
			throws InvalidDetailsException, ProductException, CustomerException, TransactionException {
				Map<Integer, Product> pData = FileExists.readProduct();
				Map<String, Customer> cData = FileExists.readCustomer();
		if (pData.isEmpty())
		{
			throw new ProductException("Product list is empty");
		}

		if (pData != null && !pData.isEmpty() && pData.containsKey(id)) {
	
			Product prod = pData.get(id);
			if (prod.getQty() >= qty) {
				
				Customer cus = cData.get(email);
				double buyingPrice = qty * prod.getPrice();

				if (cus.getWalletBalance() >= buyingPrice) {
					cus.setWalletBalance(cus.getWalletBalance() - buyingPrice);					
					cData.put(email,cus);

					prod.setQty(prod.getQty() - qty);
					pData.put(id,prod);
					FileExists.writeProduct(pData, false);
					FileExists.writeCustomer(cData, false);

					Transaction tr = new Transaction(cus.getUsername(), email, id,prod.getName(), qty, prod.getPrice(),
							prod.getPrice() * qty, LocalDate.now());

					transactions.add(tr);
					FileExists.writeTransaction(transactions, true);
					System.out.println("You have successfully bought the product " + prod.getName());
				} else {
					throw new InvalidDetailsException("Wallet balance is not sufficient");
				}

			}
			else {
				throw new InvalidDetailsException("Product quantity is not suffiecient");
			}

		} 
		else {
			throw new InvalidDetailsException("Product not available with id: " + id);
		}

		return false;
	}

	//
	public void addMoneyToWallet(double amount, String email) throws CustomerException {
		Map<String, Customer> cData = FileExists.readCustomer();
		if(!cData.isEmpty()){
			if(cData.containsKey(email)){
				Customer cus = cData.get(email);
				cus.setWalletBalance(cus.getWalletBalance() + amount);
				cData.put(email, cus);
				FileExists.writeCustomer(cData, false);
				System.out.println("Amount: " + amount + " successfully added to your wallet");
			}
			else{
				throw new CustomerException("Incorrect email.\nPls. enter correct email.");
			}
			
		}
		else{
			throw new CustomerException("Pls. Signup first to the system");
		}
	}

	//
	public double viewWalletBalance(String email) throws CustomerException {
		Map<String, Customer> cData = FileExists.readCustomer();
		if(!cData.isEmpty()){
			if(cData.containsKey(email)){
				Customer cus = cData.get(email);
				return cus.getWalletBalance();
			}
			else{
				throw new CustomerException("Incorrect email.\nPls. enter correct email.");
			}
		}
		else{
			throw new CustomerException("Pls. Signup first to the system");
		}
		
	}

	//
	public Customer viewCustomerDetails(String email) throws CustomerException {
		Map<String, Customer> cData = FileExists.readCustomer();
		if (cData != null && !cData.isEmpty()) {
			if(cData.containsKey(email)){
				return cData.get(email);
			}
			else{
				throw new CustomerException("Incorrect email.\nPls. enter correct email.");
			}
		}
		else{
			throw new CustomerException("Sorry. There are no customers yet.");
		}
	}

	//
	public Map<String, Customer> viewAllCustomers() throws ProductException, CustomerException {

		Map<String, Customer> cData = FileExists.readCustomer();
		if (cData != null && !cData.isEmpty()) {
			return cData;
		} else {
			throw new ProductException("Sorry. There are no customers yet.");
		}
	}

}
