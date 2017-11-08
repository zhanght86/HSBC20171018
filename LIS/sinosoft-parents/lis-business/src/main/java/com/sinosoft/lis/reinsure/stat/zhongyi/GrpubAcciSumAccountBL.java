

/**
 * Copyright (c) 2006 sinosoft  Co. Ltd.
 * All right reserved.
 */

/*
 * <p>ClassName: OrderDescUI </p>
 * <p>Description: OrderDescUI类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2006-07-30
 */
package com.sinosoft.lis.reinsure.stat.zhongyi;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;

public class GrpubAcciSumAccountBL
{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    /** 前台传入的公共变量 */
    private GlobalInput mGlobalInput = new GlobalInput();

    /** 往后面传输数据的容器 */
	private VData mInputData = new VData();

    private TransferData mTransferData = new TransferData();
    private String mStartDate = "";       /**统计起期*/
    private String mEndDate = "";       /**统计止期*/
    private String thisSerialNo = "";   /**这个季度的月份*/
    
    private String lastSerialNo = "";   /**上个季度的月份*/
    private String thisSerialNoStart="";/**这个季度第一个月 */
    private String thisSerialNoEnd="";  /**这个季度最后一月 */
    private String lastSerialNoStart="";/**上个季度第一个月 */
    private String lastSerialNoEnd="";  /**上个季度最后一月 */
    /**前台传入的数据*/
    private String mContNo= "";    /**合同号码*/
    private String mAccountType="";/**帐单号码*/
    private String mRIcomCode="";  /**分保公司编号*/
    private String mTempType="";
    private double sumBorrow=0;
    private double sumLend=0;
    /** 数据操作字符串 */
    private String strOperate="";
    private MMap mMap = new MMap();
    private PubSubmit tPubSubmit = new PubSubmit();
    //业务处理相关变量
    /** 全局数据 */
    public GrpubAcciSumAccountBL()
    {
    }
    
    /**
    * 提交数据处理方法
    * @param cInputData 传入的数据,VData对象
    * @param cOperate 数据操作字符串
    * @return 布尔值（true--提交成功, false--提交失败）
    */
	public boolean submitData(VData cInputData, String cOperate) 
	{
		System.out.println(" 团体公共交通意外险..帐单...");
		this.strOperate = cOperate;
       
		if (strOperate.equals("")) 
		{
			buildError("verifyOperate", " GrQuotSharCESSTabBL->1 不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData)) 
		{
			System.out.println("－－获取数据失败－－－");
			return false;
		}
		// 准备所有要打印的数据
		if (!getPrintData()) 
		{
			return false;
		}
		return true;
	}

	/**
	* 从输入数据中得到所有对象
    * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
    */
	private boolean getInputData(VData cInputData)
	{
        //全局变量
        this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        mTransferData = ((TransferData) cInputData.getObjectByObjectName("TransferData", 0));

        if (mTransferData == null) {
            buildError("getInputData", "没有得到足够的信息！");
            return false;
        }
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		mContNo  = (String) mTransferData.getValueByName("ContNo");
		mRIcomCode = (String) mTransferData.getValueByName("RIcomCode");
		mAccountType=(String) mTransferData.getValueByName("AccountType");
		mTempType = (String) mTransferData.getValueByName("TempType");
        
        RIWFLogSet tRIWFLogSet = new RIWFLogDB().executeQuery("select * from RIWFLog where NodeState = '99' and StartDate>='"+mStartDate+"' and EndDate<='"+mEndDate+"'");
        
        if(tRIWFLogSet!=null && tRIWFLogSet.size()>0)
        {
        	thisSerialNo = "'"+tRIWFLogSet.get(1).getBatchNo()+"'";
        	
        	for(int i = 2;i<=tRIWFLogSet.size();i++)
        	{
        		thisSerialNo = thisSerialNo+",'"+tRIWFLogSet.get(i).getBatchNo()+"'";
        	}
        }
        else
        {
        	buildError("getInputData", "没有得到该日期内的分保数据！");
        	return false;
        } 
        return true;
	}

	private boolean getPrintData() 
	{
		System.out.println("come in getptintData");
		//新建一个TextTag的实例
		TextTag texttag = new TextTag();
		//统计的起讫时间
		ListTable multTable = new ListTable();
		multTable.setName("MULT");
		//以下表示在 报表中生成 15列，
		String[] Title = {"col1", "col2", "col3", "col4", "col5",
						"col6", "col7", "col8", "col9", "col10",
						"col11", "col12", "col13", "col14","col15"};
		try 
		{
			SSRS tSSRS = new SSRS();
			String sql1="";
		  if (mTempType.equals("2")) 
		  {
		      sql1 =" and c.ReinsreFlag in ('03') ";
		  } 
          else if (mTempType.equals("1")) 
          {
              sql1 = " and (c.ReinsreFlag not in ('03') or c.ReinsreFlag is null) ";
          }
          
          StringBuffer st = new StringBuffer("select X.A1,X.A2,X.A3,X.A4,X.A5,X.A6,X.A7,X.A8,X.A9,X.A10,X.A11,X.A12,A8-A12,X.A14,X.A15 from(");
          
          st.append(" select a.riskname A1,(select count(distinct c.GrpContNo) from ripolrecordbake c where c.riskcode=a.riskcode  and c.eventtype in ('01','03') and c.batchno in ("+thisSerialNo+") "+ sql1 +") A2,");
          st.append(" (select count(distinct c.insuredno) from ripolrecordbake c where c.riskcode=a.riskcode  and c.eventtype in ('01','03') and c.batchno in ("+thisSerialNo+") "+ sql1 +") A3,");
          st.append(" (select nvl(sum(c.Amnt),0) from ripolrecordbake c where c.riskcode = a.riskcode and c.eventtype in ('01','03') and c.batchno in ("+thisSerialNo+")  "+ sql1 +" ) A4,");
          st.append(" (select nvl(sum(c.Amnt),0) from ripolrecordbake c where c.riskcode = a.riskcode and c.eventtype in ('01','03') and c.batchno in ("+thisSerialNo+") "+ sql1 +" ) A5,");
          st.append(" (select nvl(sum(c.Prem),0) from ripolrecordbake c where c.riskcode = a.riskcode and c.eventtype in ('01','03') and c.batchno in ("+thisSerialNo+") "+ sql1 +" ) A6,");
          st.append(" (select nvl(sum(d.CessionAmount),0) from ripolrecordbake c,rirecordtrace d where c.eventno=d.eventno and c.batchno=d.batchno and c.batchno in("+thisSerialNo+") and c.riskcode = a.riskcode and c.eventtype in ('01','03')  and d.incomecompanyno='"+mRIcomCode+"'  "+ sql1 +") A7,");
          st.append(" (select nvl(sum(d.PremChang),0) from ripolrecordbake c,rirecordtrace d where c.eventno=d.eventno and c.batchno=d.batchno and c.batchno in ("+thisSerialNo+") and c.riskcode = a.riskcode and c.eventtype in ('01','03') and d.incomecompanyno='"+mRIcomCode+"' "+ sql1 +") A8,");
          st.append(" (select nvl(sum(d.commchang),0) from ripolrecordbake c,rirecordtrace d where c.eventno=d.eventno and c.batchno=d.batchno and c.batchno in ("+thisSerialNo+")and c.riskcode = a.riskcode and c.eventtype in ('01','03') and d.incomecompanyno='"+mRIcomCode+"' "+ sql1 +" ) A9,");
          st.append(" (select count(distinct c.clmno) from ripolrecordbake c where c.batchno in ("+thisSerialNo+") and c.eventtype='04' and c.riskcode = a.riskcode "+ sql1 +") A10,");
          st.append(" (select nvl(sum(c.ClmGetMoney),0) from ripolrecordbake c where c.batchno in ("+thisSerialNo+") and c.eventtype='04' and c.riskcode = a.riskcode "+ sql1 +") A11,");
          st.append(" (select nvl(sum(d.ReturnPay),0) from ripolrecordbake c,rirecordtrace d where c.eventno=d.eventno and c.batchno=d.batchno and c.batchno in ("+thisSerialNo+") and c.riskcode = a.riskcode  and c.eventtype='04'  and d.incomecompanyno='"+mRIcomCode+"'  "+ sql1 +") A12,");
          st.append(" '' A14, '' A15 from lmrisk a where a.riskcode in (select distinct associatedcode from RIAccumulateRDCode  where  accumulatedefno in (select accumulatedefno from riprecept where ricontno='"+mContNo+"')) ");
          
          st.append(" union all ");
          
          st.append(" select '合计' A1,(select count(distinct c.GrpContNo) from ripolrecordbake c where c.riskcode in (select distinct associatedcode from RIAccumulateRDCode  where accumulatedefno in (select accumulatedefno from riprecept where ricontno='"+mContNo+"')) and c.eventtype in ('01','03') and c.batchno in ("+thisSerialNo+")"+ sql1 +") A2,");
          st.append(" (select count(distinct c.insuredno) from ripolrecordbake c where c.riskcode in (select distinct associatedcode from RIAccumulateRDCode  where accumulatedefno in (select accumulatedefno from riprecept where ricontno='"+mContNo+"')) and c.eventtype in ('01','03') and c.batchno in ("+thisSerialNo+")"+ sql1 +") A3,");
          st.append(" (select nvl(sum(c.Amnt),0) from ripolrecordbake c where c.riskcode in (select distinct associatedcode from RIAccumulateRDCode  where accumulatedefno in (select accumulatedefno from riprecept where ricontno='"+mContNo+"')) and c.eventtype in ('01','03') and c.batchno in ("+thisSerialNo+") "+ sql1 +") A4,");
          st.append(" (select nvl(sum(c.Amnt),0) from ripolrecordbake c where c.riskcode in (select distinct associatedcode from RIAccumulateRDCode  where accumulatedefno in (select accumulatedefno from riprecept where ricontno='"+mContNo+"')) and c.eventtype in ('01','03') and c.batchno in ("+thisSerialNo+") "+ sql1 +") A5,");
          st.append(" (select nvl(sum(c.Prem),0) from ripolrecordbake c where c.riskcode in (select distinct associatedcode from RIAccumulateRDCode  where accumulatedefno in (select accumulatedefno from riprecept where ricontno='"+mContNo+"'))  and c.eventtype in ('01','03') and c.batchno in ("+thisSerialNo+")"+ sql1 +") A6,");
          st.append(" (select nvl(sum(d.CessionAmount),0) from ripolrecordbake c,rirecordtrace d where c.eventno=d.eventno and c.batchno=d.batchno and c.batchno in("+thisSerialNo+") and c.eventtype in ('01','03') and c.riskcode in (select distinct associatedcode from RIAccumulateRDCode  where accumulatedefno in (select accumulatedefno from riprecept where ricontno='"+mContNo+"')) and d.incomecompanyno='"+mRIcomCode+"' "+ sql1 +") A7,");
          st.append(" (select nvl(sum(d.PremChang),0) from ripolrecordbake c,rirecordtrace d where c.eventno=d.eventno and c.batchno=d.batchno and c.batchno in ("+thisSerialNo+") and c.eventtype in ('01','03')    and c.riskcode in (select distinct associatedcode from RIAccumulateRDCode  where accumulatedefno in (select accumulatedefno from riprecept where ricontno='"+mContNo+"')) and d.incomecompanyno='"+mRIcomCode+"'"+ sql1 +") A8,");
          st.append(" (select nvl(sum(d.commchang),0) from ripolrecordbake c,rirecordtrace d where c.eventno=d.eventno and c.batchno=d.batchno and c.batchno in ("+thisSerialNo+") and c.eventtype in ('01','03')    and c.riskcode in (select distinct associatedcode from RIAccumulateRDCode  where accumulatedefno in (select accumulatedefno from riprecept where ricontno='"+mContNo+"')) and d.incomecompanyno='"+mRIcomCode+"' "+ sql1 +") A9,");
          st.append(" (select count(distinct c.clmno) from ripolrecordbake c where c.batchno in ("+thisSerialNo+") and c.eventtype='04'   and c.riskcode in (select distinct associatedcode from RIAccumulateRDCode  where accumulatedefno in (select accumulatedefno from riprecept where ricontno='"+mContNo+"')) "+ sql1 +") A10,");
          st.append(" (select nvl(sum(c.ClmGetMoney),0) from ripolrecordbake c where c.batchno in ("+thisSerialNo+") and c.eventtype='04' and c.riskcode in (select distinct associatedcode from RIAccumulateRDCode  where accumulatedefno in (select accumulatedefno from riprecept where ricontno='"+mContNo+"')) "+ sql1 +") A11,");
          st.append(" (select nvl(sum(d.ReturnPay),0) from ripolrecordbake c,rirecordtrace d where c.eventno=d.eventno and c.batchno=d.batchno and c.batchno in ("+thisSerialNo+") and c.riskcode in (select distinct associatedcode from RIAccumulateRDCode  where accumulatedefno in (select accumulatedefno from riprecept where ricontno='"+mContNo+"')) and c.eventtype='04'  and d.incomecompanyno='"+mRIcomCode+"' "+ sql1 +") A12,'' A14, '' A15 from dual) X ");
          
          System.out.println(" SQL: " + st.toString());
          
          ExeSQL tExeSQL = new ExeSQL();
          tSSRS = tExeSQL.execSQL(st.toString());
          //查询结果的记录条数
          int count = tSSRS.getMaxRow();
          System.out.println("该sql执行后的记录条数为：" + count);
          //将查询结果存放到临时 二维数组中
          String temp[][] = tSSRS.getAllData();
          String[] strCol;
          for (int i = 0; i < count; i++) 
          {
              strCol = new String[15];
              strCol[0] = temp[i][0];
              strCol[1] = temp[i][1];
              strCol[2] = temp[i][2];
              strCol[3] = temp[i][3];
              strCol[4] = temp[i][4];
              strCol[5] = temp[i][5];
              strCol[6] = temp[i][6];
              strCol[7] = temp[i][7];
              strCol[8] = temp[i][8];
              strCol[9] = temp[i][9];
              strCol[10] = temp[i][10];
              strCol[11] = temp[i][11];
              strCol[12] = temp[i][12];
              strCol[13] = temp[i][13];
              strCol[14] = temp[i][14];
              multTable.add(strCol);
          }

      } catch (Exception e) {
          CError tError = new CError();
          tError.moduleName = "BL";
          tError.functionName = "";
          tError.errorMessage = "再保合同1帐单查询失败";
          this.mErrors.addOneError(tError);
      }
      XmlExport xmlexport = new XmlExport();
      xmlexport.createDocument("GrpubAcciSumAccount.vts", "printer");
      texttag.add("RIcomCode",mRIcomCode);
      texttag.add("MakeOrganization",mGlobalInput.ComCode);
      texttag.add("Operator",mGlobalInput.Operator);
      String currentdate=PubFun.getCurrentDate();
      texttag.add("MakeDate",currentdate);
      if (texttag.size() > 0) {
          xmlexport.addTextTag(texttag);
      }
      //保存信息
      xmlexport.addListTable(multTable, Title);
      mResult.clear();
      mResult.addElement(xmlexport);

      return true;
	}


    public VData getResult() {
        return mResult;
    }

    /*
     * add by kevin, 2002-10-14
     */
    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();

        cError.moduleName = "ReComManageBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

     /**
      * 准备往后层输出所需要的数据
      * 输出：如果准备数据时发生错误则返回false,否则返回true
      */
     private boolean prepareOutputData()
     {
         try
         {
             this.mInputData.clear();
             this.mInputData.add(mMap);
         }
         catch (Exception ex)
         {
             // @@错误处理
             CError tError = new CError();
             tError.moduleName = "GrQuotSharCESSTabBL";
             tError.functionName = "prepareData";
             tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
             this.mErrors.addOneError(tError);
             return false;
         }
         return true;
     }
     public static void main(String[] args) 
     {
         GlobalInput globalInput = new GlobalInput();
         globalInput.ComCode = "8611";
         globalInput.Operator = "001";
         VData tVData = new VData();
         GrQuotSharAccountBL t = new GrQuotSharAccountBL();
//         t.getPrintData();
     }   
}
