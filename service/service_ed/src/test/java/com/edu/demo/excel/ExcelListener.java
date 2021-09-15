package com.edu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("====="+demoData);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("111111");
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
