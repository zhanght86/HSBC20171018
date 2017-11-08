 <%
//程序名称：TempFeeInit.jsp
//程序功能：
//创建日期：2002-06-27 08:49:52
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
  GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI");
%>  
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  
  try
  { 
    document.all('TempFeeNo').value='';
    document.all('bComCode').value=  "<%=tGI.ComCode%>";
		if(document.all('bComCode').value.trim().length>4)
		{
			document.all('bComCode').value = document.all('bComCode').value.substring(0,4);
	  }
              
  }
  catch(ex)
  {
    alert("在TempFeeInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();  
    initTempGrid();  
    initTempClassGrid(); 
  }
  catch(re)
  {
    alert("TempFeeInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 暂收费信息列表的初始化
function initTempGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="暂收据号";      	   		//列名
      iArray[1][1]="120px";            			//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="险种编码";          		//列名
      iArray[2][1]="80px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=2;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[2][4]="RiskCodeFin";              	        //是否引用代码:null||""为不引用
      iArray[2][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[2][18]=300;
      //iArray[2][19]=1;


      iArray[3]=new Array();
      iArray[3][0]="交费金额";      	   		//列名
      iArray[3][1]="80px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][9]="交费金额|NUM&NOTNULL";
      
      iArray[4]=new Array();
      iArray[4][0]="交费日期";      	   		//列名
      iArray[4][1]="0px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="交费日期|DATE|NULL";
      //iArray[4][14]="<%=CurrentDate%>";
      
      iArray[5]=new Array();
      iArray[5][0]="投保单号印刷号/保单号";      	   		//列名
      iArray[5][1]="140px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=1; 

      iArray[6]=new Array();
      iArray[6][0]="缴费方式";      	   		//列名
      iArray[6][1]="80px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=2;                   //是否允许输入,1表示允许，0表示不允许      
      iArray[6][10]="payintv";
      //iArray[6][11]="0|^0|趸交^1|月缴^12|年缴";     
      iArray[6][11]="0|^-1|不定期缴^0|趸交^1|月缴^3|季缴^6|半年缴^12|年缴";  
      
      
      iArray[7]=new Array();
      iArray[7][0]="缴费年期";      	   		//列名
      iArray[7][1]="80px";            			//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许           
      iArray[7][4]="standyear";
      iArray[7][15]="riskcode";
      iArray[7][17]="2";


      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //这些属性必须在loadMulLine前
      TempGrid.mulLineCount = 1;   
      TempGrid.displayTitle = 1;
      //TempGrid.locked=1;      
      TempGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 暂收费分类列表的初始化
function initTempClassGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="暂收据号";      	   		//列名
      iArray[1][1]="120px";            			//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="交费方式";          		//列名
      iArray[2][1]="40px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=2;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[2][4]="chargepaymode";              	        //是否引用代码:null||""为不引用
//      iArray[2][5]="1|2";              	                //引用代码对应第几列，'|'为分割符
//      iArray[2][6]="0|1";              	        //上面的列中放置引用代码中第几位值
      //iArray[2][9]="交费方式|code:paymode&NOTNULL";


      iArray[3]=new Array();
      iArray[3][0]="交费金额";      	   		//列名
      iArray[3][1]="80px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][9]="交费金额|NUM&NOTNULL";
      
      iArray[4]=new Array();
      iArray[4][0]="交费日期";      	   		//列名
      iArray[4][1]="0px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="交费日期|DATE|NULL";
      //iArray[4][14]="<%=CurrentDate%>";
      
      
      iArray[5]=new Array();
      iArray[5][0]="票据号码";      	   		//列名
      iArray[5][1]="80px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[5][9]="票据号码|NULL|LEN>10";              //测试或运算

      iArray[6]=new Array();
      iArray[6][0]="到帐日期";      	   		//列名
      iArray[6][1]="80px";            			//列宽
      iArray[6][2]=10;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="开户银行";      	   		//列名
      iArray[7][1]="50px";            			//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[7][4]="bank";              	        //是否引用代码:null||""为不引用
      iArray[7][9]="开户银行|code:bank";
      //iArray[7][18]=150;
      iArray[7][15]="1";
      iArray[7][16]= "#1#  and comcode like #"+document.all('bComCode').value+"%#";
      
      iArray[8]=new Array();
      iArray[8][0]="银行账号";      	   		//列名
      iArray[8][1]="100px";            			//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="户名";      	   		//列名
      iArray[9][1]="60px";            			//列宽
      iArray[9][2]=10;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="投保单号";      	   		//列名
      iArray[10][1]="0px";            			//列宽
      iArray[10][2]=20;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="其他号码类型";      	   		//列名
      iArray[11][1]="0px";            			//列宽
      iArray[11][2]=10;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="证件类型";      	   		//列名
      iArray[12][1]="40px";            			//列宽
      iArray[12][2]=10;            			//列最大值
      iArray[12][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[12][4]="IDType";
      iArray[12][9]="证件类型|code:IDType";
      iArray[12][18]=300;
      
      iArray[13]=new Array();
      iArray[13][0]="证件号码";      	   		//列名
      iArray[13][1]="110px";            			//列宽
      iArray[13][2]=20;            			//列最大值
      iArray[13][3]=1;              			//是否允许输入,1表示允许，0表示不允许      
           
      TempClassToGrid = new MulLineEnter( "fm" , "TempClassToGrid" ); 
      //这些属性必须在loadMulLine前
      TempClassToGrid.mulLineCount = 1;   
      TempClassToGrid.displayTitle = 1;     
      TempClassToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
  }

  
</script>
