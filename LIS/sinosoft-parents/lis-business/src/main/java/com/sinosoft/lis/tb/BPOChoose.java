package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

public class BPOChoose {
private static Logger logger = Logger.getLogger(BPOChoose.class);
	private static Map cacheBpo = new TreeMap();
	private static HashSet cacheFlag = new HashSet();
	static {
		init();
	}

	private static void init() {
		String sql = "select trim(managecom),trim(bpoid),trim(ALLOTTYPE) from BPOAllotRate";
		logger.debug("bpochoose init managecom sql:" + sql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(sql);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			cacheBpo.put(tSSRS.GetText(i, 3) + "-" + tSSRS.GetText(i, 1), tSSRS.GetText(i, 2));
		}
		sql = "select code1 From ldcode1 where codetype='scaninput' and code='proposal'";
		logger.debug("bpochoose init flag sql:" + sql);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);//没有sqlbv.put()语句
		tSSRS = tExeSQL.execSQL(sqlbv1);
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			cacheFlag.add("TB-" + tSSRS.GetText(i, 1));
		}
		cacheFlag.add("OF-31"); // 银行转帐授权书
		cacheFlag.add("OF-32"); // 首期保费暂收收据
		cacheFlag.add("OF-20"); // 银行划款协议书调整编码为532002开头
		logger.debug("bpochoose init done");
	}

	public static void refresh() {
		init();
	}

	public static String chooseBPO(ES_DOC_MAINSchema tES_DOC_MAINSchema) {
		logger.debug("doc:doccode=" + tES_DOC_MAINSchema.getDocCode()
				+ ",managecom=" + tES_DOC_MAINSchema.getManageCom());
		if (tES_DOC_MAINSchema.getInputState() != null
				&& tES_DOC_MAINSchema.getInputState().equals("1")) {
			logger.debug("bpo doccode=" + tES_DOC_MAINSchema.getDocCode()
					+ " has been input");
			return "";
		}
		for (Iterator iterator = cacheBpo.keySet().iterator(); iterator
				.hasNext();) {
			String bpoManageCom = (String) iterator.next();
			String[] ss = bpoManageCom.split("-");
			if (tES_DOC_MAINSchema.getBussType().equals(ss[0])
					&& tES_DOC_MAINSchema.getManageCom().startsWith(ss[1])) {
				String flag = tES_DOC_MAINSchema.getDocCode().substring(2, 4);
				if (cacheFlag.contains(tES_DOC_MAINSchema.getBussType() + "-"
						+ flag)) {
					logger.debug("bpo doccode="
							+ tES_DOC_MAINSchema.getDocCode() + " send to "
							+ cacheBpo.get(bpoManageCom));
					return (String) cacheBpo.get(bpoManageCom);
				} else
					break;
			}
		}
		logger.debug("bpo doccode=" + tES_DOC_MAINSchema.getDocCode()
				+ " not pass");
		return "";
	}
}
