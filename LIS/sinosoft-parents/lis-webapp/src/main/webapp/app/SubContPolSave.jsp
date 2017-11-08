<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>

<%	         
	
	String FlagStr="";      //�������
	String Content = "";    //����̨��Ϣ
	String tAction = "";    //�������ͣ�delete update insert
	String tOperate = "";   //��������
	String tCustomerNoRet = "";

	VData tVData = new VData();
	LDGrpSchema tLDGrpSchema   = new LDGrpSchema();                //����ͻ�
	LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema(); //����ͻ���ַ 
	LCGeneralSchema tLCGeneralSchema = new LCGeneralSchema();      //����ֵ���
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");

	tAction = request.getParameter( "fmAction" );

	//����ͻ���Ϣ  LDGrp
	tLDGrpSchema.setCustomerNo(request.getParameter("GrpNo"));            //�ͻ�����
	tLDGrpSchema.setGrpName(request.getParameter("GrpName"));             //��λ����
	loggerDebug("SubContPolSave","GrpName:" + request.getParameter("GrpName"));
	tLDGrpSchema.setGrpNature(request.getParameter("GrpNature"));         //��λ����
	tLDGrpSchema.setBusinessType(request.getParameter("BusinessType"));   //��ҵ���
	tLDGrpSchema.setPeoples(request.getParameter("Peoples"));             //������
	tLDGrpSchema.setRgtMoney(request.getParameter("RgtMoney"));           //ע���ʱ�
	tLDGrpSchema.setAsset(request.getParameter("Asset"));                 //�ʲ��ܶ�
	tLDGrpSchema.setNetProfitRate(request.getParameter("NetProfitRate")); //���ʲ�������
	tLDGrpSchema.setMainBussiness(request.getParameter("MainBussiness")); //��Ӫҵ��
	tLDGrpSchema.setCorporation(request.getParameter("Corporation"));     //����
	tLDGrpSchema.setComAera(request.getParameter("ComAera"));             //�����ֲ�����
	tLDGrpSchema.setFax(request.getParameter("Fax"));             //�����ֲ�����
	tLDGrpSchema.setPhone(request.getParameter("Phone"));             //�����ֲ�����
	tLDGrpSchema.setFoundDate(request.getParameter("FoundDate"));             //�����ֲ�����
	tLDGrpSchema.setGetFlag(request.getParameter("GetFlag"));             //�����ֲ�����
	tLDGrpSchema.setBankCode(request.getParameter("BankCode"));             //�����ֲ�����
	tLDGrpSchema.setBankAccNo(request.getParameter("BankAccNo"));             //�����ֲ�����
	tLDGrpSchema.setRemark(request.getParameter("Remark"));             //�����ֲ�����
	//����ͻ���ַ  LCGrpAddress	    
	tLCGrpAddressSchema.setCustomerNo(request.getParameter("GrpNo"));            //�ͻ�����
	tLCGrpAddressSchema.setAddressNo(request.getParameter("GrpAddressNo"));      //��ַ����
	tLCGrpAddressSchema.setGrpAddress(request.getParameter("GrpAddress"));       //��λ��ַ
	tLCGrpAddressSchema.setGrpZipCode(request.getParameter("GrpZipCode"));       //��λ�ʱ�
	//������ϵ��һ
	tLCGrpAddressSchema.setLinkMan1(request.getParameter("LinkMan1"));
	tLCGrpAddressSchema.setDepartment1(request.getParameter("Department1"));
	tLCGrpAddressSchema.setHeadShip1(request.getParameter("HeadShip1"));
	tLCGrpAddressSchema.setPhone1(request.getParameter("Phone1"));
	tLCGrpAddressSchema.setE_Mail1(request.getParameter("E_Mail1"));
	tLCGrpAddressSchema.setFax1(request.getParameter("Fax1"));
	//������ϵ�˶�
	tLCGrpAddressSchema.setLinkMan2(request.getParameter("LinkMan2"));
	tLCGrpAddressSchema.setDepartment2(request.getParameter("Department2"));
	tLCGrpAddressSchema.setHeadShip2(request.getParameter("HeadShip2"));
	tLCGrpAddressSchema.setPhone2(request.getParameter("Phone2"));
	tLCGrpAddressSchema.setE_Mail2(request.getParameter("E_Mail2"));
	tLCGrpAddressSchema.setFax2(request.getParameter("Fax2"));
			
	tLCGeneralSchema.setGrpContNo(request.getParameter("GrpContNo"));
	tLCGeneralSchema.setPrtNo(request.getParameter("PrtNo"));
	tLCGeneralSchema.setExecuteCom(request.getParameter("ExecuteCom"));
	tLCGeneralSchema.setCustomerNo(request.getParameter("CustomerNo"));
	tLCGeneralSchema.setAddressNo(request.getParameter("AddressNo"));
	loggerDebug("SubContPolSave","end setSchema:");
	// ׼���������� VData
	tVData.add( tLDGrpSchema );
	tVData.add( tLCGrpAddressSchema );
	tVData.add( tLCGeneralSchema );
	tVData.add( tG );

	if( tAction.equals( "INSERT" )) tOperate = "INSERT";
	if( tAction.equals( "UPDATE" )) tOperate = "UPDATE";
	if( tAction.equals( "DELETE" )) tOperate = "DELETE";
	//GroupSubContUI tGroupSubContUI = new GroupSubContUI();
	String busiName="tbGroupSubContUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if( tBusinessDelegate.submitData( tVData, tOperate,busiName ) == false )
	{
		Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = " ����ɹ�! ";
		FlagStr = "Succ";

		tVData.clear();
		tVData = tBusinessDelegate.getResult();
		TransferData tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
		if (tTransferData != null)
		{
			tCustomerNoRet = (String) tTransferData.getValueByName("CustomerNo");
		}

		// ��ʾ
%>
<%		
	}
        loggerDebug("SubContPolSave","Content:"+Content);	

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=tCustomerNoRet%>");
</script>
</html>
