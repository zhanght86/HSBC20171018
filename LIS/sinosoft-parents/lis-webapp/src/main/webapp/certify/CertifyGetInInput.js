//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var turnPage = new turnPageClass();
var tResourceName="certify.CertifyGetInInputSql";
//��ѯ����ⵥ֤
function queryClick()
{
  initCertifyListGrid();
 /* var strSql = "select * from (select a.certifycode, (select certifyname from lmcertifydes where certifycode = a.certifycode),"
			 +" '00' sendoutcom, 'A' || a.managecom receivecom, a.startno, a.endno, a.sumcount,"
			 +" (case a.managecom when '86' then '�ܹ�˾ӡˢ' else '�ֹ�˾��ӡ' end), a.prtno, '' Reason,a.printdate makedate"
			 +" from LZCardPrint a where a.state = '1'"
			 + getWherePart('a.certifycode', 'certifycode')
	 		 + getWherePart('a.managecom', 'managecom')
	 		 +" and exists (select 1 from lmcertifydes where certifycode = a.certifycode "
 			 + getWherePart('certifyclass', 'CertifyClass') 
 			 + getWherePart('CertifyClass2', 'CertifyClass2') + ")"
			 +" union "
			 +" select b.certifycode, (select certifyname from lmcertifydes where certifycode = b.certifycode),"
			 +" b.sendoutcom, b.receivecom, b.startno, b.endno, b.sumcount, "
			 +" (case when b.Reason is null then '�ϼ�����' when b.Reason = '2' then '����' else '�¼��˿�' end), '' prtno, b.Reason,b.makedate"
			 +" from lzcard b where b.stateflag = '1'"
			 + getWherePart('b.certifycode', 'certifycode')
			 +" and exists (select 1 from lmcertifydes where certifycode = b.certifycode "
 			 + getWherePart('certifyclass', 'CertifyClass') 
 			 + getWherePart('CertifyClass2', 'CertifyClass2') + ")";
  if(fm.managecom.value!=""){
  	strSql+=" and b.receivecom='A"+fm.managecom.value+"'";
  }
  strSql+=" ) order by makedate,certifycode,sendoutcom,receivecom,startno";*/
    var strSql = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value,document.all('managecom').value,document.all('CertifyClass').value
                                       ,document.all('CertifyClass2').value,document.all('CertifyCode').value,document.all('CertifyClass').value
                                       ,document.all('CertifyClass2').value,fm.managecom.value]);
  
  turnPage.pageDivName = "divCertifyList";
  turnPage.queryModal(strSql, CertifyListGrid);
  divCertifyList.style.display='';
  if(CertifyListGrid.mulLineCount==0){
    alert("û�д����ĵ�֤��");
    return false;  
  }  	    
}

//�ύ��ȷ�����
function submitForm()
{
  //����˫��
  fm.btnOp1.disabled = true;
  fm.btnOp2.disabled = true;
  
  var nSelectedCount = CertifyListGrid.getChkCount();
  if (nSelectedCount == null || nSelectedCount <= 0)
  {
    alert("������ѡ��һ��Ҫ���ĵ�֤�� ");
    fm.btnOp1.disabled = false;
    fm.btnOp2.disabled = false;
    return false;
  }
  
  if( verifyInput()==false || CertifyListGrid.checkValue()==false) {
    fm.btnOp1.disabled = false;
    fm.btnOp2.disabled = false;
    return false;  
  }
    
  if (confirm("��ȷʵ��ȷ�������?")){
    try{
	  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();	  
		fm.operateFlag.value = "INSERT";
	    document.getElementById("fm").submit(); //�ύ
    }catch(ex) {
  	  showInfo.close( );
  	  alert(ex);
    }
  }else{
    fm.btnOp1.disabled = false;
    fm.btnOp2.disabled = false;
    alert("��ȡ������������");
  }	
}

//�ύ���ܾ����
function cancelGetIn()
{
  //����˫��
  fm.btnOp1.disabled = true;
  fm.btnOp2.disabled = true;
  
  var nSelectedCount = CertifyListGrid.getChkCount();
  if (nSelectedCount == null || nSelectedCount <= 0)
  {
    alert("������ѡ��һ��Ҫ�ܾ����ĵ�֤�� ");
    fm.btnOp1.disabled = false;
    fm.btnOp2.disabled = false;
    return false;
  }
  
  if( verifyInput()==false || CertifyListGrid.checkValue()==false) {
    fm.btnOp1.disabled = false;
    fm.btnOp2.disabled = false;
    return false;  
  }  
    
  for (var i=0;i<CertifyListGrid.mulLineCount;i++ )
  {
    if (CertifyListGrid.getChkNo(i))
    {
	  if(CertifyListGrid.getRowColData(i,3)=="00"){
	    alert("��֤����["+CertifyListGrid.getRowColData(i,1)+"]Ϊӡˢ���ĵ�֤��������ܾ���");
    	fm.btnOp1.disabled = false;
    	fm.btnOp2.disabled = false;	    
        return false;
	  }
	  if(CertifyListGrid.getRowColData(i,10)==""){
	    alert("��֤����["+CertifyListGrid.getRowColData(i,1)+"]��¼��ܾ����ԭ��");
        fm.btnOp1.disabled = false;
    	fm.btnOp2.disabled = false;
        return false;
	  }
	  if(CertifyListGrid.getRowColData(i,10)!="3" && CertifyListGrid.getRowColData(i,10)!="4" && CertifyListGrid.getRowColData(i,10)!="5" ){
	    alert("��֤����["+CertifyListGrid.getRowColData(i,1)+"]�ܾ����ԭ��ֻ��Ϊ3��4��5��");
        fm.btnOp1.disabled = false;
    	fm.btnOp2.disabled = false;
        return false;
	  }
    }
  }
  
  if (confirm("��ȷʵ��ܾ������?")){
  try {
	  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
      var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	  var iWidth=550;      //�������ڵĿ��; 
	  var iHeight=250;     //�������ڵĸ߶�; 
	  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	  showInfo.focus();	  
	  fm.operateFlag.value = "CANCEL";
	  document.getElementById("fm").submit(); //�ύ
  } catch(ex) {
  	showInfo.close( );
  	alert(ex);
  }
  }else{
    fm.btnOp1.disabled = false;
    fm.btnOp2.disabled = false;  
    alert("��ȡ������������");
  }	  
  
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content, TakeBackNo )
{
  showInfo.close();	
  fm.operateFlag.value = "";//�������ͱ�־��INSERTΪȷ����⣬CANCELΪ�ܾ����
  fm.btnOp1.disabled = false;
  fm.btnOp2.disabled = false;
  if (FlagStr == "Fail" ) {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");    
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }else{ 
	content="�����ɹ���";	
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;	    
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	queryClick();//���²�ѯδ�������
  }
}
