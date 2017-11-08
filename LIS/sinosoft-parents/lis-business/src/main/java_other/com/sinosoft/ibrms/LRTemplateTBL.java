package com.sinosoft.ibrms;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LRRuleDataDB;
import com.sinosoft.lis.db.LRTemplateDB;
import com.sinosoft.lis.db.LRTemplateTDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LRRuleDataBSchema;
import com.sinosoft.lis.schema.LRRuleDataSchema;
import com.sinosoft.lis.schema.LRTemplateBSchema;
import com.sinosoft.lis.schema.LRTemplateSchema;
import com.sinosoft.lis.schema.LRTemplateTSchema;
import com.sinosoft.lis.vschema.LRTemplateBSet;
import com.sinosoft.lis.vschema.LRTemplateSet;
import com.sinosoft.lis.vschema.LRTemplateTSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: AgentSystem
 * </p>
 * 
 * @author
 * @version 6.0
 */
public class LRTemplateTBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData insInputData = new VData();
	private final int MaxVersion = 4; // 备份表的最大版本号的位数 例如：DTB****0001

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();
	private Reflections tReflections = new Reflections();

	/** 数据操作字符串 */
	private String mOperate;

	private LRTemplateTSet mLRTemplateTSet = new LRTemplateTSet();
	private LRTemplateSet mLRTemplateSet = new LRTemplateSet();
	private LRTemplateBSet mLRTemplateBSet = new LRTemplateBSet();

	private static Logger logger = Logger.getLogger(LRTemplateTBL.class);

	public void setOperate(String tOperate) {
		mOperate = tOperate;
	}

	public LRTemplateTBL() {
	}

	public static void main(String[] args) {

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("Begin LRTemplateTBL.submitData.........");
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		// 准备往InputData的数据
		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit insPubSubmit = new PubSubmit();
		logger.debug("Start updPubSubmit Submit...");
		if (!insPubSubmit.submitData(insInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(insPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAAgentBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	public boolean getInputData(VData cInputData) {
		// 全局变量
		// logger.debug("Begin LRTemplateTBL.getInputData.........");
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		this.mLRTemplateTSet.set((LRTemplateTSet) cInputData
				.getObjectByObjectName("LRTemplateTSet", 0));
		this.mLRTemplateSet.set((LRTemplateSet) cInputData
				.getObjectByObjectName("LRTemplateSet", 0));
		this.mLRTemplateBSet.set((LRTemplateBSet) cInputData
				.getObjectByObjectName("LRTemplateBSet", 0));

		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRTemplateTUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 业务处理主函数
	 * 
	 * @return boolean
	 */
	public boolean dealData() {

		logger.debug("mOperate:" + mOperate);
		if (mOperate.equals("4")) {
			if (!approve())
				return false;
			else
				return true;
		}
		if (mOperate.equals("7")) {
			if (!deploy())
				return false;
			else
				return true;
		}
		// tongmeng 2011-02-16 add
		// 增加规则修订功能
		if (mOperate.equals("z")) {
			if (!unDeploy())
				return false;
			else
				return true;
		}

		if (mOperate.equals("9")) {
			if (!drop())
				return false;
			else
				return true;
		}
		if (mOperate.equals("2")) {
			if (!unapprove())
				return false;
			else
				return true;
		}
		return true;
	}

	// 规则作废
	private boolean drop() {
		/**
		 * 从正式表迁出现在使用的规则到备份表(分两部分：第一部分：迁移LTRLRTemplate中的数据到LRTemplateB
		 * 第二部分：迁移DT表中的数据到DB
		 */

		LRTemplateDB tLRTemplateDB = new LRTemplateDB();
		LRTemplateSchema tLRTemplateSchema = new LRTemplateSchema();
		LRTemplateBSchema tLRTemplateBSchema = new LRTemplateBSchema();
		String sql;

		// 查询出LRTemplate中的值

		for (int i = 1; i <= mLRTemplateSet.size(); i++) {
			tLRTemplateDB.setId(mLRTemplateSet.get(i).getId());
			if (tLRTemplateDB.getInfo()) {
				tLRTemplateSchema = tLRTemplateDB.getSchema();

			} else {
				return false;
			}
			int version = tLRTemplateSchema.getVersion();

			// 取DT后四位，把DT***表的表名变成DTB***+版本号
			String DTableName = tLRTemplateSchema.getTableName();
			String DBTableName = DTableName.substring(0, 2) + "B"
					+ DTableName.substring(2, DTableName.length())
					+ PubFun.LCh(String.valueOf(version), "0", MaxVersion);

			// 迁移LRTemplate中的数据到LRTemplateB
			this.tReflections
					.transFields(tLRTemplateBSchema, tLRTemplateSchema);
			// 1.从DT表到DB表 替换LTB中 SQLStatemen 中的表名DT为DB
			String SQLStatemen = tLRTemplateSchema.getSQLStatement();
			String RSQLStatemen = SQLStatemen.replaceAll("from DT", "from "
					+ DBTableName);
			logger.debug("TD-->TB:SQLStatemen:======" + SQLStatemen);
			logger.debug("TD-->TB:RSQLStatemen:======" + RSQLStatemen);

			tLRTemplateBSchema.setModifyDate(PubFun.getCurrentDate());
			tLRTemplateBSchema.setModifyTime(PubFun.getCurrentTime());
			tLRTemplateSchema.setState(mOperate);
			tLRTemplateBSchema.setValid("0");
			tLRTemplateBSchema.setVersion(version);
			tLRTemplateBSchema.setState("9");
			tLRTemplateBSchema.setSQLStatement(RSQLStatemen);
			tLRTemplateBSchema.setTableName(DBTableName);
			mMap.put(tLRTemplateBSchema, "INSERT");
			mMap.put(tLRTemplateSchema, "DELETE");

			// 复制DT表的数据到DB表中,并删除DT表

			String sql1 = "create table " + DBTableName + " as select * from "
					+ DTableName;
			SQLwithBindVariables sbv = new SQLwithBindVariables();
			sbv.sql(sql1);
			mMap.put(sbv, "UPDATE");

			// 删除正式表的DT表
			sql = "drop table " + DTableName;
			mMap.put(sql, "DELETE");
		}
		return true;
	}

	// 规则审批 通过
	private boolean approve() {
		logger.debug("Begin LRTemplateTBL.dealData.........");

		LRTemplateTDB tLRTemplateTDB = new LRTemplateTDB();
		LRTemplateTSet tLRTemplateTSet = new LRTemplateTSet();

		for (int i = 1; i <= mLRTemplateTSet.size(); i++) {

			tLRTemplateTDB.setId(mLRTemplateTSet.get(i).getId());
			LRTemplateTSchema tLRTemplateTSchema = new LRTemplateTSchema();
			if (tLRTemplateTDB.getInfo()) {
				tLRTemplateTSchema = tLRTemplateTDB.getSchema();
			}

			else {
				return false;
			}
			tLRTemplateTSchema.setState(mOperate);
			tLRTemplateTSchema.setApprover(mGlobalInput.Operator);
			tLRTemplateTSchema.setModifyDate(PubFun.getCurrentDate());
			tLRTemplateTSchema.setModifyTime(PubFun.getCurrentTime());
			tLRTemplateTSchema.setAuthorDate(PubFun.getCurrentDate());
			tLRTemplateTSchema.setAuthorTime(PubFun.getCurrentTime());
			tLRTemplateTSet.add(tLRTemplateTSchema);
		}
		mMap.put(tLRTemplateTSet, "UPDATE");
		return true;
	}

	// 规则审批 不通过
	private boolean unapprove() {
		logger.debug("Begin LRTemplateTBL.dealData.........");

		LRTemplateTDB tLRTemplateTDB = new LRTemplateTDB();
		LRTemplateTSet tLRTemplateTSet = new LRTemplateTSet();

		for (int i = 1; i <= mLRTemplateTSet.size(); i++) {

			tLRTemplateTDB.setId(mLRTemplateTSet.get(i).getId());
			LRTemplateTSchema tLRTemplateTSchema = new LRTemplateTSchema();
			if (tLRTemplateTDB.getInfo()) {
				tLRTemplateTSchema = tLRTemplateTDB.getSchema();
			}

			else {
				return false;
			}
			tLRTemplateTSchema.setState(mOperate);
			tLRTemplateTSchema.setApprover(mGlobalInput.Operator);
			tLRTemplateTSchema.setModifyDate(PubFun.getCurrentDate());
			tLRTemplateTSchema.setModifyTime(PubFun.getCurrentTime());
			tLRTemplateTSchema.setAuthorDate(PubFun.getCurrentDate());
			tLRTemplateTSchema.setAuthorTime(PubFun.getCurrentTime());
			tLRTemplateTSet.add(tLRTemplateTSchema);
		}
		mMap.put(tLRTemplateTSet, "UPDATE");
		return true;
	}

	// 规则发布

	private boolean deploy() {
		logger.debug("Begin LRTemplateTBL.dealData.........");

		LRTemplateTDB tLRTemplateTDB = new LRTemplateTDB();
		LRTemplateTSchema tLRTemplateTSchema = new LRTemplateTSchema();
		LRTemplateDB tLRTemplateDB = new LRTemplateDB();
		LRTemplateSchema tLRTemplateSchema = new LRTemplateSchema();
		LRTemplateBSchema tLRTemplateBSchema = new LRTemplateBSchema();
		ExeSQL tExeSQL = new ExeSQL();

		for (int i = 1; i <= mLRTemplateTSet.size(); i++) {
			tLRTemplateTDB.setId(mLRTemplateTSet.get(i).getId());
			if (tLRTemplateTDB.getInfo()) {
				tLRTemplateTSchema = tLRTemplateTDB.getSchema();
			}

			else {
				return false;
			}

			/*
			 * 分两种情况：第一种情况：此规则从没发布过 第二种情况：此规则正在使用
			 */

			String DTid = tLRTemplateTSchema.getId();
			tExeSQL = new ExeSQL();
			String sql = "select count(*) from LRTemplate where id= '?id?'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("id", DTid.trim());

			String idNum = tExeSQL.getOneValue(sqlbv);
			if (idNum == null || tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "查询失败！");
				return false;
			} else if (idNum.equals("0")) {

				// 第一种情况：此规则从没发布过 分两部分：第一部分：迁移LRTemplateT中的数据到LRTemplateB
				// 第二部分：迁移DTT表中的数据到DT
				// 替换RSQLStatemen 中的表名DTT为DT
				String DTTTableName = tLRTemplateTSchema.getTableName();
				String SQLStatemen = tLRTemplateTSchema.getSQLStatement();
				String RSQLStatemen = SQLStatemen.replaceAll("from DTT",
						"from DT");
				logger.debug("===RSQLStatemen：=" + RSQLStatemen);
				// 取DT后四位，把DTT***表的表名变成DT***+版本号
				String DTTableName = DTTTableName.replaceAll("DTT", "DT");
				logger.debug("=====DTTableName：=" + DTTableName);

				this.tReflections.transFields(tLRTemplateSchema,
						tLRTemplateTSchema);
				// tLRTemplateSchema.setViewParameter(tLRTemplateTSchema.getViewParameter());
				tLRTemplateSchema.setState(mOperate);
				logger.debug("Operator::" + mGlobalInput.Operator);
				if (mGlobalInput.Operator == null) {
					CError.buildErr(this, "操作员信息传递出错！");
					return false;
				} else {
					tLRTemplateSchema.setDeployer(mGlobalInput.Operator);
				}
				tLRTemplateSchema.setApprover(tLRTemplateTSchema.getApprover());
				tLRTemplateSchema.setModifier(tLRTemplateTSchema.getModifier());
				// tongmeng 2011-01-07 modify
				String tOldTableName = tLRTemplateSchema.getTableName();
				// DTT0932.DTRate
				RSQLStatemen = RSQLStatemen.replaceAll(tOldTableName
						+ ".DTRate", DTTableName + ".DTRate");
				tLRTemplateSchema.setTableName(DTTableName);
				tLRTemplateSchema.setSQLStatement(RSQLStatemen);

				// tongmeng 2011-02-16 modify
				if (tLRTemplateSchema.getApprover() == null
						|| tLRTemplateSchema.getApprover().equals("")) {
					tLRTemplateSchema.setApprover(mGlobalInput.Operator);
				}
				if (tLRTemplateSchema.getAuthorDate() == null
						|| tLRTemplateSchema.getAuthorDate().equals("")) {
					tLRTemplateSchema.setAuthorDate(PubFun.getCurrentDate());
				}

				if (tLRTemplateSchema.getAuthorTime() == null
						|| tLRTemplateSchema.getAuthorTime().equals("")) {
					tLRTemplateSchema.setAuthorTime(PubFun.getCurrentTime());
				}
				tLRTemplateSchema.setModifyDate(PubFun.getCurrentDate());
				tLRTemplateSchema.setModifyTime(PubFun.getCurrentTime());
				tLRTemplateSchema.setDeclDate(PubFun.getCurrentDate());
				tLRTemplateSchema.setDeclTime(PubFun.getCurrentTime());
				mMap.put(tLRTemplateSchema, "INSERT");
				mMap.put(tLRTemplateTSchema, "DELETE");

				// 复制DT表的数据到DB表中
				// tongmeng 2011-02-16 modify
				// tongmeng 2011-05-09 modify
				// 如果使用结果集方式,也不创建表
				if (!DTTableName.toLowerCase().trim().equals("ldsysvar")
						&& !DTTableName.toLowerCase().trim().equals("ruledata")
						&& DTTableName.toLowerCase().trim().indexOf("dt") != -1) {
					sql = "create table " + DTTableName + " as select * from "
							+ DTTTableName;
					SQLwithBindVariables sbv1 = new SQLwithBindVariables();
					sbv1.sql(sql);
					mMap.put(sbv1, "UPDATE");
					sql = "drop table " + DTTTableName;
					SQLwithBindVariables sbv2 = new SQLwithBindVariables();
					sbv2.sql(sql);
					mMap.put(sbv2, "UPDATE");

					// 需要增加主键
					// 查询原有表的主键
					String tSQL_PK = "";
					if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
						tSQL_PK = "select a.constraint_name,a.table_name,a.column_name "
								+ " from user_cons_columns a ,user_constraints b "
								+ " where a.constraint_name=b.constraint_name and b.constraint_type='P' "
								+ " and a.table_name = upper('"
								+ "?DTTTableName?"
								+ "') "
								+ " order by a.position ";
					} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
						//修改错误 先注掉   zhangyingfeng 2016-04-20
//						tSQL_PK = "select a.constraint_name,a.table_name,a.column_name "
//								+ " from information_schema.cons_columns a ,information_schema.constraints b "
//								+ " where a.constraint_name=b.constraint_name and b.constraint_type='P' "
//								+ " and a.table_name = upper('"
//								+ "?DTTTableName?"
//								+ "') "
//								+ " order by a.position ";
						//
					}
					// SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
					// sqlbv1.sql(tSQL_PK);
					// sqlbv1.put("DTTTableName", DTTTableName);
					SQLwithBindVariables sqlbvw = new SQLwithBindVariables();
					sqlbvw.sql(tSQL_PK);
					sqlbvw.put("DTTTableName", DTTTableName);

					String tAlter_PK = "";
					SSRS tPKSSRS = new SSRS();
					tPKSSRS = tExeSQL.execSQL(sqlbvw);
					tAlter_PK = "alter table " + DTTableName
							+ " add constraint PK_" + DTTableName
							+ " primary key  ";
					tAlter_PK = tAlter_PK + " ( ";
					for (int n = 1; n <= tPKSSRS.getMaxRow(); n++) {
						tAlter_PK = tAlter_PK + tPKSSRS.GetText(n, 3);
						if (n != tPKSSRS.getMaxRow()) {
							tAlter_PK = tAlter_PK + " , ";
						}
					}
					tAlter_PK = tAlter_PK + " ) ";
					logger.debug("tAlter_PK:" + tAlter_PK);
					SQLwithBindVariables sbv3 = new SQLwithBindVariables();
					sbv3.sql(tAlter_PK);
					if (tPKSSRS != null && tPKSSRS.getMaxRow() > 0) {
						mMap.put(sbv3, "UPDATE");
					}

					/*
					 * alter table DTT1315 add constraint PK_DTT1315 primary key
					 * (COLUMN0, COLUMN1, COLUMN2)
					 */
					// select a.constraint_name,a.table_name,a.column_name from
					// user_cons_columns a ,user_constraints b
					// where a.constraint_name=b.constraint_name and
					// b.constraint_type='P'
					// and a.table_name = upper('dtt1315')
				}

			}

			// 第二种情况：此规则正在使用

			else {
				String id = tLRTemplateTSchema.getId();
				tLRTemplateDB.setId(id);
				if (tLRTemplateDB.getInfo()) {
					tLRTemplateSchema = tLRTemplateDB.getSchema();
				} else {
					return false;
				}

				/**
				 * 从正式表迁出现在使用的规则到备份表(分两部分：第一部分：迁移LTRLRTemplate中的数据到LRTemplateB
				 * 第二部分：迁移DT表中的数据到DB
				 */

				// 迁移DT表到DB表
				tLRTemplateDB.setId(tLRTemplateTSchema.getId());
				tLRTemplateSchema = tLRTemplateDB.getSchema();

				String DTableName = tLRTemplateSchema.getTableName();
				logger.debug("DTableName:==============" + DTableName);
				String sVersion = PubFun.LCh(
						String.valueOf(tLRTemplateBSchema.getVersion()), "0",
						MaxVersion);
				// 取DT后四位，把DT***表的表名变成DTB***+版本号
				String DBTableName = DTableName.substring(0, 2) + "B"
						+ DTableName.substring(3, DTableName.length())
						+ sVersion;
				String RDTTableName = DTableName.replaceAll("DTT", "DT");
				logger.debug("tablename" + DBTableName);
				// 复制DT表的数据到DB表中

				// 迁移LRTemplate中的数据到LRTemplateB

				this.tReflections.transFields(tLRTemplateBSchema,
						tLRTemplateSchema);
				// 1.从DT表到DB表 替换LTB中 SQLStatemen 中的表名DT为DB
				String SQLStatemen = tLRTemplateSchema.getSQLStatement();
				SQLStatemen = SQLStatemen.replaceAll("from DT", "from DTB");
				tLRTemplateBSchema.setState(mOperate);
				tLRTemplateBSchema.setModifyDate(PubFun.getCurrentDate());
				tLRTemplateBSchema.setModifyTime(PubFun.getCurrentTime());
				tLRTemplateBSchema.setValid("0");
				tLRTemplateBSchema.setTableName(DBTableName);
				tLRTemplateBSchema.setSQLStatement(SQLStatemen);
				mMap.put(tLRTemplateBSchema, "INSERT");
				mMap.put(tLRTemplateSchema, "DELETE");

				if (!RDTTableName.toLowerCase().trim().equals("ldsysvar")
						&& !RDTTableName.toLowerCase().trim()
								.equals("ruledata")
						&& RDTTableName.toLowerCase().trim().indexOf("dt") != -1) {
					String sql1 = "create table " + DBTableName
							+ " as select * from " + RDTTableName;
					SQLwithBindVariables sbv4 = new SQLwithBindVariables();
					sbv4.sql(sql1);
					mMap.put(sbv4, "UPDATE");

					sql = "drop table " + RDTTableName;
					SQLwithBindVariables sbv5 = new SQLwithBindVariables();
					sbv5.sql(sql);
					mMap.put(sbv5, "DELETE");

					// 需要增加主键
					// 查询原有表的主键
					String tSQL_PK = "";
					if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
						tSQL_PK = "select a.constraint_name,a.table_name,a.column_name "
								+ " from user_cons_columns a ,user_constraints b "
								+ " where a.constraint_name=b.constraint_name and b.constraint_type='P' "
								+ " and a.table_name = upper('"
								+ "?RDTTableName?" + "') ";
					} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
						//修改错误 先注掉   zhangyingfeng 2016-04-20
//						tSQL_PK = "select a.constraint_name,a.table_name,a.column_name "
//								+ " from information_schema.cons_columns a ,information_schema.constraints b "
//								+ " where a.constraint_name=b.constraint_name and b.constraint_type='P' "
//								+ " and a.table_name = upper('"
//								+ "?RDTTableName?" + "') ";
						//
					}
					// SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					// sqlbv2.sql(tSQL_PK);
					// sqlbv2.put("RDTTableName", RDTTableName);
					SQLwithBindVariables sqlbve = new SQLwithBindVariables();
					sqlbve.sql(tSQL_PK);
					sqlbve.put("RDTTableName", RDTTableName);
					String tAlter_PK = "";
					SSRS tPKSSRS = new SSRS();
					tPKSSRS = tExeSQL.execSQL(sqlbve);
					tAlter_PK = "alter table " + DBTableName
							+ " add constraint PK_" + DBTableName
							+ " primary key  ";

					tAlter_PK = tAlter_PK + " ( ";
					for (int n = 1; n <= tPKSSRS.getMaxRow(); n++) {
						tAlter_PK = tAlter_PK + tPKSSRS.GetText(n, 3);
						if (n != tPKSSRS.getMaxRow()) {
							tAlter_PK = tAlter_PK + " , ";
						}
					}
					tAlter_PK = tAlter_PK + " ) ";
					logger.debug("tAlter_PK:" + tAlter_PK);
					SQLwithBindVariables sbv6 = new SQLwithBindVariables();
					sbv6.sql(tAlter_PK);
					if (tPKSSRS != null && tPKSSRS.getMaxRow() > 0) {
						mMap.put(sbv6, "UPDATE");
					}

				}

				/**
				 * 从草稿表迁出现在使用的规则到正式表(分两部分：第一部分：迁移LRTemplateT中的数据到LRTemplate
				 * 第二部分：迁移DT表中的数据到DB，并修改表名和sql中的表名
				 */

				String tDTTableName = tLRTemplateTSchema.getTableName();
				logger.debug("tDTTableName:==============" + tDTTableName);
				// 取DTT表名换成DT**
				String ttDTTableName = tDTTableName.replaceAll("DTT", "DT");
				logger.debug("RDTTableName" + ttDTTableName);

				// 迁移数据 从LRTempleteT到LRTemplete

				LRTemplateSchema ttLRTemplateSchema = new LRTemplateSchema();
				this.tReflections.transFields(ttLRTemplateSchema,
						tLRTemplateTSchema);
				// 1.从DT表到DB表 替换LTB中 SQLStatemen 中的表名DT为DB
				String DTTSQLStatemen = tLRTemplateTSchema.getSQLStatement();
				String DTSQLStatemen = DTTSQLStatemen.replaceAll("from DTT",
						"from DT");
				ttLRTemplateSchema.setState(mOperate);
				ttLRTemplateSchema.setModifyDate(PubFun.getCurrentDate());
				ttLRTemplateSchema.setModifyTime(PubFun.getCurrentTime());
				ttLRTemplateSchema.setValid("1");
				tLRTemplateSchema.setDeployer(mGlobalInput.Operator);
				ttLRTemplateSchema.setTableName(ttDTTableName);
				ttLRTemplateSchema.setSQLStatement(DTSQLStatemen);
				mMap.put(ttLRTemplateSchema, "INSERT");
				mMap.put(tLRTemplateTSchema, "DELETE");
				// 复制DT表的数据到DB表中
				if (!ttDTTableName.toLowerCase().trim().equals("ldsysvar")
						&& !ttDTTableName.toLowerCase().trim()
								.equals("ruledata")
						&& ttDTTableName.toLowerCase().trim().indexOf("dt") != -1) {
					sql = "create table " + ttDTTableName
							+ " as select * from " + tDTTableName;
					SQLwithBindVariables sbv7 = new SQLwithBindVariables();
					sbv7.sql(sql);
					mMap.put(sbv7, "UPDATE");
					sql = "drop table " + tDTTableName;
					SQLwithBindVariables sbv8 = new SQLwithBindVariables();
					sbv8.sql(sql);
					mMap.put(sbv8, "DELETE");
				}
			}
		}

		return true;
	}

	// 规则订正
	private boolean unDeploy() {
		logger.debug("Begin LRTemplateTBL.dealData.........");

		ExeSQL tExeSQL = new ExeSQL();
		// tongmeng 2011-05-11 增加备份
		for (int i = 1; i <= mLRTemplateSet.size(); i++) {
			LRTemplateTDB tLRTemplateTDB = new LRTemplateTDB();
			LRTemplateTSchema tLRTemplateTSchema = new LRTemplateTSchema();
			LRTemplateDB tLRTemplateDB = new LRTemplateDB();
			LRTemplateSchema tLRTemplateSchema = new LRTemplateSchema();
			LRTemplateBSchema tLRTemplateBSchema = new LRTemplateBSchema();

			tLRTemplateDB.setId(mLRTemplateSet.get(i).getId());
			if (tLRTemplateDB.getInfo()) {
				tLRTemplateSchema = tLRTemplateDB.getSchema();
			}

			else {
				return false;
			}

			this.tReflections
					.transFields(tLRTemplateBSchema, tLRTemplateSchema);

			// tLRTemplateBSchema
			/*
			 * 分两种情况：第一种情况：此规则从没发布过 第二种情况：此规则正在使用
			 */

			String DTid = tLRTemplateSchema.getId();
			tExeSQL = new ExeSQL();
			String sql = "select count(*) from LRTemplatet where id= '?id?'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(sql);
			sqlbv3.put("id", DTid.trim());

			// 第一种情况：此规则从没发布过 分两部分：第一部分：迁移LRTemplateT中的数据到LRTemplateB
			// 第二部分：迁移DTT表中的数据到DT
			// 替换RSQLStatemen 中的表名DTT为DT
			String DTTTableName = tLRTemplateSchema.getTableName();
			String SQLStatemen = tLRTemplateSchema.getSQLStatement();
			String RSQLStatemen = SQLStatemen.replaceAll("from DT", "from DTT");
			logger.debug("===RSQLStatemen：=" + RSQLStatemen);
			// 取DT后四位，把DTT***表的表名变成DT***+版本号
			String DTTableName = DTTTableName.replaceAll("DT", "DTT");
			logger.debug("=====DTTableName：=" + DTTableName);

			this.tReflections
					.transFields(tLRTemplateTSchema, tLRTemplateSchema);
			// tLRTemplateSchema.setViewParameter(tLRTemplateTSchema.getViewParameter());
			tLRTemplateTSchema.setState(mOperate);
			logger.debug("Operator::" + mGlobalInput.Operator);
			if (mGlobalInput.Operator == null) {
				CError.buildErr(this, "操作员信息传递出错！");
				return false;
			} else {
				tLRTemplateTSchema.setDeployer(mGlobalInput.Operator);
			}
			tLRTemplateTSchema.setApprover(tLRTemplateSchema.getApprover());
			tLRTemplateTSchema.setModifier(tLRTemplateSchema.getModifier());
			// tongmeng 2011-01-07 modify
			String tOldTableName = tLRTemplateSchema.getTableName();
			// DTT0932.DTRate
			RSQLStatemen = RSQLStatemen.replaceAll(tOldTableName + ".DTRate",
					DTTableName + ".DTRate");
			tLRTemplateTSchema.setTableName(DTTableName);
			tLRTemplateTSchema.setSQLStatement(RSQLStatemen);
			tLRTemplateTSchema.setVersion(tLRTemplateTSchema.getVersion() + 1);

			// lrruledata 表备份
			LRRuleDataSchema tLRRuleDataSchema = new LRRuleDataSchema();
			LRRuleDataBSchema tLRRuleDataBSchema = new LRRuleDataBSchema();
			LRRuleDataDB tLRRuleDataDB = new LRRuleDataDB();
			tLRRuleDataDB.setId(DTid);
			if (tLRRuleDataDB.getInfo()) {
				tLRRuleDataSchema.setSchema(tLRRuleDataDB.getSchema());
				this.tReflections.transFields(tLRRuleDataBSchema,
						tLRRuleDataSchema);
				// tLRRuleDataBSchema.setVersion(tLRTemplateTSchema.getVersion());

				tLRRuleDataSchema.setVersion(tLRTemplateTSchema.getVersion());
				LRRuleDataSchema tLRRuleDataSchema1=new LRRuleDataSchema();
				tLRRuleDataSchema1=tLRRuleDataSchema;
				LRRuleDataBSchema tLRRuleDataBSchema1 = new LRRuleDataBSchema();
				tLRRuleDataBSchema1.setSchema(tLRRuleDataBSchema);
				tLRRuleDataBSchema.setRuleDataSQL("");
				mMap.put(tLRRuleDataBSchema, "BLOBDELETE&BLOBINSERT");
				mMap.put(tLRRuleDataBSchema1, "BLOBUPDATE");
				mMap.put(tLRRuleDataSchema, "BLOBDELETE&BLOBINSERT");
				mMap.put(tLRRuleDataSchema1, "BLOBUPDATE");

			}

			// //tongmeng 2011-02-16 modify
			// if(tLRTemplateSchema.getApprover()==null||tLRTemplateSchema.getApprover().equals(""))
			// {
			// tLRTemplateSchema.setApprover(mGlobalInput.Operator);
			// }
			// if(tLRTemplateSchema.getAuthorDate()==null||tLRTemplateSchema.getAuthorDate().equals(""))
			// {
			// tLRTemplateSchema.setAuthorDate(PubFun.getCurrentDate());
			// }
			//
			// if(tLRTemplateSchema.getAuthorTime()==null||tLRTemplateSchema.getAuthorTime().equals(""))
			// {
			// tLRTemplateSchema.setAuthorTime(PubFun.getCurrentTime());
			// }
			tLRTemplateTSchema.setModifyDate(PubFun.getCurrentDate());
			tLRTemplateTSchema.setModifyTime(PubFun.getCurrentTime());
			tLRTemplateTSchema.setDeclDate(PubFun.getCurrentDate());
			tLRTemplateSchema.setDeclTime(PubFun.getCurrentTime());
			mMap.put(tLRTemplateTSchema, "INSERT");
			mMap.put(tLRTemplateSchema, "DELETE");
			mMap.put(tLRTemplateBSchema, "INSERT");

			// 复制DT表的数据到DB表中
			// tongmeng 2011-02-16 modify
			if (!DTTableName.toLowerCase().trim().equals("ldsysvar")
					&& !DTTableName.toLowerCase().trim().equals("ruledata")
					&& DTTableName.toLowerCase().trim().indexOf("dt") != -1) {
				sql = "create table " + DTTableName + " as select * from "
						+ DTTTableName;
				SQLwithBindVariables sbv9 = new SQLwithBindVariables();
				sbv9.sql(sql);
				mMap.put(sbv9, "CREATE");
				sql = "drop table " + DTTTableName;
				SQLwithBindVariables sbv10 = new SQLwithBindVariables();
				sbv10.sql(sql);

				mMap.put(sbv10, "DROP");

				// 需要增加主键
				// 查询原有表的主键
				String tSQL_PK = "";
				if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
					tSQL_PK = "select a.constraint_name,a.table_name,a.column_name "
							+ " from user_cons_columns a ,user_constraints b "
							+ " where a.constraint_name=b.constraint_name and b.constraint_type='P' "
							+ " and a.table_name = upper('"
							+ "?DTTTableName?"
							+ "') "
							+ " order by a.position ";
				} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
					//修改错误 先注掉   zhangyingfeng 2016-04-20
//					tSQL_PK = "select a.constraint_name,a.table_name,a.column_name "
//							+ " from information_schema.cons_columns a ,information_schema.constraints b "
//							+ " where a.constraint_name=b.constraint_name and b.constraint_type='P' "
//							+ " and a.table_name = upper('"
//							+ "?DTTTableName?"
//							+ "') "
//							+ " order by a.position ";
					//
				}
				// SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
				// sqlbv4.sql(tSQL_PK);
				// sqlbv4.put("DTTTableName", DTTTableName);
				SQLwithBindVariables sqlbvr = new SQLwithBindVariables();
				sqlbvr.sql(tSQL_PK);
				sqlbvr.put("DTTTableName",DTTTableName);
				String tAlter_PK = "";
				SSRS tPKSSRS = new SSRS();
				tPKSSRS = tExeSQL.execSQL(sqlbvr);
				tAlter_PK = "alter table " + DTTableName
						+ " add constraint PK_" + DTTableName
						+ " primary key  ";
				tAlter_PK = tAlter_PK + " ( ";
				for (int n = 1; n <= tPKSSRS.getMaxRow(); n++) {
					tAlter_PK = tAlter_PK + tPKSSRS.GetText(n, 3);
					if (n != tPKSSRS.getMaxRow()) {
						tAlter_PK = tAlter_PK + " , ";
					}
				}
				tAlter_PK = tAlter_PK + " ) ";
				logger.debug("tAlter_PK:" + tAlter_PK);
				SQLwithBindVariables sbv11 = new SQLwithBindVariables();
				sbv11.sql(tAlter_PK);

				if (tPKSSRS != null && tPKSSRS.getMaxRow() > 0) {
					mMap.put(sbv11, "UPDATE");
				}

			}

		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			logger.debug("Begin LTLRTemplateTBL.prepareOutputData.........");

			insInputData.clear();
			insInputData.add(mMap);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRTemplateTBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
}
