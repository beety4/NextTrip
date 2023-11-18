package dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReviewDTO {
	private int reviewNo;
	private String name;
	private String title;
	private String category;
	private String content;
	private LocalDate date;
	private int hit;
	private int available;
}
