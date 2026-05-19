package Finsight.MONEY.DTOS;

import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class UserRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 20, message = "Name must be 3-20 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name should contain only alphabets")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 12, message = "Password must be 6-12 characters")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]+$",
        message = "Password must contain letters and numbers"
    )
    private String password;

    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Phone number must be exactly 10 digits"
    )
    private String phoneNumber;
}