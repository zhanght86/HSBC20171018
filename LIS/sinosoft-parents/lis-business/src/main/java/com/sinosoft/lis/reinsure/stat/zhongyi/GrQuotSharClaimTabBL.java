

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

public class GrQuotSharClaimTabBL
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
    private String lastSerialNo = "";
    private String mContNo= "";
    private String mRIcomCode="";
    private String mReRiskCode="";
    private String mTempType="";

    /** 数据操作字符串 */
    private String strOperate="";

    private MMap mMap = new MMap();
    private PubSubmit tPubSubmit = new PubSubmit();


    //业务处理相关变量
    /** 全局数据 */

    public GrQuotSharClaimTabBL()
    {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

   /**
    * 准备往后层输出所需要的数据
    * 输出：如果准备数据时发生错误则返回false,否则返回true
    */
   private boolean prepareOutputData() {
        try
        {
            this.mInputData.clear();
            this.mInputData.add(mMap);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDComBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }
    /**
     * 团体保障保险成数分保合同-承保报表
     * @return boolean
     */
    private boolean getPrintData() {
        //新建一个TextTag的实例
        TextTag texttag = new TextTag();

        //统计的起讫时间
        ListTable multTable = new ListTable();
        multTable.setName("MULT");
        //在 报表中生成 14列，
        String[] Title = {"col1", "col2", "col3", "col4", "col5",
                         "col6", "col7", "col8", "col9", "col10",
                         "col11", "col12", "col13", "col14","col15"};
        try {
            SSRS tSSRS = new SSRS();
            String sql = " select X.A0,X.A1 ,X.A2 ,X.A3 ,X.A4 ,X.A5 , X.A6 ,X.A7 ,X.A8 ,(X.A9+X.A10+X.A11),(X.A12+X.A13+X.A14) ,X.A15,X.A16,X.A17,X.A18 from (  "
                         ;
            //已支付
            String sql1 = " select (select distinct ComPanyName from ReInsuranceComPanyInfo where ComPanyNo=b.ComPanyNo) A0,(select distinct GrpName from lcgrpcont where grpcontno=a.grpcontno) A1, a.GrpContno A2, max(a.cvalidate) A3, max(a.enddate) A4,'团体保障保险' A5,max(a.Insuredname) A6, max(a.InsuredBirthday) A7, nvl(sum(b.ReturnPay),0) A8, (select nvl(sum(d.StandbyDouble2),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo and c.serialno = '"+lastSerialNo+"' and c.insuredno=a.insuredno and c.riskcode=a.riskcode and c.StandbyString1='1' and c.eventtype='01' and  d.ComPanyNo= b.ComPanyNo ) A9,(select nvl(sum(d.StandbyDouble3),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo and c.serialno = '"+lastSerialNo+"' and c.insuredno=a.insuredno and c.riskcode=a.riskcode and c.StandbyString1='1' and c.eventtype='01'  and  d.ComPanyNo= b.ComPanyNo ) A10,(select nvl(sum(d.StandbyDouble4),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo and c.serialno = '"+lastSerialNo+"' and c.insuredno=a.insuredno and c.riskcode=a.riskcode and c.StandbyString1='1' and c.eventtype='01'  and  d.ComPanyNo= b.ComPanyNo ) A11,(select nvl(sum(d.StandbyDouble2),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo and  c.cvalidate >= '"+mRValidate+"' and c.enddate<='"+mRInvalidate+"' and c.insuredno=a.insuredno and c.riskcode=a.riskcode and c.StandbyString1='1' and c.eventtype='01'  and  d.ComPanyNo= b.ComPanyNo ) A12,(select nvl(sum(d.StandbyDouble3),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo and c.cvalidate >= '"+mRValidate+"' and c.enddate<='"+mRInvalidate+"' and c.insuredno=a.insuredno and c.riskcode=a.riskcode and c.StandbyString1='1' and c.eventtype='01'  and  d.ComPanyNo= b.ComPanyNo ) A13,(select nvl(sum(d.StandbyDouble4),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo and c.cvalidate >= '"+mRValidate+"' and c.enddate<='"+mRInvalidate+"' and c.insuredno=a.insuredno and c.riskcode=a.riskcode and c.StandbyString1='1' and c.eventtype='01'  and  d.ComPanyNo= b.ComPanyNo ) A14,'已支付' A15,max(a.AccDate) A16, max(a.StandbyDate2) A17, max(a.ClaimReason) A18 from ripolrecordbake a, reinsurerecordtrace b where a.eventno=b.eventno and a.SerialNo=b.SerialNo and a.dutycode in (select distinct associatedcode from accumulaterdcode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000001')) and  a.cvalidate >= '"+mRValidate+"' and a.enddate<='"+mRInvalidate+"' and a.eventtype='04' and a.StandbyString1='1' and b.ComPanyNo = '"+mRIcomCode+"' "
                          ;
            //有效保单未支付
            String sql2 = " select '' A0, (select GrpName from lcgrpcont where grpcontno=a.grpcontno) A1, a.GrpContno A2, max(a.cvalidate) A3,max(a.enddate) A4,'团体保障保险' A5,max(a.Insuredname) A6, max(a.InsuredBirthday) A7, 0 A8, (select nvl(sum(c.UnExpireReserve),0) from ReserveBussness c where c.SerialNo='"+lastSerialNo+"' and c.Insuredno=a.Insuredno and c.riskCode=a.riskcode) A9,(select nvl(sum(c.UnDetermineReserve),0) from ReserveBussness c where c.SerialNo='"+lastSerialNo+"' and c.Insuredno=a.Insuredno and c.riskCode=a.riskcode) A10,(select nvl(sum(c.UnReportReserve),0) from ReserveBussness c where c.SerialNo='"+lastSerialNo+"' and c.Insuredno=a.Insuredno and c.riskCode=a.riskcode) A11,(select nvl(sum(c.UnExpireReserve),0) from ReserveBussness c where c.cvalidate >= '"+mRValidate+"' and c.enddate<='"+mRInvalidate+"' and c.Insuredno=a.Insuredno and c.riskCode=a.riskcode) A12,(select nvl(sum(c.UnDetermineReserve),0) from ReserveBussness c where  c.cvalidate >= '"+mRValidate+"' and c.enddate<='"+mRInvalidate+"' and c.Insuredno=a.Insuredno and c.riskCode=a.riskcode) A13,(select nvl(sum(c.UnReportReserve),0) from ReserveBussness c where  c.cvalidate >= '"+mRValidate+"' and c.enddate<='"+mRInvalidate+"' and c.Insuredno=a.Insuredno and c.riskCode=a.riskcode) A14,'未支付' A15, max(c.K) A16, to_date('0001-1-1','yyyy-mm-dd') A17, max((select accdesc from llcaserela a1, llsubreport a2 where a1.SubRptNo=a2.SubRptNo and a1.caseRelaNo=c.I)) A18 from lcpol a,lcduty b,(select a.caseno A,a.polno B,a.dutycode C,max(d.endcasedate) D, sum(a.standpay) E,sum(a.realpay) F, max(c.rgtstate) G, max(caseRelaNo) I,max(c.AccidentSite) J, max(c.AccidentDate) K from LLClaimDetail a ,LLcase c,LLClaim d where a.CaseNo = c.caseno and a.caseno = d.caseno and exists (select distinct(otherno) from ljaget where othernotype='5' and otherno=a.caseno and (confDate >='"+mRInvalidate+"' or confDate is null)) and c.endcasedate >='"+mRValidate+"' and c.endcasedate<='"+mRInvalidate+"' and a.dutycode in (select distinct associatedcode from accumulaterdcode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000001')) group by a.caseno,a.polno,a.dutycode ) c where a.polno=b.polno and a.polno=c.B and b.dutycode=c.C and exists (select * from lcgrpcont where grpcontno=a.grpcontno and GEBClient='1') "
                          ;
            //无效保单未支付
            String sql3 = " select '' A0, (select GrpName from lcgrpcont where grpcontno=a.grpcontno) A1, a.GrpContno A2, max(a.cvalidate) A3, max(a.enddate) A4, '团体保障保险' A5,max(a.Insuredname) A6, max(a.InsuredBirthday) A7, 0 A8, (select nvl(sum(c.UnExpireReserve),0) from ReserveBussness c where c.SerialNo='"+lastSerialNo+"' and c.Insuredno=a.Insuredno and c.riskCode=a.riskcode) A9, (select nvl(sum(c.UnDetermineReserve),0) from ReserveBussness c where c.SerialNo='"+lastSerialNo+"' and c.Insuredno=a.Insuredno and c.riskCode=a.riskcode) A10, (select nvl(sum(c.UnReportReserve),0) from ReserveBussness c where c.SerialNo='"+lastSerialNo+"' and c.Insuredno=a.Insuredno and c.riskCode=a.riskcode) A11, (select nvl(sum(c.UnExpireReserve),0) from ReserveBussness c where  c.cvalidate >= '"+mRValidate+"' and c.enddate<='"+mRInvalidate+"' and c.Insuredno=a.Insuredno and c.riskCode=a.riskcode) A12, (select nvl(sum(c.UnDetermineReserve),0) from ReserveBussness c where c.cvalidate >= '"+mRValidate+"' and c.enddate<='"+mRInvalidate+"' and c.Insuredno=a.Insuredno and c.riskCode=a.riskcode) A13, (select nvl(sum(c.UnReportReserve),0) from ReserveBussness c where c.cvalidate >= '"+mRValidate+"' and c.enddate<='"+mRInvalidate+"' and c.Insuredno=a.Insuredno and c.riskCode=a.riskcode) A14, '未支付' A15, max(c.K) A16, to_date('0001-1-1','yyyy-mm-dd') A17, max((select accdesc from llcaserela a1, llsubreport a2 where a1.SubRptNo=a2.SubRptNo and a1.caseRelaNo=c.I)) A18 from lbpol a,lbduty b, (select a.caseno A,a.polno B,a.dutycode C,max(d.endcasedate) D, sum(a.standpay) E,sum(a.realpay) F, max(c.rgtstate) G, max(caseRelaNo) I,max(c.AccidentSite) J, max(c.AccidentDate) K from LLClaimDetail a ,LLcase c,LLClaim d where a.CaseNo = c.caseno and a.caseno = d.caseno and exists (select distinct(otherno) from ljaget where othernotype='5' and otherno=a.caseno and (confDate >='"+mRInvalidate+"' or confDate is null)) and c.endcasedate >='"+mRValidate+"' and c.endcasedate<='"+mRInvalidate+"' and a.dutycode in (select distinct associatedcode from accumulaterdcode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000001')) group by a.caseno,a.polno,a.dutycode ) c where a.polno=b.polno and a.polno=c.B and b.dutycode=c.C and (exists (select * from lcgrpcont where grpcontno=a.grpcontno and GEBClient='1') or exists (select * from lbgrpcont where grpcontno=a.grpcontno and GEBClient='1')) "
                          ;
            if (mReRiskCode != null && !mReRiskCode.equals("")) {
                sql1 = sql1 + " and a.RiskCode='" + mReRiskCode + "' ";
                sql2 = sql2 + " and a.RiskCode='" + mReRiskCode + "' ";
                sql3 = sql3 + " and a.RiskCode='" + mReRiskCode + "' ";
            }
            if(mTempType.equals("2")){
                sql1=sql1 + " and a.ReinsreFlag in ('00','03') ";
                sql2=sql2 + " and exists (select * from ridutystate where proposalno=a.proposalno and dutycode =b.dutycode and state in ('00','03')) ";
                sql3=sql3 + " and exists (select * from ridutystate where proposalno=a.proposalno and dutycode =b.dutycode and state in ('00','03')) ";
            }else if(mTempType.equals("1")){
                sql1=sql1 + " and (a.ReinsreFlag not in ('00','03') or a.ReinsreFlag is null) ";
                sql2=sql2 + " and not exists (select * from ridutystate where proposalno=a.proposalno and dutycode =b.dutycode and state not in ('00','03')) ";
                sql3=sql3 + " and not exists (select * from ridutystate where proposalno=a.proposalno and dutycode =b.dutycode and state not in ('00','03')) ";
            }
            sql = sql + sql1 + " group by b.companyno,a.grpcontno,a.Insuredno,a.riskcode  union all ";
            sql = sql + sql2 + " group by a.grpcontno,a.Insuredno,a.riskcode ,c.I  union all ";
            sql = sql + sql3 + " group by a.grpcontno,a.Insuredno,a.riskcode ,c.I ";
            sql = sql + " ) X where rownum <=1000 order by X.A15,X.A0,X.A2,X.A6 ";

            System.out.println("zb: SQL: " + sql);
            ExeSQL tExeSQL = new ExeSQL();
            tSSRS = tExeSQL.execSQL(sql);
            //查询结果的记录条数
            int count = tSSRS.getMaxRow();
            System.out.println("该sql执行后的记录条数为：" + count);
            //将查询结果存放到临时 二维数组中
            String temp[][] = tSSRS.getAllData();
            String[] strCol;
            for (int i = 0; i < count; i++) {
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
            tError.moduleName = "GrQuotSharClaimTabBL";
            tError.functionName = "";
            tError.errorMessage = "团体保障保险理赔信息查询失败";
            this.mErrors.addOneError(tError);
        }
        //LRExportClaim1.vts //新建一个XmlExport的实例
        XmlExport xmlexport = new XmlExport();
        //最好紧接着就初始化xml文档
        xmlexport.createDocument("LRExportClaim1.vts", "printer");
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
      System.out.println("Come to  getInputData()..........");
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
        mReRiskCode = (String) mTransferData.getValueByName("ReRiskCode");
        mTempType = (String) mTransferData.getValueByName("TempType");

        return true;
    }


//    private boolean getStaSerialNo(){
//       String yStr = mStaTerm.substring(0,mStaTerm.indexOf("-"));
//       String qStr = mStaTerm.substring(mStaTerm.indexOf("-")+1);
//       int quater = Integer.parseInt(qStr);
//       if(quater>4){
//           buildError("getStaSerialNo", " GrQuotSharCESSTabBL->3 获取的季度值错误 ");
//           return false;
//       }
//       switch (quater){
//       case 1: thisSerialNo="'"+yStr+"01','"+yStr+"02','"+yStr+"03'"; break ;
//       case 2: thisSerialNo="'"+yStr+"04','"+yStr+"05','"+yStr+"06'"; break ;
//       case 3: thisSerialNo="'"+yStr+"07','"+yStr+"08','"+yStr+"09'"; break ;
//       case 4: thisSerialNo="'"+yStr+"10','"+yStr+"11','"+yStr+"12'"; break ;
//       }
//       String lyStr=(Integer.parseInt(yStr)-1)+"";/**2006*/
//       switch (quater){
//       case 1: lastSerialNo="'"+lyStr+"10'"+","+lyStr+"11"+","+lyStr+"12"; break ;
//       case 2: lastSerialNo="'"+yStr+"01','"+yStr+"02','"+yStr+"03'"; break ;
//       case 3: lastSerialNo="'"+yStr+"04','"+yStr+"05','"+yStr+"06'"; break ;
//       case 4: lastSerialNo="'"+yStr+"07','"+yStr+"08','"+yStr+"09'"; break ;
//       }
//       return true;
//   }


   private String getEndDate(String aSerialNo) {
       String yStr = aSerialNo.substring(0, 4);
       int y = Integer.parseInt(yStr);
       String mStr = aSerialNo.substring(4, 6);
       String dStr = "";
       switch (Integer.parseInt(mStr))
       {
       case 1: dStr="31"; break;
       case 2: if(y%4==0&&y%100!=0||y%400==0) dStr="29"; else dStr="28"; break;
       case 3: dStr="31"; break;
       case 4: dStr="30"; break;
       case 5: dStr="31"; break;
       case 6: dStr="30"; break;
       case 7: dStr="31"; break;
       case 8: dStr="31"; break;
       case 9: dStr="30"; break;
       case 10: dStr="31"; break;
       case 11: dStr="30"; break;
       case 12: dStr="31"; break;
       }
       String tEndDate = yStr+"-"+mStr+"-"+dStr;
//        System.out.println("tEndDate:"+tEndDate);
       return tEndDate;
   }

   public static void main(String[] args)
   {}

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

    private void jbInit() throws Exception {
    }
}
