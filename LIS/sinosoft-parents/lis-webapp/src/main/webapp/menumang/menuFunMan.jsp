<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%@ include file="../common/jsp/UsrCheck.jsp" %>


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


  <%
     String FlagStr = "Fail";
      String busiName="LDMenuFunUI";
     String actionStr = request.getParameter("Action");
     //loggerDebug("menuFunMan","$$$actionStr:" + actionStr);
     int action = -1;
     if (actionStr.compareTo("query") == 0)
         action = 2;
     if (actionStr.compareTo("insert") == 0)
         action = 0;
     if (actionStr.compareTo("delete") == 0)
         action = 3;

     switch (action) {
       case 1:
       case 0:  //insert
         //loggerDebug("menuFunMan","########################");
         String NodeName = request.getParameter("NodeName");
         String RunScript = request.getParameter("RunScript");
         String ischild = request.getParameter("isChild");
         String ischild2 = request.getParameter("isChild2"); //2005
         String tRadio[] = request.getParameterValues("InpQueryGrpGridSel");
         if (tRadio == null){
           loggerDebug("menuFunMan","tRadio is null");
           FlagStr = "Fail";
         }

         if (tRadio != null) {
           loggerDebug("menuFunMan","tRadio is not null");
           int index = 0;
           for (; index< tRadio.length;index++) {
             if(tRadio[index].equals("1"))
               break;
           }
           if (index == tRadio.length) {
             loggerDebug("menuFunMan","û��ѡ�ж���!");
             FlagStr = "Fail";
           }
           else{
             String tNodeCode[] = request.getParameterValues("QueryGrpGrid1");
             String tParentCode[] = request.getParameterValues("QueryGrpGrid3");
             String NodeCode = tNodeCode[index];
             String ParentCode = tParentCode[index];

             //loggerDebug("menuFunMan","@@@@selNodeCode::"+NodeCode);
             //loggerDebug("menuFunMan","@@@@ischild::"+ischild);

             //XinYQ added on 2007-04-23 : ��ֹ���Ĵ������Ū��б�ܷ���
             if (RunScript != null && !RunScript.trim().equals(""))
             {
                 RunScript = RunScript.replaceAll("\\\\", "/");
             }

             LDMenuSchema tLDMenuSchema = new LDMenuSchema();
             tLDMenuSchema.setNodeName(NodeName);
             tLDMenuSchema.setRunScript(RunScript);
             tLDMenuSchema.setChildFlag("0");
             if (ischild.equalsIgnoreCase("true")){
               tLDMenuSchema.setParentNodeCode(NodeCode);
             }else{
               tLDMenuSchema.setParentNodeCode(ParentCode);
             }
             //ҳ��Ȩ�޲˵��ж�2005
             if (ischild2.equalsIgnoreCase("true")){
                             //1Ϊһ��˵�Ҷ�ӣ�2Ϊҳ��Ȩ�޲˵�Ҷ��2005
               tLDMenuSchema.setNodeSign("2");
               }
             //LDMenuFunUI tLDMenuFunUI = new LDMenuFunUI();
            
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
             
             TransferData tTransferData = new TransferData();
             tTransferData.setNameAndValue("ischild",ischild);
             tTransferData.setNameAndValue("ischild2",ischild2);
             tTransferData.setNameAndValue("NodeCode",NodeCode);
             tTransferData.setNameAndValue("RunScript",RunScript);
             VData tData = new VData();
             tData.add(tLDMenuSchema);
             tData.add(tTransferData);
//             if(tLDMenuFunUI.submitData(tData,"insert")){
//                 FlagStr = "success";}
//             else{
//               FlagStr = "fail";}
//            }
			if(!tBusinessDelegate.submitData(tData,"insert",busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					//Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					//Content = "����ʧ��";
					FlagStr = "Fail";				
				}
			}else{
				 FlagStr = "success";
			}
         }

       }
       break;

       case 2:
           FlagStr = "success";
           break;

       case 3:
         loggerDebug("menuFunMan","here");
           String tRadio2[] = request.getParameterValues("InpQueryGrpGridSel");
           if (tRadio2 == null){
             loggerDebug("menuFunMan","tRadio is null");
             FlagStr = "Fail";
           }
           if (tRadio2 != null) {
               loggerDebug("menuFunMan","tRadio is not null");
               int index = 0;
               for (; index< tRadio2.length;index++) {
                 if(tRadio2[index].equals("1"))
                   break;
                   }
               if (index == tRadio2.length) {
                   loggerDebug("menuFunMan","û��ѡ�ж���!");
               }
               else{
               String tNodeCode2[] = request.getParameterValues("QueryGrpGrid1");
               String NodeCode = tNodeCode2[index];

               loggerDebug("menuFunMan","jsp--NodeCode:" + NodeCode);
               String tNodeCode3[] = request.getParameterValues("QueryGrpGrid2");
               String ChildFlag = tNodeCode3[index];

               loggerDebug("menuFunMan","jsp--ChildFlag:" + ChildFlag);
               String tNodeCode4[] = request.getParameterValues("QueryGrpGrid3");
               String ParentNodeCode = tNodeCode4[index];

               loggerDebug("menuFunMan","jsp--ParentNodeCode:" + ParentNodeCode);

               //��ò˵���־2005
               String tNodeCode5[] = request.getParameterValues("QueryGrpGrid7");
               String NodeSign = tNodeCode5[index];
               //loggerDebug("menuFunMan","jsp--NodeSign:" + NodeSign);
               LDMenuSchema tLDMenuSchema = new LDMenuSchema();
               tLDMenuSchema.setNodeCode(NodeCode);
               tLDMenuSchema.setParentNodeCode(ParentNodeCode);
               tLDMenuSchema.setChildFlag(ChildFlag);
               tLDMenuSchema.setNodeSign(NodeSign);
               //LDMenuFunUI tLDMenuFunUI = new LDMenuFunUI();
               BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
               VData tData = new VData();
               tData.add(tLDMenuSchema);
//               if(tLDMenuFunUI.submitData(tData,"delete")){
//                  FlagStr = "success";}
//               else{
//                  FlagStr = "fail";}
				if(!tBusinessDelegate1.submitData(tData,"delete",busiName))
				{    
				    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
				    { 
						//Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						//Content = "����ʧ��";
						FlagStr = "Fail";				
					}
				}else{
					  FlagStr = "success";
				}
            }
     }
      break;
 }
%>


<html>
<head>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>');
        }
        catch (ex)
        {
            alert('<%=FlagStr%>');
        }
    </script>
</html>
