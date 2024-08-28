package com.oliverIT.service;

import com.oliverIT.dto.DashboardViewResponse;
import com.oliverIT.entity.Counsellor;

public interface CounsellorService {

	public Counsellor findByEmail(String email);
	public boolean register(Counsellor counsellor);
	public Counsellor login(String email,String pwd);
	public DashboardViewResponse getDashboardViewResponse(Integer counsellorId);





}
