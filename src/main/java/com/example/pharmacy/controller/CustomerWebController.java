package com.example.pharmacy.controller;

import com.example.pharmacy.data.entity.Customer;
import com.example.pharmacy.services.CustomerService;
import com.example.pharmacy.data.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerWebController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @Autowired
    public CustomerWebController(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @GetMapping
    public String index() {
        return "/index.html";
    }

    @RequestMapping(value = "/data_for_datatable", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getDataForDatatable(@RequestParam Map<String, Object> params) {
        int draw = params.containsKey("draw") ? Integer.parseInt(params.get("draw").toString()) : 1;
        int length = params.containsKey("length") ? Integer.parseInt(params.get("length").toString()) : 30;
        int start = params.containsKey("start") ? Integer.parseInt(params.get("start").toString()) : 30;
        int currentPage = start / length;

        String sortName = "id";
        String dataTableOrderColumnIdx = params.get("order[0][column]").toString();
        String dataTableOrderColumnName = "columns[" + dataTableOrderColumnIdx + "][data]";
        if (params.containsKey(dataTableOrderColumnName))
            sortName = params.get(dataTableOrderColumnName).toString();
        String sortDir = params.containsKey("order[0][dir]") ? params.get("order[0][dir]").toString() : "asc";

        Sort.Order sortOrder = new Sort.Order((sortDir.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC), sortName);
        Sort sort = Sort.by(sortOrder);

        Pageable pageRequest = PageRequest.of(currentPage,
                length,
                sort);

        String queryString = (String) (params.get("search[value]"));

        Page<Customer> customers = customerService.getCustomersForDatatable(queryString, pageRequest);

        long totalRecords = customers.getTotalElements();

        List<Map<String, Object>> cells = new ArrayList<>();
        customers.forEach(customer -> {
            Map<String, Object> cellData = new HashMap<>();
            cellData.put("id", customer.getId());
            cellData.put("fullName", customer.getFullName());
            cellData.put("birthday", customer.getBirthday());
            cellData.put("inn", customer.getInn());
            cellData.put("familyStatus", customer.getFamilyStatus());
            cellData.put("education", customer.getEducation());
            cellData.put("segment", customer.getSegment());
            cellData.put("customerFamily", customer.getCustomerFamily());
            cells.add(cellData);
        });

        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("draw", draw);
        jsonMap.put("recordsTotal", totalRecords);
        jsonMap.put("recordsFiltered", totalRecords);
        jsonMap.put("data", cells);

        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Customer customerInstance = customerRepository.findById(Long.valueOf(id)).get();
        model.addAttribute("customerInstance", customerInstance);
        return "/edit.html";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("customerInstance") Customer customerInstance,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "/edit.html";
        } else {
            if (customerRepository.save(customerInstance) != null)
                atts.addFlashAttribute("message", "Customer updated successfully");
            else
                atts.addFlashAttribute("message", "Customer update failed.");

            return "redirect:/customer";
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("customerInstance", new Customer());
        return "/create.html";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("customerInstance") Customer customerInstance,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "/create.html";
        } else {
            if (customerRepository.save(customerInstance) != null)
                atts.addFlashAttribute("message", "Customer created successfully");
            else
                atts.addFlashAttribute("message", "Customer creation failed.");

            return "redirect:/customer";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes atts) {
        Customer customerInstance = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer Not Found:" + id));

        customerRepository.delete(customerInstance);

        atts.addFlashAttribute("message", "Customer deleted.");

        return "redirect:/customer";
    }

}
