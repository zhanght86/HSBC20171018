

package com.sinosoft.lis.reinsure.calculate.feerate;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CError;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RIFeeTableSet {
    public  CErrors mErrors = new CErrors();
    private RIFeeTable mRIFeeTable ;
    private String mAccumulateDefNo;

    public RIFeeTableSet(String accumulateDefNo) throws Exception
    {
        try{
            this.mAccumulateDefNo = accumulateDefNo;
            init();
        }catch(Exception ex){
            throw ex;
        }
    }

    public boolean init() throws Exception{
        mRIFeeTable = new RIFeeTable(mAccumulateDefNo);
        return true;
    }

    /**
     * 得到查询费率sql
     * @param tFeeTableNo String
     * @param tBatchNo String
     * @return String
     * @throws Exception
     */
    public String getRateSql(String pFeeTableNo,String pBatchNo) throws Exception{
        try{
            String rateSql = mRIFeeTable.getRateSql(pFeeTableNo,pBatchNo);
            if(rateSql==null){
                this.mErrors.copyAllErrors(mRIFeeTable.mErrors);
                return null;
            }
            return rateSql;
        }
        catch (Exception ex)
        {
            buildError("getRateSql","编号为" + pFeeTableNo + "的费率表，得到查询SQL语句失败，原因为:"+mErrors.getFirstError());
            return null;
        }
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = "FeeTable";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        System.out.print(szErrMsg);
    }

    public static void main(String[] args) {
        try
        {
            RIFeeTableSet tFeeTableSet = new RIFeeTableSet("L001000001");
            System.out.println(tFeeTableSet.getRateSql("F000004","20080101"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
