

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

public class GrQuotSharCESSTabBL
{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    /** 前台传入的公共变量 */
    private GlobalInput mGlobalInput = new GlobalInput();

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    private TransferData mTransferData = new TransferData();
    private String mRValidate="";
    private String mRInvalidate="";
    private String thisSerialNo = "";
    private String lastSerialNo = "";
    private String mContNo= "";
    private String mRIcomCode="";
    private String mReRiskCode="";
    private String mreprotType="";

    private String mTempType="";

    /** 数据操作字符串 */
    private String strOperate="";
    private MMap mMap = new MMap();
    private PubSubmit tPubSubmit = new PubSubmit();


    //业务处理相关变量
    /** 全局数据 */

    public GrQuotSharCESSTabBL()
    {
    }

    /**
    * 提交数据处理方法
    * @param cInputData 传入的数据,VData对象
    * @param cOperate 数据操作字符串
    * @return 布尔值（true--提交成功, false--提交失败）
    */
   public boolean submitData(VData cInputData, String cOperate) {
       System.out.println(" 团体保障保险成数分保合同.....");
       this.strOperate = cOperate;
       if (strOperate.equals("")) {
           buildError("verifyOperate", " GrQuotSharCESSTabBL->1 不支持的操作字符串");
           return false;
       }
       if (!getInputData(cInputData)) {
           System.out.println("－－获取数据失败－－－");
           return false;
       }
       // 准备所有要打印的数据
       if (!getPrintData()) {
           return false;
       }
       return true;
   }


   public static void main(String[] args) {
       GlobalInput globalInput = new GlobalInput();
       globalInput.ComCode = "8611";
       globalInput.Operator = "001";
       VData tVData = new VData();
       GrQuotSharCESSTabBL t = new GrQuotSharCESSTabBL();
     //  t.mStaTerm="200710";
    //   t.getLastSerialNo();
       System.out.println(" aa: "+t.lastSerialNo);
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

    private boolean getPrintData() {
        //新建一个TextTag的实例
      TextTag texttag = new TextTag();

      //统计的起讫时间
      ListTable multTable = new ListTable();
      multTable.setName("MULT");
      //以下表示在 报表中生成 26列，
      String[] Title = {"col1", "col2", "col3", "col4", "col5",
                       "col6", "col7", "col8", "col9", "col10",
                       "col11", "col12", "col13", "col14", "col15",
                       "col16", "col17", "col18", "col19", "col20",
                       "col21", "col22", "col23", "col24", "col25",
                       "col26", "col27", "col28", "col29", "col30",
                       "col31", "col32", "col33", "col34", "col35",
                       "col36", "col37", "col38", "col39", "col40",
                       "col41", "col42", "col43", "col44", "col45",
                       "col46", "col47", "col48", "col49", "col50",
                       "col51", "col52", "col53", "col54", "col55",
                       "col56", "col57", "col58", "col59", "col60",
                       "col61",
};
      try {
          SSRS tSSRS = new SSRS();
          String sql = "";

          sql = " select a.riskcode,"
              + " (select nvl(sum(r2.PremChang),0) "
              + " from RIPolRecordBake r1, RIrecordtrace r2 "
              + " where r1.eventno=r2.eventno and r1.riskcode=a.riskcode "
              + " and r1.EventType='01' and r1.cvalidate >= '"+mRValidate
              +"' and r1.enddate<='"+mRInvalidate+"' and IncomeCompanyNo='"+mRIcomCode+"'),"
              + " (select nvl(sum(r2.CommChang),0) "
              + " from RIPolRecordBake r1, RIrecordtrace r2 "
              + " where r1.eventno=r2.eventno and r1.riskcode=a.riskcode "
              + " and r1.EventType='01' and r1.cvalidate >= '"+mRValidate
              +"' and r1.enddate<='"+mRInvalidate+"' and IncomeCompanyNo='"+mRIcomCode+"'), "
              + " (select nvl(sum(r2.PremChang),0) "
              + " from RIPolRecordBake r1, RIrecordtrace r2 "
              + " where r1.eventno=r2.eventno and r1.riskcode=a.riskcode "
              + " and r1.EventType='03' and r1.cvalidate >= '"+mRValidate
              +"' and r1.enddate<='"+mRInvalidate+"' and IncomeCompanyNo='"+mRIcomCode+"'),"
              + " (select nvl(sum(r2.CommChang),0) "
              + " from RIPolRecordBake r1, RIrecordtrace r2 "
              + " where r1.eventno=r2.eventno and r1.riskcode=a.riskcode "
              + " and r1.EventType='03' and r1.cvalidate >= '"+mRValidate
              +"' and r1.enddate<='"+mRInvalidate+"' and IncomeCompanyNo='"+mRIcomCode+"'),"
              + " (select nvl(sum(r2.ReturnPay),0) "
              + " from RIPolRecordBake r1, RIrecordtrace r2 "
              + " where r1.eventno=r2.eventno and r1.riskcode=a.riskcode "
              + " and r1.EventType='04' and r1.cvalidate >= '"+mRValidate
              +"' and r1.enddate<='"+mRInvalidate+"' and IncomeCompanyNo='"+mRIcomCode+"'), "
              +"'',"
              +"'',"
              +"'',"
              +"'',"
              +"'',"
              +"'',"
              +"'',"
              +"'',"
              +"'',"
              +"'',"
              +"''"
              + " from lmriskapp a "
              + "where a.riskcode in (select r.associatedcode from RIAccumulateRDCode r) ";

          System.out.println(" SQL: " + sql);
          ExeSQL tExeSQL = new ExeSQL();
          tSSRS = tExeSQL.execSQL(sql);


          //查询结果的记录条数
          int count = tSSRS.getMaxRow();
          System.out.println("该sql执行后的记录条数为：" + count);
          //将查询结果存放到临时 二维数组中
          String temp[][] = tSSRS.getAllData();
          String[] strCol;
          for (int i = 0; i < count; i++) {
              strCol = new String[28];
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
              strCol[15] = temp[i][15];
              strCol[16] = temp[i][16];

              multTable.add(strCol);
          }

      } catch (Exception e) {
          CError tError = new CError();
          tError.moduleName = "BL";
          tError.functionName = "";
          tError.errorMessage = "再保合同1承保查询失败";
          this.mErrors.addOneError(tError);
      }
      XmlExport xmlexport = new XmlExport();
      xmlexport.createDocument("LRExportCess1.vts", "printer");

      SSRS tSSRS1 = new SSRS();
      String sql1 = "";

      sql1 = " select "
             + " (select nvl(sum(r2.PremChang),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno and r1.InsuYear>'1' "
             + " and r1.EventType='01' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'),"
             + " (select nvl(sum(r2.CommChang),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno and r1.InsuYear>'1' "
             + " and r1.EventType='01' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'), "
             + " (select nvl(sum(r2.PremChang),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno and r1.InsuYear>'1' "
             + " and r1.EventType='03' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'),"
             + " (select nvl(sum(r2.CommChang),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno and r1.InsuYear>'1' "
             + " and r1.EventType='03' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'),"
             + " (select nvl(sum(r2.ReturnPay),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno and r1.InsuYear>'1' "
             + " and r1.EventType='04' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'), "
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "''"
             + " from dual  ";

      ExeSQL tExeSQL1 = new ExeSQL();
      tSSRS1 = tExeSQL1.execSQL(sql1);
      texttag.add("PremChangL",tSSRS1.GetText(1,1));
      texttag.add("CommChangL",tSSRS1.GetText(1,2));
      texttag.add("PremChang1L",tSSRS1.GetText(1,3));
      texttag.add("CommChang1L",tSSRS1.GetText(1,4));
      texttag.add("ReturnPayL",tSSRS1.GetText(1,5));

      SSRS tSSRS2 = new SSRS();
      String sql2 = "";

      sql2 = " select "
             + " (select nvl(sum(r2.PremChang),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno and r1.InsuYear<='1' "
             + " and r1.EventType='01' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'),"
             + " (select nvl(sum(r2.CommChang),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno and r1.InsuYear<='1' "
             + " and r1.EventType='01' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'), "
             + " (select nvl(sum(r2.PremChang),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno and r1.InsuYear<='1' "
             + " and r1.EventType='03' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'),"
             + " (select nvl(sum(r2.CommChang),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno and r1.InsuYear<='1' "
             + " and r1.EventType='03' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'),"
             + " (select nvl(sum(r2.ReturnPay),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno and r1.InsuYear<='1' "
             + " and r1.EventType='04' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'), "
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "''"
             + " from dual  ";

      ExeSQL tExeSQL2 = new ExeSQL();
      tSSRS2 = tExeSQL2.execSQL(sql2);
      texttag.add("PremChangS", tSSRS2.GetText(1, 1));
      texttag.add("CommChangS", tSSRS2.GetText(1, 2));
      texttag.add("PremChang1S", tSSRS2.GetText(1, 3));
      texttag.add("CommChang1S", tSSRS2.GetText(1, 4));
      texttag.add("ReturnPayS", tSSRS2.GetText(1, 5));


      SSRS tSSRS3 = new SSRS();
      String sql3 = "";

      sql3 = " select "
             + " (select nvl(sum(r2.PremChang),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno "
             + " and r1.EventType='01' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'),"
             + " (select nvl(sum(r2.CommChang),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno "
             + " and r1.EventType='01' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'), "
             + " (select nvl(sum(r2.PremChang),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno "
             + " and r1.EventType='03' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'),"
             + " (select nvl(sum(r2.CommChang),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno "
             + " and r1.EventType='03' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'),"
             + " (select nvl(sum(r2.ReturnPay),0) "
             + " from RIPolRecordBake r1, RIrecordtrace r2 "
             + " where r1.eventno=r2.eventno "
             + " and r1.EventType='04' and r1.cvalidate >= '" + mRValidate
             + "' and r1.enddate<='" + mRInvalidate
             + "' and r2.IncomeCompanyNo='" + mRIcomCode + "'), "
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "'',"
             + "''"
             + " from dual  ";

      ExeSQL tExeSQL3 = new ExeSQL();
      tSSRS3 = tExeSQL3.execSQL(sql3);

      texttag.add("PremChang", tSSRS3.GetText(1, 1));
      texttag.add("CommChang", tSSRS3.GetText(1, 2));
      texttag.add("PremChang1", tSSRS3.GetText(1, 3));
      texttag.add("CommChang1", tSSRS3.GetText(1, 4));
      texttag.add("ReturnPay", tSSRS3.GetText(1, 5));

       SSRS cSSRS = new SSRS();
       ExeSQL cExeSQL = new ExeSQL();
       String tSQL = " select name from ldcom where comcode='"+mGlobalInput.ComCode+"' ";
       cSSRS = cExeSQL.execSQL(tSQL);
       String com=cSSRS.GetText(1,1);
       texttag.add("MakeOrganization",com);
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

         mRValidate = (String) mTransferData.getValueByName("RValidate");
         mRInvalidate = (String) mTransferData.getValueByName("RInvalidate");
         mContNo  = (String) mTransferData.getValueByName("ContNo");
         mRIcomCode = (String) mTransferData.getValueByName("RIcomCode");
         //mReRiskCode = (String) mTransferData.getValueByName("ReRiskCode");
         mreprotType = (String) mTransferData.getValueByName("reprotType");
         mTempType = (String) mTransferData.getValueByName("TempType");
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
}
