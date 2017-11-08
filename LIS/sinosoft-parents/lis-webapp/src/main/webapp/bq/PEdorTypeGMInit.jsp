 <%
//程序名称：
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
                      

<script language="JavaScript">  
var str_sql = "",sql_id = "", my_sql = "";
var strSQL="";
function initInpBox()
{ 
   Edorquery(); 
  try
  {  
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;   
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value; 
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;  
    document.all('PolNo').value = top.opener.document.all('PolNo').value;   
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    
	if(document.all('PolNo').value!=null || document.all('PolNo').value !='')
	{
// 	    strSQL = "SELECT RiskCode FROM LCPol WHERE PolNo = '" + document.all('PolNo').value + "'";
		var strSQL = "";
	    sql_id = "PEdorTypeGMInputSql2";
	    my_sql = new SqlClass();
	    my_sql.setResourceName("bq.PEdorTypeGMInputSql"); //指定使用的properties文件名
	    my_sql.setSqlId(sql_id);//指定使用的Sql的id
	    my_sql.addSubPara(document.all('PolNo').value);//指定传入的参数
	    strSQL = my_sql.getString();
	}
	else
	{
		alert('没有险种信息！');
	}
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);	
    if(!turnPage.strQueryResult)
    {
		alert('没有险种信息！');
	}
	else
	{
        arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
        document.all('RiskCode').value = arrSelected[0][0];
    }
    
  }
  catch(ex)
  {
    alert("在PEdorTypeGMInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在PEdorTypeGMInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    QueryCustomerInfo();
    QueryEdorInfo();
    initQueryBfChg();
    initQueryAftChg();
//    edorTypeGMQuery();  //领取标准重算，不必要了，add  by zhangrong
  }
  catch(re)
  {
    alert("PEdorTypeGMInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化

// 查询按钮
function easyQueryClick()
{
}

function initQuery()
{	
}
 function QueryCustomerInfo()
{	
	var tContNo=top.opener.document.all('ContNo').value;
	
// 	strSQL = "select 1 from lpgrpedoritem where edoracceptno = '" + document.all('EdorAcceptNo').value + "'";
    sql_id = "PEdorTypeGMInputSql3";
    my_sql = new SqlClass();
    my_sql.setResourceName("bq.PEdorTypeGMInputSql"); //指定使用的properties文件名
    my_sql.setSqlId(sql_id);//指定使用的Sql的id
    my_sql.addSubPara(document.all('EdorAcceptNo').value);//指定传入的参数
    strSQL = my_sql.getString();
	var Check = easyExecSql(strSQL);
	if(Check != null)
	{
		//团险受益人变更
		divPerson.style.display="none";
		document.all('PGrpFlag').value = "Group";
	}
	else
	{
		//个险受益人变更
		document.all('PGrpFlag').value = "Person";
	
	    if(tContNo!=null || tContNo !='')
	    {
// 	        strSQL = "SELECT APPNTNAME,APPNTIDTYPE,APPNTIDNO,INSUREDNAME,INSUREDIDTYPE,INSUREDIDNO FROM LCCONT WHERE 1=1 AND "
// 	    						+"CONTNO='"+tContNo+"'";
	        sql_id = "PEdorTypeGMInputSql4";
	        my_sql = new SqlClass();
	        my_sql.setResourceName("bq.PEdorTypeGMInputSql"); //指定使用的properties文件名
	        my_sql.setSqlId(sql_id);//指定使用的Sql的id
	        my_sql.addSubPara(tContNo);//指定传入的参数
	        strSQL = my_sql.getString();
	    }
	    else
	    {
	    	alert('没有客户信息！');
	    }
	    var arrSelected = new Array();	
	    turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);	
        if(!turnPage.strQueryResult)
        {
	    	alert("查询失败");
	    }
	    else
	    {
            arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
            try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //投保人姓名
            try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //投保人证件类型
            try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //投保人证件号码 
            try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //被保人名称
            try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //被保人证件类型
            try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //被保人证件号码 
        }
    }
//     strSQL = "select RiskSortValue from LMRiskSort where RiskSortType = '6' and RiskCode = (select RiskCode from LCPol where PolNo = '" +  document.all('PolNo').value + "')";
    sql_id = "PEdorTypeGMInputSql5";
    my_sql = new SqlClass();
    my_sql.setResourceName("bq.PEdorTypeGMInputSql"); //指定使用的properties文件名
    my_sql.setSqlId(sql_id);//指定使用的Sql的id
    my_sql.addSubPara(document.all('PolNo').value);//指定传入的参数
    strSQL = my_sql.getString();
    turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);	
    if(turnPage.strQueryResult)
    {
       
        arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
        if (arrSelected[0][0] == '1')
        {
            OldGetStand.style.display = '';
            NewGetStand.style.display = '';
        }
	}
	else
	{
	    
	}
}

// 领取方式变更前的信息
function initQueryBfChg()
{
	var tPolNo=top.opener.document.all('PolNo').value;
	var strSQL="";

	if(tPolNo!=null || tPolNo !="")
	{
// 	    strSQL ="SELECT b.GetIntv, b.GetDutyCode,b.DutyCode,b.getmode,b.StandMoney"
// 				 + " FROM LCGet b,LMDutyGet c"
// 				 + " WHERE b.PolNo='" + tPolNo + "' and b.ContNo = '"+top.opener.document.all('ContNo').value+"'"
// 				 + " and b.GetDutyCode=c.GetDutyCode";
// 			  strSQL += " and c.GetType1='1' and char_length(trim(b.dutycode))=6";	
			    sql_id = "PEdorTypeGMInputSql6";
			    my_sql = new SqlClass();
			    my_sql.setResourceName("bq.PEdorTypeGMInputSql"); //指定使用的properties文件名
			    my_sql.setSqlId(sql_id);//指定使用的Sql的id
			    my_sql.addSubPara(tPolNo);//指定传入的参数
			    my_sql.addSubPara(top.opener.document.all('ContNo').value);//指定传入的参数
			    strSQL = my_sql.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
	}
	
	var arrSelected = new Array();		
    if(!turnPage.strQueryResult)
    {
		alert("未查询到原领取方式！");
	    return false;
	}
	else
	{
    arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('GetDutyCode').value = arrSelected[0][1];} catch(ex) { };
    try {document.all('DutyCode').value = arrSelected[0][2];} catch(ex) { };
    try {document.all('GetDutyKind_S').value = arrSelected[0][3];} catch(ex) { };
    try {document.all('ChgBeforeStand').value = arrSelected[0][4];} catch(ex) { };
    
	showOneCodeNameEx('livegetmode_GM','GetDutyKind_S','GetDutyKind_SName', "1", "1 AND b.polno=#" + tPolNo +"#");
    }
}

function initQueryAftChg()
{
	var tPolNo=top.opener.document.all('PolNo').value;
    var strSQL="";
	if(tPolNo!=null || tPolNo !="")
	{
// 	    strSQL ="SELECT b.GetIntv, b.GetDutyCode,b.DutyCode,b.getmode,b.StandMoney"
// 				 + " FROM LPGet b,LMDutyGet c"
// 				 + " WHERE b.PolNo='" + tPolNo + "' and b.ContNo = '"+top.opener.document.all('ContNo').value+"'"
// 				 + " and b.GetDutyCode=c.GetDutyCode";
// 			strSQL += " and c.GetType1='1'";	
			
			sql_id = "PEdorTypeGMInputSql7";
		    my_sql = new SqlClass();
		    my_sql.setResourceName("bq.PEdorTypeGMInputSql"); //指定使用的properties文件名
		    my_sql.setSqlId(sql_id);//指定使用的Sql的id
		    my_sql.addSubPara(tPolNo);//指定传入的参数
		    my_sql.addSubPara(top.opener.document.all('ContNo').value);//指定传入的参数
		    strSQL = my_sql.getString();
	    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

        if(!turnPage.strQueryResult)
        {
            try {document.all('GetDutyKind').value = document.all('GetDutyKind_S').value;} catch(ex) { };
            try {document.all('ChgAfterStand').value = document.all('ChgBeforeStand').value;} catch(ex) { };
        	showOneCodeNameEx('livegetmode_GM','GetDutyKind','GetDutyKindName', "1", "1 AND b.polno=#" + tPolNo +"#");
        }
	    else
    	{
            arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
            try {document.all('GetDutyCode').value = arrSelected[0][1];} catch(ex) { };
            try {document.all('DutyCode').value = arrSelected[0][2];} catch(ex) { };
            try {document.all('GetDutyKind').value = arrSelected[0][3];} catch(ex) { };
            try {document.all('ChgAfterStand').value = arrSelected[0][4];} catch(ex) { };
        	showOneCodeNameEx('livegetmode_GM','GetDutyKind','GetDutyKindName', "1", "1 AND b.polno=#" + tPolNo +"#");
        }
  	}

}

</script>