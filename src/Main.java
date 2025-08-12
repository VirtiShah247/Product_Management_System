
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import entities.*;
import exceptions.*;
import service.*;
import utility.*;

public class Main {
	// admin functionality
	private static void adminFunctionality(Scanner sc, Map<Integer, Product> products, Map<String, Customer> customers,
			List<Transaction> transactions)
			throws InvalidDetailsException, ProductException, TransactionException, CustomerException {
		// admin login

		if(adminLogin(sc)){
			ProductService prodService = new ProductServicesImpl();
			CustomerService cusService = new CustomerServiceImpl();
			TransactionService trnsactionService = new TransactionServiceImpl();
			int choice = 0;
			try {
				do {
					System.out.println("Enter 1 add the product");
					System.out.println("Enter 2 view all the product");
					System.out.println("Enter 3 delete the product");
					System.out.println("Enter 4 update the product");
					System.out.println("Enter 5 view all customers");
					System.out.println("Enter 6 to view all transactions");
					System.out.println("Enter 7 to log out");
					choice = sc.nextInt();
	
					switch (choice) {
						case 1:
							try {
								adminAddProduct(sc, prodService);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 2:
							try {
								adminViewAllProducts(prodService);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 3:
							try {
								adminDeleteProduct(sc, prodService);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 4:
							try {
								adminUpdateProduct(sc, prodService);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 5:
							try {
								adminViewAllCustomers(cusService);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 6:
							try {
								adminViewAllTransactions(transactions, trnsactionService);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 7:
							System.out.println("Admin has successfully logout");
							break;
						default:
							throw new IllegalArgumentException("Unexpected value: " + choice);
					}
	
				} while (choice <= 6);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		else{
			throw new InvalidDetailsException("Invalid Admin Credentials");
		}

	}

	public static boolean adminLogin(Scanner sc) throws InvalidDetailsException {
		try {
			System.out.println("Enter the user name");
			String userName = sc.next();
			System.out.println("Enter the password");
			String password = sc.next();
			if (userName.equals(Admin.username) && password.equals(Admin.password)) {
				System.out.println("Successfully login");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return true;

	}

	public static void adminAddProduct(Scanner sc, ProductService prodService) throws ProductException {
		try {
			String str;
			System.out.println("Please enter the product details");
			System.out.println("Enter the product name");
			String name = sc.next();
			int qty = 0;
			try {
				System.out.println("Enter the product qty");
				qty = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.next();
				throw new IllegalInputException(e.getMessage());
			}
			double price = 0;
			try {
				System.out.println("Enter the product price/piece");
				price = sc.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.next();
				throw new IllegalInputException(e.getMessage());
			}

			System.out.println("Enter the product category");
			String cate = sc.next();

			Product prod = new Product(IDGeneration.generateId(), name, qty, price, cate);
			str = prodService.addProduct(prod);
			System.out.println(str);
		} catch (Exception e) {
			throw new ProductException("Error adding product");
		}

	}

	public static void adminViewAllProducts(ProductService prodService)
			throws ProductException {
		prodService.viewAllProducts();
	}

	public static void adminDeleteProduct(Scanner sc, ProductService prodService)
			throws ProductException, IllegalInputException {
		Map<Integer, Product> pData = FileExists.readProduct();
		if (!pData.isEmpty()) {
			int id = 0;
			try {
				System.out.println("please enter the id of product to be deleted");
				id = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.next();
				throw new IllegalInputException(e.getMessage());
			}
			prodService.deleteProduct(id);
		} else {
			throw new ProductException("There are no products. Pls. add new products.");
		}

	}

	public static void adminUpdateProduct(Scanner sc, ProductService prodService)
			throws ProductException {
		Map<Integer, Product> pData = FileExists.readProduct();
		if (!pData.isEmpty()) {
			try {
				String result;
				int id = 0;
				try {
					System.out.println("please enter the id of the product which is to be updated");
					id = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println(e.getMessage());
					sc.next();
				}
				String name = pData.get(id).getName();
				String cate = pData.get(id).getCategory();
				int qty = pData.get(id).getQty();
				double price = pData.get(id).getPrice();
				int choice = 0;
				do {
					System.out.println(
							"Please enter your preference\n1. product name\n2. Product quantity\n3. Product price\n4. Product Category\n5. Exit");
					choice = sc.nextInt();
					switch (choice) {
						case 1:
							System.out.println("Enter the product name");
							name = sc.next();
							break;
						case 2:
							try {
								System.out.println("Enter the product qty");
								qty = sc.nextInt();
							} catch (InputMismatchException e) {
								System.out.println(e.getMessage());
								sc.next();
							}
							break;
						case 3:
						try{
							System.out.println("Enter the product price/piece");
							price = sc.nextDouble();
						} catch (InputMismatchException e) {
							System.out.println(e.getMessage());
							sc.next();
						}
							
							break;
						case 4:
							System.out.println("Enter the product category");
							cate = sc.next();
							break;
						case 5:
							System.out.println("you have successfully exit from update product");
							break;
						default:
							break;
					}
				} while (choice < 5);

				Product update = new Product(id, name, qty, price, cate);
				result = prodService.updateProduct(id, update);
				System.out.println(result);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} else {
			throw new ProductException("There are no products. Pls. add new products.");
		}
	}

	public static void adminViewAllCustomers(CustomerService cusService)
			throws ProductException, CustomerException {
		Map<String, Customer> cData = cusService.viewAllCustomers();
		for (Map.Entry<String, Customer> customer : cData.entrySet()) {
			String[] customerParts = customer.getValue().toString().split(" ");
			String email = customerParts[0];
			String username = customerParts[1];
			double walletBalance = Double.parseDouble(customerParts[3]);
			String address = customerParts[4];
			System.out.println("Customer- " + username);
			System.out.println("Email- " + email);
			System.out.println("Address- " + address);
			System.out.println("Wallet balance- " + walletBalance);
			System.out.println("\n");
		}

	}

	public static void adminViewAllTransactions(List<Transaction> transactions, TransactionService trnsactionService)
			throws TransactionException {
		List<Transaction> allTransactions = trnsactionService.viewAllTransactions(transactions);
		for (Transaction tr : allTransactions) {
			String[] transactionParts = tr.toString().split(" ");
			String username = transactionParts[0];
			String email = transactionParts[1];
			int productId = Integer.parseInt(transactionParts[2]);
			String productName = transactionParts[3];
			int quantity = Integer.parseInt(transactionParts[4]);
			double price = Double.parseDouble(transactionParts[5]);
			double total = Double.parseDouble(transactionParts[6]);
			LocalDate date = LocalDate.parse(transactionParts[7]);
			System.out.println("Transaction- ");
			System.out.println("Username- " + username);
			System.out.println("Email- " + email);
			System.out.println("Product id- " + productId);
			System.out.println("Product name- " + productName);
			System.out.println("Quantity- " + quantity);
			System.out.println("Price- " + price);
			System.out.println("Total price- " + total);
			System.out.println("Date of transaction- " + date);
			System.out.println("\n");
		}

	}

	// customer functionality
	public static void customerFunctionality(Scanner sc, Map<String, Customer> customers,
			Map<Integer, Product> products, List<Transaction> transactions)
			throws InvalidDetailsException, TransactionException, CustomerException {

		CustomerService cusService = new CustomerServiceImpl();
		ProductService prodService = new ProductServicesImpl();
		TransactionService trnsactionService = new TransactionServiceImpl();

		// Customer login
		System.out.println("Please enter the following details to login");
		System.out.println("Please enter the email:");
		String email = sc.next();
		System.out.println("Enter the password: ");
		String pass = sc.next();
		if(customerLogin(email, pass, cusService)){
			try {
				int choice = 0;
				do {
					System.out.println("Select the option of your choice");
					System.out.println("Enter 1 to view all products");
					System.out.println("Enter 2 to buy a product");
					System.out.println("Enter 3 to add money to a wallet");
					System.out.println("Enter 4 view wallet balance");
					System.out.println("Enter 5 view my details");
					System.out.println("Enter 6 view my transactions");
					System.out.println("Enter 7 to logout");
					choice = sc.nextInt();
	
					switch (choice) {
						case 1:
							try {
								customerViewAllProducts(products, prodService);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 2:
							try {
								customerBuyProduct(sc, email, transactions, cusService);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 3:
							try {
								customerAddMoneyToWallet(sc, email, cusService);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 4:
							try {
								customerViewWalletBalance(email, cusService);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 5:
							try {
								customerViewMyDetails(email, cusService);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 6:
							try {
								customerViewCustomerTransactions(email, transactions, trnsactionService);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 7:
							System.out.println("You have successsfully logout");
							break;
						default:
							System.out.println("Invalid choice");
							break;
					}
	
				} while (choice <= 6);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public static void customerSignup(Scanner sc) throws DuplicateDataException, CustomerException {

		try {
			System.out.println("please enter the following details to Signup");
			System.out.println("please enter the username");
			String name = sc.next();
			String email = null;
			try {
				System.out.println("Enter the email id");
				email = sc.next();
				if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
					throw new IllegalInputException("Invalid email address");
				}
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			}
			String pass = " ";
			try {
				System.out.println("Enter the password");
				pass = sc.next();
				if (!pass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
					throw new IllegalInputException(
							"Invalid password.\nPassword must have at least\none digit\none lower case\none uppercase\none special character\nno whitespace allowed\n8 character long.");
				}
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("enter the address");
			sc.nextLine();
			String address = sc.nextLine();
			
			double balance = 0;
			try {
				System.out.println("Enter the balance to be added into the wallet");
				balance = sc.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println("Input wrong balance");
				sc.next();
			}
			String salt = "";
			Customer cus = new Customer(balance, name, pass, address, email, salt);

			CustomerService cusService = new CustomerServiceImpl();
			cusService.signUp(cus);
			System.out.println("customer has Succefully sign up");
		} catch (InputMismatchException e) {
			System.out.println("Invalid input.Please enter a valid type input.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static boolean customerLogin(String email, String pass, CustomerService cusService)
			throws InvalidDetailsException, CustomerException {
		Map<String, Customer> cData = FileExists.readCustomer();
		if (!cData.isEmpty()) {
			boolean loginStatus = cusService.login(email, pass);
			if (loginStatus) {
				System.out.println("Customer has successfully login");
				return true;
			} else {
				System.out.println("Customer has not been successfully login");
				return false;
			}
		} else {
			throw new CustomerException("No customer has sign up yet.");
		}
	}

	public static void customerViewAllProducts(Map<Integer, Product> products, ProductService prodService)
			throws ProductException {
		Map<Integer, Product> pData = FileExists.readProduct();
		if (!pData.isEmpty()) {
			prodService.viewAllProducts();
		} else {
			throw new ProductException("Product list is empty");
		}
	}

	public static void customerBuyProduct(Scanner sc, String email, List<Transaction> transactions,
			CustomerService cusService)
			throws InvalidDetailsException, ProductException, CustomerException, TransactionException, IllegalInputException {
		Map<Integer, Product> pData = FileExists.readProduct();
		if (!pData.isEmpty()) {
			int id = 0;
			int qty = 0;
			try {
				System.out.println("Enter the product id");
				id = sc.nextInt();
				System.out.println("enter the quantity you want to buy");
				qty = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.next();
				throw new IllegalInputException(e.getMessage());
			}
			
			cusService.buyProduct(id, qty, email, transactions);
		} else {
			throw new ProductException("Product list is empty");
		}

	}

	public static void customerAddMoneyToWallet(Scanner sc, String email,
			CustomerService cusService) throws CustomerException, IllegalInputException {
		Map<String, Customer> cData = FileExists.readCustomer();
		if (!cData.isEmpty()) {
			double money = 0;
			try {
				System.out.println("please enter the amount");
				money = sc.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.next();
				throw new IllegalInputException(e.getMessage());
			}
			cusService.addMoneyToWallet(money, email);
		} else {
			throw new CustomerException("Customer list is empty");
		}

	}

	public static void customerViewWalletBalance(String email,
			CustomerService cusService) throws CustomerException {

		double walletBalance = cusService.viewWalletBalance(email);
		System.out.println("Wallet balance is: " + walletBalance);
	}

	public static void customerViewMyDetails(String email, CustomerService cusService) throws CustomerException {
		Customer cus = cusService.viewCustomerDetails(email);
		System.out.println("\n");
		System.out.println("Your details are: ");
		System.out.println("Your Name : " + cus.getUsername());
		System.out.println("Your Address : " + cus.getAddress());
		System.out.println("your Email : " + cus.getEmail());
		System.out.println("Your Wallet balance : " + cus.getWalletBalance());
		System.out.println("\n");
	}

	public static void customerViewCustomerTransactions(String email, List<Transaction> transactions,
			TransactionService trnsactionService) throws TransactionException {
		List<Transaction> myTransactions = trnsactionService.viewCustomerTransactions(email, transactions);
		for (Transaction tr : myTransactions) {
			String[] transactionParts = tr.toString().split(" ");
			String username = transactionParts[0];
			String em = transactionParts[1];
			int productId = Integer.parseInt(transactionParts[2]);
			String productName = transactionParts[3];
			int quantity = Integer.parseInt(transactionParts[4]);
			double price = Double.parseDouble(transactionParts[5]);
			double total = Double.parseDouble(transactionParts[6]);
			LocalDate date = LocalDate.parse(transactionParts[7]);
			System.out.println("Transaction- ");
			System.out.println("Username- " + username);
			System.out.println("Email- " + em);
			System.out.println("Product id- " + productId);
			System.out.println("Product name- " + productName);
			System.out.println("Quantity- " + quantity);
			System.out.println("Price- " + price);
			System.out.println("Total price- " + total);
			System.out.println("Date of transaction- " + date);
			System.out.println("\n");
		}
	}

	public static void main(String[] args) throws CustomerException, IOException {
		// file check
		File f = new File("src", "database");
		f.mkdir();
		Map<Integer, Product> products = FileExists.productFile();
		Map<String, Customer> customers = FileExists.customerFile();
		List<Transaction> transactions = FileExists.transactionFile();

		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome in Product Management System");

		try {

			int choice = 0;
			do {
				System.out.println(
						"Please enter your preference\n1. Admin login\n2. Customer login\n3. Customer signup\n4. Exit");
				choice = sc.nextInt();
				switch (choice) {
					case 1:
						adminFunctionality(sc, products, customers, transactions);
						break;
					case 2:
						customerFunctionality(sc, customers, products, transactions);
						break;
					
					case 3:
					customerSignup(sc);
					break;

					case 4:
						System.out.println("successfully existed from the system");
						break;

					default:
						throw new IllegalArgumentException("Invalid Selection");
				}

			}

			while (choice != 4);

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}

}
