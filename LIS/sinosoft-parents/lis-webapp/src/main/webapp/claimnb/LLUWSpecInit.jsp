<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLUWSpecInit.jsp
//程序功能：二核特约承保
//创建日期：2005-11-04 
//创建人  ：万泽辉
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
                       
<%
  String tPrtNo        = "";
  String tContNo       = "";
  String tMissionID    = "";
  String tSubMissionID = "";
  String tClmNo        = "";
  String tBatNo        = "";
  String tProposalNo   = "";
  
  tPrtNo         = request.getParameter("PrtNo");
  tContNo        = request.getParameter("ContNo");
  tMissionID     = request.getParameter("MissionID");
  tSubMissionID  = request.getParameter("SubMissionID");
  tClmNo         = request.getParameter("ClmNo");
  tProposalNo    = request.getParameter("ProposalNo");
  tBatNo         = request.getParameter("BatNo");
  
%>      

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//初始化页面
function initForm(tContNo,tMissionID,tSubMission,tPrtNo,tClmNo,tProposalNo,tBatNo)
{
  try
  {
	    initHide(tContNo,tMissionID,tSubMission,tPrtNo,tClmNo,tProposalNo,tBatNo);
	    initLLUWSpecGrid();
	    if(tQueryFlag=="1")
	    {
	        fm.button1.style.display="none";
	        fm.button3.style.display="none";
		}
	    QueryPolSpecGrid(tContNo);
	    initLLUWSpecContGrid();
	    getPolGridCho();
  }
  catch(re)
  {
    alert("LLUWSpecInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initLLUWSpecGrid()
  {                               
    var iArray = new Array();
      
      try
      {
	      iArray[0]=new Array();
	      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	      iArray[0][1]="30px";            		//列宽
	      iArray[0][2]=30;            			//列最大值
	      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[1]=new Array();
	      iArray[1][0]="保单号";         		//列名
	      iArray[1][1]="140px";            		//列宽
	      iArray[1][2]=100;            			//列最大值
	      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	
	      iArray[2]=new Array();
	      iArray[2][0]="印刷号";         		//列名
	      iArray[2][1]="0px";            		//列宽
	      iArray[2][2]=100;            			//列最大值
	      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[3]=new Array();
	      iArray[3][0]="主险投保单号";         		//列名
	      iArray[3][1]="0px";            		//列宽
	      iArray[3][2]=100;            			//列最大值
	      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[4]=new Array();
	      iArray[4][0]="险种编码";         		//列名
	      iArray[4][1]="60px";            		//列宽
	      iArray[4][2]=80;            			//列最大值
	      iArray[4][3]=2;              			//是否允许输入,1表示允许，0表示不允许
	      iArray[4][4]="RiskCode";              	        //是否引用代码:null||""为不引用
	      iArray[4][5]="4";              	                //引用代码对应第几列，'|'为分割符
	      iArray[4][9]="险种编码|code:RiskCode&NOTNULL";
	      iArray[4][18]=250;
	      iArray[4][19]= 0 ;
	
	      iArray[5]=new Array();
	      iArray[5][0]="险种版本";         		//列名
	      iArray[5][1]="60px";            		//列宽
	      iArray[5][2]=100;            			//列最大值
	      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[6]=new Array();
	      iArray[6][0]="投保人";         		//列名
	      iArray[6][1]="80px";            		//列宽
	      iArray[6][2]=100;            			//列最大值
	      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[7]=new Array();
	      iArray[7][0]="被保人";         		//列名
	      iArray[7][1]="80px";            		//列宽
	      iArray[7][2]=100;            			//列最大值
	      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      LLUWSpecGrid = new MulLineEnter( "document" , "LLUWSpecGrid" ); 
	      //这些属性必须在loadMulLine前
	      LLUWSpecGrid.mulLineCount      = 5;   
	      LLUWSpecGrid.displayTitle      = 1;
	      LLUWSpecGrid.canSel            = 0;
	      LLUWSpecGrid.hiddenPlus        = 1;
	      LLUWSpecGrid.hiddenSubtraction = 1;
	      LLUWSpecGrid.loadMulLine(iArray);       
	     // LLUWSpecGrid.selBoxEventFuncName = "getPolGridCho";
	     
      }
      catch(ex)
      {
        alert(ex);
      }
}


function initLLUWSpecContGrid(){                               
   
   var iArray = new Array();
   try
   {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="投保单号";         		//列名
      iArray[1][1]="0";            		    //列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="批次号";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="特约内容";         		//列名
      iArray[3][1]="330px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="标识";         		    //列名
      iArray[4][1]="0";            		    //列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许


      LLUWSpecContGrid = new MulLineEnter( "document" , "LLUWSpecContGrid" ); 
      //这些属性必须在loadMulLine前
      LLUWSpecContGrid.displayTitle         = 5;
      LLUWSpecContGrid.canSel               = 1;
      LLUWSpecContGrid.hiddenPlus           = 1;
      LLUWSpecContGrid.hiddenSubtraction    = 1;
      LLUWSpecContGrid.loadMulLine(iArray);       
      LLUWSpecContGrid.selBoxEventFuncName  = "getSpecGridCho";
   }
   catch(ex)
   {
      alert(ex);
   }
}


function initHide(tContNo,tMissionID,tSubMission,tPrtNo,tClmNo,tProposalNo,tBatNo)
{
	document.all('ContNo').value       = tContNo;
	document.all('MissionID').value    = tMissionID;
	document.all('SubMissionID').value = tSubMission;
	document.all('PrtNo').value        = tPrtNo ;
	document.all('ClmNo').value        = tClmNo;
	document.all('ProposalNo').value   = tProposalNo;
	document.all('BatNo').value        = tBatNo;
}


</script>


