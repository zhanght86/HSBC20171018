

/**
 * <p>Title: PDDutyGetAlive</p>
 * <p>Description: 责任给付生存</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-16
 */

package com.sinosoft.productdef;

import com.sinosoft.utility.*;

public class PDGetMaxNo {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	
	public PDGetMaxNo() {
	}

	public String getMaxNo(String noType, String noLimit) throws Exception
	{
		String result = null;
		
		java.sql.Connection conn = DBConnPool.getConnection();
		try
		{
			if (conn == null)
			{
				System.out.println("getNewId() : fail to get db connection");
				this.mErrors.addOneError("创建数据库连接失败");
			}
			
			conn.setAutoCommit(false);
			
			ExeSQL tExec = new ExeSQL(conn);
			String maxno = tExec.getOneValue("select maxno from ldmaxno where notype='" + noType + "' and nolimit = '" + noLimit + "' for update");
			
			if(tExec.mErrors.needDealError())
			{
				this.mErrors.addOneError("查询ldmaxno出错");
				conn.rollback();
				conn.close();
			}
			
			if(maxno == null || maxno.equals(""))
			{
				maxno = "1";
				
				tExec.execUpdateSQL("insert into ldmaxno values('" + noType + "','" + noLimit + "',1)");
				
				if(tExec.mErrors.needDealError())
				{
					this.mErrors.addOneError("操作ldmaxno出错");
					conn.rollback();
					conn.close();
				}
			}
			
			int newMaxNo = Integer.parseInt(maxno) + 1;
			
			String sql = "update ldmaxno set maxno = '" + newMaxNo + "' where notype='" + noType + "' and nolimit = '" + noLimit + "'";
			tExec.execUpdateSQL(sql);
			
			if(tExec.mErrors.needDealError())
			{
				this.mErrors.addOneError("更新maxno出错");
				conn.rollback();
				conn.close();
			}
			
			conn.commit();
			conn.close();
			
			result = maxno; 
		}
		catch(Exception ex)
		{
			try
			{
				conn.rollback();
				conn.close();
				this.mErrors.addOneError("取最大号失败");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				this.mErrors.addOneError("取最大号失败,回滚失败");
			}
		}
		
		return result;
	}

	public static void main(String[] args) {
		
		try
		{
//			System.out.println(new ExeSQL().getOneValue("select * from ldmaxno where notype= 'PD_DutyLibMap' and nolimit = '2' "));
//			System.out.println(new ExeSQL().getOneValue("select * from ldmaxno where notype= 'PD_DutyLibMap' and nolimit = '0' "));
			PDGetMaxNo tPDDutyGetAliveBL = new PDGetMaxNo();
			String maxNo = tPDDutyGetAliveBL.getMaxNo("PD_DutyLibMap", "1");
			System.out.println(maxNo);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
