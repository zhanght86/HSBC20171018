var showInfo;
function trimname(){
   fm.Name.value = trim(document.all('Name').value);
}
function queryperinsure()
{
    mSwitch = parent.VD.gVSwitch;
    try{document.all('ProposalGrpContNo').value= mSwitch.getVar( "ProposalGrpContNo" ); }catch(ex){};
    try{document.all('ManageCom').value= mSwitch.getVar( "ManageCom" ); }catch(ex){};
    arrResult = easyExecSql("select max(prtno) from lcgrpcont where Proposalgrpcontno='" + document.all('ProposalGrpContNo').value +"'",1,0)
    var tGrpcontno=arrResult[0][0];
    var strOperate = "like";
//    var strSql = "select a.InsuredNo,a.Name,a.Sex,a.Birthday,a.IDType,a.IDNo,a.ContNo,(select sum(Prem) from lcpol where lcpol.InsuredNo=a.InsuredNo and lcpol.grpcontno='"+document.all('ProposalGrpContNo').value+"' ),a.ContPlanCode from LCInsured a where a.GrpContNo='" + tGrpcontno +"'"

    if(fm.IDNo.value!="")
    {
    	var temp=fm.IDNo.value;
    	var tReshvalue=temp;
    	if(temp.length==15)
    	{
    		var tidno=easyExecSql("select trans1('"+temp+"') from dual");
        tReshvalue=tidno[0][0];
    	}
    }
    	//tongmeng 2009-04-02 modify
    	//�޸Ĳ�ѯSQL
    	//������,�����������������˵�����
    	var tSQL_Insured = "";
tSQL_Insured = " select A.a,A.b,A.c,A.d,A.e,A.f,A.g,A.h,A.i,A.j,A.k,A.l, "
             + " (select occupationname from ldoccupation where occupationcode=A.k), "
	           + " A.n,A.o,A.p,A.q from ( "
						 + " select a.polno a,b.prtno b,b.insuredno c,b.name d,b.sex e,a.insuredappage f "
						 + " ,a.riskcode g,b.contplancode h,a.prem i,a.amnt j, "
	           + " (select occupationcode from lcinsured where lcinsured.insuredno = a.insuredno and contno=a.contno) k "
	           + " ,b.occupationtype l,'' mְҵ����, "
	           + " a.floatrate n,nvl((select cvalidate from lccont where contno = b.contno),to_date('')) o,b.contno p, "
	           + " (select currname from ldcurrency where 1 = 1 and currcode = a.currency)  q from lcpol a, lcinsured b "
	           + " where a.insuredno = b.insuredno and a.contno = b.contno "
	           + " and b.prtno = '"+tGrpcontno+"' "
	           + getWherePart( 'b.ManageCom','ManageCom',strOperate )
	           + getWherePart( 'b.InsuredNo','InsuredNo' )
				     + getWherePart( 'b.IDNo','IDNo' )
					 	 + getWherePart('b.ContPlanCode','ContPlanCode')
					 	 + " and b.Name like '%%"+document.all('Name').value+"%%' " 
	           //������,�������������ݲ�ѯ 
             + " union "
	           + " select a.polno a,b.prtno b,b.insuredno c,b.name d,b.sex e,get_age(b.birthday,(select cvalidate from lcgrpcont where grpcontno =a.grpcontno)) f "
	           + " ,'����������' g,''h,0 i,0 j, "
             //+ " (select occupationcode from lcinsured where lcinsured.insuredno = a.insuredno and contno=a.contno) k, "
             + " b.occupationcode k, "
             + " b.occupationtype,'' mְҵ����, "
	           + " 0 n,nvl((select cvalidate from lccont where contno = b.contno),to_date('')) o,b.contno p,(select currname from ldcurrency where 1 = 1 and currcode = a.currency) q "
             + " from lcpol a, lcinsured b,lcinsuredrelated c  "
             + " where b.insuredno = c.customerno and a.contno = b.contno and a.polno=c.polno "
             + " and b.prtno = '"+tGrpcontno+"' "
	           + getWherePart( 'b.ManageCom','ManageCom',strOperate )
	           + getWherePart( 'b.InsuredNo','InsuredNo' )
				     + getWherePart( 'b.IDNo','IDNo' )
					 	 + getWherePart( 'b.ContPlanCode','ContPlanCode')
					 	 + " and b.Name like '%%"+document.all('Name').value+"%%' " 
             //û����,û���������������� 
             + " union "
	           + " select b.prtno a,b.prtno b,b.insuredno c,b.name d,b.sex e, "
						 + " decode((select year(polapplydate) - year(b.birthday) from lccont where grpcontno = b.grpcontno and insuredno = b.insuredno), "
	           + " null,0,(select year(polapplydate) - year(b.birthday) from lccont where grpcontno = b.grpcontno and insuredno = b.insuredno)) f, "
						 + " '' g,'' h,0 i,0 j, "
						 + " (select max(occupationcode) from lcinsured where lcinsured.insuredno = b.insuredno) k, "
						 + " b.occupationtype l,''mְҵ����, "
						 + " 0 n,nvl((select cvalidate from lccont where contno = b.contno),to_date('')) o,contno p,'' q "
	           + " from lcinsured b "
						 + " where not exists (select '1' from lcpol where grpcontno = b.grpcontno and insuredno = b.insuredno) "
						 + " and not exists (select '1' from lcinsuredrelated where polno in (select polno from lcpol where grpcontno=b.grpcontno and customerno=b.insuredno))"
						 + " and b.prtno = '"+tGrpcontno+"' "
	           + getWherePart( 'b.ManageCom','ManageCom',strOperate )
	           + " and b.Name like '%%"+document.all('Name').value+"%%' " 
	           + " ) A order by A.p,A.a,A.c "
    	/*
    	 //���������ֵı�����
    	 var strSql1 = " select a.polno,b.prtno,b.insuredno,b.name,b.sex,a.insuredappage,a.riskcode,b.contplancode,"
    	             +" a.prem,a.amnt,(select max(occupationcode) from lcinsured where lcinsured.insuredno = a.insuredno),(select max(occupationtype) from lcpol where lcpol.grpcontno = a.grpcontno),"
    	             +" (select max(occupationname) from LDOccupation where OccupationCode in (select occupationcode from lcinsured where lcinsured.insuredno = a.insuredno)),a.floatrate,nvl((select cvalidate from lccont where contno=b.contno),to_date('')),b.contno "
    	             +" from lcpol a, lcinsured b where a.insuredno=b.insuredno and a.prtno=b.prtno and a.prtno='"+tGrpcontno+"' "
                     + getWherePart( 'b.ManageCom','ManageCom',strOperate )
				 	 + getWherePart( 'b.InsuredNo','InsuredNo' )
				     + getWherePart( 'b.IDNo','IDNo' )
					 + getWherePart('b.ContPlanCode','ContPlanCode')
		 //û�б���������Ϣ�ı�����
	     var strSql2 =" union select b.prtno, b.prtno, b.insuredno, b.name,b.sex,decode((select year(polapplydate)-year(b.birthday) from lccont where grpcontno=b.grpcontno and insuredno=b.insuredno),null,0,(select year(polapplydate)-year(b.birthday) from lccont where grpcontno=b.grpcontno and insuredno=b.insuredno))"
	                 +" ,'',b.contplancode, 0,0,(select max(occupationcode) from lcinsured  where lcinsured.insuredno = b.insuredno),"
	                 +" b.occupationtype, (select max(occupationname) from LDOccupation where ldoccupation.occupationcode = b.occupationcode), 0, nvl((select cvalidate from lccont where contno=b.contno),to_date('')),contno from lcinsured b where b.grpcontno='"+tGrpcontno+"'"
	       		     + getWherePart( 'b.ManageCom','ManageCom',strOperate)
	       	         + getWherePart( 'b.InsuredNo','InsuredNo' )
	       		     + getWherePart( 'b.ContPlanCode','ContPlanCode')
					 + getWherePart( 'b.IDNo','IDNo' )
	         		 + "  and not exists "
   			 	     + " (select '1' from lcpol where grpcontno =b.grpcontno and insuredno=b.insuredno) "
   			 	     + " and not exists (select 1 from lcinsuredrelated where polno in (select polno from lcpol where grpcontno =b.grpcontno) and customerno = b.insuredno)"
   		 //����������
	     var strSql3 =" union  select a.polno, b.prtno, a.customerno,a.name, a.sex, 0,(select c.insuredname from lcpol c where polno = a.polno)||'-'||'����', '', 0, 0, "
	                 +"  (select max(occupationcode) from lcinsured where lcinsured.insuredno = b.insuredno),"
	                 +" (select occupationtype from lcinsured where grpcontno =b.grpcontno and insuredno=b.insuredno),"
	                 +" (select max(occupationname) from LDOccupation  where ldoccupation.occupationcode = (select occupationcode from lcinsured where grpcontno = b.grpcontno and insuredno = b.insuredno)),"
	                 +"  0, nvl((select cvalidate from lccont where contno=b.contno),to_date('')), contno from lcinsuredrelated a,lcinsured b where a.polno in (select polno from lcpol where grpcontno=b.grpcontno) and a.customerno = b.insuredno and b.grpcontno='"+tGrpcontno+"'"
	       		     + getWherePart( 'b.ManageCom','ManageCom',strOperate)
	       	         + getWherePart( 'b.InsuredNo','InsuredNo' )
	       		     + getWherePart( 'b.ContPlanCode','ContPlanCode')
					 + " and b.insuredno = (select insuredno from lcinsured where grpcontno =b.grpcontno and insuredno = b.insuredno and idno='"+fm.IDNo.value+"')"
   			 	     + " and  exists (select 1 from lcinsuredrelated where polno in (select polno from lcpol where grpcontno =b.grpcontno) and customerno = b.insuredno)"
	    if(document.all('Name').value !="")
	     {
	        strSql1 =strSql1+" and b.Name like '%%"+document.all('Name').value+"%%' ";
	        strSql2 =strSql2+" and b.Name like '%%"+document.all('Name').value+"%%' ";
	        strSql3 =strSql3+" and a.Name like '%%"+document.all('Name').value+"%%' ";
	     }
     }else{
    var strSql1 = " select a.polno,b.prtno,b.insuredno,b.name,b.sex,a.insuredappage,a.riskcode,b.contplancode,"
    	              +" a.prem,a.amnt,(select max(occupationcode) from lcinsured where lcinsured.insuredno = a.insuredno),(select max(occupationtype) from lcpol where lcpol.grpcontno = a.grpcontno),"
    	              +" (select max(occupationname) from LDOccupation where OccupationCode in (select occupationcode from lcinsured where lcinsured.insuredno = a.insuredno)),a.floatrate,nvl((select cvalidate from lccont where contno=b.contno),to_date('')),b.contno "
    	              +" from lcpol a, lcinsured b where a.insuredno=b.insuredno and a.prtno=b.prtno and  a.prtno='"+tGrpcontno+"' "
				      + getWherePart( 'b.ManageCom','ManageCom',strOperate)
				      + getWherePart( 'b.InsuredNo','InsuredNo' )
				      + getWherePart('b.ContPlanCode','ContPlanCode')
	var strSql2 =" union select b.prtno, b.prtno, b.insuredno, b.name,b.sex,decode((select year(polapplydate)-year(b.birthday) from lccont where grpcontno=b.grpcontno and insuredno=b.insuredno),null,0,(select year(polapplydate)-year(b.birthday) from lccont where grpcontno=b.grpcontno and insuredno=b.insuredno))"
	                 +" ,'' ,"
	                 +" b.contplancode, 0,0,(select max(occupationcode) from lcinsured  where lcinsured.insuredno = b.insuredno),"
	                 +" b.occupationtype, (select max(occupationname) from LDOccupation where ldoccupation.occupationcode = b.occupationcode), 0, nvl((select cvalidate from lccont where contno=b.contno),to_date('')),contno from lcinsured b where b.grpcontno='"+tGrpcontno+"'"
	       		     + getWherePart( 'b.ManageCom','ManageCom',strOperate)
	       	         + getWherePart( 'b.InsuredNo','InsuredNo' )
	       		     + getWherePart( 'b.ContPlanCode','ContPlanCode')
	         		 + "  and not exists "
   			 	     + " (select '1' from lcpol where grpcontno =b.grpcontno and insuredno=b.insuredno) "
   			 	     + " and not exists (select 1 from lcinsuredrelated where polno in (select polno from lcpol where grpcontno =b.grpcontno) and customerno = b.insuredno)"
	var strSql3 =" union  select a.polno, b.prtno, a.customerno,a.name, a.sex, 0,(select c.insuredname from lcpol c where polno = a.polno)||'-'||'����', '', 0, 0, "
	                 +"  (select max(occupationcode) from lcinsured where lcinsured.insuredno = b.insuredno),"
	                 +" (select occupationtype from lcinsured where grpcontno =b.grpcontno and insuredno=b.insuredno),"
	                 +" (select max(occupationname) from LDOccupation  where ldoccupation.occupationcode = (select occupationcode from lcinsured where grpcontno = b.grpcontno and insuredno = b.insuredno)),"
	                 +"  0, nvl((select cvalidate from lccont where contno=b.contno),to_date('')), contno from lcinsuredrelated a,lcinsured b where a.polno in (select polno from lcpol where grpcontno=b.grpcontno) and a.customerno = b.insuredno and b.grpcontno='"+tGrpcontno+"'"
	       		     + getWherePart( 'b.ManageCom','ManageCom',strOperate)
	       	         + getWherePart( 'b.InsuredNo','InsuredNo' )
	       		     + getWherePart( 'b.ContPlanCode','ContPlanCode')
					 //+ getWherePart( 'b.IDNo','IDNo' )
   			 	     + " and  exists (select 1 from lcinsuredrelated where polno in (select polno from lcpol where grpcontno =b.grpcontno) and customerno = b.insuredno)"
	       if(document.all('Name').value !="")
	         {
	            strSql1 =strSql1+" and b.Name like '%%"+document.all('Name').value+"%%' ";
	            strSql2 =strSql2+" and b.Name like '%%"+document.all('Name').value+"%%' ";
	            strSql3 =strSql3+" and a.Name like '%%"+document.all('Name').value+"%%' ";
	         }
				 
	       }
	  strSql1 = strSql1+strSql2+strSql3;
	  strSql1 =strSql1+" order by insuredno ";
	  */
		fm.querySql.value  = tSQL_Insured;
    turnPage.queryModal(tSQL_Insured, PersonInsuredGrid);
    if(!turnPage.strQueryResult)
    {
    	 alert("��ѯ�޽��");
    }
}
function getin()
{
	if ( fm.ProposalGrpContNo.value =="")
	{
		alert("������ҳ���ܳ�ʱ�������µ�¼��");
		return ;
	}
   //alert("�ɹ�����");
   var strUrl = "../appgrp/DiskApplyInputMain.jsp?grpcontno="+ fm.ProposalGrpContNo.value
   showInfo=window.open(strUrl,"","width=400, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=yes");
}
function getout()
{
  //alert(fm.ProposalGrpContNo.value);
  if (confirm("����ɾ�����еı�����,ȷ����"))
	      {
	      	lockScreen('lkscreen');  
  fm.action="DeleteAllInsured.jsp";
	document.getElementById("fm").submit();
}
}

//ɾ�����ϼƻ������뱣�ϼƻ��ı�����
function getcontplan()
{
	
	if ( fm.ProposalGrpContNo.value =="")
	{
		alert("������ҳ���ܳ�ʱ�������µ�¼��");
		return ;
	}
   
   var strUrl = "../appgrp/ContPlanInputMain.jsp?grpcontno="+ fm.ProposalGrpContNo.value
   //window.open(strUrl);
   showInfo=window.open(strUrl,"","width=400, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=yes");
}

function printout()
{
	var strUrl = "../appgrp/PrintOut.jsp?ProposalGrpContNo="+ fm.ProposalGrpContNo.value
  window.open(strUrl);
	//document.getElementById("fm").submit();	
}

function downAfterSubmit(cfilePath,fileName) { 
	//showInfo.close();
  //alert("adadad");
  fileUrl.href = cfilePath ;//+ fileName; 
  //prompt("",fileUrl.href);
  //fileUrl.click();
  fm.action="./download.jsp?file="+fileUrl.href;
  document.getElementById("fm").submit();
}


function printout1()
{
	//����������
	var sql="select InsuredNo,name,sex,birthday,idtype,idno,OccupationType,OccupationCode From lcinsured where grpcontno='"+fm.ProposalGrpContNo.value+"'";
	var Result = easyExecSql(sql, 1, 1);
	//alert( Result.length);
	//return;
	if(Result!=null)
	{
		
		try {
          var xls    = new ActiveXObject ( "Excel.Application" );
       }
       catch(e) {
          alert( "Ҫ�������ݵ�Excel�������밲װExcel���ӱ�������ͬʱ����IE������İ�ȫ��������ʹ�á�ActiveX �ؼ�������");
          return "";
       }

          xls.visible = true;

      var xlBook = xls.Workbooks.Add;
      var xlsheet = xlBook.Worksheets(1);
      xls.Cells.Select;
      xls.Selection.NumberFormatLocal = "@";
xlsheet.Cells(1,1).Value="���";
xlsheet.Cells(1,2).Value="�ͻ�����";
xlsheet.Cells(1,3).Value="����";
xlsheet.Cells(1,4).Value="�Ա�";
xlsheet.Cells(1,5).Value="��������";
xlsheet.Cells(1,6).Value="֤������";
xlsheet.Cells(1,7).Value="֤������";
xlsheet.Cells(1,8).Value="ְҵ���";
xlsheet.Cells(1,9).Value="����";

     
for (i = 0; i < Result.length; i++)
{

xlsheet.Cells(i+2,1).Value=i+1;
xlsheet.Cells(i+2,2).Value=Result[i][0];
xlsheet.Cells(i+2,3).Value=Result[i][1];
xlsheet.Cells(i+2,4).Value=Result[i][2];
xlsheet.Cells(i+2,5).Value=Result[i][3];
xlsheet.Cells(i+2,6).Value=Result[i][4];
xlsheet.Cells(i+2,7).Value=Result[i][5];
xlsheet.Cells(i+2,8).Value=Result[i][6];
xlsheet.Cells(i+2,9).Value=Result[i][7];


}
		
		
	}else{
	 alert("û�����ݵ�����");
	}
}
	


function getdetail()
{
	//alert("ok");//write by yaory
        var arrReturn = new Array();
	var tSel = PersonInsuredGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			var tRow = PersonInsuredGrid.getSelNo() - 1;
		   //��������������˲��ܽ���������Ϣ
		   var tRiskCode = PersonInsuredGrid.getRowColData(tRow, 7);
		   //alert(tRiskCode);
		   if(tRiskCode=="����������"){
		      alert("��������������˲鿴������Ϣ��");
		      return false;
		   }
			mSwitch.deleteVar("ContNo");      //������ͬ�ţ�����Ͷ�������Ѿ���ֵ�������ظ�
	                mSwitch.addVar("ContNo", "", PersonInsuredGrid.getRowColData(tRow, 16));
	                mSwitch.updateVar("ContNo", "", PersonInsuredGrid.getRowColData(tRow, 16));
	            //tongmeng 2009-03-20 add
	            //���Ӹ�����Ч�յ�¼��
	  //              mSwitch.addVar("ContCValidate", "", PersonInsuredGrid.getRowColData(tRow, 15));
	//                mSwitch.updateVar("ContCValidate", "", PersonInsuredGrid.getRowColData(tRow, 15));
//alert( mSwitch.getVar( "ContCValidate" ));
	                var strSQL="select PolType,peoples,cvalidate from lccont where contno='"+PersonInsuredGrid.getRowColData(tRow, 16)+"'";
	                arrResult = easyExecSql(strSQL,1,0);
	                if(arrResult!=null)
	                {
	                    mSwitch.deleteVar("PolType");
	                    mSwitch.addVar("PolType", "", arrResult[0][0]);
	                    mSwitch.updateVar("PolType", "", arrResult[0][0]);

	                    mSwitch.deleteVar("InsuredPeoples");
	                    mSwitch.addVar("InsuredPeoples", "", arrResult[0][1]);
	                    mSwitch.updateVar("InsuredPeoples", "", arrResult[0][1]);
	                }
	                else
	                {
	                	alert("û�к�ͬ��Ϣ");
	                	return false;
	                }
			//////////edit by yaory

	        //2008-09-18 zhangzheng ���ݱ����������ͽ��뵽��Ӧҳ��
			var tInsuredType = mSwitch.getVar("PolType");
			
			//alert("��ͬ:"+PersonInsuredGrid.getRowColData(tRow, 16)+"�ı��������ʻ�����:"+tInsuredType)

			if(tInsuredType!="0" && tInsuredType!="1" && tInsuredType!="2")
			{
				tInsuredType="0";
			}
			
			//alert(tInsuredType); 
			
			top.fraInterface.window.location="../appgrp/ContInsuredInput.jsp?ContNo="+PersonInsuredGrid.getRowColData(tRow,16)+"&ContType=2&LoadFlag="+LoadFlag+"&tNameFlag="+tInsuredType+"&ProposalGrpContNo="+fm.ProposalGrpContNo.value+"&scantype="+scantype+"&checktype=2&display=0&prtNo="+prtNo+"&polNo="+polNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&InsuredNo="+PersonInsuredGrid.getRowColData(tRow, 3);
		}
		catch(ex)
		{
			alert( "�������");
		}
 
	}
}
function getintopersoninsured()
{/////edit by yaory
	top.fraInterface.window.location = "../appgrp/ContInsuredInput.jsp?LoadFlag="+LoadFlag+"&ContType=2"+"&scantype="+scantype+"&ProposalGrpContNo="+fm.ProposalGrpContNo.value+"&checktype=2&display=1";
}


function returnparent()
{
//alert(LoadFlag);
if(LoadFlag!=null && LoadFlag=="16")
{
top.fraInterface.window.location = "../sys/GrpPolDetailQueryMain.jsp?LoadFlag="+LoadFlag+"&GrpContNo="+mSwitch.getVar("GrpContNo")+"&PrtNo="+mSwitch.getVar("GrpContNo");	
}else{
//top.fraInterface.window.location = "../appgrp/ContPolInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&polNo="+document.all('GrpContNo').value;
top.fraInterface.window.location = "../appgrp/ContPolInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype+"&prtNo="+mSwitch.getVar("GrpContNo")+"&polNo="+mSwitch.getVar( "GrpContNo" );
}


}

function afterSubmit( FlagStr, content )
{
   unlockScreen('lkscreen');  
	if (FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
	    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	queryperinsure();
}
}

function contGrpInsuredCreateExcel()
{
	var querySql = fm.querySql.value;
	var GrpContNo = fm.GrpContNo.value;
	if(querySql==""){
	alert ("���Ȳ�ѯ!");
}
else
	{
	try
		{
			//alert(querySql);
			fm.action = "./ContGrpInsuredCreateExcel.jsp?querySql="+querySql+"&GrpContNo="+GrpContNo;
        	fm.target=".";
   
       		document.getElementById("fm").submit();
					}
		catch(ex)
		{
			alert( ex );
		}
	}
}

function saveinsuinfo()
{
  if(document.all('ProposalGrpContNo').value == "" ||document.all('ManageCom').value == "")
  {
   alert("������Ͷ�����ź͹������!");
   return;
  }
  //if(PersonInsuredGrid.mulLineCount<=0)
  //{
  //  alert("û�пɱ��������,���Ȳ�ѯ������ӱ������˺��ڽ��б���!");
  //  return;
  //}
    var sqldate ="select substr(sysdate,0,10) from dual";
    var date = easyExecSql(sqldate);
	var filename=document.all('ManageCom').value+"_"+document.all('ProposalGrpContNo').value+"_"+date;
	
	filename=filename+".xls";
	//alert(filename);
	fm.FileName.value=filename; 
	var strSql = "select SysVarValue from LDSysVar where SysVar = 'InsuInfoCreat'";
   	filePath = easyExecSql(strSql);
   	//filePath = filePath+'/';
   	fm.Url.value=filePath;
   	
   	
	����fm.fmtransact.value = "Create";
	lockScreen('lkscreen');  
	    fm.action="./ContGrpInsuInfoSave.jsp";
	    document.getElementById("fm").submit();
}

function ConfirmSelect()
{
	 //showInfo.close();
	 
	 if(confirm("�����Ѿ����ɣ��Ƿ����¼���?������㽫����ԭʼ���ݡ�"))
	 {
	 	   var i = 0;
//       var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//       var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
 //      showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
	 	   //��ȷ��֮����ֵ��Ϊ1 �𵽿��ص�����
	 	   document.all('myconfirm').value = "1";
	 	   //�൱��window.location�ض�������
	 	   document.getElementById("fm").submit();
	  }
	else
		{
		}
}

function filedownload()
{
  var i = 0;
//  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
 
    var sqldate ="select substr(sysdate,0,10) from dual";
    var date = easyExecSql(sqldate);
	var filename=document.all('ManageCom').value+"_"+document.all('ProposalGrpContNo').value+"_"+date;
                      
       filename=filename+".xls";
	//prompt("",filename);
	fm.FileName.value=filename; 
	//alert("bbb");
	//fm.FileName.value="ReAgentAutoDown_86_200510.xls";
	var strSql = "select SysVarValue from LDSysVar where SysVar = 'InsuInfoCreat'";
   filePath = easyExecSql(strSql);
   fm.Url.value=filePath;
   //alert(fm.Url.value+filename);
   fm.fmtransact.value = "download";
   fm.action="./ContGrpInsuInfoSave.jsp";
   document.getElementById("fm").submit(); 
   
 }
   
