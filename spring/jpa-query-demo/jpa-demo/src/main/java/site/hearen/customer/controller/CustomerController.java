package site.hearen.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import site.hearen.customer.entity.Customer;
import site.hearen.customer.service.CustomerService;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    @ResponseBody
    public List<Customer> search(@RequestParam(value = "search") String search) {
        return customerService.findAll(search);
    }
}
