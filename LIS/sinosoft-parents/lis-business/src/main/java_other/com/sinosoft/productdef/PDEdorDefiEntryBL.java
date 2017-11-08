
/**
 * <p>Title: PDEdorDefiEntry</p>
 * <p>Description: 保全信息定义</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-16
 */

package com.sinosoft.productdef;

import com.sinosoft.lis.db.LMEdorZTDB;
import com.sinosoft.lis.db.PD_LMEdorCalDB;
import com.sinosoft.lis.db.PD_LMRiskEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.RiskState;
import com.sinosoft.lis.schema.LMEdorWTSchema;
import com.sinosoft.lis.schema.LMEdorZT1Schema;
import com.sinosoft.lis.schema.LMEdorZTDutySchema;
import com.sinosoft.lis.schema.PD_LMEdorCalSchema;
import com.sinosoft.lis.schema.PD_LMEdorZT1Schema;
import com.sinosoft.lis.schema.PD_LMEdorZTDutySchema;
import com.sinosoft.lis.schema.PD_LMRiskEdorItemSchema;
import com.sinosoft.lis.vschema.LMEdorWTSet;
import com.sinosoft.lis.vschema.LMEdorZT1Set;
import com.sinosoft.lis.vschema.LMEdorZTDutySet;
import com.sinosoft.lis.vschema.LMEdorZTSet;
import com.sinosoft.lis.vschema.PD_LMEdorCalSet;
import com.sinosoft.lis.vschema.PD_LMRiskEdorItemSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PDEdorDefiEntryBL implements BusinessService {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private MMap map = new MMap();

	private TransferData mTransferData = null;

	private String mRiskCode = null;
	private RiskState mRiskState;
	private String mCalCodeType = "";

	public PDEdorDefiEntryBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData
	 *             输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!check()) {
			return false;
		}
		if (!dealData(cInputData)) {
			return false;
		}
		if (!submit()) {
			return false;
		}
		if (this.mOperate.equals("saveedoritem")) {
			String tRiskCode = (String) ((TransferData) cInputData.getObjectByObjectName("TransferData", 0))
					.getValueByName("RiskCode");
			mRiskState.setState(tRiskCode, "保全业务控制->保全项目选择", "1");
		}
		String mEdorType = (String) mTransferData.getValueByName("EdorType");
		String tRiskCode = (String) ((TransferData) cInputData.getObjectByObjectName("TransferData", 0))
				.getValueByName("RiskCode");

		if (mEdorType != null && !mEdorType.equals("")) {
			RiskState.setState(tRiskCode, "保全业务控制->保全项目算法定义", "1");
		} else {
			RiskState.setState(tRiskCode, "保全业务控制->保全项目选择", "1");
		}

		return true;
	}

	private boolean dealData(VData cInputData) {
		if (this.mOperate.equals("saveedoritem")) {
			if (!saveEdorItem()) {
				return false;
			}
		} else if (this.mOperate.equals("savecal")) {
			if (!saveCal()) {
				return false;
			}
		} else if (this.mOperate.equals("delcal")) {
			if (!delCal()) {
				return false;
			}
		}

		return true;
	}

	private boolean delCal() {
		String tEdorType = (String) mTransferData.getValueByName("EdorType");
		if (StrTool.cTrim(tEdorType).equals("")) {
			mErrors.addOneError("保全类型为空，无法删除");
			return false;
		}
		this.mCalCodeType = (String) mTransferData.getValueByName("CalCodeType");
		PD_LMEdorCalSet tPD_LMEdorCalSet = (PD_LMEdorCalSet) mTransferData.getValueByName("PD_LMEdorCalSet");
		if (tPD_LMEdorCalSet.size() <= 0) {
			CError.buildErr(this, "没有待删除的数据");
			return false;
		}

		for (int i = 1; i <= tPD_LMEdorCalSet.size(); i++) {
			PD_LMEdorCalSchema tPD_LMEdorCalSchema = tPD_LMEdorCalSet.get(i);
			PD_LMEdorCalDB tPD_LMEdorCalDB = new PD_LMEdorCalDB();
			tPD_LMEdorCalDB.setSchema(tPD_LMEdorCalSchema);
			if (!tPD_LMEdorCalDB.getInfo()) {
				CError.buildErr(this, "没有待删除的数据");
				return false;
			}
			this.map.put(tPD_LMEdorCalSchema, "DELETE");
		}
		return true;
	}

	/**
	 * 定义保全算法
	 * 
	 * @return:
	 */
	private boolean saveCal() {
		String tEdorType = (String) mTransferData.getValueByName("EdorType");
		String tEdorCalType = (String) mTransferData.getValueByName("EdorCalType");
		if (StrTool.cTrim(tEdorType).equals("")) {
			mErrors.addOneError("保全类型为空，无法保存");
			return false;
		}
		this.mCalCodeType = (String) mTransferData.getValueByName("CalCodeType");
		PD_LMEdorCalSet tPD_LMEdorCalSet = (PD_LMEdorCalSet) mTransferData.getValueByName("PD_LMEdorCalSet");

		if (tPD_LMEdorCalSet != null && tPD_LMEdorCalSet.size() > 0) {
			for (int i = 1; i <= tPD_LMEdorCalSet.size(); i++) {
				String tCalCode = tPD_LMEdorCalSet.get(i).getCalCode();
				if (tCalCode == null || tCalCode.equals("")) {
					tCalCode = PubFun1.CreateRuleCalCode("PD", mCalCodeType);
					mTransferData.removeByName("CALCODE");
					mTransferData.setNameAndValue("CALCODE", tCalCode);
					tPD_LMEdorCalSet.get(i).setCalCode(tCalCode);
					this.mResult.add(0, tCalCode);
				} else {
					// 校验算法类型和算法编码的关系
					if (tCalCode.toUpperCase().startsWith("RU") && "N".equals(mCalCodeType)
							|| !tCalCode.toUpperCase().startsWith("RU") && "Y".equals(mCalCodeType))
					// if((tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("N"))
					// ||!tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("Y"))
					{
						CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
						return false;
					}
					this.mResult.add(0, tCalCode);
				}
			}
		}

		LMEdorZT1Set tLMEdorZT1Set = (LMEdorZT1Set) mTransferData.getValueByName("LMEdorZT1Set");
		if (tLMEdorZT1Set != null && tLMEdorZT1Set.size() > 0) {
			for (int i = 1; i <= tLMEdorZT1Set.size(); i++) {
				String tCalCodeType = tLMEdorZT1Set.get(i).getCycPayCalCode();
				if (tCalCodeType == null || tCalCodeType.equals("")) {
					tCalCodeType = PubFun1.CreateRuleCalCode("PD", mCalCodeType);
					mTransferData.removeByName("CALCODE");
					mTransferData.setNameAndValue("CALCODE", tCalCodeType);
					tLMEdorZT1Set.get(i).setCycPayCalCode(tCalCodeType);
					this.mResult.add(0, tCalCodeType);
				} else {
					// 校验算法类型和算法编码的关系
					if (tCalCodeType.toUpperCase().startsWith("RU") && "N".equals(mCalCodeType)
							|| !tCalCodeType.toUpperCase().startsWith("RU") && "Y".equals(mCalCodeType))
					// if((tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("N"))
					// ||!tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("Y"))
					{
						CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
						return false;
					}
					this.mResult.add(0, tCalCodeType);
				}
			}
		}
		// 如果是退保 向表tLMEdorZT1中添加数据
		if (StrTool.cTrim(tEdorType).equals("CT")) {
			for (int i = 1; i <= tLMEdorZT1Set.size(); i++) {
				LMEdorZT1Schema tLMEdorZT1Schema = tLMEdorZT1Set.get(i);
				this.map.put(tLMEdorZT1Schema, "DELETE&INSERT");
				String tdutycode = tLMEdorZT1Schema.getDutyCode();
				String sql = "select count(1) from  pd_lmdutyget where type = '0' and  dutycode = '" + tdutycode + "'";
				ExeSQL exec = new ExeSQL();
				String ss = exec.getOneValue(sql);
				// 如果对应的责任存在生存领取就向表LMEdorZTDuty中插入一条数据
				System.out.println(ss);
				if (ss != null && Integer.parseInt(ss) > 0) {
					
					LMEdorZTDutySet tLMEdorZTDutySet = (LMEdorZTDutySet) mTransferData
							.getValueByName("LMEdorZTDutySet");
					for(int j = 1;j<=tLMEdorZTDutySet.size();j++){
						LMEdorZTDutySchema tLMEdorZTDutySchema = tLMEdorZTDutySet.get(j);
						this.map.put(tLMEdorZTDutySchema, "DELETE&INSERT");
					}
							
				} else {
					System.out.println("责任编码为" + tLMEdorZT1Schema.getDutyCode() + "不存在生存领取");
				}

			}

		
		}

		//Allen 犹豫期退保正在表LMEdorWT存入数据
		if(StrTool.cTrim(tEdorType).equals("WT")){
			LMEdorWTSet tLMEdorWTSet = (LMEdorWTSet) mTransferData.getValueByName("LMEdorWTSet");
			for(int i = 1 ;i<=tLMEdorWTSet.size();i++){
				LMEdorWTSchema tLMEdorWTSchema  = tLMEdorWTSet.get(i);
				this.map.put(tLMEdorWTSchema, "DELETE&INSERT");
			}
		} 
		
		
		if (StrTool.cTrim(tEdorType).equals("ZT") || StrTool.cTrim(tEdorType).equals("XT")
				|| StrTool.cTrim(tEdorType).equals("WM")) {
			ExeSQL exec = new ExeSQL();
			String tDeleteSql = "delete from PD_LMEdorZTDuty where riskcode='" + this.mRiskCode + "'";
			this.map.put(tDeleteSql, "DELETE");
			tDeleteSql = "delete from PD_LMEdorZT1 where riskcode='" + this.mRiskCode + "'";
			this.map.put(tDeleteSql, "DELETE");
			tDeleteSql = "delete from PD_LMEdorCal where riskcode='" + this.mRiskCode + "' and edortype='ZT'";
			this.map.put(tDeleteSql, "DELETE");
			PD_LMRiskEdorItemDB tPD_LMRiskEdorItemDB = new PD_LMRiskEdorItemDB();
			tPD_LMRiskEdorItemDB.setRiskCode(this.mRiskCode);
			tPD_LMRiskEdorItemDB.setEdorCode(tEdorType);
			PD_LMRiskEdorItemSet tPD_LMRiskEdorItemSet = tPD_LMRiskEdorItemDB.query();
			if (tPD_LMRiskEdorItemSet == null || tPD_LMRiskEdorItemSet.size() == 0) {
				mErrors.addOneError(tEdorType + " 保全项未添加，需要将保全项添加后才能继续该操作");
				return false;
			}
			if (tPD_LMEdorCalSet != null && tPD_LMEdorCalSet.size() > 0) {
				SSRS ssrsduty = exec.execSQL("select dutycode from pd_lmriskduty where riskcode='" + mRiskCode + "'");
				// String
				// tCalCode=mRiskCode.substring(0,4)+PubFun1.CreateMaxNo(this.mRiskCode,
				// 2);
				String tCalCode = tPD_LMEdorCalSet.get(1).getCalCode();

				for (int i = 1; i <= ssrsduty.MaxRow; i++) {
					PD_LMEdorZT1Schema tPD_LMEdorZT1Schema = new PD_LMEdorZT1Schema();
					tPD_LMEdorZT1Schema.setRiskCode(this.mRiskCode);
					tPD_LMEdorZT1Schema.setDutyCode(ssrsduty.GetText(i, 1));
					tPD_LMEdorZT1Schema.setCycPayCalCode(tCalCode);
					String sql = "select fieldcode,fieldinitvalue from PD_BASEFIELD where isdisplay = 0 and tablecode = upper('PD_LMEdorZT1') order by displayorder ";
					SSRS ssrs = exec.execSQL(sql);
					int rowCount = ssrs.MaxRow;
					for (int j = 1; j <= rowCount; j++) {
						tPD_LMEdorZT1Schema.setV(ssrs.GetText(j, 1), ssrs.GetText(j, 2));
					}
					tPD_LMEdorZT1Schema.setOperator(this.mGlobalInput.Operator);
					tPD_LMEdorZT1Schema.setMakeDate(PubFun.getCurrentDate());
					tPD_LMEdorZT1Schema.setMakeTime(PubFun.getCurrentTime());
					tPD_LMEdorZT1Schema.setModifyDate(PubFun.getCurrentDate());
					tPD_LMEdorZT1Schema.setModifyTime(PubFun.getCurrentTime());
					this.map.put(tPD_LMEdorZT1Schema, "DELETE&INSERT");

					PD_LMEdorZTDutySchema tPD_LMEdorZTDutySchema = new PD_LMEdorZTDutySchema();
					tPD_LMEdorZTDutySchema.setRiskCode(this.mRiskCode);
					tPD_LMEdorZTDutySchema.setDutyCode(ssrsduty.GetText(i, 1));
					tPD_LMEdorZTDutySchema.setPayCalType("1");
					sql = "select fieldcode,fieldinitvalue from PD_BASEFIELD where isdisplay = 0 and tablecode = upper('PD_LMEdorZTDuty') order by displayorder ";
					ssrs = exec.execSQL(sql);
					rowCount = ssrs.MaxRow;
					for (int j = 1; j <= rowCount; j++) {
						tPD_LMEdorZTDutySchema.setV(ssrs.GetText(j, 1), ssrs.GetText(j, 2));
					}
					tPD_LMEdorZTDutySchema.setOperator(this.mGlobalInput.Operator);
					tPD_LMEdorZTDutySchema.setMakeDate(PubFun.getCurrentDate());
					tPD_LMEdorZTDutySchema.setMakeTime(PubFun.getCurrentTime());
					tPD_LMEdorZTDutySchema.setModifyDate(PubFun.getCurrentDate());
					tPD_LMEdorZTDutySchema.setModifyTime(PubFun.getCurrentTime());
					this.map.put(tPD_LMEdorZTDutySchema, "DELETE&INSERT");
				}
				// ===============
				exec = new ExeSQL();
				for (int i = 1; i <= tPD_LMEdorCalSet.size(); i++) {
					PD_LMEdorCalSchema tPD_LMEdorCalSchema = tPD_LMEdorCalSet.get(i);
					tPD_LMRiskEdorItemDB = new PD_LMRiskEdorItemDB();
					tPD_LMRiskEdorItemDB.setRiskCode(this.mRiskCode);
					tPD_LMRiskEdorItemDB.setEdorCode(tPD_LMEdorCalSchema.getEdorType());
					tPD_LMRiskEdorItemSet = tPD_LMRiskEdorItemDB.query();
					if (tPD_LMRiskEdorItemSet == null || tPD_LMRiskEdorItemSet.size() == 0) {
						mErrors.addOneError(tPD_LMEdorCalSchema.getEdorType() + " 保全项未添加，需要将保全项添加后才能继续该操作");
						return false;
					}
					String sql = "select fieldcode,fieldinitvalue from PD_BASEFIELD where isdisplay = 0 and tablecode = upper('PD_LMEdorCal') order by displayorder ";
					SSRS ssrs = exec.execSQL(sql);
					int rowCount = ssrs.MaxRow;
					for (int j = 1; j <= rowCount; j++) {
						tPD_LMEdorCalSchema.setV(ssrs.GetText(j, 1), ssrs.GetText(j, 2));
					}
					// tPD_LMEdorCalSchema.setCalCode(mRiskCode.substring(0,4)+PubFun1.CreateMaxNo(this.mRiskCode,
					// 2));
					tPD_LMEdorCalSchema.setOperator(this.mGlobalInput.Operator);
					tPD_LMEdorCalSchema.setMakeDate(PubFun.getCurrentDate());
					tPD_LMEdorCalSchema.setMakeTime(PubFun.getCurrentTime());
					tPD_LMEdorCalSchema.setModifyDate(PubFun.getCurrentDate());
					tPD_LMEdorCalSchema.setModifyTime(PubFun.getCurrentTime());
					this.map.put(tPD_LMEdorCalSchema, "DELETE&INSERT");
				}
				// ===============
			}
		} else {
			// String tDeleteSql="delete from PD_LMEdorCal where
			// riskcode='"+this.mRiskCode+"' and
			// edortype='"+StrTool.cTrim(tEdorType)+"'";
			// this.map.put(tDeleteSql, "DELETE");
			ExeSQL exec = new ExeSQL();
			for (int i = 1; i <= tPD_LMEdorCalSet.size(); i++) {
				PD_LMEdorCalSchema tPD_LMEdorCalSchema = tPD_LMEdorCalSet.get(i);
				PD_LMRiskEdorItemDB tPD_LMRiskEdorItemDB = new PD_LMRiskEdorItemDB();
				tPD_LMRiskEdorItemDB.setRiskCode(this.mRiskCode);
				tPD_LMRiskEdorItemDB.setEdorCode(tPD_LMEdorCalSchema.getEdorType());
				PD_LMRiskEdorItemSet tPD_LMRiskEdorItemSet = tPD_LMRiskEdorItemDB.query();
				if (tPD_LMRiskEdorItemSet == null || tPD_LMRiskEdorItemSet.size() == 0) {
					mErrors.addOneError(tPD_LMEdorCalSchema.getEdorType() + " 保全项未添加，需要将保全项添加后才能继续该操作");
					return false;
				}
				String sql = "select fieldcode,fieldinitvalue from PD_BASEFIELD where isdisplay = 0 and tablecode = upper('PD_LMEdorCal') order by displayorder ";
				SSRS ssrs = exec.execSQL(sql);
				int rowCount = ssrs.MaxRow;
				for (int j = 1; j <= rowCount; j++) {
					tPD_LMEdorCalSchema.setV(ssrs.GetText(j, 1), ssrs.GetText(j, 2));
				}
				// tPD_LMEdorCalSchema.setCalCode(mRiskCode.substring(0,4)+PubFun1.CreateMaxNo(this.mRiskCode,
				// 2));
				tPD_LMEdorCalSchema.setOperator(this.mGlobalInput.Operator);
				tPD_LMEdorCalSchema.setMakeDate(PubFun.getCurrentDate());
				tPD_LMEdorCalSchema.setMakeTime(PubFun.getCurrentTime());
				tPD_LMEdorCalSchema.setModifyDate(PubFun.getCurrentDate());
				tPD_LMEdorCalSchema.setModifyTime(PubFun.getCurrentTime());
				this.map.put(tPD_LMEdorCalSchema, "DELETE&INSERT");
			}
		}
		return true;
	}

	/**
	 * 定义保全项目
	 * 
	 * @return:
	 */
	private boolean saveEdorItem() {
		PD_LMRiskEdorItemSet tPD_LMRiskEdorItemSet = (PD_LMRiskEdorItemSet) mTransferData
				.getValueByName("PD_LMRiskEdorItemSet");
		String tDeleteSql = "delete from PD_LMRISKEDORITEM where riskcode='" + this.mRiskCode + "'";
		this.map.put(tDeleteSql, "DELETE");
		ExeSQL exec = new ExeSQL();
		SSRS ssrs = null;
		String tEdorItem = "";
		for (int i = 1; i <= tPD_LMRiskEdorItemSet.size(); i++) {
			PD_LMRiskEdorItemSchema tPD_LMRiskEdorItemSchema = tPD_LMRiskEdorItemSet.get(i);
			tEdorItem += "'" + tPD_LMRiskEdorItemSchema.getEdorCode() + "',";
			String sql = "select fieldcode,fieldinitvalue from PD_BASEFIELD where isdisplay = 0 and tablecode = upper('PD_LMRISKEDORITEM') order by displayorder ";
			ssrs = exec.execSQL(sql);
			int rowCount = ssrs.MaxRow;
			for (int j = 1; j <= rowCount; j++) {
				tPD_LMRiskEdorItemSchema.setV(ssrs.GetText(j, 1), ssrs.GetText(j, 2));
			}
			sql = "select riskname from PD_LMRISK where riskcode='" + this.mRiskCode + "'";
			ssrs = exec.execSQL(sql);
			if (ssrs != null && ssrs.getMaxRow() > 0) {
				tPD_LMRiskEdorItemSchema.setRiskName(ssrs.GetText(1, 1));
			}
			sql = "select edorname from LMEdoritem where edorcode='" + tPD_LMRiskEdorItemSchema.getEdorCode() + "'";
			ssrs = exec.execSQL(sql);
			if (ssrs != null && ssrs.getMaxRow() > 0) {
				tPD_LMRiskEdorItemSchema.setEdorName(ssrs.GetText(1, 1));
			}
			tPD_LMRiskEdorItemSchema.setOperator(this.mGlobalInput.Operator);
			tPD_LMRiskEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
			tPD_LMRiskEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
			tPD_LMRiskEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
			tPD_LMRiskEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
			this.map.put(tPD_LMRiskEdorItemSchema, "DELETE&INSERT");
		}
		tEdorItem += "' '";
		String sql = "select (select edorname from LMEdoritem where rownum=1 and edorcode=PD_LMEdorCal.edortype) from PD_LMEdorCal where RiskCode = '"
				+ this.mRiskCode + "' and EdorType not in (" + tEdorItem + ") ";
		ssrs = exec.execSQL(sql);
		String tEdorName = "";
		if (ssrs != null) {
			for (int i = 1; i <= ssrs.getMaxRow(); i++) {
				tEdorName += ssrs.GetText(1, 1) + " ";
			}
		}
		if (!tEdorName.equals("")) {
			mErrors.addOneError(tEdorName + "保全项存在算法项，需要将算法项删除后才能继续该操作");
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		if (mTransferData == null) {
			mErrors.addOneError("业务数据为空");
			return false;
		}
		if (mGlobalInput == null) {
			mErrors.addOneError("登陆用户信息为空");
			return false;
		}
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");
		if (StrTool.cTrim(mRiskCode).equals("")) {
			mErrors.addOneError("业务数据为空");
			return false;
		}
		return true;
	}

	/**
	 * 提交数据到数据库
	 * 
	 * @return boolean
	 */
	private boolean submit() {
		VData data = new VData();
		data.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(data, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}
		return true;
	}

	private boolean check() {
		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}

	public static void main(String[] args) {
	}
}
