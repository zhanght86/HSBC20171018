package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.Vector;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Fuqx
 * @version 1.0
 */

public class BPODataContainer {
private static Logger logger = Logger.getLogger(BPODataContainer.class);
	private Vector BussNoVData = new Vector(); // 存放印刷号
	private Vector schemaVData = new Vector(); // 存放XML转换后的schema,set
	private Vector elementVData = new Vector(); // 存放XML
	private Vector RiskNoVData = new Vector(); // 存放主被保人主险号

	public BPODataContainer() {
	}

	/**
	 * 保存每一个业务号码BussNo对应的VData和XML
	 * 
	 * @param BussNo
	 * @param schemaData
	 * @param elementData
	 * @param RiskNoVData
	 */
	public void add(Object BussNo, Object schemaData, Object elementData,
			Object RiskNo) {
		BussNoVData.add(BussNo);
		schemaVData.add(schemaData);
		elementVData.add(elementData);
		RiskNoVData.add(RiskNo);
	}

	/**
	 * 得到业务号码集合
	 * 
	 * @return
	 */
	public Vector getBussNoData() {
		return BussNoVData;
	}

	/**
	 * 根据业务号（印刷号）取得其对应的VData数据
	 * 
	 * @param tBussNo
	 * @return
	 */
	public Object getschemaData(String tBussNo) {
		for (int i = 0; i < BussNoVData.size(); i++) {
			if (BussNoVData.elementAt(i).equals(tBussNo)) {
				return schemaVData.get(i);
			}
		}
		return null;
	}

	/**
	 * 根据业务号（印刷号）取得其对应的XML数据
	 * 
	 * @param tBussNo
	 * @return
	 */
	public Object getelementData(String tBussNo) {
		for (int i = 0; i < BussNoVData.size(); i++) {
			if (BussNoVData.elementAt(i).equals(tBussNo)) {
				return elementVData.get(i);
			}
		}
		return null;
	}

	/**
	 * 得到主险号集合
	 * 
	 * @return
	 */
	public Vector getRiskNoData() {
		return RiskNoVData;
	}

	/**
	 * 删除一个业务号码对应的VData数据以及XML数据
	 * 
	 * @param name
	 * @return
	 */
	public boolean removeByName(String tBussNo) {
		for (int i = 0; i < BussNoVData.size(); i++) {
			if (BussNoVData.elementAt(i).equals(tBussNo)) {
				BussNoVData.remove(i);
				schemaVData.remove(i);
				elementVData.remove(i);
				RiskNoVData.remove(i);
			}
		}
		return true;
	}

	public static void main(String[] args) {
		BPODataContainer BPODataContainer1 = new BPODataContainer();
	}
}
