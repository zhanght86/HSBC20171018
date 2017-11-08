

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

import com.sinosoft.lis.db.RIWFLogDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.RIWFLogSet;
import com.sinosoft.utility.*;

public class GrQuotSharAccountBL
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
    private String thisSerialNo = "''";   /**这个季度的月份*/
    private String lastSerialNoEnd="''";  /**上个季度最后一月 */
    
    private String lastSerialNo = "";   /**上个季度的月份*/
    private String thisSerialNoStart="";/**这个季度第一个月 */
    private String thisSerialNoEnd="";  /**这个季度最后一月 */
    private String lastSerialNoStart="";/**上个季度第一个月 */
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
    public GrQuotSharAccountBL()
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

        if (mTransferData == null) 
        {
            buildError("getInputData", "没有得到足够的信息！");
            return false;
        }
        mStartDate = (String) mTransferData.getValueByName("StartDate");
        mEndDate = (String) mTransferData.getValueByName("EndDate");
        mContNo  = (String) mTransferData.getValueByName("ContNo");
        mRIcomCode = (String) mTransferData.getValueByName("RIcomCode");
        mAccountType=(String) mTransferData.getValueByName("AccountType");
        mTempType = (String) mTransferData.getValueByName("TempType");
        
        String logSQL = "select * from RIWFLog where NodeState = '99' and StartDate>='"+mStartDate+"' and EndDate<='"+mEndDate+"'";
        RIWFLogSet tRIWFLogSet = new RIWFLogDB().executeQuery(logSQL);
        
        System.out.println("SQL="+logSQL);
        
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
        System.out.println("thisSerialNo="+thisSerialNo);
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
		//以下表示在 报表中生成 3列，
        String[] Title = {"col1", "col2", "col3"};
        try 
        {
		  SSRS tSSRS = new SSRS();
		  String sql1="";
		  if (mTempType.equals("2")) 
		  {
		      sql1 = " and a.ReinsreFlag ='03' ";
		  } 
		  else if (mTempType.equals("1")) 
		  {
		      sql1 = " and (a.ReinsreFlag not in ('03') or a.ReinsreFlag is null) ";
		  }
          String sql = " select 'Opening Reserve(期初准备金)', nvl(sum(b.StandbyDouble3),0),0 from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype in('01','03') and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000001')) and b.incomecompanyno = '"+mRIcomCode+"' and a.batchno in ("+thisSerialNo+") "+sql1
                       +" union all select 'Reinsurance Premium(再保保费)',nvl(sum(b.PremChang),0),0 from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype in('01','03') and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000001')) and b.incomecompanyno = '"+mRIcomCode+"' and a.batchno in ("+thisSerialNo+") "+sql1
                       +" union all select 'Interest on Reserves（准备金利息）',(nvl(sum(b.StandbyDouble5),0))*3/12,0 from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype in('01','03') and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000001')) and b.incomecompanyno = '"+mRIcomCode+"' and a.batchno in ("+thisSerialNo+") "+sql1
                       +" union all select 'Commissions incurred by the Company（返还给经纪中介的佣金和摊回税收）',0,nvl(sum(b.StandbyDouble1),0)+nvl(sum(0),0) from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype in('01','03') and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000001')) and b.incomecompanyno = '"+mRIcomCode+"' and a.batchno in ("+thisSerialNo+") "+sql1
                       +" union all select 'Reinsurance commissions（摊回佣金）' ,0,nvl(sum(b.CommChang),0) from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype in('01','03') and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000001')) and b.incomecompanyno = '"+mRIcomCode+"' and a.batchno in ("+thisSerialNo+")  "+sql1
                       +" union all select 'Claims incurred（理赔摊回）' ,0,nvl(sum(b.ReturnPay),0) from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype='04' and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000001')) and b.incomecompanyno = '"+mRIcomCode+"' and a.batchno in ("+thisSerialNo+")   "+sql1
                       +" union all select 'Policyholder''s profit sharing incurred by the Company',0,0 from ldsysvar where sysvar='onerow'  "
                       +" union all select 'Closing reserves（期末准备金）' ,0,nvl(sum(b.StandbyDouble3),0) from ripolrecordbake a, RIRecordTrace b where a.eventno=b.eventno and a.batchno = b.batchno and a.eventtype in('01','03') and a.riskcode in (select distinct associatedcode from RIAccumulateRDCode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000001')) and b.incomecompanyno = '"+mRIcomCode+"' and a.batchno in( "+thisSerialNo+") "+sql1
                       ;

          System.out.println(" SQL: " + sql);
          ExeSQL tExeSQL = new ExeSQL();
          tSSRS = tExeSQL.execSQL(sql);
          //查询结果的记录条数
          int count = tSSRS.getMaxRow();
          System.out.println("该sql执行后的记录条数为：" + count);
          //将查询结果存放到临时 二维数组中
          String temp[][] = tSSRS.getAllData();
          String[] strCol;
          for (int i = 0; i < count; i++) 
          {
              strCol = new String[3];
              strCol[0] = temp[i][0];
              strCol[1] = temp[i][1];
              strCol[2] = temp[i][2];
              multTable.add(strCol);
          }
          System.out.println("计算借贷之差");
          for (int i = 0; i < count; i++) 
          {
              if(temp[i][1]!=null && !temp[i][1].equals(""))
                    sumBorrow = sumBorrow + Double.parseDouble(temp[i][1]);
              if(temp[i][2]!=null && !temp[i][2].equals(""))
                    sumLend = sumLend + Double.parseDouble(temp[i][2]);
         }
         System.out.println("sumBorrow-sumLend="+(sumBorrow-sumLend));
         
      } 
      catch (Exception e) 
      {
          CError tError = new CError();
          tError.moduleName = "BL";
          tError.functionName = "";
          tError.errorMessage = "再保合同1帐单查询失败";
          this.mErrors.addOneError(tError);
      }
      	XmlExport xmlexport = new XmlExport();
      	xmlexport.createDocument("GrQuotSharAccount.vts", "printer");

		double balance=sumBorrow-sumLend;
		double result = Math.round(balance*100)/100.00; //保留两位小数

		System.out.println(" 分保余额： "+result);
     	if(balance>=0)
     		texttag.add("positive",result);
		else
		{
	    	 balance=sumLend-sumBorrow;
	         texttag.add("negative",result);
		}
		  texttag.add("RIcomCode",mRIcomCode);
		  texttag.add("MakeOrganization",mGlobalInput.ComCode);
		  texttag.add("Operator",mGlobalInput.Operator);
		  String currentdate=PubFun.getCurrentDate();
		  texttag.add("MakeDate",currentdate);
		  if (texttag.size() > 0) 
		  {
		      xmlexport.addTextTag(texttag);
		  }
      //保存信息
      xmlexport.addListTable(multTable, Title);
      mResult.clear();
      mResult.addElement(xmlexport);

      return true;
    }


    public VData getResult() 
    {
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
        t.getPrintData();
    }
    
}
