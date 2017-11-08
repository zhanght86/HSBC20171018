/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCCGrpSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LMRiskFormDB;
import com.sinosoft.lis.db.LDSysErrLogDB;
import com.sinosoft.lis.pubfun.EasyScanQueryBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LABranchGroupSchema;
import com.sinosoft.lis.schema.LCCGrpSpecSchema;
import com.sinosoft.lis.schema.LCContPrintSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolBakSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.vschema.LCCGrpSpecSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpAddressSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.utility.CBlob;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CMySQLBlob;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLDataBlob;
import com.sinosoft.utility.XMLDataFormater;
import com.sinosoft.utility.XMLDataList;
import com.sinosoft.utility.XMLDataMine;
import com.sinosoft.utility.XMLDataTag;
import com.sinosoft.utility.XMLDataset;
import com.sinosoft.utility.XMLDatasets;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * ClassName: LCGrpContF1PBL
 * </p>
 * <p>
 * Description: 保单xml文件生成，数据准备类
 * 保单重新打印---重新查询相关信息，生成XML文件
 * 保单补打   ---直接从LCContPrint中读取XML文件信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: LIS
 * @CreateDate：2002-11-04 modify by zhangxing modify time 2006-06-02
 * @ReWiriter pst
 * @ReWiriteDate：2009-03-10
 */
public class LCGrpContF1PBL {
private static Logger logger = Logger.getLogger(LCGrpContF1PBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private String mOperate = "";

	//业务处理相关变量

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();

	private String mszTemplatePath = null;

	private XMLDatasets mXMLDatasets = null;

	private String tCurMakeDate = PubFun.getCurrentDate();

	private String tCurMakeTime = PubFun.getCurrentTime();

	/**记录本次保单成功和是失败总计*/
	public int tSucc = 0, tFail = 0;

	private ExeSQL tExeSQL = new ExeSQL();

	public String mContent = "";

	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	public LCGrpContF1PBL() {

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!cOperate.equals("PRINT") && !cOperate.equals("REPRINT")) {
				CError.buildErr(this, "不支持的操作字符串!");
				return false;
			}

			mOperate = cOperate;

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			if (mOperate.equals("PRINT")) {
				// 准备所有要打印的数据
				if (!getPrintData()) {
					return false;
				}
			}

			if (mOperate.equals("REPRINT")) {
				if (!getPrintData()) {
					return false;
				}
			}

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "准备打印数据异常!");

			return false;
		}
	}

	/**
	 * 从输入数据中得到所有对象
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		//全局变量
		if (mOperate.equals("PRINT")) {
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));
			mLCGrpContSet.set((LCGrpContSet) cInputData.getObjectByObjectName(
					"LCGrpContSet", 0));
			mszTemplatePath = (String) cInputData.getObjectByObjectName(
					"String", 0);
		}

		if (mOperate.equals("REPRINT")) {
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));
			mLCGrpContSet.set((LCGrpContSet) cInputData.getObjectByObjectName(
					"LCGrpContSet", 0));
		}

		return true;
	}

	private boolean getPrintData() {

		try {
			if(mLCGrpContSet.size()>0)
			{
				mXMLDatasets = new XMLDatasets();
				mXMLDatasets.createDocument();
				String LockNo = "";
				for (int nIndex = 0; nIndex < mLCGrpContSet.size(); nIndex++) {

					mErrors.clearErrors();
					XMLDatasets tXMLDatasets = new XMLDatasets();
					tXMLDatasets.createDocument();

					LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
					LCGrpContDB tLCGrpContDB = new LCGrpContDB();
					tLCGrpContDB.setGrpContNo(mLCGrpContSet.get(nIndex + 1)
							.getGrpContNo());
					if (tLCGrpContDB.mErrors.needDealError()) {
						CError.buildErr(this, "保单"
								+ tLCGrpContSchema.getGrpContNo() + "保单数据不完整");
						// 插入错误日志
						insertErrLog("保单" + tLCGrpContSchema.getGrpContNo()
								+ "保单数据不完整", tLCGrpContSchema);
						tFail++;
						continue;
					}
					if (!tLCGrpContDB.getInfo()) {
						CError.buildErr(this, "保单"
								+ tLCGrpContSchema.getGrpContNo() + "保单数据不完整");
						// 插入错误日志
						insertErrLog("保单" + tLCGrpContSchema.getGrpContNo()
								+ "保单数据不完整", tLCGrpContSchema);
						tFail++;
						continue;
					}

					tLCGrpContSchema = tLCGrpContDB.getSchema();
					LockNo = "";
					LockNo = tLCGrpContSchema.getGrpContNo();
					if (!mPubLock.lock(LockNo, "LC0007", mGlobalInput.Operator)) {
						CError tError = new CError(mPubLock.mErrors.getLastError());
						this.mErrors.addOneError(tError);
						tFail++;
						continue;
					}
					XMLDataset tXMLDataset = null;
					// 如果是重打
					if (tLCGrpContSchema.getPrintCount() == -1) {

						boolean bFlag = getGrpContDataSetEx(tLCGrpContSchema);
						if (!bFlag) {
							CError.buildErr(this, "保单 "
									+ tLCGrpContSchema.getGrpContNo()
									+ ")准备重新打印数据失败!");
							insertErrLog(mErrors.getFirstError(), tLCGrpContSchema);
							tFail++;
							continue;
						}
						if (!SaveOneGrpContData(tLCGrpContSchema)) {
							CError.buildErr(this, "提交数据库出错！");
							insertErrLog(mErrors.getFirstError(), tLCGrpContSchema);
							tFail++;
							continue;
						}
					} else { // 如果是正常打印
						tXMLDataset = getGrpContDataSet(tLCGrpContSchema);
						if (tXMLDataset == null) {
							CError.buildErr(this, "保单("
									+ tLCGrpContSchema.getGrpContNo()
									+ ")准备打印数据失败!");

							insertErrLog(mErrors.getFirstError(), tLCGrpContSchema);
							tFail++;
							continue;
						} else {
							// 添加到局部变量，实现每单独立提交
							tXMLDatasets.addXMLDataset(tXMLDataset);
							if (!SaveOneGrpContData(tLCGrpContSchema, tXMLDatasets)) {
								CError.buildErr(this, "提交数据库出错！");
								insertErrLog(mErrors.getFirstError(),
										tLCGrpContSchema);
								tFail++;
								continue;
							}
						}
					}
					mXMLDatasets.addXMLDataset(tXMLDataset);
					tSucc++;
					mPubLock.unLock();
				}
				mResult.clear();
				logger.debug("add inputstream to mResult");
				mResult.add(mXMLDatasets.getInputStream());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			this.mContent += ex.getMessage();
			tFail++;
		} finally {
			mPubLock.unLock();			
			this.mContent += "共计" + tSucc + "个保单处理成功，" + "共计" + tFail
					+ "个保单处理失败.";
			logger.debug(this.mContent);
		}
		return true;
	}

	/**保单重打*/
	private boolean SaveOneGrpContData(LCGrpContSchema tLCGrpContSchema) {
		MMap map = new MMap();
		VData mResult = new VData();
		PubSubmit tPubSubmit = new PubSubmit();

		map.put("update lcgrpcont set printcount='1',ModifyDate='"
				+ tCurMakeDate + "',ModifyTime='" + tCurMakeTime
				+ "',Operator='" + mGlobalInput.Operator
				+ "' where  grpcontno='" + tLCGrpContSchema.getGrpContNo()
				+ "'", "UPDATE");
		mResult.clear();
		mResult.add(map);
		// 数据提交
		if (!tPubSubmit.submitData(mResult, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError.buildErr(this, "数据提交失败");
			return false;
		}
		return true;
	}

	private boolean SaveOneGrpContData(LCGrpContSchema tLCGrpContSchema,
			XMLDatasets tXMLDatasets) {
		
//		if(!save(tXMLDatasets))
//		{
//			CError.buildErr(this, "保存保单数据失败");
//			return false;
//		}
		
		MMap map = new MMap();
		VData mResult = new VData();
		PubSubmit tPubSubmit = new PubSubmit();
		LCContPrintSchema tLCContPrintSchemaMain = new LCContPrintSchema();
		tLCContPrintSchemaMain.setContNo(tLCGrpContSchema.getGrpContNo());
		tLCContPrintSchemaMain.setManageCom(tLCGrpContSchema.getManageCom());
		tLCContPrintSchemaMain.setPrtFlag("N");
		tLCContPrintSchemaMain.setPrtTimes(1);
		tLCContPrintSchemaMain.setContType("2");//团单
		tLCContPrintSchemaMain.setMakeDate(tCurMakeDate);
		tLCContPrintSchemaMain.setMakeTime(tCurMakeTime);
		tLCContPrintSchemaMain.setOperator(mGlobalInput.Operator);
		tLCContPrintSchemaMain.setModifyDate(tCurMakeDate);
		tLCContPrintSchemaMain.setModifyTime(tCurMakeTime);

		InputStream ins = getXMLInputStream(tXMLDatasets);

		tLCContPrintSchemaMain.setContInfo(ins);
		map.put("delete from LCContPrint where ContNo='"
				+ tLCGrpContSchema.getGrpContNo() + "'", "DELETE");
		map.put(tLCContPrintSchemaMain, "BLOBINSERT");

		map.put("update lcgrpcont set printcount='1',ModifyDate='"
				+ tCurMakeDate + "',ModifyTime='" + tCurMakeTime
				+ "',Operator='" + mGlobalInput.Operator
				+ "' where  grpcontno='" + tLCGrpContSchema.getGrpContNo()
				+ "'", "UPDATE");
		mResult.clear();
		mResult.add(map);
		// 数据提交
		if (!tPubSubmit.submitData(mResult, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError.buildErr(this, "数据提交失败");
			return false;
		}
		return true;
	}

	/***得到XMLDataset的输入流*/
	private InputStream getXMLInputStream(XMLDatasets tXMLDatasets) {
		try {
			Document doc = tXMLDatasets.getDocument();
			Element ele = doc.getRootElement().getChild("DATASET");
			doc = new Document(ele);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (!this.output(baos, doc)) {
				baos.close();
				return null;
			}
			baos.close();

			return new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 将XML文件的内容输出到一个输出流中
	 * 
	 * @param outputStream
	 *            指定的输出流
	 * @return boolean
	 */
	public boolean output(OutputStream outputStream, Document tDocument) {
		if (outputStream == null) {
			return false;
		}
		try {
			XMLOutputter outputter = new XMLOutputter("", false, "UTF-8");
			outputter.output(tDocument, outputStream);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	  //保存操作
	  private boolean save(XMLDatasets tXMLDatasets)
	  {
	    boolean tReturn =true;
	    logger.debug("Start Save...");
	    Connection conn=DBConnPool.getConnection();

	    if (conn==null)
	    {
	      // @@错误处理
	      CError tError = new CError();
	      tError.moduleName = "LCGrpContF1PBL";
	      tError.functionName = "saveData";
	      tError.errorMessage = "数据库连接失败!";
	      this.mErrors .addOneError(tError) ;
	      return false;
	    }
	    try {
	      conn.setAutoCommit(false);	      
	      // 保存数据流
	      saveDataStream(tXMLDatasets, mGlobalInput, conn);
	      conn.commit() ;
	      conn.close();
	      logger.debug("commit end");
	    }
	    catch (Exception ex)
	    {
	      // @@错误处理
	      CError tError =new CError();
	      tError.moduleName="LCGrpContF1PBL";
	      tError.functionName="submitData";
	      tError.errorMessage=ex.getMessage();
	      this.mErrors.addOneError(tError);
	      try{conn.rollback(); conn.close();} catch(Exception e){}
	      tReturn=false;
	    }

	    return tReturn;
	  }
	  /**
	   * 将所有得到的打印数据按照主险保单号存入数据库，为以后的补打保单做准备
	   * @param aXMLDataSets
	   * @param aGlobalInput
	   * @param conn
	   * @throws Exception
	   */
	  private void saveDataStream(XMLDatasets aXMLDataSets,
	                              GlobalInput aGlobalInput,
	                              Connection conn)
	      throws Exception {

	    Document doc = aXMLDataSets.getDocument();
	    List list = doc.getRootElement().getChildren("DATASET");

	    for(int nIndex = 0; nIndex < list.size(); nIndex ++) {
	      Element ele = (Element)list.get(nIndex);

	      //
	      // Build a new element from the content of old element.
	      // By doing this, we can detach element from document
	      //
	      ele = new Element("DATASET").setMixedContent(ele.getMixedContent());

	      // Document newDoc = new Document(new Element("DATASETS").addContent(ele));
	      Document newDoc = new Document(ele);
	      saveOneDataStream(newDoc, aGlobalInput, conn);
	    }

	  }

	  /**
	   * 保存一条保单打印的数据
	   * @param doc
	   * @param aGlobalInput
	   * @param conn
	   * @throws Exception
	   */
	  private void saveOneDataStream(Document doc,
	                                 GlobalInput aGlobalInput,
	                                 Connection conn)
	      throws Exception {
	    //modify by yt
	    COracleBlob tCOracleBlob = new COracleBlob();
	    CMySQLBlob tCMySQLBlob = new CMySQLBlob();
	    
	    try {
	      String strPolNo =
	          doc.getRootElement().getChildText("LCGrpPol.GrpPolNo");

	      String strCurDate = PubFun.getCurrentDate();
	      String strCurTime = PubFun.getCurrentTime();

	      // 得到数据输出对象
	      String szSQL = "DELETE FROM LCContPrint WHERE ContNo = '" + strPolNo + "'";
	      if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	    	  if (!tCOracleBlob.DeleteBlobRecord(szSQL,conn)) {
	    		  throw  new Exception("删除数据失败！"+szSQL);
	    	  }
	      }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	    	  if(!tCMySQLBlob.DeleteBlobRecord(szSQL,conn)) {
		         throw  new Exception("删除数据失败！"+szSQL);
	      }
	
		    }
	      szSQL = "INSERT INTO LCContPrint(ContNo,prtflag, PrtTimes, Operator, MakeDate, MakeTime, ModifyDate, ModifyTime, ContInfo, ContType) VALUES('"
	            + strPolNo + "', "
	            + "'Y', "
	            + "1, '"
	            + aGlobalInput.Operator + "', '"
	            + strCurDate + "', '"
	            + strCurTime + "', '"
	            + strCurDate + "', '"
	            + strCurTime + "', empty_blob(), '2')";

	      if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	    	  if (!tCOracleBlob.InsertBlankBlobRecord(szSQL,conn))
	    	  {throw  new Exception("插入数据失败！"+szSQL);}
	      }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	    	  if (!tCMySQLBlob.InsertBlankBlobRecord(szSQL,conn))
	    	  {throw  new Exception("插入数据失败！"+szSQL);}	
	      }
	      szSQL = " and ContNo = '" + strPolNo + "' ";
	      if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	    	  if (!tCOracleBlob.UpdateBlob(doc,"LCContPrint","ContInfo",szSQL,conn))
	          {throw  new Exception("修改数据失败！"+szSQL);}
	      }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	    	  if (!tCMySQLBlob.UpdateBlob(doc,"LCContPrint","ContInfo",szSQL,conn))
	          {throw  new Exception("修改数据失败！"+szSQL);}		
	      }
	    } catch (Exception ex) {
	      ex.printStackTrace();
	      throw ex;
	    }
	  }
	private boolean getScanPic(XMLDataset xmlDataset,
			LCGrpContSchema aLCGrpContSchema) {
		XMLDataList xmlDataList = new XMLDataList();

		xmlDataList.setDataObjectID("PicFile");

		xmlDataList.addColHead("FileName");
		xmlDataList.addColHead("PageIndex");
		xmlDataList.addColHead("FileUrl");
		xmlDataList.buildColHead();

		// 查出投保单影像件,分类
		String tSQL = "select code from ldcode where codetype='gpolprint' order by othersign";
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tSQL);
		int tArrLen = tSSRS.MaxRow;
		String tSubType = "";
		if (tArrLen > 0) {
			for (int i = 0; i < tArrLen; i++) {
				tSubType = tSSRS.GetText(i + 1, 1);

				VData vData = new VData();
				vData.add(aLCGrpContSchema.getPrtNo());
				vData.add("12");
				vData.add("TB");
				vData.add(tSubType);
				EasyScanQueryBL tEasyScanQueryBL = new EasyScanQueryBL();
				//只有投保单影像件才报错
				if (!tEasyScanQueryBL.submitData(vData, "QUERY||1")
						&& "TB1002".equals(tSubType)) {
//					CError.buildErr(this, "查询保单影像件失败!");
//					return false;
				} else {
					vData.clear();
					vData = tEasyScanQueryBL.getResult();

					VData tData = new VData();
					tData = (VData) vData.getObjectByObjectName("VData", 0);

					String strFileUrl = "";
					String strFileName = "";
					if (tData != null && tData.size() > 0) {
						for (int nIndex = 0; nIndex < tData.size(); nIndex++) {
							strFileUrl = (String) tData.get(nIndex);
							strFileUrl = strFileUrl.substring(0, strFileUrl
									.lastIndexOf("."))
									+ ".tif";
							strFileName = strFileUrl.substring(strFileUrl
									.lastIndexOf("/") + 1);

							xmlDataList.setColValue("FileName", strFileName);
							xmlDataList.setColValue("PageIndex", nIndex);
							xmlDataList.setColValue("FileUrl", strFileUrl);
							xmlDataList.insertRow(0);
						}
					}
				}
			}
		}

		if (xmlDataList != null) {
			xmlDataset.addDataObject(xmlDataList);
		}
		//增加参考结点
		Element table = new Element("PicFileEnd");
		xmlDataset.getRealElement().addContent(table);
		Element row = new Element("ROW");
		table.addContent(row);
		row.addContent(new Element("COL1").addContent(""));
		Element end = new Element("END");
		table.addContent(end);
		Element row1 = new Element("ROW");
		end.addContent(row1);
		row1.addContent(new Element("COL1").addContent(""));
		return true;
	}

	/**
	 * 取得暂收费信息
	 * @param xmlDataset
	 * @param aLCPolSet
	 * @throws Exception
	 */
	private boolean getReceipt(XMLDataset xmlDataset,
			LCGrpContSchema tLCGrpContSchema, LCGrpPolSet aLCGrpPolSet) {
		if (tLCGrpContSchema.getLostTimes() > 0) {
			return true; //遗失补发不打印收据
		}
		Element table = new Element("Receipt");
		xmlDataset.getRealElement().addContent(table);
		Element head = new Element("HEAD");
		table.addContent(head);

		double dPremSum = 0;
		for (int i = 0; i < aLCGrpPolSet.size(); i++) {
			dPremSum += aLCGrpPolSet.get(i + 1).getPrem();
		}

		head.addContent(new Element("LJTempFee.PayMoneySum")
				.addContent(format(dPremSum)));
		head.addContent(new Element("LJTempFee.PayMoneySum1").addContent(PubFun
				.getChnMoney(dPremSum)));

		String strSQL = "SELECT TempFeeNo, Operator FROM LJTempFee WHERE OtherNo = '"
				+ aLCGrpPolSet.get(1).getGrpContNo() + "'";

		SSRS ssRs = new ExeSQL().execSQL(strSQL);

		if (ssRs.ErrorFlag || (ssRs.MaxRow == 0)) {
			CError.buildErr(this, "找不到该个人保单的暂交费数据");
			return false;
		}
		head.addContent(new Element("LJTempFee.Handler").addContent(ssRs
				.GetText(1, 2)));
		head.addContent(new Element("LJTempFee.TempFeeNo").addContent(ssRs
				.GetText(1, 1)));

		for (int i = 1; i <= aLCGrpPolSet.size(); i++) {
			Element row = new Element("ROW");
			table.addContent(row);
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(aLCGrpPolSet.get(i).getRiskCode());
			tLMRiskDB.getInfo();

			row.addContent(new Element("COL1").addContent(tLMRiskDB
					.getRiskName()));
			row.addContent(new Element("COL2").addContent(Integer
					.toString(aLCGrpPolSet.get(i).getPayIntv())));
			row.addContent(new Element("COL3").addContent(format(aLCGrpPolSet
					.get(i).getPrem())));
		}
		return true;
	}

	/**
	 * 取得回执打印信息
	 * @param xmlDataset
	 * @param aLCPolSet
	 * @throws Exception
	 */
	private boolean getReply(XMLDataset xmlDataset, LCGrpPolSet aLCGrpPolSet) {
		Element table = new Element("Reply");
		xmlDataset.getRealElement().addContent(table);
		Element row = new Element("ROW");
		table.addContent(row);
		row.addContent(new Element("COL1").addContent(""));
		Element end = new Element("END");
		table.addContent(end);
		Element row1 = new Element("ROW");
		end.addContent(row1);
		row1.addContent(new Element("COL1").addContent(""));
		return true;
	}

	private boolean getRiskInfo(XMLDataset xmlDataset, LCGrpPolSet aLCGrpPolSet) {
		// Get all LCGrpConts of the same prtno
		XMLDataList xmlDataList = new XMLDataList("RiskInfo");

		xmlDataList.addColHead("RiskCode");
		xmlDataList.addColHead("RiskName");
		xmlDataList.addColHead("GrpPolNo");
		xmlDataList.addColHead("Peoples");
		xmlDataList.addColHead("InsuPeriod");
		xmlDataList.addColHead("Prem");
		xmlDataList.addColHead("PayIntv");
		xmlDataList.addColHead("Remark");
		xmlDataList.addColHead("PayMoney");
		xmlDataList.addColHead("ManageFeeRate");
		xmlDataList.addColHead("Remark1");
		xmlDataList.addColHead("GrpPrem");
		xmlDataList.addColHead("Prem1");
		xmlDataList.addColHead("PremSumChn");
		xmlDataList.addColHead("AcctPremSum");
		xmlDataList.addColHead("Template");
		xmlDataList.addColHead("SubRiskFlag");

		xmlDataList.buildColHead();

		double dPremSum = 0;
		double dPayMoneySum = 0;

		// 加入伤残给付和医院列表的信息
		String strInjuryGetFlag = "N";
		String strHospitalFlag = "N";
		String strRiskCode = "";
		String strTemp = "";

		LMRiskAppDB tLMRiskAppDB = null;

		for (int nIndex = 0; nIndex < aLCGrpPolSet.size(); nIndex++) {
			LCGrpPolSchema tLCGrpPolSchema = aLCGrpPolSet.get(nIndex + 1);

			strRiskCode = tLCGrpPolSchema.getRiskCode();

			tLMRiskAppDB = new LMRiskAppDB();

			tLMRiskAppDB.setRiskCode(strRiskCode);

			if (!tLMRiskAppDB.getInfo()) {
				CError.buildErr(this, "没有找到该险种的信息!");
				return false;
			}

			strTemp = tLMRiskAppDB.getNeedPrintGet();

			if ((strTemp != null) && strTemp.equals("1")) {
				strInjuryGetFlag = "Y";
			}

			strTemp = tLMRiskAppDB.getNeedPrintHospital();

			if ((strTemp != null) && strTemp.equals("1")) {
				strHospitalFlag = "Y";
			}

			xmlDataList.setColValue("RiskCode", tLCGrpPolSchema.getRiskCode());
			xmlDataList.setColValue("RiskName", tLMRiskAppDB.getRiskName());
			xmlDataList.setColValue("GrpPolNo", tLCGrpPolSchema.getGrpPolNo());
			xmlDataList.setColValue("Peoples", tLCGrpPolSchema.getPeoples2());
			if("".equals(genInsuPeriod(tLCGrpPolSchema)))
			{
				CError.buildErr(this, "该团单下的个单没有保单失效日期!");
				return false;
			}else
			{
				xmlDataList.setColValue("InsuPeriod",
						genInsuPeriod(tLCGrpPolSchema));
			}

			xmlDataList.setColValue("Prem", format(tLCGrpPolSchema.getPrem()));
			xmlDataList.setColValue("PayIntv", tLCGrpPolSchema.getPayIntv());
			xmlDataList.setColValue("Remark", tLCGrpPolSchema.getRemark());

			xmlDataList.setColValue("PayMoney", format(tLCGrpPolSchema
					.getPrem()));
			dPayMoneySum += tLCGrpPolSchema.getPrem();

			//      String strSQL = "SELECT SumActuPayMoney FROM LJAPay";
			//      strSQL += " WHERE IncomeNo = '" + tLCGrpContSchema.getGrpContNo() + "'";
			//      strSQL += " AND RiskCode = '" + tLCGrpContSchema.getRiskCode() + "'";
			//      strSQL += " AND IncomeType = '1' ORDER BY MakeDate, MakeTime";
			//
			//      SSRS ssRs = new ExeSQL().execSQL(strSQL);
			//
			//      if( ssRs.ErrorFlag || ssRs.MaxRow == 0 ) {
			//        logger.debug("获取暂交费数据失败");
			//        xmlDataList.setColValue("PayMoney", "0");
			//        fPayMoneySum += 0;
			//      } else {
			//        xmlDataList.setColValue("PayMoney", ssRs.GetText(1, 1));
			//        fPayMoneySum += Double.parseDouble(ssRs.GetText(1, 1));
			//      }
			String sql1 = "select PolNo from lcpol " + "where GrpPolNo='"
					+ tLCGrpPolSchema.getGrpPolNo() + "' " + " and RowNum <=1";

			ExeSQL aExeSQL = new ExeSQL();
			SSRS aSSRS = aExeSQL.execSQL(sql1);

			if (aSSRS == null) {
				CError.buildErr(this, "找不到这个团体保单下面的个人保单!");
				return false;
			}

			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(aSSRS.GetText(1, 1));

			LCPremSet tLCPremSet = new LCPremSet();
			tLCPremSet = tLCPremDB.query();

			double GrpPrem = 0;
			double IndiPrem = 0;
			String sql = "";
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();

			if (tLCPremSet.size() == 2) {
				//集体交费累计和
				sql = "select sum(prem) from lcprem where polno in (select polno from lcpol where grppolno='"
						+ tLCGrpPolSchema.getGrpPolNo()
						+ "') and trim(payplancode) like '%101'";
				tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sql);
				GrpPrem = Double.parseDouble(tSSRS.GetText(1, 1));

				//个人交费累计和
				sql = "select sum(prem) from lcprem where polno in (select polno from lcpol where grppolno='"
						+ tLCGrpPolSchema.getGrpPolNo()
						+ "') and trim(payplancode) like '%102'";
				tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sql);
				IndiPrem = Double.parseDouble(tSSRS.GetText(1, 1));
			} else {
				GrpPrem = tLCGrpPolSchema.getPrem();
				IndiPrem = 0;
			}

			xmlDataList.setColValue("ManageFeeRate", tLCGrpPolSchema
					.getManageFeeRate());
			xmlDataList.setColValue("Remark1", tLCGrpPolSchema.getRemark());
			xmlDataList.setColValue("GrpPrem", format(GrpPrem));
			xmlDataList.setColValue("Prem1", format(IndiPrem));
			xmlDataList.setColValue("PremSumChn", PubFun
					.getChnMoney(tLCGrpPolSchema.getPrem()));
			xmlDataList.setColValue("AcctPremSum", format(tLCGrpPolSchema
					.getPrem()
					* (1 - tLCGrpPolSchema.getManageFeeRate())));

			dPremSum += tLCGrpPolSchema.getPrem();

			LDCodeDB tLDCodeDB = new LDCodeDB();

			tLDCodeDB.setCodeType("printtemplate");
			tLDCodeDB.setCode(tLCGrpPolSchema.getRiskCode());

			if (!tLDCodeDB.getInfo()) {				
				CError.buildErr(this, "没有找到该险种对应的打印模板!");
				 return false;
			}

			// 团单模板为OtherSign数据中的第二个字符
			xmlDataList.setColValue("Template", tLDCodeDB.getOtherSign()
					.substring(1, 1));

			// 加入主附险标志
			xmlDataList.setColValue("SubRiskFlag", tLMRiskAppDB
					.getSubRiskFlag());
			xmlDataList.insertRow(0);
		}
		xmlDataset.addDataObject(new XMLDataTag("PremSum", format(dPremSum)));

		// end of "for(int nIndex = 0; nIndex < tLCGrpContSet.size(); nIndex ++)"
		xmlDataset.addDataObject(xmlDataList);

		Element end = new Element("END");
		xmlDataset.getRealElement().getChild("RiskInfo").addContent(end);
		Element row = new Element("ROW");
		end.addContent(row);
		row.addContent(new Element("COL1").addContent(""));

		xmlDataset.addDataObject(new XMLDataTag("InjuryGetFlag",
				strInjuryGetFlag));
		xmlDataset
				.addDataObject(new XMLDataTag("HospitalFlag", strHospitalFlag));

		xmlDataset.addDataObject(new XMLDataTag("LJTempFee.PayMoneySum",
				format(dPayMoneySum)));
		xmlDataset.addDataObject(new XMLDataTag("LJTempFee.PayMoneySum1",
				PubFun.getChnMoney(dPayMoneySum)));

		String strSQL = "SELECT TempFeeNo, Operator FROM LJTempFee WHERE OtherNoType = '1' AND OtherNo = '"
				+ aLCGrpPolSet.get(1).getGrpContNo() + "'";

		SSRS ssRs = new ExeSQL().execSQL(strSQL);

		if (ssRs.ErrorFlag || (ssRs.MaxRow == 0)) {
			CError.buildErr(this, "找不到该团体保单的暂交费数据!");
			return false;
		}

		xmlDataset.addDataObject(new XMLDataTag("LJTempFee.TempFeeNo", ssRs
				.GetText(1, 1)));
		xmlDataset.addDataObject(new XMLDataTag("LJTempFee.Handler", ssRs
				.GetText(1, 2)));
		return true;
	}

	// 正常打印的取数流程
	private XMLDataset getGrpContDataSet(LCGrpContSchema tLCGrpContSchema) {
		try {
			XMLDataset xmlDataset = new XMLDataset();

			LCGrpPolSet aLCGrpPolSet = getRiskList(tLCGrpContSchema
					.getGrpContNo());

			if (aLCGrpPolSet == null) {
				CError.buildErr(this, "获取团险险种信息失败");
				return null;
			}

			// 第一个元素是主险团体保单
			LCGrpPolSchema tMainLCGrpPolSchema = aLCGrpPolSet.get(1);

			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			tLMRiskAppDB.setRiskCode(tMainLCGrpPolSchema.getRiskCode());
			tLMRiskAppDB.getInfo();

			xmlDataset.addDataObject(new XMLDataTag("BranchCode",
					tMainLCGrpPolSchema.getManageCom().substring(0, 4)));
			if (tLMRiskAppDB.getBonusFlag().equals("Y")) {
				xmlDataset
						.addDataObject(new XMLDataTag("PolicyType", "group1"));
			} else {
				xmlDataset.addDataObject(new XMLDataTag("PolicyType", "group"));
			}

			LCGrpPolSchema tLCGrpPolSchema = null;

			// 获取打印的模板名
			LMRiskFormDB tLMRiskFormDB = new LMRiskFormDB();

			tLMRiskFormDB.setRiskCode(tMainLCGrpPolSchema.getRiskCode());
			tLMRiskFormDB.setFormType("PG");

			if (!tLMRiskFormDB.getInfo()) {
				CError.buildErr(this, "没有该险种保单的打印模板信息，险种");
				return null;
			}

			xmlDataset.setTemplate(tLMRiskFormDB.getFormName());

			// 签单机构取管理机构的前四位
			tLCGrpContSchema.setSignCom(tMainLCGrpPolSchema.getManageCom()
					.substring(0, 4));
			tLCGrpContSchema.setSignCom(ReportPubFun
					.getMngName(tLCGrpContSchema.getSignCom()));

			// 设置团单的缴至日期，取团单下个人的缴至日期的最大值
			// 设置团单的特约信息，如果没有特约内容，打印“此栏空白”
			
//			特约从团单特约表中取
			LCCGrpSpecSchema tLCCGrpSpecSchema = new LCCGrpSpecSchema();
			LCCGrpSpecSet tLCCGrpSpecSet = new LCCGrpSpecSet();	
			LCCGrpSpecDB tLCCGrpSpecDB = new LCCGrpSpecDB();
			tLCCGrpSpecDB.setGrpContNo(tMainLCGrpPolSchema.getGrpContNo());
			tLCCGrpSpecSet=tLCCGrpSpecDB.query();
			if(tLCCGrpSpecSet.size()<1 || tLCCGrpSpecSet==null)
			{
				tLCGrpContSchema.setGrpSpec("此栏空白");
			}else
			{
				tLCCGrpSpecSchema=tLCCGrpSpecSet.get(1);
				if("".equals(tLCCGrpSpecSchema.getSpecContent())|| tLCCGrpSpecSchema.getSpecContent()==null)
				{
					tLCGrpContSchema.setGrpSpec("此栏空白");
				}else
				{
				tLCGrpContSchema.setGrpSpec(tLCCGrpSpecSchema.getSpecContent());
				}
			}
			


			// 代理人姓名信息
			LAAgentDB tLAAgentDB = new LAAgentDB();

			tLAAgentDB.setAgentCode(tMainLCGrpPolSchema.getAgentCode());
			tLAAgentDB.getInfo();
			xmlDataset.addDataObject(new XMLDataTag("LCGrpPol.AgentName",
					tLAAgentDB.getName()));

			ExeSQL tExeSQL = new ExeSQL();
			String sql = "select InsuYear,InsuYearFlag from LCPol "
					+ " where GrpPolNo='" + tMainLCGrpPolSchema.getGrpPolNo()
					+ "' " + " and RowNum <=1";
			SSRS tSSRS = tExeSQL.execSQL(sql);

			if (tSSRS == null) {
				CError.buildErr(this, "没有找到该团单下的个单信息");
				return null;
			}

			String InsuYear = tSSRS.GetText(1, 1);
			String InsuYearFlag = tSSRS.GetText(1, 2);

			xmlDataset.addDataObject(new XMLDataTag("LCGrpPol.InsuredYear",
					InsuYear));
			xmlDataset.addDataObject(new XMLDataTag("LCGrpPol.InsuredYearFlag",
					InsuYearFlag));

			LCPolSchema tLCPolSchema = null;
			LDPersonDB tLDPersonDB = null;

			sql = "select min(PaytoDate) from LCPol " + "where GrpPolNo='"
					+ tMainLCGrpPolSchema.getGrpPolNo() + "' ";
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sql);

			if (tSSRS == null) {
				CError.buildErr(this, "没有找到该团单下的个单信息");
				return null;
			}

			String PaytoDate = tSSRS.GetText(1, 1);

			tMainLCGrpPolSchema.setPaytoDate(PaytoDate);
			tMainLCGrpPolSchema.setSumPay(format(tMainLCGrpPolSchema
					.getSumPay()));
			tMainLCGrpPolSchema.setSumPrem(format(tMainLCGrpPolSchema
					.getSumPrem()));
			Reflections tReflections = new Reflections();
			LCGrpPolBakSchema tMainLCGrpPolBakSchema = new LCGrpPolBakSchema();
			tReflections.transFields(tMainLCGrpPolBakSchema, tLCGrpContSchema);

			LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema();
			LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
			LCGrpAddressSet tLCGrpAddressSet = new LCGrpAddressSet();
			tLCGrpAddressDB.setAddressNo(tLCGrpContSchema.getAddressNo());
			tLCGrpAddressDB.setCustomerNo(tLCGrpContSchema.getAppntNo());
			tLCGrpAddressSet = tLCGrpAddressDB.query();
			if (tLCGrpAddressSet == null && tLCGrpAddressSet.size() < 1) {
				CError.buildErr(this, "查询团单地址报错");
				return null;
			} else {
				tLCGrpAddressSchema = tLCGrpAddressSet.get(1);
				tReflections.transFields(tMainLCGrpPolBakSchema,
						tLCGrpAddressSchema);
			}
			tMainLCGrpPolBakSchema.setSumPrem(tLCGrpContSchema.getSumPrem());
			tMainLCGrpPolBakSchema.setSumPay(tLCGrpContSchema.getSumPay());
			tMainLCGrpPolBakSchema.setRiskCode(tMainLCGrpPolSchema
					.getRiskCode());
			tMainLCGrpPolBakSchema.setRiskVersion(tMainLCGrpPolSchema
					.getRiskVersion());
			tMainLCGrpPolBakSchema.setGrpPolNo(tMainLCGrpPolSchema
					.getGrpContNo());
			tMainLCGrpPolBakSchema.setGrpProposalNo(tMainLCGrpPolSchema
					.getGrpProposalNo());
			tMainLCGrpPolBakSchema.setContNo("00000000000000000000");
			tMainLCGrpPolBakSchema.setPaytoDate(PaytoDate);
			tMainLCGrpPolBakSchema.setGrpNo(tLCGrpContSchema.getAppntNo());

			xmlDataset.addSchema(tMainLCGrpPolBakSchema, "1");

			// Get risk information
			if (!getRiskInfo(xmlDataset, aLCGrpPolSet)) {
				return null;
			}
			// 产生被保人清单
			if (!genInsuredList(xmlDataset, aLCGrpPolSet)) {
				return null;
			}

			if (!genRiskInfoForItem(xmlDataset, aLCGrpPolSet)) {
				return null;
			}

			if (!getScanPic(xmlDataset, tLCGrpContSchema)) {
				return null;
			}

			if (!getReceipt(xmlDataset, tLCGrpContSchema, aLCGrpPolSet)) {
				return null;
			}

			if (!getReply(xmlDataset, aLCGrpPolSet)) {
				return null;
			}
			return xmlDataset;
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, ex.getMessage());
			return null;
		}
	}

	/**
	 * 产生保单生效、失效日期数据
	 * @param strValidDate 保单生效日期
	 * @param stEndDate 保单失效日期
	 * @return
	 */
	private String genInsuPeriod(LCGrpPolSchema aLCGrpPolSchema) {
		// 将最大的失效日期减一天
		String strSQL = "SELECT TO_DATE(MAX(EndDate)) - 1 FROM LCPol WHERE GrpPolNo = '"
				+ aLCGrpPolSchema.getGrpPolNo() + "'";

		logger.debug(strSQL);

		SSRS ssRs = new ExeSQL().execSQL(strSQL);

		String strEndDate = ssRs.GetText(1, 1);

		if ((null == strEndDate) || strEndDate.equals("")) {
			CError.buildErr(this, "该团单下的个单没有保单失效日期!");
			return "";
		}

		String strRet = "自" + aLCGrpPolSchema.getCValiDate().substring(0, 4)
				+ "年" + aLCGrpPolSchema.getCValiDate().substring(5, 7) + "月"
				+ aLCGrpPolSchema.getCValiDate().substring(8, 10) + "日零时起至";

		if (Integer.parseInt(strEndDate.substring(0, 4)) > 2200) {
			strRet = strRet + "终身";
		} else {
			strRet = strRet + strEndDate.substring(0, 4) + "年"
					+ strEndDate.substring(5, 7) + "月"
					+ strEndDate.substring(8, 10) + "日二十四时止";
		}

		return strRet;
	}

	// 获取被保人清单
	private boolean genInsuredList(XMLDataset xmlDataset,
			LCGrpPolSet aLCGrpPolSet) throws FileNotFoundException {
		LCGrpPolSchema tLCGrpPolSchema = aLCGrpPolSet.get(1);
		InnerFormater innerFormater = null;
		String szTemplateFile = "";

		if (tLCGrpPolSchema != null) {
			innerFormater = new InnerFormater(tLCGrpPolSchema.getRiskCode());
		}

		szTemplateFile = mszTemplatePath + "DM" + tLCGrpPolSchema.getRiskCode()
				+ ".xml";

		if (new File(szTemplateFile).exists() == false) {
			CError.buildErr(this, "缺少配置文件!");
			return false;
		}

		Hashtable hashData = new Hashtable();

		hashData.put("_GRPPOLNO", tLCGrpPolSchema.getGrpContNo());

		XMLDataMine xmlDataMine = new XMLDataMine(new FileInputStream(
				szTemplateFile), hashData);

		xmlDataMine.setDataFormater(innerFormater);

		xmlDataset.addDataObject(xmlDataMine);

		return true;

	}

	/*
	 * 增加险种信息映射条款
	 */
	private boolean genRiskInfoForItem(XMLDataset xmlDataset,
			LCGrpPolSet aLCGrpPolSet) {
		Element table = new Element("Terms");
		xmlDataset.getRealElement().addContent(table);

		for (int i = 1; i <= aLCGrpPolSet.size(); i++) {
			LCGrpPolSchema tLCGrpPolSchema = aLCGrpPolSet.get(i);
			Element row = new Element("ROW");
			table.addContent(row);
			row.addContent(new Element("COL01").addContent(tLCGrpPolSchema
					.getRiskCode()));
		}

		return true;
	}

	/**
	 * 取得主险，附加险列表
	 * @param strGrpPolNo
	 * @return LCGrpPolSet 第一个元素是主险，其余的元素是附加险的
	 * @throws Exception
	 */
	private LCGrpPolSet getRiskList(String strGrpContNo) throws Exception {
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolSet rLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
//		tLCGrpPolDB.setGrpContNo(strGrpContNo);
//		tLCGrpPolDB.setAppFlag("1");
		
		String tAllLCGrpSQL="select * from LCGrpPol where  GrpContNo='"+strGrpContNo+"' ";
		rLCGrpPolSet = tLCGrpPolDB.executeQuery(tAllLCGrpSQL);
		if (rLCGrpPolSet.size() > 0 && rLCGrpPolSet != null) {
			for (int k = 1; k <= rLCGrpPolSet.size(); k++) {
				LCGrpPolSchema rLCGrpPolSchema = new LCGrpPolSchema();
				rLCGrpPolSchema = rLCGrpPolSet.get(k);
				String tSQL = "select SubRiskFlag from lmriskapp where riskcode='"
						+ rLCGrpPolSchema.getRiskCode() + "'";
				if ("M".equals(tExeSQL.getOneValue(tSQL))) { //团险也可能会有多主险
					tLCGrpPolSet.add(rLCGrpPolSchema);
					//break;
				}

			}
			for (int k = 1; k <= rLCGrpPolSet.size(); k++) {
				LCGrpPolSchema rLCGrpPolSchema = new LCGrpPolSchema();
				rLCGrpPolSchema = rLCGrpPolSet.get(k);
				String tSQL = "select SubRiskFlag from lmriskapp where riskcode='"
						+ rLCGrpPolSchema.getRiskCode() + "'";
				if ("S".equals(tExeSQL.getOneValue(tSQL))) {
					tLCGrpPolSet.add(rLCGrpPolSchema);
				}

			}
		} else {
			return null;
		}
		return tLCGrpPolSet;
	}

	// 获取补打保单的数据
	//    private boolean getRePrintData()
	//    {
	//        String strCurDate = PubFun.getCurrentDate();
	//        String strCurTime = PubFun.getCurrentTime();
	//
	//        ExeSQL exeSQL = new ExeSQL();
	//        SSRS ssrs = null;
	//
	//        // 利用LCGrpContSet来传递数据，其中包含的数据并不是LCGrpContSet的数据
	//        LCGrpContSet tLCGrpContSet = new LCGrpContSet();
	//
	//        for (int nIndex = 0; nIndex < mLCGrpContSet.size(); nIndex++)
	//        {
	//            LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
	//            tLCGrpContSchema.setSchema(mLCGrpContSet.get(nIndex + 1));
	//
	//            ssrs = exeSQL.execSQL(
	//                    "SELECT PrtTimes + 1 FROM LCContPrint WHERE ContNo = '"
	//                    + tLCGrpContSchema.getGrpContNo() + "'");
	//
	//            if (exeSQL.mErrors.needDealError())
	//            {
	//                mErrors.copyAllErrors(exeSQL.mErrors);
	//                return false;
	//            }
	//
	//            if (ssrs.MaxRow < 1)
	//            {
	//                throw new Exception("找不到原来的打印数据，可能传入的不是主险保单号！");
	//            }
	//
	//            tLCGrpContSchema.setPrintCount(ssrs.GetText(1, 1));
	//            tLCGrpContSchema.setModifyDate(strCurDate);
	//            tLCGrpContSchema.setModifyTime(strCurTime);
	//
	//            tLCGrpContSet.add(tLCGrpContSchema);
	//        }
	//
	//        mResult.clear();
	//        mResult.add(tLCGrpContSet);
	//
	//        LCGrpContF1PBLS tLCGrpContF1PBLS = new LCGrpContF1PBLS();
	//
	//        if (!tLCGrpContF1PBLS.submitData(mResult, "REPRINT"))
	//        {
	//            if (tLCGrpContF1PBLS.mErrors.needDealError())
	//            {
	//                mErrors.copyAllErrors(tLCGrpContF1PBLS.mErrors);
	//            }
	//
	//            throw new Exception("保存数据失败");
	//        }
	//
	//        // 取打印数据
	//        Connection conn = null;
	//
	//        try
	//        {
	//            DOMBuilder domBuilder = new DOMBuilder();
	//            Element rootElement = new Element("DATASETS");
	//
	//            conn = DBConnPool.getConnection();
	//
	//            if (conn == null)
	//            {
	//                throw new Exception("连接数据库失败！");
	//            }
	//
	//            String tSQL = "";
	//            java.sql.Blob tBlob = null;
	//            COracleBlob tCOracleBlob = new COracleBlob();
	//
	//            for (int nIndex = 0; nIndex < tLCGrpContSet.size(); nIndex++)
	//            {
	//                LCGrpContSchema tLCGrpContSchema = tLCGrpContSet.get(nIndex + 1);
	//
	//                tSQL = " and MainPolNo = '" + tLCGrpContSchema.getGrpContNo()
	//                    + "'";
	//                tBlob = tCOracleBlob.SelectBlob("LCPolPrint", "PolInfo", tSQL,
	//                        conn);
	//
	//                if (tBlob == null)
	//                {
	//                    throw new Exception("找不到打印数据");
	//                }
	//
	//                //BLOB blob = (oracle.sql.BLOB)tBlob; --Fanym
	//                Element ele = domBuilder.build(tBlob.getBinaryStream())
	//                                        .getRootElement();
	//                ele = new Element("DATASET").setMixedContent(ele
	//                        .getMixedContent());
	//                rootElement.addContent(ele);
	//            }
	//
	//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//            XMLOutputter xmlOutputter = new XMLOutputter("  ", true, "UTF-8");
	//            xmlOutputter.output(rootElement, baos);
	//
	//            mResult.clear();
	//            mResult.add(new ByteArrayInputStream(baos.toByteArray()));
	//
	//            if (conn != null)
	//            {
	//                conn.close();
	//            }
	//        }
	//        catch (Exception ex)
	//        {
	//            ex.printStackTrace();
	//
	//            try
	//            {
	//                if (conn != null)
	//                {
	//                    conn.close();
	//                }
	//            }
	//            catch (Exception e)
	//            {
	//                // do nothing
	//            }
	//
	//			CError.buildErr(this, ex.getMessage());
	//			return false;
	//        }
	//        return true;
	//    }

	// 重打保单的取数流程
	private boolean getGrpContDataSetEx(LCGrpContSchema tLCGrpContSchema) {


		// 取打印数据
		Connection conn = null;

		try {
			conn = DBConnPool.getConnection();

			if (conn == null) {
				throw new Exception("连接数据库失败！");
			}

			COracleBlob tCOracleBlob = new COracleBlob();
			CMySQLBlob tCMySQLBlob = new CMySQLBlob();
			String tSQL = "";
			java.sql.Blob tBlob = null;
			tSQL = " and ContNo = '" + tLCGrpContSchema.getGrpContNo() + "'";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tBlob = tCOracleBlob.SelectBlob("LCContPrint", "ContInfo", tSQL,
						conn);
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tBlob = tCMySQLBlob.SelectBlob("LCContPrint", "ContInfo", tSQL,
						conn);	
			}
			if (tBlob == null) {
				throw new Exception("找不到打印数据");
			}
			XMLDataset xmlDataset = mXMLDatasets.createDataset();
			//BLOB blob = (oracle.sql.BLOB)tBlob; --Fanym
			XMLDataBlob xmlDataBlob = new XMLDataBlob(tBlob.getBinaryStream());
			xmlDataBlob.getDataBlobObject();
			xmlDataset.addDataObject(xmlDataBlob);

			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				// do nothing
			}
			CError.buildErr(this, ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 2003-07-21 Kevin
	 * 格式化浮点型数据
	 * @param dValue
	 * @return
	 */
	private String format(double dValue) {
		return new DecimalFormat("0.00").format(dValue);
	}

	// 嵌套类 InnerComparator
	private class InnerComparator implements Comparator {
		private String m_strMainRiskCode = "";

		public InnerComparator(String strMainRiskCode) {
			m_strMainRiskCode = strMainRiskCode;
		}

		public int compare(Object o1, Object o2) {
			Element ele1 = (Element) o1;
			Element ele2 = (Element) o2;

			//      // 先比较客户号
			//      String str1 = ele1.getChildTextTrim("COL0");
			//      String str2 = ele2.getChildTextTrim("COL0");
			// 先比较投保单号
			String str1 = ele1.getChildTextTrim("COL16");
			String str2 = ele2.getChildTextTrim("COL16");

			if (str1 != null) {
				if (!str1.equals(str2)) {
					return str1.compareTo(str2);
				}
			}

			// 再比较主险/附加险
			str1 = ele1.getChildTextTrim("COL6");
			str2 = ele2.getChildTextTrim("COL6");

			if (!str1.equals(str2)) {
				if (str1.equals(m_strMainRiskCode)) {
					return -1;
				}

				if (str2.equals(m_strMainRiskCode)) {
					return 1;
				}

				return str1.compareTo(str2);
			}

			return 0;
		}

		public boolean equals(Object o) {
			return false;
		}
	}

	// 嵌套类 InnerFormater
	private class InnerFormater implements XMLDataFormater {
		private String m_strMainRiskCode = "";

		public InnerFormater(String strMainRiskCode) {
			m_strMainRiskCode = strMainRiskCode;
		}

		public boolean format(Element element) {
			List list = element.getChild("LCPol").getChildren("ROW");

			//            Collections.sort(list, new InnerComparator(m_strMainRiskCode));
			// 给投保人列表加上序号
			Iterator it = list.iterator();
			int nIndex = 0;

			while (it.hasNext()) {
				Element eleRow = (Element) it.next();
				eleRow.getChild("COL15").setText(String.valueOf(nIndex++));
			}

			element.getChild("LCPol").setChildren(list);

			return true;
		}
	}

	/**
	 * 纪录错误信息
	 * @param tSerialNo
	 * @param tPolNo
	 * @param errDescribe
	 * @param tGetMode
	 * @return 
	 */
	public boolean insertErrLog(String errDescribe,
			LCGrpContSchema mLCContSchema) {
		String tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
		String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);//产生流水号码
		LDSysErrLogDB tLDSysErrLogDB = new LDSysErrLogDB();
		tLDSysErrLogDB.setSerialNo(tSerialNo);
		tLDSysErrLogDB.setPolNo("00000000000000000000");
		tLDSysErrLogDB.setModule("GCB"); //团单承保
		tLDSysErrLogDB.setSubModule("GCBPP");//团单保单打印
		tLDSysErrLogDB.setGrpContNo(mLCContSchema.getGrpContNo());
		tLDSysErrLogDB.setContNo("00000000000000000000");
		tLDSysErrLogDB.seterrMsg(errDescribe);
		tLDSysErrLogDB.setMakeDate(tCurMakeDate);
		tLDSysErrLogDB.setMakeTime(tCurMakeTime);
		tLDSysErrLogDB.setManageCom(mLCContSchema.getManageCom());
		if (tLDSysErrLogDB.insert() == false) {
			CError tError = new CError();
			tError.moduleName = "LCPolF1P_IDGFBL";
			tError.functionName = "insertErrLog";
			tError.errorMessage = "纪录错误日志时发生错误："
					+ tLDSysErrLogDB.mErrors.getFirstError() + "；解决该问题后，打印此保单";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "LCGrpContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		//		tLCGrpContDB.setAppFlag("1");
		//		tLCGrpContDB.setPrintCount(0);
		//		tLCGrpContDB.setGrpContNo("00000000000000000000");
		//		tLCGrpContDB.setContType("1");
		//		tLCGrpContDB.setContNo("86131120070210037207");
		//'86510720070210011428','86131120070210037213'
		String tContSQL = "select * from lcGrpcont where grpcontno in ("
				+ "'86110020030220000018')";
		tLCGrpContSet = tLCGrpContDB.executeQuery(tContSQL);
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLCGrpContSet);
		LCGrpContF1PBL tLCGrpContF1PBL = new LCGrpContF1PBL();
		if (!tLCGrpContF1PBL.submitData(tVData, "REPRINT")) {
			logger.debug("\t@> LCGrpContF1PBL 打印失败");
		}
	} // function main end
}
