//���¼�¼�� ��ʼ����������ҳ��
//������:  
//��������:  2008-9-17 
//����ԭ��/���ݣ�

var showInfo;

function returnParent(){
	document.location.href="CommandMain.jsp"; 
}

function submitForm() {
	// �ύǰ�ļ���
	if(beforeSubmit()){
		var i = 0;
		var showStr = "�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
		//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		document.getElementById("fm").submit(); // �ύ	
		//showInfo.close();	
		
	}else{	
	
	}
}

// �ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {
	//alert(FlagStr);
	showInfo.close();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+ content;	
		if(confirm("���������ɹ�,�Ƿ�������?")){
			document.location.href="CommandAddInput.jsp";		
		}else{
			returnParent();			
		}	
	}
	
}

// ���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm() {
	try {
		initForm();
	} catch (re) {
		alert("��CommandAddInput.js-->resetForm�����з����쳣:��ʼ���������!");
	}
}

// ȡ����ť��Ӧ����
function cancelForm() {
	// window.location="../common/html/Blank.html";
}

// �ύǰ��У�顢����
function beforeSubmit() {
	// ���ǰУ��		
	if (document.all('Name').value.trim() == '') {
		alert("��������Ʋ���Ϊ��!");
		return false;
	}
	if (document.all('display').value.trim() == '') {
		alert("����չʾ����Ϊ��!");
		return false;
	}
	if (document.all('implenmation').value.trim() == '') {
		alert("����ʵ�ֲ���Ϊ��!");
		return false;
	}
	if (document.all('Valid').value == '') {
		alert("��Ч�Բ���Ϊ��!");
		return false;
	}
	if (document.all('commandType').value == '') {
		alert("�����������Ͳ���Ϊ��!");
		return false;
	}
	if (document.all('resulttype').value == '') {
		alert("���������Ͳ���Ϊ��!");
		return false;
	}
	document.all("Transact").value = "INSERT";
	return true;
}

function afterCodeSelect( cCodeName, Field ) {
  
    
    if(cCodeName=="IbrmsCommType")
    {
    		if(Field.value=='0')
    		{
    			CommDetailDiv.style.display = "none";
    			document.all('CommDetail').value = "";
    		}
    		else
    		{
    			CommDetailDiv.style.display = "";
    		}
    }
}