//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
}

function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
    parent.fraMain.rows = "0,0,0,0,*";
  }
  else
  {
    parent.fraMain.rows = "0,0,0,0,*";
  }
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

function ShowCaseDetail()
{
	fm.action = "./ShowCaseDetail.jsp";
	fm.submit();	
}

function SecondUW()
{
	var varSrc = "&CaseNo=" + fm.CaseNo.value;
  varSrc += "&InsuredNo=" + fm.InsuredNo.value;
  varSrc += "&CustomerName=" + fm.CustomerName.value;
  varSrc += "&RgtNo=" + fm.RgtNo.value;
  varSrc += "&Type=1";
  var newWindow = window.open("../case/FraimSecondUW.jsp?Interface=QuerySecondUWInput.jsp"+varSrc,"SecondUWInput",'width=700,height=500,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
}


function EndAgreement()
{
	var varSrc = "&CaseNo=" + fm.CaseNo.value;
  varSrc += "&InsuredNo=" + fm.InsuredNo.value;
  varSrc += "&CustomerName=" + fm.CustomerName.value;
  varSrc += "&RgtNo=" + fm.RgtNo.value;
  varSrc += "&Type=1";
  var newWindow = window.open("../case/FraimEndAgreement.jsp?Interface=QueryEndAgreementInput.jsp"+varSrc,"EndAgreementInput",'width=700,height=500,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
}