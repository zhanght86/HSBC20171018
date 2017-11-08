 <%
//程序名称：SinCardTempFeeInit.jsp
//程序功能：
//创建日期：2008-03-07
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  String ManageCom = tGI.ComCode;
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
    Frame1.style.display='';
    Frame2.style.display=''; 

    document.all('ManageCom').value = '<%=ManageCom%>';
    document.all('PayDate').value = '<%=CurrentDate%>';
    document.all('AgentGroup').value = '';
    document.all('AgentCode').value = '';     
    document.all('AgentName').value = '';
    document.all('PolicyCom').value = '';
    document.all('TempFeeCount').value = 0;
    document.all('SumTempFee').value = 0.0 ; 

  }
  catch(ex)
  {
    alert("在SinCardTempFeeInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}


function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在SinCardTempFeeInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    initTempGrid(); 
    initTempToGrid(); 
    initTempClassGrid(); 
    initTempClassToGrid(); 
  }
  catch(re)
  {
    alert("SinCardTempFeeInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

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
      iArray[1][0]="交费方式";          		//列名
      iArray[1][1]="60px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的



      iArray[2]=new Array();
      iArray[2][0]="交费名称";         		        //列名
      iArray[2][1]="70px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="交费金额";      	   		//列名
      iArray[3][1]="90px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[4]=new Array();
      iArray[4][0]="票据号码";      	   		//列名
      iArray[4][1]="140px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[4][9]="票据号码|NULL|LEN>10";              //测试或运算

      iArray[5]=new Array();
      iArray[5][0]="支票日期";      	   		//列名
      iArray[5][1]="80px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="到账日期";      	   		//列名
      iArray[6][1]="60px";            			//列宽
      iArray[6][2]=10;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许


  
      iArray[7]=new Array();
      iArray[7][0]="开户银行";      	   		//列名
      iArray[7][1]="60px";            			//列宽
      iArray[7][2]=40;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[8]=new Array();
      iArray[8][0]="银行账号";      	   		//列名
      iArray[8][1]="140px";            		//列宽
      iArray[8][2]=40;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="户名";      	   		//列名
      iArray[9][1]="100px";            			//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
           
      iArray[10]=new Array();
      iArray[10][0]="收款银行";      	   		//列名
      iArray[10][1]="0px";            			//列宽
      iArray[10][2]=40;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[11]=new Array();
      iArray[11][0]="收款银行账号";      	   		//列名
      iArray[11][1]="140px";            		//列宽
      iArray[11][2]=40;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="收款银行户名";      	   		//列名
      iArray[12][1]="0px";            			//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[13]=new Array();
      iArray[13][0]="交费间隔";      	   		//列名
      iArray[13][1]="0px";            			//列宽
      iArray[13][2]=60;            			//列最大值
      iArray[13][3]=3;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[14]=new Array();
      iArray[14][0]="交费期限";      	   		//列名
      iArray[14][1]="0px";            			//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=3;              			//是否允许输入,1表示允许，0表示不允许         

      iArray[15]=new Array();
      iArray[15][0]="交费单位";      	   		//列名
      iArray[15][1]="0px";            			//列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=3;              			//是否允许输入,1表示允许，0表示不允许  
      
      iArray[16]=new Array();                                                  
      iArray[16][0]="证件类型";      	   		//列名                             
      iArray[16][1]="60px";            			//列宽                             
      iArray[16][2]=60;            			//列最大值                             
      iArray[16][3]=0;              			//是否允许输入,1表示允许，0表示不允许  

      
      iArray[17]=new Array();
      iArray[17][0]="证件号码";      	   		//列名
      iArray[17][1]="120px";            			//列宽
      iArray[17][2]=60;            			//列最大值
      iArray[17][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      TempClassGrid = new MulLineEnter( "fm" , "TempClassGrid" ); 
      //这些属性必须在loadMulLine前
      TempClassGrid.mulLineCount = 5;   
      TempClassGrid.displayTitle = 1;     
      TempClassGrid.hiddenPlus=1;
      TempClassGrid.canSel=1;
      TempClassGrid.selBoxEventFuncName="showMul";
      TempClassGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
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
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

   		iArray[1]=new Array();
      iArray[1][0]="产品代码";          		//列名
      iArray[1][1]="60px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=2;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[1][4]="portfolio";              	        //是否引用代码:null||""为不引用,使用产品组合的产品代码
      iArray[1][5]="1|2";              	                //引用代码对应第几列，'|'为分割符
      iArray[1][6]="0|1";              	        //上面的列中放置引用代码中第几位值


      iArray[2]=new Array();
      iArray[2][0]="产品名称";         			//列名
      iArray[2][1]="140px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="交费金额";      	   		//列名
      iArray[3][1]="80px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][9]="交费金额|NUM&NOTNULL";
      
      iArray[4]=new Array();
      iArray[4][0]="保单号码";      	   		//列名
      iArray[4][1]="0px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="缴费方式";      	   		//列名
      iArray[5][1]="60px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][10]="PayIntv";
      iArray[5][11]="0|^12|年缴|^0|趸缴|^6|半年缴^3|季缴^1|月缴^-1|不定期缴";
      
      iArray[6]=new Array();
      iArray[6][0]="缴费年期";      	   		//列名
      iArray[6][1]="60px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许
 
      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //这些属性必须在loadMulLine前
      TempGrid.mulLineCount = 5;   
      TempGrid.displayTitle = 1;
      TempGrid.locked=0;      
      TempGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}


//初始化保存暂交费分类数据的Grid
function initTempClassToGrid()
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
      iArray[1][0]="交费收据号";          		//列名
      iArray[1][1]="0px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="交费方式";          		//列名
      iArray[2][1]="60px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="交费日期";          		//列名
      iArray[3][1]="80px";      	      		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="交费金额";          		//列名
      iArray[4][1]="80px";      	      		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="到账日期";          		//列名
      iArray[5][1]="80px";      	      		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="管理机构";          		//列名
      iArray[6][1]="0px";      	      		//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="票据号码";          		//列名
      iArray[7][1]="100px";      	      		//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="支票日期";          		//列名
      iArray[8][1]="60px";      	      		//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="开户银行";      	   		//列名
      iArray[9][1]="60px";            			//列宽
      iArray[9][2]=40;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="银行账号";      	   		//列名
      iArray[10][1]="140px";            			//列宽
      iArray[10][2]=40;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="户名";      	   		//列名
      iArray[11][1]="60px";            			//列宽
      iArray[11][2]=10;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="收款银行";      	   		//列名
      iArray[12][1]="0px";            			//列宽
      iArray[12][2]=40;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[13]=new Array();
      iArray[13][0]="收款银行账号";      	   		//列名
      iArray[13][1]="140px";            		//列宽
      iArray[13][2]=40;            			//列最大值
      iArray[13][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[14]=new Array();
      iArray[14][0]="收款银行户名";      	   		//列名
      iArray[14][1]="0px";            			//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[15]=new Array();
      iArray[15][0]="缴费间隔";      	   		//列名
      iArray[15][1]="0px";            			//列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[16]=new Array();
      iArray[16][0]="缴费期间";      	   		//列名
      iArray[16][1]="0px";            			//列宽
      iArray[16][2]=60;            			//列最大值
      iArray[16][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[17]=new Array();
      iArray[17][0]="交费账户";      	   		//列名
      iArray[17][1]="0px";            			//列宽
      iArray[17][2]=60;            			//列最大值
      iArray[17][3]=3;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[18]=new Array();
      iArray[18][0]="证件类型";      	   		//列名
      iArray[18][1]="60px";            			//列宽
      iArray[18][2]=60;            			//列最大值
      iArray[18][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[19]=new Array();
      iArray[19][0]="证件号码";      	   		//列名
      iArray[19][1]="140px";            			//列宽
      iArray[19][2]=60;            			//列最大值
      iArray[19][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      TempClassToGrid = new MulLineEnter( "fmSave" , "TempClassToGrid" ); 
      //这些属性必须在loadMulLine前
      TempClassToGrid.mulLineCount = 5;   
      TempClassToGrid.displayTitle = 1;
      TempClassToGrid.locked=1;
      TempClassToGrid.hiddenPlus=1;
      TempClassToGrid.hiddenSubtraction=1;
      TempClassToGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }	
	
}

////初始化保存暂交费分类数据的Grid
function initTempToGrid()
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
      iArray[1][0]="交费收据号";          		//列名
      iArray[1][1]="0px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="暂收费类型";          		//列名
      iArray[2][1]="0px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;
            
      iArray[3]=new Array();
      iArray[3][0]="交费日期";      	   		//列名
      iArray[3][1]="80px";            			//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="交费金额";      	   		//列名
      iArray[4][1]="80px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="到账日期";          		//列名
      iArray[5][1]="0px";      	      		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="管理机构";      	   		//列名
      iArray[6][1]="100px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="产品代码";          		//列名
      iArray[7][1]="80px";      	      		//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="代理人组别";          		//列名
      iArray[8][1]="80px";      	      		//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="代理人编码";          		//列名
      iArray[9][1]="80px";      	      		//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="其它号码";      	   	        //列名
      iArray[10][1]="160px";            			//列宽
      iArray[10][2]=20;            			//列最大值
      iArray[10][3]=0;              			//隐藏

      iArray[11]=new Array();
      iArray[11][0]="其它号码类型";      	   	        //列名
      iArray[11][1]="100px";            			//列宽
      iArray[11][2]=20;            			//列最大值
      iArray[11][3]=0;              			//隐藏

      iArray[12]=new Array();
      iArray[12][0]="交费间隔";          		//列名
      iArray[12][1]="80px";      	      		//列宽
      iArray[12][2]=20;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="交费期间";      	   	        //列名
      iArray[13][1]="80px";            			//列宽
      iArray[13][2]=20;            			//列最大值
      iArray[13][3]=0;              			//隐藏

      iArray[14]=new Array();
      iArray[14][0]="缴费单位";      	   	        //列名
      iArray[14][1]="0px";            			//列宽
      iArray[14][2]=20;            			//列最大值
      iArray[14][3]=3;              			//隐藏

      iArray[15]=new Array();
      iArray[15][0]="投保人姓名";      	   	   //列名
      iArray[15][1]="100px";            			//列宽
      iArray[15][2]=20;            			//列最大值
      iArray[15][3]=3;              			//隐藏

      iArray[16]=new Array();
      iArray[16][0]="备注";      	   	        //列名
      iArray[16][1]="100px";            			//列宽
      iArray[16][2]=500;            			//列最大值
      iArray[16][3]=3;              			//隐藏
 
      TempToGrid = new MulLineEnter( "fmSave" , "TempToGrid" ); 
      //这些属性必须在loadMulLine前
      TempToGrid.mulLineCount = 5;   
      TempToGrid.displayTitle = 1;
      TempToGrid.locked=1;
      TempToGrid.hiddenPlus=1;
      TempToGrid.hiddenSubtraction=1;      
      TempToGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }	
}

function initDivPayMode()  //添加一笔后的信息栏初始化  
 {  
   var ii; 
   for(ii=1;ii<=6;ii++)
   {
   	if(ii==5)
   	break;  
     document.all("PayFee"+ii).value = '';
     var Mode = document.all("divPayMode"+ii).value;
     if ((Mode=='3')||(Mode=='2'))
     { 
       try
         {
	          document.all("ChequeNo"+ii).value ='';
	  	      document.all("ChequeDate"+ii).value ='';
	  	      document.all("BankCode"+ii).value ='';
	  	      document.all("BankCode"+ii+"Name").value ='';
	  	      document.all("BankAccNo"+ii).value ='';
	  	      document.all("AccName"+ii).value ='';
         }
       catch(ex)
         {
         }
  	 }
     if (Mode=='4')
      {
       try
         {
	  	      document.all("BankCode"+ii).value ='';
	  	      document.all("BankCode"+ii+"Name").value ='';
	  	      document.all("BankAccNo"+ii).value ='';
	  	      document.all("AccName"+ii).value ='';
	  	      document.all('IDType').value='';
	  	      document.all('IDNo').value='';
         }
       catch(ex)
         {
         }    
     }
     if (Mode=='5')
      {
	      try
	        {
		         fm.ChequeNo5.value ='';
		       //  fm.PolNo.value ='';
		         fm.OtherNo5.value ='';
		         fm.Drawer5.value ='';
	         
	        }
      catch(ex)
        {
        }
      }
      if (Mode=='6')
      {
       try
         {
	  	      document.all("BankCode"+ii).value ='';
	  	      document.all("BankCode"+ii+"Name").value ='';
	  	      document.all("BankAccNo"+ii).value ='';
	  	      document.all("AccName"+ii).value ='';

         }
       catch(ex)
         {
         }    
     }
    document.all("divPayMode"+ii).style.display="none";
   }
 }



  
</script>
