/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:业务系统的公共业务处理函数 该类包含所有业务中有关保单状态的函数。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Fanym
 * @version 1.0
 */
public class PolState {
private static Logger logger = Logger.getLogger(PolState.class);
	public CErrors mErrors = new CErrors();

	// private VData mResult = new VData();

	public PolState() {
	}

	public boolean SetPolEnd() {
		/*
		 * 置保单为“终止”状态（状态已处于“终止”或“暂停”的除外）： 1. 对于可以自动续保且选择自动续保的，在宽限期67天的次日终止 2.
		 * 对于可以自动续保但没有选择自动续保的，在保险责任终止次日终止 3. 对于不可以自动续保的，在保险责任终止次日终止 4.
		 * 对于状态处于“无效”的，在“交至日期+2年”后终止
		 */
		/*"concat只使用两个参数，改造样例如下：
		Oracle：select 'a'||'b'||'c'||'d' from dual
		改造为：select concat(concat(concat('a','b'),'c'),'d') from dual"
*/
	/*	"Oracle：select decode(count(prtno),0,'0','1') from laagentascription 
		改造为：select (case count(prtno) when 0 then '0' else '1' end)from laagentascription "
*/
		String sql = "UPDATE lcpol"
				+ " SET polstate = concat('0303' , SUBSTR(polstate, 1, 4))"
				+ " WHERE polno IN ("
				+ " SELECT polno from (SELECT polno"
				+ " FROM lcpol"
				+ " WHERE grppolno = '00000000000000000000'"
				+ "  AND SUBSTR(polstate, 1, 2) NOT IN ('03', '04')"
				+ "  AND (   (    EXISTS (SELECT riskcode FROM lmriskrnew"
				+ "                         WHERE riskcode = lcpol.riskcode)"
				+ "           AND now() > enddate + (case rnewflag when -1 then '67' else '0' end))"
				+ "        OR (    NOT EXISTS (SELECT riskcode FROM lmriskrnew"
				+ "                             WHERE riskcode = lcpol.riskcode)"
				+ "            AND now() > enddate)"
				+ "        OR (    polstate LIKE '02%'"
				+ "            AND now() > ADD_MONTHS (PayToDate, 24))"
				+ "       )) t)";
		logger.debug(sql);

		ExeSQL tExeSQL = new ExeSQL();
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql);
		if (!tExeSQL.execUpdateSQL(sqlbv)) {
			CError.buildErr(this, tExeSQL.mErrors.getFirstError(), mErrors);

			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		// PolState tPolState = new PolState();
		// if (!tPolState.SetPolEnd())
		// {
		// logger.debug(tPolState.mErrors.getFirstError());
		// }
	}
}
