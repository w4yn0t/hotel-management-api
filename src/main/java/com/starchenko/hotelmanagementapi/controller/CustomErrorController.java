package com.starchenko.hotelmanagementapi.controller;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @org.springframework.web.bind.annotation.GetMapping("/error")
    public String handleError(WebRequest webRequest, Model model) {
        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
        int status = (int) errorDetails.get("status");
        model.addAttribute("status", status);
        model.addAttribute("error", errorDetails.get("error"));
        model.addAttribute("message", errorDetails.get("message"));
        model.addAttribute("timestamp", errorDetails.get("timestamp"));

        if (status == 404) {
            return "error-404";
        }
        return "error";
    }
}