

package com.sinosoft.productdef;

import com.sinosoft.lis.db.PD_LBRiskInfoDB;
import com.sinosoft.lis.schema.PD_LBRiskInfoSchema;
import com.sinosoft.lis.vschema.PD_LBRiskInfoSet;
import com.sinosoft.utility.*;

import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;

public class PDRiskDefReportListPrtBL {

	/** 错误的容器 */
	public CErrors mErrors = new CErrors();

	private TransferData mTransferData = null;

	private XmlExport xmlexport = null;

	private VData mResult = null;

	ListTable mtListTable = new ListTable();

	public PDRiskDefReportListPrtBL() {
	}

	/**
	 * 
	 * @param args
	 *            VData 需包含LGEdorApp(需受理号EdorAcceptNO即可)、GlobalIiput
	 * @param args
	 *            String 操作方式
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		if (!getInputData(cInputData)) {
			return false;
		}

		// 获取打印所需数据
		if (!dealData(cOperate)) {
			return false;
		}

		return true;
	}

	/**
	 * getInputData
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		try {
			mTransferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);
		} catch (Exception e) {
			mErrors.addOneError("传入的数据不完整。");
			System.out.println("传入的数据不完整，" + e.toString());
			return false;
		}
		return true;
	}

	/**
	 * 处理不同的清单报表
	 * 
	 * @param tOperate
	 * @return
	 */
	private boolean dealData(String tOperate) {

		// 上线产品分类统计
		if ("OLRiskList".equals(tOperate)) {
			return getOLRiskListPrtData();
		}
		// 产品测试问题件统计
		if ("ProblemList".equals(tOperate)) {
			return getRiskTestProblemListData();
		}
		// 产品修改轨迹清单
		if ("ModifyQuery".equals(tOperate)) {
			return getRiskModifyData();
		}
		// 产品状态统计清单
		if ("RiskStateList".equals(tOperate)) {
			return getRiskStateListData();
		}
		// 产品定义时效统计
		if ("RiskDefineEff".equals(tOperate)) {
			return getRiskDefineEffData();
		}
		return true;
	}

	/**
	 * 打印上线产品分类统计所需数据
	 * 
	 * @return
	 */
	private boolean getOLRiskListPrtData() {

		TextTag tag = new TextTag();
		xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("PDOLRiskList.vts", "printer"); // 最好紧接着就初始化xml文档

		String tStartDate = (String) mTransferData.getValueByName("StartDate");
		String tEndDate = (String) mTransferData.getValueByName("EndDate");
		tag.add("PrintStartDate", tStartDate);
		tag.add("PrintEndDate", tEndDate);

		// 【都邦】产品定义查询SQL
		StringBuffer sql = new StringBuffer();
		sql
				.append("SELECT substr(b.Missionprop4,0,4) deplyYear,a.Riskprop,a.Riskperiod,a.Risktype,a.Risktype3 FROM PD_LMRiskApp a,LWMission b ");
		sql
				.append("WHERE a.Riskcode = b.Missionprop2 and b.Processid = 'pd00000011' and b.Activityid = 'pd00000004' and b.Missionprop4 is not null ");
		if (tStartDate != null && !tStartDate.equals("")) {
			sql.append(" and TO_DATE(b.Missionprop4,'YYYY-MM-DD') >= DATE'"
					+ tStartDate + "' ");
		}
		if (tEndDate != null && !tEndDate.equals("")) {
			sql.append(" and TO_DATE(b.Missionprop4,'YYYY-MM-DD') <= DATE'"
					+ tEndDate + "' ");
		}
		sql.append(" ORDER BY deplyYear");
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sql.toString());

		String tCellecionYear = "";

		int yearDataArr[] = new int[12];
		int totalDataArr[] = new int[12];// 汇总

		HashMap map = new HashMap();

		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				tCellecionYear = tSSRS.GetText(i, 1);// 统计年度
				if (i == 1) {
					map.put(tCellecionYear, "");
					yearDataArr = collectionData(tSSRS, i, yearDataArr,
							totalDataArr);
				} else {
					if (map.containsKey(tCellecionYear)) {
						yearDataArr = collectionData(tSSRS, i, yearDataArr,
								totalDataArr);
						continue;
					} else {
						mtListTable.add(transferData(yearDataArr));
						map.put(tCellecionYear, "");
						yearDataArr = new int[12];
						yearDataArr = collectionData(tSSRS, i, yearDataArr,
								totalDataArr);
					}
				}
			}
			mtListTable.add(transferData(yearDataArr));
		}

		tag.add("TotalNum2", "" + totalDataArr[1]);
		tag.add("TotalNum3", "" + totalDataArr[2]);
		tag.add("TotalNum4", "" + totalDataArr[3]);
		tag.add("TotalNum5", "" + totalDataArr[4]);
		tag.add("TotalNum6", "" + totalDataArr[5]);
		tag.add("TotalNum7", "" + totalDataArr[6]);
		tag.add("TotalNum8", "" + totalDataArr[7]);
		tag.add("TotalNum9", "" + totalDataArr[8]);
		tag.add("TotalNum10", "" + totalDataArr[9]);
		tag.add("TotalNum11", "" + totalDataArr[10]);
		tag.add("TotalNum12", "" + totalDataArr[11]);

		xmlexport.addTextTag(tag);

		mtListTable.setName("LIST");
		if (mtListTable.size() == 0) {
			return false;
		}

		String[] title = new String[12];
		title[0] = "Year";
		title[1] = "SRisk";
		title[2] = "GRisk";
		title[3] = "ORisk";
		title[4] = "ShortRisk";
		title[5] = "LongRisk";
		title[6] = "AccRisk";
		title[7] = "HealthRisk";
		title[8] = "LifeRisk";
		title[9] = "BonusRisk";
		title[10] = "TLRisk";
		title[11] = "WNRisk";

		xmlexport.addListTable(mtListTable, title);

		mResult = new VData();
		mResult.addElement(xmlexport);

		return true;
	}

	/**
	 * getOLRiskListPrtData()子方法： 汇总各列数据
	 * 
	 * @param tSSRS
	 * @param i
	 * @param arr
	 * @return
	 */
	private int[] collectionData(SSRS tSSRS, int i, int[] arr, int[] total) {

		String tCellecionYear = tSSRS.GetText(i, 1);
		String tRiskProp = tSSRS.GetText(i, 2);
		String tRiskPeriod = tSSRS.GetText(i, 3);
		String tRiskType = tSSRS.GetText(i, 4);
		String tRiskType3 = tSSRS.GetText(i, 5);
		// 统计年度
		arr[0] = Integer.parseInt(tCellecionYear);
		// 销售类型
		if (tRiskProp.equalsIgnoreCase("G")) {
			arr[2]++;
			total[2]++;
		} else if (tRiskProp.equalsIgnoreCase("G")) {
			arr[1]++;
			total[1]++;
		} else {
			arr[3]++;
			total[3]++;
		}
		// 缴费年期
		if (tRiskPeriod.equalsIgnoreCase("L")) {
			arr[5]++;
			total[5]++;
		} else {
			arr[4]++;
			total[4]++;
		}
		// 产品类型
		if (tRiskType.equalsIgnoreCase("A")) {
			arr[6]++;
			total[6]++;
		} else if (tRiskType.equalsIgnoreCase("H")) {
			arr[7]++;
			total[7]++;
		} else {
			if (tRiskType3.equals("1")) {
				arr[8]++;
				total[8]++;
			} else if (tRiskType3.equals("2")) {
				arr[9]++;
				total[9]++;
			} else if (tRiskType3.equals("3")) {
				arr[10]++;
				total[10]++;
			} else if (tRiskType3.equals("4")) {
				arr[11]++;
				total[11]++;
			}
		}
		return arr;
	}

	/**
	 * getOLRiskListPrtData()子方法：将汇总的各项数据封装转换
	 * 
	 * @param strArr
	 * @param arr
	 * @return
	 */
	private String[] transferData(int[] arr) {
		String[] strArr = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			strArr[i] = "" + arr[i];
		}
		return strArr;
	}

	/**
	 * 产品测试问题件统计
	 * 
	 * @return
	 */
	private boolean getRiskTestProblemListData() {

		xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("PDTestProblemList.vts", "printer"); // 最好紧接着就初始化xml文档

		// String tTestType = (String) mTransferData.getValueByName("TestType");
		String tStartDate = (String) mTransferData.getValueByName("StartDate");
		String tEndDate = (String) mTransferData.getValueByName("EndDate");

		int TotalNum2 = 0;
		int TotalNum3 = 0;

		String[] title = new String[3];
		title[0] = "TestType";
		title[1] = "RiskCode";
		title[2] = "IssueNum";

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT '新产品测试',riskcode,count(1) FROM PD_issue WHERE 1=1 ");
		if (tStartDate != null && !tStartDate.equals("")) {
			sql.append(" and makedate >= DATE'" + tStartDate + "' ");
		}
		if (tEndDate != null && !tEndDate.equals("")) {
			sql.append(" and makedate <= DATE'" + tEndDate + "' ");
		}
		sql.append("group by riskcode");

		String[] info = null;
		SSRS ssrs = new ExeSQL().execSQL(sql.toString());
		if (ssrs != null && ssrs.getMaxRow() > 0) {
			TotalNum2 = ssrs.getMaxRow();
			for (int i = 1; i <= ssrs.getMaxRow(); i++) {
				info = new String[3];
				info[0] = ssrs.GetText(i, 1);
				info[1] = ssrs.GetText(i, 2);
				info[2] = ssrs.GetText(i, 3);
				try {
					TotalNum3 += Integer.parseInt(info[2]);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				mtListTable.add(info);
			}
		}

		TextTag tag = new TextTag();
		tag.add("TotalNum2", TotalNum2);
		tag.add("TotalNum3", TotalNum3);
		xmlexport.addTextTag(tag);

		mtListTable.setName("LIST");
		if (mtListTable.size() == 0) {
			return false;
		}

		xmlexport.addListTable(mtListTable, title);

		mResult = new VData();
		mResult.addElement(xmlexport);

		return true;
	}

	/**
	 * 6、产品修改查询
	 * 
	 * @return
	 */
	private boolean getRiskModifyData() {

		xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("PDRiskModifyHistoryList.vts", "printer"); // 最好紧接着就初始化xml文档

		String tRiskCode = (String) mTransferData.getValueByName("RiskCode");
		String tStartDate = (String) mTransferData.getValueByName("StartDate");
		String tEndDate = (String) mTransferData.getValueByName("EndDate");

		if (tRiskCode == null || tRiskCode.equals("")) {
			return false;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM PD_LBriskInfo WHere riskcode = '" + tRiskCode
				+ "' and standbyflag50 = 'update' ");
		if (tStartDate != null && !tStartDate.equals("")) {
			sql.append(" and makedate >= DATE'" + tStartDate + "' ");
		}
		if (tEndDate != null && !tEndDate.equals("")) {
			sql.append(" and makedate <= DATE'" + tEndDate + "' ");
		}
		sql.append(" ORDER BY tablecode,serialno");

		PD_LBRiskInfoDB db = new PD_LBRiskInfoDB();
		PD_LBRiskInfoSet set = db.executeQuery(sql.toString());

		if (set != null && set.size() > 0) {
			// PD_LBRiskInfo行数据循环
			for (int i = 1; i <= set.size(); i++) {
				getModifiedColumn(set.get(i));
			}
		} else {
			mErrors.addOneError("该险种没有修改记录！");
			return false;
		}

		mtListTable.setName("LIST");
		if (mtListTable.size() == 0) {
			return false;
		}

		String[] title = new String[5];
		title[0] = "RiskCode";
		title[1] = "MakeDate";
		title[2] = "TabAndCol";
		title[3] = "OldValue";
		title[4] = "NewValue";

		xmlexport.addListTable(mtListTable, title);

		mResult = new VData();
		mResult.addElement(xmlexport);

		return true;
	}

	/**
	 * getRiskModifyData()子方法
	 * 
	 * @return
	 */
	private void getModifiedColumn(PD_LBRiskInfoSchema schema) {
		String[] info = null;
		String tTableCode = schema.getTableCode();
		// 必须按displayorder排序
		String tSQL = "select fieldcode,displayorder,Isprimary from Pd_Basefield where tablecode = upper('"
				+ tTableCode + "') and isdisplay = '1' order by displayorder";

		SSRS ssrs = new ExeSQL().execSQL(tSQL);
		if (ssrs != null && ssrs.MaxRow > 0) {

			// 查询现数据库中数据
			StringBuffer sql = new StringBuffer();
			StringBuffer tColumns = new StringBuffer();
			StringBuffer tWherePart = new StringBuffer();

			for (int i = 1; i <= ssrs.MaxRow; i++) {
				if (ssrs.GetText(i, 3) != null
						&& "1".equals(ssrs.GetText(i, 3))) {
					tWherePart.append(" and " + ssrs.GetText(i, 1) + "='"
							+ schema.getV("standbyflag" + ssrs.GetText(i, 2))
							+ "' ");
				} else {
					tColumns.append(ssrs.GetText(i, 1) + ",");
				}
			}
			sql.append(" select ");
			sql.append(tColumns);
			sql.append("''");
			sql.append(" from " + tTableCode);
			sql.append(" where 1=1 ");
			sql.append(tWherePart);

			SSRS tSSRS = new ExeSQL().execSQL(sql.toString());
			int selIndex = 1;

			for (int i = 1; i <= ssrs.MaxRow; i++) {
				if (ssrs.GetText(i, 3) != null
						&& "1".equals(ssrs.GetText(i, 3))) {
					// 主键不判断
				} else {
					info = new String[5];
					info[0] = schema.getRiskCode();
					info[1] = schema.getMakeDate();
					info[2] = tTableCode + "." + ssrs.GetText(i, 1);
					// 原值
					info[3] = StrTool.cTrim(schema.getV("standbyflag"
							+ ssrs.GetText(i, 2)));
					if (info[3] == null || info[3].equalsIgnoreCase("null")) {
						info[3] = "";
					} else if (info[3].equals("0.0")) {
						info[3] = "0";
					}
					// 现值
					if (tSSRS != null && tSSRS.MaxRow > 0
							&& selIndex <= tSSRS.MaxCol) {
						info[4] = StrTool.cTrim(tSSRS.GetText(1, selIndex));
						if (info[4] == null || info[4].equalsIgnoreCase("null")) {
							info[4] = "";
						} else if (info[4].equals("0.0")) {
							info[4] = "0";
						}// 数字实在不好处理！
						selIndex++;
					} else {
						info[4] = "";
					}
					System.out.println("险种：" + info[0] + ";判断字段：" + info[2]
							+ ";原值：[" + info[3] + "];现值：[" + info[4]
							+ "];原值现值相同标志：" + info[3].equals(info[4]));
					if (!info[3].equals(info[4])) {
						mtListTable.add(info);
					}
				}
			}
		}
	}

	/**
	 * 产品状态统计清单
	 * 
	 * @return
	 */
	private boolean getRiskStateListData() {

		xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("PDRiskStateList.vts", "printer"); // 最好紧接着就初始化xml文档

		String tRiskState = (String) mTransferData.getValueByName("RiskState");
		String tStartDate = (String) mTransferData.getValueByName("StartDate");
		String tEndDate = (String) mTransferData.getValueByName("EndDate");

		StringBuffer sql = new StringBuffer();
		sql
				.append("SELECT case a.Activityid when 'pd00000004' then '新上线' else '定义中' end,");
		sql
				.append("b.Riskcode,b.Riskname,b.Makedate FROM Lwmission a,PD_LMrisk b WHERE a.Missionprop2 = b.Riskcode");
		if (tStartDate != null && !tStartDate.equals("")) {
			sql.append(" and b.Makedate >= DATE'" + tStartDate + "' ");
		}
		if (tEndDate != null && !tEndDate.equals("")) {
			sql.append(" and b.Makedate <= DATE'" + tEndDate + "' ");
		}
		if (tRiskState != null && !tRiskState.equals("")) {
			if (tRiskState.equals("1")) {
				sql.append(" and a.Activityid = 'pd00000004' ");
			} else {
				sql.append(" and a.Activityid <> 'pd00000004' ");
			}
		}
		sql.append(" ORDER BY a.Activityid,b.Riskcode");

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sql.toString());

		String[] info = null;
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				info = new String[4];
				info[0] = tSSRS.GetText(i, 1);
				info[1] = tSSRS.GetText(i, 2);
				info[2] = tSSRS.GetText(i, 3);
				info[3] = tSSRS.GetText(i, 4);
				mtListTable.add(info);
			}
		}

		mtListTable.setName("LIST");
		if (mtListTable.size() == 0) {
			return false;
		}

		String[] title = new String[4];
		title[0] = "RiskState";
		title[1] = "RiskCode";
		title[2] = "RiskName";
		title[3] = "ApplyDate";

		xmlexport.addListTable(mtListTable, title);

		mResult = new VData();
		mResult.addElement(xmlexport);

		return true;
	}

	/**
	 * 产品定义时效统计
	 * 
	 * @return
	 */
	private boolean getRiskDefineEffData() {

		xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("PDRiskDefineEff.vts", "printer"); // 最好紧接着就初始化xml文档

		String tRiskCode = (String) mTransferData.getValueByName("RiskCode");
		String tStartDate = (String) mTransferData.getValueByName("StartDate");
		String tEndDate = (String) mTransferData.getValueByName("EndDate");

		// 查询已上线产品
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select missionprop2, b.Riskname, missionid,TO_CHAR(a.Makedate,'YYYY-MM-DD')||' '||a.Maketime from lwmission a ");
		sql
				.append(" left outer join pd_lmrisk b on a.Missionprop2 = b.Riskcode ");
		sql
				.append(" where a.activityid = 'pd00000004' and a.processid = 'pd00000011' ");

		if (tRiskCode != null && !tRiskCode.equals("")) {
			sql.append(" and a.missionprop2 = '" + tRiskCode + "' ");
		}
		if (tStartDate != null && !tStartDate.equals("")) {
			sql.append(" and TO_DATE(a.MissionProp4,'YYYY-MM-DD') >= DATE '"
					+ tStartDate + "' ");
		}
		if (tEndDate != null && !tEndDate.equals("")) {
			sql.append(" and TO_DATE(a.MissionProp4,'YYYY-MM-DD') <= DATE '"
					+ tEndDate + "' ");
		}
		sql.append(" ORDER BY a.missionprop2");

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sql.toString());

		long totalNum3 = 0;
		long totalNum4 = 0;
		long totalNum5 = 0;

		String tSQL_Apply = "";
		String tSQL_Test = "";
		String[] info = null;
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				info = new String[5];
				info[0] = tSSRS.GetText(i, 1);
				info[1] = tSSRS.GetText(i, 2);

				// 查询申请时间
				tSQL_Apply = "SELECT TO_CHAR(a.Makedate,'YYYY-MM-DD')||' '||a.Maketime FROM Lbmission  a WHERE a.Missionprop2 = '"
						+ tSSRS.GetText(i, 1)
						+ "' and a.Missionid = '"
						+ tSSRS.GetText(i, 3)
						+ "' and a.Submissionid = '1' and a.Activityid = 'pd00000000'";
				String ApplyDate = tExeSQL.getOneValue(tSQL_Apply);

				// 查询测试日期
				tSQL_Test = "SELECT TO_CHAR(a.Makedate,'YYYY-MM-DD')||' '||a.Maketime FROM Lbmission  a WHERE a.Missionprop2 = '"
						+ tSSRS.GetText(i, 1)
						+ "' and a.Missionid = '"
						+ tSSRS.GetText(i, 3)
						+ "' and a.Submissionid = '1' and a.Activityid = 'pd00000003'";
				String TestDate = tExeSQL.getOneValue(tSQL_Test);

				long minDiff_1 = calDiffDateMins(ApplyDate, TestDate);
				long minDiff_2 = calDiffDateMins(ApplyDate, tSSRS.GetText(i, 4));
				info[2] = "" + minDiff_1;
				info[4] = "" + minDiff_2;
				info[3] = "" + (minDiff_2 - minDiff_1);
				mtListTable.add(info);

				totalNum3 += minDiff_1;
				totalNum4 += (minDiff_2 - minDiff_1);
				totalNum5 += minDiff_2;
			}
		}

		mtListTable.setName("LIST");
		if (mtListTable.size() == 0) {
			return false;
		}

		TextTag tag = new TextTag();
		tag.add("TotalNum3", "" + totalNum3);
		tag.add("TotalNum4", "" + totalNum4);
		tag.add("TotalNum5", "" + totalNum5);
		xmlexport.addTextTag(tag);

		String[] title = new String[5];
		title[0] = "RiskCode";
		title[1] = "RiskName";
		title[2] = "defineTime1";
		title[3] = "defineTime2";
		title[4] = "defineTime3";

		xmlexport.addListTable(mtListTable, title);

		mResult = new VData();
		mResult.addElement(xmlexport);

		return true;
	}

	/**
	 * 计算两个日期相差的分钟数
	 * 
	 * @param dateStr1
	 * @param dateStr2
	 * @return
	 */
	private long calDiffDateMins(String dateStr1, String dateStr2) {
		long diff = 0;
		try {
			if (dateStr1 == null || dateStr1.equals("") || dateStr2 == null
					|| dateStr2.equals("")) {
				return 0;
			}
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d1 = sf.parse(dateStr1);
			Date d2 = sf.parse(dateStr2);
			diff = (d2.getTime() - d1.getTime()) / (1000 * 60);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return diff;
	}

	/**
	 * 返回清单数据
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
}
