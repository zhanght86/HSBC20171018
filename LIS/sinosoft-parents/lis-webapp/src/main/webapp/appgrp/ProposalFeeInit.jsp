<%
//程序名称：ProposalInput.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：HST
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
<%
	String tPolNo = request.getParameter("polNo");
	String polType = request.getParameter("polType");
	String prtNo = request.getParameter("prtNo");
  loggerDebug("ProposalFeeInit","prtNo:" + prtNo);
%>
// 输入框的初始化（单记录部分）
function initInpBox()
{ 
	fm.all('PolNo').value = "<%=tPolNo%>";
	fm.all('polType').value = "<%=polType%>";
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在ProposalFeeInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
  	initInpBox();
	initFeeGrid();
	queryFee();
  }
  catch(re)
  {
    alert("ProposalFeeInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 领取项信息列表的初始化
function initFeeGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="暂交费收据号";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="交费金额";         			//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="交费日期";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="到帐日期";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      FeeGrid = new MulLineEnter( "fm" , "FeeGrid" ); 
      //这些属性必须在loadMulLine前
      FeeGrid.mulLineCount = 0;   
      FeeGrid.displayTitle = 1;
      FeeGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //FeeGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
<%
	// 初始化时的查询暂交费的函数
	out.println("<script language=javascript>");
	out.println("function queryFee()");
	out.println("{");
	//输出参数
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	if (!tPolNo.equals(""))
	{	
		// 暂交费信息
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			
		tLJTempFeeSchema.setOtherNo( tPolNo );
		if( polType.equals( "GROUP" ))
			tLJTempFeeSchema.setOtherNoType( "1" );
		if( polType.equals( "PROPOSAL" ))
			tLJTempFeeSchema.setOtherNoType( "0" );
			
	  tLJTempFeeSchema.setTempFeeNo( prtNo );
	  tLJTempFeeSchema.setOperState("0");    
	
	  	// 准备传输数据 VData
		VData tVData = new VData();
	
		tVData.addElement( tLJTempFeeSchema );
	
	  	// 数据传输
	  	ProposalFeeUI tProposalFeeUI   = new ProposalFeeUI();
		if (tProposalFeeUI.submitData(tVData,"QUERY||MAIN") == false)
		{
	      Content = " 查询失败，原因是: " + tProposalFeeUI.mErrors.getError(0).errorMessage;
	      FlagStr = "Fail";
		}
		else
		{
			tVData.clear();
			tVData = tProposalFeeUI.getResult();
			
			// 显示
			LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet(); 
			mLJTempFeeSet.set((LJTempFeeSet)tVData.getObjectByObjectName("LJTempFeeSet",0));
			int feeCount = mLJTempFeeSet.size();
			for (int i = 1; i <= feeCount; i++)
			{
			  	LJTempFeeSchema mLJTempFeeSchema = mLJTempFeeSet.get(i);
			   	%>
			   	  FeeGrid.addOne();
			   	  FeeGrid.setRowColData(<%=i-1%>, 1, "<%=mLJTempFeeSchema.getTempFeeNo()%>");
			   	  FeeGrid.setRowColData(<%=i-1%>, 2, "<%=mLJTempFeeSchema.getPayMoney()%>");
			   	  FeeGrid.setRowColData(<%=i-1%>, 3, "<%=mLJTempFeeSchema.getPayDate()%>");
			   	  FeeGrid.setRowColData(<%=i-1%>, 4, "<%=mLJTempFeeSchema.getEnterAccDate()%>");
				<%
			} // end of for
		} // end of if
	} // end of if
	out.println("}");
	out.println("</script>");
	loggerDebug("ProposalFeeInit","ProposalFeeQuery End:" + Content + "\n" + FlagStr);
%>
	
