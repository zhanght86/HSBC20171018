//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
//�ύ��ɺ���������
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

        //ֱ��ȡ��������ת����������
        var i = LLClaimRegisterGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimRegisterGrid.getRowColData(i,1);
            var tClmState = LLClaimRegisterGrid.getRowColData(i,2);
            tClmState = tClmState.substring(0,2);
            var tMissionID = LLClaimRegisterGrid.getRowColData(i,7);
            var tSubMissionID = LLClaimRegisterGrid.getRowColData(i,8);
            location.href="LLGrpClaimRegister.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
        }
    }
    tSaveFlag ="0";
}

function returnparent()
{
    var backstr=document.all("ContNo").value;
    //alert(backstr+"backstr");
    mSwitch.deleteVar("ContNo");
  mSwitch.addVar("ContNo", "", backstr);
  mSwitch.updateVar("ContNo", "", backstr);
    location.href="ContInsuredInput.jsp?LoadFlag=5&ContType="+ContType;
}

function showNotePad()
{
    alert("������")
    return;
  var selno = SelfPolGrid.getSelNo()-1;
  if (selno <0)
  {
        alert("��ѡ��һ������");
        return;
  }

  var MissionID = SelfPolGrid.getRowColData(selno, 6);
  var SubMissionID = SelfPolGrid.getRowColData(selno, 7);
  var ActivityID = SelfPolGrid.getRowColData(selno, 8);
  var PrtNo = SelfPolGrid.getRowColData(selno, 2);
  if(PrtNo == null || PrtNo == "")
  {
    alert("ӡˢ��Ϊ�գ�");
    return;
  }
  var NoType = "1";

  var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
  var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");

}

//��ʾ����ҳ��
function newReport()
{
    location.href="LLGrpClaimRegister.jsp?isNew=0";
}

// ��ʼ�����1
function queryGrid()
{
    initLLClaimRegisterGrid();
    var strSQL = "";
    var tCustomerName = fm.CustomerName.value;//�ͻ�����
    var tManageCom    = fm.ManageCom.value;   //�������
    //������,����״̬,�����˱���,����������,���屣����,��������
   /* strSQL = "select missionprop1,(case missionprop2 when '10' "
           + " then '10-�ѱ���' when '20' then '20-������' when '25' "
           + " then '25-�ӳ�����' end),missionprop3,missionprop7,"
           + " missionprop4,missionprop6,missionid,submissionid,"
           + " activityid,"
           + " makedate, "
           + " (select UserName from lduser "
           + " where UserCode = lwmission.lastoperator),missionprop20, "
           + " (select case count(*) when 0 then '��' else '��' end from es_doc_main where doccode=lwmission.missionprop1),"
           + " (select case count(*) when 0 then '��' else '��' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0')"
           + " from lwmission where 1=1 "
           + " and activityid = '0000009015'"
           + " and processid = '0000000009'"
           + " and DefaultOperator is null "
           + " and missionprop15 = '11' "
           //+ " and missionprop2 != '25' "
           + getWherePart( 'missionprop1' ,'RptNo')
           + getWherePart( 'missionprop2' ,'ClmState')
           + getWherePart( 'missionprop3' ,'CustomerNo')
           + getWherePart( 'missionprop6' ,'AccidentDate2')
           + getWherePart( 'missionprop7' ,'GrpContNo')*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
	mySql.setSqlId("LLGrpClaimRegisterMissSql1");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.ClmState.value ); 
	mySql.addSubPara(fm.CustomerNo.value ); 
	mySql.addSubPara(fm.AccidentDate2.value ); 
	mySql.addSubPara(fm.GrpContNo.value ); 
     //��������������������ȫ����Χ�ڲ���
     if (tCustomerName == "" || tCustomerName == null)
     {
        // strSQL = strSQL + " and missionprop20 like '" + tManageCom + "%%'"
         mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
		mySql.setSqlId("LLGrpClaimRegisterMissSql2");
		mySql.addSubPara(fm.RptNo.value ); 
		mySql.addSubPara(fm.ClmState.value ); 
		mySql.addSubPara(fm.CustomerNo.value ); 
		mySql.addSubPara(fm.AccidentDate2.value ); 
		mySql.addSubPara(fm.GrpContNo.value ); 
		mySql.addSubPara(tManageCom); 
     }
     else
     {
        // strSQL = strSQL + getWherePart( 'missionprop4' ,'CustomerName','like')
           mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
		mySql.setSqlId("LLGrpClaimRegisterMissSql3");
		mySql.addSubPara(fm.RptNo.value ); 
		mySql.addSubPara(fm.ClmState.value ); 
		mySql.addSubPara(fm.CustomerNo.value ); 
		mySql.addSubPara(fm.AccidentDate2.value ); 
		mySql.addSubPara(fm.GrpContNo.value ); 
		mySql.addSubPara(tManageCom); 
		mySql.addSubPara(fm.CustomerName.value ); 
     }
     //strSQL = strSQL + " order by makedate,missionprop2,missionid";
     turnPage.queryModal(mySql.getString(),LLClaimRegisterGrid,1,1);
}

// ��ʼ�����2
function querySelfGrid()
{
//��½������������,��½��������С��4λ�Ĳ�������в���
/*if( fm.ManageCom.value.length < 4){
    fm.riskbutton.disabled=true;
    fm.Report.disabled=true;
}else{
    fm.riskbutton.disabled=false;
    fm.Report.disabled=false;
}*/

    initSelfLLClaimRegisterGrid();
    var strSQL = "";
    var tRgtDateStart = document.all('RgtDateStart').value;//�Ǽ�����
    var tRgtDateEnd   = document.all('RgtDateEnd').value;  //�Ǽ�����
    var tOperator     = fm.Operator.value;           //�û�
    var tManageCom    = fm.ManageCom.value;          //�������
   /* strSQL = "select missionprop1,(case missionprop2 when '10' "
           +" then '10-�ѱ���' when '20' then '20-������' when '25' "
           +" then '25-�ӳ�����' end),missionprop3,missionprop4,"
           +" missionprop7,missionprop6,missionid,submissionid,"
           +" activityid,(select UserName from lduser "
           +" where UserCode = lwmission.lastoperator),missionprop20, "
           +" (select case count(*) when 0 then '��' else '��' end from es_doc_main where doccode=lwmission.missionprop1), "
           +" (select case count(*) when 0 then '��' else '��' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0'),"
           +" (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 "
           +" and activityid = '0000009015' "
           +" and processid = '0000000009'and missionprop10 is null "
           +" and missionprop15 = '11' "
           //+ " and missionprop2 != '25' "
           + getWherePart( 'missionprop1' ,'RptNo2')
           + getWherePart( 'missionprop7' ,'GrpContNo2')
           + getWherePart( 'missionprop3' ,'CustomerNo2')
           + getWherePart( 'missionprop4' ,'CustomerName2','like')*/
           //prompt("",strSQL);
		mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
		mySql.setSqlId("LLGrpClaimRegisterMissSql4");
		mySql.addSubPara(fm.RptNo2.value ); 
		mySql.addSubPara(fm.GrpContNo2.value ); 
		mySql.addSubPara(fm.CustomerNo2.value ); 
		mySql.addSubPara(fm.CustomerName2.value ); 
		mySql.addSubPara(tOperator); 
		
   //�������������ѯ
   var blDateStart = !(tRgtDateStart == null || tRgtDateStart == "");
   var blDateEnd = !(tRgtDateEnd== null ||tRgtDateEnd == ""); 
   
   if(blDateStart && blDateEnd)
   {
       /* strSQL = strSQL + " AND missionprop6 BETWEEN '"+
               tRgtDateStart + "' AND '" +tRgtDateEnd+ "'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
		mySql.setSqlId("LLGrpClaimRegisterMissSql5");
		mySql.addSubPara(fm.RptNo2.value ); 
		mySql.addSubPara(fm.GrpContNo2.value ); 
		mySql.addSubPara(fm.CustomerNo2.value ); 
		mySql.addSubPara(fm.CustomerName2.value ); 
		mySql.addSubPara(tRgtDateStart ); 
		mySql.addSubPara(tRgtDateEnd); 
		mySql.addSubPara(tOperator); 
   } 
   else if (blDateStart) 
   {
       // strSQL = strSQL + " AND makedate >= '" + tRgtDateStart + "'";
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
		mySql.setSqlId("LLGrpClaimRegisterMissSql6");
		mySql.addSubPara(fm.RptNo2.value ); 
		mySql.addSubPara(fm.GrpContNo2.value ); 
		mySql.addSubPara(fm.CustomerNo2.value ); 
		mySql.addSubPara(fm.CustomerName2.value ); 
		mySql.addSubPara(tRgtDateStart ); 
		mySql.addSubPara(tOperator); 
		
   } 
   else if (blDateEnd) 
   {
        //strSQL = strSQL + " AND makedate =< '" + tRgtDateEnd + "'";
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
		mySql.setSqlId("LLGrpClaimRegisterMissSql7");
		mySql.addSubPara(fm.RptNo2.value ); 
		mySql.addSubPara(fm.GrpContNo2.value ); 
		mySql.addSubPara(fm.CustomerNo2.value ); 
		mySql.addSubPara(fm.CustomerName2.value ); 
		mySql.addSubPara(tRgtDateEnd); 
		mySql.addSubPara(tOperator); 
   }

   // strSQL = strSQL + " and missionprop20 like '"+tManageCom+"%%' "
    //       +" and  DefaultOperator= '"+tOperator;
    //zy 2009-9-8 17:46 ���������һ�£������й��������У��
   // strSQL = strSQL +" and  DefaultOperator= '"+tOperator;
   // strSQL = strSQL + "' order by AcceptedDate,missionprop2,missionid";
    
    turnPage2.queryModal(mySql.getString(),SelfLLClaimRegisterGrid,1,1);
    
    HighlightSelfByRow();
}

//SelfLLClaimEndCaseGrid���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
function HighlightSelfByRow(){
    for(var i=0; i<SelfLLClaimRegisterGrid.mulLineCount; i++){
    	var accepteddate = SelfLLClaimRegisterGrid.getRowColData(i,14); //��������
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
    	   {
    		   SelfLLClaimRegisterGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}


//LLClaimRegisterGrid����¼�
function LLClaimRegisterGridClick()
{
}

//SelfLLClaimRegisterGrid����¼�
function SelfLLClaimRegisterGridClick()
{
                      HighlightSelfByRow();
					  //alert(document.all('ComCode').value.length);
					  var i = SelfLLClaimRegisterGrid.getSelNo()-1;
					  if(document.all('ComCode').value.length == 2)//�ܹ�˾���ܽ�����������
					  {
						  alert("�ܹ�˾���ܽ�����������!");
						  return false;
					  }
					  //----------------------jinsh20070522------------------//
					//  var oldornewsql2="select flowflag from llclaimmanage where managecom='"+document.all('ComCode').value+"'";
					//	var oldornewflag2=easyExecSql(oldornewsql2);
					var oldornewflag2 = '1';
						//alert(oldornewflag2);
						if(oldornewflag2=="1"||oldornewflag2=='1')//��
						{
							  //else//��λ������������
							  //{
							  			var tClmNo = SelfLLClaimRegisterGrid.getRowColData(i,1);
		        					var tClmState = SelfLLClaimRegisterGrid.getRowColData(i,2);
		        					tClmState = tClmState.substring(0,2);
		        					var tMissionID = SelfLLClaimRegisterGrid.getRowColData(i,7);
		        					var tSubMissionID = SelfLLClaimRegisterGrid.getRowColData(i,8);
		        					location.href="LLGrpClaimRegister.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
							  //}
						}
						if(oldornewflag2=="0"||oldornewflag2=='0')//��
						{
								//if(document.all('ComCode').value.length>2)//��λ������
								//{
											var tClmNo = SelfLLClaimRegisterGrid.getRowColData(i,1);
		        					var tClmState = SelfLLClaimRegisterGrid.getRowColData(i,2);
		        					tClmState = tClmState.substring(0,2);
		        					var tMissionID = SelfLLClaimRegisterGrid.getRowColData(i,7);
		        					var tSubMissionID = SelfLLClaimRegisterGrid.getRowColData(i,8);
		        					location.href="LLGrpClaimRegister.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
								//}
							  //else//��λ������������
							  //{
							  			//alert("�˲���ֻ������λ����������!");
							  			
							  //}
						}
	  //----------------------------jinsh20070522------------------------//
	  
	  
	  
	  
	  /*var printflagsql2="select count(*) from es_doc_main where doccode='"+SelfLLClaimRegisterGrid.getRowColData(i,1)+"'";
		var printflag2=easyExecSql(printflagsql2);
		if(printflag2!='0')
		{
				  if((document.all('ComCode').value.length)>2)
				  {
				  	alert("ɨ�谸����������ֻ������λ����������!");
				  }
				  else
					{
							var i = SelfLLClaimRegisterGrid.getSelNo();
    					if (i != '0')
    					{
		        			i = i - 1;
		       			  var tClmNo = SelfLLClaimRegisterGrid.getRowColData(i,1);
		        			var tClmState = SelfLLClaimRegisterGrid.getRowColData(i,2);
		        			tClmState = tClmState.substring(0,2);
		        			var tMissionID = SelfLLClaimRegisterGrid.getRowColData(i,7);
		        			var tSubMissionID = SelfLLClaimRegisterGrid.getRowColData(i,8);
		        			location.href="LLGrpClaimRegister.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
    					}
					}
		}
	  else
	  {
	  			
    			var i = SelfLLClaimRegisterGrid.getSelNo();
    			if (i != '0')
    			{
        			i = i - 1;
       			  var tClmNo = SelfLLClaimRegisterGrid.getRowColData(i,1);
        			var tClmState = SelfLLClaimRegisterGrid.getRowColData(i,2);
        			tClmState = tClmState.substring(0,2);
        			var tMissionID = SelfLLClaimRegisterGrid.getRowColData(i,7);
        			var tSubMissionID = SelfLLClaimRegisterGrid.getRowColData(i,8);
        			location.href="LLGrpClaimRegister.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
    			}
    }*/
}

//[����]��ť
function ApplyClaim()
{
/************************20070824-liuyu-���������������*********************/
						var selno = LLClaimRegisterGrid.getSelNo()-1;
						if (selno <0)
						{
									alert("��ѡ��Ҫ���봦��ı�����");
									return;
						}
						//var printflagsql="select count(*) from es_doc_main where doccode='"+LLClaimRegisterGrid.getRowColData(selno,1)+"'";
						//var printflag=easyExecSql(printflagsql);
						//alert(LLClaimRegisterGrid.getRowColData(selno,1));
						//alert(printflag);
						//if(printflag!='0')
						//{		  
								  if((document.all('ComCode').value.length)==2)
								  {
								  	alert("�ܹ�˾���ܽ�����������!");
								  	return false;
								  }
								  //else
								  //{
								  				//fm.MissionID.value = LLClaimRegisterGrid.getRowColData(selno, 7);
												  //fm.SubMissionID.value = LLClaimRegisterGrid.getRowColData(selno, 8);
												  //fm.ActivityID.value = LLClaimRegisterGrid.getRowColData(selno, 9);  
												  //fm.action = "./LLReportMissSave.jsp";
												  //submitForm(); //�ύ
								  //}
						//}
					  //else
					  //{
						/************************20030409jinshֻ�л�������λ�Ŀ��Բ���*********************/
												  //fm.MissionID.value = LLClaimRegisterGrid.getRowColData(selno, 7);
												  //fm.SubMissionID.value = LLClaimRegisterGrid.getRowColData(selno, 8);
												  //fm.ActivityID.value = LLClaimRegisterGrid.getRowColData(selno, 9);  
												  //fm.action = "./LLReportMissSave.jsp";
												  //submitForm(); //�ύ
						//}
						//var oldornewsql="select flowflag from llclaimmanage where managecom='"+document.all('ComCode').value+"'";
					//	var oldornewflag=easyExecSql(oldornewsql);
					//	alert(oldornewflag);
					oldornewflag = '1';
						if(oldornewflag=="1"||oldornewflag=='1')//��
						{
//								if(document.all('ComCode').value.length>2)//��λ������������
//								{
//											//alert("�˲���ֻ������λ����������!");
//											//return;
//								}
//							  else//��λ������������
//							  {
							  			fm.MissionID.value = LLClaimRegisterGrid.getRowColData(selno, 7);
											fm.SubMissionID.value = LLClaimRegisterGrid.getRowColData(selno, 8);
											fm.ActivityID.value = LLClaimRegisterGrid.getRowColData(selno, 9);  
											fm.action = "./LLReportMissSave.jsp";
											submitForm(); //�ύ
//							  }
						}
						if(oldornewflag=="0"||oldornewflag=='0')//��
						{
								if(document.all('ComCode').value.length>2)//��λ������
								{
											fm.MissionID.value = LLClaimRegisterGrid.getRowColData(selno, 7);
											fm.SubMissionID.value = LLClaimRegisterGrid.getRowColData(selno, 8);
											fm.ActivityID.value = LLClaimRegisterGrid.getRowColData(selno, 9);  
											fm.action = "./LLReportMissSave.jsp";
											submitForm(); //�ύ
								}
							  else//��λ������������
							  {
							  			alert("�˲���ֻ������λ����������!");
							  			return;							  			
							  }
						}
}

//�ύ����
function submitForm()
{
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
    tSaveFlag ="0";
}