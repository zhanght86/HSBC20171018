
var showInfo;
var mDebug="0";
var FlagDel;//��delete�������ж�ɾ�����Ƿ�ɹ�

function afterSubmit( FlagStr, content )
{
  FlagDel = FlagStr;

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
    	showDiv(inputButton,"false");
    }
}

function afterCodeSelect( cCodeName, Field )
{
 //alert("*****"+ cCodeName +"  " + Field.value);
 if(cCodeName == "AppReportType")
 { 
    fm.AppManageType.value="";
   /* if( Field.value=="App11" || Field.value=="App12" )
    {
      fm.AppManageType.CodeData ="0|^4|�ļ�����ͳ��"; 
    }
  else*/
    if(comcode = "86")
    {
     fm.AppManageType.CodeData ="0|^2|��������ͳ��^3|��������ͳ��^S|����^N|����";
    }
    else
    {
     fm.AppManageType.CodeData ="0|^2|��������ͳ��^3|��������ͳ��";
    }
  // alert(fm.AppManageType.CodeData);
  
    /* if(Field.value=="App09")
     {
     	 alert("Ŀǰ��֧������ͳ��!");
     	 fm.AppReportType.value="";
     }*/
     
     if(Field.value=="App02")
     {
     	 divRisk.style.display="";
     }
     else
     {
     	divRisk.style.display="none";
     }
  }
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

function PrintData()
{
  if((fm.StartDate.value=="")||(fm.EndDate.value=="")||(fm.StartDate.value=="null")||(fm.EndDate.value=="null"))
  {
    alert("�����뱣����Ч��ʼ���ںͽ�������");
    return false;
  } 
  
  if( fm.AppReportType.value == "" || fm.AppReportType.value ==null )
  {
  	alert("�������Ͳ���Ϊ�գ�");
    return false;
  }
  
  if( fm.AppManageType.value == "" || fm.AppManageType.value ==null )
  {
  	alert("�������Ͳ���Ϊ�գ�");
    return false;
  }
  if( fm.AppSalechnl.value == "" || fm.AppSalechnl.value ==null )
  {
  	alert("������������Ϊ�գ�");
    return false;
  }
  
  //������Ч��ʼ���ڲ��ܴ��ڽ�������
  if(compareDate(fm.StartDate.value,fm.EndDate.value)==1) 
   {
     alert("��ʼ���ڲ��ܴ��ڽ������ڣ�");	
    return false;                       
   }
 // alert("comcode.length :"+comcode.length+"\n fm.ManageType.value :"+ fm.ManageType.value);
  //������Ա��ͳ�ƶ�������ʱ���½�������벻�ܳ���4λ����86��8611��

   if(comcode.length>2*(fm.AppManageType.value)) 
    {
      alert("���ĵ�½������Ȩ��ӡ��ѡ��ı������ͣ�");
      return false;
    } 
   var tNoticeStr = "��ʼ����:  "+fm.StartDate.value+" "+fm.StartTime.value+" \n��������:  "+fm.EndDate.value+" "+fm.EndTime.value+"\n��������:  "+fm.ReportName.value+"\n��������:  "+fm.ManageName.value+"\n��������:  "+fm.SalechnlName.value;
   if(divRisk.style.display=="")
   {
   		if(fm.RiskCode.value==null || fm.RiskCode.value=='')
   			tNoticeStr = tNoticeStr +"\n���ֱ���:  ����";
   		else
   			tNoticeStr = tNoticeStr +"\n���ֱ���:  "+fm.RiskCode.value;
   }
   
    if(!confirm("�������ͳ������Ϊ��\n\n"+tNoticeStr))
      return false;
    	
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //fm.action = './XQBankSuccPrint.jsp';
    fm.target="f1print";
    showInfo.close();
    document.getElementById("fm").submit();
  
}

