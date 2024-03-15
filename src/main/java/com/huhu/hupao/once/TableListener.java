package com.huhu.hupao.once;

import com.alibaba.excel.context.AnalysisContext;

import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


// 有个很重要的点 TableListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Slf4j
public class TableListener implements ReadListener<XingQiuTableUserInfo> {

    @Override
    public void onException(Exception e, AnalysisContext analysisContext) throws Exception {

    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> map, AnalysisContext analysisContext) {

    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(XingQiuTableUserInfo data, AnalysisContext context) {
        System.out.println(data);
    }

    @Override
    public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {

    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("已解析完成");
    }

    @Override
    public boolean hasNext(AnalysisContext analysisContext) {
        return false;
    }

}