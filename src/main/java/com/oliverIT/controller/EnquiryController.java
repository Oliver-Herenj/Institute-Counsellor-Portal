package com.oliverIT.controller;

import com.oliverIT.dto.ViewEnqFilterRequest;
import com.oliverIT.entity.Enquiry;
import com.oliverIT.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EnquiryController {

    private EnquiryService enquiryService;

    public EnquiryController(EnquiryService enquiryService) {
        this.enquiryService = enquiryService;
    }

    @GetMapping("/view-enquiries")
    public String getEnquiries(HttpServletRequest request, Model model) {

        // get existing session
        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");

        List<Enquiry> enqList = enquiryService.getAllEnquiries(counsellorId);
        model.addAttribute("enquiries", enqList);

        ViewEnqFilterRequest filterReq = new ViewEnqFilterRequest();
        model.addAttribute("viewEnqFilterRequest", filterReq);

        return "viewEnqsPage";
    }

    @PostMapping("/filter-enqs")
    public String filterEnquirires(ViewEnqFilterRequest viewEnqFilterRequest, HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");

        List<Enquiry> enqList = enquiryService.getEnquiriesWithFilter(viewEnqFilterRequest, counsellorId);
        model.addAttribute("enquiries", enqList);

        return "viewEnqsPage";
    }

    @GetMapping("/deleteEnq")
    public String deleteEnquiry(@RequestParam("enqId") Integer enqId, HttpServletRequest request, Model model) {

        //delete enquiry from the database
        enquiryService.deleteEnquiryById(enqId);

        return "redirect:/view-enquiries";
    }

    @GetMapping("/editEnq")
    public String editEnq(@RequestParam("enqId") Integer enqId, Model model) {

        Enquiry enquiry = enquiryService.getEnquiriesById(enqId);
        model.addAttribute("enq", enquiry);

        return "enquiryForm";
    }

    @GetMapping("/enquiry")
    public String addEnquiryPage(Model model) {

        Enquiry enqObj = new Enquiry();
        model.addAttribute("enq", enqObj);

        return "enquiryForm";
    }

    @PostMapping("/addEnq")
    public String handleAddEnquiry(@ModelAttribute("enq") Enquiry enq, HttpServletRequest request, Model model)
            throws Exception {

        // get existing session
        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");

        boolean isSaved = enquiryService.addEnquiry(enq, counsellorId);
        if (isSaved) {
            model.addAttribute("smsg", "Enquiry Added");
        } else {
            model.addAttribute("emsg", "Failed to add Enquiry");
        }

        return "enquiryForm";

    }


}
