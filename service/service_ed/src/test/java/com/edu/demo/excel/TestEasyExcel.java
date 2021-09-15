package com.edu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        //实现excel写的操作
        //设置写入文件夹的地址喝excel的名称
        //String filename = "D:/write.xlsx";


        //调用easyexcel里面的方法实现操作
        //EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getList());


        //读取
        String filename = "D:/write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    //创建方法返回的集合
    private static List<DemoData> getList(){
        List<DemoData> list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            DemoData data = new DemoData();
            data.setSname("zhangshan"+i);
            data.setSno(i);
            list.add(data);
        }
        return list;
    }
}
