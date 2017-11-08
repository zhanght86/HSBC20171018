



package com.sinosoft.lis.reinsure.calculate.init;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @ zhangbin
 * @version 1.0
 */

import com.sinosoft.lis.db.RIAccumulateDefDB;
import com.sinosoft.lis.db.RIAccumulateGetDutyDB;
import com.sinosoft.lis.db.RIAccumulateRDCodeDB;
import com.sinosoft.lis.db.RIAssociateFeeTableDB;
import com.sinosoft.lis.db.RIBarGainInfoDB;
import com.sinosoft.lis.db.RICalDefDB;
import com.sinosoft.lis.db.RIDivisionLineDefDB;
import com.sinosoft.lis.db.RIExchangeRateDB;
import com.sinosoft.lis.db.RIFeeRateTableClumnDefDB;
import com.sinosoft.lis.db.RIFeeRateTableDefDB;
import com.sinosoft.lis.db.RIFeeRateTableTraceDB;
import com.sinosoft.lis.db.RIIncomeCompanyDB;
import com.sinosoft.lis.db.RIInterestDB;
import com.sinosoft.lis.db.RIItemCalDB;
import com.sinosoft.lis.db.RIPreceptDB;
import com.sinosoft.lis.db.RIRiskDivideDB;
import com.sinosoft.lis.db.RIUnionAccumulateDB;
import com.sinosoft.lis.reinsure.calculate.process.RIProcessType;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.lis.schema.RICalDefSchema;
import com.sinosoft.lis.vschema.RIAccumulateGetDutySet;
import com.sinosoft.lis.vschema.RIAccumulateRDCodeSet;
import com.sinosoft.lis.vschema.RIAssociateFeeTableSet;
import com.sinosoft.lis.vschema.RIBarGainInfoSet;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;
import com.sinosoft.lis.vschema.RIExchangeRateSet;
import com.sinosoft.lis.vschema.RIFeeRateTableClumnDefSet;
import com.sinosoft.lis.vschema.RIFeeRateTableDefSet;
import com.sinosoft.lis.vschema.RIFeeRateTableTraceSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.vschema.RIInterestSet;
import com.sinosoft.lis.vschema.RIItemCalSet;
import com.sinosoft.lis.vschema.RIPreceptSet;
import com.sinosoft.lis.vschema.RIRiskDivideSet;
import com.sinosoft.lis.vschema.RIUnionAccumulateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;

public class RIInitData {
	public CErrors mErrors = new CErrors();
	/** 私有静态构造器 */
	private static RIInitData riInitData = null;
	/** 累计风险编码 */
	private String mAccumulateDefNo = "";
	/** 累计风险类别 */
	private RIAccumulateDefSchema mRIAccumulateDefSchema;
	/** 累计险种/责任表 */
	private RIAccumulateRDCodeSet mRIAccumulateRDCodeSet;
	/** 累计给付责任表 */
	private RIAccumulateGetDutySet mRIAccumulateGetDutySet;
	/** 再保合同 */
	private RIBarGainInfoSet mRIBarGainInfoSet;
	/** 再保方案 */
	private RIPreceptSet riPreceptSet;
	/** 溢额线设置 */
	private TransferData riDivisionLineDefTD = new TransferData();
	/** 分保公司定义 */
	private TransferData riIncomeCompanyTD = new TransferData();
	/** 分配额设置 */
	private TransferData riRiskDivideTD = new TransferData();
	/** 分保方案险种/责任费率表 **/
	private TransferData riAssociateFeeTableTD = new TransferData();
	/** 提数算法 */
	private RIItemCalSet riDistillCalSet = new RIItemCalSet();
	/** 数据检验算法 */
	private RIItemCalSet riCheckCalSet = new RIItemCalSet();
	/** 方案分配算法 */
	private TransferData riAssignCalTD = new TransferData();
	/** 参数算法 */
	private TransferData riParamCalTD = new TransferData();
	/** 处理类算法 */
	private TransferData riProcessCalTD = new TransferData();
	/** 处理类 */
	private TransferData riProcessCalClassTD = new TransferData();
	/** 计算项算法 */
	private TransferData riItemCalTD = new TransferData();
	/** 费率表批次 */
	private TransferData riFeeRateTD = new TransferData();
	/** 联合累计风险定义 */
	private RIUnionAccumulateSet mRIUnionAccumulateSet = new RIUnionAccumulateSet();
	/** 费率定义表 */
	private RIFeeRateTableDefSet mRIFeeRateTableDefSet;
	/** 费率表更新轨迹表 */
	private RIFeeRateTableTraceSet mRIFeeRateTableTraceSet;
	/** 费率表字段定义 */
	private RIFeeRateTableClumnDefSet riFeeRateTableClumnDefSet;
	/** 日志文件保存路径 */
	private String[][] riLogPath;
	/** 汇率 */
	private RIExchangeRateSet mRIExchangeRateSet = new RIExchangeRateSet();
	/** 利率 */
	private RIInterestSet mRIInterestSet = new RIInterestSet();

	private RIInitData(String accumulateDefNo) throws Exception {
		mAccumulateDefNo = accumulateDefNo;
		if (!init()) {
			Exception r = new Exception(mErrors.getFirstError());
			throw r;
		}
	}

	/**
	 * 得到初始化类实例方法
	 */
	public static RIInitData getObject(String accumulateDefNo) throws Exception {
		if (riInitData == null) {
			try {
				riInitData = new RIInitData(accumulateDefNo);
			} catch (Exception ex) {
				throw ex;
			}
		}
		return riInitData;
	}

	private boolean init() {
		// 初始化累计风险
		if (!initRIAccumulateDef()) {
			return false;
		}
		// 初始化累计风险险种/责任
		if (!initRIAccumulateRDCode()) {
			return false;
		}
		// 初始化累计风险给付责任
		if (!initRIAccumulateGetDuty()) {
			return false;
		}
		// 初始化再保合同
		if (!initRICont()) {
			return false;
		}
		// 初始化再保方案
		if (!initPrecept()) {
			return false;
		}
		// 初始化溢额线
		if (!initDivisionLineDef()) {
			return false;
		}
		// 初始化分保公司信息
		if (!initIncomeCompany()) {
			return false;
		}
		// 初始化分配额设置
		if (!initRIRiskDivide()) {
			return false;
		}
		// 初始化分保方案险种/责任费率表
		if (!initRIAssociateFeeTableTD()) {
			return false;
		}
		// 初始化业务数据提取类算法
		if (!initDistillCalSet()) {
			return false;
		}
		// 初始化数据检查算法
		if (!initCheckCalSet()) {
			return false;
		}
		// 初始化方案分配算法
		if (!initAssignCalTD()) {
			return false;
		}
		// 初始化参数算法
		if (!initParamCalTD()) {
			return false;
		}
		// 初始初始类
		if (!initProcessCalTD()) {
			return false;
		}
		// 初始化计算项算法
		if (!initRIItemCalTD()) {
			return false;
		}
		// 初始化费率表定义表
		if (!initRIFeeRateTableDefSet()) {
			return false;
		}
		// 初始化费率表轨迹表
		if (!initRIFeeRateTableTraceSet()) {
			return false;
		}
		// 初始化费率表批次
		if (!initRIFeeRateTD()) {
			return false;
		}
		// 初始化费率表字段定义
		if (!initFeeRateTableClumnDefSet()) {
			return false;
		}
		if (!initUnionAccumulate()) {
			return false;
		}
		// 初始化日志文件路径
		if (!initRILogPath()) {
			return false;
		}
		// 初始化汇率
		if (!initRIExchangeRateSet()) {
			return false;
		}
		// 初始化利率
		if (!initRIInterestSet()) {
			return false;
		}
		return true;
	}

	// 初始化共保利率
	private boolean initRIInterestSet() {
		RIInterestDB tRIInterestDB = new RIInterestDB();
		String sql = " select * from RIInterest x,(select max(a.Serialno) Serialno,a.Riskcode Riskcode from RIInterest a where a.state='01' group by a.Riskcode) y where x.Serialno = y.Serialno and x.Riskcode = y.Riskcode ";
		mRIInterestSet = tRIInterestDB.executeQuery(sql);
		if (tRIInterestDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo + " 的利率出错");
			return false;
		}
		return true;
	}

	// 初始化汇率
	private boolean initRIExchangeRateSet() {
		RIExchangeRateDB tRIExchangeRateDB = new RIExchangeRateDB();
		String sql = "select * from RIExchangeRate x,(select max(a.Serialno) Serialno,a.Currency Currency from RIExchangeRate a where a.state='01' group by a.Currency) y where x.Serialno = y.Serialno and x.Currency = y.Currency ";
		mRIExchangeRateSet = tRIExchangeRateDB.executeQuery(sql);
		if (tRIExchangeRateDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo + " 的汇率出错");
			return false;
		}
		return true;
	}

	// 初始化累计风险
	private boolean initRIAccumulateDef() {
		RIAccumulateDefDB tRIAccumulateDefDB = new RIAccumulateDefDB();
		tRIAccumulateDefDB.setAccumulateDefNO(mAccumulateDefNo);
		System.out.println(" mAccumulateDefNo: " + mAccumulateDefNo);
		if (!tRIAccumulateDefDB.getInfo()) {
			buildError("InitInfo", "初始化累计风险编码为：" + mAccumulateDefNo
					+ " 的累计风险时为空");
			return false;
		}
		if (tRIAccumulateDefDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo + " 的累计风险时出错");
			return false;
		}
		mRIAccumulateDefSchema = tRIAccumulateDefDB.getSchema();
		return true;
	}

	// 初始化累计险种/责任表
	private boolean initRIAccumulateRDCode() {
		RIAccumulateRDCodeDB tRIAccumulateRDCodeDB = new RIAccumulateRDCodeDB();
		tRIAccumulateRDCodeDB.setAccumulateDefNO(mAccumulateDefNo);
		mRIAccumulateRDCodeSet = tRIAccumulateRDCodeDB.query();
		if (tRIAccumulateRDCodeDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo + " 的险种/责任出错");
			return false;
		}
		if (mRIAccumulateRDCodeSet.size() == 0) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo
					+ " 的险种/责任出错：险种/责任为空。");
			return false;
		}
		return true;
	}

	// 初始化给付责任
	private boolean initRIAccumulateGetDuty() {
		RIAccumulateGetDutyDB tRIAccumulateGetDutyDB = new RIAccumulateGetDutyDB();
		tRIAccumulateGetDutyDB.setAccumulateDefNO(mAccumulateDefNo);
		mRIAccumulateGetDutySet = tRIAccumulateGetDutyDB.query();
		if (tRIAccumulateGetDutyDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo + " 的给付责任时出错");
			return false;
		}
		return true;
	}

	// 初始化再保合同
	private boolean initRICont() {
		RIBarGainInfoDB tRIBarGainInfoDB = new RIBarGainInfoDB();
		String sql = "select * from Ribargaininfo a where a.RIContNo in (select distinct r.RIContNo from RIPrecept r where r.AccumulateDefNo='"
				+ mAccumulateDefNo
				+ "') and a.state='01' order by a.CValiDate ";
		mRIBarGainInfoSet = tRIBarGainInfoDB.executeQuery(sql);
		if (tRIBarGainInfoDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo + " 的再保合同出错");
			return false;
		}
		if (mRIBarGainInfoSet.size() == 0) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo
					+ " 的再保合同出错。没有适用该累计风险的再保合同。");
			return false;
		}
		return true;
	}

	// 初始化再保方案。包括:合同分保方案，临时分保方案
	private boolean initPrecept() {
		RIPreceptDB tRIPreceptDB = new RIPreceptDB();
		String sql = " select * from Riprecept a where a.Accumulatedefno = '"
				+ mAccumulateDefNo
				+ "'  and a.state='01' order by a.Rimainpreceptno desc ";
		riPreceptSet = tRIPreceptDB.executeQuery(sql);
		if (tRIPreceptDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo + " 的再保方案出错");
			return false;
		}
		if (riPreceptSet.size() == 0) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo
					+ " 的再保方案出错。没有适用该累计风险的再保方案。");
			return false;
		}
		return true;
	}

	// 初始化溢额线设置
	private boolean initDivisionLineDef() {
		RIDivisionLineDefSet tRIDivisionLineDefSet;
		RIDivisionLineDefDB tRIDivisionLineDefDB = new RIDivisionLineDefDB();
		for (int i = 1; i <= riPreceptSet.size(); i++) {
			String sql = " select * from RIDivisionLineDef a where a.RIPreceptNo='"
					+ riPreceptSet.get(i).getRIPreceptNo()
					+ "' and exists (select * from RIPrecept b where b.ripreceptno=a.ripreceptno and b.State='01') ";
			// tRIDivisionLineDefDB.setRIPreceptNo(riPreceptSet.get(i).getRIPreceptNo());
			tRIDivisionLineDefSet = tRIDivisionLineDefDB.executeQuery(sql);
			if (tRIDivisionLineDefDB.mErrors.needDealError()) {
				buildError("InitInfo", "初始化再保方案编号为："
						+ riPreceptSet.get(i).getRIPreceptNo() + " 的溢额线设置出错");
				return false;
			}
			if (tRIDivisionLineDefSet.size() == 0) {
				buildError("InitInfo", "初始化再保方案编号为："
						+ riPreceptSet.get(i).getRIPreceptNo()
						+ " 的溢额线设置出错。没有该再保方案下的溢额线设置。");
				return false;
			}
			riDivisionLineDefTD.setNameAndValue(riPreceptSet.get(i)
					.getRIPreceptNo(), tRIDivisionLineDefSet);
		}
		return true;
	}

	// 初始化分保公司设置
	private boolean initIncomeCompany() {
		RIIncomeCompanySet tRIIncomeCompanySet;
		RIIncomeCompanyDB tRIIncomeCompanyDB = new RIIncomeCompanyDB();
		for (int i = 1; i <= riPreceptSet.size(); i++) {
			String sql = " select * from RIIncomeCompany a where a.RIPreceptNo='"
					+ riPreceptSet.get(i).getRIPreceptNo()
					+ "' and exists (select * from RIPrecept b where b.ripreceptno=a.ripreceptno and b.State='01') ";
			// tRIIncomeCompanyDB.setRIPreceptNo(riPreceptSet.get(i).getRIPreceptNo());
			tRIIncomeCompanySet = tRIIncomeCompanyDB.executeQuery(sql);
			if (tRIIncomeCompanyDB.mErrors.needDealError()) {
				buildError("InitInfo", "初始化再保方案编号为："
						+ riPreceptSet.get(i).getRIPreceptNo() + " 的分保公司设置出错");
				return false;
			}
			if (tRIIncomeCompanySet.size() == 0) {
				buildError("InitInfo", "初始化再保方案编号为："
						+ riPreceptSet.get(i).getRIPreceptNo()
						+ " 的分保公司设置出错。没有该再保方案下的分保公司设置。");
				return false;
			}
			riIncomeCompanyTD.setNameAndValue(riPreceptSet.get(i)
					.getRIPreceptNo(), tRIIncomeCompanySet);
		}
		return true;
	}

	// 初始化分配额设置
	private boolean initRIRiskDivide() {
		RIRiskDivideSet tRIRiskDivideSet;
		RIRiskDivideDB tRIRiskDivideDB = new RIRiskDivideDB();
		for (int i = 1; i <= riPreceptSet.size(); i++) {
			String tSql = "select * from RIRiskDivide a where a.PossessScale >0 and  a.RIPreceptNo ='"
					+ riPreceptSet.get(i).getRIPreceptNo()
					+ "' and exists (select * from RIPrecept b where b.ripreceptno=a.ripreceptno and b.State='01') order by AreaID ";
			System.out.println("方案分配额设置: " + tSql);
			tRIRiskDivideSet = tRIRiskDivideDB.executeQuery(tSql);
			if (tRIRiskDivideDB.mErrors.needDealError()) {
				buildError("InitInfo", "初始化再保方案编号为："
						+ riPreceptSet.get(i).getRIPreceptNo() + " 的分配额设置出错");
				return false;
			}
			if (tRIRiskDivideSet.size() == 0) {
				buildError("InitInfo", "初始化再保方案编号为："
						+ riPreceptSet.get(i).getRIPreceptNo()
						+ " 的分配额设置出错。没有该再保方案下的分配额设置。");
				return false;
			}
			riRiskDivideTD.setNameAndValue(
					riPreceptSet.get(i).getRIPreceptNo(), tRIRiskDivideSet);
		}
		return true;
	}

	// 初始化分保方案险种/责任费率表
	private boolean initRIAssociateFeeTableTD() {
		RIAssociateFeeTableSet tRIAssociateFeeTableSet;
		RIAssociateFeeTableDB tRIAssociateFeeTableDB = new RIAssociateFeeTableDB();
		for (int i = 1; i <= riPreceptSet.size(); i++) {
			String tSql = " select * from RIAssociateFeeTable a where a.RIPreceptNo='"
					+ riPreceptSet.get(i).getRIPreceptNo()
					+ "' and exists (select * from RIPrecept b where b.ripreceptno=a.ripreceptno and b.State='01') ";
			tRIAssociateFeeTableSet = tRIAssociateFeeTableDB.executeQuery(tSql);
			if (tRIAssociateFeeTableDB.mErrors.needDealError()) {
				buildError("InitInfo", "初始化再保方案编号为："
						+ riPreceptSet.get(i).getRIPreceptNo() + " 的险种/责任费率表出错");
				return false;
			}
			riAssociateFeeTableTD.setNameAndValue(riPreceptSet.get(i)
					.getRIPreceptNo(), tRIAssociateFeeTableSet);
		}

		return true;
	}

	// 初始化数据提取算法
	private boolean initDistillCalSet() {
		RICalDefDB tRICalDefDB = new RICalDefDB();
		tRICalDefDB
				.setArithmeticDefID(mRIAccumulateDefSchema.getArithmeticID());
		if (!tRICalDefDB.getInfo()) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo
					+ " 的业务数据提取算法为空。");
			return false;
		}
		RICalDefSchema tRICalDefSchema = tRICalDefDB.getSchema();
		RIItemCalDB tRIItemCalDB = new RIItemCalDB();
		String tSQL = " select * from RIItemCal where ArithmeticType='01' and ArithmeticDefID='"
				+ tRICalDefSchema.getArithmeticDefID()
				+ "' order by CalItemOrder ";
		System.out.println("数据提取算法: " + tSQL);
		riDistillCalSet = tRIItemCalDB.executeQuery(tSQL);
		System.out.println(" aa:　" + riDistillCalSet.size());
		if (tRIItemCalDB.mErrors.needDealError()) {
			buildError("InitInfo",
					"初始化累计风险编号为：" + mRIAccumulateDefSchema.getAccumulateDefNO()
							+ " 的数据提取类算法出错");
			return false;
		}
		return true;
	}

	// 初始化数据检验算法
	private boolean initCheckCalSet() {
		RICalDefDB tRICalDefDB = new RICalDefDB();
		tRICalDefDB
				.setArithmeticDefID(mRIAccumulateDefSchema.getArithmeticID());
		if (!tRICalDefDB.getInfo()) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo
					+ " 的业务数据提取算法为空。");
			return false;
		}
		RICalDefSchema tRICalDefSchema = tRICalDefDB.getSchema();
		RIItemCalDB tRIItemCalDB = new RIItemCalDB();
		String tSQL = " select * from RIItemCal where ArithmeticType='02' and ArithmeticDefID='"
				+ tRICalDefSchema.getArithmeticDefID()
				+ "' order by CalItemOrder ";
		System.out.println("数据检验算法: " + tSQL);
		riCheckCalSet = tRIItemCalDB.executeQuery(tSQL);
		if (tRIItemCalDB.mErrors.needDealError()) {
			buildError("InitInfo",
					"初始化累计风险编号为：" + mRIAccumulateDefSchema.getAccumulateDefNO()
							+ " 的数据校验算法出错");
			return false;
		}
		return true;
	}

	// 初始化方案分配算法
	private boolean initAssignCalTD() {
		RIItemCalSet tRIItemCalSet;
		RIItemCalDB tRIItemCalDB = new RIItemCalDB();
		for (int i = 1; i <= riPreceptSet.size(); i++) {
			RICalDefDB tRICalDefDB = new RICalDefDB();
			tRICalDefDB.setArithmeticDefID(riPreceptSet.get(i)
					.getArithmeticID());
			if (!tRICalDefDB.getInfo()) {
				buildError("InitInfo", "初始化再保方案为："
						+ riPreceptSet.get(i).getRIPreceptNo()
						+ " 的方案分配算法定义时出错:没有给方案的算法定义。");
				return false;
			}
			RICalDefSchema tRICalDefSchema = tRICalDefDB.getSchema();
			String tSQL = " select * from RIItemCal where ArithmeticType='11' and ArithmeticDefID='"
					+ tRICalDefSchema.getArithmeticDefID()
					+ "' order by CalItemOrder ";
			System.out.println("方案分配算法: " + tSQL);
			tRIItemCalSet = tRIItemCalDB.executeQuery(tSQL);
			if (tRIItemCalDB.mErrors.needDealError()) {
				buildError("InitInfo", "初始化再保方案为："
						+ riPreceptSet.get(i).getRIPreceptNo()
						+ " 的方案分配算法描述时出错:没有给方案的算法描述");
				return false;
			}
			riAssignCalTD.setNameAndValue(riPreceptSet.get(i).getRIPreceptNo(),
					tRIItemCalSet);
		}
		return true;
	}

	// 初始化参数算法
	private boolean initParamCalTD() {
		RIItemCalSet tRIItemCalSet;
		RIItemCalDB tRIItemCalDB = new RIItemCalDB();
		for (int i = 1; i <= riPreceptSet.size(); i++) {
			RICalDefDB tRICalDefDB = new RICalDefDB();
			tRICalDefDB.setArithmeticDefID(riPreceptSet.get(i)
					.getArithmeticID());
			if (!tRICalDefDB.getInfo()) {
				buildError("InitInfo", "初始化再保方案为："
						+ riPreceptSet.get(i).getRIPreceptNo()
						+ " 的方案分配算法定义时出错:没有给方案的算法定义。");
				return false;
			}
			RICalDefSchema tRICalDefSchema = tRICalDefDB.getSchema();
			String tSQL = " select * from RIItemCal where ArithmeticType='12' and ArithmeticDefID='"
					+ tRICalDefSchema.getArithmeticDefID()
					+ "' order by CalItemOrder ";
			System.out.println("参数算法: " + tSQL);
			tRIItemCalSet = tRIItemCalDB.executeQuery(tSQL);
			if (tRIItemCalDB.mErrors.needDealError()) {
				buildError("InitInfo", "初始化再保方案为："
						+ riPreceptSet.get(i).getRIPreceptNo() + " 的参数算法时出错");
				return false;
			}
			riParamCalTD.setNameAndValue(riPreceptSet.get(i).getRIPreceptNo(),
					tRIItemCalSet);
		}
		return true;
	}

	// 初始化计算处理类算法,初始化计算处理类
	// 计算处理类描述时，必须按照新单，续期，保全，理赔的顺序，由CalItemOrder指定顺序
	private boolean initProcessCalTD() {
		RIItemCalSet tRIItemCalSet;
		RIItemCalDB tRIItemCalDB = new RIItemCalDB();
		for (int i = 1; i <= riPreceptSet.size(); i++) {
			RICalDefDB tRICalDefDB = new RICalDefDB();
			tRICalDefDB.setArithmeticDefID(riPreceptSet.get(i)
					.getArithmeticID());
			if (!tRICalDefDB.getInfo()) {
				buildError("InitInfo", "初始化再保方案为："
						+ riPreceptSet.get(i).getRIPreceptNo()
						+ " 的方案分配算法定义时出错:没有给方案的算法定义。");
				return false;
			}
			RICalDefSchema tRICalDefSchema = tRICalDefDB.getSchema();
			String tSQL = " select * from RIItemCal where ArithmeticType='14' and ArithmeticDefID='"
					+ tRICalDefSchema.getArithmeticDefID()
					+ "' order by CalItemOrder ";
			System.out.println("计算处理类: " + tSQL);
			tRIItemCalSet = tRIItemCalDB.executeQuery(tSQL);
			if (tRIItemCalDB.mErrors.needDealError()) {
				buildError("InitInfo", "初始化再保方案为："
						+ riPreceptSet.get(i).getRIPreceptNo() + " 的计算处理类算法时出错");
				return false;
			}
			riProcessCalTD.setNameAndValue(
					riPreceptSet.get(i).getRIPreceptNo(), tRIItemCalSet);
			RIProcessType tRIProcessType[] = new RIProcessType[tRIItemCalSet
					.size()];
			for (int j = 1; j <= tRIItemCalSet.size(); j++) {
				try {
					Class tClass = Class.forName(tRIItemCalSet.get(j)
							.getCalClass());
					tRIProcessType[j - 1] = (RIProcessType) tClass
							.newInstance();
				} catch (Exception ex) {
					buildError("InitInfo", "初始化累计风险编码为：" + mAccumulateDefNo
							+ " 的计算处理类出错： " + ex);
					return false;
				}
			}
			riProcessCalTD.setNameAndValue(
					riPreceptSet.get(i).getRIPreceptNo(), tRIItemCalSet);
			riProcessCalClassTD.setNameAndValue(riPreceptSet.get(i)
					.getRIPreceptNo(), tRIProcessType);
		}
		return true;
	}

	// 初始化计算项算法
	private boolean initRIItemCalTD() {
		RIItemCalSet tRIItemCalSet;
		RIItemCalDB tRIItemCalDB = new RIItemCalDB();
		for (int i = 1; i <= riPreceptSet.size(); i++) {
			RICalDefDB tRICalDefDB = new RICalDefDB();
			tRICalDefDB.setArithmeticDefID(riPreceptSet.get(i)
					.getArithmeticID());
			if (!tRICalDefDB.getInfo()) {
				buildError("InitInfo", "初始化再保方案为："
						+ riPreceptSet.get(i).getRIPreceptNo()
						+ " 的方案分配算法定义时出错:没有给方案的算法定义。");
				return false;
			}
			RICalDefSchema tRICalDefSchema = tRICalDefDB.getSchema();
			String tSQL = " select * from RIItemCal where ArithmeticType='13' and ArithmeticDefID='"
					+ tRICalDefSchema.getArithmeticDefID()
					+ "' order by CalItemOrder ";
			System.out.println("计算项算法: " + tSQL);
			tRIItemCalSet = tRIItemCalDB.executeQuery(tSQL);
			if (tRIItemCalDB.mErrors.needDealError()) {
				buildError("InitInfo", "初始化再保方案为："
						+ riPreceptSet.get(i).getRIPreceptNo() + " 的计算项算法时出错");
				return false;
			}
			if (tRIItemCalSet.size() == 0) {
				buildError("InitInfo", "初始化再保方案为："
						+ riPreceptSet.get(i).getRIPreceptNo()
						+ " 的计算项算法时出错.计算项算法为空");
				return false;
			}
			for (int j = 1; j < tRIItemCalSet.size(); j++) {
				System.out.println("order: "
						+ tRIItemCalSet.get(j).getCalItemOrder());
				System.out.println("sql: " + tRIItemCalSet.get(j).getCalSQL());
				System.out.println("remark: "
						+ tRIItemCalSet.get(j).getReMark());
			}
			riItemCalTD.setNameAndValue(riPreceptSet.get(i).getRIPreceptNo(),
					tRIItemCalSet);
		}
		return true;
	}

	// 初始化费率表字段定义
	private boolean initRIFeeRateTableDefSet() {
		RIFeeRateTableDefDB tRIFeeRateTableDefDB = new RIFeeRateTableDefDB();
		String strSQL = "select * from RIFeeRateTableDef where 1=1 order by feetableno ";
		mRIFeeRateTableDefSet = tRIFeeRateTableDefDB.executeQuery(strSQL);
		if (tRIFeeRateTableDefDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化费率表定义表时出错。");
			return false;
		}
		return true;
	}

	// 初始化费率表轨迹定义
	private boolean initRIFeeRateTableTraceSet() {
		RIFeeRateTableTraceDB tRIFeeRateTableTraceDB = new RIFeeRateTableTraceDB();
		String strSQL = "select * from RIFeeRateTableTrace where 1=1 order by feetableno,batchno ";
		mRIFeeRateTableTraceSet = tRIFeeRateTableTraceDB.executeQuery(strSQL);
		if (tRIFeeRateTableTraceDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化费率表定义表时出错。");
			return false;
		}
		return true;
	}

	// 初始化费率表批次
	private boolean initRIFeeRateTD() {
		String tSQL;
		String[][] feeRateTableTrace;
		for (int i = 1; i <= riPreceptSet.size(); i++) {
			tSQL = " select x.X1,x.X2,x.X3,x.X4,(case when (x.X5 is null or x.X5='') then x.X6-1 else x.X5 end),x.X7 from (select a.feetableno X1,a.batchno X2,(select c.remark from RIFeeRateTableDef c where a.feetableno=c.feetableno) X3,a.inuredate X4,a.lapsedate X5,(select min(b.inuredate) from RIFeeRateTableTrace b where b.feetableno=a.feetableno and b.inuredate>a.inuredate) X6,(SELECT d.matchmode FROM RIFeeRateTableDef d where d.feetableno = a.feetableno) x7 FROM RIFeeRateTableTrace a where exists (select * from RIRiskDivide b where (b.PremFeeTableNo=a.Feetableno or b.ComFeeTableNo=a.Feetableno ) and b.RIPreceptNo='"
					+ riPreceptSet.get(i).getRIPreceptNo()
					+ "') or exists (select * from RIAssociateFeeTable c where (c.PremFeeTableNo=a.Feetableno or c.ComFeeTableNo=a.Feetableno ) and c.RIPreceptNo='"
					+ riPreceptSet.get(i).getRIPreceptNo()
					+ "') order by a.feetableno,a.batchno ) x ";
			System.out.println("费率表批次: " + tSQL);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS temp = tExeSQL.execSQL(tSQL);
			feeRateTableTrace = temp.getAllData();
			riFeeRateTD.setNameAndValue(riPreceptSet.get(i).getRIPreceptNo(),
					feeRateTableTrace);
		}
		return true;
	}

	// 初始化费率表字段定义
	private boolean initFeeRateTableClumnDefSet() {
		RIFeeRateTableClumnDefDB tRIFeeRateTableClumnDefDB = new RIFeeRateTableClumnDefDB();
		String tSQL = " select * from RIFeeRateTableClumnDef a where 1=1 order by a.feetableno,a.feeclumnno ";
		riFeeRateTableClumnDefSet = tRIFeeRateTableClumnDefDB
				.executeQuery(tSQL);
		if (tRIFeeRateTableClumnDefDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化费率表字段定义表时出错");
			return false;
		}
		return true;
	}

	// 初始化日志文件路径
	private boolean initRILogPath() {
		String tSQL = " select SysVarType,SysVarValue from ldsysvar where sysvar='rilog' ";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS temp = tExeSQL.execSQL(tSQL);
		if (tExeSQL.mErrors.needDealError()) {
			buildError("InitInfo", "初始化日志文件路径时出错");
			return false;
		}
		riLogPath = temp.getAllData();
		return true;
	}

	private boolean initUnionAccumulate() {
		RIUnionAccumulateDB tRIUnionAccumulateDB = new RIUnionAccumulateDB();
		tRIUnionAccumulateDB.setAccumulateDefNO(mAccumulateDefNo);
		mRIUnionAccumulateSet = tRIUnionAccumulateDB.query();
		if (tRIUnionAccumulateDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化联合累计风险为：" + mAccumulateDefNo
					+ " 的再保方案出错");
			return false;
		}

		return true;
	}

	/**
	 * 销毁实例。处理下一个累计风险的再保数据时调用，用以清除上一累计风险的初始化数据。
	 */
	public void destory() {
		this.mAccumulateDefNo = null;
		this.mRIAccumulateDefSchema = null;
		this.mRIAccumulateRDCodeSet = null;
		this.mRIAccumulateGetDutySet = null;
		this.mRIBarGainInfoSet = null;
		this.riPreceptSet = null;
		this.riDivisionLineDefTD = null;
		this.riIncomeCompanyTD = null;
		this.riRiskDivideTD = null;
		this.riAssociateFeeTableTD = null;
		this.riDistillCalSet = null;
		this.riCheckCalSet = null;
		this.riAssignCalTD = null;
		this.riParamCalTD = null;
		this.riProcessCalTD = null;
		this.riProcessCalClassTD = null;
		this.riItemCalTD = null;
		this.riFeeRateTD = null;
		this.mRIUnionAccumulateSet = null;
		this.mRIFeeRateTableDefSet = null;
		this.mRIFeeRateTableTraceSet = null;
		this.riFeeRateTableClumnDefSet = null;
		this.riLogPath = null;
		this.mRIExchangeRateSet = null;
		this.mRIInterestSet = null;

		riInitData = null;
	}

	/**
	 * 返回利率
	 * 
	 * @return RIInterestSet
	 */
	public RIInterestSet getRIInterestSet() {
		return mRIInterestSet;
	}

	/**
	 * 返回汇率
	 * 
	 * @return RIExchangeRateSet
	 */
	public RIExchangeRateSet getRIExchangeRateSet() {
		return mRIExchangeRateSet;
	}

	/**
	 * 返回累积方案类别编码
	 * 
	 * @return String
	 */
	public String getRIAccumulateDefNo() {
		return mAccumulateDefNo;
	}

	/**
	 * 返回累积风险定义
	 * 
	 * @return String
	 */
	public RIAccumulateDefSchema getRIAccumulateDefSchema() {
		return mRIAccumulateDefSchema;
	}

	/**
	 * 返回累计风险险种/责任
	 * 
	 * @return RIAccumulateRDCodeSet
	 */
	public RIAccumulateRDCodeSet getRIAccumulateRDCodeSet() {
		return mRIAccumulateRDCodeSet;
	}

	/**
	 * 返回累计风险的给付责任
	 * 
	 * @return RIAccumulateGetDutySet
	 */
	public RIAccumulateGetDutySet getRIAccumulateGetDutySet() {
		return mRIAccumulateGetDutySet;
	}

	/**
	 * 返回累计风险的再保合同
	 * 
	 * @return RIBarGainInfoSet
	 */
	public RIBarGainInfoSet getRIBarGainInfoSet() {
		return mRIBarGainInfoSet;
	}

	/**
	 * 返回累计风险的再保方案
	 * 
	 * @return RIPreceptSet
	 */
	public RIPreceptSet getRIPreceptSet() {
		return riPreceptSet;
	}

	/**
	 * 返回累计风险下多个再保方案的溢额线设置。
	 * 
	 * @return TransferData，其中：Name为再保方案号，Value为该方案下溢额线集合
	 */
	public TransferData getRIDivisionLineDefTD() {
		return riDivisionLineDefTD;
	}

	/**
	 * 返回累计风险下多个再保方案的分保公司设置。
	 * 
	 * @return TransferData,其中：Name为再保方案号，Value为该方案下分保公司集合
	 */
	public TransferData getRIIncomeCompanyTD() {
		return riIncomeCompanyTD;
	}

	/**
	 * 得到累计风险下多个再保方案的再保公司分配额设置。
	 * 返回TransferData,其中：Name为再保方案号，Value为该方案下分保公司分配额设置集合
	 */
	public TransferData getRIRiskDivideTD() {
		return riRiskDivideTD;
	}

	/**
	 * 得到累计风险下多个再保方案的分保方案险种/责任费率表设置。
	 * 
	 * @return TransferData
	 */
	public TransferData getAssociateFeeTableTD() {
		return riAssociateFeeTableTD;
	}

	/**
	 * 返回累积风险下的业务数据提取算法
	 * 
	 * @return TransferData。其中：Name为累积风险编号，Value为该方案下数据检验算法
	 */
	public RIItemCalSet getRIDistillCalSet() {
		System.out.println(" bb: " + riDistillCalSet.size());
		return riDistillCalSet;
	}

	/**
	 * 返回累积风险下的数据检验算法
	 * 
	 * @return TransferData。其中：Name为累积风险编号，Value为该方案下数据检验算法
	 */
	public RIItemCalSet getRICheckCalSet() {
		return riCheckCalSet;
	}

	/**
	 * 返回累计风险下的再保方案分配算法
	 * 
	 * @return TransferData,其中：Name为再保方案号，Value为该方案下再保方案分配算法
	 */
	public TransferData getRIAssignCalTD() {
		return riAssignCalTD;
	}

	/**
	 * 返回累计风险下多个再保方案的参数初始化算法。
	 * 
	 * @return TransferData,其中：Name为再保方案号，Value为该方案下的参数初始化算法
	 */
	public TransferData getRIParamCalTD() {
		return riParamCalTD;
	}

	/**
	 * 返回累计风险下多个再保方案的计算处理类算法。
	 * 
	 * @return TransferData,其中：Name为再保方案号，Value为该方案下的计算处理类
	 */
	public TransferData getRIProcessCalTD() {
		return riProcessCalTD;
	}

	/**
	 * 返回累计风险下多个再保方案的计算处理类
	 * 
	 * @return TransferData
	 */
	public TransferData getRIProcessCalClassTD() {
		return riProcessCalClassTD;
	}

	/**
	 * 返回每一累计风险下多个再保方案的计算项算法。
	 * 
	 * @return TransferData,其中：Name为再保方案号，Value为该方案下的计算项算法
	 */
	public TransferData getRIItemCalTD() {
		return riItemCalTD;
	}

	/**
	 * 返回费率表定义
	 * 
	 * @return RIFeeRateTableDefSet
	 */
	public RIFeeRateTableDefSet getRIFeeRateTableDefSet() {
		return mRIFeeRateTableDefSet;
	}

	/**
	 * 返回费率表轨迹
	 * 
	 * @return RIFeeRateTableTraceSet
	 */
	public RIFeeRateTableTraceSet getRIFeeRateTableTraceSet() {
		return mRIFeeRateTableTraceSet;
	}

	/**
	 * 返回每一累计风险下多个再保方案的费率表批次 [1]:费率表编号,[2]:费率表批次号,[3]:费率表名称,[4]:生效日期,[5]:失效日期
	 * 
	 * @param args
	 *            TransferData
	 */
	public TransferData getRIFeeRateTD() {
		return riFeeRateTD;
	}

	/**
	 * 返回费率表字段定义
	 * 
	 * @return RIFeeRateTableClumnDefSet
	 */
	public RIFeeRateTableClumnDefSet getRIFeeRateTableClumnDefSet() {
		return riFeeRateTableClumnDefSet;
	}

	/**
	 * 返回日志保存路径
	 * 
	 * @return String[][]
	 */
	public String[][] getRILogPath() {
		return riLogPath;
	}

	public RIUnionAccumulateSet getUnionAccumulate() {
		return this.mRIUnionAccumulateSet;
	}

	public static void main(String[] args) {
		try {
			System.out.println(" aaa ");
			RIInitData tRIInitData = RIInitData.getObject("L000000010");
			RIPreceptSet tPreceptSet = tRIInitData.getRIPreceptSet();
			RIFeeRateTableTraceSet tRIFeeRateTableTraceSet = tRIInitData
					.getRIFeeRateTableTraceSet();
			for (int i = 1; i <= tPreceptSet.size(); i++) {
				System.out.println(" 再保方案编号："
						+ tPreceptSet.get(i).getRIPreceptNo());
			}
			RIAssociateFeeTableSet tRIAssociateFeeTableSet = (RIAssociateFeeTableSet) tRIInitData
					.getAssociateFeeTableTD().getValueByName("S003001001");
			for (int i = 1; i <= tRIAssociateFeeTableSet.size(); i++) {
				System.out.println(" aa : "
						+ tRIAssociateFeeTableSet.get(i).getPremFeeTableNo()
						+ "   "
						+ tRIAssociateFeeTableSet.get(i).getComFeeTableNo());
			}

			// String[][]
			// a=(String[][])tRIInitData.getRIFeeRateTD().getValueByName("S003001001");

			// for(int i=0;i<a.length;i++){
			// System.out.println(" aa: "+a[i][0]+"  "+a[i][1] +
			// " "+a[i][2]+"  "+a[i][3]+"  "+a[i][4]);
			// }
			// RIFeeRateTableDefSet tRIFeeRateTableDefSet =
			// tRIInitData.getRIFeeRateTableDefSet();
			// for(int i=1;i<=tRIFeeRateTableDefSet.size();i++){
			// System.out.println(" bb: "+tRIFeeRateTableDefSet.get(i).getFeeTableNo());
			// }
		} catch (Exception ex) {
			System.out.println(" " + ex);
		}
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "InitInfo";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}
}

