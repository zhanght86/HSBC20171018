//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var mDebug="0";
var mOperate="";
var showInfo;
window.onfocus=myonfocus;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if (verifyInput())
	{
//	if(dateDiff(fm.StartDay.value,fm.EndDay.value,"D")!=1){
//		alert("��ֹʱ�䲻ͬ����ȷ�ϡ�");
//		return;
//	}
	  
	  var i = 0;
	  var showStr="����׼����ӡ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //fm.hideOperate.value=mOperate;
	  //if (fm.hideOperate.value=="")
	  //{
	  //  alert("�����������ݶ�ʧ��");
	  //}
	  //showSubmitFrame(mDebug);
	  document.getElementById("fm").submit(); //�ύ
	  setFormAllDisabled();
	  setTimeout(function(){afterSubmit("","");},3000)
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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  setFormAllEnabled();
}
//���֧��
function PrintPKZC(){
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="YingFu"; //�����շ�
    fm.Opt.value="PKZC";//�����ı�־
    submitForm();
}
//��ȫӦ��
function PrintBQYF(){
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="YingFu"; //�����շ�
    fm.Opt.value="BQYF";//�����ı�־
    submitForm();
}
//��ȡ����
function PrintLQJF(){
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="YingFu"; //�����շ�
 	fm.Opt.value="LQJF";//�����ı�־
	submitForm();
}
//����Ӧ��
function PrintQTYF(){
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="YingFu"; //�����շ�
	fm.Opt.value="QTYF";//�����ı�־
	submitForm();
}

//�շѴ�ӡPay
function ORPrint()
{
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="PRINTPAY"; //�����շ�
	fm.Opt.value="Pay";//�շѵ�����������
	submitForm();
}
//��ӡ�˱��ս��嵥
function HeBao_Print()
{
  fm.action = "../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value = "PRINTPAY";
  fm.Opt.value="HeBao";//�����ı�־
  submitForm();
}
/*******************************************************************************
 * name:YSPrint
 * type:function
 * date:2003-05-28
 * author:Liuyansong
 * function :Ԥ���ս��ӡ
 */
function YSPrint()
{
  fm.action = "../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value = "PRINTPAY";
  fm.Opt.value = "YSPay";//�շ�������Ԥ��
  submitForm();
}
//���Ѵ�ӡGet
function OMPrint()
{
  fm.action="../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value="PRINTGET";//������
  fm.Opt.value="Get";
  submitForm();
}
//ʵ���սᵥ
function PrintYWSF()
{
  fm.action = "../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value = "PRINTGET";
  fm.Opt.value="YWSF";//�����ı�־
  submitForm();
}
//���������սᵥ
function PrintBQSF()
{
  fm.action = "../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value = "PRINTPAY";
  fm.Opt.value="BQSF";//�����ı�־
  submitForm();
}

//*********************************************************************

function PayModePrint()
{
  fm.action = "../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value = "PRINTPAY";
  fm.Opt.value="PayMode";//�����ı�־
  submitForm();
}

function GetModePrint()
{
  fm.action = "../f1print/FinDayCheckSave.jsp";
  fm.target="../f1print";
  fm.fmtransact.value = "PRINTGET";
  fm.Opt.value="GetMode";//�����ı�־
  submitForm();
}

//����������սᵥ
function PrintGLFY(){
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="PRINTPAY"; //�����շ�
	fm.Opt.value="GLFY";//�����ı�־
	submitForm();
}
//������
function AirPrint()
{
	fm.action="../f1print/FinDayCheckSave.jsp";
	fm.target="../f1print";
	fm.fmtransact.value="PRINTPAY"; //�����շ�
		fm.Opt.value="AirPay";//�շѵ������Ǻ�����
	
	submitForm();
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

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ��ɾ������
  if (confirm("��ȷʵ��ɾ���ü�¼��?"))
  {
    mOperate="DELETE||MAIN";
    submitForm();
  }
  else
  {
    mOperate="";
    alert("��ȡ����ɾ��������");
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

