package dto;

import lombok.Data;

@Data
public class ReviewFileDTO {
	private int fileNo;
	private int reviewNo;
	private String original;
	private String copy;
	private int available;
}
