//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
// ��ѯ��ť
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var mySql = new SqlClass();
function easyQueryClick() {
	// ��ʼ�����
	//alert(321);
	initPolGrid();
	
    if(fm.PrtNo.value == ""
        && fm.GrpContNo.value == ""
     	   )
     {
         alert("����������һ����ѯ����!");
         return false;
     }
    
	var strOperate = "like";
	//var strSql =  " select b.PrtNo,b.GrpContNo,b.ContNo,b.Peoples, b.Prem, b.Amnt"
				//+ " from lccont b where b.contno in"
				//+ " (select c.contno from lcpol c"
				//+ " where c.riskcode in"
				//+ " (select d.riskcode from lmrisksort d where d.risksorttype = '23'))"
				//+ getWherePart('ManageCom','ManageCom',strOperate)
    		    //+ getWherePart('PrtNo','PrtNo')
		        //+ getWherePart('GrpContNo','GrpContNo')
		        //+ getWherePart('ContNo','ContNo');
	
	/*var strSql =  " select c.PrtNo, c.GrpContNo, c.customerno, c.grpname"
		+ " from  lcgrppol c"
		+ " where  c.riskcode in (select d.riskcode   from lmrisksort d  where d.risksorttype = '23')"
		+ getWherePart('ManageCom','ManageCom',strOperate)
	    + getWherePart('PrtNo','PrtNo')
        + getWherePart('GrpContNo','GrpContNo')
        + " group by c.PrtNo, c.GrpContNo, c.customerno, c.grpname";*/
	
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.InsuredRelaWorkPoolSql");
	mySql.setSqlId("InsuredRelaWorkPoolSql1");
	mySql.addSubPara(fm.ManageCom.value ); 
	mySql.addSubPara(fm.PrtNo.value ); 
	mySql.addSubPara(fm.GrpContNo.value ); 
//	 var cerCodition ="";//��֤/��������
	// var SQL=strSql;
	//���ӶԼ�ҵ��������ѯ��֧�� ���� 2007-2-5   ��չ֧�ֲμӽ����������������ѯ
//	if(fm.CertifyNo.value != "")
//    {
//      cerCodition = " and trim(prtno) in (select ba.ProposalNo from LXbalance ba where ba.Conflag = '1' and "
//                  + " exists (select 'x' from lxbalancesub basub where ba.balanceno=basub.balanceno and "
//                  + " basub.startno<='"+fm.CertifyNo.value +"' and basub.endno>='"+fm.CertifyNo.value +"'))"; 
//       SQL = strSql + cerCodition ;
//    }
	
  	//prompt("��ѯsql",SQL);
  	
  	
	turnPage.queryModal(mySql.getString(),PolGrid);	
	
	
//	if(fm.CertifyNo.value != "")
//  { 
//  	//���Ų�ѯ��ʽ ��ҪΪ��ҵ������ʹ�ã���¼��ʱ������֤
//  	if(PolGrid.mulLineCount==0)
//  	{
//  		    cerCodition = " and trim(prtno) in (select ProposalNo from LXbalance where Conflag = '1' "
//                        + " and BalanceNo in (select trim(BalanceNo) from LXContInfo where 1=1 "
//                        + getWherePart('CardNo','CertifyNo',strOperate )
//                        + " ))"
//        SQL = strSql + cerCodition ;
//        turnPage.queryModal(SQL,PolGrid);	
//  	}
  	

//  }
}

function addNameClick(parm1,parm2) {
	var tSelNo = PolGrid.getSelNo();
    sFeatures = "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1";
    if (tSelNo == 0) 
    { 
        alert("��ѡ�񱣵���");
    }
    else 
    {
        var PrtNo = PolGrid.getRowColData(tSelNo - 1, 1); 	
        var GrpContNo = PolGrid.getRowColData(tSelNo - 1, 2); 	
        //var ContNo = PolGrid.getRowColData(tSelNo - 1, 3); 	
        
        //����������״̬��ʾ
        //var tStateSQL="select st.statetype,st.state from lccontstate st where st.contno='"+ContNo+"' ";
        //var tContState=easyExecSql(tStateSQL);
        //if(tContState==null)
        //{
        	//alert("��ѯ����״̬��Ϣʧ��");
        	//return false;
        //}
        //var tStateType=tContState[0][0];
        //var tState=tContState[0][1];
        
        
        //if(tStateType=="Available"&&tState=="1")
        //{//ʧЧ״̬����
        	//if(!confirm("�ñ����Ѿ�ʧЧ��ȷ��Ҫ��������������"))
        	//{
        		//return false;
        	//}
        //}
        //if(tStateType=="Terminate"&&tState=="1")
        //{//��ֹ״̬����
        	//if(!confirm("�ñ����Ѿ���ֹ��ȷ��Ҫ��������������"))
        	//{
        		//return false;
        	//}
        //}
        
        //alert(ContNo);
        //window.open("./InsuredRelaMain.jsp?PrtNo=" + PrtNo +"&LoadFlag=18&GrpContNo=" + GrpContNo,"window1");
        window.open("./InsuredRelaMain.jsp?PrtNo=" + PrtNo +"&LoadFlag=18&GrpContNo=" + GrpContNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
    }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr,content)
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {             
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
		content = "����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();


    
    }
}

//���У��ֻУ����������û��У�顰����������ʱ���
//function checkenough(ContNo)
//{
//    var strSql1 = " select count(*) from lcpol where appflag='4' and MasterPolNo in (select  proposalNo from lcpol where Contno='" + ContNo + "')";    
//    var arrResult1 = easyExecSql(strSql1);
//    var strSql2 = " select count(*) from lcpol where  Contno='" + ContNo+"'";    
//    var arrResult2 = easyExecSql(strSql2);    
//    if(arrResult1[0][0] != arrResult2[0][0])
//    {
//    	alert("��������������ԭ��������¼�����в���,����ǩ��,��ȷ��!");
//    	return false;
//    }
//    else
//    {
//    	return true;
//    }
//    	
//}