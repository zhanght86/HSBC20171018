<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ReInsureInit.jsp
//程序功能：人工核保未过原因查询
//创建日期：2002-06-19 11:10:36
//创建人  ：zhangbin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  
<%
	String tPrtNo=request.getParameter("PrtNo");
	String tOpeFlag = request.getParameter("OpeFlag");
	String tProposalNo = request.getParameter("ProposalNo");
	String tDutyCode = request.getParameter("DutyCode");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
	fm.Remark.value="在此录入回复意见";
  fmInfo.PrtNo.value="<%=tPrtNo%>";
	fmInfo.OpeFlag.value="<%=tOpeFlag%>";
	fmInfo.ProposalNo.value="<%=tProposalNo%>";
	fmInfo.DutyCode.value="<%=tDutyCode%>";
	fm.AuditType.value="<%=tOpeFlag%>";
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  { 
  }
  catch(ex)
  {
    myAlert("在ReInsureInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
	  initInpBox();
		initSelBox();
		initRiskInfoGrid();
		initEdorInfoGrid();
		initClaimInfoGrid();
		initReInsureAuditGrid();
  	initTempCess();
  }
  catch(re)
  {
    myAlert("ReInsureInit.jsp-->"+"初始化界面错误!");
  }
  	//ReInsureAudit();
  	//QueryReInsureAudit();
}


// 责任信息列表的初始化
function initRiskInfoGrid()
{                               
    var iArray = new Array();
    
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          				//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="被保险人";    			//列名
      iArray[1][1]="80px";            	//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种编码";         	//列名
      iArray[2][1]="80px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="责任编码";         	//列名
      iArray[3][1]="60px";            	//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			
      iArray[4]=new Array();
      iArray[4][0]="保额";         			//列名
      iArray[4][1]="60px";            	//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="保费";         			//列名
      iArray[5][1]="60px";            	//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="再保任务状态";      //列名
      iArray[6][1]="80px";            	//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="险种编码";         		//列名
      iArray[7][1]="80px";            	//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="发送次数";         	//列名
      iArray[8][1]="80px";            	//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="再保任务状态编码";  //列名
      iArray[9][1]="80px";            	//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允
      
      RiskInfoGrid = new MulLineEnter( "fm" , "RiskInfoGrid" ); 
      //这些属性必须在loadMulLine前
      RiskInfoGrid.mulLineCount = 0;   
      RiskInfoGrid.displayTitle = 1;
      RiskInfoGrid.locked = 1;
      RiskInfoGrid.hiddenPlus = 1;
      RiskInfoGrid.canSel = 1;
      RiskInfoGrid.hiddenSubtraction = 1;
      RiskInfoGrid.selBoxEventFuncName = "showTaskInfo";
      RiskInfoGrid.loadMulLine(iArray);  
   }
   
   catch(ex)
   {
     myAlert(ex);
   }
}

function initEdorInfoGrid()
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
      iArray[1][0]="被保险人";    	//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种编码";         			//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="责任编码";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			
      iArray[4]=new Array();
      iArray[4][0]="保全批次号";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="保全类型";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="保额";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[7]=new Array();
      iArray[7][0]="保费";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
      
      
      iArray[8]=new Array();
      iArray[8][0]="再保任务状态";      //列名
      iArray[8][1]="80px";            	//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="险种编码";         		//列名
      iArray[9][1]="20px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="发送次数";         //列名
      iArray[10][1]="80px";            	//列宽
      iArray[10][2]=100;            		//列最大值
      iArray[10][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="再保任务状态编码";         //列名
      iArray[11][1]="80px";            	//列宽
      iArray[11][2]=100;            		//列最大值
      iArray[11][3]=3;              		//是否允许输入,1表示允许，0表示不允许

      EdorInfoGrid = new MulLineEnter( "fm" , "EdorInfoGrid" ); 
      //这些属性必须在loadMulLine前
      EdorInfoGrid.mulLineCount = 0;   
      EdorInfoGrid.displayTitle = 1;
      EdorInfoGrid.locked = 1;
      EdorInfoGrid.hiddenPlus = 1;
      EdorInfoGrid.canSel = 1;
      EdorInfoGrid.hiddenSubtraction = 1;
      EdorInfoGrid.selBoxEventFuncName = "showTaskInfo";
      EdorInfoGrid.loadMulLine(iArray);  
   }
   catch(ex){
     myAlert(ex);
   }
}

// 理赔信息列表的初始化
function initClaimInfoGrid()
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
      iArray[1][0]="被保险人";    	//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种编码";         			//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="责任编码";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			
      iArray[4]=new Array();
      iArray[4][0]="赔案号";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="实赔金额";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="再保任务状态";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="险种编码";         		//列名
      iArray[7][1]="20px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="发送次数";         		//列名
      iArray[8][1]="20px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="再保任务状态编码";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      ClaimInfoGrid = new MulLineEnter( "fm" , "ClaimInfoGrid" ); 
      //这些属性必须在loadMulLine前
      ClaimInfoGrid.mulLineCount = 0;   
      ClaimInfoGrid.displayTitle = 1;
      ClaimInfoGrid.locked = 1;
      ClaimInfoGrid.hiddenPlus = 1;
      ClaimInfoGrid.canSel = 1;
      ClaimInfoGrid.hiddenSubtraction = 1;
      ClaimInfoGrid.selBoxEventFuncName = "showTaskInfo";
      ClaimInfoGrid.loadMulLine(iArray);  
   }
   
   catch(ex)
   {
     myAlert(ex);
   }
}

function initReInsureAuditGrid(){
    var iArray = new Array();
      
    try{
    	iArray[0]=new Array();
    	iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    	iArray[0][1]="30px";         			//列宽
    	iArray[0][2]=10;          				//列最大值
    	iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[1]=new Array();
    	iArray[1][0]="被保险人";    			//列名
    	iArray[1][1]="80px";            	//列宽
    	iArray[1][2]=100;            			//列最大值
    	iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[2]=new Array();
    	iArray[2][0]="险种编码";         	//列名
    	iArray[2][1]="80px";            	//列宽
    	iArray[2][2]=100;            			//列最大值
    	iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[3]=new Array();
    	iArray[3][0]="责任编码";         	//列名
    	iArray[3][1]="80px";            	//列宽
    	iArray[3][2]=100;            			//列最大值
    	iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[4]=new Array();
    	iArray[4][0]="发送序号";         	//列名
    	iArray[4][1]="60px";            	//列宽
    	iArray[4][2]=100;            			//列最大值
    	iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    	
    	
    	iArray[5]=new Array();
    	iArray[5][0]="发送人";         		//列名
    	iArray[5][1]="60px";            	//列宽
    	iArray[5][2]=100;            			//列最大值
    	iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[6]=new Array();
    	iArray[6][0]="发送/回复意见";     //列名
    	iArray[6][1]="60px";            	//列宽
    	iArray[6][2]=100;            			//列最大值
    	iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[7]=new Array();
    	iArray[7][0]="发送时间";         	//列名
    	iArray[7][1]="80px";            	//列宽
    	iArray[7][2]=100;            			//列最大值
    	iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[8]=new Array();
    	iArray[8][0]="上载路径";         	//列名
    	iArray[8][1]="100px";            	//列宽
    	iArray[8][2]=100;            			//列最大值
    	iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[9]=new Array();
    	iArray[9][0]="发送/回复标志";     //列名
    	iArray[9][1]="100px";            	//列宽
    	iArray[9][2]=100;            			//列最大值
    	iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[10]=new Array();
    	iArray[10][0]="再保任务状态编码";  //列名
    	iArray[10][1]="80px";            	//列宽
    	iArray[10][2]=100;            			//列最大值
    	iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    	
    	ReInsureAuditGrid = new MulLineEnter( "fm" , "ReInsureAuditGrid" ); 
    	//这些属性必须在loadMulLine前
    	ReInsureAuditGrid.mulLineCount = 0;   
    	ReInsureAuditGrid.displayTitle = 1;
    	ReInsureAuditGrid.locked = 1;
    	ReInsureAuditGrid.canSel = 1;
    	ReInsureAuditGrid.hiddenPlus = 1;
    	ReInsureAuditGrid.hiddenSubtraction = 1;
    	ReInsureAuditGrid.selBoxEventFuncName = "showAnswerIdea";
    	ReInsureAuditGrid.loadMulLine(iArray);  
    }
    
    catch(ex){
      myAlert(ex);
    }
}

</script>




