package Finsight.MONEY.DTOS;

import lombok.Data;

@Data
public class HealthResponse 
{
	 private double totalIncome;
	    private double totalExpense;
	    private double savings;
	    private double savingsPercentage;
	    private String healthStatus;

}
