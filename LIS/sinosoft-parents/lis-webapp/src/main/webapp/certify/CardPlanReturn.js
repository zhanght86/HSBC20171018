var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var tResourceName="certify.CardPlanReturnInputSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
  if(vertifyInput() == false)
  {
	return;
  }
  var nChkCount = CardPlanDetailGrid.getChkCount();
  if (nChkCount == null || nChkCount <= 0)
  {
    alert("������ѡ��һ��Ҫ�����ĵ�֤�� ");
    return false;
  }
  for(var i=0;i<CardPlanDetailGrid.mulLineCount;i++)
  {
    if(CardPlanDetailGrid.getChkNo(i)){
      if(CardPlanDetailGrid.getRowColData(i,9)==''){
        alert("����������������");
        return false;
      }
       if(CardPlanDetailGrid.getRowColData(i,9)-CardPlanDetailGrid.getRowColData(i,8)>0){
        alert("�����������ܴ��ڵ���������������¼�룡");
        return false;
      }
    }
  }
  
  fm.OperateType.value = "UPDATE||RETURN";
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
	fm.action = './CardPlanReturnSave.jsp';
  document.getElementById("fm").submit(); //�ύ
}

//Click�¼������������ѯ��ʱ�����ú���
function queryClick(flag)
{
  if(fm.managecom.value.length != 2 && fm.managecom.value.length != 4 ){
    alert("ֻ���ܹ�˾�ͷֹ�˾���ܲ����˲˵��������µ�¼��");
    return;
  } 

  initCardPlanQueryGrid();
  initCardPlanDetailGrid();
  var tManagecom = fm.managecom.value;
  if(flag=='D'){
    fm.PFbutton.disabled = false;//��������
    if(tManagecom.length==2){//�ܹ�˾��¼
      fm.PlanState.value = 'S';
      /*var strSql = "select a.appcom, a.plantype,max(a.appoperator),max(a.makedate) from lzcardplan a"
 			+" where a.planstate = 'S' and length(a.appcom) = '4' "
 			+ getWherePart('a.certifycode', 'CertifyCode')
 			+ getWherePart('a.PlanType', 'PlanType')
 			+ getWherePart('a.UpdateDate', 'StartDate', '>=')
 			+ getWherePart('a.UpdateDate', 'EndDate', '<=')
 			+" group by a.appcom, a.plantype order by a.plantype, max(a.makedate), a.appcom";*/
      var strSql = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value
                                           ,document.all('PlanType').value,document.all('StartDate').value,document.all('EndDate').value]);
    }else if(tManagecom.length==4){
      fm.PlanState.value = 'C';//�ֹ�˾��¼
      /*var strSql = "select a.appcom, a.plantype,max(a.appoperator),max(a.makedate) from lzcardplan a"
 			+" where a.planstate = 'C' and a.appcom like '" + tManagecom + "%'"
 			+ getWherePart('a.certifycode', 'CertifyCode')
 			+ getWherePart('a.PlanType', 'PlanType')
 			+ getWherePart('a.UpdateDate', 'StartDate', '>=')
 			+ getWherePart('a.UpdateDate', 'EndDate', '<=')
 			+" and exists (select 1 from lzcardplan b where b.planid=a.relaplan and b.planstate='R') "
 			+" group by a.appcom, a.plantype order by a.plantype, max(a.makedate), a.appcom";*/
      var strSql = wrapSql(tResourceName,"querysqldes2",[tManagecom,document.all('CertifyCode').value
                                           ,document.all('PlanType').value,document.all('StartDate').value,document.all('EndDate').value]);
    }else{
      alert("�˲�ѯֻ���ܹ�˾�ͷֹ�˾ʹ��!");
      return false;
    }
  }else if(flag=='Y'){
    fm.PFbutton.disabled = true;//����������
    if(fm.StartDate.value=='' || fm.EndDate.value==''){
      alert("��¼�롾��ʼ���ڡ��͡���ֹ���ڡ���");
      return false;
    }
    if(tManagecom=='86'){
      alert("�ܹ�˾���ϼ��������ƻ���");
    }else{
      fm.PlanState.value = 'R';//�ֹ�˾��¼
      /*
      var strSql = "select a.appcom, a.plantype,max(a.appoperator),max(a.makedate) from lzcardplan a"
 			+" where a.planstate = 'R' and a.appcom!=a.retcom and a.appcom = '" + tManagecom + "'"
 			+ getWherePart('a.certifycode', 'CertifyCode')
 			+ getWherePart('a.PlanType', 'PlanType')
 			+ getWherePart('a.UpdateDate', 'StartDate', '>=')
 			+ getWherePart('a.UpdateDate', 'EndDate', '<=')
 			+" group by a.appcom, a.plantype order by a.plantype, max(a.makedate), a.appcom";*/
      var strSql = wrapSql(tResourceName,"querysqldes3",[tManagecom,document.all('CertifyCode').value
                                           ,document.all('PlanType').value,document.all('StartDate').value,document.all('EndDate').value]);
    }
  }
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
 	 alert("û�в�ѯ����֤��¼��");
     return false;
    }else{
  	  turnPage.pageDivName = "divCardPlanQueryGrid";
  	  turnPage.queryModal(strSql, CardPlanQueryGrid);
	}
}

function showDetail(parm1, parm2)
{
  var tManageCom = CardPlanQueryGrid.getRowColData(CardPlanQueryGrid.getSelNo()-1, 1);
  var tPlanType = CardPlanQueryGrid.getRowColData(CardPlanQueryGrid.getSelNo()-1, 2);
  if(fm.managecom.value=='86'){//�ܹ�˾��¼
      /*var strSql = "select a.planid, a.appcom, a.plantype,a.certifycode,"
      	  +"(select certifyname from lmcertifydes where certifycode=a.certifycode),(select certifyprice from lmcertifydes where certifycode=a.certifycode) price, a.appcount, a.ConCount, a.ConCount,(select certifyprice*a.ConCount  from lmcertifydes  where certifycode = a.certifycode)"
    	  +" from lzcardplan a where a.planstate = 'S' and a.appcom = '" + tManageCom
    	  +"' and a.plantype ='" + tPlanType + "'"
    	  + getWherePart('a.certifycode', 'CertifyCode')  
    	  + getWherePart('a.UpdateDate', 'StartDate', '>=')
 		  + getWherePart('a.UpdateDate', 'EndDate', '<=')  	  
    	  +" order by a.certifycode";*/
      var strSql = wrapSql(tResourceName,"querysqldes4",[tManageCom,tPlanType,document.all('CertifyCode').value
                                           ,document.all('StartDate').value,document.all('EndDate').value]);
    }else{
      var mPlanState = fm.PlanState.value;
      if(mPlanState=='C'){//�ֹ�˾��¼��������
         /*var strSql = " select a.planid, a.appcom, a.plantype,a.certifycode,"
      	  +"(select certifyname from lmcertifydes where certifycode=a.certifycode),(select certifyprice from lmcertifydes where certifycode=a.certifycode) price, a.appcount,a.ConCount, a.ConCount,(select certifyprice*a.ConCount  from lmcertifydes  where certifycode = a.certifycode)"
		  +" from lzcardplan a where a.planstate = 'C' and a.appcom = '" + tManageCom
  		  +"' and a.plantype ='" + tPlanType + "'"
  		  + getWherePart('a.certifycode', 'CertifyCode')
    	  + getWherePart('a.UpdateDate', 'StartDate', '>=')
 		  + getWherePart('a.UpdateDate', 'EndDate', '<=')    		  
  		  +" order by a.certifycode";*/
         var strSql = wrapSql(tResourceName,"querysqldes5",[tManageCom,tPlanType,document.all('CertifyCode').value
                                              ,document.all('StartDate').value,document.all('EndDate').value]);
  	  }else if(mPlanState=='R'){//�ֹ�˾��¼��������
        /* var strSql = " select a.planid, a.appcom, a.plantype,a.certifycode,"
      	  +"(select certifyname from lmcertifydes where certifycode=a.certifycode),(select certifyprice from lmcertifydes where certifycode=a.certifycode) price, a.appcount, a.ConCount, a.retcount,(select certifyprice*a.retcount  from lmcertifydes  where certifycode = a.certifycode)"
		  +" from lzcardplan a where a.planstate = 'R' and a.appcom!=a.retcom and a.appcom = '" + tManageCom
  		  +"' and a.plantype ='" + tPlanType + "'"
  		  + getWherePart('a.certifycode', 'CertifyCode')
    	  + getWherePart('a.UpdateDate', 'StartDate', '>=')
 		  + getWherePart('a.UpdateDate', 'EndDate', '<=')    		  
  		  +" order by a.certifycode";*/
         var strSql = wrapSql(tResourceName,"querysqldes6",[tManageCom,tPlanType,document.all('CertifyCode').value
                                              ,document.all('StartDate').value,document.all('EndDate').value]);
  	  }
    }
  
  turnPage2.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage2.strQueryResult) {
 	 alert("��ѯ��֤�ƻ���ϸ����");
     return false;
    }else{
  	  turnPage2.pageDivName = "CardPlanDetailGrid";
  	  turnPage2.queryModal(strSql, CardPlanDetailGrid);
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
    queryClick('D');
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

//У������
function vertifyInput()
{
  return true;
}
