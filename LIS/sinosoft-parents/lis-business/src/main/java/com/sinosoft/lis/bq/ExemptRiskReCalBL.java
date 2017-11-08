package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

/**
 * 附豁保全自动关联重算
 * @author jiaqiangli
 * @version 1.0
 * @date 2008-11-27
 */
public class ExemptRiskReCalBL {
private static Logger logger = Logger.getLogger(ExemptRiskReCalBL.class);

	public CErrors mErrors = new CErrors();
	
	LPPolSchema mLPPolSchema = null;
	
	LPEdorItemSchema mLPEdorItemSchema = null;
	LCPolSchema mExemptLCPolSchema = null;
	
	public double mExemptGetMoney = 0.0;
	public double mExemptCashValue = 0.0; // 现金价值
	public double mExemptScale = 0.0; // 减保比例
	private EdorCalZT mEdorCalZT = null;
	
	private double mExemptPrem = 0.00;

	// 重算后附豁数据
	public LPPolSet aftExemptLPPolSet = new LPPolSet();
	public LPDutySet aftExemptLPDutySet = new LPDutySet();
	public LPPremSet aftExemptLPPremSet = new LPPremSet();
	public LPGetSet aftExemptLPGetSet = new LPGetSet();

	//有附加豁免关联时才调用此函数
	//pLPPolSchema为当前处理的保单险种，也即被豁免的险种
	//pExemptLCPolSchema为豁免险种
	//pLPEdorItemSchema此次保全结构，方便调用BL.queryLPPol必须先取lppol后取lcpol
	/**
	 * @param pLPPolSchema 为当前处理的保单险种，也即被豁免的险种
	 * @param pExemptLCPolSchema 为豁免险种
	 * @param pLPEdorItemSchema 此次保全结构，方便调用BL.queryLPPol必须先取lppol后取lcpol
	 * @param tGlobalInput
	 */
	public ExemptRiskReCalBL(LPPolSchema pLPPolSchema, LCPolSchema pExemptLCPolSchema,LPEdorItemSchema pLPEdorItemSchema,GlobalInput tGlobalInput) {
		mLPPolSchema = pLPPolSchema.getSchema();
		mExemptLCPolSchema = pExemptLCPolSchema.getSchema();
		mLPEdorItemSchema = pLPEdorItemSchema.getSchema();
		mEdorCalZT = new EdorCalZT(tGlobalInput);
	}

	private void calExemptAmnt() {
		double tExemptAmnt = 0.00;

		// 当前处理险种的保费 mExemptPrem
		tExemptAmnt += mLPPolSchema.getPrem();

		// 循环处理其他豁免的险种 注意要排除mLPPolSchema.getPolNo()
		String tExemptSQL = "select polno from lcpol where contno = '" + "?contno?" + "' and polno <> '"
				+ "?polno?" + "' "
				// 注意豁免的是非趸交件
				+ "and payintv > 0 and riskcode in (select code1 from ldcode1 where codetype = 'freerisk' and code='"
				+ "?code?" + "') ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tExemptSQL);
		sqlbv.put("contno", mLPPolSchema.getContNo());
		sqlbv.put("polno", mLPPolSchema.getPolNo());
		sqlbv.put("code", mExemptLCPolSchema.getRiskCode());
		logger.debug("tExemptSQL" + tExemptSQL);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		int tsize = tSSRS.getMaxRow();
		LPPolSchema tLPPolSchema = null;
		LPPolBL tLPPolBL = new LPPolBL();
		for (int i = 1; i <= tsize; i++) {
			// 此处借用LPEdorItemSchema来取得tableBL数据 变换polno其他信息没有做修改
			mLPEdorItemSchema.setPolNo(tSSRS.GetText(i, 1));
			tLPPolBL.queryLPPol(mLPEdorItemSchema);
			tLPPolSchema = tLPPolBL.getSchema();
			// 这里需要先查此次保全下的lppol表数据，若没有直接取lcpol
			// 累加的被豁免的保费和
			tExemptAmnt += tLPPolSchema.getPrem();
		}
		mExemptPrem += tExemptAmnt;
	}
    //add by xiongzh 通用方法计算PT有问题，为了不影响其他保全，单建此方法
	private void PTcalExemptAmnt() {
		double tExemptAmnt = 0.00;

		// 当前处理险种的保费 mExemptPrem
		tExemptAmnt += mLPPolSchema.getPrem();

		// 循环处理其他豁免的险种 注意要排除mLPPolSchema.getPolNo()
		String tExemptSQL = "select polno from lcpol where contno = '" + "?contno?" + "' and polno <> '"
				+ "?polno?" + "' "
				// 注意豁免的是非趸交件
				+ "and payintv > 0 and riskcode in (select code1 from ldcode1 where codetype = 'freerisk' and code='"
				+ "?code?" + "') ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tExemptSQL);
		sqlbv.put("contno", mLPPolSchema.getContNo());
		sqlbv.put("polno", mLPPolSchema.getPolNo());
		sqlbv.put("code", mExemptLCPolSchema.getRiskCode());
		logger.debug("tExemptSQL" + tExemptSQL);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		int tsize = tSSRS.getMaxRow();
		LPPolSchema tLPPolSchema = null;
		LPPolBL tLPPolBL = new LPPolBL();
		for (int i = 1; i <= tsize; i++) {
			// 此处借用LPEdorItemSchema来取得tableBL数据 变换polno其他信息没有做修改
            //这里需要先查此次保全下的lppol表数据，若没有直接取lcpol
			LPPolDB xLPPolDB = new LPPolDB();
			xLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			xLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
			xLPPolDB.setPolNo(tSSRS.GetText(i, 1));
			if(xLPPolDB.getInfo())  //P表有记录
			{
				tLPPolSchema=xLPPolDB.getSchema();
			}
			else  //取c表
			{
				LCPolSchema xLCPolSchema = new LCPolSchema();
				LPPolSchema xLPPolSchema = new LPPolSchema();
				LCPolDB xLCPolDB = new LCPolDB();
				xLCPolDB.setPolNo(tSSRS.GetText(i, 1));
				xLCPolSchema = xLCPolDB.query().get(1);
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(xLPPolSchema,xLCPolSchema);

				xLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				xLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPolSchema = xLPPolSchema;
			}			
			// 累加的被豁免的保费和
			tExemptAmnt += tLPPolSchema.getPrem();
		}
		mExemptPrem += tExemptAmnt;
	}
	public boolean reCalExempt() {
		Reflections mReflections = new Reflections();
		try {
			//先取出附加豁免险的保额=被豁免的保费和 mExemptPrem
			//double tExemptPrem = 0.00;
			//需要累加的话
			calExemptAmnt();
			
			//recal豁免
			//取得豁免险的信息
			LPPolSchema tExemptLPPolSchema = new LPPolSchema();
			mReflections.transFields(tExemptLPPolSchema, mExemptLCPolSchema);

			// 先保存附加豁免的减保比例
			// add by jiaqiangli 2009-07-21 减保比例
			mExemptScale = (tExemptLPPolSchema.getAmnt()-mExemptPrem) / tExemptLPPolSchema.getAmnt();
			// 计算附加豁免的现金价值
			mExemptCashValue = mEdorCalZT.getCashValue(tExemptLPPolSchema.getPolNo(), mLPEdorItemSchema
					.getEdorAppDate());
			logger.debug("mExemptCashValue" + mExemptCashValue);
			if (mExemptCashValue < 0) {
				mErrors.copyAllErrors(mEdorCalZT.mErrors);
				mErrors.addOneError("附加豁免计算现金价值失败!");
				return false;
			}
			// PT应退金额为当年现金价值乘以比例系数
			mExemptGetMoney = mExemptCashValue * mExemptScale;
			logger.debug("mExemptGetMoney" + mExemptGetMoney);

			// 修改豁免险的保额信息
			tExemptLPPolSchema.setAmnt(mExemptPrem);
			
			
			mLPEdorItemSchema.setPolNo(tExemptLPPolSchema.getPolNo());
			//调用保全重算类
			ReCalBL tReCalBL = new ReCalBL(tExemptLPPolSchema, mLPEdorItemSchema);
			
			// 准备重算需要的保单表数据
			LCPolBL tLCPolBL = tReCalBL.getRecalPol(tExemptLPPolSchema);
			// 准备重算需要的责任表数据
			LCDutyBLSet tLCDutyBLSet = tReCalBL.getRecalDuty(mLPEdorItemSchema);
			// 准备重算需要的保费项表数据
			LCPremSet tLCPermSet = tReCalBL.getRecalPrem(mLPEdorItemSchema);
			// 准备重算需要的领取项表数据
			LCGetBLSet tLCGetBLSet = tReCalBL.getRecalGet(mLPEdorItemSchema);
			
			//lcduty表的保额
			tLCDutyBLSet.get(1).setAmnt(tExemptLPPolSchema.getAmnt());
			
			// 重算豁免保单
			if (!tReCalBL.recalWithData(tLCPolBL, tLCDutyBLSet, tLCPermSet,tLCGetBLSet, mLPEdorItemSchema)) {
				this.mErrors.copyAllErrors(tReCalBL.mErrors);
				CError.buildErr(this, "豁免保费重算失败");
				return false;
			}
			
			//保存重算后的保费项信息
			aftExemptLPPolSet = tReCalBL.aftLPPolSet;
			aftExemptLPDutySet = tReCalBL.aftLPDutySet;
			aftExemptLPPremSet = tReCalBL.aftLPPremSet;
			aftExemptLPGetSet = tReCalBL.aftLPGetSet;
		} 
		catch (Exception ex) {
			ex.printStackTrace(System.out);
			CError.buildErr(this, "豁免保全重算失败");
			return false;
		}
		return true;
	}
	public boolean PTreCalExempt() {
		Reflections mReflections = new Reflections();
		try {
			//先取出附加豁免险的保额=被豁免的保费和 mExemptPrem
			//double tExemptPrem = 0.00;
			//需要累加的话
			PTcalExemptAmnt();
			
			//recal豁免
			//取得豁免险的信息
			LPPolSchema tExemptLPPolSchema = new LPPolSchema();
			mReflections.transFields(tExemptLPPolSchema, mExemptLCPolSchema);

			// 先保存附加豁免的减保比例
			// add by jiaqiangli 2009-07-21 减保比例
			mExemptScale = (tExemptLPPolSchema.getAmnt()-mExemptPrem) / tExemptLPPolSchema.getAmnt();
			// 计算附加豁免的现金价值
			mExemptCashValue = mEdorCalZT.getCashValue(tExemptLPPolSchema.getPolNo(), mLPEdorItemSchema
					.getEdorAppDate());
			logger.debug("mExemptCashValue" + mExemptCashValue);
			if (mExemptCashValue < 0) {
				mErrors.copyAllErrors(mEdorCalZT.mErrors);
				mErrors.addOneError("附加豁免计算现金价值失败!");
				return false;
			}
			// PT应退金额为当年现金价值乘以比例系数
			mExemptGetMoney = mExemptCashValue * mExemptScale;
			logger.debug("mExemptGetMoney" + mExemptGetMoney);

			// 修改豁免险的保额信息
			tExemptLPPolSchema.setAmnt(mExemptPrem);
			
			
			mLPEdorItemSchema.setPolNo(tExemptLPPolSchema.getPolNo());
			//调用保全重算类
			ReCalBL tReCalBL = new ReCalBL(tExemptLPPolSchema, mLPEdorItemSchema);
			
			// 准备重算需要的保单表数据
			LCPolBL tLCPolBL = tReCalBL.getRecalPol(tExemptLPPolSchema);
			// 准备重算需要的责任表数据
			LCDutyBLSet tLCDutyBLSet = tReCalBL.getRecalDuty(mLPEdorItemSchema);
			// 准备重算需要的保费项表数据
			LCPremSet tLCPermSet = tReCalBL.getRecalPrem(mLPEdorItemSchema);
			// 准备重算需要的领取项表数据
			LCGetBLSet tLCGetBLSet = tReCalBL.getRecalGet(mLPEdorItemSchema);
			
			//lcduty表的保额
			tLCDutyBLSet.get(1).setAmnt(tExemptLPPolSchema.getAmnt());
			
			// 重算豁免保单
			if (!tReCalBL.recalWithData(tLCPolBL, tLCDutyBLSet, tLCPermSet,tLCGetBLSet, mLPEdorItemSchema)) {
				this.mErrors.copyAllErrors(tReCalBL.mErrors);
				CError.buildErr(this, "豁免保费重算失败");
				return false;
			}
			
			//保存重算后的保费项信息
			aftExemptLPPolSet = tReCalBL.aftLPPolSet;
			aftExemptLPDutySet = tReCalBL.aftLPDutySet;
			aftExemptLPPremSet = tReCalBL.aftLPPremSet;
			aftExemptLPGetSet = tReCalBL.aftLPGetSet;
		} 
		catch (Exception ex) {
			ex.printStackTrace(System.out);
			CError.buildErr(this, "豁免保全重算失败");
			return false;
		}
		return true;
	}
}
