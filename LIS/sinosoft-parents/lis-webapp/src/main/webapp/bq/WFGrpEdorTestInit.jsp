<% 
//程序名称：WFEdorNoscanAppInit.jsp
//程序功能：保全工作流-保全无扫描申请
//创建日期：2005-04-27 15:13:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

function initInpBox()
{ 

  try
  {                                   

  }
  catch(ex)
  {
    alert("WFEdorAppInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                            

function initForm()
{
  try
  {    
    initInpBox();
    initSelfGrid(); //初始化我的任务队列
    easyQueryClickSelf();  //查询我的任务队列
  }
  catch(re)
  {
    alert("WFEdorAppInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//
function initSelfGrid()
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
      iArray[1][0]="试算流水号";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户/团单保单号";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="号码类型";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="投保人";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="下次交费对应日";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
              
      iArray[6]=new Array();
      iArray[6][0]="申请方式";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许
              
      iArray[7]=new Array();
      iArray[7][0]="管理机构";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
              
      iArray[8]=new Array();
      iArray[8][0]="录入员";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
              
      iArray[9]=new Array();
      iArray[9][0]="录入日期";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="工作流任务号";         	//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[11]=new Array();
      iArray[11][0]="工作流子任务号";        //列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=200;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[12]=new Array();
      iArray[12][0]="工作流活动Id";         	//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许    

      SelfGrid = new MulLineEnter( "document" , "SelfGrid" ); 
      //这些属性必须在loadMulLine前
      SelfGrid.mulLineCount = 5;   
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 1;
      SelfGrid.canChk = 0;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;        
      SelfGrid.loadMulLine(iArray);  

      //这些操作必须在loadMulLine后面

	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>