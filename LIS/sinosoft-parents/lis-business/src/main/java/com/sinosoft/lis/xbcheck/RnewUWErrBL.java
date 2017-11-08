package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWErrorDB;
import com.sinosoft.lis.db.RnewIndUWErrorDB;
import com.sinosoft.lis.db.LCUWErrorDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LCCUWErrorSet;
import com.sinosoft.lis.vschema.RnewIndUWErrorSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;



/**
 * <p>
 * Title: Web业务系统保单查询部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @version 6.0
 */
public class RnewUWErrBL {
private static Logger logger = Logger.getLogger(RnewUWErrBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */

	LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();
	RnewIndUWErrorSet mRnewIndUWErrorSet = new RnewIndUWErrorSet();
	LCCUWErrorSet mLCCUWErrorSet = new LCCUWErrorSet();
	/** 责任代码字符串 */
	public String mDutyCodeString = "";
	
	LCUWErrorSet mUpdateLCUWErrorSet = new LCUWErrorSet();
	RnewIndUWErrorSet mUpdateRnewIndUWErrorSet = new RnewIndUWErrorSet();
	LCCUWErrorSet mUpdateLCCUWErrorSet = new LCCUWErrorSet();
	/** 返回字符串 */
	public String mResultString = "";

	public RnewUWErrBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---getInputData---");

		// 进行业务处理
		if(!this.queryDetail())
		{
			return false;
		}

		prepareOutputData();
		
		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(this.mResult, "Insert")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError.buildErr(this, "数据提交失败!");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		try {
			// 获取数据
			this.mLCCUWErrorSet = (LCCUWErrorSet)cInputData.getObjectByObjectName("LCCUWErrorSet",0);
			this.mLCUWErrorSet = (LCUWErrorSet)cInputData.getObjectByObjectName("LCUWErrorSet",0);
			this.mRnewIndUWErrorSet = (RnewIndUWErrorSet)cInputData.getObjectByObjectName("RnewIndUWErrorSet",0);
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this,e.toString());
			return false;
		}
	}



	/**
	 * 查询保单明细信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean queryDetail() {
		// 核保轨迹信息
		
		for(int i=1;i<=this.mLCUWErrorSet.size();i++)
		{
			LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB();
			tLCUWErrorDB.setProposalNo(mLCUWErrorSet.get(i).getProposalNo());
			tLCUWErrorDB.setSerialNo(mLCUWErrorSet.get(i).getSerialNo());
			tLCUWErrorDB.setUWNo(mLCUWErrorSet.get(i).getUWNo());
			if(!tLCUWErrorDB.getInfo())
			{
				CError.buildErr(this,"查询险种核保错误信息出错!");
				return false;
			}
			if(tLCUWErrorDB.getSugPassFlag()==null||tLCUWErrorDB.getSugPassFlag().equals("N"))
			{
				tLCUWErrorDB.setSugPassFlag("Y");
				this.mUpdateLCUWErrorSet.add(tLCUWErrorDB);
			}
		}
		
		for(int i=1;i<=this.mRnewIndUWErrorSet.size();i++)
		{
			RnewIndUWErrorDB tRnewIndUWErrorDB = new RnewIndUWErrorDB();
			tRnewIndUWErrorDB.setSerialNo(mRnewIndUWErrorSet.get(i).getSerialNo());
			tRnewIndUWErrorDB.setUWNo(mRnewIndUWErrorSet.get(i).getUWNo());
			if(!tRnewIndUWErrorDB.getInfo())
			{
				CError.buildErr(this,"查询被保人核保错误信息出错!");
				return false;
			}
			if(tRnewIndUWErrorDB.getSugPassFlag()==null||tRnewIndUWErrorDB.getSugPassFlag().equals("N"))
			{
				tRnewIndUWErrorDB.setSugPassFlag("Y");
				this.mUpdateRnewIndUWErrorSet.add(tRnewIndUWErrorDB);
			}
		}
		
		for(int i=1;i<=this.mLCCUWErrorSet.size();i++)
		{
			LCCUWErrorDB tLCCUWErrorDB = new LCCUWErrorDB();
			tLCCUWErrorDB.setContNo(mLCCUWErrorSet.get(i).getProposalContNo());
			tLCCUWErrorDB.setSerialNo(mLCCUWErrorSet.get(i).getSerialNo());
			tLCCUWErrorDB.setUWNo(mLCCUWErrorSet.get(i).getUWNo());
			if(!tLCCUWErrorDB.getInfo())
			{
				CError.buildErr(this,"查询合同核保错误信息出错!");
				return false;
			}
			if(tLCCUWErrorDB.getSugPassFlag()==null||tLCCUWErrorDB.getSugPassFlag().equals("N"))
			{
				tLCCUWErrorDB.setSugPassFlag("Y");
				this.mUpdateLCCUWErrorSet.add(tLCCUWErrorDB);
			}
		}
	
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		try {
			MMap tMMap = new MMap();
			if(this.mUpdateLCCUWErrorSet.size()>0)
			{
				tMMap.put(mUpdateLCCUWErrorSet, "UPDATE");
			}
			if(this.mUpdateRnewIndUWErrorSet.size()>0)
			{
				tMMap.put(mUpdateRnewIndUWErrorSet, "UPDATE");
			}
			if(this.mUpdateLCUWErrorSet.size()>0)
			{
				tMMap.put(mUpdateLCUWErrorSet, "UPDATE");
			}
		this.mResult.add(tMMap);
			//mResult.add(mLCUWSubSet);
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this,"在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}



}
