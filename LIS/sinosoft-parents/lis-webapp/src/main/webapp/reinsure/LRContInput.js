var showInfo;
var turnPage = new turnPageClass(); 
window.onfocus=myonfocus;
var typeRadio ;
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
	fm.OperateType.value="INSERT";
	try 
	{
		if( verifyInput() == true) 
		{
			if ((veriryInput3()==true)&&(veriryInput7()==true))
			{
		  	var i = 0;
		  	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRContSave.jsp?ModType="+typeRadio;
		  	fm.submit(); //�ύ
	  	}
	  }
  } catch(ex) 
  {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function veriryInput2()
{
	if(compareDate(fm.RValidate.value,fm.RInvalidate.value)==1)
	{
		myAlert(""+"��ʼ���ڲ��ܴ�����ֹ����!"+"");
		return false;
	}
	
  var rowNum=SignerGrid. mulLineCount ; //���� 
	
	for(var i=0;i<rowNum;i++)
	{
		num=i+1;
		
		if(SignerGrid.getRowColData(i,1)==0||SignerGrid.getRowColData(i,1)==null)
		{
			myAlert(""+"��"+""+num+""+"��"+","+"��¼���ͬǩ�������ڹ�˾�����ƣ�"+"");
			return false;
		}
		if(SignerGrid.getRowColData(i,3)==0||SignerGrid.getRowColData(i,3)==null)
		{
			myAlert(""+"��"+""+num+""+"��"+","+"��¼���ͬǩ���˵�������"+"");
			return false;
		}
						
		if(!isTel(SignerGrid.getRowColData(i,5)))
		{
			myAlert(""+"��"+""+num+""+"��"+","+"�绰¼���ʽ���ԣ�"+"");
			return false;
		}
		if(!isTel(SignerGrid.getRowColData(i,6)))
		{
			myAlert(""+"��"+""+num+""+"��"+","+"�ֻ�¼���ʽ���ԣ�"+"");
			return false;
		}
		if(!isTel(SignerGrid.getRowColData(i,7)))
		{
			myAlert(""+"��"+""+num+""+"��"+","+"����¼���ʽ���ԣ�"+"");
			return false;
		}
		if(!isEMail(SignerGrid.getRowColData(i,8)))
		{
			myAlert(""+"��"+""+num+""+"��"+","+"��������¼���ʽ���ԣ�"+"");
			return false;
		}
	}
	return true; 
}

function veriryInput3()
{
	if(typeRadio=='1'){
		if(fm.ModRIContNo.value==null||fm.ModRIContNo.value==''||fm.ModRIContName.value==null||fm.ModRIContName.value==''){
			myAlert(''+"��ͬ�޸�ģʽ"+'"�޸ĺ�ͬ����""+"��"+""�޸ĺ�ͬ����""+"����Ϊ��"+" ');
			return false;
		}
	}
	if(typeRadio=='1'){
		if(fm.ModRIContNo.value==null||fm.ModRIContNo.value==''||fm.ModRIContName.value==null||fm.ModRIContName.value==''){
			myAlert(''+"��ͬ�޸�ģʽ"+'"�޸ĺ�ͬ����""+"��"+""�޸ĺ�ͬ����""+"����Ϊ��"+" ');
			return false;
		}
	}
	if(compareDate(fm.RValidate.value,fm.RInvalidate.value)==1)
	{
		myAlert(""+"��ʼ���ڲ��ܴ�����ֹ����!"+"");
		return false;
	}
  var rowNum=SignerGrid. mulLineCount ; //���� 
	
	for(var i=0;i<rowNum;i++)
	{
		num=i+1;
		
		if(SignerGrid.getRowColData(i,1)==0||SignerGrid.getRowColData(i,1)==null)
		{
			myAlert(""+"��"+""+num+""+"��"+","+"��¼���ͬǩ�������ڹ�˾�����ƣ�"+"");
			return false;
		}
		
		if(SignerGrid.getRowColData(i,3)==0||SignerGrid.getRowColData(i,3)==null)
		{
			myAlert(""+"��"+""+num+""+"��"+","+"��¼���ͬǩ���˵�������"+"");
			return false;
		}
		
		if(SignerGrid.getRowColData(i,4)==0||SignerGrid.getRowColData(i,4)==null)
		{
			myAlert(""+"��"+""+num+""+"��"+","+"��¼���ͬǩ���˵�ְ��"+"");
			return false;
		}
						
		if(!isTel(SignerGrid.getRowColData(i,5)))
		{
			myAlert(""+"��"+""+num+""+"��"+","+"�绰¼���ʽ���ԣ�"+"");
			return false;
		}
		if(!isTel(SignerGrid.getRowColData(i,6)))
		{
			myAlert(""+"��"+""+num+""+"��"+","+"�ֻ�¼���ʽ���ԣ�"+"");
			return false;
		}
		if(!isTel(SignerGrid.getRowColData(i,7)))
		{
			myAlert(""+"��"+""+num+""+"��"+","+"����¼���ʽ���ԣ�"+"");
			return false;
		}
		if(!isEMail(SignerGrid.getRowColData(i,8)))
		{
			myAlert(""+"��"+""+num+""+"��"+","+"��������¼���ʽ���ԣ�"+"");
			return false;
		}
	}
	return true;
}

function queryClick()
{
  fm.OperateType.value="QUERY";
  window.open("./FrameReContQuery.jsp?RIContNo="+fm.RIContNo.value+"&PageFlag=CONT");  //?RIContNo="+fm.RIContNo.value+"&PageFlag=CONT
}

function ChooseOldCont(){
	for(i = 0; i <fm.ContModType.length; i++)
	{
		if(fm.ContModType[i].checked)
		{
			typeRadio=fm.ContModType[i].value;
			break;
		}
	}
	if(typeRadio=='0'){
		divTitle2.style.display='none';
		divInput2.style.display='none';
		fm.ModRIContNo.value='';
		fm.ModRIContName.value='';
	}
	if(typeRadio=='1'){
		divTitle2.style.display='';
		divInput2.style.display='';
		fm.OperateType.value="CHOICE";
  	window.open("./FrameRIChoiceQuery.jsp"); 
	}
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content, ContentNO, ReComCode, CertifyCode)
{
  showInfo.close();
  if (FlagStr == "Fail" ) {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    
  } else { 
  	fm.RIContNo.value = ContentNO;
	  //content="����ɹ���";
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  if (fm.OperateType.value=="DELETE")
	  {
	  	resetForm();
	  }
  }
}
//�޸�
function updateClick()
{
	fm.OperateType.value="UPDATE";
	if(!confirm(""+"��ȷ��Ҫ�޸ĸú�ͬ��"+"")){
		return false;
	}
	try {
		if( verifyInput() == true ) {
			if ((veriryInput2()==true)&&(veriryInput7()==true)&&(checkContState()==true))
			{
		  	var i = 0;
		  	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
				fm.action="./LRContSave.jsp?ModType="+typeRadio;
		  	fm.submit(); //�ύ
	  	}
	  	else{
	  	}
	  }
  } catch(ex) 
  {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function checkContState(){
	var tRIContNo=fm.RIContNo.value;
	var tContState=fm.ContState.value;
	if(tContState=="0"){
		try{
		var mySql100=new SqlClass();
			mySql100.setResourceName("reinsure.LRContInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql100.setSqlId("LRContInputSql100");//ָ��ʹ�õ�Sql��id
			mySql100.addSubPara(tRIContNo);//ָ������Ĳ���
	    var tSql=mySql100.getString();
		
		//var tSql="select count(*) from RIPrecept where ricontno='"+tRIContNo+"' and state = '01'";	
		var  result = easyExecSql(tSql, 1, 0, 1);
		
		if(result[0][0]!="0"){
			myAlert(""+"����ͬ����δ��Ч�ٱ�����������������ٱ�����Ϊ��Ч���޸�"+"");
			return false;
			}
		}catch(ex){
			myAlert(ex);
			return false;
		}	
	}
	
	return true;
	
}

function veriryInput4() //UPDATE У��
{
	if(compareDate(fm.RValidate.value,fm.RInvalidate.value)==1){
		myAlert(""+"��ʼ���ڲ��ܴ�����ֹ����!"+"");
		return false;
	}
	return true;
	
}

//ɾ��
function deleteClick()
{
	fm.OperateType.value="DELETE";
	if(!confirm(""+"��ȷ��Ҫɾ���ú�ͬ��"+""))
	{
		return false;
	}
	try 
	{
		if( verifyInput() == true ) 
		{
			if (veriryInput5()==true)
			{
		  	var i = 0;
		  	var showStr=""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRContSave.jsp";
		  	fm.submit(); //�ύ
	  	}
	  	else
	  	{
	  	}
	  }
  } catch(ex) 
  {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function veriryInput5(){
	return true;
}

function afterQuery()
{
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
  	myAlert(""+"��CertifySendOutInput.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
  }
} 

//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           

function nextStep()
{
	if (fm.RIContNo.value==''||fm.RIContNo.value==null)
	{
		myAlert(""+"���Ȳ�ѯ�ٱ���ͬ����ӷֱ���Ϣ��"+"");
		return false;
	}
	var mySql101=new SqlClass();
			mySql101.setResourceName("reinsure.LRContInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql101.setSqlId("LRContInputSql101");//ָ��ʹ�õ�Sql��id
			mySql101.addSubPara(fm.RIContNo.value);//ָ������Ĳ���
	    var strSQL=mySql101.getString();
	//var strSQL="select * from RIBarGainInfo where RIContNo='"+fm.RIContNo.value+"'";
	var arrResult=easyExecSql(strSQL);
	if (arrResult==null||arrResult=="")
	{
		myAlert(""+"��ͬ���Ϊ�ջ�δ�����ͬ������Ϣ��"+"");
		return false;
	}
	var reContCode = fm.all('RIContNo').value;
  var varSrc="&ReContCode='"+ reContCode+"'" ;
  window.open("./FrameMainCessInfo.jsp?Interface=LRSchemaInput.jsp"+varSrc,"true"); //+varSrc,
}  

//�����������ʽ�ǲ�����ȷ
function isEMail(strValue)
{
  var NUM1="@";
  var NUM2=".";
  var i;
  if(strValue==null || strValue=="") return true;
  for(i=0;i<strValue.length;i++)
  {
    if(strValue.indexOf(NUM1)<0)
    {
    	return false;
    }
    if(strValue.indexOf(NUM2)<0)
    {
    	return false;
    }
  }
  return true;
}

//������ĵ绰���������֤
function isTel(strValue){
  var NUM="0123456789-() ";
  var i;
  if(strValue==null || strValue=="") return true;
  for(i=0;i<strValue.length;i++)
  {
    if(NUM.indexOf(strValue.charAt(i))<0) return false;

  }
  return true;
}

//Ҫ��У��
function veriryInput7()
{
	//У�鲻��¼���ظ���ͬҪ��
	for(var n=0;n<FactorGrid.mulLineCount;n++) 
	{ 
	   for(var m=n+1;m<FactorGrid.mulLineCount;m++) 
	   { 
	     if(FactorGrid.getRowColData(n,1)==FactorGrid.getRowColData(m,1)) 
	     {
	         myAlert(""+"����¼���ظ�Ҫ��!"+"");	
	         return false; 
	     }
	   }
	}    
	for(var n=0;n<FactorGrid.mulLineCount;n++) { 
	   for(var m=n+1;m<FactorGrid.mulLineCount;m++) { 
	     if(FactorGrid.getRowColData(n,2)==FactorGrid.getRowColData(m,2)) {
	         myAlert(""+"����¼���ظ�Ҫ��!"+"");	
	         return false; 
	     }
	   }
	}  
	isNull=false; //����Ƿ�¼��Ҫ��ֵ���
	for(var i=0;i<FactorGrid.mulLineCount;i++) {
		if(FactorGrid.getRowColData(i,3)==null||FactorGrid.getRowColData(i,3)==""){
			isNull=true;
		}
	}
	if(isNull){
		myAlert(""+"����¼��Ҫ��ֵ!"+"");
		return false;
	}
	return true;
}
