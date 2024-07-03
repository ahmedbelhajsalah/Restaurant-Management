package com.restaurant.restaurant_spring.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.restaurant.restaurant_spring.services.PaypalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:4200")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/customer")
public class PayPalController {

    private final PaypalService paypalService;

    @PostMapping("/payment/create/{priceToPay}")
    @ResponseBody
    public Map<String, String> createPayment(@PathVariable Number priceToPay) {
        Map<String, String> response = new HashMap<>();
        try {
            String cancelUrl = "http://localhost:4200/customer/dashboard";
            String successUrl = "http://localhost:4200/customer/dashboard";
            Payment payment = paypalService.createPayment(
                    priceToPay,
                    "USD",
                    "paypal",
                    "sale",
                    "Payment description",
                    cancelUrl,
                    successUrl
            );
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    response.put("approval_url", links.getHref());
                    return response;
                }
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred: ", e);
        }
        response.put("error", "Payment creation failed");
        return response;
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("payerId") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred: ", e);
        }
        return "paymentSuccess";
    }
}
