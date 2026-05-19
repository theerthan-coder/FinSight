package Finsight.MONEY.SERVICES;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import Finsight.MONEY.DAOS.UserDAO;
import Finsight.MONEY.DTOS.LoginResponse;
import Finsight.MONEY.DTOS.UserRequest;
import Finsight.MONEY.DTOS.UserResponse;
import Finsight.MONEY.ENTITY.User;
import Finsight.MONEY.GLOBAL_EXCEPTIONS.DuplicateUserException;
import Finsight.MONEY.GLOBAL_EXCEPTIONS.InvalidDataException;
import Finsight.MONEY.GLOBAL_EXCEPTIONS.UserNotFoundException;
import Finsight.MONEY.GLOBAL_EXCEPTIONS.emailNotFound;
import Finsight.MONEY.REPOSITORY.UserRepository;
import Finsight.MONEY.RESPONSES.Responsestructure;

@Service
public class UserServices {
	@Autowired
	private UserDAO udao;

	public User register(UserRequest request) {
		User user = new User();

		user.setUserName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setPhoneNr(request.getPhoneNumber());

		return udao.register(user);

	}

	public Responsestructure<LoginResponse> secret(String email, String password) {
		Responsestructure<LoginResponse> structure = new Responsestructure<>();

		User user = udao.findbyEmail(email).orElseThrow(() -> new emailNotFound("This email not exists !"));

		if (user.getPassword().equals(password)) {
			LoginResponse logresponse = new LoginResponse();
			logresponse.setUserId(user.getUserId());
			logresponse.setEmail(user.getEmail());
			logresponse.setName(user.getUserName());

			structure.setData(logresponse);
			structure.setMsg("the user data exist and logged in");
			structure.setStatuscode(200);
			structure.setTimestamp(LocalDateTime.now());
		} else {
			throw new InvalidDataException("Invalid password");
		}

		return structure;
	}

	public Responsestructure<UserResponse> updateUser(int userId, UserRequest request) {
		Responsestructure<UserResponse> structure = new Responsestructure<>();

		User user = udao.FindByID(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		// VALIDATIONS
		if (request.getName() == null || request.getName().isBlank())
			throw new InvalidDataException("Name cannot be empty");

		if (request.getEmail() == null || request.getEmail().isBlank())
			throw new InvalidDataException("Email cannot be empty");

		if (request.getPassword() == null || request.getPassword().length() < 6)
			throw new InvalidDataException("Password must be at least 6 characters");

		if (request.getPhoneNumber() == null || request.getPhoneNumber().length() != 10)
			throw new InvalidDataException("Invalid phone number");

		Optional<User> existingUser = udao.findbyEmail(request.getEmail());

		if (existingUser.isPresent() && existingUser.get().getUserId() != userId)
			throw new DuplicateUserException("Email already exists");

		// UPDATE
		user.setUserName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setPhoneNr(request.getPhoneNumber());

		User updated = udao.register(user);

		// RESPONSE
		UserResponse res = new UserResponse();
		res.setUserId(updated.getUserId());
		res.setUserName(updated.getUserName());
		res.setEmail(updated.getEmail());
		res.setPhoneNr(updated.getPhoneNr());

		structure.setData(res);
		structure.setMsg("User updated successfully");
		structure.setStatuscode(200);
		structure.setTimestamp(LocalDateTime.now());

		return structure;
	}

	public Responsestructure<String> deleteUser(int userId) {
		Responsestructure<String> structure = new Responsestructure<>();

		User user = udao.FindByID(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		udao.delete(user);

		structure.setData("User deleted successfully");
		structure.setMsg("User deleted");
		structure.setStatuscode(200);
		structure.setTimestamp(LocalDateTime.now());

		return structure;
	}

}
