//�������ƣ�UWGErrInit.jsp
//�����ܣ�����˱�δ��ԭ���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var tPOLNO = "";
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //�ύ
  //alert("submit");
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  }
  else
  { 
    //ִ����һ������
  }
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

//����Ͷ����Ϣ
function showApp()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  cProposalNo=fm.ProposalNo.value;
  //showModalDialog("./UWApp.jsp?ProposalNo="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo1 = window.open ("./UWApp.jsp?ProposalNo="+cProposalNo,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
    showInfo.close();
}           

//�����˱���¼
function showOldUWSub()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  cProposalNo=fm.ProposalNo.value;
  //showModalDialog("./UWSub.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo1 = window.open ("./UWSub.jsp?ProposalNo1="+cProposalNo,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
  //window.open("./UWSubMain.jsp?ProposalNo1="+cProposalNo,"window1");
  showInfo.close();
}

//��ǰ�˱���¼
function showNewUWSub()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  cProposalNo=fm.ProposalNo.value;
  //showModalDialog("./UWSub.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo1 = window.open ("./UWSub.jsp?ProposalNo1="+cProposalNo,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
  showInfo.close();
}                      

//������ϸ��Ϣ
function showPolDetail()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  cProposalNo=fm.ProposalNo.value;
  window.open("../app/ProposalDisplay.jsp?PolNo="+cProposalNo,"window1");
  showInfo.close();
}           

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}


//ѡ��Ҫ�˹��˱�����
function queryUWErr()
{
	//showSubmitFrame(mDebug);
	document.getElementById("fm").submit();
}

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initUWGErrGrid();
	initUWErrGrid();
	
	// ��дSQL���
	var strSQL = "";
	var GrpContNo = document.all('GrpContNo').value;
	var BqFlag = "";
	try{
	   BqFlag = document.all('BqFlag').value;
  }catch(ex){}
  	if(BqFlag == null || BqFlag != 1){
  		
  var sqlid825101757="DSHomeContSql825101757";
var mySql825101757=new SqlClass();
mySql825101757.setResourceName("uw.UWGErrInputSql");//ָ��ʹ�õ�properties�ļ���
mySql825101757.setSqlId(sqlid825101757);//ָ��ʹ�õ�Sql��id
mySql825101757.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825101757.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825101757.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825101757.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825101757.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825101757.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825101757.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825101757.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825101757.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825101757.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825101757.addSubPara(GrpContNo);//ָ������Ĳ���
strSQL=mySql825101757.getString();		
  
//	strSQL = "select a.grpcontno,a.grppolno,b.RiskCode x, a.uwno y,a.uwerror,a.modifydate from  LCGUWError a ,LCGrpPol b where 1=1 "
//	     + " and a.GrpPolNo = b.GrpPolNo"
//			 + " and a.GrpContNo ='" + GrpContNo + "'"
//			 + " and (a.uwno = (select max(b.uwno) from LCGUWError b where b.GrpContNo = '" + GrpContNo + "' and b.GrpPolNo = a.GrpPolNo))"
//			 + " and a.uwerror not like '%�ŵ���������˹��˱�%'"
//			 + " union "
//			 + "select c.grpcontno,'','' x, c.uwno y,c.uwerror,c.modifydate from LCGCUWError c where 1=1"
//			 + " and c.GrpContNo ='" + GrpContNo + "'"
//			 + " and (c.uwno = (select max(d.uwno) from LCGCUWError d where d.GrpContNo = '" + GrpContNo + "'))"
//			 + " and c.uwerror not like '%�ŵ���������˹��˱�%'"
//			 + " union "
//			 + "select distinct a.grpcontno,b.grppolno,b.riskcode x,"
//			 + "(select max(LCGUWError.uwno) from LCGUWError where LCGUWError.GrpContNo = '" + GrpContNo + "' and LCGUWError.GrpPolNo = b.GrpPolNo) y,"
//			 + "a.uwerror,a.modifydate from  LCUWError a ,LCPol b where 1=1 "
//	         + " and b.GrpContNo ='" + GrpContNo + "'"
//	         + " and b.PolNo = a.PolNo"
//			 + " and a.GrpContNo ='" + GrpContNo + "'"
//			 + " and (a.uwno = (select max(b.uwno) from LCUWError b where b.GrpContNo = '" + GrpContNo + "'))"
//			 + " union "
//			 + "select distinct c.grpcontno,'','' x,"
//			 + "(select max(LCGCUWError.uwno) from LCGCUWError where LCGCUWError.GrpContNo = '" + GrpContNo + "') y,"
//			 + "c.uwerror,c.modifydate from LCCUWError c where 1=1"
//			 + " and c.GrpContNo ='" + GrpContNo + "'"
//			 + " and (c.uwno = (select max(d.uwno) from LCCUWError d where d.GrpContNo = '" + GrpContNo + "'))"
//			 + " order by x,y";
			 
	//execEasyQuery( strSQL );
	turnPage.queryModal(strSQL,UWGErrGrid);
//	if (UWGErrGrid.mulLineCount == 0)
//	{
//	    alert("û���Զ��˱���Ϣ���ŵ���������˹��˱���");
//	}
	
	var sqlid825103211="DSHomeContSql825103211";
var mySql825103211=new SqlClass();
mySql825103211.setResourceName("uw.UWGErrInputSql");//ָ��ʹ�õ�properties�ļ���
mySql825103211.setSqlId(sqlid825103211);//ָ��ʹ�õ�Sql��id
mySql825103211.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825103211.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825103211.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825103211.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825103211.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825103211.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825103211.addSubPara(GrpContNo);//ָ������Ĳ���
strSQL=mySql825103211.getString();
	
//	strSQL = "select a.grpcontno,a.contno z,a.polno,c.customerseqno,a.insuredname,b.RiskCode x, a.uwno y,a.uwerror,a.modifydate from  LCUWError a ,LCPol b,LCInsured c where 1=1 "
//	         + " and b.GrpContNo='" + GrpContNo + "'"
//	         + " and b.PolNo = a.PolNo"
//			 + " and a.GrpContNo ='" + GrpContNo + "'"
//			 + " and (a.uwno = (select max(b.uwno) from LCUWError b where b.GrpContNo = '" + GrpContNo + "' and b.PolNo = a.PolNo))"
//			 + " and c.contno=a.contno and c.insuredno=a.insuredno"
//			 + " and c.grpcontno='" + GrpContNo + "'"
//			 + " union "
//			 + "select c.grpcontno,c.contno z,'',aa.customerseqno,c.insuredname,'' x, c.uwno y,c.uwerror,c.modifydate from LCCUWError c,LCInsured aa where 1=1"
//			 + " and c.GrpContNo ='" + GrpContNo + "'"
//			 + " and aa.contno=c.contno and aa.insuredno=c.insuredno"
//			 + " and aa.grpcontno='" + GrpContNo + "'"
//			 + " and (c.uwno = (select max(d.uwno) from LCCUWError d where d.GrpContNo = '" + GrpContNo + "'))"
//			 + " order by z,x,y"
	
	//execEasyQuery( strSQL );
	turnPage1.queryModal(strSQL,UWErrGrid);
  }else if(BqFlag !=null && BqFlag == 1){
  	//alert(EdorNo);
  	
  	var sqlid825105754="DSHomeContSql825105754";
var mySql825105754=new SqlClass();
mySql825105754.setResourceName("uw.UWGErrInputSql");//ָ��ʹ�õ�properties�ļ���
mySql825105754.setSqlId(sqlid825105754);//ָ��ʹ�õ�Sql��id
mySql825105754.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825105754.addSubPara(EdorNo);//ָ������Ĳ���
mySql825105754.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825105754.addSubPara(EdorNo);//ָ������Ĳ���
mySql825105754.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825105754.addSubPara(EdorNo);//ָ������Ĳ���
mySql825105754.addSubPara(EdorNo);//ָ������Ĳ���
mySql825105754.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825105754.addSubPara(EdorNo);//ָ������Ĳ���
mySql825105754.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825105754.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825105754.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825105754.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825105754.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825105754.addSubPara(GrpContNo);//ָ������Ĳ���
strSQL=mySql825105754.getString();
  	
//	strSQL = "select a.grpcontno,a.grppolno,b.RiskCode x, nvl(to_char(a.uwno),' ') y,a.uwerror,a.modifydate from  LPGUWError a ,LPGrpPol b where 1=1 "
//	     + " and a.GrpPolNo = b.GrpPolNo and a.EdorNo = b.EdorNo "
//			 + " and a.GrpContNo ='" + GrpContNo + "'"
//			 + " and a.EdorNo ='" + EdorNo + "'"
//			 + " and (a.uwno = (select max(b.uwno) from LPGUWError b where b.GrpContNo = '" + GrpContNo + "' and b.GrpPolNo = a.GrpPolNo and b.EdorNo = a.EdorNo))"
//			 + " and a.uwerror not like '%�ŵ���������˹��˱�%'"
//			 + " union "
//			 + "select c.grpcontno,' ',' ' x, nvl(to_char(c.uwno),' ') y,c.uwerror,c.modifydate from LPGCUWError c where 1=1"
//			 + " and c.GrpContNo ='" + GrpContNo + "'"
//			 + " and c.EdorNo ='" + EdorNo + "'"
//			 + " and (c.uwno = (select max(d.uwno) from LPGCUWError d where d.GrpContNo = '" + GrpContNo + "' and d.EdorNo = c.EdorNo))"
//			 + " and c.uwerror not like '%�ŵ���������˹��˱�%'"
//			 + " union "
//			 + "select distinct a.grpcontno,b.grppolno,b.riskcode x,"
//			 + "(select nvl(to_char(max(LPGUWError.uwno)),' ') from LPGUWError where LPGUWError.GrpContNo = '" + GrpContNo + "' and LPGUWError.GrpPolNo = b.GrpPolNo) y,"
//			 +"a.uwerror,a.modifydate from  LPUWError a ,LPPol b where 1=1 "
//	                 + " and b.PolNo = a.PolNo and b.EdorNo = a.EdorNo"
//			 + " and a.GrpContNo ='" + GrpContNo + "'"
//			 + " and a.EdorNo ='" + EdorNo + "'"
//			 + " and (a.uwno = (select max(b.uwno) from LPUWError b where b.GrpContNo = '" + GrpContNo + "' and b.EdorNo = a.EdorNo))"
//			 + " union "
//			 + "select distinct c.grpcontno,' ',' ' x,"
//			 + "(select nvl(to_char(max(LPGCUWError.uwno)),' ') from LPGCUWError where LPGCUWError.GrpContNo = '" + GrpContNo + "' and LPGCUWError.EdorNo = '"+EdorNo+"') y,"
//			 + "c.uwerror,c.modifydate from LPCUWError c where 1=1"
//			 + " and c.GrpContNo ='" + GrpContNo + "'"
//			 + " and c.EdorNo ='" + EdorNo + "'"
//			 + " and (c.uwno = (select max(d.uwno) from LPCUWError d where d.GrpContNo = '" + GrpContNo + "' and d.EdorNo = c.EdorNo))"
//			 + " order by x,y";
			 
			 turnPage.queryModal(strSQL,UWGErrGrid);
			 
	var sqlid825111043="DSHomeContSql825111043";
var mySql825111043=new SqlClass();
mySql825111043.setResourceName("uw.UWGErrInputSql");//ָ��ʹ�õ�properties�ļ���
mySql825111043.setSqlId(sqlid825111043);//ָ��ʹ�õ�Sql��id
mySql825111043.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825111043.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825111043.addSubPara(EdorNo);//ָ������Ĳ���
mySql825111043.addSubPara(GrpContNo);//ָ������Ĳ���
mySql825111043.addSubPara(EdorNo);//ָ������Ĳ���
mySql825111043.addSubPara(GrpContNo);//ָ������Ĳ���
strSQL=mySql825111043.getString();

	
//	strSQL = "select a.grpcontno,a.contno z,a.polno,c.customerseqno,a.insuredname,b.RiskCode x, a.uwno y,a.uwerror,a.modifydate from  LPUWError a ,LPPol b,LPInsured c where 1=1 "
//	     + " and b.PolNo = a.PolNo and a.EdorNo = b.EdorNo and a.Edorno = c.EdorNo"
//			 + " and a.GrpContNo ='" + GrpContNo + "'"
//			 + " and (a.uwno = (select max(uwno) from LPUWError  where GrpContNo = '" + GrpContNo + "' and PolNo = a.PolNo and EdorNo = a.EdorNo))"
//			 + " and c.contno=a.contno and c.insuredno=a.insuredno and a.EdorNo ='"+EdorNo+"'"
//			 + " union "
//			 + "select c.grpcontno,c.contno z,'',aa.customerseqno,c.insuredname,'' x, c.uwno y,c.uwerror,c.modifydate from LPCUWError c,LPInsured aa where 1=1"
//			 + " and c.GrpContNo ='" + GrpContNo + "'"
//			 + " and c.EdorNo ='" + EdorNo + "'"
//			 + " and aa.contno=c.contno and aa.insuredno=c.insuredno and aa.EdorNo = c.EdorNo"
//			 + " and (c.uwno = (select max(d.uwno) from LPCUWError d where d.GrpContNo = '" + GrpContNo + "' and d.EdorNo= c.EdorNo))"
//			 + " order by z,x,y";
			 
	turnPage1.queryModal(strSQL,UWErrGrid);
  }	
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				UWGErrGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

//[��ӡ]��ť����
function uwPrint()
{
	//alert("��ѯ���ļ�¼������"+CardInfoGrid.mulLineCount);
   	if(UWErrGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�����");
   		return false;
   	}
	easyQueryPrint(2,'UWErrGrid','turnPage1');	
}

function grpUWPrint()
{
    //alert("��ѯ���ļ�¼������"+CardInfoGrid.mulLineCount);
   	if(UWGErrGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�����");
   		return false;
   	}
	easyQueryPrint(2,'UWGErrGrid','turnPage');
}
