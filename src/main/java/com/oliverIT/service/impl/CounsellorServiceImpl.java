package com.oliverIT.service.impl;

import com.oliverIT.entity.Enquiry;
import com.oliverIT.repo.CounsellorRepo;
import com.oliverIT.repo.EnquiryRepo;
import com.oliverIT.service.CounsellorService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oliverIT.dto.DashboardViewResponse;
import com.oliverIT.entity.Counsellor;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CounsellorServiceImpl implements CounsellorService {

	private CounsellorRepo counsellorRepo;

	private EnquiryRepo enquiryRepo;

	public CounsellorServiceImpl(CounsellorRepo counsellorRepo, EnquiryRepo enquiryRepo) {
		this.counsellorRepo = counsellorRepo;
		this.enquiryRepo = enquiryRepo;
	}


	@Override
	public boolean register(Counsellor counsellor) {
		Counsellor savedCounsellor = counsellorRepo.save(counsellor);

		if (savedCounsellor.getCounsellorId() != null) {
			return true;
		}

		return false;
	}

	public Counsellor findByEmail(String email){

		return counsellorRepo.findByEmail(email);
	}

	@Override
	public Counsellor login(String email, String pwd) {
		Counsellor counsellor = counsellorRepo.findByEmailAndPwd(email, pwd);
		return counsellor;
	}

	@Override
	public DashboardViewResponse getDashboardViewResponse(Integer counsellorId) {

		DashboardViewResponse response = new DashboardViewResponse();

		List<Enquiry> enqList = enquiryRepo.getEnqByCounsellorId(counsellorId);

		int totalEnqs = enqList.size();

		int enrolledEnqs = enqList.stream()
				.filter(e -> e.getStatus()
						.equals("Enrolled"))
				.collect(Collectors.toList())
				.size();

		int lostEnqs = enqList.stream()
				.filter(e -> e.getStatus()
						.equals("Lost"))
				.collect(Collectors.toList())
				.size();

		int openEnqs = enqList.stream()
				.filter(e -> e.getStatus()
						.equals("Open"))
				.collect(Collectors.toList())
				.size();

		response.setTotalEnquiries(totalEnqs);
		response.setEnrolledEnquiries(enrolledEnqs);
		response.setLostEnquiries(lostEnqs);
		response.setOpenEnquiries(openEnqs);

		return response;
	}



}

