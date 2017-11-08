<%
//GEdorTypeICInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">  
//单击时查询
function reportDetailClick(parm1,parm2)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  	ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
  	divLPInsuredDetail.style.left=ex;
  	divLPInsuredDetail.style.top =ey;
   	detailQueryClick();
}
function initInpBox()
{ 
	//alert("this is initInpBox method form!");
  try
  {        
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('GrpContNo').value = top.opener.document.all('GrpContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value;
    document.all('EdorTypeCal').value = top.opener.document.all('EdorTypeCal').value;
    document.all('ContNo').value = top.opener.document.all('ContNoBak').value;
    document.all('CustomerNo').value = top.opener.document.all('CustomerNoBak').value;
    document.all('CustomerNoBak').value = top.opener.document.all('CustomerNoBak').value;
    document.all('Name').value = '';
    document.all('Sex').value ='';
    document.all('Birthday').value = '';
    document.all('IDType').value = '';
    document.all('IDNo').value = '';
    document.all('ContType').value ='2';
    document.all('Marriage').value = '';
    document.all('NativePlace').value = '';
    document.all('OccupationType').value = '';
    document.all('OccupationCode').value = '';
  }
  catch(ex)
  {
    alert("在GEdorTypeICInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在GEdorTypeICInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
	//alert("this is initInpBox method form!");
  try
  {
    initInpBox();
    initSelBox(); 
    initPolDetailGrid();
    initQuery(); 
//    ctrlGetEndorse(); 
  }
  catch(re)
  {
    alert("GEdorTypeICInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
// 信息列表的初始化
function initPolDetailGrid()
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
      iArray[1][0]="个人保单号";					//列名1
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户号";         			//列名2
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="险种保单号";         		//列名8
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="险种代码";         			//列名5
      iArray[4][1]="40px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;						//是否允许输入,1表示允许，0表示不允许,2表示代码选择

      iArray[5]=new Array();
      iArray[5][0]="保额";         		//列名5
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

      iArray[6]=new Array();
      iArray[6][0]="保费";     //列名6
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		iArray[7]=new Array();
		iArray[7][0]="币种";         		
		iArray[7][1]="50px";            		 
		iArray[7][2]=60;            			
		iArray[7][3]=2;              		
		iArray[7][4]="Currency";              	  
		iArray[7][9]="币种|code:Currency";

      PolDetailGrid = new MulLineEnter( "fm" , "PolDetailGrid" ); 
      //这些属性必须在loadMulLine前
      PolDetailGrid.mulLineCount = 5;   
      PolDetailGrid.displayTitle = 1;
      PolDetailGrid.canSel=0;
      PolDetailGrid.hiddenPlus = 1; 
      PolDetailGrid.hiddenSubtraction = 1;
      PolDetailGrid.loadMulLine(iArray);  
      }     
      
      catch(ex)
      {
        alert(ex);
      }
}

function initMoneyDetailGrid()
{
    var iArray = new Array();
    
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;
      
      iArray[1]=new Array();
      iArray[1][0]="补/退费通知书号码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="80px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="补/退费财务类型";					//列名1
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="个人险种号码";         			//列名2
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

	 		iArray[4]=new Array();
      iArray[4][0]="姓名";         			//列名2
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="补/退费日期";         		//列名8
      iArray[5][1]="70px";            		//列宽
      iArray[5][2]=30;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="险种编码";     //列名6
      iArray[6][1]="50px";            		//列宽
      iArray[6][2]=50;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="责任编码";     //列名6
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=50;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="责任名称";     //列名6
      iArray[8][1]="70px";            		//列宽
      iArray[8][2]=50;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="缴费计划编码";     //列名6
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=50;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="缴费计划名称";     //列名6
      iArray[10][1]="70px";            		//列宽
      iArray[10][2]=50;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="参考补/退费金额(元)";         		//列名8
      iArray[11][1]="60px";            		//列宽
      iArray[11][2]=30;            			//列最大值
      iArray[11][3]=0;


      iArray[12]=new Array();
      iArray[12][0]="协议补/退费金额(元)";         		//列名8
      iArray[12][1]="60px";            		//列宽
      iArray[12][2]=30;            			//列最大值
      iArray[12][3]=2;
	  iArray[12][9]="补/退费|NOTNULL&NUM";  

		iArray[13]=new Array();
		iArray[13][0]="币种";         		
		iArray[13][1]="50px";            		 
		iArray[13][2]=60;            			
		iArray[13][3]=2;              		
		iArray[13][4]="Currency";              	  
		iArray[13][9]="币种|code:Currency"; 

      MoneyDetailGrid = new MulLineEnter( "fm" , "MoneyDetailGrid" ); 
      //这些属性必须在loadMulLine前        
      MoneyDetailGrid.mulLineCount = 10;   
      MoneyDetailGrid.displayTitle = 1;
      MoneyDetailGrid.canSel=0;
      MoneyDetailGrid.hiddenPlus = 1; 
      MoneyDetailGrid.hiddenSubtraction = 1;
      MoneyDetailGrid.selBoxEventFuncName ="";
      MoneyDetailGrid.loadMulLine(iArray);  
      MoneyDetailGrid.detailInfo="单击显示详细信息";      
      MoneyDetailGrid.loadMulLine(iArray);        
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initQuery()
{	
  //var tsql = "select name,Sex,Birthday,IDType,decode(IDType,'0',decode(length(IDNo),15,trans1(IDNo),IDNo),IDNo) ,OccupationType,OccupationCode,(select occupationname from ldoccupation where OccupationCode = lcinsured.OccupationCode),nvl(socialinsuflag,0) from lcinsured where contno='"+document.all('ContNo').value+"' and insuredno='"+document.all('CustomerNo').value+"'";
  
  var sqlid903103416="DSHomeContSql903103416";
var mySql903103416=new SqlClass();
mySql903103416.setResourceName("bq.GEdorTypeICInitSql"); //指定使用的properties文件名
mySql903103416.setSqlId(sqlid903103416); //指定使用的Sql的id
mySql903103416.addSubPara(document.all('ContNo').value); //指定传入的参数
mySql903103416.addSubPara(document.all('CustomerNo').value); //指定传入的参数
var tsql=mySql903103416.getString();
  
//  var tsql = "select name,Sex,Birthday,IDType,IDNo,OccupationType,OccupationCode,(select occupationname from ldoccupation where OccupationCode = lcinsured.OccupationCode),nvl(socialinsuflag,0) from lcinsured where contno='"+document.all('ContNo').value+"' and insuredno='"+document.all('CustomerNo').value+"'";
  var arrResult = easyExecSql(tsql);  
  try {document.all('NameBak').value = arrResult[0][0];} catch(ex) { };  
  try {document.all('SexBak').value = arrResult[0][1];} catch(ex) { };
  try {document.all('BirthdayBak').value = arrResult[0][2];} catch(ex) { };
  try {document.all('IDTypeBak').value = arrResult[0][3];} catch(ex) { };
  try {document.all('IDNoBak').value = arrResult[0][4];} catch(ex) { };
  try {document.all('OccupationTypeBak').value = arrResult[0][5];} catch(ex) { };  
  try {document.all('OccupationCodeBak').value = arrResult[0][6];} catch(ex) { };
  try {document.all('OccupationCodeNameBak').value = arrResult[0][7];} catch(ex) { };
  try {document.all('SocialInsuFlagBak').value = arrResult[0][8];} catch(ex) { };    
  
  //var psql = "select name,Sex,Birthday,IDType,decode(IDType,'0',decode(length(IDNo),15,trans1(IDNo),IDNo),IDNo),OccupationType,OccupationCode,(select occupationname from ldoccupation where OccupationCode = lpinsured.OccupationCode),nvl(socialinsuflag,0) from lpinsured where edorno='"+document.all('EdorNo').value+"' and edortype='IC' and contno='"+document.all('ContNo').value+"' and insuredno='"+document.all('CustomerNo').value+"'";
  
  var sqlid903103613="DSHomeContSql903103613";
var mySql903103613=new SqlClass();
mySql903103613.setResourceName("bq.GEdorTypeICInitSql"); //指定使用的properties文件名
mySql903103613.setSqlId(sqlid903103613); //指定使用的Sql的id
mySql903103613.addSubPara(document.all('EdorNo').value); //指定传入的参数
mySql903103613.addSubPara(document.all('ContNo').value); //指定传入的参数
mySql903103613.addSubPara(document.all('CustomerNo').value); //指定传入的参数
var psql=mySql903103613.getString();
  
//  var psql = "select name,Sex,Birthday,IDType,IDNo,OccupationType,OccupationCode,(select occupationname from ldoccupation where OccupationCode = lpinsured.OccupationCode),nvl(socialinsuflag,0) from lpinsured where edorno='"+document.all('EdorNo').value+"' and edortype='IC' and contno='"+document.all('ContNo').value+"' and insuredno='"+document.all('CustomerNo').value+"'";
  arrResult = easyExecSql(psql);
  //alert(arrResult);
  if(arrResult != null && arrResult != "" && arrResult != "null"){
  try {document.all('Name').value = arrResult[0][0];} catch(ex) { };  
  try {document.all('Sex').value = arrResult[0][1];} catch(ex) { };
  try {document.all('Birthday').value = arrResult[0][2];} catch(ex) { };
  try {document.all('IDType').value = arrResult[0][3];} catch(ex) { };
  try {document.all('IDNo').value = arrResult[0][4];} catch(ex) { };
  try {document.all('OccupationType').value = arrResult[0][5];} catch(ex) { }; 
  showOneCodeName('occupationtype', 'OccupationType', 'OccupationTypeName'); 
  try {document.all('OccupationCode').value = arrResult[0][6];} catch(ex) { }; 
  try {document.all('OccupationCodeName').value = arrResult[0][7];} catch(ex) { }; 
  try {document.all('SocialInsuFlag').value = arrResult[0][8];} catch(ex) { };      
  }else{  
  try {document.all('Name').value = document.all('NameBak').value;} catch(ex) { };  
  try {document.all('Sex').value = document.all('SexBak').value;} catch(ex) { };
  try {document.all('Birthday').value = document.all('BirthdayBak').value;} catch(ex) { };
  try {document.all('IDType').value = document.all('IDTypeBak').value;} catch(ex) { };
  try {document.all('IDNo').value = document.all('IDNoBak').value;} catch(ex) { };
  try {document.all('OccupationType').value = document.all('OccupationTypeBak').value;} catch(ex) { };  
  showOneCodeName('occupationtype', 'OccupationType', 'OccupationTypeName');
  try {document.all('OccupationCode').value = document.all('OccupationCodeBak').value;} catch(ex) { }; 
  try {document.all('OccupationCodeName').value = document.all('OccupationCodeNameBak').value;} catch(ex) { }; 
  try {document.all('SocialInsuFlag').value = document.all('SocialInsuFlagBak').value;} catch(ex) { }; 
  }  
  
  Qrygetmoney();
  //alert(11111111)
  queryPolDetail();
   	 	 
}

function CondQueryClick()
{
	var i = 0;
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	
	document.all('fmtransact').value = "QUERY||MAIN";
	var tContNo = document.all('ContNo').value;
	var tCustomerNo=document.all('CustomerNo1').value;
	
	if (tContNo==null&&tContNo==''&&tCustomerNo!=null&&tCustomerNo!='')
		mFlag = "P";
	else if(tCustomerNo==null&&tCustomerNo==''&&tContNo!=null&&tContNo!='')
		mFlag = "C";
	else if (tCustomerNo==null&&tCustomerNo==''&&tContNo==null&&tContNo=='')
		mFlag = "N";
	else
		mFlag = "A";
	fm.submit();
}

function detailQueryClick()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   	
	document.all('fmtransact').value = "QUERY||DETAIL";
	
	var tSel=LCInsuredGrid.getSelNo();
	
	if (tSel==0||tSel==null)
		alert("不能是空记录!");
	else
	{
		var tCustomerNo =LCInsuredGrid.getRowColData(tSel-1,2);
		document.all('CustomerNo1').value =tCustomerNo;
		document.all('ContNo').value =LCInsuredGrid.getRowColData(tSel-1,1);
		fm.submit();
	}
}
function initDiv()
{
	divLPInsuredDetail.style.display ='none';
	divDetail.style.display='none';
}

function Qrygetmoney()
{
  var sqlid903103731="DSHomeContSql903103731";
var mySql903103731=new SqlClass();
mySql903103731.setResourceName("bq.GEdorTypeICInitSql"); //指定使用的properties文件名
mySql903103731.setSqlId(sqlid903103731); //指定使用的Sql的id
mySql903103731.addSubPara(document.all('EdorNo').value); //指定传入的参数
mySql903103731.addSubPara(document.all('ContNo').value); //指定传入的参数
var tsql=mySql903103731.getString();
  
//  var tsql = "select 1 from LJSGetEndorse  where EndorsementNo = '"+document.all('EdorNo').value+"' and FeeOperationType = 'IC' and contno='"+document.all('ContNo').value+"' ";
  var arrResult = easyExecSql(tsql, 1, 0, 1);
  divGetEndorseInfo.style.display='none';
  	if(arrResult!=null&&arrResult[0][0]=="1")
  	{
  		divGetEndorseInfo.style.display='';
  		initMoneyDetailGrid();
  		
  		var sqlid903103902="DSHomeContSql903103902";
var mySql903103902=new SqlClass();
mySql903103902.setResourceName("bq.GEdorTypeICInitSql"); //指定使用的properties文件名
mySql903103902.setSqlId(sqlid903103902); //指定使用的Sql的id
mySql903103902.addSubPara(fm.EdorNo.value); //指定传入的参数
mySql903103902.addSubPara(fm.EdorType.value); //指定传入的参数
mySql903103902.addSubPara(fm.ContNo.value); //指定传入的参数
tsql=mySql903103902.getString();

  		
//  		tsql = "Select GetNoticeNo, decode(FeeFinaType,'BF','补费','TF','退费','其它'), PolNo,(select insuredname from lccont where contno=LJSGetEndorse.contno), GetDate, RiskCode, dutycode,(select dutyname from lmduty where dutycode = LJSGetEndorse.dutycode),payplancode,(select payplanname from lmdutypay where payplancode = LJSGetEndorse.payplancode),SerialNo,GetMoney From LJSGetEndorse where otherno='"+fm.EdorNo.value+"' and FeeOperationType='"+fm.EdorType.value+"' and ContNo = '"+fm.ContNo.value+"' order by riskcode";
  		turnPage2.queryModal(tsql, MoneyDetailGrid);
  	}
  
}
</script>
