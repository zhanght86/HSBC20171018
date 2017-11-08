<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UnderwriteInit.jsp
//程序功能：个人核保信息查询
//创建日期：2006-09-20 11:32
//创建人  ：Fuqx
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
  try
  {                                  
	// 保单查询条件
    document.all('QPrtNo').value = '';
    document.all('QManageCom').value = '';
    document.all('QAppntName').value = '';
    document.all('QOperator').value = operator;
    document.all('QComCode').value = comcode;
    document.all('QState').value = '0';
    document.all('QProposalNo').value = '';
    //document.all('QRiskVersion').value = '';
    
  }
  catch(ex)
  {
    alert("在PManuUWInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 输入框的初始化（单记录部分）
function initInpBox1()
{ 

  try
  {                           
	// 保单查询条件
    document.all('PolNo').value = '';
    //document.all('State').value = '';
    document.all('AppntNo1').value = '';
    document.all('AppntName1').value = '';
    document.all('InsuredNo1').value = '';
    document.all('InsuredName1').value = '';
    document.all('AgentCode').value = '';
    //document.all('RiskCode').value = operator;
    //document.all('ManageCom').value = comcode;
    //合同信息
    document.all('CPrtNo').value = '';
    document.all('CManageCom').value = '';
    document.all('CSaleChnl').value = '';
    document.all('CAgentCode').value = '';
    document.all('CShouldPayPrem').value = '';
    document.all('CFillPrem').value = '';
    document.all('CHighAmntFlag').value = '';
    document.all('CBlacklistFlag').value = '';
    document.all('CVIPValue').value = '';
    document.all('CRemark').value = '';
    document.all('ReviseReason').value = '';
    document.all('UpperUwReason').value = '';
    document.all('UpperUwUser').value = '';
    //投保人信息
    document.all('AppntName').value = '';
    document.all('AppntSex').value = '';
    document.all('AppntBirthday').value = '';
    document.all('OccupationCode').value = '';
    document.all('OccupationType').value = '';
    document.all('income').value = '';
    document.all('Stature').value = '';
    document.all('Weight').value = '';
    document.all('BMI').value = '';
    document.all('NativePlace').value = '';
    document.all('AppntSumLifeAmnt').value = '';
    document.all('AppntSumHealthAmnt').value = '';
    document.all('AppntMedicalAmnt').value = '';
    document.all('AppntSumLifeAmnt').value = '';
    document.all('AppntSumHealthAmnt').value = '';
    codeSql="code";
    document.all('Button1').disabled='true';
    document.all('Button2').disabled='true';
    document.all('Button3').disabled='true';
    //document.all('Button4').disabled='true';
    document.all('Button5').disabled='true';
    document.all('Button7').disabled='true';
    document.all('Button9').disabled='true';
  }
  catch(ex)
  {
    alert("在UnderwriteInit.jsp-->InitInpBox1函数中发生异常:初始化界面错误!");
  }      
}

// 保单基本信息显示框的初始化（单记录部分）
function initPolBox()
{ 

  try
  {                                   
	// 保单查询条件
    document.all('ProposalNo').value = '';
    document.all('PolNo').value = '';
    document.all('ManageCom').value = '';
    document.all('RiskCode').value = '';
    document.all('RiskVersion').value = '';
    document.all('AppntNo').value = '';
    document.all('AppntName').value = '';
    document.all('InsuredNo').value = '';
    document.all('InsuredName').value = '';
    document.all('InsuredSex').value = '';
    document.all('Mult').value = '';
    document.all('Prem').value = '';
    document.all('Amnt').value = '';
    document.all('AppGrade').value = '';
    document.all('UWGrade').value = '';
    document.all('Operator').value = '<%= strOperator %>';
   
    codeSql="code";
  }
  catch(ex)
  {
    alert("在UnderwriteInit.jsp-->InitPolBox2函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    //initInpBox();
    //initPolBox();    
    initPolGrid();   
    //easyQueryClick() ;
    //initPolAddGrid();
    //initPolInsuredGrid();
    //initPolRiskGrid();
    
  }
  catch(re)
  {
    alert("UnderwriteInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[0][1]="30px";            		        //列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保单号";         		//列名
      iArray[1][1]="140px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="印刷号";         		//列名
      iArray[2][1]="140px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保人姓名";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="险种名称";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="合同状态";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=80;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

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
            			
      iArray[8]=new Array();
      iArray[8][0]="合同号";         		//列名
      iArray[8][1]="0px";                      //列宽
      iArray[8][2]=100;                         //列最大值  
      iArray[8][3]=0;                           //是否允许输入,1表示允许，0表示不允许 
      
      iArray[9]=new Array();
      iArray[9][0]="核保状态";         		//列名
      iArray[9][1]="0px";                      //列宽
      iArray[9][2]=100;                         //列最大值  
      iArray[9][3]=0;                           //是否允许输入,1表示允许，0表示不允许 
 
      iArray[10]=new Array();
      iArray[10][0]="合同号(contno)";         		//列名
      iArray[10][1]="0px";                      //列宽
      iArray[10][2]=100;                         //列最大值  
      iArray[10][3]=0;                           //是否允许输入,1表示允许，0表示不允许 

     
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      //PolGrid.mulLineCount = 3;   
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);      
      
      PolGrid.selBoxEventFuncName = "easyQueryAddClick";
      
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
      PolAddGrid.mulLineCount = 5;   
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

function initPolInsuredGrid()
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

      
      PolInsuredGrid = new MulLineEnter( "fm" , "PolInsuredGrid" ); 
      //这些属性必须在loadMulLine前
      PolInsuredGrid.mulLineCount = 5;   
      PolInsuredGrid.displayTitle = 1;
      PolInsuredGrid.locked = 1;
      PolInsuredGrid.canSel = 1;
      PolInsuredGrid.hiddenPlus = 1;
      PolInsuredGrid.hiddenSubtraction = 1;
      PolInsuredGrid.loadMulLine(iArray);       
   //   PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
      PolInsuredGrid.selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数
      //这些操作必须在loadMulLine后面
      //PolAddGrid.setRowColData(1,1,"asdf");
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

      PolRiskGrid = new MulLineEnter( "fm" , "PolRiskGrid" ); 
      //这些属性必须在loadMulLine前
      PolRiskGrid.mulLineCount = 5;   
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
