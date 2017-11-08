/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tb;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDTaxTableColumnConfigDB;
import com.sinosoft.lis.db.LMCalFactorDB;
import com.sinosoft.lis.db.LMCalModeDB;
import com.sinosoft.lis.db.LMCheckFieldDB;
import com.sinosoft.lis.db.LMDutyCtrlDB;
import com.sinosoft.lis.db.LMDutyDB;
import com.sinosoft.lis.db.LMDutyGetAliveDB;
import com.sinosoft.lis.db.LMDutyGetDB;
import com.sinosoft.lis.db.LMDutyGetRelaDB;
import com.sinosoft.lis.db.LMDutyPayDB;
import com.sinosoft.lis.db.LMDutyPayRelaDB;
import com.sinosoft.lis.db.LMRiskAccGetDB;
import com.sinosoft.lis.db.LMRiskAccPayDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskBonusDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LMRiskDutyDB;
import com.sinosoft.lis.db.LMRiskInsuAccDB;
import com.sinosoft.lis.db.LMRiskToAccDB;
import com.sinosoft.lis.schema.LDTaxTableColumnConfigSchema;
import com.sinosoft.lis.schema.LMCalFactorSchema;
import com.sinosoft.lis.schema.LMCalModeSchema;
import com.sinosoft.lis.schema.LMCheckFieldSchema;
import com.sinosoft.lis.schema.LMDutyCtrlSchema;
import com.sinosoft.lis.schema.LMDutyGetAliveSchema;
import com.sinosoft.lis.schema.LMDutyGetRelaSchema;
import com.sinosoft.lis.schema.LMDutyGetSchema;
import com.sinosoft.lis.schema.LMDutyPayRelaSchema;
import com.sinosoft.lis.schema.LMDutyPaySchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LMRiskAccGetSchema;
import com.sinosoft.lis.schema.LMRiskAccPaySchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskBonusSchema;
import com.sinosoft.lis.schema.LMRiskDutySchema;
import com.sinosoft.lis.schema.LMRiskInsuAccSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LMRiskToAccSchema;
import com.sinosoft.lis.vschema.LMCalFactorSet;
import com.sinosoft.lis.vschema.LMCheckFieldSet;
import com.sinosoft.lis.vschema.LMDutyCtrlSet;
import com.sinosoft.lis.vschema.LMDutyGetAliveSet;
import com.sinosoft.lis.vschema.LMDutyGetRelaSet;
import com.sinosoft.lis.vschema.LMDutyPayRelaSet;
import com.sinosoft.lis.vschema.LMRiskAccGetSet;
import com.sinosoft.lis.vschema.LMRiskAccPaySet;
import com.sinosoft.lis.vschema.LMRiskDutySet;
import com.sinosoft.lis.vschema.LMRiskToAccSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;

/*
 * <p>Title: 险种相关信息缓存类 </p> <p>Description: 缓存程序中经常会用到的险种相关信息 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft</p> @author Kevin
 * 
 * @version 1.0 @date 2003-07-29
 */
public class CachedRiskInfo {
private static Logger logger = Logger.getLogger(CachedRiskInfo.class);
	// @Field
	public CErrors mErrors = new CErrors(); // 错误信息

	// 用来缓存信息的结构

	// 一些只可能按单个关键信息查询的信息
	private Hashtable m_hashLMRisk = new Hashtable();
	private Hashtable m_hashLMRiskApp = new Hashtable();
	private Hashtable m_hashLMDuty = new Hashtable();
	private Hashtable m_hashLMDutyGet = new Hashtable();
	private Hashtable m_hashLMDutyPay = new Hashtable();
	private Hashtable m_hashLMRiskInsuAcc = new Hashtable();
	private Hashtable m_hashLMCalMode = new Hashtable();
	private Hashtable m_hashLMRiskBonus = new Hashtable();

	// 另外一些可能按多个关键信息查询的信息
	private Hashtable m_hashLMRiskDuty = new Hashtable();
	private Hashtable m_hashLMDutyGetRela = new Hashtable();
	private Hashtable m_hashLMDutyPayRela = new Hashtable();
	private Hashtable m_hashLMRiskAccGet = new Hashtable();
	private Hashtable m_hashLMRiskAccPay = new Hashtable();
	private Hashtable m_hashLMRiskToAcc = new Hashtable();
	private Hashtable m_hashLMCheckField = new Hashtable();
	private Hashtable m_hashLMDutyCtrl = new Hashtable();
	private Hashtable m_hashLMDutyGetAlive = new Hashtable();
	private Hashtable m_hashLMCalFactor = new Hashtable();

	
	private Hashtable m_hashLDTaxTableColumnConfig = new Hashtable();
	// 静态变量
	private static String NOFOUND = "NOFOUND";
	private static CachedRiskInfo m_cri = null;

	// @Constructor
	private CachedRiskInfo() {
	}

	public static CachedRiskInfo getInstance() {
		if (m_cri == null) {
			m_cri = new CachedRiskInfo();
		}

		m_cri.mErrors.clearErrors();

		return m_cri;
	}

	public void refresh() {
		// Kevin 2003-07-29
		// 刷新的时候，可能会出现同步问题，但是机率很小。
		m_hashLMRisk.clear();
		m_hashLMRiskApp.clear();
		m_hashLMDuty.clear();
		m_hashLMDutyGet.clear();
		m_hashLMDutyPay.clear();
		m_hashLMRiskInsuAcc.clear();
		m_hashLMCalMode.clear();
		m_hashLMRiskBonus.clear();

		m_hashLMRiskDuty.clear();
		m_hashLMDutyGetRela.clear();
		m_hashLMDutyPayRela.clear();
		m_hashLMRiskAccGet.clear();
		m_hashLMRiskAccPay.clear();
		m_hashLMRiskToAcc.clear();
		m_hashLMCheckField.clear();
		m_hashLMDutyCtrl.clear();
		m_hashLMDutyGetAlive.clear();
		m_hashLMCalFactor.clear();
	}

	/**
	 * 按险种编码查找LMRisk
	 * 
	 * @param strRiskCode
	 *            String
	 * @return LMRiskSchema
	 */
	public LMRiskSchema findRiskByRiskCode(String strRiskCode) {

		Hashtable hash = m_hashLMRisk;

		Object obj = hash.get(strRiskCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMRiskSchema) obj;
			}
		} else {
			LMRiskDB tLMRiskDB = new LMRiskDB();

			tLMRiskDB.setRiskCode(strRiskCode);

			if (!tLMRiskDB.getInfo()) {
				mErrors.copyAllErrors(tLMRiskDB.mErrors);
				hash.put(strRiskCode, NOFOUND);
				return null;
			}

			hash.put(strRiskCode, tLMRiskDB.getSchema());
			return tLMRiskDB.getSchema();
		}
	}

	public LMRiskSchema findRiskByRiskCodeClone(String strRiskCode) {
		LMRiskSchema tLMRiskSchema = findRiskByRiskCode(strRiskCode);

		if (tLMRiskSchema == null) {
			return tLMRiskSchema;
		} else {
			return tLMRiskSchema.getSchema();
		}
	}

	/**
	 * 按险种编码查找LMRiskApp
	 * 
	 * @param strRiskCode
	 *            String
	 * @return LMRiskAppSchema
	 */
	public LMRiskAppSchema findRiskAppByRiskCode(String strRiskCode) {

		Hashtable hash = m_hashLMRiskApp;

		Object obj = hash.get(strRiskCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMRiskAppSchema) obj;
			}
		} else {
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();

			tLMRiskAppDB.setRiskCode(strRiskCode);

			if (!tLMRiskAppDB.getInfo()) {
				mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
				hash.put(strRiskCode, NOFOUND);
				return null;
			}

			hash.put(strRiskCode, tLMRiskAppDB.getSchema());
			return tLMRiskAppDB.getSchema();
		}
	}

	public LMRiskAppSchema findRiskAppByRiskCodeClone(String strRiskCode) {
		LMRiskAppSchema tLMRiskAppSchema = findRiskAppByRiskCode(strRiskCode);

		if (tLMRiskAppSchema == null) {
			return tLMRiskAppSchema;
		} else {
			return tLMRiskAppSchema.getSchema();
		}
	}

	/**
	 * 按责任编码查找LMDuty
	 * 
	 * @param strDutyCode
	 *            String
	 * @return LMDutySchema
	 */
	public LMDutySchema findDutyByDutyCode(String strDutyCode) {

		Hashtable hash = m_hashLMDuty;

		Object obj = hash.get(strDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				logger.debug("哈哈====="
						+ ((LMDutySchema) obj).getDutyCode());
				return (LMDutySchema) obj;
			}

		} else {
			LMDutyDB tLMDutyDB = new LMDutyDB();

			// 增加附加责任(责任代码大于6位,现在是10位)处理,add by Minim at 2004-04-28
			String dutyCode = strDutyCode;
			strDutyCode = strDutyCode.substring(0, 6);
			// ***************************************

			tLMDutyDB.setDutyCode(strDutyCode);

			if (!tLMDutyDB.getInfo()) {
				mErrors.copyAllErrors(tLMDutyDB.mErrors);
				hash.put(strDutyCode, NOFOUND);
				return null;
			}

			// 增加附加责任(责任代码大于6位,现在是10位)处理,add by Minim at 2004-04-28
			strDutyCode = dutyCode;
			// ***************************************

			hash.put(strDutyCode, tLMDutyDB.getSchema());
			logger.debug("哈哈====");
			return tLMDutyDB.getSchema();
		}
	}

	public LMDutySchema findDutyByDutyCodeClone(String strDutyCode) {
		LMDutySchema tLMDutySchema = findDutyByDutyCode(strDutyCode);

		if (tLMDutySchema == null) {
			return tLMDutySchema;
		} else {
			return tLMDutySchema.getSchema();
		}
	}

	/**
	 * 根据给付代码查找LMDutyGet
	 * 
	 * @param strGetDutyCode
	 *            String
	 * @return LMDutyGetSchema
	 */
	public LMDutyGetSchema findDutyGetByGetDutyCode(String strGetDutyCode) {

		Hashtable hash = m_hashLMDutyGet;

		Object obj = hash.get(strGetDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMDutyGetSchema) obj;
			}
		} else {
			LMDutyGetDB tLMDutyGetDB = new LMDutyGetDB();

			tLMDutyGetDB.setGetDutyCode(strGetDutyCode);

			if (!tLMDutyGetDB.getInfo()) {
				mErrors.copyAllErrors(tLMDutyGetDB.mErrors);
				hash.put(strGetDutyCode, NOFOUND);
				return null;
			}

			hash.put(strGetDutyCode, tLMDutyGetDB.getSchema());
			return tLMDutyGetDB.getSchema();
		}
	}

	public LMDutyGetSchema findDutyGetByGetDutyCodeClone(String strGetDutyCode) {
		LMDutyGetSchema tLMDutyGetSchema = findDutyGetByGetDutyCode(strGetDutyCode);

		if (tLMDutyGetSchema == null) {
			return tLMDutyGetSchema;
		} else {
			return tLMDutyGetSchema.getSchema();
		}
	}

	/**
	 * 根据缴费编码查找LMDutyPay
	 * 
	 * @param strPayPlanCode
	 *            String
	 * @return LMDutyPaySchema
	 */
	public LMDutyPaySchema findDutyPayByPayPlanCode(String strPayPlanCode) {

		Hashtable hash = m_hashLMDutyPay;

		Object obj = hash.get(strPayPlanCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMDutyPaySchema) obj;
			}
		} else {
			LMDutyPayDB tLMDutyPayDB = new LMDutyPayDB();

			tLMDutyPayDB.setPayPlanCode(strPayPlanCode);

			if (!tLMDutyPayDB.getInfo()) {
				mErrors.copyAllErrors(tLMDutyPayDB.mErrors);
				hash.put(strPayPlanCode, NOFOUND);
				return null;
			}

			hash.put(strPayPlanCode, tLMDutyPayDB.getSchema());
			return tLMDutyPayDB.getSchema();
		}
	}

	public LMDutyPaySchema findDutyPayByPayPlanCodeClone(String strPayPlanCode) {
		LMDutyPaySchema tLMDutyPaySchema = findDutyPayByPayPlanCode(strPayPlanCode);

		if (tLMDutyPaySchema == null) {
			return tLMDutyPaySchema;
		} else {
			return tLMDutyPaySchema.getSchema();
		}
	}

	/**
	 * 根据保险帐户号码查找LMRiskInsuAcc
	 * 
	 * @param strInsuAccNo
	 *            String
	 * @return LMRiskInsuAccSchema
	 */
	public LMRiskInsuAccSchema findRiskInsuAccByInsuAccNo(String strInsuAccNo) {

		Hashtable hash = m_hashLMRiskInsuAcc;

		Object obj = hash.get(strInsuAccNo);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMRiskInsuAccSchema) obj;
			}
		} else {
			LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();

			tLMRiskInsuAccDB.setInsuAccNo(strInsuAccNo);

			if (!tLMRiskInsuAccDB.getInfo()) {
				mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);
				hash.put(strInsuAccNo, NOFOUND);
				return null;
			}

			hash.put(strInsuAccNo, tLMRiskInsuAccDB.getSchema());
			return tLMRiskInsuAccDB.getSchema();
		}
	}

	public LMRiskInsuAccSchema findRiskInsuAccByInsuAccNoClone(
			String strInsuAccNo) {
		LMRiskInsuAccSchema tLMRiskInsuAccSchema = findRiskInsuAccByInsuAccNo(strInsuAccNo);

		if (tLMRiskInsuAccSchema == null) {
			return tLMRiskInsuAccSchema;
		} else {
			return tLMRiskInsuAccSchema.getSchema();
		}
	}

	/**
	 * 根据算法编码查找LMCalMode
	 * 
	 * @param strCalCode
	 *            String
	 * @return LMCalModeSchema
	 */
	public LMCalModeSchema findCalModeByCalCode(String strCalCode) {

		Hashtable hash = m_hashLMCalMode;

		Object obj = hash.get(strCalCode);

		if (obj != null) {
			if (obj instanceof String ) {
				LMCalModeDB tLMCalModeDB = new LMCalModeDB();

				tLMCalModeDB.setCalCode(strCalCode);

				if (!tLMCalModeDB.getInfo()) {
					mErrors.copyAllErrors(tLMCalModeDB.mErrors);
					hash.put(strCalCode, NOFOUND);
					return null;
				}

				hash.put(strCalCode, tLMCalModeDB.getSchema());
				return tLMCalModeDB.getSchema();
			} else {
				return (LMCalModeSchema) obj;
			}
		} else {
			LMCalModeDB tLMCalModeDB = new LMCalModeDB();

			tLMCalModeDB.setCalCode(strCalCode);

			if (!tLMCalModeDB.getInfo()) {
				mErrors.copyAllErrors(tLMCalModeDB.mErrors);
				hash.put(strCalCode, NOFOUND);
				return null;
			}

			hash.put(strCalCode, tLMCalModeDB.getSchema());
			return tLMCalModeDB.getSchema();
		}
	}

	public LMCalModeSchema findCalModeByCalCodeClone(String strCalCode) {
		LMCalModeSchema tLMCalModeSchema = findCalModeByCalCode(strCalCode);

		if (tLMCalModeSchema == null) {
			return tLMCalModeSchema;
		} else {
			return tLMCalModeSchema.getSchema();
		}
	}

	/**
	 * 按险种编码查找LMRiskBonus
	 * 
	 * @param strRiskCode
	 *            String
	 * @return LMRiskBonusSchema
	 */
	public LMRiskBonusSchema findRiskBonusByRiskCode(String strRiskCode) {

		Hashtable hash = m_hashLMRiskBonus;

		Object obj = hash.get(strRiskCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMRiskBonusSchema) obj;
			}
		} else {
			LMRiskBonusDB tLMRiskBonusDB = new LMRiskBonusDB();

			tLMRiskBonusDB.setRiskCode(strRiskCode);

			if (!tLMRiskBonusDB.getInfo()) {
				mErrors.copyAllErrors(tLMRiskBonusDB.mErrors);
				hash.put(strRiskCode, NOFOUND);
				return null;
			}

			hash.put(strRiskCode, tLMRiskBonusDB.getSchema());
			return tLMRiskBonusDB.getSchema();
		}
	}

	public LMRiskBonusSchema findRiskBonusByRiskCodeClone(String strRiskCode) {
		LMRiskBonusSchema tLMRiskBonusSchema = findRiskBonusByRiskCode(strRiskCode);

		if (tLMRiskBonusSchema == null) {
			return tLMRiskBonusSchema;
		} else {
			return tLMRiskBonusSchema.getSchema();
		}
	}

	/**
	 * 根据险种编码取得LMRiskDuty
	 * 
	 * @param strRiskCode
	 *            String
	 * @return LMRiskDutySet
	 */
	public LMRiskDutySet findRiskDutyByRiskCode(String strRiskCode) {

		Hashtable hash = m_hashLMRiskDuty;

		Object obj = hash.get(strRiskCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMRiskDutySet) obj;
			}
		} else {
			LMRiskDutyDB tLMRiskDutyDB = new LMRiskDutyDB();

			tLMRiskDutyDB.setRiskCode(strRiskCode);

			LMRiskDutySet tLMRiskDutySet = tLMRiskDutyDB.query();

			if (tLMRiskDutyDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLMRiskDutyDB.mErrors);
				hash.put(strRiskCode, NOFOUND);
				return null;
			}

			hash.put(strRiskCode, tLMRiskDutySet);
			return tLMRiskDutySet;
		}
	}

	public LMRiskDutySet findRiskDutyByRiskCodeClone(String strRiskCode) {
		LMRiskDutySet tLMRiskDutySet = findRiskDutyByRiskCode(strRiskCode);

		if (tLMRiskDutySet == null) {
			return tLMRiskDutySet;
		} else {
			LMRiskDutySet newLMRiskDutySet = new LMRiskDutySet();
			for (int nIndex = 0; nIndex < tLMRiskDutySet.size(); nIndex++) {
				newLMRiskDutySet.add(tLMRiskDutySet.get(nIndex + 1));
			}
			return newLMRiskDutySet;
		}
	}

	public LMRiskDutySchema findRiskDuty(String strRiskCode, String strDutyCode) {
		LMRiskDutySet tLMRiskDutySet = findRiskDutyByRiskCode(strRiskCode);

		if (tLMRiskDutySet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tLMRiskDutySet.size(); nIndex++) {
			LMRiskDutySchema tLMRiskDutySchema = tLMRiskDutySet.get(nIndex + 1);
			if (StrTool.cTrim(tLMRiskDutySchema.getDutyCode()).equals(
					strDutyCode)) {
				return tLMRiskDutySchema;
			}
		}

		return null;
	}

	public LMRiskDutySchema findRiskDutyClone(String strRiskCode,
			String strDutyCode) {
		LMRiskDutySchema tLMRiskDutySchema = findRiskDuty(strRiskCode,
				strDutyCode);

		if (tLMRiskDutySchema == null) {
			return tLMRiskDutySchema;
		} else {
			return tLMRiskDutySchema.getSchema();
		}
	}

	/**
	 * 根据责任编码查询LMDutyGetRela
	 * 
	 * @param strDutyCode
	 *            String
	 * @return LMDutyGetRelaSet
	 */
	public LMDutyGetRelaSet findDutyGetRelaByDutyCode(String strDutyCode) {

		Hashtable hash = m_hashLMDutyGetRela;

		Object obj = hash.get(strDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMDutyGetRelaSet) obj;
			}
		} else {
			LMDutyGetRelaDB tLMDutyGetRelaDB = new LMDutyGetRelaDB();

			// 增加附加责任(责任代码大于6位,现在是10位)处理,add by Minim at 2004-04-28
			String dutyCode = strDutyCode;
			strDutyCode = strDutyCode.substring(0, 6);
			// ***************************************

			tLMDutyGetRelaDB.setDutyCode(strDutyCode);

			LMDutyGetRelaSet tLMDutyGetRelaSet = tLMDutyGetRelaDB.query();

			if (tLMDutyGetRelaDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLMDutyGetRelaDB.mErrors);
				hash.put(strDutyCode, NOFOUND);
				return null;
			}

			// 增加附加责任(责任代码大于6位,现在是10位)处理,add by Minim at 2004-04-28
			strDutyCode = dutyCode;
			// ***************************************

			hash.put(strDutyCode, tLMDutyGetRelaSet);
			return tLMDutyGetRelaSet;
		}
	}

	public LMDutyGetRelaSet findDutyGetRelaByDutyCodeClone(String strDutyCode) {
		LMDutyGetRelaSet tLMDutyGetRelaSet = findDutyGetRelaByDutyCode(strDutyCode);

		if (tLMDutyGetRelaSet == null) {
			return tLMDutyGetRelaSet;
		} else {
			LMDutyGetRelaSet newLMDutyGetRelaSet = new LMDutyGetRelaSet();
			for (int nIndex = 0; nIndex < tLMDutyGetRelaSet.size(); nIndex++) {
				newLMDutyGetRelaSet.add(tLMDutyGetRelaSet.get(nIndex + 1));
			}
			return newLMDutyGetRelaSet;
		}
	}

	public LMDutyGetRelaSchema findDutyGetRela(String strDutyCode,
			String strGetDutyCode) {
		LMDutyGetRelaSet tLMDutyGetRelaSet = findDutyGetRelaByDutyCode(strDutyCode);

		if (tLMDutyGetRelaSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tLMDutyGetRelaSet.size(); nIndex++) {
			LMDutyGetRelaSchema tLMDutyGetRelaSchema = tLMDutyGetRelaSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tLMDutyGetRelaSchema.getDutyCode()).equals(
					strGetDutyCode)) {
				return tLMDutyGetRelaSchema;
			}
		}

		return null;
	}

	public LMDutyGetRelaSchema findDutyGetRelaClone(String strDutyCode,
			String strGetDutyCode) {
		LMDutyGetRelaSchema tLMDutyGetRelaSchema = findDutyGetRela(strDutyCode,
				strGetDutyCode);

		if (tLMDutyGetRelaSchema == null) {
			return tLMDutyGetRelaSchema;
		} else {
			return tLMDutyGetRelaSchema.getSchema();
		}
	}

	/**
	 * 根据责任编码查找LMDutyPayRela
	 * 
	 * @param strDutyCode
	 *            String
	 * @return LMDutyPayRelaSet
	 */
	public LMDutyPayRelaSet findDutyPayRelaByDutyCode(String strDutyCode) {

		Hashtable hash = m_hashLMDutyPayRela;

		Object obj = hash.get(strDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMDutyPayRelaSet) obj;
			}
		} else {
			LMDutyPayRelaDB tLMDutyPayRelaDB = new LMDutyPayRelaDB();

			// 增加附加责任(责任代码大于6位,现在是10位)处理,add by Minim at 2004-04-28
			String dutyCode = strDutyCode;
			strDutyCode = strDutyCode.substring(0, 6);
			// ***************************************

			tLMDutyPayRelaDB.setDutyCode(strDutyCode);

			LMDutyPayRelaSet tLMDutyPayRelaSet = tLMDutyPayRelaDB.query();

			if (tLMDutyPayRelaDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLMDutyPayRelaDB.mErrors);
				hash.put(strDutyCode, NOFOUND);
				return null;
			}

			// 增加附加责任(责任代码大于6位,现在是10位)处理,add by Minim at 2004-04-28
			strDutyCode = dutyCode;
			// ***************************************

			hash.put(strDutyCode, tLMDutyPayRelaSet);
			return tLMDutyPayRelaSet;
		}
	}

	public LMDutyPayRelaSet findDutyPayRelaByDutyCodeClone(String strDutyCode) {
		LMDutyPayRelaSet tLMDutyPayRelaSet = findDutyPayRelaByDutyCode(strDutyCode);

		if (tLMDutyPayRelaSet == null) {
			return tLMDutyPayRelaSet;
		} else {
			LMDutyPayRelaSet newLMDutyPayRelaSet = new LMDutyPayRelaSet();
			for (int nIndex = 0; nIndex < tLMDutyPayRelaSet.size(); nIndex++) {
				newLMDutyPayRelaSet.add(tLMDutyPayRelaSet.get(nIndex + 1));
			}
			return newLMDutyPayRelaSet;
		}
	}

	public LMDutyPayRelaSchema findDutyPayRela(String strDutyCode,
			String strPayPlanCode) {
		LMDutyPayRelaSet tLMDutyPayRelaSet = findDutyPayRelaByDutyCode(strDutyCode);

		if (tLMDutyPayRelaSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tLMDutyPayRelaSet.size(); nIndex++) {
			LMDutyPayRelaSchema tLMDutyPayRelaSchema = tLMDutyPayRelaSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tLMDutyPayRelaSchema.getPayPlanCode()).equals(
					strPayPlanCode)) {
				return tLMDutyPayRelaSchema;
			}
		}

		return null;
	}

	public LMDutyPayRelaSchema findDutyPayRelaClone(String strDutyCode,
			String strPayPlanCode) {
		LMDutyPayRelaSchema tLMDutyPayRelaSchema = findDutyPayRela(strDutyCode,
				strPayPlanCode);

		if (tLMDutyPayRelaSchema == null) {
			return tLMDutyPayRelaSchema;
		} else {
			return tLMDutyPayRelaSchema.getSchema();
		}
	}

	/**
	 * 根据给付责任编码查询LMRiskAccGet
	 * 
	 * @param strGetDutyCode
	 *            String
	 * @return LMRiskAccGetSet
	 */
	public LMRiskAccGetSet findRiskAccGetByGetDutyCode(String strGetDutyCode) {

		Hashtable hash = m_hashLMRiskAccGet;

		Object obj = hash.get(strGetDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMRiskAccGetSet) obj;
			}
		} else {
			LMRiskAccGetDB tLMRiskAccGetDB = new LMRiskAccGetDB();

			tLMRiskAccGetDB.setGetDutyCode(strGetDutyCode);

			LMRiskAccGetSet tLMRiskAccGetSet = tLMRiskAccGetDB.query();

			if (tLMRiskAccGetDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLMRiskAccGetDB.mErrors);
				hash.put(strGetDutyCode, NOFOUND);
				return null;
			}

			hash.put(strGetDutyCode, tLMRiskAccGetSet);
			return tLMRiskAccGetSet;
		}
	}

	public LMRiskAccGetSet findRiskAccGetByGetDutyCodeClone(
			String strGetDutyCode) {
		LMRiskAccGetSet tLMRiskAccGetSet = findRiskAccGetByGetDutyCode(strGetDutyCode);

		if (tLMRiskAccGetSet == null) {
			return tLMRiskAccGetSet;
		} else {
			LMRiskAccGetSet newtLMRiskAccGetSet = new LMRiskAccGetSet();
			for (int nIndex = 0; nIndex < tLMRiskAccGetSet.size(); nIndex++) {
				newtLMRiskAccGetSet.add(tLMRiskAccGetSet.get(nIndex + 1));
			}
			return newtLMRiskAccGetSet;
		}
	}

	public LMRiskAccGetSchema findRiskAccGet(String strGetDutyCode,
			String strInsuAccNo) {
		LMRiskAccGetSet tLMRiskAccGetSet = findRiskAccGetByGetDutyCode(strGetDutyCode);

		if (tLMRiskAccGetSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tLMRiskAccGetSet.size(); nIndex++) {
			LMRiskAccGetSchema tLMRiskAccGetSchema = tLMRiskAccGetSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tLMRiskAccGetSchema.getInsuAccNo()).equals(
					strInsuAccNo)) {
				return tLMRiskAccGetSchema;
			}
		}

		return null;
	}

	public LMRiskAccGetSchema findRiskAccGetClone(String strGetDutyCode,
			String strInsuAccNo) {
		LMRiskAccGetSchema tLMRiskAccGetSchema = findRiskAccGet(strGetDutyCode,
				strInsuAccNo);

		if (tLMRiskAccGetSchema == null) {
			return tLMRiskAccGetSchema;
		} else {
			return tLMRiskAccGetSchema.getSchema();
		}
	}

	/**
	 * 根据缴费计划编码查找LMRiskAccPay
	 * 
	 * @param strPayPlanCode
	 *            String
	 * @return LMRiskAccPaySet
	 */
	public LMRiskAccPaySet findRiskAccPayByPayPlanCode(String strPayPlanCode) {

		Hashtable hash = m_hashLMRiskAccPay;

		Object obj = hash.get(strPayPlanCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMRiskAccPaySet) obj;
			}
		} else {
			LMRiskAccPayDB tLMRiskAccPayDB = new LMRiskAccPayDB();

			tLMRiskAccPayDB.setPayPlanCode(strPayPlanCode);

			LMRiskAccPaySet tLMRiskAccPaySet = tLMRiskAccPayDB.query();

			if (tLMRiskAccPayDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);
				hash.put(strPayPlanCode, NOFOUND);
				return null;
			}

			hash.put(strPayPlanCode, tLMRiskAccPaySet);
			return tLMRiskAccPaySet;
		}
	}

	public LMRiskAccPaySet findRiskAccPayByPayPlanCodeClone(
			String strPayPlanCode) {
		LMRiskAccPaySet tLMRiskAccPaySet = findRiskAccPayByPayPlanCode(strPayPlanCode);

		if (tLMRiskAccPaySet == null) {
			return tLMRiskAccPaySet;
		} else {
			LMRiskAccPaySet newtLMRiskAccPaySet = new LMRiskAccPaySet();
			for (int nIndex = 0; nIndex < tLMRiskAccPaySet.size(); nIndex++) {
				newtLMRiskAccPaySet.add(tLMRiskAccPaySet.get(nIndex + 1));
			}
			return newtLMRiskAccPaySet;
		}
	}

	public LMRiskAccPaySchema findRiskAccPay(String strPayPlanCode,
			String strInsuAccNo) {
		LMRiskAccPaySet tLMRiskAccPaySet = findRiskAccPayByPayPlanCode(strPayPlanCode);

		if (tLMRiskAccPaySet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tLMRiskAccPaySet.size(); nIndex++) {
			LMRiskAccPaySchema tLMRiskAccPaySchema = tLMRiskAccPaySet
					.get(nIndex + 1);
			if (StrTool.cTrim(tLMRiskAccPaySchema.getInsuAccNo()).equals(
					strInsuAccNo)) {
				return tLMRiskAccPaySchema;
			}
		}

		return null;
	}

	public LMRiskAccPaySchema findRiskAccPayClone(String strPayPlanCode,
			String strInsuAccNo) {
		LMRiskAccPaySchema tLMRiskAccPaySchema = findRiskAccPay(strPayPlanCode,
				strInsuAccNo);

		if (tLMRiskAccPaySchema == null) {
			return tLMRiskAccPaySchema;
		} else {
			return tLMRiskAccPaySchema.getSchema();
		}
	}

	/**
	 * 根据险种编码查找LMRiskToAcc
	 * 
	 * @param strRiskCode
	 *            String
	 * @return LMRiskToAccSet
	 */
	public LMRiskToAccSet findRiskToAccByRiskCode(String strRiskCode) {

		Hashtable hash = m_hashLMRiskToAcc;

		Object obj = hash.get(strRiskCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMRiskToAccSet) obj;
			}
		} else {
			LMRiskToAccDB tLMRiskToAccDB = new LMRiskToAccDB();

			tLMRiskToAccDB.setRiskCode(strRiskCode);

			LMRiskToAccSet tLMRiskToAccSet = tLMRiskToAccDB.query();

			if (tLMRiskToAccDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLMRiskToAccDB.mErrors);
				hash.put(strRiskCode, NOFOUND);
				return null;
			}

			hash.put(strRiskCode, tLMRiskToAccSet);
			return tLMRiskToAccSet;
		}
	}

	public LMRiskToAccSet findRiskToAccByRiskCodeClone(String strRiskCode) {
		LMRiskToAccSet tLMRiskToAccSet = findRiskToAccByRiskCode(strRiskCode);

		if (tLMRiskToAccSet == null) {
			return tLMRiskToAccSet;
		} else {
			LMRiskToAccSet newLMRiskToAccSet = new LMRiskToAccSet();
			for (int nIndex = 0; nIndex < tLMRiskToAccSet.size(); nIndex++) {
				newLMRiskToAccSet.add(tLMRiskToAccSet.get(nIndex + 1));
			}
			return newLMRiskToAccSet;
		}
	}

	public LMRiskToAccSchema findRiskToAcc(String strRiskCode,
			String strInsuAccNo) {
		LMRiskToAccSet tLMRiskToAccSet = findRiskToAccByRiskCode(strRiskCode);

		if (tLMRiskToAccSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tLMRiskToAccSet.size(); nIndex++) {
			LMRiskToAccSchema tLMRiskToAccSchema = tLMRiskToAccSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tLMRiskToAccSchema.getInsuAccNo()).equals(
					strInsuAccNo)) {
				return tLMRiskToAccSchema;
			}
		}

		return null;
	}

	public LMRiskToAccSchema findRiskToAccClone(String strRiskCode,
			String strInsuAccNo) {
		LMRiskToAccSchema tLMRiskToAccSchema = findRiskToAcc(strRiskCode,
				strInsuAccNo);

		if (tLMRiskToAccSchema == null) {
			return tLMRiskToAccSchema;
		} else {
			return tLMRiskToAccSchema.getSchema();
		}
	}

	/**
	 * 根据险种编码和控制字段名称查找LMCheckField
	 * 
	 * @param strRiskCode
	 *            String
	 * @param strFieldName
	 *            String
	 * @return LMCheckFieldSet
	 */
	public LMCheckFieldSet findCheckFieldByRiskCode(String strRiskCode,
			String strFieldName) {

		Hashtable hash = m_hashLMCheckField;

		String strPK = strRiskCode.trim() + strFieldName.trim();
		Object obj = hash.get(strPK);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMCheckFieldSet) obj;
			}
		} else {
			LMCheckFieldDB tLMCheckFieldDB = new LMCheckFieldDB();

			tLMCheckFieldDB.setRiskCode(strRiskCode);
			tLMCheckFieldDB.setFieldName(strFieldName);

			LMCheckFieldSet tLMCheckFieldSet = tLMCheckFieldDB.query();

			if (tLMCheckFieldDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLMCheckFieldDB.mErrors);
				hash.put(strPK, NOFOUND);
				return null;
			}

			hash.put(strPK, tLMCheckFieldSet);
			return tLMCheckFieldSet;
		}
	}

	public LMCheckFieldSet findCheckFieldByRiskCodeClone(String strRiskCode,
			String strFieldName) {
		LMCheckFieldSet tLMCheckFieldSet = findCheckFieldByRiskCode(
				strRiskCode, strFieldName);

		if (tLMCheckFieldSet == null) {
			return tLMCheckFieldSet;
		} else {
			LMCheckFieldSet newLMCheckFieldSet = new LMCheckFieldSet();
			for (int nIndex = 0; nIndex < tLMCheckFieldSet.size(); nIndex++) {
				newLMCheckFieldSet.add(tLMCheckFieldSet.get(nIndex + 1));
			}
			return newLMCheckFieldSet;
		}
	}

	public LMCheckFieldSchema findCheckField(String strRiskCode,
			String strFieldName, String strSerialNo) {
		LMCheckFieldSet tLMCheckFieldSet = findCheckFieldByRiskCode(
				strRiskCode, strFieldName);

		if (tLMCheckFieldSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tLMCheckFieldSet.size(); nIndex++) {
			LMCheckFieldSchema tLMCheckFieldSchema = tLMCheckFieldSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tLMCheckFieldSchema.getSerialNo()).equals(
					strSerialNo)) {
				return tLMCheckFieldSchema;
			}
		}

		return null;
	}

	public LMCheckFieldSchema findCheckFieldClone(String strRiskCode,
			String strFieldName, String strSerialNo) {
		LMCheckFieldSchema tLMCheckFieldSchema = findCheckField(strRiskCode,
				strFieldName, strSerialNo);

		if (tLMCheckFieldSchema == null) {
			return tLMCheckFieldSchema;
		} else {
			return tLMCheckFieldSchema.getSchema();
		}
	}

	/**
	 * 根据责任编码查找LMDutyCtrl
	 * 
	 * @param strDutyCode
	 *            String
	 * @return LMDutyCtrlSet
	 */
	public LMDutyCtrlSet findDutyCtrlByDutyCode(String strDutyCode) {

		Hashtable hash = m_hashLMDutyCtrl;

		Object obj = hash.get(strDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMDutyCtrlSet) obj;
			}
		} else {
			LMDutyCtrlDB tLMDutyCtrlDB = new LMDutyCtrlDB();

			tLMDutyCtrlDB.setDutyCode(strDutyCode);

			LMDutyCtrlSet tLMDutyCtrlSet = tLMDutyCtrlDB.query();

			if (tLMDutyCtrlDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLMDutyCtrlDB.mErrors);
				hash.put(strDutyCode, NOFOUND);
				return null;
			}

			hash.put(strDutyCode, tLMDutyCtrlSet);
			return tLMDutyCtrlSet;
		}
	}

	public LMDutyCtrlSet findDutyCtrlByDutyCodeClone(String strDutyCode) {
		LMDutyCtrlSet tLMDutyCtrlSet = findDutyCtrlByDutyCode(strDutyCode);

		if (tLMDutyCtrlSet == null) {
			return tLMDutyCtrlSet;
		} else {
			LMDutyCtrlSet newLMDutyCtrlSet = new LMDutyCtrlSet();
			for (int nIndex = 0; nIndex < tLMDutyCtrlSet.size(); nIndex++) {
				newLMDutyCtrlSet.add(tLMDutyCtrlSet.get(nIndex + 1));
			}
			return newLMDutyCtrlSet;
		}
	}

	public LMDutyCtrlSchema findDutyCtrl(String strDutyCode,
			String strOtherCode, String strFieldName) {
		LMDutyCtrlSet tLMDutyCtrlSet = findDutyCtrlByDutyCode(strDutyCode);

		if (tLMDutyCtrlSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tLMDutyCtrlSet.size(); nIndex++) {
			LMDutyCtrlSchema tLMDutyCtrlSchema = tLMDutyCtrlSet.get(nIndex + 1);
			if (StrTool.cTrim(tLMDutyCtrlSchema.getOtherCode()).equals(
					strOtherCode)
					&& StrTool.cTrim(tLMDutyCtrlSchema.getFieldName()).equals(
							strFieldName)) {
				return tLMDutyCtrlSchema;
			}
		}

		return null;
	}

	public LMDutyCtrlSchema findDutyCtrlClone(String strDutyCode,
			String strOtherCode, String strFieldName) {
		LMDutyCtrlSchema tLMDutyCtrlSchema = findDutyCtrl(strDutyCode,
				strOtherCode, strFieldName);

		if (tLMDutyCtrlSchema == null) {
			return tLMDutyCtrlSchema;
		} else {
			return tLMDutyCtrlSchema.getSchema();
		}
	}

	/**
	 * 根据给付责任编码查找LMDutyGetAlive
	 * 
	 * @param strGetDutyCode
	 *            String
	 * @return LMDutyGetAliveSet
	 */
	public LMDutyGetAliveSet findDutyGetAliveByGetDutyCode(String strGetDutyCode) {

		Hashtable hash = m_hashLMDutyGetAlive;

		Object obj = hash.get(strGetDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMDutyGetAliveSet) obj;
			}
		} else {
			LMDutyGetAliveDB tLMDutyGetAliveDB = new LMDutyGetAliveDB();

			tLMDutyGetAliveDB.setGetDutyCode(strGetDutyCode);

			LMDutyGetAliveSet tLMDutyGetAliveSet = tLMDutyGetAliveDB.query();

			if (tLMDutyGetAliveDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLMDutyGetAliveDB.mErrors);
				hash.put(strGetDutyCode, NOFOUND);
				return null;
			}

			hash.put(strGetDutyCode, tLMDutyGetAliveSet);
			return tLMDutyGetAliveSet;
		}
	}

	public LMDutyGetAliveSet findDutyGetAliveByGetDutyCodeClone(
			String strGetDutyCode) {
		LMDutyGetAliveSet tLMDutyGetAliveSet = findDutyGetAliveByGetDutyCode(strGetDutyCode);

		if (tLMDutyGetAliveSet == null) {
			return tLMDutyGetAliveSet;
		} else {
			LMDutyGetAliveSet newLMDutyGetAliveSet = new LMDutyGetAliveSet();
			for (int nIndex = 0; nIndex < tLMDutyGetAliveSet.size(); nIndex++) {
				newLMDutyGetAliveSet.add(tLMDutyGetAliveSet.get(nIndex + 1));
			}
			return newLMDutyGetAliveSet;
		}
	}

	public LMDutyGetAliveSchema findDutyGetAlive(String strGetDutyCode,
			String strGetDutyKind) {
		LMDutyGetAliveSet tLMDutyGetAliveSet = findDutyGetAliveByGetDutyCode(strGetDutyCode);

		if (tLMDutyGetAliveSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tLMDutyGetAliveSet.size(); nIndex++) {
			LMDutyGetAliveSchema tLMDutyGetAliveSchema = tLMDutyGetAliveSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tLMDutyGetAliveSchema.getGetDutyKind()).equals(
					strGetDutyKind)) {
				return tLMDutyGetAliveSchema;
			}
		}

		return null;
	}

	public LMDutyGetAliveSchema findDutyGetAliveClone(String strGetDutyCode,
			String strGetDutyKind) {
		LMDutyGetAliveSchema tLMDutyGetAliveSchema = findDutyGetAlive(
				strGetDutyCode, strGetDutyKind);

		if (tLMDutyGetAliveSchema == null) {
			return tLMDutyGetAliveSchema;
		} else {
			return tLMDutyGetAliveSchema.getSchema();
		}
	}
	
	
	/**
	 * 根据表名获取Schema
	 * @param tableCode
	 * @return
	 */
	public LDTaxTableColumnConfigSchema findLDTaxTableColumnConfigSchemaByTableCode(String tableCode) {

		Hashtable hash = m_hashLDTaxTableColumnConfig;

		Object obj = hash.get(tableCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LDTaxTableColumnConfigSchema) obj;
			}
		} else {
			LDTaxTableColumnConfigDB tLDTaxTableColumnConfigDB = new LDTaxTableColumnConfigDB();

			tLDTaxTableColumnConfigDB.setTableCode(tableCode);
			if(!tLDTaxTableColumnConfigDB.getInfo()){
				hash.put(tableCode, NOFOUND);
				return null;
			}
			
			hash.put(tableCode, tLDTaxTableColumnConfigDB.getSchema());
			return tLDTaxTableColumnConfigDB.getSchema();
		}
	}

	/**
	 * 根据算法编码查找LMCalFactor
	 * 
	 * @param strCalCode
	 *            String
	 * @return LMCalFactorSet
	 */
	public LMCalFactorSet findCalFactorByCalCode(String strCalCode) {

		Hashtable hash = m_hashLMCalFactor;

		Object obj = hash.get(strCalCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (LMCalFactorSet) obj;
			}
		} else {
			LMCalFactorDB tLMCalFactorDB = new LMCalFactorDB();

			tLMCalFactorDB.setCalCode(strCalCode);

			LMCalFactorSet tLMCalFactorSet = tLMCalFactorDB.query();

			if (tLMCalFactorDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLMCalFactorDB.mErrors);
				hash.put(strCalCode, NOFOUND);
				return null;
			}

			hash.put(strCalCode, tLMCalFactorSet);
			return tLMCalFactorSet;
		}
	}

	public LMCalFactorSet findCalFactorByCalCodeClone(String strCalCode) {
		LMCalFactorSet tLMCalFactorSet = findCalFactorByCalCode(strCalCode);

		if (tLMCalFactorSet == null) {
			return tLMCalFactorSet;
		} else {
			LMCalFactorSet newLMCalFactorSet = new LMCalFactorSet();
			for (int nIndex = 0; nIndex < tLMCalFactorSet.size(); nIndex++) {
				newLMCalFactorSet.add(tLMCalFactorSet.get(nIndex + 1));
			}
			return newLMCalFactorSet;
		}
	}

	public LMCalFactorSchema findCalFactor(String strCalCode,
			String strFactorCode) {
		LMCalFactorSet tLMCalFactorSet = findCalFactorByCalCode(strCalCode);

		if (tLMCalFactorSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tLMCalFactorSet.size(); nIndex++) {
			LMCalFactorSchema tLMCalFactorSchema = tLMCalFactorSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tLMCalFactorSchema.getFactorCode()).equals(
					strFactorCode)) {
				return tLMCalFactorSchema;
			}
		}

		return null;
	}

	public LMCalFactorSchema findCalFactorClone(String strCalCode,
			String strFactorCode) {
		LMCalFactorSchema tLMCalFactorSchema = findCalFactor(strCalCode,
				strFactorCode);

		if (tLMCalFactorSchema == null) {
			return tLMCalFactorSchema;
		} else {
			return tLMCalFactorSchema.getSchema();
		}
	}
}
