//���¼�¼�� queryClick(),fillUserGrid(),insertClick()
//������:  ����
//��������:  2005-5-4  
//����ԭ��/���ݣ����Ӳ˵�����ʱ���߼��жϡ�

var showInfo;

//���ƽ����ϵ�mulLine����ʾ����
var mulLineShowCount = 15;

var sqlcount = 0;

var selectArray = new Array();
var unselectArray = new Array();

var userArray = new Array();
var userArrayLen = 0; 
var userCurPage = 0;	

var removeArray = new Array() //���²˵����е�ɾ����¼
var showDiv = "false";

var turnPage = new turnPageClass();   
var mySql = new SqlClass();
//��ѯ���г����в˵���¼
function queryClick()
{
	document.all("checkbox1").checked = false;
	document.all("checkbox2").checked = false;
	if(document.all("NodeName").value.length ==0){
	//	var sqlStr = "select nodecode,ChildFlag,nodename,parentnodecode,runscript,nodeorder,nodesign from LDMenu order by nodeorder";  
	
	mySql = new SqlClass();
	mySql.setResourceName("menumang.menuFunManSql");
	mySql.setSqlId("menuFunManSql1");
	mySql.addSubPara(""); 
	
	}else{
		/*var sqlStr = "select nodecode,ChildFlag,nodename,parentnodecode,runscript,nodeorder,nodesign from LDMenu "
		           + " where nodename like  '%%"+ document.all("NodeName").value +"%%' "
				   + " order by nodeorder";*/
	mySql = new SqlClass();
	mySql.setResourceName("menumang.menuFunManSql");
	mySql.setSqlId("menuFunManSql2");
	mySql.addSubPara(document.all("NodeName").value ); 
	
	}
	var sqlStr=mySql.getString();
	turnPage.queryModal(sqlStr, QueryGrpGrid,1); 
}  

function fillUserGrid()
{
   QueryGrpGrid.clearData("QueryGrpGrid");

   for (var i = 0; i < mulLineShowCount; i++) {

        QueryGrpGrid.addOne("QueryGrpGrid");
   	var offset = i  + userCurPage*mulLineShowCount;

   	if (offset < userArrayLen) {
   	    QueryGrpGrid.setRowColData(i,1,userArray[offset][0]);
   	    QueryGrpGrid.setRowColData(i,2,userArray[offset][1]);
   	    QueryGrpGrid.setRowColData(i,3,userArray[offset][3]);
   	    QueryGrpGrid.setRowColData(i,4,userArray[offset][2]);
   	    QueryGrpGrid.setRowColData(i,5,userArray[offset][4]);
   	    QueryGrpGrid.setRowColData(i,6,userArray[offset][5]);
   	    QueryGrpGrid.setRowColData(i,7,userArray[offset][6]);//�˵���־
//   	    alert(userArray[offset][4]);
   	} else {
   	    QueryGrpGrid.setRowColData(i,1,"");
   	    QueryGrpGrid.setRowColData(i,2,"");
        QueryGrpGrid.setRowColData(i,3,"");
   	    QueryGrpGrid.setRowColData(i,4,"");
   	    QueryGrpGrid.setRowColData(i,5,"");
   	    QueryGrpGrid.setRowColData(i,6,"");
   	    QueryGrpGrid.setRowColData(i,7,"");//�˵���־
   	}  	        
   }

   //����Ĵ�����Ϊ��ʹ��ҳʱ�������ȷ��ʾ
   for (var i = 0; i < mulLineShowCount; i++) {
		var offset = i  + userCurPage*mulLineShowCount;
        document.all("QueryGrpGridNo")[i].value = offset + 1;
   }
		
}

function userFirstPage()
{
	turnPage.firstPage();
	/*if (userArrayLen == 0)
	    return;
	    
	userCurPage = 0;
	fillUserGrid();
	*/
}

function userLastPage()
{
	turnPage.lastPage();
	/*
	if (userArrayLen == 0)
	    return;
	    
	while ((userCurPage + 1)*mulLineShowCount < userArrayLen)
	    userCurPage++;

	fillUserGrid();
	*/
}


function userPageDown()
{
	turnPage.nextPage();
	/*
	if (userArrayLen == 0)
	    return;
	    
    if (userArrayLen <= (userCurPage + 1) * mulLineShowCount) {
    	alert("�Ѵ�βҳ");
    } else {
        userCurPage++;
        fillUserGrid();
    }
    */
}

function userPageUp()
{
	turnPage.previousPage();
	/*
	if (userArrayLen == 0)
	    return;

    if (userCurPage == 0) {
    	alert("�ѵ���ҳ");
    } else {
        userCurPage--;
        fillUserGrid();
    }
    */
}

function submitForm()
{
	//alert('document.all("isChild").value:'+document.all("isChild").value);
  var i = 0;
  var showStr="�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit(); //�ύ
  document.all("RunScript").value ="";
  //queryClick();
}

function insertClick()
{
	//ȡ�ò˵���־
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
	if(selMenuGrpNo<1)
	{
		alert('��ѡ��һ����¼!');
		return false;
	}
	var selMenu = selMenuGrpNo - 1;
	var NodeSign = QueryGrpGrid.getRowColData(selMenu,7);
	var RunScript = QueryGrpGrid.getRowColData(selMenu,5);
	//alert(NodeSign);
  //alert(RunScript);
 	if (document.all("NodeName").value == ""){
 		alert ("������˵��ڵ�����!");
 	}
 	//�ų�ҳ�湦�ܲ˵�ͬ�����뵽һ��˵�2005
	else 
	{
		if (NodeSign != 2 && document.all("checkbox2").checked == true){
			if(document.all("checkbox1").checked == false){
				alert("ҳ�湦�ܲ˵�����ͬ�����뵽һ��˵�,������ѡ��");
				}
			else {
				//�ж��Ƿ�ĩ���˵�
				if(NodeSign != 1 && RunScript==null){
					alert("ҳ�湦�ܲ˵������¼����뵽��Ҷ�Ӳ˵�,������ѡ��");
					}
				else{
					document.all("Action").value = "insert";   
				   //��Ϊ�Ӳ˵�����(��ѡ����ͬ���˵�����)     	
				   if(document.all("checkbox1").checked == true) 
				      {     	
				      	document.all("isChild").value = "true";
				      }      
				   else
				      {document.all("isChild").value = "false";}
				   //�Ƿ���Ϊҳ��Ȩ�޲˵����� 2005  	
				   if(document.all("checkbox2").checked == true) 
				      {     	
				      	document.all("isChild2").value = "true";
				      }      
				   else
				      {document.all("isChild2").value = "false";}            
				   submitForm();
					}					
				}
			}
			else {
				if (NodeSign == 2 && document.all("checkbox1").checked == true){
					alert("ҳ�湦�ܲ˵���������Ӳ˵�,������ѡ��");
					}
				else{		 
				   document.all("Action").value = "insert";   
				   //��Ϊ�Ӳ˵�����(��ѡ����ͬ���˵�����)     	
				   if(document.all("checkbox1").checked == true) 
				      {     	
				      	document.all("isChild").value = "true";
				      }      
				   else
				      {document.all("isChild").value = "false";}
				   //�Ƿ���Ϊҳ��Ȩ�޲˵����� 2005  	
				   if(document.all("checkbox2").checked == true) 
				      {     	
				      	document.all("isChild2").value = "true";
				      }      
				   else
				      {document.all("isChild2").value = "false";}            
				   submitForm();
			    }
				}
  }
}

function deleteClick() 
{
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
        if (selMenuGrpNo == 0) {
	  alert("����û��ѡ����Ҫɾ���Ĳ˵�");
	  return;
	}    	
	if (!confirm("��ȷʵҪɾ������˵���"))
	  return;
	
	document.all("Action").value = "delete";

    submitForm();    	
}

function afterSubmit(FlagStr)
{
    showInfo.close();	
       
    if (document.all('Action').value == "insert") {
        if (FlagStr == "success")
            alert("���Ӳ˵��ɹ���");
        else
            alert("���Ӳ˵�ʧ�ܣ����ܵ�ԭ���Ǵ˲˵��Ѵ���");
    }
    
    if (document.all('Action').value == "delete") {
        if (FlagStr == "success")
            alert("ɾ���˵��ɹ���");
        else
            alert("ɾ���˵�ʧ��,ԭ������ǲ���ɾ�����Ӳ˵��Ĳ˵�!");
    }		
    queryClick();
}







