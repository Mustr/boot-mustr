package com.mustr.common.excel.reader;


public interface AnalysisEventRegisterCenter {
    
    void appendLister(String name, AnalysisEventListener listener);

    void notifyListeners(OneRowAnalysisFinishEvent event);

    void cleanAllListeners();
}
