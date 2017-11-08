<%
//程序名称：TaskServiceInput.jsp
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
  	//alert('1');
	queryLDUserGridInfo();
	//alert('2');
	initUserTabDetailGrid();
  }
  catch(re)
  {
    alert(initForm.re);
    alert("TaskServiceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initLDUserGrid()
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
      iArray[1][0]="用户编码";             	//列名
      iArray[1][1]="80px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="用户姓名";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
      LDUserGrid = new MulLineEnter( "fm" , "LDUserGrid" ); 
      //这些属性必须在loadMulLine前
      LDUserGrid.mulLineCount = 1;   
      LDUserGrid.displayTitle = 1;
      LDUserGrid.canSel =1;
      LDUserGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LDUserGrid.hiddenSubtraction=1;
	 	  LDUserGrid.selBoxEventFuncName = "onLDUserSelected";

	  LDUserGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}



function initUserTabDetailGrid()
{                               
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

			//var tSQL = "select tasktabid,tasktabname from LDTaskTabConfig order by showorder";
			var mySql = new SqlClass();
			mySql = new SqlClass();
			mySql.setResourceName("taskservice.TaskTabConfigSql");
			mySql.setSqlId("TaskTabConfigSql1");
			mySql.addSubPara('1');  
			//turnPagePlan.queryModal(mySql.getString(),TaskPlanGrid);
			
			var ttemp = (easyQueryVer3(mySql.getString(), 1, 0, 1));
			
      iArray[1]=new Array();
      iArray[1][0]="标签编码";          	//列名
      iArray[1][1]="80px";      	      	//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
  		iArray[1][10]="TaskTabID";  //引用代码:
			iArray[1][11]=ttemp;
		  iArray[1][12]="1|2";
		  iArray[1][13]="0|1";
    
      iArray[2]=new Array();
      iArray[2][0]="标签名称";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      UserTabDetailGrid = new MulLineEnter( "fm" , "UserTabDetailGrid" ); 
      //这些属性必须在loadMulLine前
      UserTabDetailGrid.mulLineCount = 1;   
      UserTabDetailGrid.displayTitle = 1;
      //TaskGroupDetailGrid.canSel =1;
      //TaskGroupDetailGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      //TaskGroupDetailGrid.hiddenSubtraction=1;
	  	//TaskGroupDetailGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  	UserTabDetailGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
