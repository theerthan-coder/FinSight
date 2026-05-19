package Finsight.MONEY.AUTH;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Finsight.MONEY.DTOS.LoginRequest;
import Finsight.MONEY.DTOS.UserRequest;
import Finsight.MONEY.RESPONSES.Responsestructure;
import Finsight.MONEY.SERVICES.UserServices;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employee")
public class AuthController {
	@Autowired
	private UserServices uservice;
	
	@PostMapping("/secret")
	public ResponseEntity<?> login(@RequestBody LoginRequest ur) {
		Responsestructure<?> structure = uservice.secret(ur.getEmail(), ur.getPassword());
		return new ResponseEntity<>(structure, HttpStatus.OK);
	}

}
