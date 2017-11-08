//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var arrDataSet;

function DateCompare(date1,date2)
{
  var strValue=date1.split("-");
  var date1Temp=new Date(strValue[0],strValue[1]-1,strValue[2]);

  strValue=date2.split("-");
  var date2Temp=new Date(strValue[0],strValue[1]-1,strValue[2]);

  if(date1Temp.getTime()==date2Temp.getTime())
    return 0;
  else if(date1Temp.getTime()>date2Temp.getTime())
    return 1;
  else
    return -1;
}

function QueryModifyReason()
{
	if(_DBT==_DBO){
		//var queryModifysql="select reason from lcedorreason where contno='"+fm.ContNo.value+"' and Type='11' and edortype='10' and rownum=1";
		var  sqlid1="PolModifyInputSql0";
	 	var  mySql1=new SqlClass();
	 	mySql1.setResourceName("uw.PolModifyInputSql"); //指定使用的properties文件名
	 	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	 	mySql1.addSubPara(fm.ContNo.value);//指定传入的参数
	 	var queryModifysql=mySql1.getString();
	}else if(_DBT==_DBM){
		//var queryModifysql="select reason from lcedorreason where contno='"+fm.ContNo.value+"' and Type='11' and edortype='10' limit 1";
		var  sqlid2="PolModifyInputSql1";
	 	var  mySql2=new SqlClass();
	 	mySql2.setResourceName("uw.PolModifyInputSql"); //指定使用的properties文件名
	 	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	 	mySql2.addSubPara(fm.ContNo.value);//指定传入的参数
	 	var queryModifysql=mySql2.getString();
	}
	var arrResult = easyExecSql(queryModifysql);
	if(arrResult==null)
		return;
	document.all('Reason').value=arrResult[0][0];
}

function PolModify()
{
  /*
 //银行自动转账，银行转账应该录入完整的帐户信息
 if(document.all('PayLocation').value=='0'||document.all('PayLocation').value=='8')
 {
 	if(document.all('BankCode').value==''||document.all('AccName').value==''||document.all('BankAccNo').value=='')
 	{
 		alert("请填写完整的帐户信息！");	
 		return false;
 	}
 }
 */
   if(verifyInput() == false) return false; 
   
   if(checkInput() == false) return false;
 
  if(trim(document.all('Reason').value)=='')
  {
	alert("请填写保单修改原因!");
	return false;
  }
 
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    alert(content);
    //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  }
  else
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    initForm();
  }
}

//录入校验
function checkInput()
{
    if ((fm.AppntRelationToInsured.value!="" && fm.AppntRelationToInsured.value=="00") || (fm.RelationToAppnt.value!="" && fm.RelationToAppnt.value=="00")) {
	    if(fm.AppntName.value!=fm.Name.value)
	    {
	    	alert("投保人和被保人关系为本人，姓名必须相同！");
	    	return false;
	    }
    }
    //首期校验
    if(fm.NewPayMode.value==null || fm.NewPayMode.value=='' ||(fm.NewPayMode.value!='0' && fm.NewPayMode.value!='8'))
    {
    	if((fm.NewBankCode.value!=null && fm.NewBankCode.value!="")
    		||(fm.NewAccName.value!=null && fm.NewAccName.value!="")
    		||(fm.NewBankAccNo.value!=null && fm.NewBankAccNo.value!=""))
    		{
    			alert("首期交费形式为空或不为银行转账，不能录入首期帐户信息！");
	   			 return false;
    		}
    }
    else
    {
    	if((fm.NewAccName.value==null || fm.NewAccName.value=="")
    		||(fm.NewAccName.value==null || fm.NewAccName.value=="")
    		||(fm.NewAccName.value==null || fm.NewAccName.value==""))
    		{
    			alert("首期帐户信息必须录入！");
	   			 return false;
    		}
    	
    }
    
    //续期校验
    if(fm.PayLocation.value==null || fm.PayLocation.value=='' ||(fm.PayLocation.value!='0' && fm.PayLocation.value!='8'))
    {
    	if((fm.BankCode.value!=null && fm.BankCode.value!="")
    		||(fm.AccName.value!=null && fm.AccName.value!="")
    		||(fm.BankAccNo.value!=null && fm.BankAccNo.value!=""))
    		{
    			alert("续期交费形式为空或不为银行转账，不能录入续期帐户信息！");
	   			 return false;
    		}
    }
    else
    {
    	if((fm.BankCode.value==null || fm.BankCode.value=="")
    		||(fm.AccName.value==null || fm.AccName.value=="")
    		||(fm.BankAccNo.value==null || fm.BankAccNo.value==""))
    		{
    			alert("续期帐户信息必须录入！");
	   			 return false;
    		}
    	
    }
    
    if(fm.NewAccName.value!=null && fm.NewAccName.value!="" && fm.AppntName.value!=fm.NewAccName.value)
	    {
	    	
	    	alert("首期帐户名必须为投保人姓名！");
	    	return false;
	    }
	if(fm.AccName.value!=null && fm.AccName.value!="" && fm.AppntName.value!=fm.AccName.value)
	    {
	    	alert("续期帐户名必须为投保人姓名！");
	    	return false;
	    }
}

//查询合同的所有数据库的信息
//
function queryAllContInfo()
{
	queryAppntInfo();
    queryContNo();    
    queryInsuredInfo();
    queryRiskInfo();
	getLCBnfInfo();
	getImpartInfo();//告知
	showCodeName();
	QueryModifyReason();
}

//【查询合同以及缴费信息】－－－
function queryContNo()
{
	var sSQL ="select l.NewBankCode,l.NewAccName,l.NewBankAccNo,l.BankCode,l.AccName,l.BankAccNo"
	        +" ,m.polno "
	         //不可修改的内容 
	        +" ,l.prtno,l.polapplydate,l.managecom,l.agentcom,l.agentcode"
	        +" ,(select a.ManageCom from LAAgent a where a.AgentCode=l.agentcode),l.agentgroup,l.salechnl,l.remark"
	        +" ,l.NewPayMode,l.PayLocation,l.AutoPayFlag,l.RnewFlag,l.GetPolMode,l.OutPayFlag,l.PayIntv"
	        +" ,(select a.name from LAAgent a where a.AgentCode=l.agentcode)"
	        +" ,(select a.name from LACom a where a.AgentCom=l.agentcom),l.CustomGetPolDate"
	        +" ,m.LiveGetMode,m.BonusGetMode"
	       //--不可修改的内容
			+" from lccont l,lcpol m where l.contno=m.contno and m.polno=m.mainpolno and l.contno='"+fm.ContNo.value+"'";
	
	//prompt('',sSQL);
	var arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{
		fm.NewBankCode.value=arrInsuredAdd[0][0];
		fm.NewAccName.value=arrInsuredAdd[0][1];
		fm.NewBankAccNo.value=arrInsuredAdd[0][2];
		fm.BankCode.value=arrInsuredAdd[0][3];
		fm.AccName.value=arrInsuredAdd[0][4];
		fm.BankAccNo.value=arrInsuredAdd[0][5];
		fm.SelPolNo.value=arrInsuredAdd[0][6];
		
		fm.PrtNo.value=arrInsuredAdd[0][7];
		fm.PolApplyDate.value=arrInsuredAdd[0][8];
		fm.ManageCom.value=arrInsuredAdd[0][9];
		fm.AgentCom.value=arrInsuredAdd[0][10];
		fm.AgentCode.value=arrInsuredAdd[0][11];
		fm.AgentManageCom.value=arrInsuredAdd[0][12];
		fm.AgentGroup.value=arrInsuredAdd[0][13];
		fm.SaleChnl.value=arrInsuredAdd[0][14];
		fm.remark.value=arrInsuredAdd[0][15];
		fm.NewPayMode.value=arrInsuredAdd[0][16];
		fm.PayLocation.value=arrInsuredAdd[0][17];
		fm.AutoPayFlag.value=arrInsuredAdd[0][18];
		fm.RnewFlag.value=arrInsuredAdd[0][19];
		fm.GetPolMode.value=arrInsuredAdd[0][20];
		fm.OutPayFlag.value=arrInsuredAdd[0][21];
		fm.PayIntv.value=arrInsuredAdd[0][22];
		fm.AgentName.value=arrInsuredAdd[0][23];	
		fm.InputAgentComName.value=arrInsuredAdd[0][24];
		fm.CustomGetPolDate.value=arrInsuredAdd[0][25];	
		fm.LiveGetMode.value=arrInsuredAdd[0][26];
		fm.BonusGetMode.value=arrInsuredAdd[0][27];
	}
}

//【查询投保人信息】－－－
function queryAppntInfo()
{
    //alert(fm.ContNo.value);
	var sSQL ="select a.AppntName,a.idtype,a.idno, b.postaladdress,b.zipcode,b.homeaddress,b.homezipcode,b.mobile,b.phone,b.email,b.grpname,a.appntno,a.addressno,a.RelationToInsured"
			//不可修改的内容 
	        +" ,a.appntsex,a.appntbirthday,a.NativePlace,a.RgtAddress,a.Marriage,a.PluralityType,a.OccupationCode,a.OccupationType"	        
	       //--不可修改的内容
			+ " from lcappnt a ,lcaddress b where contno='"+fm.ContNo.value+"'"
			+ " and b.customerno=a.appntno and b.addressno=a.addressno";
	var arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{
		fm.AppntName.value=arrInsuredAdd[0][0];
		fm.AppntIDType.value=arrInsuredAdd[0][1];
		fm.AppntIDNo.value=arrInsuredAdd[0][2];
		fm.AppntPostalAddress.value=arrInsuredAdd[0][3];
		fm.AppntZipCode.value=arrInsuredAdd[0][4];
		fm.AppntHomeAddress.value=arrInsuredAdd[0][5];		
		fm.AppntHomeZipCode.value=arrInsuredAdd[0][6];
		fm.AppntPhone.value=arrInsuredAdd[0][7];
		fm.AppntPhone2.value=arrInsuredAdd[0][8];
		fm.AppntEMail.value=arrInsuredAdd[0][9];
		fm.AppntGrpName.value=arrInsuredAdd[0][10];
		fm.AppntNo.value=arrInsuredAdd[0][11];
		fm.AppntAddressNo.value=arrInsuredAdd[0][12];
		//alert(fm.AppntAddressNo.value);
		fm.AppntRelationToInsured.value=arrInsuredAdd[0][13];
		
		fm.ApntSex.value=arrInsuredAdd[0][14];
		fm.ApntBirthday.value=arrInsuredAdd[0][15];
		fm.ApntNativePlace.value=arrInsuredAdd[0][16];
		fm.ApntRgtAddress.value=arrInsuredAdd[0][17];
		fm.ApntMarriage.value=arrInsuredAdd[0][18];
		fm.ApntPluralityType.value=arrInsuredAdd[0][19];
		fm.ApntOccupationCode.value=arrInsuredAdd[0][20];
		fm.ApntOccupationType.value=arrInsuredAdd[0][21];
	}
}

//【查询被保人信息】－－－
function queryInsuredInfo()
{
	var sSQL ="select a.Name,a.idtype,a.idno,b.homeaddress,b.homezipcode,b.mobile,b.phone,b.email,b.grpname,a.insuredno,a.addressno,a.RelationToAppnt"
			//不可修改的内容 
	        +" ,a.customerseqno,a.RelationToMainInsured,a.RelationToAppnt,a.Sex,a.Birthday,a.NativePlace,a.RgtAddress"
	        +" ,a.Marriage,a.WorkType,a.PluralityType,a.OccupationCode,a.OccupationType"	        
	       //--不可修改的内容
			+ " from lcinsured a ,lcaddress b where contno='"+fm.ContNo.value+"'"
			+ " and b.customerno=a.insuredno and b.addressno=a.addressno";
	var arrInsuredAdd=easyExecSql(sSQL); 
	if(arrInsuredAdd!=null)
	{	    
		fm.Name.value=arrInsuredAdd[0][0];
		fm.IDType.value=arrInsuredAdd[0][1];
		fm.IDNo.value=arrInsuredAdd[0][2];
		fm.HomeAddress.value=arrInsuredAdd[0][3];		
		fm.HomeZipCode.value=arrInsuredAdd[0][4];
		fm.Phone.value=arrInsuredAdd[0][5];
		fm.Phone2.value=arrInsuredAdd[0][6];
		fm.EMail.value=arrInsuredAdd[0][7];
		fm.GrpName.value=arrInsuredAdd[0][8];
		fm.InsuredNo.value=arrInsuredAdd[0][9];
		fm.AddressNo.value=arrInsuredAdd[0][10];
		//alert(fm.AddressNo.value);
		fm.RelationToAppnt.value=arrInsuredAdd[0][11];
		
		fm.SequenceNoCode.value=arrInsuredAdd[0][12];
		fm.RelationToMainInsured.value=arrInsuredAdd[0][13];
		fm.RelationToAppnt.value=arrInsuredAdd[0][14];
		fm.Sex.value=arrInsuredAdd[0][15];
		fm.Birthday.value=arrInsuredAdd[0][16];		
		fm.NativePlace.value=arrInsuredAdd[0][17];
		fm.RgtAddress.value=arrInsuredAdd[0][18];
		fm.Marriage.value=arrInsuredAdd[0][19];
		fm.WorkType.value=arrInsuredAdd[0][20];
		fm.PluralityType.value=arrInsuredAdd[0][21];		
		fm.OccupationCode.value=arrInsuredAdd[0][22];
		fm.OccupationType.value=arrInsuredAdd[0][23];
	}
}

//获得险种信息
function queryRiskInfo()
{
	var tContNo=document.all('ContNo').value;
//	var sSQL="select polno,riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),prem,amnt "
//		+" from lcpol a where contno='"+tContNo+"' order by polno ";
	var  sqlid3="PolModifyInputSql2";
 	var  mySql3=new SqlClass();
 	mySql3.setResourceName("uw.PolModifyInputSql"); //指定使用的properties文件名
 	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
 	mySql3.addSubPara(tContNo);//指定传入的参数
 	var sSQL=mySql3.getString();
	turnPage.queryModal(sSQL,PolGrid);
}

//获得受益人信息
function getLCBnfInfo()
{
	var tContNo=document.all('ContNo').value;
//	var sSQL="select bnftype"
//	   // + " ,(select codename from ldcode where codetype='bnftype' and code=bnftype)"
//	    +" ,name,idtype,idno,relationtoinsured,bnflot,bnfgrade"
//		+" ,PostalAddress,'',operator,makedate,maketime"
//		+" from lcbnf where contno='"+tContNo+"' order by bnfno ";
	var  sqlid4="PolModifyInputSql3";
 	var  mySql4=new SqlClass();
 	mySql4.setResourceName("uw.PolModifyInputSql"); //指定使用的properties文件名
 	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
 	mySql4.addSubPara(tContNo);//指定传入的参数
 	var sSQL=mySql4.getString();
	turnPage.queryModal(sSQL,LCBnfGrid);
}

//获得告知信息
function getImpartInfo()
{
	var tContNo=document.all('ContNo').value;
//	var sSQL="select impartver,impartcode,impartcontent,impartparammodle,customernotype,customerno"	   
//		+" from lccustomerimpart where contno='"+tContNo+"' order by customernotype ";
	var  sqlid5="PolModifyInputSql4";
 	var  mySql5=new SqlClass();
 	mySql5.setResourceName("uw.PolModifyInputSql"); //指定使用的properties文件名
 	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
 	mySql5.addSubPara(tContNo);//指定传入的参数
 	var sSQL=mySql5.getString();
	turnPage.queryModal(sSQL,ImpartGrid);
}

//初始化汉字信息
function showCodeName()
{
	if(fm.ManageCom.value!="")
	{
		//var strSql = "select Name from LDCom where ComCode= '"+document.all('ManageCom').value+"'";
		var  sqlid6="PolModifyInputSql5";
	 	var  mySql6=new SqlClass();
	 	mySql6.setResourceName("uw.PolModifyInputSql"); //指定使用的properties文件名
	 	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	 	mySql6.addSubPara(document.all('ManageCom').value);//指定传入的参数
	 	var strSql=mySql6.getString();
	    var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.ManageComName.value = arrResult[0][0];
	      }
	}
	//showOneCodeNameRefresh('comcode','ManageCom','ManageComName');
	if(fm.AgentCom.value!="")
	{
		//var strSql = "select Name from LACom where AgentCom= '"+document.all('AgentCom').value+"'";
		var  sqlid7="PolModifyInputSql6";
	 	var  mySql7=new SqlClass();
	 	mySql7.setResourceName("uw.PolModifyInputSql"); //指定使用的properties文件名
	 	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	 	mySql7.addSubPara(document.all('AgentCom').value);//指定传入的参数
	 	var strSql=mySql7.getString();
	    var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.InputAgentComName.value = arrResult[0][0];
	      }
	}
	//showOneCodeNameRefresh('AgentCom','AgentCom','InputAgentComName');
	if(fm.AgentManageCom.value!="")
	{
		//var strSql = "select Name from LDCom where ComCode= '"+document.all('AgentManageCom').value+"'";
		var  sqlid8="PolModifyInputSql7";
	 	var  mySql8=new SqlClass();
	 	mySql8.setResourceName("uw.PolModifyInputSql"); //指定使用的properties文件名
	 	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	 	mySql8.addSubPara(document.all('AgentManageCom').value);//指定传入的参数
	 	var strSql=mySql8.getString();
	    var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.AgentManageComName.value = arrResult[0][0];
	      }
	}
	if(fm.ApntOccupationCode.value!="")
	{
		//var strSql = "select OccupationName from LDOccupation where occupationver = '002' and OccupationCode='"+document.all('ApntOccupationCode').value+"'";
		var  sqlid9="PolModifyInputSql8";
	 	var  mySql9=new SqlClass();
	 	mySql9.setResourceName("uw.PolModifyInputSql"); //指定使用的properties文件名
	 	mySql9.setSqlId(sqlid9);//指定使用的Sql的id
	 	mySql9.addSubPara(document.all('ApntOccupationCode').value);//指定传入的参数
	 	var strSql=mySql9.getString();
	    var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.ApntOccupationCodeName.value = arrResult[0][0];
	      }
	}
	//alert(fm.AppntRelationToInsured.value);
	//alert(fm.RelationToAppnt.value);
    if ((fm.AppntRelationToInsured.value!="" && fm.AppntRelationToInsured.value=="00") || (fm.RelationToAppnt.value!="" && fm.RelationToAppnt.value=="00")) {}
    else{
	    document.all("divSamePerson").style.display = "none";
    } 
    //alert(fm.AppntRelationToInsured.value);
    //alert(fm.RelationToAppnt.value);
	if(document.all("AppntIDType").value!="")
	showOneCodeName('idtype','AppntIDType','AppntIDTypeName');	
	if(fm.NewBankCode.value!="")
	showOneCodeName('bank','NewBankCode','AppntBankCodeName');	
	if(fm.BankCode.value!="")
	showOneCodeName('bank','BankCode','ReNBankCodeName');	
	if(fm.IDType.value!="")
	showOneCodeName('idtype','IDType','IDTypeName');
	//初始化只读信息
	//if(fm.ManageCom.value!="")
	//showOneCodeName('comcode','ManageCom','ManageComName');
	//if(fm.AgentCom.value!="")
	//showOneCodeName('agentcom','AgentCom','InputAgentComName');
	//if(fm.AgentManageCom.value!="")
	//showOneCodeName('comcode','AgentManageCom','AgentManageComName');
	if(fm.SaleChnl.value!="")
	showOneCodeName('salechnl','SaleChnl','SaleChnlName');
	if(fm.ApntSex.value!="")
	showOneCodeName('sex','ApntSex','ApntSexName');
	if(fm.AppntRelationToInsured.value!="")
	showOneCodeName('relation','AppntRelationToInsured','RelationToInsuredName');
	if(fm.ApntNativePlace.value!="")
	showOneCodeName('nativeplace','ApntNativePlace','ApntNativePlaceName');
	if(fm.ApntMarriage.value!="")
	showOneCodeName('marriage','ApntMarriage','ApntMarriageName');
	//if(fm.ApntOccupationCode.value!="")
	//showOneCodeName('occupationcode','ApntOccupationCode','ApntOccupationCodeName');
	if(fm.ApntOccupationType.value!="")
	showOneCodeName('occupationtype','ApntOccupationType','ApntOccupationTypeName');
	
	if(fm.NewPayMode.value!="")
	showOneCodeName('paylocation2','NewPayMode','PayModeName');
	if(fm.PayLocation.value!="")
	showOneCodeName('paylocation','PayLocation','PayLocationName');
	if(fm.RelationToMainInsured.value!="")
	showOneCodeName('relation','RelationToMainInsured','RelationToMainInsuredName');
	if(fm.RelationToAppnt.value!="")
	showOneCodeName('relation','RelationToAppnt','RelationToAppntName');
	if(fm.Sex.value!="")
	showOneCodeName('sex','Sex','SexName');
	if(fm.NativePlace.value!="")
	showOneCodeName('nativeplace','NativePlace','NativePlaceName');
	if(fm.Marriage.value!="")
	showOneCodeName('marriage','Marriage','MarriageName');
	if(fm.OccupationCode.value!="")
	{
		//var strSql = "select OccupationName from LDOccupation where occupationver = '002' and OccupationCode='"+document.all('OccupationCode').value+"'";
		var  sqlid10="PolModifyInputSql9";
	 	var  mySql10=new SqlClass();
	 	mySql10.setResourceName("uw.PolModifyInputSql"); //指定使用的properties文件名
	 	mySql10.setSqlId(sqlid10);//指定使用的Sql的id
	 	mySql10.addSubPara(document.all('OccupationCode').value);//指定传入的参数
	 	var strSql=mySql10.getString();
	    var arrResult = easyExecSql(strSql);
	       //alert(arrResult);
	    if (arrResult != null) {      
	      fm.OccupationCodeName.value = arrResult[0][0];
	      }
	}
	//if(fm.OccupationCode.value!="")
	//showOneCodeName('occupationcode','OccupationCode','OccupationCodeName');
	if(fm.OccupationType.value!="")
	showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
	if(fm.LiveGetMode.value!="")
	showOneCodeName('livegetmode','LiveGetMode','LiveGetModeName');
	if(fm.BonusGetMode.value!="")
	showOneCodeName('livegetmode','BonusGetMode','BonusGetModeName');
	
}

//*************************************************************
//首续期账号一致选择框事件
function theSameToFirstAccount() {
  //打钩的情况
  if (fm.theSameAccount.checked == true) {
    document.all('BankCode').value = document.all('NewBankCode').value;
    document.all('AccName').value = document.all('NewAccName').value;
    document.all('BankAccNo').value = document.all('NewBankAccNo').value;
  }
  //对应不选的情况
  else if (fm.theSameAccount.checked == false) {
    document.all('BankCode').value = '';
    document.all('AccName').value = '';
    document.all('BankAccNo').value = '';
    document.all('ReNBankCodeName').value = '';    
  }  
  
  if(fm.BankCode.value!="")
	showOneCodeName('bank','BankCode','ReNBankCodeName');

} 

//*************************************************************
//投保人与被保人相同选择框事件
function isSamePerson() {
  //对应未选同一人，又打钩的情况
/*
  if (((fm.AppntRelationToInsured.value!="" && fm.AppntRelationToInsured.value!="00") || fm.RelationToAppnt.value!="00") && fm.SamePersonFlag.checked == true) {
    fm.SamePersonFlag.checked = false;
    alert("投保人与被保人关系不是本人，不能进行该操作！");
    return false;
  } 
  */
  if(fm.SamePersonFlag.checked == true)
  for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
	  if (fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
	    try {
	      insuredName = fm.elements[elementsNum].name.substring(fm.elements[elementsNum].name.indexOf("t") + 1);
	        //alert(insuredName);
	        //alert(fm.elements[elementsNum].value);
	        document.all(insuredName).value = fm.elements[elementsNum].value;
	    }
	    catch (ex) {}
	  }
	}
  
} 

function afterCodeSelect( cCodeName, Field )
{
 	//alert("cCodeName==="+cCodeName);
	 //自动填写受益人信息
      if (cCodeName == "customertypea") {
        if (Field.value == "0") {
          //alert("1111111");          
          var index = LCBnfGrid.mulLineCount;
          LCBnfGrid.setRowColData(index-1, 2, document.all("AppntName").value);
          LCBnfGrid.setRowColData(index-1, 3, document.all("AppntIDType").value);
          LCBnfGrid.setRowColData(index-1, 4, document.all("AppntIDNo").value);
          //tongmeng 2007-12-18 Modify
          //修改受益人关系问题
          LCBnfGrid.setRowColData(index-1, 5,document.all( "AppntRelationToInsured").value);
          LCBnfGrid.setRowColData(index-1, 8, document.all("AppntHomeAddress").value);
        }
        else if (Field.value == "1") {
        //alert("2222222");
          var index = LCBnfGrid.mulLineCount;
        LCBnfGrid.setRowColData(index-1, 2, document.all("Name").value);
        LCBnfGrid.setRowColData(index-1, 3, document.all("IDType").value);
        LCBnfGrid.setRowColData(index-1, 4, document.all("IDNo").value);
        LCBnfGrid.setRowColData(index-1, 5, "00");
        LCBnfGrid.setRowColData(index-1, 8, document.all("HomeAddress").value);
				//tongmeng 2007-12-28 add
					//身故受益人不能为本人
		var tempDeadBnf = LCBnfGrid.getRowColData(index-1, 1);
          if(LCBnfGrid==null||trim(LCBnfGrid)=='')
          {
          	//默认为生存受益人
          	tempDeadBnf = '0'
          }
          if(tempDeadBnf=='1')
          {
          	alert('身故受益人不能为本人!');
          	//alert("3333333");
          	LCBnfGrid.setRowColData(index-1, 2, '');
          	LCBnfGrid.setRowColData(index-1, 3, '');
          	LCBnfGrid.setRowColData(index-1, 4, '');
          	LCBnfGrid.setRowColData(index-1, 5, '');
          	LCBnfGrid.setRowColData(index-1, 8, '');
          	return ;
          }
        }
        return;
    }    
}

//被保人年龄龄<被保人年龄应该为投保日期与生日之差;2005-11-18修
function getAge2()
{
	if(fm.Birthday.value=="")
	{
		return;
	}
	if(fm.Birthday.value.indexOf('-')==-1)
	{
		var Year =     fm.Birthday.value.substring(0,4);
		var Month =    fm.Birthday.value.substring(4,6);
		var Day =      fm.Birthday.value.substring(6,8);
		fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	}
  	else
  	{
    	var Year1 =     fm.Birthday.value.substring(0,4);
	    var Month1 =    fm.Birthday.value.substring(5,7);
	    var Day1 =      fm.Birthday.value.substring(8,10);	
	    fm.Birthday.value = Year1+"-"+Month1+"-"+Day1;	
	}
	if(calAge(fm.Birthday.value)<0)
  	{
		alert("出生日期只能为当前日期以前");
		fm.InsuredAppAge.value="";
		return;
    }
    fm.InsuredAppAge.value=calPolAppntAge(fm.Birthday.value,fm.PolAppntDate.value);
  	return ;
}
/**
* 计算投保人被保人年龄《投保日期与生日之差=投保人被保人年龄》,2005-11-18日添加
* 参数，出生日期yy－mm－dd；投保日期yy－mm－dd
* 返回  年龄
*/
function calPolAppntAge(BirthDate,PolAppntDate)
{
	var age ="";
	if(BirthDate=="" || PolAppntDate=="")
	{
		age ="";
		return age;
	}
	var arrBirthDate = BirthDate.split("-");
	if (arrBirthDate[1].length == 1) arrBirthDate[1] = "0" + arrBirthDate[1];
	if (arrBirthDate[2].length == 1) arrBirthDate[2] = "0" + arrBirthDate[2];
	
	var arrPolAppntDate = PolAppntDate.split("-");
	if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
	if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];

	if(arrPolAppntDate[0]<=99)
	{
		arrBirthDate[0]=	arrBirthDate[0]-1900;
	}
	age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
	
	//当前月大于出生月
	if (arrPolAppntDate[1] > arrBirthDate[1]) 
	{
		age = age + 1;
		return age;
	}
	//当前月小于出生月
	else if (arrPolAppntDate[1] < arrBirthDate[1]) 
	{
		return age;
	}
  	//当前月等于出生月的时候，看出生日
	else if (arrPolAppntDate[2] >= arrBirthDate[2])
	{
		age = age + 1;
		return age;
  	}
	else
	{
		return age;
	}
}

//投保人年龄<投保人被保人年龄应该为投保日期与生日之差;2005-11-18修改>
function getAge()
{
	if(fm.AppntBirthday.value=="")
	{
		return;
	}
	if(fm.AppntBirthday.value.indexOf('-')==-1)
	{
		var Year =fm.AppntBirthday.value.substring(0,4);
		var Month =fm.AppntBirthday.value.substring(4,6);
		var Day =fm.AppntBirthday.value.substring(6,8);
		fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
	}
	if(calAge(fm.AppntBirthday.value)<0)
	{
		alert("出生日期只能为当前日期以前!");
		return;
	}
	fm.AppntAge.value=calPolAppntAge(fm.AppntBirthday.value,fm.PolAppntDate.value);
  	return ;
}
//查询操作员随动速度
function initQueryRollSpeed()
{
    //var strSQL = "select SYSVAR,SYSVARVALUE from LDSysvar where SYSVAR like 'ROLLSPEED%25' order by SYSVAR ASC";
    var  sqlid11="PolModifyInputSql10";
 	var  mySql11=new SqlClass();
 	mySql11.setResourceName("uw.PolModifyInputSql"); //指定使用的properties文件名
 	mySql11.setSqlId(sqlid11);//指定使用的Sql的id
 	var strSQL=mySql11.getString();
	var arrSpeed = easyExecSql(strSQL); 
	if (arrSpeed != null)
	{
    	fm.RollSpeedBase.value = arrSpeed[0][1];
    	fm.RollSpeedSelf.value = arrSpeed[1][1];
	}

    //strSQL = "select rollspeed from LDRollSpeed where UserCode='" + fm.Operator.value + "'";
    var  sqlid12="PolModifyInputSql11";
 	var  mySql12=new SqlClass();
 	mySql12.setResourceName("uw.PolModifyInputSql"); //指定使用的properties文件名
 	mySql12.setSqlId(sqlid12);//指定使用的Sql的id
 	mySql12.addSubPara(fm.Operator.value);//指定传入的参数
 	 strSQL=mySql12.getString();
	arrSpeed1 = null;
	arrSpeed1 = easyExecSql(strSQL); 
	if (arrSpeed1 != null)
	{
    	fm.RollSpeedOperator.value = arrSpeed1[0][0];
	}
    else
        fm.RollSpeedOperator.value = 1;
}

function disableAllNeed()
{
	//document.all('AgentCode').readonly=true;
}