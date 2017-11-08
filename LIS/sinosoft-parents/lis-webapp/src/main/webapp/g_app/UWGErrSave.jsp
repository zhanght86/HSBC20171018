<%
/***************************************************************
* <p>ProName��UWGErrSave.jsp</p>
 * <p>Title���˱�����</p>
 * <p>Description���˱�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-0-05-06
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
		
			// �ٱ�����			
			String tOperate = request.getParameter("Operate");	
			String tGrpPropNo = request.getParameter("GrpPropNo");
			String tUWNo = request.getParameter("UWNo");
			String tReinsType = request.getParameter("ReinsType"); // �����ٱ�����
			String tUWConclu = request.getParameter("UWConclu"); // ���˺˱�����
			String tUWIdea = request.getParameter("UWIdea"); // ���˺˱����	
			String tContno=  request.getParameter("ContNo");
			String tInsuredno = request.getParameter("InsuredNo"); 
			
			// �ŵ�����
			String tUWConclusion = request.getParameter("UWConclusion");// �ŵ��˱�����
			String tUWOption = request.getParameter("UWOption"); // �ŵ��˱����
			String tRulecode = request.getParameter("Rulecode");
			String tRulevel = request.getParameter("Rulevel");
			String tRiskcode = request.getParameter("Riskcode");
		
			LCUWMasterSet  tLCUWMasterSet = new LCUWMasterSet();
			LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();	
			LCGUWTraceSchema  tLCGUWTraceSchema = new LCGUWTraceSchema();
			
			if("INSERT||Person".equals(tOperate)){
				tLCUWMasterSchema.setGrpContNo(tGrpPropNo);
				tLCUWMasterSchema.setContNo(tContno);
				tLCUWMasterSchema.setInsuredNo(tInsuredno);
				tLCUWMasterSchema.setUWNo(tUWNo);
				tLCUWMasterSchema.setUWConclu(tUWConclu);
				tLCUWMasterSchema.setUWIdea(tUWIdea);
							
			}else if("INSERT||AllPerson".equals(tOperate)){
				String[] tErrNum= request.getParameterValues("InpUWGErrGridChk");
				String tContNo[] = request.getParameterValues("UWGErrGrid2");//������
				String tInsuredNo[] = request.getParameterValues("UWGErrGrid1");//�ͻ���
				String tUWNO[] = request.getParameterValues("UWGErrGrid3");//�˱����
				int chkCount = 0;
				for(int index=0;index<tErrNum.length;index++) {
					if(tErrNum[index].equals("1"))	{
						chkCount = chkCount + 1;
					}
				}	
				if( chkCount <= 0 )	{
					tContent = "������ѡ��һ������������Ϣ��";
					tFlagStr  = "Fail";
				}else{
					for(int index=0;index<tErrNum.length;index++) {
						if(tErrNum[index].equals("1"))	{
							LCUWMasterSchema aLCUWMasterSchema = new LCUWMasterSchema();
							aLCUWMasterSchema.setGrpContNo(tGrpPropNo);	
							aLCUWMasterSchema.setContNo(tContNo[index]);
							aLCUWMasterSchema.setUWNo(tUWNO[index]);
							aLCUWMasterSchema.setInsuredNo(tInsuredNo[index]);
							aLCUWMasterSchema.setUWConclu(tUWConclu);
							aLCUWMasterSchema.setUWIdea(tUWIdea);
							tLCUWMasterSet.add(aLCUWMasterSchema);
						}
					}	
				}
			}else if("INSERT||GRP".equals(tOperate)){
				
				tLCGUWTraceSchema.setGrpContNo(tGrpPropNo);
				tLCGUWTraceSchema.setUWConclu(tUWConclusion);
				tLCGUWTraceSchema.setUWIdea(tUWOption);
				tLCGUWTraceSchema.setUWNo(tUWNo);
				tLCGUWTraceSchema.setRuleCode(tRulecode);
				tLCGUWTraceSchema.setRuleLevel(tRulevel);
				tLCGUWTraceSchema.setRiskCode(tRiskcode);		
			}
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("GrpPropNo",tGrpPropNo);
			tTransferData.setNameAndValue("ReinsType",tReinsType);
			tTransferData.setNameAndValue("UWConclu",tUWConclu);
			tTransferData.setNameAndValue("UWIdea",tUWIdea);
			
			VData tVData = new VData();		
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLCUWMasterSchema);
			tVData.add(tLCGUWTraceSchema);
			tVData.add(tLCUWMasterSet);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "UWGErrUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				
				if ("INSERT||Person".equals(tOperate)) {
				
					tContent = "���˺˱����۱���ɹ���";
				} else if("INSERT||AllPerson".equals(tOperate)) {
					
					tContent = "��������˱����۳ɹ���";
				} else if("INSERT||GRP".equals(tOperate)) {
					
					tContent = "�˱����۱���ɹ���";
				} else if("INSERT||ALL".equals(tOperate)) {
					
					tContent = "�����˱����۱���ɹ���";
				}	
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
