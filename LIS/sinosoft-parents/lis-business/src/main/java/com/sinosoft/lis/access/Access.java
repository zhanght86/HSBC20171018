/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.access;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.bq.UserEdorPopedomBL;
import com.sinosoft.lis.db.LDMenuDB;
import com.sinosoft.lis.db.LDPageAccessDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.LDMenuSet;
import com.sinosoft.lis.vschema.LDPageAccessSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

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

public class Access implements BusinessService{
private static Logger logger = Logger.getLogger(Access.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	
	public Access() {
	}
	public boolean submitData(VData cInputData, String cOperate) {

		TransferData mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mTransferData == null || mTransferData == null) 
		{
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		
		String mUserCode = (String) mTransferData.getValueByName("UserCode");
		String strURL = (String) mTransferData.getValueByName("strURL");
		

		if (!canAccess(mUserCode, strURL))
		{
			// @@错误处理
			return false;
		} 
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * JspUrl字符串处理
	 * 
	 * @param jspPage
	 *            String
	 * @return String
	 */
	public String getShortJspPage(String jspPage) {
		int lastIndex = jspPage.lastIndexOf('\\');
		int lastIndex2 = jspPage.lastIndexOf('/');
		if (lastIndex < lastIndex2)
			lastIndex = lastIndex2;
		if (lastIndex == -1 || lastIndex == jspPage.length() - 1)
			return jspPage;
		return jspPage.substring(lastIndex + 1);
	}

	/**
	 * 校验用户是否有权限进入此节点
	 * 
	 * @param userCode
	 *            String
	 * @param jspPage
	 *            String
	 * @return boolean
	 */
	public boolean canAccess(String userCode, String jspPage) {
		return true;
		// logger.debug("canAceess check");
		
		//暂时修改 start 
		/*if(userCode.equals("001")||userCode.equals("nb001"))
			return true;
		if (jspPage == null)
			return false;

		// 首先查询出所有的叶子菜单节点号
		LDMenuSet tLDMenuSet = new LDMenuSet();
		LDMenuDB tLDMenuDB = new LDMenuDB();
		// String sqlStr = "select * from LDMenu where NodeCode in ( select
		// NodeCode from LDMenuGrpToMenu where MenuGrpCode in ( select
		// MenuGrpCode from LDMenuGrp where MenuGrpCode in (select MenuGrpCode
		// from LDUserToMenuGrp where UserCode = '" +
		// userCode + "') ) ) and childflag = '0' order by nodeorder";
		
		StringBuffer tSBql = new StringBuffer();
		tSBql
				.append("select * from LDMenu where NodeCode in ( select NodeCode from LDMenuGrpToMenu where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp where MenuGrpCode in (select MenuGrpCode from LDUserToMenuGrp where UserCode = '");
		tSBql.toString();
		tSBql.append(userCode);
		
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(tSBql.toString());
		tSBql.append("') ) ) and childflag = '0' order by nodeorder");

		tLDMenuSet = tLDMenuDB.executeQuery(sqlbv);
		if (tLDMenuSet.size() == 0)
			return false;

		Connection conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			return false;
		}
		try {
			LDPageAccessDB tLDPageAccessDB = new LDPageAccessDB(conn);
			for (int i = 1; i < tLDMenuSet.size(); i++) {
				String leafMenuCode = tLDMenuSet.get(i).getNodeCode();
				// sqlStr = "select * from ldPageAccess where nodecode = '" +
				// leafMenuCode +
				// "'";
				// 使用StringBuffer的坏处在于需要重新初始化一下
				tSBql.setLength(0);
				tSBql.append("select * from ldPageAccess where nodecode = '");
				tSBql.append("?leafMenuCode?");
				tSBql.append("'");

				LDPageAccessSet tLDPageAccessSet = new LDPageAccessSet();
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(tSBql.toString());
				sqlbv1.put("leafMenuCode", leafMenuCode);
				tLDPageAccessSet = tLDPageAccessDB.executeQuery(sqlbv1);

				for (int j = 1; j <= tLDPageAccessSet.size(); j++) {
					String runscript = tLDPageAccessSet.get(j).getRunScript();
					// 获取jsp文件名
					String shortJspPage = getShortJspPage(runscript);
					if (jspPage.indexOf(shortJspPage) != -1) {
						conn.close();
						// logger.debug("can access!");
						return true;
					}
				}
			}
			conn.close();
			// logger.debug("illegal access!");
			return true;
		} catch (Exception e) {
			try {
				logger.debug(e.toString());
				conn.close();
			} catch (Exception ex) {
			}
			return true;
		}*/
		//end
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		// Access tAccess = new Access();
		// if (tAccess.canAccess("001",
		// "http://localhost:8900/ui/userMan/UserAddInput.jsp"))
		// logger.debug("access success");
		// else
		// logger.debug("access deny");
	}
}
