package Finsight.MONEY.GLOBAL_EXCEPTIONS;

public class TransactionNotFoundException extends RuntimeException
{
	public TransactionNotFoundException(String msg) {
        super(msg);
    }

}
