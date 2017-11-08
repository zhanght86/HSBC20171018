/**
 * 
 */
package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPContStateDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.operfee.RnAccountDeal;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
/**
 * @author LH  恢复交费  add 2008-11-23
 *
 */
public class PEdorHJConfirmBL implements EdorConfirm {
	private static Logger logger = Logger.getLogger(PEdorHJConfirmBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();


    /** 数据操作字符串 */
    private String mOperate;
    String sCurrentDate = PubFun.getCurrentDate();
    String sCurrentTime = PubFun.getCurrentTime();
    private LPContSchema mLPContSchema = new LPContSchema();
    private LCContSchema mLCContSchema = new LCContSchema();
    private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();
    private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
//  新增加进入帐户
    private LCInsureAccClassFeeSet mLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
    private LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
    private LCInsureAccFeeSet mLCInsureAccFeeSet =new LCInsureAccFeeSet();
    private LCInsureAccClassSet mLCInsureAccClassSet = new LCInsureAccClassSet();
    private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
    private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();
    /** 判断险种是投连还是万能标志 1 投连 2 万能*/
	private int mInsureAccRisk = 0;
    
    private GlobalInput mGlobalInput = new GlobalInput();
    private MMap map = new MMap();
    private LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
	/**
	 * 
	 */
	public PEdorHJConfirmBL() 
	{
	}
	
	public boolean submitData(VData cInputData, String cOperate)
	{
		//将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()) 
        {
            return false;
        }
        //核心处理
        if (!dealDate())
        {
        	return false;
        }
        
        //准备数据
        if (!prepareOutputData())
        {
        	return false;
        }
		return true;
	}
	
	/**
     * 从输入数据中得到所有对象
     * @return
     */
    private boolean getInputData()
    {
        try 
        {
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema",0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
            tLJAPayPersonSet = (LJAPayPersonSet) mInputData.getObjectByObjectName("LJAPayPersonSet", 0);
        } catch (Exception e) 
        {
            CError.buildErr(this, "接收数据失败");
            return false;
        }

        return true;
    }
    
    /**
     * 核心处理
     * @return
     */
    private boolean dealDate()
    {
    	
    	
    	// 因为投连和万能附件险的处理不一样，所以这里判断一下,投连返回 1, 万能返回 2
		mInsureAccRisk = BqNameFun.isInsureAccRisk(mLPEdorItemSchema.getContNo(), null);
        if (mInsureAccRisk == -1)
        {
            CError.buildErr(this, "判断险种是否包含投连险,万能险发生异常！");
            return false;
        } 
    	Reflections tRef = new Reflections();
		LPPolSet aLPPolSet = new LPPolSet();
		LCPolSet aLCPolSet = new LCPolSet();
		LPDutySet aLPDutySet = new LPDutySet();
		LCDutySet aLCDutySet = new LCDutySet();
		LPGetSet aLPGetSet = new LPGetSet();
		LCGetSet aLCGetSet = new LCGetSet();
		LPPremSet aLPPremSet = new LPPremSet();
		LCPremSet aLCPremSet = new LCPremSet();
    	LPPolDB tLPPolDB = new LPPolDB();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setSchema(tLPPolSchema);
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet.size() < 1)
		{
			CError tError = new CError();
			tError.buildErr(this, "查询险种表出错!");
			return false;
		}
		// 进行红利补派
		// for (int i = 1; i <= tLPPolSet.size(); i++)
		// {
		// tDispatchBonusBL.callPatchBonus(tLPPolSet.get(i).getPolNo(), mLPEdorItemSchema.getEdorValiDate());
		// if (tDispatchBonusBL.mErrors.needDealError())
		// {
		// mErrors.copyAllErrors(tDispatchBonusBL.mErrors);
		// mErrors.addOneError("进行红利补派失败!");
		// return false;
		// }
		// }
		String APolNo = "";
		for (int i = 1; i <= tLPPolSet.size(); i++)
		{
			if (i == 1)
			{
				APolNo = "'" + tLPPolSet.get(i).getPolNo() + "'";
			}
			else
			{
				APolNo += ",'" + tLPPolSet.get(i).getPolNo() + "'";
			}
			LCPolSchema aLCPolSchema = new LCPolSchema();
			LPPolSchema aLPPolSchema = new LPPolSchema();
			LPPolSchema cLPPolSchema = new LPPolSchema();
			aLPPolSchema.setSchema(tLPPolSet.get(i));
			tRef.transFields(aLCPolSchema, aLPPolSchema);
			aLCPolSchema.setModifyDate(PubFun.getCurrentDate());
			aLCPolSchema.setModifyTime(PubFun.getCurrentTime());
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tempLCPolSet = new LCPolSet();
			tLCPolDB.setPolNo(aLPPolSchema.getPolNo());
			tempLCPolSet = tLCPolDB.query();
			if(tempLCPolSet.size()>=1)
			{
				tRef.transFields(cLPPolSchema,tempLCPolSet.get(1));
				cLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				cLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			}
			aLPPolSet.add(cLPPolSchema);
			aLCPolSet.add(aLCPolSchema);
			
			
			// 险种责任表
			LPDutyDB tLPDutyDB = new LPDutyDB();
			LPDutySchema tLPDutySchema = new LPDutySchema();
			LPDutySet tLPDutySet = new LPDutySet();
			tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPDutySchema.setPolNo(aLCPolSchema.getPolNo());
			tLPDutyDB.setSchema(tLPDutySchema);
			tLPDutySet = tLPDutyDB.query();
			for (int m = 1; m <= tLPDutySet.size(); m++)
			{
				LCDutySchema aLCDutySchema = new LCDutySchema();
				LPDutySchema aLPDutySchema = new LPDutySchema();
				// tLPDutySet.get(i).setPaytoDate(mPaytoDate);
				aLPDutySchema = tLPDutySet.get(m);
				tRef.transFields(aLCDutySchema, aLPDutySchema);
				aLCDutySchema.setModifyDate(PubFun.getCurrentDate());
				aLCDutySchema.setModifyTime(PubFun.getCurrentTime());
				LCDutyDB tLCDutyDB = new LCDutyDB();
				LCDutySet tempLCDutySet = new LCDutySet();
				tLCDutyDB.setPolNo(aLPDutySchema.getPolNo());
				tLCDutyDB.setDutyCode(aLPDutySchema.getDutyCode());
				tempLCDutySet = tLCDutyDB.query();
				if(tempLCDutySet.size()>=1)
				{
					tRef.transFields(aLPDutySchema, tempLCDutySet.get(1));
					aLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					aLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
				}
				aLPDutySet.add(aLPDutySchema);
				aLCDutySet.add(aLCDutySchema);
			}
			map.put(aLPDutySet, "DELETE&INSERT");
			map.put(aLCDutySet, "DELETE&INSERT");
			
			
			// 险种责任表
			LPGetDB tLPGetDB = new LPGetDB();
			LPGetSchema tLPGetSchema = new LPGetSchema();
			LPGetSet tLPGetSet = new LPGetSet();
			tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPGetSchema.setPolNo(aLCPolSchema.getPolNo());
			tLPGetDB.setSchema(tLPGetSchema);
			tLPGetSet = tLPGetDB.query();

			for (int m = 1; m <= tLPGetSet.size(); m++)
			{
				LCGetSchema aLCGetSchema = new LCGetSchema();
				LPGetSchema aLPGetSchema = new LPGetSchema();
				// tLPGetSet.get(i).setPaytoDate(mPaytoDate);
				aLPGetSchema = tLPGetSet.get(m);
				tRef.transFields(aLCGetSchema, aLPGetSchema);
				aLCGetSchema.setModifyDate(PubFun.getCurrentDate());
				aLCGetSchema.setModifyTime(PubFun.getCurrentTime());
				LCGetDB tLCGetDB = new LCGetDB();
				LCGetSet tempLCGetSet = new LCGetSet();
				tLCGetDB.setPolNo(aLPGetSchema.getPolNo());
				tLCGetDB.setGetDutyCode(aLPGetSchema.getGetDutyCode());
				tLCGetDB.setDutyCode(aLPGetSchema.getDutyCode());
				tempLCGetSet = tLCGetDB.query();
				if(tempLCGetSet.size()>=1)
				{
					tRef.transFields(aLPGetSchema, tempLCGetSet.get(1));
					aLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					aLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				}
				aLPGetSet.add(aLPGetSchema);
				aLCGetSet.add(aLCGetSchema);
			}
			map.put(aLPGetSet, "DELETE&INSERT");
			map.put(aLCGetSet, "DELETE&INSERT");			
			String sPolNo = mLPEdorItemSchema.getPolNo().trim();
			// 保费项表
			LPPremDB tLPPremDB = new LPPremDB();
			LPPremSet tLPPremSet = new LPPremSet();
			LCPremSet tLCPremSet = new LCPremSet();
			tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPremDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPPremDB.setPolNo(aLCPolSchema.getPolNo());
			tLPPremSet = tLPPremDB.query();
			for (int j = 1; j <= tLPPremSet.size(); j++)
			{
				LCPremSchema tLCPremSchema = new LCPremSchema();
				tRef.transFields(tLCPremSchema, tLPPremSet.get(j).getSchema());
				tLCPremSchema.setUrgePayFlag("Y");
				aLCPremSet.add(tLCPremSchema);
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setPolNo(tLCPremSchema.getPolNo());
				tLCPremDB.setPayPlanCode(tLCPremSchema.getPayPlanCode());
				tLCPremDB.setDutyCode(tLCPremSchema.getDutyCode());
				tLCPremSet = tLCPremDB.query();
				if (tLCPremSet != null && tLCPremSet.size() > 0)
				{
					LPPremSchema tLPPremSchema = new LPPremSchema();
					tRef.transFields(tLPPremSchema, tLCPremSet.get(1).getSchema());
					tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					aLPPremSet.add(tLPPremSchema);
				}
			}
			map.put(aLCPremSet, "DELETE&INSERT");
			map.put(aLPPremSet, "DELETE&INSERT");
		}
		map.put(aLPPolSet, "DELETE&INSERT");
		map.put(aLCPolSet, "DELETE&INSERT");
    	
    	//互换LCCont  ,LPPcont
    	LPContDB tLPContDB = new LPContDB();
        tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
        tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
        if (!tLPContDB.getInfo()) {
            mErrors.addOneError("查询更改险种信息失败!");
            return false;
        }
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
        if (!tLCContDB.getInfo()) {
            mErrors.addOneError("查询原险种信息失败!");
            return false;
        }
        PubFun.copySchema(mLPContSchema, tLCContDB.getSchema());
        PubFun.copySchema(mLCContSchema, tLPContDB.getSchema());
        mLCContSchema.setOperator(mGlobalInput.Operator);
        mLCContSchema.setModifyDate(sCurrentDate);
        mLCContSchema.setModifyTime(sCurrentTime);
        
        mLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        mLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
        mLPContSchema.setOperator(mGlobalInput.Operator);
        mLPContSchema.setModifyDate(sCurrentDate);
        mLPContSchema.setModifyTime(sCurrentTime);
        map.put(mLPContSchema, "UPDATE");
        map.put(mLCContSchema, "UPDATE");
        
      //改变保单状态为未正常交费（险种层）
        LPContStateDB tLPContStateDB = new LPContStateDB();
        tLPContStateDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPContStateDB.setEdorType(mLPEdorItemSchema.getEdorType());
        LPContStateSet tLPContStateSet = tLPContStateDB.query();
        if (tLPContStateSet != null && tLPContStateSet.size() > 0)
        {
        	map.put(tLPContStateSet, "DELETE");
            LCContStateSet tLCContStateSet = new LCContStateSet();
            for (int i=1;i<=tLPContStateSet.size();i++)
            {
                LCContStateSchema tLCContStateSchema = new LCContStateSchema();
                PubFun.copySchema(tLCContStateSchema,tLPContStateSet.get(i));
                tLCContStateSchema.setOperator(mGlobalInput.Operator);
                tLCContStateSchema.setModifyDate(sCurrentDate);
                tLCContStateSchema.setModifyTime(sCurrentTime);
                tLCContStateSet.add(tLCContStateSchema);
            }
            map.put(tLCContStateSet, "DELETE&INSERT");
        }

        //将保单状态改为 101（有效－缴费期内）
        String xsql = "update lccont set state = '101' where contno='?contno?'";
        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql(xsql);
        sbv1.put("contno", mLPEdorItemSchema.getContNo());
        map.put(sbv1,"UPDATE");
        
//      处理LASPayPerson数据
		String strSQL = "select getnoticeno from ljsgetendorse where endorsementno = '?endorsementno?' and feeoperationtype = '?feeoperationtype?'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(strSQL);
		sbv2.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		sbv2.put("feeoperationtype", mLPEdorItemSchema.getEdorType());
		ExeSQL tes = new ExeSQL();
		String sGetNoticeNo = tes.getOneValue(sbv2);
		if (sGetNoticeNo == null || sGetNoticeNo.trim().equals(""))
		{
			strSQL = "select getnoticeno from ljsgetendorse where ContNo = '?ContNo?' and SubFeeOperationType = 'SR01' ";
			sbv2=new SQLwithBindVariables();
			sbv2.sql(strSQL);
			sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
			sGetNoticeNo = tes.getOneValue(sbv2);
		}
		if (sGetNoticeNo == null || sGetNoticeNo.trim().equals(""))
		{
			CError.buildErr(this, "查询缴费通知书号码失败!");
			return false;
		}
//		删除在LJFinaConfirm类中为了得到实收号而特别增加的ljagetEndorseSet
		LJSGetEndorseSet tmpSRLJSGetEndorseSet = new LJSGetEndorseSet();
		LJSGetEndorseDB tmpSRLJSGetEndorseDB = new LJSGetEndorseDB();
		tmpSRLJSGetEndorseDB.setContNo(mLPEdorItemSchema.getContNo());
		tmpSRLJSGetEndorseDB.setFeeOperationType(mLPEdorItemSchema.getEdorType());
		tmpSRLJSGetEndorseDB.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		tmpSRLJSGetEndorseDB.setSubFeeOperationType("SR01");
		tmpSRLJSGetEndorseSet = tmpSRLJSGetEndorseDB.query();
		if(tmpSRLJSGetEndorseSet != null && tmpSRLJSGetEndorseSet.size() > 0 ){
			//删除SR特别增加的记录
			map.put(tmpSRLJSGetEndorseSet, "DELETE");
		}
//		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
//		tLJAPayPersonDB.setContNo(mLCContSchema.getContNo());
//		mLJAPayPersonSet = tLJAPayPersonDB.query();
//		判断是否有续期保费
		if(tLJAPayPersonSet != null && tLJAPayPersonSet.size() > 0){
			//保费进入帐户
			for( int i = 1; i <= tLJAPayPersonSet.size(); i++ ){
				LJAPayPersonSet tmpLJAPayPersonSet = new LJAPayPersonSet();
				tmpLJAPayPersonSet.add(tLJAPayPersonSet.get(i));
				if (!DealAccount(tmpLJAPayPersonSet))
		        {
		            return false;
		        }
			}
	        
		}
		// 删除 续期抽件抽出来的记录  add by LH 2009-05-31  begin -----------------
		String sDelLJSpayPerson = "delete from ljspayperson where contno = '?contno?' and paytype = 'ZC' ";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sDelLJSpayPerson);
		sbv3.put("contno", mLCContSchema.getContNo());
		map.put(sbv3, "DELETE");
		String sDelLJSpay = "delete from ljspay where otherno = '?contno?' ";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(sDelLJSpay);
		sbv4.put("contno", mLCContSchema.getContNo());
		map.put(sbv4, "DELETE");
		LCPolDB tLCPolDB = new LCPolDB();
        LCPolSet tLCPolSet = new LCPolSet();
        tLCPolDB.setContNo(mLCContSchema.getContNo());
        tLCPolSet = tLCPolDB.query();
        if (tLCPolSet.size() <= 0)
        {
        	return false;
        }
        
        for (int n = 1; n <= tLCPolSet.size(); n++) 
        {
            LCPolSchema tLCPolSchema = new LCPolSchema();
            tLCPolSchema = tLCPolSet.get(n);
            if (tLCPolSchema.getAppFlag().equals("9")) 
            {
            	SQLwithBindVariables sbv5=new SQLwithBindVariables();
            	sbv5.sql("delete from lcpol where polno = '?polno?'");
            	sbv5.put("polno", tLCPolSchema.getPolNo());
                map.put(sbv5, "DELETE");
            	SQLwithBindVariables sbv6=new SQLwithBindVariables();
            	sbv6.sql("delete from lcduty where polno = '?polno?'");
            	sbv6.put("polno", tLCPolSchema.getPolNo());
                map.put(sbv6, "DELETE");
            	SQLwithBindVariables sbv7=new SQLwithBindVariables();
            	sbv7.sql("delete from lcprem where polno = '?polno?'");
            	sbv7.put("polno", tLCPolSchema.getPolNo());
                map.put(sbv7, "DELETE");
            	SQLwithBindVariables sbv8=new SQLwithBindVariables();
            	sbv8.sql("delete from lcget where polno = '?polno?'");
            	sbv8.put("polno", tLCPolSchema.getPolNo());
                map.put(sbv8, "DELETE");
            }
        }
       // 删除 续期抽件抽出来的记录  add by LH 2009-05-31  end -----------------
    	return true;
    }
    
    /**
	 *  复效的保费进入帐户并且扣初始费用
	 * @return boolean
	 * add on 2008-12-04 HY
	 */
	public boolean DealAccount(LJAPayPersonSet tLJAPayPersonSet)
    {
        RnAccountDeal mRnAccountDeal = new RnAccountDeal();
        VData tVData = new VData();
        tVData.add(mGlobalInput);
        LJAPayPersonSet nLJAPayPersonSet = new LJAPayPersonSet();
        for (int j = 1; j <= tLJAPayPersonSet.size(); j++)
        {
            LJAPayPersonSchema mLJAPayPersonSchema = new LJAPayPersonSchema();
            mLJAPayPersonSchema = tLJAPayPersonSet.get(j);
            logger.debug(mLJAPayPersonSchema.getPayType());
            if (mLJAPayPersonSchema.getPayType().equals("ZC"))
            {
                nLJAPayPersonSet.add(mLJAPayPersonSchema);
            }
        }
        tVData.add(nLJAPayPersonSet);

        if (!mRnAccountDeal.submitData(tVData, ""))
        {
            this.mErrors.copyAllErrors(mRnAccountDeal.mErrors);
            return false;
        }

        /**@todo 生成帐户处理新数据**/

        
        mLCInsureAccClassFeeSet = (LCInsureAccClassFeeSet) mRnAccountDeal.
                                  getResult().
                                  getObjectByObjectName(
                                          "LCInsureAccClassFeeSet", 0);
        mLCInsureAccFeeTraceSet = (LCInsureAccFeeTraceSet) mRnAccountDeal.
                                  getResult().
                                  getObjectByObjectName(
                                          "LCInsureAccFeeTraceSet", 0);
        mLCInsureAccFeeSet = (LCInsureAccFeeSet) mRnAccountDeal.getResult().
                             getObjectByObjectName("LCInsureAccFeeSet", 0);

        mLCInsureAccClassSet = (LCInsureAccClassSet) mRnAccountDeal.getResult().
                               getObjectByObjectName("LCInsureAccClassSet", 0);

        mLCInsureAccTraceSet = (LCInsureAccTraceSet) mRnAccountDeal.getResult().
                               getObjectByObjectName("LCInsureAccTraceSet", 0);

        mLCInsureAccSet = (LCInsureAccSet) mRnAccountDeal.getResult().
                          getObjectByObjectName("LCInsureAccSet", 0);

        if (mLCInsureAccClassFeeSet != null
				&& mLCInsureAccClassFeeSet.size() > 0) 
        {
        	for(int i = 1 ; i <= mLCInsureAccFeeTraceSet.size(); i++)
        	{
        		mLCInsureAccFeeTraceSet.get(i).setPayDate(mLPEdorItemSchema.getEdorAppDate());
        	}
        	for(int i = 1 ; i <= mLCInsureAccTraceSet.size(); i++)
        	{
        		mLCInsureAccTraceSet.get(i).setPayDate(mLPEdorItemSchema.getEdorAppDate());
        		mLCInsureAccTraceSet.get(i).setAccAlterNo(mLPEdorItemSchema.getEdorNo());
        		mLCInsureAccTraceSet.get(i).setAccAlterType("3");
        		mLCInsureAccTraceSet.get(i).setBusyType(mLPEdorItemSchema.getEdorType());
        		if (mInsureAccRisk == 2)
        		{
        			mLCInsureAccTraceSet.get(i).setValueDate(PubFun.getCurrentDate());
        		}
        	}
            //营改增 add zhangyingfeng 2016-07-14
            //价税分离 计算器
            TaxCalculator.calBySchemaSet(mLCInsureAccFeeTraceSet);
            //end zhangyingfeng 2016-07-14
        	map.put(mLCInsureAccFeeTraceSet, "DELETE&INSERT");
        	map.put(mLCInsureAccTraceSet, "DELETE&INSERT");
			//mMap.put(mLCInsureAccClassSet, "UPDATE");
			//mMap.put(mLCInsureAccClassFeeSet, "UPDATE");
			//mMap.put(mLCInsureAccSet, "UPDATE");
			//mMap.put(mLCInsureAccFeeSet, "UPDATE");

		}
        
        return true;
    }
    
    /**
     * 提交数据
     * @return
     */
    private boolean prepareOutputData() 
    {

        mResult.clear();
        mResult.add(map);

        return true;
    }

    /**
     * 返回结果
     */
    public VData getResult() 
	{
        return mResult;
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
