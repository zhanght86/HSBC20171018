var turnPage = new turnPageClass();
var mSwitch = parent.VD.gVSwitch;
var prtNo="";
var showInfo;
var mySql = new SqlClass();
function SaveRelation(){
	
	  var checkedRowNum = 0 ;
	  var rowNum = InsuredGrid.mulLineCount ; 
	  var count = -1;
	  for ( var i = 0 ; i< rowNum ; i++ )
	  {
		 
	  	if(InsuredGrid.getChkNo(i))
	  	{	  		
		  	   checkedRowNum = checkedRowNum + 1;
		  	   
		  	   if(InsuredGrid.getRowColData(i,7)==null||InsuredGrid.getRowColData(i, 7)=="")
		  	   {
			       alert("��ѡ���"+(i+1)+"�еļ�¼�е������������˹�ϵ");
			       return false;
		  	   }
	     }
	  }
	  
	  if(checkedRowNum<1)
	  {
	       alert("������Ҫ�޸ĵļ�¼ǰ���[��]");
	       return false;
	  }
	  
	  
	  
	fm.action="./AddRelationSave.jsp";
	submitForm();
}


function submitForm(){
    //�ύ����
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    document.getElementById("fm").submit(); //�ύ
    //tSaveFlag ="0";    
}

function queryMainInsured(){
	var grpcontno=fm.GrpContNo.value;
	
//	var tMainInsuredSql = "select insuredno, name, sex, birthday, idtype, idno"
//							+ " from lcinsured b "
//							+ " where insuredno in "
//							+ " (select insuredno"
//							+ " from lcpol a "
//							+ " where a.insuredno in "
//							+ " (select distinct maincustomerno from lcinsuredrelated)"
//							+ " and a.grpcontno = b.grpcontno) and b.grpcontno='"+grpcontno+"'";
	
	/*var tMainInsuredSql = "select b.contno,b.riskcode,(select riskname from lmrisk where riskcode=b.riskcode),b.insuredno, b.insuredname, b.insuredSex,  insuredbirthday,d.idtype ,d.idno,b.polno"
		+ "  from lcpol b,lcinsured d  where b.contno=d.contno  and b.insuredno=d.insuredno"
		+ " and b.insuredno in (select distinct maincustomerno from lcinsuredrelated a where  a.polno=b.polno)"
		+ " and b.grpcontno='"+grpcontno+"'";	*/
	
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.AddRelationSql");
	mySql.setSqlId("AddRelationSql1");
	mySql.addSubPara(grpcontno); 
	//prompt("tMainInsuredSql",tMainInsuredSql);
	turnPage.queryModal(mySql.getString(), MainInsuredGrid);
}

function getInsuredDetail(parm1,parm2){

	var tMainCustomerNo = document.all(parm1).all('MainInsuredGrid4').value;
	fm.MainCustomerNo.value = tMainCustomerNo;
	var grpcontno=fm.GrpContNo.value;
	/*var tInsuredSql = "select customerno, name, sex, birthday, idtype, idno, relationtoinsured, polno from "
						+ " lcinsuredrelated b where polno in ( select polno from lcpol a where a.grpcontno = '"
						+ grpcontno+"' )"
						+ " and b.maincustomerno = '"+tMainCustomerNo+"' and b.polno='"+document.all(parm1).all('MainInsuredGrid10').value+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.AddRelationSql");
	mySql.setSqlId("AddRelationSql2");
	mySql.addSubPara(grpcontno);
	mySql.addSubPara(tMainCustomerNo); 
	mySql.addSubPara(document.all(parm1).all('MainInsuredGrid10').value);  
	//prompt("",tInsuredSql);
	turnPage.queryModal(mySql.getString(), InsuredGrid);
}

function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();


    }
    //tSaveFlag ="0";
}