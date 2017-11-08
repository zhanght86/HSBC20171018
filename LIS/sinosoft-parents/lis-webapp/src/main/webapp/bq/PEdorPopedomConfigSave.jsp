<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//程序名称：PEdorPopedomConfigSave.jsp
//程序功能：保全权限配置
//创建日期：2006-01-09 15:13:22
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
//
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>  

<%
    //输出参数
    String FlagStr = "";
    String Content = "";
    String sOperate = "";
    CErrors tErrors = new CErrors();
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");

    String sEdorPopedomType = request.getParameter("EdorPopedomType");
    String sEdorPopedom = request.getParameter("EdorPopedom");
    String sGEdorPopedom = request.getParameter("GEdorPopedom");
    String tManageCom = request.getParameter("ManageCom");
//    String tLimitGetMoneyI = request.getParameter("LimitGetMoneyI");
//    String tLimitGetMoneyY = request.getParameter("LimitGetMoneyY");
//    String tLimitGetMoneyL = request.getParameter("LimitGetMoneyL");
//    String tLimitGetMoneyM = request.getParameter("LimitGetMoneyM");
//    String tLimitGetMoneyS = tLimitGetMoneyM;
    String tModifyAppDateNum = request.getParameter("ModifyAppDateNum");
//    //为空时代表最大权限，设默认值
//    if(tLimitGetMoneyI == null || tLimitGetMoneyI.trim().equals(""))
//    {
//          tLimitGetMoneyI = "99999999999999";
//    }
//    if(tLimitGetMoneyY == null || tLimitGetMoneyY.trim().equals(""))
//    {
//          tLimitGetMoneyY = "99999999999999";
//    }
//    if(tLimitGetMoneyL == null || tLimitGetMoneyL.trim().equals(""))
//    {
//          tLimitGetMoneyL = "99999999999999";
//    }
//    if(tLimitGetMoneyM == null || tLimitGetMoneyM.trim().equals(""))
//    {
//          tLimitGetMoneyM = "99999999999999";
//    }
    if(tModifyAppDateNum == null || tModifyAppDateNum.trim().equals(""))
    {
          tModifyAppDateNum = "17";
    }
    //System.out.println("tLimitMoneyI:"+tLimitGetMoneyI);
    //System.out.println("tLimitMoneyY:"+tLimitGetMoneyY);
    //System.out.println("tLimitMoneyL:"+tLimitGetMoneyL);
    //System.out.println("tLimitMoneyM:"+tLimitGetMoneyM);
    //System.out.println("tModifyAppDateNum:"+tModifyAppDateNum);
    LPEdorPopedomSchema tLPEdorPopedomSchema = null;
    LPEdorPopedomSet tLPEdorPopedomSet = new LPEdorPopedomSet();

    //XinYQ added on 2006-12-25 : 如果选择了多项，进行修正
    if (sEdorPopedomType != null && sEdorPopedomType.trim().equals("1"))
    {
        sGEdorPopedom = null;
    }
    else if (sEdorPopedomType != null && sEdorPopedomType.trim().equals("2"))
    {
        sEdorPopedom = null;
    }

    String tItemArr[] = request.getParameterValues("SelfGrid1");
    String tApplyArr[] = request.getParameterValues("SelfGrid3");
    String tApproveArr[] = request.getParameterValues("SelfGrid4");
    String tAppObj[] = request.getParameterValues("SelfGrid5");
    String tLimitGetMoney[]=request.getParameterValues("SelfGrid6");
    if (tItemArr != null)
    {
        for(int i = 0; i < tItemArr.length; i++)
        {
            tLPEdorPopedomSchema = new LPEdorPopedomSchema();

            tLPEdorPopedomSchema.setEdorCode(tItemArr[i]);
            tLPEdorPopedomSchema.setApplyFlag(tApplyArr[i]);
            tLPEdorPopedomSchema.setApproveFlag(tApproveArr[i]);
            tLPEdorPopedomSchema.setLimitGetMoney(tLimitGetMoney[i]);
            tLPEdorPopedomSet.add(tLPEdorPopedomSchema);
        }
    }
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("EdorPopedomType", sEdorPopedomType);
    tTransferData.setNameAndValue("EdorPopedom", sEdorPopedom);
    tTransferData.setNameAndValue("GEdorPopedom", sGEdorPopedom);
    tTransferData.setNameAndValue("ManageCom", tManageCom);
//    tTransferData.setNameAndValue("LimitGetMoneyI", tLimitGetMoneyI);
//    tTransferData.setNameAndValue("LimitGetMoneyY", tLimitGetMoneyY);
//    tTransferData.setNameAndValue("LimitGetMoneyL", tLimitGetMoneyL);
//    tTransferData.setNameAndValue("LimitGetMoneyM", tLimitGetMoneyM);
//    tTransferData.setNameAndValue("LimitGetMoneyS", tLimitGetMoneyS);
    tTransferData.setNameAndValue("ModifyAppDateNum", tModifyAppDateNum);
    
    System.out.println("-------------" + tLPEdorPopedomSet.encode());
    VData tVData = new VData();
    tVData.add(tG);
    tVData.add(tTransferData);
    tVData.add(tLPEdorPopedomSet);
    tG = null;
    tTransferData = null;
    tLPEdorPopedomSet = null;

   String busiName="PEdorPopedomConfigUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   if(!tBusinessDelegate.submitData(tVData,sOperate,busiName))
   {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "保存失败";
				   FlagStr = "Fail";				
				}
   }
   else
   {
      	Content = " 操作成功! ";
       	FlagStr = "Succ";  
   }    

%>


<html>
<head>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert('<%=Content%>');
        }
    </script>
</html>
