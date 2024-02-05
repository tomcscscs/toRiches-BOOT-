package org.edupoll.app.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SigninCommand {
	
	@Email
	@NotBlank
	private String inputedEmail;
	
	@Size(min = 4, max = 12)
	private String inputedPassword;
	

}
