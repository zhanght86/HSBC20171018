//�������ƣ�GroupUW.js
//�����ܣ������˹��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����


/*********************************************************************
 *  //���ļ��а����ͻ�����Ҫ����ĺ������¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
var showInfo;
window.onfocus=myonfocus;
var mDebug="0";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var mSwitch = parent.VD.gVSwitch;
var tBonusFlag="0";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
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

/*********************************************************************
 *  �ύ��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  //showSubmitFrame(mDebug);
  document.getElementById("fmQuery").submit(); //�ύ
  alert("submit");
}



/*********************************************************************
 *  �ύ�����,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
    
  }
  else
  {

    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //ִ����һ������
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

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
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
}



/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
   parent.fraMain.rows = "0,0,50,82,*";
  }
 else
 {
   parent.fraMain.rows = "0,0,0,82,*";
 }
}


//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}


/*********************************************************************
 *  ��ѯ���嵥�¸��˵�����Ͷ����Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showApp()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cProposalNo=fmQuery.ProposalNo.value;
  var cInsureNo = fmQuery.InsuredNo.value;
  if(cProposalNo==""||cProposalNo==null|| cInsureNo==""||cInsureNo==null)
  {
  	showInfo.close();
  	alert("��ѡ����˱���,��鿴����Ϣ!");
  	return ;
  	}
  //showModalDialog("./UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  window.open("./UWAppMain.jsp?ProposalNo1="+cProposalNo+"&InsureNo1="+cInsureNo,"window1",sFeatures);
  showInfo.close();
}


/*********************************************************************
 *  ��ѯ���嵥�¸��˵������˱���¼
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showOldUWSub()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cProposalNo=fmQuery.ProposalNo.value;
  if(cProposalNo==""||cProposalNo==null)
  {
  	showInfo.close();
  	alert("��ѡ����˱���,��鿴����Ϣ!");
  	return ;
  	}
  //showModalDialog("./UWSubMain.jsp?ProposalNo1="+cProposalNo,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  window.open("./UWSubMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures);
  showInfo.close();
}


/*********************************************************************
 *  ��ѯ���嵥�¸��˵��˱���¼
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showNewUWSub()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cProposalNo=fmQuery.ProposalNo.value;
  if(cProposalNo==""||cProposalNo==null)
  {
  	showInfo.close();
  	alert("����ѡ����˱���,��鿴����Ϣ!");
  	return ;
  	}
  window.open("./UWErrMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures);
  showInfo.close();
}


/*********************************************************************
 *  ��ѯ���嵥�˱���¼
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showGNewUWSub()
{
  var cGrpContNo=fmQuery.GrpContNo.value;
  if(cGrpContNo==""||cGrpContNo==null)
  {
  	alert("����ѡ��һ������Ͷ����!");
  	return ;
  	}
  window.open("./UWGErrMain.jsp?GrpContNo="+cGrpContNo,"window1",sFeatures);
}



/*********************************************************************
 *  ��ѯ�����µĸ����ڸ��ŵ��ĸ��˱�����ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showPolDetail()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fmQuery.ProposalNo.value;
  if(cProposalNo==null||cProposalNo=="")
  {
  	showInfo.close();
  	alert("����ѡ����˱���,��鿴����Ϣ!");

  }
  else{
  mSwitch.deleteVar( "PolNo" );
  mSwitch.addVar( "PolNo", "", cProposalNo );
  window.open("../appgrp/ProposalMain.jsp?LoadFlag=4","",sFeatures);
  showInfo.close();}
}


/*********************************************************************
 *  ��ѯ�����¸��˱����������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showHealth()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cProposalNo=fmQuery.ProposalNo.value;
  if (cProposalNo != "")
  {
  	window.open("./UWManuHealthMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ����˱���,��鿴����Ϣ!");
  }
}


/*********************************************************************
 *  �����嵥�µĸ��˼ӷѳб�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSpec()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cProposalNo=fmQuery.ProposalNo.value;
  tUWIdea = fmQuery.UWIdea.value;
  if (cProposalNo != "")
  {
  	window.open("./UWGSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag=2&UWIdea="+tUWIdea,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ��һ������Ͷ����!");
  }
}


/*********************************************************************
 *  �����嵥��Լ�б�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showGSpec()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  var cProposalNo=fmQuery.GrpProposalContNo.value;
  var tUWIdea = fmQuery.GUWIdea.value;
  if (cProposalNo != "")
  {
  	window.open("./UWGrpSpecMain.jsp?ProposalNo1="+cProposalNo+"&Flag=1&UWIdea="+tUWIdea,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ��һ�����屣��!");
  }
}


/*********************************************************************
 *  ����������Ͷ�����ĸ���������ϲ�ѯ(���嵥����ֻ����¼�뵽���ո�����)
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showHealthQ()
{
  var cProposalNo=fmQuery.ProposalNo.value;
  var cGrpProposalContNo=fmQuery.GrpProposalContNo.value;
  var cGrpMainProposalNo=fmQuery.GrpMainProposalNo.value;

  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

  if (cProposalNo != "" && cGrpProposalContNo == cGrpMainProposalNo)
  {
  	window.open("./UWManuHealthQMain.jsp?ProposalNo1="+cProposalNo,"window1",sFeatures);

  }
  else
  {

  	alert("����ѡ��һ����������Ͷ�����µĸ���Ͷ����!");
  }
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  //initPolGrid();
  document.getElementById("fmQuery").submit(); //�ύ
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("delete click");
}

//Click�¼����������������Ϣ����ťʱ�����ú���
function showDuty()
{
  //����������Ӧ�Ĵ���
  showModalDialog("./ProposalDuty.jsp",window,"status:no;help:0;close:0;dialogWidth=18cm;dialogHeight=13cm");

}

//Click�¼�����������ݽ�����Ϣ����ťʱ�����ú���
function showFee()
{
  //����������Ӧ�Ĵ���
  showModalDialog("./ProposalFee.jsp",window,"status:no;help:0;close:0;dialogWidth=16cm;dialogHeight=8cm");

}


/*********************************************************************
 *  ��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
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


/*********************************************************************
 *  ��ѯ���嵥����������Ͷ������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function querygrp()
{
	// ��ʼ�����
    initPolBox();
	initGrpGrid();

	// ��дSQL���
	var str = "";
	var PrtNo = fmQuery.PrtNoHide.value;
	var mOperate = fmQuery.Operator.value;

	var strsql = "";
//	strsql = "select LCGrpPol.GrpProposalContNo,LCGrpPol.prtNo,LCGrpPol.GrpName,LCGrpPol.RiskCode,LCGrpPol.RiskVersion,LCGrpPol.ManageCom from LCGrpPol,LCGUWMaster where 1=1 "
//			 	 + " and LCGrpPol.AppFlag='0'"
//				 + " and LCGrpPol.ApproveFlag in('2','9') "
//				 + " and LCGrpPol.UWFlag in ('2','3','5','6','8','7')"
//				 + " and LCGrpPol.contno = '00000000000000000000'"				          //�Զ��˱����˹��˱�
//				 + " and LCGrpPol.PrtNo = '"+PrtNo+"'"
//				 + " and LCGrpPol.GrpPolNo = LCGUWMaster.GrpPolNo"
//				 + " and (LCGUWMaster.appgrade <= (select UWPopedom from LDUser where usercode = '"+mOperate+"') or LCGUWMaster.appgrade is null)"
//				 + " order by LCGrpPol.makedate ,LCGrpPol.maketime" ;
	strsql = "select GrpContNo,PrtNo,GrpName,RiskCode,UWFlag,ManageCom,GrpPolNo from LCGrpPol  where 1=1 and GrpContNo='"+GrpContNo+"'";

	 //execEasyQuery( strSQL );
	//��ѯSQL�����ؽ���ַ���
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
    alert("û�з����������嵥��");
    return "";
    }

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = GrpGrid;

  //����SQL���
  turnPage.strQuerySql = strsql;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

//alert(1)
	var arrSelected = new Array();
	var strSQL = "select GrpContNo,PrtNo,ManageCom,agenttype,AgentCom,AgentCode,AgentGroup,AppntNo,VIPValue,BlacklistFlag,lcgrpcont.GrpName from lcgrpcont,LDGrp where grpcontno='"+GrpContNo+"'"
		+" and lcgrpcont.AppntNo = LDGrp.CustomerNo " ;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
if (turnPage.strQueryResult) {	
	fmQuery.GrpContNo.value = arrSelected[0][0];
	fmQuery.PrtNo.value = arrSelected[0][1];
	fmQuery.ManageCom.value = arrSelected[0][2];
	fmQuery.SaleChnl.value = arrSelected[0][3];
	fmQuery.AgentCom.value = arrSelected[0][4];
	fmQuery.AgentCode.value = arrSelected[0][5];
	fmQuery.AgentGroup.value = arrSelected[0][6];

	fmQuery.AppntNo.value = arrSelected[0][7];
	fmQuery.VIPValue.value = arrSelected[0][8];
	fmQuery.BlacklistFlag.value = arrSelected[0][9];
	fmQuery.AppntName.value = arrSelected[0][10];
}
	initSendTrace();
	
	
  return true;
}
function getcodedata()
{

	var sql="  select code,codename from ldcode where codetype='guwstate'";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    fmQuery.all("GUWState").CodeData=tCodeData;
	
}
function getcodedata1()
{

	var sql="  select code,codename from ldcode where codetype='cooperate'";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
   // fmQuery.all("cooperate").CodeData=tCodeData;
	
}

function getcodedata2()
{

	var sql="  select code,codename from ldcode where codetype='riskgrade'";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    fmQuery.all("riskgrade").CodeData=tCodeData;
	
}

function getcodedata3()
{

	var sql="  select code,codename from ldcode where codetype='appcontract'";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    //fmQuery.all("appcontract").CodeData=tCodeData;
	
}

function getcodedata4()
{

	var sql="  select code,codename from ldcode where codetype='uwupreport'";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    fmQuery.all("uwUpReport").CodeData=tCodeData;
	
}
/********************************************************************
*���������·�֪ͨ�鹤�����ڵ�
*����  ����
*����ֵ����
***********************************************************************
*/

	function makeWorkFlow()
{

	fmQuery.MissionID.value = MissionID;

	fmQuery.SubMissionID.value = SubMissionID;

	fmQuery.action = "./GrpManuUWChk.jsp";
	document.getElementById("fmQuery").submit();

	}


/*********************************************************************
 *  ��ѯ���嵥���Զ��˱�δͨ���ĸ��˵�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryPol()
{
	var GrpProposalContNo = fmQuery.GrpProposalContNo.value;
	var mOperate = fmQuery.Operator.value;
	var strsql = "";

	// ��ʼ�����
	showDiv(divPerson,"false");
	initPolBox();
	//initPolGrid();
	// ��дSQL���

	strsql = "select LCPol.ProposalNo,LCPol.AppntName,LCPol.RiskCode,LCPol.RiskVersion,LCPol.InsuredName,LCPol.ManageCom from LCPol where 1=1 "
			  + "and LCPol.GrpPolNo = '"+GrpProposalContNo+"'"
 	    	  + " and LCPol.AppFlag='0'"
         	  + " and LCPol.UWFlag in ('3','5','6','8','7')"         //�Զ��˱����˹��˱�
			  + " and LCPol.contno = '00000000000000000000'"
			  + " order by LCPol.makedate ";

 	//alert(strsql);
    //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 1, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�˵��¸��˵��˱�����ȫ��ͨ����");
    return "";
  }

  showDiv(divPerson,"true");
  //�����������¼��ֻ�����������Ͷ�����µĸ��˵�
  if(GrpProposalContNo == fmQuery.GrpMainProposalNo.value)
  {
  	showDiv(divBoth,"true");
  	showDiv(divAddFee,"false");
  }
  else
  {
  	showDiv(divBoth,"false");
  	showDiv(divAddFee,"true");
  }
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;

  //����SQL���
  turnPage.strQuerySql     = strsql;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}


/*********************************************************************
 *  ��ѯ������δ�����˵�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getGrpPolGridCho()
{
	fmQuery.PolTypeHide.value = '2';
	document.getElementById("fmQuery").submit();
}



/*********************************************************************
 *  ѡ��Ҫ�˹��˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getPolGridCho()
{
	fmQuery.PolTypeHide.value = '1';
	document.getElementById("fmQuery").submit();
}


/*********************************************************************
 *  ���˵��˱�ȷ��
 *  UWState:(0 δ�˱� 1�ܱ� 2���� 3�����б� 4ͨ�� 5�Զ� 6���ϼ� 7����� 8�˱�֪ͨ�� 9���� a���� b���ռƻ����)
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function manuchk()
{
	var cProposalNo=fmQuery.ProposalNo.value;
	var flag = fmQuery.UWState.value;
	var tUWIdea = fmQuery.UWIdea.value;

	if (flag == "0"||flag == "1"||flag == "4"||flag == "9")
	{
	   //showModalDialog("./UWManuNormMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		fmQuery.action = "./UWManuNormChk.jsp";
		document.getElementById("fmQuery").submit();
	}
	if (flag == "")
	{
		alert("ѡ��˱����ۣ�")
	}

	initPolBox();
	//initPolGrid();
}


/*********************************************************************
 *  ���������˱�ȷ��
 *  (0 δ�˱� 1�ܱ� 2���� 3�����б� 4ͨ�� 5�Զ� 6���ϼ� 7����� 8�˱�֪ͨ�� 9���� a���� b���ռƻ����)
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function gmanuchk(flag)
{
	
	//  var tt = GrpGrid.getSelNo()-1;
	//if (tt <0)
	//{
	//	    
	//      alert("����ȷ���Ƿ����е��ŵ����ֵĽ������£�");
	//      
	//      return;
	//}
	var tUWIdea = fmQuery.UWIdea.value;
  var tUpReport = fmQuery.uwUpReport.value;                //�ϱ���־
	if(tUpReport!="1" && tUpReport!="4"&&tUpReport!="0")
     {
     	
      alert("����ѡ��˱�����!");
    	return ;
     }else{
       if(tUpReport=="4"){
      //У��������߼���ȷ�� �����һ�κ˱� ���������¼�
        var tSql = "select missionprop11 from lwmission where activityid='0000002004' and missionprop1 = '"+fmQuery.GrpContNo.value+"' ";
        var tReportType =easyExecSql(tSql);
        if(tReportType!="1"){
           //�˱�����Ϊ�ϱ����� ���Բ��ܷ����¼�
           alert("�ڴ�֮ǰû���ϱ�,�޷������¼���");
           return false;
          }
        }
     }
    if(tUpReport=="0")
    {
    	if(fmQuery.riskgrade.value=="")
    	{
    		alert("��ѡ����ռ���!");
    		return;
    	}
    	
    	//if(fmQuery.appcontract.value=="")
    	//{
    	//	alert("��ѡ���Ƿ��и���Э��!");
    	//	return;
    	//}
    }
  //  if(tUWIdea == "")
 //    {
      
  //    //alert("����¼��б��˱����!");
  //    return ;
  //  }
	 if(checkQuestion()==false) return false; 
	 if(checkUWGrade()==false) return false;
	 if(flag==1)
  {
  	fmQuery.YesOrNo.value = "Y";
  }
  if(flag ==2)
  {
  	fmQuery.YesOrNo.value = "N";
  }
	var cGrpContNo=fmQuery.GrpContNo.value;
  //var flag = fmQuery.GUWState.value;
  
	//var tUWIdea = fmQuery.GUWIdea.value;
	//alert("flag:"+flag);

	if( cGrpContNo == "" || cGrpContNo == null )
	{
		alert("����ѡ��һ������Ͷ����!");
	    cancelchk();
		return ;
	}

    k++;

    var strsql = "select submissionid from lwmission where "+k+"="+k
                +" and activityid = '0000002010'"
    						+" and missionid = '"+MissionID+"'"
    						;

    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (!turnPage.strQueryResult) {
    	alert("δ�鵽������SubMissionID");
    	 fmQuery.SubMissionID.value = "";
    	return;
    }
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    
    fmQuery.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
    
  //�ж��Ƿ������֪ͨ�飬��������Ƿ��Ѿ��ظ�
    //if(check(cGrpContNo)=="2")
    //{
    //	alert("�������֪ͨ��δ�ظ���");
    //	return;
    //}
    //if(check(cGrpContNo)=="1")
    //{
    //	alert("�������֪ͨ��δ��ӡ��");
    //	return;
    //}
	//�ж��Ƿ���������ֺ����
	  
	  //checkPerFH();
	  if(tBonusFlag=="1")
	  {
	  	alert("��Ͷ�����´��ڸ���ֺ�����֣���������ȷ��ǰ�Ը����ָ���ֺ죡");
	  	return;
	  }

		fmQuery.WorkFlowFlag.value = "0000002010";
		fmQuery.MissionID.value = MissionID;
		
		//fmQuery.SubMissionID.value = SubMissionID;
		//showModalDialog("./UWGrpManuNormMain.jsp?GrpContNo="+cGrpContNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
		 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
		lockScreen('lkscreen');  
		fmQuery.action = "./UWConfirm.jsp";
  	document.getElementById("fmQuery").submit(); //�ύ
	
}

function checkQuestion(){
   var strSql="	select count(1) from lcgrpissuepol	where 1 = 1"
             +" and ProposalGrpContNo = '"+GrpContNo+"' and BackObjType = '1' "
             +" and ReplyMan is null and ReplyResult is null";
   var tCount = easyExecSql(strSql);
   if (tCount>0){
       alert("����δ�ظ��������������[�����¼�����],����������޸ĸڻظ������");
       return false;
   }
   return true;
}

function checkUWGrade(){
  var tMMSql="select max(code),min(code) from ldcode where codetype = 'uwgrppopedom'";
  var arrResult = easyExecSql(tMMSql);
  var tMaxGrade = arrResult[0][0];
  var tMinGrade = arrResult[0][1];
  var tUSerSql = " select uwpopedom from lduwuser where usercode = '"+operator+"' and uwtype ='2'";
  var tUserGrade = easyExecSql(tUSerSql);
  //alert("tMaxGrade:"+tMaxGrade+"tMinGrade:"+tMinGrade+"tUserGrade:"+tUserGrade);
  var tUWUpReport = fmQuery.uwUpReport.value;
  if(tUWUpReport=="1"){
     //�ϱ�
     if(tUserGrade==tMaxGrade){
        if(!confirm("���Ѿ�����߼���˱�ʦ���Ƿ�����ϱ���")) return false;
     }
  }else if (tUWUpReport=="4"){
     if(tUserGrade==tMinGrade){
        if(!confirm("������ͼ���˱�ʦ���Ƿ���������¼���")) return false;
     }
  }
  return true;
}
/*********************************************************************
 *  У�����֪ͨ���Ƿ�ظ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function check(grpcontno)
{
	var strSql = "select distinct 1 From loprtmanager where otherno='"+grpcontno+"'";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ;
	var notice = easyExecSql(strSql);//�ж��Ƿ����֪ͨ��
	if(notice==null)
	{
		return 0;//û�з���֪ͨ��
	}
	//alert(1);
	//alert(notice);
  strSql = "select distinct 1 From loprtmanager where otherno='"+grpcontno+"' and stateflag='0'";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ;
	var num = easyExecSql(strSql);//�Ѿ�����֪ͨ�鵫û�д�ӡ
	if(num!=null)
	{
		return 1;
	}
	//alert(2);
	//alert(num);
	strSql = "select count(*) From loprtmanager where StandbyFlag3='"+grpcontno+"'";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ;
	var printno = easyExecSql(strSql);//�Ѿ���ӡ
	strSql = "select count(*) From lcpenoticeresult where grpcontno='"+grpcontno+"'";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      ;
	var result = easyExecSql(strSql);//�Ѿ��ظ�
	//alert(3);
	//alert(result);
	//alert(printno);
	if(result[0][0]==printno[0][0])
	{
		return 0;//û��ȫ���ظ�
	}else{
	//alert(4);
	//alert(result);
	//alert(printno);
	return 2;//����
}
}


/*********************************************************************
 *  ���������¼��(Ŀǰֻ��Ԥ��,������ʵ��,ֻ��δ���û�������δ��ʾ¼�밴ť)
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function QuestInput()
{
	var cProposalNo = fmQuery.ProposalNo.value;

	if(cProposalNo == "")
	{
		alert("����ѡ��һ�����˵���¼��");
	}
	else
	{
		window.open("./GrpQuestInputMain.jsp?GrpProposalContNo="+cProposalNo+"&ProposalNo="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);
	}

}


/*********************************************************************
 *  ���������¼��(�����µ������¼��ԭ����:���˵������ȫ�����������嵥���������嵥��,��������Ӧ�ĸ�����(���ǵ����嵥����ҵ���ص�����))
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function GrpQuestInput()
{
	var cGrpProposalContNo = fmQuery.GrpContNo.value;  //���屣������	
	if(cGrpProposalContNo==""||cGrpProposalContNo==null)
	{
  		alert("����ѡ��һ������Ͷ����!");
  		return ;
         }
	showInfo=window.open("../appgrp/GrpQuestInputMain.jsp?GrpContNo="+cGrpProposalContNo+"&Flag=9","",sFeatures);
}


/*********************************************************************
 *  �����������ѯ(Ŀǰֻ��Ԥ��,������ʵ��,ֻ��δ���û�������δ��ʾ��ѯ��ť)
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function QuestQuery()
{
	var cProposalNo = fmQuery.ProposalNo.value;//����Ͷ������
	if(cProposalNo==""||cProposalNo==null)
	{
  		alert("����ѡ��һ������Ͷ����!");
  		return ;
    }
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./QuestQueryMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cflag,"window1",sFeatures);
}


/*********************************************************************
 *  �ŵ������ѯ(�����µ����������ԭ����:���˵������ȫ�����������嵥���������嵥��,��������Ӧ�ĸ�����(���ǵ����嵥����ҵ���ص�����))
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function GrpQuestQuery()
{
	
	var cGrpContNo = fmQuery.GrpContNo.value;  //�ŵ�Ͷ��������
	//alert(cGrpContNo);
	if(cGrpContNo==""||cGrpContNo==null)
	{
  		alert("����ѡ��һ����������Ͷ����!");
  		return ;
    }
 
	window.open("./GrpQuestQueryMain.jsp?ProposalNo1="+cGrpContNo+"&Flag="+cflag,"",sFeatures);
}


/*********************************************************************
 *  �����嵥���ӷѺ˱�֪ͨ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 /*
function SendNotice()
{
  var cGrpProposalContNo = fmQuery.GrpProposalContNo.value;
  fmQuery.GUWState.value = "8";
  if (cGrpProposalContNo != "" && cGrpProposalContNo == fmQuery.GrpMainProposalNo.value)
  {
	gmanuchk();
	cancelchk();
  }
  else
  {
  	alert("����ѡ�����嵥�µ�����Ͷ����!");
  	cancelchk();
  }
}


*/
/*********************************************************************
 *  �����嵥���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function SendQuest()
{
  var cGrpProposalContNo = fmQuery.GrpMainProposalNo.value;
  fmQuery.GUWState.value = "7";
  if (cGrpProposalContNo != "" && cGrpProposalContNo==fmQuery.GrpMainProposalNo.value)
  {
	gmanuchk();
	//cancelchk();
  }
  else
  {
  	alert("����ѡ�����嵥�µ�����Ͷ����!");
  	cancelchk();
  }
}


/*********************************************************************
 *  ���ȡ����ť,��ʼ����������ر���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function cancelchk()
{
	fmQuery.GUWState.value = "";
	fmQuery.GUWIdea.value = "";
}


/*********************************************************************
 *  ���ص��Զ��˱���ѯ������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function GoBack()
 {
 	location.href  = "./ManuUWAll.jsp?type=2";
 	
 }
function temp()
{
	alert("�˹�����ȱ��");
}
/*********************************************************************
 *  ���ɨ�����ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ScanQuery2()
{
	var arrReturn = new Array();

	var prtNo=fmQuery.PrtNo.value;
	
	if(prtNo==""||prtNo==null)
	{
	  	alert("����ѡ��һ������Ͷ����!");
  		return ;
  	}
  	
  	
  	//var tsql="select subtype From es_doc_main where doccode='"+prtNo+"' order by subtype";
 	//			var crr = easyExecSql(tsql);
 	//			var tsubtype="";
 	//			//alert(crr);
 	//			if(crr!=null)
 	//			{
 	//			if(crr[0][0]=="1000")
 	//			{
 	//			 tsubtype="TB1002";
 	//			}else{
 	//			 tsubtype="TB1003";
 	//			}
 	//		}else{
 	//		 tsubtype="TB1002";
 	//	}
 	var strSql2="select missionprop5 from lbmission where activityid='0000002099' and missionprop1='"+prtNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
	SubType = crrResult[0][0];
	}
	window.open("../sys/ProposalEasyScan.jsp?BussType=TB&BussNoType=12&SubType="+SubType+"&prtNo="+prtNo,"", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
}

function GrpContQuery(cGrpContNo){
	
	window.open("./GroupContMain.jsp?GrpContNo=" + cGrpContNo,"GroupContQuery",sFeatures);
}
/*********************************************************************
 *  ���������ͬ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function GrpContReasonQuery(cGrpContNo){
	 
	window.open("./GroupUWReason.jsp?GrpContNo=" + cGrpContNo,"",sFeatures); 
}

function showGrpCont()
{
     var cGrpContNo=fmQuery.GrpContNo.value;
    //var newWindow=window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=16&polNo="+cGrpContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1"); 
    window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=16&prtNo="+cGrpContNo+"&polNo="+cGrpContNo+"&GrpContNo="+cGrpContNo,"window1",sFeatures);
}


//�ŵ�����ѯ����Ϣ
 function showAskApp()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cGrpContNo=fmQuery.GrpContNo.value;
  cAppntNo = fmQuery.AppntNo.value;
  
  if (cGrpContNo != "")
  {
  	
  	window.open("../askapp/AskUWAppMain.jsp?GrpContNo1="+cGrpContNo+"&AppntNo1="+cAppntNo,"window2",sFeatures);
  	showInfo.close();
  }
  
}             

/*********************************************************************
 *  �������֪ͨ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function checkBody()
{
	  cGrpContNo=fmQuery.GrpContNo.value;
	
	  
	  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
      //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	  	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	  	var iWidth=550;      //�������ڵĿ��; 
	  	var iHeight=250;     //�������ڵĸ߶�; 
	  	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	  	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	  	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	  	showInfo.focus();
	  	//[end]
	 
	  if (cGrpContNo != "" )
	  {
	  	fmQuery.action = "./UWManuSendBodyCheckChk.jsp";
	  	
	    document.getElementById("fmQuery").submit();
	   
	  }
	  else
	  {
  	   showInfo.close();
  	   alert("����ѡ�񱣵�!");
      }
      
  	
}
 
/*********************************************************************
 *  ����֪ͨ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 
function RReport()
{
	 cGrpContNo=fmQuery.GrpContNo.value;
	
	  
	  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
      //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	  	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	  	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	  	var iWidth=550;      //�������ڵĿ��; 
	  	var iHeight=250;     //�������ڵĸ߶�; 
	  	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	  	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	  	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	  	showInfo.focus();
	  	//[end]
	 
	  if (cGrpContNo != "" )
	  {
	  	fmQuery.action = "./UWManuSendRReportChk.jsp?GrpContNo="+cGrpContNo;
	  	
	    document.getElementById("fmQuery").submit();
	   
	  }
	  else
	  {
  	   showInfo.close();
  	   alert("����ѡ�񱣵�!");
      }
  	
}


function showRReport()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  
  var cContNo = fmQuery.GrpContNo.value;
  

  var cPrtNo = fmQuery.PrtNo.value;
 
  if (cContNo != "")
  {
  	
  	window.open("./GrpUWRReportMain.jsp?ContNo1="+cContNo+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();
  }
      
 
}



/*********************************************************************
 *  ����������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function showHistoryCont()
{
		cGrpContNo=fmQuery.GrpContNo.value;
		window.open("../uwgrp/GrpUWHistoryContMain.jsp?GrpContNo="+cGrpContNo,"window2",sFeatures);
}
/*********************************************************************
 *  �б��ƻ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function showChangePlan()
{
  var cpolNo = fmQuery.GrpContNo.value;
  //var cType = "ChangePlan";
  

  window.open("../appgrp/GroupPolApproveInfo.jsp?LoadFlag=23&polNo="+cpolNo, "",sFeatures);  	  
}


/*********************************************************************
 *  ���ռƻ��������¼����ʾ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function showChangeResultView()
{
	fmQuery.ChangeIdea.value = "";
	//fm.PolNoHide.value = fm.ProposalNo.value;  //��������
	//divUWResult.style.display= "none";
	divChangeResult.style.display= "";	
}


//��ʾ���ռƻ��������
function showChangeResult()
{

	var cContNo = fmQuery.GrpContNo.value;
	
	cChangeResult = fmQuery.ChangeIdea.value;
	
	if (cChangeResult == "")
	{
		alert("û��¼�����!");
	}
	else
	{
		
       
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
 
  	fmQuery.action = "./GrpUWChangPlanChk.jsp";
  	document.getElementById("fmQuery").submit(); //�ύ
  }	

}


//���ر��ռƻ��������<img src="">
function HideChangeResult()
{
	//divUWResult.style.display= "";
	divChangeResult.style.display= "none";
	//fmQuery.uwstate.value = "";
	//fmQuery.UWIdea.value = "";
	//fmQuery.UWPopedomCode.value = "";			
}


//�����ڽ���֪ͨ��
function SendFirstNotice()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fmQuery.GrpContNo.value;
 
  cOtherNoType="01"; //������������
  cCode="57";        //��������
  
  if (cProposalNo != "")
  {
  	showModalDialog("./GrpUWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}



//���˱�����֪ͨ��
function GrpSendNotice()
{
	
	  var strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 + " and LWMission.MissionProp1 = '" + PrtNo +"'"
				
				 + " and LWMission.ActivityID = '0000002101'"
				 + " and LWMission.ProcessID = '0000000004'"
				 + " and LWMission.MissionID = '" +MissionID +"'";
		
  
  	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);   
  	
    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) {
    	
     	alert("���������µĺ˱�����֪ͨ��,ԭ�������:1.�ѷ��˱�֪ͨ��,��δ��ӡ.");
        fmQuery.SubMissionID.value = "";
        return ;
    }
   
    var arrSelected = new Array();
    arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    fmQuery.SubMissionID.value = arrSelected[0][0];
  
    
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  cProposalNo=fmQuery.GrpContNo.value;
 
  if (cProposalNo != "")
  {
  	fmQuery.action = "./UWSendNoticeChk.jsp";
  	document.getElementById("fmQuery").submit();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
		  
}


//�����¼��
function QuestInput()
{
	cContNo = fmQuery.ContNo.value;  //��������
	
	var strSql = "select * from LCcUWMaster where ContNo='" + cContNo + "' and ChangePolFlag ='1'";
    //alert(strSql);
    var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) {
      var tInfo = "�б��ƻ����δ�ظ�,ȷ��Ҫ¼���µ������?";
   if(!window.confirm( tInfo )){ 
          return;
        }
      }    
	window.open("./QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window1",sFeatures);

}

/*********************************************************************
 *  ��ʼ���Ƿ��ϱ��˱�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initSendTrace()
{

	var tSql = "";

	tSql = "select sendflag,uwflag,uwidea from lcuwsendtrace where 1=1 "
					+ " and otherno = '"+GrpContNo+"'"
					+ " and othernotype = '2'"
					+ " and uwno in (select max(uwno) from lcuwsendtrace where otherno = '"+GrpContNo+"')" 
					;
					
					//alert(tSql);
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {
		fmQuery.SendFlag.value =arrSelected[0][0];
		fmQuery.SendUWFlag.value =arrSelected[0][1];
		fmQuery.SendUWIdea.value =arrSelected[0][2];

		DivLCSendTrance.style.display="";
	}

	if(fmQuery.SendFlag.value == '0')
	{
		divUWSave.style.display = "";
		divUWAgree.style.display = "none";
	}	
	else if(fmQuery.SendFlag.value == '1')
	{
		//divUWAgree.style.display = "";
		//divUWSave.style.display = "none";
		tSql = "select passflag,uwidea from lcgcuwmaster where 1=1 "
					+ " and grpcontno = '"+GrpContNo+"'" 
					;
		turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		
		fmQuery.GUWState.value = arrSelected[0][0];
		fmQuery.GUWIdea.value = arrSelected[0][1];
	}
}
/*
  �ֱ���Ŧ
*/

function tt(cGrpContNo)
{

	showInfo=window.open("./distributeMain.jsp?GrpContNo=" + cGrpContNo,"Groupdistribute",sFeatures);
}


function getfee(parm1,parm2)
{
	var grppolno=fmQuery.all(parm1).all('GrpGrid7').value;
         var strSQL="select  Case When sum(prem) Is Null Then 0 Else sum(prem) End,Case When  round(sum(prem/floatrate),0) Is Null Then 0 Else round(sum(prem/floatrate),2) End,Case When round(sum(prem)/sum(prem/floatrate),4) Is Null Then 0 Else round(sum(prem)/sum(prem/floatrate),4) End , max(currname) from lcduty,ldcurrency "
         + "where polno in (select polno from lcpol where grppolno='"+grppolno+"') and currcode = (select currency from lcpol where 1 = 1 and polno = lcduty.polno)  ";	
  	turnPage.queryModal(strSQL,GrpPolFeeGrid);
  	
  var tSql = "select passflag ,uwidea from lcguwmaster where grppolno = '"+grppolno+"'";
  	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		
		fmQuery.GUWState.value = arrSelected[0][0];
		fmQuery.GUWIdea.value = arrSelected[0][1];
  
}


function showNotePad()
{

//	var selno = SelfGrpGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("��ѡ��һ������");
//	      return;
//	}
	
//var MissionID = SelfGrpGrid.getRowColData(selno, 4);
  //var MissionID = MissionID;
 
  
//var SubMissionID = SelfGrpGrid.getRowColData(selno, 5);
 // var SubMissionID = fm.all.SubMissionID.value;
 
  
//	var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
//	var ActivityID = fm.all.ActivityID.value;
	
	
//	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
//  var PrtNo = fm.all.PrtNo2.value;

  
//	var NoType = fm.all.NoType.value;

	if(PrtNo == null || PrtNo == "")
	{
		alert("ӡˢ��Ϊ�գ�");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uwgrp/WorkFlowNotePadFrame.jsp?Interface=../uwgrp/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}

/*********************************************************************
 *  ��ʼ�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInfo1()
{
		showOneCodeName('comcode','ManageCom','ManageComName');
		showOneCodeName('agenttype','SaleChnl','SaleChnlName');
		showOneCodeName('comcode','AgentCom','AgentComName');
		showOneCodeName('vipvalue','VIPValue','VIPValueName');
		showOneCodeName('blacklistflag','BlacklistFlag','BlacklistFlagName');
		showOneCodeName('uwupreport','SendFlag','SendFlagName');
		showOneCodeName('contuwstate','SendUWFlag','SendUWFlagName');
		//alert(GrpContNo);
		var arrQueryResult = easyExecSql("select ConferNo,SignReportNo,AgentConferNo from lcgrpcont where grpcontno='" + GrpContNo + "'", 1, 0);
		if(arrQueryResult!=null)
		{
			//fmQuery.cooperate.value=arrQueryResult[0][0];
			fmQuery.riskgrade.value=arrQueryResult[0][1];
			//fmQuery.appcontract.value=arrQueryResult[0][2];
			
		}

}


//����Ͷ�����⸶�����ѯ
function UWPastInfo(tGrpContNo){
     window.open("./UWPastInfoMain.jsp?GrpContNo="+tGrpContNo,"window1",sFeatures);
}
/*********************************************************************
 *  �Լ��屣�������½���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function grpPolCommit()
 {
 	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
		    showInfo.close();
	      alert("��ѡ��һ������");
	      
	      return;
	}
	
  cProposalNo=GrpGrid.getRowColData(selno,7);
  fmQuery.temriskcode.value=GrpGrid.getRowColData(selno,4);
  fmQuery.GrpPolNo.value = cProposalNo;
  
  var flag = fmQuery.GUWState.value;
	var tUWIdea = fmQuery.GUWIdea.value;
	if(tUWIdea=="")
	{
		showInfo.close();
		alert("�������������ֵ��˱����!");
		return;
	}
  
  	if (flag == null || flag == "")
	{
		alert("����ѡ��˱�����!");
	    cancelchk();
		return ;
	}
	

  if (cProposalNo != "")
  {
  	fmQuery.action = "./GrpPolCommitChk.jsp";
  	document.getElementById("fmQuery").submit();
  }
 
}
function queryProposal(){
	  
	var cContNo = fmQuery.GrpContNo.value;
	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
	var arr=easyExecSql(strsql);
	
	if(arr!=null){
		fmQuery.AppntNo.value=arr[0][0];
	}
	//alert(fmQuery.AppntNo.value);
    window.open("./GroupProposalQueryMain.jsp?AppntNo="+fmQuery.AppntNo.value,"window1",sFeatures);

}
function queryNotProposal(){
  
  var cContNo = fmQuery.GrpContNo.value;
	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
	var arr=easyExecSql(strsql);
	if(arr!=null){
		fmQuery.AppntNo.value=arr[0][0];
	}
	//alert(fmQuery.AppntNo.value);

	window.open("./GroupNotProposalQueryMain.jsp?AppntNo="+fmQuery.AppntNo.value,"window1",sFeatures);

}

function grpAddFee()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var tGrpContNo = fmQuery.GrpContNo.value;
 
  //var tContNo = fmQuery.ContNo.value;
  //var tPolNo = fmQuery.PolNo.value;
  if (tGrpContNo != "")
  {
	window.open("./GrpUWAddFeeMain.jsp?GrpContNo=" + tGrpContNo ,"window1",sFeatures);
  }
  else
  {
  alert("����ѡ�񱣵�!");
  }
}
/*********************************************************************
 *  ��ѯ���屣����������
 *  ����  ��  GrpContNo
 *  ����ֵ��  ��
 *********************************************************************
 */
 function showReport()
 {
 	var tGrpContNo = fmQuery.GrpContNo.value;
 	if (tGrpContNo != "")
  {
	window.open("./GrpRReportResultMain1.jsp?GrpContNo=" + tGrpContNo ,"window1",sFeatures);
  }
  else
  {
  alert("����ѡ�񱣵�!");
  }
 }
 /*********************************************************************
 *  ��ѯ���������������
 *  ����  ��  GrpContNo
 *  ����ֵ��  ��
 *********************************************************************
 */
 function showPE()
 {
 	var tGrpContNo = fmQuery.GrpContNo.value;
 	if (tGrpContNo != "")
  {
	window.open("./GrpPEResultMain.jsp?GrpContNo=" + tGrpContNo ,"window1",sFeatures);
  }
  else
  {
  alert("����ѡ�񱣵�!");
  }
 }
 
 //����Ҫ�ط��ʵ���
 
 function grpRiskElement()
 {
 	//add by yaory
 	var selno = GrpGrid.getSelNo()-1;
 	if (selno <0)
    {
	      alert("��ѡ�������ֵ�");
	      return;
	  }
 	var tGrpContNo = fmQuery.GrpContNo.value;
 	var riskcode=GrpGrid.getRowColData(selno, 4);
 	if (tGrpContNo != "")
  {
	window.open("./GrpRiskElenemtMain.jsp?GrpContNo="+tGrpContNo+"&riskcode="+riskcode  ,"window1",sFeatures);
  }
  else
  {
  alert("����ѡ�񱣵�!");
  }
 }
 
 /*********************************************************************
 *  �ŵ��������ʵ���
 *  ����  ��  GrpPolNo
 *  ����ֵ��  ��
 *********************************************************************
 */
 function grpFloatRate()
 {
   	var selno = GrpGrid.getSelNo()-1;
  	if (selno <0)
    {
	      alert("��ѡ�������ֵ�");
	      return;
	  }
	
   var tGrpPolNo=GrpGrid.getRowColData(selno,7);
    	if (tGrpPolNo != "")
  {
	window.open("./GrpFloatRateMain.jsp?GrpPolNo=" + tGrpPolNo ,"window1",sFeatures);
  }
   
 }
 /*********************************************************************
 *  �ŵ��˱��������¼�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function GrpQuestInputConfirm()
{
	var StrSQL = "select submissionid from lwmission where 1=1 "
								+ " and missionid = '"+ MissionID +"'"
								+ " and activityid = '0000002007'"
								;
	turnPage.strQueryResult  = easyQueryVer3(StrSQL, 1, 0, 1);
	  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("�������ڵ�����ʧ�ܣ�");
    return "";
  }
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
	
	fmQuery.WorkFlowFlag.value = "0000002007";
	fmQuery.SubMissionID.value = arrSelected[0][0];
	
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	lockScreen('lkscreen');  
	fmQuery.action = "./GrpQuestInputConfirm.jsp";
  document.getElementById("fmQuery").submit(); //�ύ
}
//������¼��
function pp(cGrpContNo)
{
	
	showInfo=window.open("./FeeMain.jsp?GrpContNo=" + cGrpContNo,"Feedistribute",sFeatures);
}
//���ϼƻ���ѯ
function showContPlan()
{
	//alert(GrpContNo);
	window.open("../appgrp/ContPlan.jsp?GrpContNo="+GrpContNo+"&LoadFlag=11","",sFeatures);
}

function showManaFee()
{
	showInfo=window.open("./ContFee.jsp?GrpContNo="+GrpContNo+"&LoadFlag=11","",sFeatures);
}
function showBonus()
{
	var AppntNo=fmQuery.AppntNo.value;
	showInfo=window.open("./GrpBonus.jsp?GrpContNo="+GrpContNo+"&LoadFlag="+AppntNo+"","",sFeatures);
}

function showSpecInfo()
{
	window.open("./SpecInp.jsp?GrpContNo="+GrpContNo+"&LoadFlag=11","",sFeatures);
}

function ManageFeeCal()
{
	
var strUrl = "../appgrp/CalMangeFeeMain.jsp?grpcontno="+ fmQuery.GrpContNo.value
   showInfo=window.open(strUrl,"����Ѽ���","width=600, height=250, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=yes");
}


function showRealFee()
{
	window.open("./RealFeeInp.jsp?GrpContNo="+GrpContNo+"","����ѽ��","width=300, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=yes");
}
//����и���ֺ�����֣���ʾ������ֺ촦��
function fenHong()
{
   var arr=new Array();
   strSQL="select riskcode from lcgrppol where grpcontno='"+GrpContNo+"' and standbyflag1='0'";
   arr = easyExecSql( strSQL );
   if(arr)
   {
  	   alert("��Ͷ�����´��ڸ���ֺ�����֣���������ȷ��ʱ�Ը����ָ���ֺ죡");

 //	     fmQuery.scan.disabled=true;
 // 	   fmQuery.autoCheck.disabled=true;
 // 	   fmQuery.grpDetails.disabled=true;
 // 	   fmQuery.perAutoRea.disabled=true;
 // 	   fmQuery.perAutoCheck.disabled=true;
 // 	   fmQuery.grpPolReason.disabled=true;
 // 	   fmQuery.perAutoInfo.disabled=true;
 // 	   
 // 	   fmQuery.PE.disabled=true;
 // 	   fmQuery.RealFee.disabled=true;
 // 	   fmQuery.grpManageFee.disabled=true;
 // 	   fmQuery.SpecInfo.disabled=true;
 // 	   
 // 	   fmQuery.grpQuestionInput.disabled=true;
 // 	   fmQuery.questInputConfirm.disabled=true;
 // 	   fmQuery.distriContract.disabled=true;
 // 	   fmQuery.FeeContract.disabled=true;
 // 	   fmQuery.manageFeeQuery.disabled=true;
 // 	  
 // 	   fmQuery.ok.disabled=true;
 // 	   fmQuery.isOk.disabled=true;
 // 	   fmQuery.isNotOk.disabled=true;
 // 	   fmQuery.grpPolUtility.disabled=true;     	   
   }
   
}

//У���Ƿ���������ֺ�
function checkPerFH()
{
	 
	 var arr=new Array();
	 strSQL="select riskcode from lcgrppol where grpcontno='"+GrpContNo+"' and standbyflag1='0'";
   arr = easyExecSql( strSQL );
   if(arr!=null)
   {
  	   strSQL="select riskcode from LCGrpBonus where grpcontno='"+GrpContNo+"' and standbyflag1='0'";
       arr = easyExecSql( strSQL );
       var tflag=easyExecSql( "select * from lcgrpbonussub where grpcontno='"+GrpContNo+"'" );

       if(arr==null && tflag==null)
       {
  	     tBonusFlag="1";
  	   }else
  	   {
  	   	 tBonusFlag="0";
  	   } 	   
   }  
}
//�Ƿ���ʾ����ֺ찴ť
function checkPerFHButton()
{
	 
	 var arr=new Array();
	 strSQL="select riskcode from lcgrppol where grpcontno='"+GrpContNo+"' and standbyflag1='0'";
   arr = easyExecSql( strSQL );
   if(arr!=null)
   {	   
  	  //fmQuery.perFH.disabled=false;
  	  //fmQuery.FeeContract.disabled=false; 
  	  //fmQuery.distriContract.disabled=false;    	   
  	  //fmQuery.SpecInfo.disabled=false;    	   
  	  fmQuery.perAutoRea.disabled=false;    	   
   }else
   	{
   	  //fmQuery.perFH.disabled=true;     	   	
  	  //fmQuery.FeeContract.disabled=true;     	   
  	  //fmQuery.distriContract.disabled=true;    	   
  	  //fmQuery.SpecInfo.disabled=true;    	
  	  //fmQuery.perAutoRea1.disabled=true;    	   
   	}  
}

//ǿ��ת���ٱ��չ�����    add by sujd 2007-08-17
function ReLife(cGrpContNo)
{
	//��ȷ�ϰ�ťȡ��
	fmQuery.ok.disabled="true";
	fmQuery.grpPolUtility.disabled="true";

  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	 //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	fmQuery.action = "./RelifeSubmitSave.jsp?GrpContNo="+cGrpContNo;
  document.getElementById("fmQuery").submit(); //�ύ
}

//�����˹��˱����水ť��������ʾ
function ctrlButtonDisabled(tContNo,LoadFlag){

 //�޸�Ϊ��Function��ֱ��ִ�к��������ؽ������(��ά����:��һ��Ϊ��ť���ƣ��ڶ���Ϊdisabled����)��
    try
    {
	    var tarrStr = easyExecSql("select ctrlGrpSigUWButton('"+tContNo+"','"+LoadFlag+"') from dual");
	  // prompt('',tarrStr);
	    if(tarrStr!=null)
	    {
	    	for(var i=0; i<tarrStr.length; i++)
	    	{
	    		//alert(tarrStr[i][0]+":"+tarrStr[i][1]);
	    		try {
	    			if(tarrStr[i][1]==0)
	    			{
	    			//	prompt('',"fm.all('"+tarrStr[i][0]+"').disabled=true");
	    				eval("fmQuery.all('"+tarrStr[i][0]+"').disabled=true ");
	    				
	    			}
	    			else
	    			{  				
	    		//		prompt('111',"fm.all('"+tarrStr[i][0]+"').disabled=true");
	    				eval("fmQuery.all('"+tarrStr[i][0]+"').disabled=false");
	    			}
	    		} 
	    		catch(e) {
	    			//alert(e);
	    			}
	    		 
	    	}
	    }
    }
    catch(ex)
    {
    
    }     	
}