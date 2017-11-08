
<%
	//**************************************************************************************************
	//程序名称：CertifyInfoInit.jsp
	//程序功能：查询单证轨迹信息
	//创建日期： 2009-02-18
	//创建人  ： mw
	//更新记录：  更新人    更新日期     更新原因/内容
	//**************************************************************************************************
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
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
  	//fm.Operator.value = nullToEmpty("<%= tGI.Operator %>");
  	fm.MakeDateB.value =nullToEmpty("<%= strCurDate %>");
  	fm.MakeDateE.value = nullToEmpty("<%= strCurDate %>");
  }
  catch(ex)
  {
    alert("在CertifyInfoInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}
  
//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initForm()
{
  try
  {
    initInpBox();
    initCardInfoGrid();    
  }
  catch(re)
  {
    alert("CertifyInfoInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 单证信息列表的初始化
function initCardInfoGrid()
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
		iArray[1][0]="单证编码";         		 
		iArray[1][1]="60px";            		 
		iArray[1][2]=100;            			   
		iArray[1][3]=0;              			   
		
		iArray[2]=new Array();
		iArray[2][0]="单证名称";          		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=85;            			   
		iArray[2][3]=0;  
		
		iArray[3]=new Array();
		iArray[3][0]="发放者";          		 
		iArray[3][1]="100px";            		 
		iArray[3][2]=101;            			   
		iArray[3][3]=0;              			   
		
		iArray[4]=new Array();
		iArray[4][0]="接收者";        		   
		iArray[4][1]="100px";            		 
		iArray[4][2]=100;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="起始号";          		 
		iArray[5][1]="150px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;              			   
		
		iArray[6]=new Array();
		iArray[6][0]="终止号";         		   
		iArray[6][1]="150px";            		 
		iArray[6][2]=100;            			   
		iArray[6][3]=0;              			   
		
		iArray[7]=new Array();
		iArray[7][0]="数量";          		   
		iArray[7][1]="60px";            		 
		iArray[7][2]=60;            			   
		iArray[7][3]=0;              			   

		iArray[8]=new Array();
		iArray[8][0]="单证状态";         		   
		iArray[8][1]="100px";            		 
		iArray[8][2]=100;            			   
		iArray[8][3]=0;   
		
		iArray[9]=new Array();
		iArray[9][0]="经办人";         		   
		iArray[9][1]="60px";            		 
		iArray[9][2]=100;            			   
		iArray[9][3]=0;  
	
		iArray[10]=new Array();
		iArray[10][0]="操作员";         		   
		iArray[10][1]="60px";            		 
		iArray[10][2]=100;            			   
		iArray[10][3]=0;      
		
		iArray[11]=new Array();
		iArray[11][0]="操作日期";       		  //列名
		iArray[11][1]="80px";            		//列宽
		iArray[11][2]=100;            			  //列最大值
		iArray[11][3]=0;              			  //是否允许输入,1表示允许，0表示不允许

		iArray[12]=new Array();
		iArray[12][0]="操作时间";       		  //列名
		iArray[12][1]="80px";            		//列宽
		iArray[12][2]=100;            			  //列最大值
		iArray[12][3]=0;              			  //是否允许输入,1表示允许，0表示不允许 
		           
	    CardInfoGrid = new MulLineEnter( "document" , "CardInfoGrid" ); 
	    CardInfoGrid.mulLineCount = 5;   
	    CardInfoGrid.displayTitle = 1;
	    CardInfoGrid.locked = 1;
	    CardInfoGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
	    //CardInfoGrid.selBoxEventFuncName ="CardInfoGridClick"; //响应的函数名，不加扩号   
	    CardInfoGrid.hiddenPlus=1;
	    CardInfoGrid.hiddenSubtraction=1;
	    CardInfoGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
