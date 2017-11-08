<%
/***************************************************************
* <p>ProName��LCBnfQueryESave.jsp</p>
 * <p>Title��������ά��</p>
 * <p>Description��������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-0-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
		
			// ��������Ϣ
			String tOperate = request.getParameter("Operate");	
			String tGrpcontno = request.getParameter("GrpPropNo");
			String tGrpPropNo	= request.getParameter("GrpPropNo");
			String tContNo = request.getParameter("mContNo");
			String tInsuredNo = request.getParameter("InsuredSeqNo");
			String tBnfNum[] = request.getParameterValues("BnfGridNo");
			String tBnfOrder[] = request.getParameterValues("BnfGrid2");//������˳��
			String tName[] = request.getParameterValues("BnfGrid3");//����
			String tSex[] = request.getParameterValues("BnfGrid5");//�Ա�
			String tBirthday[] = request.getParameterValues("BnfGrid6");//��������
			String tIDType[] = request.getParameterValues("BnfGrid8");//֤������
			String tIDNo[] = request.getParameterValues("BnfGrid9");//֤������
			String tRelationToInsured[] = request.getParameterValues("BnfGrid11");//�뱻���˹�ϵ
			String tBnfLot[] = request.getParameterValues("BnfGrid12");//�������	

			LCBnfSet tLCBnfSet = new LCBnfSet();
			LCBnfSchema tLCBnfSchema = new LCBnfSchema();
			if(null != tBnfNum &&tBnfNum.length>=1 ){
				for (int i = 0; i < tBnfNum.length; i++){
					tLCBnfSchema   = new LCBnfSchema();
					tLCBnfSchema.setContNo(tContNo);
					tLCBnfSchema.setPolNo(tContNo);
					tLCBnfSchema.setInsuredNo(tInsuredNo); // �����˿ͻ���
					tLCBnfSchema.setBnfType("0");
					tLCBnfSchema.setBnfGrade(tBnfOrder[i]);
					tLCBnfSchema.setBnfNo(i+1);
					tLCBnfSchema.setRelationToInsured(tRelationToInsured[i]);
					tLCBnfSchema.setBnfLot(tBnfLot[i]); // �����˷ݶ�
					tLCBnfSchema.setName(tName[i]);
					tLCBnfSchema.setSex(tSex[i]);
					String xBirthday=tBirthday[i];
					tLCBnfSchema.setBirthday(tBirthday[i]);	    
					tLCBnfSchema.setIDType(tIDType[i]);
					//��������֤���룬�����������˵��Ա�ͳ�������
					if("0".equals(tIDType[i])){
						String xSex = PubFun.getSexFromId(tIDNo[i]);
						xBirthday = PubFun.getBirthdayFromId(tIDNo[i]);
						if(tSex[i]==null || "".equals(tSex[i])){
							tLCBnfSchema.setSex(xSex);
						}
						if(tBirthday[i]==null || "".equals(tBirthday[i])){
							tLCBnfSchema.setBirthday(xBirthday);
						}
					}
					tLCBnfSchema.setIDNo(tIDNo[i]);
					tLCBnfSet.add(tLCBnfSchema);
				}
			}
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("Flag",request.getParameter("Flag"));
			tTransferData.setNameAndValue("ContNo",tContNo);
			tTransferData.setNameAndValue("InsuredNo",tInsuredNo);
	
			VData tVData = new VData();
			
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLCBnfSet);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCBnfSaveUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "�����˲����ɹ���";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
