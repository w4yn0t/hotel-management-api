package com.starchenko.hotelmanagementapi.controller;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(
                webRequest,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE)
        );
        int status = getStatus(request, errorDetails);
        model.addAttribute("status", status);
        model.addAttribute("error", errorDetails.getOrDefault("error", ""));
        model.addAttribute("message", errorDetails.getOrDefault("message", ""));
        model.addAttribute("timestamp", errorDetails.getOrDefault("timestamp", ""));

        if (status == 404) {
            return "error-404";
        }
        return "error";
    }

    private int getStatus(HttpServletRequest request, Map<String, Object> errorDetails) {
        Object statusAttr = request.getAttribute("javax.servlet.error.status_code");
        if (statusAttr instanceof Integer) {
            return (Integer) statusAttr;
        }
        if (statusAttr instanceof String) {
            try {
                return Integer.parseInt((String) statusAttr);
            } catch (NumberFormatException ignored) {}
        }
        Object fromMap = errorDetails.get("status");
        if (fromMap instanceof Integer) {
            return (Integer) fromMap;
        }
        return 500;
    }
}