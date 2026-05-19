package Finsight.MONEY.CONTROLLER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Finsight.MONEY.DTOS.TransactionRequest;
import Finsight.MONEY.DTOS.TransactionResponse;
import Finsight.MONEY.RESPONSES.Responsestructure;
import Finsight.MONEY.SERVICES.TransactionService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/transaction")
public class TransactionController {
	@Autowired
	private TransactionService service;
	
	
	@Operation(summary = "Add a new transaction ")
	@PostMapping("/addt")
	public ResponseEntity<?> add(@RequestBody TransactionRequest request) {
		Responsestructure<?> rs = service.addTransaction(request);
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	
	@Operation(summary = "get transaction by the user ")
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getByUser(@PathVariable int id) {
		Responsestructure<?> tr = service.getTransactionsByUser(id);
		return new ResponseEntity<>(tr, HttpStatus.OK);
	}

	
	@Operation(summary = "delete the transaction ")
	@DeleteMapping("/delete/transaction-id/{id}")
	public Responsestructure<String> delete(@PathVariable int id) {
		return service.deleteTransaction(id);
	}
	
	@Operation(summary = "update the transaction ")
	@PutMapping("/update/transaction-id/{id}")
	public Responsestructure<TransactionResponse> update(@PathVariable int id,
			@RequestBody TransactionRequest request) {

		return service.updateTransaction(id, request);
	}

}
