package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 业务系统团体延长保险期间功能部分</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @version 1.0
 */
public class GEdorEIDetailBL
{
private static Logger logger = Logger.getLogger(GEdorEIDetailBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 往后面传输数据的容器 */
    private VData mOutputData;

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;

    /** 业务数据 */
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    private LPGrpPolSchema mLPGrpPolSchema = new LPGrpPolSchema();
    //恢复后的终止日期
    private String mEndDate = "";
    private String mOldEndDate = "";
    private String mCValiDate = "";
    private int mInsuYear = 0;
    private String mInsuYearFlag = "";
    /** 修改数据 */
    private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
    private LPPolSet mLPPolSet = new LPPolSet();
    private LPDutySet mLPDutySet = new LPDutySet();
    private LPPremSet mLPPremSet = new LPPremSet();
    private LPGetSet mLPGetSet = new LPGetSet();
    private LPContSet mLPContSet = new  LPContSet();
    
    private String mEdorNo = "";
    private String mEdorType = "";
    private String mGrpContNo = "";
    private String mEdorAcceptNo = "";

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private Reflections mRef = new Reflections();
    private MMap mMap = new MMap();

    public GEdorEIDetailBL()
    {
    }

    public void setOperate(String cOperate)
    {
        this.mOperate = cOperate;
    }

    public String getOperate()
    {
        return this.mOperate;
    }

    /**
 * 传输数据的公共方法
 * @param: cInputData 输入的数据
 *         cOperate 数据操作
 * @return:
 */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("==> Begin to EIDetailBL");

        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.setOperate(cOperate);

        //得到外部传入的数据,将数据备份到本类中
        if(!getInputData())
        {
        	return false;
        }

        //数据校验操作（checkdata)
        if (!checkData())
        {
            return false;
        }


        if (this.getOperate().equals("INSERT||MAIN"))
        {
            logger.debug("==> operate:" + this.getOperate());

            //数据复杂业务处理(dealData())
            if (!dealData())
            {
                return false;
            }

            //数据准备操作（preparedata())
            if (!prepareData())
            {
                return false;
            }

            PubFun.out(this, "提交后台,更新数据库数据!\n");
            
            
            mInputData.clear();
            mInputData.add(mMap);
            PubSubmit tPubSubmit = new  PubSubmit();
            if(!tPubSubmit.submitData(mInputData,mOperate))
            {
                CError tError = new CError();
                tError.moduleName = "PGrpEdorCTDetailBL";
                tError.functionName = "checkData";
                tError.errorMessage = "提交数据失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            mInputData.clear();

        }
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    /**
 * 从输入数据中得到所有对象
 *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
 */
    private boolean getInputData()
    {
    	mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
            .getObjectByObjectName("LPGrpEdorItemSchema", 0);

        mLPGrpPolSchema = (LPGrpPolSchema) mInputData.getObjectByObjectName("LPGrpPolSchema",
                0);

        mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput", 0));
        if (mGlobalInput == null || mGlobalInput.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "PGrpEdorEIDetailBL";
            tError.functionName = "getInput";
            tError.errorMessage = "GlobalInput数据为空!";
            this.mErrors.addOneError(tError);

            return false;
        }
        if (mLPGrpPolSchema == null)
        {
            CError tError = new CError();
            tError.moduleName = "PGrpEdorEIDetailBL";
            tError.functionName = "getInput";
            tError.errorMessage = "LPGrpPolSchema 数据为空!!";
            this.mErrors.addOneError(tError);

            return false;
        }
        if (mLPGrpEdorItemSchema == null)
        {
            CError tError = new CError();
            tError.moduleName = "PGrpEdorEIDetailBL";
            tError.functionName = "getInput";
            tError.errorMessage = "LPGrpEdorItemSchema 数据为空!";
            this.mErrors.addOneError(tError);

            return false;
        }
        mEdorAcceptNo = mLPGrpEdorItemSchema.getEdorAcceptNo();
        mEdorNo = mLPGrpEdorItemSchema.getEdorNo();
        mEdorType = mLPGrpEdorItemSchema.getEdorType();
        mEndDate = mLPGrpPolSchema.getPayEndDate();
        mGrpContNo = mLPGrpPolSchema.getGrpContNo();
        
        return true;

    }

    /**
 * 校验传入的数据的合法性
 * 输出：如果发生错误则返回false,否则返回true
 */
    private boolean checkData()
    {
//        LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
//        tLCGrpPolDB.setGrpPolNo(mLPGrpPolSchema.getGrpPolNo());
//        if (!tLCGrpPolDB.getInfo())
//        {
//            CError.buildErr(this, "团单查询失败!");
//            return false;
//        }
//        LCGrpPolSchema tLCGrpPolSchema = tLCGrpPolDB.getSchema();
//        String tRiskCode = tLCGrpPolSchema.getRiskCode();
//        LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
//        tLMRiskAppDB.setRiskCode(tRiskCode);
//        if (!tLMRiskAppDB.getInfo())
//        {
//            CError.buildErr(this, "险种定义信息查询失败!");
//            return false;
//        }
//        LMRiskAppSchema tLMRiskAppSchema = tLMRiskAppDB.getSchema();
//        String tSubRiskFlag = tLMRiskAppSchema.getSubRiskFlag();
//        if(tSubRiskFlag.equals("S"))
//        {
//        	LMRiskRelaDB tLMRiskRelaDB = new LMRiskRelaDB();
//        	tLMRiskRelaDB.setRelaRiskCode(tRiskCode);
//        	tLMRiskRelaDB.setRelaCode("01");
//        	LMRiskRelaSet tLMRiskRelaSet = tLMRiskRelaDB.query();
//        	
//        	if(tLMRiskRelaSet != null && tLMRiskRelaSet.size()>0 ){
//        		LMRiskRelaSchema tLMRiskRelaSchema = null;
//        		for(int i = 1;i<=tLMRiskRelaSet.size();i++)
//        		{
//        			tLMRiskRelaSchema = tLMRiskRelaSet.get(i);
//        			String tMainRiskCode = tLMRiskRelaSchema.getRiskCode();
//        			LCGrpPolDB cLCGrpPolDB = new LCGrpPolDB();
//        			cLCGrpPolDB.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
//        			cLCGrpPolDB.setRiskCode(tMainRiskCode);
//        			LCGrpPolSet cLCGrpPolSet = cLCGrpPolDB.query();
//        			if(cLCGrpPolSet != null && cLCGrpPolSet.size()>0)
//        			{
//        				LCGrpPolSchema cLCGrpPolSchema = cLCGrpPolSet.get(1);
//        				String cEndDate = cLCGrpPolSchema.getPayEndDate();
//        				int intv = PubFun.calInterval(mEndDate, cEndDate, "D");
//        				if(intv < 0)
//        				{
//        					CError.buildErr(this, "该附加险变更的保险期间不可以超过该合同下主险保险期间");
//        		            return false;
//        				}
//        			}
//        		}
//        	}
//        }
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        tLPGrpEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
        tLPGrpEdorItemDB.setEdorNo(mEdorNo);
        tLPGrpEdorItemDB.setEdorType(mEdorType);
        tLPGrpEdorItemDB.setGrpContNo(mLPGrpPolSchema.getGrpContNo());
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
        mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1).getSchema());

        return true;
    }

    /**
 * 复杂业务处理
 * @return
 */
    private boolean dealData()
    {
//    	ExeSQL tExeSQL=new ExeSQL();
//    	SSRS tSSRS=tExeSQL.execSQL("select grppolno from lcgrppol where prtno='"+mPrtNo+"'");
//    	
//    	for (int i = 1; i <= tSSRS.MaxRow; i++) {
//			String grppolno=tSSRS.GetText(i, 1);
//			LPGrpEdorMainSchema tLPGrpEdorMainSchema=mLPGrpEdorMainSchema.getSchema();
//			tLPGrpEdorMainSchema.setGrpPolNo(grppolno);
//			LPGrpPolBL tLPGrpPolBL = new LPGrpPolBL();
//			if (!tLPGrpPolBL.queryLPGrpPol(tLPGrpEdorMainSchema))
//	        {
//	            CError.buildErr(this, "查询团单信息失败!");
//	            return false;
//	        }
//			LPGrpPolSchema tLPGrpPolSchema=tLPGrpPolBL.getSchema();
//			tLPGrpPolSchema.setPayEndDate(mLPGrpPolSchema.getPayEndDate());
//			tLPGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
//			tLPGrpPolSchema.setModifyTime(PubFun.getCurrentTime());			
//			outLPGrpPolSet.add(tLPGrpPolSchema);
//		}
    	
    	//deal grppol start...............
    	LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
    	tLCGrpPolDB.setGrpContNo(mGrpContNo);
    	LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
    	if(tLCGrpPolSet == null || tLCGrpPolSet.size()<1)
    	{
    		  CError.buildErr(this, "团单保单查询失败!");
              return false;
    	}
    	LCGrpPolSchema tLCGrpPolSchema = null;
    	LPGrpPolSchema tLPGrpPolSchema = null;
    	for(int i = 1;i<=tLCGrpPolSet.size();i++){
    		tLCGrpPolSchema = tLCGrpPolSet.get(i);
    		tLPGrpPolSchema = new LPGrpPolSchema();
    		mRef.transFields(tLPGrpPolSchema, tLCGrpPolSchema);
    		tLPGrpPolSchema.setEdorNo(mEdorNo);
    		tLPGrpPolSchema.setEdorType(mEdorType);
    		tLPGrpPolSchema.setPayEndDate(mEndDate);
    		tLPGrpPolSchema.setPaytoDate(mEndDate);
    		tLPGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
    		tLPGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
    		tLPGrpPolSchema.setOperator(mGlobalInput.Operator);
    		mLPGrpPolSet.add(tLPGrpPolSchema);
    		mCValiDate = tLCGrpPolSchema.getCValiDate();
    		mOldEndDate = tLCGrpPolSchema.getPayEndDate();
    		PubFun.out(this, "==> 团单(" + mGrpContNo + ")原保险止期为" + mOldEndDate);
    		PubFun.out(this, "==> 团单(" + mGrpContNo + ")现保险止期为" + mEndDate);

    		mInsuYear = PubFun.calInterval2(mCValiDate, mEndDate, "M");
    		mInsuYearFlag = "M";
    		if ((mInsuYear % 12) == 0)
    		{
    			mInsuYear = mInsuYear / 12;
    			mInsuYearFlag = "Y";
    		}

    		PubFun.out(this, "计算新的保险期间为" + mInsuYear + mInsuYearFlag);
    		//处理该险种下的所有个单 --start
    		LCPolDB tLCPolDB = new LCPolDB();
    		tLCPolDB.setGrpPolNo(tLPGrpPolSchema.getGrpPolNo());
    		LCPolSet tLCPolSet = tLCPolDB.query();
    		if ((tLCPolSet == null) || (tLCPolSet.size() <= 0))
    		{
    			CError.buildErr(this, "团单下个人保单查询失败!");
    			return false;
    		}
    		LCPolSchema tLCPolSchema = null;
    		LPPolSchema tLPPolSchema = null;
    		for (int m = 1; m <= tLCPolSet.size(); m++)
    		{
    			//修改个人保单表信息
    			tLPPolSchema = new LPPolSchema();
    			tLCPolSchema = tLCPolSet.get(m);
    			mRef.transFields(tLPPolSchema, tLCPolSchema);
    			tLPPolSchema.setEdorNo(mEdorNo);
    			tLPPolSchema.setEdorType(mEdorType);
    			tLPPolSchema.setPayEndDate(mEndDate);
    			tLPPolSchema.setEndDate(mEndDate);
    			tLPPolSchema.setPaytoDate(mEndDate);
    			tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
    			tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
    			tLPPolSchema.setInsuYear(mInsuYear);
    			tLPPolSchema.setInsuYearFlag(mInsuYearFlag);
    			tLPPolSchema.setPayEndYear(mInsuYear);
    			tLPPolSchema.setPayEndYearFlag(mInsuYearFlag);

    			mLPPolSet.add(tLPPolSchema);

    			//duty deal start...............
    			LCDutyDB tLCDutyDB = new LCDutyDB();
    			tLCDutyDB.setPolNo(tLPPolSchema.getPolNo());
    			LCDutySet tLCDutySet = tLCDutyDB.query();
    			LCDutySchema tLCDutySchema = null;
    			LPDutySchema tLPDutySchema = null;
    			for (int j = 1; j <= tLCDutySet.size(); j++)
    			{
    				tLPDutySchema = new LPDutySchema();
    				tLCDutySchema = tLCDutySet.get(j);
    				mRef.transFields(tLPDutySchema, tLCDutySchema);
    				tLPDutySchema.setEdorNo(mEdorNo);
    				tLPDutySchema.setEdorType(mEdorType);
    				tLPDutySchema.setPayEndDate(mEndDate);
    				tLPDutySchema.setPaytoDate(mEndDate);
    				tLPDutySchema.setEndDate(mEndDate);
    				tLPDutySchema.setModifyDate(PubFun.getCurrentDate());
    				tLPDutySchema.setModifyTime(PubFun.getCurrentTime());

    				tLPDutySchema.setInsuYear(mInsuYear);
    				tLPDutySchema.setInsuYearFlag(mInsuYearFlag);
    				tLPDutySchema.setPayEndYear(mInsuYear);
    				tLPDutySchema.setPayEndYearFlag(mInsuYearFlag);
    				mLPDutySet.add(tLPDutySchema);
    			}

    			// duty deal end...........

    			// prem deal start...............
    			LCPremDB tLCPremDB = new LCPremDB();
    			tLCPremDB.setPolNo(tLPPolSchema.getPolNo());
    			LCPremSet tLCPremSet = tLCPremDB.query();
    			LPPremSchema tLPPremSchema = null;
    			for (int j = 1; j <= tLCPremSet.size(); j++)
    			{
    				tLPPremSchema = new LPPremSchema();
    				mRef.transFields(tLPPremSchema, tLCPremSet.get(j));
    				tLPPremSchema.setEdorNo(mEdorNo);
    				tLPPremSchema.setEdorType(mEdorType);
    				tLPPremSchema.setPayEndDate(mEndDate);
    				tLPPremSchema.setPaytoDate(mEndDate);
    				tLPPremSchema.setModifyDate(PubFun.getCurrentDate());
    				tLPPremSchema.setModifyTime(PubFun.getCurrentTime());

    				mLPPremSet.add(tLPPremSchema);
    			}

    			//prem deal end...............

    			//get deal start...............
    			LCGetDB tLCGetDB = new LCGetDB();
    			tLCGetDB.setPolNo(tLPPolSchema.getPolNo());
    			LCGetSet tLCGetSet = tLCGetDB.query();
    			LPGetSchema tLPGetSchema  = null;
    			for (int j = 1; j <= tLCGetSet.size(); j++)
    			{
    				tLPGetSchema = new LPGetSchema();
    				mRef.transFields(tLPGetSchema, tLCGetSet.get(j));
    				tLPGetSchema.setEdorNo(mEdorNo);
    				tLPGetSchema.setEdorType(mEdorType);
    				tLPGetSchema.setGetEndDate(mEndDate);

    				tLPGetSchema.setModifyDate(PubFun.getCurrentDate());
    				tLPGetSchema.setModifyTime(PubFun.getCurrentTime());

    				mLPGetSet.add(tLPGetSchema);
    			}
    			//get deal end...............
    		}
    		//deal grppol end..................

    	}
      	
        
    	 
        
        //cont deal start
        LCContDB tLCContDB = new LCContDB(); 
        tLCContDB.setGrpContNo(mGrpContNo);
        tLCContDB.setAppFlag("1");
        LCContSet tLCContSet = tLCContDB.query();
        if(tLCContSet == null || tLCContSet.size()<1){
			CError.buildErr(this, "团单下个人保单查询失败!");
			return false;
        }
        
        //TODO
        LCContSchema tLCContSchema = null;
        LPContSchema tLPContSchema = null;
        for(int i = 1 ; i<=tLCContSet.size();i++)
        {
        	tLCContSchema = tLCContSet.get(i);
        	tLPContSchema = new LPContSchema();
        	mRef.transFields(tLPContSchema, tLCContSchema);
        	tLPContSchema.setEdorNo(mEdorNo);
        	tLPContSchema.setEdorType(mEdorType);
        	tLPContSchema.setPaytoDate(mEndDate);
        	tLPContSchema.setModifyDate(PubFun.getCurrentDate());
        	tLPContSchema.setModifyTime(PubFun.getCurrentTime());
        	mLPContSet.add(tLPContSchema);
        }
        
        
        mLPGrpEdorItemSchema.setEdorState("1");
        mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
        mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
        return true;
    }

    /**
 * 准备需要保存的数据
 */
    private boolean prepareData()
    {
    	mMap.put(mLPGrpEdorItemSchema, "DELETE&INSERT");
    	mMap.put(mLPGrpPolSet, "DELETE&INSERT");
    	mMap.put(mLPPolSet, "DELETE&INSERT");
    	mMap.put(mLPGetSet, "DELETE&INSERT");
    	mMap.put(mLPDutySet, "DELETE&INSERT");
    	mMap.put(mLPPremSet, "DELETE&INSERT");
    	mMap.put(mLPContSet, "DELETE&INSERT");

    	return true;

    }

    public static void main(String[] args)
    {
        
    }
}
