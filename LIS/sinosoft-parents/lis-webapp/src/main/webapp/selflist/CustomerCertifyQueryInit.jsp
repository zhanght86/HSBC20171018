<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
//程序名称：CustomerCertifyQuery.jsp
//程序功能：客户信息查询
//创建日期：2008-03-17
//创建人  ：zz
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	String strCurDate = PubFun.getCurrentDate();
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
                                      

function initForm()
{
  try
  {
	    document.all('CertifyNo').value = '';
        document.all('Name').value = '';
        document.all('Birthday').value = '';
        document.all('CustomerNo').value = '';
        document.all('IDType').value = '';
        document.all('IDNo').value = '';
        document.all('Sex').value = '';
        
        
		initCardInfo();
  }
  catch(re)
  {
    alert("CustomerCertifyQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initCardInfo()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="40px";            		//列宽
		iArray[0][2]=10;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[1]=new Array();
		iArray[1][0]="卡号";         		  //列名
		iArray[1][1]="165px";            		//列宽
		iArray[1][2]=100;            			  //列最大值
		iArray[1][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[2]=new Array();
		iArray[2][0]="险种名称";          		//列名
		iArray[2][1]="190px";            		//列宽
		iArray[2][2]=100;            			  //列最大值
		iArray[2][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[3]=new Array();
		iArray[3][0]="生效日期"; //列名
		iArray[3][1]="85px";            		//列宽
		iArray[3][2]=80;            			  //列最大值
		iArray[3][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[4]=new Array();
		iArray[4][0]="责任终止日期";            //列名
		iArray[4][1]="85px";            		//列宽
		iArray[4][2]=80;            		 	  //列最大值
		iArray[4][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[5]=new Array();
		iArray[5][0]="激活日期";            //列名
		iArray[5][1]="85px";            		//列宽
		iArray[5][2]=80;            		 	  //列最大值
		iArray[5][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[6]=new Array();
		iArray[6][0]="卡单状态";         		//列名
		iArray[6][1]="60px";            		//列宽
		iArray[6][2]=80;            			  //列最大值
		iArray[6][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[7]=new Array();
		iArray[7][0]="客户姓名";            //列名
		iArray[7][1]="70px";            		//列宽
		iArray[7][2]=80;            			  //列最大值
		iArray[7][3]=0;              			  //是否允许输入,1表示允许，0表示不允许

		iArray[8]=new Array();
		iArray[8][0]="客户号";         		    //列名
		iArray[8][1]="85px";            		//列宽
		iArray[8][2]=100;            			  //列最大值
		iArray[8][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
	    iArray[9]=new Array();
		iArray[9][0]="出生日期";          		  //列名
		iArray[9][1]="85px";            		//列宽
		iArray[9][2]=60;            			  //列最大值
		iArray[9][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[10]=new Array();
		iArray[10][0]="证件类型";         		    //列名
		iArray[10][1]="70px";            		//列宽
		iArray[10][2]=100;            			  //列最大值
		iArray[10][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
	    iArray[11]=new Array();
		iArray[11][0]="证件号";          		  //列名
		iArray[11][1]="70px";            		//列宽
		iArray[11][2]=60;            			  //列最大值
		iArray[11][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[12]=new Array();
		iArray[12][0]="联系电话";          		  //列名
		iArray[12][1]="70px";            		//列宽
		iArray[12][2]=60;            			  //列最大值
		iArray[12][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[13]=new Array();
		iArray[13][0]="联系地址";         		    //列名
		iArray[13][1]="70px";            		//列宽
		iArray[13][2]=100;            			  //列最大值
		iArray[13][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
	    iArray[14]=new Array();
		iArray[14][0]="邮政编码";          		  //列名
		iArray[14][1]="70px";            		//列宽
		iArray[14][2]=60;            			  //列最大值
		iArray[14][3]=0;              			  //是否允许输入,1表示允许，0表示不允许

		iArray[15]=new Array();
		iArray[15][0]="职业名称";          		  //列名
		iArray[15][1]="100px";            		//列宽
		iArray[15][2]=60;            			  //列最大值
		iArray[15][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[16]=new Array();
		iArray[16][0]="投\被保人标志";          		  //列名
		iArray[16][1]="80px";            		//列宽
		iArray[16][2]=80;            			  //列最大值
		iArray[16][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		CardInfo = new MulLineEnter("fm" , "CardInfo"); 
		// 这些属性必须在loadMulLine前
		CardInfo.mulLineCount = 5;   
		CardInfo.displayTitle = 1;
	    CardInfo.hiddenSubtraction=1;
	    CardInfo.hiddenPlus=1;
		CardInfo.locked = 1;
		CardInfo.canSel = 1;
		CardInfo.loadMulLine(iArray);  
		//这些操作必须在loadMulLine后面
		//CardInfo.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
