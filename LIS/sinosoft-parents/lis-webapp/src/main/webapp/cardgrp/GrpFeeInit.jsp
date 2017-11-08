<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GrpFeeInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
	//添加页面控件的初始化。
%>
<script language="JavaScript">

function initInpBox()
{ 
  try
  {                                   
    fm.all('ProposalGrpContNo').value = ProposalGrpContNo;
    fm.all('GrpContNo').value = ProposalGrpContNo;
   // alert(fm.all('GrpContNo').value);
    
  }
  catch(ex)
  {
    alert("在GroupPolInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm(){
	try{
		initInpBox();
		//initGrpFeeGrid();
		initContPlanGrid();
		//easyQueryClick();
	        if(this.LoadFlag=="4"||this.LoadFlag=="16")
                {
                    divRiskSave.style.display="none";
                }
		initContPlanDutyGrid();
	}
	catch(re){
		alert("GrpFeeInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

var ContPlanGrid; 

// 要约信息列表的初始化
function initContPlanGrid() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="险种名称";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=150;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      
      iArray[2]=new Array();
      iArray[2][0]="险种编码";    	        //列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
     	
      
      iArray[3]=new Array();
      iArray[3][0]="责任编码";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]= 60;            			//列最大值
      iArray[3][3]= 3;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][10]="DutyCode";   
      
      
      iArray[4]=new Array();
      iArray[4][0]="险种责任";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      iArray[5]=new Array();
      iArray[5][0]="计算要素";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;  
      iArray[5][10]="FactorCode";            			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="要素名称";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=150;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="要素说明";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=150;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="要素值";         		//列名
      iArray[8][1]="50px";            		//列宽
      iArray[8][2]=150;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="特别说明";         		//列名
      iArray[9][1]="200px";            		//列宽
      iArray[9][2]=150;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="险种版本";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=10;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="集体保单险种号码";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=10;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="主险编码";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=10;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="类型";         		//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=10;            			//列最大值
      iArray[13][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      ContPlanGrid = new MulLineEnter( "fm" , "ContPlanGrid" ); 
      //这些属性必须在loadMulLine前
      ContPlanGrid.mulLineCount = 0;   
      ContPlanGrid.displayTitle = 1;
      ContPlanGrid.hiddenPlus = 1;
      ContPlanGrid.hiddenSubtraction = 1;
      ContPlanGrid.canChk=0;
      ContPlanGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ContPlanGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}

// 要约信息列表的初始化
function initContPlanDutyGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	        //列名
      iArray[1][1]="155px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[2]=new Array();
      iArray[2][0]="责任名称";         		//列名
      iArray[2][1]="155px";            		//列宽
      iArray[2][2]=150;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][10]="RiskCode";

      iArray[3]=new Array();
      iArray[3][0]="责任类型";         		//列名
      iArray[3][1]="155px";            		//列宽
      iArray[3][2]= 60;            			//列最大值
      iArray[3][3]= 0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="类型名称";         		//列名
      iArray[4][1]="155px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      ContPlanDutyGrid = new MulLineEnter( "fm" , "ContPlanDutyGrid" );
      //这些属性必须在loadMulLine前
      ContPlanDutyGrid.mulLineCount = 0;
      ContPlanDutyGrid.displayTitle = 1;
      ContPlanDutyGrid.hiddenPlus = 1;
      ContPlanDutyGrid.hiddenSubtraction = 1;
      ContPlanDutyGrid.canChk=1;
      ContPlanDutyGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      //ContPlanGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}
</script>
