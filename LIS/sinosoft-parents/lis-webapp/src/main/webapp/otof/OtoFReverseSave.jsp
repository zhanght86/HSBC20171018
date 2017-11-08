<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.otof.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
	String Content = "";
	String FlagStr ="";
	OFInterfaceSet tOFInterfaceSet=new OFInterfaceSet();
	TransferData tTransferData=new TransferData();  
	tTransferData.setNameAndValue("Reason",StrTool.unicodeToGBK(request.getParameter("Reason")));
	tTransferData.setNameAndValue("AccountingDate",request.getParameter("AccountingDate"));
  
	String transact = request.getParameter("fmAction");
	if(transact.equals("Reverse"))
	{
	  String tGridNo[] = request.getParameterValues("CodeGridNo");
	  String tBatchNo[] = request.getParameterValues("CodeGrid1");
	  String tMatchID[] = request.getParameterValues("CodeGrid2");
	  String tManageCom[] = request.getParameterValues("CodeGrid9");
	  String tTransDate[] = request.getParameterValues("CodeGrid10");
	  String tPolno[] = request.getParameterValues("CodeGrid11");
	  String tBussno[] = request.getParameterValues("CodeGrid12");
	  String tAccountingDate[] = request.getParameterValues("CodeGrid13");
	  String tVoucherType[] = request.getParameterValues("CodeGrid14");
	  String lastBatchNo="";
	  String lastMatchID="";
	  String lastPolno="";
	  String lastBussno="";
	  int Count = tGridNo.length;
	  String tFlagchk[]=request.getParameterValues("InpCodeGridChk");
	  int iMax = tFlagchk.length;
	  for (int i=0;i<iMax;i++)
	  {
	  	if(tFlagchk[i].equals("1"))
	  	{
	  		FlagStr="Fail";
	  		if(tBatchNo[i].equals(lastBatchNo)&&tMatchID[i].equals(lastMatchID)&&tPolno[i].equals(lastPolno)&&tBussno[i].equals(lastBussno))
	  		{
	  			continue;
	  		}else
	  		{
		  		OFInterfaceSchema tOFInterfaceSchema=new OFInterfaceSchema();
				tOFInterfaceSchema.setBatchNo(tBatchNo[i]);
				tOFInterfaceSchema.setMatchID(tMatchID[i]);
				tOFInterfaceSchema.setManageCom(tManageCom[i]);
				tOFInterfaceSchema.setTransDate(tTransDate[i]);
				tOFInterfaceSchema.setPolNo(tPolno[i]);
				tOFInterfaceSchema.setBussNo(tBussno[i]);
				tOFInterfaceSchema.setAccountingDate(tAccountingDate[i]);
				tOFInterfaceSchema.setVoucherType(tVoucherType[i]);
				tOFInterfaceSet.add(tOFInterfaceSchema);
				lastBatchNo=tBatchNo[i];
				lastMatchID=tMatchID[i];
				lastPolno=tPolno[i];
				lastBussno=tBussno[i];
			}
		}
	  }
	}else if(transact.equals("ReverseAll"))
	{
		String VoucherType = request.getParameter("VoucherType");
		String ManageCom = request.getParameter("ManageCom");
		String ComCode = request.getParameter("ComCode");
		String TransDate = request.getParameter("TransDate");
		String VoucherID = request.getParameter("VoucherID");
		String PolNo = request.getParameter("PolNo");
		String BussNo = request.getParameter("BussNo");
	  String lastBatchNo="";
	  String lastMatchID="";
	  String lastPolno="";
	  String lastBussno="";
		String strsql="select distinct BatchNo,MatchID,ManageCom,TransDate,polno,bussno,AccountingDate,VoucherType from ofinterface where (ReversedStatus='0') and VoucherFlag<>'NA' and VoucherID<>'-1' and VoucherDate is not null ";
		if(!VoucherType.equals(""))
		{
			strsql+=" and VoucherType='"+VoucherType+"' ";
		}
		if(!ManageCom.equals(""))
		{
			strsql+=" and ManageCom like '"+ManageCom+"%' ";
		}
		if(ManageCom.equals(""))
		{
			strsql+=" and ManageCom like '"+ComCode+"%' ";
		}
		if(!TransDate.equals(""))
		{
			strsql+=" and TransDate='"+TransDate+"' ";
		}
		if(!VoucherID.equals(""))
		{
			strsql+=" and VoucherID='"+VoucherID+"' ";
		}
		if(!PolNo.equals(""))
		{
			strsql+=" and PolNo='"+PolNo+"' ";
		}
		if(!BussNo.equals(""))
		{
			strsql+=" and BussNo='"+BussNo+"' ";
		}
		strsql+=" order by BatchNo,MatchID,polno,bussno";
		loggerDebug("OtoFReverseSave","strsql:"+strsql);
		ExeSQL tExeSQL=new ExeSQL();
		SSRS tSSRS=new SSRS();
		tSSRS=tExeSQL.execSQL(strsql);
		if(tSSRS!=null&&tSSRS.getMaxRow()!=0)
		{
			FlagStr="Fail";
			for(int i=1;i<=tSSRS.getMaxRow();i++)
			{
			  if(tSSRS.GetText(i,1).equals(lastBatchNo)&&tSSRS.GetText(i,2).equals(lastMatchID)&&tSSRS.GetText(i,5).equals(lastPolno)&&tSSRS.GetText(i,6).equals(lastBussno))
			  {
			  continue;
			  }
			else
				{
				OFInterfaceSchema tOFInterfaceSchema=new OFInterfaceSchema();
				tOFInterfaceSchema.setBatchNo(tSSRS.GetText(i,1));
				tOFInterfaceSchema.setMatchID(tSSRS.GetText(i,2));
				tOFInterfaceSchema.setManageCom(tSSRS.GetText(i,3));
				tOFInterfaceSchema.setTransDate(tSSRS.GetText(i,4));
				tOFInterfaceSchema.setPolNo(tSSRS.GetText(i,5));
				tOFInterfaceSchema.setBussNo(tSSRS.GetText(i,6));
				tOFInterfaceSchema.setAccountingDate(tSSRS.GetText(i,7));
				tOFInterfaceSchema.setVoucherType(tSSRS.GetText(i,8));
				tOFInterfaceSet.add(tOFInterfaceSchema);
				lastBatchNo=tSSRS.GetText(i,1);
				lastMatchID=tSSRS.GetText(i,2);
				lastPolno=tSSRS.GetText(i,5);
				lastBussno=tSSRS.GetText(i,6);
				}
			}
		}
	}
	GlobalInput tG = new GlobalInput(); 
	tG = (GlobalInput)session.getValue("GI");

	VData tVData = new VData();
	tVData.add(tG);
	tVData.add(tOFInterfaceSet);
	tVData.add(tTransferData);  
  
	OtoFReverseUI tOtoFReverseUI = new OtoFReverseUI();

	if(FlagStr.equals("Fail"))
	{
		if(tOtoFReverseUI.submitData(tVData,transact))
		{
			Content = "财务凭证冲销成功! ";
			FlagStr = "Success";
		}
		else
		{
			Content = "财务凭证冲销失败，原因是:" + tOtoFReverseUI.mErrors.getFirstError();
			FlagStr = "Fail";
		}
	}
	else
	{
		Content = "请选择需要冲销的凭证信息 ";
		FlagStr = "Fail";
	}
%>                      

<html>
<script language="javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
