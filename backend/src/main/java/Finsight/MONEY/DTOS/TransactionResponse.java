package Finsight.MONEY.DTOS;

import lombok.Data;

@Data
public class TransactionResponse
{
	

    private int id;
    private double amount;
    private String type;
    private String category;
    private String date;
    private String note;

    private int userId;
    private String userName;
}
