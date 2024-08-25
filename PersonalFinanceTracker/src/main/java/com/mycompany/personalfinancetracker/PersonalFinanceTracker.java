package com.mycompany.personalfinancetracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Soliman Mahmoud
 */
public class PersonalFinanceTracker {

    static String menu = "1. Input Transactions\n"
            + "2. View Transactions\n"
            + "3. View Summary\n"
            + "4. Get Insights\n"
            + "5. Exit\n";

    static int option;

    static ArrayList<Transaction> allTransactions = new ArrayList();

    static Map<String, Double> categorysSpent = new HashMap<>();

    public static void main(String[] args) {

        do {
            System.out.print(menu);
            System.out.print("Choose an Option: ");
            option = new Scanner(System.in).nextInt();

            switch (option) {
                case 1:
                    inputTransactions();
                    break;
                case 2:
                    viewTransactions();
                    break;
                case 3:
                    viewSummary();
                    break;
                case 4:
                    getInsight();
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!\n");
                    break;
                default:
                    System.out.println("Invalid Options\n");
            }

        } while (option != 5); // Exit

    }

    public static void inputTransactions() {
        try {
            System.out.print("Enter transaction description: ");
            String description = new Scanner(System.in).nextLine();
            System.out.print("Enter transaction amount (positive for income, negative for expense): ");
            double amount = new Scanner(System.in).nextDouble();
            System.out.print("Enter category (e.g., Food, Entertainment, Bills): ");
            String category = new Scanner(System.in).nextLine();

            allTransactions.add(new Transaction(description, category, amount));
            System.out.println("");
        } catch (Exception e) {
            System.out.println("Ensure all inputs are correctly formatted.\n For example, amounts should be numerical"
                    + "values, and descriptions and categories should be properly spelled.\n");
        }
    }

    public static void viewTransactions() {
        System.out.println("\nAll Transactions:");
        System.out.printf("%-20s %-15s %-20s\n", "Description", "Amount", "Category");
        System.out.println("----------------------------------------------");
        for (Transaction transaction : allTransactions) {
            System.out.printf("%-20s %-15s %-20s\n", transaction.getDescription(), transaction.getAmount(), transaction.getCategory());
        }
        viewSort();
    }

    public static void viewSummary() {
        double totalIncome = 0, totalExpenses = 0;
        for (Transaction transaction : allTransactions) {
            if (transaction.getAmount() >= 0) {
                totalIncome += transaction.getAmount();
            } else {
                totalExpenses += transaction.getAmount();
            }
        }
        System.out.println("\nSummary:");
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expenses: " + totalExpenses);
        System.out.println("Balance: " + (totalIncome + totalExpenses) + "\n");
    }

    public static void getInsight() {
        System.out.println("\nSpending Insights:");
        double totalExpenses = 0;
        for (Transaction transaction : allTransactions) {
            if (transaction.getAmount() < 0) {
                totalExpenses += transaction.getAmount();
                if (categorysSpent.containsKey(transaction.getCategory())) {
                    categorysSpent.put(transaction.getCategory(),
                            (transaction.getAmount() + categorysSpent.get(transaction.getCategory())));
                } else {
                    categorysSpent.put(transaction.getCategory(),
                            transaction.getAmount());
                }
            }
        }
        totalExpenses *= -1;
        System.out.println("Total Expenses: " + totalExpenses);
        for (Map.Entry<String, Double> e : categorysSpent.entrySet()) {
            System.out.print("Category: " + e.getKey() + " - Spent: " + (e.getValue() * -1) + " (");
            System.out.printf("%.4f", (((e.getValue() * -1) / totalExpenses) * 100));
            System.out.println("% of total)");
        }
        System.out.println("");
    }

    public static void viewSort() {
        System.out.print("\nDo you want to sort transactions by amount? (y/n): ");
        char op = new Scanner(System.in).nextLine().charAt(0);
        if (op == 'y' || op == 'Y') {
            ArrayList<Transaction> copyAllTransactions = new ArrayList<>(allTransactions);
            System.out.println("transactions sorted by amount (low to high).\n");
            Collections.sort(copyAllTransactions, Transaction.amountComparatorDescending);
            System.out.println("All Transactions (Sorted):");
            System.out.printf("%-20s %-15s %-20s\n", "Description", "Amount", "Category");
            System.out.println("----------------------------------------------");
            for (Transaction transaction : copyAllTransactions) {
                System.out.printf("%-20s %-15s %-20s\n", transaction.getDescription(), transaction.getAmount(), transaction.getCategory());
            }
            System.out.println("");
        }
    }

}

class Transaction {

    private String description, category;
    private double amount = 0;

    public Transaction(String description, String category, double amount) {
        this.description = description;
        this.category = category;
        this.amount = amount;
    }

    public static Comparator<Transaction> amountComparatorDescending = (p1, p2) -> Double.compare(p1.getAmount(), p2.getAmount());

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

}
