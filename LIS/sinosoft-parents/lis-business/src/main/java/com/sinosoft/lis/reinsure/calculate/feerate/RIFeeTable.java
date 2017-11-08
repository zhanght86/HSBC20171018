

package com.sinosoft.lis.reinsure.calculate.feerate;

import com.sinosoft.lis.vschema.RIFeeRateTableClumnDefSet;
import com.sinosoft.lis.schema.RIFeeRateTableDefSchema;
import com.sinosoft.lis.vschema.RIFeeRateTableTraceSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.schema.RIFeeRateTableClumnDefSchema;
import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.lis.vschema.RIFeeRateTableDefSet;
import com.sinosoft.lis.schema.RIFeeRateTableTraceSchema;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class RIFeeTable {

    public  CErrors mErrors = new CErrors();
    private RIFeeRateTableDefSchema mRIFeeRateTableDefSchema;
    private RIFeeRateTableClumnDefSet mRIFeeRateTableClumnDefSet;
    private RIFeeRateTableTraceSchema mRIFeeRateTableTraceSchema;
    private String mSqlHead = "select FeeRate from ";
    private String mSqlTail = " ";
    private RIInitData mRIInitData ;

    public RIFeeTable(String accumulateDefNo) throws Exception
    {
        mRIInitData = RIInitData.getObject(accumulateDefNo);
    }

    public boolean InitInfo(String pFeeTableNo,String pBatchNo)
    {
        try
        {
            RIFeeRateTableDefSet tRIFeeRateTableDefSet = mRIInitData.getRIFeeRateTableDefSet();
            for(int i=1;i<=tRIFeeRateTableDefSet.size();i++){
                if(tRIFeeRateTableDefSet.get(i).getFeeTableNo().equals(pFeeTableNo)){
                    mRIFeeRateTableDefSchema = tRIFeeRateTableDefSet.get(i);
                }
            }
            if(mRIFeeRateTableDefSchema==null){
                buildError("InitInfo","编号为" + pFeeTableNo + "的费率表处理类初始化失败,原因为:没有得到费率表定义!");
                return false;
            }
            mRIFeeRateTableClumnDefSet = new RIFeeRateTableClumnDefSet();
            RIFeeRateTableClumnDefSet tRIFeeRateTableClumnDefSet = mRIInitData.getRIFeeRateTableClumnDefSet();
            for(int i=1;i<=tRIFeeRateTableClumnDefSet.size();i++){
                if(tRIFeeRateTableClumnDefSet.get(i).getFeeTableNo().equals(pFeeTableNo)){
                    mRIFeeRateTableClumnDefSet.add(tRIFeeRateTableClumnDefSet.get(i));
                }
            }
            if(mRIFeeRateTableClumnDefSet==null||mRIFeeRateTableClumnDefSet.size()==0){
                buildError("InitInfo","编号为" + pFeeTableNo + "的费率表处理类初始化失败,原因为:没有得到费率表字段定义!");
                return false;
            }
            RIFeeRateTableTraceSet tRIFeeRateTableTraceSet = mRIInitData.getRIFeeRateTableTraceSet();
            for(int i=1;i<=tRIFeeRateTableTraceSet.size();i++){
                if(tRIFeeRateTableTraceSet.get(i).getFeeTableNo().equals(pFeeTableNo)&&tRIFeeRateTableTraceSet.get(i).getBatchNo().equals(pBatchNo)){
                    mRIFeeRateTableTraceSchema = tRIFeeRateTableTraceSet.get(i);
                }
            }
            if(mRIFeeRateTableTraceSchema==null){
                buildError("InitInfo","编号为" + pFeeTableNo + "的费率表处理类初始化失败,原因为:没有得到费率表轨迹表!");
                return false;
            }
            mSqlTail=" ";
            for (int i = 1; i <= mRIFeeRateTableClumnDefSet.size(); i++)
            {
                RIFeeRateTableClumnDefSchema tRIFeeRateTableClumnDefSchema= mRIFeeRateTableClumnDefSet.get(i);
                if(tRIFeeRateTableClumnDefSchema.getFeeClumnType().trim().equals("A01"))
                {
                    mSqlTail = mSqlTail + tRIFeeRateTableClumnDefSchema.getTagetClumn() +" = (select "+tRIFeeRateTableClumnDefSchema.getFeeClumnDataCode()+" from RIPolRecord b where b.EventNo=a.EventNo)";
                }
                else if(tRIFeeRateTableClumnDefSchema.getFeeClumnType().trim().equals("A02"))
                {
                    mSqlTail = mSqlTail + " (select "+tRIFeeRateTableClumnDefSchema.getFeeClumnDataCode()+" from RIPolRecord b where b.EventNo=a.EventNo) between "+tRIFeeRateTableClumnDefSchema.getTagetDLClumn()+" and "+ tRIFeeRateTableClumnDefSchema.getTagetULClumn();
                }
                else if(tRIFeeRateTableClumnDefSchema.getFeeClumnType().trim().equals("N01"))
                {
                    mSqlTail = mSqlTail + tRIFeeRateTableClumnDefSchema.getTagetClumn() +" = (select "+tRIFeeRateTableClumnDefSchema.getFeeClumnDataCode()+" from RIPolRecord b where b.EventNo=a.EventNo)";
                }
                else if(tRIFeeRateTableClumnDefSchema.getFeeClumnType().trim().equals("N02"))
                {
                    mSqlTail = mSqlTail + " (select "+tRIFeeRateTableClumnDefSchema.getFeeClumnDataCode()+" from RIPolRecord b where b.EventNo=a.EventNo) between "+tRIFeeRateTableClumnDefSchema.getTagetDLClumn()+" and "+ tRIFeeRateTableClumnDefSchema.getTagetULClumn();
                }
                else
                {
                    Exception e = new Exception("编号为" + pFeeTableNo + "的费率表" + tRIFeeRateTableClumnDefSchema.getFeeClumnDataCode() + "表字段定义未知类型" + tRIFeeRateTableClumnDefSchema.getFeeClumnType());
                    throw e;
                }
                if(i!=mRIFeeRateTableClumnDefSet.size())
                {
                    mSqlTail = mSqlTail + " and ";
                }
            }
            return true;
        }
        catch (Exception ex)
        {
            buildError("InitInfo","编号为" + pFeeTableNo + "的费率表处理类初始化失败," + "原因为:" + ex.getMessage() + "!");
            return false;
        }
    }

    public String getRateSql(String pFeeTableNo,String pBatchNo) throws Exception
    {
       try
       {
           if(!InitInfo(pFeeTableNo,pBatchNo)){
               buildError("getRateSql","编号为" + pFeeTableNo + "的费率表，得到查询SQL语句失败，原因为:"+mErrors.getFirstError());
               return null;
           }
           String tSql = "";
           if(this.mRIFeeRateTableDefSchema.getFeeTableType().equals("01"))
           {
               String tDateSaveTable = this.getFeeTableNameByDate();
               tSql = this.mSqlHead + tDateSaveTable + this.mSqlTail;
           }
           else
           {
               Exception e = new Exception("编号为" + mRIFeeRateTableDefSchema.getFeeTableNo() + "的费率表义未知类型" + mRIFeeRateTableDefSchema.getFeeTableType());
               throw e;
           }
           return tSql;
       }
       catch (Exception ex)
       {
           throw ex;
       }
    }

    //得到相应的费率表表名
    private String getFeeTableNameByDate() throws Exception
    {
      try
      {
          return mRIFeeRateTableTraceSchema.getSaveDataName() + " where FeeTableNo = '" + this.mRIFeeRateTableDefSchema.getFeeTableNo() + "' and BatchNo = '" +  mRIFeeRateTableTraceSchema.getBatchNo() + "' and ";
      }
      catch (Exception ex)
      {
          throw ex;
      }
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = "RIFeeTable";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        System.out.print(szErrMsg);
    }

    public static void main(String[] args)
    {
        try {
            RIFeeTable tFeeTable = new RIFeeTable("L001000001");
            System.out.println("aa: "+tFeeTable.getRateSql("F000003","20080101"));
        } catch (Exception ex) {

        }
    }
}
