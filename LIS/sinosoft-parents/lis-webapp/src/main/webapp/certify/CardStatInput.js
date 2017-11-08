//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();
var tResourceName="certify.CardStatInputSql";
//单证状态查询
function certifyQuery()
{
  if(verifyInput()==false){
    return false;
  }

  initCardPrintInfoGrid();  
  var ManageCom = fm.ManageCom.value;

  /*var strSQL = "select '"+ManageCom+"', "
		+" (select name from ldcom where comcode = '"+ManageCom+"'), "
		+" card.certifycode, "
		+" (select certifyname from lmcertifydes where certifycode = card.certifycode), "
		+" sum(card.FF), sum(card.HT), sum(card.DB), sum(card.TK) "
		+" from (select a.certifycode, "
		+" decode(a.OperateFlag, '0', a.sumcount,0 ) FF, "	//发放
        +" decode(a.OperateFlag, '2', decode(a.reason, '1',a.sumcount,0 ), 0) HT, "  //本级申请回退
		+" decode(a.OperateFlag, '2', decode(a.reason, '2',a.sumcount,0 ), 0) DB, "  //上级调拨回退	
        +" decode(a.OperateFlag, '5', a.sumcount, 0) TK "	//拒绝入库回退		
		+" from (select * from lzcardtrack "
		+" where 1=1 and OperateFlag in ('0','2','5') and StateFlag in ('1','3') "
		+ getWherePart('makedate', 'makedateB','>=')
 		+ getWherePart('makedate', 'makedateE','<=')
 		+ getWherePart('certifycode', 'CertifyCode')
 		+" and exists (select 1 from lmcertifydes where certifycode = lzcardtrack.certifycode "
 		+ getWherePart('certifyclass', 'CertifyClass') + ")"
		+ " and sendoutcom = 'A" + ManageCom +"') a) card "
		+" group by card.certifycode "
		+" order by card.certifycode " ;*/
    var strSQL = wrapSql(tResourceName,"querysqldes1",[ManageCom,ManageCom,document.all('makedateB').value,document.all('makedateE').value
                                       ,document.all('CertifyCode').value,document.all('CertifyClass').value,ManageCom]);
		
  /*var strSQL2 = "select '"+ManageCom+"', "
		+" (select name from ldcom where comcode = '"+ManageCom+"'), "
		+" card.certifycode, "
		+" (select certifyname from lmcertifydes where certifycode = card.certifycode), "
		+" sum(card.ZDJX), sum(card.SGJX), sum(card.SYZF), sum(card.TYZF), sum(card.YS), sum(card.XH) "
		+" from (select a.certifycode, "
		+" decode(a.stateflag, '4', a.sumcount, 0) ZDJX, "
		+" decode(a.stateflag, '5', a.sumcount, 0) SGJX, "
		+" decode(a.stateflag, '6', a.sumcount, 0) SYZF, "
		+" decode(a.stateflag, '7', a.sumcount, 0) TYZF, "
		+" decode(a.stateflag, '10', a.sumcount, 0) YS, "
		+" decode(a.stateflag, '11', a.sumcount, 0) XH "	
		+" from (select * from lzcardB "
		+" where 1=1 "
		+ getWherePart('makedate', 'makedateB','>=')
 		+ getWherePart('makedate', 'makedateE','<=')
 		+ getWherePart('certifycode', 'CertifyCode')
 		+" and exists (select 1 from lmcertifydes where certifycode = lzcardB.certifycode "
 		+ getWherePart('certifyclass', 'CertifyClass') + ")"
		+ " and ((sendoutcom like 'A" + ManageCom + "%' or sendoutcom like 'B" + ManageCom+"%') "
		+"        or exists (select 1 from laagent where agentcode = substr(sendoutcom, 2) and managecom like '"+ManageCom+"%'))) a) card "
		+" group by card.certifycode "
		+" order by card.certifycode " ;*/
    var strSQL2 = wrapSql(tResourceName,"querysqldes2",[ManageCom,ManageCom,document.all('makedateB').value,document.all('makedateE').value
                                       ,document.all('CertifyCode').value,document.all('CertifyClass').value,ManageCom,ManageCom,ManageCom]);
				
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardPrintInfoGrid);
   	
   	turnPage2.pageLineNum = 10;
   	turnPage2.queryModal(strSQL2, CardPrintInfo2Grid);
   	
   	if(CardPrintInfoGrid.mulLineCount==0 && CardPrintInfo2Grid.mulLineCount==0)
   	{
   		alert("没有查询到任何单证信息！");
   		return false;
   	}
}

//[打印]按钮函数
function certifyPrint()
{
   	if(CardPrintInfoGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
	easyQueryPrint(2,'CardPrintInfoGrid','turnPage');	
}

function certifyPrint2()
{
   	if(CardPrintInfo2Grid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
	easyQueryPrint(2,'CardPrintInfo2Grid','turnPage2');	
}
