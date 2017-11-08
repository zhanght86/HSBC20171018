package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.io.File;

import com.f1j.ss.BookModelImpl;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LZCardImportSchema;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.vschema.LZCardImportSet;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

public class CertifyImportTakeBack implements BusinessService {
private static Logger logger = Logger.getLogger(CertifyImportTakeBack.class);

	// 错误处理类
	public CErrors logErrors = new CErrors();

	public CErrors mErrors = new CErrors();

	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();// 保存界面传入的信息,以及解析后的数据

	private VData tVData = null;// 保存解析后的数据

	private MMap tMap = null;

	// 文件路径
	private String FileName;

	private String SaveExcelPath = "";// 放上传Excel的绝对路径

	public CertifyImportTakeBack() {

	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		logger.debug("CertifyImportTakeBack开始时间:" + PubFun.getCurrentTime());
		logger.debug("-----CertifyImportTakeBack.getInputData -----");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;

		try {
			logger.debug("-----CertifyImportTakeBack.checkData -----");
			// 检查数据
			if (!checkData()) {
				return false;
			}

			logger.debug("-----CertifyImportTakeBack.dealData -----");
			// 把Excel转换为Schema,数据处理
			if (!dealData()) {
				return false;
			}

			logger.debug("-----CertifyImportTakeBack.prepareData -----");
			// 准备数据
			if (!prepareData()) {
				return false;
			}

			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tVData, "")) {
				CError.buildErr(this, "批量导入失败！");
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "导入文件格式有误!");
			return false;
		}

		logger.debug("CertifyImportTakeBack结束时间:" + PubFun.getCurrentTime());

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);

		if (mTransferData == null && mTransferData == null) {
			CError.buildErr(this, "数据传输失败!");
			return false;
		}

		FileName = (String) mTransferData.getValueByName("FileName");
		logger.debug("后台获得的FileName:" + FileName);

		SaveExcelPath = (String) mTransferData.getValueByName("SaveExcelPath");
		logger.debug("后台获得的SaveExcelPath:" + SaveExcelPath);

		return true;
	}

	/**
	 * 解析处理
	 * 
	 * @return
	 */
	private boolean dealData() {
		// 校验待导入的文件是否存在
		if (!initImportFile())
			return false;
		logger.debug("*************结束校验待导入的文件*************");

		// 解析Excel中的数据生成记录
		if (!parseData())
			return false;
		logger.debug("*************结束执行Excel文件的解析*************");

		return true;

	}

	/**
	 * 准备传往工作流处理的数据
	 * 
	 */
	private boolean prepareData() {

		tVData = new VData();
		tVData.add(tMap);

		return true;

	}

	/**
	 * 根据Excel的内容生成出险人数据
	 * 
	 */
	private boolean parseData() {

		BookModelImpl bmTemplate = new com.f1j.ss.BookModelImpl();
		try {
			bmTemplate.read(SaveExcelPath, new com.f1j.ss.ReadParams());
			int mSumCount = bmTemplate.getLastRow();
			logger.debug("Excel文件一共有" + mSumCount + "行");

			if (mSumCount <= 0) {
				CError.buildErr(this, "上传文件记录为空，请填写后重新上传!");
				return false;
			}
			if(mSumCount >10000)
			{
				CError.buildErr(this, "上传文件记录超过10000条，请减少记录条数重新上传!");
				return false;
			}
			long cardnum=0;
			for (int nIndex = 1; nIndex <= mSumCount; nIndex++)
			{
				//增加单证数量的控制
				if( !"".equals(bmTemplate.getText(nIndex, 2)) && !"".equals(bmTemplate.getText(nIndex, 1)))
				cardnum=cardnum+ CertifyFunc.bigIntegerDiff(bmTemplate.getText(nIndex, 2), bmTemplate.getText(nIndex, 1)) + 1;	

			}
			if(cardnum>10000)
			{
				CError.buildErr(this, "一次单证操作的单证数量不能超过10000张，请减少单证数量");
				return false;
			}
			// 产生回收清算单号
			String tTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO", PubFun
					.getNoLimit(mGlobalInput.ComCode));

			LZCardImportSchema tLZCardImportSchema;
			LZCardImportSet tLZCardImportSet = new LZCardImportSet();
			LZCardSchema tLZCardSchema;
			LZCardSet tLZCardSet;
			for (int i = 1; i <= mSumCount; i++) {
				logger.debug("第" + i + "条记录的单证编码：" + bmTemplate.getText(i, 0));
				logger.debug("第" + i + "条记录的单证起始单号: " + bmTemplate.getText(i, 1));
				logger.debug("第" + i + "条记录的单证终止单号: " + bmTemplate.getText(i, 2));
				logger.debug("第" + i + "条记录的单证经办人: " + bmTemplate.getText(i, 3));
				ExeSQL mExeSQL = new ExeSQL();
				
				if(bmTemplate.getText(i, 3)==null ||"".equals( bmTemplate.getText(i, 3)) )
				{
					CError.buildErr(this, "上传文件中的第"+i+"条单证缴销提交人为空");
					continue;
				}
				String userSql ="select distinct usercode from lduser where usercode='"+"?usercode?"+"'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(userSql);
				sqlbv.put("usercode", bmTemplate.getText(i, 3));
				String userCode = mExeSQL.getOneValue(sqlbv);
				if(userCode==null  || "".equals(userCode))
				{

					CError.buildErr(this, "上传文件中的第"+i+"条单证缴销提交人不存在");
					continue;

				}

				tLZCardImportSchema = new LZCardImportSchema();
				tLZCardImportSchema.setCertifyCode(bmTemplate.getText(i, 0));
				tLZCardImportSchema.setStartNo(bmTemplate.getText(i, 1));
				tLZCardImportSchema.setEndNo(bmTemplate.getText(i, 2));
				tLZCardImportSchema.setSumCount((int) CertifyFunc.bigIntegerDiff(bmTemplate
						.getText(i, 2), bmTemplate.getText(i, 1)) + 1);
				tLZCardImportSchema.setHandler(bmTemplate.getText(i, 3));
				tLZCardImportSchema.setOperateFlag("1");// 1、缴销导入 2、销毁导入
				tLZCardImportSchema.setOperator(mGlobalInput.Operator);
				tLZCardImportSchema.setMakeDate(PubFun.getCurrentDate());
				tLZCardImportSchema.setMakeTime(PubFun.getCurrentTime());
				tLZCardImportSchema.setModifyDate(PubFun.getCurrentDate());
				tLZCardImportSchema.setModifyTime(PubFun.getCurrentTime());
				tLZCardImportSchema.setManageCom(mGlobalInput.ComCode);

				tLZCardSchema = new LZCardSchema();
				tLZCardSchema.setCertifyCode(bmTemplate.getText(i, 0));
				tLZCardSchema.setStartNo(bmTemplate.getText(i, 1));
				tLZCardSchema.setEndNo(bmTemplate.getText(i, 2));
				tLZCardSchema.setStateFlag("5");// 5、手工缴销，人工触发。
				tLZCardSchema.setHandler(bmTemplate.getText(i, 3));
				tLZCardSchema.setHandleDate(PubFun.getCurrentDate());

				tLZCardSet = new LZCardSet();
				tLZCardSet.add(tLZCardSchema);

				// 准备传输数据 VData
				VData vData = new VData();
				vData.addElement(mGlobalInput);
				vData.addElement(tLZCardSet);

				// 调用缴销程序
				CertTakeBackBL tCertTakeBackBL = new CertTakeBackBL();
				if (!tCertTakeBackBL.submitData(vData, "INSERT")) {
					tLZCardImportSchema.setState("N");// N、失败
					tLZCardImportSchema.setTakeBackNo(tTakeBackNo);
					tLZCardImportSet.add(tLZCardImportSchema);
//					CError.buildErr(this, tCertTakeBackBL.mErrors.getFirstError());
					mErrors.copyAllErrors(tCertTakeBackBL.mErrors);
					continue;
				} else {
					tLZCardImportSchema.setState("Y");// Y、成功
					tLZCardImportSchema.setTakeBackNo(tTakeBackNo);
					tLZCardImportSet.add(tLZCardImportSchema);
				}
			}

			tMap = new MMap();
			tMap.put(tLZCardImportSet, "INSERT");
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "解析上传文件错误!");
			return false;
		}

		return true;
	}

	/**
	 * 初始化上传文件
	 * 
	 * @return
	 */
	private boolean initImportFile() {
		logger.debug("SaveExcelPath-----" + SaveExcelPath);

		File tFile = new File(SaveExcelPath);
		if (!tFile.exists()) {
			CError.buildErr(this, "未上传文件到指定路径:" + SaveExcelPath);
			return false;
		}

		return true;
	}

	/**
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "全局信息传输失败!");
			return false;
		}

		return true;
	}

	/**
	 * 校验一行数据
	 * 
	 * @return boolean
	 */
	private boolean checkOne(LZCardSchema tLZCardSchema) {

		// 格式化数据，为回收单证单独写的
		LZCardSet mLZCardSet = CertifyFunc.formatCardListTakeBack(tLZCardSchema);
		if (mLZCardSet == null) {
			mErrors.copyAllErrors(CertifyFunc.mErrors);
			return false;
		}
		// 校验起止号之间的单证是否都拆分成单条记录，是否都可以核销
		long count = CertifyFunc.bigIntegerDiff(tLZCardSchema.getEndNo(), tLZCardSchema
				.getStartNo()) + 1;
		if (mLZCardSet.size() != count) {
			CError.buildErr(this, tLZCardSchema.getStartNo() + "-" + tLZCardSchema.getEndNo()
					+ "之间不能核销的单证数量为:" + (count - mLZCardSet.size()));
			return false;
		}

		return true;
	}
	
	public VData getResult() {
		tVData.clear();
		return tVData;
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public static void main(String[] args) {
//		CertifyBatchReportIn tPGI = new CertifyBatchReportIn();
//		VData tVData = new VData();
//		TransferData tTransferData = new TransferData();
//		tTransferData.setNameAndValue("FileName", "20050426.xls");
//		tTransferData.setNameAndValue("FilePath", "E:");
//		GlobalInput tG = new GlobalInput();
//		tG.Operator = "claim";
//		tG.ManageCom = "86";
//
//		long a = System.currentTimeMillis();
//		logger.debug(a);
//		tVData.add(tTransferData);
//		tVData.add(tG);
//		tPGI.submitData(tVData, "");
//		logger.debug(System.currentTimeMillis() - a);
	}

}
