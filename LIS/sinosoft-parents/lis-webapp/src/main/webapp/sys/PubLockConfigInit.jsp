<%
//程序名称：
//程序功能：
//创建日期：2004-12-15 
//创建人  ：ZhangRong
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
}

function initSelBox()
{  
}                                        

function initForm()
{
  try
  {
	divAllLockInfo.style.display = "";
	divAllLockInfoButton.style.display = "";
	divLockBase.style.display = "none";
	divLockBaseButton.style.display = "none";
		divLockGroup.style.display = "none";
	divLockGroupButton.style.display = "none";
	divLockConfig.style.display = "none";
    	initInpBox();
    	initSelBox();    
	initAllLockInfoGrid();
	initLockBaseGrid();
	initLockGroupGrid();
	initLockGroupConfigGrid();
	 queryLockGroup();
	 queryAllLockModule();
	 queryAllLockGroup();
//	initParamGrid();
//	initMoreParamGrid();
	//queryTaskInfo();
	//queryTaskPlanInfo();
  }
  catch(re)
  {
    alert(initForm.re);
    alert("TaskServiceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initAllLockInfoGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="业务号";          	//列名
      iArray[1][1]="80px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="并发控制组";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="锁定日期";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="锁定时间";      	   		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0; 

      iArray[5]=new Array();
      iArray[5][0]="已锁定时间(天)";      	   		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="已锁定时间(分)";      	   		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		
      AllLockInfoGrid = new MulLineEnter( "document" , "AllLockInfoGrid" ); 
      //这些属性必须在loadMulLine前
      AllLockInfoGrid.mulLineCount = 5;   
      AllLockInfoGrid.displayTitle = 1;
      AllLockInfoGrid.canSel =1;
      AllLockInfoGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      AllLockInfoGrid.hiddenSubtraction=1;
	  AllLockInfoGrid.selBoxEventFuncName = "onAllLockInfoSelected";

	  AllLockInfoGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initLockBaseGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="基本模块编码";             	//列名
      iArray[1][1]="80px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="基本模块名称";         		//列名
      iArray[2][1]="180px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="基本模块描述";         		//列名
      iArray[3][1]="180px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许


		
      LockBaseGrid = new MulLineEnter( "document" , "LockBaseGrid" ); 
      //这些属性必须在loadMulLine前
      LockBaseGrid.mulLineCount = 5;   
      LockBaseGrid.displayTitle = 1;
      LockBaseGrid.canSel =0;
      LockBaseGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LockBaseGrid.hiddenSubtraction=1;
	  	LockBaseGrid.selBoxEventFuncName = "onTaskSelected";

	  	LockBaseGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initLockGroupGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="并发控制组编码";             	//列名
      iArray[1][1]="80px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="并发控制组名称";         		//列名
      iArray[2][1]="180px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="并发控制组描述";         		//列名
      iArray[3][1]="180px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许


		
      LockGroupGrid = new MulLineEnter( "document" , "LockGroupGrid" ); 
      //这些属性必须在loadMulLine前
      LockGroupGrid.mulLineCount = 5;   
      LockGroupGrid.displayTitle = 1;
      LockGroupGrid.canSel =1;
      LockGroupGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LockGroupGrid.hiddenSubtraction=1;
	  LockGroupGrid.selBoxEventFuncName = "onTaskSelected";

	  LockGroupGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initLockGroupConfigGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="并发控制组编码";             	//列名
      iArray[1][1]="0px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=1;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的


      iArray[2]=new Array();
      iArray[2][0]="控制模块编码";         		//列名
      iArray[2][1]="180px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=2;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[2][4]="lockmodule";  //引用代码:
			iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      
      iArray[3]=new Array();
      iArray[3][0]="控制模块名称";         		//列名
      iArray[3][1]="180px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许


		
      LockGroupConfigGrid = new MulLineEnter( "document" , "LockGroupConfigGrid" ); 
      //这些属性必须在loadMulLine前
      LockGroupConfigGrid.mulLineCount = 5;   
      LockGroupConfigGrid.displayTitle = 1;
      LockGroupConfigGrid.canSel =0;
      LockGroupConfigGrid.hiddenPlus=0;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LockGroupConfigGrid.hiddenSubtraction=0;
			LockGroupConfigGrid.addEventFuncName="addNewGroupConfig";
	  	LockGroupConfigGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}
</script>
