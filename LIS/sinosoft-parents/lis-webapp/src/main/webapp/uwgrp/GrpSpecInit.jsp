<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
              

<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  { 
   //var sql="select SpecContent from LCCGrpSpec where ProposalGrpContNo='"+GrpContno+"'";                                  
   //var arrQueryResult = easyExecSql(sql, 1, 0);
   //if(arrQueryResult!=null)
   //{
   //  fm.Content.value=arrQueryResult[0][0];
   //}
  }
  catch(ex)
  {
    alert("在UWManuDateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}

                 

function initForm(tGrpContNo,tFlag)
{
  try
  {
	
	initInpBox();	
	initSpecInfoGrid();
	querySpec();
	
	
  }
  catch(re)
  {
    alert("GrpSpecInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initSpecInfoGrid() {
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="序号"; 			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";		//列宽
    iArray[0][2]=10;			//列最大值
    iArray[0][3]=0;			//是否允许输入,1表示允许，0表示不允许

	iArray[1]=new Array();
    iArray[1][0]="合同号"; 		//列名
    iArray[1][1]="120px";		//列宽
    iArray[1][2]=40;			//列最大值
    iArray[1][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="特约内容"; 		//列名
    iArray[2][1]="300px";		//列宽
    iArray[2][2]=40;			//列最大值
    iArray[2][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="操作员"; 	//列名
    iArray[3][1]="80px";		//列宽
    iArray[3][2]=30;			//列最大值
    iArray[3][3]=0;			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="录入时间"; 	//列名
    iArray[4][1]="80px";		//列宽
    iArray[4][2]=30;			//列最大值
    iArray[4][3]=0;			//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="流水号"; 	//列名
    iArray[5][1]="0px";		//列宽
    iArray[5][2]=30;			//列最大值
    iArray[5][3]=0;			//是否允许输入,1表示允许，0表示不允许

    
    iArray[6]=new Array();
    iArray[6][0]="集体投保单号"; 	//列名
    iArray[6][1]="0px";		//列宽
    iArray[6][2]=30;			//列最大值
    iArray[6][3]=0;			//是否允许输入,1表示允许，0表示不允许

    SpecInfoGrid = new MulLineEnter( "fm" , "SpecInfoGrid" );
    //这些属性必须在loadMulLine前
    SpecInfoGrid.mulLineCount = 1;
    SpecInfoGrid.displayTitle = 1;
    SpecInfoGrid.hiddenPlus = 1;
    SpecInfoGrid.canSel=1;
    SpecInfoGrid.hiddenSubtraction = 1;   
    SpecInfoGrid. selBoxEventFuncName ="getSpecContent";
    SpecInfoGrid.loadMulLine(iArray);

  } catch(ex) {
    alert("在ProposalInit.jsp-->initBnfGrid函数中发生异常:初始化界面错误!");
  }
}



</script>


