<%
//程序名称：SelAssignDuty.jsp
//程序功能：
//创建日期：2008-09-26
//创建人  ：liuqh
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
	initAssignGrid();
	InitAssign();

  }
  catch(re)
  {
    alert(initForm.re);
    alert("SelUWAssignDutyInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initAssignGrid()
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
      iArray[1][0]="核保师代码";          	//列名
      iArray[1][1]="60px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=2;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[1][4]="UWCode";              	        //是否引用代码:null||""为不引用
      iArray[1][5]="1|2|3|4";              	                //引用代码对应第几列，'|'为分割符
      iArray[1][6]="0|1|3|4";              	        //上面的列中放置引用代码中第几位值

      iArray[2]=new Array();
      iArray[2][0]="核保师姓名";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="核保级别";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="核保状态";      	   		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0; 

      iArray[5]=new Array();
      iArray[5][0]="分配单量总数";      	   		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="已经分出单量";      	   		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="是否启动";      	   		//列名
      iArray[7][1]="40px";            		//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=2; 
      iArray[7][10]="Planstate"; 
      iArray[7][11]="0|^0|启动^1|终止"; 
      
      

 
		
      AssignGrid = new MulLineEnter( "document" , "AssignGrid" ); 
      //这些属性必须在loadMulLine前
      AssignGrid.mulLineCount = 5;   
      AssignGrid.displayTitle = 1;
      AssignGrid.canSel =0;
      AssignGrid.canChk =1;
      AssignGrid.hiddenPlus=0;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      AssignGrid.hiddenSubtraction=0;
	  //AssignGrid.selBoxEventFuncName = "onTaskPlanSelected";

	  AssignGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
}






</script>
