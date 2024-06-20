package com.codehows.zegozero.controller;

import com.codehows.zegozero.dto.Order_Dto;
import com.codehows.zegozero.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
@RequiredArgsConstructor

@Controller
public class order_controller {

    private final OrderService orderService;
    private static final Logger logger = Logger.getLogger(OrderApiController.class.getName());

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

    @PostMapping("/excelorder")
    public String registApiExcelOrder(@RequestParam("file1") MultipartFile file) {
        if (file.isEmpty()) {
            logger.log(Level.SEVERE, "파일이 없습니다.");
            return "redirect:/progressorder";
        }

        try {
            // Excel 파일을 파싱하여 Order_Dto 객체로 변환하는 로직
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            // 3번째 줄 데이터 읽기
            Row row = sheet.getRow(2); // 인덱스는 0부터 시작하므로 2번째 인덱스는 3번째 줄

            // Order_Dto 객체 생성 및 데이터 설정
            Order_Dto orderData = new Order_Dto();
            orderData.setProduct_name(row.getCell(0).getStringCellValue());
            orderData.setQuantity((int) row.getCell(1).getNumericCellValue());
            orderData.setCustomer_name(row.getCell(2).getStringCellValue());
            orderData.setDelivery_address(row.getCell(3).getStringCellValue());
            // 엑셀 등록시 재고는 0, 제작수량 = 주문 수량
            orderData.setUsed_inventory(0);
            orderData.setProduction_quantity((int) row.getCell(1).getNumericCellValue());

            // orderService를 사용하여 저장
            orderService.save(orderData);

            return "redirect:/progressorder";
        } catch (IOException e) {
            logger.log(Level.SEVERE, "파일 처리 중 오류가 발생했습니다.", e);
            return "redirect:/progressorder";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "예상치 못한 오류가 발생했습니다.", e);
            return "redirect:/progressorder";
        }
    }

    @PostMapping("/excelorder2")
    public String registApiExcelOrder2(@RequestParam("file1") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/completedorder";
        }

        try {
            // Excel 파일을 파싱하여 Order_Dto 객체로 변환하는 로직
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            // 3번째 줄 데이터 읽기
            Row row = sheet.getRow(2); // 인덱스는 0부터 시작하므로 2번째 인덱스는 3번째 줄

            // Order_Dto 객체 생성 및 데이터 설정
            Order_Dto orderData = new Order_Dto();
            orderData.setProduct_name(row.getCell(0).getStringCellValue());
            orderData.setQuantity((int) row.getCell(1).getNumericCellValue());
            orderData.setCustomer_name(row.getCell(2).getStringCellValue());
            orderData.setDelivery_address(row.getCell(3).getStringCellValue());
            // 엑셀 등록시 재고는 0, 제작수량 = 주문 수량
            orderData.setUsed_inventory(0);
            orderData.setProduction_quantity((int) row.getCell(1).getNumericCellValue());

            // orderService를 사용하여 저장
            orderService.save(orderData);

            return "redirect:/completedorder";
        } catch (IOException e) {
            logger.log(Level.SEVERE, "파일 처리 중 오류가 발생했습니다.", e);
            return "redirect:/completedorder";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "예상치 못한 오류가 발생했습니다.", e);
            return "redirect:/completedorder";
        }
    }
}
