package dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserDTO {
	private String id;
	private String password;
	private String name;
	private String birth;
	private int gender;
	private String email;
	private String joindate;
	private String img;
}
