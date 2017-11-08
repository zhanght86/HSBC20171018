

/**
 * Copyright (c) 2006 sinosoft  Co. Ltd.
 * All right reserved.
 */

/*
 * <p>ClassName: OrderDescUI </p>
 * <p>Description: OrderDescUI类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2006-07-30
 */
package com.sinosoft.lis.reinsure.stat.antail;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class RICataRiskBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 前台传入的公共变量 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private TransferData mTransferData = new TransferData();
	private String tableType = "";
	private String EndDate = "";

	/** 数据操作字符串 */
	private String strOperate = "";
	private MMap mMap = new MMap();
	private PubSubmit tPubSubmit = new PubSubmit();

	// 业务处理相关变量
	/** 全局数据 */

	public RICataRiskBL() {
	}
	/**
	 * 提交数据处理方法
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.strOperate = cOperate;
		if (strOperate.equals("")) {
			buildError("verifyOperate", " GrQuotSharCESSTabBL->1 不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData)) {
			System.out.println("－－获取数据失败－－－");
			return false;
		}
		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}
	private boolean getPrintData() {
		// TODO Auto-generated method stub
		TextTag texttag = new TextTag();
		// 统计的起讫时间
		ListTable multTable = new ListTable();
		multTable.setName("MULT");
		XmlExport xmlexport = new XmlExport();
		// 以下表示在 报表中生成 26列，
		String[] Title = { "col1", "col2", "col3", "col4", "col5", "col6",
				"col7", "col8", "col9", "col10", "col11", "col12", "col13",
				"col14", "col15", "col16", "col17", "col18", "col19", "col20",
				"col21", "col22", "col23", "col24", "col25", "col26", "col27",
				"col28", "col29", "col30", "col31", "col32", "col33", "col34",
				"col35", "col36", "col37", "col38", "col39", "col40", "col41",
				"col42", "col43", "col44", "col45", "col46", "col47", "col48",
				"col49", "col50", "col51", "col52", "col53", "col54", "col55",
				"col56", "col57", "col58", "col59", "col60", "col61", };
		SSRS tSSRS1 = new SSRS();
		if(tableType.equals("section1")){
			texttag.add("EndDate", EndDate);
			if (texttag.size() > 0) {
				xmlexport.createDocument("section1-001.vts", "printer");
				xmlexport.addTextTag(texttag);
			}
		}
		if(tableType.equals("section2")){
			String  sql="select sum(DivisionLineValue) from RIDivisionLineDef t, RIPrecept r where t.RIPreceptNo=r.RIPreceptNo and t.RIContNo=r.RIContNo and r.AccumulateDefNO='L000000001'";
			ExeSQL tExeSQL1 = new ExeSQL();
			tSSRS1 = tExeSQL1.execSQL(sql);
			texttag.add("individaul_life", tSSRS1.GetText(1, 1));
			tSSRS1=new SSRS();
			String  sql2="select sum(DivisionLineValue) from RIDivisionLineDef t, RIPrecept r where t.RIPreceptNo=r.RIPreceptNo and t.RIContNo=r.RIContNo and r.AccumulateDefNO='F00088'";
			ExeSQL tExeSQL2 = new ExeSQL();
			tSSRS1 = tExeSQL2.execSQL(sql2);
			texttag.add("individual_pa", tSSRS1.GetText(1, 1));
			tSSRS1=new SSRS();
			String  sql3="select count(r.RIContNo) from RIBarGainInfo r,RIPrecept t where r.ricontno=t.ricontno and t.accumulatedefno='L000000001'";
			ExeSQL tExeSQL3 = new ExeSQL();
			tSSRS1 = tExeSQL3.execSQL(sql3);
			texttag.add("number_life", tSSRS1.GetText(1, 1));
			tSSRS1=new SSRS();
			String  sql4="select count(r.RIContNo) from RIBarGainInfo r,RIPrecept t where r.ricontno=t.ricontno and t.accumulatedefno='F00088'";
			ExeSQL tExeSQL4 = new ExeSQL();
			tSSRS1 = tExeSQL4.execSQL(sql4);
			texttag.add("number_pa", tSSRS1.GetText(1, 1));
			tSSRS1=new SSRS();
			if (texttag.size() > 0) {
				xmlexport.createDocument("section2-001.vts", "printer");
				xmlexport.addTextTag(texttag);
			}
		}
		 if(tableType.equals("section3")){
			 int [] amountBegin={0,50001,100001,200001,300001,500001,800001,1000001,2000001,3000001,5000001,10000001,20000000};
			 int [] amountEnd={50000,100000,200000,300000,500000,800000,1000000,2000000,3000000,5000000,10000000,20000000,20000000};
			 String [] name={"0-50,000","50,001-100,000","100,001-200,000","200,001-300,000","300,001-500,000","500,001-800,000","800,001-1,000,000","1,000,001-2,000,000","2,000,001-3,000,000","3,000,001-5,000,000",
					 "5,000,001-10,000,000","10,000,001-20,000,000","20,000,000 and more"};
			 String sqlQuery="";
			 Double[] rsd=new Double[]{0.0,0.0,0.0,0.0};
			 //死亡
			 for(int j=0;j<amountEnd.length;j++){
				 if(j==amountEnd.length-1){
					 sqlQuery="select count(r.InsuredNo)," +
								 		"sum(r.RiskAmnt)," +
								 		"sum(x.DivisionLineValue)," +
								 		"TRUNC(sum(x.DivisionLineValue)/count(r.InsuredNo))" +
								 		"from RIDivisionLineDef x,RIPrecept t,RIPolRecord  r " +
								 		"where t.ripreceptno = x.ripreceptno and t.accumulatedefno = r.accumulatedefno" +
								 		" and x.divisionlinetype = '01'and r.prem >'"+amountEnd[j]+"'" +
								 		"and t.accumulatedefno='L000000001'"; 
				 }
				 else{
					  sqlQuery=" select count(r.InsuredNo)," +
										 		"sum(r.RiskAmnt)," +
										 		"sum(x.DivisionLineValue)," +
										 		"TRUNC(sum(x.DivisionLineValue)/count(r.InsuredNo))" +
										 		"from RIDivisionLineDef x,RIPrecept t,RIPolRecord  r " +
										 		"where t.ripreceptno = x.ripreceptno and t.accumulatedefno = r.accumulatedefno" +
										 		" and x.divisionlinetype = '01'and r.prem between '"+amountBegin[j]+"' and '"+amountEnd[j]+"'" +
										 		"and t.accumulatedefno='L000000001'"; 
				 }
					 ExeSQL tExeSQL = new ExeSQL();
			 tSSRS1 = tExeSQL.execSQL(sqlQuery);
			// 查询结果的记录条数
				int count = tSSRS1.getMaxRow();
				System.out.println("该sql执行后的记录条数为：" + count);
				// 将查询结果存放到临时 二维数组中
				String temp[][] = tSSRS1.getAllData();
				String[] strCol;
				for (int i = 0; i < count; i++) 
				{
					strCol = new String[28];
					strCol[0] = name[j];
					strCol[1] = temp[i][0];
					rsd[0]=rsd[0]+Double.parseDouble(strCol[1]);
					if(temp[i][1]=="null"|| temp[i][1].equals("null"))
					{
						strCol[2]="0";
					}
					else{
						strCol[2] = temp[i][1];
					}
					rsd[1]=rsd[1]+Double.parseDouble(strCol[2]);
					if(temp[i][2]=="null"|| temp[i][2].equals("null"))
					{
						strCol[3]="0";
					}
					else{
						strCol[3] = temp[i][2];
					}
					rsd[2]=rsd[2]+Double.parseDouble(strCol[3]);
					if(temp[i][3]=="null"|| temp[i][3].equals("null"))
					{
						strCol[4]="0";
					}
					else{
						strCol[4] = temp[i][3];
					}
					rsd[3]=rsd[3]+Double.parseDouble(strCol[4]);
					multTable.add(strCol);
				}
			 }
			 //意外
			ListTable multTable2 = new ListTable();
			multTable2.setName("MULT2");
			Double[] rsy = new Double[]{0.0,0.0,0.0,0.0};
			 for(int j=0;j<amountEnd.length;j++){
				 if(j==amountEnd.length-1){
					 sqlQuery="select count(r.InsuredNo)," +
								 		"sum(r.RiskAmnt)," +
								 		"sum(x.DivisionLineValue)," +
								 		"TRUNC(sum(x.DivisionLineValue)/count(r.InsuredNo))" +
								 		"from RIDivisionLineDef x,RIPrecept t,RIPolRecord  r " +
								 		"where t.ripreceptno = x.ripreceptno and t.accumulatedefno = r.accumulatedefno" +
								 		" and x.divisionlinetype = '01'and r.prem >'"+amountEnd[j]+"'" +
								 		"and t.accumulatedefno='F00088'"; 
				 }
				 else{
					  sqlQuery=" select count(r.InsuredNo)," +
										 		"sum(r.RiskAmnt)," +
										 		"sum(x.DivisionLineValue)," +
										 		"TRUNC(sum(x.DivisionLineValue)/count(r.InsuredNo))" +
										 		"from RIDivisionLineDef x,RIPrecept t,RIPolRecord  r " +
										 		"where t.ripreceptno = x.ripreceptno and t.accumulatedefno = r.accumulatedefno" +
										 		" and x.divisionlinetype = '01'and r.prem between '"+amountBegin[j]+"' and '"+amountEnd[j]+"'" +
										 		"and t.accumulatedefno='F00088'"; 
				 }
					 ExeSQL tExeSQL = new ExeSQL();
			 tSSRS1 = tExeSQL.execSQL(sqlQuery);
			// 查询结果的记录条数
				int count = tSSRS1.getMaxRow();
				System.out.println("该sql执行后的记录条数为：" + count);
				// 将查询结果存放到临时 二维数组中
				String temp[][] = tSSRS1.getAllData();
				String[] strCol;
				for (int i = 0; i < count; i++) 
				{
					strCol = new String[28];
					strCol[0] = name[j];
					strCol[1] = temp[i][0];
					rsy[0]=rsy[0]+Double.parseDouble(strCol[1]);
					if(temp[i][1]=="null"|| temp[i][1].equals("null"))
					{
						strCol[2]="0";
					}
					else{
						strCol[2] = temp[i][1];
					}
					rsy[1]=rsy[1]+Double.parseDouble(strCol[2]);
					if(temp[i][2]=="null"|| temp[i][2].equals("null"))
					{
						strCol[3]="0";
					}
					else{
						strCol[3] = temp[i][2];
					}
					rsy[2]=rsy[2]+Double.parseDouble(strCol[3]);
					if(temp[i][3]=="null"|| temp[i][3].equals("null"))
					{
						strCol[4]="0";
					}
					else{
						strCol[4] = temp[i][3];
					}
					rsy[3]=rsy[3]+Double.parseDouble(strCol[4]);
					multTable2.add(strCol);
				}
			 }
			 texttag.add("NOI_TOTAL_1", rsd[0]);
			 texttag.add("GSR_TOTAL_1", rsd[1]);
			 texttag.add("RSR_TOTAL_1", rsd[2]);
			 texttag.add("MRP_TOTAL_1", rsd[3]);
			 texttag.add("NOI_TOTAL_2", rsy[0]);
			 texttag.add("GSR_TOTAL_2", rsy[1]);
			 texttag.add("RSR_TOTAL_2", rsy[2]);
			 texttag.add("MRP_TOTAL_2", rsy[3]);
			 xmlexport.createDocument("section3-001.vts", "printer");
			 xmlexport.addListTable(multTable2, Title);
			 xmlexport.addListTable(multTable, Title);
			 xmlexport.addTextTag(texttag);
		 }
		 if(tableType.equals("section4")){
			 xmlexport.createDocument("section4-001.vts", "printer");
			}
		 if(tableType.equals("section5")){
			 xmlexport.createDocument("section5-001.vts", "printer");
			}
		 if(tableType.equals("section6")){
			 xmlexport.createDocument("section6-001.vts", "printer");
			}
		// 保存信息
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}
	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "ReComManageBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		mTransferData = ((TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0));

		if (mTransferData == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		tableType = (String) mTransferData.getValueByName("tableType");
		EndDate = (String) mTransferData.getValueByName("EndDate");
		return true;

	}

	public VData getResult() {
		return mResult;
	}

}
