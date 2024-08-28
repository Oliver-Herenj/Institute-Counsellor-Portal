package com.oliverIT.controller;

import com.oliverIT.dto.DashboardViewResponse;
import com.oliverIT.entity.Counsellor;
import com.oliverIT.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CounsellorController {


    private CounsellorService counsellorService;

    public CounsellorController(CounsellorService counsellorService) {
        this.counsellorService = counsellorService;
    }

    @GetMapping("/")
    public String index(Model model) {

        Counsellor cobj = new Counsellor();
        model.addAttribute("counsellor",cobj);

        return "index";
    }



    @GetMapping("/register")
    public String register(Model model) {

        Counsellor cobj = new Counsellor();
        model.addAttribute("counsellor",cobj);

        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(Counsellor counsellor, Model model) {

        Counsellor byEmail = counsellorService.findByEmail(counsellor.getEmail());
        if(byEmail!=null) {
            model.addAttribute("emsg", "Duplicate Email Id");
            return "register";
        }


        boolean isRegistered = counsellorService.register(counsellor);

        if(isRegistered == true) {
            model.addAttribute("smsg", "CounsellorInfo Registered..!!");
        }
        else {
            model.addAttribute("emsg", "Registration Failed .");
        }

        return "register";

    }

    @GetMapping("/dashboard")
    public String viewDashboard(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");

        DashboardViewResponse dshResdObj = counsellorService.getDashboardViewResponse(counsellorId);
        model.addAttribute("dashboardInfo", dshResdObj);

        return "dashboard";

    }


    @PostMapping("/login")
    public String login(Counsellor counsellor,HttpServletRequest request, Model model) {

        Counsellor login = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());

        if(login == null) {
            model.addAttribute("emsg", "Invalid Crediantials");
            return "index";
        }
        else {

            //valid login, store counsellorId in session for future purpose
            HttpSession session = request.getSession(true);
            session.setAttribute("counsellorId", login.getCounsellorId());

            DashboardViewResponse dshResdObj = counsellorService.getDashboardViewResponse(login.getCounsellorId());
            model.addAttribute("dashboardInfo", dshResdObj);
        }

        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        session.invalidate();

        return "redirect:/";
    }


}