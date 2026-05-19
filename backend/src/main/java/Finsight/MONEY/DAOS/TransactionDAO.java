package Finsight.MONEY.DAOS;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Finsight.MONEY.DTOS.TransactionRequest;
import Finsight.MONEY.ENTITY.Transactions;
import Finsight.MONEY.REPOSITORY.TransactionRepo;
import Finsight.MONEY.REPOSITORY.UserRepository;


@Repository
public class TransactionDAO
{
	  @Autowired
	    private TransactionRepo trep;
	  	
	  
	  
	  	//to add a transaction 
	    public Transactions save(Transactions t) {
	        return trep.save(t);
	    }
	
	    //to fetch transactions by user id
	    public List<Transactions> findByUserId(int userId) 
	    {
	        return trep.findByUser_UserId(userId);
	    }
	    //category specified 
	    public List<Transactions> findByUserIdAndCategory(int userId, String category) 
	    {
	        return trep.findByUser_UserIdAndCategory(userId, category);
	    }
	    
	    //range specified
	    public List<Transactions> findByUserAndDateRange(int userId, LocalDate from, LocalDate to) 
	    {
	        return trep.findByUser_UserIdAndDateBetween(userId, from, to);
	    }
	    
	    
	    public Optional<Transactions> findById(int id) {
	        return trep.findById(id);
	    }

	    public void delete(Transactions t) {
	        trep.delete(t);
	    }
	    
	    
	    
	    
	    
	    
	    
	    
}
