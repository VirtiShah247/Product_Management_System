 package service;

import java.util.*;
import entities.*;
import exceptions.*;


public interface CustomerService {

	public boolean login(String email,String password) throws InvalidDetailsException, CustomerException;

	public void signUp(Customer cus) throws DuplicateDataException, CustomerException;

	public boolean buyProduct(int id, int qty, String email, List<Transaction> transactions)
			throws InvalidDetailsException, ProductException, CustomerException, TransactionException;

	public void addMoneyToWallet(double amount, String email) throws CustomerException;

	public double viewWalletBalance(String email) throws CustomerException;

	public Customer viewCustomerDetails(String email) throws CustomerException;

	public Map<String, Customer> viewAllCustomers() throws ProductException, CustomerException;

}
