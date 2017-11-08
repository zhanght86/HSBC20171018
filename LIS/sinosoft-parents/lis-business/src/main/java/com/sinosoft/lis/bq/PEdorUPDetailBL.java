package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

//import utils.system;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.lang.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全-万能险追加保费处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author：QianLy
 * @version：1.0
 * @CreateDate：2007-10-29
 */
public class PEdorUPDetailBL
{
private static Logger logger = Logger.getLogger(PEdorUPDetailBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
        private TransferData mTransferData = new TransferData();
        private GlobalInput mGlobalInput = new GlobalInput();
        private Reflections mReflections = new Reflections();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private double mWorkNoteFee=0.0; //保单初始管理费
        private double tPayMoney =0.0;//追加保费总额
        private double tAddMoney = 0.0;//减去保单初始管理费后净值
	private String tRiskCode =null;
        private String remark;
        private String tPayDate;//进帐户的日期

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
        /**管理费轨迹记录*/
        private LPInsureAccFeeTraceSet mLPInsureAccFeeTraceSet = new LPInsureAccFeeTraceSet();
        /**子管理费帐户表*/
        private LPInsureAccClassFeeSet updLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
        /**管理费帐户表*/
	private LPInsureAccFeeSet updLPInsureAccFeeSet = new LPInsureAccFeeSet();

        /** 计算要素 */
        private BqCalBase mBqCalBase = new BqCalBase();
        private Reflections ref = new Reflections();
	// 获得系统日期和时间
	String strCurrentDate = PubFun.getCurrentDate();
	String strCurrentTime = PubFun.getCurrentTime();

	public PEdorUPDetailBL(){
	}

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串，主要包括""和""
     * @return 布尔值（true--提交成功, false--提交失败）
     */
	public boolean submitData(VData cInputData, String cOperate){
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()){
                    return false;
		}
		// 数据校验操作
		if (!checkData()){
                    return false;
		}
                // 清除上次保存的P表数据
                if(!delPData()){
                    return false;
                }
		// 进行业务处理
		if (!dealData()){
                    return false;
		}
		return true;
	}

        /**
         * 删除上次保存过的数据
         * @return boolean
         */
        private boolean delPData(){
            //清除P表中上次保存过的数据
            String edorno = mLPEdorItemSchema.getEdorNo();
            String edortype = mLPEdorItemSchema.getEdorType();
            String contno = mLPEdorItemSchema.getContNo();
            String sqlWhere = " contno = '?contno?' and edorno = '?edorno?' and edortype = '?edortype?'";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql("delete from lppol where" + sqlWhere);
            sqlbv1.put("contno", contno);
            sqlbv1.put("edorno", edorno);
            sqlbv1.put("edortype", edortype);
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
            sqlbv2.sql("delete from lpcont where" + sqlWhere);
            sqlbv2.put("contno", contno);
            sqlbv2.put("edorno", edorno);
            sqlbv2.put("edortype", edortype);
            SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
            sqlbv3.sql("delete from lpduty where" + sqlWhere);
            sqlbv3.put("contno", contno);
            sqlbv3.put("edorno", edorno);
            sqlbv3.put("edortype", edortype);
            SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
            sqlbv4.sql("delete from lpprem where" + sqlWhere);
            sqlbv4.put("contno", contno);
            sqlbv4.put("edorno", edorno);
            sqlbv4.put("edortype", edortype);
            SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
            sqlbv5.sql("delete from LPInsureAccClass where" + sqlWhere);
            sqlbv5.put("contno", contno);
            sqlbv5.put("edorno", edorno);
            sqlbv5.put("edortype", edortype);
            SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
            sqlbv6.sql("delete from LPInsureAccTrace where" + sqlWhere);
            sqlbv6.put("contno", contno);
            sqlbv6.put("edorno", edorno);
            sqlbv6.put("edortype", edortype);
            SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
            sqlbv7.sql("delete from LPInsureAccFee where" + sqlWhere);
            sqlbv7.put("contno", contno);
            sqlbv7.put("edorno", edorno);
            sqlbv7.put("edortype", edortype);
            SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
            sqlbv8.sql("delete from LPInsureAccClassFee where" + sqlWhere);
            sqlbv8.put("contno", contno);
            sqlbv8.put("edorno", edorno);
            sqlbv8.put("edortype", edortype);
            SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
            sqlbv9.sql("delete from LPInsureAccFeeTrace where" + sqlWhere);
            sqlbv9.put("contno", contno);
            sqlbv9.put("edorno", edorno);
            sqlbv9.put("edortype", edortype);
            SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
            sqlbv10.sql(" delete from LJSGetEndorse " +
                    " where EndorsementNo='?EndorsementNo?' and FeeOperationType='?FeeOperationType?' and contno = '?contno?'");
            sqlbv10.put("contno", contno);
            sqlbv10.put("EndorsementNo", edorno);
            sqlbv10.put("FeeOperationType", edortype);
            SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
            sqlbv11.sql("delete from ljspayperson where getnoticeno='?getnoticeno?' and paytype = '?paytype' and contno = '?contno?'");
            sqlbv11.put("contno", contno);
            sqlbv11.put("getnoticeno", edorno);
            sqlbv11.put("paytype", edortype);
            mMap.put(sqlbv1, "DELETE");
            mMap.put(sqlbv2, "DELETE");
            mMap.put(sqlbv3, "DELETE");
            mMap.put(sqlbv4, "DELETE");
            mMap.put(sqlbv5, "DELETE");
            mMap.put(sqlbv6, "DELETE");
            mMap.put(sqlbv7, "DELETE");
            mMap.put(sqlbv8, "DELETE");
            mMap.put(sqlbv9, "DELETE");
            mMap.put(sqlbv10, "DELETE");
            mMap.put(sqlbv11, "DELETE");
            return true;
    }

    /**
     * 数据输出方法，供外界获取数据处理结果
     *
     * @return 包含有数据查询结果字符串的VData对象
     */
     public VData getResult(){
         return mResult;
     }

    /**
     * 数据输出方法，供外界获取数据处理结果
     *
     * @return 包含有数据查询结果字符串的VData对象
     */
     public TransferData getTransferResult(){
         return mTransferData;
     }

    /**
     * 将外部传入的数据分解到本类的属性中
     *
     * @param: 无
     * @return: boolean
     */
     private boolean getInputData(){
         try{
             mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
             mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
             if (mLPEdorItemSchema == null || mGlobalInput == null){
                 mErrors.addOneError(new CError("数据传输不完全！"));
                 return false;
             }
             remark = mLPEdorItemSchema.getStandbyFlag3();
             LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
             tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
             tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
             tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
             tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
             LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
             if (tLPEdorItemSet.size()<1){
                 // @@错误处理
                 CError tError = new CError();
                 tError.moduleName = "PEdorUPDetailBL";
                 tError.functionName = "getInputData";
                 tError.errorMessage = "查询保全项目失败！";
                 this.mErrors.addOneError(tError);
                 return false;
             }
             mLPEdorItemSchema = tLPEdorItemSet.get(1);

             if (mOperate.equals("UP||MAIN")){
                 mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
                 if (mTransferData == null){
                     mErrors.addOneError(new CError("数据传输不完全！"));
                     return false;
                 }
             }
         }catch (Exception e){
             // @@错误处理
             CError tError = new CError();
             tError.moduleName = "PEdorUPDetailBL";
             tError.functionName = "getInputData";
             tError.errorMessage = "接收数据失败！";
             this.mErrors.addOneError(tError);
             return false;
         }
         return true;
     }

    /**
     * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
     */
     private boolean checkData(){
         if (mOperate.equals("UP||MAIN")){
             String tStrPayMoney = (String)mTransferData.getValueByName("PayMoney");
             try{
                 tPayMoney = Double.parseDouble(tStrPayMoney);
                 if(tPayMoney<500 || tPayMoney%100!=0){
                     CError tError = new CError();
                     tError.moduleName = "PEdorUPDetailBL";
                     tError.functionName = "checkData";
                     tError.errorMessage = "追加保费最少500元,且为100元的整数倍！";
                     this.mErrors.addOneError(tError);
                     return false;
                 }
             }catch(Exception e){
                 CError tError = new CError();
                 tError.moduleName = "PEdorUPDetailBL";
                 tError.functionName = "checkData";
                 tError.errorMessage = "可能领取金额输入错误！";
		 this.mErrors.addOneError(tError);
                 return false;
             }
          }
          // 查询保单信息
          LCContDB tLCContDB = new LCContDB();
          tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
          if (!tLCContDB.getInfo()){
              // @@错误处理
              CError tError = new CError();
              tError.moduleName = "PEdorUPDetailBL";
              tError.functionName = "getInputData";
              tError.errorMessage = "查询保单信息失败！";
              this.mErrors.addOneError(tError);
              return false;
          }
          mLCContSchema.setSchema(tLCContDB.getSchema());
          return true;
      }

    /**
     * 根据前面的输入数据，进行逻辑处理
     *
     * @return 如果在处理过程中出错，则返回false,否则返回true
     */
     private boolean dealData(){
         String sql = " select * from lcpol where 1=1 "
                    + " and polno = mainpolno " + " and contno = '?contno?'";
         LCPolDB nLCPolDB = new LCPolDB();
         SQLwithBindVariables sqlbv=new SQLwithBindVariables();
         sqlbv.sql(sql);
         sqlbv.put("contno", mLPEdorItemSchema.getContNo());
         LCPolSet tLCPolSet = nLCPolDB.executeQuery(sqlbv);
         if (nLCPolDB.mErrors.needDealError()){
             CError.buildErr(this, "险种查询失败!");
             return false;
         }
         LCPolSchema tCPolSchema = tLCPolSet.get(1);
         tRiskCode = tCPolSchema.getRiskCode();
         if(mOperate.equals("UP||MAIN")){
             //准备个人保单帐户表的信息
             LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
             LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
             tLCInsureAccDB.setContNo(mLPEdorItemSchema.getContNo());
             tLCInsureAccDB.setPolNo(tCPolSchema.getPolNo());
             LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
             if (tLCInsureAccSet.size() < 1 || tLCInsureAccSet == null){
                 // @@错误处理
                 CError tError = new CError();
                 tError.moduleName = "PEdorUPDetailBL";
                 tError.functionName = "dealData";
                 tError.errorMessage = "帐户表表查询失败!";
                 this.mErrors.addOneError(tError);
                 return false;
             }else{
               tLCInsureAccSchema.setSchema(tLCInsureAccSet.get(1).getSchema());

               //查询该险种的不定期缴费责任编码
               String tDutyCode="";
               if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
            	   tDutyCode= BqNameFun.getAnother(null,"LMRiskDuty m,LMDutyPayRela y,LMDutyPay p",
                   "rownum = 1 and m.dutycode = y.dutycode and p.payplancode = y.payplancode and p.payintv = '-1' and m.riskcode",
                   tRiskCode,"M.dutycode");
               }else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
            	   tDutyCode= BqNameFun.getAnother_row(null,"LMRiskDuty m,LMDutyPayRela y,LMDutyPay p",
                           "m.dutycode = y.dutycode and p.payplancode = y.payplancode and p.payintv = '-1' and m.riskcode",
                           tRiskCode,"M.dutycode"," limit 0,1");  
               }
               //查询该险种的不定期缴费缴费编码
               SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
          	   sqlbv16.put("tRiskCode",tRiskCode);
               String tPayPlanCode = BqNameFun.getAnother(sqlbv16,"LMDutyPay",
                   "payplancode in( select payplancode from lmdutypayrela where dutycode in (select dutycode from lmriskduty where riskcode = '?tRiskCode?" +
                   "')) and payintv = '-1' and 1", "1", "PayPlanCode");

               if(!createLJSGetEndorse(tPayMoney, "BF", BqCode.Pay_Prem,tCPolSchema,tPayPlanCode,tDutyCode)){
                 // @@错误处理
                 CError tError = new CError();
                 tError.moduleName = "PEdorUPDetailBL";
                 tError.functionName = "dealData";
                 tError.errorMessage = "产生财务记录LJSGetEndorse失败!";
                 this.mErrors.addOneError(tError);
                 return false;
               }

               //PayDate的取值方式:上次结算点后一天与保全申请日次日取大
               tPayDate = PubFun.getLaterDate(PubFun.calDate(tLCInsureAccSchema.getBalaDate(),1,"D",null),mLPEdorItemSchema.getEdorValiDate());

               if(!makeFee(tLCInsureAccSchema,tPayPlanCode)){//计算保单初始扣费,,把保单管理费增加到扣费3个表中
                   // @@错误处理
                   CError tError = new CError();
                   tError.moduleName = "PEdorUPDetailBL";
                   tError.functionName = "dealData";
                   tError.errorMessage = "计算保单初始管理费失败!";
                   this.mErrors.addOneError(tError);
                    return false;
               }

               tAddMoney = tPayMoney - mWorkNoteFee;

               //处理Trace表
               //把追加部分加进AccTrace
               String tSql = "delete from LPInsureAccTrace where edortype ='?edortype?' and edorno ='?edorno?'";
               SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
               sqlbv1.sql(tSql);
               sqlbv1.put("edortype", mLPEdorItemSchema.getEdorType());
               sqlbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
               mMap.put(sqlbv1, "DELETE");
               LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
               String mSerialNo = PubFun1.CreateMaxNo("SERIALNO", PubFun.getNoLimit(mLPEdorItemSchema.getManageCom()));
               tLPInsureAccTraceSchema.setSerialNo(mSerialNo);
               tLPInsureAccTraceSchema.setFeeCode("000000");
               tLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema.getEdorType());
               tLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
               tLPInsureAccTraceSchema.setGrpContNo(tLCInsureAccSchema.getGrpContNo());
               tLPInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema.getGrpPolNo());
               tLPInsureAccTraceSchema.setContNo(tLCInsureAccSchema.getContNo());
               tLPInsureAccTraceSchema.setPolNo(tCPolSchema.getPolNo());
               tLPInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
               tLPInsureAccTraceSchema.setPayPlanCode(tPayPlanCode);
               tLPInsureAccTraceSchema.setAccAscription("1");
               tLPInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema.getRiskCode());
               tLPInsureAccTraceSchema.setPolNo(tCPolSchema.getPolNo());
               tLPInsureAccTraceSchema.setMoney(tAddMoney);
               tLPInsureAccTraceSchema.setMoneyType(mLPEdorItemSchema.getEdorType()); //追加保费
               tLPInsureAccTraceSchema.setPayDate(tPayDate);
               tLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
               tLPInsureAccTraceSchema.setManageCom(tCPolSchema.getManageCom());
               tLPInsureAccTraceSchema.setMakeDate(strCurrentDate);
               tLPInsureAccTraceSchema.setMakeTime(strCurrentTime);
               tLPInsureAccTraceSchema.setModifyDate(strCurrentDate);
               tLPInsureAccTraceSchema.setModifyTime(strCurrentTime);
               tLPInsureAccTraceSchema.setAccAlterType("3");
               tLPInsureAccTraceSchema.setAccAlterNo(mLPEdorItemSchema.getEdorNo()); //待定
               tLPInsureAccTraceSchema.setBusyType(mLPEdorItemSchema.getEdorType());
               tLPInsureAccTraceSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
               tLPInsureAccTraceSchema.setOtherType("1");
               tLPInsureAccTraceSchema.setState("0");
               mMap.put(tLPInsureAccTraceSchema, "DELETE&INSERT");

               //处理子帐户AccClass表
               //如果没有不定期缴费帐户的话要新建一个
               //如果已经存在不需要进行操作
               LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
               tLCInsureAccClassDB.setContNo(mLPEdorItemSchema.getContNo());
               tLCInsureAccClassDB.setPolNo(tCPolSchema.getPolNo());
               tLCInsureAccClassDB.setPayPlanCode(tPayPlanCode);
               tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());

               LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();
               if (tLCInsureAccClassSet == null || tLCInsureAccClassSet.size() < 1){
                   LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
                   mReflections.transFields(tLPInsureAccClassSchema,tLCInsureAccSchema);
                   tLPInsureAccClassSchema.setPayPlanCode(tPayPlanCode);
                   tLPInsureAccClassSchema.setAccAscription("1");
                   tLPInsureAccClassSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
                   tLPInsureAccClassSchema.setOtherType("1");
                   tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                   tLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                   tLPInsureAccClassSchema.setLastAccBala(0);
                   tLPInsureAccClassSchema.setInsuAccBala(tAddMoney);
                   tLPInsureAccClassSchema.setBalaDate(tPayDate);
                   tLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
                   tLPInsureAccClassSchema.setManageCom(tCPolSchema.getManageCom());
                   tLPInsureAccClassSchema.setMakeDate(strCurrentDate);
                   tLPInsureAccClassSchema.setMakeTime(strCurrentTime);
                   tLPInsureAccClassSchema.setModifyDate(strCurrentDate);
                   tLPInsureAccClassSchema.setModifyTime(strCurrentTime);
                   mMap.put(tLPInsureAccClassSchema,"DELETE&INSERT");
               }

               //处理责任Duty表

               LCDutyDB tLCDutyDB = new LCDutyDB();
               tLCDutyDB.setPolNo(tLCInsureAccSchema.getPolNo());
               tLCDutyDB.setDutyCode(tDutyCode);
               LCDutySet tLCDutySet = tLCDutyDB.query();
               //如果没有该对应责任项就新增一条
               if (tLCDutySet.size() < 1 || tLCDutySet == null){
                   LPDutySchema tLPDutySchema = new LPDutySchema();
                   LCDutyDB mLCDutyDB = new LCDutyDB();
                   mLCDutyDB.setPolNo(tLCInsureAccSchema.getPolNo());
                   LCDutySet mLCDutySet = mLCDutyDB.query();
                   mReflections.transFields(tLPDutySchema,mLCDutySet.get(1));
                   tLPDutySchema.setDutyCode(tDutyCode);
                   tLPDutySchema.setAmnt(0);
                   tLPDutySchema.setStandPrem(tPayMoney);
                   tLPDutySchema.setPrem(tPayMoney);
                   tLPDutySchema.setSumPrem(tPayMoney);
                   tLPDutySchema.setCValiDate(mLPEdorItemSchema.getEdorValiDate());
                   tLPDutySchema.setPaytoDate(mLPEdorItemSchema.getEdorValiDate());
                   tLPDutySchema.setPayIntv("-1");
                   tLPDutySchema.setMakeDate(strCurrentDate);
                   tLPDutySchema.setMakeTime(strCurrentTime);
                   tLPDutySchema.setModifyDate(strCurrentDate);
                   tLPDutySchema.setModifyTime(strCurrentTime);
                   tLPDutySchema.setOperator(mGlobalInput.Operator);
                   tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                   tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
                   mMap.put(tLPDutySchema,"DELETE&INSERT");
               }else{//已经存在不定期责任项的话就更新
                   LPDutySchema tLPDutySchema = new LPDutySchema();
                   LCDutySchema tLCDutySchema = new LCDutySchema();
                   tLCDutySchema.setSchema(tLCDutySet.get(1));
                   mReflections.transFields(tLPDutySchema,tLCDutySchema);
                   tLPDutySchema.setMakeDate(strCurrentDate);
                   tLPDutySchema.setMakeTime(strCurrentTime);
                   tLPDutySchema.setModifyDate(strCurrentDate);
                   tLPDutySchema.setModifyTime(strCurrentTime);
                   tLPDutySchema.setOperator(mGlobalInput.Operator);
                   tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                   tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
//                   tLPDutySchema.setPrem(tLCDutySchema.getPrem() + tPayMoney);
                   tLPDutySchema.setSumPrem(tLCDutySchema.getSumPrem() + tPayMoney);
//                   tLPDutySchema.setStandPrem(tLCDutySchema.getStandPrem() + tPayMoney);
                   tLPDutySchema.setPaytoDate(mLPEdorItemSchema.getEdorValiDate());
                   mMap.put(tLPDutySchema,"DELETE&INSERT");
               }

               //处理保费项Prem表
               LCPremDB tLCPremDB = new LCPremDB();
               tLCPremDB.setPolNo(tLCInsureAccSchema.getPolNo());
               tLCPremDB.setDutyCode(tDutyCode);
               tLCPremDB.setPayPlanCode(tPayPlanCode);
               LCPremSet tLCPremSet = tLCPremDB.query();
               //如果没有该对应不定期缴费项就新增一条
               if (tLCPremSet.size() < 1 || tLCPremSet == null){
                 LPPremSchema tLPPremSchema = new LPPremSchema();
                 LCPremDB mLCPremDB = new LCPremDB();
                 mLCPremDB.setPolNo(tLCInsureAccSchema.getPolNo());
                 LCPremSet mLCPremSet = mLCPremDB.query();
                 mReflections.transFields(tLPPremSchema,mLCPremSet.get(1));
                 tLPPremSchema.setDutyCode(tDutyCode);
                 tLPPremSchema.setPayPlanCode(tPayPlanCode);
                 tLPPremSchema.setUrgePayFlag("N");
                 tLPPremSchema.setPayIntv("-1");
                 tLPPremSchema.setPayTimes(1);
                 tLPPremSchema.setMakeDate(strCurrentDate);
                 tLPPremSchema.setMakeTime(strCurrentTime);
                 tLPPremSchema.setModifyDate(strCurrentDate);
                 tLPPremSchema.setModifyTime(strCurrentTime);
                 tLPPremSchema.setOperator(mGlobalInput.Operator);
                 tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                 tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//                 tLPPremSchema.setStandPrem(tPayMoney);
                 tLPPremSchema.setPrem(tPayMoney);
                 tLPPremSchema.setSumPrem(tPayMoney);
                 tLPPremSchema.setPaytoDate(mLPEdorItemSchema.getEdorValiDate());
                 mMap.put(tLPPremSchema,"DELETE&INSERT");
               }else{//已经存在不定期缴费项就更新
                 LPPremSchema tLPPremSchema = new LPPremSchema();
                 LCPremSchema tLCPremSchema = new LCPremSchema();
                 tLCPremSchema.setSchema(tLCPremSet.get(1));

                 mReflections.transFields(tLPPremSchema,tLCPremSchema);
                 tLPPremSchema.setMakeDate(strCurrentDate);
                 tLPPremSchema.setMakeTime(strCurrentTime);
                 tLPPremSchema.setModifyDate(strCurrentDate);
                 tLPPremSchema.setModifyTime(strCurrentTime);
                 tLPPremSchema.setOperator(mGlobalInput.Operator);
                 tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                 tLPPremSchema.setPayTimes(tLCPremSchema.getPayTimes() + 1);
                 tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                 tLPPremSchema.setPrem(tLCPremSchema.getPrem()+tPayMoney);
                 tLPPremSchema.setSumPrem(tLCPremSchema.getSumPrem() + tPayMoney);
//                 tLPPremSchema.setStandPrem(tLCPremSchema.getStandPrem() + tPayMoney);
                 tLPPremSchema.setPaytoDate(mLPEdorItemSchema.getEdorValiDate());
                 mMap.put(tLPPremSchema,"DELETE&INSERT");
               }

               //险种表更新
               LPPolSchema tLPPolSchema = new LPPolSchema();
               mReflections.transFields(tLPPolSchema,tCPolSchema);
               tLPPolSchema.setMakeDate(strCurrentDate);
               tLPPolSchema.setMakeTime(strCurrentTime);
               tLPPolSchema.setModifyDate(strCurrentDate);
               tLPPolSchema.setModifyTime(strCurrentTime);
               tLPPolSchema.setOperator(mGlobalInput.Operator);
               tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
               tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//               tLPPolSchema.setPrem(tCPolSchema.getPrem() + tPayMoney);
               tLPPolSchema.setSumPrem(tCPolSchema.getSumPrem() + tPayMoney);
               mMap.put(tLPPolSchema,"DELETE&INSERT");

               //保单表更新
               LCContDB tLCContDB = new LCContDB();
               tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
               LCContSchema tLCContSchema = new LCContSchema();
               tLCContSchema = tLCContDB.query().get(1);
               LPContSchema tLPContSchema = new LPContSchema();
               mReflections.transFields(tLPContSchema,tLCContSchema);
               tLPContSchema.setMakeDate(strCurrentDate);
               tLPContSchema.setMakeTime(strCurrentTime);
               tLPContSchema.setModifyDate(strCurrentDate);
               tLPContSchema.setModifyTime(strCurrentTime);
               tLPContSchema.setOperator(mGlobalInput.Operator);
               tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
               tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//               tLPContSchema.setPrem(tLCContSchema.getPrem() + tAddMoney);
               tLPContSchema.setSumPrem(tLCContSchema.getSumPrem() + tPayMoney);
               mMap.put(tLPContSchema,"DELETE&INSERT");

               //产生个人应收表LJSPayPerson
               LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
               mReflections.transFields(tLJSPayPersonSchema,tCPolSchema);
               tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
               tLJSPayPersonSchema.setPayAimClass("1");
               tLJSPayPersonSchema.setDutyCode(tDutyCode);
               tLJSPayPersonSchema.setPayPlanCode(tPayPlanCode);
               tLJSPayPersonSchema.setPayCount(1);

               //计算保单年度
               String tMainPolYearSql = "Select trunc(Months_between('?EdorValiDate?',lcpol.CValiDate)/12)+1 From lcpol Where polno = '?polno?'";
               SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
               sqlbv2.sql(tMainPolYearSql);
               sqlbv2.put("EdorValiDate", mLPEdorItemSchema.getEdorValiDate());
               sqlbv2.put("polno", tCPolSchema.getMainPolNo());
               ExeSQL tExeSQL = new ExeSQL();
			   String tMainPolYear =tExeSQL.getOneValue(sqlbv2);
//               int mInterval = PubFun.calInterval( mLPEdorItemSchema.getEdorValiDate(),tCPolSchema.getCValiDate(),"Y");
               tLJSPayPersonSchema.setMainPolYear(tMainPolYear);

               tLJSPayPersonSchema.setPolNo(tCPolSchema.getPolNo());
               tLJSPayPersonSchema.setPayDate(tCPolSchema.getPaytoDate());
               tLJSPayPersonSchema.setPayType(mLPEdorItemSchema.getEdorType());
               tLJSPayPersonSchema.setLastPayToDate(mLPEdorItemSchema.getEdorAppDate());
               tLJSPayPersonSchema.setCurPayToDate(mLPEdorItemSchema.getEdorValiDate());
               tLJSPayPersonSchema.setPayIntv("-1");
               tLJSPayPersonSchema.setMakeDate(strCurrentDate);
               tLJSPayPersonSchema.setMakeTime(strCurrentTime);
               tLJSPayPersonSchema.setModifyDate(strCurrentDate);
               tLJSPayPersonSchema.setModifyTime(strCurrentTime);
               tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
               tLJSPayPersonSchema.setSumActuPayMoney(tPayMoney);
               tLJSPayPersonSchema.setSumDuePayMoney(tPayMoney);
               //营改增 add zhangyingfeng 2016-07-14
               //价税分离 计算器
               TaxCalculator.calBySchema(tLJSPayPersonSchema);
               //end zhangyingfeng 2016-07-14
               mMap.put(tLJSPayPersonSchema,"DELETE&INSERT");

              }

              mMap.put(mLJSGetEndorseSet, "DELETE&INSERT");
              mMap.put(mLPInsureAccFeeTraceSet, "DELETE&INSERT");
              mMap.put(updLPInsureAccClassFeeSet, "DELETE&INSERT");
              mMap.put(updLPInsureAccFeeSet, "DELETE&INSERT");

              // 修改“个险保全项目表”相应信息
              mLPEdorItemSchema.setEdorState("3");
              mLPEdorItemSchema.setGetMoney(tPayMoney);
              mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
              mLPEdorItemSchema.setModifyDate(strCurrentDate);
              mLPEdorItemSchema.setModifyTime(strCurrentTime);
              mLPEdorItemSchema.setStandbyFlag1(BqNameFun.getRound(mWorkNoteFee));
              mLPEdorItemSchema.setStandbyFlag3(remark);
              mMap.put(mLPEdorItemSchema, "UPDATE");
              mResult.clear();
              mResult.add(mMap);
              mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
              mResult.add(mBqCalBase);
          }
          return true;
      }

   private boolean makeFee(LCInsureAccSchema tLCInsureAccSchema,String tPayPlanCode){
     String mPolNo = tLCInsureAccSchema.getPolNo();
     String mInsuAccNo = tLCInsureAccSchema.getInsuAccNo();
     //查询保险账户管理费表
     LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
     tLCInsureAccFeeDB.setPolNo(mPolNo);
     tLCInsureAccFeeDB.setInsuAccNo(mInsuAccNo);
     LCInsureAccFeeSet tLCInsureAccFeeSet = tLCInsureAccFeeDB.query();
     if (tLCInsureAccFeeDB.mErrors.needDealError()) {
         CError.buildErr(this, "账户管理费查询失败!");
         return false;
     }
     if (tLCInsureAccFeeSet == null || tLCInsureAccFeeSet.size() < 1) {
         CError.buildErr(this, "账户管理费查询失败!");
         return false;
     }
     LCInsureAccFeeSchema tLCInsureAccFeeSchema = tLCInsureAccFeeSet.get(1);

     //查询这个险种的所有管理费
     String tFeeCode = "";

     LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
     tLMRiskFeeDB.setInsuAccNo(mInsuAccNo);
     tLMRiskFeeDB.setFeeKind("03"); //03-个单管理费
     tLMRiskFeeDB.setFeeTakePlace("01"); //01－缴费时扣除
     tLMRiskFeeDB.setPayPlanCode(tPayPlanCode);//这里就是不定期缴费责任
     LMRiskFeeSet tLMRiskFeeSet = tLMRiskFeeDB.query();
     if (tLMRiskFeeDB.mErrors.needDealError()) {
         CError.buildErr(this, "账户管理费查询失败!LMRiskFee.InsuAccNo="+mInsuAccNo);
         return false;
     }
     if (tLMRiskFeeSet != null && tLMRiskFeeSet.size() > 0) {
         for (int k = 1; k <= tLMRiskFeeSet.size(); k++){ //循环计算每条管理费
             double dRiskFee = 0.0;
             tFeeCode = tLMRiskFeeSet.get(k).getFeeCode();

             //获得不同管理费
             LCInsureAccClassFeeSchema pLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();

             LCInsureAccClassFeeDB pLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
             pLCInsureAccClassFeeDB.setPolNo(mPolNo);
             pLCInsureAccClassFeeDB.setInsuAccNo(mInsuAccNo);
             pLCInsureAccClassFeeDB.setFeeCode(tFeeCode);
             pLCInsureAccClassFeeDB.setPayPlanCode(tPayPlanCode);
             pLCInsureAccClassFeeDB.setAccAscription("1");
             pLCInsureAccClassFeeDB.setOtherNo(mPolNo);
             pLCInsureAccClassFeeSchema = pLCInsureAccClassFeeDB.query().get(1);

             if (pLCInsureAccClassFeeSchema == null) {
//                 continue;
                 pLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
                 mReflections.transFields(pLCInsureAccClassFeeSchema,tLCInsureAccSchema);
                 pLCInsureAccClassFeeSchema.setFeeCode(tFeeCode);
                 pLCInsureAccClassFeeSchema.setPayPlanCode(tPayPlanCode);
                 pLCInsureAccClassFeeSchema.setAccAscription("1");
                 pLCInsureAccClassFeeSchema.setOtherNo(mPolNo);
                 pLCInsureAccClassFeeSchema.setOtherType("1");
                 pLCInsureAccClassFeeSchema.setOperator(mGlobalInput.Operator);
                 pLCInsureAccClassFeeSchema.setMakeDate(strCurrentDate);
                 pLCInsureAccClassFeeSchema.setMakeTime(strCurrentTime);
                 pLCInsureAccClassFeeSchema.setModifyDate(strCurrentDate);
                 pLCInsureAccClassFeeSchema.setModifyTime(strCurrentTime);
                 pLCInsureAccClassFeeSchema.setFee(0);
                 pLCInsureAccClassFeeSchema.setFeeRate(0);
                 pLCInsureAccClassFeeSchema.setFeeUnit(0);
             }

             //计算首次保单管理费
             dRiskFee = calRiskFee(tPayMoney, tLMRiskFeeSet.get(k));
             if (dRiskFee == -1) {
                 return false;
             }

             //创建保险帐户管理费轨迹记表
             if(!createFeeTrace(pLCInsureAccClassFeeSchema, dRiskFee, "GL",tLMRiskFeeSet.get(k).getFeeCode())){
                 CError.buildErr(this, "创建保险帐户管理费轨迹记表失败!");
                 return false;
             }

             //更新子帐户帐户管理费表
             LPInsureAccClassFeeSchema rLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
             ref.transFields(rLPInsureAccClassFeeSchema, pLCInsureAccClassFeeSchema);
             rLPInsureAccClassFeeSchema.setFee(pLCInsureAccClassFeeSchema.getFee()+ dRiskFee);
             rLPInsureAccClassFeeSchema.setBalaDate(tPayDate);
             rLPInsureAccClassFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
             rLPInsureAccClassFeeSchema.setEdorType(mLPEdorItemSchema.getEdorType());
             rLPInsureAccClassFeeSchema.setBalaTime(strCurrentTime);
             rLPInsureAccClassFeeSchema.setModifyDate(strCurrentDate);
             rLPInsureAccClassFeeSchema.setModifyTime(strCurrentTime);
             updLPInsureAccClassFeeSet.add(rLPInsureAccClassFeeSchema);
             mWorkNoteFee += dRiskFee;
         }
      }
      //更新帐户管理费表
      LPInsureAccFeeSchema rLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
      ref.transFields(rLPInsureAccFeeSchema, tLCInsureAccFeeSchema);
      rLPInsureAccFeeSchema.setFee(tLCInsureAccFeeSchema.getFee()+ mWorkNoteFee);
      rLPInsureAccFeeSchema.setBalaDate(tPayDate);
      rLPInsureAccFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
      rLPInsureAccFeeSchema.setEdorType(mLPEdorItemSchema.getEdorType());
      rLPInsureAccFeeSchema.setBalaTime(strCurrentTime);
      rLPInsureAccFeeSchema.setModifyDate(strCurrentDate);
      rLPInsureAccFeeSchema.setModifyTime(strCurrentTime);
      updLPInsureAccFeeSet.add(rLPInsureAccFeeSchema);
      return true;
   }

   /**
    * 创建结算轨迹记录
    * @param pLCInsureAccClassFeeSchema
    * @param dMoney      结算金额
    * @param sMoneyType  金额类型
    * @param sFeeCode    管理费编码
    * @return boolean
    */
   private boolean createFeeTrace(
                   LCInsureAccClassFeeSchema pLCInsureAccClassFeeSchema,
                   double dMoney, String sMoneyType, String sFeeCode) {

           //创建帐户轨迹记录
           LPInsureAccFeeTraceSchema tLPInsureAccFeeTraceSchema = new LPInsureAccFeeTraceSchema();
           ref.transFields(tLPInsureAccFeeTraceSchema, pLCInsureAccClassFeeSchema);
           String tLimit = PubFun.getNoLimit(pLCInsureAccClassFeeSchema
                           .getManageCom());
           String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

           tLPInsureAccFeeTraceSchema.setSerialNo(serNo);
           tLPInsureAccFeeTraceSchema.setState("1");
           tLPInsureAccFeeTraceSchema.setMoneyType(sMoneyType);
           tLPInsureAccFeeTraceSchema.setFee(dMoney);
           tLPInsureAccFeeTraceSchema.setPayDate(tPayDate);
           tLPInsureAccFeeTraceSchema.setFeeCode(sFeeCode);
           tLPInsureAccFeeTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
           tLPInsureAccFeeTraceSchema.setEdorType(mLPEdorItemSchema.getEdorType());
           tLPInsureAccFeeTraceSchema.setMakeDate(strCurrentDate);
           tLPInsureAccFeeTraceSchema.setMakeTime(strCurrentTime);
           tLPInsureAccFeeTraceSchema.setModifyDate(strCurrentDate);
           tLPInsureAccFeeTraceSchema.setModifyTime(strCurrentTime);
           tLPInsureAccFeeTraceSchema.setOperator(mGlobalInput.Operator);
           mLPInsureAccFeeTraceSet.add(tLPInsureAccFeeTraceSchema);
   		//add zhangyingfeng 2016-08-03
   		//营改增 价税分离计算器
   		TaxCalculator.calBySchemaSet(mLPInsureAccFeeTraceSet);
   		//end zhangyingfeng 2016-08-03
           return true;
	}

   /**
    * 计算管理费
    * @param dInsuAccBala 帐户价值
    * @param pLMRiskFeeSchema  管理费描述信息
    * @return double
    */
   private double calRiskFee(double PayMoney,LMRiskFeeSchema pLMRiskFeeSchema) {
           double dRiskFee = 0.0;
           if (pLMRiskFeeSchema.getFeeCalModeType().equals("0")) //0-直接取值
           {
                   if (pLMRiskFeeSchema.getFeeCalMode().equals("01")) //固定值内扣
                   {
                           dRiskFee = pLMRiskFeeSchema.getFeeValue();
                   } else if (pLMRiskFeeSchema.getFeeCalMode().equals("02")) //固定比例内扣
                   {
                           dRiskFee = PayMoney * pLMRiskFeeSchema.getFeeValue();
                   } else {
                           dRiskFee = pLMRiskFeeSchema.getFeeValue(); //默认情况
                   }
           } else if (pLMRiskFeeSchema.getFeeCalModeType().equals("1")) //1-SQL算法描述
           {
                   //准备计算要素
                   Calculator tCalculator = new Calculator();
                   tCalculator.setCalCode(pLMRiskFeeSchema.getFeeCalCode());

                   //本次追加的保费总和
                   tCalculator.addBasicFactor("SumPrem", String.valueOf(PayMoney));

                   String sCalResultValue = tCalculator.calculate();
                   if (tCalculator.mErrors.needDealError()) {
                           CError.buildErr(this, "管理费计算失败!");
                           return -1;
                   }

                   try{
                       dRiskFee = Double.parseDouble(sCalResultValue);
                   }catch(Exception e){
                       CError.buildErr(this, "管理费计算结果错误!" + "错误结果：" + sCalResultValue);
                       return -1;
                   }
           }

           return dRiskFee;
	}

    /**
     * 创建批改补退费记录
     * @param dBJJE
     * @param sBQFeeType
     * @param sSubFeeOperationType
     * @return boolean
     */
    private boolean createLJSGetEndorse(double dMoneyValue, String sBQFeeType, String sSubFeeOperationType,
			LCPolSchema aLCPolSchema,String tPayPlanCode,String tDutyCode){
		if (Math.abs(dMoneyValue - 0) < 0.01){
                    return true;
		}

		String sFeeType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(), sBQFeeType, aLCPolSchema.getPolNo());

		BqCalBL tBqCalBL = new BqCalBL();
		LJSGetEndorseSchema tLJSGetEndorseSchema;

		tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema = tBqCalBL.initLJSGetEndorse(mLPEdorItemSchema, aLCPolSchema, null, sSubFeeOperationType,
				sFeeType, Arith.round(dMoneyValue, 2), mGlobalInput);
		// 用子业务类型区分同一保全项目下不同的费用
		if (tLJSGetEndorseSchema == null){
                    mErrors.copyAllErrors(tBqCalBL.mErrors);
                    return false;
		}
                tLJSGetEndorseSchema.setPayPlanCode(tPayPlanCode);
                tLJSGetEndorseSchema.setDutyCode(tDutyCode);
                //营改增 add zhangyingfeng 2016-07-14
                //价税分离 计算器
                TaxCalculator.calBySchema(tLJSGetEndorseSchema);
                //end zhangyingfeng 2016-07-14     
		mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		return true;
    }
    public CErrors getErrors(){
        return this.mErrors;
    }
}
