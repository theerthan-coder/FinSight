package Finsight.MONEY.DTOS;

import lombok.Data;

@Data
public class UserResponse
{
	private int userId;
	private String userName;
	private String email;
	private String password;
	private String phoneNr;

}
