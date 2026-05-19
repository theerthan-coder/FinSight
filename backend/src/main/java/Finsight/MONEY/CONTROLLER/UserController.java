package Finsight.MONEY.CONTROLLER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Finsight.MONEY.DTOS.UserRequest;
import Finsight.MONEY.DTOS.UserResponse;
import Finsight.MONEY.ENTITY.User;
import Finsight.MONEY.RESPONSES.Responsestructure;
import Finsight.MONEY.SERVICES.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.val;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users/paths")
public class UserController {
	@Autowired
	private UserServices uservice;
	
	@Operation(summary = "register new user")
	// @ApiResponses(value = { @ApiResponse(responseCode = "200", description =
	// "User registered successfully"),
	// @ApiResponse(responseCode = "400", description = "Invalid input data") })
	@PostMapping("/register")
	public User register(@Valid @RequestBody UserRequest request) {
		return uservice.register(request);
	}
	@Operation(summary = "update the user ")
	@PutMapping("/{id}")
	public Responsestructure<UserResponse> update(@PathVariable int id, @RequestBody UserRequest request) {

		return uservice.updateUser(id, request);
	}

	@Operation(summary = "delete the user ")
	@DeleteMapping("/{id}")
	public Responsestructure<String> delete(@PathVariable int id) {
		return uservice.deleteUser(id);
	}

}
