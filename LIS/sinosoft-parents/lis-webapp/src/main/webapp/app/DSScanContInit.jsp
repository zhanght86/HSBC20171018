<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
    document.all('PrtNo').value = '';
    document.all('ManageCom').value = '';
    document.all('InputDate').value = '';
  }
  catch(ex)
  {
    alert("在GroupUWAutoInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                            

function initForm()
{
  try
  {
    initInpBox();
    initSelfGrpGrid();
    initGrpGrid();
    easyQueryClickSelf();
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[0][1]="30px";            	//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="投保单印刷号";         	//列名
      iArray[1][1]="120px";            	//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="开始日期";         	//列名
      iArray[2][1]="80px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="开始时间";         	//列名
      iArray[3][1]="80px";            	//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[4]=new Array();
      iArray[4][0]="管理机构";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
      
      iArray[5]=new Array();
      iArray[5][0]="扫描日期";         		//列名
      iArray[5][1]="140px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许    

      iArray[6]=new Array();
      iArray[6][0]="工作流任务号";      //列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流子任务号";    //列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="工作流活动Id";      //列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[9]=new Array();
      iArray[9][0]="单证类型";         		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许    
      
      

      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //这些属性必须在loadMulLine前
      GrpGrid.mulLineCount =5;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.canChk = 0;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;        
      GrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
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
      iArray[0][1]="30px";            	//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="投保单印刷号";         	//列名
      iArray[1][1]="120px";            	//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="开始日期";         	//列名
      iArray[2][1]="80px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="开始时间";         	//列名
      iArray[3][1]="80px";            	//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[4]=new Array();
      iArray[4][0]="管理机构";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
      
      iArray[5]=new Array();
      iArray[5][0]="扫描日期";         		//列名
      iArray[5][1]="140px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许    

      iArray[6]=new Array();
      iArray[6][0]="工作流任务号";      //列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流子任务号";    //列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="工作流活动Id";      //列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[9]=new Array();
      iArray[9][0]="单证类型";         		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许    
             

      SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
      //这些属性必须在loadMulLine前
      SelfGrpGrid.mulLineCount =5;   
      SelfGrpGrid.displayTitle = 1;
      SelfGrpGrid.locked = 1;
      SelfGrpGrid.canSel = 1;
      SelfGrpGrid.canChk = 0;
      SelfGrpGrid.hiddenPlus = 1;
      SelfGrpGrid.hiddenSubtraction = 1;   
      SelfGrpGrid.selBoxEventFuncName = "InitGoToInput";     
      SelfGrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
