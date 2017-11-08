<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//PDDiscountDefiInit.jsp
//程序功能：客户品质管理
//创建日期：2011-03-03
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
           
<script type="text/javascript">
	
function initAll() {
    fm.all('DiscountType').value = '';
    fm.all('DiscountCode').value = '';
    //fm.all('CampaignCode').value = '';
    fm.all('AddFeeDiscFlag').value = '';
    fm.all('RiskCode').value = tRiskCode;
    fm.all('DutyCode').value = '';
    fm.all('StartDate').value = '';
    fm.all('EndDate').value = '';
    fm.all('DiscounTypeName').value = '';
		fm.all('DutyCodeName').value = '';
		fm.all('AddFeeDiscFlagName').value = '';
		fm.all('CalCode').value = '';
    
}

function initForm() {
  try {
	initAll();
	initPremDiscountGrid();
	queryPremDiscountGrid();
  }
  catch(re) {
    myAlert("PDDiscountDefiInit.jsp-->"+"初始化界面错误!");
  }
}

function initPremDiscountGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="折扣类型";          		//列名
      iArray[1][1]="50px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[1][4]="discounttype";              	        //是否引用代码:null||""为不引用
      //iArray[1][9]="折扣类型|code:discounttype";
      //iArray[1][15]="1";  //依赖的列 
      //iArray[1][16]="1 and code=#D_PR#";//限制条件
      
      iArray[2]=new Array();
      iArray[2][0]="折扣号码";          		//列名
      iArray[2][1]="120px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[3]=new Array();
      iArray[3][0]="促销活动编码";         			//列名
      iArray[3][1]="0px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="加费折扣标记";      	   		//列名
      iArray[4][1]="50px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			//iArray[4][4]="yesno";              	        //是否引用代码:null||""为不引用
      //iArray[4][9]="加费折扣标记|code:yesno";

      iArray[5]=new Array();
      iArray[5][0]="险种编码";      	   		//列名
      iArray[5][1]="0px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
			iArray[5][4]="riskcode";              	        //是否引用代码:null||""为不引用

			var tSqlStr = "1 and riskcode=#" + tRiskCode + "#";
      iArray[6]=new Array();
      iArray[6][0]="责任编码";      	   		//列名
      iArray[6][1]="50px";            			//列宽
      iArray[6][2]=20;            			//列最大值
     	iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			//iArray[6][4]="pd_dutycode";              	        //是否引用代码:null||""为不引用
			//iArray[6][15]="1";  //依赖的列 
      //iArray[6][16]=tSqlStr;//限制条件

      iArray[7]=new Array();
      iArray[7][0]="起始日期";      	   		//列名
      iArray[7][1]="60px";            			//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=8;                   //是否允许输入,1表示允许，0表示不允许      
            
      iArray[8]=new Array();
      iArray[8][0]="截止日期";      	   		//列名
      iArray[8][1]="60px";            			//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=8;                   //是否允许输入,1表示允许，0表示不允许      
 
 		  iArray[9]=new Array();
      iArray[9][0]="算法编码";      	   		//列名
      iArray[9][1]="60px";            			//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=0;                   //是否允许输入,1表示允许，0表示不允许      
     
      PremDiscountGrid = new MulLineEnter( "fm" , "PremDiscountGrid" ); 
      //这些属性必须在loadMulLine前
      PremDiscountGrid.mulLineCount = 1;   
      PremDiscountGrid.displayTitle = 1;
      PremDiscountGrid.hiddenPlus=1;
      PremDiscountGrid.hiddenSubtraction = 1;
      PremDiscountGrid.locked=0;
      PremDiscountGrid.canSel=1;
      PremDiscountGrid.selBoxEventFuncName="showPremDiscountDetail";
      PremDiscountGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        myAlert(ex);
      }
}

</script>
