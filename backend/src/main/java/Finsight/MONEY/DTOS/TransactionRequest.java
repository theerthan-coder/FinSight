package Finsight.MONEY.DTOS;

import lombok.Data;

@Data
public class TransactionRequest 
{
	private double amount;

    private String type; // will convert to enum

    private String category;

    private String date;

    private String note;

    private int userId;

}
