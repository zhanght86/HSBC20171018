//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
// ��ѯ��ť
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

function easyQueryClick() 
{
	// ��ʼ�����
	initPolGrid();
	
    //��������У��
    if(fm.PrtNo.value == ""
       && fm.GrpContNo.value == ""
    	   )
    {
        alert("������Ͷ�����Ż������ͬ��!");
        return false;
    }
    
	var strOperate = "like";
	
	 	var sqlid1="DSHomeContSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.GrpFillListInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		
//	  var strSql=mySql1.getString();
	  
//	  var strSql = "select PrtNo,GrpContNo,ContNo,Peoples,Prem,Amnt from LCCont"
//	           + " where (appflag='1' or appflag='4') and poltype='1' and conttype='2' /*and peoples>0*/ and grpcontno !='00000000000000000000' "
//	           + " and ManageCom like "+fm.ManageCom.value+" and PrtNo like "+fm.PrtNo.value+" and GrpContNo like "+fm.GrpContNo.value+" and ContNo like "+fm.ContNo.value+""
	          
//EJB ����ǰSQL	
//	var strSql = "select PrtNo,GrpContNo,ContNo,Peoples,Prem,Amnt from LCCont"
//	           + " where (appflag='1' or appflag='4') and poltype='1' and conttype='2' /*and peoples>0*/ and grpcontno !='00000000000000000000' "
//	           + getWherePart('ManageCom','ManageCom',strOperate)
//    		     + getWherePart('PrtNo','PrtNo',strOperate )
//		         + getWherePart('GrpContNo','GrpContNo',strOperate)
//		         + getWherePart('ContNo','ContNo',strOperate)
	 var cerCodition =" and 1=1 ";//��֤/��������
//	 var SQL=strSql;
	//���ӶԼ�ҵ��������ѯ��֧�� ���� 2007-2-5   ��չ֧�ֲμӽ����������������ѯ
	if(fm.CertifyNo.value != "")
    {      
      cerCodition = " and trim(prtno) in (select ba.ProposalNo from LXbalance ba where ba.Conflag = '1' and "
                  + " exists (select 'x' from lxbalancesub basub where ba.balanceno=basub.balanceno and "
                  + " basub.startno<='"+fm.CertifyNo.value +"' and basub.endno>='"+fm.CertifyNo.value +"'))"; 
//       SQL = strSql + cerCodition ;
    }
	
  	//prompt("��������������ѯ",SQL);
  	
	mySql1.addSubPara(cerCodition);//ָ������Ĳ���
    var SQL=mySql1.getString();  	
	turnPage.queryModal(SQL,PolGrid);	
	
	
	if(fm.CertifyNo.value != "")
  { 
  	//���Ų�ѯ��ʽ ��ҪΪ��ҵ������ʹ�ã���¼��ʱ������֤
  	if(PolGrid.mulLineCount==0)
  	{  		    
  		    cerCodition = " and trim(prtno) in (select ProposalNo from LXbalance where Conflag = '1' "
                        + " and BalanceNo in (select trim(BalanceNo) from LXContInfo where 1=1 "
                        + getWherePart('CardNo','CertifyNo',strOperate )
                        + " ))"
//        SQL = strSql + cerCodition ;
//        turnPage.queryModal(SQL,PolGrid);	

	 	var sqlid2="DSHomeContSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.GrpFillListInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
        mySql2.addSubPara(cerCodition);//ָ������Ĳ���
        var SQL=mySql2.getString();  	
    	turnPage.queryModal(SQL,PolGrid);	
  	}
  	

  }
}

function addNameClick() 
{ 
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
        var ContNo = PolGrid.getRowColData(tSelNo - 1, 3); 	
        var Peoples =PolGrid.getRowColData(tSelNo - 1,4);
        if(Peoples<=0){
          alert("����������Ϊ 0,�������Ѿ����꣬��ȷ�ϣ�");
          return false;
        }
        
        //����������״̬��ʾ
          var sqlid4="DSHomeContSql4";
					var mySql4=new SqlClass();
					mySql4.setResourceName("app.GrpFillListInputSql"); //ָ��ʹ�õ�properties�ļ���
					mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
					mySql4.addSubPara(ContNo);//ָ������Ĳ���
				  var tStateSQL=mySql4.getString();	
        
        
//        var tStateSQL="select st.statetype,st.state from lccontstate st where st.contno='"+ContNo+"' ";
        var tContState=easyExecSql(tStateSQL);
        if(tContState==null)
        {
        	alert("��ѯ����״̬��Ϣʧ��");
        	return false;
        }
        var tStateType=tContState[0][0];
        var tState=tContState[0][1];
        
        
        if(tStateType=="Available"&&tState=="1")
        {//ʧЧ״̬����
        	if(!confirm("�ñ����Ѿ�ʧЧ��ȷ��Ҫ��������������"))
        	{
        		return false;
        	}
        }
        if(tStateType=="Terminate"&&tState=="1")
        {//��ֹ״̬����
        	if(!confirm("�ñ����Ѿ���ֹ��ȷ��Ҫ��������������"))
        	{
        		return false;
        	}
        }
        
        //alert(ContNo);
        window.open("../app/GrpContPolMain.jsp?ContNo=" + ContNo + "&PrtNo=" + PrtNo +"&LoadFlag=18&GrpContNo=" + GrpContNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
    }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr,content)
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {             
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
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
		content = "����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
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

//���У��ֻУ����������û��У�顰����������ʱ���
function checkenough(ContNo)
{
    var sqlid5="DSHomeContSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.GrpFillListInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(ContNo);//ָ������Ĳ���
	  var strSql1=mySql5.getString();	
    
//    var strSql1 = " select count(*) from lcpol where appflag='4' and MasterPolNo in (select  proposalNo from lcpol where Contno='" + ContNo + "')";    
    var arrResult1 = easyExecSql(strSql1);
    
    var sqlid6="DSHomeContSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.GrpFillListInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(ContNo);//ָ������Ĳ���
	  var strSql2=mySql6.getString();	
    
    //var strSql2 = " select count(*) from lcpol where  Contno='" + ContNo+"'";    
    var arrResult2 = easyExecSql(strSql2);    
    if(arrResult1[0][0] != arrResult2[0][0])
    {
    	alert("��������������ԭ��������¼�����в���,����ǩ��,��ȷ��!");
    	return false;
    }
    else
    {
    	return true;
    }
    	
}