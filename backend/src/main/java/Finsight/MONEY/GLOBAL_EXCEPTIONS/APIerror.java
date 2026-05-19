package Finsight.MONEY.GLOBAL_EXCEPTIONS;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class APIerror
{
	public LocalDateTime timeStamp;
	
	private int status;
	
	private String error;
	
	private String path;
	
	private String message;

}

