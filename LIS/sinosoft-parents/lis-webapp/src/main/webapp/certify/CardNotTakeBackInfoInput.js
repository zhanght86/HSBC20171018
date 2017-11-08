//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var tResourceName="certify.CardNotTakeBackInfoInputSql";
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
		+" a.startno, a.endno, a.SumCount, a.modifydate, "
		+" (case substr(a.receivecom, 0, 1) "
		+" when 'D' then "
		+" a.makedate +(select validate1 from lmcertifydes where certifycode = a.certifycode and exists (select 1 from laagent a where a.branchtype not in (2, 3, 6, 7) and agentcode = substr(a.receivecom, 2, 20)) "
		+" union select validate2 from lmcertifydes where certifycode = a.certifycode and exists (select 1 from laagent a where a.branchtype in (2, 3, 6, 7) and agentcode = substr(a.receivecom, 2, 20))) "
		+" end)-1 maxdate, " //有效使用期=发放日期+单证定义有效使用期限				
		+" case a.stateflag when '3' then '已发放未核销 ' when '8' then '逾期' when '9' then '挂失' else '非法状态' end," 
		+" a.receivecom,"
		+" (case substr(a.receivecom, 0, 1)"
		+" when 'A' then (select name from ldcom where comcode = substr(a.receivecom, 2, 10))"
		+" when 'B' then (case substr(a.receivecom, length(a.receivecom) - 1, length(a.receivecom)) when '01' then '个人业务部' when '02' then '银行保险部' when '03' then '多元销售部' when '04' then '联办项目组' when '05' then '保费部' when '06' then '培训部' when '07' then '市场部' when '08' then '客户服务部(分公司为运营部)' when '09' then '财务部'  when '10' then '业务管理部' end) "
		+" when 'D' then (select name from laagent where agentcode = substr(a.receivecom, 2, 10)) else '未知' end),a.agentcom,(select name from lacom where agentcom=a.agentcom)"
		+" from (select * from lzcard union all  select * from lzcardb) a "
		+" where 1 = 1 and a.stateflag in ('3','8','9')"
		+" and ((a.receivecom like 'A'||'"+ManageCom+"%' or a.receivecom like 'B'||'"+ManageCom+"%')"
		+" or ( exists (select 1 from laagent where agentcode = substr(a.receivecom, 2) and (managecom like '"+ManageCom+"%'))))"
		+ getWherePart('a.certifycode', 'CertifyCode')
		+ getWherePart('a.StateFlag', 'StateFlagN')
		+ getWherePart('a.sendoutcom', 'sendoutcom')
		+ getWherePart('a.receivecom', 'receivecom')
 		+ getWherePart('a.makedate', 'MakeDateB','>=')
 		+ getWherePart('a.makedate', 'MakeDateE','<=')
	 	+" and exists (select *	from lmcertifydes b where b.certifyclass in ('D','B') and b.certifycode = a.certifycode"
		+ getWherePart('b.certifyclass', 'CertifyClass')
		+ getWherePart('b.certifyclass2', 'CertifyClass2')
		+") order by a.makedate, a.certifycode, a.startno" ;*/
  strSQL = wrapSql(tResourceName,"querysqldes1",[ManageCom,ManageCom,ManageCom,document.all('CertifyCode').value,document.all('StateFlagN').value
                                   ,document.all('sendoutcom').value,document.all('receivecom').value,document.all('MakeDateB').value
                                   ,document.all('MakeDateE').value,document.all('CertifyClass').value,document.all('CertifyClass2').value]);
	
   	turnPage.pageLineNum = 10;
   	 /**turnPage.queryModal(strSQL, CardSendOutInfoGrid, 1);
   	//prompt("strSQL：",strSQL);
  if(CardSendOutInfoGrid.mulLineCount==0)
   	{
   		alert("没有查询到任何单证信息！");
   		return false;
   	}**/
   	
   	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有查询到任何单证信息！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = CardSendOutInfoGrid;
          
  //保存SQL语句
  turnPage.strQuerySql = strSQL;
  
  //设置查询起始位置
  turnPage.pageIndex = 0;
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  MulLinereflash();
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

