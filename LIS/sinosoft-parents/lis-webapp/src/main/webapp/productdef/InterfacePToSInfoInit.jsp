<%@include file="../i18n/language.jsp"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getAttribute("GI");	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	//String strOperator = globalInput.Operator;
%>
<script type="text/javascript"><!--
function initForm()
{
initMullineGrid();
initSubRiskMullineGrid();
}
function initSubRiskMullineGrid()
{
var iArray = new Array();
	try{
	  iArray[0]=new Array();
	  iArray[0][0]="序号";
	  iArray[0][1]="30px";
	  iArray[0][2]=100;
	  iArray[0][3]=0;
	  

	  //iArray[1]=new Array();
	  //iArray[1][0]="主险";
	  //iArray[1][1]="80px";
	  //iArray[1][2]=100;
	  //iArray[1][3]=3;
	  //iArray[1][10]="code";
	  //iArray[1][11]="0|^1"; 主險PlanCode
	  
	  iArray[1]=new Array();
	  iArray[1][0]="附加险";
	  iArray[1][1]="120px";
	  iArray[1][2]=100;
	  iArray[1][3]=2;
	  iArray[1][4]='querysrisk';
	  iArray[1][5]='1';
	  iArray[1][6]='0|1';
	  
	  iArray[2]=new Array();
	  iArray[2][0]="附加险接口编码";
	  iArray[2][1]="120px";
	  iArray[2][2]=100;
	  iArray[2][3]=1;
	  
	  iArray[3]=new Array();
	  iArray[3][0]="批次";
	  iArray[3][1]="40px";
	  iArray[3][2]=100;
	  iArray[3][3]=1;
	  
	  iArray[4]=new Array();
	  iArray[4][0]="操作员";
	  iArray[4][1]="80px";
	  iArray[4][2]=100;
	  iArray[4][3]=3;
	  
	  iArray[5]=new Array();
	  iArray[5][0]="是否与保障相关";
	  iArray[5][1]="100px";
	  iArray[5][2]=100;
	  iArray[5][3]=2;
	  iArray[5][4]='isbenefitoption';
	  iArray[5][5]='5';
	  iArray[5][6]='0|1'
	  
	  
	  iArray[6]=new Array();
	  iArray[6][0]="保障计划类型";
	  iArray[6][1]="100px";
	  iArray[6][2]=100;
	  iArray[6][3]=2;
	  iArray[6][4]='benefitoptiontype';
	  iArray[6][5]='6';
	  iArray[6][6]='0|1'
	  
	  iArray[7]=new Array();
	  iArray[7][0]="保障计划相关值";
	  iArray[7][1]="100px";
	  iArray[7][2]=100;
	  iArray[7][3]=1;
	  
	  SubRiskMullineGrid = new MulLineEnter( "fm" , "SubRiskMullineGrid" ); 
	  SubRiskMullineGrid.mulLineCount=1;
	  SubRiskMullineGrid.displayTitle=1;
	  SubRiskMullineGrid.canSel=0;
	  SubRiskMullineGrid.canChk=0;
	  SubRiskMullineGrid.hiddenPlus=0;
	  SubRiskMullineGrid.hiddenSubtraction=0;
	  //SubRiskMullineGrid.selBoxEventFuncName ="showList";
	  SubRiskMullineGrid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
	}
}
function initMullineGrid()
{
	var iArray = new Array();
	try{
	  iArray[0]=new Array();
	  iArray[0][0]="序号";
	  iArray[0][1]="30px";
	  iArray[0][2]=100;
	  iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="险种编码";
      iArray[1][1]="80px";
      iArray[1][2]=20;
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="PlanCode";
      iArray[2][1]="80px";
      iArray[2][2]=20;
      iArray[2][3]=0;
      
      
      iArray[3]=new Array();
      iArray[3][0]="险种中文名称";
      iArray[3][1]="120px";
      iArray[3][2]=20;
      iArray[3][3]=0;
      
      iArray[4]=new Array();
      iArray[4][0]="险种英文名称";
      iArray[4][1]="120px";
      iArray[4][2]=20;
      iArray[4][3]=0;
      
      
      iArray[5]=new Array();
      iArray[5][0]="接口险种中文名称";
      iArray[5][1]="120px";
      iArray[5][2]=20;
      iArray[5][3]=3;
      
      iArray[6]=new Array();
      iArray[6][0]="接口险种英文名称";
      iArray[6][1]="120px";
      iArray[6][2]=20; 
      iArray[6][3]=3; 
                                                         
      iArray[7]=new Array();  
      iArray[7][0]="是否销售日期相关"; 
      iArray[7][1]="100px"; 
      iArray[7][2]=20; 
      iArray[7][3]=3; 
                                                      
      iArray[8]=new Array();  
      iArray[8][0]="开售日期";
      iArray[8][1]="100px";
      iArray[8][2]=20; 
      iArray[8][3]=3; 
                                                      
      iArray[9]=new Array(); 
      iArray[9][0]="停售日期";
      iArray[9][1]="120px";                            
      iArray[9][2]=20;                                
      iArray[9][3]=3;                                 
                                                      
      iArray[10]=new Array();
      iArray[10][0]="是否与配偶共购相关";
      iArray[10][1]="80px";
      iArray[10][2]=20;
      iArray[10][3]=3;
      
      iArray[11]=new Array();
      iArray[11][0]="配偶共购标记";
      iArray[11][1]="100px";
      iArray[11][2]=20;
      iArray[11][3]=3;
      																							                     
      iArray[12]=new Array();                                  
      iArray[12][0]="是否与员工折扣相关";                                      
      iArray[12][1]="80px";                                    
      iArray[12][2]=20;                                              
      iArray[12][3]=3;                                         
                                                                     
      iArray[13]=new Array();                                            
      iArray[13][0]="员工折扣标记";                                    
      iArray[13][1]="100px";                                              
      iArray[13][2]=20;                                                      
      iArray[13][3]=3;                                         
                                                                     
      iArray[14]=new Array();                                            
      iArray[14][0]="是否与交费期间相关";                                    
      iArray[14][1]="80px";                                                  
      iArray[14][2]=20;                                                          
      iArray[14][3]=3;
      
      iArray[15]=new Array();
      iArray[15][0]="交费期间类型";
      iArray[15][1]="100px";
      iArray[15][2]=20;
      iArray[15][3]=3;
      
      iArray[16]=new Array();
      iArray[16][0]="交费期间";
      iArray[16][1]="80px";
      iArray[16][2]=20;
      iArray[16][3]=3;
      
      iArray[17]=new Array();
      iArray[17][0]="是否与保障相关";
      iArray[17][1]="80px";
      iArray[17][2]=20;
      iArray[17][3]=3;
      
      iArray[18]=new Array();
      iArray[18][0]="保障类型";
      iArray[18][1]="80px";
      iArray[18][2]=20;
      iArray[18][3]=3;
      
      iArray[19]=new Array();
      iArray[19][0]="保障";
      iArray[19][1]="60px";
      iArray[19][2]=20;
      iArray[19][3]=3;
                          
      iArray[20]=new Array();
      iArray[20][0]="是否与保险期间相关";
      iArray[20][1]="80px";
      iArray[20][2]=20;
      iArray[20][3]=3;
      
      iArray[21]=new Array();
      iArray[21][0]="保险期间类型";
      iArray[21][1]="80px";
      iArray[21][2]=20;
      iArray[21][3]=3;
      
      iArray[22]=new Array();
      iArray[22][0]="保险期间";
      iArray[22][1]="80px";
      iArray[22][2]=20;
      iArray[22][3]=3;
      
      iArray[23]=new Array();
      iArray[23][0]="是否与渠道相关";
      iArray[23][1]="80px";
      iArray[23][2]=20;
      iArray[23][3]=3;
      
      iArray[24]=new Array();
      iArray[24][0]="渠道";
      iArray[24][1]="50px";
      iArray[24][2]=20;
      iArray[24][3]=3;
      
      iArray[25]=new Array();
      iArray[25][0]="是否与币种相关";
      iArray[25][1]="80px";
      iArray[25][2]=20;
      iArray[25][3]=3;
      
      iArray[26]=new Array();
      iArray[26][0]="币种";
      iArray[26][1]="40px";
      iArray[26][2]=20;
      iArray[26][3]=3;
      
      iArray[27]=new Array();
      iArray[27][0]="是否与分红相关";
      iArray[27][1]="80px";
      iArray[27][2]=20;
      iArray[27][3]=3;
      
      iArray[28]=new Array();
      iArray[28][0]="分红标记";
      iArray[28][1]="80px";
      iArray[28][2]=20;
      iArray[28][3]=3;
      
      iArray[29]=new Array();
      iArray[29][0]="是否与核保类型相关";
      iArray[29][1]="80px";
      iArray[29][2]=20;
      iArray[29][3]=3;
	  
	  iArray[30]=new Array();
      iArray[30][0]="是否与联保相关";
      iArray[30][1]="80px";
      iArray[30][2]=20;
      iArray[30][3]=3;
      
      iArray[31]=new Array();
      iArray[31][0]="批号";
      iArray[31][1]="80px";
      iArray[31][2]=20;
      iArray[31][3]=0;
      
      iArray[32]=new Array();
      iArray[32][0]="是否联保";
      iArray[32][1]="80px";
      iArray[32][2]=20;
      iArray[32][3]=3;
      
      iArray[33]=new Array();
      iArray[33][0]="核保类型";
      iArray[33][1]="80px";
      iArray[33][2]=20;
      iArray[33][3]=3;

     		
		
		MullineGrid = new MulLineEnter( "fm" , "MullineGrid" ); 

		MullineGrid.mulLineCount=0;
		MullineGrid.displayTitle=1;
		MullineGrid.canSel=1;
		MullineGrid.canChk=0;
		MullineGrid.hiddenPlus=1;
		MullineGrid.hiddenSubtraction=1;
		MullineGrid.selBoxEventFuncName ="showList";
		MullineGrid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
	}
}
--></script>
</html>
