//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var tResourceName="certify.CardDestroyInfoInputSql";
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
  /*strSQL="select a.certifycode, "
  		+" (select certifyname from lmcertifydes where '1235121562000' = '1235121562000' and certifycode = a.certifycode),"
		+" a.startno, a.endno, a.sumcount, a.modifydate, a.SendOutCom, "
		+" (case substr(a.SendOutCom, 0, 1)"
		+" when 'A' then (select name from ldcom where comcode = substr(a.SendOutCom, 2, 10))"
		+" when 'B' then (case substr(a.SendOutCom, length(a.SendOutCom) - 1, length(a.SendOutCom)) when '01' then '个人业务部' when '02' then '银行保险部' when '03' then '多元销售部' when '04' then '联办项目组' when '05' then '保费部' when '06' then '培训部' when '07' then '市场部' when '08' then '客户服务部(分公司为运营部)' when '09' then '财务部'  when '10' then '业务管理部' end) "
		+" when 'D' then (select name from laagent where agentcode = substr(a.SendOutCom, 2, 10)) else '未知' end)"
		+" from lzcardB a where 1 = 1 and a.stateflag = '11'"
		+" and ((a.sendoutcom like 'A'||'"+ManageCom+"%' or a.sendoutcom like 'B'||'"+ManageCom+"%')"
		+" or ( exists (select 1 from laagent where agentcode = substr(a.sendoutcom, 2) and (managecom like '"+ManageCom+"%'))))"
		+ getWherePart('a.certifycode', 'CertifyCode')
		+ getWherePart('a.sendoutcom', 'sendoutcom')
		+ getWherePart('a.receivecom', 'receivecom')
 		+ getWherePart('a.ModifyDate', 'MakeDateB','>=')
 		+ getWherePart('a.ModifyDate', 'MakeDateE','<=')
	 	+" and exists (select *	from lmcertifydes b where b.certifycode = a.certifycode"
		+ getWherePart('b.certifyclass', 'CertifyClass')
		+ getWherePart('b.certifyclass2', 'CertifyClass2')
		+") order by a.makedate, a.certifycode, a.startno" ;*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[ManageCom,ManageCom,ManageCom,document.all('CertifyCode').value,document.all('sendoutcom').value
                                   ,document.all('receivecom').value,document.all('MakeDateB').value,document.all('MakeDateE').value
                                   ,document.all('CertifyClass').value,document.all('CertifyClass2').value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardSendOutInfoGrid);
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
