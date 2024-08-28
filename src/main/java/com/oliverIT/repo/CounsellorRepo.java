package com.oliverIT.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oliverIT.entity.Counsellor;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer>{

    //select * from counsellor_tbl where counsellor_id=:counsellorId
    public Counsellor findByEmail(String email);

    //select * from counsellor_tbl where email=:email and pwd=:pwd;
    public Counsellor findByEmailAndPwd(String email,String pwd);


}
