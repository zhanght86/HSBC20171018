//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var tResourceName="certify.CertifyTrackInfoInputSql";
//单证轨迹查询
function certifyTrackQuery()
{
    if(verifyInput() == false)
    {
	  return;
    }	
	initCardInfoGrid();
	
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	
	var strSQL="";//查寻SQL语句字串
	/*strSQL="select * from (select t.certifycode,"
	    +" (select lmcertifydes.certifyname from lmcertifydes where lmcertifydes.certifycode=t.certifycode),"
		+" t.sendoutcom, t.receivecom, t.startno, t.endno, t.sumcount,"
		+" (case t.stateflag when '1' then '待入库' when '2' then '已入库' when '3' then '已发放未核销' when '4' then '自动缴销' when '5' then '手工缴销' when '6' then '使用作废' when '7' then '停用作废' when '8' then '逾期' when '9' then '挂失' when '10' then '遗失' when '11' then '销毁' else '' end),"
		+" t.handler, t.operator,  t.modifydate , t.modifytime "
		+" from lzcardtrack t where 1=1 "
		+ getWherePart('t.CertifyCode','CertifyCode')
		+ getWherePart('t.StartNo','EndNo','<=')
		+ getWherePart('t.EndNo','StartNo','>=')			
		+ getWherePart('t.StateFlag','StateFlag')		
		+ getWherePart('t.SendOutCom','SendOutCom')
		+ getWherePart('t.ReceiveCom','ReceiveCom')
		+ getWherePart('t.Operator','Operator')
		+ getWherePart('t.Handler','Handler')	
		+ getWherePart('t.MakeDate','MakeDateB','>=')
		+ getWherePart('t.MakeDate','MakeDateE','<=')
		+" union "
		+" select t.certifycode,"
	    +" (select lmcertifydes.certifyname from lmcertifydes where lmcertifydes.certifycode=t.certifycode),"
		+" t.sendoutcom, t.receivecom, t.startno, t.endno, t.sumcount,"
		+" (case t.stateflag when '1' then '待入库' when '2' then '已入库' when '3' then '已发放未核销' when '4' then '自动缴销' when '5' then '手工缴销' when '6' then '使用作废' when '7' then '停用作废' when '8' then '逾期' when '9' then '挂失' when '10' then '遗失' when '11' then '销毁' else '' end),"
		+" t.handler, t.operator,  t.modifydate , t.modifytime "
		+" from lzcardtrackB t where 1=1 "
		+ getWherePart('t.CertifyCode','CertifyCode')
		+ getWherePart('t.StateFlag','StateFlag')		
		+ getWherePart('t.SendOutCom','SendOutCom')
		+ getWherePart('t.ReceiveCom','ReceiveCom')
		+ getWherePart('t.Operator','Operator')
		+ getWherePart('t.Handler','Handler')
		+ getWherePart('t.StartNo','EndNo','<=')
		+ getWherePart('t.EndNo','StartNo','>=')		
		+ getWherePart('t.MakeDate','MakeDateB','>=')
		+ getWherePart('t.MakeDate','MakeDateE','<=')
		+") order by modifydate, modifytime" ;*/
	strSQL = wrapSql(tResourceName,"querysqldes4",[document.all('CertifyCode').value,document.all('EndNo').value,document.all('StartNo').value,document.all('StateFlag').value
	                                 ,document.all('SendOutCom').value,document.all('ReceiveCom').value,document.all('Operator').value,document.all('Handler').value
	                                 ,document.all('MakeDateB').value,document.all('MakeDateE').value
	                                 ,document.all('CertifyCode').value,document.all('StateFlag').value,document.all('SendOutCom').value,document.all('ReceiveCom').value
	                                 ,document.all('Operator').value,document.all('Handler').value,document.all('EndNo').value,document.all('StartNo').value
	                                 ,document.all('MakeDateB').value,document.all('MakeDateE').value]);
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardInfoGrid, 1);
   	//alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	//prompt("strSQL:",strSQL);
   	if(CardInfoGrid.mulLineCount==0)
   	{
   		showInfo.close();
   		alert("没有查询到任何单证信息！");
   		return false;
   	}
   showInfo.close();
}

//[打印]按钮函数
function certifyPrint()
{
	//alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	if(CardInfoGrid.mulLineCount==0)
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
   	
   	
	easyQueryPrint(2,'CardInfoGrid','turnPage');	
}
