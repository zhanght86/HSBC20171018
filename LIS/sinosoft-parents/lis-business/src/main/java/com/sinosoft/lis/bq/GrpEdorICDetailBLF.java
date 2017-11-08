package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02
 * @date     : 2006-04-10, 2005-05-19, 2006-08-19
 * @direction: 团单保全客户重要资料变更提交
 ******************************************************************************/

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class GrpEdorICDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(GrpEdorICDetailBLF.class);
	// public GrpEdorICDetailBLF() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	// 输出数据
	private MMap rMap;
	private VData rResultData;
	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> GrpEdorICDetailBLF.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> GrpEdorICDetailBLF.submitData() : 无法获取 InputData 数据！");
			return false;
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";

		// ----------------------------------------------------------------------

		// 业务处理
		if (!dealData())
			return false;
		if (!outputData())
			return false;

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(rResultData, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError.buildErr(this, "数据提交失败!");
			return false;
		}

		// 垃圾处理
		collectGarbage();
		// logger.debug("\t@> GrpEdorICDetailBLF.submitData() 结束");
		return true;
	} // function submitData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> GrpEdorICDetailBLF.dealData() 开始");

		rMap = new MMap();

		// ----------------------------------------------------------------------

		// 特殊: 调用 BLF, 而不是 BL
		PEdorICDetailBLF tPEdorICDetailBLF = new PEdorICDetailBLF();
		if (!tPEdorICDetailBLF.submitData(rInputData, rOperation)) {
			mErrors.copyAllErrors(tPEdorICDetailBLF.getErrors());
			CError.buildErr(this, "处理提交的数据失败！");
			logger.debug("\t@> GrPEdorICDetailBLF.dealData() : PEdorICDetailBLF.submitData() 失败！");
			return false;
		}
		tPEdorICDetailBLF = null;
		// ---add---zhangtao---2006-11-1---为了满足人工核保需要，将LCGrpPol中数据复制到LPGrpPol--------BGN-------
		LPGrpEdorItemSchema mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) rInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);

		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpPolDB.setAppFlag("1");
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();

		LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
		Reflections tRef = new Reflections();
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
			tRef.transFields(tLPGrpPolSchema, tLCGrpPolSet.get(i));
			tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPGrpPolSchema.setModifyDate(CurrDate);
			tLPGrpPolSchema.setModifyTime(CurrTime);
			tLPGrpPolSet.add(tLPGrpPolSchema);
		}

		String sql = "delete from lpgrppol where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'";
		rMap.put(sql, "DELETE");

		rMap.put(tLPGrpPolSet, "INSERT");

		// 更新团体险种表表更后的保费、保额、份数
		sql = " update lpgrppol g set "
				+ " prem = prem - (select sum(c.prem-p.prem) from lcpol c, lppol p where c.polno = p.polno and p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "'), "
				+ " amnt = amnt - (select sum(c.amnt-p.amnt) from lcpol c, lppol p where c.polno = p.polno and p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "'), "
				+ " mult = mult - (select sum(c.mult-p.mult) from lcpol c, lppol p where c.polno = p.polno and p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "') "
				+ " where g.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' "
				+ " and g.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and g.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' ";
		rMap.put(sql, "UPDATE");
		// ---add---zhangtao---2006-11-1---为了满足人工核保需要，将LCGrpPol中数据复制到LPGrpPol--------END-------

		// logger.debug("\t@> GrpEdorICDetailBLF.dealData() 结束");
		return true;
	} // function dealData end

	// ==========================================================================

	/**
	 * 准备经过本类处理的输出数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean outputData() {
		// logger.debug("\t@> GrpEdorICDetailBLF.outputData() 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> GrpEdorICDetailBLF.outputData() 结束");
		return true;
	} // function outputData end

	// ==========================================================================

	/**
	 * 返回经过本类处理的数据结果
	 * 
	 * @param null
	 * @return VData
	 */
	public VData getResult() {
		return rResultData;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回传入本类的操作类型
	 * 
	 * @param null
	 * @return String
	 */
	public String getOperation() {
		return rOperation;
	} // function getOperation end

	// ==========================================================================

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @param null
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	} // function getErrors end

	// ==========================================================================

	/**
	 * 处理本类运行时产生的垃圾
	 * 
	 * @param null
	 */
	private void collectGarbage() {
		if (rInputData != null)
			rInputData = null;
		if (rMap != null)
			rMap = null;
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// GrpEdorICDetailBLF tGrpEdorICDetailBLF = new GrpEdorICDetailBLF();
	// } //function main end
	// ==========================================================================

} // class GrpEdorICDetailBLF end
