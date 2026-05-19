package Finsight.MONEY.DTOS;

import java.util.List;

import lombok.Data;

@Data
public class InsightResponse 
{
	private double currentMonthExpense;
    private double lastMonthExpense;
    private String trend;
    private String topCategory; 
    private double currentMonthIncome;
    private double lastMonthIncome;

    private List<String> suggestions;

	

}
