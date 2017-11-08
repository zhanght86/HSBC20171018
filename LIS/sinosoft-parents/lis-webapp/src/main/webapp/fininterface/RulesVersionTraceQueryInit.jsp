<%
//Creator :FanXin
//Date :2008-08-21
%>
<!--用户校验类-->

<script language="JavaScript">

// 输入框的初始化
function initInpBox()
{


  try
  {
  	fm.reset();
    document.all('Maintenanceno').value = '';
    document.all('VersionNo').value = VersionNo;
    document.all('MaintenanceState').value = '';
    document.all('MaintenanceReMark').value = '';
  }
  catch(ex)
  {
    alert("在RulesVersionTraceQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在RulesVersionTraceQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}


function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initRulesVersionTraceGrid();
  }
  catch(re)
  {
    alert("RulesVersionTraceQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


function initRulesVersionTraceGrid()
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
      iArray[1][0]="维护编号";    	//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[2]=new Array();
      iArray[2][0]="版本编号";         			//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="维护状态";         			//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[4]=new Array();
      iArray[4][0]="维护描述";         			//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="操作员";         		//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      
      iArray[6]=new Array();
      iArray[6][0]="入机日期";         			//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

			iArray[7]=new Array();
      iArray[7][0]="入机时间";         			//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      

      RulesVersionTraceGrid = new MulLineEnter( "document" , "RulesVersionTraceGrid" ); 
      RulesVersionTraceGrid.mulLineCount = 5;   
      RulesVersionTraceGrid.displayTitle = 1;
      RulesVersionTraceGrid.canSel=1;
      RulesVersionTraceGrid.locked = 1;	
			RulesVersionTraceGrid.hiddenPlus = 1;
			RulesVersionTraceGrid.hiddenSubtraction = 1;
      RulesVersionTraceGrid.loadMulLine(iArray);  
      RulesVersionTraceGrid.detailInfo="单击显示详细信息";
     
      }
      
      catch(ex)
      {
        alert("初始化RulesVersionTraceGrid时出错："+ ex);
      }

}

</script>
