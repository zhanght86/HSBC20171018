<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.schema.InterfacePToSInfoSchema"%>
<%@page import="com.sinosoft.lis.schema.SUBRISKCODETOPLANCODEINFOSchema"%>
<%@page import="com.sinosoft.lis.vschema.SUBRISKCODETOPLANCODEINFOSet"%>
<%@page import="com.sinosoft.productdef.InterfacePToSInfoBL"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
System.out.println("sava .........................");
  String FlagStr = "Successs";
  String Content = ""+"提交成功！"+"";
  GlobalInput tG = (GlobalInput)session.getAttribute("GI");

  try{
  TransferData tTransferData = new TransferData();
    String operator=request.getParameter("Operator");
        tTransferData.setNameAndValue("Operator", operator);
        System.out.println(operator);
        InterfacePToSInfoSchema mInterfacePToSInfoSchema=new InterfacePToSInfoSchema();
mInterfacePToSInfoSchema.setProductCode(request.getParameter("ProductCode"));
mInterfacePToSInfoSchema.setProductCHName(request.getParameter("ProductChName"));
mInterfacePToSInfoSchema.setProductENName(request.getParameter("ProductEnName"));
mInterfacePToSInfoSchema.setIProductCode(request.getParameter("IProductCode"));
mInterfacePToSInfoSchema.setIProductCHName(request.getParameter("IProductChName"));
mInterfacePToSInfoSchema.setIProductENName(request.getParameter("IProductEnName"));
//mInterfacePToSInfoSchema.setWithoutPolicyIssued(request.getParameter("WithoutPolicyIssued"));
mInterfacePToSInfoSchema.setIsEffectiveDate(request.getParameter("IsEffectiveDate"));
mInterfacePToSInfoSchema.setEffectiveDate(request.getParameter("EffectiveDate"));

mInterfacePToSInfoSchema.setEffectiveEndDate(request.getParameter("EffectiveEndDate"));

mInterfacePToSInfoSchema.setIsSpouseCode(request.getParameter("IsSpouseCode"));
mInterfacePToSInfoSchema.setSpouseCode(request.getParameter("SpouseCode"));
mInterfacePToSInfoSchema.setIsStaffCode(request.getParameter("IsStaffCode"));
mInterfacePToSInfoSchema.setStaffCode(request.getParameter("StaffCode"));

mInterfacePToSInfoSchema.setIsJoinCode(request.getParameter("IsJoinCode"));
mInterfacePToSInfoSchema.setJoinCode(request.getParameter("JoinCode"));

mInterfacePToSInfoSchema.setIsPremPayPeriod(request.getParameter("IsPremPayPeriod"));
mInterfacePToSInfoSchema.setPremPayPeriodType(request.getParameter("PremPayPeriodType"));
mInterfacePToSInfoSchema.setPremPayPeriod(request.getParameter("PremPayPeriod"));
mInterfacePToSInfoSchema.setIsBenefitOption(request.getParameter("IsBenefitOption"));
mInterfacePToSInfoSchema.setBenefitOptionType(request.getParameter("BenefitOptionType"));
mInterfacePToSInfoSchema.setBenefitOption(request.getParameter("BenefitOption"));
mInterfacePToSInfoSchema.setIsBenefitPeriod(request.getParameter("IsBenefitPeriod"));
mInterfacePToSInfoSchema.setBenefitPeriodType(request.getParameter("BenefitPeriodType"));
mInterfacePToSInfoSchema.setBenefitPeriod(request.getParameter("BenefitPeriod"));
mInterfacePToSInfoSchema.setIsChannel(request.getParameter("IsChannel"));
mInterfacePToSInfoSchema.setChannel(request.getParameter("Channel"));
mInterfacePToSInfoSchema.setIsCurrency(request.getParameter("IsCurrency"));
mInterfacePToSInfoSchema.setCurrency(request.getParameter("Currency"));
mInterfacePToSInfoSchema.setIsPar(request.getParameter("IsPar"));
mInterfacePToSInfoSchema.setPar(request.getParameter("Par"));

mInterfacePToSInfoSchema.setIsUreUWType(request.getParameter("IsUreUWType"));
mInterfacePToSInfoSchema.setUreUWType(request.getParameter("UreUWType"));
mInterfacePToSInfoSchema.setBatchNo(request.getParameter("BatchNo"));
mInterfacePToSInfoSchema.setOPERATOR(operator);

//mInterfacePToSInfoSchema.setRemarks(request.getParameter("Remarks"));
//对附加险的处理
//-------------------------------------------------------------------
//String s = null;
//System.out.println(s.equals("a"));
String[] subrisk0=request.getParameterValues("SubRiskMullineGrid1");
String[] subrisk1=request.getParameterValues("SubRiskMullineGrid1");
String[] subrisk2=request.getParameterValues("SubRiskMullineGrid2");
String[] subrisk3=request.getParameterValues("SubRiskMullineGrid3");
String[] subrisk4=request.getParameterValues("SubRiskMullineGrid4");
String[] subrisk5=request.getParameterValues("SubRiskMullineGrid5");
String[] subrisk6=request.getParameterValues("SubRiskMullineGrid6");
String[] subrisk7=request.getParameterValues("SubRiskMullineGrid7");
String[] subrisk8=request.getParameterValues("SubRiskMullineGrid6");
String[] subrisk9=request.getParameterValues("SubRiskMullineGrid6");
System.out.println(subrisk0+"..........................");
SUBRISKCODETOPLANCODEINFOSet tSUBRISKCODETOPLANCODEINFOSet=new SUBRISKCODETOPLANCODEINFOSet();
if(subrisk0!=null){
System.out.println(subrisk0.length+"..........................");

for(int i=0;i<subrisk0.length;i++){	
SUBRISKCODETOPLANCODEINFOSchema tSUBRISKCODETOPLANCODEINFO=new SUBRISKCODETOPLANCODEINFOSchema();
System.out.println("SUBRISKCODETOPLANCODEINFOSchema...................."+i);
tSUBRISKCODETOPLANCODEINFO.setRISKCODE(mInterfacePToSInfoSchema.getProductCode());
tSUBRISKCODETOPLANCODEINFO.setPLANCODE(mInterfacePToSInfoSchema.getIProductCode());
tSUBRISKCODETOPLANCODEINFO.setISBENEFITOPTION(mInterfacePToSInfoSchema.getIsBenefitOption());
tSUBRISKCODETOPLANCODEINFO.setISBENEFITOPTION(mInterfacePToSInfoSchema.getBenefitOptionType());
tSUBRISKCODETOPLANCODEINFO.setISBENEFITOPTION(mInterfacePToSInfoSchema.getBenefitOption());
if(subrisk5[i]==null||subrisk5[i].trim()==""){
	throw new Exception("N");
	
}
tSUBRISKCODETOPLANCODEINFO.setSUBRISKCODE(subrisk1[i]);
System.out.println(subrisk3[i]+"........subrisk3[]................");
System.out.println(subrisk5[i]+"........subrisk5[]................");
System.out.println(subrisk6[i]+"........subrisk6[]................");
System.out.println(subrisk7[i]+"........subrisk7[]................");

tSUBRISKCODETOPLANCODEINFO.setSUBPLANCODE(subrisk2[i]);
tSUBRISKCODETOPLANCODEINFO.setBATCHNO(subrisk3[i]);
tSUBRISKCODETOPLANCODEINFO.setOPERATOR(operator);
tSUBRISKCODETOPLANCODEINFO.setISBENEFITOPTION(subrisk5[i]);
tSUBRISKCODETOPLANCODEINFO.setBENEFITOPTIONTYPE(subrisk6[i]);
tSUBRISKCODETOPLANCODEINFO.setBENEFITOPTION(subrisk7[i]);
tSUBRISKCODETOPLANCODEINFOSet.add(tSUBRISKCODETOPLANCODEINFO);
}
  }
//-------------------------------------------------------------------
   System.out.println(operator);
  VData tVData = new VData();
  tVData.addElement(tG);
  tVData.addElement(tTransferData);
   tVData.addElement(mInterfacePToSInfoSchema);
   tVData.addElement(tSUBRISKCODETOPLANCODEINFOSet);
  InterfacePToSInfoBL mInterfacePToSInfoBL=new InterfacePToSInfoBL();
   if(!mInterfacePToSInfoBL.submitData(tVData,operator)){
   FlagStr = "Fail";
   Content=""+"提交失败！"+""+mInterfacePToSInfoBL.getErrors().getFirstError();
   }
   }catch(Exception e){
   e.printStackTrace();
      FlagStr = "Fail";
   Content=""+"提交失败！"+""+e.getMessage();
   }
%> 




<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>