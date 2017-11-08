//程序名称：FinInterfaceCheck.js
//程序功能：凭证核对
//创建日期：2007-10-23 
//创建人  ：m
//更新记录：  更新人    更新日期     更新原因/内容

var turnPage = new turnPageClass();//日结试算相关数据
var turnPage2 = new turnPageClass();//日结确认相关数据
var mFlag = "0";
var showInfo;

/****************************************************
*导出相关数据财务接口报表分明细和期间的统计
*********************************************************/ 
function ToExcel()
{
	if(CheckQueryDataGrid.mulLineCount=="0"){
		alert("没有查询到数据");
		return false;
	} 	 	
	fm.action="./FinInterfaceCheckExcel.jsp";
	fm.target="fraSubmit";
	document.getElementById("fm").submit(); //提交
}
/************************
*PDF打印功能实现 
***************************/
function printFinInterface(){
	
	if(CheckQueryDataGrid.getSelNo()){	  	
		    var i = 0;
			var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
			var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		 	fm.action = "./TestInterfacePrintPDF.jsp?operator=printPDF";//测试PDF打印相关数据
		 	fm.target=".";	 
		 	document.all("serialNo").value = CheckQueryDataGrid.getRowColData(CheckQueryDataGrid.getSelNo()-1, 1);
		 	document.getElementById("fm").submit();
		 	showInfo.close();  
      }else{
        alert("请选择一条记录!");
      }

}
/********************
*对页面的条件限制
************************/
function beforeSubmit(){
	if(fm.checkType.value==''||fm.checkType.value==null){
		alert("请选择核对类型");
		fm.checkType.focus();
		return false;
	}
	  	
   if(fm.checkType.value=="1"){
	  if(fm.accountCode.value==''||fm.accountCode.value==null){
	  			alert("请选择科目类型及科目");
	  			fm.accountCodeType.focus();
	  			return false;
	  	}	  	
	  if(fm.FinItemType.value==''||fm.FinItemType.value==null){
	  			alert("请选择借贷标志");
	  			fm.FinItemType.focus();
	  			return false;
	  	}
	  if(fm.ManageCom.value==''||fm.ManageCom.value==null){
	  			alert("请选择管理机构");
	  			fm.ManageCom.focus();
	  			return false;
	  	}		  	  	
	  if(fm.StartDay.value==''||fm.StartDay.value==null){
	  			alert("请选择起始日期");
	  			fm.StartDay.focus();
	  			return false;
	  	}
	  if(fm.EndDay.value==''||fm.EndDay.value==null){
	  			alert("请选择终止日期");
	  			fm.EndDay.focus();
	  			return false;
	  	}
	  if(fm.EndDay.value<fm.StartDay.value){
					alert("起始时间大于终止时间,请重新输入!");
					fm.StartDay.focus();
					return false;
	　　}	  		  		  		  	
	 }
   else if(fm.checkType.value=="2"){
 	  if(fm.ContType.value==''||fm.ContType.value==null){
	  			alert("请输入业务号码类型！");
	  			fm.ContType.focus();
	  			return false;
	  	}    
	  if(fm.tNo.value==''||fm.tNo.value==null){
	  			alert("请输入业务号码！");
	  			fm.tNo.focus();
	  			return false;
	  	}
	 }	  
   else if(fm.checkType.value=="3"){
 	  if(fm.SClassType.value==''||fm.SClassType.value==null){
	  			alert("请输入凭证类型！");
	  			fm.SClassType.focus();
	  			return false;
	  	}    
	  if(fm.SDate.value==''||fm.SDate.value==null){
	  			alert("请输入起始日期！");
	  			fm.SDate.focus();
	  			return false;
	  	}
	  if(fm.EDate.value==''||fm.EDate.value==null){
	  			alert("请输入结束日期！");
	  			fm.EDate.focus();
	  			return false;
	  	}
	 }	
	else if (fm.checkType.value=="4")
		{
				  if(fm.AccountID.value==''||fm.AccountID.value==null){
	  			alert("请输入凭证号码！");
	  			fm.AccountID.focus();
	  			return false;
	  	}
		}
	return true;
}

/*********************************************************************
 *  后台执行完毕反馈信息
 *  描述: 后台执行完毕反馈信息
 *********************************************************************/
//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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
    //执行下一步操作
  }
}
// add 2006-9-30 11:49
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}

function CheckQueryData()
{
  try
  {
	if(beforeSubmit()){
	
		var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	
	CheckQueryDataGrid.clearData("CheckQueryDataGrid");   
	var strSQL = "";
	/**
		strSQL="select CostId,CostName from FICostTypeDef order by CostId";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FinInterfaceCheckSql"); //指定使用的properties文件名
		mySql1.setSqlId("FinInterfaceCheckSql1");//指定使用的Sql的id
		mySql1.addSubPara(1);//指定传入的参数
		strSQL= mySql1.getString();
	
			var strQueryResultC = easyQueryVer3(strSQL,1,1,1);
			var	tArrayC = decodeEasyQueryResult(strQueryResultC);

    if(fm.checkType.value=="2"){//核对类型^1|科目 ^2|业务代码	
			
		var tNoType = "";
		if(fm.ContType.value=="1"||fm.ContType.value=="3")  //个险保单号和个险投保单号
			tNoType = "a.businessno03";
		else if(fm.ContType.value=="2"||fm.ContType.value=="4") //团险保单号和团险投保单号
			tNoType = "a.grpcontno";
		else if(fm.ContType.value=="5") //暂交费号
			tNoType = "a.BusinessNo";
		else if(fm.ContType.value=="6") //实付号
			tNoType = "a.BusinessNo06";
		else if(fm.ContType.value=="7") //实收号
			tNoType = "a.BusinessNo05";
		else if(fm.ContType.value=="8") //个险保全批单号
			tNoType = "a.BusinessNo07";
		else if(fm.ContType.value=="9") //团险保全批单号
			tNoType = "a.endorsementno";
		else if(fm.ContType.value=="10") //赔案号
			tNoType = "a.BusinessNo08";
		/**	
		strSQL = "select distinct "
             +"a.batchno as batchno, "
             +"a.StringInfo01 as managecom, "
             +"b.finitemtype as listflag, "
             +"b.accountcode as finiteminfo, "
             +"(select certificatename from FICertificateTypeDef where certificateid = a.certificateid) as classinfo, "
             +"a.StringInfo05 as riskcode, "
             +"b.salechnl as salechnl, "
             +"b.accountdate as accountdate, "
             +"a.businessno03 as contno, "
             +"a.StringInfo15 as bankdetail, "
             +"a.StringInfo09 as budget, "
             +"b.costcenter as costcenter, "
             +"to_char(b.summoney,'FM999999999990.00') as money, "
             +"b.certificateid as classtype, "
             +"(select keyname from FICostDataKeyDef where Keyid = 'BusinessNo' and acquisitionid = a.acquisitionid) as keyvaluename, "
             +"a.businessno as orzno "
             +"from  FIAboriginalData a,FIDataTransResult b "
	       		 +" where a.aserialno = b.aserialno "
	       		 +" and b.summoney<>0"
             +" and a.Stringinfo01 like '" +manageCom + "%%'";
		*/
			var mySql2=new SqlClass();
				mySql2.setResourceName("fininterface.FinInterfaceCheckSql"); //指定使用的properties文件名
				mySql2.setSqlId("FinInterfaceCheckSql2");//指定使用的Sql的id
				mySql2.addSubPara(manageCom);//指定传入的参数
				strSQL= mySql2.getString();
		if(fm.ContType.value=="8"){
		/**
			strSQL = strSQL + "and "+tNoType+" in trim((select edoracceptno from lpedormain where edorno = '" + trim(fm.tNo.value) + "'))";
			*/
			var mySql3=new SqlClass();
				mySql3.setResourceName("fininterface.FinInterfaceCheckSql"); //指定使用的properties文件名
				mySql3.setSqlId("FinInterfaceCheckSql3");//指定使用的Sql的id
				mySql3.addSubPara(manageCom);//指定传入的参数
				mySql3.addSubPara(tNoType);//指定传入的参数
				mySql3.addSubPara(fm.tNo.value);//指定传入的参数
				strSQL= mySql3.getString();
			}
		else if (fm.ContType.value=="9"){
		/**
		  strSQL = strSQL + "and "+tNoType+" in trim((select edoracceptno from lpedormain where edorno = '" + trim(fm.tNo.value) + "'))";
		  	*/
		  	var mySql4=new SqlClass();
				mySql4.setResourceName("fininterface.FinInterfaceCheckSql"); //指定使用的properties文件名
				mySql4.setSqlId("FinInterfaceCheckSql4");//指定使用的Sql的id
				mySql4.addSubPara(manageCom);//指定传入的参数
				mySql4.addSubPara(tNoType);//指定传入的参数
				mySql4.addSubPara(fm.tNo.value);//指定传入的参数
				strSQL= mySql4.getString();
		  }	
		else{
		/**
			strSQL = strSQL + " and "+ tNoType +" = '" + trim(fm.tNo.value) + "'";
			*/
			var mySql5=new SqlClass();
				mySql5.setResourceName("fininterface.FinInterfaceCheckSql"); //指定使用的properties文件名
				mySql5.setSqlId("FinInterfaceCheckSql5");//指定使用的Sql的id
				mySql5.addSubPara(manageCom);//指定传入的参数
				mySql5.addSubPara(tNoType);//指定传入的参数
				mySql5.addSubPara(fm.tNo.value);//指定传入的参数
				strSQL= mySql5.getString();
		}
			/**
			strSQL = strSQL +" order by orzno,accountdate";
			*/
    }
    else if(fm.checkType.value=="1"){//核对类型^1|科目 ^2|业务代码

       	strSQL =  "select distinct "
             +"a.batchno as batchno, "
             +"a.StringInfo01 as managecom, "
             +"b.finitemtype as listflag, "
             +"b.accountcode as finiteminfo, "
             +"(select certificatename from FICertificateTypeDef where certificateid = a.certificateid) as classinfo, "
             +"a.StringInfo05 as riskcode, "
             +"b.salechnl as salechnl, "
             +"b.accountdate as accountdate, "
             +"a.businessno03 as contno, "
             +"a.StringInfo15 as bankdetail, "
             +"a.StringInfo09 as budget, "
             +"b.costcenter as costcenter, "
             +"to_char(b.summoney,'FM999999999990.00') as money, "
             +"b.certificateid as classtype, "
             +"(select keyname from FICostDataKeyDef where Keyid = 'BusinessNo' and acquisitionid = a.acquisitionid) as keyvaluename, "
             +"a.businessno as orzno "
             +"from  FIAboriginalData a,FIDataTransResult b "
	       		 +" where a.aserialno = b.aserialno "
	       		 +" and b.accountcode like '"+fm.accountCode.value+"%%'"
	       		 +" and b.summoney<>0"
             +" and a.Stringinfo01 like '" +fm.ManageCom.value + "%%'"
       		   +" and a.accountdate >= '" +fm.StartDay.value + "'"
       		   +" and a.accountdate <= '" +fm.EndDay.value + "'";
			var mySql6=new SqlClass();
				mySql6.setResourceName("fininterface.FinInterfaceCheckSql"); //指定使用的properties文件名
				mySql6.setSqlId("FinInterfaceCheckSql6");//指定使用的Sql的id
				mySql6.addSubPara(fm.accountCode.value);//指定传入的参数
				mySql6.addSubPara(fm.ManageCom.value);//指定传入的参数
				mySql6.addSubPara(fm.StartDay.value);//指定传入的参数
				mySql6.addSubPara(fm.EndDay.value);//指定传入的参数
				strSQL= mySql6.getString();
//      if (fm.accountCode.value == '1001/01/'||fm.accountCode.value == '1001/02/')
//      {
//      	strSQL = strSQL + " and substr(b.accountcode,1,8) = '" + fm.accountCode.value + "'";
//      	}
//    else
//    	{
//    		strSQL = strSQL + " and substr(b.accountcode,1,4) = '" + fm.accountCode.value + "'";
//    		} 		   
    		
      if (fm.FinItemType.value == null || fm.FinItemType.value == '') 	
      {
      /**
      		strSQL = strSQL + " order by accountdate,orzno";
      		*/
      		var mySql7=new SqlClass();
				mySql7.setResourceName("fininterface.FinInterfaceCheckSql"); //指定使用的properties文件名
				mySql7.setSqlId("FinInterfaceCheckSql7");//指定使用的Sql的id
				mySql7.addSubPara(fm.accountCode.value);//指定传入的参数
				mySql7.addSubPara(fm.ManageCom.value);//指定传入的参数
				mySql7.addSubPara(fm.StartDay.value);//指定传入的参数
				mySql7.addSubPara(fm.EndDay.value);//指定传入的参数
				strSQL= mySql7.getString();
      }
      else
    	{	   
    	/**
	  	strSQL = strSQL 
	  	       + " and b.FinItemType = '"  +fm.FinItemType.value + "'"
       		   + " order by accountdate,orzno";
       		   */
       		var mySql8=new SqlClass();
				mySql8.setResourceName("fininterface.FinInterfaceCheckSql"); //指定使用的properties文件名
				mySql8.setSqlId("FinInterfaceCheckSql8");//指定使用的Sql的id
				mySql8.addSubPara(fm.accountCode.value);//指定传入的参数
				mySql8.addSubPara(fm.ManageCom.value);//指定传入的参数
				mySql8.addSubPara(fm.StartDay.value);//指定传入的参数
				mySql8.addSubPara(fm.EndDay.value);//指定传入的参数
				mySql8.addSubPara(fm.FinItemType.value);//指定传入的参数
				strSQL= mySql8.getString();	   
      }
    }
    else if(fm.checkType.value=="3"){

			//var cSQL = easyQueryVer3("select keyid from liclasstypekeydef where classtype = '"+document.all("SClassType").value+"'",1,1,1);
			//var	cArray = decodeEasyQueryResult(cSQL);
			/**
			strSQL ="select distinct "
             +"a.batchno as batchno, "
             +"a.StringInfo01 as managecom, "
             +"b.finitemtype as listflag, "
             +"b.accountcode as finiteminfo, "
             +"(select certificatename from FICertificateTypeDef where certificateid = a.certificateid) as classinfo, "
             +"a.StringInfo05 as riskcode, "
             +"b.salechnl as salechnl, "
             +"b.accountdate as accountdate, "
             +"a.businessno03 as contno, "
             +"a.StringInfo15 as bankdetail, "
             +"a.StringInfo09 as budget, "
             +"b.costcenter as costcenter, "
             +"to_char(b.summoney,'FM999999999990.00') as money, "
             +"b.certificateid as classtype, "
             +"(select keyname from FICostDataKeyDef where Keyid = 'BusinessNo' and acquisitionid = a.acquisitionid) as keyvaluename, "
             +"a.businessno as orzno "
             +"from  FIAboriginalData a,FIDataTransResult b "
	       		 +" where a.aserialno = b.aserialno "
	       		 +" and b.summoney<>0"
       			 +" and b.certificateid in ( select Code From FiCodeTrans where Codetype = 'BigCertificateID' and codeAlias = '" +document.all("SClassType").value+"')"
       			 +" and a.Stringinfo01 like '" +fm.ManageCom1.value + "%%'"
       			 +" and b.accountdate >= '" +fm.SDate.value + "'"
       			 +" and b.accountdate <= '" +fm.EDate.value + "'"
       		   +" order by a.businessno,accountdate";
       		   */
       		var mySql9=new SqlClass();
				mySql9.setResourceName("fininterface.FinInterfaceCheckSql"); //指定使用的properties文件名
				mySql9.setSqlId("FinInterfaceCheckSql10");//指定使用的Sql的id
				mySql9.addSubPara(document.all("SClassType").value);//指定传入的参数
				mySql9.addSubPara(fm.ManageCom1.value);//指定传入的参数
				mySql9.addSubPara(fm.SDate.value);//指定传入的参数
				mySql9.addSubPara(fm.EDate.value);//指定传入的参数
				strSQL= mySql9.getString();
    	}
    else if(fm.checkType.value=="4"){
			/**
			strSQL ="select distinct "
             +"a.batchno as batchno, "
             +"a.StringInfo01 as managecom, "
             +"b.finitemtype as listflag, "
             +"b.accountcode as finiteminfo, "
             +"(select certificatename from FICertificateTypeDef where certificateid = a.certificateid) as classinfo, "
             +"a.StringInfo05 as riskcode, "
             +"b.salechnl as salechnl, "
             +"b.accountdate as accountdate, "
             +"a.businessno03 as contno, "
             +"a.StringInfo15 as bankdetail, "
             +"a.StringInfo09 as budget, "
             +"b.costcenter as costcenter, "
             +"to_char(b.summoney,'FM999999999990.00') as money, "
             +"b.certificateid as classtype, "
             +"(select keyname from FICostDataKeyDef where Keyid = 'BusinessNo' and acquisitionid = a.acquisitionid) as keyvaluename, "
             +"a.businessno as orzno "
             +"from  FIAboriginalData a,FIDataTransResult b,FIDataTransGather c "
             +"where a.aserialno = b.aserialno "
             +"and a.batchno = b.batchno "
             +"and b.batchno = c.batchno "
             +"and b.managecom = c.managecom "
             +"and b.accountdate = c.accountdate "
             +"and b.certificateid in (select Code From FiCodeTrans where Codetype = 'BigCertificateID' and codeAlias = c.certificateid) "
             +"and a.Stringinfo01 like '" +manageCom + "%%' "
             +"and c.voucherno = '"+fm.AccountID.value+"' "
             +"and b.summoney<>0 "
             +"order by a.businessno,accountdate";
             */
            var mySql10=new SqlClass();
				mySql10.setResourceName("fininterface.FinInterfaceCheckSql"); //指定使用的properties文件名
				mySql10.setSqlId("FinInterfaceCheckSql14");//指定使用的Sql的id
				mySql10.addSubPara(manageCom);//指定传入的参数
				mySql10.addSubPara(fm.AccountID.value);//指定传入的参数
				strSQL= mySql10.getString();
    	}
	fm.ExportExcelSQL.value=strSQL;//保存sql，导出excel时用到
	turnPage.queryModal(strSQL,CheckQueryDataGrid);

/*var strSQL = "select '1' as batchno,"
       +"'1' as managecom,"
       +"'1' as listflag,"
       +"'1' as accountcode,"
       +"'1' as bankdetail,"
       +"'1' as riskcode,"
       +"'1' as salechnl,"
       +"'1' as classid,"
       +"'1' as classname,"
       +"'1' as accountdate,"
       +"'1' as contno,"
       +"'1' as money"
  	   +" from lidatatransresult a"
       +" where 1=1";

    if(fm.checkType.value=="2"){//核对类型^1|科目 ^2|业务代码
       	if(fm.ContType.value=="1"){//个单
       		if(fm.ContNo.value!="" && fm.ContNo.value!=null)
       		{
       			strSQL= strSQL +" and ContNo='" + fm.ContNo.value + "'";
       		}
       		else if(fm.PrtNo.value!="" && fm.PrtNo.value!=null)
       		{
       			strSQL= strSQL +" and ContNo=(select contno from lccont where ContType='1' and prtno='" + fm.PrtNo.value + "')";      		
       		}
       }
       	if(fm.ContType.value=="2"){//团单
       		if(fm.ContNo.value!="" && fm.ContNo.value!=null)
       		{
       			strSQL= strSQL +" and ContNo='" + fm.ContNo.value + "'";
       		}
       		else if(fm.PrtNo.value!="" && fm.PrtNo.value!=null)
       		{
				strSQL= strSQL +" and ContNo=(select distinct GrpContNo from lccont where ContType='2' and prtno='" + fm.PrtNo.value + "')";      		
       		}
      }
    }
    else if(fm.checkType.value=="1"){//核对类型^1|科目 ^2|业务代码
       	strSQL= strSQL + " and 2=2";
    }
    else{}
	fm.ExportExcelSQL.value=strSQL;//保存sql，导出excel时用到
	fm.temptext.value=strSQL;
//	alert(fm.ExportExcelSQL.value);
	turnPage.queryModal(strSQL,CheckQueryDataGrid);
*/
	showInfo.close();
	
	if(CheckQueryDataGrid.mulLineCount=="0"){
		alert("没有查询到数据");
	}		 
	return true;		
	}
  }
  catch(ex)
  {
     alert(ex);
   }
}

function afterCodeSelect(cCodeName, Field) 
{
	if(cCodeName == "checkType") 
	{  
		if(fm.checkType.value=="1"){
			document.all("divSearch1").style.display = '';
			document.all("divSearch2").style.display = 'none';
			document.all("divSearch3").style.display = 'none';
			document.all("divSearch4").style.display = 'none';
		}else if(fm.checkType.value=="2"){
			document.all("divSearch2").style.display = '';
			document.all("divSearch1").style.display = 'none';	
			document.all("divSearch3").style.display = 'none';		
			document.all("divSearch4").style.display = 'none';
		}else if(fm.checkType.value=="3"){
			document.all("divSearch3").style.display = '';
			document.all("divSearch2").style.display = 'none';
			document.all("divSearch1").style.display = 'none';	
			document.all("divSearch4").style.display = 'none';		
		}else if(fm.checkType.value=="4"){
			document.all("divSearch4").style.display = '';
			document.all("divSearch2").style.display = 'none';
			document.all("divSearch1").style.display = 'none';	
			document.all("divSearch3").style.display = 'none';		
		}
		
	}
}

//显示数据的函数，和easyQuery及MulLine 一起使用
function showRecord(strRecord)
{

  //保存查询结果字符串
  turnPage.strQueryResult  = strRecord;

//alert(strRecord);
  
  //使用模拟数据源，必须写在拆分之前
  turnPage.useSimulation   = 1;  
    
  //查询成功则拆分字符串，返回二维数组
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置
  var filterArray = new Array(0,9,4,1,7);
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //过滤二维数组，使之与MULTILINE匹配
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  //初始化的对象
  	 turnPage.pageDisplayGrid = CheckQueryDataGrid;       
              
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  //必须将所有数据设置为一个数据块
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
	
}
