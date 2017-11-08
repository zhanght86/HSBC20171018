<%
//程序名称：PEdorInputInit.jsp
//程序功能：
//创建日期：2003-01-08 
//创建人  ：Minim
//更新记录：  更新人    更新日期     更新原因/内容 
//						Lihs			2005-4-27 01:50下午  需求变更
%>                          

<script language="JavaScript">  
function initInpBox()
{ 
  try
  {   
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value; 
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value; 
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value; 
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;  
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('TempSaveField').value = document.all('InsuYear').value + document.all('GetDutyKind').value;
    document.all('IninSuccessFlag').value = "0";
    //document.all('ContType').value ="1";
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
    initInpBox();
    initSelBox(); 
    QueryCustomerInfo();
    initQueryPolInfo();
    //initLCInsuredGrid();
    //QueryEdorInfo();
    queryNewData();
  }
 catch(re)
  {
    alert("PEdorTypePUInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initQueryPolInfo()
{
	var tPolNo=top.opener.document.all('PolNo').value;
	var strSQL=""
	if(tPolNo!=null || tPolNo !='')
	{
	  strSQL = "SELECT M.RISKCODE,M.RISKNAME,C.APPNTNO,C.APPNTNAME,C.INSUREDNO,C.INSUREDNAME,C.MULT,C.PREM,C.AMNT,C.PAYTODATE FROM LCPOL C,LMRISK M WHERE C.RISKCODE = M.RISKCODE AND "
							+"POLNO='"+tPolNo+"'";
	}
	else
	{
		alert('没有相应的险种代码！');
	}
	var arrSelected = new Array();		
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
  if(!turnPage.strQueryResult)
  {
		alert("查询失败！");
	}
	else
	{
	  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
	  try {document.all('RiskCode').value = arrSelected[0][0];} catch(ex) { }; //险种代码
	  try {document.all('RiskName').value = arrSelected[0][1];} catch(ex) { }; //险种名称
	  //try {document.all('AppntNo').value = arrSelected[0][2];} catch(ex) { }; //投保人号码
	  try {document.all('AppntName').value = arrSelected[0][3];} catch(ex) { }; //投保人名称
	  //try {document.all('InsuredNo').value = arrSelected[0][4];} catch(ex) { }; //被保人号码
	  //try {document.all('InsuredName').value = arrSelected[0][5];} catch(ex) { }; //被保人名称
	  try {document.all('Mult').value = arrSelected[0][6];} catch(ex) { }; //份数
	  try {document.all('Prem').value = arrSelected[0][7];} catch(ex) { }; //保费
	  try {document.all('Amnt').value = arrSelected[0][8];} catch(ex) { }; //保额
	  try {document.all('PaytoDate').value = arrSelected[0][9];}catch(ex){}; //交至日期
	  //根据险种判断该显示的信息，针对个别险种，先在界面里固定写
	  strSQL = " select distinct 1 from Lmrisksort where riskcode = '" + document.all('RiskCode').value + "' and risksorttype='31' ";
  
  	var insu = easyExecSql(strSQL);
		
		if(insu!=null)
	  
	  {
	   	showDiv(divIntv,"true");
	  	document.all('InsuYear').verify = "保险期间|notnull&num";
	  	document.all('SpecialRiskFlag').value = "Y";
	  	
	  }
	 	strSQL = " select distinct 1 from Lmrisksort where riskcode = '" + document.all('RiskCode').value + "' and risksorttype='32' ";
  
  	var gdkind = easyExecSql(strSQL);
  	
	  if(gdkind!=null)
	  
	  {
	  	showDiv(divGetMode,"true");
	  	document.all('GetDutyKind').verify = "领取方式|notnull&num";
	  	document.all('SpecialRiskFlag').value = "Y";
	  }
  }
}

 function QueryCustomerInfo()
{
	var tContNo=top.opener.document.all('ContNo').value;
	var strSQL=""
	//alert("------"+tContNo+"---------");
	if(tContNo!=null || tContNo !='')
	  {
	  strSQL = "SELECT APPNTNAME,APPNTIDTYPE,APPNTIDNO,INSUREDNAME,INSUREDIDTYPE,INSUREDIDNO FROM LCCONT WHERE 1=1 AND "
							+"CONTNO='"+tContNo+"'";
	//alert("-----------"+strSQL+"------------");
	}
	else
	{
		alert('没有客户信息！');
	}
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);	
	//arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0); 
  if(!turnPage.strQueryResult){
		alert("查询失败！");
	}
	else
	{
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //投保人姓名
  try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //投保人证件类型
  showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
  try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //投保人证件号码 
  try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //被保人名称
  try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //被保人证件类型
  showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
  try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //被保人证件号码 
  
  //showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
  //showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
  }
}
//险种信息列表
function initLCInsuredGrid()
{
    var iArray = new Array();
    
    try
    {

      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种代码";					//列名1
      iArray[1][1]="50px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";         			//列名2
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="基本保额/份数";         		//列名8
      iArray[3][1]="50px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="累积红利保额";         		//列名8
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="终了红利";         		//列名8
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="保费标准";         		//列名8
      iArray[6][1]="50px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;
      
      iArray[7]=new Array();
      iArray[7][0]="交费对应日";         		//列名8
      iArray[7][1]="50px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;

      LCInsuredGrid = new MulLineEnter( "fm" , "LCInsuredGrid" ); 
      //这些属性必须在loadMulLine前
      //LCInsuredGrid.mulLineCount = 4;   
      //LCInsuredGrid.displayTitle = 1;
      //LCInsuredGrid.canSel=0;
      //LCInsuredGrid.hiddenPlus = 1; 
      //LCInsuredGrid.hiddenSubtraction = 1;
      //LCInsuredGrid.selBoxEventFuncName ="";
      //LCInsuredGrid.loadMulLine(iArray);  
      //LCInsuredGrid.detailInfo="单击显示详细信息";
	    LCInsuredGrid.displayTitle = 1;
	    LCInsuredGrid.hiddenPlus = 1;
	    LCInsuredGrid.hiddenSubtraction = 1;
	    LCInsuredGrid.loadMulLine(iArray);  

      var strSQL ="select RiskCode,(select RiskName from LMRisk where LMRisk.RiskCode = LCPol.RiskCode),Amnt,'','',Prem from LCPol where ContNo='"+document.all('ContNo').value+"'";
      easyQueryVer3Modal(strSQL,LCInsuredGrid);
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initPolGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=10;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="险种号";
        iArray[1][1]="140px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="险种代码";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="险种名称";
        iArray[3][1]="170px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="缴清后险种代码";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;        
        
        iArray[5]=new Array();
        iArray[5][0]="缴清后险种名称";
        iArray[5][1]="170px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="累计红利保额";
        iArray[6][1]="0px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array();
        iArray[7][0]="终了红利";
        iArray[7][1]="0px";
        iArray[7][2]=100;
        iArray[7][3]=3;
                         
        iArray[8]=new Array();
        iArray[8][0]="现金价值";
        iArray[8][1]="0px";
        iArray[8][2]=100;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="新险种保额 ";
        iArray[9][1]="0px";
        iArray[9][2]=100;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="原交至日期";
        iArray[10][1]="0px";
        iArray[10][2]=100;
        iArray[10][3]=3;
        
        iArray[11]=new Array();
        iArray[11][0]="原保费标准";
        iArray[11][1]="0px";
        iArray[11][2]=100;
        iArray[11][3]=3;
        
        iArray[12]=new Array();
        iArray[12][0]="原保额";
        iArray[12][1]="0px";
        iArray[12][2]=100;
        iArray[12][3]=3;
                 
        iArray[13]=new Array();
        iArray[13][0]="原份数";
        iArray[13][1]="0px";
        iArray[13][2]=100;
        iArray[13][3]=3;

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel =1;
      PolGrid.selBoxEventFuncName ="getSelNo" ;     //点击RadioBox时响应的JS函数
      PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);        
      //这些操作必须在loadMulLine后面
      
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>