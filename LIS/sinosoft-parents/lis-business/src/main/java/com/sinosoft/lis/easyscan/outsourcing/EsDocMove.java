package com.sinosoft.lis.easyscan.outsourcing;
import org.apache.log4j.Logger;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: XML生成的简单工厂模式——实现类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author liuxin
 * @version 1.0
 */

public class EsDocMove implements EsDocMoveInterface {
private static Logger logger = Logger.getLogger(EsDocMove.class);

	private static final String CON_XML_ROOT = "NewCont";

	private static final String CON_XML_MISSIONID = "Missionid";
	private static final String CON_XML_SUBMISSIONID = "SubMissionid";
	private static final String CON_XML_ACTIVITYID = "ActivityId";
	private static final String CON_XML_CONTNO = "ContNo";
	private static final String CON_XML_PRTSEQ = "PrtSeq";
	private static final String CON_XML_MANAGECOM = "Managecom";
	private static final String CON_XML_MAKEDATE = "MakeDate";

	private static final String CON_XML_IMAGES = "Images";
	private static final String CON_XML_IMAGE = "Image";
	private static final String CON_XML_DOCID = "Docid";
	private static final String CON_XML_DOCCODE = "DocCode";
	private static final String CON_XML_SUBTYPE = "SubType";
	private static final String CON_XML_NUMPAGES = "NumPages";
	private static final String CON_XML_PAGES = "Pages";
	private static final String CON_XML_PAGE = "Page";
	private static final String CON_XML_PAGEID = "Pageid";
	private static final String CON_XML_PAGECODE = "PageCode";
	private static final String CON_XML_PAGENAME = "PageName";
	private static final String CON_XML_PAGESUFFIX = "PageSuffix";
	private static final String CON_XML_PICPATH = "PicPath";

	private static final String CON_XML_QUESTIONS = "Questions";
	private static final String CON_XML_QUESTIONCOUNT = "QuestionCount";
	private static final String CON_XML_QUESTION = "Question";
	private static final String CON_XML_QUESTIONTYPE = "QuestionType";
	private static final String CON_XML_QUESTIONCONTENT = "QuestionContent";

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	//
	public Document GetXMLDoc(VData cInputData) {
		try {
			// 取数据ES_DOC_MAINSchema、ES_DOC_PAGESSet、TransferData
			ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
			ES_DOC_MAINSet tES_DOC_MAINSet = (ES_DOC_MAINSet) cInputData.get(0);
			tES_DOC_MAINSchema = tES_DOC_MAINSet.get(1);

			ES_DOC_PAGESSet tES_DOC_PAGESSet = (ES_DOC_PAGESSet) cInputData
					.get(1);
			TransferData tTransferData = (TransferData) cInputData
					.getObjectByObjectName("TransferData", 0);

			String tMissionid = (String) tTransferData
					.getValueByName(CON_XML_MISSIONID);
			String tSubMissionid = (String) tTransferData
					.getValueByName(CON_XML_SUBMISSIONID);
			String tContNo = (String) tTransferData
					.getValueByName(CON_XML_CONTNO);
			String tActivityId = (String) tTransferData
					.getValueByName(CON_XML_ACTIVITYID);
			String tManagecom = (String) tTransferData
					.getValueByName(CON_XML_MANAGECOM);
			String tMakeDate = (String) tTransferData
					.getValueByName(CON_XML_MAKEDATE);
			String tPrtSeq = (String) tTransferData
					.getValueByName(CON_XML_PRTSEQ);
			LCIssuePolSet tLCIssuePolSet = (LCIssuePolSet) tTransferData
					.getValueByName("LCIssuePolSet");
			if (tLCIssuePolSet.size() == 0) {
				logger.debug("EsDocMove->GetXMLDoc传入数据出错!");
				CError tError = new CError();
				tError.moduleName = "EsDocMove";
				tError.functionName = "GetXMLDoc";
				tError.errorNo = "-500";
				tError.errorMessage = "传入数据出错!";
				return null;
			}
			// 创建根节点 list;
			Element root = new Element(CON_XML_ROOT);
			// 根节点添加到文档中；
			Document Doc = new Document(root);

			root.addContent(new Element(CON_XML_MISSIONID).setText(tMissionid));
			root.addContent(new Element(CON_XML_SUBMISSIONID)
					.setText(tSubMissionid));
			root.addContent(new Element(CON_XML_ACTIVITYID)
					.setText(tActivityId));
			root.addContent(new Element(CON_XML_CONTNO).setText(tContNo));
			root.addContent(new Element(CON_XML_PRTSEQ).setText(tPrtSeq));
			root.addContent(new Element(CON_XML_MANAGECOM).setText(tManagecom));
			root.addContent(new Element(CON_XML_MAKEDATE).setText(tMakeDate));

			Element elementImages = new Element(CON_XML_IMAGES);
			Element elementImage = new Element(CON_XML_IMAGE);

			// 取ES_DOC_MAINSchema的数据
			String tDocid = String.valueOf((int) tES_DOC_MAINSchema.getDocID());
			String tDocCode = tES_DOC_MAINSchema.getDocCode();
			String tSubType = tES_DOC_MAINSchema.getSubType();
			String tNumPages = String.valueOf(tES_DOC_MAINSchema.getNumPages());
			;
			// 创建Image的子节点
			elementImage.addContent(new Element(CON_XML_DOCID).setText(tDocid));
			elementImage.addContent(new Element(CON_XML_DOCCODE)
					.setText(tDocCode));
			elementImage.addContent(new Element(CON_XML_SUBTYPE)
					.setText(tSubType));
			elementImage.addContent(new Element(CON_XML_NUMPAGES)
					.setText(tNumPages));

			Element elementPages = new Element(CON_XML_PAGES);
			// 此处 for 循环生成page
			for (int i = 0; i < tES_DOC_PAGESSet.size(); i++) {
				ES_DOC_PAGESSchema tES_DOC_PAGESSchema = tES_DOC_PAGESSet
						.get(i + 1);
				String tPageid = String.valueOf((int) tES_DOC_PAGESSchema
						.getPageID());
				String tPageCode = String.valueOf(tES_DOC_PAGESSchema
						.getPageCode());
				String tPageName = tES_DOC_PAGESSchema.getPageName();
				String tPageSuffix = tES_DOC_PAGESSchema.getPageSuffix();
				String tPicPath = tES_DOC_PAGESSchema.getPicPath();
				// 创建节点 Page;
				Element elementPage = new Element(CON_XML_PAGE);
				// 给Page节点添加子节点并赋值；
				elementPage.addContent(new Element(CON_XML_PAGEID)
						.setText(tPageid));
				elementPage.addContent(new Element(CON_XML_PAGECODE)
						.setText(tPageCode));
				elementPage.addContent(new Element(CON_XML_PAGENAME)
						.setText(tPageName));
				elementPage.addContent(new Element(CON_XML_PAGESUFFIX)
						.setText(tPageSuffix));
				elementPage.addContent(new Element(CON_XML_PICPATH)
						.setText(tPicPath));
				// 给父节点Pages添加Page子节点;
				elementPages.addContent(elementPage);
			}
			elementImage.addContent(elementPages);
			elementImages.addContent(elementImage);
			root.addContent(elementImages);

			// Element elementQuestions = ne Elemt(CON_XML_QUESTIONS);
			Element elementQuestions = new Element(CON_XML_QUESTIONS);
			String tQuestionCount = String.valueOf(tLCIssuePolSet.size());
			elementQuestions.addContent(new Element(CON_XML_QUESTIONCOUNT)
					.setText(tQuestionCount));
			// 此处 for 循环生成Question
			for (int i = 0; i < tLCIssuePolSet.size(); i++) {
				LCIssuePolSchema tLCIssuePolSchema = tLCIssuePolSet.get(i + 1);
				String tQuestionType = tLCIssuePolSchema.getIssueType();
				String tQuestionContent = tLCIssuePolSchema.getIssueCont();

				// 创建节点 Question;
				// Element elementuestion = new Element(CON_XML_QUESTION;
				Element elementQuestion = new Element(CON_XML_QUESTION);
				// 给Question节点添加子节点并赋值；
				elementQuestion.addContent(new Element(CON_XML_QUESTIONTYPE)
						.setText(tQuestionType));
				elementQuestion.addContent(new Element(CON_XML_QUESTIONCONTENT)
						.setText(tQuestionContent));
				// 给父节点Pages添加Page子节点;
				elementQuestions.addContent(elementQuestion);
			}
			root.addContent(elementQuestions);

			return Doc;
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EsDocMove";
			tError.functionName = "GetXMLDoc";
			tError.errorNo = "-99";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return null;
		}

	}

	public String getXMLName(VData cInputData) {
		// 取TransferData

		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		String tMissionid = (String) tTransferData
				.getValueByName(CON_XML_MISSIONID);
		if (tMissionid == null || tMissionid == "") {
			logger.debug("EsDocMove->getXMLName获取xml文件名称出错!");
			CError tError = new CError();
			tError.moduleName = "EsDocMove";
			tError.functionName = "getXMLName";
			tError.errorNo = "-500";
			tError.errorMessage = "获取xml文件名称出错!";
			return "";
		}

		return tMissionid;
	}

	public VData getTempFolder(VData cInputData) {
		// 取TransferData
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);

		String tTempFolder = (String) tTransferData.getValueByName("TempPath");
		if (tTempFolder == null || tTempFolder == "") {
			logger.debug("EsDocMove->getTempFolder获取临时路径出错");
			CError tError = new CError();
			tError.moduleName = "EsDocMove";
			tError.functionName = "getTempFolder";
			tError.errorNo = "-500";
			tError.errorMessage = "获取临时路径出错!";

			return null;
		}

		VData tVData = new VData();
		tVData.add(0, tTempFolder);
		return tVData;

	}

	public VData getDesFolder(VData cInputData) {
		// 取TransferData
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);

		String tManagecom = (String) tTransferData
				.getValueByName(CON_XML_MANAGECOM);
		String tDesFolder = "";
		// 调用接口

		// 获取IP
		// String tServerIP = (String) mVData.get(0);
		// 获取相对路径
		// String tFilePath = (String) mVData.get(3);
		// String strDesFolder = "//" + tServerIP + "/" + tFilePath;
		VData tVData = new VData();
		tVData.add(0, tDesFolder);
		return tVData;

	}

	public void show() {
		logger.debug("----------Create XMLA----------");
	}

}
