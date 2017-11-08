var turnPage = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mSwitch = parent.VD.gVSwitch;

//提交后操作,服务器数据返回后执行的操作
function afterSubmit1( FlagStr, content )
{
	
	showInfo.close();     
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	divCustomerContInfo.style.display="none";
	var showStr="正在刷新数据，请您稍候";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	lockScreen('lkscreen');
	top.opener.initForm();
	showInfo.close();     
	unlockScreen('lkscreen');
	top.close();
}

         

//Click事件，当点击“查询”图片时触发该函数
function queryClick() {
	var tCustomerno = fm.OldCustomerno.value;
	 if ((tCustomerno == "" || tCustomerno == null) ) {
			alert("请输入客户号再进行查询！");
			return;	 	
	 }
//	 OPolGrid.clearData();
	 ClientList.clearData();

   var strSQL = "";      
   	 
var sqlid42="ContPolinputSql42";
var mySql42=new SqlClass();
mySql42.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
mySql42.setSqlId(sqlid42);//指定使用的Sql的id
mySql42.addSubPara(tCustomerno);//指定传入的参数
strSQL=mySql42.getString();		 
	 
  //查询客户号码，去掉原来的注释  modified by 李林森 2011-9-14 17:07:34    
if(_DBT==_DBO){
//	 strSQL = "select a.customerno,a.name,a.sex,a.Birthday, a.IDType,a.idno, a.othidtype,a.othidno,"
//	   		+ " b.postaladdress,b.addressno,b.mobile,b.phone,'',''"
//	   		+ " from ldperson a,lcaddress b where 1=1 "
//	   		+ " and a.customerno=b.customerno"
//	   		+ " and rownum=1"
//	   		+ " and a.customerno='"+tCustomerno+"'"
//		    + " order by b.addressno desc ";
	 
		var sqlid0="CustomerForceUnionInputSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("app.CustomerForceUnionInputSql"); //指定使用的properties文件名
		mySql0.setSqlId(sqlid0);//指定使用的Sql的id
		mySql0.addSubPara(tCustomerno);//指定传入的参数
		strSQL=mySql0.getString();
	 
}else if(_DBT==_DBM){
//	strSQL = "select a.customerno,a.name,a.sex,a.Birthday, a.IDType,a.idno, a.othidtype,a.othidno,"
//   		+ " b.postaladdress,b.addressno,b.mobile,b.phone,'',''"
//   		+ " from ldperson a,lcaddress b where 1=1 "
//   		+ " and a.customerno=b.customerno"
//   		+ " and a.customerno='"+tCustomerno+"' limit 1"
//	    + " order by b.addressno desc ";
	
	var sqlid0="CustomerForceUnionInputSql1";
	var mySql0=new SqlClass();
	mySql0.setResourceName("app.CustomerForceUnionInputSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(tCustomerno);//指定传入的参数
	strSQL=mySql0.getString();
}
 
   
	turnPage.queryModal(strSQL, ClientList);

}           

function customerUnion() {
	var selno = ClientList.getSelNo()-1;
	if (selno <0)
	{
		alert( "请先选择相同的投保人" );
		return;
	}	

//	divCustomerUnion.style.display="";
	
	//var CustomerNo_OLD = ClientList.getRowColData(0, 1);
	var CustomerNo_NEW = ClientList.getRowColData(selno, 1);
	var CustomerName = ClientList.getRowColData(selno, 2);
	//alert(CustomerNo_NEW);
	//alert(CustomerName);
	
	//fm.CustomerNo_OLD.value = CustomerNo_OLD;
	fm.CustomerNo_NEW.value = CustomerNo_NEW;
	//fm.CustomerName.value = CustomerName;
	divCustomerContInfo.style.display="";
	QueryAllPol(CustomerNo_NEW);
}


//提交，保存按钮对应操作 
function ClientMerge() {
//      var selno1 = OPolGrid.getSelNo()-1;
//	  var selno2 = ClientList.getSelNo()-1;
//	  if(selno2<0||selno1<0)
//	  {
//	  	 alert('请选择需要合并的客户');
//	  	 return false;
//	  }
//	  //alert(selno2+":"+selno1);
//	  var birthday1 = OPolGrid.getRowColData(selno1, 4);
//	  var birthday2 = ClientList.getRowColData(selno2, 4);
//	  var sex1 = OPolGrid.getRowColData(selno1, 3);
//	  var sex2 = ClientList.getRowColData(selno2, 3);
//	  var name1 = OPolGrid.getRowColData(selno1, 2);
//	  var name2 = ClientList.getRowColData(selno2, 2);
//	  var idtype1 = OPolGrid.getRowColData(selno1, 5);
//	  var idtype2 = ClientList.getRowColData(selno2, 5);
//	  var idno1 = OPolGrid.getRowColData(selno1, 6);
//	  var idno2 = ClientList.getRowColData(selno2, 6);
  
	if (fm.CustomerNo_OLD.value == null || fm.CustomerNo_OLD.value == "")
	{
		alert("请选择要合并的原客户");
		return;
	}
	if (fm.CustomerNo_NEW.value == null || fm.CustomerNo_NEW.value == "")
	{
		alert("请选择要合并的原客户");
		return;
	}
	
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.action =  "../app/CustomerMergeSave.jsp";
  document.getElementById("fm").submit(); //提交 

}


function exchangeCustomerNo()
{
	var exchangeValue="";
	for(i = 0; i < fm.exchangeRadio.length; i++)
	{   
		if(fm.exchangeRadio[i].checked)
		{ 
			exchangeValue = fm.exchangeRadio[i].value;
			break;                         
		}
	}		

	if(exchangeValue == "1")
	{
	   //反向
		var selno = ClientList.getSelNo()-1;
		if (selno <0)
		{
			alert( "请先选择相同的投保人" );
			return;
		}	
		fm.CustomerNo_NEW.value = OPolGrid.getRowColData(0, 1);
		
	}
	if(exchangeValue == "-1")
	{
	   //正向
		var selno = ClientList.getSelNo()-1;
		if (selno <0)
		{
			alert( "请先选择相同的投保人" );
			return;
		}
		var selno1 = 	OPolGrid.getSelNo()-1;
		fm.CustomerNo_NEW.value = ClientList.getRowColData(selno, 1);
			
	}
}

function displayCustomer(){
//	alert(383);
	var arrResult1;
	if(Hole_CustomerNo==""||Hole_CustomerNo=="null"||Hole_CustomerNo==null){
		return;
	}
	
	var sqlid43="ContPolinputSql43";
var mySql43=new SqlClass();
mySql43.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
mySql43.setSqlId(sqlid43);//指定使用的Sql的id
mySql43.addSubPara(PtrNo);//指定传入的参数
mySql43.addSubPara(Hole_CustomerNo);//指定传入的参数
	var CustomerSql=mySql43.getString();	
	
//	var CustomerSql = "select a.appntno,a.appntname,a.appntsex,a.appntbirthday,a.idtype,a.idno,'','' "
//				+ ",b.PostalAddress,b.mobile,b.phone,b.grpname,c.managecom,(select d.name from laagent d where d.agentcode=c.agentcode) "
//				+ " from lcappnt a ,lcaddress b,lccont c "
//				+ " where c.contno='"+PtrNo+"' and a.contno=c.contno and a.appntno='"+Hole_CustomerNo
//				+ "' and b.customerno=a.appntno and b.addressno=a.addressno ";

	turnPage.queryModal(CustomerSql, OPolGrid);
//	alert(16);
//	arrResult1=easyExecSql(CustomerSql,1,0);
//	if (arrResult!="")
//	{
//		displayMultiline(arrResult1,OPolGrid);
//	}
}


/**
  * 15位身份证号码转化为18位的身份证。
  * 如果是18位的身份证则直接返回，不作任何变化。
  * @param idCard,15位的有效身份证号码
  * @return idCard18 返回18位的有效身份证
  */
 function IdCard15to18(idCard){
  idCard = idCard.trim();
  var idCard18 = new String(idCard);
  //校验码值
  var checkBit = ['1','0','X','9','8','7','6','5','4','3','2'];
  var sum = 0;
  //alert("idCard18="+idCard18.length);
  
  //15位的身份证
  if(idCard!=null && idCard.length==15){
  
   idCard18 = idCard18.substring(0,6)+"19"+idCard18.substring(6,15);

   for(var index=0;index<idCard18.length;index++){
    var c = idCard18.charAt(index);
    var ai = parseInt(c);
    //加权因子的算法
    var Wi = (Math.pow(2, idCard18.length-index))%11;
    sum = sum+ai*Wi;
   }
   var indexOfCheckBit = sum%11; //取模
   idCard18 = idCard18+(checkBit[indexOfCheckBit]);
  }
  
  //alert("idCard18="+idCard18);
  return idCard18;
 }
 
 /**
  * 转化18位身份证位15位身份证。如果输入的是15位的身份证则不做任何转化，直接返回。
  * @param idCard 18位身份证号码
  * @return idCard15
  */
 function IdCard18to15(idCard){
   idCard = idCard.trim();
  var idCard15 = new String(idCard);
  if(idCard!=null && idCard.length==18){
  
   idCard15 = idCard15.substring(0, 6)+idCard15.substring(8, 17);
   
  }
  //alert("idCard15="+idCard15);
  return idCard15;
  
 }

function QueryAllPol(tCustomerNo)
{
	initCustomerContGrid();
	
		var sqlid44="ContPolinputSql44";
var mySql44=new SqlClass();
mySql44.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
mySql44.setSqlId(sqlid44);//指定使用的Sql的id
mySql44.addSubPara(tCustomerNo);//指定传入的参数
mySql44.addSubPara(tCustomerNo);//指定传入的参数
	var tSQL_CustomerPol=mySql44.getString();	
	
//	var tSQL_CustomerPol = "select a.grpcontno,a.contno,a.prtno "
//						 + " ,b.name,b.sex,b.birthday,b.idtype,b.idno,c.PostalAddress,c.mobile,c.phone,c.grpname "
//	                     + " ,d.appntname,d.appntsex,d.appntbirthday,d.idtype,d.idno,e.PostalAddress,e.mobile,e.phone,e.grpname "
//	                     + " ,a.managecom,(select f.name from laagent f where f.agentcode=a.agentcode) "
//	                     + " ,(case when (a.appflag='1' or a.appflag='4') then  to_char(a.cvalidate,'yyyy-mm-dd') when a.appflag='0' then '' end ),a.conttype "
//	                     + " from lccont a,lcinsured b,lcaddress c,lcappnt d, lcaddress e "
//                       + " where a.contno=b.contno and b.insuredno='"+tCustomerNo+"' "
//                       + " and c.customerno=b.insuredno and c.addressno=b.addressno "
//                       + " and d.contno=a.contno "
//                       + " and e.customerno=d.appntno and e.addressno=d.addressno "
//                       + " and a.appflag in ('0','1','4') "
//										   + " union "
//                       + "select a.grpcontno,a.contno,a.prtno "
//						 + " ,b.name,b.sex,b.birthday,b.idtype,b.idno,c.PostalAddress,c.mobile,c.phone,c.grpname "
//	                     + " ,d.appntname,d.appntsex,d.appntbirthday,d.idtype,d.idno,e.PostalAddress,e.mobile,e.phone,e.grpname "
//	                     + " ,a.managecom,(select f.name from laagent f where f.agentcode=a.agentcode) "
//	                     + " ,(case when (a.appflag='1' or a.appflag='4') then  to_char(a.cvalidate,'yyyy-mm-dd') when a.appflag='0' then '' end ),a.conttype "
//	                     + " from lccont a,lcinsured b,lcaddress c,lcappnt d, lcaddress e "
//                       + " where a.contno=b.contno "
//                       + " and c.customerno=b.insuredno and c.addressno=b.addressno "
//                       + " and d.contno=a.contno and d.appntno='"+tCustomerNo+"' "
//                       + " and e.customerno=d.appntno and e.addressno=d.addressno "
//                       + " and a.appflag in ('0','1','4') ";
	turnPage3.queryModal(tSQL_CustomerPol, CustomerContGrid);
}

function getContDetailInfo()
{
    var tsel = CustomerContGrid.getSelNo() - 1;
    if (tsel < 0)
    {
        alert("请选择保单！");
        return;
    }
    //09-12-22 lixiang  由于上一版本对查询的mulitLine的内容进行过修改，导致原来的conttype
    //变成了现在的idtype  所以会导致无法打开保单明细页面 
    //考虑到客户关联不会涉及到团险，所以此处修改为将conttype写死为1
//    var tContType = CustomerContGrid.getRowColData(tsel, 7);
    var tContType = "1";
//alert(tContType);
    if (tContType == "1")
    {
    	//tongmeng 2009-06-09 modify
    	//修改投保单显示逻辑
        //var tSql = "select salechnl from lccont where contno='"+ContGrid.getRowColData(ContGrid.getSelNo()-1,1)+"'";
       /* var tSql = "select case lmriskapp.riskprop"
                + " when 'I' then"
                + " '1'"
                + " when 'G' then"
                + " '2'"
                + " when 'Y' then"
                + " '3'"
                + " end"
                + " from lmriskapp"
                + " where riskcode in (select riskcode"
                + " from lcpol"
                + " where polno = mainpolno"
                + " and contno = '" + CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 2) + "')"
    
        var BankFlag = ""
        var brrResult = easyExecSql(tSql);
        if (brrResult != null)
        {
            BankFlag = brrResult[0][0];
        }*/
        
       var  tSplitPrtNo = CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 3).substring(0,4);
    var BankFlag = "";
    var SubType = "";
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635、8625、8615走银代投保单界面，其余的都走个险界面
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	//判断是否为银邮保通  如果是银邮保通 BankFlag=3 subtype='TB1003'
    	//否则与个险一样
    	var prtno = CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 3);
		
				var sqlid45="ContPolinputSql45";
var mySql45=new SqlClass();
mySql45.setResourceName("app.ContPolinputSql"); //指定使用的properties文件名
mySql45.setSqlId(sqlid45);//指定使用的Sql的id
mySql45.addSubPara(prtno);//指定传入的参数
	var CheckSql=mySql45.getString();	
		
    	//var CheckSql = "select 1 from lccont where prtno='"+prtno+"' and salechnl='03' and selltype='08'";
    	var arr = easyExecSql(CheckSql);//prompt("",CheckSql);
        if(!arr){
        	BankFlag="1";
        	SubType = "01";
        }else {
        	BankFlag="3";
        	SubType = "03";
        }
    }else{
    	BankFlag="1";
    	SubType = "01";
    }
        window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo=" + CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 3) + "&ContNo=" + CustomerContGrid.getRowColData(CustomerContGrid.getSelNo() - 1, 2) + "&BankFlag=" + BankFlag+"&SubType="+SubType, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
    }
    
    if (tContType == "2")
    {
        cContNo=CustomerContGrid.getRowColData(tsel,2);
        cPrtNo=CustomerContGrid.getRowColData(tsel,3);;
        cGrpContNo=CustomerContGrid.getRowColData(tsel,1);
        mSwitch.deleteVar( "ContNo" );
      	mSwitch.addVar( "ContNo", "", cContNo );
      	mSwitch.updateVar("ContNo", "", cContNo);
      	mSwitch.deleteVar( "ProposalContNo" );
      	mSwitch.addVar( "ProposalContNo", "", cContNo );
      	mSwitch.updateVar("ProposalContNo", "", cContNo);
      	mSwitch.deleteVar( "PrtNo" );
      	mSwitch.addVar( "PrtNo", "", cPrtNo );
      	mSwitch.updateVar("PrtNo", "", cPrtNo);
      	mSwitch.deleteVar( "GrpContNo" );
      	mSwitch.addVar( "GrpContNo", "", cGrpContNo );
      	mSwitch.updateVar("GrpContNo", "", cGrpContNo);
  	    //window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&Auditing=1","window1");
  		window.open("../sys/AllProQueryGMain.jsp?LoadFlag=16&checktype=2&ContType=2&Auditing=1&ProposalGrpContNo="+cGrpContNo+"&ContNo="+cContNo+"&NameFlag=0","window1",sFeatures);
    }
}