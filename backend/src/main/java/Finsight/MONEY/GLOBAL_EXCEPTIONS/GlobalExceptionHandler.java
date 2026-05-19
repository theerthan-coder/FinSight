package Finsight.MONEY.GLOBAL_EXCEPTIONS;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler
{
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<?> illegalArgException(MethodArgumentNotValidException e,HttpServletRequest req)
	{
		APIerror error=new APIerror();
		
		error.setTimeStamp(LocalDateTime.now());
		error.setError(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
		error.setStatus(HttpStatus.EXPECTATION_FAILED.value());
		error.setMessage(e.getMessage());
		error.setPath(req.getRequestURI());
	
		
		return new ResponseEntity<> (error ,HttpStatus.EXPECTATION_FAILED);
		
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<?> ill(IllegalArgumentException e,HttpServletRequest req)
	{
		APIerror error=new APIerror();
		
		error.setTimeStamp(LocalDateTime.now());
		error.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(e.getMessage());
		error.setPath(req.getRequestURI());
	
		
		return new ResponseEntity<> (error ,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(value = InvalidDataException.class)
	public ResponseEntity<?> ill(InvalidDataException e,HttpServletRequest req)
	{
		APIerror error=new APIerror();
		
		error.setTimeStamp(LocalDateTime.now());
		error.setError(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
		error.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		error.setMessage(e.getMessage());
		error.setPath(req.getRequestURI());
	
		
		return new ResponseEntity<> (error ,HttpStatus.NOT_ACCEPTABLE);
		
	}
	

	@ExceptionHandler(value = TransactionNotFoundException.class)
	public ResponseEntity<?> ill(TransactionNotFoundException e,HttpServletRequest req)
	{
		APIerror error=new APIerror();
		
		error.setTimeStamp(LocalDateTime.now());
		error.setError(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
		error.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		error.setMessage(e.getMessage());
		error.setPath(req.getRequestURI());
	
		
		return new ResponseEntity<> (error ,HttpStatus.NOT_ACCEPTABLE);
		
	}
	
	@ExceptionHandler(value = emailNotFound.class)
	public ResponseEntity<?> ill(emailNotFound e,HttpServletRequest req)
	{
		APIerror error=new APIerror();
		
		error.setTimeStamp(LocalDateTime.now());
		error.setError(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
		error.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		error.setMessage(e.getMessage());
		error.setPath(req.getRequestURI());
	
		
		return new ResponseEntity<> (error ,HttpStatus.NOT_ACCEPTABLE);
		
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> ill(Exception e,HttpServletRequest req)
	{
		APIerror error=new APIerror();
		
		error.setTimeStamp(LocalDateTime.now());
		error.setError(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase());
		error.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
		error.setMessage(e.getMessage());
		error.setPath(req.getRequestURI());
	
		
		return new ResponseEntity<> (error ,HttpStatus.REQUEST_TIMEOUT);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
    
}