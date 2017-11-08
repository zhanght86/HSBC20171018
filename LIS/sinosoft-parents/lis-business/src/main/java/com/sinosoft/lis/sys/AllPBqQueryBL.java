package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */
import com.sinosoft.lis.bl.LPEdorMainBL;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

public class AllPBqQueryBL {
private static Logger logger = Logger.getLogger(AllPBqQueryBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 返回的结果字符串 */
	private String Result;
	/** 业务处理相关变量 */
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();

	public AllPBqQueryBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;

		// 数据查询业务处理
		if (mOperate.equals("QUERY|EDOR")) {
			if (!queryData())
				return false;
			logger.debug("---queryData---");
		}
		// 进行业务处理
		// 进行插入数据
		if (mOperate.equals("INSERT||AGENT")) {
			if (!dealData()) {
				// @@错误处理
				logger.debug("deal data failed!");
				CError tError = new CError();
				tError.moduleName = "AgentBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败AgentBL-->dealData!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		if (this.mOperate.equals("QUERY||AGENT")) {
			// this.submitquery();
			return true;
		}

		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("start deal date!");
		String tNo = "", tLimit = "";
		LPEdorMainBL tLPEdorMainBL = new LPEdorMainBL();
		tLPEdorMainBL.setSchema(mLPEdorMainSchema);
		tLimit = "SN";
		tNo = PubFun1.CreateMaxNo("AgentCode", tLimit);
		logger.debug("create no" + tNo);
		// tLPEdorMainBL.setAgentCode(tNo);
		tLPEdorMainBL.setDefaultFields();
		tLPEdorMainBL.setOperator(mGlobalInput.Operator);
		logger.debug("b set schema");
		mLPEdorMainSchema.setSchema(tLPEdorMainBL);
		logger.debug("e set schema");

		return true;

		// boolean tReturn =true;
		// this.mLPEdorMainSchema.setModifyDate(getDate());
		// this.mLPEdorMainSchema.setModifyTime();
		// tReturn=true;
		// return tReturn ;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		logger.debug("moperator:" + mOperate);
		this.mLPEdorMainSchema.setSchema((LPEdorMainSchema) cInputData
				.getObjectByObjectName("LPEdorMainSchema", 0));
		this.mLCPolSchema.setSchema((LCPolSchema) cInputData
				.getObjectByObjectName("LCPolSchema", 0));
		// if (!mOperate.equals("QUERY||AGENT"))
		// {
		// this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		// }
		return true;
	}

	/**
	 * 查询符合条件的代理人信息 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean queryData() {
		String tsql = "select LPEdorMain.EdorNo,LPEdorMain.PolNo,LCPol.RiskCode,LPEdorMain.InsuredNo," +
				"LPEdorMain.InsuredName,LPEdorMain.GetMoney,LPEdorMain.EdorState from LPEdorMain,LCPol where " +
				"LPEdorMain.PolNo=LCPol.PolNo order by LPEdorMain.EdorNo";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tsql);
		SSRS nSSRS = new SSRS();
		ExeSQL nExeSQL = new ExeSQL();
		nSSRS = nExeSQL.execSQL(sqlbv);

		int tRow = nSSRS.MaxRow;
		int tCol = nSSRS.MaxCol;
		if (nSSRS.MaxNumber == 0) {
			Result = "100|未查询到相关数据";
		} else {
			String tEdorNo = new String();
			// Result = "0|"+nSSRS.MaxNumber;
			int i, j;
			for (i = 1; i <= tRow; i++) {
				if (tEdorNo.equals(nSSRS.GetText(i, 1))) {

				}
				tEdorNo = nSSRS.GetText(i, 1);
				Result = Result + "^";
				for (j = 1; j <= tCol; j++) {
					if (j == 1) {
						Result = Result + nSSRS.GetText(i, j);
					}
					Result = "|" + Result + nSSRS.GetText(i, j);
				}
			}
		}
		String manageCom = nSSRS.GetText(1, 1);
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setSchema(mLPEdorMainSchema);

		if (!tLPEdorMainDB.getInfo()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPEdorMainDB";
			tError.functionName = "queryData";
			tError.errorMessage = "保单查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPEdorMainSchema.setSchema(tLPEdorMainDB);
		mResult.clear();
		mResult.add(mLPEdorMainSchema);

		return true;

	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		this.mResult.clear();
		logger.debug("Start AllPBqQueryBLQuery Submit...");
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setSchema(this.mLPEdorMainSchema);
		this.mLPEdorMainSet = tLPEdorMainDB.query();
		this.mResult.add(this.mLPEdorMainSet);
		logger.debug("End AllPBqQueryBLQuery Submit...");
		// 如果有需要处理的错误，则返回
		if (tLPEdorMainDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "AllPBqQueryBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			// this.mInputData.add(this.mGlobalInput);
			this.mInputData.add(this.mLPEdorMainSchema);
			mResult.clear();
			mResult.add(this.mLPEdorMainSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AllPBqQueryBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {

		return this.mResult;
	}

	public static void main(String[] args) {
		AllPBqQueryBL allPBqQueryBL1 = new AllPBqQueryBL();
	}
}
