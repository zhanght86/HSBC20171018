//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var tResourceName="certify.CertifyInfoInputSql";
//单证状态查询
function certifyQuery()
{
	//单证编码必录
	//if(fm.CertifyCode.value==null||fm.CertifyCode.value=="")
	//{
   		//alert("请选择要统计的单证编码！");
   		//return false ;
	//}
	
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if(verifyInput() == false)
	{
   		document.all('divSum').style.display="none";
   		showInfo.close();
   		return false ;
	}
	 
  initCardInfoGrid();
  fm.SumCount.value="";	  
	
	/*var strSQL1="select t.certifycode, (select lmcertifydes.certifyname from lmcertifydes where lmcertifydes.certifycode=t.certifycode),"
		+" t.sendoutcom, t.receivecom, t.startno, t.endno, t.sumcount,"
		+" (case t.stateflag when '1' then '待入库' when '2' then '已入库' when '3' then '已发放未核销' when '4' then '自动缴销' when '5' then '手工缴销' when '6' then '使用作废' when '7' then '停用作废' when '8' then '逾期' when '9' then '挂失' when '10' then '遗失' when '11' then '销毁' else '' end),"
		+" t.handler, t.operator,  t.modifydate , t.modifytime "
		+" from (select * from lzcard union select * from lzcardb) t where 1=1 "
		+" and (t.ReceiveCom like 'A" + fm.ManageCom.value + "%' or t.ReceiveCom like 'B" + fm.ManageCom.value + "%'"
		+"      or t.ReceiveCom in (select 'D'||agentcode from laagent where managecom like '" + fm.ManageCom.value + "%'))"
		+ getWherePart('t.CertifyCode','CertifyCode')
		+ getWherePart('t.StartNo','EndNo','<=')
		+ getWherePart('t.EndNo','StartNo','>=')		
		+ getWherePart('t.SendOutCom','SendOutCom')
		+ getWherePart('t.ReceiveCom','ReceiveCom')
		+ getWherePart('t.MakeDate','MakeDateB','>=')
		+ getWherePart('t.MakeDate','MakeDateE','<=');*/  
		
		var StateFlag = fm.StateFlag.value;
		var StateFlag1 ='';
		var StateFlag2 ='';
		var StateFlag3 ='';
		var StateFlag4 ='';
		if(StateFlag!=null && StateFlag!=''){		  
		  if(StateFlag>=1 && StateFlag<=11)
		  {
			  StateFlag1 = StateFlag;
		    //strSQL1+=" and t.StateFlag='" + StateFlag + "'";
		  }else if(StateFlag == '12')
		  {
			  StateFlag2 = StateFlag;
		    //strSQL1+=" and t.StateFlag in ('4','5')";
		  }
		  else if(StateFlag == '13')
		  {
			  StateFlag3 = StateFlag;
		    //strSQL1+=" and t.StateFlag in ('6','7')";
		  }
		  else if(StateFlag == '14')
		  {
			  StateFlag4 = StateFlag;
		    //strSQL1+=" and t.StateFlag in ('4','5','6','7','10')";
		  }	
		}		

	//加上印刷后待入库的单证
		var StateFlag5 ='2';
	if(StateFlag==null || StateFlag=='' || StateFlag==1){
		/*strSQL1+=" union all select a.certifycode, (select lmcertifydes.certifyname from lmcertifydes where lmcertifydes.certifycode = a.certifycode),"
				+" '00' sendoutcom, 'A' || a.managecom, a.startno, a.endno, a.sumcount,"
			 	+" '待入库', a.printman, a.printman, a.printdate, a.modifytime "
				+" from lzcardprint a where a.state = '1'"
				+" and a.managecom like '" + fm.ManageCom.value + "%'"
				+ getWherePart('a.CertifyCode','CertifyCode')
				+ getWherePart('a.StartNo','EndNo','<=')
				+ getWherePart('a.EndNo','StartNo','>=')
				+ getWherePart('a.managecom','ReceiveCom')
				+ getWherePart('a.printdate','MakeDateB','>=')
				+ getWherePart('a.printdate','MakeDateE','<=');*/
				StateFlag5 = '1';
	}	
		  
   	//如果录入单证编码、单证状态，自动显示总数量
   	if(fm.CertifyCode.value!=null && fm.CertifyCode.value!="" && fm.StateFlag.value!=null && fm.StateFlag.value!=""){
   		document.all('divSum').style.display="";
   		//var	strSQL3="select sum(sumcount) from (" + strSQL1 +")";
   		var strSQL3 = wrapSql(tResourceName,"querysqldes1",[fm.ManageCom.value,document.all('CertifyCode').value,document.all('EndNo').value
		                                      ,document.all('StartNo').value,document.all('SendOutCom').value,document.all('ReceiveCom').value,document.all('MakeDateB').value
		                                      ,document.all('MakeDateE').value,StateFlag1,StateFlag2,StateFlag3,StateFlag4
		                                      ,StateFlag5,fm.ManageCom.value,document.all('CertifyCode').value,document.all('EndNo').value
		                                      ,document.all('StartNo').value,document.all('ReceiveCom').value,document.all('MakeDateB').value
		                                      ,document.all('MakeDateE').value]);
   		fm.SumCount.value = easyExecSql(strSQL3);   		
   	}else{
   		document.all('divSum').style.display="none";
   		fm.SumCount.value="";
   	}		  
		  
	//var	strSQL2="select * from (" + strSQL1 +") order by certifycode, sendoutcom, startno " ;
   	var strSQL2 = wrapSql(tResourceName,"querysqldes2",[fm.ManageCom.value,document.all('CertifyCode').value,document.all('EndNo').value
	                                      ,document.all('StartNo').value,document.all('SendOutCom').value,document.all('ReceiveCom').value,document.all('MakeDateB').value
	                                      ,document.all('MakeDateE').value,StateFlag1,StateFlag2,StateFlag3,StateFlag4
	                                      ,StateFlag5,fm.ManageCom.value,document.all('CertifyCode').value,document.all('EndNo').value
	                                      ,document.all('StartNo').value,document.all('ReceiveCom').value,document.all('MakeDateB').value
	                                      ,document.all('MakeDateE').value]);
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL2, CardInfoGrid, 1);
   	if(CardInfoGrid.mulLineCount==0)
   	{
   		document.all('divSum').style.display="none";
   		showInfo.close();
   		alert("没有查询到单证信息！");
   		return false ;
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
