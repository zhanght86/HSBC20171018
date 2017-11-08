package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: FinDayTool：通过哈希表进行key-value的查询  </p>
 * <p>Description:  key的按照行和列的划分然后加成key     </p>
 * <p>注意FinCheckKey这个类应该看成KEY和VALUE的共同类    </p>
 * <p>update at 2003-8-26  把value按照key的形式同样处理 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:Sinosoft                     </p>
 * @author hezy
 * @version 1.0
 *
 *
 */
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class FinDayTool {
private static Logger logger = Logger.getLogger(FinDayTool.class);
	private Dictionary dict = new Hashtable();

	public FinDayTool() {
	}

	public void enterBasicInfo(FinCheckKey key, String money) {
		Vector set = (Vector) dict.get(key);
		if (set == null) {
			set = new Vector();
			set.addElement(money);
			dict.put(key, set);
		} else {
			set.addElement(money);
		}
	}

	public void enterBasicInfo(FinCheckKey key, FinCheckKey value) {
		Vector set = (Vector) dict.get(key);
		if (set == null) {
			set = new Vector();
			set.addElement(value);
			dict.put(key, set);
		} else {
			set.addElement(value);
		}
	}

	/***************************************************************************
	 * add by guoxiang ************ 返回类型为：double,但是可以存取比的类型如：int 可以在调用本方法的时候进行转型
	 * 参数i 是你指定的计算列数
	 */
	public double getTotalValue(FinCheckKey key, int i) {
		double mSum = 0;
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			FinCheckKey tFinCheckKey = (FinCheckKey) e.nextElement();
			String tRow1 = tFinCheckKey.getRow()[0];
			String ttRow1 = key.getRow()[0];
			if (tRow1.equals(ttRow1)) {
				Vector set = (Vector) dict.get(tFinCheckKey);
				Enumeration f = set.elements();
				while (f.hasMoreElements()) {
					FinCheckKey mFinCheckKey = (FinCheckKey) f.nextElement();
					String mRowl = mFinCheckKey.getRow()[i];
					String mm = mRowl;
					if ("".equals(mm)) {
						logger.debug(mRowl + "为空");
						mSum = 0.0;
					} else {
						mSum = mSum + Double.parseDouble(mm);
					}
				}
			}
		}
		return mSum;
	}

	public String getTotalValue(FinCheckKey key) {
		double tSumMoney = 0;
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			FinCheckKey tFinCheckKey = (FinCheckKey) e.nextElement();
			String tRow1 = tFinCheckKey.getRow()[0];
			String ttRow1 = key.getRow()[0];
			if (tRow1.equals(ttRow1)) {
				Vector set = (Vector) dict.get(tFinCheckKey);
				Enumeration f = set.elements();
				while (f.hasMoreElements()) {
					String tmoney = (String) f.nextElement();
					tSumMoney = tSumMoney + Double.parseDouble(tmoney);
				}
			}
		}
		return String.valueOf(tSumMoney);
	}

	public String getUniqueValue(FinCheckKey key, int i) {
		String mValue = "";
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			FinCheckKey tFinCheckKey = (FinCheckKey) e.nextElement();
			if (tFinCheckKey.equals(key)) {
				Vector set = (Vector) dict.get(key);
				Enumeration f = set.elements();
				while (f.hasMoreElements()) {
					FinCheckKey mFinCheckKey = (FinCheckKey) f.nextElement();
					mValue = mFinCheckKey.getRow()[i];

				}

			}
		}
		return mValue;
	}

	public String getUniqueValue(FinCheckKey key) {
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			FinCheckKey tFinCheckKey = (FinCheckKey) e.nextElement();
			if (tFinCheckKey.equals(key)) {
				Vector set = (Vector) dict.get(key);
				Enumeration f = set.elements();
				while (f.hasMoreElements()) {
					String tmoney = (String) f.nextElement();
					return tmoney;
				}
			}
		}
		return "";
	}

	/***************************************************************************
	 * add by guoxiang ************ 返回类型为：double,但是可以存取比的类型如：int 可以在调用本方法的时候进行转型
	 * 参数i 是你指定的计算列数
	 */
	public double getCheckValue(FinCheckKey key, int i) {
		double tSum = 0;
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			FinCheckKey tFinCheckKey = (FinCheckKey) e.nextElement();
			if (tFinCheckKey.equals(key)) {
				Vector set = (Vector) dict.get(key);
				Enumeration f = set.elements();
				while (f.hasMoreElements()) {
					FinCheckKey mFinCheckKey = (FinCheckKey) f.nextElement();
					String mSum = mFinCheckKey.getRow()[i];
					tSum = tSum + Double.parseDouble(mSum);

				}
			}
		}
		return tSum;
	}

	public String getCheckValue(FinCheckKey key) {
		double tSumMoney = 0;
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			FinCheckKey tFinCheckKey = (FinCheckKey) e.nextElement();
			if (tFinCheckKey.equals(key)) {
				Vector set = (Vector) dict.get(key);
				Enumeration f = set.elements();
				while (f.hasMoreElements()) {
					String tmoney = (String) f.nextElement();
					tSumMoney = tSumMoney + Double.parseDouble(tmoney);
				}
			}
		}
		return String.valueOf(tSumMoney);
	}

	public Vector getAllRiskCode(String tRiskType) {
		Vector tRiskCode = new Vector();
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			FinCheckKey tFinCheckKey = (FinCheckKey) e.nextElement();
			if (tFinCheckKey.getRow()[0].equals(tRiskType)) {
				String RiskCode = tFinCheckKey.getRow()[1];
				if (!tRiskCode.contains(RiskCode)) {
					tRiskCode.add(RiskCode);
				}
			}
		}
		return tRiskCode;
	}

	public Vector getAllRiskCode() {
		Vector tRiskCode = new Vector();
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			FinCheckKey tFinCheckKey = (FinCheckKey) e.nextElement();
			String RiskCode = tFinCheckKey.getRow()[0];
			if (!tRiskCode.contains(RiskCode)) {
				tRiskCode.add(RiskCode);
			}
		}
		return tRiskCode;
	}

	public Vector getAllKey(int i) {
		Vector tCode = new Vector();
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			FinCheckKey tFinCheckKey = (FinCheckKey) e.nextElement();
			String Code = tFinCheckKey.getRow()[i];
			if (!tCode.contains(Code)) {
				tCode.add(Code);
			}
		}
		return tCode;
	}

	public Vector getAllRowKey(int i) {
		Vector tCode = new Vector();
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			FinCheckKey tFinCheckKey = (FinCheckKey) e.nextElement();
			String Code = tFinCheckKey.getRow()[i];
			if (!tCode.contains(Code)) {
				tCode.add(Code);
			}
		}
		return tCode;
	}

	public Vector getAllColumnKey(int i) {
		Vector tCode = new Vector();
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			FinCheckKey tFinCheckKey = (FinCheckKey) e.nextElement();
			String Code = tFinCheckKey.getColumn()[i];
			if (!tCode.contains(Code)) {
				tCode.add(Code);
			}
		}
		return tCode;
	}

	public static void main(String[] args) {

		logger.debug("SUCCECT");

	}
}
