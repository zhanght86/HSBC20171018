/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst
 * @version  : 1.00
 * @date     : 2007-06-19
 * @direction: ���ļ��а����ͻ�����Ҫ����ĺ������¼�
 *****************************************************************************/


var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "bq.PEdorTypeBSInputSql";
 
function initCheck()
{
	//�ͻ�ǩ����֤
	if(fm.chk01.checked)
	{
		fm.chkS1.value="1";
		}
	if(fm.chk02.checked)
	{
		fm.chkS2.value="1";
		}
	if(fm.chk03.checked)
	{
		fm.chkS3.value="1";
		}
	if(fm.chk04.checked)
	{
		fm.chkS4.value="1";
		}	
	if(fm.chk05.checked)
	{
		fm.chkS5.value="1";
		}
	if(fm.chk06.checked)
	{
		fm.chkS6.value="1";
		}
	if(fm.chk07.checked)
	{
		fm.chkS7.value="1";
		}
	if(fm.chk08.checked)
	{
		fm.chkS8.value="1";
		}
  //Ч��ȷ�϶���
	if(fm.chk11.checked)
	{
		fm.chkO1.value="1";
		}
	if(fm.chk12.checked)
	{
		fm.chkO2.value="1";
		}
	if(fm.chk13.checked)
	{
		fm.chkO3.value="1";
		}	
	}
  
function initRemark()
{
	  var tContNo=fm.ContNo.value;
	  var tEdorNo=fm.EdorNo.value;
/*	  var tSQL="select standbyflag1,standbyflag2,standbyflag3 from lpedoritem where contno='"+tContNo+"'"
	          +" and EdorNo='"+tEdorNo+"' and edortype='BS'";
*/  	
  	var tSQL = "";
	var sqlid1="PEdorTypeBSInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	mySql1.addSubPara(tEdorNo);
	tSQL=mySql1.getString();
  	
  	var tResult=easyExecSql(tSQL,1,0,1);
    if(tResult[0][0]=="" || tResult[0][0]==null){
    	fm.AppReason.value="";    	
    }else
    {
    	fm.AppReason.value=tResult[0][0];   
//    	var tAppSQL="select codename from ldcode where codetype='appreasoncode' and code='"+fm.AppReason.value+"'"
    	
    var tAppSQL = "";
	var sqlid2="PEdorTypeBSInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.AppReason.value);//ָ������Ĳ���
	tAppSQL=mySql2.getString();
    	
    	var tResultName=easyExecSql(tAppSQL,1,0,1); 
      fm.AppReasonCodeName.value=tResultName[0][0];  
      if(fm.AppReason.value=='3')
      {
      document.all("divApproveMofiyReasonTitle").style.display = "";
      document.all("divApproveMofiyReasonInput").style.display = "";	
//      var tUnAppSQL="select codename from ldcode where codetype='nnvalireasoncode' and code='"+tResult[0][1]+"'"
    	
    var tUnAppSQL = "";
	var sqlid3="PEdorTypeBSInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tResult[0][1]);//ָ������Ĳ���
	tUnAppSQL=mySql3.getString();
    	
    	var tResultUNName=easyExecSql(tUnAppSQL,1,0,1); 
    	fm.UnValiReason.value=tResult[0][1];  
      fm.ReValiReasonName.value=tResultUNName[0][0];  
      if(fm.UnValiReason.value=='4')
      {

      	    document.all("divRemarkInfo").style.display = "";
      	    fm.Remark.value=tResult[0][2].substring(55,tResult[0][2].length);
      	}
      }
      var  tCheck=tResult[0][2].substring(0,54);
      //alert(tResult[0][2]);

      var  tCheck=tCheck.split("$");
     
      var  tS=tCheck[0];
      var  tO=tCheck[1];

      var  tSubS=tS.split("\\");
      var  tSubO=tO.split("\\");
      for( var t=0;t<tSubS.length;t++)
      {
      	var tTemp=tSubS[t];
      	if(tTemp.substring(3)=='1')
      	{
      		 var tStr="fm.chk0"+(t+1);
      		 var object=eval(tStr); 
      		 object.checked=true;    		           		     
      	}      	
      }
      for( var t=0;t<tSubO.length;t++)
      {
      	var tTemp=tSubO[t];
      	if(tTemp.substring(3)=='1')
      	{
      		 var tStr="fm.chk1"+(t+1);
      		 var object=eval(tStr); 
      		 object.checked=true;    	
      	}      	
      }
    }


}

function initQuery()
{
/*      var	SQL="select edoracceptno,edorno,edortype,(select edorname from lmedoritem where edorcode=edortype and appobj='I'),"
	             +" contno,edorappdate,edorvalidate from lpedoritem  where 1=1 and contno='"+fm.ContNo.value+"' and edortype='BS' and edoracceptno!='"+fm.EdorAcceptNo.value+"' ";
*/        
    var SQL = "";
	var sqlid4="PEdorTypeBSInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql4.addSubPara(fm.EdorAcceptNo.value);
	SQL=mySql4.getString();
        
        arrResult = easyExecSql(SQL);
        if (arrResult)
        {
            displayMultiline(arrResult,EdorGrid);
        }
    initRemark();
	  return true;
}

//�ύ
function edorTypePSSubmit()
{
	var tName=fm.AppReason.value;
  if(tName==""||tName==null){
  	alert("����ԭ����Ϊ��"); 	
  	return false;
  }
  if(fm.AppReason.value=='3'&& fm.UnValiReason.value=='' )
  {
  	alert("ԭ����Ϊ��"); 	
  	return false; 	
  	}
  if(fm.AppReason.value=='3'&& fm.UnValiReason.value=='4' && trim(fm.Remark.value)=='' )
  {
  	alert("��ע��Ϣ����Ϊ��"); 	
  	return false; 	
  	}
  	
  initCheck();
  if(!checkCheck())	
  {
  	initCheckInfo();
  	return false;
  	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.fmtransact.value="INSERT||MAIN";
    fm.submit();
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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


    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  }
  if(fm.AppReason.value=='3')
  {
   alert("�뼰ʱ��ӡ��ͬӰ�����ϣ��ÿͻ��Ը�֪�������ȷ�ϣ�");
 	 // content+=" "
   }
  initForm();
}


/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    if( cDebug == "1" )
        parent.fraMain.rows = "0,0,50,82,*";
    else
        parent.fraMain.rows = "0,0,0,72,*";
}
function returnParent()
{

 top.opener.initEdorItemGrid();
 top.opener.getEdorItemGrid();
 top.close();
}



/**
* showCodeList �Ļص�����
*/
function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "appreasoncode")
    {
        if (oCodeListField.value == "3")
        {
            try
            {
                document.all("divApproveMofiyReasonTitle").style.display = "";
                document.all("divApproveMofiyReasonInput").style.display = "";

            }
            catch (ex) {}
        }
        else
        {
            try
            {
                document.all("divApproveMofiyReasonTitle").style.display = "none";
                document.all("divApproveMofiyReasonInput").style.display = "none";
                document.all("divRemarkInfo").style.display = "none";
            }
            catch (ex) {}
        }
    } 
    
    if (sCodeListType == "nnvalireasoncode")
    {
        if (oCodeListField.value == "4")
        {
            try
            {
                document.all("divRemarkInfo").style.display = "";
            }
            catch (ex) {}
        }
        else
        {
            try
            {
                document.all("divRemarkInfo").style.display = "none";
            }
            catch (ex) {}
        }
    } 
}


function QueryPhoto()
{
	
	  var selno = EdorGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ��ѯ�ı�ȫ��Ŀ��");
          return;
    }
    var tEdorAcceptNo = EdorGrid.getRowColData(selno, 1);

     var tUrl="../bq/ImageQueryMain.jsp?BussNo="+tEdorAcceptNo+"&BussType=BQ";
     OpenWindowNew(tUrl,"��ȫɨ��Ӱ��","left");
}

	
	//Ӱ�����ϲ�ѯ
function ImageQuery()
{
//   var str = "select distinct PrtNo from lccont where contno = '" + fm.ContNo.value + "'";
     
    var str = "";
	var sqlid5="PEdorTypeBSInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	str=mySql5.getString();
     
     var arrResult = easyExecSql(str);
     if(arrResult == null){
          alert("��ѯ�а�Ӱ��ʧ��");
          return;
     }
     var ContNo = arrResult[0][0];

//	window.open("../uw/ImageQueryMain.jsp?ContNo="+ContNo);
	window.open("../uw/ImageQueryMain.jsp?ContNo="+ContNo,"",sFeatures);
}
	function  checkCheck()
	{
		//alert(1);
		 var tChek0Flag=false;
		 var tChek1Flag=false;
     for(var t=1;t<=8;t++)
     {
     			//alert(t);
         var tStr="fm.chk0"+(t);
         var object=eval(tStr); 
         if(object.checked)
         {
         	 tChek0Flag=true;
         	 break;
         }             	
     }
     var tChek12Flag=false;
     var tChek13Flag=false;	
     var r=1;	
	   while(r<=3)
     {
     	   //alert(r);
         var tStr="fm.chk1"+(r);
          //alert(tStr);
         var object=eval(tStr); 
         if(object.checked)
         {
         	 tChek1Flag=true;
         } 
         if(r==2&&object.checked)  
         {
         	tChek12Flag=true;
         	}
         if(r==3&&object.checked)  
         {
         	tChek13Flag=true;
         	}  
         	//alert(r);
         	r++;      	          	
     }
     if(!(tChek1Flag&&tChek0Flag))
     {
     	if(!confirm("�ͻ�ǩ����֤��Ч��ȷ�϶���û��ѡ���Ƿ������"))
     	{
     		return false
     	}
     }
     
     if(tChek12Flag&&tChek13Flag)		
     {
     	alert("�������˵ķ����໤�˺ͱ������˲���ͬʱѡ������ֻ��ѡ����һ");
     	return false;
     	}
     	return true;
		}  

function initCheckInfo()
{
        
     fm.chk01.checked=false;
     fm.chk02.checked=false;
     fm.chk03.checked=false;
     fm.chk04.checked=false;
     fm.chk05.checked=false;
     fm.chk06.checked=false;
     fm.chk07.checked=false;
     fm.chk08.checked=false;
                     
     fm.chk11.checked=false;
     fm.chk12.checked=false;
     fm.chk13.checked=false;
     //alert(1);
     
     fm.chkS1.value="0";
     fm.chkS2.value="0";
     fm.chkS3.value="0";
     fm.chkS4.value="0";
     fm.chkS5.value="0";
     fm.chkS6.value="0";
     fm.chkS7.value="0";
     fm.chkS8.value="0";
     
     fm.chkO1.value="0";
     fm.chkO2.value="0";
     fm.chkO3.value="0";
     
 }