

package com.sinosoft.productdef;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.lis.db.PD_LBRiskInfoDB;
import com.sinosoft.lis.schema.PD_LBRiskInfoSchema;
import com.sinosoft.lis.vschema.PD_LBRiskInfoSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PDLBRiskInfoBL {

	/** 错误的容器 */
	public CErrors mErrors = new CErrors();

	private TransferData mTransferData = null;

	private VData mResult = null;

	ListTable mtListTable = new ListTable();
	
	private String mRiskCode;
	
	private String mBatchNum;

	public PDLBRiskInfoBL() {
	}

	/**
	 * 
	 * @param args
	 *            VData 需包含LGEdorApp(需受理号EdorAcceptNO即可)、GlobalIiput
	 * @param args
	 *            String 操作方式
	 * @return boolean
	 */
	public ListTable submitData(VData cInputData, String cOperate) {

		if (!getInputData(cInputData)) {
			return mtListTable;
		}

		// 获取打印所需数据
		if (!dealData(cOperate)) {
			return mtListTable;
		}

		return mtListTable;
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
			mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
			mRiskCode = (String) mTransferData.getValueByName("RiskCode");
			mBatchNum = (String)mTransferData.getValueByName("batch");
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
		//获得查询条件
		
		String tStartDate = (String) mTransferData.getValueByName("StartDate");
		String tEndDate = (String) mTransferData.getValueByName("EndDate");
		
		if (mRiskCode == null || mRiskCode.equals("")) {
			return false;
		}
		
		//查询出所有满足条件的修改记录
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM PD_LBriskInfo WHere riskcode = '")
		   .append(mRiskCode)
		   .append("'");
		
		if (tStartDate != null && !tStartDate.equals("")) {
			sql.append(" and makedate >= DATE'" + tStartDate + "' ");
		}
		if (tEndDate != null && !tEndDate.equals("")) {
			sql.append(" and makedate <= DATE'" + tEndDate + "' ");
		}
		if(mBatchNum != null && !mBatchNum.equals("")){
			sql.append(" and serialno = '")
			   .append(mBatchNum)
			   .append("' ");
		}
		sql.append(" ORDER BY tablecode,serialno");
		
		System.out.println("sql:" + sql);
		
		PD_LBRiskInfoDB db = new PD_LBRiskInfoDB();
		PD_LBRiskInfoSet set = db.executeQuery(sql.toString());

		if (set != null && set.size() > 0) {
			// PD_LBRiskInfo行数据循环
			for (int i = 1; i <= set.size(); i++) {
				//将数据转化成需要的格式
				System.out.println("第" + i + "次");
				getModifiedColumn(set.get(i));
			}
			getOtherInfo();
		} else {
			mErrors.addOneError("该险种没有修改记录！");
			return false;
		}

		return true;
	}

	/**
	 * getRiskModifyData()子方法
	 * 
	 * @return
	 */
	private void getModifiedColumn(PD_LBRiskInfoSchema schema) {
		
		String[] info = null;
		
		//获得表名
		String tTableCode = schema.getTableCode();
		
		// 必须按displayorder排序, 查询出修改的表的所有显示的行
//		String tSQL = "select fieldcode,displayorder,Isprimary,fieldname from Pd_Basefield where tablecode = upper('"
//				       + tTableCode + "') and isdisplay = '1' order by displayorder";
		
		//取出所有的行
		String tSQL = "select fieldcode,displayorder,Isprimary,fieldname from Pd_Basefield where tablecode = upper('"
						+ tTableCode + "') order by displayorder";
		SSRS ssrs = new ExeSQL().execSQL(tSQL);
		
		if (ssrs != null && ssrs.MaxRow > 0) {

			// 查询现数据库中数据
			StringBuffer sql = new StringBuffer();
			StringBuffer tColumns = new StringBuffer();
			StringBuffer tWherePart = new StringBuffer();

			for (int i = 1; i <= ssrs.MaxRow; i++) {
				if (ssrs.GetText(i, 3) != null && "1".equals(ssrs.GetText(i, 3))) {//如果是主键
					String value = schema.getV("standbyflag" + ssrs.GetText(i, 2));
					
					//如果为null 将null转换为空
					if(value == null || value.trim().toLowerCase().equals("null")){
						value = "";
					}
					
					tWherePart.append(" and ")
							  .append(ssrs.GetText(i, 1))
							  .append("='")
							  .append(value)
							  .append("' ");
				} else {//其他的列
					tColumns.append(ssrs.GetText(i, 1) + ",");
				}
			}
			
			//拼成SQL ，查询现在的值
			sql.append(" select ");
			sql.append(tColumns);
			sql.append("''");
			sql.append(" from " + tTableCode);
			sql.append(" where 1=1 ");
			sql.append(tWherePart);

			SSRS tSSRS = new ExeSQL().execSQL(sql.toString());
			int selIndex = 1;
			
				for (int i = 1; i <= ssrs.MaxRow; i++) {

					if (ssrs.GetText(i, 3) != null && "1".equals(ssrs.GetText(i, 3))) {
						// 主键不判断
						
					} else {
						info = new String[5];
						info[0] = schema.getRiskCode();
						info[1] = schema.getMakeDate();
						//获得table的中文名字
						String tableNameSQL = "select tablename from pd_basetable where tablecode = upper('" + tTableCode + "')";
						SSRS tSSRST = new ExeSQL().execSQL(tableNameSQL);
						String tableName = tSSRST.GetText(1, 1);
						
						info[2] = tableName + "." + ssrs.GetText(i, 4);
						// 原值
						info[3] = StrTool.cTrim(schema.getV("standbyflag" + ssrs.GetText(i, 2)));
						if (info[3] == null || info[3].equalsIgnoreCase("null")) {
							info[3] = "";
						} else if (info[3].equals("0.0")) {
							info[3] = "0";
						}else if((info[3].lastIndexOf("0") + 1) == info[3].length() && info[3].lastIndexOf(".") != -1){
							java.text.DecimalFormat df=new java.text.DecimalFormat("##.##");
							String str=df.format(Double.parseDouble(info[3]));
							
							info[3] = str;
							}
						
						
						// 现值
						if (tSSRS != null && tSSRS.MaxRow > 0 && selIndex <= tSSRS.MaxCol) {
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
	 * 获得备份版本里面没有的数据，而当前版本有的数据
	 * 
	 * @return
	 */
	private boolean getOtherInfo(){
		//查询出所有的表
		String findAllTabless = "select distinct b.Tablecode,a.standbyflag1,a.tablename from Pd_Basetable a,PD_TableMap b where a.Tablecode=upper(b.tablecode) and trim(a.standbyflag2) in ('1','101') order by tablecode";
		ExeSQL exeSQL = new ExeSQL();
		SSRS tablesSSRS = exeSQL.execSQL(findAllTabless);
		
		for(int o = 1; o <= tablesSSRS.MaxRow; o ++){
			String tTableCode = tablesSSRS.GetText(o, 1);
			String tTableName = tablesSSRS.GetText(o, 3);
	
			if(tTableCode.trim().equals("PD_LMEdorZT1")){
				System.out.println("stop");
			}
			System.out.println("这次执行的表名为" + o + ": " + tTableCode);
			
			String findAllTables = "select distinct b.Tablecode,a.standbyflag1 from Pd_Basetable a,PD_TableMap b where a.Tablecode=upper(b.tablecode) and trim(a.standbyflag2) in ('1','101') and upper(b.tablecode) = upper('" + tTableCode + "')";
			ExeSQL tExeSQl = new ExeSQL();
			SSRS tSSRS = tExeSQl.execSQL(findAllTables);
			
			if(StrTool.cTrim(tSSRS.GetText(1, 2)).equals("")){
	
			}else{
				String selectSQL = tSSRS.GetText(1, 2).replaceAll("@RISKCODE@", this.mRiskCode);			
				//取出所有主键
				String tSQL = "select fieldcode,displayorder,Isprimary,fieldname from Pd_Basefield where tablecode = upper('"
					+ tTableCode + "') and Isprimary = '1' order by displayorder";
				
				SSRS ssrs = tExeSQl.execSQL(tSQL);
				
				//获得所有的主键 进行比较
				String getSQL1 = "";
				boolean isFirstFlag = true;
				
				for(int j = 1; j <= ssrs.getMaxRow(); j ++){
					if(isFirstFlag){
						getSQL1 = getSQL1 + " " + ssrs.GetText(j, 1);
						isFirstFlag = false;
					}else{
						getSQL1 = getSQL1 + " ," + ssrs.GetText(j, 1);
					}
				}
				
				String newSQL = selectSQL.replace("*", getSQL1);
				SSRS findSSRS1 = tExeSQl.execSQL(newSQL);
					
				String getSQL2 = "select ";
				boolean isFirstFlag2 = true;
				for(int k = 1; k <= ssrs.getMaxRow(); k ++){
					if(isFirstFlag2){
						getSQL2 = getSQL2 + " standbyflag" + ssrs.GetText(k, 2);
						isFirstFlag2 = false;
					}else{
						getSQL2 = getSQL2 + " , standbyflag" + ssrs.GetText(k, 2);
					}
				}
				
				getSQL2 = getSQL2 + " from pd_lbriskinfo where serialno = '" + mBatchNum + "' and upper(tablecode) = upper('" + tTableCode + "')";
				
				System.out.println("getSQL2:" + getSQL2);
				SSRS findSSRS2 = tExeSQl.execSQL(getSQL2);
				List dif = new ArrayList();
				for(int i = 1; i <= findSSRS1.MaxRow; i ++){
					dif.add("" + i);
				}
				
				//判断不一样的数据
				for(int i = 1; i <= findSSRS1.MaxRow; i ++){
					String str = "";
					
					for(int j = 1; j <= findSSRS1.MaxCol; j ++){
						str = findSSRS1.GetText(i, j) + str;
					}
					
					System.out.println("str:" + str);
					
					for(int k = 1; k <= findSSRS2.MaxRow; k ++){
						String str2 = "";
						
						for(int l = 1; l <= findSSRS2.MaxCol; l ++){
							str2 = findSSRS1.GetText(k, l) + str2;
						}
						
						System.out.println("str2:" + str2);
						if(str.trim().equals(str2)){
							dif.remove("" + i);
							break;
						}				
					}
				}
				
				//获得不同的
				for(int i = 0; i < dif.size(); i ++){
					String k = (String)dif.get(i);
					
					System.out.println("剩余的是：" + k);
					//拼装SQL				
					String whereSQL = " where 1 = 1 ";
					
					for(int u = 1; u <= ssrs.MaxRow; u ++){
						whereSQL = whereSQL + " and " + ssrs.GetText(u, 1) + " = '" + findSSRS1.GetText(1, u) + "'";
					}
					
					System.out.println("whereSQL:" + whereSQL);
					
					//获得当前系统中的该表的所有的列
					String findAllFieldName = "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = upper('" + tTableCode + "') ORDER BY COLUMN_ID";
					SSRS currentTableFields = tExeSQl.execSQL(findAllFieldName);
				
					//查找makedate
					String findMakeDate = "select makedate from pd_lbriskinfo where serialno = '" + mBatchNum + "'";
					SSRS makeDateSSRS = tExeSQl.execSQL(findMakeDate);
					String makeDate = makeDateSSRS.GetText(1, 1);
					
					for(int y = 1; y <= currentTableFields.MaxRow; y ++){
						String fieldName = currentTableFields.GetText(y, 1);
						System.out.println("处理的列为：" + fieldName);
						//查询出列的中文名字
						String findFieldName = "select fieldname from pd_basefield where tablecode = upper('" + tTableCode +"') and fieldcode = upper('" + fieldName + "')";
						SSRS fieldCHNameSSRS = tExeSQl.execSQL(findFieldName);
						String fieldCHName = fieldCHNameSSRS.GetText(1, 1);
						
						String[] info = new String[5];
						info[0] = mRiskCode;
						info[1] = makeDate;
						info[2] = tTableName + "." + fieldCHName;
						info[3] = "";				
						String findInfo4Value = "select " + fieldName + " from " + tTableCode + whereSQL;
						SSRS fieldValueField = tExeSQl.execSQL(findInfo4Value);
						info[4] = fieldValueField.GetText(1, 1);
						System.out.println("险种：" + info[0] + ";判断字段：" + info[2] 
						            	   + ";原值：[" + info[3] + "];现值：[" + info[4]
						            	   + "];原值现值相同标志：" + info[3].equals(info[4]));
						
						if(!(info[3].trim().equals(info[4].trim()))){
							mtListTable.add(info);
						}
					} 
				}
				
			}
		}

		return true;
	}
	
	public static void main(String args[]){
//		String a = 0 + "";
//		
//		if((a.lastIndexOf("0") + 1) == a.length() && a.lastIndexOf(".") != -1){
//			java.text.DecimalFormat df=new java.text.DecimalFormat("##.##");
//			String str=df.format(Double.parseDouble(a));
//			
//			a = str;
//			}
//		
//		System.out.println(a);
		
		String a = "select * from PD_LMEdorZT1 where riskcode =Trim('1234')";
		String b = " RISKCODE ,DUTYCODE ,PAYPLANCODE ,SURRCALTYPE ,CYCPAYINTVTYPE";
		System.out.println(a.replace("*", b));
	}
}
