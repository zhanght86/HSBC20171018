/*********************************************************************
 *  程序名称：EdorManuUWInsured.js
 *  程序功能：人工核保被保人信息页面函数
 *  创建日期：2005-06-20 18:48:36
 *  创建人  ：liurx
 *  更新记录：  更新人    更新日期     更新原因/内容
 *********************************************************************
 */

var showInfo;
var turnPage = new turnPageClass();  
var turnPage1 = new turnPageClass();

/*********************************************************************
 *  查询核保被保人信息
 *  描述  ：  先查保全被保人表，如果无信息则查询承保被保人表
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryInsuredInfo()
{
	
	var tContNo=fm.ContNo.value;	
	var tEdorNo=fm.EdorNo.value;
	var tInsuredNo=fm.InsuredNo.value;
	
//    var strSql = "select InsuredNo,Name,(select codename from ldcode where trim(code) = trim(sex) and codetype = 'sex'),"
//                      +"birthday,(select codename from ldcode where trim(code) = trim(NativePlace) and codetype = 'nativeplace'),"
//                      +"(select codename from ldcode where trim(code) = trim(RelationToMainInsured) and codetype = 'relation'),"
//                      +"(select codename from ldcode where trim(code) = trim(RelationToAppnt) and codetype = 'relation'),"
//                      +"(select occupationName from ldOccupation where trim(ldoccupation.occupationcode) = trim(lpinsured.OccupationCode)),"
//                      +"(select codename from ldcode where trim(code) = trim(OccupationType) and codetype = 'occupationtype') "
//                      +"from lpinsured where 1=1 "
//                      +"and ContNo='"+tContNo+"' "
//                      +"and EdorNo='"+tEdorNo+"' "
//				      +"and InsuredNo='"+tInsuredNo+"'";
	
    var sqlid1="EdorManuUWInsuredSql1";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("bq.EdorManuUWInsuredSql");
 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
 	mySql1.addSubPara(tContNo);//指定传入参数
 	mySql1.addSubPara(tEdorNo);
 	mySql1.addSubPara(tInsuredNo);
 	var strSql = mySql1.getString();
    
	var brr = easyExecSql(strSql);
	
    if (brr)
    {
         brr[0][0]==null||brr[0][0]=='null'?'0':fm.InsuredNo.value  = brr[0][0];
         brr[0][1]==null||brr[0][1]=='null'?'0':fm.Name.value  = brr[0][1];
         brr[0][2]==null||brr[0][2]=='null'?'0':fm.Sex.value  = brr[0][2];
         brr[0][3]==null||brr[0][3]=='null'?'0':fm.InsuredAppAge.value  = brr[0][3];
         brr[0][4]==null||brr[0][4]=='null'?'0':fm.NativePlace.value  = brr[0][4];
         brr[0][5]==null||brr[0][5]=='null'?'0':fm.RelationToMainInsured.value  = brr[0][5];
         brr[0][6]==null||brr[0][6]=='null'?'0':fm.RelationToAppnt.value  = brr[0][6];
         brr[0][7]==null||brr[0][7]=='null'?'0':fm.OccupationCode.value  = brr[0][7];
         brr[0][8]==null||brr[0][8]=='null'?'0':fm.OccupationType.value  = brr[0][8];
        
        
    }
    else
    {
//    	strSql = "select InsuredNo,Name,(select codename from ldcode where trim(code) = trim(sex) and codetype = 'sex'),"
//    	              +"birthday,(select codename from ldcode where trim(code) = trim(NativePlace) and codetype = 'nativeplace'),"
//                      +"(select codename from ldcode where trim(code) = trim(RelationToMainInsured) and codetype = 'relation'),"
//                      +"(select codename from ldcode where trim(code) = trim(RelationToAppnt) and codetype = 'relation'),"
//                      +"(select occupationName from ldOccupation where trim(ldoccupation.occupationcode) = trim(lcinsured.OccupationCode)),"
//                      +"(select codename from ldcode where trim(code) = trim(OccupationType) and codetype = 'occupationtype') "
//                      +" from lcinsured where 1=1"
//                      +" and ContNo='"+tContNo+"'"
//                      +" and InsuredNo='"+tInsuredNo+"'";
    	
    	var sqlid2="EdorManuUWInsuredSql2";
     	var mySql2=new SqlClass();
     	mySql2.setResourceName("bq.EdorManuUWInsuredSql");
     	mySql2.setSqlId(sqlid2); //指定使用SQL的id
     	mySql2.addSubPara(tContNo);//指定传入参数
     	mySql2.addSubPara(tInsuredNo);
     	strSql = mySql2.getString();
    	
        brr = easyExecSql(strSql);
        if (brr)
        {            
    	    brr[0][0]==null||brr[0][0]=='null'?'0':fm.InsuredNo.value  = brr[0][0];
            brr[0][1]==null||brr[0][1]=='null'?'0':fm.Name.value  = brr[0][1];
            brr[0][2]==null||brr[0][2]=='null'?'0':fm.Sex.value  = brr[0][2];
            brr[0][3]==null||brr[0][3]=='null'?'0':fm.InsuredAppAge.value  = brr[0][3];
            brr[0][4]==null||brr[0][4]=='null'?'0':fm.NativePlace.value  = brr[0][4];
            brr[0][5]==null||brr[0][5]=='null'?'0':fm.RelationToMainInsured.value  = brr[0][5];
            brr[0][6]==null||brr[0][6]=='null'?'0':fm.RelationToAppnt.value  = brr[0][6];
            brr[0][7]==null||brr[0][7]=='null'?'0':fm.OccupationCode.value  = brr[0][7];
            brr[0][8]==null||brr[0][8]=='null'?'0':fm.OccupationType.value  = brr[0][8];
        }
        else
        {
        	alert("被保人信息查询失败！");
        	return "";
        }
     }
    return true;
}



/*********************************************************************
 *  查询核保被保人险种信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryRiskInfo()
{
		
	var tEdorNo=fm.EdorNo.value;
	var tInsuredNo=fm.InsuredNo.value;
	var tContNo=fm.ContNo.value;

	
//	var strSql = "select a.EdorNo,a.EdorType,a.polno,a.MainPolNo,a.riskcode,b.riskname,a.Prem,a.Amnt,a.CValiDate,a.EndDate,a.PayIntv,a.PayYears,"
//	                        + "(select c.codename from ldcode c where c.codetype = 'edoruwstate' and trim(c.code)=trim(a.UWFlag)),a.UWFlag"
//	                        + " from LPPol a,lmrisk b where 1=1 and a.appflag <> '4' "
//							+ " and edorno='"+tEdorNo+"'"
//							+ " and contno='"+tContNo+"'"
//							//XinYQ modified on 2006-07-31 : 支持连生被保人 : OLD : + " and insuredno ='"+tInsuredNo+"'"
//							+ " and InsuredNo in ('" + tInsuredNo + "', (select MainCustomerNo from LCInsuredRelated where CustomerNo = '" + tInsuredNo + "'))"
//							+ " and b.riskcode = a.riskcode";
	
	       var sqlid3="EdorManuUWInsuredSql3";
 	       var mySql3=new SqlClass();
 	       mySql3.setResourceName("bq.EdorManuUWInsuredSql");
 	       mySql3.setSqlId(sqlid3); //指定使用SQL的id
 	       mySql3.addSubPara(tEdorNo);//指定传入参数
 	       mySql3.addSubPara(tContNo);
 	       mySql3.addSubPara(tInsuredNo);
 	       mySql3.addSubPara(tInsuredNo);
 	       var strSql = mySql3.getString();
	
    var brr=easyExecSql(strSql);
    
    if(brr)
    {
    	initRiskGrid();
        turnPage.queryModal(strSql, RiskGrid);
    	
    	
    }
    else
    {
    	
    	alert("该保单下没有险种需要保全核保！");
    	
    	divEdorManuUWResultInfo.style.display='none'; //隐藏下核保结论的输入框
    	DivLPPol.style.display = 'none';
    	divReturn.style.display='';
        return;
    	
    }


  return true;  					
}


 /*********************************************************************
 *  鼠标双击初始化核保结论选项
 *  描述: 初始化核保结论选项
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initUWState(cObjCode, cObjName)
{
	var UWType = "EdorItem";
	var UWStateCode;
    UWStateCode = "edorpoluw";
    initUWCodeData(UWStateCode);
    return showCodeListEx('nothis',[cObjCode,cObjName],[0,1],null,null,null,1);
}
/*********************************************************************
 *  初始化核保结论选项
 *  描述: 初始化核保结论选项
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function onKeyUpUWState(cObjCode, cObjName)
{
	var UWType = "EdorItem";
	var UWStateCode;

    UWStateCode = "edorpoluw";
    initUWCodeData(UWStateCode);
    return showCodeListKeyEx('nothis',[cObjCode,cObjName],[0,1],null,null,null,1);
}
//初始化核保结论代码，由于要按othersign排序，故写此函数
function initUWCodeData(tCodeType)
{
    	var i,j,m,n;
	    var returnstr;
	    var tTurnPage = new turnPageClass();

//	    var strSQL = " select code,codename from ldcode where codetype = '"+tCodeType+"' order by othersign ";
	    
		var sqlid4="EdorManuUWInsuredSql4";
     	var mySql4=new SqlClass();
     	mySql4.setResourceName("bq.EdorManuUWInsuredSql");
     	mySql4.setSqlId(sqlid4); //指定使用SQL的id
     	mySql4.addSubPara(tCodeType);//指定传入参数
     	var strSQL = mySql4.getString();
	    
	    tTurnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
        if (tTurnPage.strQueryResult == "")
        {
          return "";
        }
        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);  
        var returnstr = "";
        var n = tTurnPage.arrDataCacheSet.length;
        if (n > 0)
        {
        	for( i = 0;i < n; i++)
        	{
  	        	m = tTurnPage.arrDataCacheSet[i].length;
        		if (m > 0)
  		        {
  		        	for( j = 0; j< m; j++)
  			        {
  		 		        if (i == 0 && j == 0)
  			        	{
  				        	returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
  			        	}
  			        	if (i == 0 && j > 0)
  			        	{
  					        returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
  				        }
  			         	if (i > 0 && j == 0)
  				        {
  					        returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
  				        }
  			        	if (i > 0 && j > 0)
  				        {
  					        returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
  				        }
     		        }
  		        }
  	            else
  	            {
  		          	alert("查询核保结论描述失败!!");
  			        return "";
  		        }
             }
         }
         else
         {
	         alert("查询核保结论描述失败!");
	         return "";
         }
         fm.EdorUWState.CodeData = returnstr;
         //return returnstr;
}
/*********************************************************************
 *  取出保全险种信息
 *  描述: 取出保全险种信息 
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getRiskInfo()
{
        
    var tSel = RiskGrid.getSelNo() - 1;
    fm.EdorType.value   = RiskGrid.getRowColData(tSel, 2);
    fm.PolNo.value      = RiskGrid.getRowColData(tSel, 3);
    UWcancel();
    showFormerUWInfo();//显示原核保结论
    showInsuredRelated();//显示连生被保人信息
          
}
/*********************************************************************
 *  显示原核保结论
 *  描述: 显示原核保结论(对于新增附加险还没有原核保结论，lcuwmaster中不会有记录)
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showFormerUWInfo()
{
 	var tPolNo = fm.PolNo.value;
 	var tEdorNo = fm.EdorNo.value;
// 	var strSql = " select passflag,(select codename from ldcode where codetype = 'uwstate1' and code = passflag),uwidea "
// 	           + " from lpuwmaster where polno='"+tPolNo+"' and edorno='"+tEdorNo+"'";
 	
 	var sqlid5="EdorManuUWInsuredSql5";
 	var mySql5=new SqlClass();
 	mySql5.setResourceName("bq.EdorManuUWInsuredSql");
 	mySql5.setSqlId(sqlid5); //指定使用SQL的id
 	mySql5.addSubPara(tPolNo);//指定传入参数
 	mySql5.addSubPara(tEdorNo);
 	var strSql = mySql5.getString();
 	
 	var brr=easyExecSql(strSql);
 	if(brr)
 	{
 		fm.FormerUWState.value = brr[0][0];
 		fm.FormerUWStateName.value = brr[0][1];
 		fm.FormerUWIdea.value = brr[0][2];
 		divFormerRiskUWInfo.style.display = '';
 	}
    else
    {
 		fm.FormerUWState.value = "";
 		fm.FormerUWStateName.value = "";
 		fm.FormerUWIdea.value = "";
 		divFormerRiskUWInfo.style.display = 'none';
    }
}
/*********************************************************************
 *  显示连生被保人信息
 *  描述: 显示该险种下连生被保人信息 ，p表没有则查c表
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsuredRelated()
{
	
	var tPolNo = fm.PolNo.value;
	var tEdorNo = fm.EdorNo.value;
	
//	var strSql = "select customerNo,name,(select codename from ldcode where trim(code)=trim(lpinsuredrelated.sex) and codetype='sex'),"
//	               +"birthday,(select codename from ldcode where trim(code) = trim(IDType) and codetype='idtype'),"
//	               +"idno,maincustomerno,(select codename from ldcode where trim(code)=trim(relationtoinsured) and codetype='relation') "
//	               +"from lpinsuredrelated "
//	               +"where edorno='"+tEdorNo+"' "+"and polno='"+tPolNo+"'";
	
	var sqlid6="EdorManuUWInsuredSql6";
 	var mySql6=new SqlClass();
 	mySql6.setResourceName("bq.EdorManuUWInsuredSql");
 	mySql6.setSqlId(sqlid6); //指定使用SQL的id
 	mySql6.addSubPara(tEdorNo);//指定传入参数
 	mySql6.addSubPara(tPolNo);
 	var strSql = mySql6.getString();
	
	var brr=easyExecSql(strSql);
	
	if(brr)
	{
		
    	initInsuredRelatedGrid();
        turnPage1.queryModal(strSql, InsuredRelatedGrid);
        DivLPInsuredRelated.style.display = '';
		
	}
    else
    {   
//    	strSql = "select customerNo,name,(select codename from ldcode where trim(code)=trim(lcinsuredrelated.sex) and codetype='sex'),"
//	               +"birthday,(select codename from ldcode where trim(code) = trim(IDType) and codetype='idtype'),"
//	               +"idno,maincustomerno,(select codename from ldcode where trim(code)=trim(relationtoinsured) and codetype='relation') "
//	               +"from lcinsuredrelated "
//	               +"where polno='"+tPolNo+"'";
    	
    	var sqlid7="EdorManuUWInsuredSql7";
     	var mySql7=new SqlClass();
     	mySql7.setResourceName("bq.EdorManuUWInsuredSql");
     	mySql7.setSqlId(sqlid7); //指定使用SQL的id
     	mySql7.addSubPara(tPolNo);//指定传入参数
     	strSql = mySql7.getString();
    	
	    brr=easyExecSql(strSql);
	    
	    if(brr)
	    {
	    	initInsuredRelatedGrid();
            turnPage1.queryModal(strSql, InsuredRelatedGrid);
            DivLPInsuredRelated.style.display = '';
        }
        else
        {
    	    //alert("该险种下没有连生被保人！");
    	    DivLPInsuredRelated.style.display = 'none';
    	    return;
    	}
    	
    }
	
	return true;
	
}

/*********************************************************************
 *  返回
 *  描述: 页面层显示控制
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function returnParent()
{
    parent.close();
    top.opener.focus();
}
/*********************************************************************
 *  保全险种级核保结论提交
 *  描述: 保全险种级核保结论提交 
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function UWSubmit()
{
    
    
    

    if (fm.EdorNo.value == null     || fm.EdorNo.value == "" ||
            fm.EdorType.value == null   || fm.EdorType.value == "" ||
            fm.ContNo.value == null     || fm.ContNo.value == "" ||
            fm.InsuredNo.value == null  || fm.InsuredNo.value == "" ||
            fm.PolNo.value == null      || fm.PolNo.value == "" )
        {
            alert("请选择要核保的险种！");
            return false;
        }
   
    
    var tEdorUWState    = fm.EdorUWState.value;
    var tUWDelay        = fm.UWDelay.value;
    var tUWIdea         = fm.UWIdea.value;
    var tUWPopedomCode  = fm.UWPopedomCode.value;
    
    if(tEdorUWState == "")
        {
        alert("请录入保全核保结论!");  
        return ;
    }   
    if(tEdorUWState == "6" && tUWPopedomCode == "")
    {
        alert("请选择上报级别!");  
        return ;
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
    document.all('ActionFlag').value = "UWMANUSUBMIT";
    fm.action = "./EdorAppManuUWSave.jsp";
    fm.submit();
}
/*********************************************************************
 *  后台执行完毕反馈信息
 *  描述: 后台执行完毕反馈信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit1( FlagStr, content )
{
    showInfo.close();
         
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    if (FlagStr == "Succ" ) 
    {
		if (document.all('ActionFlag').value == "UWMANUSUBMIT")
		{
		    initRiskGrid();
		    queryRiskInfo();
		}
        if (document.all('ActionFlag').value == "UWMANUAPPLY")
        {
            //人工核保申请成功后需要重新查询人工核保确认节点的MissionID和SubMissionID
            //查询保全人工核保确认节点
//            strSQL =  " select submissionid " 
//                    + " from lwmission " 
//                    + " where activityid = '0000000006' " 
//                    + " and missionid = '" + fm.MissionID.value + "'";
            
            var sqlid8="EdorManuUWInsuredSql8";
         	var mySql8=new SqlClass();
         	mySql8.setResourceName("bq.EdorManuUWInsuredSql");
         	mySql8.setSqlId(sqlid8); //指定使用SQL的id
         	mySql8.addSubPara(fm.MissionID.value);//指定传入参数
         	strSQL = mySql8.getString();
            
            var brr = easyExecSql(strSQL); 
            
            if ( brr )
            {
                brr[0][0]==null||brr[0][0]=='null'?'0':fm.SubMissionID.value = brr[0][0];
            }

        }
    }
    
    if (FlagStr == "Fail" )
    {
        if (document.all('ActionFlag').value == "UWMANUAPPLY")
        {
            //人工核保申请
            
            divEdorAppUWResultInfo.style.display='none';
            
        }

    }

}
/*********************************************************************
 *  保全核保取消
 *  描述: 清空核保结论输入框
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function UWcancel()
{   
    document.all('EdorUWState').value = "";
    document.all('EdorUWStateName').value = "";
    document.all('UWDelay').value = "";
    document.all('UWPopedomCode').value = "";
    document.all('UWIdea').value = "";
}
//选择核保结论后触发
function uwSelected()
{
	var uw = fm.EdorUWState.value;
	switch(uw)
    {
    	case "":
    	case "2": divUwDelayTitle.style.display = '';
    	          divUwDelay.style.display = '';
    	          break;
    	default:  divUwDelayTitle.style.display = 'none';
    	          divUwDelay.style.display = 'none';
    	          break;
    }
}


/*************按钮功能区域*******Begin******************************************2005-06-22******************/



//被保人健康告知查询
function queryHealthImpart()
{
	
	var newWindow = window.open("./EdorHealthImpartQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1", 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
//查询体检结果
function queryHealthReportResult()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;
  var cInsuredNo = fm.InsuredNo.value;
 
 
  window.open("../uw/UWBeforeHealthQMain.jsp?ContNo="+cContNo+"&CustomerNo="+cInsuredNo+"&type=1");
 
  showInfo.close();	
}
//被保人保额累计提示信息
function amntAccumulate()
{
	window.open("../uw/AmntAccumulateMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}
//被保人已承保保单查询
function queryProposal()
{
	
	window.open("../uw/ProposalQueryMain.jsp?CustomerNo="+fm.InsuredNo.value,"window1");
}
//被保人未承保投保单查询
function queryNotProposal(){
	
	window.open("../uw/NotProposalQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}
//被保人既往理赔查询
function queryClaim(){
	
	window.open("../uw/ClaimQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}

//被保人既往保全查询
function queryEdor()
{
	//window.open("../uw/EdorQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
	var CustomerNo = fm.InsuredNo.value;
	var EdorAcceptNo = fm.EdorAcceptNo.value;
	var varSrc = "CustomerNo="+CustomerNo+"&EdorAcceptNo="+EdorAcceptNo; 
    window.open("../bq/EdorAgoQueryMain.jsp?"+varSrc,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");	

}

/*********************************************************************
 *  特约录入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */ 
function showSpec()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
  var tContNo = fm.ContNo.value;
  var tPolNo = fm.PolNo.value;
  var tPrtNo = fm.PrtNo.value;
  var tEdorNo = fm.EdorNo.value;
  var tMissionID = fm.MissionID.value;
  var tSubMissionID = fm.SubMissionID.value;
  var tEdorType = fm.EdorType.value;
  
  if (tContNo != ""&& tEdorNo !="" && tMissionID != "" && tPolNo != "")
  {  
  	
  	
  	var strTran = "ContNo="+tContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID
                             +"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&PrtNo="+tPrtNo+"&PolNo="+tPolNo;
                             
  	window.open("./EdorUWManuSpecMain.jsp?"+strTran,"window1");  	
  	showInfo.close();
                              
  }
  else
  {
  	showInfo.close();
  	alert("请先选择险种信息!");
  }
}

/*********************************************************************
 *  加费录入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */ 
function showAdd()
{
//  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
  var tContNo = fm.ContNo.value;
  var tEdorNo = fm.EdorNo.value;
  var tPolNo = fm.PolNo.value;
  var tMissionID = fm.MissionID.value;
  var tSubMissionID = fm.SubMissionID.value;
  var tInsuredNo = fm.InsuredNo.value;
  var tEdorAcceptNo = fm.EdorAcceptNo.value;
 
  var strTran = "ContNo="+tContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID
               +"&EdorNo="+tEdorNo+"&InsuredNo="+tInsuredNo+"&EdorAcceptNo="+tEdorAcceptNo+"&PolNo="+tPolNo;
                             
  var newWindow = window.open("./EdorUWManuAddMain.jsp?"+strTran,"EdorUWManuAdd", 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//  showInfo.close();
  

}

//既往理赔查询
function queryClaim()
{
	
	window.open("../uw/ClaimQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1");
}




function NewChangeRiskPlan()
{
    var strSql="";
    var tContNo=fm.ContNo.value;//alert("contno=="+tContNo);return false;
    var tEdorNo=fm.EdorNo.value;//alert("tEdorNo=="+tEdorNo);return false;
    var MissionID=fm.MissionID.value;//alert("tEdorNo=="+tEdorNo);return false;
    var SubMissionID=fm.SubMissionID.value;//alert("tEdorNo=="+tEdorNo);return false;
    var InsuredNo=fm.InsuredNo.value;//alert("tEdorNo=="+tEdorNo);return false;
    var InsuredSumLifeAmnt=fm.InsuredSumLifeAmnt.value;//alert("tEdorNo=="+tEdorNo);return false;
    var InsuredSumHealthAmnt=fm.InsuredSumHealthAmnt.value;//alert("tEdorNo=="+tEdorNo);return false;
    var InsuredNo=fm.InsuredNo.value;//alert("tEdorNo=="+tEdorNo);return false;
    var EdorAcceptNo=fm.EdorAcceptNo.value;//alert("EdorAcceptNo=="+EdorAcceptNo);return false;
    var EdorType=fm.EdorType.value;//alert("EdorType=="+EdorType);return false;
    //strSql="select salechnl from lccont where contno='"+prtNo+"'";
//    strSql="select case lmriskapp.riskprop"
//            +" when 'I' then '1'"
//	        +" when 'G' then '2'"
//	        +" when 'Y' then '3'"
//	        +" when 'T' then '5'"
//           + " end"
//           + " from lmriskapp"
//           + " where riskcode in (select riskcode"
//           + " from lppol"
//           + " where polno = mainpolno"
//           + " and contno = '"+tContNo+"' and edorNo='"+tEdorNo+")" ; 
    
    var sqlid9="EdorManuUWInsuredSql9";
 	var mySql9=new SqlClass();
 	mySql9.setResourceName("bq.EdorManuUWInsuredSql");
 	mySql9.setSqlId(sqlid9); //指定使用SQL的id
 	mySql9.addSubPara(tContNo);//指定传入参数
 	mySql9.addSubPara(tEdorNo);
 	strSql = mySql9.getString();
    
    turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
    if(!turnPage.strQueryResult){
//	    strSql = "select case lmriskapp.riskprop"
//	            +" when 'I' then '1'"
//		        +" when 'G' then '2'"
//		        +" when 'Y' then '3'"
//		        +" when 'T' then '5'"
//	           + " end"
//	           + " from lmriskapp"
//	           + " where riskcode in (select riskcode"
//	           + " from lcpol"
//	           + " where polno = mainpolno"
//	           + " and contno = '"+tContNo+"')"    
	       
	    var sqlid10="EdorManuUWInsuredSql10";
	 	var mySql10=new SqlClass();
	 	mySql10.setResourceName("bq.EdorManuUWInsuredSql");
	 	mySql10.setSqlId(sqlid10); //指定使用SQL的id
	 	mySql10.addSubPara(tContNo);//指定传入参数
	 	strSql = mySql10.getString();
	       
    }       
    arrResult = easyExecSql(strSql);
    if(arrResult==null){
    	if(_DBT==_DBO){
//    		  strSql = " select * from ("
//                  + " select case missionprop5"
//                  + " when '05' then '3'"
//                  + " when '12' then '3'"
//                  + " when '13' then '5'"
//                  + " else '1'"
//                  + " end"
//                  + " from lbmission"
//                  + " where missionprop1 = '"+tContNo+"'"
//                  + " and activityid = '0000001099'"
//                  + " union"
//                  + " select case missionprop5"
//                  + " when 'TB05' then '3'"
//                  + " when 'TB12' then '3'"
//                  + " when 'TB06' then '5'"
//                  + " else '1'"
//                  + " end"
//                  + " from lbmission"
//                  + " where missionprop1 = '"+tContNo+"'"
//                  + " and activityid = '0000001098'"
//                  + ") where rownum=1";
    		  
    		    var sqlid11="EdorManuUWInsuredSql11";
    		 	var mySql11=new SqlClass();
    		 	mySql11.setResourceName("bq.EdorManuUWInsuredSql");
    		 	mySql11.setSqlId(sqlid11); //指定使用SQL的id
    		 	mySql11.addSubPara(tContNo);//指定传入参数
    		 	mySql11.addSubPara(tContNo);
    		 	strSql = mySql11.getString();
    		  
    	}else if(_DBT==_DBM){
//    		  strSql = " select * from ("
//                  + " select case missionprop5"
//                  + " when '05' then '3'"
//                  + " when '12' then '3'"
//                  + " when '13' then '5'"
//                  + " else '1'"
//                  + " end"
//                  + " from lbmission"
//                  + " where missionprop1 = '"+tContNo+"'"
//                  + " and activityid = '0000001099'"
//                  + " union"
//                  + " select case missionprop5"
//                  + " when 'TB05' then '3'"
//                  + " when 'TB12' then '3'"
//                  + " when 'TB06' then '5'"
//                  + " else '1'"
//                  + " end"
//                  + " from lbmission"
//                  + " where missionprop1 = '"+tContNo+"'"
//                  + " and activityid = '0000001098'"
//                  + ") ax limit 1";
    		  
    		var sqlid12="EdorManuUWInsuredSql12";
  		 	var mySql12=new SqlClass();
  		 	mySql12.setResourceName("bq.EdorManuUWInsuredSql");
  		 	mySql12.setSqlId(sqlid12); //指定使用SQL的id
  		 	mySql12.addSubPara(tContNo);//指定传入参数
  		 	mySql12.addSubPara(tContNo);
  		 	strSql = mySql12.getString();
    		  
    	}
      
        arrResult = easyExecSql(strSql);               
    }
    var BankFlag = arrResult[0][0];
    //alert("BankFlag="+arrResult[0][0]); 
    
    
 //   var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+tContNo+"'";
    
        var sqlid13="EdorManuUWInsuredSql13";
	 	var mySql13=new SqlClass();
	 	mySql13.setResourceName("bq.EdorManuUWInsuredSql");
	 	mySql13.setSqlId(sqlid13); //指定使用SQL的id
	 	mySql13.addSubPara(tContNo);//指定传入参数
	 	var strSql2 = mySql13.getString();
    
    var crrResult = easyExecSql(strSql2);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }
     
     var NoType = "1";
	var InsuredNo = document.all('InsuredNo').value;
	window.open("../uw/BQChangeRiskPlanMain.jsp?ContNo="+tContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&InsuredNo="+InsuredNo+"&InsuredSumLifeAmnt="+fm.InsuredSumLifeAmnt.value+"&InsuredSumHealthAmnt="+fm.InsuredSumHealthAmnt.value+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3&EdorNo="+tEdorNo+"&EdorAcceptNo="+EdorAcceptNo+"&EdorType="+EdorType,"window1");  	

}



/*************按钮功能区域*******End***********************************************2005-06-22******************/