<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QuestInputInit.jsp
//程序功能：核保等待初始化
//创建日期：2008-09-27 11:10:36
//创建人  ：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {                                   
    //document.all('ContNo').value = tContNo;
    document.all('WaitReason').value = '';
    document.all('Reason').value = '';
    document.all('Content').value = '';  
    document.all('UniteNo').value = '';  
 
  }
  catch(ex)
  {
    alert("在WaitReasonInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
} 

// 隐藏输入框的初始化
function initHide()
{ 
try
  {                                   
    document.all('ContNoH').value = tContNo;
    document.all('MissionIDH').value = '';
    document.all('SubMissionIDH').value = '';
    document.all('InsuredNoH').value = '';    
  }
  catch(ex)
  {
    alert("在WaitReasonInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}                                     

function initForm()
{
  try
  {
    
	initInpBox();
	initHide();	
	initPolAddGrid();
	//alert(2);
	initWaitContGrid();	
	iniReason();
	//alert(1);
	queryInsuredNo();
	if(tType == '1')
	{
		//document.all('UniteNo').value = tContNo;
		//initUniteNo();
	}
	if(tType == '2')
	{	    
	    showReasonInfo();
	}
		
  }
  catch(re)
  {
    alert("WaitReasonInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//查询同一被保险人未承保投保单
function initWaitContGrid()
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
      iArray[1][0]="合同号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="0px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="印刷号";    	//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=130;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保人客户号";         			//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="投保人姓名";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="投保日期";         			//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=8;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="核保任务状态";         			//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=50;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="核保任务状态名称";         			//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="最近核保人工号";         			//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="目前位置";         			//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许     
      
      iArray[10]=new Array();
      iArray[10][0]="任务号";         			//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=1;              			//是否允许输入,1表示允许，0表示不允许  
      
      iArray[11]=new Array();
      iArray[11][0]="子任务号";         			//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=1;              			//是否允许输入,1表示允许，0表示不允许    
          
      
      WaitContGrid = new MulLineEnter( "fm" , "WaitContGrid" ); 
      //这些属性必须在loadMulLine前                            
      WaitContGrid.mulLineCount = 0;   
      WaitContGrid.displayTitle = 1;
      WaitContGrid.locked = 1;
      WaitContGrid.canSel = 1;
      WaitContGrid.hiddenPlus = 1;
      WaitContGrid.hiddenSubtraction = 1;
      WaitContGrid.loadMulLine(iArray); 
      WaitContGrid.selBoxEventFuncName = "initClick"; 

      }
      catch(ex)
      {
        alert(ex);
      }
}

//被保人列表
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
      iArray[1][0]="客户号码";         		//列名
      iArray[1][1]="140px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[2]=new Array();
      iArray[2][0]="姓名";         		//列名
      iArray[2][1]="140px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="证件类型";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="证件号码";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      PolAddGrid = new MulLineEnter( "fm" , "PolAddGrid" ); 
      //这些属性必须在loadMulLine前
      PolAddGrid.mulLineCount = 0;   
      PolAddGrid.displayTitle = 1;
      PolAddGrid.locked = 1;
      PolAddGrid.canSel = 1;
      PolAddGrid.hiddenPlus = 1;
      PolAddGrid.hiddenSubtraction = 1;
      PolAddGrid.loadMulLine(iArray);       
      PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
      //这些操作必须在loadMulLine后面
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>


