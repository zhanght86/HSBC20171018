

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

public class GrpLifeExCESSTabBL
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
    private String mContNo= "";
    private String mRIcomCode="";
    private String mReRiskCode="";
    private String mTempType="";
    /** 数据操作字符串 */
    private String strOperate="";

    private MMap mMap = new MMap();
    private PubSubmit tPubSubmit = new PubSubmit();
    /**业务处理相关变量
    /** 全局数据 */
    public GrpLifeExCESSTabBL()
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
     this.strOperate = cOperate;
     if (strOperate.equals(""))
     {
         buildError("verifyOperate", "不支持的操作字符串");
         return false;
     }
     if (!getInputData(cInputData))
     {
         return false;
     }
     /** 准备所有要打印的数据*/
     if (!getPrintData()) {
         return false;
     }
     return true;
 }

 public static void main(String[] args)
 {
     GlobalInput globalInput = new GlobalInput();
     globalInput.ComCode = "8611";
     globalInput.Operator = "001";

     /** prepare main plan
      * 准备传输数据 VData*/
     VData vData = new VData();
     GrpLifeExCESSTabBL t = new GrpLifeExCESSTabBL();
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
         CError tError = new CError();
         tError.moduleName = "LDComBL";
         tError.functionName = "prepareData";
         tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
         this.mErrors.addOneError(tError);
         return false;
     }
     return true;
 }

 private boolean getPrintData() {
     TextTag texttag = new TextTag();

     /**统计的起讫时间*/
     ListTable multTable = new ListTable();
     multTable.setName("MULT");
     /**以下表示在 报表中生成 26列，*/
     String[] Title = {"col1", "col2", "col3", "col4", "col5", "col6", "col7", "col8", "col9", "col10",
                      "col11","col12","col13","col14","col15","col16","col17","col18","col19","col20",
                      "col21","col22","col23","col24","col25","col26"};
     try {
         SSRS tSSRS = new SSRS();
         String sql = "";
         sql=" select X.A26,X.A0,X.A1,X.A2,X.A3,X.A4,X.A5,X.A6,X.A7,X.A8,X.A9,X.A10,X.A11,X.A12,X.A13,X.A14,X.A15,X.A16,X.A17,X.A18,(X.A18-X.A19),X.A20,X.A21,X.A22,X.A23,X.A24 "
             + " from (select a.GrpContno A0,(select GrpName from lcgrpcont where grpcontno=a.grpcontno) A1,max(a.StandbyDouble2) A2, max(a.InsuredName) A3,a.InsuredNo A4,max(a.InsuredIDNo) A5,(case max(a.InsuredSex) when '0' then '男' else '女' end) A6, max(a.InsuredBirthday) A7,(select max(c.InsuredAppAge) from lcpol c where c.insuredNo=a.insuredno and c.riskcode=a.riskcode) A8,max(a.InsuredAge) A9,max(a.OccupationType) A10,max(a.DeathRate) A11, max(a.CValidate) A12, (case trim(to_char(max(InsuYear)))||max(InsuYearFlag) when '1000A' then '终身' when '70A' then '至70岁' else (trim(to_char(max(InsuYear)))||case max(InsuYearFlag) when 'Y' then '年' when 'M' then '月' when 'D' then '天' when 'A' then '岁' else '年' end) end ) A13, (select riskname from lmrisk where riskcode=a.RiskCode) A14,a.riskcode A15, (case when a.riskcode='NMK01' then 'PA' when a.riskcode='MMK01' then 'DI' when a.riskcode='NAK01' then 'TERM' when a.riskcode='MGK01' or a.riskcode='MGK02' then 'CI' end) A16,(select nvl(sum(c.Amnt),0.0) from ripolrecordbake c where c.SerialNo=a.SerialNo and c.insuredno=a.insuredno and c.riskcode=a.riskcode and c.eventtype='01' and c.standbystring1='2' and c.grpcontno=a.grpcontno) A17,(select nvl(sum(c.Amnt),0.0) from ripolrecordbake c where c.SerialNo=a.SerialNo and c.insuredno=a.insuredno and c.riskcode=a.riskcode and c.eventtype='01' and c.standbystring1='2' and c.grpcontno=a.grpcontno) A18,  (select nvl(sum(d.CessionAmount),0.0) from ripolrecordbake c,reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=a.SerialNo and c.insuredno=a.insuredno and c.riskcode=a.riskcode and c.eventtype='01' and c.standbystring1='2' and c.grpcontno=a.grpcontno) A19, nvl(sum(b.CessionAmount),0.0) A20,max(b.StandbyDoubleE) A21,nvl(sum(b.PremChang),0) A22, nvl(sum(b.ReturnComm),0) A23, (case when max(a.ReinsreFlag)='0' or max(a.ReinsreFlag) is null then '自动分保' else '临分' end) A24,(select  ComPanyName from ReInsuranceComPanyInfo  where companyno=b.companyno) A26 from RIPolRecordBake a, reinsurerecordtrace b where a.eventno=b.eventno and a.SerialNo=b.SerialNo  and a.eventtype='01' and a.dutycode in (select distinct associatedcode from accumulaterdcode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000004')) and  a.standbyString1='2'  and a.cvalidate >= '"+mRValidate+"' and a.enddate<='"+mRInvalidate+"' "
             ;
         if (mReRiskCode != null && !mReRiskCode.equals(""))
         {
             sql = sql + " and a.RiskCode='" + mReRiskCode + "' ";
         }
         if (mRIcomCode != null && !mRIcomCode.equals("")) {
             sql = sql + " and b.ComPanyNo = '" + mRIcomCode + "' ";
         }
         if (mTempType.equals("2")) {
             sql = sql + " and a.ReinsreFlag in ('00','03') ";
         } else if (mTempType.equals("1")) {
             sql = sql + " and (a.ReinsreFlag not in ('00','03') or a.ReinsreFlag is null) ";
         }
         sql=sql + " group by a.SerialNo,b.companyno,a.GrpContno,a.InsuredNo,a.RiskCode order by b.companyno,a.GrpContno,a.InsuredNo,a.RiskCode) X where rownum <=1000 " ;

         System.out.println("zb: SQL: "+sql);
         ExeSQL tExeSQL = new ExeSQL();
         tSSRS = tExeSQL.execSQL(sql);
         /**查询结果的记录条数*/
         int count = tSSRS.getMaxRow();
         System.out.println("该sql执行后的记录条数为："+count);

         /**将查询结果存放到临时 二维数组中*/
         String temp[][] = tSSRS.getAllData();
         String[] strCol;
         for (int i = 0; i < count; i++) {
             strCol = new String[26];
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
             strCol[17] = temp[i][17];
             strCol[18] = temp[i][18];
             strCol[19] = temp[i][19];
             strCol[20] = temp[i][20];
             strCol[21] = temp[i][21];
             strCol[22] = temp[i][22];
             strCol[23] = temp[i][23];
             strCol[24] = temp[i][24];
             strCol[25] = temp[i][25];
             multTable.add(strCol);
         }
     } catch (Exception e) {
         CError tError = new CError();
         tError.moduleName = "OrderCollectBL";
         tError.functionName = "";
         tError.errorMessage = "客户基本信息查询失败";
         this.mErrors.addOneError(tError);
     }
     /**LRExportCess4.vts/**新建一个XmlExport的实例*/
     XmlExport xmlexport = new XmlExport();
     /**最好紧接着就初始化xml文档*/
     xmlexport.createDocument("LRExportCess4.vts", "printer");
     SSRS cSSRS = new SSRS();
     ExeSQL cExeSQL = new ExeSQL();
     String tSQL = " select name from ldcom where comcode='"+mGlobalInput.ComCode+"' ";
     cSSRS = cExeSQL.execSQL(tSQL);
     String com=cSSRS.GetText(1,1);
     texttag.add("MakeOrganization",com);
     texttag.add("Operator", mGlobalInput.Operator);
     String currentdate = PubFun.getCurrentDate();
     texttag.add("MakeDate", currentdate);
     if (texttag.size() > 0) {
         xmlexport.addTextTag(texttag);
     }
     /**保存信息*/
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
     /**全局变量*/
     this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
     mTransferData = ((TransferData) cInputData.getObjectByObjectName("TransferData", 0));/**TransferData不是表，这和LRContManageBl.java是不同的*/

     if (mTransferData == null) {
         buildError("getInputData", "没有得到足够的信息！");
         return false;
     }
     mRValidate = (String) mTransferData.getValueByName("RValidate");
     mRInvalidate = (String) mTransferData.getValueByName("RInvalidate");
     mContNo = (String) mTransferData.getValueByName("ContNo");
     mRIcomCode = (String) mTransferData.getValueByName("RIcomCode");
     mReRiskCode = (String) mTransferData.getValueByName("ReRiskCode");
     mTempType = (String) mTransferData.getValueByName("TempType");

     return true;
 }

 public VData getResult() {
     return mResult;
 }

 /*
  * add by kevin, 2002-10-14
  */
 private void buildError(String szFunc, String szErrMsg) {
     CError cError = new CError();

     cError.moduleName = "ReComManageBL";
     cError.functionName = szFunc;
     cError.errorMessage = szErrMsg;
     this.mErrors.addOneError(cError);
 }
}
