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

          var testResults = repo.getTestResultItems(productId);

        List<TestResultDto> items = new ArrayList<>();

        for (var limitation :
                limitations) {
            items.add(mapTestResult(limitation, testResults));
        }

          return items;
    }

    private TestResultDto mapTestResult(Limitation limitation, Iterable<TestResultItem> testResults) {

        List<TestResultItem> items = new ArrayList<>();

        for (var result :
                testResults) {
            items.add(result);
        }

        var testItem = items.stream().filter(i->i.getBacteria().getId() == limitation.getBacteria().getId()).findFirst();

        var bacteriaName = limitation.getBacteria().getName();
        var categoryLimitation = limitation.getLimitation();
        var testValue = 0;
        var id = 0;
        var bacteriaId = limitation.getBacteria().getId();

        if(testItem.isPresent()) {
            var test = testItem.get();
            bacteriaName = test.getBacteriaName();
            categoryLimitation = test.getCategoryLimit();
            testValue = test.getTestValue();
            id = test.getId();
            bacteriaId = test.getBacteria().getId();
        }

        return new TestResultDto(id, bacteriaName, categoryLimitation, testValue, bacteriaId);
    }
}
