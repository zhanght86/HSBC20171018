package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.sinosoft.lis.db.LYBankLogDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubLock;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 银行数据转换到文件模块
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class CheckWriteToFileBL {
private static Logger logger = Logger.getLogger(CheckWriteToFileBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// 业务数据
	private TransferData inTransferData = new TransferData();
	private String serialNo = "";

	public CheckWriteToFileBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"WRITE"和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");
		// 增加并发校验
		String key = "Bank" + serialNo;
		PubLock tPubLock = new PubLock();
		if (!tPubLock.lock(key, "准备" + serialNo + "发盘文件")) {
			this.mErrors.copyAllErrors(tPubLock.mErrors);
			return false;
		}
		try {
			// 进行业务处理
			if (!dealData())
				return false;
			logger.debug("---End dealData---");

		} finally {
			if (!tPubLock.unLock(key)) {
				CError.buildErr(this, serialNo + "解锁失败:"
						+ tPubLock.mErrors.getFirstError());
				return false;
			}
		}
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			inTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			serialNo = (String) inTransferData.getValueByName("serialNo");
			if (serialNo == null || serialNo.equals(""))
				throw new RuntimeException("批次号为空");
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WriteToFileBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private String getFilePath() {
		LDSysVarSchema tLDSysVarSchema = new LDSysVarSchema();

		tLDSysVarSchema.setSysVar("SendToBankFilePath");
		tLDSysVarSchema = tLDSysVarSchema.getDB().query().get(1);

		String basepath = tLDSysVarSchema.getSysVarValue();
		if (basepath == null || basepath.equals("")) {
			throw new IllegalArgumentException("系统发盘基路径为空");
		}
		return basepath;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 生成银行文件数据
			if (mOperate.equals("WRITE")) {

				LYBankLogDB tLYBankLogDB = new LYBankLogDB();
				tLYBankLogDB.setSerialNo((String) inTransferData
						.getValueByName("serialNo"));
				if (!tLYBankLogDB.getInfo())
					throw new RuntimeException("批次号错误"
							+ tLYBankLogDB.getSerialNo());

				String outfile = tLYBankLogDB.getOutFile();
				if (outfile == null || outfile.equals("")) {
					throw new RuntimeException("发盘文件为空");
				}
				File file = new File(getFilePath(), outfile);
				if (!file.exists() || !file.isFile()) {
					throw new RuntimeException("发盘文件不存在");
				}
				File checkfile = new File(file.getParentFile(), "CK"
						+ file.getName());
				logger.debug("校验文件:" + checkfile.getAbsolutePath());
				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(checkfile)));

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(file)));

				try {
					if ("0103".equals(tLYBankLogDB.getBankCode()))
						create0103File(writer, reader);
					else {
						throw new IllegalArgumentException("不支持的银行编码");
					}
				} catch (Exception e) {
					throw e;
				} finally {
					reader.close();
					writer.close();
				}
				mResult.clear();
				mResult.add(checkfile.getAbsolutePath());

			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WriteToFileBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private void create0103File(BufferedWriter writer, BufferedReader reader)
			throws IOException {
		String line;
		int num = 0;
		while ((line = reader.readLine()) != null) {
			num++;
			if (num < 7)
				continue;
			if (line.equals(""))
				continue;
			if (line.startsWith("#"))
				continue;
			String[] cs = line.split("\\|");
			if (cs.length != 18)
				throw new IllegalArgumentException("第" + num + "行格式错误");
			//要求去除空格
			writer.write(cs[6].trim() + "|" + cs[7].trim() + "|" + cs[5].trim() + "||\r\n");
		}
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}
}
