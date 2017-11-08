

    /**
 * <p>Title: PDRateCashValue</p>
 * <p>Description: 数据表和现金价值定制</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-17
 */

package com.sinosoft.productdef;

import java.util.ArrayList;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class PDRateCashValueBL {
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

	private TransferData mTransferData;

	public PDRateCashValueBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;

		try
		{
			if (!check()) {
				return false;
			}
	
			if (!deal(cInputData, cOperate)) {
				return false;
			}
			
			this.mResult.clear();
			this.mResult.add(this.map);
			
			PubSubmit tSubmit = new PubSubmit();
	        if (!tSubmit.submitData(mResult, "")) {
	            // @@错误处理
	            this.mErrors.copyAllErrors(tSubmit.mErrors);
	            CError tError = new CError();
	            tError.moduleName = "PDRateCashValueBL";
	            tError.functionName = "submitData";
	            tError.errorMessage = "数据提交失败!";
	            this.mErrors.addOneError(tError);
	            return false;
	        }
		}
		catch(Exception ex)
		{
			this.mErrors.addOneError("PDRateCashValueBL处理错误");
			return false;
		}

		return true;
	}

	private boolean deal(VData cInputData, String cOperate) throws Exception{
		mTransferData = new TransferData();
		this.mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		java.util.ArrayList list = new ArrayList();

		list = (ArrayList) mTransferData.getValueByName("list");

		String newTableName = (String) mTransferData.getValueByName("newTableName");
		
		String tableName = (String) mTransferData.getValueByName("tableName");
		String tRiskCode = (String) mTransferData.getValueByName("RiskCode");
		String tPayCode = (String) mTransferData.getValueByName("PayCode");

		String sql = "";
		/**
		 * 遍历问题
		 */
		
		
		if (cOperate.equals("createRateTable")) {
			if (!checkIsExist(newTableName)) {
				newTableName = creatNewTableName(newTableName);
			}

			String colStr = "";

			for (int i = 0; i < list.size(); i++) {
				String colName = (String) list.get(i);

				String colDataType = getDataTypeByName(tableName, colName);

				if (colDataType.equals("SelectError")) {
					return false;
				}

				colStr += colName + " " + colDataType + ",";
			}

			colStr = colStr.substring(0, colStr.length() - 1);

			sql = "create table " + newTableName + " ( " + colStr + " )";
			
			// 将建表语句存储在PD_RateCVSQL，供发布调用
			String coreCreateSQL = "create table " + newTableName + " ( " + colStr + " )";
			saveSQLForDeploy(tRiskCode, tPayCode, coreCreateSQL, newTableName, "1");
			
		} else if (cOperate.equals("deleteRateTable")) {
			sql = "drop table " + newTableName;
			
			deleteSQL(tRiskCode, tPayCode, "1", newTableName);

		} else if (cOperate.equals("createCashValue")) {
			if (!checkIsExist(newTableName)) {
				return false;
			}

			String colStr = "";

			for (int i = 0; i < list.size(); i++) {
				String colName = (String) list.get(i);

				String colDataType = getDataTypeByName(tableName, colName);

				if (colDataType.equals("SelectError")) {
					return false;
				}

				colStr += colName + " " + colDataType + ",";
			}

			colStr = colStr.substring(0, colStr.length() - 1);

			sql = "create table " + newTableName + " ( " + colStr + " )";
			
//			 将建表语句存储在PD_RateCVSQL，供发布调用
			String coreCreateSQL = "create table " + newTableName + " ( " + colStr + " )";
			saveSQLForDeploy(tRiskCode, "000000", coreCreateSQL, newTableName, "2");
			
		} else if (cOperate.equals("deleteCVTable")) {
			sql = "drop table " + newTableName;
			
			deleteSQL(tRiskCode, "000000", "2", newTableName);
			
		}
		
		this.map.put(sql, "INSERT");

		return true;
	}
	
	private String creatNewTableName(String oldTableName) {
		String[] tab=  oldTableName.split("_");
		String rateTableNamePrefix= tab[0];
		String tRiskCode =tab[1];
		String tPayCode =tab[2];
		int lengs =rateTableNamePrefix.length() +1+tRiskCode.length()+1+tPayCode.length()+1+1;
		ExeSQL exesql = new ExeSQL();
		String sql = "select max(to_number(b.a)) from (select nvl(substr(coretablename,"+lengs+"),0) a from PD_RateCVSQL where riskcode ='" +
				tRiskCode +
				"' and dutycode ='" +
				tPayCode +
				"') b";
		return rateTableNamePrefix+"_"+tRiskCode+"_"+tPayCode+"_"+String.valueOf((Integer.valueOf(exesql.execSQL(sql).GetText(1, 1))+1));
	}

	private boolean saveSQLForDeploy(String riskCode, String dutyCode, String sql, String tableName, String tableType) throws Exception
	{
		// 校验是否已经保存过该信息
		//-

		String tDataTBLName = (String) mTransferData.getValueByName("DataTBLName");
		String insertSQL = "insert into PD_RateCVSQL values('" 
			+ riskCode + "','"
			+ dutyCode + "','"
			+ tableName + "','"
			+ tableType + "','"
			+ "1','"
			+ sql + "','"
			+ tDataTBLName 
			+ "')";

		this.map.put(insertSQL, "INSERT");
		
		return true;
	}
	
	private boolean deleteSQL(String riskCode, String dutyCode, String tableType, String coreTableName) throws Exception
	{
		String deleteSQL = "delete from Pd_Ratecvsql where riskcode = '"
			+ riskCode + "' and dutycode = '"
			+ dutyCode + "' and tabletype = '"
			+ tableType + "' and SQLType = '1'"
			+ " and CoreTableName = '" + coreTableName + "'";
		
		this.map.put(deleteSQL, "DELETE");
		
		return true;
	}
	
	private boolean checkIsExist(String newTableName) {
		String sql = "select 1 from User_Tables where table_name = upper('"
				+ newTableName + "')";
		ExeSQL exec = new ExeSQL();

		String result = exec.getOneValue(sql);

		if (result.equals("1")) {
//			this.mErrors.addOneError("数据表不能重复创建，请删除后再创建");
			return false;
		}

		return true;
	}

	private String getDataTypeByName(String tableName, String colName) {
		String result = "SelectError";

		if (colName.equalsIgnoreCase("Rate")) {
			return (String) this.mTransferData
					.getValueByName("PremDataTypeName");
		} else if (colName.equalsIgnoreCase("CaVal")) {
			return (String) this.mTransferData
					.getValueByName("CashValueDataTypeName");
		}

		String sql = "select factordatatype from " + tableName
				+ " where factorcode = '" + colName + "'";
		ExeSQL exec = new ExeSQL();

		try {
			result = exec.getOneValue(sql);
		} catch (Exception ex) {
			this.mErrors.addOneError("从要素表查找" + colName + "的数据类型失败");
		}

		return result;
	}

	private boolean check() {
		return true;
	}

	public static boolean execDDLByDBMSSQL(String dbLinkName, String sql)
			throws Exception {
		
		String dbmsSQL = "DECLARE	  sqlstr  VARCHAR2(50);		  tCursor PLS_INTEGER;		  RetVal  NUMBER; "
				+ " begin		  sqlstr := '"
				+ sql
				+ "';		  tCursor := dbms_sql.open_cursor@"
				+ dbLinkName
				+ "; "
				+ " dbms_sql.parse@"
				+ dbLinkName
				+ "(tCursor, sqlstr, dbms_sql.native);	  RetVal := dbms_sql.execute@"
				+ dbLinkName
				+ "(tCursor); "
				+ " dbms_sql.close_cursor@"
				+ dbLinkName + "(tCursor);	END;";

		ExeSQL exec = new ExeSQL();
		exec.execUpdateSQL(dbmsSQL);

		return true;
	}

	public static void main(String[] args) {
		
		try
		{
			execDDLByDBMSSQL("devlis", "drop table testproc10 ");
			execDDLByDBMSSQL("devlis", "create table testproc10 (n1 int)");
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}

