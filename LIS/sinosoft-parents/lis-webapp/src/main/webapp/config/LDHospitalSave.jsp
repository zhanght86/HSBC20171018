<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�HospitalSave.jsp
//������
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.config.*"%>
<%@page import="java.util.*"%>
<%
//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI = ( GlobalInput )session.getValue( "GI" );
        
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
}
else //ҳ����Ч
{
    try  {
        LDHospitalManageUI tLDHospitalManageUI  = new LDHospitalManageUI();
        LDHospitalSchema tLDHospitalSchema = new LDHospitalSchema();
        LDHospitalSet tLDHospitalSet = new LDHospitalSet();
        VData tVData = new VData();
        String tOperate = request.getParameter("fmtransact");
        /*
        HospitalCode
		HospitalName
		HospitalGrade
		ManageCom
		ValidityDate
		Address
        */
        //���ҽԺ����
        String tHospitalCode = request.getParameter("HospitalCode");
        String tHospitalName = request.getParameter("HospitalName");
        String tHospitalGrade = request.getParameter("HospitalGrade");
        String tManageCom = request.getParameter("ManageCom");
        String tValidityDate = request.getParameter("ValidityDate");
        String tAddress = request.getParameter("Address");
        String tRemark = request.getParameter("Remark");
        String tHosState = request.getParameter("HosState");
        tLDHospitalSchema.setHospitCode(tHospitalCode);
        tLDHospitalSchema.setHospitalName(tHospitalName);
        tLDHospitalSchema.setHospitalGrade(tHospitalGrade);
        tLDHospitalSchema.setMngCom(tManageCom);
        tLDHospitalSchema.setValidityDate(tValidityDate);
        tLDHospitalSchema.setAddress(tAddress);
        tLDHospitalSchema.setHosState(tHosState);
        tLDHospitalSchema.setRemark(tRemark);
        tLDHospitalSet.add(tLDHospitalSchema);
        // ׼���������� VData
        tVData.addElement(tLDHospitalSet);
        tVData.addElement(tGI);
        
        if(!tLDHospitalManageUI.submitData(tVData,tOperate))
        {
            Content = "���ݲ���ʧ�ܣ�ԭ����: " +
                      tLDHospitalManageUI.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";
        }
        else
        {
            Content = "���ݲ����ɹ�";
            FlagStr = "Succ";
        }
    }
    catch(Exception ex){
        FlagStr = "Fail";
        Content = "���ݲ���ʧ�ܣ�ԭ����:" + ex.toString();
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

