var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var tResourceName="certify.CardPlanApplyInputSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
  if(fm.managecom.value.length != 4 && fm.managecom.value.length != 6 ){
    alert("ֻ�зֹ�˾��֧��˾���ܲ����˲˵��������µ�¼��");
    return;
  } 

  if(fm.PlanType1.value=="")
  {
    alert("��¼��ƻ����ͣ�");
	return;
  }
  
  CardPlanGrid.delBlankLine("CardPlanGrid");
  var nmulLineCount = CardPlanGrid.mulLineCount;
  if (nmulLineCount == null || nmulLineCount <= 0)
  {
    alert("����������һ��Ҫ����ļƻ��� ");
    return false;
  }
  
  if(CardPlanGrid.checkValue()==false)
  {
	return false;
  }
  
  //У�鲻ͬ�еĵ�֤���벻����ͬ
  for(var i=1; i<=CardPlanGrid.mulLineCount; i++){
    var certifyCode = CardPlanGrid.getRowColData(i-1,1);
    for(var j=i+1; j<=CardPlanGrid.mulLineCount; j++){
      var certifyCode2 = CardPlanGrid.getRowColData(j-1,1);
      if(certifyCode2==certifyCode){
        alert("��"+i+"�е�֤����͵�"+j+"���е�֤������ͬ����ͬ�ƻ����͵ĵ�֤�������ֻ������һ�Σ����飡");
        return false;
      }
    }  
  }
  
  fm.OperateType.value = "INSERT||APPLY";
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
	fm.action = './CardPlanApplySave.jsp';
  document.getElementById("fm").submit(); //�ύ
}

//Click�¼������������ѯ��ʱ�����ú���
function queryClick()
{
  if(fm.managecom.value.length != 4 && fm.managecom.value.length != 6 ){
    alert("ֻ�зֹ�˾��֧��˾���ܲ����˲˵��������µ�¼��");
    return;
  }
  
  initCardPlanQueryGrid();
  /*var strSql = "select a.planid, a.certifycode, a.plantype, (select certifyprice from lmcertifydes where certifycode=a.certifycode) price,a.appcount, (select certifyprice*a.appcount  from lmcertifydes  where certifycode = a.certifycode),a.appoperator, a.appcom, a.PlanState, a.makedate	"
  			+" from lzcardplan a where 1=1 and a.planstate='A' and a.appcom='"+ fm.managecom.value +"'"
  			+ getWherePart('a.PlanID', 'PlanID')
  			+ getWherePart('a.CertifyCode', 'CertifyCode')
  			+ getWherePart('a.PlanType', 'PlanType')
  			+" order by a.plantype, a.certifycode";*/
  var strSql = wrapSql(tResourceName,"querysqldes1",[fm.managecom.value,document.all('PlanID').value
                                        ,document.all('CertifyCode').value,document.all('PlanType').value]);
  //prompt("",strSql);
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
 	 alert("û�з��������ĵ�֤��¼��");
     return false;
    }else{
  	  turnPage.pageDivName = "divCardPlanQueryGrid";
  	  turnPage.queryModal(strSql, CardPlanQueryGrid);
	}
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  var nChkCount = CardPlanQueryGrid.getChkCount();
  if (nChkCount == null || nChkCount <= 0)
  {
    alert("������ѡ��һ��Ҫ�ύ�ļƻ��� ");
    return false;
  }
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
   {
    fm.OperateType.value = "UPDATE||APPLY";
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.action = './CardPlanApplySave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ�����޸Ĳ�����");
  }
}

function deleteClick()
{
  var nChkCount = CardPlanQueryGrid.getChkCount();
  if (nChkCount == null || nChkCount <= 0)
  {
    alert("������ѡ��һ��Ҫ�ύ�ļƻ��� ");
    return false;
  }
  if (confirm("��ȷʵ��ɾ���ü�¼��?"))
   {
    fm.OperateType.value = "DELETE||APPLY";
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
     var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    
	fm.action = './CardPlanApplySave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ����ɾ��������");
  }
}

function clearData()
{
    document.all('PlanID').value = '';
    document.all('CertifyCode').value = '';
    document.all('PlanType').value = '';
    document.all('MakeDate').value = '';
    //document.all('PlanState').value = '';
    initCardPlanQueryGrid();
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

function easyPrint()
{
   	if(CardPlanQueryGrid.mulLineCount==0)
   	{
   		alert("û�п��Դ�ӡ�����ݣ�");
   		return false;
   	}
	easyQueryPrint(2,'CardPlanQueryGrid','turnPage');
}

