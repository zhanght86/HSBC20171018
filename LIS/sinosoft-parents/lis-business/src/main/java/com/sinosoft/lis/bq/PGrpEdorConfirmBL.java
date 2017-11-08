package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全确认逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 2.0
 */
public class PGrpEdorConfirmBL {
private static Logger logger = Logger.getLogger(PGrpEdorConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private VData pInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	LPGrpEdorMainSet mLPGrpEdorMainSet = new LPGrpEdorMainSet();
	LPGrpEdorItemSet mLPGrpEdorItemSet = new LPGrpEdorItemSet();
	LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	String mStrTemplatePath = "";

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();

	public PGrpEdorConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据准备操作，检查团体保全主表数据，看是否通过了保全核保
		if (!prepareData()) {
			return false;
		}
		logger.debug("---End prepareData---");

		// 数据操作业务处理
		if (cOperate.equals("INSERT||GRPEDORCONFIRM")) {
			pInputData = new VData();

			EdorConfirmBL aEdorConfirmBL = new EdorConfirmBL();
			// mLPGrpEdorMainSchema.setSchema(mLPGrpEdorMainSet.get(i));

			GEdorConfirmBL aGEdorConfirmBL = new GEdorConfirmBL();
			LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			tLPEdorMainDB.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());
			mLPEdorMainSet = tLPEdorMainDB.query();
			if (tLPEdorMainDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询个人保全失败！");
			}
			for (int i = 1; i <= mLPEdorMainSet.size(); i++) {
				pInputData.clear();
				pInputData.addElement(mGlobalInput);
				pInputData.addElement("G");
				pInputData.addElement(mLPGrpEdorMainSchema);
				pInputData.addElement(mStrTemplatePath);

				logger.debug("处理个人保全主表");
				pInputData.addElement(mLPEdorMainSet.get(i));
				if (!aEdorConfirmBL.submitData(pInputData, cOperate)) {
					this.mErrors.copyAllErrors(aEdorConfirmBL.mErrors);
					return false;
				} else {
					VData rVData = aEdorConfirmBL.getResult();
					MMap tMap = new MMap();
					tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
					if (tMap == null) {
						CError.buildErr(this, "得到个人保单为:"
								+ mLPEdorMainSet.get(i).getContNo()
								+ "的保全确认结果时失败！");
						return false;

					} else {
						map.add(tMap);
					}
				}
			}
			pInputData.clear();
			pInputData.addElement(mGlobalInput);
			pInputData.addElement("G");
			pInputData.addElement(mStrTemplatePath);
			pInputData.addElement(mLPGrpEdorMainSchema);

			logger.debug("处理团体保全主表，包括更新保全状态");
			if (!aGEdorConfirmBL.submitData(pInputData, cOperate)) {
				this.mErrors.copyAllErrors(aGEdorConfirmBL.mErrors);
				return false;
			} else {
				VData rVData = aGEdorConfirmBL.getResult();
				MMap tMap = new MMap();
				tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					CError.buildErr(this, "得到团体保单的保全确认结果时失败！");
					return false;

				} else {
					map.add(tMap);
				}
			}

			logger.debug("Start 转换申请书号到批单号");
			EdorNoUpdate tEdorNoUpdate = new EdorNoUpdate(mLPGrpEdorMainSchema
					.getEdorNo(), mLPGrpEdorMainSchema.getManageCom(), true);
			if (tEdorNoUpdate.updateEdorNo()) {

				map.add((MMap) tEdorNoUpdate.getResult().getObjectByObjectName(
						"MMap", 0));
				changeXml tChangeXml = new changeXml(mLPGrpEdorMainSchema
						.getEdorNo(), tEdorNoUpdate.getNewEdorno(),
						mGlobalInput);
				if (tChangeXml.changeGrpEdor()) {
					map.add((MMap) tChangeXml.getResult()
							.getObjectByObjectName("MMap", 0));
				} else {
					CError.buildErr(this, "转化保全申请单到批单失败！");
					return false;

				}
			}
			mResult.add(tEdorNoUpdate.getNewEdorno());
			logger.debug("End 转换申请书号到批单号");

		} else {
			CError.buildErr(this, "保全申请单更换号码失败！");
			return false;

		}
		// 数据提交、保存
		mInputData.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "PGrpEdorConfirmBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mStrTemplatePath = (String) mInputData.get(0);
			mLPGrpEdorMainSchema = (LPGrpEdorMainSchema) mInputData
					.getObjectByObjectName("LPGrpEdorMainSchema", 0);
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		String tEdorNo = null;
		LPGrpEdorItemSchema tLPGrpEdorMainSchema = new LPGrpEdorItemSchema();
		mInputData.clear();

		String sql = "select * from LPGrpEdorMain where EdorState='2' and UWState ='9' "
				+ " and EdorNo='" + mLPGrpEdorMainSchema.getEdorNo() + "' ";
		logger.debug("----grpsql" + sql);
		mLPGrpEdorMainSet.clear();
		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		mLPGrpEdorMainSet = tLPGrpEdorMainDB.executeQuery(sql);
		if (tLPGrpEdorMainDB.mErrors.needDealError()
				|| mLPGrpEdorMainSet.size() == 0) {
			CError.buildErr(this, "查询保全主表时失败！");
			return false;
		} else {
			mLPGrpEdorMainSchema.setSchema(mLPGrpEdorMainSet.get(1));
		}

		// for (int i=1; i<=tLPGrpEdorItemSet.size(); i++)
		// {
		// tLPGrpEdorMainSchema = new LPGrpEdorItemSchema();
		// tLPGrpEdorMainSchema = tLPGrpEdorItemSet.get(i);
		// if (i==1)
		// {
		// mLPGrpEdorMainSet.add(tLPGrpEdorMainSchema);
		//
		// tEdorNo = tLPGrpEdorMainSchema.getEdorNo();
		//
		// LPGrpEdorItemSet iLPGrpEdorMainSet = new LPGrpEdorItemSet();
		// String tSql = "select * from LPGrpedoritem where Edorno='"+tEdorNo+"'
		// and edorstate ='2' and uwstate<>'9'";
		// LPGrpEdorItemDB iLPGrpEdorMainDB = new LPGrpEdorItemDB();
		// iLPGrpEdorMainSet = iLPGrpEdorMainDB.executeQuery(tSql);
		// if (iLPGrpEdorMainSet.size()>0)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "PEdorAutoUWBL";
		// tError.functionName = "prepareData";
		// tError.errorMessage = "批单号:"+tEdorNo+"有个别项目未通过核保!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// }
		// else
		// {
		// if (tLPGrpEdorMainSchema.getEdorNo().equals(tEdorNo))
		// continue;
		// mLPGrpEdorMainSet.add(tLPGrpEdorMainSchema);
		// tEdorNo = tLPGrpEdorMainSchema.getEdorNo();
		//
		// LPGrpEdorItemSet iLPGrpEdorMainSet = new LPGrpEdorItemSet();
		// String tSql = "select * from LPGrpedormain where Edorno='"+tEdorNo+"'
		// and edorstate ='2' and uwstate<>'9'";
		// LPGrpEdorItemDB iLPGrpEdorMainDB = new LPGrpEdorItemDB();
		// iLPGrpEdorMainSet = iLPGrpEdorMainDB.executeQuery(tSql);
		// if (iLPGrpEdorMainSet.size()>0)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "PEdorAutoUWBL";
		// tError.functionName = "prepareData";
		// tError.errorMessage = "批单号:"+tEdorNo+"有个别项目未通过核保!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// }
		// }

		// if (mLPGrpEdorItemSet.size()>0)
		// {
		// mInputData.addElement(mLPGrpEdorItemSet);
		// }
		// else
		// return false;

		mInputData.addElement(mGlobalInput);
		return true;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PGrpEdorConfirmBL aPGrpEdorConfirmBL = new PGrpEdorConfirmBL();
		LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tLPGrpEdorMainSchema.setGrpContNo("240110000000207");
		tLPGrpEdorMainSchema.setEdorNo("430110000000394");
		String strTemplatePath = "xerox/printdata/";

		tInputData.addElement(strTemplatePath);
		tInputData.addElement(tLPGrpEdorMainSchema);
		tInputData.addElement(tGlobalInput);

		if (!aPGrpEdorConfirmBL
				.submitData(tInputData, "INSERT||GRPEDORCONFIRM")) {
			logger.debug(aPGrpEdorConfirmBL.mErrors.getErrContent());
		}
	}

}
