package service;

import java.util.*;
import entities.*;
import exceptions.*;

public interface TransactionService {

	public List<Transaction> viewCustomerTransactions(String email, List<Transaction> transactions)throws TransactionException;
	
	public List<Transaction> viewAllTransactions(List<Transaction> transactions) throws TransactionException;
}
