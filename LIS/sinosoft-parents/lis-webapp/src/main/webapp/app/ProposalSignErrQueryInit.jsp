<%
//程序名称：ProposalSignErrQueryInit.jsp
//程序功能：查询个单签单失败原因
//创建日期：2007-03-09 11:42
//创建人  ：Fuqx
//更新记录：  更新人    更新日期     更新原因/内容
%>                         
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	String curDate = PubFun.getCurrentDate();
	//loggerDebug("ProposalSignErrQueryInit",curDate);
%> 

<script language="JavaScript">

function initForm()
{
  try
  {
    initInpBox();
    initCodeGrid();
  }
  catch(re)
  {
    alert("ProposalSignErrQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initCodeGrid()    							//函数名为init+MulLine的对象名ObjGrid
{                           						
	var iArray = new Array(); 						//数组放置各个列
	try
	{
    	iArray[0]=new Array();
    	iArray[0][0]="序号";         			//列名
    	iArray[0][1]="30px";         			//列宽
    	iArray[0][2]=100;         				//列最大值
    	iArray[0][3]=0;         					//1表示允许该列输入，0表示只读且不响应Tab键
    																		//2表示为容许输入且颜色加深

	    iArray[1]=new Array();
	    iArray[1][0]="印刷号";    			//列名（序号列，第1列）
	    iArray[1][1]="100px";  						//列宽
	    iArray[1][2]=30;      						//列最大值
	    iArray[1][3]=0;   								//1表示允许该列输入，0表示只读且不响应Tab键
	                   										

	    iArray[2]=new Array();          	
	    iArray[2][0]="险种名称";  				//列名（第2列）
	    iArray[2][1]="0px";  						//列宽
	    iArray[2][2]=10;        					//列最大值
	    iArray[2][3]=0;       						//是否允许输入,1表示允许，0表示不允许

	    iArray[3]=new Array();
	    iArray[3][0]="销售渠道";    			//列名（序号列，第1列）
	    iArray[3][1]="50px";  						//列宽
	    iArray[3][2]=30;      						//列最大值
	    iArray[3][3]=3;   								//1表示允许该列输入，0表示只读且不响应Tab键


	    iArray[4]=new Array();
	    iArray[4][0]="管理机构";    			//列名（序号列，第1列）
	    iArray[4][1]="65px";  						//列宽
	    iArray[4][2]=30;      						//列最大值
	    iArray[4][3]=3;   								//1表示允许该列输入，0表示只读且不响应Tab键	    
	    
	    iArray[5]=new Array();
	    iArray[5][0]="代理人姓名";    			//列名（序号列，第1列）
	    iArray[5][1]="0px";  						//列宽
	    iArray[5][2]=30;      						//列最大值
	    iArray[5][3]=3;   								//1表示允许该列输入，0表示只读且不响应Tab键

	                                    	
	    iArray[6]=new Array();          	
	    iArray[6][0]="投保人";  			//列名（第2列）
	    iArray[6][1]="80px";  						//列宽
	    iArray[6][2]=30;        					//列最大值
	    iArray[6][3]=0;       						//是否允许输入,1表示允许，0表示不允许


	    iArray[7]=new Array();          	
	    iArray[7][0]="被保人";  					//列名（第2列）
	    iArray[7][1]="70px";  						//列宽
	    iArray[7][2]=30;        					//列最大值
	    iArray[7][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																
	
	    iArray[8]=new Array();          	
	    iArray[8][0]="主险保额";  				//列名（第2列）
	    iArray[8][1]="60px";  						//列宽
	    iArray[8][2]=10;        					//列最大值
	    iArray[8][3]=0;       						//是否允许输入,1表示允许，0表示不允许

	    iArray[9]=new Array();          	
	    iArray[9][0]="总保费";  				//列名（第2列）
	    iArray[9][1]="60px";  						//列宽
	    iArray[9][2]=10;        					//列最大值
	    iArray[9][3]=0;       						//是否允许输入,1表示允许，0表示不允许

	    iArray[10]=new Array();          	
	    iArray[10][0]="业务员姓名";  				//列名（第2列）
	    iArray[10][1]="100px";  						//列宽
	    iArray[10][2]=10;        					//列最大值
	    iArray[10][3]=0;       						//是否允许输入,1表示允许，0表示不允许

	    iArray[11]=new Array();          	
	    iArray[11][0]="签单次数";  				//列名（第2列）
	    iArray[11][1]="50px";  						//列宽
	    iArray[11][2]=10;        					//列最大值
	    iArray[11][3]=0;    

	    iArray[12]=new Array();          	
	    iArray[12][0]="最后一次签单时间";  				//列名（第2列）
	    iArray[12][1]="100px";  						//列宽
	    iArray[12][2]=10;        					//列最大值
	    iArray[12][3]=0;    

	    iArray[13]=new Array();          	
	    iArray[13][0]="签单失败原因";  				//列名（第2列）
	    iArray[13][1]="250px";  						//列宽
	    iArray[13][2]=10;        					//列最大值
	    iArray[13][3]=0;    

	    iArray[14]=new Array();          	
	    iArray[14][0]="管理机构";  				//列名（第2列）
	    iArray[14][1]="100px";  						//列宽
	    iArray[14][2]=10;        					//列最大值
	    iArray[14][3]=0;    



	    //生成对象区，规则：对象名=new MulLineEnter(“表单名”,”对象名”); 
	    CodeGrid= new MulLineEnter( "fm" , "CodeGrid" ); 
	    //设置属性区 (需要其它特性，在此设置其它属性)
	    CodeGrid.mulLineCount = 5 ; 		//行属性：设置行数=10    
	    CodeGrid.displayTitle = 1;   		//标题属性：1显示标题(缺省值), 0隐藏标题        
	    //对象初始化区：调用对象初始化方法，属性必须在此前设置
	    CodeGrid.hiddenSubtraction=1;
	    CodeGrid.hiddenPlus=1;
	    //CodeGrid.canChk=1;
	    CodeGrid.loadMulLine(iArray);   
	}
	catch(ex)
	{ alert(ex); }
}

function initInpBox()
{ 
  try
  {
		document.all('ManageCom').value = '';
		//document.all('PrtNo').value = '';
		document.all('AgentCode').value = '';
		document.all('SaleChnl').value = '';
		document.all('InsuredName').value = '';
		document.all('RiskCode').value = '';
		document.all('StartDate').value = '<%=curDate%>';
		document.all('EndDate').value = '<%=curDate%>';
		document.all('ScanStartDate').value = '<%=curDate%>';
		document.all('ScanEndDate').value = '<%=curDate%>';
		document.all('SignStartDate').value = '<%=curDate%>';
		document.all('SignEndDate').value = '<%=curDate%>';
  }
  catch(ex)
  {
    alert("ProposalSignErrQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}    
    
</script>
