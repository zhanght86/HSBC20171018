package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import java.util.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 团单催付处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author TJJ
 * @version 1.0
 */
public class PayPlanBL
{
private static Logger logger = Logger.getLogger(PayPlanBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 往后面传输数据的容器 */
    private VData mResult;

    /** 数据操作字符串 */
    private String mOperate;

    /**控制信息传输类*/
    private TransferData mTransferData = new TransferData();

    /**参数描述*/
    private String mTimeStart;
    private String mTimeEnd;
    private Date mTimeEndDate;
    private String mSerialNo;

    /**给付至日期*/
    private Date mGetToDate;

    /**上次给付至日期*/
    private Date mLastGettoDate;

    /**销户标志*/
    private String mCancelFlag;
    private String mCalFlag;

    /** 业务处理相关变量 */
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private String mOperator;
    private String mManageCom;
    private LCPolSchema inLCPolSchema = new LCPolSchema();
    private LCGetSchema inLCGetSchema = new LCGetSchema();
    private LCGetSchema mLCGetSchema = new LCGetSchema();
    private LCGetSet mLCGetSet = new LCGetSet();
    private LJSGetSet mLJSGetSet = new LJSGetSet();
    private LJSGetSet mLJSTotalGetSet = new LJSGetSet();
    private LJSGetDrawSet mLJSGetDrawSet = new LJSGetDrawSet();
    private LCPolSchema mLCPolSchema = new LCPolSchema();
    private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
    private LMDutyGetAliveSchema mLMDutyGetAliveSchema = new LMDutyGetAliveSchema();
    private LMDutyGetSchema mLMDutyGetSchema = new LMDutyGetSchema();
    private LMDutySchema mLMDutySchema = new LMDutySchema();
    private String mDutyCode = "";
	private FDate tFDate = new FDate();
    public PayPlanBL()
    {
    }



	// 添加构造函数，单独处理
	public PayPlanBL(LCGetSet valLCGetSet, String valSerialNo) {
		mLCGetSet.set(valLCGetSet);
		mSerialNo = valSerialNo;
	}
	/**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("==> Begin to PayPlanBL");

        //将操作数据拷贝到本类中
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }

        logger.debug("==> End InputData");

        //进行业务处理
        if (!dealData(cInputData))
        {
            // @@错误处理
            CError.buildErr(this, "数据处理失败PayPlanBL-->dealData!");

            return false;
        }

        logger.debug("==> End DealDate");

        //准备往后台的数据
        if (!prepareData())
        {
            return false;
        }

        logger.debug("==> End PrepareData");

        logger.debug("Start PayPlan BLS Submit...");


//        tPayPlanBLS.submitData(mInputData, cOperate);
//
//        logger.debug("End PayPlan BLS Submit...");
//
//        //如果有需要处理的错误，则返回
//        if (tPayPlanBLS.mErrors.needDealError())
//        {
//            // @@错误处理
//            CError.buildErr(this, "数据提交失败!", tPayPlanBLS.mErrors);
//
//            return false;
//        }

        mInputData = null;

        return true;
    }

    /**
    * 初始化查询条件
    * @param Schema
    * @return
    */
    private String initCondGet(SQLwithBindVariables sqlbv)
    {
        String aSql = "select * from LCGet where 1 = 1"
        			+ "and exists (select 'X' from LCPol where PolNo=LCGet.PolNo and appflag = '1') ";

        if ((inLCGetSchema.getManageCom() == null) ||
                inLCGetSchema.getManageCom().trim().equals(""))
        {
            aSql = aSql + " and ManageCom like concat('?mManageCom?','%')";
            sqlbv.put("mManageCom", mManageCom);
        }
        else
        {
            aSql = aSql + " and ManageCom like concat('?ManageCom?','%')";
            sqlbv.put("ManageCom", inLCGetSchema.getManageCom());
        }

        if ((inLCGetSchema.getGrpContNo() != null) &&
                !inLCGetSchema.getGrpContNo().trim().equals(""))
        {
            aSql = aSql + " and GrpContNo= '?GrpContNo?'";
            sqlbv.put("GrpContNo", inLCGetSchema.getGrpContNo());
        }

        if ((inLCGetSchema.getContNo() != null) &&
                !inLCGetSchema.getContNo().trim().equals(""))
        {
            aSql = aSql + " and ContNo= '?ContNo?'";
            sqlbv.put("ContNo",inLCGetSchema.getContNo());
        }
		if ((inLCGetSchema.getPolNo() != null)
				&& !inLCGetSchema.getPolNo().trim().equals("")) {
			aSql = aSql + " and PolNo= '?PolNo?'";
			sqlbv.put("PolNo", inLCGetSchema.getPolNo());
		}
        if ((inLCGetSchema.getInsuredNo() != null) &&
                !inLCGetSchema.getInsuredNo().trim().equals(""))
        {
            aSql = aSql + " and InsuredNo= '?InsuredNo?'";
            sqlbv.put("InsuredNo", inLCGetSchema.getInsuredNo());
        }

        if (!((mTimeEnd == null) && mTimeEnd.trim().equals("")))
        {
            aSql = aSql + " and GetToDate<='?mTimeEnd?' and Gettodate>='?mTimeStart?' and GetEndDate>='?mTimeStart?'";
            sqlbv.put("mTimeEnd", mTimeEnd);
            sqlbv.put("mTimeStart", mTimeStart);
        }

        aSql = aSql +
               " and LiveGetType='0' and canget = '0' and  UrgeGetFlag = 'Y' and GrpContNo!='00000000000000000000' and ((getintv =0 and summoney=0) or getintv>0)";
        logger.debug("==> 查询LCGet表SQL：" + aSql);

        return aSql;
    }

    /**
    * 根据前面的输入数据，进行BL逻辑处理
    * 如果在处理过程中出错，则返回false,否则返回true
    */
    public boolean dealData(VData tVDate)
    {

        String condSql;
        LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
        LJSGetSet tLJSGetSet = new LJSGetSet();

        //生成批次号
        mSerialNo = PubFun1.CreateMaxNo("SERIALNO",
                                        PubFun.getNoLimit(mManageCom));
        logger.debug("==> serialNo:" + mSerialNo);

        //初始化催付查询条件
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        condSql = initCondGet(sqlbv);
        sqlbv.sql(condSql);

        
        LCGetDB tLCGetDB = new LCGetDB();
        mLCGetSet = tLCGetDB.executeQuery(sqlbv);
        logger.debug("有" + mLCGetSet.size() + "条符合条件的给付数据!");

		if (mLCGetSet == null || mLCGetSet.size() < 1)
		{
			CError.buildErr(this, "无符合条件的给付记录!");
			
			return false;
		}
		List ContNoList = new ArrayList();
        for (int j = 1; j <= mLCGetSet.size(); j++)
        {
        	
        	//本次循环以保单ContNo为单位处理
        	
            mLCGetSchema.setSchema(mLCGetSet.get(j));
            String thisContNo = mLCGetSchema.getContNo();
            mManageCom = mLCGetSchema.getManageCom();
            if(ContNoList.contains(thisContNo)){
            	//跳过
            	continue;
            }else{
            	ContNoList.add(thisContNo);
            }
            //*1.处理逻辑第一步,首先查出每个Get项有几期未领
            SQLwithBindVariables sbv=new SQLwithBindVariables();
//            String contSql = condSql+ " and ContNo='" + thisContNo
//			+ "' order by ContNo,PolNo,getdutycode,dutycode";
            String contSql = initCondGet(sbv)+ " and ContNo='?thisContNo?' order by ContNo,PolNo,getdutycode,dutycode";
            sbv.sql(contSql);
            sbv.put("thisContNo", thisContNo);
			LCGetSet tempLCGetSet = new LCGetSet();
			LCGetDB tempLCGetDB = new LCGetDB();
			tempLCGetSet = tempLCGetDB.executeQuery(sbv);
			logger.debug("tempLCGetSet.size=" + tempLCGetSet.size());
			//
			LCGetSchema tempLCGetSchema = null;
			mLJSGetDrawSet.clear();//存放个人合同所有的GetDraw数据
			mLJSGetSet.clear();//存放个人合同所有的Get数据
			for (int k = 1; k <= tempLCGetSet.size(); k++) {
				tempLCGetSchema = tempLCGetSet.get(k);
				//校验各种限制条件同时准备数据
				if (!this.checkDataToGet(tempLCGetSchema))
				{
					CError.buildErr(this, "保单" + tempLCGetSchema.getContNo() + "校验失败!");
					
					continue;
				}
				//生成给付子表的信息,若有多期给付责任则生成相应多条催付记录
 				//*2.每个Get项的每期生成一条GetDraw,同一期的GetDraw使用同一通知书号
				tLJSGetDrawSet = getLJSGetDraw(tempLCGetSchema);
				
				if (tempLCGetSchema.getGetIntv() != 0 && tLJSGetDrawSet.size() < 1)
				{
					CError.buildErr(this,
							"保单" + tempLCGetSchema.getContNo() + "生成给付子表的信息失败!");
					
					continue;
				}
				if(tLJSGetDrawSet.size()>0){
					mLJSGetDrawSet.add(tLJSGetDrawSet);
				}
			}
			//*3.统计生成相应应付总表信息同一期的GetDraw生成一条ljsget
			tLJSGetSet = getLJSGet(mLJSGetDrawSet);
			logger.debug("保单" + mLCGetSet.get(j).getContNo() + "下的给付责任成功催付" + tLJSGetSet.size() + "条信息!");
			
			mLJSGetSet.add(tLJSGetSet);
			
	        MMap tMap = new MMap();
	        tMap.put(mLJSGetDrawSet, "DELETE&INSERT");
	        tMap.put(mLJSGetSet, "DELETE&INSERT");
	        VData tVData = new VData();
	        tVData.add(tMap);
	        //数据提交
	        PubSubmit tSubmit = new PubSubmit();
	        if (!tSubmit.submitData(tVData, "")) {
	        	CError.buildErr(this, "保单" + mLCGetSet.get(j).getContNo() + "下的给付责任保存失败原因是:" + tSubmit.mErrors.getFirstError());
	        	continue;
	        }
	        mLJSTotalGetSet.add(mLJSGetSet);

        }

        return true;
    }

    /**
    * 从输入数据中得到所有对象
    *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
    */
    private boolean getInputData(VData cInputData)
    {
        //全局变量
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",
                                                                              0));
        inLCPolSchema.setSchema((LCPolSchema) cInputData.getObjectByObjectName("LCPolSchema",
                                                                               0));
        inLCGetSchema.setSchema((LCGetSchema) cInputData.getObjectByObjectName("LCGetSchema",
                                                                               0));
        mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData",
                                                                        0);
        mTimeEnd = (String) mTransferData.getValueByName("timeEnd");
        mTimeStart = (String) mTransferData.getValueByName("timeStart");

        //如果没传入催付开始日期，则默认为MS开业日
        if ((mTimeStart == null) || mTimeStart.equals(""))
        {
            mTimeStart = "2003-01-01";
        }

        if ((mTimeEnd == null) || mTimeEnd.equals(""))
        {
            CError.buildErr(this, "催付至日期不能为空!");

            return false;
        }

        //规范字符串类型的日期，下面是把"2005-6-1"转为"2005-06-01",便与后面日期的比较
        //也可以选择都使用日期类型比较，而不是字符串类型
        FDate tFDate = new FDate();
        mTimeEndDate = tFDate.getDate(mTimeEnd);
        mTimeEnd = tFDate.getString(mTimeEndDate);
        logger.debug("催付至日期为：" + mTimeEnd);

        //记录操作员
        mOperator = mGlobalInput.Operator;
        mManageCom = mGlobalInput.ManageCom;

        return true;
    }

    /**
    * 从输入数据中得到所有对象
    *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
    */
    public boolean getEdorData(VData cInputData)
    {
        mLCGetSchema.setSchema((LCGetSchema) cInputData.getObjectByObjectName("LCGetSchema",
                                                                              0));
        mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData",
                                                                        0);
        mTimeEnd = (String) mTransferData.getValueByName("timeEnd");

        return true;
    }

    /**
    * 准备往后层输出所需要的数据
    * 输出：如果准备数据时发生错误则返回false,否则返回true
    */
    private boolean prepareData()
    {
        mInputData = new VData();

        try
        {
            mInputData.add(this.mLJSGetDrawSet);
            mInputData.add(this.mLJSGetSet);
            mInputData.add(this.mGlobalInput);

            if ((mLJSTotalGetSet != null) && (mLJSTotalGetSet.size() > 0))
            {
                mResult = new VData();
                mResult.addElement(mSerialNo);
                mResult.addElement(String.valueOf(mLJSTotalGetSet.size()));
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LFGetPayBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);

            return false;
        }

        return true;
    }

    //领取时给付校验
    private boolean checkDataToGet(LCGetSchema aLCGetSchema)
    {
        FDate tFDate = new FDate();

        logger.debug("开始处理保单" + aLCGetSchema.getContNo() + "下的" +
                           aLCGetSchema.getGetDutyCode() + "给付责任!");

				LCGetDB tLCGetDB = new LCGetDB();
				tLCGetDB.setSchema(aLCGetSchema);
				if(!tLCGetDB.getInfo())
				{
					  CError.buildErr(this,"获取给付信息失败!");
						return false;
				}

				if(!tLCGetDB.getGettoDate().equals(aLCGetSchema.getGettoDate()))
				{
					  CError.buildErr(this,"领取项有变化，请稍后在催收!");
						return false;
				}

				aLCGetSchema = tLCGetDB.getSchema();

        //判断该给付责任是否已经催付
        String strSQL = "select * from LJSGetDraw where polno='?polno?' and dutycode='?dutycode?' and getdutycode='?getdutycode?' and getdutykind='?getdutykind?' order by curgettodate desc";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(strSQL);
        sqlbv.put("polno", aLCGetSchema.getPolNo());
        sqlbv.put("dutycode", aLCGetSchema.getDutyCode());
        sqlbv.put("getdutycode", aLCGetSchema.getGetDutyCode());
        sqlbv.put("getdutykind", aLCGetSchema.getGetDutyKind());
        LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
        LJSGetDrawSet tLJSGetDrawSet = tLJSGetDrawDB.executeQuery(sqlbv);

        if (tLJSGetDrawSet.size() > 0)
        {
            //取得最后一次催付的GetToDate
            this.setGetToDate(tFDate.getDate(tLJSGetDrawSet.get(1)
                                                           .getCurGetToDate()));

            if (this.getGetToDate().after(mTimeEndDate))
            {
                //催至日期超过催付日期
                CError.buildErr(this,
                                "保单(" + aLCGetSchema.getContNo() + ")催收数据已经存在!");

                return false;
            }

            //判断是否是趸领，如果是则返回错误 add by ck
            if (aLCGetSchema.getGetIntv() == 0)
            {
                CError.buildErr(this,
                                "保单(" + aLCGetSchema.getContNo() + ")催收数据已经存在!");

                return false;
            }
        }
        else
        {
            this.setGetToDate(tFDate.getDate(aLCGetSchema.getGettoDate()));
        }

        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(aLCGetSchema.getPolNo());

        if (!tLCPolDB.getInfo())
        {
            CError.buildErr(this, "保单(" + aLCGetSchema.getContNo() + ")查询失败!");

            return false;
        }

        mLCPolSchema.setSchema(tLCPolDB.getSchema());

        if (!mLCPolSchema.getAppFlag().trim().equals("1"))
        {
            CError.buildErr(this, "保单(" + aLCGetSchema.getContNo() + ")没有签单!");

            return false;
        }

//        if (!mLCPolSchema.getPolState().trim().substring(0, 2).equals("00"))
//        {
//            CError.buildErr(this, "保单(" + aLCGetSchema.getContNo() +
//                            ")不在有效状态内!");
//
//            return false;
//        }

        LCInsuredDB tLCInsuredDB = new LCInsuredDB();
        tLCInsuredDB.setContNo(mLCPolSchema.getContNo());
        tLCInsuredDB.setInsuredNo(mLCPolSchema.getInsuredNo());

        if (!tLCInsuredDB.getInfo())
        {
            CError.buildErr(this,
                            "保单(" + mLCPolSchema.getContNo() + ")主被保人信息不足无法计算!");

            return false;
        }

        mLCInsuredSchema.setSchema(tLCInsuredDB.getSchema());

        //把非标准的给付责任转换为标准6位给付责任
        if (aLCGetSchema.getDutyCode().trim().length() > 6)
        {
            mDutyCode = aLCGetSchema.getDutyCode().trim().substring(0, 6);
        }
        else
        {
            mDutyCode = aLCGetSchema.getDutyCode();
        }

        LMDutyDB tLMDutyDB = new LMDutyDB();
        tLMDutyDB.setDutyCode(mDutyCode);

        if (!tLMDutyDB.getInfo())
        {
            CError.buildErr(this, "查询责任表失败!");

            return false;
        }

        mLMDutySchema.setSchema(tLMDutyDB.getSchema());

        //取得计算给付信息的方式
        mCalFlag = mLMDutySchema.getCalMode();

        //判断是否为生存给付责任,并准备给付责任数据
        LMDutyGetDB tLMDutyGetDB = new LMDutyGetDB();
        tLMDutyGetDB.setGetDutyCode(aLCGetSchema.getGetDutyCode());

        if (!tLMDutyGetDB.getInfo())
        {
            // @@错误处理
            CError.buildErr(this, "查询给付责任描述数据失败!");

            return false;
        }

        mLMDutyGetSchema.setSchema(tLMDutyGetDB.getSchema());

        //检查生存给付描述表是否存在,并准备给付责任数据。
        LMDutyGetAliveDB tLMDutyGetAliveDB = new LMDutyGetAliveDB();
        tLMDutyGetAliveDB.setGetDutyCode(aLCGetSchema.getGetDutyCode());
        tLMDutyGetAliveDB.setGetDutyKind(aLCGetSchema.getGetDutyKind());

        if (!tLMDutyGetAliveDB.getInfo())
        {
            // @@错误处理
            CError.buildErr(this, "无生存给付描述数据");

            return false;
        }

        mLMDutyGetAliveSchema.setSchema(tLMDutyGetAliveDB.getSchema());

        //校验责任领取条件
        if (mLMDutyGetAliveSchema.getGetCond().equals("0"))
        {
            if (mLCPolSchema.getPayEndDate().compareTo(aLCGetSchema.getGetStartDate()) < 0)
            {
                CError.buildErr(this, "该责任不满足领取条件(交费不足)!");

                return false;
            }
        }

        //检查给付数据是否需要申请
        if ((mLMDutyGetSchema.getCanGet() != null) &&
                mLMDutyGetSchema.getCanGet().equals("1"))
        {
            //必须申请后才能催收
            if ((aLCGetSchema.getCanGet() == null) ||
                    !aLCGetSchema.getCanGet().equals("0"))
            {
                //没有进行领取申请
                CError.buildErr(this,
                                "保单" + aLCGetSchema.getPolNo() + "没有进行领取申请");

                return false;
            }
        }

        return true;
    }

    //取得生存领取子表记录
    public LJSGetDrawSet getLJSGetDraw(LCGetSchema aLCGetSchema)
    {
        LJSGetDrawSet rLJSGetDrawSet = new LJSGetDrawSet();

        double aGetMoney = 0;
        BqCalBase aBqCalBase = new BqCalBase();
        FDate tFDate = new FDate();

        if (aLCGetSchema.getGetIntv() == 0)
        {
            //趸领生存给付计算
            if (aLCGetSchema.getSumMoney() <= 0)
            {
                if ((mLMDutyGetAliveSchema.getNeedReCompute() == null) ||
                        !mLMDutyGetAliveSchema.getNeedReCompute().equals("1"))
                {
                    //不需要重新计算，直接去标准给付金额
                    aGetMoney = aLCGetSchema.getStandMoney();
                }
                else
                {
                    //该给付责任在领取时需要重新计算,现在只有增额交清的红利在给付时需要重新计算
                    //查找计算 CalCode
                    String mCalCode = null;

                    if (StrTool.cTrim(mCalFlag).equals("P"))
                    {
                        mCalCode = mLMDutyGetAliveSchema.getCalCode(); // 保费算保额
                    }

                    if (StrTool.cTrim(mCalFlag).equals("G"))
                    {
                        mCalCode = mLMDutyGetAliveSchema.getCnterCalCode(); // 保额算保费
                    }

                    if (StrTool.cTrim(mCalFlag).equals("O"))
                    {
                        mCalCode = mLMDutyGetAliveSchema.getOthCalCode(); // 其他算保费
                    }

                    if (StrTool.cTrim(mCalFlag).equals("A"))
                    {
                        mCalCode = mLMDutyGetAliveSchema.getCnterCalCode(); // 保费、保额互算
                    }

                    if (StrTool.cTrim(mCalFlag).equals("I"))
                    {
                        mCalCode = mLMDutyGetAliveSchema.getCnterCalCode(); // 录入保费保额
                    }

                    if ((mCalCode == null) || mCalCode.equals(""))
                    {
                        logger.debug("保单" + aLCGetSchema.getPolNo() +
                                           "没有重算公式");

                        //return 0;
                    }

                    //重新计算给付金额
                    aBqCalBase = new BqCalBase();
                    aBqCalBase = initCalBase(aLCGetSchema);
                    logger.debug("aCalCode :" + mCalCode);

                    BqCalBL tBqCalBL = new BqCalBL(aBqCalBase, mCalCode, "");
                    aGetMoney = tBqCalBL.calGetDraw();
                }
            }

            this.setGetToDate(tFDate.getDate(aLCGetSchema.getGetStartDate()));
            mLastGettoDate = this.getGetToDate(); //一定要有
            //趸领大于0的才生成催付记录
            if(aGetMoney >0 ){
            	LJSGetDrawSchema tLJSGetDrawSchema = initLJSGetDraw(aGetMoney,aLCGetSchema);
            	rLJSGetDrawSet.add(tLJSGetDrawSchema);
            }
        }
        else
        {
            //期领责任给付计算
            LCGetSchema tLCGetSchema = new LCGetSchema();
            tLCGetSchema.setSchema(aLCGetSchema);

            while (!this.getGetToDate().after(mTimeEndDate))
            {
                logger.debug("---oridate:" +
                                   tFDate.getString(this.getGetToDate()));
                logger.debug("---Todate:" +
                                   tFDate.getString(mTimeEndDate));
                aBqCalBase = new BqCalBase();
                aBqCalBase = initCalBase(tLCGetSchema);
                aGetMoney = 0;

                if (mLMDutyGetAliveSchema.getMaxGetCount() == 0)
                {
                    if (tLCGetSchema.getGettoDate().compareTo(tLCGetSchema.getGetEndDate()) > 0)
                    {
                        this.setCancelFlag("1");

                        break;
                    }
                }
                else
                {
                    //无条件给付最大次数
                    if (mLMDutyGetAliveSchema.getMaxGetCountType().equals("0") &&
                            (aBqCalBase.getGetTimes().compareTo(String.valueOf(mLMDutyGetAliveSchema.getMaxGetCount())) > 0))
                    {
                        this.setCancelFlag("1");

                        break;
                    }

                    //无条件给付最大年龄
                    else if (mLMDutyGetAliveSchema.getMaxGetCountType().equals("1") &&
                                 (aBqCalBase.getGetAge().compareTo(String.valueOf(mLMDutyGetAliveSchema.getMaxGetCount())) > 0))
                    {
                        this.setCancelFlag("1");

                        break;
                    }

                    //被保人死亡标志给付最大次数
                    else if (mLCPolSchema.getDeadFlag().equals("1") &&
                                 mLMDutyGetAliveSchema.getMaxGetCountType()
                                                          .equals("2") &&
                                 (aBqCalBase.getGetTimes().compareTo(String.valueOf(mLMDutyGetAliveSchema.getMaxGetCount())) > 0))
                    {
                        this.setCancelFlag("1");

                        break;
                    }

                    //被保人死亡标志给付最大年龄
                    else if (mLCPolSchema.getDeadFlag().equals("1") &&
                                 mLMDutyGetAliveSchema.getMaxGetCountType()
                                                          .equals("3") &&
                                 (aBqCalBase.getGetAge().compareTo(String.valueOf(mLMDutyGetAliveSchema.getMaxGetCount())) > 0))
                    {
                        this.setCancelFlag("1");

                        break;
                    }

                    //投保人死亡标志给付最大次数
                    else if (mLCPolSchema.getDeadFlag().equals("2") &&
                                 mLMDutyGetAliveSchema.getMaxGetCountType()
                                                          .equals("4") &&
                                 (aBqCalBase.getGetTimes().compareTo(String.valueOf(mLMDutyGetAliveSchema.getMaxGetCount())) > 0))
                    {
                        this.setCancelFlag("1");

                        break;
                    }

                    //投保人死亡标志给付最大年龄
                    else if (mLCPolSchema.getDeadFlag().equals("2") &&
                                 mLMDutyGetAliveSchema.getMaxGetCountType()
                                                          .equals("5") &&
                                 (aBqCalBase.getGetAge().compareTo(String.valueOf(mLMDutyGetAliveSchema.getMaxGetCount())) > 0))
                    {
                        this.setCancelFlag("1");

                        break;
                    }
                    else
                    {
                        logger.debug("------------------no descriptions----------");
                    }
                }

                //计算应领金额
                if ((mLMDutyGetAliveSchema.getNeedReCompute() == null) ||
                        !mLMDutyGetAliveSchema.getNeedReCompute().equals("1"))
                {
                    //不需要重新计算
                    if (mLMDutyGetAliveSchema.getAddFlag().equals("N"))
                    {
                        //非递增
                        aGetMoney = aGetMoney + tLCGetSchema.getStandMoney();
                    }
                    else
                    {
                        aGetMoney = aGetMoney +
                                    calAddGetMoney(aBqCalBase, tLCGetSchema);
                    }
                }
                else
                {
                    //该给付责任在领取时需要重新计算
                    //查找计算 CalCode
                    String mCalCode = null;

                    if (StrTool.cTrim(mCalFlag).equals("P"))
                    {
                        mCalCode = mLMDutyGetAliveSchema.getCalCode(); // 保费算保额
                    }

                    if (StrTool.cTrim(mCalFlag).equals("G"))
                    {
                        mCalCode = mLMDutyGetAliveSchema.getCnterCalCode(); // 保额算保费
                    }

                    if (StrTool.cTrim(mCalFlag).equals("O"))
                    {
                        mCalCode = mLMDutyGetAliveSchema.getOthCalCode(); // 其他算保费
                    }

                    if (StrTool.cTrim(mCalFlag).equals("A"))
                    {
                        mCalCode = mLMDutyGetAliveSchema.getCnterCalCode(); // 保费、保额互算
                    }

                    if (StrTool.cTrim(mCalFlag).equals("I"))
                    {
                        mCalCode = mLMDutyGetAliveSchema.getCnterCalCode(); // 录入保费保额
                    }

                    BqCalBL tBqCalBL = new BqCalBL(aBqCalBase, mCalCode, "");
                    aGetMoney = aGetMoney + tBqCalBL.calGetDraw();
                }

                //  保存上次领至日期
                mLastGettoDate = this.getGetToDate();

                //  计算应领至日期
                tLCGetSchema.setGettoDate(this.calGetToDate(tLCGetSchema));
                this.setGetToDate(tFDate.getDate(tLCGetSchema.getGettoDate()));
                logger.debug("---oridate:" + tLCGetSchema.getGettoDate());

                LJSGetDrawSchema tLJSGetDrawSchema = initLJSGetDraw(aGetMoney,
                                                                    aLCGetSchema);
                rLJSGetDrawSet.add(tLJSGetDrawSchema);
            }

            logger.debug("------end while-----");
        }

        logger.debug("-------getMoney:" + aGetMoney);

        //        return aGetMoney;
        return rLJSGetDrawSet;
    }

    //生成
    public LJSGetDrawSchema initLJSGetDraw(double money,
                                           LCGetSchema aLCGetSchema)
    {
        LJSGetDrawSchema rLJSGetDrawSchema = new LJSGetDrawSchema();

        rLJSGetDrawSchema.setGetMoney(money);
        rLJSGetDrawSchema.setDestrayFlag(this.getContCancelFlag(this.getCancelFlag()));
        String tGetNoticeNo = "";
        Date aGetDate = this.getGetToDate();
        logger.debug("==> GetDate:" + aGetDate);
        
		String CreateFlag = "1";
		// 对每个相同ContNo和GettoDate聚合，即通知书号GetNoticeNo相同
		for (int count = 1; count <= mLJSGetDrawSet.size(); count++) {
			if (aLCGetSchema.getContNo().equals(
					mLJSGetDrawSet.get(count).getContNo())
					&&aLCGetSchema.getGetDutyCode().equals(mLJSGetDrawSet.get(count).getGetDutyCode())
					&& tFDate.getString(mLastGettoDate).equals(
							mLJSGetDrawSet.get(count).getLastGettoDate())) {
				tGetNoticeNo = mLJSGetDrawSet.get(count).getGetNoticeNo();
				logger.debug("==> old GetNoticeNo is " + tGetNoticeNo);
				CreateFlag = "0";
			}
		}
		// 新生成通知书号
		if (CreateFlag.equals("1")) {
			logger.debug("==> Creat new GetNoticeNo is " + tGetNoticeNo);
			tGetNoticeNo = PubFun1.CreateMaxNo("GetNoticeNo", PubFun
					.getNoLimit(mLCPolSchema.getManageCom()));
		}



        logger.debug("==> GetNoticeNo is " + tGetNoticeNo);
        rLJSGetDrawSchema.setGetNoticeNo(tGetNoticeNo);
        rLJSGetDrawSchema.setSerialNo(mSerialNo);
        rLJSGetDrawSchema.setGetDate(mLastGettoDate);
        rLJSGetDrawSchema.setDutyCode(aLCGetSchema.getDutyCode());
        rLJSGetDrawSchema.setGetDutyCode(aLCGetSchema.getGetDutyCode());
        rLJSGetDrawSchema.setGetDutyKind(aLCGetSchema.getGetDutyKind());
        rLJSGetDrawSchema.setLastGettoDate(mLastGettoDate);
        rLJSGetDrawSchema.setFeeFinaType("YF");
        rLJSGetDrawSchema.setFeeOperationType("YF");
        rLJSGetDrawSchema.setCurGetToDate(aGetDate);

        //是否首期给付
        if (aLCGetSchema.getGetStartDate().equals(aLCGetSchema.getGettoDate()))
        {
            rLJSGetDrawSchema.setGetFirstFlag("1");
        }
        else
        {
            rLJSGetDrawSchema.setGetFirstFlag("0");
        }

        //给付渠道
        rLJSGetDrawSchema.setGetChannelFlag("1");

        rLJSGetDrawSchema.setAppntNo(mLCPolSchema.getAppntNo());
        rLJSGetDrawSchema.setPolNo(mLCPolSchema.getPolNo());
		rLJSGetDrawSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
        rLJSGetDrawSchema.setGrpName(mLCPolSchema.getAppntName());
        rLJSGetDrawSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
        rLJSGetDrawSchema.setContNo(mLCPolSchema.getContNo());
        rLJSGetDrawSchema.setPolNo(mLCPolSchema.getPolNo());
        rLJSGetDrawSchema.setPolType("1");
        rLJSGetDrawSchema.setInsuredNo(mLCPolSchema.getInsuredNo());
        rLJSGetDrawSchema.setAgentCode(mLCPolSchema.getAgentCode());
        rLJSGetDrawSchema.setAgentCom(mLCPolSchema.getAgentCom());
        rLJSGetDrawSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
        rLJSGetDrawSchema.setAgentType(mLCPolSchema.getAgentType());
        rLJSGetDrawSchema.setRiskCode(mLCPolSchema.getRiskCode());
        rLJSGetDrawSchema.setKindCode(mLCPolSchema.getKindCode());
        rLJSGetDrawSchema.setRiskVersion(mLCPolSchema.getRiskVersion());

        rLJSGetDrawSchema.setMakeDate(PubFun.getCurrentDate());
        rLJSGetDrawSchema.setMakeTime(PubFun.getCurrentTime());
        rLJSGetDrawSchema.setModifyDate(PubFun.getCurrentDate());
        rLJSGetDrawSchema.setModifyTime(PubFun.getCurrentTime());
        rLJSGetDrawSchema.setOperator(mOperator);
        rLJSGetDrawSchema.setManageCom(mManageCom);

        return rLJSGetDrawSchema;
    }

    public LJSGetSet getLJSGet(LJSGetDrawSet aLJSGetDrawSet)
    {
        LJSGetSet rLJSGetSet = new LJSGetSet();

        //总表与子表信息量一致
        List tNoticeNoList = new ArrayList();
        LJSGetSchema tLJSGetSchema = null;
        LJSGetDrawSchema tLJSGetDrawSchema = null;
        for (int k = 1; k <= aLJSGetDrawSet.size(); k++)
        {
        	//以GetNoticeNo为单位进行汇总ljsget
        	
        	
        	tLJSGetDrawSchema = aLJSGetDrawSet.get(k);
        	String tGetNoticeNo = tLJSGetDrawSchema.getGetNoticeNo();
        	double dSumGetMoney = tLJSGetDrawSchema.getGetMoney();
        	if(tNoticeNoList.contains(tGetNoticeNo)){
        		continue;
        	}else {
        		tNoticeNoList.add(tGetNoticeNo);
        	}
    		String tPolNo = tLJSGetDrawSchema.getPolNo();
    		String tDutyCode = tLJSGetDrawSchema.getDutyCode();
    		String tGetDutyCode = tLJSGetDrawSchema.getGetDutyCode();
        	LJSGetDrawSchema tempLJSGetDrawSchema = null;
        	for(int count = 1;count <= aLJSGetDrawSet.size();count++)
        	{
        		tempLJSGetDrawSchema = aLJSGetDrawSet.get(count);
        		String tempGetNoticeNo = tempLJSGetDrawSchema.getGetNoticeNo();
        		String tempPolNo = tempLJSGetDrawSchema.getPolNo();
        		String tempDutyCode = tempLJSGetDrawSchema.getDutyCode();
        		String tempGetDutyCode = tempLJSGetDrawSchema.getGetDutyCode();
        		if(tempGetNoticeNo.equals(tGetNoticeNo)
        				&& tempPolNo.equals(tPolNo)
        				&& tempGetDutyCode.equals(tGetDutyCode)
        				&& !tempDutyCode.equals(tDutyCode)){
        			double tempMoney = tempLJSGetDrawSchema.getGetMoney();
        			dSumGetMoney+=tempMoney;
        		}
        	}
        	
        	tLJSGetSchema = new LJSGetSchema();
        	logger.debug("本期合计领取金额总计为:"+dSumGetMoney);
            tLJSGetSchema.setGetNoticeNo(tLJSGetDrawSchema.getGetNoticeNo());
            tLJSGetSchema.setSerialNo(mSerialNo);
            tLJSGetSchema.setOtherNo(tLJSGetDrawSchema.getContNo());
            tLJSGetSchema.setOtherNoType("2");
            tLJSGetSchema.setSumGetMoney(dSumGetMoney);
            
			tLJSGetSchema.setAppntNo(mLCPolSchema.getAppntNo());
			tLJSGetSchema.setAgentCode(mLCPolSchema.getAgentCode());
			tLJSGetSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
			tLJSGetSchema.setAgentCom(mLCPolSchema.getAgentCom());
			tLJSGetSchema.setAgentType(mLCPolSchema.getAgentType());
			
            tLJSGetSchema.setGetDate(tLJSGetDrawSchema.getGetDate());
            tLJSGetSchema.setMakeDate(PubFun.getCurrentDate());
            tLJSGetSchema.setMakeTime(PubFun.getCurrentTime());
            tLJSGetSchema.setModifyDate(PubFun.getCurrentDate());
            tLJSGetSchema.setModifyTime(PubFun.getCurrentTime());
            tLJSGetSchema.setOperator(mOperator);
            tLJSGetSchema.setManageCom(mManageCom);
            tLJSGetSchema.setStartGetDate(mLCGetSchema.getGetStartDate());

            rLJSGetSet.add(tLJSGetSchema);
        }

        return rLJSGetSet;
    }

    //计算新的领至日期
    public Date calGetToDate(LCGetSchema aLCGetSchema)
    {
        Date rGetToDate;
        FDate tFDate = new FDate();

        Date aLastToDate = this.getGetToDate();

        //趸领记成原领至日期即起领日期；
        //Modify by JL at 2005-7-12
        //由于原来的PubFun中的函数不能满足需求,这里不在调用PubFun中的函数。
        //计算事例：
        // 开始日期    参考日期   时间间隔(M)   计算结果
        //2006-2-28  2004-2-29      12       2007-2-28
        //2007-2-28  2004-2-29      12       2008-2-29
        //2011-1-31  2010-12-31     1        2011-2-28
        //2011-2-28  2010-12-31     1        2011-3-31
        GregorianCalendar bCalendar = new GregorianCalendar();
        GregorianCalendar cCalendar = new GregorianCalendar();
        bCalendar.setTime(aLastToDate); //计算起始日期
        cCalendar.setTime(tFDate.getDate(aLCGetSchema.getGetStartDate())); //参考日期

        int mDays = bCalendar.get(Calendar.DATE);
        int cDays = cCalendar.get(Calendar.DATE);

        bCalendar.add(Calendar.MONTH, aLCGetSchema.getGetIntv()); //增加时间间隔

        if (mDays != cDays) //mDays < cDays
        {
            int LastDay = bCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            bCalendar.set(bCalendar.get(Calendar.YEAR),
                          bCalendar.get(Calendar.MONTH),
                          (cDays < LastDay) ? cDays : LastDay);
        }

        rGetToDate = bCalendar.getTime();
        logger.debug("==> 新的领至日期为：" + tFDate.getString(rGetToDate));

        //End Add
        return rGetToDate;
    }

    private BqCalBase initCalBase(LCGetSchema aLCGetSchema)
    {
        logger.debug("......initCalBase ");

        BqCalBase aBqCalBase = new BqCalBase();
        FDate tFDate = new FDate();
        int aGetYears;
        int aLastGetTimes;
        int aCurGetTimes;
        int aGetAge;
        int aAppYears;
        String aGetIntv;
        Date tGetToDate;
        Date aGetStartDate;
        LCPolSchema tLCPolSchema = new LCPolSchema();
        LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();

        if (aLCGetSchema.getGetIntv() == 0)
        {
            tGetToDate = tFDate.getDate(mTimeEnd);
        }
        else
        {
            tGetToDate = this.calGetToDate(aLCGetSchema);
        }

        aGetStartDate = tFDate.getDate(aLCGetSchema.getGetStartDate());
        aGetIntv = String.valueOf(aLCGetSchema.getGetIntv());

        //本次是第几年给付(领取时的领取年期)
        aGetYears = PubFun.calInterval(aGetStartDate, tGetToDate, "Y");

        // 上次是第几次给付
        aLastGetTimes = PubFun.calInterval(aLCGetSchema.getGetStartDate(),
                                           aLCGetSchema.getGettoDate(), aGetIntv) +
                        1;

        //本次是第几次给付
        aCurGetTimes = PubFun.calInterval(aGetStartDate, tGetToDate, aGetIntv) +
                       1;

        //领取时被保人年龄,投保年期
        tLCPolSchema = this.getPolInfo();
        tLCInsuredSchema = this.getInsuredInfo();

        aGetAge = PubFun.calInterval(tFDate.getDate(tLCInsuredSchema.getBirthday()),
                                     tGetToDate, "Y");
        aAppYears = PubFun.calInterval(tFDate.getDate(tLCPolSchema.getCValiDate()),
                                       tGetToDate, "Y");

        //投保年龄，已交费年期，始领年龄，始领年期
        int aAppAge;

        //投保年龄，已交费年期，始领年龄，始领年期
        int aPayYear;

        //投保年龄，已交费年期，始领年龄，始领年期
        int aGetStartAge;

        //投保年龄，已交费年期，始领年龄，始领年期
        int aGetStartYear;
        aAppAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(),
                                     tLCPolSchema.getCValiDate(), "Y");
        aPayYear = PubFun.calInterval(tLCPolSchema.getCValiDate(),
                                      tLCPolSchema.getPaytoDate(), "Y");
        aGetStartAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(),
                                          aLCGetSchema.getGetStartDate(), "Y");
        aGetStartYear = PubFun.calInterval(tLCPolSchema.getCValiDate(),
                                           aLCGetSchema.getGetStartDate(), "Y");

        //得到getblance值
        LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
        tLCInsureAccDB.setPolNo(aLCGetSchema.getPolNo());
        tLCInsureAccDB.setRiskCode(tLCPolSchema.getRiskCode());
        tLCInsureAccDB.setAccType("003");

        LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
        logger.debug("tLCInsureAccSet :" + tLCInsureAccSet.size());

        double aGetBalance = 0.0;

        for (int i = 1; i <= tLCInsureAccSet.size(); i++)
        {
            aGetBalance += tLCInsureAccSet.get(i).getInsuAccGetMoney();
        }

        aBqCalBase.setAppAge(aAppAge);

        aBqCalBase.setGrpContNo(aLCGetSchema.getGrpContNo());
        aBqCalBase.setAddRate(aLCGetSchema.getAddRate());

        LCDutyDB tLCDutyDB = new LCDutyDB();
        tLCDutyDB.setPolNo(aLCGetSchema.getPolNo());
        tLCDutyDB.setDutyCode(aLCGetSchema.getDutyCode());
        tLCDutyDB.getInfo();
        aBqCalBase.setGet(tLCDutyDB.getAmnt());
        aBqCalBase.setGetIntv(aLCGetSchema.getGetIntv());
        aBqCalBase.setGDuty(aLCGetSchema.getGetDutyCode());
        aBqCalBase.setGetStartDate(aLCGetSchema.getGetStartDate());

        aBqCalBase.setGetStartYear(aGetStartYear);
        aBqCalBase.setGetStartAge(aGetStartAge);
        aBqCalBase.setGetAge(aGetAge);
        aBqCalBase.setGetTimes(aCurGetTimes);
        aBqCalBase.setPayEndYear(aPayYear);
        aBqCalBase.setGetYear(aGetYears);
        aBqCalBase.setGetAppYear(aAppYears);
        aBqCalBase.setInterval(1);
        aBqCalBase.setPayEndYear(tLCPolSchema.getPayEndYear());

        aBqCalBase.setMult(tLCPolSchema.getMult());
        aBqCalBase.setPayIntv(tLCPolSchema.getPayIntv());
        aBqCalBase.setGetIntv(aLCGetSchema.getGetIntv());
        aBqCalBase.setPolNo(tLCPolSchema.getPolNo());
        aBqCalBase.setCValiDate(tLCPolSchema.getCValiDate());
        aBqCalBase.setYears(tLCPolSchema.getYears());
        aBqCalBase.setPrem(tLCPolSchema.getPrem());

        aBqCalBase.setSex(tLCInsuredSchema.getSex());
        aBqCalBase.setJob(tLCInsuredSchema.getOccupationCode());

        aBqCalBase.setGetBalance(aGetBalance);

        this.displayObject(aBqCalBase);

        return aBqCalBase;
    }

    //递增给付领取处理
    private double calAddGetMoney(BqCalBase aBqCalBase, LCGetSchema aLCGetSchema)
    {
        double aGetMoney = 0;
        LMDutyGetAliveSchema tLMDutyGetAliveSchema = new LMDutyGetAliveSchema();
        tLMDutyGetAliveSchema.setSchema(mLMDutyGetAliveSchema);

        BqCalBase tBqCalBase = new BqCalBase();
        int aGetAge;
        int aGetAppYear;
        int aGetYear;
        int aTimes;

        aGetAge = Integer.parseInt(aBqCalBase.getGetAge());
        aGetAppYear = Integer.parseInt(aBqCalBase.getGetAppYear());
        aGetYear = Integer.parseInt(aBqCalBase.getGetYear());
        aTimes = 0;

        //del by JL at 2005-8-30,以险种描述为准
        //if (aLCGetSchema.getAddRate() > 0)
        //{
        //    tLMDutyGetAliveSchema.setAddValue(aLCGetSchema.getAddRate());
        //}
        //递增开始年龄
        if (tLMDutyGetAliveSchema.getAddStartUnit().equals("0"))
        {
            if (aGetAge < tLMDutyGetAliveSchema.getAddStartPeriod())
            {
                aGetMoney = aLCGetSchema.getActuGet();
            }
            else
            {
                if (aGetAge > tLMDutyGetAliveSchema.getAddEndPeriod())
                {
                    aGetAge = tLMDutyGetAliveSchema.getAddEndPeriod();
                }

                aGetAge = aGetAge - tLMDutyGetAliveSchema.getAddStartPeriod() +
                          1;

                //递增间隔单位默认为年
                if ((aGetAge % tLMDutyGetAliveSchema.getAddIntv()) != 0)
                {
                    aTimes = (aGetAge / tLMDutyGetAliveSchema.getAddIntv()) +
                             1;
                }
                else
                {
                    aTimes = aGetAge / tLMDutyGetAliveSchema.getAddIntv();
                }

                aGetMoney = this.calAddData(aLCGetSchema.getStandMoney(),
                                            tLMDutyGetAliveSchema.getAddValue(),
                                            aTimes,
                                            tLMDutyGetAliveSchema.getAddType());
            }
        }

        //领取递增开始年期,领取后多少年开始递增
        else if (tLMDutyGetAliveSchema.getAddStartUnit().equals("1"))
        {
            if (aGetYear < tLMDutyGetAliveSchema.getAddStartPeriod())
            {
                aGetMoney = aLCGetSchema.getStandMoney();
            }
            else
            {
                if (aGetYear > tLMDutyGetAliveSchema.getAddEndPeriod())
                {
                    aGetYear = tLMDutyGetAliveSchema.getAddEndPeriod();
                }

                aGetYear = aGetYear -
                           tLMDutyGetAliveSchema.getAddStartPeriod() + 1;

                //递增间隔单位默认为年
                if ((aGetYear % tLMDutyGetAliveSchema.getAddIntv()) != 0)
                {
                    aTimes = (aGetYear / tLMDutyGetAliveSchema.getAddIntv()) +
                             1;
                }
                else
                {
                    aTimes = aGetYear / tLMDutyGetAliveSchema.getAddIntv();
                }

                aGetMoney = this.calAddData(aLCGetSchema.getStandMoney(),
                                            tLMDutyGetAliveSchema.getAddValue(),
                                            aTimes,
                                            tLMDutyGetAliveSchema.getAddType());
            }
        }

        //投保递增开始年期,投保后多少年递增
        else if (tLMDutyGetAliveSchema.getAddStartUnit().equals("2"))
        {
            if (aGetAppYear < tLMDutyGetAliveSchema.getAddStartPeriod())
            {
                aGetMoney = aLCGetSchema.getActuGet();
            }
            else
            {
                if (aGetAppYear > tLMDutyGetAliveSchema.getAddEndPeriod())
                {
                    aGetAppYear = tLMDutyGetAliveSchema.getAddEndPeriod();
                }

                aGetAppYear = aGetAppYear -
                              tLMDutyGetAliveSchema.getAddStartPeriod() + 1;

                //递增间隔单位默认为年
                if ((aGetAppYear % tLMDutyGetAliveSchema.getAddIntv()) != 0)
                {
                    aTimes = (aGetAppYear / tLMDutyGetAliveSchema.getAddIntv()) +
                             1;
                }
                else
                {
                    aTimes = aGetAppYear / tLMDutyGetAliveSchema.getAddIntv();
                }

                aGetMoney = this.calAddData(aLCGetSchema.getActuGet(),
                                            tLMDutyGetAliveSchema.getAddValue(),
                                            aTimes,
                                            tLMDutyGetAliveSchema.getAddType());
            }
        }
        else
        {
            logger.debug("-----------no this add descriptions--------");
        }

        return aGetMoney;
    }

    //计算递增值
    private double calAddData(double aOriginData, double aAddRate,
                              double aTimes, String aAddRateType)
    {
        double aResultData = 0.0;

        //按照比例递增
        if (aAddRateType.equals("R"))
        {
            aResultData = aOriginData + (aOriginData * aAddRate * aTimes);
            logger.debug("==>aAddRate = " + aAddRate + ",aResultData = " +
                               aResultData);
        }

        //几何递增
        else if (aAddRateType.equals("G"))
        {
            aResultData = aOriginData;

            for (int i = 1; i <= aTimes; i++)
            {
                aResultData = aResultData * (1 + aAddRate);
            }
        }
        else
        {
            aResultData = aOriginData + (aAddRate * aTimes);
        }

        return aResultData;
    }

    //得到保单是否销户标志
    private String getContCancelFlag(String aCancelFlag)
    {
        LMDutyGetAliveSchema tLMDutyGetAliveSchema = new LMDutyGetAliveSchema();
        tLMDutyGetAliveSchema = this.getGetAliveInfo();

        //默认为000，为防止描述造成的数据问题
        try
        {
            if ((tLMDutyGetAliveSchema.getAfterGet() == null) ||
                    tLMDutyGetAliveSchema.getAfterGet().trim().equals(""))
            {
                tLMDutyGetAliveSchema.setAfterGet("000");
            }
        }
        catch (Exception ex)
        {
        }

        //"000" 无动作
        if (tLMDutyGetAliveSchema.getAfterGet().equals("000"))
        {
            aCancelFlag = "0";
        }

        // "001"   保额递减（暂不用）
        else if (tLMDutyGetAliveSchema.getAfterGet().equals("001"))
        {
            aCancelFlag = "0";
        }

        //"002"    保额递增（暂不用）
        else if (tLMDutyGetAliveSchema.getAfterGet().equals("002"))
        {
            aCancelFlag = "0";
        }

        // "003" 无条件销户
        else if (tLMDutyGetAliveSchema.getAfterGet().equals("003"))
        {
            aCancelFlag = "1";
        }

        //"004" 最后一次给付销户
        else if (tLMDutyGetAliveSchema.getAfterGet().equals("004"))
        {
            if (aCancelFlag.equals("1"))
            {
                aCancelFlag = "1";
            }
            else
            {
                aCancelFlag = "0";
            }
        }
        else
        {
            logger.debug("----no des getAliv---");
        }

        return aCancelFlag;
    }

    //是否销户标志
    private void setCancelFlag(String aCancelFlag)
    {
        mCancelFlag = aCancelFlag;
    }

    private String getCancelFlag()
    {
        if (mCancelFlag == null)
        {
            mCancelFlag = "0";
        }

        return mCancelFlag;
    }

    //给付至日期
    private void setGetToDate(Date aGetToDate)
    {
        mGetToDate = aGetToDate;
    }

    private Date getGetToDate()
    {
        return mGetToDate;
    }

    //保单信息
    private LCPolSchema getPolInfo()
    {
        LCPolSchema tLCPolSchema = new LCPolSchema();
        tLCPolSchema.setSchema(mLCPolSchema);

        return tLCPolSchema;
    }

    //保单客户信息
    private LCInsuredSchema getInsuredInfo()
    {
        LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
        tLCInsuredSchema.setSchema(mLCInsuredSchema);

        return tLCInsuredSchema;
    }

    //生存给付项信息
    private LMDutyGetAliveSchema getGetAliveInfo()
    {
        LMDutyGetAliveSchema tLMDutyGetAliveSchema = new LMDutyGetAliveSchema();
        tLMDutyGetAliveSchema.setSchema(mLMDutyGetAliveSchema);

        return tLMDutyGetAliveSchema;
    }

    public VData getResult()
    {
        return mResult;
    }

    public LJSGetDrawSet getLJSGetDrawSet()
    {
        return mLJSGetDrawSet;
    }

    private void displayObject(Object a)
    {
        Reflections aReflections = new Reflections();
        aReflections.printFields(a);
    }

    public static void main(String[] args)
    {
        double b = 1.1666666667;
        double c = 3000 * b;
        logger.debug(c);

        FDate tFDate = new FDate();
        Date bDate = tFDate.getDate("2005-9-1");
        Date sDate = tFDate.getDate("2005-9-1");
        logger.debug(bDate.after(sDate));
        String strdate = bDate.toString();
        logger.debug(strdate+"+"+bDate+"="+tFDate.getString(bDate));
                VData tVData = new VData();
                GlobalInput tG = new GlobalInput();
                LCPolSchema tLCPolSchema = new LCPolSchema();
                LCGetSchema tLCGetSchema = new LCGetSchema();
                TransferData tTransferData = new TransferData();
                tTransferData.setNameAndValue("timeEnd", "2009-2-11");
                tG.Operator = "001";
                tG.ManageCom = "86";
                tLCGetSchema.setContNo("86110020040210112252");
                tVData.addElement(tG);
                tVData.addElement(tLCPolSchema);
                tVData.addElement(tLCGetSchema);
                tVData.addElement(tTransferData);
                PayPlanBL tPayPlanBL = new PayPlanBL();
        
                tPayPlanBL.submitData(tVData, "");
    }



	public String getSerialNo() {
		return mSerialNo;
	}
}
