<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UnderwriteInit.jsp
//程序功能：个人人工核保
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
   
//tSql = " select HighAmntFlag('<%=request.getParameter("ContNo")%>') from dual";
tSql = " select '' from dual"; //临时不查
		  var HighAmntFlag=easyExecSql(tSql,1,0);
      
  try
  {                                  
	// 保单查询条件
    document.all('QPrtNo').value = '';
    document.all('QManageCom').value = '';
    document.all('QAppntName').value = '';
    document.all('QOperator').value = operator;
    document.all('QComCode').value = comcode;
    document.all('State').value = '1';
    document.all('QProposalNo').value = '';
    //document.all('QRiskVersion').value = '';
    codeSql="code";
    document.all('HighAmntFlag').value = HighAmntFlag;
  }
  catch(ex)
  {
    alert("在PManuUWInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 保单基本信息显示框的初始化（单记录部分）
function initPolBox()
{ 

  try
  {                                   
	// 保单查询条件
  document.all('ManageCom').value = '';
   //document.all('RiskCode').value = '';
   //document.all('RiskVersion').value = '';
   document.all('AppntNo').value = '';
   document.all('AppntName').value = '';
   //document.all('InsuredNo').value = '';
   //document.all('InsuredName').value = '';
   //document.all('InsuredSex').value = '';
   //document.all('Mult').value = '';
   //document.all('Prem').value = '';
   //document.all('Amnt').value = '';
   document.all('AppGrade').value = '';
   document.all('UWGrade').value = '';
   document.all('Operator').value = '<%= strOperator %>';
   document.all('UWPopedomCode').value = '';
   //document.all('UWDelay').value = '';
   document.all('UWIdea').value = '';
   document.all('uwState').value = '';
    codeSql="code";
 
  }
  catch(ex)
  {
    alert("在在PManuUWInit.jsp-->InitPolBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {

		initUW();
	
		
  }
  catch(re)
  {
    //alert("在PManuUWInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  
  }
}

// 保单信息列表的初始化
function initPolGrid()
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
      iArray[1][0]="印刷号";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保单号";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保人";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="代理人";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="管理机构";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][4]="station";              	        //是否引用代码:null||""为不引用
      iArray[5][5]="3";              	                //引用代码对应第几列，'|'为分割符
      iArray[5][9]="管理机构|code:station&NOTNULL";
      iArray[5][18]=250;
      iArray[5][19]= 0 ;

      iArray[6]=new Array();
      iArray[6][0]="工作流任务号";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流子任务号";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);     
      
      PolGrid. selBoxEventFuncName = "easyQueryAddClick";
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
        
      }
}

function initPolAddGrid()
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
      iArray[1][0]="客户号码";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[2]=new Array();
      iArray[2][0]="姓名";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
   
     	iArray[3]=new Array();
      iArray[3][0]="性别";         		//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
	  	iArray[3][4]="sex";              	        //是否引用代码:null||""为不引用
    	iArray[3][5]="3";              	                //引用代码对应第几列，'|'为分割符

  		iArray[4]=new Array();
      iArray[4][0]="年龄";         		//列名
      iArray[4][1]="40px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="与投保人关系";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
	  	iArray[5][4]="relation";              	        //是否引用代码:null||""为不引用
    	iArray[5][5]="3";              	                //引用代码对应第几列，'|'为分割符

     	iArray[6]=new Array();
      iArray[6][0]="与主被保人关系";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
	  	iArray[6][4]="relation";              	        //是否引用代码:null||""为不引用
    	iArray[6][5]="3";              	                //引用代码对应第几列，'|'为分割符

    	iArray[7]=new Array();
      iArray[7][0]="国籍";         		//列名
      iArray[7][1]="40px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
	  	iArray[7][4]="nativeplace";              	        //是否引用代码:null||""为不引用
    	iArray[7][5]="3";              	                //引用代码对应第几列，'|'为分割符



      iArray[8]=new Array();
      iArray[8][0]="证件类型";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="证件号码";         		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      PolAddGrid = new MulLineEnter( "fm" , "PolAddGrid" ); 
      //这些属性必须在loadMulLine前
      PolAddGrid.mulLineCount = 3;   
      PolAddGrid.displayTitle = 1;
      PolAddGrid.locked = 1;
      PolAddGrid.canSel = 1;
      PolAddGrid.hiddenPlus = 1;
      PolAddGrid.hiddenSubtraction = 1;
      PolAddGrid.loadMulLine(iArray);       
   //   PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
      PolAddGrid.selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数
      //这些操作必须在loadMulLine后面
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 自动核保信息提示
function initUWErrGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="投保单号";    	//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="被保险人客户号";    	//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="被保险人";    	//列名
      iArray[3][1]="50px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="险种编码";    	//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     
      iArray[5]=new Array();
      iArray[5][0]="险种名称";    	//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="自核未通过原因";    	//列名
      iArray[6][1]="300px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="核保日期";    	//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=8;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="审阅状态";    	//列名
      iArray[8][1]="50px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="投保单号";    	//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="流水号";    	//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
//合同号,被保人,险种编码,险种名称,核保信息,修改时间,是否审阅,
//投保单号,流水号,核保序列号,合同险种标记,投保单号

      iArray[11]=new Array();
      iArray[11][0]="核保顺序号";         			//列名
      iArray[11][1]="60px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="合同险种标记";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="投保单号";         		//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=100;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      UWErrGrid = new MulLineEnter( "fm" , "UWErrGrid" ); 
      //这些属性必须在loadMulLine前
      UWErrGrid.mulLineCount = 0;   
      UWErrGrid.displayTitle = 1;
      UWErrGrid.canChk = 1;
      UWErrGrid.daiplayCanChkAll = 1;
      UWErrGrid.locked = 1;
      UWErrGrid.hiddenPlus = 1;
      UWErrGrid.hiddenSubtraction = 1;
      UWErrGrid.loadMulLine(iArray);  
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

function initPolRiskGrid()
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
      iArray[1][0]="险种编码";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
   
     	iArray[3]=new Array();
      iArray[3][0]="保额";         		//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

  		iArray[4]=new Array();
      iArray[4][0]="份数";         		//列名
      iArray[4][1]="40px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="保险期间";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
	
     	iArray[6]=new Array();
      iArray[6][0]="交费期间";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

    	iArray[7]=new Array();
      iArray[7][0]="交费频率";         		//列名
      iArray[7][1]="40px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
	


      iArray[8]=new Array();
      iArray[8][0]="标准保费";         		//列名
      iArray[8][1]="40px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="职业加费";         		//列名
      iArray[9][1]="40px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="健康加费";         		//列名
      iArray[10][1]="40px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="险种号";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=200;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="其他加费";         		//列名
      iArray[12][1]="60px";            		//列宽
      iArray[12][2]=200;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="爱好加费";         		//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=200;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[14]=new Array();
      iArray[14][0]="币种";         		//列名
      iArray[14][1]="40px";            		        //列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
      iArray[14][4]="Currency";              	        //是否引用代码:null||""为不引用
      iArray[14][9]="币种|code:Currency";
      
      PolRiskGrid = new MulLineEnter( "fm" , "PolRiskGrid" ); 
      //这些属性必须在loadMulLine前
      PolRiskGrid.mulLineCount = 3;   
      PolRiskGrid.displayTitle = 1;
      PolRiskGrid.locked = 1;
      PolRiskGrid.canSel = 0;
      PolRiskGrid.hiddenPlus = 1;
      PolRiskGrid.hiddenSubtraction = 1;
      PolRiskGrid.loadMulLine(iArray);       
   //   PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
   //   PolRiskGrid.selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数
      //这些操作必须在loadMulLine后面
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
