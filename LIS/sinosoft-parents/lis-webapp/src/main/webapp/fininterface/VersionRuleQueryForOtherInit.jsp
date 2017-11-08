<%
//Creator :ZhongYan
//Date :2008-08-12
%>
<!--用户校验类-->

<script language="JavaScript">


// 输入框的初始化
function initInpBox()
{
  try
  {
    
  }
  catch(ex)
  {
    alert("在VersionRuleQueryForOtherInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在VersionRuleQueryForOtherInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}


function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initRulesVersionGrid();
  }
  catch(re)
  {
    alert("VersionRuleQueryForOtherInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


function initRulesVersionGrid()
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
      iArray[1][0]="版本编号";    	//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[2]=new Array();
      iArray[2][0]="申请日期";         			//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="生效日期";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[4]=new Array();
      iArray[4][0]="失效日期";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="版本状态";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      
      iArray[6]=new Array();
      iArray[6][0]="版本状态";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      
      iArray[7]=new Array();
      iArray[7][0]="版本描述";         			//列名
      iArray[7][1]="200px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

      

      RulesVersionGrid = new MulLineEnter( "document" , "RulesVersionGrid" ); 
      RulesVersionGrid.mulLineCount = 5;   
      RulesVersionGrid.displayTitle = 1;
      RulesVersionGrid.canSel=1;
      RulesVersionGrid.locked = 1;	
			RulesVersionGrid.hiddenPlus = 1;
			RulesVersionGrid.hiddenSubtraction = 1;
      RulesVersionGrid.loadMulLine(iArray);  
      RulesVersionGrid.detailInfo="单击显示详细信息";
      //RulesVersionGrid.detailClick=reportDetailClick;
     
      }
      
      catch(ex)
      {
        alert("初始化RulesVersionGrid时出错："+ ex);
      }

}

</script>
