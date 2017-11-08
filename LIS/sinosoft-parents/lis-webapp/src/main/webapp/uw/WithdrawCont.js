//程序名称：WithdrawCont.js
//程序功能：撤单
//创建日期：2008-10-18 11:10:36
//创建人  ：ln
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件
var strsql;
var showInfo;
var turnPage = new turnPageClass();
var turnPageAll = new turnPageClass();
var turnQuery = new turnPageClass();
var tContType = "1";
//提交，保存按钮对应操作
function submitForm()
{  
    var SelNo = WihtDContGrid.getSelNo();
    if(SelNo==0)
    {
        alert("请选择要撤保的投保单！");
        return;
    }
    document.all('ContNoH').value = WihtDContGrid.getRowColData( SelNo- 1, 1);
    document.all('PrtNoH').value = WihtDContGrid.getRowColData( SelNo- 1, 2);    
    
	if(document.all('UWWithDReasonCode').value=="")
	{
		alert("撤单原因不能为空！");
    	return;
  	}
	if(document.all('UWWithDReasonCode').value=="14"&&(document.all('Content').value==null||document.all('Content').value==""))
	{
		alert("请录入撤单说明！");
    	return;
  	} 
  	if(document.all('Content').value!="" && document.all('Content').value.length>20)
	{
		alert("撤单说明必须在20字以内！");
    	return;
  	} 
  	//有“特约”“加费”“承保结论变更”“体检”“生调”“问题件代码－提取病历”的已发送未回复投保单，撤单原因不可选择“客户申请撤单” 2008-12-15 ln add
    var sqlid916101523="DSHomeContSql916101523";
var mySql916101523=new SqlClass();
mySql916101523.setResourceName("uw.WithdrawContSql");//指定使用的properties文件名
mySql916101523.setSqlId(sqlid916101523);//指定使用的Sql的id
mySql916101523.addSubPara(document.all('ContNoH').value);//指定传入的参数
mySql916101523.addSubPara(document.all('ContNoH').value);//指定传入的参数
mySql916101523.addSubPara(document.all('ContNoH').value);//指定传入的参数
mySql916101523.addSubPara(document.all('ContNoH').value);//指定传入的参数
mySql916101523.addSubPara(document.all('ContNoH').value);//指定传入的参数
mySql916101523.addSubPara(document.all('ContNoH').value);//指定传入的参数
var strSQL1=mySql916101523.getString();
    
//    var strSQL1="select 1 from lcuwmaster where contno='"+document.all('ContNoH').value+"' and changepolflag is not null and changepolflag not in ('0','2') "
//                + " union (select 1 from lccspec where contno='" +document.all('ContNoH').value+ "' and (spectype='ch' or spectype in(select code from ldcode where 1 = 1 and codetype = 'healthspcetemp')) and prtflag is not null and prtflag <>'2')"
//                //+ " union (select 1 from lcprem where contno='" +document.all('ContNoH').value+ "' and payplancode like '000000%%' and exists (select 1 from lwmission where missionprop2='" +document.all('ContNoH').value+ "' and activityid='0000001100' and missionprop18='4'))"
//                + " union (select 1 from lcprem where contno='" +document.all('ContNoH').value+ "' and payplancode like '000000%%' )"
//                + " union (select 1 from lcpenotice where contno='"+document.all('ContNoH').value+"' and printflag is not null and printflag <>'2' )"
//                + " union (select 1 from lcrreport where contno='"+document.all('ContNoH').value+"'  and replyflag is not null and replyflag <>'2')"
//                + " union (select 1 from lcissuepol where contno='"+document.all('ContNoH').value+"' and issuetype='2302' and state is not null "
//                	+ " and exists(select 1 from LOPRTManager where code='TB90' and otherno=lcissuepol.contno and othernotype='02' and stateflag<>'2'))";
    var arrResult1 = easyExecSql(strSQL1);
		if (arrResult1!=null && document.all('UWWithDReasonCode').value == "00") 
		{
			alert("有承保计划变更、体检、生调等通知书未回复，不能选择客户申请撤单！");
			return;
		}	
  	
  	//判断支票是否到帐----add by haopan --2007-2-1
  	var sqlid916111715="DSHomeContSql916111715";
var mySql916111715=new SqlClass();
mySql916111715.setResourceName("uw.WithdrawContSql");//指定使用的properties文件名
mySql916111715.setSqlId(sqlid916111715);//指定使用的Sql的id
mySql916111715.addSubPara(document.all('ContNoH').value);//指定传入的参数
var strSQL=mySql916111715.getString();

  	
//    var strSQL="select * from ljtempfeeclass where paymode='3' and otherno='"+document.all('ContNoH').value+"'  and enteraccdate is null";
    var arrResult = easyExecSql(strSQL);
		if (arrResult!=null) 
		{
			alert("支票尚未到帐，不允许进行撤单！");
			return;
		}
	//if(document.all('UWWithDReasonCode').value<"05")
	//{	
		if(!confirm("操作后无法恢复，是否撤单？"))
		     return;
	//}

  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");  
lockScreen('lkscreen');  
  document.getElementById("fm").submit(); //提交 
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showInfo.close();
       
  }
  else
  { 
		var content="操作成功！";
  	    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
  	//parent.close();
   // top.close();   
   withdrawQueryClick();
   queryWihtDContInfo("");
  }
}

//操作履历查询
function QueryRecord(){

    var SelNo = WihtDContGrid.getSelNo();
    if(SelNo==0)
    {
        alert("请选择要查询的投保单！");
        return;
    }
    
    var mContNo = WihtDContGrid.getRowColData( SelNo- 1, 1);
    
	window.open("./RecordQueryMain.jsp?ContNo="+mContNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//显示待撤销投保单详细信息
function showWihtDContInfo()
{	
	  var tSelNo = WithDContAllGrid.getSelNo();
	  var tContNo = WithDContAllGrid.getRowColData(tSelNo - 1,1);	 	
	 	
	  queryWihtDContInfo(tContNo); 	
      
}

//查询待撤销投保单详细信息
function queryWihtDContInfo(tContNo)
{	 	
	 	if (tContType == "1")
    {
    	var sqlid916111950="DSHomeContSql916111950";
var mySql916111950=new SqlClass();
mySql916111950.setResourceName("uw.WithdrawContSql");//指定使用的properties文件名
mySql916111950.setSqlId(sqlid916111950);//指定使用的Sql的id
mySql916111950.addSubPara(tContNo);//指定传入的参数
mySql916111950.addSubPara(tContNo);//指定传入的参数
mySql916111950.addSubPara(tContNo);//指定传入的参数
mySql916111950.addSubPara(tContNo);//指定传入的参数
mySql916111950.addSubPara(tContNo);//指定传入的参数
mySql916111950.addSubPara(tContNo);//指定传入的参数
mySql916111950.addSubPara(tContNo);//指定传入的参数
strsql=mySql916111950.getString();
    	
//    	strsql = " select a.contno, a.prtno, a.appntno, a.appntname"
//    	   + " ,nvl((select '是' from LCPENotice where contno='" +tContNo+ "' and rownum=1),'否')"
//    	   + " ,nvl((select '是' from LCRReport where contno='" +tContNo+ "' and rownum=1),'否')"
//	       + " ,nvl((select '是' from lcissuepol where contno='" +tContNo+ "' and rownum=1),'否')"
//	       + " ,nvl((select '是' from lccspec where contno='" +tContNo+ "' and (spectype='ch' or spectype in(select code from ldcode where 1 = 1 and codetype = 'healthspcetemp')) and rownum=1),'否')"
//	       + " ,nvl((select '是' from lcprem where contno='" +tContNo+ "' and payplancode like '000000%%' and rownum=1),'否')"
//	       + " ,nvl((select '是' from lcuwmaster where contno='" +tContNo+ "' and changepolflag is not null and changepolflag<>'0' and rownum=1),'否')"
//	       + " from lccont a where a.contno='" +tContNo+ "' ";
    }      
	
  turnPage.queryModal(strsql,WihtDContGrid); 
}

//查询所有待撤销投保单
function withdrawQueryClick()
{	
    if(document.all('ManageCom').value==null||document.all('ManageCom').value=="" || document.all('ManageCom').value.length!=8)
	{
		alert("管理机构必须选择8位！");
    	return;
  	}
	// 书写SQL语句
    //var tContType = fm.ContType.value;
   // fm.PolType.value = tContType;
    if (tContType == "1")
    {
        var wherePartStr = getWherePart('l.PrtNo', 'PrtNo')
                + getWherePart('l.ManageCom', 'ManageCom', 'like')
                + getWherePart('l.AppntName', 'AppntName')
                + getWherePart('l.InsuredName', 'InsuredName')
                + getWherePart('l.AgentCode', 'AgentCode')
                + getWherePart('l.SaleChnl', 'SaleChnl');
        var sqlid916112252="DSHomeContSql916112252";
				var mySql916112252=new SqlClass();
				mySql916112252.setResourceName("uw.WithdrawContSql");//指定使用的properties文件名
				mySql916112252.setSqlId(sqlid916112252);//指定使用的Sql的id
				mySql916112252.addSubPara(wherePartStr);//指定传入的参数
				var strSQL=mySql916112252.getString();
        
//        strsql = " select l.ContNo,l.PrtNo,l.AppntName,l.InsuredName,l.AgentCode,l.ManageCom,"
//            + " nvl((select (select codename from ldcode where codetype='uwstatus' and code=MissionProp18) from lwmission where MissionProp1=l.PrtNo and activityid='0000001100' ),'待签单'),"
//            + " l.UWDate "
//        		+ " from LCCont l where 1=1 "
//        		+ " and exists(select 1 from ES_DOC_MAIN m where "
//        		+ " l.prtno=m.doccode and m.subtype='UR201')"
//                + " and l.grpcontno='00000000000000000000' and l.appflag = '0'"
//                + " and ((l.uwflag <> 'a')"
//                + " or (l.uwflag is null))"
//                + " and (("
//                + " (substr(l.state, 0, 4)) <> '1002' and"
//                + " (substr(l.state, 0, 4)) <> '1003') or l.state is null) "
//                + " and not exists(select 1 from lwmission where missionprop1=l.PrtNo and activityid in ('0000001089','0000001090','0000001091'))"//排除待外包处理、异常件、复核抽查的投保单
//                + getWherePart('l.PrtNo', 'PrtNo')
//                + getWherePart('l.ManageCom', 'ManageCom', 'like')
//                + getWherePart('l.AppntName', 'AppntName')
//                + getWherePart('l.InsuredName', 'InsuredName')
//                + getWherePart('l.AgentCode', 'AgentCode')
//                + getWherePart('l.SaleChnl', 'SaleChnl')
//                + " group by l.ContNo,l.PrtNo,l.AppntName,l.InsuredName,l.AgentCode,l.ManageCom, l.UWDate,l.MakeDate " 
//                + " order by l.MakeDate ";
    }   
	
  turnPageAll.queryModal(strSQL,WithDContAllGrid);
}
//查询业务员编码
function queryAgent()
{	
	if(document.all('ManageCom').value==""){
		 alert("请先录入管理机构信息！");
		 return;
	}
  if(document.all('AgentCode').value == "")	{	  
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	else if(document.all('AgentCode').value != ""&& document.all('AgentCode').value.length!=10){
	    alert("代理人编码位数错误！");
    	return;
    }	
    else
    {
      	var sqlid916112726="DSHomeContSql916112726";
var mySql916112726=new SqlClass();
mySql916112726.setResourceName("uw.WithdrawContSql");//指定使用的properties文件名
mySql916112726.setSqlId(sqlid916112726);//指定使用的Sql的id
mySql916112726.addSubPara(document.all('AgentCode').value);//指定传入的参数
mySql916112726.addSubPara(document.all("ManageCom").value);//指定传入的参数
var strSQL=mySql916112726.getString();
      	
//      	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03'  and a.AgentCode='"+document.all('AgentCode').value+"' and a.managecom='"+document.all("ManageCom").value+"'";
	   //alert(strSQL);
	    var arrResult = easyExecSql(strSQL); 
	    //alert(arrResult);
	    if (arrResult != null) {
	    	fm.AgentCode.value = arrResult[0][0];
	  	  fm.AgentName.value = arrResult[0][3];
	      //fm.AgentManageCom.value = arrResult[0][2];
	      alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][3]+"]");
	    }
	    else
	    {
	      alert("编码为:["+document.all('AgentCode').value+"]的业务员不存在，或者不在该管理机构下，或者已经离职， 请确认!");
	      fm.AgentCode.value ="";
	  	  fm.AgentName.value ="";	     
	    }
    }  	
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{
	if(arrResult!=null)
	{
	fm.AgentCode.value = arrResult[0][0];
	fm.AgentName.value = arrResult[0][3];
	}
}
