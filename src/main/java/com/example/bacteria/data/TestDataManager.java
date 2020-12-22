package com.example.bacteria.data;

import com.example.bacteria.dto.TestResultDto;

import java.util.ArrayList;
import java.util.List;

public class TestDataManager {
    private DataRepository repo;

    public TestDataManager() {
        repo = new DataRepository();
    }

    public Iterable<TestResultDto> getTestResults(int productId) {
          var product = repo.getProduct(productId);

          var categoryId = product.getCategory().getId();

          var limitations = repo.getCategoryLimitations(categoryId);

        List<TestResultDto> items = new ArrayList<>();

        for (var limitation :
                limitations) {
            items.add(new TestResultDto(limitation.getBacteria().getName(), limitation.getLimitation(), 0));
        }

          return items;
    }
}
