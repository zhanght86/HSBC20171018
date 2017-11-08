/**
 * <p>程序名称: CertifyCommon.js</p>
 * <p>程序功能: 单证管理公用函数变量定义 </p>
 * <p>注释更新人: </p>
 * <p>最近更新日期:</p>
*/

/*************************************************************************************
本函数专用于在单证管理查询获取“发放者”、“接收者”对应的中文名称
<根据代码表单的值为代码值，查询出名称赋值给名称表单>，以便于操作核对
所需参数：代码表单名[]，名称表单名[] 
     注意：表单名格式【所在Form名＋“.”＋表单名,例：fm.ReceiveCom。切忌在表单名称后加“Value”】  
例：onblur="getSendReceiveName(fm.ReceiveCom,fm.ReceiveComName)"
   则 根据代码表单的值fm.ReceiveCom.value为代码值，查询出名称赋值给名称表单 fm.ReceiveComName.value
*************************************************************************************/
function getSendReceiveName(CodeFiled,NameFiled)
{
	var strCode=trim(CodeFiled.value);//传入代码
	var strCodeName="";//代码名称
	var strCodeLenth=0;//传入代码的长度
	var strFirstCode="";//传入代码的第一位值,用于判断是机构还是业务员<A--机构代码；D--业务员代码>
	var strLeaveCode="";//传入代码的截掉第一位后剩下的代码值，用于查询数据库
	var querySQL="";//查询的SQL语句
	var tResourceName="certify.CertifyTrackInfoInputSql";
	if(strCode==null ||strCode=="")
	{
		strCodeName="";
	}
	else
	{
		strCodeLenth=strCode.length;//获取传入代码的长度
		strFirstCode=strCode.substring(0,1);//获取传入代码的第一位值<A--机构代码；D--业务员代码>
		strLeaveCode=trim(strCode.substring(1,strCodeLenth));//获取传入代码的截掉第一位后剩下的代码值，用于查询数据库
		//以下准备查询数据库所需的SQL字符串
		if(strFirstCode=="A" && strLeaveCode!="")
		{
			//querySQL="select comcode,name from ldcom where comcode='"+strLeaveCode+"' ";
			querySQL = wrapSql(tResourceName,"querysqldes1",[strLeaveCode]);
		}
		else if(strFirstCode=="D" && strLeaveCode!="")
		{
			//querySQL="select agentcode,name from laagent where agentcode='"+strLeaveCode+"'";
			querySQL = wrapSql(tResourceName,"querysqldes2",[strLeaveCode]);
		}
		else if(strFirstCode=="E" && strLeaveCode!="")
		{
			//querySQL="select agentcom,name from lacom where agentcom='"+strLeaveCode+"'";
			querySQL = wrapSql(tResourceName,"querysqldes3",[strLeaveCode]);
		}
		else 
		{
			querySQL="";
		}
		//以下准备查询数据库
		if(querySQL!="")
		{	
			var QueryArr=easyExecSql(querySQL);
			if(QueryArr!=null)
			{
				strCodeName=QueryArr[0][1];
			}
			else				 
			{ 
			 	strCodeName="";
			} 
		}
		else
		{
			strCodeName="";
		}
	}
	NameFiled.value=strCodeName;
} 

/***************************************************
以下函数方法专用于在单证管理查询“银行代理机构”时使用
***************************************************/
var PuObjectName="";
//判断是否需要显示【查询银行代理机构】按钮
function chkQueryAgCom(objCheck)
{
	try
	{
		if(objCheck.checked == true) 
		{
			try{fm.btnSeQueryAgentCom.style.display = "";}catch(ex){}
			try{fm.btnReQueryAgentCom.style.display = "";} catch(e) {}
		}
		else 
		{
			try{fm.btnSeQueryAgentCom.style.display = "none";}catch(ex){}
			try{fm.btnReQueryAgentCom.style.display = "none";} catch(e) {}
		}
	}
	catch(ex)
	{
	}
}
//调用查询银行代理机构代码
function queryAgentCom(sObject)
{
	try{PuObjectName=sObject.name;}catch(ex){}
	showInfo=window.open('../treeCom/jsp/BankSelectCategory.jsp','newwindow','height=400, width=800, top='+(screen.availHeight-400)/2+',left='+(screen.availWidth-800)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
}

//选择银代机构后返回机构名称
function afterSelectBank(cAgentCom)
{
	try{cAgentCom="E"+cAgentCom;}catch(ex){}
	try{eval("fm.all('"+PuObjectName+"').value = cAgentCom;");}catch(ex){}
	try{eval("fm.all('"+PuObjectName+"').focus();");}catch(ex){}
}
