<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {            
  	if(operFlag == "1")
  	{
  		//fm.ApplyNo.value = "5";    
  		fm.ApplyType.value = "1";  				//个单申请
  		fm.ActivityID.value = "0000001100"; 
  	}
  	if(operFlag == "2")
  	{
  		//fm.ApplyNo.value = "2";  
  		fm.ApplyType.value = "2";    		  //团单申请
  		fm.ActivityID.value = "0000002004"; 
  	}  	      
  	if(operFlag == "3")
  	{
 
  		//fm.ApplyNo.value = "2";  
  		fm.ApplyType.value = "3";    			//询价申请
  		fm.ActivityID.value = "0000006004"; 
  	}            
          
  }
  catch(ex)
  {
    alert("在UWInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
   // alert('operFlag:'+operFlag);
    if(operFlag == "1")
    {
    	initSelfGrpGrid();  
    	initGrpGrid();    
    }
    if(operFlag == "2" || operFlag == "3")
    {
    	initGrpSelfGrpGrid();
    	initGrpGrpGrid(); 
    }  
            
    easyQueryClick();
  }
  catch(re)
  {
    alert("在UWInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initSelfGrpGrid()
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
      iArray[1][0]="印刷号";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保单号";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保人";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="核保任务状态";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许            

      iArray[5]=new Array();                                                       
      iArray[5][0]="核保类型";         		//列名                                     
      iArray[5][1]="0px";            		//列宽                                   
      iArray[5][2]=100;            			//列最大值                                 
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许   
            
      iArray[6]=new Array();
      iArray[6][0]="工作流任务号";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流子任务号";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="工作流活动Id";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      
      iArray[9]=new Array();
      iArray[9][0]="上报类型";              //列名
      iArray[9][1]="80px";                   //列宽
      iArray[9][2]=60;                      //列最大值
      iArray[9][3]=0;       
      
      iArray[10]=new Array();
      iArray[10][0]="核保级别";               //列名
      iArray[10][1]="60px";                 //列宽
      iArray[10][2]=60;                     //列最大值
      iArray[10][3]=0;                      

      iArray[11]=new Array();
      iArray[11][0]="业务员";         		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[12]=new Array();
      iArray[12][0]="投保日期";         		//列名
      iArray[12][1]="80px";            		//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[13]=new Array();
      iArray[13][0]="复核日期";         		//列名
      iArray[13][1]="80px";            		//列宽
      iArray[13][2]=60;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[14]=new Array();
      iArray[14][0]="星级业务员";         		//列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[15]=new Array();
      iArray[15][0]="VIP客户";         		//列名
      iArray[15][1]="0px";            		//列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[16]=new Array();
      iArray[16][0]="上报标志";         		//列名
      iArray[16][1]="0px";            		//列宽
      iArray[16][2]=60;            			//列最大值
      iArray[16][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
 
      SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
      //这些属性必须在loadMulLine前
      SelfGrpGrid.mulLineCount = 5;   
      SelfGrpGrid.displayTitle = 1;
      SelfGrpGrid.locked = 1;
      SelfGrpGrid.canSel = 1;
      SelfGrpGrid.hiddenPlus = 1;
      SelfGrpGrid.hiddenSubtraction = 1;
      SelfGrpGrid.loadMulLine(iArray);
      
      SelfGrpGrid.selBoxEventFuncName = "IniteasyQueryAddClick";
      
      //这些操作必须在loadMulLine后面
      //SelfGrpGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保单信息列表的初始化
function initGrpSelfGrpGrid()
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
      iArray[1][0]="状态描述";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=30;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="机构";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=30;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="印刷号";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="集体投保单号";         		//列名
      iArray[4][1]="160px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="投保单位";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="提交核保日期";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=30;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="核保任务状态";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许            

      iArray[8]=new Array();                                                       
      iArray[8][0]="核保类型";         		//列名                                     
      iArray[8][1]="80px";            		//列宽                                   
      iArray[8][2]=100;            			//列最大值                                 
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许   
            
      iArray[9]=new Array();
      iArray[9][0]="工作流任务号";         		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="工作流子任务号";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[11]=new Array();
      iArray[11][0]="工作流活动Id";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[12]=new Array();
      iArray[12][0]="上报类型";              //列名
      iArray[12][1]="80px";                   //列宽
      iArray[12][2]=60;                      //列最大值
      iArray[12][3]=3;       
      
      iArray[13]=new Array();
      iArray[13][0]="核保级别";               //列名
      iArray[13][1]="60px";                 //列宽
      iArray[13][2]=60;                     //列最大值
      iArray[13][3]=3;                      

      iArray[14]=new Array();
      iArray[14][0]="业务员";         		//列名
      iArray[14][1]="80px";            		//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[15]=new Array();
      iArray[15][0]="投保日期";         		//列名
      iArray[15][1]="80px";            		//列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[16]=new Array();
      iArray[16][0]="复核日期";         		//列名
      iArray[16][1]="80px";            		//列宽
      iArray[16][2]=60;            			//列最大值
      iArray[16][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[17]=new Array();
      iArray[17][0]="星级业务员";         		//列名
      iArray[17][1]="0px";            		//列宽
      iArray[17][2]=60;            			//列最大值
      iArray[17][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[18]=new Array();
      iArray[18][0]="VIP客户";         		//列名
      iArray[18][1]="0px";            		//列宽
      iArray[18][2]=60;            			//列最大值
      iArray[18][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[19]=new Array();
      iArray[19][0]="上报标志";         		//列名
      iArray[19][1]="0px";            		//列宽
      iArray[19][2]=60;            			//列最大值
      iArray[19][3]=3;              			//是否允许输入,1表示允许，0表示不允许    
    
      SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
      //这些属性必须在loadMulLine前
      SelfGrpGrid.mulLineCount = 5;   
      SelfGrpGrid.displayTitle = 1;
      SelfGrpGrid.locked = 1;
      SelfGrpGrid.canSel = 1;
      SelfGrpGrid.hiddenPlus = 1;
      SelfGrpGrid.hiddenSubtraction = 1;     
      SelfGrpGrid.selBoxEventFuncName = "GrpIniteasyQueryAddClick";
      SelfGrpGrid.loadMulLine(iArray); 
     
      
      //这些操作必须在loadMulLine后面
      //SelfGrpGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保单信息列表的初始化
function initGrpGrid()
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
      iArray[1][0]="印刷号";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保单号";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保人";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="核保任务状态";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许            

      iArray[5]=new Array();                                                       
      iArray[5][0]="核保类型";         		//列名                                     
      iArray[5][1]="0px";            		//列宽                                   
      iArray[5][2]=100;            			//列最大值                                 
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
            
      iArray[6]=new Array();
      iArray[6][0]="工作流任务号";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流子任务号";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="工作流活动Id";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[9]=new Array();
      iArray[9][0]="上报类型";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[10]=new Array();
      iArray[10][0]="业务员";         		//列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[11]=new Array();
      iArray[11][0]="投保日期";         		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[12]=new Array();
      iArray[12][0]="复核日期";         		//列名
      iArray[12][1]="80px";            		//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[13]=new Array();
      iArray[13][0]="星级业务员";         		//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=60;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[14]=new Array();
      iArray[14][0]="VIP客户";         		//列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[15]=new Array();
      iArray[15][0]="上报标志";         		//列名
      iArray[15][1]="0px";            		//列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
 
      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //这些属性必须在loadMulLine前
      GrpGrid.mulLineCount = 5;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;
      GrpGrid.loadMulLine(iArray);
     // GrpGrid.selBoxEventFuncName = "GrpIniteasyQueryAddClick";   

      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保单信息列表的初始化
function initGrpGrpGrid()
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
      iArray[1][0]="印刷号";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="集体投保单号";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保单位";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="险种标识";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[5]=new Array();
      iArray[5][0]="核保任务状态";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许            

      iArray[6]=new Array();                                                       
      iArray[6][0]="核保类型";         		//列名                                     
      iArray[6][1]="80px";            		//列宽                                   
      iArray[6][2]=100;            			//列最大值                                 
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
            
      iArray[7]=new Array();
      iArray[7][0]="工作流任务号";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="工作流子任务号";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[9]=new Array();
      iArray[9][0]="工作流活动Id";         		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许
  
      iArray[10]=new Array();
      iArray[10][0]="上报类型";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[11]=new Array();
      iArray[11][0]="业务员";         		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[12]=new Array();
      iArray[12][0]="投保日期";         		//列名
      iArray[12][1]="80px";            		//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[13]=new Array();
      iArray[13][0]="复核日期";         		//列名
      iArray[13][1]="80px";            		//列宽
      iArray[13][2]=60;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[14]=new Array();
      iArray[14][0]="星级业务员";         		//列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[15]=new Array();
      iArray[15][0]="VIP客户";         		//列名
      iArray[15][1]="0px";            		//列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[16]=new Array();
      iArray[16][0]="上报标志";         		//列名
      iArray[16][1]="0px";            		//列宽
      iArray[16][2]=60;            			//列最大值
      iArray[16][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
     
      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //这些属性必须在loadMulLine前
      GrpGrid.mulLineCount = 5;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;
      GrpGrid.loadMulLine(iArray); 

      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
