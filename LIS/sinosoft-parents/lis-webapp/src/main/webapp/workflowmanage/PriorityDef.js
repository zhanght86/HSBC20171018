//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
/*******************************************************************************
 * <p>Title       : ���ȼ������</p>
 * <p>Description : �������������ȼ�������</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        : 
 * @version       : 1.00
 * @date          : 2012-5-25
 ******************************************************************************/
var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ��� 
var turnPage1 = new turnPageClass();
function Addto(){
    var rows = WorkFlowGridP.getSelNo();
    if (rows == 0){
    alert("��ѡ��ָ���С�лл��");
    return false;
    }else{
    var row = WorkFlowGridP.getSelNo()	//���ѡ�е�Radio�к�  
    var rows = row-1;
    var Date = new Array; //����һ����     
    Date = WorkFlowGridP.getRowData(rows)  //ȡ��ָ����ĳһ�е�ֵ
    var priorityid = Date[5];
    var version = Date[6];
    var activityid = Date[1];
    var sqls1 = Date[3];   
    var sqls = encodeURI(encodeURI(sqls1));
    var tempdate ="&OperFlag="+"INSERT||Condition"+"&ProcessID="+fma.ProcessID.value+"&ActivityID="+activityid+"&PriorityID="+priorityid+"&Sqls="+sqls+"&Version="+version;
    var urli = "PriorityDefSave.jsp?OperFlag=INSERT||Condation";
    var AjaxRequestObj = new ActiveXObject("Microsoft.XMLHTTP");
    AjaxRequestObj.open("POST", urli, true);
    AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    AjaxRequestObj.send(tempdate); 
    AjaxRequestObj.onreadystatechange = function(){
     if ( AjaxRequestObj.readyState == 4 &&  AjaxRequestObj.status == 200){
                 var result =  AjaxRequestObj.responseText;
                 var showStr = result.substr(1,5);                
                 if(showStr==("��ӳɹ�!")){
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
				 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
               }else{
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + " ���ʧ��! " ;
                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
				 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
                 alert("���̺ţ����̰汾Ϊ�����ֶ�")
             }
          }
      }
   }
}
function search(){
	var arr=new Array();
	  var strSQL = "Select c.priorityname, b.color,b.colorid,c.range,c.priorityid from lwpriority c left join lwprioritycolor b on c.colorid = b.colorid  ";
	  initWorkGridPa();
	  turnPage1.queryModal(strSQL, WorkGridPa); 
}
function updatepriority(){
    var rows = WorkGridPa.getSelNo()
    if (rows == 0){
    alert("��ѡ��ָ���С�лл��");
    return false;
    }else{
	    var row = WorkGridPa.getSelNo()	//���ѡ�е�Radio�к�  
	    var rows = row-1;
	    var Date = new Array; //����һ����    
	    Date = WorkGridPa.getRowData(rows)  //ȡ��ָ����ĳһ�е�ֵ
	    var priorityid = Date[4];
	    var colorid = Date[2];
	    var range = Date[3]; 
	    var priorityname1 = Date[0]
	    var priorityname = encodeURI(encodeURI(priorityname1));
	    var tempdate ="&OperFlag="+"UPDATE||PRIORITY"+"&priorityid="+priorityid+"&colorid="+colorid+"&range="+range+"&priorityname="+priorityname;
	    var urli = "PriorityDefSave.jsp?OperFlag=UPDATE||PRIORITY";
	    var AjaxRequestObj = new ActiveXObject("Microsoft.XMLHTTP");
	    AjaxRequestObj.open("POST", urli, true);
	    AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	    AjaxRequestObj.send(tempdate); 
	    AjaxRequestObj.onreadystatechange = function(){
	     if ( AjaxRequestObj.readyState == 4 &&  AjaxRequestObj.status == 200){
	                 var result =  AjaxRequestObj.responseText;
	                 var showStr = result.substr(1,5);                
	                 if(showStr==("���³ɹ�!")){
	                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
					 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
					var iWidth=550;      //�������ڵĿ��; 
					var iHeight=250;     //�������ڵĸ߶�; 
					var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
					var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
					showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

					showInfo.focus();
	               }else{
	                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + " ����ʧ��! " ;
	                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
					 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
					var iWidth=550;      //�������ڵĿ��; 
					var iHeight=250;     //�������ڵĸ߶�; 
					var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
					var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
					showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

					showInfo.focus();
                         }
	                 }
	    }
   }
}
function updateclick(){
    var rows = WorkFlowGridP.getSelNo()
    if (rows == 0){
    alert("��ѡ��ָ���С�лл��");
    return false;
    }else{
var row = WorkFlowGridP.getSelNo()	//���ѡ�е�Radio�к�  
    var rows = row-1;
    var Date = new Array; //����һ����     
    Date = WorkFlowGridP.getRowData(rows)  //ȡ��ָ����ĳһ�е�ֵ
    var priorityid = Date[5];
    var activityid = Date[1];
    var version = Date[6];
    var sqls1 = Date[3];   
    var sqls = encodeURI(encodeURI(sqls1));
    var tempdate ="&OperFlag="+"UPDATE||Condition"+"&ProcessID="+fma.ProcessID.value+"&ActivityID="+activityid+"&PriorityID="+priorityid+"&Sqls="+sqls+"&Version="+version;
    var urli = "PriorityDefSave.jsp?OperFlag=UPDATE||Condation";
    var AjaxRequestObj = new ActiveXObject("Microsoft.XMLHTTP");
    AjaxRequestObj.open("POST", urli, true);
    AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    AjaxRequestObj.send(tempdate); 
    AjaxRequestObj.onreadystatechange = function(){
    if ( AjaxRequestObj.readyState == 4 &&  AjaxRequestObj.status == 200){
                 var result =  AjaxRequestObj.responseText;
                 var showStr = result.substr(1,5);        
                 if(showStr==("�޸ĳɹ�!")){
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
				 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
               }else{
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + "�޸�ʧ��!" ;
                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
				 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
                }
           }
     }
   }
}
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
function deleteClick(){
    var rows = WorkFlowGridP.getSelNo()
    if (rows == 0){
    alert("��ѡ��ָ���С�лл��");
    return false;
    }else{
        var row = WorkFlowGridP.getSelNo()	//���ѡ�е�Radio�к�  
        var rows = row-1;
        var Date = new Array; //����һ����     
        Date = WorkFlowGridP.getRowData(rows)  //ȡ��ָ����ĳһ�е�ֵ
        var priorityid = Date[5];
        var activityid = Date[1];
        var sqls = Date[3];   
        var version = Date[6];
        var tempdate ="&OperFlag="+"DELETE||Condition"+"&ProcessID="+fma.ProcessID.value+"&ActivityID="+activityid+"&PriorityID="+priorityid+"&Sqls="+sqls+"&Version="+version;
        var urli = "PriorityDefSave.jsp?OperFlag=DELETE||Condation";
        var AjaxRequestObj = new ActiveXObject("Microsoft.XMLHTTP");
        AjaxRequestObj.open("POST", urli, true);
        AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        AjaxRequestObj.send(tempdate); 
        AjaxRequestObj.onreadystatechange = function(){
        if ( AjaxRequestObj.readyState == 4 &&  AjaxRequestObj.status == 200){
                 var result =  AjaxRequestObj.responseText;
                 var showStr = result.substr(1,5);
                 if(showStr==("ɾ���ɹ�!")){
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
				 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
          }else{
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + " ɾ���ɹ�! " ;
                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
				 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
          }
                 query();
          }
       }   
    }       
}
function query()
{ 
  checkprocess();
  var arr=new Array();
  var strSQL = "Select t.priorityname, d.activityid,(select activityname from lwactivity where activityid = d.activityid) ,d.prioritysql ,d.processid,t.priorityid,d.version from lwpriority t join lwprioritysql d on t.priorityid = d.priorityid  where processid = '"+fma.ProcessID.value+"'";
  initWorkFlowGridP();
  turnPage.queryModal(strSQL, WorkFlowGridP); 
}
function checkprocess()
{
	if (document.all('ProcessID').value==""||document.all('ProcessID').value==null)
	{
		alert("�������Ʋ���Ϊ�գ���ѡ��һ������")
		return false;
	}
}
function checkBusiType()
{
	if (document.all('BusiType').value==""||document.all('BusiType').value==null)
	{
		alert("����ѡ��һ��ҵ������")
		return false;
	}
}
function getProcessName(p1,p2) 
{
	return showCodeList('prioritysql',[document.all(p1).all('WorkFlowGridP2'),document.all(p1).all('WorkFlowGridP3')],[0,1],null,document.all("ProcessID").value,'ProcessID',1);
}