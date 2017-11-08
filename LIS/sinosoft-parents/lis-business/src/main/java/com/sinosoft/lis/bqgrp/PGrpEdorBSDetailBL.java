package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bq.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.service.BusinessService;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:保险期间中断
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Sinosoft</p>
 * @author 孙少贤 2009-04-19
 * @version 1.0
 */
public class PGrpEdorBSDetailBL implements BusinessService
{
private static Logger logger = Logger.getLogger(PGrpEdorBSDetailBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();


    /** 往后面传输数据的容器 */
    private VData mInputData;
    private MMap mMap = new MMap();


    /** 往界面传输数据的容器 */
    private VData mResult = new VData();


    /** 数据操作字符串 */
    private String mOperate;
    private Reflections mRef = new Reflections();
    private String mCurrentDate = PubFun.getCurrentDate();
    private String mCurrentTime = PubFun.getCurrentTime();

    /** 传入数据 */
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    private TransferData mTransferData = new TransferData();
    private GlobalInput mGlobalInput = new GlobalInput();
    private String mStopDate = "";//效力中断日期
    private String mEndDate = "3000-01-01";//中断把日期置为无限
    private String mGrpContNo = "";
    private String mEdorNo = "";
    private String mEdorType = "";
    private String mRiskCode = "";


    /** 修改数据 */
    private LPContStateSet mLPContStateSet = new LPContStateSet();
    private LPGrpContStateSet mLPGrpContStateSet = new LPGrpContStateSet();
    private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
    private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
    private LPDutySet mLPDutySet = new LPDutySet();
    private LPPremSet mLPPremSet = new LPPremSet();
    private LPContSet mLPContSet = new  LPContSet();
    private LPPolSet mLPPolSet = new LPPolSet();
    private LPGetSet mLPGetSet = new LPGetSet();


    //记录个人合同号
    //private String mContNo = "";

    /**
     * Constructor
     **/
    public PGrpEdorBSDetailBL()
    {}

    /**
     *
     **/
    public VData getResult()
    {
        return mResult;
    }


    /**
     * 数据提交的公共方法
     * @param: cInputData 传入的数据
     * @param: cOperate 数据操作字符串
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug(
            "=====This is PGrpEdorBSDetailBL->submitData=====\n");

        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }

        //数据校验
        if (!checkData())
        {
            return false;
        }

        //数据准备操作
        if (!dealData())
        {
            return false;
        }
        mInputData.clear();
        mInputData.add(mMap);
        PubSubmit tPubSubmit = new  PubSubmit();
        if(!tPubSubmit.submitData(mInputData, mOperate))
        {
            CError tError = new CError();
            tError.moduleName = "PGrpEdorCTDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "提交数据失败！";
            this.mErrors.addOneError(tError);

            return false;
        }
        mInputData.clear();

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * @return
     */
    private boolean getInputData()
    {
        logger.debug(
            "=====This is PGrpEdorBSDetailBL->getInputData=====\n");
        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput", 0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
//        mLCGrpPolSet = (LCGrpPolSet) mInputData.getObjectByObjectName(
//                "LCGrpPolSet", 0);
        if (mGlobalInput == null || mGlobalInput.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "PGrpEdorCTDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "GlobalInput数据为空!";
            this.mErrors.addOneError(tError);

            return false;
        }
        if (mTransferData == null || mTransferData.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "PGrpEdorCTDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "TransferData 数据为空!!";
            this.mErrors.addOneError(tError);

            return false;
        }

        mStopDate = (String) mTransferData.getValueByName("EndDate");
        mEdorNo = (String) mTransferData.getValueByName("EdorNo");
        mEdorType = (String) mTransferData.getValueByName("EdorType");
        mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");

        return true;
    }

    /**
     *
     **/
    private boolean checkData()
    {
    	logger.debug("=====This is PGrpEdorBRDetailBL->checkData=====\n");

    	//校验保全信息
    	LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
    	//tLPGrpEdorItemDB.setEdorAcceptNo(mEdorNo);
    	tLPGrpEdorItemDB.setEdorType(mEdorType);
    	tLPGrpEdorItemDB.setEdorNo(mEdorNo);
    	tLPGrpEdorItemDB.setGrpContNo(mGrpContNo);
    	LPGrpEdorItemSet tLPGrpEdorItemSet = null;
    	try {
    		tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
    	} catch (Exception ex){
    		CError.buildErr(this, ex.toString());
    		ex.printStackTrace();
    		return false;
    	}
    	if (tLPGrpEdorItemDB.mErrors.needDealError()){
    		mErrors.copyAllErrors(tLPGrpEdorItemDB.mErrors);
    		return false;
    	}
    	if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1)
    	{
    		CError tError = new CError();
    		tError.moduleName = "PGrpEdorCTDetailBL";
    		tError.functionName = "checkData";
    		tError.errorMessage = "无保全项目数据!";
    		this.mErrors.addOneError(tError);

    		return false;
    	}
    	if (tLPGrpEdorItemSet.get(1).getEdorState().equals("2"))
    	{
    		CError tError = new CError();
    		tError.moduleName = "PGrpEdorCTDetailBL";
    		tError.functionName = "checkData";
    		tError.errorMessage = "该保全项目已经申请确认不能修改!";
    		this.mErrors.addOneError(tError);

    		return false;
    	}
    	mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));

    	int intv = PubFun.calInterval(mStopDate, mLPGrpEdorItemSchema.getEdorAppDate(), "D");
    	if(intv>0){
    		CError.buildErr(this, "中断日期不可早于保全申请日期!");
    		return false;
    	}

    	//清理数据
    	if (!delStopEdorState()){
    		return false;
    	}
    	String tSQL="select max(accdate) from (select b.accdate from llreport a, llsubreport b where a.rptno = b.subrptno and a.grpcontno = '"
    		+mGrpContNo+"' and not exists (select 'X' from llregister where rgtno = a.rptno) union select b.accdate"
    		+" from llregister a, llcase b where a.rgtno = b.caseno and a.grpcontno = '"
    		+mGrpContNo+"' and clmstate not in ('60', '70', '80')) A ";
    	ExeSQL tExeSQL=new ExeSQL();
    	String tAccDate=tExeSQL.getOneValue(tSQL);
    	if(tAccDate!=null && !tAccDate.equals(""))
    	{
    		int tIntv = PubFun.calInterval(mStopDate, tAccDate, "D");
    		if (tIntv > 0) {
    			CError tError = new CError();
    			tError.moduleName = "PGrpEdorCTDetailBL";
    			tError.functionName = "checkData";
    			tError.errorMessage = "保单中断日期以后有已经报案的理赔案件!";
    			this.mErrors.addOneError(tError);

    			return false;
    		}
    	}
    	return true;
    }



    /**
     *
     **/
    private boolean delStopEdorState(){

        logger.debug(
            "=====This is PGrpEdorBSDetailBL->delStopEdorState=====\n");
        String tSql =
            "delete from LPGrpContState where EdorNo = '" + mEdorNo +
            "' and GrpContNo = '" + mGrpContNo + "'";
        mMap.put(tSql, "DELETE");
        tSql =
            "delete from LPGrpPol where EdorNo = '" + mEdorNo + "' and EdorType in ('BS', 'BF')";
        mMap.put(tSql, "DELETE");
        tSql =
            "delete from LPContState where EdorNo = '" + mEdorNo + "' and EdorType in ('BS', 'BF')";
        mMap.put(tSql, "DELETE");
        tSql =
            "delete from LPPol where EdorNo = '" + mEdorNo +
            "' and EdorType = 'BS'";
        mMap.put(tSql, "DELETE");
        tSql =
            "delete from LPDuty where EdorNo = '" + mEdorNo +
            "' and EdorType = 'BS'";
        mMap.put(tSql, "DELETE");
        tSql =
            "delete from LPGet where EdorNo = '" + mEdorNo +
            "' and EdorType = 'BS'";
        mMap.put(tSql, "DELETE");
        tSql =
            "delete from LPPrem where EdorNo = '" + mEdorNo +
            "' and EdorType = 'BS'";
        mMap.put(tSql, "DELETE");
        tSql =
            "delete from LPCont where EdorNo = '" + mEdorNo +
            "' and EdorType = 'BS'";
        mMap.put(tSql, "DELETE");

        return true;
    }

    /**
     * TK加保业务处理
     * @return boolean
     */
    private boolean dealData() {

    	logger.debug("=====This is PGrpEdorBRDetailBL->dealData=====\n");

    	/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    	 * No.1 更新LPGrpEdorItem表
    	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    	 */
    	if (!dealGrpEdorItem()) {
    		return false;
    	}
    	LCGrpPolSchema tLCGrpPolSchema = null;
    	LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
    	tLCGrpPolDB.setGrpContNo(mGrpContNo);
    	tLCGrpPolDB.setAppFlag("1");
    	mLCGrpPolSet = tLCGrpPolDB.query();
    	for (int n = 1; n <= mLCGrpPolSet.size(); n++) {

    		tLCGrpPolSchema = mLCGrpPolSet.get(n);
    		String tGrpPolNo = tLCGrpPolSchema.getGrpPolNo();
    		String tPolEndDate = tLCGrpPolSchema.getPayEndDate();
    		int intv = PubFun.calInterval(mStopDate, tPolEndDate, "D");
    		if(intv <= 0){
    			CError.buildErr(this, "中断日期应该在团体保单险种的终止期前!");
    			return false;
    		}
    		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    		 * 校验终止日期在保险期间内，以及如果做过类似保全，那么终止日期不能为类似保全期间内
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    		 */
    		if (!IfContinue(tGrpPolNo)) {
    			return false;
    		}

    		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    		 * No.2 插入lcgrpcontstopedorstate表
    		 *－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    		 */
    		if (!InsertGrpStopStateData(tLCGrpPolSchema)) {
    			return false;
    		}

    		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    		 * No.3 更新LPGrpPol表
    		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    		 */
    		if (!dealLPGrpPol(tLCGrpPolSchema)) {
    			return false;
    		}
    		String tSql = "";

    		LCPolDB tLCPolDB = new LCPolDB();
    		LCPolSet tLCPolSet = null;
    		tSql = "select * from LCPol where GrpContNo = '" + mGrpContNo +
    		"' and GrpPolNo = '" + tGrpPolNo + "' and appflag = '1' ";
    		try {
    			tLCPolSet = tLCPolDB.executeQuery(tSql);
    		} catch (Exception ex) {
    			CError.buildErr(this, ex.toString());
    			ex.printStackTrace();
    			return false;
    		}
    		if (tLCPolDB.mErrors.needDealError()) {
    			mErrors.copyAllErrors(tLCPolDB.mErrors);
    			return false;
    		}

    		String tPolNo = "";
    		LCPolSchema tLCPolSchema = null;
    		for (int i = 1; i <= tLCPolSet.size(); i++) {

    			/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 * No.4 插入LPPol表
    			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 */
    			tLCPolSchema = tLCPolSet.get(i);
    			tPolNo = tLCPolSchema.getPolNo();

    			
        		if (!InsertPolStopStateData(tLCPolSchema)) {
        			return false;
        		}
    			
    			if (!InsertLPPolData(tLCPolSchema)) {
    				return false;
    			}

    			/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 * No.5 插入LPDuty表
    			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 */
    			if (!dealLPDutyData(tPolNo)) {
    				return false;
    			}

    			/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 * No.6 插入LPPrem表
    			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 */
    			if (!dealLPPremData(tPolNo)) {
    				return false;
    			}

    			/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 * No.7 插入LPGet表
    			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    			 */
    			if (!dealLPGetData(tPolNo)) {
    				return false;
    			}


    		}
    	}
    	if (!dealLPContData(mGrpContNo)) {
    		return false;
    	}
    	mMap.put(mLPGrpContStateSet, "DELETE&INSERT");
    	mMap.put(mLPContStateSet, "DELETE&INSERT");
    	mMap.put(mLPGrpPolSet, "DELETE&INSERT");
    	mMap.put(mLPPolSet, "DELETE&INSERT");
    	mMap.put(mLPGetSet, "DELETE&INSERT");
    	mMap.put(mLPDutySet, "DELETE&INSERT");
    	mMap.put(mLPPremSet, "DELETE&INSERT");
    	mMap.put(mLPContSet, "DELETE&INSERT");

    	return true;
    }

    private boolean InsertGrpStopStateData(LCGrpPolSchema grpPolSchema) {
    	
    	String tOldEndDate = PubFun.calDate(mStopDate, -1,"D", null);//上一状态结束日期
    	String strSql = "select * from lcgrpcontstate where statetype = 'Available' "
			+ "and state = '0' and enddate is null and grpcontno = '" + grpPolSchema.getGrpContNo() + "' and grppolno = '"+grpPolSchema.getGrpPolNo()+"'";
    	//先查询是否存在state='0'的这条记录 保单初始可能并没有生成Available的state='0'的记录,新增NP也有可能
    	LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
    	LPGrpContStateSchema tLPGrpContStateSchema = null;
    	LCGrpContStateSet tLCGrpContStateSet = tLCGrpContStateDB.executeQuery(strSql);
    	if (tLCGrpContStateSet.size() > 0) {
    		//如果存在,先结束上一状态
    		tLPGrpContStateSchema = new LPGrpContStateSchema();
    		mRef.transFields(tLPGrpContStateSchema, tLCGrpContStateSet.get(1));
    		
			if (tLPGrpContStateSchema.getStartDate().compareTo(tOldEndDate) > 0)
				tLPGrpContStateSchema.setEndDate(tLPGrpContStateSchema.getStartDate());
			else 
				tLPGrpContStateSchema.setEndDate(tOldEndDate);
			
			tLPGrpContStateSchema.setEdorNo(mEdorNo);
			tLPGrpContStateSchema.setEdorType(mEdorType);
			tLPGrpContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGrpContStateSchema.setModifyTime(PubFun.getCurrentTime());
			mLPGrpContStateSet.add(tLPGrpContStateSchema);
			
			tLPGrpContStateSchema = new LPGrpContStateSchema();
			//先置上旧状态
			mRef.transFields(tLPGrpContStateSchema, tLCGrpContStateSet.get(1));
			tLPGrpContStateSchema.setState("1");
			if (tLPGrpContStateSchema.getStartDate().compareTo(mStopDate) == 0)
				tLPGrpContStateSchema.setStartDate(PubFun.calDate(mStopDate,1,"D",null));
			else
				tLPGrpContStateSchema.setStartDate(mStopDate);
			tLPGrpContStateSchema.setEdorNo(mEdorNo);
			tLPGrpContStateSchema.setEdorType(mEdorType);
			tLPGrpContStateSchema.setMakeDate(PubFun.getCurrentDate());
			tLPGrpContStateSchema.setMakeTime(PubFun.getCurrentTime());
			tLPGrpContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGrpContStateSchema.setModifyTime(PubFun.getCurrentTime());
			//tLPGrpContStateSchema.setStateReason(mStateReason);
			tLPGrpContStateSchema.setRemark(grpPolSchema.getPayEndDate());//申请失效时存放险种的申请失效前的EndDate
			mLPGrpContStateSet.add(tLPGrpContStateSchema);
			
    	}else{
    		//如果不存在,生成新的
    		tLPGrpContStateSchema = new LPGrpContStateSchema();
			tLPGrpContStateSchema.setEdorNo(mEdorNo);
			tLPGrpContStateSchema.setEdorType(mEdorType);
			tLPGrpContStateSchema.setGrpContNo(mGrpContNo);
			tLPGrpContStateSchema.setGrpPolNo(grpPolSchema.getGrpPolNo());
			//tLPContStateSchema.setPolNo("000000");
			tLPGrpContStateSchema.setStateType("Available");
			tLPGrpContStateSchema.setState("1");
			tLPGrpContStateSchema.setStartDate(mStopDate);
			tLPGrpContStateSchema.setOperator(mGlobalInput.Operator);
			tLPGrpContStateSchema.setMakeDate(PubFun.getCurrentDate());
			tLPGrpContStateSchema.setMakeTime(PubFun.getCurrentTime());
			tLPGrpContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGrpContStateSchema.setModifyTime(PubFun.getCurrentTime());

			tLPGrpContStateSchema.setStateReason("");
			tLPGrpContStateSchema.setRemark(grpPolSchema.getPayEndDate());

			mLPGrpContStateSet.add(tLPGrpContStateSchema);
			

    	}
		return true;
	}

    private boolean InsertPolStopStateData(LCPolSchema tPolSchema) {
    	
    	String tOldEndDate = PubFun.calDate(mStopDate, -1,"D", null);//上一状态结束日期
    	String strSql = "select * from lccontstate where statetype = 'Available' "
			+ "and state = '0' and enddate is null and contno = '" + tPolSchema.getContNo() + "' and polno = '"+tPolSchema.getPolNo()+"'";
    	//先查询是否存在state='0'的这条记录 保单初始可能并没有生成Available的state='0'的记录,新增NP也有可能
    	LCContStateDB tLCContStateDB = new LCContStateDB();
    	LPContStateSchema tLPContStateSchema = null;
    	LCContStateSet tLCContStateSet = tLCContStateDB.executeQuery(strSql);
    	if (tLCContStateSet.size() > 0) {
    		//如果存在,先结束上一状态
    		tLPContStateSchema = new LPContStateSchema();
    		mRef.transFields(tLPContStateSchema, tLCContStateSet.get(1));
    		
			if (tLPContStateSchema.getStartDate().compareTo(tOldEndDate) > 0)
				tLPContStateSchema.setEndDate(tLPContStateSchema.getStartDate());
			else 
				tLPContStateSchema.setEndDate(tOldEndDate);
			
			tLPContStateSchema.setEdorNo(mEdorNo);
			tLPContStateSchema.setEdorType(mEdorType);
			tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
			mLPContStateSet.add(tLPContStateSchema);
			
			tLPContStateSchema = new LPContStateSchema();
			//先置上旧状态
			mRef.transFields(tLPContStateSchema, tLCContStateSet.get(1));
			tLPContStateSchema.setState("1");
			if (tLPContStateSchema.getStartDate().compareTo(mStopDate) == 0)
				tLPContStateSchema.setStartDate(PubFun.calDate(mStopDate,1,"D",null));
			else
				tLPContStateSchema.setStartDate(mStopDate);
			tLPContStateSchema.setEdorNo(mEdorNo);
			tLPContStateSchema.setEdorType(mEdorType);
			tLPContStateSchema.setMakeDate(PubFun.getCurrentDate());
			tLPContStateSchema.setMakeTime(PubFun.getCurrentTime());
			tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
			//tLPContStateSchema.setStateReason(mStateReason);
			tLPContStateSchema.setRemark(tPolSchema.getPayEndDate());//申请失效时存放险种的申请失效前的EndDate
			mLPContStateSet.add(tLPContStateSchema);
			
    	}else{
    		//如果不存在,生成新的
    		tLPContStateSchema = new LPContStateSchema();
			tLPContStateSchema.setEdorNo(mEdorNo);
			tLPContStateSchema.setEdorType(mEdorType);
			tLPContStateSchema.setContNo(tPolSchema.getContNo());
			tLPContStateSchema.setPolNo(tPolSchema.getPolNo());
			tLPContStateSchema.setInsuredNo(tPolSchema.getInsuredNo());
			tLPContStateSchema.setStateType("Available");
			tLPContStateSchema.setState("1");
			tLPContStateSchema.setStartDate(mStopDate);
			tLPContStateSchema.setOperator(mGlobalInput.Operator);
			tLPContStateSchema.setMakeDate(PubFun.getCurrentDate());
			tLPContStateSchema.setMakeTime(PubFun.getCurrentTime());
			tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());

			tLPContStateSchema.setStateReason("");
			tLPContStateSchema.setRemark(tPolSchema.getPayEndDate());

			mLPContStateSet.add(tLPContStateSchema);
			

    	}
		return true;
	}

	/**
     *
     **/
    private boolean IfContinue(String tGrpPolNo) {

        logger.debug("=====This is PGrpEdorBSDetailBL->IfContinue=====\n");
		String strSql = "select * from lcgrpcontstate where statetype = 'Available' "
			+ "and state = '1' and enddate is null and grpcontno = '" + mGrpContNo + "' and grppolno = '"+tGrpPolNo+"'";
		LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
		LCGrpContStateSet tLCGrpContStateSet = tLCGrpContStateDB.executeQuery(strSql);
		logger.debug("strSql[" + strSql + "]");
		if (tLCGrpContStateSet != null && tLCGrpContStateSet.size() >= 1) {
			mErrors.addOneError(new CError(mGrpContNo + "已经存在效力中断状态！"));
			return false;
		}
        return true;
    }

    /**
     *
     **/
    private boolean dealGrpEdorItem(){

        logger.debug(
            "=====This is PGrpEdorBSDetailBL->dealGrpEdorItem=====\n");
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        //tLPGrpEdorItemDB.setEdorAcceptNo(mEdorNo);
        tLPGrpEdorItemDB.setEdorNo(mEdorNo);
        tLPGrpEdorItemDB.setGrpContNo(mGrpContNo);
        tLPGrpEdorItemDB.setEdorType(mEdorType);
        LPGrpEdorItemSet tLPGrpEdorItemSet = null;
        try {
            tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLPGrpEdorItemDB.mErrors.needDealError()){
            mErrors.copyAllErrors(tLPGrpEdorItemDB.mErrors);
            return false;
        }
        if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1)
        {
            mErrors.addOneError("查询LPGrpEdorItem表失败!");
            return false;
        }
        mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1).getSchema());
        mLPGrpEdorItemSchema.setEdorState("1");
        mLPGrpEdorItemSchema.setModifyDate(mCurrentDate);
        mLPGrpEdorItemSchema.setModifyTime(mCurrentTime);
        mMap.put(mLPGrpEdorItemSchema, "UPDATE");

        return true;
    }



    /**
     *
     **/
    private boolean dealLPGrpPol(LCGrpPolSchema tLCGrpPolSchema){

        logger.debug("=====This is PGrpEdorBSDetailBL->dealLCGrpPol=====\n");
	LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
	LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
        LPGrpPolSchema mLPGrpPolSchema = new LPGrpPolSchema();
	tLCGrpPolDB.setSchema(tLCGrpPolSchema);
	mLCGrpPolSchema=tLCGrpPolDB.query().get(1);
        mRef.transFields(mLPGrpPolSchema, mLCGrpPolSchema);
        mLPGrpPolSchema.setEdorNo(mEdorNo);
        mLPGrpPolSchema.setEdorType(mEdorType);
        mLPGrpPolSchema.setPayEndDate(mEndDate);
        mLPGrpPolSchema.setPaytoDate(mEndDate);
        mLPGrpPolSchema.setModifyDate(mCurrentDate);
        mLPGrpPolSchema.setModifyTime(mCurrentTime);
        mLPGrpPolSet.add(mLPGrpPolSchema);

        return true;
    }

    /**
     *
     **/
    private boolean InsertLPPolData(LCPolSchema tLCPolSchema){

        logger.debug(
            "=====This is PGrpEdorBSDetailBL->InsertLPPolData=====\n");
        LPPolSchema tLPPolSchema = new LPPolSchema();
        mRef.transFields(tLPPolSchema, tLCPolSchema);
        tLPPolSchema.setEdorNo(mEdorNo);
        tLPPolSchema.setEdorType(mEdorType);
        tLPPolSchema.setModifyDate(mCurrentDate);
        tLPPolSchema.setModifyTime(mCurrentTime);
        tLPPolSchema.setPayEndDate(mEndDate);
        tLPPolSchema.setPaytoDate(mEndDate);
        tLPPolSchema.setEndDate(mEndDate);
        tLPPolSchema.setAcciEndDate(mEndDate);
        mLPPolSet.add(tLPPolSchema);

        return true;
    }

    /**
     *
     **/
    private boolean dealLPDutyData(String tPolNo) {

        LCDutyDB tLCDutyDB = new LCDutyDB();
        LCDutySet tLCDutySet = null;
        String tSql = "select * from lcduty where polno = '" + tPolNo + "'";
        try {
            tLCDutySet = tLCDutyDB.executeQuery(tSql);
        } catch (Exception ex) {
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLCDutyDB.mErrors.needDealError()) {
            mErrors.copyAllErrors(tLCDutyDB.mErrors);
            return false;
        }
        if (tLCDutySet == null || tLCDutySet.size() < 1) {
            mErrors.addOneError(
                    "查询个人险种号[" + tPolNo + "]的LCDuty表失败！");
            return false;
        }
        LCDutySchema tLCDutySchema = new LCDutySchema();
        for (int t = 1; t <= tLCDutySet.size(); t++) {
            tLCDutySchema.setSchema(tLCDutySet.get(t).getSchema());
            if (!InsertLPDutyData(tLCDutySchema)) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     **/
    private boolean InsertLPDutyData(LCDutySchema tLCDutySchema){

        logger.debug(
            "=====This is PGrpEdorBSDetailBL->InsertLPDutyData=====\n");
        LPDutySchema tLPDutySchema = new LPDutySchema();
        mRef.transFields(tLPDutySchema, tLCDutySchema);
        tLPDutySchema.setPayEndDate(mEndDate);
        tLPDutySchema.setPaytoDate(mEndDate);
        tLPDutySchema.setModifyDate(mCurrentDate);
        tLPDutySchema.setModifyTime(mCurrentTime);
        tLPDutySchema.setEdorNo(mEdorNo);
        tLPDutySchema.setEdorType(mEdorType);
        mLPDutySet.add(tLPDutySchema);

        return true;
    }

    /**
     *
     **/
    private boolean dealLPPremData(String tPolNo) {

        logger.debug("=====This is PGrpEdorBSDetailBL->dealLPPremData");
        LCPremDB tLCPremDB = new LCPremDB();
        LCPremSet tLCPremSet = null;
        String tSql = "select * from lcprem where polno ='" + tPolNo + "'";
        try {
            tLCPremSet = tLCPremDB.executeQuery(tSql);
        } catch (Exception ex) {
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLCPremDB.mErrors.needDealError()) {
            mErrors.copyAllErrors(tLCPremDB.mErrors);
            return false;
        }
        if (tLCPremSet == null || tLCPremSet.size() < 1) {
            mErrors.addOneError(
                    "查询个人险种号[" + tPolNo + "]的LCPrem表失败！");
            return false;
        }
        for (int t = 1; t <= tLCPremSet.size(); t++) {
            LCPremSchema tLCPremSchema = new LCPremSchema();
            LPPremSchema tLPPremSchema = new LPPremSchema();
            tLCPremSchema = tLCPremSet.get(t);
            mRef.transFields(tLPPremSchema, tLCPremSchema);
            tLPPremSchema.setPayEndDate(mEndDate);
            tLPPremSchema.setPaytoDate(mEndDate);
            tLPPremSchema.setModifyDate(mCurrentDate);
            tLPPremSchema.setModifyTime(mCurrentTime);
            tLPPremSchema.setEdorNo(mEdorNo);
            tLPPremSchema.setEdorType(mEdorType);
            mLPPremSet.add(tLPPremSchema);
        }

        return true;
    }

    /**
     *
     **/
    private boolean dealLPGetData(String tPolNo) {

        logger.debug(
            "=====This is PGrpEdorBSDetailBL->dealLPGetData=====\n");
        LCGetDB tLCGetDB = new LCGetDB();
        LCGetSet tLCGetSet = new LCGetSet();
        String tSql = "select * from lcget where polno ='" + tPolNo +
                      "'";
        tLCGetSet = tLCGetDB.executeQuery(tSql);
        if (tLCGetSet.size() < 1) {
            mErrors.addOneError("查询个人险种号[" + tPolNo + "]的LCGet表失败！");
            return false;
        }
        for (int t = 1; t <= tLCGetSet.size(); t++) {
            LCGetSchema tLCGetSchema = new LCGetSchema();
            LPGetSchema tLPGetSchema = new LPGetSchema();
            tLCGetSchema = tLCGetSet.get(t);
            mRef.transFields(tLPGetSchema, tLCGetSchema);
            tLPGetSchema.setGetEndDate(mEndDate);
            tLPGetSchema.setModifyDate(mCurrentDate);
            tLPGetSchema.setModifyTime(mCurrentTime);
            tLPGetSchema.setEdorNo(mEdorNo);
            tLPGetSchema.setEdorType(mEdorType);
            mLPGetSet.add(tLPGetSchema);
        }

        return true;
    }

    /**
     *
     **/
    private boolean dealLPContData(String tGrpContNo) {

        logger.debug(
            "=====This is PGrpEdorBSDetailBL->dealLPContData=====\n");
        LCContDB tLCContDB = new LCContDB();
        LCContSet tLCContSet = null;
        String tSql = "select * from lccont where grpcontno ='" + tGrpContNo + "'";
        try {
            tLCContSet = tLCContDB.executeQuery(tSql);
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLCContDB.mErrors.needDealError()){
            mErrors.copyAllErrors(tLCContDB.mErrors);
            return false;
        }
        if (tLCContSet == null || tLCContSet.size() < 1) {
            mErrors.addOneError("查询个人保单号[" + tGrpContNo + "]的LCCont表失败！");
            return false;
        }
        for (int t = 1; t <= tLCContSet.size(); t++) {
            LCContSchema tLCContSchema = new LCContSchema();
            LPContSchema tLPContSchema = new LPContSchema();
            tLCContSchema = tLCContSet.get(t);
            //mContNo = tLCContSchema.getContNo();
            mRef.transFields(tLPContSchema, tLCContSchema);
            tLPContSchema.setPaytoDate(mEndDate);
            tLPContSchema.setModifyDate(mCurrentDate);
            tLPContSchema.setModifyTime(mCurrentTime);
            tLPContSchema.setEdorNo(mEdorNo);
            tLPContSchema.setEdorType(mEdorType);
            mLPContSet.add(tLPContSchema);
        }

        return true;
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
