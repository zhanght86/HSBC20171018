//���¼�¼�� showAllMenuInUnselect()��showMenuGrp()
//������:  ����
//��������:  2005-5-4  
//����ԭ��/���ݣ�����ҳ��Ȩ�޲˵�

var turnPage = new turnPageClass();
var mySql = new SqlClass();
function queryClick()
{
	var UserCode = document.all("UserCode").value;
	var ComCode = document.all("ComCode").value;
	if(UserCode=="") 
  {
  	alert("��������Ҫ��ѯ�Ĺ���Ա���룬�ٽ��в�ѯ��");
   	return ;
  }
	
	
	// ��дSQL���
	/*var sqlStr="select Operator,MenuGrpCode,MenuGrpName,MenuGrpDescription " +
						 " from LDMenuGrp where Operator = '" + UserCode + "' and MenuGrpCode in " +
						 " (select MenuGrpCode from LDUserTOMenuGrp where usercode in " +
						 " (select usercode from lduser where comcode like '" + ComCode + "%%'))";*/
	mySql = new SqlClass();
	mySql.setResourceName("menumang.menuGrpNewSql");
	mySql.setSqlId("menuGrpNewSql1");
	mySql.addSubPara(UserCode); 
	mySql.addSubPara(ComCode); 
	//alert(sqlStr);
						 
	turnPage.queryModal(mySql.getString(), QueryGrpGrid);
}


//�ύǰ���б�Ҫ�ļ��
function DataCopy()
{
	//�õ���ѡ�е�����
	getInput();
	//alert("1111111111111");
	if(document.all("MenuGrpCode").value == "" || document.all("MenuGrpCode").value == null)
	{
		alert("���������µĲ˵�������룡");
		return;
	}
	//���̨�ύ����
  submitForm();
    
}


//�ύ�����水ť��Ӧ����
function submitForm()
{
	var i = 0;
	var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); //�ύ
}

function afterSubmit(FlagStr)
{
    showInfo.close();
    
    if (FlagStr == "success")
    	alert("���Ʋ˵���ɹ���");
    else
    	alert("���Ʋ˵���ʧ�ܣ����ܵ�ԭ���Ǵ˲˵����Ѵ��ڣ�");  
    	
    clear();	
}

function getInput()
{
	var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<QueryGrpGrid.mulLineCount; i++) 
  {
   if (QueryGrpGrid.getSelNo(i)) 
   { 
      checkFlag = QueryGrpGrid.getSelNo();
      break;
   }
  }
  
  if (checkFlag != 0) 
  { 
    var	Action = QueryGrpGrid.getRowColData(checkFlag - 1, 2); 	
    //alert("ѡ�еĲ˵�������룺" + Action);
    document.all("Action").value = Action;
  }
  else 
  {
    alert("����ѡ��һ��Ҫ���ƵĲ˵�����Ϣ��"); 
  }
}
  
function clear()
{
	document.all("MenuGrpName").value = "";
	document.all("MenuGrpCode").value = "";
	document.all("MenuGrpDescription").value = "";
	document.all("MenuSign").value = "";
}










