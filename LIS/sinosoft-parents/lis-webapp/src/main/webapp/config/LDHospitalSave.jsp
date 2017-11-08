<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：HospitalSave.jsp
//程序功能
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.config.*"%>
<%@page import="java.util.*"%>
<%
//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI = ( GlobalInput )session.getValue( "GI" );
        
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
}
else //页面有效
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
        //体检医院编码
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
        // 准备传输数据 VData
        tVData.addElement(tLDHospitalSet);
        tVData.addElement(tGI);
        
        if(!tLDHospitalManageUI.submitData(tVData,tOperate))
        {
            Content = "数据操作失败，原因是: " +
                      tLDHospitalManageUI.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";
        }
        else
        {
            Content = "数据操作成功";
            FlagStr = "Succ";
        }
    }
    catch(Exception ex){
        FlagStr = "Fail";
        Content = "数据操作失败，原因是:" + ex.toString();
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

