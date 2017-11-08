<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWMissionAssignInit.jsp
//程序功能：核保任务调配
//创建日期：2005-5-14 16:25
//创建人  ：HWM
//更新记录：  更新人    更新日期     更新原因/内容
//            刘晓松    2006-10-20    加入核保任务分配类型
%> 
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

function initForm()
{
  try
  {
   	initAllPolGrid();    
   	initBqPolGrid();
   	initLpPolGrid();
  }
  catch(re)
  {
    alert("在UWMissionAssignInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initAllPolGrid()
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
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保单号";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保人";         		//列名
      iArray[3][1]="200px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="核保状态";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许            

      iArray[5]=new Array();                                                       
      iArray[5][0]="保单类型";         		//列名                                     
      iArray[5][1]="80px";            		//列宽                                   
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
      iArray[9][0]="核保师编号";         		//列名                                     
      iArray[9][1]="80px";            		//列宽                                   
      iArray[9][2]=100;            			//列最大值                                 
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[10]=new Array();                                                       
      iArray[10][0]="核保级别";         		//列名                                     
      iArray[10][1]="80px";            		//列宽                                   
      iArray[10][2]=100;            			//列最大值                                 
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
	    iArray[11]=new Array();                                                       
      iArray[11][0]="管理机构";         		//列名                                     
      iArray[11][1]="80px";            		//列宽                                   
      iArray[11][2]=100;            			//列最大值                                 

      
      
	  AllPolGrid = new MulLineEnter( "document" , "AllPolGrid" ); 
      //这些属性必须在loadMulLine前
      AllPolGrid.mulLineCount = 5;   
      AllPolGrid.displayTitle = 1;
      AllPolGrid.locked = 1;
      AllPolGrid.canSel = 0;
      AllPolGrid.canChk = 1;
      AllPolGrid.hiddenPlus = 1;
      AllPolGrid.hiddenSubtraction = 1;
  //    AllPolGrid.selBoxEventFuncName = "setManageCom";
      AllPolGrid.loadMulLine(iArray);     

      }
      catch(ex)
      {
        alert(ex);
      }
}
function initBqPolGrid()
  {                               
    var iArray = new Array();
    try 
     {
      iArray[0] = new Array();
      iArray[0][0] = "序号";                          //列名(顺序号, 无意义)
      iArray[0][1] = "30px";                          //列宽
      iArray[0][2] = 30;                              //列最大值
      iArray[0][3] = 0;                               //是否允许输入: 0表示不允许; 1表示允许。

      iArray[1] = new Array();
      iArray[1][0] = "工作流任务号";
      iArray[1][1] = "0px";
      iArray[1][2] = 0;
      iArray[1][3] = 3;

      iArray[2] = new Array();
      iArray[2][0] = "工作流子任务号";
      iArray[2][1] = "0px";
      iArray[2][2] = 0;
      iArray[2][3] = 3;

      iArray[3] = new Array();
      iArray[3][0] = "保全受理号";
      iArray[3][1] = "110px";
      iArray[3][2] = 150;
      iArray[3][3] = 0;

      iArray[4] = new Array();
      iArray[4][0] = "客户/保单号";
      iArray[4][1] = "110px";
      iArray[4][2] = 150;
      iArray[4][3] = 0;

      iArray[5] = new Array();
      iArray[5][0] = "号码类型";
      iArray[5][1] = "70px";
      iArray[5][2] = 100;
      iArray[5][3] = 0;

      iArray[6] = new Array();
      iArray[6][0] = "申请人";
      iArray[6][1] = "70px";
      iArray[6][2] = 100;
      iArray[6][3] = 0;
      
      iArray[7] = new Array();
      iArray[7][0] = "核保状态";
      iArray[7][1] = "60px";
      iArray[7][2] = 100;
      iArray[7][3] = 0;
                
      iArray[8] = new Array();
      iArray[8][0] = "保单类型";
      iArray[8][1] = "60px";
      iArray[8][2] = 100;
      iArray[8][3] = 0;
                
      iArray[9] = new Array();
      iArray[9][0] = "核保师编码";
      iArray[9][1] = "60px";
      iArray[9][2] = 100;
      iArray[9][3] = 0;
                   
      iArray[10] = new Array();
      iArray[10][0] = "管理机构";
      iArray[10][1] = "85px";
      iArray[10][2] = 100;
      iArray[10][3] = 0;
      
      iArray[11] = new Array();
      iArray[11][0] = "Activityid";
      iArray[11][1] = "0px";
      iArray[11][2] = 100;
      iArray[11][3] = 0;
      
     }
     catch (ex)
      {
        alert("在 UWMissionAssignInit.jsp --> initSelfGrid 函数中发生异常: 初始化数组错误！");
      }
      try
      {
        BqPolGrid = new MulLineEnter("document", "BqPolGrid");
        BqPolGrid.mulLineCount = 5;
        BqPolGrid.displayTitle = 1;
        BqPolGrid.locked = 1;
        BqPolGrid.hiddenPlus = 1;
        BqPolGrid.hiddenSubtraction = 1;
        BqPolGrid.canSel = 0;
        BqPolGrid.canChk = 1;
        BqPolGrid.chkBoxEventFuncName = "";
        BqPolGrid.selBoxEventFuncName = "";
         //上面属性必须在 MulLineEnter loadMulLine 之前
        BqPolGrid.loadMulLine(iArray);
       }
        catch (ex)
        {
                alert("在 WFGrpEdorManuUWApplyInit.jsp --> initSelfGrid 函数中发生异常: 初始化界面错误！");
        }
 }
 
function initLpPolGrid()
{                               
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			//列名 （此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=30;            			//列最大值
		iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许       			 
		
		iArray[1]=new Array();
		iArray[1][0]="赔案号";         		 
		iArray[1][1]="100px";            		 
		iArray[1][2]=100;            			 
		iArray[1][3]=0;              			 
	
		iArray[2]=new Array();
		iArray[2][0]="批次号";         		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=100;            			 
		iArray[2][3]=0;  
		            			 
		iArray[3]=new Array();
		iArray[3][0]="核保任务状态";         		 
		iArray[3][1]="100px";            		 
		iArray[3][2]=100;            			 
		iArray[3][3]=0;              			 
		
		iArray[4]=new Array();
		iArray[4][0]="被保险人名称";         		 
		iArray[4][1]="100px";            		 
		iArray[4][2]=100;            			 
		iArray[4][3]=0;              			 
	
		iArray[5]=new Array();
		iArray[5][0]="赔案相关标志";         		 
		iArray[5][1]="80px";            		 
		iArray[5][2]=100;            			 
		iArray[5][3]=0;              			 
		
		iArray[6]=new Array();
		iArray[6][0]="核保师编码";         		 
		iArray[6][1]="100px";            		 
		iArray[6][2]=100;            			 
		iArray[6][3]=0;              			    
	
		iArray[7]=new Array();                                                       
		iArray[7][0]="管理机构";         		                                      
		iArray[7][1]="120px";            		                                    
		iArray[7][2]=100;            			                                  
		iArray[7][3]=0;              			    
	
	  iArray[8]=new Array();
	  iArray[8][0]="Missionid";              
    iArray[8][1]="0px";                 
    iArray[8][2]=200;                   
	  iArray[8][3]=3;                           
	
	  iArray[9]=new Array();
	  iArray[9][0]="Submissionid";              
	  iArray[9][1]="0px";                 
	  iArray[9][2]=200;                   
	  iArray[9][3]=3;                           
	    
    iArray[10]=new Array();
	  iArray[10][0]="Activityid";              
	  iArray[10][1]="0px";                 
	  iArray[10][2]=200;                   
	  iArray[10][3]=3;                      
	   
	  iArray[11]=new Array();
	  iArray[11][0]="IsReFalg";              // 隐藏 “赔案相关标志”
	  iArray[11][1]="0px";                
	  iArray[11][2]=200;                  
	  iArray[11][3]=3;                       
	
		LpPolGrid = new MulLineEnter( "document" , "LpPolGrid" ); 
		//这些属性必须在loadMulLine前
		LpPolGrid.mulLineCount =5;   
		LpPolGrid.displayTitle = 1;
		LpPolGrid.locked = 1;
		LpPolGrid.canSel = 0;
    LpPolGrid.canChk = 1;
		LpPolGrid.hiddenPlus = 1;
		LpPolGrid.hiddenSubtraction = 1;
		LpPolGrid.loadMulLine(iArray);     
	//	LpPolGrid.selBoxEventFuncName = "SelfLLCUWBatchGridClick";
		//这些操作必须在loadMulLine后面
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
