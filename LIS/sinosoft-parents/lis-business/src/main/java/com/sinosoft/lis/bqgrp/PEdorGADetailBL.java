package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;



import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.bq.ReCalBL;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 业务系统年金领取功能部分</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author sunsx
 * @version 1.0
 */
public class PEdorGADetailBL {
private static Logger logger = Logger.getLogger(PEdorGADetailBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;
    /**领取日期类型*/
    private String mNewGetStartType;
    
    private String mCurrDate = PubFun.getCurrentDate();
    private String mCurrTime = PubFun.getCurrentTime();
    

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private MMap map = new MMap();
    private TransferData mTransferData;
    private Reflections ref = new Reflections();
    private LPGetSet mLPGetSet = new LPGetSet();
    private LPPremSet mLPPremSet = new LPPremSet();
    private LPDutySet mLPDutySet = new LPDutySet();
    private LPGetSchema mLPGetSchema = new LPGetSchema();
    private LPPolSchema mLPPolSchema = new LPPolSchema();
    private LCPolSchema mLCPolSchema = new LCPolSchema();
    private LPPremSchema mLPPremSchema = new LPPremSchema();
    private LPDutySchema mLPDutySchema = new LPDutySchema();
    private LPAccMoveSet mLPAccMoveSetIn = new LPAccMoveSet();
    private LPAccMoveSet mLPAccMoveSetOut = new LPAccMoveSet();
    private LJSGetDrawSet mLJSGetDrawSet = new LJSGetDrawSet();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
    private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
    private LPInsureAccTraceSet mLPInsureAccTraceSet = new LPInsureAccTraceSet();
    private LPInsureAccClassSchema mLPInsureAccClassSchema = new LPInsureAccClassSchema();



    /**
     * Constructor
     **/
    public PEdorGADetailBL() {}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public void setOperate(String cOperate) {
        this.mOperate = cOperate;
    }

    /**
     *
     **/
    public String getOperate() {
        return this.mOperate;
    }

    /**
     *
     **/
    public boolean submitData(VData cInputData, String cOperate) {

        logger.debug("=====This is PEdorGADetialBL->submitData=====\n");

        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.setOperate(cOperate);

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData)){
            return false;
        }

        //数据校验操作（checkdata)
        if (!checkData()) {
            return false;
        }

        //保存申请
        if (this.getOperate().equals("INSERT||MONEY")) {
            if (!dealData()) {
                return false;
            }
            if (!prepareData()) {
                return false;
            }
            PubSubmit PubSubmitGA = new PubSubmit();
            if (!(PubSubmitGA.submitData(mResult, ""))) {

                //@@错误处理
                this.mErrors.copyAllErrors(PubSubmitGA.mErrors);
                return false;
            }
        }
        
        if(this.getOperate().equals("INSERT||CONFIRM")){
        	if (!prepareDataConfirm()) {
                return false;
            }
            PubSubmit PubSubmitGA = new PubSubmit();
            if (!(PubSubmitGA.submitData(mResult, ""))) {

                //@@错误处理
                this.mErrors.copyAllErrors(PubSubmitGA.mErrors);
                return false;
            }
        	
        }

        //结息
        if (this.getOperate().equals("INSERT||INTEREST")) {
            if (!GetInterest()) {
                return false;
            }
        }

        return true;
    }

    private boolean prepareDataConfirm() {
    	mResult.clear();
    	
    	LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
    	tLCInsureAccDB.setPolNo(mLPEdorItemSchema.getPolNo());
    	LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
    	LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();
    	LPInsureAccSchema tLPInsureAccSchema = null;
    	LCInsureAccSchema tLCInsureAccSchema = null;
    	
    	for(int i = 1; i<=tLCInsureAccSet.size();i++){
    		tLCInsureAccSchema = tLCInsureAccSet.get(i);
    		tLPInsureAccSchema = new LPInsureAccSchema();
    		ref.transFields(tLPInsureAccSchema, tLCInsureAccSchema);
    		tLPInsureAccSchema.setInsuAccBala(0);//帐户全部转为年金,帐户金额清0
    		tLPInsureAccSchema.setState("1");//转为年金标志
    		tLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
    		tLPInsureAccSchema.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPInsureAccSchema.setModifyDate(PubFun.getCurrentDate());
            tLPInsureAccSchema.setModifyTime(PubFun.getCurrentTime());
            tLPInsureAccSet.add(tLPInsureAccSchema);
    	}
    	map.put(tLPInsureAccSet, "DELETE&INSERT");
        map.put("delete from LPEdorItem where EdorNo = '" +
                mLPEdorItemSchema.getEdorNo() +
                "' and EdorType = 'GA' and ContNo = '" +
                mLPEdorItemSchema.getContNo() + "' and PolNo = '000000'",
                "DELETE");
        mLPEdorItemSchema.setEdorState("1");
        mLPEdorItemSchema.setModifyDate(mCurrDate);
        mLPEdorItemSchema.setModifyTime(mCurrTime);
        map.put(mLPEdorItemSchema, "DELETE&INSERT");
        map.put("update LPGrpEdorItem set EdorState = '1' where EdorNo = '" +
                mLPEdorItemSchema.getEdorNo() + "' and EdorType = 'GA'", "UPDATE");
        
        mResult.add(map);

        return true;
	}

	/**
     * GetInterest
     * 计算lcinsureaccclass结息 结果放入lpinsuredaccclass
     * @return boolean
     */
    private boolean GetInterest() {

    	logger.debug("=====This is PEdorGADetialBL->GetInterest=====\n");
    	String tPolNo = mLPEdorItemSchema.getPolNo();

    	//新的起领日期类型
    	String sNewGetStartType = (String)mTransferData.getValueByName("GetStartType");
    	//确认结息日期,结息日期为起领日期
    	String aBalanceDate = "";
    	logger.debug("==> 确定领取日期类型为" + sNewGetStartType);

    	
    	//责任表的数据
    	LCDutySet tLCDutySet = new LCDutySet();
    	LCDutyDB tLCDutyDB = new LCDutyDB();
    	tLCDutyDB.setPolNo(tPolNo);
    	tLCDutySet = tLCDutyDB.query();
    	if(tLCDutySet == null ||tLCDutySet.size() < 1)
    	{
    		CError.buildErr(this, "责任表查询失败!");
    		return false;

    	}
    	String sOldGetStartType = tLCDutySet.get(1).getGetStartType();
    
    	String strSQL = "select * from lcget a where polno ='"
    		+ mLCPolSchema.getPolNo() + "' and livegettype ='0'" //生存领取
    		+ "and exists (select getdutycode from lmdutyget b "
    		+ "where a.getdutycode=b.getdutycode and canget ='1') " //必须申请给付
    		+ "and exists (select * from lmriskduty c "
    		+ "where a.dutycode = c.dutycode and riskcode='"
    		+ mLCPolSchema.getRiskCode() + "')";

    	LCGetDB tLCGetDB = new LCGetDB();
    	LCGetSet tLCGetSet = tLCGetDB.executeQuery(strSQL);

    	logger.debug("共有" + tLCGetSet.size() + "条需要申请给付的生存领取");

    	if (tLCGetSet.size() <= 0)
    	{
    		// @@错误处理
    		CError.buildErr(this, "该保单不用进行给付申请!");
    		return false;
    	}
    	if (!sNewGetStartType.equals(sOldGetStartType))
    	{
    		String sBaseDate = "";
    		String sCompDate = "";
    		//更改了领取日期类型，重新计算开始领取日期
    		if (sNewGetStartType.equals("S"))
    		{
    			//不能设置领取日期类型,结息点为生存金起领日期
    			sBaseDate = mLCPolSchema.getInsuredBirthday();
    			sCompDate = mLCPolSchema.getCValiDate();
    		}
    		if (sNewGetStartType.equals("B"))
    		{
    			//不能设置领取日期类型,结息点为生存金起领日期
    			sBaseDate = mLCPolSchema.getInsuredBirthday();
    			sCompDate = mLCPolSchema.getInsuredBirthday();
    		}
    		aBalanceDate = PubFun.calDate(sBaseDate,
    				tLCDutySet.get(1).getGetYear(), "Y", sCompDate);
    	}
    	else
    	{
    		//没有更改领取日期类型,起领日期不变
    		aBalanceDate = tLCGetSet.get(1).getGetStartDate();
    	}
    	logger.debug("结息日期为" + aBalanceDate + ",领取日期类型为"
    			+ sNewGetStartType);
    	LCInsureAccClassSet aLCInsureAccClassSet = new LCInsureAccClassSet();
    	
    	LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
        String strSql = "select * from LCInsureAcc where PolNo ='" + mLCPolSchema.getPolNo() + "'";
        LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.executeQuery(strSql);
        if (tLCInsureAccDB.mErrors.needDealError())
        {
            CError.buildErr(this, "保单帐户查询失败!");
            return false;
        }
        if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1)
        {
            CError.buildErr(this, "保单没有帐户数据!");
            return false;
        }
    	TransferData tTransferData = new TransferData();
    	tTransferData.setNameAndValue("InsuAccNo",tLCInsureAccSet.get(1).getInsuAccNo());
    	tTransferData.setNameAndValue("PolNo", tLCInsureAccSet.get(1).getPolNo());
    	tTransferData.setNameAndValue("BalaDate", aBalanceDate);
		VData tVData = new VData();
		tVData.add(mGlobalInput);
		tVData.add(tTransferData);
		//非万能险的账户型结算
		InsuAccBala tInsuAccBala = new InsuAccBala();
		if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
			CError.buildErr(this, "分红结算失败！");
			return false;
		}
		VData tResult  = tInsuAccBala.getResult();
		aLCInsureAccClassSet = (LCInsureAccClassSet)tResult.getObjectByObjectName("LCInsureAccClassSet", 0);

    	
    	
    	 String tReturn = aLCInsureAccClassSet.encode();
         mResult.clear();
         mResult.add(tReturn);
         mResult.add(sNewGetStartType);
    	return true;
    }




    /**
     *
     **/
    public VData getResult() {
        return mResult;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {

        logger.debug(
            "=====This is PEdorGADetailBL->getInputData=====\n");
        mLPEdorItemSchema = (LPEdorItemSchema)
                       cInputData.getObjectByObjectName("LPEdorItemSchema", 0);
        mLPGetSchema = (LPGetSchema)
                       cInputData.getObjectByObjectName("LPGetSchema", 0);
        mGlobalInput.setSchema((GlobalInput)
                       mInputData.getObjectByObjectName("GlobalInput", 0));
        if (mLPEdorItemSchema == null || mLPGetSchema == null ||
                mGlobalInput == null ){
            CError.buildErr(this, "传输数据失败!");
            return false;
        }
        
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			CError.buildErr(this, "无法获取传输数据信息！");
			logger.debug("\t@> GrpEdorGADetailBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}
        mLPInsureAccClassSchema = (LPInsureAccClassSchema)cInputData.getObjectByObjectName("LPInsureAccClassSchema", 0);
        mLPAccMoveSetIn = (LPAccMoveSet)cInputData.getObjectByObjectName("LPAccMoveSet",0);
        mNewGetStartType = (String)mTransferData.getValueByName("GetStartType");
        return true;
    }

    /**
     * 校验传入的数据的合法性
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkData() {

        logger.debug("=====This is PEdorGADetialBL->checkData=====\n");
        boolean flag = true;

        //校验个人保全项目数据
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        //保存申请时校验该保单是否做过年金转换
        tLPEdorItemDB.setEdorType("GA");
        tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
        tLPEdorItemDB.setEdorState("0");
        LPEdorItemSet tLPEdorItemSet = null;
        try {
            tLPEdorItemSet = tLPEdorItemDB.query();
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLPEdorItemSet != null && tLPEdorItemSet.size() > 0) {
            CError tError = new CError();
            tError.moduleName = "PEdorGADetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "该保单之前已经做过年金转换!";
            this.mErrors.addOneError(tError);

            return false;
        }
        
        tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
        tLPEdorItemDB.setGrpContNo(mLPEdorItemSchema.getGrpContNo());
        tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
        tLPEdorItemSet = null;
        try {
            tLPEdorItemSet = tLPEdorItemDB.query();
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {

            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorGADetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "传入的数据有误!";
            this.mErrors.addOneError(tError);

            return false;
        }
        

        String tPolNo = mLPEdorItemSchema.getPolNo();
        mLPEdorItemSchema = new LPEdorItemSchema();
        mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1).getSchema());
        mLPEdorItemSchema.setPolNo(tPolNo);
        if (mLPEdorItemSchema.getEdorState().equals("2")) {

            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorGADetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "该保全已经申请确认不能修改!";
            this.mErrors.addOneError(tError);

            return false;
        }
        
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(tPolNo);
        if (!tLCPolDB.getInfo())
        {
            // @@错误处理
            CError.buildErr(this, "个人保单查询失败");
            return false;
        }
        mLCPolSchema.setSchema(tLCPolDB.getSchema());
        if(this.getOperate().equals("INSERT||INTEREST"))
        {
        	LCDutyDB tLCDutyDB = new LCDutyDB();
        	tLCDutyDB.setPolNo(tPolNo);
        	LCDutySet tLCDutySet = tLCDutyDB.query();
        	if(tLCDutySet == null || tLCDutySet.size() < 1){
        		CError.buildErr(this, "责任表查询失败!");
        		return false;
        	}

        	for(int i = 1; i<=tLCDutySet.size(); i++)
        	{
        		if (tLCDutySet.get(i).getGetYearFlag() == null || !tLCDutySet.get(i).getGetYearFlag().equals("A"))
        		{
        			CError.buildErr(this, "不能进行起领日期类型的变更，原因是没有设定领取年龄\n若需要进行起领日期类型变更，请先进行领取年龄变更(GB)的保全项目!");
        			return false;
        		}
        	}
        }
        
        
        if(this.getOperate().equals("INSERT||CONFIRM"))
        {
        	//校验GET
        	LPGetDB tLPGetDB = new LPGetDB();
        	tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        	tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
        	tLPGetDB.setPolNo(mLPEdorItemSchema.getPolNo());
        	LPGetSet tLPGetSet = tLPGetDB.query();
        	
        	String strSQL = "select * from lcget a where polno ='"
        		+ mLCPolSchema.getPolNo() + "' and livegettype ='0'" //生存领取
        		+ "and exists (select getdutycode from lmdutyget b "
        		+ "where a.getdutycode=b.getdutycode and canget ='1') " //必须申请给付
        		+ "and exists (select * from lmriskduty c "
        		+ "where a.dutycode = c.dutycode and riskcode='"
        		+ mLCPolSchema.getRiskCode() + "')";

        	LCGetDB tLCGetDB = new LCGetDB();
        	LCGetSet tLCGetSet = tLCGetDB.executeQuery(strSQL);
        	
        	if(tLPGetSet.size() != tLCGetSet.size()){
        		 CError.buildErr(this, "帐户年金转换未全部转换完成!");
        		 return false;
        	}
        	
        	for(int i = 1; i<=tLPGetSet.size();i++){
        		if(!tLPGetSet.get(i).getGetDutyKind().equals(mLPGetSchema.getGetDutyKind()))
        		{
        			CError.buildErr(this, "帐户的年金转换类型必须一致!");
           		 	return false;
        		}
        		
        	}
        }

        return flag;
    }

    /**
     *
     **/
    private boolean dealData() {

        logger.debug("=====This is PEdorGADetailBL->dealData=====\n");
        mLPInsureAccTraceSet.clear();
    	String tEdorNo = mLPEdorItemSchema.getEdorNo();
    	String tEdorType = mLPEdorItemSchema.getEdorType();
    	String tPolNo = mLPEdorItemSchema.getPolNo();
    	String tRiskCode = mLCPolSchema.getRiskCode();

    	/**新的起领日期类型*/
    	String sNewGetStartType = (String)mTransferData.getValueByName("GetStartType");
    	/**年金转换标志*/
    	String tAnnuityFlag = (String) mTransferData.getValueByName("annuityFlag");
    	/**现金转出标志*/
    	String tCashFlag = (String) mTransferData.getValueByName("cashFlag");
    	/**年金转换比例*/
    	String tRAnnuity = (String) mTransferData.getValueByName("RAnnuity");
    	/**年金转换金额*/
    	String tCAnnuity = (String) mTransferData.getValueByName("CAnnuity");
    	/**现金转出比例*/
    	String tRCash = (String) mTransferData.getValueByName("RCash");
    	/**现金转出金额*/
    	String tCCash = (String) mTransferData.getValueByName("CCash");
    	
    	
    	logger.debug("***新的起领日期类型"+sNewGetStartType);
    	logger.debug("***年金转换标志:"+tAnnuityFlag);
    	logger.debug("***现金转出标志:"+tCashFlag);
    	logger.debug("***年金转换比例:"+tRAnnuity);
    	logger.debug("***年金转换金额:"+tCAnnuity);
    	logger.debug("***现金转出比例:"+tRCash);
    	logger.debug("***现金转出金额:"+tCCash);
    	
        String tAnnuityType = "";
        String tCashType = "";
        String tAnnuity = "";
        String tCash = "";
    	
//    	处理帐户转换为年金
        if (tAnnuityFlag.equals("1"))
        {
            if (((tRAnnuity == null) || tRAnnuity.trim().equals(""))
                    && ((tCAnnuity == null) || tCAnnuity.trim().equals("")))
            {
                // @@错误处理
                CError.buildErr(this, "请对帐户进行转年金处理!");
                return false;
            }

            if ((tRAnnuity.trim().length() > 0)
                    && (tCAnnuity.trim().length() > 0))
            {
                // @@错误处理
                CError.buildErr(this, "您只能选择金额或比例任意一种转为年金!");
                return false;
            }
            else
            {
                if (tRAnnuity.trim().length() > 0)
                {
                    //按比例转为年金
                    tAnnuity = tRAnnuity.trim();
                    tAnnuityType = "R";
                }
                else
                {
                    //按金额转为年金
                    tAnnuity = tCAnnuity.trim();
                    tAnnuityType = "C";
                }
            }
            logger.debug("转换为年金:类型" + tAnnuityType + "数值" + tAnnuity);
        }

        //处理帐户转为现金
        if (tCashFlag.equals("1"))
        {
            if (((tRCash == null) || tRCash.trim().equals(""))
                    && ((tCCash == null) || tCCash.trim().equals("")))
            {
                // @@错误处理
                CError.buildErr(this, "请对帐户进行转现金处理!");
                return false;
            }

            if ((tRCash.trim().length() > 0) && (tCCash.trim().length() > 0))
            {
                // @@错误处理
                CError.buildErr(this, "您只能选择金额或比例任意一种转为现金!");
                return false;
            }
            else
            {
                if (tRCash.trim().length() > 0)
                {
                    //按比例转为现金
                    tCash = tRCash.trim();
                    tCashType = "R";
                }
                else
                {
                    //按金额转换为现金
                    tCash = tCCash.trim();
                    tCashType = "C";
                }
            }
            logger.debug("转换为现金:类型" + tCashType + "数值" + tCash);
        }
        
        //生存给付信息
        LMDutyGetAliveDB tLMDutyGetAliveDB = new LMDutyGetAliveDB();
        tLMDutyGetAliveDB.setGetDutyCode(mLPGetSchema.getGetDutyCode());
        tLMDutyGetAliveDB.setGetDutyKind(mLPGetSchema.getGetDutyKind());
        if (!tLMDutyGetAliveDB.getInfo())
        {
            // @@错误处理
            CError.buildErr(this, "生存给付责任查询失败!");
            return false;
        }
        
        //--------------新的给付信息准备-------------
        LCGetDB tLCGetDB = new LCGetDB();
        tLCGetDB.setPolNo(tPolNo);
        tLCGetDB.setDutyCode(mLPGetSchema.getDutyCode());
        tLCGetDB.setGetDutyCode(mLPGetSchema.getGetDutyCode());
        if (!tLCGetDB.getInfo())
        {
            // @@错误处理
            CError.buildErr(this, "生存给付数据查询失败!");
            return false;
        }
        
        LPGetSchema tLPGetSchema = new LPGetSchema();
        ref.transFields(tLPGetSchema, tLCGetDB.getSchema());
        tLPGetSchema.setEdorNo(tEdorNo);
        tLPGetSchema.setEdorType(tEdorType);
        tLPGetSchema.setAddRate(tLMDutyGetAliveDB.getSchema().getAddValue());
        tLPGetSchema.setGetIntv(mLPGetSchema.getGetIntv());
        tLPGetSchema.setGetDutyKind(mLPGetSchema.getGetDutyKind());
       // tLPGetSchema.set
        logger.debug("LCGET的addrate: "+tLPGetSchema.getAddRate());
        logger.debug("LCGET的getIntv: "+tLPGetSchema.getGetIntv());
        logger.debug("结束新GET数据的处理");
        
        //开始重算


        if (!ReCalPol(tLPGetSchema))
        {
            return false;
        }
        for (int i = 1; i <= mLPGetSet.size(); i++)
        {
            if (mLPGetSet.get(i).getGetDutyCode().equals(tLPGetSchema
                        .getGetDutyCode())
                    && mLPGetSet.get(i).getDutyCode().equals(tLPGetSchema
                        .getDutyCode()))
            {
            	mLPGetSchema.setSchema(mLPGetSet.get(i));
            }
        }
        //------------------------结束新GET数据的准备---------------------------------
        
        
        //------------------------帐户分类明细数据的准备------------------------------------
        
        LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
        tLCInsureAccClassDB.setPolNo(mLPInsureAccClassSchema.getPolNo());
        tLCInsureAccClassDB.setInsuAccNo(mLPInsureAccClassSchema.getInsuAccNo());
        tLCInsureAccClassDB.setPayPlanCode(mLPInsureAccClassSchema.getPayPlanCode());
        tLCInsureAccClassDB.setAccAscription(mLPInsureAccClassSchema.getAccAscription());
        tLCInsureAccClassDB.setOtherNo(mLPInsureAccClassSchema.getOtherNo());
        if(!tLCInsureAccClassDB.getInfo())
        {
        	CError.buildErr(this, "帐户分类明细查询失败!");
            return false;
        	
        }
        LCInsureAccClassSchema tLCInsureAccClassSchema = tLCInsureAccClassDB.getSchema();
        LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
        ref.transFields(tLPInsureAccClassSchema, tLCInsureAccClassSchema);
        tLPInsureAccClassSchema.setEdorNo(tEdorNo);
        tLPInsureAccClassSchema.setEdorType(tEdorType);
        tLPInsureAccClassSchema.setOperator(mGlobalInput.Operator);
        tLPInsureAccClassSchema.setModifyDate(PubFun.getCurrentDate());
        tLPInsureAccClassSchema.setModifyTime(PubFun.getCurrentTime());
        tLPInsureAccClassSchema.setState("1");//标志为转为年金标志
        tLPInsureAccClassSchema.setInsuAccBala(mLPInsureAccClassSchema.getInsuAccBala());
        tLPInsureAccClassSchema.setBalaDate(mLPInsureAccClassSchema.getBalaDate());

        //结息产生的利息
        logger.debug("结息后的帐户余额为:" + tLPInsureAccClassSchema.getInsuAccBala());
        double balaLX = tLPInsureAccClassSchema.getInsuAccBala()- tLCInsureAccClassSchema.getInsuAccBala();
        if(balaLX > 0)
        {
        	//生成利息帐户履历
        	LPInsureAccTraceSchema tLPInsureAccTraceSchema = creatTraceData(tLPInsureAccClassSchema, balaLX, "LX");
        	mLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);

        }
        

        //--------------------------Acc prepare end--------------------------------
        
        
        //------------LPACCMOVE deal start---------------
        double tAccBalance = tLPInsureAccClassSchema.getInsuAccBala();
        logger.debug("帐户余额为(结息后):" + tAccBalance);
        double tGetAccBalance = 0;
        double tCashAccBalance = 0;
        int tAMSize = mLPAccMoveSetIn.size();
        if(tAMSize <= 0)
        {
        	CError.buildErr(this, "请录入年金转换信息");
            return false;
        }
        for(int i = 1;i<=tAMSize;i++)
        {
        	LPAccMoveSchema tLPAccMoveSchema = mLPAccMoveSetIn.get(i);
        	if(tLPAccMoveSchema.getAccMoveType().equals("2"))
        	{
        		//存在年金转换信息
        		if(tAnnuityType.equals("C"))
        		{
        			//按金额转换
        			tGetAccBalance = tLPAccMoveSchema.getAccMoveBala();
        			if(tAccBalance > 0){
        				
        				tAnnuity = String.valueOf(PubFun.round((tGetAccBalance / tAccBalance)*100,2));
        			}else{
        				tAnnuity = "0.0";
        			}
                    tLPAccMoveSchema.setAccMoveRate(tAnnuity);
        		}
        		else
        		{
        			//按比例转换
        			tGetAccBalance =PubFun.round((tLPAccMoveSchema.getAccMoveRate()*tAccBalance/100),2);
        			tLPAccMoveSchema.setAccMoveBala(tGetAccBalance);
        		}
        		tLPAccMoveSchema.setMoneyMoveType(tAnnuityType);
        		tLPAccMoveSchema.setAccType(tLPInsureAccClassSchema.getAccType());
        		tLPAccMoveSchema.setRiskCode(tLPInsureAccClassSchema.getRiskCode());
                tLPAccMoveSchema.setOperator(mGlobalInput.Operator);
                tLPAccMoveSchema.setMakeDate(PubFun.getCurrentDate());
                tLPAccMoveSchema.setMakeTime(PubFun.getCurrentTime());
                tLPAccMoveSchema.setModifyDate(PubFun.getCurrentDate());
                tLPAccMoveSchema.setModifyTime(PubFun.getCurrentTime());
                
                //生成帐户履历
                LPInsureAccTraceSchema annuityLPInsureAccTraceSchema = creatTraceData(tLPInsureAccClassSchema, -tGetAccBalance, "TF");
                mLPInsureAccTraceSet.add(annuityLPInsureAccTraceSchema);
        		
        	}
        	if(tLPAccMoveSchema.getAccMoveType().equals("1"))
        	{
        		//存在现金转换信息
        		if(tCashType.equals("C"))
        		{
        			//按金额转换
        			tCashAccBalance = tLPAccMoveSchema.getAccMoveBala();
                    tAnnuity = String.valueOf(PubFun.round((tCashAccBalance / tAccBalance)*100,2));
                    tLPAccMoveSchema.setAccMoveRate(tAnnuity);
        		}
        		else
        		{
        			//按比例转换
        			tCashAccBalance =PubFun.round((tLPAccMoveSchema.getAccMoveRate()*tAccBalance/100),2);
        			tLPAccMoveSchema.setAccMoveBala(tCashAccBalance);
        		}
        		tLPAccMoveSchema.setMoneyMoveType(tCashType);
        		tLPAccMoveSchema.setAccType(tLPInsureAccClassSchema.getAccType());
        		tLPAccMoveSchema.setRiskCode(tLPInsureAccClassSchema.getRiskCode());
                tLPAccMoveSchema.setOperator(mGlobalInput.Operator);
                tLPAccMoveSchema.setMakeDate(PubFun.getCurrentDate());
                tLPAccMoveSchema.setMakeTime(PubFun.getCurrentTime());
                tLPAccMoveSchema.setModifyDate(PubFun.getCurrentDate());
                tLPAccMoveSchema.setModifyTime(PubFun.getCurrentTime());
                
                //生成帐户履历
                LPInsureAccTraceSchema cashLPInsureAccTraceSchema = creatTraceData(tLPInsureAccClassSchema, -tCashAccBalance, "TF");
                mLPInsureAccTraceSet.add(cashLPInsureAccTraceSchema);
                if(tCashAccBalance >0){
                	addEndorseData(tCashAccBalance,"GA","YF");
                }
        	}
        	mLPAccMoveSetOut.add(tLPAccMoveSchema);
        }
        double balanceMoney = tLPInsureAccClassSchema.getInsuAccBala()-tGetAccBalance-tCashAccBalance;
        logger.debug("帐户剩余的金额为:"+balanceMoney);
        if(PubFun.round(balanceMoney,2)!=0)
        {
        	CError.buildErr(this, "帐户金额未转移完整或转换金额大于帐户金额!");
            return false;
        }
        //-------------------------ACCMOVE deal end------------------
        //tGetAccBalance
        //-------------------------acctoget deal start -------------------
        LMRiskAccGetDB tLMRiskAccGetDB = new LMRiskAccGetDB();
        tLMRiskAccGetDB.setRiskCode(tRiskCode);
        tLMRiskAccGetDB.setInsuAccNo(tLPInsureAccClassSchema.getInsuAccNo());
        tLMRiskAccGetDB.setGetDutyCode(mLPGetSchema.getGetDutyCode());
        if (!tLMRiskAccGetDB.getInfo())
        {
        	CError.buildErr(this, "险种帐户年金转换信息查询失败");
            return false;
        }
        String[] tReturnData = PrepareCalData();
        if (tReturnData == null){
            return false;
        }
        Calculator tCalculator = new Calculator();
        String tCalCode = tLMRiskAccGetDB.getSchema().getCalCodeMoney();
        tCalculator.setCalCode(tCalCode);
        tCalculator.addBasicFactor("GetBalance", String.valueOf(tGetAccBalance));
        tCalculator.addBasicFactor("GetDutyKind", mLPGetSchema.getGetDutyKind());
        tCalculator.addBasicFactor("Sex", tReturnData[0]);
        tCalculator.addBasicFactor("Appage", tReturnData[1]);
        String GAresult = tCalculator.calculate();
        double tGetAccMoney = Double.parseDouble(GAresult);
        logger.debug("计算每期给付的金额为：" + tGetAccMoney);
        if(tGetAccMoney<0)
        {
            logger.debug("转换年金金额计算错误,请检查该被保人");
            return false;
        }
        mLPGetSchema.setStandMoney(mLPGetSchema.getStandMoney() + tGetAccMoney);
        mLPGetSchema.setBalaDate(mLPEdorItemSchema.getEdorValiDate());
//        mLPGetSchema.setGetStartDate(tLPInsureAccClassSchema.getBalaDate());
        mLPGetSchema.setGettoDate(mLPGetSchema.getGetStartDate());
        //mLPGetSchema.setOperator(mGlobalInput.Operator);
        mLPGetSchema.setModifyDate(PubFun.getCurrentDate());
        mLPGetSchema.setModifyTime(PubFun.getCurrentTime());
        mLPGetSchema.setCanGet("0");
        mLPGetSchema.setUrgeGetFlag("Y");
        //TODO
       
        
        

        //生成应付数据
        //1生成Ljsgetendorse
        //addEndorseData(mLPGetSchema.getStandMoney(),"AG","YF");
        //2生成ljsgetdraw
        //addLJSGetDraw(mLPGetSchema.getStandMoney());

            

        /**********************
         *计算每期的领取金额
         **********************/

        mLPEdorItemSchema.setEdorState("3");
        mLPEdorItemSchema.setModifyDate(mCurrDate);
        mLPEdorItemSchema.setModifyTime(mCurrTime);
        mLPInsureAccClassSchema.setSchema(tLPInsureAccClassSchema);
        //mLPInsureAccClassSchema.setInsuAccBala(0);
        return true;
    }

    private boolean ReCalPol(LPGetSchema aLPGetSchema) {
        //重新计算
        logger.debug("********************重算 start***************");
        
		LPPolBL tLPPolBL = new LPPolBL();
		tLPPolBL.setContNo(aLPGetSchema.getContNo());
		tLPPolBL.setPolNo(aLPGetSchema.getPolNo());
		tLPPolBL.setEdorNo(aLPGetSchema.getEdorNo());
		tLPPolBL.setEdorType(aLPGetSchema.getEdorType());
		tLPPolBL.queryLPPol(mLPEdorItemSchema);
		

        
        LCDutyDB tLCDutyDB = new LCDutyDB();

        //准备责任信息
        tLCDutyDB.setPolNo(aLPGetSchema.getPolNo());
        tLCDutyDB.setDutyCode(mLPGetSchema.getDutyCode());
        if (!tLCDutyDB.getInfo())
        {
            return false;
        }

        LCPremDB tLCPremDB = new LCPremDB();
        tLCPremDB.setPolNo(mLPGetSchema.getPolNo());
        tLCPremDB.setDutyCode(mLPGetSchema.getDutyCode());
        tLCPremDB.setPayPlanCode(mLPInsureAccClassSchema.getPayPlanCode());
        if (!tLCPremDB.getInfo())
        {
            return false;
        }

        
		ReCalBL tReCalBL = new ReCalBL(tLPPolBL.getSchema(), mLPEdorItemSchema);
		// 准备数据进行重算
		// 准备重算需要的保单表数据
		LCPolBL tLCPolBL = tReCalBL.getRecalPol(tLPPolBL.getSchema());
		// 准备重算需要的责任表数据
		LCDutyBLSet tLCDutyBLSet = tReCalBL.getRecalDuty(mLPEdorItemSchema);
		for (int i = 1; i <= tLCDutyBLSet.size(); i++)
		{
			//del by sunsx 2008-12-16 原因:重算时所有的责任领取类型都是一样的
			//if (tLCDutyBLSet.get(i).getDutyCode().equals(tLCDutyDB.getDutyCode()))
			//{
				tLCDutyBLSet.get(i).setGetStartType(mNewGetStartType);
			//}
		}
		// 准备重算需要的保费项表数据
		LCPremSet tLCPermSet = tReCalBL.getRecalPrem(mLPEdorItemSchema);
		// 准备重算需要的领取项表数据
		LCGetBLSet tLCGetBLSet = tReCalBL.getRecalGet(mLPEdorItemSchema);
		for (int i = 1; i <= tLCGetBLSet.size(); i++) {
			if (aLPGetSchema.getGetDutyCode().equals(
					tLCGetBLSet.get(i).getGetDutyCode())) {
				tLCGetBLSet.get(i)
						.setGetDutyKind(aLPGetSchema.getGetDutyKind());
				tLCGetBLSet.get(i).setGettoDate("");
				tLCGetBLSet.get(i).setGetEndDate("");
			}
		}
		
		if (!tReCalBL.recalWithData(tLCPolBL, tLCDutyBLSet, tLCPermSet,
				tLCGetBLSet, mLPEdorItemSchema)) {
			CError.buildErr(this, "重算失败");
			return false;
		}



        mLPPolSchema = tReCalBL.aftLPPolSet.get(1);
        mLPPremSet = tReCalBL.aftLPPremSet;
        mLPDutySet = tReCalBL.aftLPDutySet;
        mLPGetSet = tReCalBL.aftLPGetSet;
        logger.debug("计算成功!");
        
        for (int i = 1; i <= mLPPremSet.size(); i++)
        {
            if (mLPPremSet.get(i).getPayPlanCode().equals(mLPInsureAccClassSchema
                        .getPayPlanCode())
                    && mLPPremSet.get(i).getDutyCode().equals(tLCPremDB
                        .getDutyCode()))
            {
            	mLPPremSchema.setSchema(mLPPremSet.get(i));
            }
        }
        for (int i = 1; i <= mLPDutySet.size(); i++)
        {
            if (mLPDutySet.get(i).getDutyCode().equals(tLCDutyDB.getDutyCode()))
            {
            	mLPDutySchema.setSchema(mLPDutySet.get(i));
            }
        }
		return true;
	}



    /*
     * 生成生存领取应付数据
     */
    public boolean addLJSGetDraw(double getMoney) {

        logger.debug(
            "=====This is PEdorGADEtailBL->addLJSGetDraw=====\n");

        //如果是第一次的趸领领取数据
        LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();
        ref.transFields(tLJSGetDrawSchema, mLPPolSchema);

        tLJSGetDrawSchema.setGetMoney(getMoney);

        //0:代表保单不失效(消户标记)
        tLJSGetDrawSchema.setDestrayFlag("0");
        //生成通知
        String StrLimit = mGlobalInput.ManageCom;
        String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", StrLimit);
        tLJSGetDrawSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
        tLJSGetDrawSchema.setSerialNo(tSerialNo);
        tLJSGetDrawSchema.setGetDate(mCurrDate);
        tLJSGetDrawSchema.setDutyCode(mLPGetSchema.getDutyCode());
        tLJSGetDrawSchema.setGetDutyCode(mLPGetSchema.getGetDutyCode());
        tLJSGetDrawSchema.setGetDutyKind(mLPGetSchema.getGetDutyKind());
        tLJSGetDrawSchema.setLastGettoDate(mCurrDate);
        tLJSGetDrawSchema.setFeeFinaType("YF");
        tLJSGetDrawSchema.setFeeOperationType("YF");
        tLJSGetDrawSchema.setCurGetToDate(mLPGetSchema.getGettoDate());
        //给付首期标志
        tLJSGetDrawSchema.setGetFirstFlag("1");
        tLJSGetDrawSchema.setGrpName("");
        tLJSGetDrawSchema.setPolType("1");
        tLJSGetDrawSchema.setMakeDate(mCurrDate);
        tLJSGetDrawSchema.setMakeTime(mCurrTime);
        tLJSGetDrawSchema.setModifyDate(mCurrDate);
        tLJSGetDrawSchema.setModifyTime(mCurrTime);
        tLJSGetDrawSchema.setOperator(mGlobalInput.Operator);
        tLJSGetDrawSchema.setManageCom(mGlobalInput.ManageCom);

        //领取金额为0，不生调，不上柜
        if (getMoney == 0) {

            //是否生调
            tLJSGetDrawSchema.setRReportFlag("0");

            //是否上柜
            tLJSGetDrawSchema.setComeFlag("0");
        } else {
            tLJSGetDrawSchema.setComeFlag("1");
        }

        //给付渠道标志
        tLJSGetDrawSchema.setGetChannelFlag(tLJSGetDrawSchema.getComeFlag());
        mLJSGetDrawSet.add(tLJSGetDrawSchema);

        return true;
    }

    /**
     *
     **/
    private String[] PrepareCalData(){

        logger.debug(
            "=====This is PEdorGADetailBL->PrepareCalData=====\n");

        //查询被保人信息
        LCInsuredDB tLCInsuredDB = new LCInsuredDB();
        tLCInsuredDB.setContNo(mLPGetSchema.getContNo());
        tLCInsuredDB.setInsuredNo(mLPGetSchema.getInsuredNo());
        LCInsuredSet tLCInsuredSet = null;
        try {
            tLCInsuredSet = tLCInsuredDB.query();
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return null;
        }
        if (tLCInsuredSet == null || tLCInsuredSet.size() < 1) {
            CError tError = new CError();
            tError.moduleName = "PEdorGADetailBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询被保人信息表数据失败！";
            this.mErrors.addOneError(tError);

            return null;
        }

        //查询个人信息表
        String tSex = "";
        String tBirthday;
        if (tLCInsuredSet.get(1).getSex() == null ||
            tLCInsuredSet.get(1).getSex().equals("") ||
            tLCInsuredSet.get(1).getBirthday() == null ||
            tLCInsuredSet.get(1).getBirthday().equals("")) {
            LDPersonDB tLDPersonDB = new LDPersonDB();
            tLDPersonDB.setCustomerNo(mLPGetSchema.getInsuredNo());
            LDPersonSet tLDPersonSet = null;
            try {
                tLDPersonSet = tLDPersonDB.query();
            } catch (Exception ex){
                CError.buildErr(this, ex.toString());
                ex.printStackTrace();
                return null;
            }
            if (tLDPersonSet == null || tLDPersonSet.size() < 1) {
                CError tError = new CError();
                tError.moduleName = "PEdorGADetailBL";
                tError.functionName = "dealData";
                tError.errorMessage = "查询客户信息表数据失败！";
                this.mErrors.addOneError(tError);

                return null;
            }
            tSex = tLDPersonSet.get(1).getSex();
            tBirthday = tLDPersonSet.get(1).getBirthday();
        } else {
            tSex = tLCInsuredSet.get(1).getSex();
            tBirthday = tLCInsuredSet.get(1).getBirthday();
        }
        
        int tAppage =PubFun.calInterval(tBirthday,mLPGetSchema.getGetStartDate(), "Y");
        
        // add by sunsx 2008-12-26 增加了对性别和年龄范围的校验
        if((!"0".equals(tSex)) && (!"1".equals(tSex)))
        {
        	CError.buildErr(this, "被保人的性别不详,请做被保人资料变更");
        	return null;
        }
        
        if(mLPGetSchema.getGetDutyKind().equals("1"))
        {
        	//一次性领取的最小和最大年龄分别为40和99
        	if(tAppage > 99 ||tAppage < 40)
        	{
        		CError.buildErr(this, "被保人的年龄不在领取范围之内");
            	return null;
        	}
        	
        }else {
        	//其它的领取方式分别为40和70
        	if(tAppage < 40 ||tAppage >70)
        	{
        		CError.buildErr(this, "被保人的年龄不在领取范围之内");
            	return null;
        	}
        	
        }
        
        //-----------add end------------
        logger.debug("tYear：" + tAppage);
        String[] tReturn = {tSex, String.valueOf(tAppage)};

        return tReturn;
    }

    /**
     * 设置批改补退费表
     **/
    private boolean addEndorseData(double cashValue,String sFeeOperationType,String sFeeFinaType) {

        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
        LCPolSchema tLCPolSchema =  new  LCPolSchema();
        
        if (!tLCPolDB.getInfo()) 
        {
            CError tError = new CError();
            tError.moduleName = "PEdorGADetailBL";
            tError.functionName = "addEndorseData";
            tError.errorMessage = "查询个人险种保单表失败!";
            this.mErrors.addOneError(tError);

            return false;
        }
        tLCPolSchema= tLCPolDB.getSchema();
        
        mLJSGetEndorseSchema = new LJSGetEndorseSchema();
        ref.transFields(mLJSGetEndorseSchema, tLCPolSchema);
        ref.transFields(mLJSGetEndorseSchema, mLPEdorItemSchema);
        mLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
        mLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
        mLJSGetEndorseSchema.setGrpContNo(mLPEdorItemSchema.getGrpContNo());
        mLJSGetEndorseSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
        mLJSGetEndorseSchema.setPolNo(mLPEdorItemSchema.getPolNo());
        mLJSGetEndorseSchema.setRiskCode(tLCPolSchema.getRiskCode());
        mLJSGetEndorseSchema.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
        mLJSGetEndorseSchema.setFeeOperationType(sFeeOperationType);
        mLJSGetEndorseSchema.setGetDate(mCurrDate);
        mLJSGetEndorseSchema.setGetMoney(cashValue);
        mLJSGetEndorseSchema.setCurrency(tLCPolSchema.getCurrency());
        mLJSGetEndorseSchema.setPayPlanCode(mLPInsureAccClassSchema.getPayPlanCode());
        mLJSGetEndorseSchema.setDutyCode(mLPGetSchema.getDutyCode());
        mLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
        mLJSGetEndorseSchema.setOtherNoType("3");
        mLJSGetEndorseSchema.setGetFlag("1");
        mLJSGetEndorseSchema.setFeeFinaType(sFeeFinaType);
        mLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Get_GetDraw);
        mLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
        mLJSGetEndorseSchema.setManageCom(mGlobalInput.ManageCom);
        mLJSGetEndorseSchema.setMakeDate(mCurrDate);
        mLJSGetEndorseSchema.setMakeTime(mCurrTime);
        mLJSGetEndorseSchema.setModifyDate(mCurrDate);
        mLJSGetEndorseSchema.setModifyTime(mCurrTime);
        mLJSGetEndorseSet.add(mLJSGetEndorseSchema);

        return true;
    }

    /**
     * 生成lcinsureacctrace数据
     **/
    private LPInsureAccTraceSchema creatTraceData(LPInsureAccClassSchema iLPInsureAccClassSchema,double iMoney,String iMoneyType) {

    	LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
    	ref.transFields(tLPInsureAccTraceSchema,iLPInsureAccClassSchema);
    	tLPInsureAccTraceSchema.setOtherNo(iLPInsureAccClassSchema.getEdorNo());
    	tLPInsureAccTraceSchema.setOtherType("4");
    	//tLPInsureAccTraceSchema.setPayNo(iLPInsureAccClassSchema.getOtherNo());
    	tLPInsureAccTraceSchema.setMakeDate(mCurrDate);
    	tLPInsureAccTraceSchema.setMakeTime(mCurrTime);
    	tLPInsureAccTraceSchema.setManageCom(mGlobalInput.ManageCom);
    	tLPInsureAccTraceSchema.setModifyDate(mCurrDate);
    	tLPInsureAccTraceSchema.setModifyTime(mCurrTime);
    	tLPInsureAccTraceSchema.setPayDate(PubFun.getCurrentDate());
    	tLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
    	tLPInsureAccTraceSchema.setSerialNo(PubFun1.CreateMaxNo("SERIALNO",PubFun.getNoLimit(mGlobalInput.ManageCom)));
    	tLPInsureAccTraceSchema.setFeeCode("000000");
    	tLPInsureAccTraceSchema.setState("0");
    	tLPInsureAccTraceSchema.setUnitCount(0);
    	tLPInsureAccTraceSchema.setMoney(iMoney);
    	tLPInsureAccTraceSchema.setMoneyType(iMoneyType);
    	return tLPInsureAccTraceSchema;
    }

    /**
     *
     **/
    private boolean prepareData() {

        mResult.clear();

        map.put("delete from LJSGetEndorse where EndorsementNo = '" +
              mLPEdorItemSchema.getEdorNo() +
              "' and FeeOperationType = 'AG' and PolNo = '" +
              mLPEdorItemSchema.getPolNo() + "' and payplancode = '" +
              mLPInsureAccClassSchema.getPayPlanCode()+"' and dutycode = '" +
              mLPGetSchema.getDutyCode()+"'", "DELETE");
        map.put("delete from LPInsureAccTrace where EdorNo = '" +
                mLPEdorItemSchema.getEdorNo() +
                "' and EdorType = 'GA' and PolNo = '" +
                mLPEdorItemSchema.getPolNo() + "' and " +
                "insuaccno = '"+mLPInsureAccClassSchema.getInsuAccNo()+"' and payplancode = '"+mLPInsureAccClassSchema.getPayPlanCode()+"'", "DELETE");
        map.put("delete from LPEdorItem where EdorNo = '" +
                mLPEdorItemSchema.getEdorNo() +
                "' and EdorType = 'GA' and ContNo = '" +
                mLPEdorItemSchema.getContNo() + "' and PolNo = '000000'",
                "DELETE");
        map.put("delete from LPAccMove where EdorNo = '" +
                mLPEdorItemSchema.getEdorNo() +
                "' and edortype = 'GA' and PolNo = '" +
                mLPInsureAccClassSchema.getPolNo() + "'" +
                " and insuaccno = '"+mLPInsureAccClassSchema.getInsuAccNo()+
                "' and payplancode = '"+mLPInsureAccClassSchema.getPayPlanCode()+"'", "DELETE");
       
//        map.put("delete from LJSGetDraw where GetnoticeNo = '" +
//                mLPEdorItemSchema.getEdorNo() + "' and PolNo = '"
//                + mLPEdorItemSchema.getPolNo() + "' and dutycode ='"+mLPGetSchema.getDutyCode()+"'", "DELETE");
//        
//        map.put(mLJSGetDrawSet, "DELETE&INSERT");
        map.put(mLPPolSchema, "DELETE&INSERT");
        map.put(mLPGetSchema, "DELETE&INSERT");
        map.put(mLPDutySchema, "DELETE&INSERT");
        map.put(mLPPremSchema, "DELETE&INSERT");
        map.put(mLPAccMoveSetOut, "DELETE&INSERT");
        map.put(mLPEdorItemSchema, "DELETE&INSERT");
        map.put(mLPInsureAccClassSchema, "DELETE&INSERT");
//        map.put("update LPGrpEdorItem set EdorState = '1' where EdorNo = '" +
//                mLPEdorItemSchema.getEdorNo() + "' and EdorType = 'GA'", "UPDATE");
        if (mLPInsureAccTraceSet != null && mLPInsureAccTraceSet.size() > 0) {
        	map.put(mLPInsureAccTraceSet, "DELETE&INSERT");
        }
        if (mLJSGetEndorseSet != null && mLJSGetEndorseSet.size() > 0) {
        	map.put(mLJSGetEndorseSet, "DELETE&INSERT");
        }
        mResult.add(map);

        return true;
    }

    /**
     *
     **/
    public static void main(String[] args) {
    	Calculator tCalculator = new Calculator();
        String tCalCode = "601303";
        tCalculator.setCalCode(tCalCode);
        tCalculator.addBasicFactor("GetBalance", "170000");
        tCalculator.addBasicFactor("GetDutyKind", "2");
        tCalculator.addBasicFactor("Sex", "0");
        tCalculator.addBasicFactor("Appage", "67");
        String GAresult = tCalculator.calculate();
        logger.debug(GAresult);
        MMap mMap = new MMap();
        mMap.put(new String("1"), new String("DELETE"));
        mMap.put(new String("1"), new String("DELETE"));
    }
}
