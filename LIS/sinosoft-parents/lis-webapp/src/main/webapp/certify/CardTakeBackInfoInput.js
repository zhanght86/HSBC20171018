//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var tResourceName="certify.CardTakeBackInfoInputSql";
//单证状态查询
function certifyQuery()
{
  if(verifyInput() == false)
  {
	return;
  }
  CardSendOutInfoGrid.clearData();//清除表格中所有数据

  var ManageCom = fm.ManageCom.value;
  var strSQL= "";//查寻SQL语句字串
  /*strSQL="select a.certifycode, "
  		+" (select certifyname from lmcertifydes where '1235121562000' = '1235121562000' and certifycode = a.certifycode),"
		+" a.startno, a.endno, a.SumCount, a.makedate, "
		+" (case substr(a.sendoutcom, 0, 1) "
		+" when 'D' then "
		+" (select max(makedate) from lzcardtrack where certifycode = a.certifycode and startno <= a.startno and endno>=a.startno and receivecom = a.sendoutcom)+ "
        +" (select validate1 from lmcertifydes where certifycode = a.certifycode and exists (select 1 from laagent a where a.branchtype not in (2, 3, 6, 7) and agentcode = substr(a.sendoutcom, 2, 20)) "
		+" union select validate2 from lmcertifydes where certifycode = a.certifycode and exists (select 1 from laagent a where a.branchtype in (2, 3, 6, 7) and agentcode = substr(a.sendoutcom, 2, 20))) "
		+" end)-1 maxdate, "//有效使用期=发放轨迹最大日期+单证定义有效使用期限
		+" case a.stateflag when '4' then '自动缴销' when '5' then '手工缴销' when '6' then '使用作废' when '7' then '停用作废' when '10' then '遗失' else '非法状态' end," 
		+" a.receivecom,"
		+" (case substr(a.receivecom, 0, 1)"
		+" when 'A' then (select name from ldcom where comcode = substr(a.receivecom, 2, 10))"
		+" when 'B' then (case substr(a.receivecom, length(a.receivecom) - 1, length(a.receivecom)) when '01' then '个人业务部' when '02' then '银行保险部' when '03' then '多元销售部' when '04' then '联办项目组' when '05' then '保费部' when '06' then '培训部' when '07' then '市场部' when '08' then '客户服务部(分公司为运营部)' when '09' then '财务部'  when '10' then '业务管理部' end) "
		+" when 'D' then (select name from laagent where agentcode = substr(a.receivecom, 2, 10)) else '未知' end)"
		+" from lzcardB a where 1 = 1 and a.stateflag in ('4','5','6','7','10')"
		//+" and (a.receivecom like 'A'||'"+ManageCom+"%' or a.receivecom like 'B'||'"+ManageCom+"%')"		
		+" and ((a.sendoutcom like 'A'||'"+ManageCom+"%' or a.sendoutcom like 'B'||'"+ManageCom+"%')"
		+" or ( exists (select 1 from laagent where agentcode = substr(a.sendoutcom, 2) and (managecom like '"+ManageCom+"%'))))"
		+ getWherePart('a.certifycode', 'CertifyCode')
		+ getWherePart('a.sendoutcom', 'sendoutcom')
		+ getWherePart('a.receivecom', 'receivecom')
 		+ getWherePart('a.makedate', 'MakeDateB','>=')
 		+ getWherePart('a.makedate', 'MakeDateE','<=')
	 	+" and exists (select 1	from lmcertifydes b where b.certifyclass in ('D','B') and b.certifycode = a.certifycode"
		+ getWherePart('b.certifyclass', 'CertifyClass')
		+ getWherePart('b.certifyclass2', 'CertifyClass2')
		+")" ;*/
  var whereflag1='';
  var whereflag2='';
  if(fm.INinvalidate.value != ''){
    if(fm.INinvalidate.value == 'Y'){
        /*strSQL+="and (a.sendoutcom like 'A%' "
    		+" or a.sendoutcom like 'B%' "
    		+" or exists (select 1 from lmcertifydes where certifycode = a.certifycode and havevalidate='N') "
			+" or a.makedate <= (select max(makedate) from lzcardtrack where certifycode = a.certifycode and startno <= a.startno and endno>=a.startno and receivecom = a.sendoutcom)+ "
			+" (select validate1 from lmcertifydes where certifycode = a.certifycode and exists	(select 1 from laagent where branchtype not in (2, 3, 6, 7) and agentcode=substr(a.sendoutcom, 2))"
			+" union select validate2 from lmcertifydes where certifycode = a.certifycode	and exists (select 1 from laagent where branchtype in (2, 3, 6, 7) and agentcode=substr(a.sendoutcom, 2))))";
      //strSQL+=" and (a.makedate <= a.invalidate or a.invalidate is null)";*/
        whereflag1='1';
    }else if(fm.INinvalidate.value == 'N'){
         /*strSQL+="and (a.sendoutcom like 'D%' "
    		+" and exists (select 1 from lmcertifydes where certifycode = a.certifycode and havevalidate='Y') "
			+" and a.makedate > (select max(makedate) from lzcardtrack where certifycode = a.certifycode and startno <= a.startno and endno>=a.startno and receivecom = a.sendoutcom)+ "
			+" (select validate1 from lmcertifydes where certifycode = a.certifycode and exists	(select 1 from laagent where branchtype not in (2, 3, 6, 7) and agentcode=substr(a.sendoutcom, 2))"
			+" union select validate2 from lmcertifydes where certifycode = a.certifycode	and exists (select 1 from laagent where branchtype in (2, 3, 6, 7) and agentcode=substr(a.sendoutcom, 2))))";
      //strSQL+=" and a.makedate > a.invalidate ";*/
         whereflag2='1';
    }  
  }	
	//strSQL+=" order by a.makedate, a.certifycode, a.startno";
	strSQL = wrapSql(tResourceName,"querysqldes1",[ManageCom,ManageCom,ManageCom,document.all('CertifyCode').value,document.all('sendoutcom').value
	                                   ,document.all('receivecom').value,document.all('MakeDateB').value,document.all('MakeDateE').value
	                                   ,document.all('CertifyClass').value,document.all('CertifyClass2').value,whereflag1,whereflag2]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardSendOutInfoGrid, 1);
   	//alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	
   	//prompt("",strSQL);
   	
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
