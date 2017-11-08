//程序名称：UWManuSpec.js
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var operate = "";
var proposalcontno = "";
var serialno = "";

//提交，保存按钮对应操作
function submitForm(tflag)
{
	if(tflag=="1")
	   {
	   	var tRemark=fm.Remark.value;
	   	if(trim(tRemark)=="null"||trim(tRemark)==null||trim(tRemark)=="")
	   	{
	   		alert("特约内容不能为空!");
	   		return false;
	   		}
	   	 var tNeedPrintFlag=fm.NeedPrintFlag.value;
  	        if(trim(tNeedPrintFlag)=="null"||trim(tNeedPrintFlag)==null||trim(tNeedPrintFlag)=="")
	   	{
	   		alert("请选择下发标记!");
	   		return false;
	   	}
  	      fm.operate.value = "INSERT"
	    //alert(operate);
	    //return;
	   }
	else if(tflag=="2")   
		 {
		 	var tRemark=fm.Remark.value;
	   	if(trim(tRemark)=="null"||trim(tRemark)==null||trim(tRemark)=="")
	   	{
	   		alert("特约内容不能为空!");
	   		return false;
	   	}
	 	 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("请选择要修改的特约信息！");
		 	  	return;
		 	  } 
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("只有在未发送或者已回收状态才能修改！");
		 	    return;	
		 	  }
		 	fm.proposalcontno.value = UWSpecGrid.getRowColData(tSelNo,5);
     // alert(proposalno);
		 	fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);
		 	fm.operate.value = "UPDATE";
			//alert(operate);
			//return;
		 }
  else if(tflag=="3")
  	 {
		 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("请选择要删除的特约信息！");
		 	  	return;
		 	  }
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("只有在未发送或者已回收状态才能删除！");
		 	    return;	
		 	  }
		 	fm.proposalcontno.value = UWSpecGrid.getRowColData(tSelNo,5);
      //alert(proposalno);
		 	fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);  	 	
		 	fm.operate.value   = "DELETE";
  	  //alert(operate);
  	  //return;
     }
     else
  	 { 	    
		 	var tSelNo = UWSpecGrid.getSelNo()-1;
		 	if(tSelNo=="-1")
		 	  {
		 	  	alert("请选择要修改下发标记的特约信息！");
		 	  	return;
		 	  }
		 	if(UWSpecGrid.getRowColData(tSelNo,3)=="0"||UWSpecGrid.getRowColData(tSelNo,3)=="1")
		 	  {
		 	    alert("只有在未发送或者已回收状态才能修改下发标记！");
		 	    return;	
		 	  }
		 	   var tNeedPrintFlag=fm.NeedPrintFlag.value;
  	        if(trim(tNeedPrintFlag)=="null"||trim(tNeedPrintFlag)==null||trim(tNeedPrintFlag)=="")
	   	{
	   		alert("请选择下发标记!");
	   		return false;
	   	}
		 	  if (!confirm('确认修改?'))
			{
			return false;
			}
		 	 fm.proposalcontno.value = UWSpecGrid.getRowColData(tSelNo,5);
      //alert(proposalno);
		 	fm.serialno.value = UWSpecGrid.getRowColData(tSelNo,6);
      //alert(serialno);  	 	
		 	fm.operate.value   = "UPDATE";
  	  //alert(operate);
  	  //return;
     }
	   
    var i = 0;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
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
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  if (FlagStr == "Fail" )
  {                 
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	QueryPolSpecGrid(fm.ContNo.value,fm.EdorNo.value,fm.InsuredNo.value);
  }

}


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}
         

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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


function manuchkspecmain()
{
	fm.submit();
}

function QueryPolSpecGrid(tContNo,tEdorNo,tInsuredNo)
{
	// 初始化表格
	// 书写SQL语句
	//alert("QueryPolSpecGrid"+tContNo);
	var strSQL = "";
	var i, j, m, n;	
       //获取原保单信息
       // strSQL = "select LCPol.ContNo,LCPol.PrtNo,LCPol.PolNo,LCPol.RiskCode,LCPol.RiskVersion,LCPol.AppntName,LCPol.InsuredName from LCPol where "				 			
			 //+ "  ContNo =(select ContNo from LCCont where Prtno = '"+tContNo+"')"
			 //+ "  order by polno ";			
			// strSQL = "select proposalcontno from lccont where contno = '"+tContNo+"'";
			// var arrResult=easyExecSql(strSQL);  
	    // try{tProposalContNo = arrResult[0][0];}catch(ex){}
	
	
//			 strSQL = "select a,b,c,case c when 'x' then '未发送' "
//			                          + " when '0' then '已发送未打印'"
//			                          + " when '1' then '已打印未回收'"
//                                + " when '2' then '已回收'"
//                         + " end,"
//                         + " f,d,e,"
//                         + " case e when 'Y' then '下发' "
//			                         + " when 'N' then '不下发'"
//                         + " end"
//                + "   from (select s.speccontent as a,"
//                + "                s.serialno as b,"
//                + "                (case when (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.prtseq = s.prtseq) is not null then (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.prtseq = s.prtseq) else 'x' end) as c,"
//                + "                s.serialno as d,"
//                + "                s.needprint as e,proposalcontno f"
//                + "                from lpcspec s "
//                + "                where s.edorno='"+tEdorNo+"' and s.contno = '"+tContNo+"' and s.customerno = '"+tInsuredNo+"' ";
                if(tQueryFlag=="2"){
//                	  strSQL = strSQL + "  )";
                	  
                	   var sqlid1="BQManuSpecSql1";
                	   var mySql1=new SqlClass();
                	   mySql1.setResourceName("uw.BQManuSpecSql");//指定使用的properties文件名
                	   mySql1.setSqlId(sqlid1);//指定使用的Sql的id
                	   mySql1.addSubPara(tEdorNo);//指定传入参数
                	   mySql1.addSubPara(tContNo);//指定传入参数
                	   mySql1.addSubPara(tInsuredNo);//指定传入参数
                	   strSQL = mySql1.getString();
                	         	  
                	   document.all("divQuery").style.display="none";                	
                }
                else{
//                	strSQL = strSQL + "  and s.spectype='ch')";
                	
                   var sqlid2="BQManuSpecSql2";
             	   var mySql2=new SqlClass();
             	   mySql2.setResourceName("uw.BQManuSpecSql");//指定使用的properties文件名
             	   mySql2.setSqlId(sqlid2);//指定使用的Sql的id
             	   mySql2.addSubPara(tEdorNo);//指定传入参数
             	   mySql2.addSubPara(tContNo);//指定传入参数
             	   mySql2.addSubPara(tInsuredNo);//指定传入参数
             	   strSQL = mySql2.getString();
                }
               
    //prompt('',strSQL);
	turnPage.queryModal(strSQL, UWSpecGrid); 
    return true;	
}


function QueryPolSpecCont(tContNo,tEdorNo,tInsuredNo)
{
	//alert("QueryPolSpecCont"+tContNo);
		var strSQL = "";
		//查询被保人信息
//		strSQL = "select name, (select max(a.insuredappage) from lppol a where b.edorno=a.edorno and b.contno=a.contno and b.insuredno=a.insuredno) age, managecom , substr(managecom,1,4) from lpinsured b where edorno='"+tEdorNo+"' and contno='"+tContNo+"' and insuredno='"+tInsuredNo+"'";
//		//prompt('',strSQL);
		
	   var sqlid3="BQManuSpecSql3";
  	   var mySql3=new SqlClass();
  	   mySql3.setResourceName("uw.BQManuSpecSql");//指定使用的properties文件名
  	   mySql3.setSqlId(sqlid3);//指定使用的Sql的id
  	   mySql3.addSubPara(tEdorNo);//指定传入参数
  	   mySql3.addSubPara(tContNo);//指定传入参数
  	   mySql3.addSubPara(tInsuredNo);//指定传入参数
  	   strSQL = mySql3.getString();
		
		var arr = easyExecSql(strSQL);
		if(arr!=null){
			fm.InsuredName.value = arr[0][0];
			fm.InsuredAge.value = arr[0][1];
			fm.ManageCom.value = arr[0][2];	
		    if(arr[0][3]=="8611"||arr[0][3]=="8631")	
		    	fm.SpecTemp.value = 'ch002';		    	
		    else
		    	fm.SpecTemp.value = 'ch001';
//		    strSQL = "select noti,speccontent from LCCSpecTemplet where temptype='ch' and templetcode='"+fm.SpecTemp.value+"'";
		    
		       var sqlid4="BQManuSpecSql4";
		  	   var mySql4=new SqlClass();
		  	   mySql4.setResourceName("uw.BQManuSpecSql");//指定使用的properties文件名
		  	   mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		  	   mySql4.addSubPara(fm.SpecTemp.value);//指定传入参数
		  	   strSQL = mySql4.getString();
		    
		    var arr1 = easyExecSql(strSQL);
		    if(arr1!=null)
		    {
		    	fm.SpecTempname.value = arr1[0][0];	
		    	fm.Remark.value = arr1[0][1];
		    }		    	
		    
		}
		
/**
		strSQL = "select substr(managecom,1,4) from lccont where contno=(select ContNo from LCCont where PrtNo = '"+tContNo+"')";
		var managecom = easyExecSql(strSQL); 
		
		strSQL = "select substr(managecom,1,6) from lccont where contno=(select ContNo from LCCont where PrtNo = '"+tContNo+"')";
		var managecom2 = easyExecSql(strSQL); 
		
		//alert(managecom);
		if(managecom!=null){
			//如果是北分、上海、广州的投保单则显示十万
		  if(managecom=="8621"||managecom=="8622"){
       strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'spectype2'"
				 ;	
		  }
		else if (managecom=="8623"){
			 if(managecom2!=null&&managecom2=="862300"){
			 strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'spectype2'"
				 ;		
			 	}
			 else {
       strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'spectype'"
				 ;
			 	}
			}
		  else{
       strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'spectype'"
				 ;	
		  }
		}
	 else{
       strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'spectype'"
				 ;	
	 }
	
       //获取原保单信息
       // strSQL = "select cont from ldcodemod where 1 =1 and codetype='spectype'";		
       
	turnPage.queryModal(strSQL, UWSpecContGrid);   
**/
}
function getPolGridCho()
{
  var tSelNo = UWSpecGrid.getSelNo()-1;
  var cPolNo = UWSpecGrid.getRowColData(tSelNo,3);
  var tcontno = UWSpecGrid.getRowColData(tSelNo,1);
  //alert(tcontno);
 if(cPolNo != null && cPolNo != "" )
  {
    document.all("PolNo").value = cPolNo;
   
    QuerySpecReason(cPolNo);   
    QuerySpec(tcontno);
  }	
}



//查询已经录入特约内容
function QuerySpec(tcontno)
{
	
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select speccontent from lpcspec where contno ='"+tcontno+"' and SerialNo in (select max(SerialNo) from lcspec where contno ='"+tcontno+"')";
	
	   var sqlid5="BQManuSpecSql5";
	   var mySql5=new SqlClass();
	   mySql5.setResourceName("uw.BQManuSpecSql");//指定使用的properties文件名
	   mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	   mySql5.addSubPara(tcontno);//指定传入参数
	   mySql5.addSubPara(tcontno);//指定传入参数
	   strSQL = mySql5.getString();
	
	var arrResult=easyExecSql(strSQL);  
	try{document.all('Remark').value= arrResult[0][0];}catch(ex){};                                                                                          
//	tur   try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};                                                                     nPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
//
//  //判断是否查询成功
//  if (!turnPage.strQueryResult) {
//    return "";
//  }
//  
//  //查询成功则拆分字符串，返回二维数组
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//  
//  fm.Remark.value = turnPage.arrDataCacheSet[0][0];	
  
  return true;	
}


//查询已经录入加费特约原因
function QuerySpecReason(cPolNo)
{
	
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
//	strSQL = "select specreason from LCUWMaster where 1=1 "
//			 + " and polno = '"+cPolNo+"'";
	
	   var sqlid6="BQManuSpecSql6";
	   var mySql6=new SqlClass();
	   mySql6.setResourceName("uw.BQManuSpecSql");//指定使用的properties文件名
	   mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	   mySql6.addSubPara(cPolNo);//指定传入参数
	   strSQL = mySql6.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.SpecReason.value = turnPage.arrDataCacheSet[0][0];
  //fm.Reason.value = turnPage.arrDataCacheSet[0][1];
  
  return true;	
}


function getSpecGridCho()
{
	var tContent = fm.Remark.value;
	
	var tSelNo = UWSpecContGrid.getSelNo()-1;
	var tRemark = UWSpecContGrid.getRowColData(tSelNo,1);	
	
	fm.Remark.value = tRemark + tContent; 
}


function getSpecGridCho2()
{
	//var tContent = fm.Remark.value;
	
	var tSelNo = UWSpecGrid.getSelNo()-1;
	var tSerialNo = UWSpecGrid.getRowColData(tSelNo,2);	
	var tProposalContNo = UWSpecGrid.getRowColData(tSelNo,5);
//	var tSQL = "select speccode,speccontent,needPrint,"
//	                                 + " case needPrint when 'Y' then '下发' "
//			                         + " when 'N' then '不下发'"
//                         + " end from lpcspec where edorno='"+fm.EdorNo.value+"' and proposalcontno='"+tProposalContNo+"' and serialno='"+tSerialNo+"'";
	
	   var sqlid7="BQManuSpecSql7";
	   var mySql7=new SqlClass();
	   mySql7.setResourceName("uw.BQManuSpecSql");//指定使用的properties文件名
	   mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	   mySql7.addSubPara(fm.EdorNo.value);//指定传入参数
	   mySql7.addSubPara(tProposalContNo);//指定传入参数
	   mySql7.addSubPara(tSerialNo);//指定传入参数
	   var tSQL = mySql7.getString();
	
	var arrResult=easyExecSql(tSQL);  
	//document.all('SpecReason').value= arrResult[0][0]; 
	if(arrResult!=null) 
	{
		fm.SpecTemp.value = arrResult[0][0];
		fm.Remark.value = arrResult[0][1];
		fm.NeedPrintFlag.value = arrResult[0][2];
		fm.IFNeedFlagName.value = arrResult[0][3];		
	}   	
}

function initpolno(tContNo,tInsuredNo,tEdorNo)
{
	var strSQL = "";

	if(_DBT==_DBO){
//		strSQL = "select mainpolno from LPPol where 1=1 "
//			 + " and edorno='"+tEdorNo+"' and contno = '"+tContNo+"' and insuredno='"+tInsuredNo+"' and rownum = 1";
		
		   var sqlid8="BQManuSpecSql8";
		   var mySql8=new SqlClass();
		   mySql8.setResourceName("uw.BQManuSpecSql");//指定使用的properties文件名
		   mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		   mySql8.addSubPara(tEdorNo);//指定传入参数
		   mySql8.addSubPara(tContNo);//指定传入参数
		   mySql8.addSubPara(tInsuredNo);//指定传入参数
		   strSQL = mySql8.getString();
		
	}else if(_DBT==_DBM){
//		strSQL = "select mainpolno from LPPol where 1=1 "
//			 + " and edorno='"+tEdorNo+"' and contno = '"+tContNo+"' and insuredno='"+tInsuredNo+"' limit 0,1";
		
		   var sqlid9="BQManuSpecSql9";
		   var mySql9=new SqlClass();
		   mySql9.setResourceName("uw.BQManuSpecSql");//指定使用的properties文件名
		   mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		   mySql9.addSubPara(tEdorNo);//指定传入参数
		   mySql9.addSubPara(tContNo);//指定传入参数
		   mySql9.addSubPara(tInsuredNo);//指定传入参数
		   strSQL = mySql9.getString();
	}
	

	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	
    return "";
  }
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.PolNo.value = turnPage.arrDataCacheSet[0][0];
  //alert(fm.PolNo.value);

}