//�������ƣ�UWManuGrpSpec.js
//�����ܣ��˹��˱������б�
//�������ڣ�
//������  ��
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
	
	if(fm.SpecReason.value==null||fm.SpecReason.value=="")
	{
		 alert("��Լԭ����Ϊ��");	   		  
		 return false;
	}
	if(fm.SpecReason.value.length>400)
	{
		 alert("��Լԭ�򳤶Ȳ��ܳ���200������");	   		  
		 return false;
	}	
	if(fm.Remark.value==null||fm.Remark.value=="")
	{
		alert("��Լ���ݲ���Ϊ��");
		return false;
		
	}
	if(fm.Remark.value.length>400)
	{
		alert("��Լ���ݳ��Ȳ��ܳ���200������");
		return false;
		
	}	
	fm.Operate.value="INSERT||GrpUWSpec";
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  fm.submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr,content)
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
  }
  else
  { 
	  var showStr="�����ɹ�";  	
  	showInfo.close();
  	alert(showStr);
    //ִ����һ������
  }
}


function QueryGrpSpecGrid()
{
  var strSQL = "select grpcontno,prtno,grpname from lcgrpcont where grpcontno='"+fm.GrpContNo.value+"'";
  turnPage.queryModal(strSQL,UWSpecGrid);
  getGrpSpec();
  return true;	
}

//������Լ��ѯ
function getGrpSpec()
{
  var strSQL = "select a.specreason,b.speccontent from LCGCUWMaster a, LCCGrpSpec b where a.grpcontno=b.grpcontno and a.grpcontno='"+fm.GrpContNo.value+"' ";
  var arrResult=new Array();
  arrResult=easyExecSql(strSQL);
  if(arrResult!=null)
  {
    fm.Remark.value=arrResult[0][1];
    fm.SpecReason.value=arrResult[0][0];
  }
}
//��Լ��Ϣɾ��
function deleteSpec()
{
	fm.Operate.value="DELETE||GrpUWSpec";
    var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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