package Finsight.MONEY.DAOS;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Finsight.MONEY.ENTITY.User;
import Finsight.MONEY.REPOSITORY.UserRepository;
@Repository
public class UserDAO 
{
	@Autowired
	private UserRepository userRepo;
	
	//register new user
	public User register(User us)
	{
		User user=userRepo.save(us);
		return user;
	}
	
	public Optional<User> findbyEmail(String email)
	{
		return userRepo.findByEmail(email);
		
	}
	
	public Optional<User> FindByID(int userid)
	{
		return userRepo.findById(userid);
		
	}
	
	public void delete(User user) {
	    userRepo.delete(user);
	}
	
	

}
