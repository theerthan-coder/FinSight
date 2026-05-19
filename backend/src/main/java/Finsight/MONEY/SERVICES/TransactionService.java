package Finsight.MONEY.SERVICES;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Finsight.MONEY.DAOS.TransactionDAO;
import Finsight.MONEY.DTOS.*;
import Finsight.MONEY.ENTITY.Transactions;
import Finsight.MONEY.ENTITY.User;
import Finsight.MONEY.FUNCTIONS.SummaryResponse;
import Finsight.MONEY.GLOBAL_EXCEPTIONS.InvalidDataException;
import Finsight.MONEY.GLOBAL_EXCEPTIONS.TransactionNotFoundException;
import Finsight.MONEY.GLOBAL_EXCEPTIONS.UserNotFoundException;
import Finsight.MONEY.REPOSITORY.UserRepository;
import Finsight.MONEY.RESPONSES.Responsestructure;
import Finsight.MONEY.TRANSACTION_TYPES.TransactionType;

@Service
public class TransactionService {

	@Autowired
	private TransactionDAO tdao;

	@Autowired
	private UserRepository urep;

	// ADD TRANSACTION

	public Responsestructure<TransactionResponse> addTransaction(TransactionRequest tr) {

		if (tr.getUserId() <= 0)
			throw new InvalidDataException("Invalid user id");

		User user = urep.findById(tr.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

		if (tr.getAmount() <= 0)
			throw new InvalidDataException("Amount must be greater than 0");

		if (tr.getCategory() == null || tr.getCategory().isBlank())
			throw new InvalidDataException("Category cannot be empty");

		LocalDate date;
		try {
			date = LocalDate.parse(tr.getDate());
		} catch (Exception e) {
			throw new InvalidDataException("Invalid date format");
		}

		if (date.isAfter(LocalDate.now()))
			throw new InvalidDataException("Future date not allowed");

		TransactionType type;
		try {
			type = TransactionType.valueOf(tr.getType().toUpperCase());
		} catch (Exception e) {
			throw new InvalidDataException("Invalid transaction type");
		}

		Transactions t = new Transactions();
		t.setAmount(tr.getAmount());
		t.setCategory(tr.getCategory());
		t.setDate(date);
		t.setNote(tr.getNote());
		t.setType(type);
		t.setUser(user);

		Transactions saved = tdao.save(t);

		TransactionResponse res = new TransactionResponse();
		res.setId(saved.getId());
		res.setAmount(saved.getAmount());
		res.setType(saved.getType().name());
		res.setCategory(saved.getCategory());
		res.setDate(saved.getDate().toString());
		res.setNote(saved.getNote());
		res.setUserId(saved.getUser().getUserId());
		res.setUserName(saved.getUser().getUserName());

		return buildResponse(res, "Transaction added successfully", 201);
	}

	// GET BY USER

	public Responsestructure<List<TransactionResponse>> getTransactionsByUser(int userId) {

		if (!urep.existsById(userId))
			throw new UserNotFoundException("User not found");

		List<Transactions> list = tdao.findByUserId(userId);

		if (list.isEmpty())
			throw new TransactionNotFoundException("No transactions found");

		List<TransactionResponse> responseList = new ArrayList<>();

		for (Transactions t : list) {
			TransactionResponse res = new TransactionResponse();
			res.setId(t.getId());
			res.setAmount(t.getAmount());
			res.setType(t.getType().name());
			res.setCategory(t.getCategory());
			res.setDate(t.getDate().toString());
			res.setNote(t.getNote());
			res.setUserId(t.getUser().getUserId());
			res.setUserName(t.getUser().getUserName());
			responseList.add(res);
		}

		return buildResponse(responseList, "Transactions retrieved", 200);
	}

	// SUMMARY

	public Responsestructure<SummaryResponse> totalSummary(int userId) {

		if (!urep.existsById(userId))
			throw new UserNotFoundException("User not found");

		List<Transactions> list = tdao.findByUserId(userId);

		if (list.isEmpty())
			throw new TransactionNotFoundException("No data for summary");

		double income = 0, expense = 0;

		for (Transactions t : list) {
			if (t.getType() == TransactionType.INCOME)
				income += t.getAmount();
			else
				expense += t.getAmount();
		}

		SummaryResponse res = new SummaryResponse();
		res.setTotalIncome(income);
		res.setTotalExpense(expense);
		res.setSavings(income - expense);

		return buildResponse(res, "Summary calculated", 200);
	}

	// CATEGORY SUMMARY

	public Responsestructure<Map<String, Double>> getCategorySummary(int userId) {

		if (!urep.existsById(userId))
			throw new UserNotFoundException("User not found");

		List<Transactions> list = tdao.findByUserId(userId);

		Map<String, Double> map = new HashMap<>();

		for (Transactions t : list) {
			if (t.getType() == TransactionType.EXPENSE) {
				map.put(t.getCategory(), map.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
			}
		}

		if (map.isEmpty())
			throw new TransactionNotFoundException("No expense data");

		return buildResponse(map, "Category summary generated", 200);
	}

	// CATEGORY FILTER

	public Responsestructure<Map<String, Double>> getCategoryWise(int userId, String category) {

		if (!urep.existsById(userId))
			throw new UserNotFoundException("User not found");

		if (category == null || category.isBlank())
			throw new InvalidDataException("Category required");

		List<Transactions> list = tdao.findByUserIdAndCategory(userId, category);

		if (list.isEmpty())
			throw new TransactionNotFoundException("No transactions for category");

		double total = 0;

		for (Transactions t : list) {
			if (t.getType() == TransactionType.EXPENSE)
				total += t.getAmount();
		}

		Map<String, Double> map = new HashMap<>();
		map.put(category, total);

		return buildResponse(map, "Category filtered result", 200);
	}

	// DATE RANGE

	public Responsestructure<List<TransactionRangeResponse>> getTransactionsByDateRange(int userId, String from,
			String to) {

		if (!urep.existsById(userId))
			throw new UserNotFoundException("User not found");

		LocalDate start, end;

		try {
			start = LocalDate.parse(from);
			end = LocalDate.parse(to);
		} catch (Exception e) {
			throw new InvalidDataException("Invalid date format");
		}

		if (start.isAfter(end))
			throw new InvalidDataException("From date cannot be after To date");

		List<Transactions> list = tdao.findByUserAndDateRange(userId, start, end);

		if (list.isEmpty())
			throw new TransactionNotFoundException("No transactions in range");

		List<TransactionRangeResponse> responseList = new ArrayList<>();

		for (Transactions t : list) {
			if (t.getType() == TransactionType.EXPENSE) {
				TransactionRangeResponse res = new TransactionRangeResponse();
				res.setCategory(t.getCategory());
				res.setDate(t.getDate().toString());
				res.setAmount(t.getAmount());
				responseList.add(res);
			}
		}

		return buildResponse(responseList, "Range data fetched", 200);
	}

	
	// HEALTH SCORE
	
	public Responsestructure<HealthResponse> getHealthScore(int userId) {

		if (!urep.existsById(userId))
			throw new UserNotFoundException("User not found");

		List<Transactions> list = tdao.findByUserId(userId);

		if (list.isEmpty())
			throw new TransactionNotFoundException("No data for health");

		double income = 0, expense = 0;

		for (Transactions t : list) {
			if (t.getType() == TransactionType.INCOME)
				income += t.getAmount();
			else
				expense += t.getAmount();
		}

		double savings = income - expense;
		double percentage = (income > 0) ? (savings / income) * 100 : 0;

		String status = (percentage > 50) ? "EXCELLENT"
				: (percentage > 30) ? "GOOD" : (percentage > 10) ? "AVERAGE" : "POOR";

		HealthResponse res = new HealthResponse();
		res.setTotalIncome(income);
		res.setTotalExpense(expense);
		res.setSavings(savings);
		res.setSavingsPercentage(percentage);
		res.setHealthStatus(status);

		return buildResponse(res, "Health calculated", 200);
	}

	
	// INSIGHTS 
	
	public Responsestructure<InsightResponse> getInsights(int userId) {

		if (!urep.existsById(userId))
			throw new UserNotFoundException("User not found");

		List<Transactions> list = tdao.findByUserId(userId);

		if (list.isEmpty())
			throw new TransactionNotFoundException("No data for insights");

		Map<String, Double> map = new HashMap<>();

		for (Transactions t : list) {
			if (t.getType() == TransactionType.EXPENSE) {
				map.put(t.getCategory(), map.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
			}
		}

		String topCategory = map.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
				.orElse("N/A");

		LocalDate now = LocalDate.now();
		int currentMonth = now.getMonthValue();
		int lastMonth = (currentMonth == 1) ? 12 : currentMonth - 1;

		double currentExpense = 0, lastExpense = 0;
		double currentIncome = 0, lastIncome = 0;

		for (Transactions t : list) {

			int month = t.getDate().getMonthValue();

			if (t.getType() == TransactionType.EXPENSE) {
				if (month == currentMonth)
					currentExpense += t.getAmount();
				else if (month == lastMonth)
					lastExpense += t.getAmount();
			} else {
				if (month == currentMonth)
					currentIncome += t.getAmount();
				else if (month == lastMonth)
					lastIncome += t.getAmount();
			}
		}

		String trend = (currentExpense > lastExpense) ? "INCREASED"
				: (currentExpense < lastExpense) ? "DECREASED" : "SAME";

		List<String> suggestions = new ArrayList<>();

		if (currentExpense > lastExpense)
			suggestions.add("Spending increased");

		if (currentIncome > lastIncome)
			suggestions.add("Income increased");

		if (map.isEmpty())
			suggestions.add("No expense data");

		InsightResponse res = new InsightResponse();
		res.setTopCategory(topCategory);
		res.setCurrentMonthExpense(currentExpense);
		res.setLastMonthExpense(lastExpense);
		res.setCurrentMonthIncome(currentIncome);
		res.setLastMonthIncome(lastIncome);
		res.setTrend(trend);
		res.setSuggestions(suggestions);

		return buildResponse(res, "Insights generated", 200);
	}

	public Responsestructure<TransactionResponse> updateTransaction(int id, TransactionRequest tr) {

		Transactions t = tdao.findById(id).orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

		// VALIDATIONS
		if (tr.getAmount() <= 0)
			throw new InvalidDataException("Amount must be greater than 0");

		if (tr.getCategory() == null || tr.getCategory().isBlank())
			throw new InvalidDataException("Category cannot be empty");

		LocalDate date;
		try {
			date = LocalDate.parse(tr.getDate());
		} catch (Exception e) {
			throw new InvalidDataException("Invalid date format");
		}

		if (date.isAfter(LocalDate.now()))
			throw new InvalidDataException("Future date not allowed");

		TransactionType type;
		try {
			type = TransactionType.valueOf(tr.getType().toUpperCase());
		} catch (Exception e) {
			throw new InvalidDataException("Invalid transaction type");
		}

		// UPDATE FIELDS
		t.setAmount(tr.getAmount());
		t.setCategory(tr.getCategory());
		t.setDate(date);
		t.setNote(tr.getNote());
		t.setType(type);

		Transactions updated = tdao.save(t);

		// RESPONSE
		TransactionResponse res = new TransactionResponse();
		res.setId(updated.getId());
		res.setAmount(updated.getAmount());
		res.setCategory(updated.getCategory());
		res.setDate(updated.getDate().toString());
		res.setNote(updated.getNote());
		res.setType(updated.getType().name());
		res.setUserId(updated.getUser().getUserId());
		res.setUserName(updated.getUser().getUserName());

		return buildResponse(res, "Transaction updated successfully", 200);
	}

	private <T> Responsestructure<T> buildResponse(T data, String msg, int code) {

		Responsestructure<T> res = new Responsestructure<>();
		res.setData(data);
		res.setMsg(msg);
		res.setStatuscode(code);
		res.setTimestamp(LocalDateTime.now());
		return res;
	}

	public Responsestructure<String> deleteTransaction(int transactionId) {

		Transactions t = tdao.findById(transactionId)
				.orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

		tdao.delete(t);

		return buildResponse("Deletion operation", "Transaction deleted successfully", 200);
	}

}
