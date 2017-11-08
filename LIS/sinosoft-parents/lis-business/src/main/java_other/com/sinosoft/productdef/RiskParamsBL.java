

package com.sinosoft.productdef;

/*
 * <p>ClassName: GlobalPools </p>
 * <p>Description: 线程号与连接池映射缓存类 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @author tongmeng
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.PDT_RiskShowColSchema;
import com.sinosoft.lis.schema.PDT_RiskShowTypeSchema;
import com.sinosoft.lis.schema.PDT_RiskTypeTemplateSchema;
import com.sinosoft.lis.vschema.PDT_RiskShowColSet;
import com.sinosoft.lis.vschema.PDT_RiskShowTypeSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

public class RiskParamsBL {

	// @Field
	public CErrors mErrors = new CErrors(); // 错误信息
	/* 保存线程号和连接池名映射 */
	private Hashtable mRuleMap = new Hashtable();
	private SSRS mRuleSSRS = new SSRS();

	ArrayList mRiskParamsList = new ArrayList();

	private String mRiskCode = "";
	private String mStandbyFlag1 = "";
	private JSONObject items;

	public RiskParamsBL() {

	}

	public RiskParamsBL(String tRiskCode) {
		mRiskCode = tRiskCode;
	}

	public RiskParamsBL(String tRiskCode, String tStandbyFlag1) {
		mRiskCode = tRiskCode;
		mStandbyFlag1 = tStandbyFlag1;
	}

	public void initRiskParamsBL() {
		// 初始化所有的信息
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		/*
		 * String tSQL =
		 * "select othersign,code,codename,comcode, filtersql , flag from (select othersign,code,codename,comcode,'' filtersql ,'0' flag from ldcode where codetype ='riskparams' "
		 * + " and  code not in " + " ( " +
		 * " select colcode from pdt_riskshowcol where templateid = " +
		 * " (select templateid from pdt_risktypetemplate where riskType='"
		 * +this.mRiskCode+"') " + " ) " + " union " +
		 * " select othersign,code,codename,comcode,b.filtersql filtersql,'1' flag from ldcode a,pdt_riskshowcol b where a.codetype ='riskparams' "
		 * + " and b.templateid = " +
		 * " (select templateid from pdt_risktypetemplate where riskType='"
		 * +this.mRiskCode+"')" +
		 * "  and a.code=b.colcode)  order by to_number(othersign) ";
		 */String tSQL = "select othersign,code,codename,comcode,'','0' from ldcode where codetype='riskparams' order by to_number(othersign)";
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tSQL);
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			Map<String, String> item = new HashMap<String, String>();
			String riskParamCode = tSSRS.GetText(i, 2);
			String riskParamName = tSSRS.GetText(i, 3);
			String showFlag = tSSRS.GetText(i, 4);

			String riskParamsShow = this.buildShow(showFlag);

			item.put("idx", tSSRS.GetText(i, 1));
			item.put("riskParamCode", riskParamCode);
			item.put("riskParamName", riskParamName);
			item.put("riskParamsShow", riskParamsShow);
			item.put("fileterSql", tSSRS.GetText(i, 5));
			// item.put("chooseflag", tSSRS.GetText(i, 6));

			String sql = "select count(*) from PDT_RiskTypeTemplate tpl LEFT join pdt_riskshowcol show on tpl.templateid=show.templateid   where tpl.risktype='"
					+ mRiskCode + "' and  show.colcode='" + riskParamCode + "'";
			String count = tExeSQL.getOneValue(sql);

			if ("0".equals(count) || count == null || "".equals(count)) {
				item.put("chooseflag", "0");
			}else{
				item.put("chooseflag", "1");
			}

			mRuleMap.put(riskParamCode, riskParamName + ":" + showFlag);
			//
			// "editor":"text"
			list.add(item);
		}

		// }
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", list.size());
		jsonMap.put("rows", list);
		try {
			items = JSONObject.fromObject(jsonMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("RiskCode:" + this.mRiskCode);
	}

	private String buildShow(String tType) {
		String tShow = "";
		if (tType.equals("0")) {
			tShow = "<input class=readonly readonly>";
		} else {
			tShow = "<input class=codeno readonly><input class=codename readonly>";
		}
		return tShow;
	}

	public ArrayList getParamsList() {
		return mRiskParamsList;
	}

	public JSONObject getItems() {
		this.initRiskParamsBL();
		return items;
	}

	public JSONObject getDutyItems() {
		this.initRiskDutyParamsBL();
		return items;
	}

	public void initRiskDutyParamsBL() {
		// 初始化所有的信息
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		String tSQL = "select showtype from pdt_riskshowtype where showtype in ('Duty','Pay') "
				+ " and templateid = "
				+ " (select templateid from pdt_risktypetemplate where riskType='"
				+ this.mRiskCode + "' ";

		if ("02".equals(this.mStandbyFlag1)) {
			tSQL += " and standbyflag1='02')";
		} else {
			tSQL += " and standbyflag1<>'02')";
		}

		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tSQL);

		JSONArray jsonArray = new JSONArray();

		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			Map<String, String> item = new HashMap<String, String>();

			item.put("showtype", tSSRS.GetText(i, 1));
			item.put("showvalue", "1");

			// "editor":"text"
			// list.add(item);
			jsonArray.add(i - 1, item);
		}

		// }
		// Map<String, Object> jsonMap = new HashMap<String, Object>();
		// jsonMap.put("total",list.size());
		// jsonMap.put("rows",list);
		try {
			// items = JSONObject.fromObject(jsonMap);
			items = new JSONObject();
			items.element("data", jsonArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("RiskCode:" + this.mRiskCode);
	}

	public String getMsg(String tKeyId, String tLanguage) {
		return this.mRuleMap.get(tKeyId + tLanguage) == null ? ""
				: (String) this.mRuleMap.get(tKeyId + tLanguage);

	}

	public String getMsg(String tKeyId, String tLanguage, String tDefalutValue) {
		String tValue = mRuleMap.get(tKeyId + tLanguage) == null ? ""
				: (String) this.mRuleMap.get(tKeyId + tLanguage);

		if (tValue.equals("")) {
			return tDefalutValue;
		} else {
			String[] temp = tValue.split(":");
			if (temp.length == 1) {
				return tDefalutValue;
			}
			return tValue.split(":")[1];
		}
	}

	public boolean submitData(VData tInputData, String tOperate) {
		String[] tRiskParamsList = (String[]) tInputData.get(0);
		String tRiskCode = (String) tInputData.get(1);
		String tDutyChoose = (String) tInputData.get(2);
		String tPayChoose = (String) tInputData.get(3);

		String[] tDutySelList = (String[]) tInputData.get(4);
		String[] tPaySelList = (String[]) tInputData.get(5);

		String tStandbyFlag1 = "";
		try {
			tStandbyFlag1 = (String) tInputData.get(6);
		} catch (Exception ex) {
			tStandbyFlag1 = "01";
		}

		System.out.println("tDutyChoose:" + tDutyChoose + ":tPayChoose:"
				+ tPayChoose);
		ArrayList tArrayList = new ArrayList();
		initRiskParamsBL();
		PDT_RiskShowColSet tPDT_RiskShowColSet = new PDT_RiskShowColSet();

		PDT_RiskShowTypeSet tPDT_RiskShowTypeSet = new PDT_RiskShowTypeSet();
		if (tRiskParamsList.length > 0) {
			PDT_RiskShowTypeSchema tPDT_RiskShowTypeSchema = new PDT_RiskShowTypeSchema();
			tPDT_RiskShowTypeSchema.setShowType("Risk");
			tPDT_RiskShowTypeSchema.setShowMethod("0");
			tPDT_RiskShowTypeSchema.setShowTypeRela("0");
			tPDT_RiskShowTypeSet.add(tPDT_RiskShowTypeSchema);

		}
		if (tDutyChoose.equals("true")) {
			PDT_RiskShowTypeSchema tPDT_RiskShowTypeSchema = new PDT_RiskShowTypeSchema();
			tPDT_RiskShowTypeSchema.setShowType("Duty");
			tPDT_RiskShowTypeSchema.setShowMethod("1");
			tPDT_RiskShowTypeSchema.setShowTypeRela("1");
			tPDT_RiskShowTypeSet.add(tPDT_RiskShowTypeSchema);
		}
		if (tPayChoose.equals("true")) {
			PDT_RiskShowTypeSchema tPDT_RiskShowTypeSchema = new PDT_RiskShowTypeSchema();
			tPDT_RiskShowTypeSchema.setShowType("Pay");
			tPDT_RiskShowTypeSchema.setShowMethod("1");
			tPDT_RiskShowTypeSchema.setShowTypeRela("1");
			tPDT_RiskShowTypeSet.add(tPDT_RiskShowTypeSchema);
		}

		for (int i = 0; i < tRiskParamsList.length; i++) {
			String[] temp = tRiskParamsList[i].split(",");
			System.out.println("i:" + i + ":tRiskParamsList:"
					+ tRiskParamsList[i]);

			for (int m = 0; m < temp.length / 2; m++) {

				// System.out.println("m:"+m+":tRiskParamsList:"+temp[m]);
				String tParamsName = temp[m * 2];
				String tSQL = temp[m * 2 + 1];

				System.out.println("m:" + m + ":tParamsName:" + tParamsName
						+ ":tSQL:" + tSQL);

				PDT_RiskShowColSchema tPDT_RiskShowColSchema = new PDT_RiskShowColSchema();
				tPDT_RiskShowColSchema.setColCode(tParamsName);
				String tValue = (String) this.mRuleMap.get(tParamsName);
				String[] tSpiltValue = tValue.split(":");
				tPDT_RiskShowColSchema.setColName(tSpiltValue[0]);
				if (tSQL != null && !tSQL.equals("") && !tSQL.equals("null")) {
					tSQL = tSQL.replaceAll("@", ",");
				}
				tPDT_RiskShowColSchema.setColOrder(m + 1);
				tPDT_RiskShowColSchema.setFilterSql(tSQL);
				tPDT_RiskShowColSchema.setShowType("Risk");

				tPDT_RiskShowColSchema.setOptionFlag(tSpiltValue[1]);
				tPDT_RiskShowColSet.add(tPDT_RiskShowColSchema);
				// String[] tfinal
			}
		}

		// 增加责任列表
		Hashtable tDutyHashtable = new Hashtable();
		String tSQL = " select code,codename,codealias,comcode from ldcode where codetype='riskparamscol' "
				+ "order by othersign/1";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(tSQL);
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			tDutyHashtable.put(tSSRS.GetText(i, 1), tSSRS.GetText(i, 2) + ":"
					+ tSSRS.GetText(i, 3) + ":" + tSSRS.GetText(i, 4));
		}

		for (int i = 0; i < tDutySelList.length; i++) {

			String[] temp = tDutySelList[i].split(",");
			for (int m = 0; m < temp.length / 2; m++) {
				String tColId = temp[m * 2];
				String tCheckFlag = temp[m * 2 + 1];
				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%:" + tColId);
				String tFinalColId = tColId.substring(5);
				System.out
						.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%:" + tFinalColId);

				String[] tOtherProperty = ((String) tDutyHashtable
						.get(tFinalColId)).split(":");
				PDT_RiskShowColSchema tPDT_RiskShowColSchema = new PDT_RiskShowColSchema();
				tPDT_RiskShowColSchema.setColCode(tFinalColId);
				tPDT_RiskShowColSchema.setShowType("Duty");
				tPDT_RiskShowColSchema.setColName(tOtherProperty[0]);
				tPDT_RiskShowColSchema.setColOrder(m + 1);
				tPDT_RiskShowColSchema.setOptionFlag(tOtherProperty[2]);
				if (tCheckFlag.equals("0")) {
					tPDT_RiskShowColSchema.setColProperties("0px");
				} else {
					tPDT_RiskShowColSchema.setColProperties(tOtherProperty[1]);
				}
				tPDT_RiskShowColSet.add(tPDT_RiskShowColSchema);

			}
		}

		// 增加缴费列表

		Hashtable tPayHashtable = new Hashtable();
		tSQL = " select code,codename,codealias,comcode from ldcode where codetype='riskparamscol1' "
				+ "order by othersign/1";

		tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(tSQL);
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			tPayHashtable.put(tSSRS.GetText(i, 1), tSSRS.GetText(i, 2) + ":"
					+ tSSRS.GetText(i, 3) + ":" + tSSRS.GetText(i, 4));
		}

		for (int i = 0; i < tPaySelList.length; i++) {

			String[] temp = tPaySelList[i].split(",");
			for (int m = 0; m < temp.length / 2; m++) {
				String tColId = temp[m * 2];
				String tCheckFlag = temp[m * 2 + 1];
				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%:" + tColId);
				String tFinalColId = tColId.substring(4);
				System.out
						.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%:" + tFinalColId);

				String[] tOtherProperty = ((String) tPayHashtable
						.get(tFinalColId)).split(":");
				PDT_RiskShowColSchema tPDT_RiskShowColSchema = new PDT_RiskShowColSchema();
				tPDT_RiskShowColSchema.setColCode(tFinalColId);
				tPDT_RiskShowColSchema.setShowType("Pay");
				tPDT_RiskShowColSchema.setColName(tOtherProperty[0]);
				tPDT_RiskShowColSchema.setColOrder(m + 1);
				tPDT_RiskShowColSchema.setOptionFlag(tOtherProperty[2]);
				if (tCheckFlag.equals("0")) {
					tPDT_RiskShowColSchema.setColProperties("0px");
				} else {
					tPDT_RiskShowColSchema.setColProperties(tOtherProperty[1]);
				}
				tPDT_RiskShowColSet.add(tPDT_RiskShowColSchema);

			}
		}

		// ExeSQL tExeSQL = new ExeSQL();
		String tSQL_templateid = "select templateid from pdt_risktypetemplate where risktype='"
				+ tRiskCode + "' and standbyflag1='" + tStandbyFlag1 + "'";
		String tTemplateID = "";
		tTemplateID = tExeSQL.getOneValue(tSQL_templateid);
		if (tTemplateID == null || tTemplateID.equals("")) {
			tTemplateID = PubFun1.CreateMaxNo("RiskTempID", 10);
		}

		PDT_RiskTypeTemplateSchema tPDT_RiskTypeTemplateSchema = new PDT_RiskTypeTemplateSchema();
		tPDT_RiskTypeTemplateSchema.setRiskType(tRiskCode);
		tPDT_RiskTypeTemplateSchema.setTemplateID(tTemplateID);
		tPDT_RiskTypeTemplateSchema.setStandbyFlag1(tStandbyFlag1);

		for (int i = 1; i <= tPDT_RiskShowColSet.size(); i++) {
			tPDT_RiskShowColSet.get(i).setTemplateID(tTemplateID);
		}

		for (int i = 1; i <= tPDT_RiskShowTypeSet.size(); i++) {
			tPDT_RiskShowTypeSet.get(i).setTemplateID(tTemplateID);
		}

		String tDelete1 = "delete  from PDT_RiskTypeTemplate where templateid='"
				+ tTemplateID + "'";
		String tDelete2 = "delete  from PDT_RiskShowCol where templateid='"
				+ tTemplateID + "'";
		String tDelete3 = "delete  from PDT_RiskShowType where templateid='"
				+ tTemplateID + "'";
		MMap mMMap = new MMap();
		mMMap.put(tDelete1, "DELETE");
		mMMap.put(tDelete2, "DELETE");
		mMMap.put(tDelete3, "DELETE");
		mMMap.put(tPDT_RiskShowColSet, "INSERT");
		mMMap.put(tPDT_RiskShowTypeSet, "INSERT");
		mMMap.put(tPDT_RiskTypeTemplateSchema, "INSERT");

		PubSubmit tPubSubmit = new PubSubmit();
		VData tSubmitData = new VData();
		tSubmitData.add(mMMap);
		if (!tPubSubmit.submitData(tSubmitData, "")) {
			// System.out.println(tSubmitData.)
		}
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

