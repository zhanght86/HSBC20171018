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
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�
var mSwitch = parent.VD.gVSwitch;
var isClosed = false;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";


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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //showSubmitFrame(mDebug);
  fmQuery.submit(); //�ύ
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
    else
    {
        //ִ����һ������
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        
        fmQuery.all('GrpContNo').value = GrpContNo;
        	
        initGrpGrid();    
    	initGrpPolFeeGrid();    
        querygrp();
        makeWorkFlow();
        if(isClosed == true)
        {
            top.close();
        }
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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  cProposalNo=fmQuery.ProposalNo.value;
  if(cProposalNo==null||cProposalNo=="")
  {
  	showInfo.close();
  	alert("����ѡ����˱���,��鿴����Ϣ!");

  }
  else{
  mSwitch.deleteVar( "PolNo" );
  mSwitch.addVar( "PolNo", "", cProposalNo );
  window.open("../app/ProposalMain.jsp?LoadFlag=4","",sFeatures);
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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cProposalNo=fmQuery.ProposalNo.value;
  tUWIdea = fmQuery.all('UWIdea').value;
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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  //initPolGrid();
  fmQuery.submit(); //�ύ
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
  //showModalDialog("./ProposalDuty.jsp",window,"status:no;help:0;close:0;dialogWidth=18cm;dialogHeight=13cm");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open ("./ProposalDuty.jsp",name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

}

//Click�¼�����������ݽ�����Ϣ����ťʱ�����ú���
function showFee()
{
  //����������Ӧ�Ĵ���
  //showModalDialog("./ProposalFee.jsp",window,"status:no;help:0;close:0;dialogWidth=16cm;dialogHeight=8cm");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open ("./ProposalFee.jsp",name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

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
	var PrtNo = fmQuery.all('PrtNoHide').value;
	var mOperate = fmQuery.all('Operator').value;

	var strsql = "";

		var sqlid1="GroupUWSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(GrpContNo);//ָ������Ĳ���
		mySql1.addSubPara(GrpContNo);//ָ������Ĳ���
	    strsql=mySql1.getString();	


//	strsql = "select GrpContNo,PrtNo,GrpName,RiskCode,"
//	       + "(select riskshortname from lmrisk where riskcode=a.riskcode),"
//	       + "payintv,peoples2,amnt,cvalidate," 
//	       + "(select max(enddate) from lcpol where grpcontno='" + GrpContNo + "' and grppolno=a.grppolno),"
//	       + "GrpPolNo,ManageCom,UWFlag from LCGrpPol a "
//	       + "where 1=1 and GrpContNo='"+GrpContNo+"'";

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
	
		var sqlid2="GroupUWSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(GrpContNo);//ָ������Ĳ���
	    var strSQL=mySql2.getString();	
	
//	var strSQL = "select GrpContNo,PrtNo,ManageCom,SaleChnl,AgentCom,AgentCode,AgentGroup,AppntNo,VIPValue,BlacklistFlag,lcgrpcont.GrpName from lcgrpcont,LDGrp where grpcontno='"+GrpContNo+"'"
//		+" and lcgrpcont.AppntNo = LDGrp.CustomerNo " ;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
if (turnPage.strQueryResult) {	
	fmQuery.all('GrpContNo').value = arrSelected[0][0];
	fmQuery.all('PrtNo').value = arrSelected[0][1];
	fmQuery.all('ManageCom').value = arrSelected[0][2];
	fmQuery.all('SaleChnl').value = arrSelected[0][3];
	fmQuery.all('AgentCom').value = arrSelected[0][4];
	fmQuery.all('AgentCode').value = arrSelected[0][5];
	fmQuery.all('AgentGroup').value = arrSelected[0][6];

	fmQuery.all('AppntNo').value = arrSelected[0][7];
	fmQuery.all('VIPValue').value = arrSelected[0][8];
	fmQuery.all('BlacklistFlag').value = arrSelected[0][9];
	fmQuery.all('AppntName').value = arrSelected[0][10];
}
	initSendTrace();
	//ctrlButtonDisabled(GrpContNo);
	
  return true;
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
	fmQuery.submit();

	}


/*********************************************************************
 *  ��ѯ���嵥���Զ��˱�δͨ���ĸ��˵�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryPol()
{
	var GrpProposalContNo = fmQuery.all('GrpProposalContNo').value;
	var mOperate = fmQuery.all('Operator').value;
	var strsql = "";

	// ��ʼ�����
	showDiv(divPerson,"false");
	initPolBox();
	//initPolGrid();
	// ��дSQL���

		var sqlid3="GroupUWSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(GrpProposalContNo);//ָ������Ĳ���
	    strsql=mySql3.getString();	


//	strsql = "select LCPol.ProposalNo,LCPol.AppntName,LCPol.RiskCode,LCPol.RiskVersion,LCPol.InsuredName,LCPol.ManageCom from LCPol where 1=1 "
//			  + "and LCPol.GrpPolNo = '"+GrpProposalContNo+"'"
// 	    	  + " and LCPol.AppFlag='0'"
//         	  + " and LCPol.UWFlag in ('3','5','6','8','7')"         //�Զ��˱����˹��˱�
//			  + " and LCPol.contno = '00000000000000000000'"
//			  + " order by LCPol.makedate ";

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
  if(GrpProposalContNo == fmQuery.all('GrpMainProposalNo').value)
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
	fmQuery.submit();
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
	fmQuery.submit();
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
	var flag = fmQuery.all('UWState').value;
	var tUWIdea = fmQuery.all('UWIdea').value;

	if (flag == "0"||flag == "1"||flag == "4"||flag == "9")
	{
	   //showModalDialog("./UWManuNormMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+flag+"&UWIdea="+tUWIdea,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
		fmQuery.action = "./UWManuNormChk.jsp";
		fmQuery.submit();
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
	
    var tUWIdea = fmQuery.UWIdea.value;
    var tUpReport = fmQuery.uwUpReport.value                //�ϱ���־
    if(flag==1)
    {
        fmQuery.all('YesOrNo').value = "Y";
    }
    if(flag ==2)
    {
        fmQuery.all('YesOrNo').value = "N";
    }
    if(flag == 0)
    {
        isClosed = true;
    }
	var cGrpContNo=fmQuery.GrpContNo.value;

	if( cGrpContNo == "" || cGrpContNo == null )
	{
		alert("����ѡ��һ������Ͷ����!");
	    cancelchk();
		return ;
	}
    if(tUpReport == "")
    {
        alert("����ѡ��˱�����!");
        return ;
    }

//    if(tUWIdea == "")
//     {
//      alert("����¼��б��˱����!");
//      return ;
//    }

    k++;

		var sqlid4="GroupUWSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(k);//ָ������Ĳ���
		mySql4.addSubPara(k);//ָ������Ĳ���
		mySql4.addSubPara(MissionID);//ָ������Ĳ���
	    var strsql =mySql4.getString();	

//    var strsql = "select submissionid from lwmission where "+k+"="+k
//                +" and activityid = '0000002010'"
//    						+" and missionid = '"+MissionID+"'"
//    						;

    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (!turnPage.strQueryResult) {
    	alert("δ�鵽������SubMissionID");
    	fmQuery.SubMissionID.value = "";
    	return;
    }
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    
    fmQuery.SubMissionID.value = turnPage.arrDataCacheSet[0][0];

	//��������ʵ��
	fmQuery.WorkFlowFlag.value = "0000002010";
	fmQuery.MissionID.value = MissionID;		
		
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fmQuery.action = "./UWConfirm.jsp";
  	fmQuery.submit(); //�ύ
	
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
	var cGrpProposalContNo = fmQuery.GrpProposalContNo.value;  //���屣������
	if(cGrpProposalContNo==""||cGrpProposalContNo==null)
	{
  		alert("����ѡ��һ����������Ͷ����!");
  		return ;
    }
    if(cGrpProposalContNo != fmQuery.GrpMainProposalNo.value)
	{
  		alert("����ѡ��һ����������Ͷ����!");
  		return ;
    }
	//showModalDialog("./QuestInputMain.jsp?ProposalNo1="+cProposalNo+"&Flag="+cFlag,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	window.open("./GrpQuestInputMain.jsp?GrpProposalContNo="+cGrpProposalContNo+"&ProposalNo="+cGrpProposalContNo+"&Flag="+cflag,"window2",sFeatures);

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
	
	var cGrpContNo = fmQuery.all('GrpContNo').value;  //�ŵ�Ͷ��������
	//alert(cGrpContNo);
	if(cGrpContNo==""||cGrpContNo==null)
	{
  		alert("����ѡ��һ����������Ͷ����!");
  		return ;
    }
 
	window.open("./GrpQuestQueryMain.jsp?ProposalNo1="+cGrpContNo+"&Flag="+cflag,sFeatures);
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
	fmQuery.all('GUWState').value = "";
	fmQuery.all('GUWIdea').value = "";
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

	var prtNo=fmQuery.all("PrtNo").value;
	if(prtNo==""||prtNo==null)
	{
	  	alert("����ѡ��һ������Ͷ����!");
  		return ;
  	}
	window.open("../uw/ImageQueryMainGrp.jsp?ContNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);								
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

function showGrpCont()
{
     var cGrpContNo=fmQuery.GrpContNo.value;
    //var newWindow=window.open("../app/GroupPolApproveInfo.jsp?LoadFlag=16&polNo="+cGrpContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1"); 
   // window.open("../app/GroupPolApproveInfo.jsp?LoadFlag=16&polNo="+cGrpContNo+"&GrpPrtNo="+cGrpContNo+"&SubType=TB1002","window1");
    window.open("../app/GroupPolApproveInfo.jsp?LoadFlag=16&polNo="+cGrpContNo+"&prtNo="+PrtNo+"&SubType=TB1002","window1",sFeatures);
}


//�ŵ�����ѯ����Ϣ
 function showAskApp()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

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
      //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	 
	  if (cGrpContNo != "" )
	  {
	  	fmQuery.action = "./UWManuSendBodyCheckChk.jsp";
	  	
	    fmQuery.submit();
	   
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
      //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	 
	  if (cGrpContNo != "" )
	  {
	  	fmQuery.action = "./UWManuSendRReportChk.jsp?GrpContNo="+cGrpContNo;
	  	
	    fmQuery.submit();
	   
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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  
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
		window.open("../uw/GrpUWHistoryContMain.jsp?GrpContNo="+cGrpContNo,"window2",sFeatures);
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
  

  window.open("../app/GroupPolApproveInfo.jsp?LoadFlag=23&polNo="+cpolNo, "","status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);  	  
}


/*********************************************************************
 *  ���ռƻ��������¼����ʾ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function showChangeResultView()
{
	//fmQuery.ChangeIdea.value = "";
	//fm.PolNoHide.value = fm.ProposalNo.value;  //��������
	//divUWResult.style.display= "none";
	var cGrpContNo = fmQuery.GrpContNo.value;
	//divChangeResult.style.display= "";
	window.open("./UWGChangeResultMain.jsp?GrpContNo=" + cGrpContNo + "&MissionID=" + MissionID,"",sFeatures);	
}

//�����ڽ���֪ͨ��
function SendFirstNotice()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  cProposalNo=fmQuery.GrpContNo.value;
 
  cOtherNoType="01"; //������������
  cCode="57";        //��������
  
  if (cProposalNo != "")
  {
  	//showModalDialog("./GrpUWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open ("./GrpUWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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
	
		var sqlid5="GroupUWSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(GrpContNo);//ָ������Ĳ���
	    var tSQL =mySql5.getString();	
	
//	var tSQL = "select 'X' from lcgrppol where grpcontno='" + GrpContNo
//	           + "' and (uwflag='5' or uwflag='z')";
	turnPage.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1); 
	 //�ж��Ƿ��ѯ�ɹ�
    if (turnPage.strQueryResult) {
    	
     	alert("�������ź˱�����֪ͨ��,ԭ����:��δ�����ֺ˱�����.");
        return ;
    }
    
			var sqlid6="GroupUWSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(PrtNo);//ָ������Ĳ���
		mySql6.addSubPara(MissionID);//ָ������Ĳ���
	    var strsql =mySql6.getString();	
	
//    var strsql = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + PrtNo +"'"
//				
//				 + " and LWMission.ActivityID = '0000002101'"
//				 + " and LWMission.ProcessID = '0000000004'"
//				 + " and LWMission.MissionID = '" +MissionID +"'";
		
  
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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();


  cProposalNo=fmQuery.GrpContNo.value;
 
  if (cProposalNo != "")
  {
  	fmQuery.action = "./UWSendNoticeChk.jsp";
  	fmQuery.submit();
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
	
		var sqlid7="GroupUWSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(cContNo);//ָ������Ĳ���
	    var strSql =mySql7.getString();	
	
//	var strSql = "select * from LCcUWMaster where ContNo='" + cContNo + "' and ChangePolFlag ='1'";
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

		var sqlid8="GroupUWSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySq8.addSubPara(GrpContNo);//ָ������Ĳ���
		mySql8.addSubPara(GrpContNo);//ָ������Ĳ���
	   tSql=mySql8.getString();	

//	tSql = "select sendflag,uwflag,uwidea from lcuwsendtrace where 1=1 "
//					+ " and otherno = '"+GrpContNo+"'"
//					+ " and othernotype = '2'"
//					+ " and uwno in (select max(uwno) from lcuwsendtrace where otherno = '"+GrpContNo+"')" 
//					;
	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

  //�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult) {
		fmQuery.all('SendFlag').value =arrSelected[0][0];
		fmQuery.all('SendUWFlag').value =arrSelected[0][1];
		fmQuery.all('SendUWIdea').value =arrSelected[0][2];

		DivLCSendTrance.style.display="";
	}

	if(fmQuery.all('SendFlag').value == '0')
	{
		divUWSave.style.display = "";
		divUWAgree.style.display = "none";
	}	
	else if(fmQuery.all('SendFlag').value == '1')
	{
		divUWAgree.style.display = "";
		divUWSave.style.display = "none";
		tSql = "select passflag,uwidea from lcgcuwmaster where 1=1 "
					+ " and grpcontno = '"+GrpContNo+"'" 
					;
		turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		
		fmQuery.all('GUWState').value = arrSelected[0][0];
		fmQuery.all('GUWIdea').value = arrSelected[0][1];
	}
}
function getfee(parm1,parm2)
{
	var grppolno=fmQuery.all(parm1).all('GrpGrid11').value;
	var tAddPrem = "";
	
		var sqlid9="GroupUWSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(grppolno);//ָ������Ĳ���
	   var strSQL=mySql9.getString();	
	
//	var strSQL="select sum(standprem) from lcpol where grppolno='"+grppolno+"'";
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

    //�ж��Ƿ��ѯ�ɹ�
    if (turnPage.strQueryResult) {
		tAddPrem =arrSelected[0][0];
	}	
    
		var sqlid10="GroupUWSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(tAddPrem);//ָ������Ĳ���
		mySql10.addSubPara(tAddPrem);//ָ������Ĳ���
		mySql10.addSubPara(fmQuery.GrpContNo.value);//ָ������Ĳ���
		mySql10.addSubPara(grppolno);//ָ������Ĳ���
	   strSQL=mySql10.getString();	
	
//    strSQL="select concat('��',ltrim(to_char(nvl(sum(prem),0),'999999999999.99')))," +
//          "concat('��',ltrim(to_char(" + tAddPrem + 
//         ",'999999999999.99'))),round(nvl(sum(prem)/" + tAddPrem +",0),2)"
//         + " from lcprem where grpcontno='" + fmQuery.GrpContNo.value
//         + "' and polno in (select polno from lcpol where grppolno='"+grppolno+"')";	
  	turnPage.queryModal(strSQL,GrpPolFeeGrid);
  	
  	if (GrpPolFeeGrid.mulineCount > 0){
		
		var sqlid11="GroupUWSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(grppolno);//ָ������Ĳ���
	   var sSQL=mySql11.getString();	
		
//      	var sSQL = "select standbyflag2,standbyflag3 from lcgrppol where grppolno='" + grppolno + "'" ;
      	turnPage.strQueryResult  = easyQueryVer3(sSQL, 1, 0, 1);  
    	arrRate = decodeEasyQueryResult(turnPage.strQueryResult);
    	if (arrRate != null && arrRate.length > 0)
    	{
        	GrpPolFeeGrid.setRowColData(0,4,arrRate[0][0]);
        	GrpPolFeeGrid.setRowColData(0,5,arrRate[0][1]);
        }
    }
  	
		var sqlid12="GroupUWSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(grppolno);//ָ������Ĳ���
	   var tSql=mySql12.getString();	
	
//  var tSql = "select passflag ,uwidea from lcguwmaster where grppolno = '"+grppolno+"'";
  	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		if (arrSelected != null && arrSelected.length > 0){
		
		    fmQuery.all('GUWState').value = arrSelected[0][0];
		    fmQuery.all('GUWIdea').value = arrSelected[0][1];
		}
  
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
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}

/*********************************************************************
 *  ��ʼ�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInfo()
{
		showOneCodeName('comcode','ManageCom','ManageComName');
		showOneCodeName('agenttype','SaleChnl','SaleChnlName');
		showOneCodeName('comcode','AgentCom','AgentComName');
		showOneCodeName('vipvalue','VIPValue','VIPValueName');
		showOneCodeName('blacklistflag','BlacklistFlag','BlacklistFlagName');
		showOneCodeName('uwupreport','SendFlag','SendFlagName');
		showOneCodeName('contuwstate','SendUWFlag','SendUWFlagName');
}

/*********************************************************************
 *  �Լ��屣�������½���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function grpPolCommit()
 {
 	 
	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
	
	if (GrpPolFeeGrid.getRowColData(0,4) == "null" 
	     || GrpPolFeeGrid.getRowColData(0,4) == ""
	     || GrpPolFeeGrid.getRowColData(0,4) == null)
	{
	    alert("���������Ϊ�գ�");
	}
	
	if (GrpPolFeeGrid.getRowColData(0,5) == "null" 
	     || GrpPolFeeGrid.getRowColData(0,5) == ""
	     || GrpPolFeeGrid.getRowColData(0,5) == null)
	{
	    alert("���۷�����Ϊ�գ�");
	}
	
	if (GrpPolFeeGrid.getRowColData(0,4) != "null" && 
        GrpPolFeeGrid.getRowColData(0,4)!= ""){
    	if (isNumeric(GrpPolFeeGrid.getRowColData(0,4)) == false)
    	{
    	    alert("��������ȷ�Ĺ�������ʣ�");
    	    return;
    	}
        else if (parseFloat(GrpPolFeeGrid.getRowColData(0,4)) == 0){
            alert("���������Ϊ0��");
        }
    }
    if (GrpPolFeeGrid.getRowColData(0,5) != "null" && 
        GrpPolFeeGrid.getRowColData(0,5)!= ""){
    	if (isNumeric(GrpPolFeeGrid.getRowColData(0,5)) == false)
    	{
    	    alert("��������ȷ�����۷����ʣ�");
    	    return;
    	}
    	else if (parseFloat(GrpPolFeeGrid.getRowColData(0,5)) == 0){
            alert("���۷�����Ϊ0��");
        }
    }	
   
	
  cProposalNo=GrpGrid.getRowColData(selno,11);
  fmQuery.GrpPolNo.value = cProposalNo;
  
  var flag = fmQuery.all('GUWState').value;
	var tUWIdea = fmQuery.all('GUWIdea').value;
  
  	if (flag == null || flag == "")
	{
		alert("����ѡ��˱�����!");
	    cancelchk();
		return ;
	}
	
	if (flag == "1")
	{
	    if (confirm("ȷ��Ҫ�¾ܱ�������") == false)
	    {
	        return;
	    }
	}
	//var strSQL="select UWFlag from LCGrpPol where PrtNo='"+PrtNo+"'";
	//var tflag = easyExecSql(strSQL);
	
	if(flag=='z')
	{
		alert("�˱����۲���Ϊz!");
	    cancelchk();
		return ;
	}
     
var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();
  if (cProposalNo != "")
  {
  	fmQuery.action = "./GrpPolCommitChk.jsp";
  	fmQuery.submit();
  }
 
}
function queryProposal(){
	  
	var cContNo = fmQuery.all('GrpContNo').value;
	
		var sqlid13="GroupUWSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
		mySql13.addSubPara(cContNo);//ָ������Ĳ���
	   var strsql=mySql13.getString();	
	
//	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
	var arr=easyExecSql(strsql);
	
	if(arr!=null){
		fmQuery.all('AppntNo').value=arr[0][0];
	}
	//alert(fmQuery.all('AppntNo').value);
    window.open("./GroupProposalQueryMain.jsp?AppntNo="+fmQuery.all('AppntNo').value,"window1",sFeatures);

}
function queryNotProposal(){
  
  var cContNo = fmQuery.all('GrpContNo').value;
  
  		var sqlid14="GroupUWSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
		mySql14.addSubPara(cContNo);//ָ������Ĳ���
	   var strsql=mySql14.getString();	
  
//	var strsql="select AppntNo from  LccUWMaster Where GrpContNo='"+cContNo+"'";
	var arr=easyExecSql(strsql);
	if(arr!=null){
		fmQuery.all('AppntNo').value=arr[0][0];
	}
	//alert(fmQuery.all('AppntNo').value);

	window.open("./GroupNotProposalQueryMain.jsp?AppntNo="+fmQuery.all('AppntNo').value,"window1",sFeatures);

}

function grpAddFee()
{
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var tGrpContNo = fmQuery.all("GrpContNo").value;
 
  //var tContNo = fmQuery.all("ContNo").value;
  //var tPolNo = fmQuery.all("PolNo").value;
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
 	var tGrpContNo = fmQuery.all("GrpContNo").value;
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
 	var tGrpContNo = fmQuery.all("GrpContNo").value;
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
 	var tGrpContNo = fmQuery.all("GrpContNo").value;
 	var riskcode=GrpGrid.getRowColData(selno, 4);
 	var tGrpPolNo = GrpGrid.getRowColData(selno, 11);
 	var tPrtNo = GrpGrid.getRowColData(selno, 2);
 	if (tGrpContNo != "")
  {
	window.open("./GrpRiskElenemtMain.jsp?GrpContNo="+tGrpContNo+"&riskcode="+riskcode,"window1",sFeatures);
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
	
   var tGrpPolNo=GrpGrid.getRowColData(selno,11);
    	if (tGrpPolNo != "")
  {
	window.open("./GrpFloatRateMain.jsp?GrpPolNo=" + tGrpPolNo ,"window1",sFeatures);
  }
   
 }
 
 /*********************************************************************
 *  ���ظ��˽ڵ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  �޸��ˣ�Gaoht
 *  ʱ  �䣺2005-10-12
 *********************************************************************
 */
function returnApprove()
{

//    var tSql = " select * from lwmission where  1=1 "
//             + " and activityid in ('0000002004') "
//             + " and missionid = '"+fm.MissionID.value+"'"
//             + " and submissionid = '"+fm.SubMissionID.value+"'";

//    var arr = easyExecSql( tSql );
   
//    if (arr) 
//    {
//         alert("����֪ͨ��û�лظ�, �������ظ���")   
//         return ;   
//    }
    
    var i = 0;
    var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();


    fmQuery.action = "../uw/GrpUWReturnApproveSave.jsp";
    fmQuery.submit(); //�ύ

}

/*********************************************************************
 *  ִ�з��ظ��˽ڵ��Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *  �޸��ˣ�����
 *  ʱ  �䣺2005-10-12
 *********************************************************************
 */
function afterReturnApprove(FlagStr,content)
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
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	   showInfo.focus();
    }
}


//*****************add by haopan********************//
function ctrlButtonDisabled(GrpContNo)
{
	  var tSQL = "";
  	var arrResult;
  	var arrButtonAndSQL = new Array;
  	
	  arrButtonAndSQL[0] = new Array;
	  arrButtonAndSQL[0][0] = "uwButton1";
	  arrButtonAndSQL[0][1] = "����ɨ�����ѯ";
	  arrButtonAndSQL[0][2] = "select * from  es_doc_relation e where e.bussno='"+GrpContNo+"'";
	  	  
	  arrButtonAndSQL[1] = new Array;
	  arrButtonAndSQL[1][0] = "uwButton2";
	  arrButtonAndSQL[1][1] = "�����Զ��˱���Ϣ";
	  arrButtonAndSQL[1][2] = "";
	  
	  arrButtonAndSQL[2] = new Array;
	  arrButtonAndSQL[2][0] = "uwButton3";
	  arrButtonAndSQL[2][1] = "���屣����ϸ";
	  arrButtonAndSQL[2][2] = "";
	  
	  arrButtonAndSQL[3] = new Array;
	  arrButtonAndSQL[3][0] = "uwButton4";
	  arrButtonAndSQL[3][1] = "�����Զ��˱���Ϣ";
	  arrButtonAndSQL[3][2] = "";
	  
	  arrButtonAndSQL[4] = new Array;
	  arrButtonAndSQL[4][0] = "uwButton5";
	  arrButtonAndSQL[4][1] = "�����ѳб�������ѯ";
	  arrButtonAndSQL[4][2] = "select * from lcgrppol a,lcgrpcont b where a.grpcontno=b.grpcontno and a.customerno='"+fmQuery.all('AppntNo').value+"'  and a.appflag in ('1','4')";
	  
	  arrButtonAndSQL[5] = new Array;
	  arrButtonAndSQL[5][0] = "uwButton6";
	  arrButtonAndSQL[5][1] = "����δ�б�������ѯ";
	  arrButtonAndSQL[5][2] = "select * from lcgrppol a,lcgrpcont b where a.grpcontno=b.grpcontno and a.customerno='"+fmQuery.all('AppntNo').value+"'  and a.appflag = '0'";
	  
	  arrButtonAndSQL[6] = new Array;
	  arrButtonAndSQL[6][0] = "uwButton7";
	  arrButtonAndSQL[6][1] = "���屣���������ѯ";
	  arrButtonAndSQL[6][2] = "select * from lcgrpissuepol where grpcontno='"+GrpContNo+"' and rownum=1";
	
		arrButtonAndSQL[7] = new Array;
	  arrButtonAndSQL[7][0] = "uwButton8";
	  arrButtonAndSQL[7][1] = "���˱�����֪ͨ��";
	  arrButtonAndSQL[7][2] = "";
	  
	  arrButtonAndSQL[8] = new Array;
	  arrButtonAndSQL[8][0] = "uwButton9";
	  arrButtonAndSQL[8][1] = "�޸�������Ҫ����¼��";
	  arrButtonAndSQL[8][2] = "";
	
		arrButtonAndSQL[9] = new Array;
	  arrButtonAndSQL[9][0] = "uwButton10";
	  arrButtonAndSQL[9][1] = "�����������۲�ѯ";
	  arrButtonAndSQL[9][2] = "select * from  lcrreport where grpcontno='"+GrpContNo+"' and rownum=1";
	  
	  arrButtonAndSQL[10] = new Array;
	  arrButtonAndSQL[10][0] = "uwButton11";
	  arrButtonAndSQL[10][1] = "���������۲�ѯ";
	  arrButtonAndSQL[10][2] = "select * from lcpenotice where grpcontno='"+GrpContNo+"' and rownum=1";
	
		arrButtonAndSQL[11] = new Array;
	  arrButtonAndSQL[11][0] = "uwButton12";
	  arrButtonAndSQL[11][1] = "����Ҫ������";
	  arrButtonAndSQL[11][2] = "";
	
		arrButtonAndSQL[12] = new Array;
	  arrButtonAndSQL[12][0] = "uwButton13";
	  arrButtonAndSQL[12][1] = "�������ʵ���";
	  arrButtonAndSQL[12][2] = "";
	  
	  arrButtonAndSQL[13] = new Array;
	  arrButtonAndSQL[13][0] = "uwButton14";
	  arrButtonAndSQL[13][1] = "���������ȫ��ѯ";
	  arrButtonAndSQL[13][2] = "select * from lpgrpedoritem p where '"+fmQuery.all('AppntNo').value+"' in  (select appntno from lpgrpcont where grpcontno = p.grpcontno) and edorstate = '0'";
	  
	  arrButtonAndSQL[14] = new Array;
	  arrButtonAndSQL[14][0] = "uwButton15";
	  arrButtonAndSQL[14][1] = "������������ѯ";
	  arrButtonAndSQL[14][2] = "select * from llgrpregister g,lcgrpcont c where g.rgtobjno=c.grpcontno and c.appntno='"+fmQuery.all('AppntNo').value+"' "; 

	  
	  arrButtonAndSQL[15] = new Array;
	  arrButtonAndSQL[15][0] = "uwButton16";
	  arrButtonAndSQL[15][1] = "֪ͨ���ѯ";
	  arrButtonAndSQL[15][2] = "select * from loprtmanager where otherno='"+GrpContNo+"'or standbyflag1='"+GrpContNo+"' or standbyflag2='"+GrpContNo+"'";
	
	  arrButtonAndSQL[16] = new Array;
	  arrButtonAndSQL[16][0] = "uwButton17";
	  arrButtonAndSQL[16][1] = "�ݽ�����Ϣ��ѯ";
	  arrButtonAndSQL[16][2] = "select * from ljtempfee where otherno='"+GrpContNo+"' and othernotype='4'";
		
		for(var i=0; i<arrButtonAndSQL.length; i++)
			{
				if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!="")
				{
					//alert(arrButtonAndSQL[i][1]+":"+arrButtonAndSQL[i][2]);
					arrResult = easyExecSql(arrButtonAndSQL[i][2]);
				
					if(arrResult!=null)
					{
					
						eval("fmQuery.all('"+arrButtonAndSQL[i][0]+"').disabled=''");	
					}
					else
					{
							eval("fmQuery.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");	
					}
				}
				else
				{
					continue;
				}	
			}
	}

//��ѯ���±�
function checkNotePad(PrtNo){

  		var sqlid15="GroupUWSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
		mySql15.addSubPara(PrtNo);//ָ������Ĳ���
	   var strSQL=mySql15.getString();	

//	var strSQL="select count(*) from LWNotePad where otherno='"+PrtNo+"'";
	
	var arrResult = easyExecSql(strSQL);


	eval("fmQuery.all('NotePadButton').value='���±��鿴 ����"+arrResult[0][0]+"����'");
	
}

//������ȫ��ѯ
function queryEdor(){

	window.open("../bq/GEdorAgoQueryInput.jsp?CustomerNo="+fmQuery.all('AppntNo').value,"window1",sFeatures);
	
}

//���������ѯ
function queryClaim(){

	window.open("./ClaimGrpQueryInput.jsp?CustomerNo="+fmQuery.all('AppntNo').value,"window1",sFeatures);
}

//֪ͨ���ѯ
function queryNotice()
{
	window.open("./QueryNotice.jsp?GrpContNo="+fmQuery.all('GrpContNo').value,"window1",sFeatures);	
}
//*************add end****************//

//����ŵ���Ч��
function GrpChgCvalidate(){
    var cGrpContNo=fmQuery.GrpContNo.value;
    if(cGrpContNo==""||cGrpContNo==null)
    {
        alert("����ѡ��һ������Ͷ����!");
        return ;
    }
    window.open("./UWGChgCvalidateMain.jsp?GrpContNo="+cGrpContNo,"window1",sFeatures);
    
}

//�ݽ��Ѳ�ѯ
function showTempFee()
{
	var arrReturn = new Array();
	var tSel = GrpGrid.getSelNo();
	
	
	   var cGrpContNo = +fmQuery.all('GrpContNo').value; 
	   
	   if (cGrpContNo == "")
		    return;
		 
		var sqlid16="GroupUWSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
		mySql16.addSubPara(cGrpContNo);//ָ������Ĳ���
	   var strSQL=mySql16.getString();	
		 
		 
//		 var strSQL="select AppntNo from LCGrpCont where GrpContNo='"+cGrpContNo+"'";
		 var tAppntNo = easyExecSql(strSQL);
		   
		var sqlid17="GroupUWSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
		mySql17.addSubPara(cGrpContNo);//ָ������Ĳ���
	   var strSQL=mySql17.getString();	
		   
//		 var strSQL="select APPntName from LJTempFee where OtherNo='"+cGrpContNo+"'group by APPntName";
		 var tAppntName = easyExecSql(strSQL);
		  
		  window.open("../uw/ShowTempFee.jsp?Prtno=" + cGrpContNo + " &AppntNo=" + tAppntNo + " &AppntName= " + tAppntName,"window1",sFeatures);
	
}


//�ж��Ƿ��ǲ�Ʒ�������
function isContPlan()
{
	
		var sqlid18="GroupUWSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
		mySql18.addSubPara(GrpContNo);//ָ������Ĳ���
	   var sql=mySql18.getString();	
	
//	var sql="select contplancode from lccontplan where 1=1 and plantype='6' and grpcontno='"+GrpContNo+"'";
	var arrResult=new Array();
	arrResult=easyExecSql(sql);
	if(arrResult!=null)
  {
  	divLCGrpPol.style.display="none";
  	divGrpPolUWResult.style.display="none";
  	fmQuery.all('uwButton13').disabled='true';
  	fmQuery.all('uwButton12').disabled='true';
		divGrpContPlan.style.display="";
		divGrpPlanUWResult.style.display="";
  	return true;
  }
else
	{
		divLCGrpPol.style.display="";
		divGrpPolUWResult.style.display="";
		divGrpContPlan.style.display="none";
		divGrpPlanUWResult.style.display="none";
		return false;
	}
}


//��Ʒ��ϲ�ѯ
function queryContPlan()
{
	
		var sqlid19="GroupUWSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
		mySql19.addSubPara(fmQuery.all('GrpContNo').value);//ָ������Ĳ���
		mySql19.addSubPara( fmQuery.all('GrpContNo').value);//ָ������Ĳ���
		mySql19.addSubPara( fmQuery.all('GrpContNo').value);//ָ������Ĳ���
		
		mySql19.addSubPara(fmQuery.all('GrpContNo').value);//ָ������Ĳ���
		mySql19.addSubPara(fmQuery.all('GrpContNo').value);//ָ������Ĳ���
	   strSql=mySql19.getString();
	
//	 strSql=" select /*+RULE*/ a.contplancode, a.contplanname,'0',a.peoples2,"
//        +"(select nvl(sum(i.insuredpeoples),0) from lcinsured i "
//        +"where i.grpcontno = '"
//        +fmQuery.all('GrpContNo').value + "' and i.contplancode=a.contplancode)" 
//        +" as peoples,"
//        +"'',"
//        +"(select nvl(sum(prem),0) from lcprem where grpcontno='" + fmQuery.all('GrpContNo').value
//        +"' and contno in (select contno from lcinsured where grpcontno='" 
//        + fmQuery.all('GrpContNo').value +"' and contplancode=a.contplancode)),"
//        +"round((select nvl(sum(i.insuredpeoples),0) from lcinsured i "
//        +"where i.grpcontno = '"
//        +fmQuery.all('GrpContNo').value + "' and i.contplancode=a.contplancode)/decode(a.peoples2,0,1,a.peoples2),2) "
//        +"from lccontplan a Where a.plantype='6' and a.grpcontno = '"
//        +fmQuery.all('GrpContNo').value+"' order by a.contplancode" ;
        
      	turnPage2.queryModal(strSql,PlanGrid);
	
	
	var arrSelected = new Array();
	
			var sqlid20="GroupUWSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
		mySql20.addSubPara(GrpContNo);//ָ������Ĳ���
	   var strSQL=mySql20.getString();
	
//	var strSQL = "select GrpContNo,PrtNo,ManageCom,SaleChnl,AgentCom,AgentCode,AgentGroup,AppntNo,VIPValue,BlacklistFlag,lcgrpcont.GrpName from lcgrpcont,LDGrp where grpcontno='"+GrpContNo+"'"
//		+" and lcgrpcont.AppntNo = LDGrp.CustomerNo " ;
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  if (turnPage.strQueryResult)
  {	
	 fmQuery.all('GrpContNo').value = arrSelected[0][0];
	 fmQuery.all('PrtNo').value = arrSelected[0][1];
	 fmQuery.all('ManageCom').value = arrSelected[0][2];
	 fmQuery.all('SaleChnl').value = arrSelected[0][3];
	 fmQuery.all('AgentCom').value = arrSelected[0][4];
	 fmQuery.all('AgentCode').value = arrSelected[0][5];
	 fmQuery.all('AgentGroup').value = arrSelected[0][6];

	 fmQuery.all('AppntNo').value = arrSelected[0][7];
	 fmQuery.all('VIPValue').value = arrSelected[0][8];
	 fmQuery.all('BlacklistFlag').value = arrSelected[0][9];
	 fmQuery.all('AppntName').value = arrSelected[0][10];
  }
	initSendTrace();
}

//��Ʒ���������ϸ��ѯ
function getPlanRiskDetail(parm1,parm2)
{
    var tContPlanCode=fmQuery.all(parm1).all('PlanGrid1').value;
	
		var sqlid21="GroupUWSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
		mySql21.addSubPara(fmQuery.all('PrtNo').value );//ָ������Ĳ���
		mySql21.addSubPara(fmQuery.all('PrtNo').value);//ָ������Ĳ���
		mySql21.addSubPara(tContPlanCode);//ָ������Ĳ���
		
		mySql21.addSubPara(fmQuery.all('PrtNo').value);//ָ������Ĳ���
		mySql21.addSubPara(tContPlanCode);//ָ������Ĳ���
		mySql21.addSubPara(tContPlanCode);//ָ������Ĳ���
		
		mySql21.addSubPara(fmQuery.PrtNo.value);//ָ������Ĳ���
		mySql21.addSubPara(fmQuery.PrtNo.value );//ָ������Ĳ���
		mySql21.addSubPara(tContPlanCode);//ָ������Ĳ���
	   var tSQL=mySql21.getString();
	
//    var tSQL = "select distinct a.riskcode,'',b.dutycode,e.dutyname," 
//             + "(select nvl(sum(prem),0) from lcprem "
//             + "where grpcontno='" + fmQuery.all('PrtNo').value 
//             + "' and dutycode=b.dutycode and contno in "
//             + "(select contno from lcinsured where grpcontno='" 
//             + fmQuery.all('PrtNo').value +"' and contplancode='" 
//             + tContPlanCode +"')),"
//             + "(select nvl(sum(amnt),0) from lcduty "
//             + "where dutycode=b.dutycode and contno in "
//             + "(select contno from lcinsured where grpcontno='" 
//             + fmQuery.all('PrtNo').value +"' and contplancode='" 
//             + tContPlanCode +"'))"
//             + " from lccontplanrisk a,lccontplandutyparam b,"
//             + "lmriskduty d,lmduty e"
//             + " where d.riskcode=a.riskcode"
//             + " and b.dutycode=d.dutycode and e.dutycode=b.dutycode"
//             + " and a.contplancode='" + tContPlanCode 
//             + "' and a.plantype='6' and a.proposalgrpcontno='" 
//             + fmQuery.PrtNo.value + "' and b.grpcontno='" + fmQuery.PrtNo.value 
//             + "' and b.plantype='6' and b.contplancode='" + tContPlanCode + "'";
    turnPage3.queryModal(tSQL, PlanRiskGrid);
    //��ѯ��Ʒ��Ϻ˱�������Ϣ
       
	   		var sqlid22="GroupUWSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
		mySql22.addSubPara(fmQuery.PrtNo.value );//ָ������Ĳ���
		mySql22.addSubPara(tContPlanCode);//ָ������Ĳ���
		
	   var tSql=mySql22.getString();
	   
//    var tSql = "select passflag ,uwidea from lcgplanuwmaster where grpcontno = '"+fmQuery.PrtNo.value
//              +"' and contplancode='"+tContPlanCode+"' and plantype='6'";
  	turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		if (arrSelected != null && arrSelected.length > 0)
		{
		
		    fmQuery.all('GPlanUWState').value = arrSelected[0][0];
		    fmQuery.all('GPlanUWIdea').value = arrSelected[0][1];
		}
		
		//��Ʒ�����Լ��Ϣ
		
		var sqlid23="GroupUWSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("uw.GroupUWSql"); //ָ��ʹ�õ�properties�ļ���
		mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
		mySql23.addSubPara(fmQuery.PrtNo.value );//ָ������Ĳ���
		mySql23.addSubPara(tContPlanCode);//ָ������Ĳ���
		
	   tSql=mySql23.getString();
		
//		tSql ="select remark2 from lccontplan where grpcontno='"+fmQuery.PrtNo.value+"' and contplancode='"+tContPlanCode+"' and plantype='6'";
		turnPage.strQueryResult  = easyQueryVer3(tSql, 1, 0, 1);  
		arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
		
	 if (arrSelected != null && arrSelected.length > 0)
	  {
		    fmQuery.all('PlanSpecContent').value = arrSelected[0][0];
		}
}
//��Ʒ��Ϻ˱����۱���
function grpPlanCommit()
{
	
	var selno = PlanGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
  var cContPlanCode=PlanGrid.getRowColData(selno,1);
  fmQuery.ContPlanCode.value = cContPlanCode;
  
  var flag = fmQuery.all('GPlanUWState').value;
	var tUWIdea = fmQuery.all('GPlanUWIdea').value;
  
  	if (flag == null || flag == "")
	{
		alert("����ѡ��˱�����!");
	    cancelchk();
		return ;
	}
	
	if (flag == "1")
	{
	    if (confirm("ȷ��Ҫ�¾ܱ�������") == false)
	    {
	        return;
	    }
	}
	if(flag=='z')
	{
		alert("�˱����۲���Ϊz!");
	    cancelchk();
		return ;
	}
     
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();
    if (cContPlanCode != "")
  {
  	  fmQuery.action = "./GrpPlanUWManuNormGChk.jsp";
  	  fmQuery.submit();
  }
}

/*********************************************************************
 *  
 *  �ŵ������ر�Լ��¼��
 *  
 *********************************************************************/
 function grpSpecInput()
 {
 	  
 	 var cGrpContNo=fmQuery.GrpContNo.value;
   window.open("../uw/UWManuGrpSpecInputMain.jsp?GrpContNo=" + cGrpContNo,"window1",sFeatures);
 }