//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";


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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		top.close();
	}
}

// 查询按钮
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var queryBug = 1;
function initQuery() { 
	// 初始化表格
	initPolGrid();

	var addSQL = "";
	if(!(document.all('ManageCom').value==null||document.all('ManageCom').value==""))
	{
		 addSQL=addSQL+ " and a.managecom like '%25" + document.all('ManageCom').value + "%25'" ;
	}
	else
	{
		//管理机构为空，则默认以当前机构查询
		addSQL=addSQL+" and a.managecom like '%25" + manageCom + "%25'" ;
	}	
	var strSql1 = "";
	if(_DBT==_DBO){
//		strSql1 = " select (select operator from ldsystrace where ldsystrace.polno = rpad(a.doccode,20) and CreatePos='异常件处理'  and  PolState='2002' and rownum=1 ) operator,d.bussno"
//		     +" ,a.managecom,d.bpoid,(case dealtype when '02' then '外包方返回可处理异常件' when '03' then '外包方无法处理的异常件（如整个扫描件无法识别）' when '04' then '清洁件导入系统出错所致的异常件' end) "
//			 +" ,a.makedate,a.maketime"
//			 +" ,d.dealtype "
//			 +" from ES_DOC_MAIN a,BPOMissionState d "                                    
//	                +" where a.doccode=d.bussno"
//		        +" and d.bussnotype='OF'"
//		        +" and d.dealtype in ('02','03','04') and a.makedate>=now()-30 and a.makedate<=now()+1 and d.makedate>=now()-30 and d.makedate<=now()+1 "
//		        +" and d.state='0' and a.managecom like '"+manageCom+"%%' "
//		        + getWherePart('a.makedate','ScanDate','<=')
//		        +addSQL
//		        +" order by a.makedate,a.maketime "	;
		
	    
        var  ScanDate = window.document.getElementsByName(trim("ScanDate"))[0].value;
     	var  sqlid1="FineWbProposalQuestModifySql0";
     	var  mySql1=new SqlClass();
     	mySql1.setResourceName("finfee.FineWbProposalQuestModifySql"); //指定使用的properties文件名
     	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
     	mySql1.addSubPara(manageCom);//指定传入的参数
     	mySql1.addSubPara(ScanDate);//指定传入的参数
     	mySql1.addSubPara(addSQL);//指定传入的参数
     	strSql1=mySql1.getString();
	}else if(_DBT==_DBM){
//		strSql1 = " select (select operator from ldsystrace where ldsystrace.polno = rpad(a.doccode,20) and CreatePos='异常件处理'  and  PolState='2002' limit 0,1 ) operator,d.bussno"
//		     +" ,a.managecom,d.bpoid,(case dealtype when '02' then '外包方返回可处理异常件' when '03' then '外包方无法处理的异常件（如整个扫描件无法识别）' when '04' then '清洁件导入系统出错所致的异常件' end) "
//			 +" ,a.makedate,a.maketime"
//			 +" ,d.dealtype "
//			 +" from ES_DOC_MAIN a,BPOMissionState d "                                    
//	                +" where a.doccode=d.bussno"
//		        +" and d.bussnotype='OF'"
//		        +" and d.dealtype in ('02','03','04') and a.makedate>=now()-30 and a.makedate<=now()+1 and d.makedate>=now()-30 and d.makedate<=now()+1 "
//		        +" and d.state='0' and a.managecom like '"+manageCom+"%%' "
//		        + getWherePart('a.makedate','ScanDate','<=')
//		        +addSQL
//		        +" order by a.makedate,a.maketime "	;
		    var  ScanDate = window.document.getElementsByName(trim("ScanDate"))[0].value;
	     	var  sqlid2="FineWbProposalQuestModifySql1";
	     	var  mySql2=new SqlClass();
	     	mySql2.setResourceName("finfee.FineWbProposalQuestModifySql"); //指定使用的properties文件名
	     	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	     	mySql2.addSubPara(manageCom);//指定传入的参数
	     	mySql2.addSubPara(ScanDate);//指定传入的参数
	     	mySql2.addSubPara(addSQL);//指定传入的参数
	     	strSql1=mySql2.getString();
		
	}
	 
 
	turnPage.queryModal(strSql1, PolGrid);
}

function easyQueryClick(){
	initPolGrid();
	
	var addSQL = "";
	
	if(!(document.all('ManageCom').value==null||document.all('ManageCom').value==""))
	{
		 addSQL=addSQL+ " and a.managecom like '%25" + document.all('ManageCom').value + "%25'" ;
	}
	else
	{
		//管理机构为空，则默认以当前机构查询
		addSQL=addSQL+" and a.managecom like '%25" + manageCom + "%25'" ;
	}	
     var strSql1 = "";
     if(_DBT==_DBO){
//    	 strSql1 = " select (select operator from ldsystrace where ldsystrace.polno = rpad(a.doccode,20) and CreatePos='异常件处理'  and  PolState='2002' and rownum=1 ) operator,d.bussno,a.managecom,d.bpoid,(case dealtype when '02' then '外包方返回可处理异常件' when '03' then '外包方无法处理的异常件（如整个扫描件无法识别）' when '04' then '清洁件导入系统出错所致的异常件' end) "
//    			+" ,a.makedate,a.maketime,d.dealtype from ES_DOC_MAIN a,BPOMissionState d "                                    
//    	                +" where a.doccode=d.bussno"
//    		        +" and d.bussnotype='OF'"
//    		        +" and d.dealtype in ('02','03','04') and a.makedate>=sysdate-30 and a.makedate<=now()+1 and d.makedate>=now()-30 and d.makedate<=now()+1 "
//    		        +" and d.state='0' and a.managecom like '"+manageCom+"%%' "
//    		        + getWherePart('a.makedate','ScanDate','<=')
//    			      + getWherePart( 'BussNo','TempFeeNo' )
//    			      + getWherePart( 'BPOID','BPOID' )
//    			      + addSQL
//    			      +" order by a.makedate,a.maketime "	;
    	 
    	    var  ScanDate = window.document.getElementsByName(trim("ScanDate"))[0].value;
    	    var  TempFeeNo = window.document.getElementsByName(trim("TempFeeNo"))[0].value;
    	    var  BPOID = window.document.getElementsByName(trim("BPOID"))[0].value;
	     	var  sqlid3="FineWbProposalQuestModifySql2";
	     	var  mySql3=new SqlClass();
	     	mySql3.setResourceName("finfee.FineWbProposalQuestModifySql"); //指定使用的properties文件名
	     	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	     	mySql3.addSubPara(manageCom);//指定传入的参数
	     	mySql3.addSubPara(ScanDate);//指定传入的参数
	     	mySql3.addSubPara(TempFeeNo);//指定传入的参数
	     	mySql3.addSubPara(BPOID);//指定传入的参数
	     	mySql3.addSubPara(addSQL);//指定传入的参数
	     	strSql1=mySql3.getString();
     }else if(_DBT==_DBM){
//    	 strSql1 = " select (select operator from ldsystrace where ldsystrace.polno = rpad(a.doccode,20) and CreatePos='异常件处理'  and  PolState='2002' limit 0,1 ) operator,d.bussno,a.managecom,d.bpoid,(case dealtype when '02' then '外包方返回可处理异常件' when '03' then '外包方无法处理的异常件（如整个扫描件无法识别）' when '04' then '清洁件导入系统出错所致的异常件' end) "
//    			+" ,a.makedate,a.maketime,d.dealtype from ES_DOC_MAIN a,BPOMissionState d "                                    
//    	                +" where a.doccode=d.bussno"
//    		        +" and d.bussnotype='OF'"
//    		        +" and d.dealtype in ('02','03','04') and a.makedate>=now()-30 and a.makedate<=now()+1 and d.makedate>=now()-30 and d.makedate<=now()+1 "
//    		        +" and d.state='0' and a.managecom like '"+manageCom+"%%' "
//    		        + getWherePart('a.makedate','ScanDate','<=')
//    			      + getWherePart( 'BussNo','TempFeeNo' )
//    			      + getWherePart( 'BPOID','BPOID' )
//    			      + addSQL
//    			      +" order by a.makedate,a.maketime "	;
    	 
    	    var  ScanDate = window.document.getElementsByName(trim("ScanDate"))[0].value;
 	        var  TempFeeNo = window.document.getElementsByName(trim("TempFeeNo"))[0].value;
 	        var  BPOID = window.document.getElementsByName(trim("BPOID"))[0].value;
	     	var  sqlid4="FineWbProposalQuestModifySql3";
	     	var  mySql4=new SqlClass();
	     	mySql4.setResourceName("finfee.FineWbProposalQuestModifySql"); //指定使用的properties文件名
	     	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	     	mySql4.addSubPara(manageCom);//指定传入的参数
	     	mySql4.addSubPara(ScanDate);//指定传入的参数
	     	mySql4.addSubPara(TempFeeNo);//指定传入的参数
	     	mySql4.addSubPara(BPOID);//指定传入的参数
	     	mySql4.addSubPara(addSQL);//指定传入的参数
	     	strSql1=mySql4.getString();
     }			
	turnPage.queryModal(strSql1, PolGrid);
	
}


var mSwitch = parent.VD.gVSwitch;
function modifyClick() {
  var i = 0;
  var checkFlag = 0;
  
  for (i=0; i<PolGrid.mulLineCount; i++) {
    if (PolGrid.getSelNo(i)) { 
      checkFlag = PolGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
  	var cTempFeeNo = PolGrid.getRowColData(checkFlag - 1, 2); 	
  	mSwitch.deleteVar( "TempFeeNo" );
  	mSwitch.addVar( "TempFeeNo", "", cTempFeeNo );
  	
  
  	
  //var strSql = "select * from ldsystrace where PolNo='" + cTempFeeNo + "' and CreatePos='异常件处理'  and  PolState='2002'";
    var  sqlid5="FineWbProposalQuestModifySql4";
	var  mySql5=new SqlClass();
	mySql5.setResourceName("finfee.FineWbProposalQuestModifySql"); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(cTempFeeNo);//指定传入的参数
	strSql=mySql5.getString();
  //prompt("",strSql);
  var arrResult = easyExecSql(strSql);
  //alert("arrResult[0][1]"+arrResult[0][1]);
  //alert("Operator"+Operator);
  if (arrResult!=null && arrResult[0][1]!=operator) {
    alert("该印刷号的投保单已经被操作员（" + arrResult[0][1] + "）在（" + arrResult[0][5] + "）位置锁定！您不能操作，请选其它的印刷号！");
    return;
  }
    //锁定该印刷号
    var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo=" + cTempFeeNo + "&CreatePos=异常件处理&PolState=2002&Action=INSERT";
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  	
    //urlStr = "./ProposalMain.jsp?LoadFlag=3";
    var TempFeeNo = PolGrid.getRowColData(PolGrid.getSelNo() - 1, 2);
    var tType=PolGrid.getRowColData(PolGrid.getSelNo() - 1, 8);
    //alert("tType:"+tType);
    //alert("TempFeeNo:"+TempFeeNo);
    window.open("./FineWbProposalInputMain1.jsp?LoadFlag=3&DealType="+tType+"&TempFeeNo="+TempFeeNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
  }
  else {
    alert("请先选择一条收费单信息！"); 
  }
  
    initQuery();
}
 
function unlock(TempFeeNo)
{
	//alert("TempFeeNo"+TempFeeNo);
	
	var urlStr = "../common/jsp/UnLockTable.jsp?PrtNo="+TempFeeNo+"&CreatePos=异常件处理&PolState=2002&Action=DELETE";
  	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable:1"); 
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	initQuery();
}


