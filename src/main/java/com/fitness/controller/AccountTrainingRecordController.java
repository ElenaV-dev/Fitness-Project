package com.fitness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account/training-records")
public class AccountTrainingRecordController {

    public String myRecords(Model model) {

        model.addAttribute("activeTab", "trainingRecords");
        return "account/training-records/list";
    }
}
