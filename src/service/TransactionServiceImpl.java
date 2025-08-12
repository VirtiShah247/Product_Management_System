package service;

import java.util.*;
import entities.*;
import exceptions.*;
import utility.FileExists;

public class TransactionServiceImpl implements TransactionService{

	//
	public List<Transaction> viewCustomerTransactions(String email, List<Transaction> transactions)	throws TransactionException {
		List<Transaction> myTransactions = new ArrayList<>();
		List<Transaction> tFile = FileExists.readTransaction();
		boolean flag = false;
		if(tFile != null && !tFile.isEmpty()){
			for (Transaction tr : tFile) {
				if (tr.getEmail().equals(email)) {
					myTransactions.add(tr);
					flag = true;
				}
			}
			if (!flag) {
				throw new TransactionException("You hav not done any transaction yet");
			}
		}
		else {
			throw new TransactionException("No transactions yet");
		}
		return myTransactions;
	}

	//
	public List<Transaction> viewAllTransactions(List<Transaction> transactions) throws TransactionException {
		List<Transaction> tFile = FileExists.readTransaction();
		if(tFile != null && !tFile.isEmpty()) {
			return tFile;
		}
		else {
			throw new TransactionException("No transactions yet");
		}
		
	}

}
