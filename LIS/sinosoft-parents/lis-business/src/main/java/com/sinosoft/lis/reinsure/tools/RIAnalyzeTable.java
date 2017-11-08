

package com.sinosoft.lis.reinsure.tools;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @张斌
 * @version 1.0
 */
public class RIAnalyzeTable {
    public CErrors mErrors = new CErrors();
    public RIAnalyzeTable() {
    }
    /**
     * 不管analyze table 执行如何都返回true
     * @param tableName String
     * @return boolean
     */
    public boolean analyzeTable(String tableName){
       ExeSQL exeSQL = new ExeSQL();
       String analyze = " ANALYZE TABLE "+tableName+" COMPUTE STATISTICS ";
       try{
           if (!exeSQL.execUpdateSQL(analyze)) {
               buildError("analyzeTable", "分析"+tableName+"表时出现错误!");
               return false;
           }
       }catch(Exception ex){
           buildError("analyzeTable", "分析"+tableName+"表时出现错误!");
           return false;
       }
        return true;
   }

   private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "RICalPro";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        System.out.print(szErrMsg);
    }
    public static void main(String[] args) {
        RIAnalyzeTable tRIAnalyzeTable = new RIAnalyzeTable();
        try{
            tRIAnalyzeTable.analyzeTable("RIRECORDTRACE");
        }catch(Exception ex){

        }
    }
}
