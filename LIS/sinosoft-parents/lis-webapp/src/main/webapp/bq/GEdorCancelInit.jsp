<%
//程序名称：EdorCancelInit.jsp
//程序功能：保全撤销
//创建日期：2005-05-08 09:20:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
%>

<script language="JavaScript">

    var AppItemGrid;     //全局变量, 批单项目队列

//页面初始化
function initForm()
{
  try
  {
    initParam();
    initInputBox();
    initQuery();
    initEdorItemGrid();
    queryAppItemGrid();
  }
  catch(re)
  {
    alert("PEdorInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//把null的字符串转为空
function nullToEmpty(string)
{
    if ((string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

//接收从工作流保全撤销页面传递过来的参数
function initParam()
{
   document.all('EdorAcceptNo').value     = nullToEmpty("<%= tEdorAcceptNo %>");
   document.all('MissionID').value        = nullToEmpty("<%= tMissionID %>");
   document.all('SubMissionID').value     = nullToEmpty("<%= tSubMissionID %>");
}

//初始化页面元素
function initInputBox()
{
    try
    {
        document.getElementsByName("DelFlag")[0].value = "GEdorApp";    //XinYQ added on 2006-02-09 : 默认撤销全部保全申请
    }
    catch(ex)
    {
        alert("在 PEdorInputInit.jsp --> initInputBox 函数中发生异常:初始化界面错误！ ");
    }
}

function initEdorItemGrid()
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
	  	  iArray[1][0]="申请批单号";         		//列名
	  	  iArray[1][1]="100px";            		//列宽
	  	  iArray[1][2]=100;            			//列最大值
	  	  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
	  	  iArray[2]=new Array();
	  	  iArray[2][0]="批改类型";         		//列名
	  	  iArray[2][1]="120px";            		//列宽
	  	  iArray[2][2]=100;            			//列最大值
	  	  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  	  
	  	  iArray[3]=new Array();
	  	  iArray[3][0]="团体保单号";         		//列名
	  	  iArray[3][1]="110px";            		//列宽
	  	  iArray[3][2]=100;            			//列最大值
	  	  iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
	  	  iArray[4]=new Array();
	  	  iArray[4][0]="生效日期";         		//列名
	  	  iArray[4][1]="70px";            		//列宽
	  	  iArray[4][2]=100;            			//列最大值
	  	  iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
	  	  iArray[5]=new Array();
	  	  iArray[5][0]="补退费金额合计";         	//列名
	  	  iArray[5][1]="70px";            		//列宽
	  	  iArray[5][2]=100;            			//列最大值
	  	  iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
	  	  iArray[6]=new Array();
	  	  iArray[6][0]="补退费利息";         	//列名
	  	  iArray[6][1]="70px";            		//列宽
	  	  iArray[6][2]=100;            			//列最大值
	  	  iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  	  		  
	  	  iArray[7]=new Array();
	  	  iArray[7][0]="批改状态";
	  	  iArray[7][1]="70px";
	  	  iArray[7][2]=100;
	  	  iArray[7][3]=0;
                  
	  	  iArray[8]=new Array();
	  	  iArray[8][0]="核保状态";
	  	  iArray[8][1]="70px";
	  	  iArray[8][2]=100;
	  	  iArray[8][3]=0; 
	  	  
	  	  iArray[9]=new Array();
		  iArray[9][0]="币种";          
		  iArray[9][1]="50px";              
	      iArray[9][2]=60;             
		  iArray[9][3]=2;               
    	  iArray[9][4]="Currency";                 
          iArray[9][9]="币种|code:Currency";
	  	  
	  	  
	  	  EdorItemGrid = new MulLineEnter( "document" , "EdorItemGrid" ); 
	  	  //这些属性必须在loadMulLine前
	  	  EdorItemGrid.mulLineCount = 3;   
	  	  EdorItemGrid.displayTitle = 1;
	  	  EdorItemGrid.locked = 1;
	  	  //EdorItemGrid.canSel = 1;
	  	  EdorItemGrid.hiddenPlus = 1;
	  	  EdorItemGrid.hiddenSubtraction=1;
	  	  EdorItemGrid.loadMulLine(iArray);       
	  }
	  catch(ex)
	  {
	  	alert(ex);
	  }
}

</script>
