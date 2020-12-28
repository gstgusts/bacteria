package com.example.bacteria;

import com.example.bacteria.data.DataRepository;
import com.example.bacteria.data.TestDataManager;
import com.example.bacteria.data.TestResultItem;
import com.example.bacteria.dto.TestUpdateDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @PostMapping("/products/{id}")
    public ModelAndView saveTestPosition(@PathVariable int id, @ModelAttribute("testResult") TestUpdateDto updateDto) {

        var product = repo.getProduct(id);
        var bacteria = repo.getBacteria(updateDto.getBacteriaId());

        var testResultItem = new TestResultItem(updateDto.getTestId(),
                updateDto.getTestValue(),
                updateDto.getCategoryLimit(),
                updateDto.getBacteriaName(), product, bacteria);

        if(updateDto.getTestId() == 0) {
            repo.add(testResultItem);
        } else {
            repo.save(testResultItem);
        }

        return new ModelAndView("redirect:/products/" + id);
    }
}
