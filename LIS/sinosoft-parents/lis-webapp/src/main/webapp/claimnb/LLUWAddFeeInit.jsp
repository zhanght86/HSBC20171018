<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuAddInit.jsp
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
  String tContNo = "";
  String tPolNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tProposalNo = "";
  String tInsuredNo="";
tContNo = request.getParameter("ContNo");
//tPolNo = request.getParameter("PolNo");
tMissionID = request.getParameter("MissionID");
tSubMissionID = request.getParameter("SubMissionID");
tInsuredNo = request.getParameter("InsuredNo");
loggerDebug("LLUWAddFeeInit",tInsuredNo);

%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

var str = "";
var tRow = "";

/**=========================================================================
    修改状态：页面初始化
    修改原因：
    修 改 人：续涛
    修改日期：2005.10.28
   =========================================================================
**/
function initForm(tPolNo,tContNo, tMissionID, tSubMissionID,tInsuredNo)
{
		var str = "";
		try
		{
				initInpBox();
				
				initPolAddGrid();
			    initLCPremGrid();
			    initLLUWPremSubGrid();
				initHide(tPolNo,tContNo, tMissionID, tSubMissionID,tInsuredNo);
				
				QueryPolAddGrid(tContNo,tInsuredNo);
				getPolGridCho();
				LLUWPremMaster("",tContNo);		
				if(tQueryFlag=="1")
				{
				    fm.button1.style.display="none";
				    fm.button3.style.display="none";
				}	
				
			}
			catch(re)
			{
			  alert("在LLUWAddInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
			}
}


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {                                   
	document.all('AddReason').value = '';
   }
  catch(ex)
  {
    alert("在PEdorUWManuAddInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在PEdorUWManuAddInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        



// 责任信息列表的初始化
function initSpecGrid(str){ 
    var iArray = new Array();      
      try
      {
      
	      iArray[0]=new Array();
	      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	      iArray[0][1]="30px";         			//列宽
	      iArray[0][2]=10;          			//列最大值
	      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
	
	      iArray[1]=new Array();
	      iArray[1][0]="加费类型";    	        //列名
	      iArray[1][1]="60px";            		//列宽
	      iArray[1][2]=100;            			//列最大值
	      iArray[1][3]=2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
	      //iArray[1][7]="RadioSelected";   
	      iArray[1][10] = "DutyCode";             			
	      iArray[1][11] = str;
	      iArray[1][12] = "1|3|4";
	      iArray[1][13] = "0|1|2";
	      iArray[1][19] = 1;	
	
	      iArray[2]=new Array();
	      iArray[2][0]="加费原因";    	        //列名
	      iArray[2][1]="60px";            		//列宽
	      iArray[2][2]=100;            			//列最大值
	      iArray[2][3]=2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择   
	      //iArray[2][7]="RadioSelected";   
	      iArray[2][10] = "PlanPay";             			
	      iArray[2][11] = "0|^01|健康加费^02|职业加费";
	      iArray[2][12] = "2";
	      iArray[2][13] = "0";
	      iArray[2][19]= 1 ;
	             
	      
	      iArray[3]=new Array();
	      iArray[3][0]="起始日期";         		//列名
	      iArray[3][1]="60px";            		//列宽
	      iArray[3][2]=100;            			//列最大值
	      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
	
	      iArray[4]=new Array();
	      iArray[4][0]="终止日期";         		//列名
	      iArray[4][1]="60px";            		//列宽
	      iArray[4][2]=60;            			//列最大值
	      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
	
	      iArray[5]=new Array();
	      iArray[5][0]="加费评点";         		//列名
	      iArray[5][1]="60px";            		//列宽
	      iArray[5][2]=100;            			//列最大值
	      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
	      
	      iArray[6]=new Array();
	      iArray[6][0]="第二被保险人加费评点";         		//列名
	      iArray[6][1]="80px";            		//列宽
	      iArray[6][2]=100;            			//列最大值
	      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
	
	          
	      iArray[7]=new Array();
	      iArray[7][0]="加费对象";         		//列名
	      iArray[7][1]="60px";            		//列宽
	      iArray[7][2]=100;            			//列最大值
	      iArray[7][3]=2;
	      //iArray[7][7]="RadioSelected";   
	      iArray[7][10] = "PayObject";             			
	      iArray[7][11] = "0|^01|投保人^02|被保险人^03|多被保险人^04|第二被保险人";
	      iArray[7][12] = "7";
	      iArray[7][13] = "0";
	      iArray[7][19]= 1 ;     
	     
	      iArray[8]=new Array();
	      iArray[8][0]="加费金额";         		//列名
	      iArray[8][1]="80px";            		//列宽
	      iArray[8][2]=100;            			//列最大值
	      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
	      iArray[8][7]="CalHealthAddFee";
	         
	
	      SpecGrid = new MulLineEnter( "document" , "SpecGrid" ); 
	      //这些属性必须在loadMulLine前                            
	      SpecGrid.mulLineCount = 5;
	      SpecGrid.canSel       = 1;
	      SpecGrid.displayTitle = 1;
	      SpecGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保单信息列表的初始化
function initPolAddGrid()
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
	      iArray[1][0]="保单险种号";         		//列名
	      iArray[1][1]="140px";            		//列宽
	      iArray[1][2]=100;            			//列最大值
	      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
	
	
	      iArray[2]=new Array();
	      iArray[2][0]="合同号码";         		//列名
	      iArray[2][1]="140px";            		//列宽
	      iArray[2][2]=100;            			//列最大值
	      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[3]=new Array();
	      iArray[3][0]="印刷号";         		//列名
	      iArray[3][1]="0px";            		//列宽
	      iArray[3][2]=200;            			//列最大值
	      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[4]=new Array();
	      iArray[4][0]="险种编码";         		//列名
	      iArray[4][1]="60px";            		//列宽
	      iArray[4][2]=80;            			//列最大值
	      iArray[4][3]=2;              			//是否允许输入,1表示允许，0表示不允许
	      iArray[4][4]="RiskCode";              	        //是否引用代码:null||""为不引用
	      iArray[4][5]="4";              	                //引用代码对应第几列，'|'为分割符
	      iArray[4][9]="险种编码|code:RiskCode&NOTNULL";
	      iArray[4][18]=250;
	      iArray[4][19]=0 ;
	
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
	      
	      iArray[8]=new Array();
	      iArray[8][0]="保费";         		    //列名
	      iArray[8][1]="0px";            		//列宽
	      iArray[8][2]=100;            			//列最大值
	      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许
	
	      PolAddGrid = new MulLineEnter( "document" , "PolAddGrid" ); 
	      //这些属性必须在loadMulLine前
	      PolAddGrid.mulLineCount      = 5;   
	      PolAddGrid.displayTitle      = 1;
	      PolAddGrid.canSel            = 0;
	      PolAddGrid.hiddenPlus        = 1;
	      PolAddGrid.hiddenSubtraction = 1;
	      PolAddGrid.loadMulLine(iArray);       
	      PolAddGrid.selBoxEventFuncName = "getPolGridCho";
	     
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tPolNo,tContNo,tMissionID, tSubMissionID,tInsuredNo)
{        
  
	document.all('PolNo').value = tPolNo;
	document.all('ContNo').value = tContNo;
	document.all('MissionID').value = tMissionID;
	document.all('SubMissionID').value = tSubMissionID;
	document.all('InsuredNo').value = tInsuredNo;

}

/**=========================================================================
    修改状态：添加“缴费信息”列表
    修改原因：
    修 改 人：万泽辉
    修改日期：2005.11.03
   =========================================================================
**/
// 责任信息列表的初始化
function initLCPremGrid()
  {                            
    var iArray = new Array();      
      try
      {
      
	      iArray[0]=new Array();
	      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	      iArray[0][1]="30px";         			//列宽
	      iArray[0][2]=10;          			//列最大值
	      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
	
	      iArray[1]=new Array();
	      iArray[1][0]="加费类型";    	        //列名
	      iArray[1][1]="60px";            		//列宽
	      iArray[1][2]=100;            			//列最大值
	      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
	      
	      iArray[2]=new Array();
	      iArray[2][0]="加费原因";    	        //列名
	      iArray[2][1]="60px";            		//列宽
	      iArray[2][2]=100;            			//列最大值
	      iArray[2][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择   
	      
	      iArray[3]=new Array();
	      iArray[3][0]="起始日期";         		//列名
	      iArray[3][1]="60px";            		//列宽
	      iArray[3][2]=100;            			//列最大值
	      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
	
	      iArray[4]=new Array();
	      iArray[4][0]="终止日期";         		//列名
	      iArray[4][1]="60px";            		//列宽
	      iArray[4][2]=60;            			//列最大值
	      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
	
	      iArray[5]=new Array();
	      iArray[5][0]="加费评点";         		//列名
	      iArray[5][1]="60px";            		//列宽
	      iArray[5][2]=100;            			//列最大值
	      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
	      
	      iArray[6]=new Array();
	      iArray[6][0]="第二被保险人加费评点";         		//列名
	      iArray[6][1]="80px";            		//列宽
	      iArray[6][2]=100;            			//列最大值
	      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
	
	          
	      iArray[7]=new Array();
	      iArray[7][0]="加费对象";         		//列名
	      iArray[7][1]="60px";            		//列宽
	      iArray[7][2]=100;            			//列最大值
	      iArray[7][3]=0;
	      
	      iArray[8]=new Array();
	      iArray[8][0]="加费金额";         		//列名
	      iArray[8][1]="80px";            		//列宽
	      iArray[8][2]=100;            			//列最大值
	      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
	         
	
	      LCPremGrid = new MulLineEnter( "document" , "LCPremGrid" ); 
	      //这些属性必须在loadMulLine前                            
	      LCPremGrid.mulLineCount      = 5;
	      LCPremGrid.canSel            = 0;
	      LCPremGrid.displayTitle      = 1;
	      LCPremGrid.hiddenPlus        = 1;
	      LCPremGrid.hiddenSubtraction = 1 ;
	      LCPremGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

//加费轨迹信息列表的初始化 2006-02-09 Add by zhaorx
function initLLUWPremSubGrid()
{                            
    var iArray = new Array();      
      try
      {      
	      iArray[0]=new Array();
	      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	      iArray[0][1]="30px";         			//列宽
	      iArray[0][2]=10;          			//列最大值
	      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
	
	      iArray[1]=new Array();
	      iArray[1][0]="批次号";    	        //列名
	      iArray[1][1]="100px";            		//列宽
	      iArray[1][2]=100;            			//列最大值
	      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
	
	      iArray[2]=new Array();
	      iArray[2][0]="加费类型";    	        //列名
	      iArray[2][1]="60px";            		//列宽
	      iArray[2][2]=100;            			//列最大值
	      iArray[2][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
	      
	      iArray[3]=new Array();
	      iArray[3][0]="加费原因";    	        //列名
	      iArray[3][1]="100px";            		//列宽
	      iArray[3][2]=100;            			//列最大值
	      iArray[3][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择   
	      
	      iArray[4]=new Array();
	      iArray[4][0]="起始日期";         		//列名
	      iArray[4][1]="80px";            		//列宽
	      iArray[4][2]=100;            			//列最大值
	      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
	
	      iArray[5]=new Array();
	      iArray[5][0]="终止日期";         		//列名
	      iArray[5][1]="80px";            		//列宽
	      iArray[5][2]=60;            			//列最大值
	      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
	
	      iArray[6]=new Array();
	      iArray[6][0]="加费评点";         		//列名
	      iArray[6][1]="60px";            		//列宽
	      iArray[6][2]=100;            			//列最大值
	      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
	      
	      iArray[7]=new Array();
	      iArray[7][0]="第二被保险人加费评点";         		//列名
	      iArray[7][1]="80px";            		//列宽
	      iArray[7][2]=100;            			//列最大值
	      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
		          
	      iArray[8]=new Array();
	      iArray[8][0]="加费对象";         		//列名
	      iArray[8][1]="80px";            		//列宽
	      iArray[8][2]=100;            			//列最大值
	      iArray[8][3]=0;
	      
	      iArray[9]=new Array();
	      iArray[9][0]="加费金额";         		//列名
	      iArray[9][1]="80px";            		//列宽
	      iArray[9][2]=100;            			//列最大值
	      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择 
	         
	
	      LLUWPremSubGrid = new MulLineEnter( "document" , "LLUWPremSubGrid" ); 
	      //这些属性必须在loadMulLine前
	      LLUWPremSubGrid.mulLineCount      = 5;   
	      LLUWPremSubGrid.displayTitle      = 1;
	      LLUWPremSubGrid.hiddenPlus        = 1;
	      LLUWPremSubGrid.hiddenSubtraction = 1;     
	      
	      LLUWPremSubGrid.loadMulLine(iArray);  
	                	      
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>


