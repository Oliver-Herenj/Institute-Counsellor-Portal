package com.oliverIT.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oliverIT.entity.Enquiry;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EnquiryRepo extends JpaRepository<Enquiry, Integer>{

    @Query(value = "select * from enquiry_tbl where counsellor_id=:counsellorId",nativeQuery = true)
    public List<Enquiry> getEnqByCounsellorId(Integer counsellorId);

//	public boolean deleteEnq

}
