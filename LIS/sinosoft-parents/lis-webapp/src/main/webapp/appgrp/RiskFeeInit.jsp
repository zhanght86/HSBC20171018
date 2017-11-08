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
function initForm(){
	try{
	        if(this.LoadFlag=="4"||this.LoadFlag=="16")
                {
                     divRiskFeeSave.style.display="none";
                }
                if(this.LoadFlag=="11")
                {
                  divReturnBack.style.display="none";
                }
		initRiskFeeGrid();
		divRiskFeeParam.style.display='none';
	}
	catch(re){
		alert("GrpFeeInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

//险种管理费明细
function initRiskFeeGrid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=10;            			//列最大值
		iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		iArray[1]=new Array();
		iArray[1][0]="帐户代码";
		iArray[1][1]="60px";
		iArray[1][2]=60;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="帐户说明";
		iArray[2][1]="150px";
		iArray[2][2]=150;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="缴费代码";
		iArray[3][1]="60px";
		iArray[3][2]=60;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="缴费说明";
		iArray[4][1]="150px";
		iArray[4][2]=150;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="管理费代码";
		iArray[5][1]="60px";
		iArray[5][2]=60;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="管理费名称";
		iArray[6][1]="150px";
		iArray[6][2]=150;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="计算说明";
		iArray[7][1]="0px";
		iArray[7][2]=200;
		iArray[7][3]=3;

		iArray[8]=new Array();
		iArray[8][0]="计算方式";
		iArray[8][1]="60px";
		iArray[8][2]=60;
		iArray[8][3]=2;
		iArray[8][4]="FeeCalMode";

		iArray[9]=new Array();
		iArray[9][0]="计算类别";
		iArray[9][1]="0px";
		iArray[9][2]=200;
		iArray[9][3]=3;

		iArray[10]=new Array();
		iArray[10][0]="固定值/固定比例";
		iArray[10][1]="60px";
		iArray[10][2]=60;
		iArray[10][3]=1;
		iArray[10][9]= "固定值|num"; 

		iArray[11]=new Array();
		iArray[11][0]="比较值";
		iArray[11][1]="0px";
		iArray[11][2]=200;
		iArray[11][3]=3;

		iArray[12]=new Array();
		iArray[12][0]="计算类型";
		iArray[12][1]="0px";
		iArray[12][2]=200;
		iArray[12][3]=3;

		iArray[13]=new Array();
		iArray[13][0]="扣除管理费周期";
		iArray[13][1]="0px";
		iArray[13][2]=200;
		iArray[13][3]=3;

		iArray[14]=new Array();
		iArray[14][0]="扣除管理费最大次数";
		iArray[14][1]="0px";
		iArray[14][2]=200;
		iArray[14][3]=3;

		iArray[15]=new Array();
		iArray[15][0]="缺省标记";
		iArray[15][1]="0px";
		iArray[15][2]=200;
		iArray[15][3]=3;

		iArray[16]=new Array();
		iArray[16][0]="记录状态";
		iArray[16][1]="50px";
		iArray[16][2]=200;
		iArray[16][3]=0;

		RiskFeeGrid = new MulLineEnter( "fm" , "RiskFeeGrid" );
		//这些属性必须在loadMulLine前
		//RiskFeeGrid.mulLineCount = 10;
		RiskFeeGrid.displayTitle = 5;
		RiskFeeGrid.locked = 1;
		RiskFeeGrid.canSel = 0;
		RiskFeeGrid.hiddenPlus = 1;
		RiskFeeGrid.hiddenSubtraction = 1;
		//RiskFeeGrid.selBoxEventFuncName = "QueryRiskFeeParam"; 
		RiskFeeGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}

//险种管理费明细参数
function initRiskFeeParamGrid() {                     
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="费用上限";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=150;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="费用下限";    	        //列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[3]=new Array();
      iArray[3][0]="管理费比列";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]= 60;            			//列最大值
      iArray[3][3]= 1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="管理费比列";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]= 60;            			//列最大值
      iArray[4][3]= 3;              			//是否允许输入,1表示允许，0表示不允许

		iArray[5]=new Array();
		iArray[5][0]="记录状态";
		iArray[5][1]="0px";
		iArray[5][2]=200;
		iArray[5][3]=0;
		
      RiskFeeParamGrid = new MulLineEnter( "fm" , "RiskFeeParamGrid" );
      //这些属性必须在loadMulLine前
      RiskFeeParamGrid.mulLineCount = 5;
      RiskFeeParamGrid.displayTitle = 1;
      RiskFeeParamGrid.hiddenPlus = 1;
      RiskFeeParamGrid.hiddenSubtraction = 0;
      RiskFeeParamGrid.canChk=0;
      RiskFeeParamGrid.loadMulLine(iArray);

    }
    catch(ex) {
      alert(ex);
    }
}
</script>
