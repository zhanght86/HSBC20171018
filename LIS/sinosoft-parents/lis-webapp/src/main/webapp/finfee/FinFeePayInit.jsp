 <%
//程序名称：FinFeePayInit.jsp
//程序功能：
//创建日期：2002-06-27 08:49:52
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  GlobalInput tGI = new GlobalInput(); 
  String ManageCom = "";
  String Operator = "";
  String CurrentDate = "";
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  
  if(tGI==null)
  {
   Operator="unknown";
   ManageCom="unknown";
  }
  else //页面有效
  {
   Operator  = tGI.Operator ;  //保存登陆管理员账号
   ManageCom = tGI.ComCode  ; //保存登陆区站,ManageCom=内存中登陆区站代码
  }
  CurrentDate = PubFun.getCurrentDate();

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
    //document..ActuGetNo.value = '';
    fmSave.PolNo.value = '';
    fmSave.GetMoney.value = '';
    fmSave.PayMode.value = '';
    fmSave.PayModeName.value = '';
    fmSave.GetMoney.value='';
    fmSave.EnterAccDate.value='';                                       
    fmSave.SDrawer.value = '';
    fmSave.SDrawerID.value = '';   
    //fmSave..Drawer.value = '';
    //fmSave..DrawerID.value = ''; 
    fmSave.BankCode.value = '';
    //fmSave..BankAccName.value = '';   
    fmSave.BankAccNo.value = '';
    fmSave.BankName.value = ''; 
    fmSave.BankCode2.value = '';   
    fmSave.BankName2.value = '';
    fmSave.ChequeNo.value = ''; 
    fmSave.BankCode9.value='';
    fmSave.BankAccNo9.value='';
    fmSave.BankAccName9.value='';
    fmSave.BankName9.value='';
  }
  catch(ex)
  {
    alert("在FinFeePayInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在FinFeePayInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initPolicyCom()
{
	try
  { 
 //   document..PolicyCom.value = '<%=ManageCom%>';
  }
  catch(ex)
  {
    alert("在FinFeePayInit.jsp-->initPolicyCom函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initFinFeeQueryGrid();
   	initPolicyCom();
  }
  catch(re)
  {
    alert("FinFeePayInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

 var QueryLJAGetGrid ;
function initFinFeeQueryGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            		//列最大值
      iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="实付号码";   		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            		//列最大值
      iArray[1][3]=0;              		//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="其它号码";		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=60;            		//列最大值
      iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="号码类型";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=200;            	        //列最大值
      iArray[3][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="付费方式";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=200;            	        //列最大值
      iArray[4][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="币种";      	   		//列名
      iArray[5][1]="80px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="给付金额";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=200;            	        //列最大值
      iArray[6][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="到账日期";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            	        //列最大值
      iArray[7][3]=0;                   	//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="领取人";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=200;            	        //列最大值
      iArray[8][3]=0;                   	//是否允许输入,1表示允许，0表示不允许  

      iArray[9]=new Array();
      iArray[9][0]="领取人身份证";         		//列名
      iArray[9][1]="120px";            		//列宽
      iArray[9][2]=200;            	        //列最大值
      iArray[9][3]=0;                   	//是否允许输入,1表示允许，0表示不允许  
      
      iArray[10]=new Array();
      iArray[10][0]="银行编码";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=200;            	        //列最大值
      iArray[10][3]=3;                   	//是否允许输入,1表示允许，0表示不允许  
      
      iArray[11]=new Array();
      iArray[11][0]="银行账户";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=200;            	        //列最大值
      iArray[11][3]=3;                   	//是否允许输入,1表示允许，0表示不允许  
      
      iArray[12]=new Array();
      iArray[12][0]="银行户名";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=200;            	        //列最大值
      iArray[12][3]=3;                   	//是否允许输入,1表示允许，0表示不允许          
       
      QueryLJAGetGrid = new MulLineEnter( "fm" , "QueryLJAGetGrid" ); 
      //这些属性必须在loadMulLine前
      QueryLJAGetGrid.mulLineCount = 0;   
      QueryLJAGetGrid.displayTitle = 1;
      QueryLJAGetGrid.hiddenPlus = 1;
      QueryLJAGetGrid.hiddenSubtraction = 1;
      QueryLJAGetGrid.canSel = 1;
      QueryLJAGetGrid.loadMulLine(iArray);
      QueryLJAGetGrid.selBoxEventFuncName = "GetRecord";  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
