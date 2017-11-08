//程序名称：SendAllnotice.js
//程序功能：核保等待
//创建日期：2008-09-27 11:10:36
//创建人  ：ln
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnConfirmPage = new turnPageClass();
//提交，保存按钮对应操作
function submitForm()
{
    var SelNo = WaitContGrid.getSelNo();
    if(SelNo==0)
    {
        alert("请先选择需要等待的未承保投保单！");
        return;
    }
    //2009-2-14 ln add
    var mUWState = WaitContGrid.getRowColData( SelNo- 1, 6);
    //tongmeng 2009-05-14 modify
    //只有4状态能选择等待
    //if(mUWState==null || mUWState>'4')
    if(mUWState==null || mUWState!='4')
    {
    	//alert("该投保单不符合核保等待的条件！");
    	alert("只能选择4状态的投保单!");
    	return;
    }    	
    
    /*var mMissionIDH = WaitContGrid.getRowColData( SelNo- 1, 10);
    var mSubMissionIDH = WaitContGrid.getRowColData( SelNo- 1, 11);*/
    var mMissionIDH = tMissionID;
    var mSubMissionIDH = tSubMissionID;
    document.all('MissionIDH').value = mMissionIDH;
    document.all('SubMissionIDH').value = mSubMissionIDH;
    
	if(document.all('WaitReason').value=="")
	{
		alert("核保等待原因不能为空！");
    	return;
  	}
	if(document.all('WaitReason').value=="05"&&(document.all('Content').value==null||document.all('Content').value==""))
	{
		alert("请录入核保员说明！");
    	return;
  	}  	

  //alert(tempSubMissionID);
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

function initClick()
{
	var SelNo = WaitContGrid.getSelNo();
    var mUniteNo = WaitContGrid.getRowColData( SelNo- 1, 1);
    document.all('UniteNo').value = mUniteNo;//显示并单号--为等待投保单的合同号
}

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
    top.close();   
   //queryWaitCont(document.all('InsuredNoH').value);
  }
}

//操作履历查询
function QueryRecord(){

    var SelNo = WaitContGrid.getSelNo();
    if(SelNo==0)
    {
        alert("请先选择未承保投保单！");
        return;
    }
    var mContNo = WaitContGrid.getRowColData( SelNo- 1, 1);
    
	window.open("./RecordQueryMain.jsp?ContNo="+mContNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//查询所有未承保投保单
function queryWaitCont(tInsuredNo)
{	
	var sqlid917160057="DSHomeContSql917160057";
var mySql917160057=new SqlClass();
mySql917160057.setResourceName("uw.WaitReasonSql");//指定使用的properties文件名
mySql917160057.setSqlId(sqlid917160057);//指定使用的Sql的id
mySql917160057.addSubPara(tContNo);//指定传入的参数
mySql917160057.addSubPara(tInsuredNo);//指定传入的参数
strsql=mySql917160057.getString();
	
//	strsql = " select a.contno, a.prtno, a.appntno, a.appntname, a.polapplydate "
//	       + " ,w.missionprop18 ,(select codename from ldcode where codetype='uwstatus' and code=w.missionprop18),nvl(w.lastoperator,'AUTO')"
//	       + " ,nvl((select nvl(defaultoperator,missionprop14) from lwmission where MissionID=w.MissionID and submissionid=w.submissionid and activityid='0000001100' and (defaultoperator is not null or (missionprop14 is not null and missionprop14<>'0000000000'))),'公共池')"
//	       //+ "  ,nvl((select Operator from LWLock where MissionID=w.MissionID and submissionid=w.submissionid),'公共池')"
//	       + " ,w.missionid,w.submissionid "
//	       + " from lwmission w,lccont a,lcinsured b where w.activityid='0000001100' and w.missionprop18>='1' and w.missionprop18<='8' and w.MissionProp2<>'"+tContNo+"' "
//	       + " and a.contno = w.MissionProp2 and a.contno=b.contno and b.insuredno='" +tInsuredNo+ "' ";    
	
  turnPage.queryModal(strsql,WaitContGrid); 
}

//初始化并单号
function initUniteNo()
{	
	var sqlid917160147="DSHomeContSql917160147";
var mySql917160147=new SqlClass();
mySql917160147.setResourceName("uw.WaitReasonSql");//指定使用的properties文件名
mySql917160147.setSqlId(sqlid917160147);//指定使用的Sql的id
mySql917160147.addSubPara(tContNo);//指定传入的参数
strsql=mySql917160147.getString();
	
//	strsql = " select w.missionprop21 "
//	       + " from lwmission w,lcinsured b "
//	       + " where w.activityid='0000001100' and w.missionprop18='6' "
//	       + " and w.MissionProp2=b.contno and w.missionprop21 is not null "
//	       + " and b.insuredno in (select insuredno from lcinsured where contno='"+tContNo+"') ";    
	
    turnConfirmPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (!turnConfirmPage.strQueryResult) {
    		//合同号作为并单号
    		document.all('UniteNo').value = tContNo;
    	}
    	else
    	{
    		turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);      
		      document.all('UniteNo').value = turnConfirmPage.arrDataCacheSet[0][0]; 
    	}
    		 
}

//查询被保人客户号
function queryInsuredNo()
{
		var sqlid917160238="DSHomeContSql917160238";
var mySql917160238=new SqlClass();
mySql917160238.setResourceName("uw.WaitReasonSql");//指定使用的properties文件名
mySql917160238.setSqlId(sqlid917160238);//指定使用的Sql的id
mySql917160238.addSubPara(tContNo);//指定传入的参数
strsql=mySql917160238.getString();

		
//		strsql = "select LCInsured.InsuredNo,LCInsured.Name,LCInsured.IDType,LCInsured.IDNo,case when LCInsured.INSUREDSTAT='1' then '已暂停' end from LCInsured where 1=1"
//					  + " and LCInsured.Contno = '" + tContNo + "'";
  turnPage1.queryModal(strsql,PolAddGrid);
}

/*********************************************************************
 *  查询被保人客户号，进入被保人并单信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsuredInfo()
{
	  var tSelNo = PolAddGrid.getSelNo();
		var tInsuredNo = PolAddGrid.getRowColData(tSelNo - 1,1);
         document.all('InsuredNoH').value = tInsuredNo;	
        // document.all('UniteNo').value = tInsuredNo;
         queryWaitCont(tInsuredNo);	
}

/*********************************************************************
 *  查询投保单核保等待原因，并显示出来
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function iniReason()
{	  
     //initial
	//alert(tOtherFlag);
     if(tType == '2')
	 {	    
	    document.all('uwButton6').disabled=true;
	     sure.disabled=true;
	    // alert(2);
	     document.all('Content').readOnly = true;
	     document.all('WaitReason').readOnly = true;
	     divQuery.style.display='';
	 }         
     //query
     var strsql="";
     if(tOtherFlag=="1"){//alert(183);
    	 
    	 var sqlid917160322="DSHomeContSql917160322";
var mySql917160322=new SqlClass();
mySql917160322.setResourceName("uw.WaitReasonSql");//指定使用的properties文件名
mySql917160322.setSqlId(sqlid917160322);//指定使用的Sql的id
mySql917160322.addSubPara(tContNo);//指定传入的参数
strsql=mySql917160322.getString();

    	 
//    	 strsql = " select MissionProp1,MissionProp10,MissionProp4 "
//		       + " from lbmission w where w.activityid='0000001100' and w.MissionProp2 in (select prtno from lccont where contno='"+tContNo+"') ";
     }else{//alert(186);
		  
		  var sqlid917160404="DSHomeContSql917160404";
var mySql917160404=new SqlClass();
mySql917160404.setResourceName("uw.WaitReasonSql");//指定使用的properties文件名
mySql917160404.setSqlId(sqlid917160404);//指定使用的Sql的id
mySql917160404.addSubPara(tContNo);//指定传入的参数
strsql=mySql917160404.getString();
		  
//		  strsql = " select MissionProp1,MissionProp10,MissionProp4 "
//		       + " from lwmission w where w.activityid='0000001100' and w.MissionProp2='"+tContNo+"' ";
     }//prompt("",strsql);
	  turnConfirmPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
      turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);
      document.all('PrtNo').value = turnConfirmPage.arrDataCacheSet[0][0];
      document.all('ManageCom').value = turnConfirmPage.arrDataCacheSet[0][1];
      document.all('AgentCode').value = turnConfirmPage.arrDataCacheSet[0][2];     
	  
}  

/*********************************************************************
 *  查询投保单核保等待原因，并显示出来
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showReasonInfo()
{    
     //query
    // alert(tOtherFlag);
	if(tOtherFlag=='1'){
		var sqlid917160521="DSHomeContSql917160521";
var mySql917160521=new SqlClass();
mySql917160521.setResourceName("uw.WaitReasonSql");//指定使用的properties文件名
mySql917160521.setSqlId(sqlid917160521);//指定使用的Sql的id
mySql917160521.addSubPara(tContNo);//指定传入的参数
mySql917160521.addSubPara(tContNo);//指定传入的参数
strsql=mySql917160521.getString();
		
//		strsql = " select w.missionprop22,(select codename from ldcode where codetype='waitreason' and code=w.missionprop22),w.missionprop23,w.missionprop21 "
//		       + " from lwmission w where w.activityid='0000001100' and w.missionprop18 ='6' and w.MissionProp2 in (select prtno from lccont where contno='"+tContNo+"') "
//		       + " union "
//		       + " select w.missionprop22,(select codename from ldcode where codetype='waitreason' and code=w.missionprop22),w.missionprop23,w.missionprop21 "
//		       + " from lbmission w where w.activityid='0000001100' and w.missionprop18 ='6' and w.MissionProp2 in (select prtno from lccont where contno='"+tContNo+"') ";
	}else{
	  var sqlid917160726="DSHomeContSql917160726";
var mySql917160726=new SqlClass();
mySql917160726.setResourceName("uw.WaitReasonSql");//指定使用的properties文件名
mySql917160726.setSqlId(sqlid917160726);//指定使用的Sql的id
mySql917160726.addSubPara(tContNo);//指定传入的参数
strsql=mySql917160726.getString();

	  
	  
//	  strsql = " select w.missionprop22,(select codename from ldcode where codetype='waitreason' and code=w.missionprop22),w.missionprop23,w.missionprop21 "
//	       + " from lwmission w where w.activityid='0000001100' and w.missionprop18 ='6' and w.MissionProp2='"+tContNo+"' ";
	}
	  turnConfirmPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
      if (!turnConfirmPage.strQueryResult) {
    		alert("没有核保等待信息！");
    		return false;
    	}    	
    	      turnConfirmPage.arrDataCacheSet = decodeEasyQueryResult(turnConfirmPage.strQueryResult);      
		      document.all('WaitReason').value = turnConfirmPage.arrDataCacheSet[0][0];
		      document.all('Reason').value = turnConfirmPage.arrDataCacheSet[0][1];
		      document.all('Content').value = turnConfirmPage.arrDataCacheSet[0][2];  
		      document.all('UniteNo').value = turnConfirmPage.arrDataCacheSet[0][3];    
			  //alert(5);
      
}   

