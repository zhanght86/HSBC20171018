var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var tResourceName="certify.CardPlanAssignInputSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
  if(CardPlanQueryGrid.checkValue() == false)
  {
	return false;
  }
  
  var nChkCount = CardPlanQueryGrid.getChkCount();
  if (nChkCount == null || nChkCount <= 0)
  {
    alert("������ѡ��һ��Ҫ����Ļ�����");
    return false;
  }
  
  for(var i=0;i<CardPlanQueryGrid.mulLineCount;i++)
  {
    if(CardPlanQueryGrid.getChkNo(i)){
      if(CardPlanQueryGrid.getRowColData(i,5)==''){
        alert("���������������");
        return false;
      }
    }
  }
  
  fm.OperateType.value = "UPDATE||ASSIGN";
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
	fm.action = './CardPlanAssignSave.jsp';
    document.getElementById("fm").submit(); //�ύ
}

//Click�¼������������ѯ��ʱ�����ú���
function queryClick()
{
  if(fm.managecom.value.length != 2 ){
    alert("ֻ���ܹ�˾���ܲ����˲˵��������µ�¼��");
    return;
  } 
  
  initCardPlanQueryGrid();
  var strSql ='';
  var arrResult ;
  if(fm.CertifyCode.value=="" || fm.PlanType.value==""){
    alert("�����롾��֤���롿�͡��ƻ����͡���");
    return false;
  }
  /*strSql = "select nvl(sum(a.sumcount),0) from lzcard a where a.receivecom = 'A86' and a.stateflag = '2' "
    	+ getWherePart('a.CertifyCode', 'CertifyCode');*/
  strSql = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value]);
  arrResult = easyExecSql(strSql);
  if (arrResult != null) {
    fm.sumCount.value = arrResult[0][0];//�������
  }
  /*strSql = "select nvl(sum(a.assigncount), 0) from lzcardplan a where length(a.appcom) = 4 and a.relaplan is null"
    	+ getWherePart('a.CertifyCode', 'CertifyCode')
  	    + getWherePart('a.PlanType', 'PlanType');*/
  strSql = wrapSql(tResourceName,"querysqldes2",[document.all('CertifyCode').value,document.all('PlanType').value]);
  arrResult = easyExecSql(strSql);
  if (arrResult != null) {
    fm.sumAssign.value = arrResult[0][0];//�ѷ����ܼ�
  }
  fm.sumBalance.value = fm.sumCount.value - fm.sumAssign.value;//��������
  
  /*strSql = "select a.planid, a.appcom, (select name from ldcom where comcode = a.appcom), a.retcount, a.assigncount "
	+" from lzcardplan a where a.planstate = 'R' and a.relaplan is null and a.relaprint is null "	
  	+ getWherePart('a.CertifyCode', 'CertifyCode')
  	+ getWherePart('a.PlanType', 'PlanType')
  	+" order by a.appcom";*/
  strSql = wrapSql(tResourceName,"querysqldes3",[document.all('CertifyCode').value,document.all('PlanType').value]);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
 	 alert("û�з��������ĵ�֤��¼��");
     return false;
    }else{
  	  turnPage.pageDivName = "divCardPlanQueryGrid";
  	  turnPage.queryModal(strSql, CardPlanQueryGrid);
	}
}

function clearData()
{
    document.all('CertifyCode').value = '';
    document.all('PlanType').value = '';
    document.all('MakeDate').value = '';
    initCardPlanQueryGrid();
}

function queryClick2()
{
  if(fm.managecom.value.length != 2 ){
    alert("ֻ���ܹ�˾���ܲ����˲˵��������µ�¼��");
    return;
  } 
  if(fm.PlanType2.value==""){
    alert("�����롾�ƻ����͡���");
    return false;
  }
  
  initCardPlanListGrid();
  /*var strSql = "select a.appcom, (select name from ldcom where comcode = a.appcom) name,"
			 +"a.certifycode, (select certifyname from lmcertifydes where certifycode = a.certifycode) certifyname,"
			 +"(select nvl(sum(sumcount), 0) from lzcard where certifycode = a.certifycode and receivecom = 'A86' and stateflag in ('2', '3')) sumcount,"
			 +"a.assigncount,"
			 +"(select Printery from lzcardprint where certifycode = a.certifycode"
			 +"		 and printdate = (select max(printdate) from lzcardprint where certifycode = a.certifycode and state in ('1', '2')) and rownum = 1)"
			 +" from lzcardplan a"
 			 +" where a.planstate = 'R' and a.relaplan is null and a.assigncount > 0"
 			 + getWherePart('a.PlanType', 'PlanType2')
 			 + getWherePart('a.certifycode', 'CertifyCode2')			 
 			 + getWherePart('a.appcom', 'ManageCom2')
			 +" order by a.appcom, a.certifycode" ;*/
  var strSql = wrapSql(tResourceName,"querysqldes4",[document.all('PlanType2').value,document.all('CertifyCode2').value,document.all('ManageCom2').value]);

  turnPage2.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage2.strQueryResult) {
 	 alert("û�з��������ĵ�֤��¼��");
     return false;
    }else{
  	  turnPage2.pageDivName = "divCardPlanListGrid";
  	  turnPage2.queryModal(strSql, CardPlanListGrid);
	}

}

//[��ӡ]��ť����
function certifyPrint()
{
   	if(CardPlanListGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}
	easyQueryPrint(2,'CardPlanListGrid','turnPage2');	
}

function afterCodeSelect( cName, Filed)
{
  if(cName=='CertifyClassListNew')
  {
    displayMulLine();
  }else if( cName == 'HaveNumber' ) {
	displayNumberInfo();		
  }else if( cName == 'HaveLimit' ) {
	displayHaveLimit();		
  }else if( cName == 'HaveValidate' ) {
	displayHaveValidate();		
  }
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
    queryClick()
  }
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
    initCardRiskGrid();
  }
  catch(re)
  {
    alert("��ʼ��ҳ��������ó���");
  }
}

