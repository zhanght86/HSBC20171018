

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

public class GrpubAcciClaimTabBL
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
    private String mContNo="";
    private String thisSerialNo = "";
    private String mRIcomCode="";
    private String mReRiskCode="";
    private String mTempType="";
    /** 数据操作字符串 */
    private String strOperate="";
    private MMap mMap = new MMap();

    //业务处理相关变量
    /** 全局数据 */
    public GrpubAcciClaimTabBL()
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
            /** @@错误处理*/
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
        /**新建一个TextTag的实例*/
        TextTag texttag = new TextTag();
        /**统计的起讫时间*/
        ListTable multTable = new ListTable();
        multTable.setName("MULT");
        /**以下表示在 报表中生成 20列，*/
        String[] Title = {"col1", "col2", "col3", "col4", "col5", "col6",
                         "col7", "col8", "col9", "col10",
                         "col11", "col12", "col13", "col14", "col15", "col16",
                         "col17", "col18", "col19", "col20"};
        try {
            SSRS tSSRS = new SSRS();
            String sql = "";
            //从再保接口表中提数
            sql = " select X.A0, X.A19,X.A1,X.A2,X.A3,X.A4,X.A5,X.A6,X.A7,X.A8,X.A9,X.A10,X.A11,X.A12,X.A13,X.A14,X.A15,X.A16,X.A17,X.A18 "
                  + " from (select (select distinct ComPanyName from ReInsuranceComPanyInfo where ComPanyNo=b.ComPanyNo) A0, a.GrpContno A1,a.RiskCode  A2,a.InsuredNo A3,max(a.InsuredName) A4,(case max(a.InsuredSex) when '0' then '男' else '女' end)  A5,max(a.CValiDate)  A6, (case a.polstate when '00' then '有效' when '01' then '垫缴有效' when '02' then '失效' when '03' then '终止' when '04' then '暂停' when '05' then '豁免' when '06' then '迁出' else '不知名状态' end)  A7, max(a.InsuredBirthday) A8,(select max(c.InsuredAppAge) from lcpol c where c.insuredNo=a.insuredno and c.riskcode=a.riskcode) A9, (case max(a.payintv) when 0 then '趸缴' when 1 then '月缴' when 3 then '季缴' when 6 then '半年缴' when 12 then '年缴' when -1 then '不定期缴' else '不知名缴费方式' end ) A10, nvl(sum(a.Amnt),0) A11, (select nvl(sum(c.prem),0) from ripolrecordbake c where c.serialno=a.serialno and c.grpcontno = a.grpcontno and c.insuredno=a.insuredno and c.riskcode=a.riskcode and c.eventtype='04' and c.StandbyString1='2') A12,max(a.AccDate) A13, max(ClaimDate) A14, (select nvl(sum(d.PremChang),0) from ripolrecordbake c,ReinsureRecordTrace d where c.serialno=d.serialno and c.eventno=d.eventno and c.serialno=a.serialno and c.grpcontno = a.grpcontno and c.insuredno=a.insuredno and c.riskcode=a.riskcode and c.eventtype in ('01','03') and c.StandbyString1='2' and d.ComPanyNo=b.ComPanyNo ) A15,nvl(sum(a.ClmGetMoney),0) A16, (select NVL(sum(d.ReturnPay),0) from RIPolRecordBake c,ReinsureRecordTrace d where c.SerialNo=d.SerialNo and c.eventno=d.eventno and c.serialno=a.serialno and c.grpcontno = a.grpcontno and c.insuredno=a.insuredno and c.riskcode=a.riskcode and c.eventtype='04' and c.StandbyString1='2' and d.ComPanyNo=b.ComPanyNo) A17, max(a.ClaimReason)  A18,(select name from ldcom where comcode=a.StandbyString3) A19  from RIPolRecordBake a, reinsurerecordtrace b where a.eventno=b.eventno and a.SerialNo=b.SerialNo  and a.EventType='04' and a.dutycode in (select distinct associatedcode from accumulaterdcode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000002')) and a.StandbyString1='2' and  a.cvalidate >= '"+mRValidate+"' and a.enddate<='"+mRInvalidate+"' ";
            if (mReRiskCode != null && !mReRiskCode.equals("")) {
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

            sql = sql + " group by a.serialno,b.ComPanyNo,a.StandbyString3,a.GrpContno,a.InsuredNo,a.RiskCode,a.polstate order by a.GrpContno,a.insuredno,a.RiskCode) X  where rownum <=1000 ";

            //从中间表中提数
//            sql = " select StrColumn1,StrColumn2,StrColumn3,StrColumn4,StrColumn5,StrColumn6,StrColumn7,StrColumn8,StrColumn9,StrColumn10,StrColumn11,StrColumn12,NumColumn1,NumColumn2,StrColumn13,StrColumn14,NumColumn3,NumColumn4,NumColumn5,StrColumn15 from RIStaTab where SerialNo = '"+mStaTerm+"' and TabType ='020003' ";
//            if (mReRiskCode != null && !mReRiskCode.equals("")) {
//                sql = sql+" and trim(StrColumn4)='" + mReRiskCode +"' ";
//            }
//            if(mRIcomCode!=null &&!mRIcomCode.equals("")){
//                sql = sql+" and trim(StrColumn15) = '"+mRIcomCode+"' ";
//            }
//            if (mTempType.equals("2")) {
//                sql = sql + " and TempType = '03' ";
//            } else if (mTempType.equals("1")) {
//                sql = sql + " and TempType = '00' ";
//            }
            System.out.println("zb: SQL: " + sql);

            ExeSQL tExeSQL = new ExeSQL();
            tSSRS = tExeSQL.execSQL(sql);
            /**查询结果的记录条数*/
            int count = tSSRS.getMaxRow();
            System.out.println("该sql执行后的记录条数为：" + count);

            /**将查询结果存放到临时 二维数组中*/
            String temp[][] = tSSRS.getAllData();
            String[] strCol;
            for (int i = 0; i < count; i++) {
                strCol = new String[20];
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
                multTable.add(strCol);
            }
        } catch (Exception e) {
            CError tError = new CError();
            tError.moduleName = "OrderCollectBL";
            tError.functionName = "";
            tError.errorMessage = "客户基本信息查询失败";
            this.mErrors.addOneError(tError);
        }
        /**LRExportClaim2.vts/**新建一个XmlExport的实例*/
        XmlExport xmlexport = new XmlExport();
        /**最好紧接着就初始化xml文档*/
        xmlexport.createDocument("LRExportClaim2.vts", "printer");

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
        System.out.println("Come to  getInputData()..........");
         /**全局变量*/
         this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
         mTransferData = ((TransferData) cInputData.getObjectByObjectName("TransferData", 0));
        /**TransferData不是表，这和LRContManageBl.java不同*/
         if (mTransferData == null) {
             buildError("getInputData", "没有得到足够的信息！");
             return false;
         }
        mRValidate = (String) mTransferData.getValueByName("RValidate");
        mRInvalidate = (String) mTransferData.getValueByName("RInvalidate");
        mContNo  = (String) mTransferData.getValueByName("ContNo");
        mRIcomCode = (String) mTransferData.getValueByName("RIcomCode");
        mReRiskCode = (String) mTransferData.getValueByName("ReRiskCode");
        mTempType = (String) mTransferData.getValueByName("TempType");
        return true;
    }

    public VData getResult() {
        return mResult;
    }

    private String getEndDate(String aSerialNo) {
        String yStr = aSerialNo.substring(0, 4);
        int y = Integer.parseInt(yStr);
        String mStr = aSerialNo.substring(4, 6);
        String dStr = "";
        switch (Integer.parseInt(mStr)) {
        case 1: dStr = "31"; break;
        case 2: if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) dStr = "29"; else dStr = "28";break;
        case 3: dStr = "31"; break;
        case 4: dStr = "30"; break;
        case 5: dStr = "31"; break;
        case 6: dStr = "30"; break;
        case 7: dStr = "31"; break;
        case 8: dStr = "31"; break;
        case 9: dStr = "30"; break;
        case 10: dStr = "31";break;
        case 11: dStr = "30"; break;
        case 12: dStr = "31"; break;
        }
        String tEndDate = yStr + "-" + mStr + "-" + dStr;
        return tEndDate;
    }

    public static void main(String[] args) {
        GlobalInput globalInput = new GlobalInput();
        globalInput.ComCode = "8611";
        globalInput.Operator = "001";
         VData vData = new VData();
        GrpubAcciClaimTabBL t = new GrpubAcciClaimTabBL();
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
