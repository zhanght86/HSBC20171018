<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuSpecInit.jsp
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
                       
<%

%>      

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

var str = "";

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {                                   
	// 延长日期天数
    //document.all('Prem').value = '';
    //document.all('SumPrem').value = '';
    //document.all('Mult').value = '';
    //document.all('RiskAmnt').value = '';
    //document.all('Remark').value = '';
    //document.all('Reason').value = '';
    //document.all('SpecReason').value = '';
    changeRiskPlan.style.display='none';
  }
  catch(ex)
  {
    alert("在UWManuDateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在UWSubInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        


function initForm()
{
  var str = "";
  try
  {
  	//alert("tInsuredNo:"+tInsuredNo);
  	changeRiskMain.style.display="";
  	changeRiskPlanTitle.style.display="none";
    initHide();
    //initInpBox();
    initUWSpecGrid();
    initPolAddGrid();
    //initCancleGiven(); //初始化取消提前给付特约按钮
    queryInsuredInfo();
    
    if(tQueryFlag=="1"){
      document.getElementById("fm").button1.style.display="none";
      document.getElementById("fm").button3.style.display="none";
      document.getElementById("fm").button4.style.display="none";
      
	  }
	  
   // initpolno(tContNo);
    QueryPolSpecGrid();
   
    queryRiskAddFee();
    
    initOldRislPlanGrid("");
    
    queryOldRiskPlan();
    
    //alert('4');
    //alert("document.all('EdorType').value=="+document.all('EdorType').value);
    //initUWSpecContGrid();
    //QueryPolSpecCont(tContNo);
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initUWSpecGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="特约内容";         		//列名
      iArray[1][1]="430px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="流水号";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="状态代码";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="状态";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="合同号";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    
      iArray[6]=new Array();
      iArray[6][0]="序列号";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许

			iArray[7]=new Array();
      iArray[7][0]="客户号";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许

			iArray[8]=new Array();
      iArray[8][0]="特约类型";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许

			iArray[9]=new Array();
      iArray[9][0]="特约编码";         		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="特约原因";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="录入时间";         		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=8;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="录入员";         		//列名
      iArray[12][1]="80px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[13]=new Array();
      iArray[13][0]="下发状态标志";         		//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=100;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[14]=new Array();
      iArray[14][0]="下发状态";         		//列名
      iArray[14][1]="80px";            		//列宽
      iArray[14][2]=100;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      UWSpecGrid = new MulLineEnter( "document" , "UWSpecGrid" ); 
      //这些属性必须在loadMulLine前
      UWSpecGrid.mulLineCount = 5;   
      UWSpecGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWSpecGrid.canSel = 1;
      UWSpecGrid.hiddenPlus = 1;
      UWSpecGrid.hiddenSubtraction = 1;
      UWSpecGrid.loadMulLine(iArray);       
      UWSpecGrid.selBoxEventFuncName = "getSpecGridCho2";
     
      //这些操作必须在loadMulLine后面
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


function initHide()
{
	document.all('ContNo').value=tContNo;
	document.all('MissionID').value=tMissionID;
	document.all('SubMissionID').value=tSubMissionID;
	document.all('InsuredNo').value = tInsuredNo ;
	document.all('EdorNo').value = tEdorNo ;
	document.all('EdorAcceptNo').value = tEdorAcceptNo ;
	document.all('EdorType').value = tEdorType ;
}

// 保单信息列表的初始化
function initPolAddGrid()
  {
  var str = initlist();

  var iArray = new Array();

      try
      {

      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			  //列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
//险种编码,险种名称,加费类型,加费类别,加费方式,评点,加费金额,加费原因,起期,止期,下发状态,保单号,主险保单号
   	  iArray[1]=new Array();
      iArray[1][0]="险种编码";    	        //列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=1;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[1][10] = "RiskCode";
      iArray[1][11] = str;
      iArray[1][12] = "1|2|3|9|10|12|13|17";
      iArray[1][13] = "0|1|2|3|4|5|6|7";
      iArray[1][19] = 1;
      
			iArray[2]=new Array();
      iArray[2][0]="险种名称";         		//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;    //列最大值
      iArray[2][3]=2;         			
      iArray[2][10] = "RiskCode";
      iArray[2][11] = str;
      iArray[2][12] = "2|1|3|9|10|12|13|17";
      iArray[2][13] = "1|0|2|3|4|5|6|7";
      iArray[2][19] = 1;


      iArray[3]=new Array();
      iArray[3][0]="加费责任编码";    	        //列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择

		  iArray[4]=new Array();
      iArray[4][0]="加费类别";    	        //列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[4][10] = "PlanPay";
      iArray[4][11] = "0|^01|健康加费||^02|职业加费|03|^03|其他加费|03|";
      iArray[4][12] = "4|5|6";
      iArray[4][13] = "0|2|3";
      iArray[4][19] = 1;


		  iArray[5]=new Array();
      iArray[5][0]="加费方式";    	        //列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[5][10] = "AddFeeMethod";
      iArray[5][11] = "0|^01|按EM值|^02|按保费比例|^03|按保额|";
      iArray[5][12] = "5|6";
      iArray[5][13] = "0|2";
      iArray[5][19] = 1;

/*
			iArray[5]=new Array();
      iArray[5][0]="加费方式";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=2;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[5][4] = "addfeetypemethod";
      iArray[5][5] = "5";
      iArray[5][6] = "0";
*/      
			iArray[6]=new Array();
      iArray[6][0]="评点/比例";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

			iArray[7]=new Array();
      iArray[7][0]="加费金额";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=2;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
     
			iArray[7][7]="AutoCalAddFee";

      iArray[8]=new Array();
      iArray[8][0]="加费原因";    	        //列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
     

      iArray[9]=new Array();
      iArray[9][0]="加费起期";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=8;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[10]=new Array();
      iArray[10][0]="加费止期";         		//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=8;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[11]=new Array();
      iArray[11][0]="下发状态";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[12]=new Array();
      iArray[12][0]="险种号";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[13]=new Array();
      iArray[13][0]="主险险种号";         		//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=60;            			//列最大值
      iArray[13][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[14]=new Array();
      iArray[14][0]="缴费计划编码";         		//列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=1;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
      
      iArray[15]=new Array();
      iArray[15][0]="加费开始时间类型";         		//列名
      iArray[15][1]="100px";            		//列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=2;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[15][4]="addfeedatetype";
      //iArray[15][10] = "addFormMethod";
      //iArray[15][11] = "0|^0|追溯|^1|当期加费|^2|下期加费|";      
      
      iArray[16]=new Array();
      iArray[16][0]="交至日期";         		//列名
      iArray[16][1]="60px";            		//列宽
      iArray[16][2]=60;            			//列最大值
      iArray[16][3]=8;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择


      iArray[17]=new Array();
			iArray[17][0]="币种";
			iArray[17][1]="60px";
			iArray[17][2]=100;
			iArray[17][3]=2;
			iArray[17][4]="currency"; 
				
      PolAddGrid = new MulLineEnter( "fm" , "PolAddGrid" );
      //这些属性必须在loadMulLine前
      PolAddGrid.mulLineCount = 5;
      PolAddGrid.canSel = 1;
      PolAddGrid.displayTitle = 1;
      PolAddGrid.loadMulLine(iArray);      
       //这些操作必须在loadMulLine后面
      //SpecGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


function initOldRislPlanGrid(str)
  {
  var iArray = new Array();

      try
      {

      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			  //列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

   		iArray[1]=new Array();
      iArray[1][0]="险种编码";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

			iArray[2]=new Array();
      iArray[2][0]="险种名称";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择


      iArray[3]=new Array();
      iArray[3][0]="保额";    	        //列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=7;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[3][23]="0"; 


      iArray[4]=new Array();
      iArray[4][0]="份数";    	        //列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择


      iArray[5]=new Array();
      iArray[5][0]="保费";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=7;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择
      iArray[5][23]="0";

      iArray[6]=new Array();
      iArray[6][0]="保险期间";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[7]=new Array();
      iArray[7][0]="缴费期间";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[8]=new Array();
      iArray[8][0]="缴费频率";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许 2表示代码选择

	  iArray[9]=new Array();
      iArray[9][0]="原核保结论";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;
	  
      iArray[10]=new Array();
      iArray[10][0]="核保决定";         		//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
	  // iArray[10][4]="edoruwstate";              	        //是否引用代码:null||""为不引用
	  iArray[10][10] = "edoruwstate";
      iArray[10][11] = "0|^9|标准承保|^4|次标准承保|^1|拒保^c|维持原条件承保";
      //iArray[10][5]="1";
      //iArray[10][6]="0|";
      iArray[10][15]="1";
      // iArray[10][16]="#1# and code in (#9#,#4#,#1#,#c#)";

   	  iArray[11]=new Array();
      iArray[11][0]="险种号";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=200;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

   	  iArray[12]=new Array();
      iArray[12][0]="主险险种号";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=200;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许 


 			iArray[13]=new Array();
			iArray[13][0]="币种";
			iArray[13][1]="60px";
			iArray[13][2]=100;
			iArray[13][3]=2;
			iArray[13][4]="currency";
				
      OldRislPlanGrid = new MulLineEnter( "document" , "OldRislPlanGrid" );
      //这些属性必须在loadMulLine前
      OldRislPlanGrid.mulLineCount = 5;
      OldRislPlanGrid.canSel = 1;
      OldRislPlanGrid.displayTitle = 1;
      OldRislPlanGrid.hiddenPlus = 1;
      OldRislPlanGrid.hiddenSubtraction = 1;
     
      OldRislPlanGrid.loadMulLine(iArray);
       //这些操作必须在loadMulLine后面
      //SpecGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>


