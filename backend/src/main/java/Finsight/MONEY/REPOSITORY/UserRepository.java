package Finsight.MONEY.REPOSITORY;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Finsight.MONEY.ENTITY.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
	Optional<User> findByEmail(String email);
	
	 
	

}
