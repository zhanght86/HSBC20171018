var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var tResourceName="certify.CardPlanConfInputSql";
//Click�¼������������ѯ��ʱ�����ú���
function queryClick()
{
  if(fm.managecom.value.length != 4 ){
    alert("ֻ�зֹ�˾���ܲ����˲˵��������µ�¼��");
    return;
  } 

  initCardPlanQueryGrid();
  var tManagecom = fm.managecom.value;
  /*var strSql = "select a.appcom, a.plantype, max(a.appoperator), max(a.updatedate)"
			+" from lzcardplan a where a.relaplan is null and a.planstate = 'C' " 
  			+" and a.appcom like '"+ tManagecom +"%'"
  			+" group by a.appcom, a.plantype order by a.appcom, a.plantype";*/
  var strSql = wrapSql(tResourceName,"querysqldes1",[tManagecom]);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
 	 alert("û�д������ĵ�֤�ƻ���");
     return false;
    }else{
  	  turnPage.pageDivName = "divCardPlanQueryGrid";
  	  turnPage.queryModal(strSql, CardPlanQueryGrid);
	}
}

function showDetail(parm1, parm2)
{
  var tAppcom = CardPlanQueryGrid.getRowColData(CardPlanQueryGrid.getSelNo()-1, 1);
  var tPlantype = CardPlanQueryGrid.getRowColData(CardPlanQueryGrid.getSelNo()-1, 2);
  
  initCardPlanQueryDetailGrid();
  /*var strSql="select a.planid,(select certifyclass from lmcertifydes where certifycode = a.certifycode) certifyclass, "
			+" a.certifycode, (select certifyname from lmcertifydes where certifycode = a.certifycode), "
			+" (select certifyprice from lmcertifydes where certifycode=a.certifycode) price,a.appcount, a.concount, "
			+" (select certifyprice*a.concount  from lmcertifydes  where certifycode = a.certifycode)"
  			+" from lzcardplan a where a.relaplan is null and a.planstate = 'C' "
  			+" and a.appcom='" + tAppcom + "' and a.plantype='" + tPlantype + "'"
			+" order by certifyclass, a.certifycode";*/
  var strSql = wrapSql(tResourceName,"querysqldes2",[tAppcom,tPlantype]);
  	  turnPage2.pageDivName = "divCardPlanQueryDetailGrid";
  	  turnPage2.queryModal(strSql, CardPlanQueryDetailGrid);
}

//��ѯ�˻��������е�֤�Ļ��ܽ����չʾ��ҳ��
function queryPack()
{
  if(fm.managecom.value.length != 4 ){
    alert("ֻ�зֹ�˾���ܲ����˲˵��������µ�¼��");
    return;
  } 
  if(fm.PlanType.value==null || fm.PlanType.value==""){
    alert("������ƻ����ͣ�");
    return;  
  }
  
  initCardPlanPackGrid();
  /*var strSql=" select k1,k2,k3,k4,k5,sumcount,(k5*sumcount) k6 from("
	  		+" select a.plantype k1, (select certifyclass from lmcertifydes where certifycode = a.certifycode) k2, "
			+" a.certifycode k3, (select certifyname from lmcertifydes where certifycode = a.certifycode) k4, (select certifyprice from lmcertifydes where certifycode=a.certifycode) k5,"
			+" sum(a.concount) sumcount from lzcardplan a where a.relaplan is null and a.planstate = 'C' "
	 		+" and a.appcom like '" + fm.managecom.value + "%'"
 			+" and a.PlanType = '" + fm.PlanType.value + "'"
 			+" group by a.plantype,a.certifycode ) order by k1,k3";*/
  var strSql = wrapSql(tResourceName,"querysqldes3",[fm.managecom.value,fm.PlanType.value]);
  turnPage3.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage3.strQueryResult) {
 	 alert("û�д����ܵĵ�֤�ƻ���");
     return false;
    }else{ 					
  	  turnPage3.pageDivName = "divCardPlanPackGrid";
  	  turnPage3.queryModal(strSql, CardPlanPackGrid);
  	}
}

//Click�¼����������ѡ�����ĵ�֤��������
function updateClick()
{
  if(CardPlanQueryDetailGrid.checkValue()==false){
	return false;
  }
  if(CardPlanQueryDetailGrid.getChkCount()==0){
    alert("������ѡ��һ�������ļƻ���");
    return false;
  }
  for (var i=0;i<CardPlanQueryDetailGrid.mulLineCount;i++ )
  {
    if (CardPlanQueryDetailGrid.getChkNo(i))
    {
  	  var AppCount=CardPlanQueryDetailGrid.getRowColData(i,6);//��������
  	  var ConCount=CardPlanQueryDetailGrid.getRowColData(i,7);//��������
  	  if(AppCount/1 < ConCount/1){
  	    alert("��"+(i+1)+"�е����������ܴ�����������,������¼�룡");
        return false;
  	  }
  	}
  }  
  if (confirm("��ȷ�ϵ���������?"))
  {
    fm.OperateType.value = "UPDATE||CONF";
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.action = './CardPlanConfSave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ���˵������棡");
  }
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var tmulLineCount = CardPlanPackGrid.mulLineCount;
  if (tmulLineCount == null || tmulLineCount <= 0)
  {
    alert("����Ҫ��һ��Ҫ�ύ�ļƻ���");
    return false;
  }
  
  if (confirm("��ȷ�ϻ����ύ����ȷ������֧��˾�ƻ�������ϣ�")){
    fm.OperateType.value = "INSERT||PACK";
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.action = './CardPlanConfSave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }else{
    alert("��ȡ���˻����ύ������");
  }	 
}

//[��ӡ]��ť����
function CardPlanDetailPrint()
{
	//alert("��ѯ���ļ�¼������"+CardPlanQueryDetailGrid.mulLineCount);
   	if(CardPlanQueryDetailGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}
	easyQueryPrint(2,'CardPlanQueryDetailGrid','turnPage2');	
}

//[��ӡ]��ť����
function CardPlanPackPrint()
{
	//alert("��ѯ���ļ�¼������"+CardPlanPackGrid.mulLineCount);
   	if(CardPlanPackGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}
	easyQueryPrint(2,'CardPlanPackGrid','turnPage3');	
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
	initForm();
  }
}
