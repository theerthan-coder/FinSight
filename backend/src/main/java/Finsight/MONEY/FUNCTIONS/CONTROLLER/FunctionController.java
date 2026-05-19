package Finsight.MONEY.FUNCTIONS.CONTROLLER;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Finsight.MONEY.DTOS.HealthResponse;
import Finsight.MONEY.DTOS.InsightResponse;
import Finsight.MONEY.DTOS.TransactionRangeResponse;
import Finsight.MONEY.FUNCTIONS.SummaryResponse;
import Finsight.MONEY.RESPONSES.Responsestructure;
import Finsight.MONEY.SERVICES.TransactionService;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/function")
public class FunctionController 
{
	@Autowired
	 private TransactionService service;
	
	
	@GetMapping("/summary/user/{id}")
	public Responsestructure<SummaryResponse> getSummary(@PathVariable int id) {
	    return service.totalSummary(id);
	}
	
	
	@GetMapping("/category/user/{id}")
	public Responsestructure<Map<String, Double>> getCategorySummary(@PathVariable int id) {
	    return service.getCategorySummary(id);
	}
	
	
	@GetMapping("/category/{category}/{id}")
	public Responsestructure<Map<String, Double>> getCategoryWise(
	        @PathVariable String category,
	        @PathVariable int id) {

	    return service.getCategoryWise(id, category);
	}
	
	@GetMapping("/range")
	public Responsestructure<List<TransactionRangeResponse>> getByRange(
	        @RequestParam int userId,
	        @RequestParam String from,
	        @RequestParam String to) {

	    return service.getTransactionsByDateRange(userId, from, to);
	}
	
	@GetMapping("/financial-health/{id}")
	public Responsestructure<HealthResponse> getHealth(@PathVariable int id) 
	{
	    return service.getHealthScore(id);
	}
	
	
	@GetMapping("/insights/{id}")
	public Responsestructure<InsightResponse> getInsights(@PathVariable int id) {
	    return service.getInsights(id);
	}
	

}
