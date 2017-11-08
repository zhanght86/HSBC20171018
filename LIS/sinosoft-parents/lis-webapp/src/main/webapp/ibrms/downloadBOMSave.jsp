<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//更新记录： CREATEPAET，CREATEALL
	//更新人:  
	//更新日期:  
	//更新原因/内容：
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.menumang.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.sinosoft.service.*" %>

<%@page import="com.sinosoft.ibrms.*"%>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="./downloadBOM.js"></SCRIPT>
<head>
<%@include file="../common/jsp/UsrCheck.jsp"%>
</head>
<body>
<%
	loggerDebug("downloadBOMSave","start jsp");
	String Content = "";
	String FlagStr = "";
	CErrors tError = null;
	String creatFlag = request.getParameter("creatFlag");
	loggerDebug("downloadBOMSave","creatFlag:" + creatFlag);
	String busiName="BOMMakerUI";
	int action = -1;
	//生成java
	if (creatFlag.compareTo("CREATEPAET") == 0)
		action = 0;
	//生成所有的java
	if (creatFlag.compareTo("CREATEALL") == 0)
		action = 1;
	//下载class文件
	if (creatFlag.compareTo("DOWNCLASS") == 0)
		action = 2;

	loggerDebug("downloadBOMSave","action:" + action);
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	switch (action) {
		case 0 :
			// 准备传输数据 VData

			//BOMMakerUI tBOMMakerUI = new BOMMakerUI();
			LRBOMSet tLRBOMSet = new LRBOMSet();
			String tChk[] = request
					.getParameterValues("InpQueryGrpGridChk");
			String eName[] = request
					.getParameterValues("QueryGrpGrid1");
			String cName[] = request
					.getParameterValues("QueryGrpGrid2");
			String fBOM[] = request.getParameterValues("QueryGrpGrid3");
			String fatherItem[] = request
					.getParameterValues("QueryGrpGrid5");
			String localItem[] = request
					.getParameterValues("QueryGrpGrid4");
			String bomLevel[] = request
					.getParameterValues("QueryGrpGrid6");
			String Business[] = request
					.getParameterValues("QueryGrpGrid7");
			String Description[] = request
					.getParameterValues("QueryGrpGrid8");
			String Valid[] = request
					.getParameterValues("QueryGrpGrid10");
			//参数格式=” Inp+MulLine对象名+Chk”  
			for (int index = 0; index < tChk.length; index++) {
				if (tChk[index].equals("1")) {
					LRBOMSchema tLRBOMSchema = new LRBOMSchema();
					tLRBOMSchema.setName(eName[index]);
					tLRBOMSchema.setCNName(cName[index]);
					tLRBOMSchema.setFBOM(fBOM[index]);
					tLRBOMSchema.setFatherItem(fatherItem[index]);
					tLRBOMSchema.setLocalItem(localItem[index]);
					tLRBOMSchema.setBOMLevel(bomLevel[index]);
					tLRBOMSchema.setBusiness(Business[index]);
					tLRBOMSchema.setDiscription(Description[index]);
					tLRBOMSchema.setValid(Valid[index]);
					tLRBOMSet.add(tLRBOMSchema);
				}
			}
			VData tVData = new VData();
			tVData.add(tLRBOMSet);
			
			
			if(!tBusinessDelegate.submitData(tVData, creatFlag,busiName))
			{
			//if (!tBOMMakerUI.submitData(tVData, creatFlag)) {
				tError = tBusinessDelegate.getCErrors();
				Content = "生成JAVA文件失败，原因是:" + tError.getFirstError();
				loggerDebug("downloadBOMSave","Content:" + Content);
				FlagStr = "Fail";
			} else {
				loggerDebug("downloadBOMSave","成功");
				Content = "生成JAVA文件操作成功";
				loggerDebug("downloadBOMSave","Content:" + Content);
				FlagStr = "Succ";
			}
			break;
		case 1 :
			// 准备传输数据 VData
			VData mVData = new VData();
			//BOMMakerUI mBOMMakerUI = new BOMMakerUI();
			LRBOMSet mLRBOMSet = new LRBOMSet();
			LRBOMDB mLRBOMDB = new LRBOMDB();
			mLRBOMSet = mLRBOMDB.executeQuery("select * from LRBOM");
			loggerDebug("downloadBOMSave","mLRBOMSet:" + mLRBOMSet.size());
			mVData.add(mLRBOMSet);
			//if (!mBOMMakerUI.submitData(mVData, creatFlag)) {
			
			if(!tBusinessDelegate.submitData(mVData, creatFlag,busiName))
			{
				tError = tBusinessDelegate.getCErrors();
				Content = "生成JAVA文件失败，原因是:" + tError.getFirstError();
				loggerDebug("downloadBOMSave","Content:" + Content);
				FlagStr = "Fail";
			} else {
				loggerDebug("downloadBOMSave","成功");
				Content = "生成JAVA文件操作成功";
				loggerDebug("downloadBOMSave","Content:" + Content);
				FlagStr = "Succ";
			}
			break;
		case 2 :
			loggerDebug("downloadBOMSave","action:" + action);
			VData dVData = new VData();
			BOMMakerUI dBOMMakerUI = new BOMMakerUI();
			LRBOMDB dLRBOMDB = new LRBOMDB();
			//if (!dBOMMakerUI.submitData(dVData, creatFlag)) {
			if(!tBusinessDelegate.submitData(dVData, creatFlag,busiName))
			{	
				tError = tBusinessDelegate.getCErrors();
				Content = "下载失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			} else {
				String targetSql = "select sysvarvalue from ldsysvar where sysvar='ibrmsTarget'";
				String target = new ExeSQL().getOneValue(targetSql);
				Content = "压缩完毕,BOM下载操作成功,存储路径为：" + target;
				loggerDebug("downloadBOMSave","Content:" + Content);
				FlagStr = "Succ";
			}
			break;
	}
	loggerDebug("downloadBOMSave","FlagStr : " + FlagStr);
	loggerDebug("downloadBOMSave","end of jsp");
%>

<script>
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>

</body>

