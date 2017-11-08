//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var tResourceName="certify.CardSendOutInfoInputSql";
//单证状态查询
function certifyQuery()
{
    if(verifyInput() == false)
    {
	  return;
    }
  CardSendOutInfoGrid.clearData();//清除表格中所有数据

  var ManageCom = fm.ManageCom.value;
  var strSQL="";//查寻SQL语句字串
  /*strSQL="select a.certifycode,(select certifyname from lmcertifydes where certifycode = a.certifycode),"
		+" a.startno, a.endno, a.sumcount, a.makedate, a.sendoutcom, a.receivecom,"
		+" (case substr(a.receivecom, 0, 1)"
		+" when 'A' then (select name from ldcom where comcode = substr(a.receivecom, 2, 10))"
		+" when 'B' then (case substr(a.receivecom,length(a.receivecom)-1,length(a.receivecom)) when '01' then '个人业务部' when '02' then '银行保险部' when '03' then '多元销售部' when '04' then '联办项目组' when '05' then '保费部' when '06' then '培训部' when '07' then '市场部' when '08' then '客户服务部(分公司为运营部)' when '09' then '财务部'  when '10' then '业务管理部' end )"
		+" when 'D' then (select name from laagent where agentcode = substr(a.receivecom, 2, 10))"
		+" else	'未知' end),agentcom,(select name from lacom where agentcom=a.agentcom)"
		+" from lzcardtrack a where 1 = 1 and (a.stateflag = '2' or a.stateflag = '3')"
		+" and (a.sendoutcom like 'A'||'" + ManageCom + "%' or a.sendoutcom like 'B'||'" + ManageCom + "%')"
		+ getWherePart('a.certifycode', 'CertifyCode')
		+ getWherePart('a.sendoutcom', 'sendoutcom')
		+ getWherePart('a.receivecom', 'receivecom')
 		+ getWherePart('a.makedate', 'MakeDateB','>=')
 		+ getWherePart('a.makedate', 'MakeDateE','<=')
	 	+" and exists (select *	from lmcertifydes b where b.certifycode = a.certifycode"
		+ getWherePart('b.certifyclass', 'CertifyClass')
		+ getWherePart('b.certifyclass2', 'CertifyClass2')
		+") order by a.makedate, a.sendoutcom, a.receivecom, a.certifycode, a.startno" ;*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[ManageCom,ManageCom,document.all('CertifyCode').value,document.all('sendoutcom').value
                                   ,document.all('receivecom').value,document.all('MakeDateB').value,document.all('MakeDateE').value
                                   ,document.all('CertifyClass').value,document.all('CertifyClass2').value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardSendOutInfoGrid, 1);
   	//alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	if(CardSendOutInfoGrid.mulLineCount==0)
   	{
   		alert("没有查询到任何单证信息！");
   		return false;
   	}
}

//[打印]按钮函数
function certifyPrint()
{
	//alert("查询到的记录行数："+CardSendOutInfoGrid.mulLineCount);
   	if(CardSendOutInfoGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
   	
   	//alert(turnPage.queryAllRecordCount);
   	
   	if(turnPage.queryAllRecordCount>100000)
   	{
   		alert("一次打印的数据超过100000条,请精确查询条件！");
   		return false;
   	}
   	
   	
	easyQueryPrint(2,'CardSendOutInfoGrid','turnPage');	
}

//获取非个人代理人所在机构代码、名称
function getAgentCom()
{
	var receivecom = document.all('ReceiveCom').value;
	if(receivecom!=null && receivecom != "" && receivecom.substring(0,1) == 'D')	 
	{
		/*var strSQL="select a.agentcom,(select name from lacom where agentcom=a.agentcom) "
		+" from lacomtoagent a where a.relatype='1' "//银代 3
		+" and a.agentcode='" + receivecom.substring(1, 12) + "' "
		+" union "
		+" select b.agentcom,b.name "
		+" from lacom b where b.comtoagentflag='1' "//联办 6
		+" and b.agentcode='" + receivecom.substring(1, 12) + "' "
		+" union "
		+" select c.agentcom,(select name from lacom where agentcom=c.agentcom)  "
		+" from lacomtoagentnew c where c.relatype='2' "//中介 7
		+" and c.agentcode='" + receivecom.substring(1, 12) + "'";*/
		var strSQL = wrapSql(tResourceName,"querysqldes2",[receivecom.substring(1, 12),receivecom.substring(1, 12),receivecom.substring(1, 12)]);
		
		if(easyQueryVer3(strSQL)!=false){
			fm.agentCom.value="";
			fm.agentComName.value="";
			fm.agentCom.CodeData = easyQueryVer3(strSQL); 	
		}else{
			fm.agentCom.value="";
			fm.agentComName.value="";
			fm.agentCom.CodeData = "";
		}
	}
}

