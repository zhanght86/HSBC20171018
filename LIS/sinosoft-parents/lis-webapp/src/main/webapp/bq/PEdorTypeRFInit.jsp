<%
//程序名称：PEdorTypeRFInit.jsp
//程序功能：
//创建日期：2003-01-08 
//创建人  ：Minim
//更新人  ：Nicholas
//更新日期：2005-07-09
%>                          

<script language="JavaScript">
	
function initInpBox()
{ 
  try
  {  
    Edorquery();  
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    //document.all('ContType').value ="1";
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("初始化界面错误!");
  }      
}


function initForm()
{
 
  try
  {
    initInpBox();
    initCustomerGrid();
    ShowCustomerInfo();
    initLoanGrid();
  }
  catch(re)
  {
    alert("初始化界面错误!");
  }
}


function QueryCustomerInfo()
{
	var tContNo=top.opener.document.all('ContNo').value;
	var strSQL;

	if(tContNo != null && tContNo != "")
	{
// 	  strSQL = "SELECT AppntName,(select CodeName from LDCode where CodeType='idtype' and Code=a.AppntIDType and rownum=1),AppntIDNo,"
// 	               + " InsuredName,(select CodeName from LDCode where CodeType='idtype' and Code=a.InsuredIDType and rownum=1),InsuredIDNo"
// 	        + " FROM LCCont a WHERE ContNo='" + tContNo + "'";
	  var sqlid1="PEdorTypeRFInputSql3";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("bq.PEdorTypeRFInputSql"); //指定使用的properties文件名
	  mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	  mySql1.addSubPara(tContNo);//指定传入的参数
	  strSQL=mySql1.getString();
	}
	else
	{
		alert('获取保单号失败！');
	}

	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);	

  if(!turnPage.strQueryResult)
  {
		alert("查询客户基本信息失败！");
	}
	else
	{
	  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	  try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //投保人姓名
	  try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //投保人证件类型
	  try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //投保人证件号码 
	  try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //被保人名称
	  try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //被保人证件类型
	  try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //被保人证件号码 
	  
  }
}


//查询后初始化
function afterCount(tFlagStr,tContent,tMulArray)
{
		if (tFlagStr == "Success")
		{
        displayMultiline(tMulArray,LoanGrid,turnPage);
    }
    else
    {
		  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;  
		  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");   
    }
     getLoanInfo();

}

function initCustomerGrid()
{                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="客户号码";
      iArray[1][1]="120px";
      iArray[1][2]=100;
      iArray[1][3]=0;
      
       iArray[2]=new Array();
      iArray[2][0]="角色";
      iArray[2][1]="80px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="姓名";
      iArray[3][1]="80px";
      iArray[3][2]=100;
      iArray[3][3]=0;
      
      iArray[4]=new Array();
      iArray[4][0]="证件类型";
      iArray[4][1]="80px";
      iArray[4][2]=100;
      iArray[4][3]=0;
      
      iArray[5]=new Array();
      iArray[5][0]="证件号码";
      iArray[5][1]="120px";
      iArray[5][2]=100;
      iArray[5][3]=0;        
      
      iArray[6]=new Array();
      iArray[6][0]="性别";
      iArray[6][1]="60px";
      iArray[6][2]=100;
      iArray[6][3]=0;
      
      iArray[7]=new Array();
      iArray[7][0]="出生日期";
      iArray[7][1]="100px";
      iArray[7][2]=100;
      iArray[7][3]=8;
 
      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" );   
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canSel=0;       
      CustomerGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      CustomerGrid.hiddenSubtraction=1;
      CustomerGrid.loadMulLine(iArray);
      CustomerGrid.selBoxEventFuncName ="" ; 
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initLoanGrid()
{                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="批单号";
      iArray[1][1]="100px";
      iArray[1][2]=100;
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="借款日期";
      iArray[2][1]="80px";
      iArray[2][2]=100;
      iArray[2][3]=8;
      
      iArray[3]=new Array();
      iArray[3][0]="最迟还款日期";
      iArray[3][1]="80px";
      iArray[3][2]=100;
      iArray[3][3]=8;
      
      iArray[4]=new Array();
      iArray[4][0]="借款金额";
      iArray[4][1]="80px";
      iArray[4][2]=100;
      iArray[4][3]=7; 
      iArray[4][23]="0";       
  
      iArray[5]=new Array();
      iArray[5][0]="借款利息";
      iArray[5][1]="80px";
      iArray[5][2]=100;
      iArray[5][3]=7;   
      iArray[5][23]="0";
      
          
      iArray[6]=new Array();
      iArray[6][0]="计息方式";
      iArray[6][1]="80px";
      iArray[6][2]=100;
      iArray[6][3]=0;
             
      iArray[7]=new Array();
      iArray[7][0]="本次还款";
      iArray[7][1]="100px";
      iArray[7][2]=100;
      iArray[7][3]=7;   
      iArray[7][23]="1";
   
      iArray[8]=new Array();
      iArray[8][0]="主险保单号";
      iArray[8][1]="0px";
      iArray[8][2]=100;
      iArray[8][3]=1;   
      
      iArray[9]=new Array();
      iArray[9][0]="币种";
      iArray[9][1]="60px";
      iArray[9][2]=100;
      iArray[9][3]=2;
      iArray[9][4]="currency";   

      LoanGrid = new MulLineEnter( "fm" , "LoanGrid" );  
      LoanGrid.displayTitle = 1;
      LoanGrid.canSel=0;       
      LoanGrid.canChk=1;
      LoanGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LoanGrid.hiddenSubtraction=1;
      LoanGrid.loadMulLine(iArray);
      LoanGrid.selBoxEventFuncName ="" ;          
      //这些操作必须在loadMulLine后面
      fm.action="./PEdorTypeRFCount.jsp";
     fm.submit();
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>