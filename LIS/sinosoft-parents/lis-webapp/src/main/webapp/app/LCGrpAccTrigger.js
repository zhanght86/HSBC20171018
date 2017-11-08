var turnPage = new turnPageClass();
var queryResult;
var arrDataSet;
var initDataSet;
var showInfo;
var arrResult=new Array();
var arrResult2=new Array();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

function afterCodeSelect( cCodeName, Field )
{
    try
    {
        if(cCodeName=='accscope')
        {
            if(Field.value=='00')
            {
                document.all('subRiskAccCode').style.display="none";
                document.all('RiskAccCode').style.display='';
                //document.all('subRiskAccCode').style.visibility="hidden"; 
                //document.all('RiskAccCode').style.visibility="visible";
            }
            if(Field.value=='01')
            {
                document.all('RiskAccCode').style.display="none";
                document.all('subRiskAccCode').style.display='';
            }
        }
        
        //alert(cCodeName);
        if(cCodeName=='subRiskAccCode')
        {
            var str=Field.value;
            var PayPlanCode=str.substring(0,6);
            //alert(PayPlanCode);
            var InsuAccNo=str.substring(6,12);
            document.all('PayPlanCode').value=PayPlanCode;
            document.all('InsuAccNo').value=InsuAccNo;
            document.all('ToPayPlanCode').value=PayPlanCode;
        }
        
        if(cCodeName=='riskacccode')
        {
            var str=Field.value;
            var PayPlanCode=str.substring(0,6);
            var InsuAccNo=str.substring(6,12);
            document.all('PayPlanCode').value=PayPlanCode;
            document.all('InsuAccNo').value=InsuAccNo;
            document.all('ToPayPlanCode').value=PayPlanCode;
        }
        
        if(cCodeName=='actioncalmode')
        {
            //alert(cCodeName);
            try
            {
            
				var sqlid58="ContQuerySQL58";
				var mySql58=new SqlClass();
				mySql58.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
				mySql58.setSqlId(sqlid58);//指定使用的Sql的id
				mySql58.addSubPara(fm.PayPlanCode.value);//指定传入的参数
				mySql58.addSubPara(fm.InsuAccNo.value);//指定传入的参数
				mySql58.addSubPara(fm.ActionCalMode.value);//指定传入的参数
			    var strSQL=mySql58.getString();	
			
//                var strSQL="select ActionCalModeType,ActionCalCode,Value,CompareValue from LMAccTrigger where 1=1" + 
//                         getWherePart("PayPlanCode") + getWherePart("InsuAccNo") + getWherePart("ActionCalMode");
                turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
                if (turnPage.strQueryResult)
                {
                    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
                    fm.ActionCalModeType.value=turnPage.arrDataCacheSet[0][0];
                    fm.ActionCalCode.value=turnPage.arrDataCacheSet[0][1];
                    if(turnPage.arrDataCacheSet[0][2]!='null')
                        fm.Value.value=turnPage.arrDataCacheSet[0][2];
                    else
                        fm.Value.value='';
                    
                    if(turnPage.arrDataCacheSet[0][3]!='null')
                        fm.CompareValue.value=turnPage.arrDataCacheSet[0][3];
                    else
                        fm.CompareValue.value='';
                }
                //fm.Value.value='0000';
            }
            catch(ex)
            {
                alert("出现异常" + ex);
            }
        
        }
  	
        if(cCodeName=='GrpRisk')
        {
            getGrpPolNo();     	
            //加载当前险种（分红险）的缴费帐户
            var strRiskCode=Field.value;
			
					var sqlid59="ContQuerySQL59";
				var mySql59=new SqlClass();
				mySql59.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
				mySql59.setSqlId(sqlid59);//指定使用的Sql的id
				mySql59.addSubPara(fm.GrpContNo.value);//指定传入的参数
				mySql59.addSubPara(strRiskCode);//指定传入的参数
				mySql59.addSubPara(strRiskCode);//指定传入的参数
			    var strSql=mySql59.getString();	
			
//            var strSql="select b.InsuAccNo,b.PayPlanCode,b.RiskAccPayName,"
//                        + "(select a.toobjecttype from LCGrpAccTrigger a"
//                        + " where a.grpcontno='" + fm.GrpContNo.value 
//                        + "' and trim(a.riskcode)=trim(b.riskcode) and trim(a.insuaccno)=trim(b.insuaccno)"
//                        + " and trim(a.payplancode)=trim(b.payplancode) and rownum=1)"
//                        + " from LMRiskAccPay b where"
//                        + " b.riskcode='" + strRiskCode 
//                        + "' and b.insuaccno=(select LMRisktoAcc.InsuAccNo from LMRisktoAcc,LMRiskInsuAcc"
//                        + " where LMRisktoAcc.riskcode='"+strRiskCode+"'"
//                        + " and LMRisktoAcc.InsuAccNo=LMRiskInsuAcc.InsuAccNo"
//                        + " and LMRiskInsuAcc.BonusFlag='0')"
//                        + " order by b.payplancode";

            turnPage.queryModal(strSql,AccTriggerGrid);   
        
        }
        
        if(cCodeName=='toobjecttype')
        {
            if(Field.value=='00')
            {
                fm.ToObject.value='00';
                if(fm.InsuAccNo.value=='')
                {
                    alert("保险账户号码为空");
                    changeText(false);
                    return false;
                }
                fm.ToInsuAccNo.value=fm.InsuAccNo.value;
                fm.ToPolNo.value='';
                fm.ToDutycode.value='';
                changeText(true);
                //fm.ToPolNo.readOnly=true;
            }
            if(Field.value=='01')
            {
                fm.ToObject.value='01';
                if(InsuAcc("002")==false)
                   return false;
                fm.ToPolNo.value='';
                fm.ToDutycode.value='';
                changeText(true);
                //fm.ToInsuAccNo.value='000002';
            }
            if(Field.value=='02')
            {
                fm.ToObject.value='02';
                if(companyAcc("002")==false)
                return false; 
                changeText(true);
            }
            if(Field.value=='03')
            {
                fm.ToObject.value='02';
                if(companyAcc("001")==false)
                return false; 
                changeText(true);
            }
        }
    }
    catch(ex)
    {
    }
}

function InsuAcc(AccType)
{
	
				var sqlid60="ContQuerySQL60";
				var mySql60=new SqlClass();
				mySql60.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
				mySql60.setSqlId(sqlid60);//指定使用的Sql的id
				mySql60.addSubPara(fm.RiskCode.value);//指定传入的参数
				mySql60.addSubPara(AccType);//指定传入的参数
			    var sql=mySql60.getString();	
	
//    var sql="select lmi.insuaccno from lmriskinsuacc lmi,lmrisktoacc lmt where lmi.insuaccno=lmt.insuaccno " + 
//       	            "and lmt.riskcode='" + fm.RiskCode.value + "' and lmi.acctype='" + AccType + "'";
    var InsuResult=easyQueryVer3(sql,1,1,1);    	    
    if(!InsuResult)
    {
        alert("没有查到红利账户信息，请自已添写");
        changeText(false);
        return false;
    }
    var InsuDataSet=decodeEasyQueryResult(InsuResult);
    fm.ToInsuAccNo.value=InsuDataSet[0][0];
} 
 	
function companyAcc(AccType)
{
	
					var sqlid61="ContQuerySQL61";
				var mySql61=new SqlClass();
				mySql61.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
				mySql61.setSqlId(sqlid61);//指定使用的Sql的id
				mySql61.addSubPara(fm.GrpPolNo.value);//指定传入的参数
			    var strSQL=mySql61.getString();	
	
   // var strSQL="select polno from lcpol where poltypeflag='2' and grppolno='" + fm.GrpPolNo.value + "'";
    //alert(strSQL);
    var accResult=easyExecSql(strSQL);
    if(!accResult)
    {
        alert("没有查到企业帐户，请自已添写");
        changeText(false);
        return false;
    }
    //var accDataSet=decodeEasyQueryResult(InsuResult);
    //alert(accResult.length);
    if(accResult.length>1)
    {
        alert("保单险种下有多个企业账户，请自已添写");
        changeText(false);
        return false;
    }
    fm.ToPolNo.value=accResult[0];
    InsuAcc(AccType);
}
	
function changeText(ble)
{
    fm.ToObject.readOnly=ble;
    fm.ToInsuAccNo.readOnly=ble;
	fm.ToPolNo.readOnly=ble;
	fm.ToDutycode.readOnly=ble;
}
 	
function returnparent(){
	window.location.href = "GrpFeeInput.jsp?ProposalGrpContNo="+fm.GrpContNoIn.value+"&LoadFlag="+fm.LoadFlag.value;
}

function showAccTrigger()
{
    var i=AccTriggerGrid.getSelNo();
    var j=i-1;
    //alert(j);
    fm.GrpPolNo.value=initDataSet[j][0];
    fm.RiskCode.value=initDataSet[j][2];
    fm.AccType.value=initDataSet[j][3];
    if(fm.AccType.value=='00')
    {
        fm.RiskAccCode.style.display='';
        fm.subRiskAccCode.style.display="none";
        fm.RiskAccCode.value=initDataSet[j][4]+initDataSet[j][5];
    }
    if(fm.AccType.value=='01')
    {
        fm.RiskAccCode.style.display="none";
        fm.subRiskAccCode.style.display="";
        fm.subRiskAccCode.value=initDataSet[j][4]+initDataSet[j][5];
    }
    fm.PayPlanCode.value=initDataSet[j][4];
    fm.InsuAccNo.value=initDataSet[j][5];
    fm.RiskAccPayName.value=initDataSet[j][6];
    fm.ChgType.value=initDataSet[j][7];
    fm.ChgOperationType.value=initDataSet[j][8];
    fm.TriggerOrder.value=initDataSet[j][9];
    fm.Action.value=initDataSet[j][10];
    fm.ActionObject.value=initDataSet[j][12];
    fm.ActionCalMode.value=initDataSet[j][13];
    fm.ActionCalModeType.value=initDataSet[j][14];
    fm.ActionCalCode.value=initDataSet[j][15];
    fm.Value.value=initDataSet[j][16];
    fm.CompareValue.value=initDataSet[j][17];
    fm.ToObject.value=initDataSet[j][18];
    fm.ToObjectType.value=initDataSet[j][19];
    fm.ToPolNo.value=initDataSet[j][20];
    fm.ToDutycode.value=initDataSet[j][21];
    fm.ToPayPlanCode.value=initDataSet[j][22];
    fm.ToInsuAccNo.value=initDataSet[j][23];
}
	 
function submitForm()
{
	
	//if( verifyInput2() == false ) return false;
	if(document.all('RiskCode').value==null||document.all('RiskCode').value.length==0)
	{
	  	alert("请选择险种");
	  	return false;
	}
	document.all('mOperate').value="INSERT";
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); //提交
}

function afterSubmit( FlagStr , Content )
{
    showInfo.close();
    if(FlagStr=='Fail')
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content ;
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
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content ;
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
    //
    afterCodeSelect("GrpRisk",document.all('RiskCode'));
}	
	
function updateForm()
{
	
    if(AccTriggerGrid.getSelNo()==0)
    {
        alert("请选择要修改的记录");
        return false;
    }
    if(verifyInput2()==false)return false;
    document.all('mOperate').value="UPDATE";
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit();//修改
}
	
function deleteForm()
{
    if(AccTriggerGrid.getSelNo()==0)
    {
        alert("请选择要删除的记录");
        return false;
    }
    if(!confirm("真的要删除这条记录吗？"))
    {
        return false;
    }
    if(verifyInput2()==false)return false;
    document.all('mOperate').value='DELETE';
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit();//删除
}	
function getGrpPolNo()
{
	
				var sqlid62="ContQuerySQL62";
				var mySql62=new SqlClass();
				mySql62.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
				mySql62.setSqlId(sqlid62);//指定使用的Sql的id
				mySql62.addSubPara(fm.GrpPolNo.value);//指定传入的参数
				mySql62.addSubPara(fm.RiskCode.value);//指定传入的参数
			    var strSQL=mySql62.getString();	
	
   // var strSQL="select a.GrpPolNo from LCGrpPol a where a.GrpContNo='" + fm.GrpContNo.value + "' and a.RiskCode='" + fm.RiskCode.value+"'";
    turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
    if (turnPage.strQueryResult)
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        document.all('GrpPolNo').value=turnPage.arrDataCacheSet[0][0];
    }
}

function GrpPerPolDefine()
{
	var strSQL;
	
					var sqlid62="ContQuerySQL62";
				var mySql62=new SqlClass();
				mySql62.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
				mySql62.setSqlId(sqlid62);//指定使用的Sql的id
				mySql62.addSubPara(fm.GrpContNoIn.value);//指定传入的参数
			    strSQL=mySql62.getString();	
	
  	//strSQL = "select GrpContNo,ProposalGrpContNo,ManageCom,AppntNo,GrpName from LCGrpCont where GrpContNo = '" +fm.GrpContNoIn.value+ "'";
	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult)
	{
	 	
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		
		document.all('GrpContNo').value = turnPage.arrDataCacheSet[0][0];
		
		document.all('ProposalGrpContNo').value = turnPage.arrDataCacheSet[0][1];
		
		document.all('ManageCom').value = turnPage.arrDataCacheSet[0][2];
		
		document.all('AppntNo').value = turnPage.arrDataCacheSet[0][3];
		document.all('GrpName').value = turnPage.arrDataCacheSet[0][4];
		//document.all('RiskAccCode').style.visibility="hidden";
		//document.all('RiskAccCode').style.display="none";
	}
	return ;
}
	
function GrpPerPolDefineOld(){
	// 初始化表格

	var strSQL = "";

				var sqlid63="ContQuerySQL63";
				var mySql63=new SqlClass();
				mySql63.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
				mySql63.setSqlId(sqlid63);//指定使用的Sql的id
				mySql63.addSubPara(fm.GrpContNoIn.value);//指定传入的参数
			    strSQL=mySql63.getString();	


//	strSQL = "	select RiskCode,AccType,PayPlanCode,InsuAccNo,RiskAccPayName,chgtype," + 
//	"chgoperationtype,triggerorder,grppolno from LCGrpAccTrigger where GrpContNo='"+fm.GrpContNoIn.value+"'"; 
	turnPage = new turnPageClass();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) 
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		turnPage.pageDisplayGrid = AccTriggerGrid;
		turnPage.strQuerySql     = strSQL;
		turnPage.pageIndex = 0;
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		getInitResult();
	}
    return ;
}
function getInitResult()
{
	
				var sqlid64="ContQuerySQL64";
				var mySql64=new SqlClass();
				mySql64.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
				mySql64.setSqlId(sqlid64);//指定使用的Sql的id
				mySql64.addSubPara(fm.GrpContNoIn.value);//指定传入的参数
			     var sql=mySql64.getString();	
	
   // var sql="select * from LCGrpAccTrigger where GrpContNo='" + fm.GrpContNoIn.value + "'";
    queryResult=easyQueryVer3(sql,1,1,1);
    if(queryResult)
    {
     //initDataSet=decodeEasyQueryResult(queryResult);
    }
}

function check()
{
    if(fm.RiskCode.value==''||fm.RiskCode.value==null)
    {
        alert("请选择一个险种信息");
        return false;
    }
    if(fm.AccType.value==''||fm.AccType.value==null)
    {
        alert("请先选择账户范围");
        return false;
    }
}
function check2()
{
    if(document.all('PayPlanCode').value==''||document.all('PayPlanCode').value==null)
    {
         alert("请选择险种账户编码!");
         return false;
    }
    if(document.all('InsuAccNo').value==''&&document.all('InsuAccNo').value==null)
    {
        alert("请选择险种账户编码!");
        return false;
    }
}

//查询险种帐户编码
function getRiskAcc()
{
   var strRiskCode=document.all('RiskCode').value;
}	
	

	