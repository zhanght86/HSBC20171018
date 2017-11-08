package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
/**
 * <p>Title: lis</p>
 * <p>Description: 个单分红计算 - 分红险种数据准备BL</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft</p>
 * @author tm
 * @version 1.0
 */

public class BonusRiskPreBL {
private static Logger logger = Logger.getLogger(BonusRiskPreBL.class);
  public CErrors mErrors = new CErrors();
  private String CurrentDate = PubFun.getCurrentDate();
  private String CurrentTime = PubFun.getCurrentTime();
  private GlobalInput mGlobalInput = new GlobalInput();
  //需要插入的保单红利分配主表
  private LOBonusMainSet mInsertLOBonusMainSet = new LOBonusMainSet();
  //需要插入的险种维护报表
  private LoBonusRiskRemSet mInsertLoBonusRiskRemSet = new LoBonusRiskRemSet();
  //红利计算年
  private String mCalYear = "";
  //界面传入红利险种维护数据
  private LoBonusRiskRemSet mLoBonusRiskRemSet = new LoBonusRiskRemSet();
  //界面传入需要校验的SQL
  private String mCalSQL = "";
  private String mOperator = "";
  //传入BLS的VData
  private VData mResult = new VData();
  
  private MMap mMap = new MMap();	
  
  public BonusRiskPreBL() {
  }

  /**
   * 程序处理入口
   * @param sInputData
   * @param tOperator
   * @return
   */
  public boolean submitData(VData sInputData,String tOperator)
  {
    this.mOperator = tOperator;
    //开始数据准备
    if(!this.getInputData(sInputData))
    {
      return false;
    }
    //开始数据校验
    if(!this.checkData(tOperator))
    {
      return false;
    }
    //业务逻辑处理
    if(!this.dealData())
    {
      return false;
    }
    //更新数据准备
    if(!this.prepareOutData())
    {
      return false;
    }
    //数据保存
	PubSubmit tSubmit = new PubSubmit();
	if (!tSubmit.submitData(this.mResult, "")) {
		buildError("BonusRiskPre", "数据提交失败！");
		return false;
	}
//    BonusRiskPreBLS tBonusRiskPreBLS = new BonusRiskPreBLS();
//    if(!tBonusRiskPreBLS.submitData(this.mResult,this.mOperator))
//    {
//      this.mErrors.copyAllErrors(tBonusRiskPreBLS.mErrors);
//      return false;
//    }
    return true;
  }

  private boolean prepareOutData()
  {
    this.mResult.clear();
    this.mResult.add(mMap);

    return true;
  }
  /**
   * 业务逻辑处理
   * @return
   */
  private boolean dealData()
  {
    try {
      if(this.mOperator.equals("INSERT"))
      {
        //数据插入
        //1-查询红利分配主表是否有数据，如果有的话，不做处理，没有的话，做插入
        String tSQL_LoBonusMain = "select * from lobonusmain where FiscalYear='"+"?FiscalYear?"+"' and GroupID=1 ";
        LOBonusMainSet tLOBonusMainSet = new LOBonusMainSet();
        LOBonusMainDB tLOBonusMainDB = new LOBonusMainDB();
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(tSQL_LoBonusMain);
        sqlbv.put("FiscalYear", this.mCalYear);
        tLOBonusMainSet.set(tLOBonusMainDB.executeQuery(sqlbv));
        if(tLOBonusMainSet.size()<=0)
        {
          //没有数据，做插入处理
          LOBonusMainSchema tLOBonusMainSchema = new LOBonusMainSchema();
          tLOBonusMainSchema.setGroupID(1);
          tLOBonusMainSchema.setFiscalYear(this.mCalYear);
          tLOBonusMainSchema.setDistributeValue(0);//大盘金额
          tLOBonusMainSchema.setDistributeRate(0);//红利比例
          tLOBonusMainSchema.setComputeState("0");//计算状态设置为0 ：未计算
          tLOBonusMainSchema.setOperator(this.mGlobalInput.Operator);
          tLOBonusMainSchema.setMakeDate(this.CurrentDate);
          tLOBonusMainSchema.setMakeTime(this.CurrentTime);
          tLOBonusMainSchema.setModifyDate(this.CurrentDate);
          tLOBonusMainSchema.setModifyTime(this.CurrentTime);
          tLOBonusMainSchema.setBonusCoefSum(0);//红利系数和，最初设置为0
          tLOBonusMainSchema.setBonusGroupType("1");//红利分配组类型，1
//          tLOBonusMainSchema.setBonusCondition(this.tReplaceSQL(this.mCalSQL));//红利计算限制条件
          this.mInsertLOBonusMainSet.add(tLOBonusMainSchema);
	      mMap.put(mInsertLOBonusMainSet,  "DELETE&INSERT");
        }
//        for(int i=1;i<=tLOBonusMainSet.size();i++)
//        {
//          //存在，需要做校验，暂时不加。
//        }
        //2-准备分红险种维护表数据
        for(int i=1;i<=this.mLoBonusRiskRemSet.size();i++)
        {
          LoBonusRiskRemSchema tempLoBonusRiskRemSchema = new LoBonusRiskRemSchema();
          tempLoBonusRiskRemSchema.setSchema(mLoBonusRiskRemSet.get(i));
          tempLoBonusRiskRemSchema.setMakeDate(this.CurrentDate);
          tempLoBonusRiskRemSchema.setMakeTime(this.CurrentTime);
          tempLoBonusRiskRemSchema.setModifyDate(this.CurrentDate);
          logger.debug(tempLoBonusRiskRemSchema.getModifyDate()+"@@@");
          tempLoBonusRiskRemSchema.setModifyTime(this.CurrentTime);
          tempLoBonusRiskRemSchema.setOperator(this.mGlobalInput.Operator);
          this.mInsertLoBonusRiskRemSet.add(tempLoBonusRiskRemSchema);
	      mMap.put(mInsertLoBonusRiskRemSet,  "DELETE&INSERT");
          logger.debug(mInsertLoBonusRiskRemSet.get(i).getModifyDate()+"@@@1");
        }
      }
      else if(this.mOperator.equals("DELETE"))
      {
        this.mInsertLoBonusRiskRemSet.add(this.mLoBonusRiskRemSet);
	    mMap.put(mInsertLoBonusRiskRemSet,  "DELETE");       
      }
//      else if(this.mOperator.equals("OTHERSAVE"))
//      {
//        String tSQL1 = "select * from lcpol a where grppolno = '00000000000000000000'  and appflag='1' "
//                     + " and cvalidate <= '"+this.mCalYear+"'||'-12-31' "
//                     + " and exists (select riskcode from lmriskapp where bonusflag = 'Y' and riskcode=a.riskcode) "
//                     + " and not exists (select '1' from LoBonusRiskRem where "
//                     + " FiscalYear='"+this.mCalYear+"' and state='00' and riskcode = a.riskcode)  and not exists "
//                     + " (select polno from LOBonusPol  where FiscalYear="+this.mCalYear+" and polno = a.polno and GroupID='1') ";
//        TransferData tTransferData = new TransferData();
//        tTransferData.setNameAndValue("BonusCalYear",this.mCalYear);
//        String tOtherSQL = this.explanSQL(this.mCalSQL,tTransferData);
//       // tOtherSQL = this.tReplaceSQL(tOtherSQL);
//        //提高校验效率
//        if(tOtherSQL.indexOf("group by")==-1)
//        {
//          tOtherSQL = tOtherSQL + " and rownum=1 ";
//        }
//        ExeSQL tExeSQL = new ExeSQL();
//        //检查扩展算法是否有问题
//        try {
//          tExeSQL.execSQL(tSQL1+" "+tOtherSQL);
//          if(tExeSQL.mErrors.needDealError())
//          {
//            this.dealError("","扩充算法错误："+tExeSQL.mErrors.getFirstError().toString());
//            return false;
//          }
//        }
//        catch (Exception ex) {
//          this.dealError("","扩充算法错误："+ex.toString());
//          return false;
//        }
//
//        //
//        String tSQL_LoBonusMain = "select * from lobonusmain where FiscalYear='"+this.mCalYear+"' and GroupID=1 ";
//        LOBonusMainSet tLOBonusMainSet = new LOBonusMainSet();
//        LOBonusMainDB tLOBonusMainDB = new LOBonusMainDB();
//        tLOBonusMainSet.set(tLOBonusMainDB.executeQuery(tSQL_LoBonusMain));
//        if(tLOBonusMainSet.size()<=0)
//        {
//          //没有数据，做插入处理
//          LOBonusMainSchema tLOBonusMainSchema = new LOBonusMainSchema();
//          tLOBonusMainSchema.setGroupID(1);
//          tLOBonusMainSchema.setFiscalYear(this.mCalYear);
//          tLOBonusMainSchema.setDistributeValue(0);//大盘金额
//          tLOBonusMainSchema.setDistributeRate(0);//红利比例
//          tLOBonusMainSchema.setComputeState("0");//计算状态设置为0 ：未计算
//          tLOBonusMainSchema.setOperator(this.mGlobalInput.Operator);
//          tLOBonusMainSchema.setMakeDate(this.CurrentDate);
//          tLOBonusMainSchema.setMakeTime(this.CurrentTime);
//          tLOBonusMainSchema.setModifyDate(this.CurrentDate);
//          tLOBonusMainSchema.setModifyTime(this.CurrentTime);
//          tLOBonusMainSchema.setBonusCoefSum(0);//红利系数和，最初设置为0
//          tLOBonusMainSchema.setBonusGroupType("1");//红利分配组类型，1
//          tLOBonusMainSchema.setBonusCondition(this.tReplaceSQL(this.mCalSQL));//红利计算限制条件
//          this.mInsertLOBonusMainSet.add(tLOBonusMainSchema);
//        }
//        for(int i=1;i<=tLOBonusMainSet.size();i++)
//        {
//          //存在，需要做校验，暂时不加。
//          LOBonusMainSchema tLOBonusMainSchema = new LOBonusMainSchema();
//          tLOBonusMainSchema.setSchema(tLOBonusMainSet.get(i));
//          tLOBonusMainSchema.setBonusCondition(this.mCalSQL);
//          tLOBonusMainSchema.setModifyDate(this.CurrentDate);
//          tLOBonusMainSchema.setModifyTime(this.CurrentTime);
//          tLOBonusMainSchema.setOperator(this.mGlobalInput.Operator);
//          tLOBonusMainSchema.setBonusCondition(this.tReplaceSQL(this.mCalSQL));//红利计算限制条件
//          this.mInsertLOBonusMainSet.add(tLOBonusMainSchema);
//        }
//      }
		//输入完毕
		String tUPDateSQL="update LOBonusMain set ComputeState='1',ModifyDate='"+"?CurrentDate?"+"',ModifyTime='"+"?CurrentTime?"+"'"
        +" where GroupID='1' and FiscalYear="+"?mCalYear?";
		 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	        sqlbv1.sql(tUPDateSQL);
	        sqlbv1.put("CurrentDate", this.CurrentDate);
	        sqlbv1.put("CurrentTime", this.CurrentTime);
	        sqlbv1.put("mCalYear", this.mCalYear);
		mMap.put(sqlbv1, "UPDATE");
    }
    catch (Exception ex) {
      ex.printStackTrace();
      this.dealError("",ex.toString());
      return false;
    }
    return true;
  }
  /**
   * 获得数据
   * @param sInputData
   * @return
   */
  private boolean getInputData(VData sInputData)
  {
    try {
      this.mCalYear = sInputData.get(0)==null?"":(String)sInputData.get(0);
      this.mLoBonusRiskRemSet.set(sInputData.get(1)==null?(new LoBonusRiskRemSet()):(LoBonusRiskRemSet)sInputData.get(1));
      this.mCalSQL = sInputData.get(2)==null?"":(String)sInputData.get(2);
      this.mGlobalInput = sInputData.get(3)==null?(new GlobalInput()):(GlobalInput)sInputData.get(3);
      return true;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      this.dealError("",ex.toString());
      return false;
    }
  }
  /**
   * 数据校验
   * @param tOpeartor
   * @return
   */
  private boolean checkData(String tOpeartor)
  {
    return true;
  }
  /**
   * 错误容器
   * @param FuncName String
   * @param ErrMsg String
   */
  private void dealError(String FuncName, String ErrMsg) {
    CError tError = new CError();
    tError.moduleName = "NAAgentAccreditAutoRun";
    tError.functionName = FuncName;
    tError.errorMessage = ErrMsg;
    this.mErrors.addOneError(tError);
  }

  private String tReplaceSQL(String tSQL)
  {
    String tResult = "";
    int tBegin = 0;
    String tSql,tStr="",tStr1="";
    tSql=tSQL;
    try {
      tSql=StrTool.replaceEx(tSql,"'","''");
       logger.debug("tSql:"+tSql);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return tSQL;
    }
    tResult = tSql;
     logger.debug("tResult:"+tResult);
    return tResult;
  }
  /**
   * 解析字符串，替换变量。
   * @param tSQL
   * @param tTransferData
   * @return
   */
  public static String explanSQL(String tSQL,TransferData tTransferData)
  {
    String sResult = "";
    String tSql,tStr="",tStr1="";
    tSql=tSQL;
    try
    {
      while(true)
      {
        tStr=PubFun.getStr(tSql,2,"?");
        if (tStr.equals(""))
          break;
        tStr1="?"+tStr.trim()+"?";
        //替换变量
        tSql=StrTool.replaceEx(tSql,tStr1,getValueByName(tStr,tTransferData));
      }
    }
    catch(Exception ex)
    {
      // @@错误处理
      return "";
    }
    sResult = tSql;
    return sResult;
  }
  /**
   * 根据变量名得到变量的值
   * @return String 如果不正确返回"",否则返回变量值
   */
  public static String getValueByName(String cVarName,TransferData tTransferData)
  {
    String tReturn="";
    String tTemp = "";
    tReturn = (String)tTransferData.getValueByName(cVarName);
    return tReturn;
  }
	/**
	 * 错误构建方法
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "InsuAccBala";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理错误
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
  public static void main(String[] args) {
    BonusRiskPreBL bonusRiskPreBL1 = new BonusRiskPreBL();
  }
}
