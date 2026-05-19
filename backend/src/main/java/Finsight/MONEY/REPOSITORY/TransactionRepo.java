package Finsight.MONEY.REPOSITORY;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Finsight.MONEY.ENTITY.Transactions;

public interface TransactionRepo extends JpaRepository<Transactions, Integer> 
{
	//find transaction by user id
	List<Transactions> findByUser_UserId(int userId);
	//category-specified wise
	List<Transactions> findByUser_UserIdAndCategory(int userId, String category);
	//range of date for transaction
	List<Transactions> findByUser_UserIdAndDateBetween(int userId, LocalDate from, LocalDate to);
	
	
	
	
	
	
}
