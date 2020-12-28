package com.example.bacteria;

import com.example.bacteria.data.Bacteria;
import com.example.bacteria.data.DataRepository;
import com.example.bacteria.data.TestDataManager;
import com.example.bacteria.data.TestResultItem;
import com.example.bacteria.dto.TestUpdateDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

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

    @GetMapping("file_upload")
    public String uploadFile(Model model) {
        return "file_upload";
    }

    @PostMapping("file_upload")
    public String saveFile(@RequestParam("file") MultipartFile file, ModelMap modelMap) {

        try {
            String s = new String(file.getBytes(), StandardCharsets.UTF_8);

            var lines = s.lines();
            var listOfLines = lines.collect(Collectors.toList());

            for (var line :
                    listOfLines) {

                var parts = line.split(",");

                var bac = new Bacteria(Integer.valueOf(parts[0]), parts[1]);

                if(bac.getId() == 0) {
                    repo.add(bac);
                } else {
                    repo.save(bac);
                }

                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "file_upload";
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
