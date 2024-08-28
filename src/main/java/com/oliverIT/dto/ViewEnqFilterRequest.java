package com.oliverIT.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViewEnqFilterRequest {
	
	private String classMode;
	private String course;
	private String status;

}
