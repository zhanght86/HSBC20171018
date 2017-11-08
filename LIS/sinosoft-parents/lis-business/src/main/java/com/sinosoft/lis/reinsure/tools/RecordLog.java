

package com.sinosoft.lis.reinsure.tools;

import java.io.IOException;
import java.util.Calendar;
import java.io.FileWriter;
import java.io.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @Zhang Bin
 * @version 1.0
 */

public class RecordLog {
    public CErrors mErrors = new CErrors();

    public RecordLog() {
    }

    public boolean WriteLog(String fileLogPath, String log) {
        try {
            FileWriter fw = new FileWriter(fileLogPath, true); //写进日志文件中
            fw.write("[" + Calendar.getInstance().getTime() + "]  " + log); //写进日志文件中
            fw.write("\r\n");
            fw.flush(); //刷新该流的缓冲
            fw.close(); //关闭此流，但要先刷新它
            return true;
        } catch (Exception e) {
            buildError("WriteLog","写入日志文件时系统保存，可能是日志文件配置错误。");
            return false;
        }
    }

    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "RecordLog";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    public static void main(String[] args) {

    }
}
