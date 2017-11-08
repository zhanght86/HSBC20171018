
<%
	//**************************************************************************************************
	//程序名称：LDUserInfoInputInit.jsp
	//程序功能：单证人员信息查询
	//创建日期： 2009-02-18
	//创建人  ： mw
	//更新记录：  更新人    更新日期     更新原因/内容
	//**************************************************************************************************
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
<%
String strCurDate = PubFun.getCurrentDate();
%>

// 输入框的初始化（单记录部分）
function initInpBox()
{
  try
  {
  	fm.reset();
	fm.ManageCom.value = ''
  	fm.grade.value = '';
  	fm.validstartdate.value = '';
  }
  catch(ex)
  {
    alert("在CertifyInfoInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}


function initForm()
{
  try
  {
    initInpBox();
    initUserInfoGrid();    
  }
  catch(re)
  {
    alert("CertifyInfoInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 单证信息列表的初始化
function initUserInfoGrid()
{                               
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=10;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
		
		iArray[1]=new Array();
		iArray[1][0]="机构编码";         		 
		iArray[1][1]="40px";            		 
		iArray[1][2]=100;            			   
		iArray[1][3]=0;              			   
		
		iArray[2]=new Array();
		iArray[2][0]="机构名称";          		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=85;            			   
		iArray[2][3]=0;  
		
		iArray[3]=new Array();
		iArray[3][0]="工号";          		 
		iArray[3][1]="60px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;              			   
		
		iArray[4]=new Array();
		iArray[4][0]="姓名";        		   
		iArray[4][1]="60px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="授权时间";          		 
		iArray[5][1]="60px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;    
		
		iArray[6]=new Array();
		iArray[6][0]="离岗时间";          		 
		iArray[6][1]="60px";            		 
		iArray[6][2]=100;            		 	   
		iArray[6][3]=0;    
		
	    UserInfoGrid = new MulLineEnter( "document" , "UserInfoGrid" ); 
	    UserInfoGrid.mulLineCount = 5;   
	    UserInfoGrid.displayTitle = 1;
	    UserInfoGrid.locked = 1;
	    UserInfoGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
	    //UserInfoGrid.selBoxEventFuncName ="CardInfoGridClick"; //响应的函数名，不加扩号   
	    UserInfoGrid.hiddenPlus=1;
	    UserInfoGrid.hiddenSubtraction=1;
	    UserInfoGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
