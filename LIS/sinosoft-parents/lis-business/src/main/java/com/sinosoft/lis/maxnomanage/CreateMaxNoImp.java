package com.sinosoft.lis.maxnomanage;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.CachedLDMaxNo;

import com.sinosoft.lis.schema.LDMaxNoRuleSchema;
import com.sinosoft.utility.VData;

public class CreateMaxNoImp implements CreateMaxNo {
	private static Logger logger = Logger.getLogger(CreateMaxNo.class);
	private CachedLDMaxNo mCached = CachedLDMaxNo.getInstance();

	private String ManageCom = "";

	private String getMaxNo(MaxNoElement maxNoElement) {
		return maxNoElement.CreateMaxNo();
	}

	private String getPreviewMaxNo(MaxNoElement maxNoElement) {
		return maxNoElement.CreatePrviewMaxNo();
	}

	private String getMaxNo(String cNoType, Hashtable tOthers) {
		cNoType = cNoType.toUpperCase();

		VData tMaxNoVData = this.mCached.findLDMaxNoRuleConfig(cNoType);
		if (tMaxNoVData == null) {
			return null;
		}

		// 先获取规则约束
		String cNoLimit;

		// 再生成号码
		String tFinalNo = "";
		tFinalNo = creatNo(cNoType, tMaxNoVData, tOthers, "0");

		return tFinalNo;
	}

	private String creatNo(String cNoType, VData tMaxNoVData,
			Hashtable tOthers, String tType) {
		String tFinalNo = "";
		MaxNoElement tMaxNoElement = null;
		for (int i = 0; i < tMaxNoVData.size(); i++) {
			String tTempNo = "";
			VData tempMaxNoData = (VData) tMaxNoVData.get(i);

			LDMaxNoRuleSchema tempLDMaxNoRuleSchema = (LDMaxNoRuleSchema) tempMaxNoData
					.getObjectByObjectName("LDMaxNoRuleSchema", 0);
			// LDMaxNoRulePropSet tempLDMaxNoRulePropSet =
			// (LDMaxNoRulePropSet)tempMaxNoData.getObjectByObjectName("LDMaxNoRulePropSet",
			// 0);
			Hashtable propHashtable = new Hashtable();
			propHashtable = (Hashtable) tempMaxNoData.getObjectByObjectName(
					"Hashtable", 0);

			String tCode = tempLDMaxNoRuleSchema.getCode();
			logger.debug("tCode:" + tCode);

			if (tCode.equals("SerialNo")) {
				tMaxNoElement = new SerialNo(cNoType, propHashtable, tOthers);
			} else if (tCode.equals("StringNo")) {
				tMaxNoElement = new StringNo(cNoType, propHashtable, tOthers);
			} else if (tCode.equals("ComNo")) {
				tMaxNoElement = new ComNo(cNoType, propHashtable, tOthers);
			} else if (tCode.equals("YearNo")) {
				tMaxNoElement = new YearNo(cNoType, propHashtable, tOthers);
			}

			if (tType.equals("0")) {
				tTempNo = this.getMaxNo(tMaxNoElement);
			} else {
				tTempNo = this.getPreviewMaxNo(tMaxNoElement);
			}
			tFinalNo = tFinalNo + tTempNo;

		}
		return tFinalNo;
	}

	public void setManageCom(String tManageCom) {
		// TODO Auto-generated method stub
		this.ManageCom = tManageCom;
	}

	public String getMaxNo(String cNoType) {
		// TODO Auto-generated method stub
		String tManageCom = this.ManageCom;
		Hashtable tOthers = new Hashtable();
		tOthers.put("ManageCom", tManageCom);
		return this.getMaxNo(cNoType, tOthers);
	}

	private String getPreviewMaxNo(String cNoType, Hashtable tOthers) {
		cNoType = cNoType.toUpperCase();

		VData tMaxNoVData = this.mCached.findLDMaxNoRuleConfig(cNoType);
		if (tMaxNoVData == null) {
			return null;
		}

		// 先获取规则约束
		String cNoLimit;

		// 再生成号码
		String tFinalNo = "";
		tFinalNo = creatNo(cNoType, tMaxNoVData, tOthers, "1");

		return tFinalNo;
	}

	public String getPreviewMaxNo(String cNoType) {
		// TODO Auto-generated method stub
		// 设置默认值
		String tManageCom = "86110000";
		Hashtable tOthers = new Hashtable();
		tOthers.put("ManageCom", tManageCom);
		//预览需要刷新缓存
		this.mCached.refresh(cNoType);
		return this.getPreviewMaxNo(cNoType, tOthers);
	}
}
