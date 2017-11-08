<%@include file="../i18n/language.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
<%
//程序名称：LABKRateToRiskInit.jsp
//程序功能：
//创建日期： 
//创建人  ： 
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                           
<script type="text/javascript">

// 输入框的初始化（单记录部分）

function initInpBox(){  
   fm.all('BranchType').value ='<%=request.getParameter("BranchType")%>';
}

function initForm(){
	try{
		initInpBox();    
		initRateGrid();
		isshowbutton();
	}catch(re){
		myAlert(""+"初始化界面错误!");
	}
}

var RateGrid; 
// 保单信息列表的初始化
function initRateGrid(){                               
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=10;            			//列最大值
		iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
		
		iArray[1]=new Array();
		iArray[1][0]="管理机构";         		//列名
		iArray[1][1]="60px";            		//列宽100
		iArray[1][2]=200;            			//列最大值
		iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[2]=new Array();
		iArray[2][0]="银行机构";         		//列名
		iArray[2][1]="0px";            		//列宽100
		iArray[2][2]=0;            			//列最大值
		iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[3]=new Array();
		iArray[3][0]="险种编码";         		//列名
		iArray[3][1]="60px";            		//列宽60
		iArray[3][2]=100;            			//列最大值
		iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[4]=new Array();
		iArray[4][0]="险种名称"; //列名
		iArray[4][1]="100px";        //列宽
		iArray[4][2]=100;            //列最大值
		iArray[4][3]=0;              //是否允许输入,1表示允许,0表示不允许    
		    
		iArray[5]=new Array();
		iArray[5][0]="付款年期(年)"; //列名
		iArray[5][1]="80px";        //列宽
		iArray[5][2]=100;            //列最大值
		iArray[5][3]=0;              //是否允许输入,1表示允许,0表示不允许    
		
		iArray[6]=new Array();
		iArray[6][0]="年龄起期"; //列名
		iArray[6][1]="80px";        //列宽
		iArray[6][2]=100;            //列最大值
		iArray[6][3]=0;              //是否允许输入,1表示允许,0表示不允许  
		
		iArray[7]=new Array();
		iArray[7][0]="年龄止期"; //列名
		iArray[7][1]="80px";        //列宽
		iArray[7][2]=100;            //列最大值
		iArray[7][3]=0;              //是否允许输入,1表示允许,0表示不允许  
		
		iArray[8]=new Array();
		iArray[8][0]="缴至岁数"; //列名
		iArray[8][1]="60px";        //列宽
		iArray[8][2]=100;            //列最大值
		iArray[8][3]=0;              //是否允许输入,1表示允许,0表示不允许  
		
		iArray[9]=new Array();
		iArray[9][0]="币种代码"; //列名
		iArray[9][1]="0px";        //列宽
		iArray[9][2]=100;            //列最大值
		iArray[9][3]=1;              //是否允许输入,1表示允许,0表示不允许
		    
		iArray[10]=new Array();
		iArray[10][0]="币种"; //列名
		iArray[10][1]="60px";        //列宽
		iArray[10][2]=100;            //列最大值
		iArray[10][3]=1;              //是否允许输入,1表示允许,0表示不允许   
		     
		iArray[11]=new Array();
		iArray[11][0]="缴费所属类型代码"; //列名
		iArray[11][1]="0px";        //列宽
		iArray[11][2]=100;            //列最大值
		iArray[11][3]=0;              //是否允许输入,1表示允许,0表示不允许    
		
		iArray[12]=new Array();
		iArray[12][0]="缴费所属类型"; //列名
		iArray[12][1]="100px";        //列宽
		iArray[12][2]=100;            //列最大值
		iArray[12][3]=0;              //是否允许输入,1表示允许,0表示不允许  
		
		
		iArray[13]=new Array();
		iArray[13][0]="缴费方式代码"; //列名
		iArray[13][1]="0px";        //列宽
		iArray[13][2]=100;            //列最大值
		iArray[13][3]=0;              //是否允许输入,1表示允许,0表示不允许    
		
		iArray[14]=new Array();
		iArray[14][0]="缴费方式"; //列名
		iArray[14][1]="60px";        //列宽
		iArray[14][2]=100;            //列最大值
		iArray[14][3]=0;              //是否允许输入,1表示允许,0表示不允许    
		
		iArray[15]=new Array();
		iArray[15][0]="保障计划代码"; //列名
		iArray[15][1]="0px";        //列宽
		iArray[15][2]=100;            //列最大值
		iArray[15][3]=0;              //是否允许输入,1表示允许,0表示不允许   
		
		iArray[16]=new Array();
		iArray[16][0]="保障计划"; //列名
		iArray[16][1]="60px";        //列宽
		iArray[16][2]=100;            //列最大值
		iArray[16][3]=0;              //是否允许输入,1表示允许,0表示不允许   
		
		iArray[17]=new Array();
		iArray[17][0]="保单年度(年)"; //列名
		iArray[17][1]="60px";        //列宽
		iArray[17][2]=100;            //列最大值
		iArray[17][3]=0;              //是否允许输入,1表示允许,0表示不允许    
		
		iArray[18]=new Array();
		iArray[18][0]="员工折扣比例"; //列名
		iArray[18][1]="60px";        //列宽
		iArray[18][2]=100;            //列最大值
		iArray[18][3]=0;              //是否允许输入,1表示允许,0表示不允许   
		
		iArray[19]=new Array();
		iArray[19][0]="有效起期";         		//列名
		iArray[19][1]="100px";            		//列宽60
		iArray[19][2]=100;            			//列最大值
		iArray[19][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[20]=new Array();
		iArray[20][0]="审核状态代码";         		//列名
		iArray[20][1]="0px";            		//列宽60
		iArray[20][2]=100;            			//列最大值
		iArray[20][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[21]=new Array();
		iArray[21][0]="审核状态";         		//列名
		iArray[21][1]="60px";            		//列宽60
		iArray[21][2]=100;            			//列最大值
		iArray[21][3]=3;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[22]=new Array();
		iArray[22][1]="4px";            		//列宽60
		iArray[22][2]=100;            			//列最大值
		iArray[22][3]=3; 
		  
		iArray[23]=new Array();
		iArray[23][1]="4px";            		//列宽60
		iArray[23][2]=100;            			//列最大值
		iArray[23][3]=3;           			//是否允许输入,1表示允许，0表示不允许    
		
		iArray[24]=new Array();
		iArray[24][1]="4px";            		//列宽60
		iArray[24][2]=100;            			//列最大值
		iArray[24][3]=3; 
		
		
		iArray[25]=new Array();               //plancode
        iArray[25][0]="PlanCode";         		//列名
        iArray[25][1]="60px";            		//列宽60
        iArray[25][2]=100;            			//列最大值
        iArray[25][3]=0;        
		RateGrid = new MulLineEnter( "fm" , "RateGrid" ); 
		//这些属性必须在loadMulLine前
		RateGrid.mulLineCount = 1;   
		RateGrid.displayTitle = 1;
		RateGrid.locked = 0;
		RateGrid.canSel = 1;
		RateGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		RateGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
		
		//在进行“+”操作时，输入项自动添加到mulLine的对应项，实现函数为addRateGrid（）
		RateGrid.addEventFuncName="addRateGrid"; 
		RateGrid.selBoxEventFuncName ="selectClick"; 
		RateGrid.loadMulLine(iArray);  
		
		//这些操作必须在loadMulLine后面
	}
	catch(ex){
		myAlert(ex);
	}
}

</script>
