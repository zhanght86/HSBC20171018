<%
//程序名称：ProposalDutyInit.jsp
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
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
<%
  String tPolNo = "";
  tPolNo = request.getParameter("PolNo");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

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
    alert("在ProposalDutyInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tPolNo)
{
  try
  {
	initDutyGrid();
	initPremGrid();
	initGetGrid();
	queryDuty();
  }
  catch(re)
  {
    alert("ProposalDutyInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 责任信息列表的初始化
function initDutyGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="责任名称";         			//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保费";         			//列名
      iArray[3][1]="150px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保额";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //这些属性必须在loadMulLine前
      DutyGrid.mulLineCount = 1;   
      DutyGrid.displayTitle = 1;
      DutyGrid.hiddenPlus = 1;
      DutyGrid.hiddenSubtraction = 1;
      DutyGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保费项信息列表的初始化
function initPremGrid()
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
      iArray[1][0]="责任编码";         	//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="保费项编码";         	//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保费";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=80;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="交费间隔";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=80;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="起交日期";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=80;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="终交日期";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=80;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      PremGrid = new MulLineEnter( "fm" , "PremGrid" ); 
      //这些属性必须在loadMulLine前
      PremGrid.mulLineCount = 1;   
      PremGrid.displayTitle = 1;
      PremGrid.hiddenPlus = 1;
      PremGrid.hiddenSubtraction = 1;
      PremGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //PremGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 领取项信息列表的初始化
function initGetGrid()
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
      iArray[1][0]="责任编码";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="领取项编码";         			//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保额";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="领取间隔";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="起领日期";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="终领日期";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      GetGrid = new MulLineEnter( "fm" , "GetGrid" ); 
      //这些属性必须在loadMulLine前
      GetGrid.mulLineCount = 1;   
      GetGrid.displayTitle = 1;
      GetGrid.hiddenPlus = 1;
      GetGrid.hiddenSubtraction = 1;
      GetGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //GetGrid.setRowColData(3,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>

<%
	// 初始化时的查询责任的函数
	out.println("<script language=javascript>");
	out.println("function queryDuty()");
	out.println("{");
 	//输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	
	if (!tPolNo.equals(""))
		{
		// 责任信息
		LCDutySchema tLCDutySchema = new LCDutySchema();

		tLCDutySchema.setPolNo( tPolNo );
	
	  	// 准备传输数据 VData
		VData tVData = new VData();
	
		tVData.addElement( tLCDutySchema );
	
	  	// 数据传输
	  	DutyQueryUI tDutyQueryUI   = new DutyQueryUI();
		if (tDutyQueryUI.submitData(tVData,"QUERY||MAIN") == false)
		{
	      Content = " 查询失败，原因是: " + tDutyQueryUI.mErrors.getError(0).errorMessage;
	      FlagStr = "Fail";
		}
		else
		{
			tVData.clear();
			tVData = tDutyQueryUI.getResult();
			
			// 显示
			// 责任部分
			LCDutySet mLCDutySet = new LCDutySet(); 
			mLCDutySet.set((LCDutySet)tVData.getObjectByObjectName("LCDutySet",0));
			int dutyCount = mLCDutySet.size();
			for (int i = 1; i <= dutyCount; i++)
			{
			  	LCDutySchema mLCDutySchema = mLCDutySet.get(i);
			   	%>
		   	  		DutyGrid.addOne("DutyGrid")
			   		fm.DutyGrid1[<%=i-1%>].value="<%=mLCDutySchema.getDutyCode()%>";
			   		fm.DutyGrid3[<%=i-1%>].value="<%=mLCDutySchema.getPrem()%>";
			   		fm.DutyGrid4[<%=i-1%>].value="<%=mLCDutySchema.getAmnt()%>";
				<%
			}
			// end of for
		
			// 保费项
			LCPremSet mLCPremSet = new LCPremSet(); 
			mLCPremSet.set((LCPremSet)tVData.getObjectByObjectName("LCPremSet",0));
			int premCount = mLCPremSet.size();
			for (int i = 1; i <= premCount; i++)
			{
			  	LCPremSchema mLCPremSchema = mLCPremSet.get(i);
			   	%>
		   	  		PremGrid.addOne("PremGrid")
			   		fm.PremGrid1[<%=i-1%>].value="<%=mLCPremSchema.getDutyCode()%>";
			   		fm.PremGrid2[<%=i-1%>].value="<%=mLCPremSchema.getPayPlanCode()%>";
			   		fm.PremGrid3[<%=i-1%>].value="<%=mLCPremSchema.getPrem()%>";
			   		fm.PremGrid4[<%=i-1%>].value="<%=mLCPremSchema.getPayIntv()%>";
			   		fm.PremGrid5[<%=i-1%>].value="<%=mLCPremSchema.getPayStartDate()%>";
			   		fm.PremGrid6[<%=i-1%>].value="<%=mLCPremSchema.getPayEndDate()%>";
				<%
			} // end of for
	
			// 领取项
			LCGetSet mLCGetSet = new LCGetSet(); 
			mLCGetSet.set((LCGetSet)tVData.getObjectByObjectName("LCGetSet",0));
			int getCount = mLCGetSet.size();
			for (int i = 1; i <= getCount; i++)
			{
			  	LCGetSchema mLCGetSchema = mLCGetSet.get(i);
			   	%>
		   	  		GetGrid.addOne("GetGrid")
			   		fm.GetGrid1[<%=i-1%>].value="<%=mLCGetSchema.getDutyCode()%>";
			   		fm.GetGrid2[<%=i-1%>].value="<%=mLCGetSchema.getGetDutyCode()%>";
			   		fm.GetGrid3[<%=i-1%>].value="<%=mLCGetSchema.getActuGet()%>";
			   		fm.GetGrid4[<%=i-1%>].value="<%=mLCGetSchema.getGetIntv()%>";
			   		fm.GetGrid5[<%=i-1%>].value="<%=mLCGetSchema.getGetStartDate()%>";
			   		fm.GetGrid6[<%=i-1%>].value="<%=mLCGetSchema.getGetEndDate()%>";
				<%
			} // end of for
		} // end of if
	} // end of if
	out.println("}");
	out.println("</script>");
%>

