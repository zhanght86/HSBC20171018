<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ScanContInit.jsp
//程序功能：个单新契约扫描件保单录入
//创建日期：2004-12-22 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
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
    fm.all('PrtNo').value = '';
    fm.all('ManageCom').value = '';
    fm.all('InputDate').value = '';
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
    easyQueryClickSelf();
    initGrpGrid();
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
      iArray[1][0]="投保单号";         	//列名
      iArray[1][1]="140px";            	//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="管理机构";         	//列名
      iArray[2][1]="120px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="录入日期";         	//列名
      iArray[3][1]="200px";            	//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="工作流任务号";      //列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="工作流子任务号";    //列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="工作流活动Id";      //列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[7]=new Array();
      iArray[7][0]="单证类型";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许    

      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //这些属性必须在loadMulLine前
      GrpGrid.mulLineCount = 5;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.canChk = 0;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;        
      GrpGrid.loadMulLine(iArray);  
      
      
      //这些操作必须在loadMulLine后面
      //GrpGrid.setRowColData(1,1,"asdf");
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
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="投保单号";         		//列名
      iArray[1][1]="140px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="管理机构";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="录入日期";         		//列名
      iArray[3][1]="200px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="工作流任务号";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="工作流子任务号";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="工作流活动Id";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="单证类型";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许
             

      SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
      //这些属性必须在loadMulLine前
      SelfGrpGrid.mulLineCount = 5;   
      SelfGrpGrid.displayTitle = 1;
      SelfGrpGrid.locked = 1;
      SelfGrpGrid.canSel = 1;
      SelfGrpGrid.canChk = 0;
      SelfGrpGrid.hiddenPlus = 1;
      SelfGrpGrid.hiddenSubtraction = 1;   
      SelfGrpGrid.selBoxEventFuncName = "InitGoToInput";     
      SelfGrpGrid.loadMulLine(iArray);  
    
      
      //这些操作必须在loadMulLine后面
      //SelfGrpGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
