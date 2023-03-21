package com.techelevator;

import com.techelevator.view.Product;

import com.techelevator.view.Transaction;
import com.techelevator.view.VendingMenu;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;




public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_SECRET_OPTION = "*Sales Report";

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";

	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_SECRET_OPTION};
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	private final VendingMenu menu;
	private final VendingMachine vendingMachine;
	private final Map<String, Product> products;
	private final Scanner scanner = new Scanner(System.in);



	public VendingMachineCLI(VendingMenu menu) {
		this.menu = menu;
		this.vendingMachine = new VendingMachine();
		this.vendingMachine.restock();
		this.products = vendingMachine.getProducts();
	}

	public void displayProducts() {
		vendingMachine.displayProducts();
	}
	public void feedMoney() {
		String[] validAmounts = {"$1.00", "$5.00", "$10.00", "Reject funds"};
		System.out.println("Please enter your funds");
		Scanner in = new Scanner(System.in);
		String choice = in.nextLine();
		BigDecimal moneyInput = new BigDecimal(choice.substring(1));

		vendingMachine.addMoney(moneyInput);
		Transaction.logTransaction(new Transaction("Feed Money", moneyInput));
	}

	public void purchaseProduct() {
		BigDecimal balance = vendingMachine.getBalance();
		balance = balance.setScale(2, RoundingMode.HALF_UP);
		menu.displayMessage("Current Money Provided: $" + String.format("%.2f",balance));

		label:
		while (true) {
			String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
			switch (purchaseChoice) {
				case PURCHASE_MENU_OPTION_FEED_MONEY:
					feedMoney();
					balance = vendingMachine.getBalance();
					menu.displayMessage("Current active funds: $" + String.format("%.2f",balance));
					break;
				case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
					menu.displayMessage("Enter the slotId to purchase: ");
					String slotId = scanner.nextLine();
					Product product = products.get(slotId);
					if (product == null) {
						menu.displayMessage("Invalid slotId please try again ");
					} else {
						if (!product.isInStock()) {
							menu.displayMessage("Sorry that item is out of stock.");
						} else if (balance.compareTo(product.getPrice()) < 0 ) {
							menu.displayMessage("Insufficient funds, Please add more funds.");
						} else {
							vendingMachine.purchaseProduct(product);
							product.setQuantity(product.getQuantity() - 1);
							String sound = "";
							switch (product.getType()) {
								case "Chip":
									sound = "Crunch, Crunch Yum!";
									break;
								case "Candy":
									sound = "Munch Munch, Yum!";
									break;
								case "Gum":
									sound = "Chew Chew, Yum!";
									break;
								case "Drink":
									sound = "Glug Glug, Chug Chug";
									break;
								default:
									sound = "Aw shucks wrappers again";
									break;
							}
							menu.displayMessage("Enjoy your " + product.getName() + "!" + product.toString());
							Transaction.logTransaction(new Transaction("Purchase", product.getPrice()));
							balance = vendingMachine.getBalance();
							menu.displayMessage("Current active funds: $" + String.format("%.2f",balance));
						}
					}
					break;
				case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
					BigDecimal change = vendingMachine.dispenseChange();
					String formattedChange = String.format("%.2f", change);
					menu.displayMessage("Machine balance equals $" + formattedChange + " Thank you for using the Vendo-Matic 800!");
					break label;
			}
		}
	}
	public void run() {

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			switch (choice) {
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					displayProducts();
					break;
				case MAIN_MENU_OPTION_PURCHASE:
					purchaseProduct();
					break;
				case MAIN_MENU_OPTION_EXIT:
					System.exit(0);
					break;
			}
		}
	}
		public static void main (String[]args){
			VendingMenu menu = new VendingMenu(System.in, System.out);
			VendingMachineCLI cli = new VendingMachineCLI(menu);
			cli.run();
		}


	}


