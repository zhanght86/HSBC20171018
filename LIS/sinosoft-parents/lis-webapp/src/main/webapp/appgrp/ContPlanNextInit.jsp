<%
//程序名称：GrpHealthFactoryQueryInit.jsp
//程序功能：
//创建日期：2004-08-30
//创建人  ：sunxy
//更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">
function initForm(){
  try{
    if(this.LoadFlag=="4"||this.LoadFlag=="16")
    {
       divRiskPlanSave.style.display="none";
    }
    GrpPerPolDefine();
  }
  catch(re)
  {
    alert("ContPlanNextInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var ContPlanGrid;
// 要约信息列表的初始化
function initImpartGrid(tImpContPlanCode) {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";
      iArray[0][1]="30px";
      iArray[0][2]=10;
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="计划级别";
      iArray[1][1]="60px";
      iArray[1][2]=100;
      iArray[1][3]=2;
      iArray[1][10] = "ImpContPlanCode";
      iArray[1][11] = tImpContPlanCode;
      iArray[1][12] = "1|10|9";
      iArray[1][13] = "0|1|2";
      iArray[1][19] = 1;

      iArray[2]=new Array();
      iArray[2][0]="计划险种";
      iArray[2][1]="60px";
      iArray[2][2]=100;
      iArray[2][3]=2;
      iArray[2][4] = "ImpRiskCode";
      iArray[2][5] = "2|11|13|14";
      iArray[2][6] = "0|2|3|4";
      iArray[2][15]="ContPlanCode";
      iArray[2][17]="9";
      iArray[2][19] = 1;


      iArray[3]=new Array();
      iArray[3][0]="要素类别";
      iArray[3][1]="60px";
      iArray[3][2]=100;
      iArray[3][3]=2;
      iArray[3][4] = "ImpFactoryType";
      iArray[3][5] = "3|8";
      iArray[3][6] = "0|2";
      iArray[3][15]="RiskCode";
      iArray[3][17]="2";
      iArray[3][19] = 1;

      iArray[4]=new Array();
      iArray[4][0]="要素目标编码";
      iArray[4][1]="80px";
      iArray[4][2]=60;
      iArray[4][3]=2;
      iArray[4][4]="ImHealthFactoryNo";
      iArray[4][9]="要素目标编码|len<=6";
      iArray[4][15]="RiskCode";
      iArray[4][17]="8";

      iArray[5]=new Array();
      iArray[5][0]="要素计算编码";
      iArray[5][1]="80px";
      iArray[5][2]=60;
      iArray[5][3]=2;
      iArray[5][4]="ImHealthFactory";
      iArray[5][5] = "5|6|7|12";
      iArray[5][6] = "0|1|2|3";
      iArray[5][9]="要素计算编码|len<=4";
      iArray[5][15]="RiskCode";
      iArray[5][17]="8";

      iArray[6]=new Array();
      iArray[6][0]="要素内容";
      iArray[6][1]="300px";
      iArray[6][2]=200;
      iArray[6][3]=1;

      iArray[7]=new Array();
      iArray[7][0]="要素值";
      iArray[7][1]="80px";
      iArray[7][2]=150;
      iArray[7][3]=1;

      iArray[8]=new Array();
      iArray[8][0]="条件1";
      iArray[8][1]="0px";
      iArray[8][2]=150;
      iArray[8][3]=3;

      iArray[9]=new Array();
      iArray[9][0]="条件2";
      iArray[9][1]="0px";
      iArray[9][2]=150;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="计划名称";
      iArray[10][1]="0px";
      iArray[10][2]=150;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="险种版本";
      iArray[11][1]="0px";
      iArray[11][2]=150;
      iArray[11][3]=3;

      iArray[12]=new Array();
      iArray[12][0]="计算编码名称";
      iArray[12][1]="0px";
      iArray[12][2]=150;
      iArray[12][3]=3;

      iArray[13]=new Array();
      iArray[13][0]="主险编码";
      iArray[13][1]="50px";
      iArray[13][2]=150;
      iArray[13][3]=0;

      iArray[14]=new Array();
      iArray[14][0]="主险版本";
      iArray[14][1]="0px";
      iArray[14][2]=150;
      iArray[14][3]=3;

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" );
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 1;
      ImpartGrid.displayTitle = 1;
      ImpartGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
    }
    catch(ex) {
      alert(ex);
    }
}
</script>
