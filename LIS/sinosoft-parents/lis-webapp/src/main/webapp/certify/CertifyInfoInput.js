//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass(); //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var tResourceName="certify.CertifyInfoInputSql";
//��֤״̬��ѯ
function certifyQuery()
{
	//��֤�����¼
	//if(fm.CertifyCode.value==null||fm.CertifyCode.value=="")
	//{
   		//alert("��ѡ��Ҫͳ�Ƶĵ�֤���룡");
   		//return false ;
	//}
	
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
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
		+" (case t.stateflag when '1' then '�����' when '2' then '�����' when '3' then '�ѷ���δ����' when '4' then '�Զ�����' when '5' then '�ֹ�����' when '6' then 'ʹ������' when '7' then 'ͣ������' when '8' then '����' when '9' then '��ʧ' when '10' then '��ʧ' when '11' then '����' else '' end),"
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

	//����ӡˢ������ĵ�֤
		var StateFlag5 ='2';
	if(StateFlag==null || StateFlag=='' || StateFlag==1){
		/*strSQL1+=" union all select a.certifycode, (select lmcertifydes.certifyname from lmcertifydes where lmcertifydes.certifycode = a.certifycode),"
				+" '00' sendoutcom, 'A' || a.managecom, a.startno, a.endno, a.sumcount,"
			 	+" '�����', a.printman, a.printman, a.printdate, a.modifytime "
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
		  
   	//���¼�뵥֤���롢��֤״̬���Զ���ʾ������
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
   		alert("û�в�ѯ����֤��Ϣ��");
   		return false ;
   	}

   	showInfo.close();
}


//[��ӡ]��ť����
function certifyPrint()
{
	//alert("��ѯ���ļ�¼������"+CardInfoGrid.mulLineCount);
   	if(CardInfoGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}
   	
   	//alert(turnPage.queryAllRecordCount);
   	
   	if(turnPage.queryAllRecordCount>100000)
   	{
   		alert("һ�δ�ӡ�����ݳ���100000��,�뾫ȷ��ѯ������");
   		return false;
   	}
   	
	easyQueryPrint(2,'CardInfoGrid','turnPage');	
}
