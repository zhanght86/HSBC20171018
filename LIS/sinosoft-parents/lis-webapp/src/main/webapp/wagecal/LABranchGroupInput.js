//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var mDebug="0";
var mOperate="";
var showInfo;
var tResourceName="wagecal.LABranchGroupInputSql";
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
  if (!beforeSubmit())
    return false;
  if(mOperate=="INSERT||MAIN")
  {
  	if(!CheckBranchValid())
  		return false;
  }
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  fm.hideOperate.value=mOperate;
  if (fm.hideOperate.value=="")
  {
    alert("�����������ݶ�ʧ��");
  }
  //showSubmitFrame(mDebug);
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
    //alert(content);
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
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
    //ִ����һ������
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
	  initForm();
  }
  catch(re)
  {
  	alert("��LABranchGroup.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
 if (!verifyInput()) return false;
  //alert(mOperate);
  if (trim(mOperate) == 'INSERT||MAIN')
  {
    //У����ʽչҵ��������  
    var strSQL = "";
    /*strSQL = "select AgentGroup from LABranchGroup where 1=1 and (state<>'1' or state is null)"
	    + getWherePart('BranchAttr')
	    + getWherePart('BranchType')
	    +" Union "
	    +"Select AgentGroup From LABranchGroupB Where 1=1 and (state<>'1' or state is null)"
	    + getWherePart('BranchAttr')
	    + getWherePart('BranchType');*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('BranchAttr').value,document.all('BranchType').value,document.all('BranchAttr').value,document.all('BranchType').value]);
    var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
    //alert(strSQL);
    if (strQueryResult) {  	
  	alert('��չҵ���������Ѵ��ڣ�');
  	document.all('BranchAttr').value = '';
  	return false;
    }
  }
  //tongmeng 2007-04-28 add
  //���ӻ��������У��
  var tBranchLevel = document.all('BranchLevel').value;
  if(tBranchLevel == '05')
  {
  	alert("��������Υ��!");
  	document.all('BranchLevel').value='';
  	return false;
  }
  return true;
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


//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  mOperate="INSERT||MAIN";
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
  if(document.all('EndFlag').value=='Y')
  {
   initForm();
  }
   document.all('BranchManager').value ='';
  document.all('BranchManagerName').value ='';
   document.all('AgentGroup').disabled = false;
   document.all('Name').disabled = false;                                             
		    document.all('ManageCom').disabled = false;                                              
			document.all('UpBranch').disabled = false;                     
        	document.all('BranchAttr').disabled = false; 
			document.all('BranchType').disabled = false;                                             
			document.all('BranchLevel').disabled = false;                                           
			document.all('BranchManager').disabled = false;                                          
			document.all('BranchAddress').disabled = false;                                              
			document.all('BranchPhone').disabled = false;                                             
			document.all('BranchFax').disabled = false;                                             
			document.all('BranchZipcode').disabled = false;                                            
			document.all('FoundDate').disabled = false;                                             
			document.all('EndDate').disabled = false;                                             
			document.all('EndFlag').disabled = false;                                              
			document.all('FieldFlag').disabled = false;                                             
			document.all('UpBranchAttr').disabled = false;                                               
			document.all('Operator').disabled = false;                                              
			document.all('BranchManagerName').disabled = false;
			
  
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  
  if ((document.all("BranchAttr").value==null)||(trim(document.all("BranchAttr").value)==''))
    alert("��ȷ��Ҫ�޸ĵ����۵�λ��");
  else
  { 
    if (!queryBranchCode())
      return false;
    if (confirm("��ȷʵ���޸ĸü�¼��?"))
    {
      mOperate="UPDATE||MAIN";
      submitForm();
    }
    else
    {
      mOperate="";
      alert("��ȡ�����޸Ĳ�����");
    }
  }
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  mOperate="QUERY||MAIN";
  showInfo=window.open("./LABranchGroupQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ��ɾ������
  if ((document.all("BranchAttr").value==null)||(trim(document.all("BranchAttr").value)==''))
    alert("��ȷ��Ҫɾ�������۵�λ��");
  else
  {
    if (!queryBranchCode())
      return false;
    if (!queryAgent())
      return false;
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

function afterQuery(arrQueryResult)
{	
	var arrResult = new Array();
	//alert('11');
	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;

		document.all('AgentGroup').value = arrResult[0][0];  
		document.all('Name').value = arrResult[0][1];                                              
		document.all('ManageCom').value = arrResult[0][2];                                              
		document.all('UpBranch').value = arrResult[0][3];                         
        document.all('BranchAttr').value = arrResult[0][4];   
		document.all('BranchType').value = arrResult[0][5];                                             
		document.all('BranchLevel').value = arrResult[0][6];                                           
		document.all('BranchManager').value = arrResult[0][7];                                           
		document.all('BranchAddress').value = arrResult[0][9];                                               
		document.all('BranchPhone').value = arrResult[0][10];                                               
		document.all('BranchFax').value = arrResult[0][11];                                               
		document.all('BranchZipcode').value = arrResult[0][12];                                               
		document.all('FoundDate').value = arrResult[0][13];                                               
		document.all('EndDate').value = arrResult[0][14];                                               
		document.all('EndFlag').value = arrResult[0][15];                                               
		document.all('FieldFlag').value = arrResult[0][17];                                               
		document.all('UpBranchAttr').value = arrResult[0][25];                                                  
		document.all('Operator').value = arrResult[0][19];                                                 
		document.all('BranchManagerName').value = arrResult[0][24];
		
		//add by jiaqiangli 2007-11-26 
		//���²�ѯBranchManagerName
		//var tsql="select name from laagent where agentcode = '"+arrResult[0][7]+"' ";
		var tsql = wrapSql(tResourceName,"querysqldes2",[arrResult[0][7]]);
                var tQueryResult = easyQueryVer3(tsql, 1, 1, 1); 
                var tarr;
                if(tQueryResult) {
                	tarr = decodeEasyQueryResult(tQueryResult);
                	document.all('BranchManagerName').value = tarr[0][0];
                }   
		
		if(arrResult[0][15]=='Y')
		{
			document.all('AgentGroup').disabled = true;
		    document.all('Name').disabled = true;                                             
		    document.all('ManageCom').disabled = true;                                              
			document.all('UpBranch').disabled = true;                     
        	document.all('BranchAttr').disabled = true; 
			document.all('BranchType').disabled = true;                                             
			document.all('BranchLevel').disabled = true;                                           
			document.all('BranchManager').disabled = true;                                          
			document.all('BranchAddress').disabled = true;                                              
			document.all('BranchPhone').disabled = true;                                             
			document.all('BranchFax').disabled = true;                                             
			document.all('BranchZipcode').disabled = true;                                            
			document.all('FoundDate').disabled = true;                                             
			document.all('EndDate').disabled = true;                                             
			document.all('EndFlag').disabled = true;                                              
			document.all('FieldFlag').disabled = true;                                             
			document.all('UpBranchAttr').disabled = true;                                               
			document.all('Operator').disabled = true;                                              
			document.all('BranchManagerName').disabled = true;

			}                                                                                                                                                                                                                                              	
 	}   
}

function queryBranchCode()
{  
  // ��дSQL���
  var strSQL = "";
  /*strSQL = "select AgentGroup,UpBranch from LABranchGroup where 1=1 and (EndFlag is null or EndFlag <> 'Y') and (state<>'1' or state is null)"
	  + getWherePart('BranchAttr')
	  + getWherePart('BranchType');*/
  strSQL = wrapSql(tResourceName,"querysqldes3",[document.all('BranchAttr').value,document.all('BranchType').value]);
	        
  var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	
  if (!strQueryResult) {
    alert("��������Ҫ���������۵�λ��");
    document.all("BranchAttr").value = '';
    document.all('AgentGroup').value = '';
    document.all('UpBranch').value = '';
    return false;
  }
  var arr = decodeEasyQueryResult(strQueryResult);
  document.all('AgentGroup').value = arr[0][0];
  document.all('UpBranch').value = arr[0][1];
  return true;
}

function queryAgent()
{
    var strSQL="";	
    /*strSQL="select AgentCode from LAAgent where 1=1 and (AgentState is null or AgentState < '03') "
            +getWherePart('AgentGroup','AgentGroup');*/
    strSQL = wrapSql(tResourceName,"querysqldes4",[document.all('AgentGroup').value]);
	        
    var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	
    if (strQueryResult) {
    	alert('�����۵�λ�»���ҵ��Ա��');
    	return false;
    }
    strQueryResult = null;
    /*strSQL = "select AgentGroup from LABranchGroup where 1=1 and (EndFlag is null or EndFlag <> 'Y') and (state<>'1' or state is null)"
	  + getWherePart('UpBranch','AgentGroup')
	  + getWherePart('BranchType');*/
    strSQL = wrapSql(tResourceName,"querysqldes5",[document.all('AgentGroup').value,document.all('BranchType').value]);
	        
    strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
    if (strQueryResult) {
    	alert('�����۵�λ�»�������������');
    	return false;
    }
    return true;
} 

function CheckBranchValid()
{
	if((document.all('BranchAttr').value==null)||(document.all('BranchAttr').value==""))
	{
		alert("�������벻��Ϊ�գ�");
		return false;
	}
	/*var sql="select AgentGroup from LABranchGroup where 1 = 1 and (state<>'1' or state is null)" 
	        + getWherePart('BranchAttr','BranchAttr')
	        + getWherePart('BranchType') ;*/
	var sql = wrapSql(tResourceName,"querysqldes6",[document.all('BranchAttr').value,document.all('BranchType').value]);
	strQueryResult  = easyQueryVer3(sql, 1, 0, 1);  
    	if (strQueryResult) 
    	{
    		alert('�û����Ѿ����ڣ�ֻ�ܸ��»�����ɾ����¼�룡');
    		return false;
    	}
    return true;
}
/*
function changeManager()
{
    if (getWherePart('AgentCode','BranchManager')=='')
      return false;
      
    var strSQL="";	
    strSQL="select AgentCode from LAAgent where 1=1 and (AgentState is null or AgentState < '03') "
            +getWherePart('AgentCode','BranchManager');	 
    //alert(strSQL);
    var strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	
    if (!strQueryResult) {
    	alert('���������Ч��');
    	document.all('BranchManager').value='';
    	return false;
    }
}*/
