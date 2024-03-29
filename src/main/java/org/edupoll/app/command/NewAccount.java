package org.edupoll.app.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class NewAccount {

	@Email
	private String username;

	@Size(min = 4, max = 7)
	private String password;

	@Size(min = 2, max = 12)
	private String nickname;

}
