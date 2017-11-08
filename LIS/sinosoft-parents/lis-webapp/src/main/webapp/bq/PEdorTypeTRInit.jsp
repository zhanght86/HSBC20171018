<%
//程序名称：PEdorTypeTRInit.jsp
//程序功能：
//创建日期：2003-01-08 
//创建人  ：Minim
//更新记录：  更新人    更新日期     更新原因/内容
//						Lihs			2005-4-27 01:50下午  需求变更
//           Nicholas
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
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
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
  	//alert('1');
    initInpBox();
    //	alert('2');
    initSelBox();
    //	alert('3');
    initQueryPolInfo();  
    //	alert('4');
    QueryCustomerInfo();
    //	alert('5');
    initLoanGrid();
    //	alert('6');
  }
  catch(re)
  {
    alert("初始化界面错误!");
  }
}

function initQueryPolInfo()
{
  //查询险种基本信息
	var tContNo=document.all('ContNo').value;
	var strSQL=""
	if(tContNo!=null && tContNo !='')
	{
// 	  strSQL = "SELECT RiskCode,"
//                  + " (select RiskName from LMRisk where RiskCode=a.RiskCode),"
//        					 + " PayToDate,Mult,Amnt,Prem,PolNo"
// 					+ " FROM LCPol a"
// 					+ " WHERE a.Contno='" + tContNo + "' and polno=mainpolno";
	  var sqlid1="PEdorTypeTRInputSql2";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("bq.PEdorTypeTRInputSql"); //指定使用的properties文件名
	  mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	  mySql1.addSubPara(tContNo);//指定传入的参数
	  strSQL=mySql1.getString();
	}
	else
	{
		alert('传入的险种号错误！');
	}
	var arrSelected = new Array();		
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
  if(!turnPage.strQueryResult){
		alert("查询险种基本信息失败！");
	}
	else
	{
	  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
	  try {document.all('RiskCode').value = arrSelected[0][0];} catch(ex) { }; //险种代码
	  try {document.all('RiskName').value = arrSelected[0][1];} catch(ex) { }; //险种名称
	  try {document.all('PaytoDate').value = arrSelected[0][2];}catch(ex){}; //交至日期
	  try {document.all('Mult').value = arrSelected[0][3];} catch(ex) { }; //份数
	  try {document.all('Amnt').value = arrSelected[0][4];} catch(ex) { }; //保额
	  try {document.all('Prem').value = arrSelected[0][5];} catch(ex) { }; //保费
	  try {document.all('PolNo').value  = arrSelected[0][6];} catch(ex) { }; //保费
	  
  }
}

function QueryCustomerInfo()
{
  //查询客户基本信息
	var tContNo=document.all('ContNo').value;
	var strSQL="" 
	if(tContNo!=null && tContNo !='')
	{
// 	  strSQL = "SELECT distinct a.AppntName,"
//        										+ " (select CodeName from LDCode where CodeType='idtype' and Code=a.IDType),"
//        										+ " a.IDNo,"
//        										+ " b.Name,"
//        										+ " (select CodeName from LDCode where CodeType='idtype' and Code=b.IDType),"
//        										+ " b.IDNo"
//        		+ " FROM LCAppnt a,LCInsured b,LCPol c"
//        		+ " WHERE c.ContNo='" + tContNo + "' and a.ContNo=c.ContNo and b.ContNo=c.ContNo and b.InsuredNo=c.InsuredNo and c.polno=c.mainpolno";
	  var sqlid1="PEdorTypeTRInputSql3";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("bq.PEdorTypeTRInputSql"); //指定使用的properties文件名
	  mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	  mySql1.addSubPara(tContNo);//指定传入的参数
	  strSQL=mySql1.getString();
	}
	else
	{
		alert('传入的保单号错误！');
		return;
	}
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);	
  if(!turnPage.strQueryResult){
		alert("查询客户基本信息失败！");
		return;
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
	  
	  //showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
	  //showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
  }
}
//var LoanGrid;
// 信息列表的初始化
function initLoanGrid()
{
  var iArray = new Array();
    
  try
  {

    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          			  //列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
  
    iArray[1]=new Array();
    iArray[1][0]="垫交日期";    	    //列名1
    iArray[1][1]="150px";            	//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
  
    iArray[2]=new Array();
    iArray[2][0]="垫交保费";         	//列名2
    iArray[2][1]="120px";            	//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=7;              			//是否允许输入,1表示允许，0表示不允许
  	iArray[2][23]="0";
  
    iArray[3]=new Array();
    iArray[3][0]="垫交利息";         	//列名5
    iArray[3][1]="120px";            	//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=7;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
    iArray[3][23]="0";
  
  
    iArray[4]=new Array();
    iArray[4][0]="实际偿还垫交保费";         	//列名2
    iArray[4][1]="120px";            	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
  	iArray[4][23]="0";
  
    iArray[5]=new Array();
    iArray[5][0]="实际偿还垫交利息";         	//列名5
    iArray[5][1]="120px";            	//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
    iArray[5][23]="0";
    
    iArray[6]=new Array();
    iArray[6][0]="原批单号";					//记录原来的批单号，即LOLoan里的EdorNo，								
    iArray[6][1]="0px";								//存到LPReturnLoan里就是LoanNo
    iArray[6][2]=20;
    iArray[6][3]=3;
    
    iArray[7]=new Array();
    iArray[7][0]="主险险种号码";					//记录原来的批单号，即LOLoan里的EdorNo，	iArray[7][1]="120px";								//存到LPReturnLoan里就是LoanNo
    iArray[7][1]="0px";	
    iArray[7][2]=20;
    iArray[7][3]=0;
    
    iArray[8]=new Array();
		iArray[8][0]="币种";
		iArray[8][1]="60px";
		iArray[8][2]=100;
		iArray[8][3]=2;
		iArray[8][4]="currency";
  
    LoanGrid = new MulLineEnter( "fm" , "LoanGrid" ); 
    //这些属性必须在loadMulLine前
    //LoanGrid.mulLineCount = 6;   
    //alert('1111');
    LoanGrid.displayTitle = 1;
    
    LoanGrid.hiddenPlus = 1;
    LoanGrid.hiddenSubtraction = 1;
    LoanGrid.canChk=1;    
    LoanGrid.loadMulLine(iArray);  
    // alert('1111222');
    
    //后台查询，初始化界面MulLine用数组
		fm.action="./PEdorTypeTRCount.jsp";
		fm.submit();
  }
  catch(ex)
  {
    alert(ex);
  }
}

//查询后初始化MulLine
function afterCount(tFlagStr,tMulArray,tContent)
{
		//初始化MulLine
		var brr = new Array()
		//alert('afterCount');
		if (tFlagStr == "Success")
		{
    	displayMultiline(tMulArray,LoanGrid,turnPage);
    	//document.all('AllShouldPay').value = tWholeMoney; 
    	//document.all('AllShouldPayIntrest').value = tWholeMoneyIntrest; 

      //for (i=0; i<LoanGrid.mulLineCount; i++)
      //{  
      //var tStr="select ReturnMoney,returninterest from lpreturnloan where edorno='"+fm.EdorNo.value+"' and edortype='TR' and LoanNo='"+LoanGrid.getRowColData(i, 6)+"' order by loandate";    
      //brr = easyExecSql(tStr);   
      //if(brr!=null && brr!='')
      //{

        // LoanGrid.setRowColData(i,4,brr[0][0]);
         //LoanGrid.setRowColData(i,5,brr[0][1]);
      //}	
  
      //}  
      
    }
    else
    {
    	alert(tContent);
    }
    
    //add by jiaqiangli 2009-04-03 增加DivLockScreen遮罩层的处理 解锁
	unlockScreen('DivLockScreen');
	//add by jiaqiangli 2009-04-03 增加DivLockScreen遮罩层的处理 解锁
}

</script>