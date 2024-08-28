package com.oliverIT.service;

import java.util.List;

import com.oliverIT.dto.ViewEnqFilterRequest;
import com.oliverIT.entity.Counsellor;
import com.oliverIT.entity.Enquiry;

public interface EnquiryService {
	public boolean addEnquiry(Enquiry enquiry,Integer counsellorId) throws Exception;

	public List<Enquiry> getAllEnquiries(Integer counsellorId);

	public List<Enquiry> getEnquiriesWithFilter(ViewEnqFilterRequest viewEnqFilterRequest,Integer counsellorId);

	public Enquiry getEnquiriesById(Integer enqId);

	public void deleteEnquiryById(Integer enqId);
}
