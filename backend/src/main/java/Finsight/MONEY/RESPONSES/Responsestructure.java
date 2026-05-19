package Finsight.MONEY.RESPONSES;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class Responsestructure<T> 
{
	private T data;
	
	private LocalDateTime timestamp;
	
	private String msg;
	
	private int statuscode;
	

}