package com.example.bacteria;

import com.example.bacteria.data.DataRepository;
import com.example.bacteria.data.TestDataManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    private DataRepository repo;

    public MainController() {
        repo = new DataRepository();
    }

    @GetMapping("")
    public String index(Model model) {

        model.addAttribute("products", repo.getProducts());

        return "index";
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable int id, Model model) {
        model.addAttribute("product", repo.getProduct(id));

        var testDataManager = new TestDataManager();

        model.addAttribute("testResults", testDataManager.getTestResults(id));

        return "product_details";
    }
}
