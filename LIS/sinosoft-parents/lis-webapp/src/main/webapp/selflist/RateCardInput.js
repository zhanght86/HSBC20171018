var showInfo;
var mDebug="1";
var Operatetype="";


function displayNumberInfo()
{
	var vHaveNumber = fm.HaveNumber.value;
	if( vHaveNumber == 'Y' ) {
		fm.CertifyLength.disabled = false;
	} else {
		fm.CertifyLength.value = 18;
		fm.CertifyLength.disabled = true;
	}
}

function displayMulLine()
{
  var displayType = fm.CertifyClass.value;

  document.all('divShow').style.display="";
  document.all('divCardRisk').style.display="";

}


//�ύ�����水ť��Ӧ����
function submitForm()
{
	if(vertifyInput() == false)
	{
		return;
	}

  //����ʱ��Ҫ���ݽ�������ı��Ѻ����ֱ���ȥУ�����ݿ����Ƿ��Ѵ�����ͬ�ļ�¼
  fm.HiddenRiskCode.value=fm.Riskcode.value;
  fm.HiddenPrem.value=fm.Prem.value;
  
  fm.Operatetype.value = "INSERT";
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
  fm.action = './RateCardSave.jsp';
  document.getElementById("fm").submit(); //�ύ
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
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    initForm();
  }
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
    initForm();
  }
  catch(re)
  {
    alert("��ʼ��ҳ��������ó���");
  }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//��ʾfrmSubmit��ܣ���������
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

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
   {
		if(vertifyInput() == false)
		{
			return;
		}
    fm.Operatetype.value = "UPDATE";
    var i = 0;
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showSubmitFrame(mDebug);
    
    fm.action = './RateCardSave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    fm.Operatetype.value = "";
    alert("��ȡ�����޸Ĳ�����");
  }
}
//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
	initForm();					//�������ʼ��form�Ļ�������һ�β�ѯ������ˣ��ٴβ�ѯ��ʱ�������div��ʾ�Ͳ�������
	//showDiv(divShow,"");
	//showDiv(divCardRisk,"");
	
  //fm.Operatetype.value="QUERY";
  mOperate="QUERY||MAIN";
  showInfo=window.open("./FrameRateCardQuery.jsp");
}

function deleteClick()
{
  alert("���޷�ִ��ɾ��������������");
  return ;
}


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

//У����������ֺ���
function doCheckNumber(str)
{ 
    //alert(str);
	for(var i=0; i<str.length; i++)
	{
		if(str.charAt(i)<'0' || str.charAt(i)>'9')
		{
			return false;
		}
	}
	
	return true;
}

//У������
function vertifyInput()
{
  if((fm.Riskcode.value==null)||(fm.Riskcode.value==""))
  {
    alert("����¼�����ֱ��룡����");
    return false;
  }

  
  if((fm.ProductPlan.value==null)||(fm.ProductPlan.value==""))
  {
    alert("����¼���Ʒ�ƻ�������");
    return false;
  }
  
  if((fm.InsuYear.value==null)||(fm.InsuYear.value==""))
  {
    alert("����¼�뱣�����ڣ�����");
    return false;
  }
  
  if(!doCheckNumber(document.all('InsuYear' ).value))
  {
	alert("�������ڱ���������");
	return false;
  }

  
  if((fm.InsuYearFlag.value==null)||(fm.InsuYearFlag.value==""))
  {
    alert("����¼�뱣�����ڵ�λ������");
    return false;
  }
  
  if((fm.Prem.value==null)||(fm.Prem.value==""))
  {
    alert("����¼�뱣�ѣ�����");
    return false;
  }

  return;
}