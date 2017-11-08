<%
//程序名称：ReceiveAccountInit.jsp
//程序功能： 统计管理—承保统计—保费3日到账率
//创建日期：2010-05-26
//创建人  ：hanbin
//更新记录：  更新人    更新日期     更新原因/
%>

<%@page import="com.sinosoft.lis.pubfun.PubFun"%><script language="JavaScript">
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    PubFun tpubFun = new PubFun();
	String strCurDay = tpubFun.getCurrentDate();
%>
function initForm()
{
  try
  {
    initInpBox();
    initCodeGrid();
  }
  catch(re)
  {
    alert("ReceiveAccountInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initCodeGrid()    							//函数名为init+MulLine的对象名ObjGrid
{                           						
	var iArray = new Array(); 						//数组放置各个列
	try
	{
    	iArray[0]=new Array();
    	iArray[0][0]="序号";         			//列名
    	iArray[0][1]="40px";         			//列宽
    	iArray[0][2]=30;           				//列最大值
    	iArray[0][3]=0;         					//1表示允许该列输入，0表示只读且不响应Tab键
    																		//2表示为容许输入且颜色加深

	    iArray[1]=new Array();
	    iArray[1][0]="管理机构";  			//列名（序号列，第1列）
	    iArray[1][1]="100px";  						//列宽
	    iArray[1][2]=10;      						//列最大值
	    iArray[1][3]=0;   								//1表示允许该列输入，0表示只读且不响应Tab键
	                   										//2表示为容许输入且颜色加深.
	                                    	
	    iArray[2]=new Array();          	
	    iArray[2][0]="保费3日到账件数";				//列名（第2列）
	    iArray[2][1]="200px";  						//列宽
	    iArray[2][2]=20;        					//列最大值
	    iArray[2][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置
	    iArray[3]=new Array();          	
	    iArray[3][0]="总件数";					//列名（第2列）
	    iArray[3][1]="200px";  						//列宽
	    iArray[3][2]=10;        					//列最大值
	    iArray[3][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置
	       																
	    iArray[4]=new Array();          	
	    iArray[4][0]="保费3日到账率";					//列名（第2列）
	    iArray[4][1]="200px";  						//列宽
	    iArray[4][2]=10;        					//列最大值
	    iArray[4][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置	       																
	
	    //生成对象区，规则：对象名=new MulLineEnter(“表单名”,”对象名”); 
	    CodeGrid= new MulLineEnter( "document" , "CodeGrid" ); 
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
		document.all('ManageCom').value = comCode;
		//document.all('StartUWDate').value ="<%=strCurDay%>";
		//document.all('EndUWDate').value ="<%=strCurDay%>";
		//document.all('takebackdate').value ="<%=strCurDay%>";
		//document.all('takebackdatebegin').value ="<%=strCurDay%>";
		//document.all('takebacktime').value = '23:59:59';

  }
  catch(ex)
  {
    alert("ReceiveAccountInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}    
    
</script>