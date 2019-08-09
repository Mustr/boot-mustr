package com.mustr.common.excel.reader;

/**
 * 读取数据监听处理类
 * @author mustr
 */
public interface AnalysisEventListener {

    /**
     * 读取一行触发的方法
     *
     * @param object 一行就是一个data 默认是list<String>
     * @param context read context
     */
    public void invoke(Object object, AnalysisContext context);

    /**
     * 所有信息读取完之后触发的方法
     *
     * @param context context
     */
    public void doAfterAllAnalysed(AnalysisContext context);
}
