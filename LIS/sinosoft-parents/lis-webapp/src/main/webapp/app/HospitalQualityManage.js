//程序名称：UWCustomerQuality.js
//程序功能：客户品质管理
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
function queryClick()
{
	// 初始化表格
	//initQuestGrid();
	//initContent();
	
	// 书写SQL语句
//  var strSql = "select PrtNo, ProposalContNo, NotePadCont, Operator, MakeDate, MakeTime from LCNotePad where PrtNo='" + PrtNo 
//             + "' and ContNo='" + ContNo + "' and OperatePos='" + OperatePos + "'"
//             + getWherePart('Operator', 'Operator')
//             + getWherePart('MakeDate', 'MakeDate');
	
	var  Operator = window.document.getElementsByName(trim("Operator"))[0].value;
  	var  MakeDate = window.document.getElementsByName(trim("MakeDate"))[0].value;
	var sqlid1="HospitalQualityManageSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.HospitalQualityManageSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(PrtNo);//指定传入的参数
	mySql1.addSubPara(ContNo);//指定传入的参数
	mySql1.addSubPara(OperatePos);//指定传入的参数
	mySql1.addSubPara(Operator);//指定传入的参数
	mySql1.addSubPara(MakeDate);//指定传入的参数
	var strSql=mySql1.getString();
	
  turnPage.queryModal(strSql, QuestGrid); 
  
  fm.Content.value = "";
}


function showOne(parm1, parm2) {	
  //判断该行是否确实被选中
	if(document.all(parm1).all('InpQuestGridSel').value == '1' ) {
	  var index = document.all(parm1).all('QuestGridNo').value - 1;
	  fm.Content.value = turnPage.arrDataCacheSet[index][2];
  }
}

function InsertClick() {
 
  if(!verifyInput())
  {
  	return false;
  }
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;    
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
  document.getElementById("fm").submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {   
  showInfo.close();         
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
  initAll();
  easyQueryClick();
}

function initData(){
//  var Sql= "select managecom,name,employdate,sex,'0',idno,agentcode from laagent where agentcode in (select agentcode from lcpol where contno='"+ContNo+"')";
	
	var sqlid2="HospitalQualityManageSql1";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.HospitalQualityManageSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(ContNo);//指定传入的参数
	var Sql=mySql2.getString();	
    var arrResult = easyExecSql(Sql);
    try{document.all('ManageCom').value= arrResult[0][0];}catch(ex){};         
    try{document.all('Name').value= arrResult[0][1];}catch(ex){};          
    try{document.all('EmployDate').value= arrResult[0][2];}catch(ex){};     
    try{document.all('sex').value= arrResult[0][3];}catch(ex){};            
    try{document.all('IDType').value= arrResult[0][4];}catch(ex){};              
    try{document.all('IDNumber').value= arrResult[0][5];}catch(ex){};   
    try{document.all('agent').value=arrResult[0][6]}catch(ex){};
    try{document.all('agentname').value=arrResult[0][1]}catch(ex){};
}


function easyQueryClick(){
//  
//    var tSql = "select hospitcode,hospitalname,mngcom,(case when BlackListFlag is not null then BlackListFlag else '0' end),(case when HosState is not null then HosState else '0' end),remark,operator,ReasonType,makedate "
//             + " from ldhospital where 1=1 "
//             +getWherePart('hospitcode','HospitalCode1')
//             +getWherePart('ValidityDate','InputDate')
//             +getWherePart('HospitalName','HospitalName1','like')
//             +getWherePart('mngcom','ManageCom1','like')
//             ;
    
    var  Operator = window.document.getElementsByName(trim("HospitalCode1"))[0].value;
  	var  InputDate = window.document.getElementsByName(trim("InputDate"))[0].value;
  	var  HospitalName1 = window.document.getElementsByName(trim("HospitalName1"))[0].value;
  	var  ManageCom1 = window.document.getElementsByName(trim("ManageCom1"))[0].value;
	var sqlid3="HospitalQualityManageSql2";
	var mySql3=new SqlClass();
	mySql3.setResourceName("app.HospitalQualityManageSql"); //指定使用的properties文件名
	mySql3.setSqlId(sqlid1);//指定使用的Sql的id
	mySql3.addSubPara(Operator);//指定传入的参数
	mySql3.addSubPara(InputDate);//指定传入的参数
	mySql3.addSubPara(HospitalName1);//指定传入的参数
	mySql3.addSubPara(ManageCom1);//指定传入的参数
	var tSql=mySql3.getString();
     if(document.all('FQualityFlag').value!=null&&document.all('FQualityFlag').value!="")
     {
     	  if(document.all('FQualityFlag').value=='0')
     	  {
     	  	tSql = tSql + " and (BlackListFlag is null or BlackListFlag='0') " ;
     	  }
     	  else if(document.all('FQualityFlag').value=='1')
     	  {
     	  	tSql = tSql + " and BlackListFlag='1' " ;
     	  }
     	  	
     }
   	initHospitalGrid();
    turnPage1.queryModal(tSql, HospitalGrid);
}

function showHospitalDetail(){
   

   var tSelNo = HospitalGrid.getSelNo()-1;
   //初始化体检医院相关信息
   fm.HospitalCode.value = HospitalGrid.getRowColData(tSelNo,1);
   fm.HospitalName.value = HospitalGrid.getRowColData(tSelNo,2);
   fm.ManageCom.value = HospitalGrid.getRowColData(tSelNo,3);
   fm.QualityFlag.value = HospitalGrid.getRowColData(tSelNo,4);
   fm.QualityFlagType.value = HospitalGrid.getRowColData(tSelNo,8);
   fm.Reason.value = HospitalGrid.getRowColData(tSelNo,6);
  // var sql = "select shortname from ldcom where comcode='"+fm.ManageCom.value+"' ";
   var sqlid4="HospitalQualityManageSql3";
	var mySql4=new SqlClass();
	mySql4.setResourceName("app.HospitalQualityManageSql"); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(fm.ManageCom.value);//指定传入的参数
	var sql=mySql4.getString();
    var arrResult = easyExecSql(sql);
    if(arrResult!=null)
    {
    	fm.ManageComName.value = arrResult[0][0];
    	}
   quickGetName('hospitalqaflag',fm.QualityFlag,fm.QualityFlagName);
   quickGetName('hospitalqaflagsub',fm.QualityFlagType,fm.QualityFlagTypeName);

   //var tSQL_Sub = "select from ";
     
   
}
/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */
function afterQuery( arrQueryResult)
{
	var arrResult = new Array();
	if( arrQueryResult != null )
	{
		
//		  var strSql = "select hospitcode,a.hospitalname,mngcom,a.validitydate,BlackListFlag "         		
//         		 		 + " from  LDHospital a where 1=1 "
//         		     + " and hospitcode = '"+arrQueryResult[0][0]+"'";
		  
	
			var sqlid5="HospitalQualityManageSql4";
			var mySql5=new SqlClass();
			mySql5.setResourceName("app.HospitalQualityManageSql"); //指定使用的properties文件名
			mySql5.setSqlId(sqlid5);//指定使用的Sql的id
			mySql5.addSubPara(arrQueryResult[0][0]);//指定传入的参数
			var strSql=mySql5.getString();
		   
	 	arrResult = easyExecSql(strSql);
	 
	  document.all('HospitalCode1').value = arrResult[0][0];
    document.all('HospitalName1').value = arrResult[0][1];
    document.all('ManageCom1').value = arrResult[0][2];
    document.all('InputDate').value = arrResult[0][3]; 
     document.all('FQualityFlag').value = arrResult[0][4]; 
   }
   easyQueryClick();
} 

function afterCodeSelect( cCodeName, Field )
{
 if(cCodeName=="agent"){
 	//alert(fm.customer.value);
 	var strSQL="select managecom,name,employdate,sex,'0',idno from laagent where trim(agentcode)=trim('"+fm.agent.value+"')";
 	
 	//alert(strSQL);
   var arrResult=easyExecSql(strSQL);
   //alert(arrResult);                         
   try{document.all('ManageCom').value= arrResult[0][0];}catch(ex){};         
   try{document.all('Name').value= arrResult[0][1];}catch(ex){};          
   try{document.all('EmployDate').value= arrResult[0][2];}catch(ex){};     
   try{document.all('sex').value= arrResult[0][3];}catch(ex){};            
   try{document.all('IDType').value= arrResult[0][4];}catch(ex){};              
   try{document.all('IDNumber').value= arrResult[0][5];}catch(ex){};                
   //try{document.all('BlacklistFlagNo').value= arrResult[0][31];}catch(ex){};
  
  
   //try{   
    // if(arrResult[0][31]==0) document.all('BlacklistFlagName').value="正常";  	
     //    else
     //    document.all('BlacklistFlagName').value="黑名单";   
     // }
      
   //catch(ex){};       
  
   //try{document.all('Remark').value= arrResult[0][33];}catch(ex){};        
   return;       
 }
   if(cCodeName =="QualityState")
   {
     if(document.all('QualityState').value == "0")
     {
       //异常类别不可选
   //    fm.CReasonType.value = "";
      // fm.CReasonType.disabled = true;
     }else
       
      // fm.CReasonType.disabled = false;
     return;
   }
// if(cCodeName=="paymode"){
//   	if(document.all('PayMode').value=="4"){
//   	  //divLCAccount1.style.display="";
//    }
//    else
//    {
//   	  //divLCAccount1.style.display="none";
//      //alert("accountImg===");	
//    }
// }
 //alert("aaa");
// afterCodeSelect2( cCodeName, Field );
}

/*********************************************************************
 *  投保人客户号查询按扭事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryCustomer()
{
    if (document.all("InsuredNo").value == "")
    {
      //showAppnt1();
      showInsured1();
    }
    else
    {
    	arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
        if (arrResult == null)
        {
          alert("未查到被保人信息");
          displayInsuredInfo(new Array());
          emptyUndefined();
        }
        else
        {
          //displayAppnt(arrResult[0]);
          displayInsuredInfo(arrResult[0]);
        }
        
    }
}

/*********************************************************************
 *  投保人查询按扭事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
//function showAppnt1()
function showHospital()
{
	
		showInfo = window.open( "../config/LDHospitalDetailQuery.html" );
	
}



function returnParent(){
  top.close();
	
}

function getClickHospital()
{
  	var tTempMain = document.all('QualityFlag').value;
  	if(tTempMain==null||trim(tTempMain)=='')
  	{
  		alert('请输入品质状态!');
  		return false;
  	}
		sql_temp = " 1 and othersign=#"+tTempMain+"#   ";
   //alert(tManageCom.length);
  // alert(sql_temp);
  //showCodeList('hospitalqaflagsub',[this,QualityFlagTypeName],[0,1])
   showCodeList('hospitalqaflagsub',[document.all('QualityFlagType'),document.all('QualityFlagTypeName')],[0,1],null,sql_temp,"1",1);
}

function getClickUpHospital()
{
  	var tTempMain = document.all('QualityFlag').value;
  	if(tTempMain==null||trim(tTempMain)=='')
  	{
  		//alert('请输入品质状态!');
  		return false;
  	}
	sql_temp = " 1 and othersign=#"+tTempMain+"#   ";
	showCodeListKey('hospitalqaflagsub',[document.all('QualityFlagType'),document.all('QualityFlagTypeName')],[0,1],null,sql_temp,"1",1);
}


var cacheWin=null;
function quickGetName(strCode, showObjc, showObjn) {
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var urlStr = "";
	var sFeatures = "";
	var strQueryResult = "";	//代码选择的查询结果集
	var arrCode = null;	//拆分数组
	var strCodeValue = "";	//代码值
	var cacheFlag = false;	//内存中有数据标志
	var showObjn;
	var showObjc;
	//遍历所有FORM


	//如果代码栏的数据不为空，才查询，否则不做任何操作
	if (showObjc.value != "")
	{
		//寻找主窗口
		if(cacheWin==null)
		{
			 cacheWin = searchMainWindow(this);
			if (cacheWin == false) { cacheWin = this; }
		}
		cacheFlag == false;
		
		{
			urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode;
			sFeatures = "status:no;help:0;close:0;dialogWidth:150px;dialogHeight:0px;dialogLeft:-1;dialogTop:-1;resizable=1";
			//连接数据库进行CODE查询，返回查询结果给strQueryResult
			//strQueryResult = window.showModalDialog(urlStr, "", sFeatures);
			var name='提示';   //网页名称，可为空; 
			var iWidth=150;      //弹出窗口的宽度; 
			var iHeight=1;     //弹出窗口的高度; 
			var iTop = 1; //获得窗口的垂直位置 
			var iLeft = 1;  //获得窗口的水平位置 
			strQueryResult = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			strQueryResult.focus();
		}
		//拆分成数组
		try {
			if (!cacheFlag)
			{
				try
				{
					arrCode = decodeEasyQueryResult(strQueryResult);
				}
				catch(ex)
				{
					alert("页面缺少引用EasyQueryVer3.js");
				}

			for (i=0; i<arrCode.length; i++)
			{
				if (showObjc.value == arrCode[i][0])
				{
					showObjn.value = arrCode[i][1];
					break;
				}
			}
		}
	}
		catch(ex)
		{}
}
}
