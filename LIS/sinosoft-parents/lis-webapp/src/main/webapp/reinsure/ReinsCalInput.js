//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var Action;
var tRowNo=0;

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
}
function SubmitForm3()
{
    fm.Opt.value="Claim";
    fm.submit();
}
function SubmitForm2()
{
    fm.Opt.value="Edor";
    fm.submit();
}

function SubmitForm1()
{
    fm.Opt.value="Legal";
    fm.submit();
}

function SubmitForm()
{
    fm.Opt.value="Comm";
    fm.submit();
}
