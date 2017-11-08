package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

/**
 * <p>Title: Lis_New</p>
 *
 * <p>Description:获取应收保费和预提佣金的数据 </p>
 *
 * <p>Copyright: Copyright (c) 2002</p>
 *
 * <p>Company: </p>
 *
 * @author Sinosoft
 * @version 1.0
 */
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import java.util.*;
import java.text.*;

public class ReceiDataBL {
private static Logger logger = Logger.getLogger(ReceiDataBL.class);
  public ReceiDataBL() {
  }

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData;
  /** 数据操作字符串 */
  private String mOperate;
  private String mToday;
  private String nToday;
  private String mWageNo;
  private String Money="";
  private int mTime = 0;
  private String bDate = ""; //界面传入的提取日期
  private String eDate = "";
  private String mAccountingDate = ""; //界面传入的记账日期
  private GlobalInput mGlobalInput = new GlobalInput();
  private String mManageCom = "";
  private String Serialno = "";
  private String lToday = "";
  private MMap map = new MMap();
  private VData vd = new VData();

  public boolean submitData(VData cInputData, String cOperate) {

    logger.debug("Begin  ReceiDataBL");
    mInputData = (VData) cInputData.clone();
    mOperate = cOperate;

    try {
      if (!getInputData(cInputData)) {
        return false;
      }

      if (!checkData()) {
        return false;
      }

      if (mOperate.equals("Prem")) {
        getPremData();
      }
      if (mOperate.equals("Commion")){
        getCommissionData();
      }

      PubSubmit tPubSubmit = new PubSubmit();
      if (!tPubSubmit.submitData(vd, "")) {
        buildError("submitData", "提交数据库失败");
        return false;
      }

    }
    catch (Exception ex) {
      ex.printStackTrace();
      buildError("submit", "发生异常");
      return false;
    }
    return true;
  }

  private boolean checkData() {

    if (mOperate.equals("")) {
      buildError("check", "不支持的操作字符");
      return false;
    }

    return true;
  }

  private boolean getInputData(VData cInputData) {

    mGlobalInput.setSchema( (GlobalInput) cInputData.getObjectByObjectName(
        "GlobalInput", 0));

    TransferData tTransferData = (TransferData) cInputData.getObjectByObjectName(
        "TransferData", 0);
    if("Prem".equals(mOperate))
    {
		bDate = (String) tTransferData.getValueByName("bDate"); //提取日期
		eDate = (String) tTransferData.getValueByName("eDate"); //提取日期
		mAccountingDate = (String) tTransferData.getValueByName("accountDate"); //记账日期
		Integer itemp = (Integer) tTransferData.getValueByName("itemp"); //凭证类别
		mTime = itemp.intValue();
		mManageCom = (String) tTransferData.getValueByName("Managecom"); //管理机构
		
		FDate chgdate = new FDate();
	    Date lDate = chgdate.getDate(PubFun.calDate(eDate, -60, "D", null));//宽限期为60日
	    lToday = chgdate.getString(lDate);
	    mToday = eDate;
	    if (mToday == null || mToday.equals("")) {
	     	buildError("getInputData", "获取提取终止时间失败！");
	       return false;
	     }
    }

    if("Commion".equals(mOperate))
    {
    	mWageNo = (String) tTransferData.getValueByName("tWageNo");
		mAccountingDate = (String) tTransferData.getValueByName("accountDate"); //记账日期
		Integer itemp = (Integer) tTransferData.getValueByName("itemp"); //凭证类别
		mTime = itemp.intValue();
		mManageCom = (String) tTransferData.getValueByName("Managecom"); //管理机构
    }


    if (mGlobalInput == null) {
      buildError("getInputData", "没有得到足够的信息！");
      return false;
    }




    return true;
  }

  private boolean getPremData() {

    logger.debug("Begin getPremData--------");
    String fsql ="select sum(t.money), t.salechnl, t.riskcode, t.managecom from (select sumduepaymoney money, "
    			+"(select salechnl from lcpol where polno = a.polno) salechnl,a.riskcode riskcode,a.managecom managecom "
    			+"from ljspayperson a where grppolno = '00000000000000000000' and paycount > 1 and paytype = 'ZC' "
    			+"and exists (select 1 from ljspay where getnoticeno = a.getnoticeno and startpaydate >='"+"?date?"+"' and startpaydate <='"+"?date1?"+"') "
    			+"and exists (select 1 from lmriskapp where riskcode = a.riskcode and riskperiod = 'L' and risktype in ('L', 'H')) "
    			+"and exists (select 1 from lcpol c where polno = a.polno and appflag = '1' and exists (select 1 from lccontstate where "
    			+"contno = c.contno and polno = c.polno  and statetype = 'Available' and state='0' and enddate is null) "
    			+"and payintv > 0 and datediff(to_date(to_char(paytodate,'YYYY-MM-DD'),'YYYY-MM-DD'),to_date(to_char(add_months(cvalidate, 12),'YYYY-MM-DD'),'YYYY-MM-DD')) < 0)) t "
    			+"group by t.riskcode, t.salechnl, t.managecom"; 
    SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    sqlbv1.sql(fsql);
    sqlbv1.put("date",lToday);
    sqlbv1.put("date1",eDate);
    logger.debug("查询首年续期应收保费---------" + fsql);
    SSRS fSSRS = new SSRS();
    ExeSQL tExeSQL = new ExeSQL();
    fSSRS = tExeSQL.execSQL(sqlbv1);
    OfinaStorDataSet sOfinaStorDataSet = new OfinaStorDataSet();

    for (int j = 1; j <= fSSRS.getMaxRow(); j++) {

      Money = new DecimalFormat("0.00").format(new Double(fSSRS.GetText(j, 1)));
      Serialno = PubFun1.CreateMaxNo("SerialNo", 20);
      OfinaStorDataSchema tOfinaStorDataSchema = new OfinaStorDataSchema();
      tOfinaStorDataSchema.setSerialNo(Serialno);
      tOfinaStorDataSchema.setVoucherType("61");
      tOfinaStorDataSchema.setTransDate(mToday);                                  //提取期间的最后一天
      tOfinaStorDataSchema.setMoney(Money);
      tOfinaStorDataSchema.setSaleChnl(fSSRS.GetText(j, 2));
      tOfinaStorDataSchema.setRiskCode(fSSRS.GetText(j, 3));
      tOfinaStorDataSchema.setManageCom(fSSRS.GetText(j, 4));
      tOfinaStorDataSchema.setMakeDate(PubFun.getCurrentDate());
      tOfinaStorDataSchema.setMakeTime(PubFun.getCurrentTime());
      tOfinaStorDataSchema.setType("S"); //首年续期
      if( isExist(tOfinaStorDataSchema))
        continue;

        sOfinaStorDataSet.add(tOfinaStorDataSchema);
       

    }
    map.put(sOfinaStorDataSet, "INSERT");


    String xsql ="select sum(t.money), t.salechnl, t.riskcode, t.managecom from (select sumduepaymoney money, "
				+"(select salechnl from lcpol where polno = a.polno) salechnl,a.riskcode riskcode,a.managecom managecom "
				+"from ljspayperson a where grppolno = '00000000000000000000' and paycount > 1 and paytype = 'ZC' "
				+"and exists (select 1 from ljspay where getnoticeno = a.getnoticeno and startpaydate >='"+"?date2?"+"' and startpaydate <='"+"?date3?"+"') "
				+"and exists (select 1 from lmriskapp where riskcode = a.riskcode and riskperiod = 'L' and risktype in ('L', 'H')) "
				+"and exists (select 1 from lcpol c where polno = a.polno and appflag = '1' and exists (select 1 from lccontstate where "
				+"contno = c.contno and polno = c.polno  and statetype = 'Available' and state='0' and enddate is null) "
				+"and payintv > 0 and datediff(to_date(to_char(paytodate,'YYYY-MM-DD'),'YYYY-MM-DD'),to_date(to_char(add_months(cvalidate, 12),'YYYY-MM-DD'),'YYYY-MM-DD'))>= 0)) t "
				+"group by t.riskcode, t.salechnl, t.managecom"; 
    SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    sqlbv2.sql(xsql);
    sqlbv2.put("date2",lToday);
    sqlbv2.put("date3",eDate);
    logger.debug("查询续年续期应收保费---------" + xsql);
    SSRS xSSRS = new SSRS();
    ExeSQL xExeSQL = new ExeSQL();
    xSSRS = xExeSQL.execSQL(sqlbv2);
    OfinaStorDataSet xOfinaStorDataSet = new OfinaStorDataSet();
    for (int i = 1; i <= xSSRS.getMaxRow(); i++) {

      Money = new DecimalFormat("0.00").format(new Double(xSSRS.GetText(i, 1)));
      Serialno = PubFun1.CreateMaxNo("SerialNo", 20);
      OfinaStorDataSchema xOfinaStorDataSchema = new OfinaStorDataSchema();
      xOfinaStorDataSchema.setSerialNo(Serialno);
      xOfinaStorDataSchema.setVoucherType("61");
      xOfinaStorDataSchema.setTransDate(mToday);                    //提取期间的最后一天
      xOfinaStorDataSchema.setMoney(Money);
      xOfinaStorDataSchema.setSaleChnl(xSSRS.GetText(i, 2));
      xOfinaStorDataSchema.setRiskCode(xSSRS.GetText(i, 3));
      xOfinaStorDataSchema.setManageCom(xSSRS.GetText(i, 4));
      xOfinaStorDataSchema.setMakeDate(PubFun.getCurrentDate());
      xOfinaStorDataSchema.setMakeTime(PubFun.getCurrentTime());
      xOfinaStorDataSchema.setType("X");                                      //续年续期

      if(isExist(xOfinaStorDataSchema))
        continue;

        xOfinaStorDataSet.add(xOfinaStorDataSchema);
       

    }
    map.put(xOfinaStorDataSet, "INSERT");


    vd.add(map);

    return true;
  }

  private boolean getCommissionData() {


    String tCalDate[] = new String[2];
    String msDate =mWageNo.substring(0, 4)+ "-"+mWageNo.substring(4,6)+"-01";
    tCalDate = PubFun.calFLDate(msDate);
    String mStartDate = tCalDate[0];                                                 //默认业务日期为每月1号

    logger.debug("mWageNo:" + mWageNo);

    LAWageOTOFDB tLAWageOTOFDB = new LAWageOTOFDB();

    String tComSql = "select distinct(substr(ManageCom,1,6)) from LAWageOtoF "
                   + "where IndexCalNo='" + "?d2?" + "' and ManageCom like concat('" +"?d3?"+ "','%') and branchtype='1' ";
    SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
    sqlbv3.sql(tComSql);
    sqlbv3.put("d2",mWageNo);
    sqlbv3.put("d3",mManageCom);
    logger.debug("tComSql---------"+tComSql);
    ExeSQL mExeSQL = new ExeSQL();
    SSRS mSSRS = mExeSQL.execSQL(sqlbv3);
    OfinaStorDataSet wOfinaStorDataSet = new OfinaStorDataSet();
    for (int k = 1; k <= mSSRS.MaxRow; k++) {

      String tManageCom = mSSRS.GetText(k, 1);
      //附加佣金提取
      String tSql = "select * from LAWageOtoF where IndexCalNo='"+"?m1?"+"' and ManageCom like concat('"+"?m2?"+ "','%')"
                  + "and branchtype='1' and f25>=0 and state='0' ";
      SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
      sqlbv4.sql(tSql);
      sqlbv4.put("m1",mWageNo);
      sqlbv4.put("m2",tManageCom);
      logger.debug(tSql);
      LAWageOTOFSet tLAWageOTOFSet = tLAWageOTOFDB.executeQuery(sqlbv4);
      if (tLAWageOTOFSet.size() == 0)
        return true;


      double[] tWage;
      double[] tTax;
      String[] tWageItem;
      String[] tTaxItem;


      tWage = new double[21];
      tTax = new double[3];


      for (int i = 1; i <= tLAWageOTOFSet.size(); i++) {

        LAWageOTOFSchema tLAWageOTOFSchema = new LAWageOTOFSchema();
        tLAWageOTOFSchema.setSchema(tLAWageOTOFSet.get(i));
        tWage[0] = tWage[0] + tLAWageOTOFSchema.getF06(); //全勤奖金
        tWage[1] = tWage[1] + tLAWageOTOFSchema.getF07(); //转正奖金
        tWage[2] = tWage[2] + tLAWageOTOFSchema.getF08(); //月度销售奖
        tWage[3] = tWage[3] + tLAWageOTOFSchema.getF10(); //继续率奖金
        tWage[4] = tWage[4] + tLAWageOTOFSchema.getF11(); //个人突出贡献奖
        tWage[5] = tWage[5] + tLAWageOTOFSchema.getF09(); //增员辅导津贴
        tWage[6] = tWage[6] + tLAWageOTOFSchema.getF17(); //客户服务奖
        tWage[7] = tWage[7] + tLAWageOTOFSchema.getF12(); //永续经营奖
        tWage[8] = tWage[8] + tLAWageOTOFSchema.getF13(); //组职务底薪
        tWage[9] = tWage[9] + tLAWageOTOFSchema.getF14(); //部职务底薪
        tWage[10] = tWage[10] + tLAWageOTOFSchema.getF15(); //区职务底薪
        tWage[11] = tWage[11] + tLAWageOTOFSchema.getF16(); //总监职务底薪
        tWage[12] = tWage[12] + tLAWageOTOFSchema.getF22(); //衔接薪资
        tWage[13] = tWage[13] + tLAWageOTOFSchema.getF30(); //加扣款1
        tWage[14] = tWage[14] + tLAWageOTOFSchema.getK11(); //差惩款
        tWage[15] = tWage[15] - tLAWageOTOFSchema.getK21(); //差勤扣款
        tWage[16] = tWage[16] + tLAWageOTOFSchema.getLastMoney(); //上期余额
        tWage[17] = tWage[17] + tLAWageOTOFSchema.getK12(); //加扣款2
        tWage[18] = tWage[18] + tLAWageOTOFSchema.getF19(); //推动费加扣款
        tWage[19] = tWage[19] + tLAWageOTOFSchema.getF18(); //推动费奖金加款
        tWage[20] = tWage[20] + tLAWageOTOFSchema.getF21();             //加扣款7

        tTax[0] = tTax[0] + tLAWageOTOFSchema.getK01(); //营业税及附加
        tTax[1] = tTax[1] + tLAWageOTOFSchema.getK02(); //个人所得税
        tTax[2] = tTax[2] + tLAWageOTOFSchema.getF20(); //推动费加扣款
      }

      tWageItem = new String[21];
      tTaxItem = new String[3];
      tWageItem[0] = "全勤奖金";
      tWageItem[1] = "转正奖";
      tWageItem[2] = "月度销售奖";
      tWageItem[3] = "继续率奖金";
      tWageItem[4] = "个人突出贡献奖";
      tWageItem[5] = "增员辅导津贴";
      tWageItem[6] = "客户服务奖";
      tWageItem[7] = "永续经营奖";
      tWageItem[8] = "组职务底薪";
      tWageItem[9] = "部职务底薪";
      tWageItem[10] = "区职务底薪";
      tWageItem[11] = "总监职务底薪";
      tWageItem[12] = "同业衔接资金(支持薪资)"; //衔接薪资
      tWageItem[13] = "加扣款";
      tWageItem[14] = "奖惩款项";
      tWageItem[15] = "差勤扣款";
      tWageItem[16] = "业务员佣金上月余额"; //上期余额
      tWageItem[17] = "同业衔接资金(加扣款2)";
      tWageItem[18] = "推动费加扣款";
      tWageItem[19] = "推动费奖金加款";
      tWageItem[20] = "机构发展奖金（加扣款7)";

      tTaxItem[0] = "代理人营业税";
      tTaxItem[1] = "代理人个人所得税";
      tTaxItem[2] = "推动费加扣款";




      for (int j = 0; j < tWage.length; j++) {

        if (tWage[j] == 0)
          continue;
        Money = new DecimalFormat("0.00").format(new Double(tWage[j]));
        Serialno = PubFun1.CreateMaxNo("SerialNo", 20);
        OfinaStorDataSchema fOfinaStorDataSchema = new OfinaStorDataSchema();
        fOfinaStorDataSchema.setSerialNo(Serialno);
        fOfinaStorDataSchema.setVoucherType("62");
        fOfinaStorDataSchema.setTransDate(mStartDate);
        fOfinaStorDataSchema.setMoney(Money);
        fOfinaStorDataSchema.setManageCom(tManageCom);
        fOfinaStorDataSchema.setMakeDate(PubFun.getCurrentDate());
        fOfinaStorDataSchema.setMakeTime(PubFun.getCurrentTime());
        fOfinaStorDataSchema.setSegment1(tWageItem[j]);                            //附加佣金项的说明
        fOfinaStorDataSchema.setSegment2(mWageNo);
        fOfinaStorDataSchema.setType("W");                                         //附加佣金
        if (isExist(fOfinaStorDataSchema))
          break;
        wOfinaStorDataSet.add(fOfinaStorDataSchema);
      }


      for (int j = 0; j < tTax.length; j++) {

        if (tTax[j] == 0)
          continue;
        Money = new DecimalFormat("0.00").format(new Double(tTax[j]));
        Serialno = PubFun1.CreateMaxNo("SerialNo", 20);
        OfinaStorDataSchema tOfinaStorDataSchema = new OfinaStorDataSchema();
        tOfinaStorDataSchema.setSerialNo(Serialno);
        tOfinaStorDataSchema.setVoucherType("62");
        tOfinaStorDataSchema.setTransDate(mStartDate);
        tOfinaStorDataSchema.setMoney(Money);
        tOfinaStorDataSchema.setManageCom(tManageCom);
        tOfinaStorDataSchema.setMakeDate(PubFun.getCurrentDate());
        tOfinaStorDataSchema.setMakeTime(PubFun.getCurrentTime());
        tOfinaStorDataSchema.setSegment1(tTaxItem[j]);
        tOfinaStorDataSchema.setSegment2(mWageNo);
        tOfinaStorDataSchema.setType("T");                                     //税款
        if (isExist(tOfinaStorDataSchema))
          break;
        wOfinaStorDataSet.add(tOfinaStorDataSchema);
        }

    }
    map.put(wOfinaStorDataSet, "INSERT");


  //提取直接佣金
    tComSql = "select distinct(substr(ManageCom,1,6)) from LACommisionOtoF where WageNo='" +"?w1?"+ "' and ManageCom like '"+"?w2?"+"%'";
    SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
    sqlbv6.sql(tComSql);
    sqlbv6.put("w1",mWageNo);
    sqlbv6.put("w2",mManageCom);
    ExeSQL fExeSQL = new ExeSQL();
    SSRS fmSSRS = fExeSQL.execSQL(sqlbv6);
    OfinaStorDataSet fOfinaStorDataSet = new OfinaStorDataSet();
    for (int k = 1; k <= fmSSRS.MaxRow; k++) {

      String fManageCom = fmSSRS.GetText(k, 1);
      String tSql = "select riskcode,payyear,BranchType,sum(Fyc) from "
                  + "(select riskcode,payyear,BranchType,Fyc from LACommisionOtoF where ManageCom like '"+"?q1?"+"%' "
                  + "and Fyc!=0 and riskmark='0' and commdire='1' and branchtype='1' and agentcode in "
                  + "(select agentcode from lawageotof where state='0' and indexcalno='"+"?q2?"+"' and f25>=0 and not exists "
                  + "(select 'X' from labranchgroup where branchattr = lawageotof.branchattr and branchtype = '1' and state = '1' "
                  + "and indexcalno = '"+"?q3?"+"' and branchlevel = '01')) "  //排除特殊服务组
                  + "and caldate>='"+"?q4?"+"' and caldate<='"+ "?q5?"+"') g "
                  + "group by riskcode,payyear,BranchType ";
      SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
      sqlbv5.sql(tSql);
      sqlbv5.put("q1",fManageCom);
      sqlbv5.put("q2",mWageNo);
      sqlbv5.put("q3",mWageNo);
      sqlbv5.put("q4",tCalDate[0]);
      sqlbv5.put("q5",tCalDate[1]);
      logger.debug(tSql);

      SSRS fSSRS = fExeSQL.execSQL(sqlbv5);
      for (int i = 1; i <= fSSRS.MaxRow; i++) {
        Money = new DecimalFormat("0.00").format(new Double(fSSRS.GetText(i,4)));
        Serialno = PubFun1.CreateMaxNo("SerialNo", 20);
        OfinaStorDataSchema fyOfinaStorDataSchema = new OfinaStorDataSchema();
        fyOfinaStorDataSchema.setSerialNo(Serialno);
        fyOfinaStorDataSchema.setVoucherType("62");
        fyOfinaStorDataSchema.setTransDate(mStartDate);
        fyOfinaStorDataSchema.setMoney(Money);
        fyOfinaStorDataSchema.setSaleChnl(fSSRS.GetText(i,3));
        fyOfinaStorDataSchema.setRiskCode(fSSRS.GetText(i,1));
        fyOfinaStorDataSchema.setManageCom(fManageCom);
        fyOfinaStorDataSchema.setMakeDate(PubFun.getCurrentDate());
        fyOfinaStorDataSchema.setMakeTime(PubFun.getCurrentTime());
        fyOfinaStorDataSchema.setSegment1(fSSRS.GetText(i,2));
        fyOfinaStorDataSchema.setSegment2(mWageNo);
        fyOfinaStorDataSchema.setType("F");                                    //直接佣金
        if (isExist(fyOfinaStorDataSchema))
          break;
        fOfinaStorDataSet.add(fyOfinaStorDataSchema);
        }
      }
      map.put(fOfinaStorDataSet, "INSERT");
     vd.add(map);
    return true;
  }

  public void buildError(String szFunc, String szErrMsg) {
    CError cr = new CError();
    cr.moduleName = "";
    cr.functionName = szFunc;
    cr.errorMessage = szErrMsg;
    this.mErrors.addOneError(cr);

  }




  private boolean isExist(OfinaStorDataSchema tOfinaStorDataSchema)
  {
    OfinaStorDataDB tOfinaStorDataDB = new OfinaStorDataDB();

    tOfinaStorDataDB.setVoucherType(tOfinaStorDataSchema.getVoucherType());
    tOfinaStorDataDB.setType(tOfinaStorDataSchema.getType());
    tOfinaStorDataDB.setTransDate(tOfinaStorDataSchema.getTransDate());
    tOfinaStorDataDB.setMoney(tOfinaStorDataSchema.getMoney());
    tOfinaStorDataDB.setManageCom(tOfinaStorDataSchema.getManageCom());

    if(!(tOfinaStorDataSchema.getSaleChnl()==null || tOfinaStorDataSchema.getSaleChnl().equals("")))
    {
      tOfinaStorDataDB.setSaleChnl(tOfinaStorDataSchema.getSaleChnl());
    }
    if(!(tOfinaStorDataSchema.getRiskCode()==null || tOfinaStorDataSchema.getRiskCode().equals("")))
    {
      tOfinaStorDataDB.setRiskCode(tOfinaStorDataSchema.getRiskCode());
    }
    if(!(tOfinaStorDataSchema.getSegment1()==null || tOfinaStorDataSchema.getSegment1().equals("")))
    {
      tOfinaStorDataDB.setSegment1(tOfinaStorDataSchema.getSegment1());
    }
    if(!(tOfinaStorDataSchema.getSegment2()==null || tOfinaStorDataSchema.getSegment2().equals("")))
    {
      tOfinaStorDataDB.setSegment2(tOfinaStorDataSchema.getSegment2());
    }
    if(tOfinaStorDataDB.getCount()>0)
     return true;

    return false;
  }




  public static void main(String[] args)
{
    ReceiDataBL tReceiDataBL = new ReceiDataBL();
    VData vData = new VData();
    GlobalInput tG = new GlobalInput();

    tG.Operator = "001";
    tG.ManageCom = "86";
    vData.addElement(tG);
    TransferData tTransferData = new TransferData();
    String bDate ="2007-10-26";
    String eDate = "2007-11-25";
    String accountDate = "2007-11-19";
    Integer itemp = new Integer(15);
    String Managecom = "86";
    tTransferData.setNameAndValue("bDate", bDate);
    tTransferData.setNameAndValue("eDate", eDate);
    tTransferData.setNameAndValue("accountDate", accountDate);
    tTransferData.setNameAndValue("itemp", itemp);
    tTransferData.setNameAndValue("Managecom", Managecom);
    vData.addElement(tTransferData);
    tReceiDataBL.submitData(vData, "Prem");
  }

}
