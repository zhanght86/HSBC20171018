//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RICataRisk.js
//�����ܣ����ֱ���
//�������ڣ�2011-6-29
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
var reg=/^[0-9]*\.?[0-9]*$/;
var sqlresourcename = "reinsure.RICataRiskInputSql";


//��  �水ť
function button134( )
{
	fm.OperateType.value = "INSERT";
	try {
		if(!reg.test(fm.RateFee.value)){
			 myAlert(""+"���ַ��ʱ���Ϊ�������ͣ�"+"");
			 return;
			}
			if(fm.MakeDate.value==""||fm.MakeDate.value==null){
			   myAlert(""+"ά�����ڲ���Ϊ�գ�"+"");
			   return 
			}
			var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action="./RICataRiskSave.jsp";
			fm.submit(); // �ύ
	}catch (ex) {
		showInfo.close();
		myAlert(ex);
	}
}

//��  ѯ��ť
function button135( )
{
	if(fm.MakeDateB.value!=""){
		
		
		var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RICataRiskInputSql1");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.MakeDateB.value);// ָ������Ĳ������������˳�����
	var sql = mySql.getString();
		turnPage.queryModal(sql,Mul13Grid);
	}
	else{
		var mySql1 = new SqlClass();
	mySql1.setResourceName(sqlresourcename);
	mySql1.setSqlId("RICataRiskInputSql1");//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara("");
	var sql = mySql1.getString();
		
		turnPage.queryModal(sql,Mul13Grid);	
	}
}

//ͳ   �ư�ť
function button136( )
{
	try 
	{
		//if (verifyInput() == true) {
			//if (verifyInput2() == true) {
	     fm.target = "importCessData"; 
	     fm.action = "LPrtPrintCataRiskSave.jsp?tableType="+fm.tableType.value;
	     fm.submit();
		//	}
	//	}

  } 
  catch(ex) 
  {
  
  	myAlert(ex);
  }
}


//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ��� 
}

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

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  { 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
	var  sql="select r.RateFee,r.MakeDate,r.SerialNo from RIBIGRATEFEE r order by r.MakeDate DESC";
	turnPage.queryModal(sql,Mul13Grid);	
  }
}

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
function showCateRate(){
	var tSel = Mul13Grid.getSelNo();	
	fm.RateFee.value = Mul13Grid.getRowColData(tSel-1,1);
	fm.MakeDate.value = Mul13Grid.getRowColData(tSel-1,2);
	fm.SerialNo.value= Mul13Grid.getRowColData(tSel-1,3);
}
function deleteMess(){
	fm.OperateType.value = "DELETE";
	try {
			if(fm.MakeDate.value==""||fm.MakeDate.value==null){
			   myAlert(""+"ά�����ڲ���Ϊ�գ�"+"");
			   return 
			}
			var showStr = ""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
			showInfo = window
					.showModelessDialog(urlStr, window,
							"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			fm.action="./RICataRiskSave.jsp";
			fm.submit(); // �ύ
	}catch (ex) {
		showInfo.close();
		myAlert(ex);
	}	
}
