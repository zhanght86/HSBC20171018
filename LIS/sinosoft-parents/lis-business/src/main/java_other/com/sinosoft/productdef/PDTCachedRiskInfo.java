

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.productdef;

import java.util.Hashtable;

import com.sinosoft.lis.db.PD_LMCalFactorDB;
import com.sinosoft.lis.db.PD_LMCheckFieldDB;
import com.sinosoft.lis.db.PD_LMDutyCtrlDB;
import com.sinosoft.lis.db.PD_LMDutyDB;
import com.sinosoft.lis.db.PD_LMDutyGetAliveDB;
import com.sinosoft.lis.db.PD_LMDutyGetDB;
import com.sinosoft.lis.db.PD_LMDutyGetRelaDB;
import com.sinosoft.lis.db.PD_LMDutyPayDB;
import com.sinosoft.lis.db.PD_LMDutyPayRelaDB;
import com.sinosoft.lis.db.PD_LMRiskAccGetDB;
import com.sinosoft.lis.db.PD_LMRiskAccPayDB;
import com.sinosoft.lis.db.PD_LMRiskAppDB;
import com.sinosoft.lis.db.PD_LMRiskBonusDB;
import com.sinosoft.lis.db.PD_LMRiskDB;
import com.sinosoft.lis.db.PD_LMRiskDutyDB;
import com.sinosoft.lis.db.PD_LMRiskInsuAccDB;
import com.sinosoft.lis.db.PD_LMRiskToAccDB;
import com.sinosoft.lis.db.PD_LMCalModeDB;
import com.sinosoft.lis.schema.PD_LMCalFactorSchema;
import com.sinosoft.lis.schema.PD_LMCalModeSchema;
import com.sinosoft.lis.schema.PD_LMCheckFieldSchema;
import com.sinosoft.lis.schema.PD_LMDutyCtrlSchema;
import com.sinosoft.lis.schema.PD_LMDutyGetAliveSchema;
import com.sinosoft.lis.schema.PD_LMDutyGetRelaSchema;
import com.sinosoft.lis.schema.PD_LMDutyGetSchema;
import com.sinosoft.lis.schema.PD_LMDutyPayRelaSchema;
import com.sinosoft.lis.schema.PD_LMDutyPaySchema;
import com.sinosoft.lis.schema.PD_LMDutySchema;
import com.sinosoft.lis.schema.PD_LMRiskAccGetSchema;
import com.sinosoft.lis.schema.PD_LMRiskAccPaySchema;
import com.sinosoft.lis.schema.PD_LMRiskAppSchema;
import com.sinosoft.lis.schema.PD_LMRiskBonusSchema;
import com.sinosoft.lis.schema.PD_LMRiskDutySchema;
import com.sinosoft.lis.schema.PD_LMRiskInsuAccSchema;
import com.sinosoft.lis.schema.PD_LMRiskSchema;
import com.sinosoft.lis.schema.PD_LMRiskToAccSchema;
import com.sinosoft.lis.vschema.PD_LMCalFactorSet;
import com.sinosoft.lis.vschema.PD_LMCheckFieldSet;
import com.sinosoft.lis.vschema.PD_LMDutyCtrlSet;
import com.sinosoft.lis.vschema.PD_LMDutyGetAliveSet;
import com.sinosoft.lis.vschema.PD_LMDutyGetRelaSet;
import com.sinosoft.lis.vschema.PD_LMDutyPayRelaSet;
import com.sinosoft.lis.vschema.PD_LMRiskAccGetSet;
import com.sinosoft.lis.vschema.PD_LMRiskAccPaySet;
import com.sinosoft.lis.vschema.PD_LMRiskDutySet;
import com.sinosoft.lis.vschema.PD_LMRiskToAccSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;


/*
 * <p>Title: 险种相关信息缓存类 </p> <p>Description: 缓存程序中经常会用到的险种相关信息 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft</p> @author Kevin
 * 
 * @version 1.0 @date 2003-07-29
 */
public class PDTCachedRiskInfo {
	// @Field
	public CErrors mErrors = new CErrors(); // 错误信息

	// 用来缓存信息的结构

	// 一些只可能按单个关键信息查询的信息
	private Hashtable m_hashPD_LMRisk = new Hashtable();
	private Hashtable m_hashPD_LMRiskApp = new Hashtable();
	private Hashtable m_hashPD_LMDuty = new Hashtable();
	private Hashtable m_hashPD_LMDutyGet = new Hashtable();
	private Hashtable m_hashPD_LMDutyPay = new Hashtable();
	private Hashtable m_hashPD_LMRiskInsuAcc = new Hashtable();
	private Hashtable m_hashPD_LMCalMode = new Hashtable();
	private Hashtable m_hashPD_LMRiskBonus = new Hashtable();

	// 另外一些可能按多个关键信息查询的信息
	private Hashtable m_hashPD_LMRiskDuty = new Hashtable();
	private Hashtable m_hashPD_LMDutyGetRela = new Hashtable();
	private Hashtable m_hashPD_LMDutyPayRela = new Hashtable();
	private Hashtable m_hashPD_LMRiskAccGet = new Hashtable();
	private Hashtable m_hashPD_LMRiskAccPay = new Hashtable();
	private Hashtable m_hashPD_LMRiskToAcc = new Hashtable();
	private Hashtable m_hashPD_LMCheckField = new Hashtable();
	private Hashtable m_hashPD_LMDutyCtrl = new Hashtable();
	private Hashtable m_hashPD_LMDutyGetAlive = new Hashtable();
	private Hashtable m_hashPD_LMCalFactor = new Hashtable();

	// 静态变量
	private static String NOFOUND = "NOFOUND";
	private static PDTCachedRiskInfo m_cri = null;

	// @Constructor
	private PDTCachedRiskInfo() {
	}

	public static PDTCachedRiskInfo getInstance() {
		if (m_cri == null) {
			m_cri = new PDTCachedRiskInfo();
		}

		m_cri.mErrors.clearErrors();

		return m_cri;
	}
	

	public void refresh() {
		// Kevin 2003-07-29
		// 刷新的时候，可能会出现同步问题，但是机率很小。
		m_hashPD_LMRisk.clear();
		m_hashPD_LMRiskApp.clear();
		m_hashPD_LMDuty.clear();
		m_hashPD_LMDutyGet.clear();
		m_hashPD_LMDutyPay.clear();
		m_hashPD_LMRiskInsuAcc.clear();
		m_hashPD_LMCalMode.clear();
		m_hashPD_LMRiskBonus.clear();

		m_hashPD_LMRiskDuty.clear();
		m_hashPD_LMDutyGetRela.clear();
		m_hashPD_LMDutyPayRela.clear();
		m_hashPD_LMRiskAccGet.clear();
		m_hashPD_LMRiskAccPay.clear();
		m_hashPD_LMRiskToAcc.clear();
		m_hashPD_LMCheckField.clear();
		m_hashPD_LMDutyCtrl.clear();
		m_hashPD_LMDutyGetAlive.clear();
		m_hashPD_LMCalFactor.clear();
	}

	/**
	 * 按险种编码查找PD_LMRisk
	 * 
	 * @param strRiskCode
	 *            String
	 * @return PD_LMRiskSchema
	 */
	public PD_LMRiskSchema findRiskByRiskCode(String strRiskCode) {

		Hashtable hash = m_hashPD_LMRisk;

		Object obj = hash.get(strRiskCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMRiskSchema) obj;
			}
		} else {
			PD_LMRiskDB tPD_LMRiskDB = new PD_LMRiskDB();

			tPD_LMRiskDB.setRiskCode(strRiskCode);

			if (!tPD_LMRiskDB.getInfo()) {
				mErrors.copyAllErrors(tPD_LMRiskDB.mErrors);
				hash.put(strRiskCode, NOFOUND);
				return null;
			}

			hash.put(strRiskCode, tPD_LMRiskDB.getSchema());
			return tPD_LMRiskDB.getSchema();
		}
	}

	public PD_LMRiskSchema findRiskByRiskCodeClone(String strRiskCode) {
		PD_LMRiskSchema tPD_LMRiskSchema = findRiskByRiskCode(strRiskCode);

		if (tPD_LMRiskSchema == null) {
			return tPD_LMRiskSchema;
		} else {
			return tPD_LMRiskSchema.getSchema();
		}
	}

	/**
	 * 按险种编码查找PD_LMRiskApp
	 * 
	 * @param strRiskCode
	 *            String
	 * @return PD_LMRiskAppSchema
	 */
	public PD_LMRiskAppSchema findRiskAppByRiskCode(String strRiskCode) {

		Hashtable hash = m_hashPD_LMRiskApp;

		Object obj = hash.get(strRiskCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMRiskAppSchema) obj;
			}
		} else {
			PD_LMRiskAppDB tPD_LMRiskAppDB = new PD_LMRiskAppDB();

			tPD_LMRiskAppDB.setRiskCode(strRiskCode);

			if (!tPD_LMRiskAppDB.getInfo()) {
				mErrors.copyAllErrors(tPD_LMRiskAppDB.mErrors);
				hash.put(strRiskCode, NOFOUND);
				return null;
			}

			hash.put(strRiskCode, tPD_LMRiskAppDB.getSchema());
			return tPD_LMRiskAppDB.getSchema();
		}
	}

	public PD_LMRiskAppSchema findRiskAppByRiskCodeClone(String strRiskCode) {
		PD_LMRiskAppSchema tPD_LMRiskAppSchema = findRiskAppByRiskCode(strRiskCode);

		if (tPD_LMRiskAppSchema == null) {
			return tPD_LMRiskAppSchema;
		} else {
			return tPD_LMRiskAppSchema.getSchema();
		}
	}

	/**
	 * 按责任编码查找PD_LMDuty
	 * 
	 * @param strDutyCode
	 *            String
	 * @return PD_LMDutySchema
	 */
	public PD_LMDutySchema findDutyByDutyCode(String strDutyCode) {

		Hashtable hash = m_hashPD_LMDuty;

		Object obj = hash.get(strDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				System.out.println("哈哈====="
						+ ((PD_LMDutySchema) obj).getDutyCode());
				return (PD_LMDutySchema) obj;
			}

		} else {
			PD_LMDutyDB tPD_LMDutyDB = new PD_LMDutyDB();

			// 增加附加责任(责任代码大于6位,现在是10位)处理,add by Minim at 2004-04-28
			String dutyCode = strDutyCode;
			strDutyCode = strDutyCode.substring(0, 6);
			// ***************************************

			tPD_LMDutyDB.setDutyCode(strDutyCode);

			if (!tPD_LMDutyDB.getInfo()) {
				mErrors.copyAllErrors(tPD_LMDutyDB.mErrors);
				hash.put(strDutyCode, NOFOUND);
				return null;
			}

			// 增加附加责任(责任代码大于6位,现在是10位)处理,add by Minim at 2004-04-28
			strDutyCode = dutyCode;
			// ***************************************

			hash.put(strDutyCode, tPD_LMDutyDB.getSchema());
			System.out.println("哈哈====");
			return tPD_LMDutyDB.getSchema();
		}
	}

	public PD_LMDutySchema findDutyByDutyCodeClone(String strDutyCode) {
		PD_LMDutySchema tPD_LMDutySchema = findDutyByDutyCode(strDutyCode);

		if (tPD_LMDutySchema == null) {
			return tPD_LMDutySchema;
		} else {
			return tPD_LMDutySchema.getSchema();
		}
	}

	/**
	 * 根据给付代码查找PD_LMDutyGet
	 * 
	 * @param strGetDutyCode
	 *            String
	 * @return PD_LMDutyGetSchema
	 */
	public PD_LMDutyGetSchema findDutyGetByGetDutyCode(String strGetDutyCode) {

		Hashtable hash = m_hashPD_LMDutyGet;

		Object obj = hash.get(strGetDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMDutyGetSchema) obj;
			}
		} else {
			PD_LMDutyGetDB tPD_LMDutyGetDB = new PD_LMDutyGetDB();

			tPD_LMDutyGetDB.setGetDutyCode(strGetDutyCode);

			if (!tPD_LMDutyGetDB.getInfo()) {
				mErrors.copyAllErrors(tPD_LMDutyGetDB.mErrors);
				hash.put(strGetDutyCode, NOFOUND);
				return null;
			}

			hash.put(strGetDutyCode, tPD_LMDutyGetDB.getSchema());
			return tPD_LMDutyGetDB.getSchema();
		}
	}

	public PD_LMDutyGetSchema findDutyGetByGetDutyCodeClone(String strGetDutyCode) {
		PD_LMDutyGetSchema tPD_LMDutyGetSchema = findDutyGetByGetDutyCode(strGetDutyCode);

		if (tPD_LMDutyGetSchema == null) {
			return tPD_LMDutyGetSchema;
		} else {
			return tPD_LMDutyGetSchema.getSchema();
		}
	}

	/**
	 * 根据缴费编码查找PD_LMDutyPay
	 * 
	 * @param strPayPlanCode
	 *            String
	 * @return PD_LMDutyPaySchema
	 */
	public PD_LMDutyPaySchema findDutyPayByPayPlanCode(String strPayPlanCode) {

		Hashtable hash = m_hashPD_LMDutyPay;

		Object obj = hash.get(strPayPlanCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMDutyPaySchema) obj;
			}
		} else {
			PD_LMDutyPayDB tPD_LMDutyPayDB = new PD_LMDutyPayDB();

			tPD_LMDutyPayDB.setPayPlanCode(strPayPlanCode);

			if (!tPD_LMDutyPayDB.getInfo()) {
				mErrors.copyAllErrors(tPD_LMDutyPayDB.mErrors);
				hash.put(strPayPlanCode, NOFOUND);
				return null;
			}

			hash.put(strPayPlanCode, tPD_LMDutyPayDB.getSchema());
			return tPD_LMDutyPayDB.getSchema();
		}
	}

	public PD_LMDutyPaySchema findDutyPayByPayPlanCodeClone(String strPayPlanCode) {
		PD_LMDutyPaySchema tPD_LMDutyPaySchema = findDutyPayByPayPlanCode(strPayPlanCode);

		if (tPD_LMDutyPaySchema == null) {
			return tPD_LMDutyPaySchema;
		} else {
			return tPD_LMDutyPaySchema.getSchema();
		}
	}

	/**
	 * 根据保险帐户号码查找PD_LMRiskInsuAcc
	 * 
	 * @param strInsuAccNo
	 *            String
	 * @return PD_LMRiskInsuAccSchema
	 */
	public PD_LMRiskInsuAccSchema findRiskInsuAccByInsuAccNo(String strInsuAccNo) {

		Hashtable hash = m_hashPD_LMRiskInsuAcc;

		Object obj = hash.get(strInsuAccNo);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMRiskInsuAccSchema) obj;
			}
		} else {
			PD_LMRiskInsuAccDB tPD_LMRiskInsuAccDB = new PD_LMRiskInsuAccDB();

			tPD_LMRiskInsuAccDB.setInsuAccNo(strInsuAccNo);

			if (!tPD_LMRiskInsuAccDB.getInfo()) {
				mErrors.copyAllErrors(tPD_LMRiskInsuAccDB.mErrors);
				hash.put(strInsuAccNo, NOFOUND);
				return null;
			}

			hash.put(strInsuAccNo, tPD_LMRiskInsuAccDB.getSchema());
			return tPD_LMRiskInsuAccDB.getSchema();
		}
	}

	public PD_LMRiskInsuAccSchema findRiskInsuAccByInsuAccNoClone(
			String strInsuAccNo) {
		PD_LMRiskInsuAccSchema tPD_LMRiskInsuAccSchema = findRiskInsuAccByInsuAccNo(strInsuAccNo);

		if (tPD_LMRiskInsuAccSchema == null) {
			return tPD_LMRiskInsuAccSchema;
		} else {
			return tPD_LMRiskInsuAccSchema.getSchema();
		}
	}

	/**
	 * 根据算法编码查找PD_LMCaPD_LMode
	 * 
	 * @param strCalCode
	 *            String
	 * @return PD_LMCaPD_LModeSchema
	 */
	public PD_LMCalModeSchema findCalModeByCalCode(String strCalCode) {

		Hashtable hash = m_hashPD_LMCalMode;

		Object obj = hash.get(strCalCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMCalModeSchema) obj;
			}
		} else {
			PD_LMCalModeDB tPD_LMCaLModeDB = new PD_LMCalModeDB();

			tPD_LMCaLModeDB.setCalCode(strCalCode);

			if (!tPD_LMCaLModeDB.getInfo()) {
				mErrors.copyAllErrors(tPD_LMCaLModeDB.mErrors);
				hash.put(strCalCode, NOFOUND);
				return null;
			}

			hash.put(strCalCode, tPD_LMCaLModeDB.getSchema());
			return tPD_LMCaLModeDB.getSchema();
		}
	}

	public PD_LMCalModeSchema findCaLModeByCalCodeClone(String strCalCode) {
		PD_LMCalModeSchema tPD_LMCalModeSchema = findCalModeByCalCode(strCalCode);

		if (tPD_LMCalModeSchema == null) {
			return tPD_LMCalModeSchema;
		} else {
			return tPD_LMCalModeSchema.getSchema();
		}
	}

	/**
	 * 按险种编码查找PD_LMRiskBonus
	 * 
	 * @param strRiskCode
	 *            String
	 * @return PD_LMRiskBonusSchema
	 */
	public PD_LMRiskBonusSchema findRiskBonusByRiskCode(String strRiskCode) {

		Hashtable hash = m_hashPD_LMRiskBonus;

		Object obj = hash.get(strRiskCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMRiskBonusSchema) obj;
			}
		} else {
			PD_LMRiskBonusDB tPD_LMRiskBonusDB = new PD_LMRiskBonusDB();

			tPD_LMRiskBonusDB.setRiskCode(strRiskCode);

			if (!tPD_LMRiskBonusDB.getInfo()) {
				mErrors.copyAllErrors(tPD_LMRiskBonusDB.mErrors);
				hash.put(strRiskCode, NOFOUND);
				return null;
			}

			hash.put(strRiskCode, tPD_LMRiskBonusDB.getSchema());
			return tPD_LMRiskBonusDB.getSchema();
		}
	}

	public PD_LMRiskBonusSchema findRiskBonusByRiskCodeClone(String strRiskCode) {
		PD_LMRiskBonusSchema tPD_LMRiskBonusSchema = findRiskBonusByRiskCode(strRiskCode);

		if (tPD_LMRiskBonusSchema == null) {
			return tPD_LMRiskBonusSchema;
		} else {
			return tPD_LMRiskBonusSchema.getSchema();
		}
	}

	/**
	 * 根据险种编码取得PD_LMRiskDuty
	 * 
	 * @param strRiskCode
	 *            String
	 * @return PD_LMRiskDutySet
	 */
	public PD_LMRiskDutySet findRiskDutyByRiskCode(String strRiskCode) {

		Hashtable hash = m_hashPD_LMRiskDuty;

		Object obj = hash.get(strRiskCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMRiskDutySet) obj;
			}
		} else {
			PD_LMRiskDutyDB tPD_LMRiskDutyDB = new PD_LMRiskDutyDB();

			tPD_LMRiskDutyDB.setRiskCode(strRiskCode);

			PD_LMRiskDutySet tPD_LMRiskDutySet = tPD_LMRiskDutyDB.query();

			if (tPD_LMRiskDutyDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tPD_LMRiskDutyDB.mErrors);
				hash.put(strRiskCode, NOFOUND);
				return null;
			}

			hash.put(strRiskCode, tPD_LMRiskDutySet);
			return tPD_LMRiskDutySet;
		}
	}

	public PD_LMRiskDutySet findRiskDutyByRiskCodeClone(String strRiskCode) {
		PD_LMRiskDutySet tPD_LMRiskDutySet = findRiskDutyByRiskCode(strRiskCode);

		if (tPD_LMRiskDutySet == null) {
			return tPD_LMRiskDutySet;
		} else {
			PD_LMRiskDutySet newPD_LMRiskDutySet = new PD_LMRiskDutySet();
			for (int nIndex = 0; nIndex < tPD_LMRiskDutySet.size(); nIndex++) {
				newPD_LMRiskDutySet.add(tPD_LMRiskDutySet.get(nIndex + 1));
			}
			return newPD_LMRiskDutySet;
		}
	}

	public PD_LMRiskDutySchema findRiskDuty(String strRiskCode, String strDutyCode) {
		PD_LMRiskDutySet tPD_LMRiskDutySet = findRiskDutyByRiskCode(strRiskCode);

		if (tPD_LMRiskDutySet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tPD_LMRiskDutySet.size(); nIndex++) {
			PD_LMRiskDutySchema tPD_LMRiskDutySchema = tPD_LMRiskDutySet.get(nIndex + 1);
			if (StrTool.cTrim(tPD_LMRiskDutySchema.getDutyCode()).equals(
					strDutyCode)) {
				return tPD_LMRiskDutySchema;
			}
		}

		return null;
	}

	public PD_LMRiskDutySchema findRiskDutyClone(String strRiskCode,
			String strDutyCode) {
		PD_LMRiskDutySchema tPD_LMRiskDutySchema = findRiskDuty(strRiskCode,
				strDutyCode);

		if (tPD_LMRiskDutySchema == null) {
			return tPD_LMRiskDutySchema;
		} else {
			return tPD_LMRiskDutySchema.getSchema();
		}
	}

	/**
	 * 根据责任编码查询PD_LMDutyGetRela
	 * 
	 * @param strDutyCode
	 *            String
	 * @return PD_LMDutyGetRelaSet
	 */
	public PD_LMDutyGetRelaSet findDutyGetRelaByDutyCode(String strDutyCode) {

		Hashtable hash = m_hashPD_LMDutyGetRela;

		Object obj = hash.get(strDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMDutyGetRelaSet) obj;
			}
		} else {
			PD_LMDutyGetRelaDB tPD_LMDutyGetRelaDB = new PD_LMDutyGetRelaDB();

			// 增加附加责任(责任代码大于6位,现在是10位)处理,add by Minim at 2004-04-28
			String dutyCode = strDutyCode;
			strDutyCode = strDutyCode.substring(0, 6);
			// ***************************************

			tPD_LMDutyGetRelaDB.setDutyCode(strDutyCode);

			PD_LMDutyGetRelaSet tPD_LMDutyGetRelaSet = tPD_LMDutyGetRelaDB.query();

			if (tPD_LMDutyGetRelaDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tPD_LMDutyGetRelaDB.mErrors);
				hash.put(strDutyCode, NOFOUND);
				return null;
			}

			// 增加附加责任(责任代码大于6位,现在是10位)处理,add by Minim at 2004-04-28
			strDutyCode = dutyCode;
			// ***************************************

			hash.put(strDutyCode, tPD_LMDutyGetRelaSet);
			return tPD_LMDutyGetRelaSet;
		}
	}

	public PD_LMDutyGetRelaSet findDutyGetRelaByDutyCodeClone(String strDutyCode) {
		PD_LMDutyGetRelaSet tPD_LMDutyGetRelaSet = findDutyGetRelaByDutyCode(strDutyCode);

		if (tPD_LMDutyGetRelaSet == null) {
			return tPD_LMDutyGetRelaSet;
		} else {
			PD_LMDutyGetRelaSet newPD_LMDutyGetRelaSet = new PD_LMDutyGetRelaSet();
			for (int nIndex = 0; nIndex < tPD_LMDutyGetRelaSet.size(); nIndex++) {
				newPD_LMDutyGetRelaSet.add(tPD_LMDutyGetRelaSet.get(nIndex + 1));
			}
			return newPD_LMDutyGetRelaSet;
		}
	}

	public PD_LMDutyGetRelaSchema findDutyGetRela(String strDutyCode,
			String strGetDutyCode) {
		PD_LMDutyGetRelaSet tPD_LMDutyGetRelaSet = findDutyGetRelaByDutyCode(strDutyCode);

		if (tPD_LMDutyGetRelaSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tPD_LMDutyGetRelaSet.size(); nIndex++) {
			PD_LMDutyGetRelaSchema tPD_LMDutyGetRelaSchema = tPD_LMDutyGetRelaSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tPD_LMDutyGetRelaSchema.getDutyCode()).equals(
					strGetDutyCode)) {
				return tPD_LMDutyGetRelaSchema;
			}
		}

		return null;
	}

	public PD_LMDutyGetRelaSchema findDutyGetRelaClone(String strDutyCode,
			String strGetDutyCode) {
		PD_LMDutyGetRelaSchema tPD_LMDutyGetRelaSchema = findDutyGetRela(strDutyCode,
				strGetDutyCode);

		if (tPD_LMDutyGetRelaSchema == null) {
			return tPD_LMDutyGetRelaSchema;
		} else {
			return tPD_LMDutyGetRelaSchema.getSchema();
		}
	}

	/**
	 * 根据责任编码查找PD_LMDutyPayRela
	 * 
	 * @param strDutyCode
	 *            String
	 * @return PD_LMDutyPayRelaSet
	 */
	public PD_LMDutyPayRelaSet findDutyPayRelaByDutyCode(String strDutyCode) {

		Hashtable hash = m_hashPD_LMDutyPayRela;

		Object obj = hash.get(strDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMDutyPayRelaSet) obj;
			}
		} else {
			PD_LMDutyPayRelaDB tPD_LMDutyPayRelaDB = new PD_LMDutyPayRelaDB();

			// 增加附加责任(责任代码大于6位,现在是10位)处理,add by Minim at 2004-04-28
			String dutyCode = strDutyCode;
			strDutyCode = strDutyCode.substring(0, 6);
			// ***************************************

			tPD_LMDutyPayRelaDB.setDutyCode(strDutyCode);

			PD_LMDutyPayRelaSet tPD_LMDutyPayRelaSet = tPD_LMDutyPayRelaDB.query();

			if (tPD_LMDutyPayRelaDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tPD_LMDutyPayRelaDB.mErrors);
				hash.put(strDutyCode, NOFOUND);
				return null;
			}

			// 增加附加责任(责任代码大于6位,现在是10位)处理,add by Minim at 2004-04-28
			strDutyCode = dutyCode;
			// ***************************************

			hash.put(strDutyCode, tPD_LMDutyPayRelaSet);
			return tPD_LMDutyPayRelaSet;
		}
	}

	public PD_LMDutyPayRelaSet findDutyPayRelaByDutyCodeClone(String strDutyCode) {
		PD_LMDutyPayRelaSet tPD_LMDutyPayRelaSet = findDutyPayRelaByDutyCode(strDutyCode);

		if (tPD_LMDutyPayRelaSet == null) {
			return tPD_LMDutyPayRelaSet;
		} else {
			PD_LMDutyPayRelaSet newPD_LMDutyPayRelaSet = new PD_LMDutyPayRelaSet();
			for (int nIndex = 0; nIndex < tPD_LMDutyPayRelaSet.size(); nIndex++) {
				newPD_LMDutyPayRelaSet.add(tPD_LMDutyPayRelaSet.get(nIndex + 1));
			}
			return newPD_LMDutyPayRelaSet;
		}
	}

	public PD_LMDutyPayRelaSchema findDutyPayRela(String strDutyCode,
			String strPayPlanCode) {
		PD_LMDutyPayRelaSet tPD_LMDutyPayRelaSet = findDutyPayRelaByDutyCode(strDutyCode);

		if (tPD_LMDutyPayRelaSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tPD_LMDutyPayRelaSet.size(); nIndex++) {
			PD_LMDutyPayRelaSchema tPD_LMDutyPayRelaSchema = tPD_LMDutyPayRelaSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tPD_LMDutyPayRelaSchema.getPayPlanCode()).equals(
					strPayPlanCode)) {
				return tPD_LMDutyPayRelaSchema;
			}
		}

		return null;
	}

	public PD_LMDutyPayRelaSchema findDutyPayRelaClone(String strDutyCode,
			String strPayPlanCode) {
		PD_LMDutyPayRelaSchema tPD_LMDutyPayRelaSchema = findDutyPayRela(strDutyCode,
				strPayPlanCode);

		if (tPD_LMDutyPayRelaSchema == null) {
			return tPD_LMDutyPayRelaSchema;
		} else {
			return tPD_LMDutyPayRelaSchema.getSchema();
		}
	}

	/**
	 * 根据给付责任编码查询PD_LMRiskAccGet
	 * 
	 * @param strGetDutyCode
	 *            String
	 * @return PD_LMRiskAccGetSet
	 */
	public PD_LMRiskAccGetSet findRiskAccGetByGetDutyCode(String strGetDutyCode) {

		Hashtable hash = m_hashPD_LMRiskAccGet;

		Object obj = hash.get(strGetDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMRiskAccGetSet) obj;
			}
		} else {
			PD_LMRiskAccGetDB tPD_LMRiskAccGetDB = new PD_LMRiskAccGetDB();

			tPD_LMRiskAccGetDB.setGetDutyCode(strGetDutyCode);

			PD_LMRiskAccGetSet tPD_LMRiskAccGetSet = tPD_LMRiskAccGetDB.query();

			if (tPD_LMRiskAccGetDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tPD_LMRiskAccGetDB.mErrors);
				hash.put(strGetDutyCode, NOFOUND);
				return null;
			}

			hash.put(strGetDutyCode, tPD_LMRiskAccGetSet);
			return tPD_LMRiskAccGetSet;
		}
	}

	public PD_LMRiskAccGetSet findRiskAccGetByGetDutyCodeClone(
			String strGetDutyCode) {
		PD_LMRiskAccGetSet tPD_LMRiskAccGetSet = findRiskAccGetByGetDutyCode(strGetDutyCode);

		if (tPD_LMRiskAccGetSet == null) {
			return tPD_LMRiskAccGetSet;
		} else {
			PD_LMRiskAccGetSet newtPD_LMRiskAccGetSet = new PD_LMRiskAccGetSet();
			for (int nIndex = 0; nIndex < tPD_LMRiskAccGetSet.size(); nIndex++) {
				newtPD_LMRiskAccGetSet.add(tPD_LMRiskAccGetSet.get(nIndex + 1));
			}
			return newtPD_LMRiskAccGetSet;
		}
	}

	public PD_LMRiskAccGetSchema findRiskAccGet(String strGetDutyCode,
			String strInsuAccNo) {
		PD_LMRiskAccGetSet tPD_LMRiskAccGetSet = findRiskAccGetByGetDutyCode(strGetDutyCode);

		if (tPD_LMRiskAccGetSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tPD_LMRiskAccGetSet.size(); nIndex++) {
			PD_LMRiskAccGetSchema tPD_LMRiskAccGetSchema = tPD_LMRiskAccGetSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tPD_LMRiskAccGetSchema.getInsuAccNo()).equals(
					strInsuAccNo)) {
				return tPD_LMRiskAccGetSchema;
			}
		}

		return null;
	}

	public PD_LMRiskAccGetSchema findRiskAccGetClone(String strGetDutyCode,
			String strInsuAccNo) {
		PD_LMRiskAccGetSchema tPD_LMRiskAccGetSchema = findRiskAccGet(strGetDutyCode,
				strInsuAccNo);

		if (tPD_LMRiskAccGetSchema == null) {
			return tPD_LMRiskAccGetSchema;
		} else {
			return tPD_LMRiskAccGetSchema.getSchema();
		}
	}

	/**
	 * 根据缴费计划编码查找PD_LMRiskAccPay
	 * 
	 * @param strPayPlanCode
	 *            String
	 * @return PD_LMRiskAccPaySet
	 */
	public PD_LMRiskAccPaySet findRiskAccPayByPayPlanCode(String strPayPlanCode) {

		Hashtable hash = m_hashPD_LMRiskAccPay;

		Object obj = hash.get(strPayPlanCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMRiskAccPaySet) obj;
			}
		} else {
			PD_LMRiskAccPayDB tPD_LMRiskAccPayDB = new PD_LMRiskAccPayDB();

			tPD_LMRiskAccPayDB.setPayPlanCode(strPayPlanCode);

			PD_LMRiskAccPaySet tPD_LMRiskAccPaySet = tPD_LMRiskAccPayDB.query();

			if (tPD_LMRiskAccPayDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tPD_LMRiskAccPayDB.mErrors);
				hash.put(strPayPlanCode, NOFOUND);
				return null;
			}

			hash.put(strPayPlanCode, tPD_LMRiskAccPaySet);
			return tPD_LMRiskAccPaySet;
		}
	}

	public PD_LMRiskAccPaySet findRiskAccPayByPayPlanCodeClone(
			String strPayPlanCode) {
		PD_LMRiskAccPaySet tPD_LMRiskAccPaySet = findRiskAccPayByPayPlanCode(strPayPlanCode);

		if (tPD_LMRiskAccPaySet == null) {
			return tPD_LMRiskAccPaySet;
		} else {
			PD_LMRiskAccPaySet newtPD_LMRiskAccPaySet = new PD_LMRiskAccPaySet();
			for (int nIndex = 0; nIndex < tPD_LMRiskAccPaySet.size(); nIndex++) {
				newtPD_LMRiskAccPaySet.add(tPD_LMRiskAccPaySet.get(nIndex + 1));
			}
			return newtPD_LMRiskAccPaySet;
		}
	}

	public PD_LMRiskAccPaySchema findRiskAccPay(String strPayPlanCode,
			String strInsuAccNo) {
		PD_LMRiskAccPaySet tPD_LMRiskAccPaySet = findRiskAccPayByPayPlanCode(strPayPlanCode);

		if (tPD_LMRiskAccPaySet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tPD_LMRiskAccPaySet.size(); nIndex++) {
			PD_LMRiskAccPaySchema tPD_LMRiskAccPaySchema = tPD_LMRiskAccPaySet
					.get(nIndex + 1);
			if (StrTool.cTrim(tPD_LMRiskAccPaySchema.getInsuAccNo()).equals(
					strInsuAccNo)) {
				return tPD_LMRiskAccPaySchema;
			}
		}

		return null;
	}

	public PD_LMRiskAccPaySchema findRiskAccPayClone(String strPayPlanCode,
			String strInsuAccNo) {
		PD_LMRiskAccPaySchema tPD_LMRiskAccPaySchema = findRiskAccPay(strPayPlanCode,
				strInsuAccNo);

		if (tPD_LMRiskAccPaySchema == null) {
			return tPD_LMRiskAccPaySchema;
		} else {
			return tPD_LMRiskAccPaySchema.getSchema();
		}
	}

	/**
	 * 根据险种编码查找PD_LMRiskToAcc
	 * 
	 * @param strRiskCode
	 *            String
	 * @return PD_LMRiskToAccSet
	 */
	public PD_LMRiskToAccSet findRiskToAccByRiskCode(String strRiskCode) {

		Hashtable hash = m_hashPD_LMRiskToAcc;

		Object obj = hash.get(strRiskCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMRiskToAccSet) obj;
			}
		} else {
			PD_LMRiskToAccDB tPD_LMRiskToAccDB = new PD_LMRiskToAccDB();

			tPD_LMRiskToAccDB.setRiskCode(strRiskCode);

			PD_LMRiskToAccSet tPD_LMRiskToAccSet = tPD_LMRiskToAccDB.query();

			if (tPD_LMRiskToAccDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tPD_LMRiskToAccDB.mErrors);
				hash.put(strRiskCode, NOFOUND);
				return null;
			}

			hash.put(strRiskCode, tPD_LMRiskToAccSet);
			return tPD_LMRiskToAccSet;
		}
	}

	public PD_LMRiskToAccSet findRiskToAccByRiskCodeClone(String strRiskCode) {
		PD_LMRiskToAccSet tPD_LMRiskToAccSet = findRiskToAccByRiskCode(strRiskCode);

		if (tPD_LMRiskToAccSet == null) {
			return tPD_LMRiskToAccSet;
		} else {
			PD_LMRiskToAccSet newPD_LMRiskToAccSet = new PD_LMRiskToAccSet();
			for (int nIndex = 0; nIndex < tPD_LMRiskToAccSet.size(); nIndex++) {
				newPD_LMRiskToAccSet.add(tPD_LMRiskToAccSet.get(nIndex + 1));
			}
			return newPD_LMRiskToAccSet;
		}
	}

	public PD_LMRiskToAccSchema findRiskToAcc(String strRiskCode,
			String strInsuAccNo) {
		PD_LMRiskToAccSet tPD_LMRiskToAccSet = findRiskToAccByRiskCode(strRiskCode);

		if (tPD_LMRiskToAccSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tPD_LMRiskToAccSet.size(); nIndex++) {
			PD_LMRiskToAccSchema tPD_LMRiskToAccSchema = tPD_LMRiskToAccSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tPD_LMRiskToAccSchema.getInsuAccNo()).equals(
					strInsuAccNo)) {
				return tPD_LMRiskToAccSchema;
			}
		}

		return null;
	}

	public PD_LMRiskToAccSchema findRiskToAccClone(String strRiskCode,
			String strInsuAccNo) {
		PD_LMRiskToAccSchema tPD_LMRiskToAccSchema = findRiskToAcc(strRiskCode,
				strInsuAccNo);

		if (tPD_LMRiskToAccSchema == null) {
			return tPD_LMRiskToAccSchema;
		} else {
			return tPD_LMRiskToAccSchema.getSchema();
		}
	}

	/**
	 * 根据险种编码和控制字段名称查找PD_LMCheckField
	 * 
	 * @param strRiskCode
	 *            String
	 * @param strFieldName
	 *            String
	 * @return PD_LMCheckFieldSet
	 */
	public PD_LMCheckFieldSet findCheckFieldByRiskCode(String strRiskCode,
			String strFieldName) {

		Hashtable hash = m_hashPD_LMCheckField;

		String strPK = strRiskCode.trim() + strFieldName.trim();
		Object obj = hash.get(strPK);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMCheckFieldSet) obj;
			}
		} else {
			PD_LMCheckFieldDB tPD_LMCheckFieldDB = new PD_LMCheckFieldDB();

			tPD_LMCheckFieldDB.setRiskCode(strRiskCode);
			tPD_LMCheckFieldDB.setFieldName(strFieldName);

			PD_LMCheckFieldSet tPD_LMCheckFieldSet = tPD_LMCheckFieldDB.query();

			if (tPD_LMCheckFieldDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tPD_LMCheckFieldDB.mErrors);
				hash.put(strPK, NOFOUND);
				return null;
			}

			hash.put(strPK, tPD_LMCheckFieldSet);
			return tPD_LMCheckFieldSet;
		}
	}

	public PD_LMCheckFieldSet findCheckFieldByRiskCodeClone(String strRiskCode,
			String strFieldName) {
		PD_LMCheckFieldSet tPD_LMCheckFieldSet = findCheckFieldByRiskCode(
				strRiskCode, strFieldName);

		if (tPD_LMCheckFieldSet == null) {
			return tPD_LMCheckFieldSet;
		} else {
			PD_LMCheckFieldSet newPD_LMCheckFieldSet = new PD_LMCheckFieldSet();
			for (int nIndex = 0; nIndex < tPD_LMCheckFieldSet.size(); nIndex++) {
				newPD_LMCheckFieldSet.add(tPD_LMCheckFieldSet.get(nIndex + 1));
			}
			return newPD_LMCheckFieldSet;
		}
	}

	public PD_LMCheckFieldSchema findCheckField(String strRiskCode,
			String strFieldName, String strSerialNo) {
		PD_LMCheckFieldSet tPD_LMCheckFieldSet = findCheckFieldByRiskCode(
				strRiskCode, strFieldName);

		if (tPD_LMCheckFieldSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tPD_LMCheckFieldSet.size(); nIndex++) {
			PD_LMCheckFieldSchema tPD_LMCheckFieldSchema = tPD_LMCheckFieldSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tPD_LMCheckFieldSchema.getSerialNo()).equals(
					strSerialNo)) {
				return tPD_LMCheckFieldSchema;
			}
		}

		return null;
	}

	public PD_LMCheckFieldSchema findCheckFieldClone(String strRiskCode,
			String strFieldName, String strSerialNo) {
		PD_LMCheckFieldSchema tPD_LMCheckFieldSchema = findCheckField(strRiskCode,
				strFieldName, strSerialNo);

		if (tPD_LMCheckFieldSchema == null) {
			return tPD_LMCheckFieldSchema;
		} else {
			return tPD_LMCheckFieldSchema.getSchema();
		}
	}

	/**
	 * 根据责任编码查找PD_LMDutyCtrl
	 * 
	 * @param strDutyCode
	 *            String
	 * @return PD_LMDutyCtrlSet
	 */
	public PD_LMDutyCtrlSet findDutyCtrlByDutyCode(String strDutyCode) {

		Hashtable hash = m_hashPD_LMDutyCtrl;

		Object obj = hash.get(strDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMDutyCtrlSet) obj;
			}
		} else {
			PD_LMDutyCtrlDB tPD_LMDutyCtrlDB = new PD_LMDutyCtrlDB();

			tPD_LMDutyCtrlDB.setDutyCode(strDutyCode);

			PD_LMDutyCtrlSet tPD_LMDutyCtrlSet = tPD_LMDutyCtrlDB.query();

			if (tPD_LMDutyCtrlDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tPD_LMDutyCtrlDB.mErrors);
				hash.put(strDutyCode, NOFOUND);
				return null;
			}

			hash.put(strDutyCode, tPD_LMDutyCtrlSet);
			return tPD_LMDutyCtrlSet;
		}
	}

	public PD_LMDutyCtrlSet findDutyCtrlByDutyCodeClone(String strDutyCode) {
		PD_LMDutyCtrlSet tPD_LMDutyCtrlSet = findDutyCtrlByDutyCode(strDutyCode);

		if (tPD_LMDutyCtrlSet == null) {
			return tPD_LMDutyCtrlSet;
		} else {
			PD_LMDutyCtrlSet newPD_LMDutyCtrlSet = new PD_LMDutyCtrlSet();
			for (int nIndex = 0; nIndex < tPD_LMDutyCtrlSet.size(); nIndex++) {
				newPD_LMDutyCtrlSet.add(tPD_LMDutyCtrlSet.get(nIndex + 1));
			}
			return newPD_LMDutyCtrlSet;
		}
	}

	public PD_LMDutyCtrlSchema findDutyCtrl(String strDutyCode,
			String strOtherCode, String strFieldName) {
		PD_LMDutyCtrlSet tPD_LMDutyCtrlSet = findDutyCtrlByDutyCode(strDutyCode);

		if (tPD_LMDutyCtrlSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tPD_LMDutyCtrlSet.size(); nIndex++) {
			PD_LMDutyCtrlSchema tPD_LMDutyCtrlSchema = tPD_LMDutyCtrlSet.get(nIndex + 1);
			if (StrTool.cTrim(tPD_LMDutyCtrlSchema.getOtherCode()).equals(
					strOtherCode)
					&& StrTool.cTrim(tPD_LMDutyCtrlSchema.getFieldName()).equals(
							strFieldName)) {
				return tPD_LMDutyCtrlSchema;
			}
		}

		return null;
	}

	public PD_LMDutyCtrlSchema findDutyCtrlClone(String strDutyCode,
			String strOtherCode, String strFieldName) {
		PD_LMDutyCtrlSchema tPD_LMDutyCtrlSchema = findDutyCtrl(strDutyCode,
				strOtherCode, strFieldName);

		if (tPD_LMDutyCtrlSchema == null) {
			return tPD_LMDutyCtrlSchema;
		} else {
			return tPD_LMDutyCtrlSchema.getSchema();
		}
	}

	/**
	 * 根据给付责任编码查找PD_LMDutyGetAlive
	 * 
	 * @param strGetDutyCode
	 *            String
	 * @return PD_LMDutyGetAliveSet
	 */
	public PD_LMDutyGetAliveSet findDutyGetAliveByGetDutyCode(String strGetDutyCode) {

		Hashtable hash = m_hashPD_LMDutyGetAlive;

		Object obj = hash.get(strGetDutyCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMDutyGetAliveSet) obj;
			}
		} else {
			PD_LMDutyGetAliveDB tPD_LMDutyGetAliveDB = new PD_LMDutyGetAliveDB();

			tPD_LMDutyGetAliveDB.setGetDutyCode(strGetDutyCode);

			PD_LMDutyGetAliveSet tPD_LMDutyGetAliveSet = tPD_LMDutyGetAliveDB.query();

			if (tPD_LMDutyGetAliveDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tPD_LMDutyGetAliveDB.mErrors);
				hash.put(strGetDutyCode, NOFOUND);
				return null;
			}

			hash.put(strGetDutyCode, tPD_LMDutyGetAliveSet);
			return tPD_LMDutyGetAliveSet;
		}
	}

	public PD_LMDutyGetAliveSet findDutyGetAliveByGetDutyCodeClone(
			String strGetDutyCode) {
		PD_LMDutyGetAliveSet tPD_LMDutyGetAliveSet = findDutyGetAliveByGetDutyCode(strGetDutyCode);

		if (tPD_LMDutyGetAliveSet == null) {
			return tPD_LMDutyGetAliveSet;
		} else {
			PD_LMDutyGetAliveSet newPD_LMDutyGetAliveSet = new PD_LMDutyGetAliveSet();
			for (int nIndex = 0; nIndex < tPD_LMDutyGetAliveSet.size(); nIndex++) {
				newPD_LMDutyGetAliveSet.add(tPD_LMDutyGetAliveSet.get(nIndex + 1));
			}
			return newPD_LMDutyGetAliveSet;
		}
	}

	public PD_LMDutyGetAliveSchema findDutyGetAlive(String strGetDutyCode,
			String strGetDutyKind) {
		PD_LMDutyGetAliveSet tPD_LMDutyGetAliveSet = findDutyGetAliveByGetDutyCode(strGetDutyCode);

		if (tPD_LMDutyGetAliveSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tPD_LMDutyGetAliveSet.size(); nIndex++) {
			PD_LMDutyGetAliveSchema tPD_LMDutyGetAliveSchema = tPD_LMDutyGetAliveSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tPD_LMDutyGetAliveSchema.getGetDutyKind()).equals(
					strGetDutyKind)) {
				return tPD_LMDutyGetAliveSchema;
			}
		}

		return null;
	}

	public PD_LMDutyGetAliveSchema findDutyGetAliveClone(String strGetDutyCode,
			String strGetDutyKind) {
		PD_LMDutyGetAliveSchema tPD_LMDutyGetAliveSchema = findDutyGetAlive(
				strGetDutyCode, strGetDutyKind);

		if (tPD_LMDutyGetAliveSchema == null) {
			return tPD_LMDutyGetAliveSchema;
		} else {
			return tPD_LMDutyGetAliveSchema.getSchema();
		}
	}

	/**
	 * 根据算法编码查找PD_LMCalFactor
	 * 
	 * @param strCalCode
	 *            String
	 * @return PD_LMCalFactorSet
	 */
	public PD_LMCalFactorSet findCalFactorByCalCode(String strCalCode) {

		Hashtable hash = m_hashPD_LMCalFactor;

		Object obj = hash.get(strCalCode);

		if (obj != null) {
			if (obj instanceof String) {
				return null;
			} else {
				return (PD_LMCalFactorSet) obj;
			}
		} else {
			PD_LMCalFactorDB tPD_LMCalFactorDB = new PD_LMCalFactorDB();

			tPD_LMCalFactorDB.setCalCode(strCalCode);

			PD_LMCalFactorSet tPD_LMCalFactorSet = tPD_LMCalFactorDB.query();

			if (tPD_LMCalFactorDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tPD_LMCalFactorDB.mErrors);
				hash.put(strCalCode, NOFOUND);
				return null;
			}

			hash.put(strCalCode, tPD_LMCalFactorSet);
			return tPD_LMCalFactorSet;
		}
	}

	public PD_LMCalFactorSet findCalFactorByCalCodeClone(String strCalCode) {
		PD_LMCalFactorSet tPD_LMCalFactorSet = findCalFactorByCalCode(strCalCode);

		if (tPD_LMCalFactorSet == null) {
			return tPD_LMCalFactorSet;
		} else {
			PD_LMCalFactorSet newPD_LMCalFactorSet = new PD_LMCalFactorSet();
			for (int nIndex = 0; nIndex < tPD_LMCalFactorSet.size(); nIndex++) {
				newPD_LMCalFactorSet.add(tPD_LMCalFactorSet.get(nIndex + 1));
			}
			return newPD_LMCalFactorSet;
		}
	}

	public PD_LMCalFactorSchema findCalFactor(String strCalCode,
			String strFactorCode) {
		PD_LMCalFactorSet tPD_LMCalFactorSet = findCalFactorByCalCode(strCalCode);

		if (tPD_LMCalFactorSet == null) {
			return null;
		}

		for (int nIndex = 0; nIndex < tPD_LMCalFactorSet.size(); nIndex++) {
			PD_LMCalFactorSchema tPD_LMCalFactorSchema = tPD_LMCalFactorSet
					.get(nIndex + 1);
			if (StrTool.cTrim(tPD_LMCalFactorSchema.getFactorCode()).equals(
					strFactorCode)) {
				return tPD_LMCalFactorSchema;
			}
		}

		return null;
	}

	public PD_LMCalFactorSchema findCalFactorClone(String strCalCode,
			String strFactorCode) {
		PD_LMCalFactorSchema tPD_LMCalFactorSchema = findCalFactor(strCalCode,
				strFactorCode);

		if (tPD_LMCalFactorSchema == null) {
			return tPD_LMCalFactorSchema;
		} else {
			return tPD_LMCalFactorSchema.getSchema();
		}
	}
}
