//�������ƣ�UWManuReport.js
//�����ܣ��˹��˱��˱�����¼��
//�������ڣ�
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //���������λ�� 1.�˱�

//�ύ�����水ť��Ӧ����
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

  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    //showInfo.close();
    alert(content);
    parent.close();
  }
  else
  { 
	var showStr="�����ɹ�";  	
  	//showInfo.close();
  	alert(showStr);
  	parent.close();
  	
    //ִ����һ������
  }
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


function inputReport()
{
	fm.action= "./UWManuReportChk.jsp";
	fm.submit();
}

function initContent()
{
	fm.action= "./UWManuReportQuery.jsp";
	fm.submit();
}

// ��ѯ��ť
function initlist(tProposalNo)
{
	
	// ��дSQL���
	k++;
	var strSQL = "";
	
//	strSQL = "select dutycode,firstpaydate,payenddate from lcduty where "+k+" = "+k
//		+ " and polno = '"+tProposalNo+"'";
//	
	    var sqlid1="UWManuReportSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.UWManuReportSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1); //ָ��ʹ�õ�Sql��id
		var strSQL =mySql1.getString();	
		
  str  = easyQueryVer3(strSQL, 1, 0, 1); 
  //alert("ddd:"+str); 
  //�ж��Ƿ��ѯ�ɹ�
  //if (!turnPage.strQueryResult) {
    //alert("û��ûͨ���˱����˵���");
    //return "";
  //}
  //else
  //{
  	//str = turnPage.strQuestResult;
  	//alert("sss:"+turnPage.strQuestResult);
  //alert("1:"+str);	
  //}

  
  return str;
}