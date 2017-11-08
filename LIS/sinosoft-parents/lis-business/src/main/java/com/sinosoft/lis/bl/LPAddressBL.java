/*
 * <p>ClassName: LPGrpBL </p>
 * <p>Description: LPGrpBLSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;

public class LPAddressBL extends LPAddressSchema {
private static Logger logger = Logger.getLogger(LPAddressBL.class);

	// @Constructor
	public CErrors mErrors = new CErrors(); // 错误信息

	public LPAddressBL() {
	}

	public void setDefaultFields() {
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	// 查询投保险人变动信息
	public boolean queryLPAddress(LPAddressSchema aLPAddressSchema) {
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		LPPersonSet aLPPersonSet = new LPPersonSet();

		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		LCAddressSet tLCAddressSet = new LCAddressSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的其他保全项目更新后得LPGrp表
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		aLPEdorItemSchema.setEdorNo(aLPAddressSchema.getEdorNo());
		// aLPGrpEdorItemSchema.setEdorType(aLPGrpAddressSchema.getEdorType());
		// aLPGrpEdorItemSchema.setGrpContNo(aLPGrpAddressSchema.getGrpContNo());
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		sql = "select * from LPEdorItem where EdorNo='"
				+ "?EdorNo?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("EdorNo", aLPEdorItemSchema.getEdorNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv1);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPAddressDB tLPAddressDB = new LPAddressDB();

			tLPAddressSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPAddressSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPAddressSchema.setCustomerNo(aLPAddressSchema.getCustomerNo());
			tLPAddressSchema.setAddressNo(aLPAddressSchema.getAddressNo());
			// logger.debug(tLPGrpEdorItemSchema.getEdorType());
			// tLPGrpAddressSchema.setCustomerNo(aLPGrpAddressSchema.getCustomerNo());

			tLPAddressDB.setSchema(tLPAddressSchema);
			if (!tLPAddressDB.getInfo()) {
				continue;
			} else {
				tLPAddressDB.setEdorNo(aLPAddressSchema.getEdorNo());
				tLPAddressDB.setEdorType(aLPAddressSchema.getEdorType());
				this.setSchema(tLPAddressDB.getSchema());
				return true;
			}

		}

		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select a.* from LPEdorItem a, LPAddress b where b.addressno='"
				+ "?addressno?"
				+ "' and a.edorno=b.edorno and a.EdorState='2' order by a.MakeDate desc,a.MakeTime desc";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("addressno", aLPAddressSchema.getAddressNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv2);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPAddressDB tLPAddressDB = new LPAddressDB();

			tLPAddressSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPAddressSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPAddressSchema.setCustomerNo(aLPAddressSchema.getCustomerNo());
			tLPAddressSchema.setAddressNo(aLPAddressSchema.getAddressNo());
			tLPAddressDB.setSchema(tLPAddressSchema);
			if (!tLPAddressDB.getInfo()) {
				continue;
			} else {
				tLPAddressDB.setEdorNo(aLPAddressSchema.getEdorNo());
				tLPAddressDB.setEdorType(aLPAddressSchema.getEdorType());
				this.setSchema(tLPAddressDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCAddressDB tLCAddressDB = new LCAddressDB();
		tLCAddressDB.setCustomerNo(aLPAddressSchema.getCustomerNo());
		tLCAddressDB.setAddressNo(aLPAddressSchema.getAddressNo());

		if (tLCAddressDB.getInfo()) {
			Reflections tReflections = new Reflections();
			tLCAddressSchema = tLCAddressDB.getSchema();
			tReflections.transFields(tLPAddressSchema, tLCAddressSchema);

			tLPAddressSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPAddressSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			this.setSchema(tLPAddressSchema);
			return true;

		} else {
			CError.buildErr(this, "查询团体地址信息错误！");
			return false;
		}
	}

	// 查询上次保全投保人资料信息(Added by Nicholas)
	public boolean queryLastLPAddress(LPEdorItemSchema aLPEdorItemSchema,
			LPAddressSchema aLPAddressSchema) {
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		LPAddressSet aLPAddressSet = new LPAddressSet();

		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		LCAddressSet tLCAddressSet = new LCAddressSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		LPAddressDB tLPAddressDB = null;

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找最近申请的保单批改信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);

		sql = "select * from LPEdorItem where ContNo='"
				+ "?ContNo?"
				+ "' and edorstate <>'0' and MakeDate<='"
				+ "?MakeDate?" + "' and MakeTime<'"
				+ "?MakeTime?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("ContNo", aLPEdorItemSchema.getContNo());
		sqlbv3.put("MakeDate", aLPEdorItemSchema.getMakeDate());
		sqlbv3.put("MakeTime", aLPEdorItemSchema.getMakeTime());
		// logger.debug(sql);

		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			tLPAddressDB = new LPAddressDB();

			tLPAddressSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPAddressSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPAddressSchema.setCustomerNo(aLPAddressSchema.getCustomerNo());
			tLPAddressSchema.setAddressNo(aLPAddressSchema.getAddressNo());
			// logger.debug(tLPEdorItemSchema.getEdorType());

			tLPAddressDB.setSchema(tLPAddressSchema);
			if (!tLPAddressDB.getInfo()) {
				continue;
			} else {
				tLPAddressDB.setEdorNo(aLPAddressSchema.getEdorNo());
				tLPAddressDB.setEdorType(aLPAddressSchema.getEdorType());
				this.setSchema(tLPAddressDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCAddressDB tLCAddressDB = new LCAddressDB();
		tLCAddressDB.setCustomerNo(aLPAddressSchema.getCustomerNo());
		tLCAddressDB.setAddressNo(aLPAddressSchema.getAddressNo());

		if (!tLCAddressDB.getInfo()) {
			return false;
		}
		tLCAddressSchema.setSchema(tLCAddressDB.getSchema());
		// 转换Schema
		Reflections tReflections = new Reflections();
		tReflections.transFields(tLPAddressSchema, tLCAddressSchema);
		tLPAddressSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
		tLPAddressSchema.setEdorType(aLPEdorItemSchema.getEdorType());
		this.setSchema(tLPAddressSchema);
		return true;
	}

	/**
	 * <p>
	 * Description: 获得最新可用的地址号。 如果地址号需要增加，当且紧当P表里有当前保全项目的数据且此数据的地址号大于C表里相应
	 * 的地址号时，才不增加地址号。如需增加，增加的基数是C表以及P表客户对应最大的地址号。
	 * </p>
	 */
	public String getNewAddressNo(LPAddressSchema aLPAddressSchema) {
		StrTool tStrTool = new StrTool();
		LPAddressDB tLPAddressDB = new LPAddressDB();
		tLPAddressDB.setEdorNo(aLPAddressSchema.getEdorNo());
		tLPAddressDB.setEdorType(aLPAddressSchema.getEdorType());
		tLPAddressDB.setCustomerNo(aLPAddressSchema.getCustomerNo());
		tLPAddressDB.setAddressNo(aLPAddressSchema.getAddressNo());
		if (!tLPAddressDB.getInfo()) {
			// 到C表去查
			LCAddressDB tLCAddressDB = new LCAddressDB();
			tLCAddressDB.setCustomerNo(aLPAddressSchema.getCustomerNo());
			tLCAddressDB.setAddressNo(aLPAddressSchema.getAddressNo());
			if (tLCAddressDB.getInfo()) {
				if (tStrTool.compareString(tLCAddressDB.getPostalAddress(),
						aLPAddressSchema.getPostalAddress())) {
					return String.valueOf(aLPAddressSchema.getAddressNo());
				}
			}
		} else {
			if (tStrTool.compareString(tLPAddressDB.getPostalAddress(),
					aLPAddressSchema.getPostalAddress())) {
				return String.valueOf(aLPAddressSchema.getAddressNo());
			}
		}

		String tSql = "";
		// 先判断新记录是否可以替换P表中旧记录
		tSql = "SELECT (case"
				+ " when exists("
				+ " select 'X'"
				+ " from LPAddress"
				+ " where EdorNo='"
				+ "?EdorNo?"
				+ "'"
				+ " and EdorType='"
				+ "?EdorType?"
				+ "'"
				+ " and CustomerNo='"
				+ "?CustomerNo?"
				+ "'"
				+ " and AddressNo='"
				+ "?AddressNo?"
				+ "'"
				+ " and not exists(select 'X' from LCAddress where CustomerNo='"
				+ "?CustomerNo1?" + "'" + " and AddressNo='"
				+ "?AddressNo1?" + "'))" + " then '0'"
				+ " else '1'" + " end)" + " FROM dual";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("EdorNo", aLPAddressSchema.getEdorNo());
		sqlbv4.put("EdorType", aLPAddressSchema.getEdorType());
		sqlbv4.put("CustomerNo", aLPAddressSchema.getCustomerNo());
		sqlbv4.put("AddressNo", aLPAddressSchema.getAddressNo());
		sqlbv4.put("CustomerNo1", aLPAddressSchema.getAddressNo());
		sqlbv4.put("AddressNo1", aLPAddressSchema.getAddressNo());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv4);
		String addFlag = tSSRS.GetText(1, 1);
		// 查询C表和P表中最大的地址号
		/*"为select 的 from中子查询增加别名，别名请结合sql情况起名。例如：
		Oracle：select * from (select * from ldsysvar);
		改造为：select * from (select * from ldsysvar) a;"
*/
		tSql = "SELECT max(X)"
				+ " FROM (select (case when max(AddressNo) is not null then max(AddressNo) else 0 end) as X from LPAddress where CustomerNo='"
				+ "?CustomerNo2?"
				+ "'"
				+ " union select (case when max(AddressNo) is not null then max(AddressNo) else 0 end)  as X from LCAddress where CustomerNo='"
				+ "?CustomerNo3?" + "') a";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("CustomerNo2", aLPAddressSchema.getCustomerNo());
		sqlbv5.put("CustomerNo3", aLPAddressSchema.getCustomerNo());
		tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv5);
		int mAN = Integer.parseInt(tSSRS.GetText(1, 1));
		// if (mAN == 0)
		// {
		// return String.valueOf(aLPAddressSchema.getAddressNo());
		// }
		return String.valueOf(mAN + Integer.parseInt(addFlag));
	}
}
