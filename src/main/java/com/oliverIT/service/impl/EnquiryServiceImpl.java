package com.oliverIT.service.impl;

import java.util.List;
import java.util.Optional;

import com.oliverIT.repo.CounsellorRepo;
import com.oliverIT.service.EnquiryService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.oliverIT.dto.ViewEnqFilterRequest;
import com.oliverIT.entity.Counsellor;
import com.oliverIT.entity.Enquiry;
import com.oliverIT.repo.EnquiryRepo;


@Service
public class EnquiryServiceImpl implements EnquiryService{


	private EnquiryRepo enquiryRepo;

	private CounsellorRepo counsellorRepo;

	public EnquiryServiceImpl(EnquiryRepo enquiryRepo, CounsellorRepo counsellorRepo) {
		this.enquiryRepo = enquiryRepo;
		this.counsellorRepo = counsellorRepo;
	}

	@Override
	public boolean addEnquiry(Enquiry enquiry, Integer counsellorId) throws Exception {

		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);

		if(counsellor == null) {
			throw new Exception("No counsellor found");
		}
		enquiry.setCounsellor(counsellor);

		Enquiry save = enquiryRepo.save(enquiry);
		if(save.getEnqId()!=null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Enquiry> getAllEnquiries(Integer counsellorId) {

		List<Enquiry> enqList = enquiryRepo.getEnqByCounsellorId(counsellorId);

		return enqList;
	}

	@Override
	public List<Enquiry> getEnquiriesWithFilter(ViewEnqFilterRequest viewEnqFilterRequest, Integer counsellorId) {

		// QBE Dynamic Query Example

		Enquiry enquiry = new Enquiry();

		if(StringUtils.isNotEmpty(viewEnqFilterRequest.getClassMode())) {
			enquiry.setClassMode(viewEnqFilterRequest.getClassMode());
		}

		if(StringUtils.isNotEmpty(viewEnqFilterRequest.getCourse())) {
			enquiry.setCourse(viewEnqFilterRequest.getCourse());
		}

		if(StringUtils.isNotEmpty(viewEnqFilterRequest.getStatus())) {
			enquiry.setStatus(viewEnqFilterRequest.getStatus());
		}

		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);
		enquiry.setCounsellor(counsellor);

		Example<Enquiry> of = Example.of(enquiry);

		List<Enquiry> enqList = enquiryRepo.findAll(of);

		return enqList;
	}


	@Override
	public Enquiry getEnquiriesById(Integer enqId) {

		return enquiryRepo.findById(enqId).orElse(null);

	}

	@Override
	public void deleteEnquiryById(Integer enqId) {

		enquiryRepo.deleteById(enqId);

		return;
	}

}

