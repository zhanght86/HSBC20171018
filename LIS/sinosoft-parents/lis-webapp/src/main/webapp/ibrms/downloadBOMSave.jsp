<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//���¼�¼�� CREATEPAET��CREATEALL
	//������:  
	//��������:  
	//����ԭ��/���ݣ�
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
	//����java
	if (creatFlag.compareTo("CREATEPAET") == 0)
		action = 0;
	//�������е�java
	if (creatFlag.compareTo("CREATEALL") == 0)
		action = 1;
	//����class�ļ�
	if (creatFlag.compareTo("DOWNCLASS") == 0)
		action = 2;

	loggerDebug("downloadBOMSave","action:" + action);
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	switch (action) {
		case 0 :
			// ׼���������� VData

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
			//������ʽ=�� Inp+MulLine������+Chk��  
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
				Content = "����JAVA�ļ�ʧ�ܣ�ԭ����:" + tError.getFirstError();
				loggerDebug("downloadBOMSave","Content:" + Content);
				FlagStr = "Fail";
			} else {
				loggerDebug("downloadBOMSave","�ɹ�");
				Content = "����JAVA�ļ������ɹ�";
				loggerDebug("downloadBOMSave","Content:" + Content);
				FlagStr = "Succ";
			}
			break;
		case 1 :
			// ׼���������� VData
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
				Content = "����JAVA�ļ�ʧ�ܣ�ԭ����:" + tError.getFirstError();
				loggerDebug("downloadBOMSave","Content:" + Content);
				FlagStr = "Fail";
			} else {
				loggerDebug("downloadBOMSave","�ɹ�");
				Content = "����JAVA�ļ������ɹ�";
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
				Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
				FlagStr = "Fail";
			} else {
				String targetSql = "select sysvarvalue from ldsysvar where sysvar='ibrmsTarget'";
				String target = new ExeSQL().getOneValue(targetSql);
				Content = "ѹ�����,BOM���ز����ɹ�,�洢·��Ϊ��" + target;
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

