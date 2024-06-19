package com.codehows.zegozero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class order_controller {

    @GetMapping("/main")
    public String current_status_management() {
        return "current_status_management";
    }

    @GetMapping("/progressorder")
    public String progress_order() {
        return "progress_order";
    }

    @GetMapping("/completedorder")
    public String completed_order() {
        return "completed_order";
    }

    @GetMapping("/productionplan")
    public String production_plan(){
        return "production_plan";
    }

    @GetMapping("/materialordermanagement")
    public String material_order_management(){
        return "material_order_management";
    }

    @GetMapping("/materialinventorymanagement")
    public String material_inventory_management(){
        return "material_inventory_management";
    }

    @GetMapping("/finishedproductmanagement")
    public String finished_product_management(){
        return "finished_product_management";
    }

    @GetMapping("/facilitymanagement")
    public String facility_management(){
        return "facility_management";
    }

    @GetMapping("/shipmentmanagement")
    public String shipment_management(){
        return "shipment_management";
    }

    @GetMapping("/example")
    public String example(){
        return "example";
    }
}
