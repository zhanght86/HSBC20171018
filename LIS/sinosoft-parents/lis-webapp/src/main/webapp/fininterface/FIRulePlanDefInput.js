 //Creator :���	
//Date :2008-09-16

var showInfo;
var mDebug="0";
var arrDataSet;
window.onfocus=myonfocus;

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null) //shwoInfo��ʲô��
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


function VersionStateQuery()
{
	window.open("./FrameVersionRuleQuery.jsp");
}
function queryClick()
{
	var VersionNo = document.all('VersionNo').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��");
  	return;
  }
  showInfo=window.open("./FrameFIRulePlanDefQueryInput.jsp?VersionNo=" + VersionNo);
}

function FIRulePlanDefDetailInputClick()
{
	var VersionNo = document.all('VersionNo').value;
	var RulesPlanID = document.all('RulesPlanID').value;
	var VersionState = document.all('VersionState').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��Ȼ���ٽ���У��ƻ���ϸ����");
  	return;
  }
  
  if (RulesPlanID == null||RulesPlanID == '')  
  {
  	alert("���Ƚ���У��ƻ���Ϣ��ѯ��Ȼ���ٽ���У��ƻ���ϸ����");
  	return;
  }
  var countjudge = "";
  var strSQL = "";
  /**
  strSQL = "select count(*) from FIRulePlanDef where VersionNo = '"+document.all('VersionNo').value+"' and RulesPlanID = '"+document.all('RulesPlanID').value+"' ";
  */
  	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FIRulePlanDefInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("FIRulePlanDefInputSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(document.all('VersionNo').value);//ָ������Ĳ���
		mySql1.addSubPara(document.all('RulesPlanID').value);//ָ������Ĳ���
		strSQL= mySql1.getString();
  countjudge = easyQueryVer3(strSQL, 1, 0, 1); 
 	countjudge = trim(countjudge.substr(4));
  if(countjudge == "0")
  {
  	alert("����У��ƻ���Ϣ�����ڣ���ȷ�������Ӹ�����Ϣ���ٽ���У��ƻ���ϸ����");
  	return false;
  }
  showInfo=window.open("./FrameFIRulePlanDefDetailInput.jsp?VersionNo=" + VersionNo  + "&RulesPlanID="+RulesPlanID  + "&VersionState=" + VersionState);
}

function submitForm()
{
	if(!beforeSubmit2())
	{
		return false;
	}
  fm.OperateType.value = "INSERT||MAIN";
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
	
  fm.action = './FIRulePlanDefSave.jsp';
  document.getElementById("fm").submit(); //�ύ
  //alert(1)
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
	if((document.all('RulesPlanID').value=="")||(document.all('RulesPlanID').value=="null"))
	 {
	  alert("��ȷ��Ҫɾ����У��ƻ����룡����");
	  return false
	 }
	 if((document.all('RulesPlanName').value=="")||(document.all('RulesPlanName').value==null))
	 {
	  alert("У��ƻ����Ʋ���Ϊ�գ�");
	  return false
	 }
	 if((document.all('RulePlanState').value=="")||(document.all('RulePlanState').value==null))
	 {
	  alert("У��ƻ�״̬����Ϊ�գ�");
	  return false
	 }
	 if((document.all('CallPointID').value=="")||(document.all('CallPointID').value==null))
	 {
	  alert("���ýڵ㲻��Ϊ�գ�");
	  return false
	 }
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
  {
    fm.OperateType.value = "UPDATE||MAIN";
    var i = 0;
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
showInfo.focus();

    fm.action = './FIRulePlanDefSave.jsp';
    document.getElementById("fm").submit(); //�ύ

  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ�����޸Ĳ�����");
  }
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
	if((document.all('RulesPlanID').value=="")||(document.all('RulesPlanID').value=="null"))
	 {
	  alert("��ȷ��Ҫɾ����У��ƻ����룡����");
	  return false
	 }
  if (confirm("��ȷʵҪɾ���ü�¼��"))
  {
    fm.OperateType.value = "DELETE||MAIN";
    var i = 0;
    var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ�����������Ľ���";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
showInfo.focus();

    fm.action = './FIRulePlanDefSave.jsp';
    document.getElementById("fm").submit();//�ύ

  }
}
//...׷�ӱ��
function afterQuery1( arrQueryResult1 )
{
	var arrResult1 = new Array();
	if( arrQueryResult1 != null )
	{

		arrResult1 = arrQueryResult1;
		document.all('RulesPlanID').value = arrResult1[0][1];
		document.all('RulesPlanName').value = arrResult1[0][2];
		document.all('RulePlanState').value = arrResult1[0][3];
		document.all('CallPointID').value = arrResult1[0][4];
		document.all('MarkInfo').value = arrResult1[0][5];
		document.all('Sequence').value = arrResult1[0][6];
		
		document.all('addbutton').disabled = false;
		document.all('updatebutton').disabled = false;		
		document.all('deletebutton').disabled = false;
		
		if (document.all('VersionState').value == "01"||document.all('VersionState').value == "03"||document.all('VersionState').value == ""||document.all('VersionState').value == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}	
		showCodeName(); 	
	}
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //�ͷš����ӡ���ť

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
    fm.OperateType.value="";
    //resetForm();
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
		if(fm.OperateType.value == "DELETE||MAIN")
		{
			document.all('RulesPlanID').value = '';        
    	document.all('RulesPlanName').value = '';
    	document.all('RulePlanState').value = '';        
    	document.all('RulePlanStateName').value = '';  
    	document.all('CallPointID').value = '';        
   	  document.all('CallPointIDName').value = '';    
    	document.all('MarkInfo').value = '';
    	
    	document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true; 
		}
		if(fm.OperateType.value == "INSERT||MAIN")
		{
			document.all('RulesPlanID').value = '';        
    	document.all('RulesPlanName').value = '';
    	document.all('RulePlanState').value = '';        
    	document.all('RulePlanStateName').value = '';  
    	document.all('CallPointID').value = '';        
   	  document.all('CallPointIDName').value = '';    
    	document.all('MarkInfo').value = '';
    	
    	document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true; 
		}
    fm.OperateType.value="";
  }
	
}

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();
		//alert(arrQueryResult);

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('VersionNo').value = arrResult[0][0];
		document.all('VersionState').value = arrResult[0][5];		
		document.all('VersionState2').value = arrResult[0][9];	
		
			document.all('RulesPlanID').value = '';        
    	document.all('RulesPlanName').value = '';
    	document.all('RulePlanState').value = '';        
    	document.all('RulePlanStateName').value = '';  
    	document.all('CallPointID').value = '';        
   	  document.all('CallPointIDName').value = '';    
    	document.all('MarkInfo').value = '';	
    //���汾״̬��Ϊ02-ά����ʱ����ɾ�İ�ťΪ��ɫ		
		if (arrResult[0][5] == "01"||arrResult[0][5] == "03"||arrResult[0][5] == ""||arrResult[0][5] == null)
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
		if (arrResult[0][5] == "02")
		{
			document.all('addbutton').disabled = false;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}
		
		
		//������Common\javascript\Common.js�����ݴ���ѡ��Ĵ�����Ҳ���ʾ����
		showCodeName(); 

	}
}

function beforeSubmit()
  {
  	if((fm.VersionNo.value=="")||(fm.VersionNo.value=="null"))
	 {
	 	alert("����ͨ����ѯȡ�ð汾��ţ�����");
	 	return false;
	 }
	 if((document.all('RulesPlanID').value=="")||(document.all('RulesPlanID').value=="null"))
	 {
	  alert("У��ƻ����벻��Ϊ�գ�����");
	  return false
	 }
	 if((document.all('RulesPlanName').value=="")||(document.all('RulesPlanName').value==null))
	 {
	  alert("У��ƻ����Ʋ���Ϊ�գ�");
	  return false
	 }
	 if((document.all('RulePlanState').value=="")||(document.all('RulePlanState').value==null))
	 {
	  alert("У��ƻ�״̬����Ϊ�գ�");
	  return false
	 }
	 if((document.all('CallPointID').value=="")||(document.all('CallPointID').value==null))
	 {
	  alert("���ýڵ㲻��Ϊ�գ�");
	  return false
	 }
	 return true;
  }
  
  function beforeSubmit2()
  {
  	if((fm.VersionNo.value=="")||(fm.VersionNo.value=="null"))
	 {
	 	alert("����ͨ����ѯȡ�ð汾��ţ�����");
	 	return false;
	 }
	 if((document.all('RulesPlanID').value!=""))
	 {
	  alert("У��ƻ������Ѿ����ڣ���ˢ��ҳ����ٽ��д˲���������");
	  return false
	 }
	 if((document.all('RulesPlanName').value=="")||(document.all('RulesPlanName').value==null))
	 {
	  alert("У��ƻ����Ʋ���Ϊ�գ�");
	  return false
	 }
	 if((document.all('RulePlanState').value=="")||(document.all('RulePlanState').value==null))
	 {
	  alert("У��ƻ�״̬����Ϊ�գ�");
	  return false
	 }
	 if((document.all('CallPointID').value=="")||(document.all('CallPointID').value==null))
	 {
	  alert("���ýڵ㲻��Ϊ�գ�");
	  return false
	 }
	 return true;
  }
  
  function resetAgain()
{
			document.all('RulesPlanID').value = '';        
    	document.all('RulesPlanName').value = '';
    	document.all('RulePlanState').value = '';        
    	document.all('RulePlanStateName').value = '';  
    	document.all('CallPointID').value = '';        
   	  document.all('CallPointIDName').value = '';    
    	document.all('MarkInfo').value = '';
   	
   		document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true; 
}