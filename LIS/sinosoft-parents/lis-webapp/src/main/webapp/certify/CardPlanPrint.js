var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var tResourceName="certify.CardPlanPrintInputSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
  if(vertifyInput() == false)
  {
	return;
  }
  var nChkCount = CardPrintQueryGrid.getChkCount();
  if (nChkCount == null || nChkCount <= 0)
  {
    alert("������ѡ��һ��Ҫӡˢ�ĵ�֤�� ");
    return false;
  }
  for(var i=0;i<CardPrintQueryGrid.mulLineCount;i++)
  {
    if(CardPrintQueryGrid.getChkNo(i)){        
      if(CardPrintQueryGrid.getRowColData(i,8)=='' || CardPrintQueryGrid.getRowColData(i,8)<=0){
        alert("������ӡˢ����, ��ӡˢ����Ҫ����0��");
        return false;
      }
      
      if(CardPrintQueryGrid.getRowColData(i,9)==''){
        alert("������ӡˢ�����ƣ�");
        return false;
      }   

      if(CardPrintQueryGrid.getRowColData(i,12)!='' && isDate(CardPrintQueryGrid.getRowColData(i,12))==false){
        alert("��������ȷ��ʽ��ʹ�ý�ֹ���ڣ�yy-mm-dddd��");
        return false;
      }      
    }
  }
  if (confirm("��ȷ���кŵ�֤����ֹ���롢ӡˢ���ۡ�ӡˢ�����ơ�ʹ�ý�ֹ���ڵ���Ϣ��¼����ȷ������Ҫӡˢ��?")){ 
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
     var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.action = './CardPlanPrintSave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }else{
    alert("��ȡ����ӡˢ������");
  }
}

//��ӡˢ��ѯ
function queryClick()
{
  if(fm.managecom.value.length != 2 && fm.managecom.value.length != 4 ){
    alert("ֻ���ܹ�˾�ͷֹ�˾���ܲ����˲˵��������µ�¼��");
    return;
  } 
  
  initCardPrintQueryGrid();
  fm.buttonP.disabled = false;//����ӡˢ
  /*var strSql = "select a.prtno, a.plantype, a.certifycode, "
  	+" (select b.certifyname from lmcertifydes b where b.certifycode=a.certifycode), a.sumcount,"
  	+" case a.plantype when '6' then a.startno "
	+"  else lpad(((select nvl(max(endno), 0) + 1 from lzcardprint where certifycode = a.certifycode)),"
	+"  (select certifylength from lmcertifydes where certifycode = a.certifycode), '0') end, "
  	+" case a.plantype when '6' then a.endno "
	+"  else lpad(((select nvl(max(endno), 0) + a.sumcount from lzcardprint where certifycode = a.certifycode)),"
	+"  (select certifylength from lmcertifydes where certifycode = a.certifycode),'0') end,"  	
  	+" a.certifyprice, "
  	+" (select Printery from lzcardprint where certifycode = a.certifycode and printdate = (select max(printdate) from lzcardprint where certifycode = a.certifycode and state in ('1', '2')) and rownum = 1),"
	+" (select Phone    from lzcardprint where certifycode = a.certifycode and printdate = (select max(printdate) from lzcardprint where certifycode = a.certifycode and state in ('1', '2')) and rownum = 1),"
	+" (select LinkMan  from lzcardprint where certifycode = a.certifycode and printdate = (select max(printdate) from lzcardprint where certifycode = a.certifycode and state in ('1', '2')) and rownum = 1),"
  	+" '' "
	+" from lzcardprint a where a.state = '0'"
  	+ getWherePart('a.CertifyCode', 'CertifyCode')
  	+ getWherePart('a.PlanType', 'PlanType')
  	+ getWherePart('a.ManageCom', 'managecom')
  	+" order by a.plantype, a.certifycode";*/
  var strSql = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value,document.all('PlanType').value,fm.managecom.value]);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
  if (!turnPage.strQueryResult) {
 	 alert("û�з��������ĵ�֤��¼��");
     return false;
    }else{
  	  turnPage.pageDivName = "divCardPrintQueryGrid";
  	  turnPage.queryModal(strSql, CardPrintQueryGrid);
	}
}

//��ӡˢ��ѯ
function queryClick2()
{
  if(fm.managecom.value.length != 2 && fm.managecom.value.length != 4 ){
    alert("ֻ���ܹ�˾�ͷֹ�˾���ܲ����˲˵��������µ�¼��");
    return;
  } 
  
  initCardPrintQueryGrid();
  fm.buttonP.disabled = true;//������ӡˢ
 /* var strSql = "select a.prtno, a.plantype, a.certifycode,"
  +"(select b.certifyname from lmcertifydes b where b.certifycode=a.certifycode),"
  +" a.sumcount, a.startno,a.endno, a.certifyprice, a.printery, a.phone, a.linkman, a.maxdate "
	+" from lzcardprint a where a.state in ('1','2')"
  	+ getWherePart('a.CertifyCode', 'CertifyCode')
  	+ getWherePart('a.PlanType', 'PlanType')
  	+ getWherePart('a.ManageCom', 'managecom')
  	+" order by a.plantype, a.certifycode, a.startno";*/
  var strSql = wrapSql(tResourceName,"querysqldes2",[document.all('CertifyCode').value,document.all('PlanType').value,fm.managecom.value]);
  	
  turnPage.pageDivName = "divCardPrintQueryGrid";
  turnPage.queryModal(strSql, CardPrintQueryGrid); 	
  if (CardPrintQueryGrid.mulLineCount==0){
 	 alert("û�з��������ĵ�֤��¼��");
     return false;
  }   
}

function showDetail(parm1, parm2)
{
  var tPrtNo = CardPrintQueryGrid.getRowColData(CardPrintQueryGrid.getSelNo()-1, 1);
  /*var sql="select a.maxdate,a.printery,a.phone,a.linkman from lzcardprint a where a.prtno='"
  	     + tPrtNo+"'";*/
  var sql = wrapSql(tResourceName,"querysqldes3",[tPrtNo]);
  var tResult = easyExecSql(sql);
  	document.all('MaxDate').value = tResult[0][0];
    document.all('Printery').value = tResult[0][1];
    document.all('Phone').value = tResult[0][2];
    document.all('LinkMan').value = tResult[0][3];
}

function clearData()
{
  initForm();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    queryClick();  
  }
}

//У������
function vertifyInput()
{
  return true;
}