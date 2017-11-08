/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDMenuDB;
import com.sinosoft.lis.schema.LDMenuSchema;
import com.sinosoft.lis.vschema.LDMenuSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.pubfun.PubFun;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 菜单节点显示处理/p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author dingzhong
 * @version 1.0
 */
public class MenuShow {
private static Logger logger = Logger.getLogger(MenuShow.class);

	// String ls_location = "现在的位置：";
	StringBuffer ls_location = new StringBuffer("现在的位置：");

	public MenuShow() {
	}

	/**
	 * 获得所有根节点
	 * 
	 * @param strNode
	 *            String[][] 0 parent; 1 nodecode 2 nodename 3 childflag 4
	 *            runscript
	 * @param NodeCount
	 *            int
	 * @return String
	 */
	public String getAllLeafNodePath(String[][] strNode, int NodeCount) {
		StringBuffer resultStr = new StringBuffer(256);
		int count = 0; // count the leafnode num of strNode
		for (int i = 0; i < NodeCount; i++) {
			if (strNode[i][3].compareTo("0") != 0) // it is not a leaf node
			{
				continue;
			}

			// now make the leafnode's string ,it's format is
			// "fullpathname-leafnodename-runscript"

			// make leaf node's full path name
			if (count != 0) {
				// resultStr.append(resultStr);
				resultStr.append("^");
				// resultStr += "^";
			}

			// String nameStr = strNode[i][2];
			StringBuffer nameStr = new StringBuffer("256");
			int index = i - 1;
			String parentCode = strNode[i][0];
			while (index >= 0 && parentCode.compareTo("0") != 0) {
				if (strNode[index][1].compareTo(parentCode) == 0) {
					// nameStr = strNode[index][2] + "-" + nameStr;
					nameStr.append(strNode[index][2]);
					nameStr.append("-");
					nameStr.append(strNode[i][2]);
					parentCode = strNode[index][0];
				}
				index--;
			}
			// make up the leafnode string
			// resultStr.append(resultStr);
			resultStr.append(nameStr);
			resultStr.append("|");
			resultStr.append(strNode[i][1]);
			resultStr.append("|");
			resultStr.append(strNode[i][4]);
			// resultStr += nameStr + "|" + strNode[i][1] + "|" + strNode[i][4];
			count++;

		}
		return resultStr.toString();
	}

	/**
	 * 外层循环
	 * 
	 * @param strNode
	 *            String[][]
	 * @param intIndex
	 *            int
	 * @param NodeCount
	 *            int
	 * @param menuCount
	 *            int[]
	 * @return String
	 */
	public String getMenu(String[][] strNode, int intIndex, int NodeCount,
			int[] menuCount) {
		// String strMenuReturn = "";
		// String strMenu = "";
		StringBuffer strMenuReturn = new StringBuffer(256);
		StringBuffer strMenu = null;
		StringBuffer menuStr = null;

		int z = 1;
		for (int j = intIndex; j < NodeCount; j++) {

			if (strNode[j][0].compareTo("0") == 0) {
				int[] childNum = new int[1];
				childNum[0] = 0;
				// String temp = "";
				// 重新初始化StringBuffer
				strMenu = new StringBuffer(256);
				strMenu.append("Menu");
				strMenu.append(z);
				// strMenu = "Menu" + z;

				String childStr = "";
				// String menuStr = "";
				// StringBuffer menuStr = new StringBuffer(256);

				// 得到子节点菜单字符串
				if (strNode[j][3].compareTo("0") != 0) {
					// 判断是否下面的点真是它的子节点
					if (j == NodeCount - 1) {
						break;
					}
					if (strNode[j + 1][0].compareTo(strNode[j][1]) == 0) {
						// strMenu.append(strMenu);
						strMenu.append("_");
						// strMenu = strMenu + "_";
						// 调用生成子菜单函数
						childStr = getMenuChild(strNode, j + 1, strNode[j][3],
								strMenu.toString(), NodeCount, childNum);
						if (childNum[0] == 0) {
							continue;
						}
					}
				}

				if (strNode[j][3].compareTo("0") != 0 && childNum[0] == 0) {
					continue;
				}

				if ((strNode[j][4].compareTo("null") == 0)
						|| (strNode[j][4].compareTo("") == 0)
						|| (strNode[j][4] == null)) {
					menuStr = new StringBuffer(256);
					menuStr.append("Menu");
					menuStr.append(z);
					menuStr.append("=new Array('");
					menuStr.append(strNode[j][2]);
					menuStr.append("','','',");
					menuStr.append(childNum[0]);
					menuStr
							.append(",MenuHeight,120,'','','','','','',-1,1,-1,'','");
					menuStr.append(strNode[j][1]);
					menuStr.append("');");
					// menuStr = "Menu" + z + "=new Array('" + strNode[j][2] +
					// "','',''," +
					// childNum[0] +
					// ",MenuHeight,120,'','','','','','',-1,1,-1,'','" +
					// strNode[j][1] + "');";
				} else {
					menuStr = new StringBuffer(256);
					menuStr.append("Menu");
					menuStr.append(z);
					menuStr.append("=new Array('");
					menuStr.append(strNode[j][2]);
					menuStr.append("','");
					menuStr.append(strNode[j][4]);
					menuStr.append("','',");
					menuStr.append(childNum[0]);
					menuStr
							.append(",MenuHeight,120,'','','','','','',-1,1,-1,'','");
					menuStr.append(strNode[j][1]);
					menuStr.append("');");
					// menuStr = "Menu" + z + "=new Array('" + strNode[j][2] +
					// "','" + strNode[j][4] + "',''," + childNum[0] +
					// ",MenuHeight,120,'','','','','','',-1,1,-1,'','" +
					// strNode[j][1] + "');";
				}

				z++;
				menuCount[0]++;
				// strMenuReturn.append(strMenuReturn);
				strMenuReturn.append(menuStr);
				strMenuReturn.append(childStr);
				// strMenuReturn += menuStr.toString() + childStr;
			}
		}
		return strMenuReturn.toString();
	}
	public String getFavorite(String cUserCode)
	{
		StringBuffer tSBql = new StringBuffer(256);
		String tResult = "";
		// 首先是查询这个用户所在的菜单组，在查询这个用户在收藏夹中所拥有的节点
		tSBql.append("select * from ldmenu where nodecode in (select a.nodecode from ldmenugrptomenu a,ldmenushort b where b.operator = '");
		tSBql.append("?cUserCode?");
		tSBql.append("' and a.nodecode = b.nodecode and a.menugrpcode in (select menugrpcode from ldusertomenugrp where usercode = '");
		tSBql.append("?cUserCode?");
		tSBql.append("')) order by nodeorder");
		// logger.debug(tSBql.toString());
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSBql.toString());
		sqlbv.put("cUserCode", cUserCode);

		LDMenuDB tLDMenuDB = new LDMenuDB();
		LDMenuSet tLDMenuSet = tLDMenuDB.executeQuery(sqlbv);

		//
		if (tLDMenuSet != null && tLDMenuSet.size() > 0)
	    {
	        StringBuffer sbMenuNodeData = new StringBuffer(256);
	        for (int i = 1; i <= tLDMenuSet.size(); i++)
	        {
	            LDMenuSchema tLDMenuSchema = new LDMenuSchema();
	            tLDMenuSchema = tLDMenuSet.get(i);
	            sbMenuNodeData.append("\r\n");
	            sbMenuNodeData.append("                ");
	            sbMenuNodeData.append("nodes[\"");
	            String sParentNodeCode = tLDMenuSchema.getParentNodeCode();
//	            if (sParentNodeCode == null || sParentNodeCode.trim().equals("") || sParentNodeCode.trim().equals("0"))
//	            {
//	                sbMenuNodeData.append("1");
//	            }
//	            else
//	            {
//	               // sbMenuNodeData.append(sParentNodeCode);
//	            }
	            sbMenuNodeData.append("2");
	            sbMenuNodeData.append("_0000"+String.valueOf(i));
	            sbMenuNodeData.append("");
	            sbMenuNodeData.append("\"] = \"");
	            sbMenuNodeData.append("text:");
	            sbMenuNodeData.append(tLDMenuSchema.getNodeName());
	            sbMenuNodeData.append("; url:");
	            String sRunScript = tLDMenuSchema.getRunScript();
	            if (sRunScript == null || sRunScript.trim().equals(""))
	            {
	            	//sunsheng modify 2011-05-24 优化首页，显示用户权限菜单页面  
	                //sbMenuNodeData.append("../whatsnew.xml");
	            	sbMenuNodeData.append("../usermission/UserMission.jsp");
	            }
	            else if (sRunScript.indexOf("?") != -1)
	            {
	                String sLinkPage = sRunScript.substring(0, sRunScript.indexOf("?"));
	                String sLinkData = sRunScript.substring(sRunScript.indexOf("?") + 1);
	                sbMenuNodeData.append(sLinkPage);
	                sbMenuNodeData.append("; data:");
	                sbMenuNodeData.append(sLinkData);
	            }
	            else
	            {
	                sbMenuNodeData.append(sRunScript);
	            }
	            sbMenuNodeData.append("; hint:");
	            String sNodeHint = tLDMenuSchema.getNodeDescription();
	            if (sNodeHint == null || sNodeHint.trim().equals(""))
	            {
	                sbMenuNodeData.append(tLDMenuSchema.getNodeName());
	            }
	            else
	            {
	                sbMenuNodeData.append(sNodeHint);
	            }
	            sbMenuNodeData.append("; method:showStation('");
	            sbMenuNodeData.append(tLDMenuSchema.getNodeCode());
	            sbMenuNodeData.append("');\";");
	            tLDMenuSchema = null;
	        }
	        tResult = sbMenuNodeData.toString();
	      
	        //我的收藏夹
	      //  tMenuNode1 = "text:我的收藏夹; url:../whatsnew.xml; hint:我的收藏夹; ";
	      //  tMenuNode3 = "text:定制我的收藏夹; url:../whatsnew.xml; hint:我的收藏夹;"
	    }
		  return tResult;
		 
	}
	public String getFavoriteTop(String cUserCode)
	{
		StringBuffer tSBql = new StringBuffer(256);
		String tResult = "";
		// 首先是查询这个用户所在的菜单组，在查询这个用户在收藏夹中所拥有的节点
		tSBql.append("select * from ldmenu where nodecode in (select a.nodecode from ldmenugrptomenu a,ldmenushort b where b.operator = '");
		tSBql.append("?cUserCode?");
		tSBql.append("' and a.nodecode = b.nodecode and a.menugrpcode in (select menugrpcode from ldusertomenugrp where usercode = '");
		tSBql.append("?cUserCode?");
		tSBql.append("')) order by nodeorder");
		// logger.debug(tSBql.toString());
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSBql.toString());
		sqlbv1.put("cUserCode", cUserCode);

		LDMenuDB tLDMenuDB = new LDMenuDB();
		LDMenuSet tLDMenuSet = tLDMenuDB.executeQuery(sqlbv1);

		//
		if (tLDMenuSet != null && tLDMenuSet.size() > 0)
	    {
	        StringBuffer sbMenuNodeData = new StringBuffer(256);
	        for (int i = 1; i <= tLDMenuSet.size(); i++)
	        {
	            LDMenuSchema tLDMenuSchema = new LDMenuSchema();
	            tLDMenuSchema = tLDMenuSet.get(i);
	            sbMenuNodeData.append(tLDMenuSchema.getNodeName()+","+tLDMenuSchema.getRunScript()+"|");
	           
	        }
	        tResult = sbMenuNodeData.toString();
	      
	        //我的收藏夹
	      //  tMenuNode1 = "text:我的收藏夹; url:../whatsnew.xml; hint:我的收藏夹; ";
	      //  tMenuNode3 = "text:定制我的收藏夹; url:../whatsnew.xml; hint:我的收藏夹;"
	    }
		  return tResult;
		 
	}

	/**
	 * 子菜单递归生成函数
	 * 
	 * @param strNode
	 *            String[][] 菜单节点二维数组
	 * @param intIndex
	 *            int 第一个子节点可能的位子
	 * @param ChildCount
	 *            String 菜单节点拥有的子节点总数
	 * @param strMenu
	 *            String 子节点菜单前缀
	 * @param nodeCount
	 *            int 全局菜单节点总数
	 * @param childNum
	 *            int[]
	 * @return String
	 */
	public String getMenuChild(String[][] strNode, int intIndex,
			String ChildCount, String strMenu, int nodeCount, int[] childNum) {
		// 菜单节点拥有的实际子节点数
		childNum[0] = 0;

		// String strMenuChildReturn = "";
		StringBuffer strMenuChildReturn = new StringBuffer(256);
		StringBuffer strMenus = null;

		int k = intIndex;
		String strParent = strNode[intIndex][0]; // 取得当前节点的父节点

		Integer tInteger = new Integer(ChildCount); // 转化父节点的字节点总数
		int ForCount = tInteger.intValue();

		for (int y = 1; y <= ForCount; y++) {
			if (intIndex >= nodeCount) {
				break;
			}

			// 判定是否当前节点为父节点，如果是则递归调用函数getMenuChild
			if (strNode[intIndex][3].compareTo("0") != 0) {

				int[] myChildNum = new int[1];

				if (strNode[intIndex + 1][0].compareTo(strNode[intIndex][1]) == 0) {
					int tempid = childNum[0] + 1;

					// String strMenus = strMenu + tempid + "_";
					// //递归层次，0层：menu1；1层:menu1_1；2层：menu1_1_1；类推
					strMenus = new StringBuffer(256);
					strMenus.append(strMenu);
					strMenus.append(tempid);
					strMenus.append("_");

					String temp = getMenuChild(strNode, intIndex + 1,
							strNode[intIndex][3], strMenus.toString(),
							nodeCount, myChildNum);

					// myChildNum不为0表示本菜单节点是有效的
					if (myChildNum[0] != 0) {
						childNum[0]++;

						strMenuChildReturn.append(strMenu);
						strMenuChildReturn.append(childNum[0]);
						strMenuChildReturn.append("=new Array('");
						strMenuChildReturn.append(strNode[intIndex][2]);
						strMenuChildReturn.append("','','',");
						strMenuChildReturn.append(myChildNum[0]);
						strMenuChildReturn
								.append(",MenuHeight,120,'','','','','','',-1,1,-1,'','");
						strMenuChildReturn.append(strNode[intIndex][1]);
						strMenuChildReturn.append("');");
						// strMenuChildReturn += strMenu + childNum[0] +
						// "=new Array('" +
						// strNode[intIndex][2] + "','',''," +
						// myChildNum[0] +
						// ",MenuHeight,120,'','','','','','',-1,1,-1,'','" +
						// strNode[intIndex][1] + "');";

						strMenuChildReturn.append(temp);
						// strMenuChildReturn = strMenuChildReturn + temp;
					}

					if (intIndex == nodeCount) {
						break;
					}
				}
			} else {
				childNum[0]++;

				strMenuChildReturn.append(strMenu);
				strMenuChildReturn.append(childNum[0]);
				// strMenuChildReturn = strMenuChildReturn + strMenu +
				// childNum[0];
				// 判定是否有相应连接，并相应改变颜色
				// 由于该数组的选取，导致空变量会存放null字符串
				if ((strNode[intIndex][4].compareTo("null") == 0)
						|| (strNode[intIndex][4].compareTo("") == 0)
						|| (strNode[intIndex][4] == null)) {
					strMenuChildReturn.append("=new Array('");
					strMenuChildReturn.append(strNode[intIndex][2]);
					strMenuChildReturn.append("','','',");
					strMenuChildReturn.append(strNode[intIndex][3]);
					strMenuChildReturn
							.append(",MenuHeight,120,'','','','','','',-1,1,-1,'','");
					strMenuChildReturn.append(strNode[intIndex][1]);
					strMenuChildReturn.append("');");

					// strMenuChildReturn = strMenuChildReturn + "=new Array('"
					// +
					// strNode[intIndex][2] + "','',''," +
					// strNode[intIndex][3] +
					// ",MenuHeight,120,'','','','','','',-1,1,-1,'','" +
					// strNode[intIndex][1] + "');";
				} else {
					strMenuChildReturn.append("=new Array('");
					strMenuChildReturn.append(strNode[intIndex][2]);
					strMenuChildReturn.append("','");
					strMenuChildReturn.append(strNode[intIndex][4]);
					strMenuChildReturn.append("','',");
					strMenuChildReturn.append(strNode[intIndex][3]);
					strMenuChildReturn
							.append(",MenuHeight,120,'','','','','','',-1,1,-1,'','");
					strMenuChildReturn.append(strNode[intIndex][1]);
					strMenuChildReturn.append("');");

					// strMenuChildReturn = strMenuChildReturn + "=new Array('"
					// +
					// strNode[intIndex][2] + "','" +
					// strNode[intIndex][4] + "',''," +
					// strNode[intIndex][3] +
					// ",MenuHeight,120,'','','','','','',-1,1,-1,'','" +
					// strNode[intIndex][1] + "');";
				}

			}
			// 将指针指到父节点为strParent的下一个数据

			k = nodeCount;
			if (intIndex + 1 < nodeCount) {
				for (int i = intIndex + 1; i < nodeCount; i++) {
					if (strNode[i][0].compareTo(strParent) == 0) {
						k = i; // 一旦找到，立即退出循环
						break;
					}
				}
			}

			intIndex = k; // 并将该指针数据赋给intIndex

		}
		return strMenuChildReturn.toString();
	}

	/**
	 * 获得节点
	 * 
	 * @param menuID
	 *            int 这个节点具有的显示菜单的编号
	 * @param strNode
	 *            String[][] 菜单节点二维数组
	 * @param arrayOffset
	 *            int
	 * @return String
	 */
	public String advanced_getMenu(int menuID, String[][] strNode,
			int arrayOffset) {
		// 此节点在strNode中的位置
		String result = "";
		return result;
	}

	/**
	 * 获取菜单扩展路径
	 * 
	 * @param nodecode
	 *            String
	 * @return String
	 */
	public String getStation(String nodecode) {
		// LDMenuDB tLDMenuDB = new LDMenuDB();
		// LDMenuSet tLDMenuSet = new LDMenuSet();
		// LDMenuSchema tLDMenuSchema = new LDMenuSchema();
		// tLDMenuDB.setNodeCode(nodecode);
		// String tsql = "";
		// tsql = "select * from LDMenu where nodecode = '" +
		// nodecode + "'";
		// tLDMenuSet = tLDMenuDB.executeQuery(tsql);
		// int i, iMax;
		// iMax = tLDMenuSet.size();
		// for (i = 1; i <= iMax; i++)
		// {
		// tLDMenuSchema = tLDMenuSet.get(i);
		// }
		// if (tLDMenuSchema.getParentNodeCode().compareTo("0") != 0)
		// {
		// getStation(tLDMenuSchema.getParentNodeCode());
		// }
		if(nodecode==null || "".equals(nodecode))
			return ls_location.toString();

		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		StringBuffer tSql = new StringBuffer();

		tSql
				.append("select NodeCode,NodeName,ParentNodeCode from LDMenu where NodeCode = '");
		tSql.append(nodecode);
		tSql.append("'");
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSql.toString());
		sqlbv2.put("nodecode", nodecode);

		tSSRS = tExeSQL.execSQL(sqlbv2);

		if (!"".equals(tSSRS.GetText(1, 3))&&tSSRS.GetText(1, 3).compareTo("0") != 0) {
			getStation(tSSRS.GetText(1, 3));
		}

		ls_location.append(tSSRS.GetText(1, 2));
		ls_location.append("-->");
		// ls_location = ls_location + tSSRS.GetText(1, 2) + "-->";
		return ls_location.toString();
	}

	public TransferData getSQL(String sUserCode)
	{
		TransferData tTransferData=new TransferData();
	    String QuerySQL = new String("");
	    if (sUserCode == null || sUserCode.trim().equals(""))
	    {
	        QuerySQL = "select * from LDMenu where 1 = 2 order by NodeOrder asc";
			 tTransferData.setNameAndValue("Expired", "FALSE");
			 //此处外层是当做String处理的，不能将SQLwithBindVariables放入
		     tTransferData.setNameAndValue("QuerySQL", QuerySQL);
	    }
	    else if (sUserCode.trim().equals("001"))
	    {
	        QuerySQL = "select * from LDMenu where 1 = 1 order by NodeOrder asc";
			 tTransferData.setNameAndValue("Expired", "FALSE");
			 //此处外层是当做String处理的，不能将SQLwithBindVariables放入
		     tTransferData.setNameAndValue("QuerySQL", QuerySQL);
	    }
	    else
	    {
	    // zy 2009-04-10 17:34 用户第一次登陆系统，必须提示用户对初始密码进行修改
	    //zy 2009-05-04 10:17 由于定期清理lduserlog，所以需要调整现有逻辑
	     /* String SQL = "select 1 from lduser a where a.usercode = '"+sUserCode+"' and months_between(date'"+mCurrentDate+"',a.modifydate)>=3";
	    	ExeSQL exeSql = new ExeSQL();
				SSRS SSRS = new SSRS();
				SSRS = exeSql.execSQL(SQL);
				
				String SQL2 = "select count(1) from lduserlog a where a.operator = '"+sUserCode+"' and exists(select 1 from lduser where usercode='"+sUserCode+"' and modifydate is not null)";
				SSRS SSRS2 = new SSRS();
				SSRS2 = exeSql.execSQL(SQL2);	
				
				if(!"".equals(SSRS2.GetText(1,1))&& !"1".equals(SSRS2.GetText(1,1)) &&  !"0".equals(SSRS2.GetText(1,1)) && !"1".equals(SSRS.GetText(1,1)) ){  
				logger.debug("========="+SSRS.GetText(1,1)+"===========");
				logger.debug("========="+SSRS2.GetText(1,1)+"===========");
				logger.debug("=========正常使用===========");*/
		     String SQL = "select (case when months_between(to_date('?CurrentDate?','yyyy-mm-dd'),a.modifydate) is not null then months_between(to_date('?CurrentDate?','yyyy-mm-dd'),a.modifydate) else 3 end) from lduser a where a.usercode = '?sUserCode?'";
	    	 SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
	    	 sqlbv5.sql(SQL);
	    	 sqlbv5.put("CurrentDate", PubFun.getCurrentDate());
	    	 sqlbv5.put("sUserCode", sUserCode);
		     ExeSQL exeSql = new ExeSQL();
		     SSRS tSSRS = new SSRS();
		     tSSRS = exeSql.execSQL(sqlbv5);		
				
			 if( Double.parseDouble(tSSRS.GetText(1,1)) < 3 )
			 {  
	               QuerySQL = "select * "
	               +   "from LDMenu "
	               +  "where NodeCode in "
	               +        "(select NodeCode "
	               +           "from LDMenuGrpToMenu "
	               +          "where MenuGrpCode in "
	               +                "(select MenuGrpCode "
	               +                   "from LDMenuGrp "
	               +                  "where MenuGrpCode in "
	               +                        "(select MenuGrpCode "
	               +                           "from LDUserToMenuGrp "
	               +                          "where UserCode = '"+sUserCode+"'))) "
	               +  "order by NodeOrder asc";
				 tTransferData.setNameAndValue("Expired", "FALSE");
				 //此处外层是当做String处理的，不能将SQLwithBindVariables放入
			     tTransferData.setNameAndValue("QuerySQL", QuerySQL);
			   //  tTransferData.setNameAndValue("QuerySQL", sqlbv6);
	         }
			 else 
			 {
				 QuerySQL="select *  from LDMenu where NodeCode = '8935'"; 
			 
				 tTransferData.setNameAndValue("Expired", "TRUE");
				 //此处外层是当做String处理的，不能将SQLwithBindVariables放入
			     tTransferData.setNameAndValue("QuerySQL", QuerySQL);
				// tTransferData.setNameAndValue("QuerySQL", sqlbv7);
			 }
	    }
	    return tTransferData;
	}
	public String getLDMenu(SQLwithBindVariables sql)
	{
	    //查询 LDMenu
		String sMenuNodeData ="";
	    LDMenuDB tLDMenuDB = new LDMenuDB();
	    LDMenuSet tLDMenuSet = new LDMenuSet();
	    try
	    {
	        tLDMenuSet = tLDMenuDB.executeQuery(sql);
	    }
	    catch (Exception ex) {}
	    if (tLDMenuSet != null && tLDMenuSet.size() > 0)
	    {
	        StringBuffer sbMenuNodeData = new StringBuffer(256);
	        for (int i = 1; i <= tLDMenuSet.size(); i++)
	        {
	            LDMenuSchema tLDMenuSchema = new LDMenuSchema();
	            tLDMenuSchema = tLDMenuSet.get(i);
	            sbMenuNodeData.append("\r\n");
	            sbMenuNodeData.append("                ");
	            sbMenuNodeData.append("nodes[\"");
	            String sParentNodeCode = tLDMenuSchema.getParentNodeCode();
	            if (sParentNodeCode == null || sParentNodeCode.trim().equals("") || sParentNodeCode.trim().equals("0"))
	            {
	                sbMenuNodeData.append("1");
	            }
	            else
	            {
	                sbMenuNodeData.append(sParentNodeCode);
	            }
	            sbMenuNodeData.append("_");
	            sbMenuNodeData.append(tLDMenuSchema.getNodeCode());
	            sbMenuNodeData.append("\"] = \"");
	            sbMenuNodeData.append("text:");
	            sbMenuNodeData.append(tLDMenuSchema.getNodeName());
	            sbMenuNodeData.append("; url:");
	            String sRunScript = tLDMenuSchema.getRunScript();
	            if (sRunScript == null || sRunScript.trim().equals(""))
	            { 
	            	//sunsheng modify 2011-05-24 优化首页，显示用户权限菜单页面  
	                //sbMenuNodeData.append("../whatsnew.xml");
	            	  sbMenuNodeData.append("../usermission/UserMission.jsp");
	            }
	            else if (sRunScript.indexOf("?") != -1)
	            {
	                String sLinkPage = sRunScript.substring(0, sRunScript.indexOf("?"));
	                String sLinkData = sRunScript.substring(sRunScript.indexOf("?") + 1);
	                sbMenuNodeData.append(sLinkPage);
	                sbMenuNodeData.append("; data:");
	                sbMenuNodeData.append(sLinkData);
	            }
	            else
	            {
	                sbMenuNodeData.append(sRunScript);
	            }
	            sbMenuNodeData.append("; hint:");
	            String sNodeHint = tLDMenuSchema.getNodeDescription();
	            if (sNodeHint == null || sNodeHint.trim().equals(""))
	            {
	                sbMenuNodeData.append(tLDMenuSchema.getNodeName());
	            }
	            else
	            {
	                sbMenuNodeData.append(sNodeHint);
	            }
	            sbMenuNodeData.append("; method:showStation('");
	            sbMenuNodeData.append(tLDMenuSchema.getNodeCode());
	            sbMenuNodeData.append("');\";");
	            tLDMenuSchema = null;
	        }
	        sMenuNodeData = sbMenuNodeData.toString();
	      
	    }		
	    return sMenuNodeData;
	}	
	public static void main(String[] args) {
		// MenuShow a = new MenuShow();
		// a.getStation("1003");
		// int[] menuCount = new int[1];
		// menuCount[0] = 0;
		// VData tData = new VData();
		// LDUserSchema tLDUserSchema = new LDUserSchema();
		// LDMenuQueryUI tLDMenuQueryUI = new LDMenuQueryUI();
		// String userCode = "O3";
		// tLDUserSchema.setUserCode(userCode);
		// tData.add(tLDUserSchema);
		// tLDMenuQueryUI.submitData(tData, "query");
		// tData = tLDMenuQueryUI.getResult();
		// int node_count = tLDMenuQueryUI.getResultNum();
		// String[][] node = new String[node_count][5];
		// String tStr = "";
		// tStr = tLDMenuQueryUI.getResultStr();
		// String sl = tStr;
		// sl += SysConst.RECORDSPLITER;
		// //根据排序正确的字符串给数组重新赋值
		// int i = 0;
		// for (i = 0; i < node_count; i++)
		// {
		// String str = StrTool.decodeStr(sl, SysConst.RECORDSPLITER, i + 1);
		// for (int j = 0; j < 5; j++)
		// {
		// str += "|";
		// node[i][0] = StrTool.decodeStr(str, SysConst.PACKAGESPILTER, 1);
		// node[i][1] = StrTool.decodeStr(str, SysConst.PACKAGESPILTER, 2);
		// node[i][2] = StrTool.decodeStr(str, SysConst.PACKAGESPILTER, 3);
		// node[i][3] = StrTool.decodeStr(str, SysConst.PACKAGESPILTER, 4);
		// node[i][4] = StrTool.decodeStr(str, SysConst.PACKAGESPILTER, 5);
		// }
		// }
		// //调用生成菜单函数
		// logger.debug("menuCount before getMenu");
		// logger.debug(menuCount[0]);
		// MenuShow tmenuShow = new MenuShow();
		// logger.debug("nodecount:" + node_count);
		// logger.debug("menuCount" + menuCount);
		// String menushowstr = tmenuShow.getMenu(node, 0, node_count,
		// menuCount);
		// logger.debug("menuCount after getMenu");
		// logger.debug(menuCount[0]);
		// int totalMenu = menuCount[0] + 3;
		// logger.debug("complete getMenu");
	}
	
}
