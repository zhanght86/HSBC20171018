

package com.sinosoft.productdef;

import java.util.ArrayList;

import org.json.XML;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;

/**
 * 
 * 规则引擎决策表替代方案
 * @author Administrator
 *
 */
public class RateInfoPrepare extends XML{
	
	public static final int rowNumLimit = 10;
	
	private RateInfoPrepare(){};
	
	public  static boolean existsTable(String tTableName)
	{
		String tResult = "";
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select count(1) from user_tab_columns where upper(table_name)='"+tTableName+"'";   					 
		tResult = tExeSQL.getOneValue(sql);
		if(tResult!=""&&!"0".equals(tResult)){
			return true;
		}else{
			return false;
		}
	}
	
	public  static String[][] getDtColumn(String tRateTableName)
	{
		String[][] tResultSSRS = null;
		ExeSQL tExeSQL = new ExeSQL();
		String queryColNames = "select column_name,table_name from user_tab_columns where upper(table_name) = '"+tRateTableName+"'";   					 
		tResultSSRS = tExeSQL.execSQL(queryColNames).getAllData();
		return tResultSSRS;
	}
	
	
	public  static TransferData getColumnNameAndTypes(String tTableName)
	{
		TransferData tTransferData = new TransferData();
		ExeSQL tExeSQL = new ExeSQL();
			String queryColNames = "select  column_name,data_type from user_tab_columns where upper(table_name) = '"+tTableName+"'";   					 
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(queryColNames);
			String tColumnNames = "";
			String tColumnTypes = "";
			for(int i=1;i<=tSSRS.getMaxRow();i++)
			{
				String tType = tSSRS.GetText(i, 2);
				if(tType.toLowerCase().equals("varchar2"))
				{
					tType = "String";
				}
				else if(tType.toLowerCase().equals("button"))
					{
						tType = "String";
					}
				if(i==1)
				{
					tColumnNames = tSSRS.GetText(i, 1);
					tColumnTypes = tType;
				}
				else
				{
					tColumnNames = tColumnNames + ";" + tSSRS.GetText(i, 1);
					tColumnTypes = tColumnTypes + ";" + tType;
				}
			}
			tTransferData.setNameAndValue("columnnames", tColumnNames);
			tTransferData.setNameAndValue("columntypes", tColumnTypes);
		return tTransferData;
	}
	

	public static ArrayList getRuleDataArray(String tTableName)
	{
		ArrayList tArrayList = new ArrayList();
		String sql="select * from "+tTableName;
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sql);
		String[][] tDTColums = getDtColumn(tTableName);
		for(int i=1;i<=tSSRS.getMaxRow();i++)
		{
			String[][] tempStr = new String[tDTColums.length][2];
			for(int j=0;j<tDTColums.length;j++)
			{
				tempStr[j][0] = tDTColums[j][0];
				tempStr[j][1] = tSSRS.GetText(i, j+1);
			}
			tArrayList.add(tempStr);
		}
		return tArrayList;
	}
	public static void main(String[] args) {
		
	}
}

