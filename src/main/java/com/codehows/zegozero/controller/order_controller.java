package com.codehows.zegozero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class order_controller {

    @GetMapping("/progressorder")
    public String progressorder() {
        return "progress_order";
    }

    @GetMapping("/completedorder")
    public String completedorder() {
        return "completed_order";
    }

    @GetMapping("/1")
    public String main(){
        return "MaterialManagementPage";
    }

    @GetMapping("/2")
    public String two(){
        return "raw-material-details";
    }

    @GetMapping("/3")
    public String three(){
        return "shipment_management";
    }

    sdfsdfsdf
}
