//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var Action;
var tRowNo=0;

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	fm.all('distill').disabled = false;
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
	}
}






function SubmitFormAgent()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("��¼��ͳ�����ͳ���£�");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("ͳ�����ʽΪ��YYYY����ͳ���¸�ʽΪ��MM�����������ȷ�ĸ�ʽ");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("��¼�����������룡");
			return ;
		}		
		if(comcode.length>6)
		{
			alert("����������ܵ�����������¼���������������Ϲ��������");
			return ;
		}
  
        
    fm.Opt.value="Agent";
    fm.all('distill').disabled = true;
    fm.submit();
}

function SubmitFormReAgent()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("��¼��ͳ�����ͳ���£�");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("ͳ�����ʽΪ��YYYY����ͳ���¸�ʽΪ��MM�����������ȷ�ĸ�ʽ");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("��¼�����������룡");
			return ;
		}		
    fm.Opt.value="ReAgent";
    fm.all('distill').disabled = true;
    fm.submit();
}


function SubmitFormReAgency()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("��¼��ͳ�����ͳ���£�");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("ͳ�����ʽΪ��YYYY����ͳ���¸�ʽΪ��MM�����������ȷ�ĸ�ʽ");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("��¼�����������룡");
			return ;
		}		
    fm.Opt.value="ReAgency";
    fm.all('distill').disabled = true;
    fm.submit();
}

function SubmitFormAdminAgent()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("��¼��ͳ�����ͳ���£�");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("ͳ�����ʽΪ��YYYY����ͳ���¸�ʽΪ��MM�����������ȷ�ĸ�ʽ");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("��¼�����������룡");
			return ;
		}		
    fm.Opt.value="AdminAgent";
    fm.all('distill').disabled = true;
    fm.submit();
}


function SubmitFormXiAgent()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("��¼��ͳ�����ͳ���£�");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("ͳ�����ʽΪ��YYYY����ͳ���¸�ʽΪ��MM�����������ȷ�ĸ�ʽ");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("��¼�����������룡");
			return ;
		}		
    fm.Opt.value="XiAgent";
    fm.all('distill').disabled = true;
    fm.submit();
}



function SubmitFormLWage()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("��¼��ͳ�����ͳ���£�");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("ͳ�����ʽΪ��YYYY����ͳ���¸�ʽΪ��MM�����������ȷ�ĸ�ʽ");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("��¼�����������룡");
			return ;
		}		
    fm.Opt.value="LWage";
    fm.all('distill').disabled = true;
    fm.submit();
}



function SubmitFormReB()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("��¼��ͳ�����ͳ���£�");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("ͳ�����ʽΪ��YYYY����ͳ���¸�ʽΪ��MM�����������ȷ�ĸ�ʽ");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("��¼�����������룡");
			return ;
		}		
    fm.Opt.value="ReB";
    fm.all('distill').disabled = true;
    fm.submit();
}


function SubmitFormYCom()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("��¼��ͳ�����ͳ���£�");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("ͳ�����ʽΪ��YYYY����ͳ���¸�ʽΪ��MM�����������ȷ�ĸ�ʽ");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("��¼�����������룡");
			return ;
		}		
    fm.Opt.value="YCom";
    fm.all('distill').disabled = true;
    fm.submit();
}



function SubmitFormXiAgent()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("��¼��ͳ�����ͳ���£�");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("ͳ�����ʽΪ��YYYY����ͳ���¸�ʽΪ��MM�����������ȷ�ĸ�ʽ");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("��¼�����������룡");
			return ;
		}		
    fm.Opt.value="XiAgent";
    fm.all('distill').disabled = true;
    fm.submit();
}


function SubmitFormYAgent()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("��¼��ͳ�����ͳ���£�");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("ͳ�����ʽΪ��YYYY����ͳ���¸�ʽΪ��MM�����������ȷ�ĸ�ʽ");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("��¼�����������룡");
			return ;
		}		
    fm.Opt.value="YAgent";
    fm.all('distill').disabled = true;
    fm.submit();
}

function SubmitFormAgeFee()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("��¼��ͳ�����ͳ���£�");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("ͳ�����ʽΪ��YYYY����ͳ���¸�ʽΪ��MM�����������ȷ�ĸ�ʽ");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("��¼�����������룡");
			return ;
		}		
    fm.Opt.value="AgentFee";
    fm.all('distill').disabled = true;
    fm.submit();
}

function SubmitFormBanksFee()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("��¼��ͳ�����ͳ���£�");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("ͳ�����ʽΪ��YYYY����ͳ���¸�ʽΪ��MM�����������ȷ�ĸ�ʽ");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("��¼�����������룡");
			return ;
		}		
    fm.Opt.value="BanksFee";
    fm.all('distill').disabled = true;
    fm.submit();
}