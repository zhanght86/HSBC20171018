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
        var i = SelfLLClaimSimpleGrid.getSelNo();
        if (i != '0')
        {   
            i = i - 1;
            var tClmNo = SelfLLClaimSimpleGrid.getRowColData(i,1);
            var tClmState = SelfLLClaimSimpleGrid.getRowColData(i,2);
            var tMissionID = SelfLLClaimSimpleGrid.getRowColData(i,6);
            var tSubMissionID = SelfLLClaimSimpleGrid.getRowColData(i,7);
            location.href="LLGrpSimpleInput.jsp?RptNo="+tClmNo+"&isNew=2&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
        }
    }
    tSaveFlag ="0";
}

function returnparent()
{
    var backstr=document.all("ContNo").value;
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


// ��ʼ�����2
function querySelfGrid()
{
    
    initSelfLLClaimSimpleGrid();
    var strSQL = "";
    var tManageCom    = fm.ManageCom.value;
    var tCustomerName = fm.CustomerName.value;
    var tOperator     = fm.Operator.value;
    
    /*strSQL = "select RgtNo,decode(trim(ClmState),'60','�᰸','������'),"
           +" AppntNo,GrpName,Peoples2,RgtDate,Operator,MngCom "
           +" from LLRegister a where appntno is not null  and "
           +" rgtstate in( '01','03') and ClmState not in('20','25') and ClmState != '60'"
           + getWherePart( 'RgtNo' ,'RptNo')
           + getWherePart( 'AppntNo' ,'CustomerNo')
           if (tCustomerName!= '' && tCustomerName!= null)
           {
               strSQL = strSQL + " and GrpName like '%%" +tCustomerName + "%%'"
           }
           if(tManageCom != '%')
           {
               strSQL = strSQL + " and MngCom like '"+tManageCom+"%%'";
           }
    strSQL = strSQL + " order by RgtNo,RgtDate";

    turnPage2.queryModal(strSQL,SelfLLClaimSimpleGrid);*/

    /*var strSQL = "";
    strSQL = "select missionprop1,(case missionprop2 when '20' then '����' when '30' then '���' when '40' then '����' end),"
         + "missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),"
         + "missionid,submissionid,activityid,lastoperator,missionprop20,"                           
         + " (select case count(*) when 0 then '��' else '��' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0') "
         + " from lwmission where 1=1 "      
         + " and activityid = '0000009035'"   
         + " and processid = '0000000009'"
         + " and DefaultOperator is null "         
         + getWherePart( 'missionprop1' ,'RptNo')         
         + getWherePart( 'missionprop3' ,'CustomerNo')
         + getWherePart( 'missionprop4' ,'CustomerName','like')
//         + getWherePart( 'missionprop5' ,'customerSex')
//         + getWherePart( 'missionprop6' ,'AccidentDate2')
         + " and MissionProp19 = '0'"   //û��ԭ�����
         + " and MissionProp15 ='03' "
//         + " and (missionprop20 like '" + fm.ManageCom.value + "%%'"  //����
//         + " or (missionprop20 like '" + fm.ManageCom.value + "%%'"  //��������
//         + " and missionprop18 = '" + tRank + "'))" */
mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
mySql.setSqlId("LLGrpClaimConfirmSql1");
mySql.addSubPara(fm.RptNo.value ); 
mySql.addSubPara(fm.CustomerNo.value ); 
mySql.addSubPara(fm.CustomerName.value ); 
         if(fm.ManageCom.value.length >= 2){
        // strSQL += " and missionprop20 like '" + fm.ManageCom.value + "%%'" 
         mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
mySql.setSqlId("LLGrpClaimConfirmSql2");
mySql.addSubPara(fm.RptNo.value ); 
mySql.addSubPara(fm.CustomerNo.value ); 
mySql.addSubPara(fm.CustomerName.value ); 
mySql.addSubPara(fm.ManageCom.value ); 
         }
       //  strSQL += " order by missionprop10 desc,missionid ";
    //prompt("����ز�ѯ",strSQL);
    turnPage.queryModal(mySql.getString(),SelfLLClaimSimpleGrid,1,1);
}

function queryGrid()
{
   	initLLClaimSimpleGrid();
    var strSQL = "";
    var tManageCom    = fm.ManageCom.value;
    var tCustomerName = fm.CustomerName.value;
    var tOperator     = fm.Operator.value;    

    /*var strSQL = "";
    strSQL = "select missionprop1,(case missionprop2 when '20' then '����' when '30' then '���' when '40' then '����' end),"
         + "missionprop3,missionprop4,(case missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end),"
         + "missionid,submissionid,activityid,lastoperator,missionprop20,"                           
         + " (select case count(*) when 0 then '��' else '��' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0'), "
         + " (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 "
         + " and activityid = '0000009035'"   
         + " and processid = '0000000009'"
         + " and (DefaultOperator = '" + fm.Operator.value + "'"
         + " or MissionProp19 = '" + fm.Operator.value + "')"
         + " and MissionProp15 ='03' "
         // zy 2009-9-8 17:55 ���������һ�£�ȡ�������������
        // if(fm.ManageCom.value.length >= 2){
        // strSQL += " and missionprop20 like '" + fm.ManageCom.value + "%%'" 
        // }
         strSQL += " order by AcceptedDate,missionprop10 desc,missionid ";*/
    //prompt("����������˹�������:",strSQL);
    
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
	mySql.setSqlId("LLGrpClaimConfirmSql3");
	mySql.addSubPara(fm.Operator.value ); 
	mySql.addSubPara(fm.Operator.value );  
    
    turnPage2.queryModal(mySql.getString(),LLClaimSimpleGrid,1,1);
    HighlightByRow();
}

//SelfLLClaimEndCaseGrid���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
function HighlightByRow(){
    for(var i=0; i<LLClaimSimpleGrid.mulLineCount; i++){
    	var accepteddate = LLClaimSimpleGrid.getRowColData(i,12); //��������
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
    	   {
    		   LLClaimSimpleGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}


//LLClaimSimpleGrid����¼�
function SelfLLClaimSimpleGridClick()
{
}

//SelfLLClaimSimpleGrid����¼�
function LLClaimSimpleGridClick()
{    
	          HighlightByRow();
			  /************************20030410jinshֻ�л�������λ�Ŀ��Բ���*********************/
			  //alert(document.all('ComCode').value.length);
//			  if((document.all('ComCode').value.length)>2)
//			  {
//			  		alert("�᰸����ֻ������λ����������!");
//			  }
//			  else
//			  {
							 	/************************20030410jinshֻ�л�������λ�Ŀ��Բ���*********************/
						    var i = LLClaimSimpleGrid.getSelNo();
						    if (i != '0')
						    {
						          i = i - 1;
						          var tRptNo = LLClaimSimpleGrid.getRowColData(i,1);			        
						          var tMissionID = LLClaimSimpleGrid.getRowColData(i,6);
						          var tSubMissionID = LLClaimSimpleGrid.getRowColData(i,7);                
						          location.href="LLGrpBatchAuditInput.jsp?RptNo="+tRptNo+"&isNew=2&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;             
						    }
//				}

}

//[����]��ť
function ApplyClaim()
{
location.href="LLGrpSimpleInput.jsp?'";
}

function ApplyClaim2()
{
	 /************************20030410jinshֻ�л�������λ�Ŀ��Բ���*********************/
	 //alert(document.all('ComCode').value.length);
	 if((document.all('ComCode').value.length)>2)
	 {
	 	alert("�᰸����ֻ������λ����������!");
	 }
	 else
	 {
	 /************************20030410jinshֻ�л�������λ�Ŀ��Բ���*********************/
					     var selno = SelfLLClaimSimpleGrid.getSelNo()-1;
					     if (selno <0)
					     {
					        alert("��ѡ��Ҫ���봦��İ�����");
					        return;
					     }
					     var tRgtNO = SelfLLClaimSimpleGrid.getRowColData(selno, 1);
					   //  var tSql = "select Operator from llregister where rgtno = '"+tRgtNO+"'";
					     mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql4");
						mySql.addSubPara(tRgtNO);   
					     var tOperator = easyExecSql(mySql.getString());
					     if(fm.Operator.value == tOperator)
					     {
					        alert("������˺͸��˽᰸����Ϊͬһ������Ա!");
					        return;
					     }
				//var txsql="select distinct givetype from llclaimdetail where clmno='"+tRgtNO+"' ";
				mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql5");
						mySql.addSubPara(tRgtNO);  
        var tGiveType=easyExecSql(mySql.getString());
				
			if (tGiveType == '0')     
      {	
        //var csql="select customerno from llcase where caseno='"+tRgtNO+"' ";
        mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql6");
						mySql.addSubPara(tRgtNO);  
        var tcustomerno=new Array();
        tcustomerno=easyExecSql(mySql.getString());
        var tClaimType1="";
        var tClaimType2="";
        for (var i=0;i<tcustomerno.length;i++)
        { 				

				 //01.1��ѯ���ⰸ�������յ��������ֵ
          /*  var strSql00 = " select sum(realpay) from llclaimpolicy "
                      + " where clmno = '"+tRgtNO+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='10') and insuredno='"+tcustomerno[i]+"'";                  
		*/ mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql7");
						mySql.addSubPara(tRgtNO); 
						mySql.addSubPara(tcustomerno[i]); 
            var tSubReport = new Array;
            tSubReport = easyExecSql(mySql.getString());    
            var tRealpay1 = tSubReport[0][0];            
            if(tRealpay1==null || tRealpay1 == "")
              {
                 tRealpay1=0;       
              }
             else
              {              	 
              	 tClaimType1='10';              	 
              }
         //01.2��ѯ���ⰸ���˽����յ��������ֵ    
           /* var strSql01 = " select sum(realpay) from llclaimpolicy "
                          + " where clmno = '"+tRgtNO+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='30') and insuredno='"+tcustomerno[i]+"'";                  
          */  mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql8");
						mySql.addSubPara(tRgtNO); 
						mySql.addSubPara(tcustomerno[i]); 
            var tSubReport1 = new Array;
            tSubReport1 = easyExecSql(mySql.getString());
            var tRealpay2 = tSubReport1[0][0];
            if(tRealpay2==null || tRealpay2 == "")
              {
                 tRealpay2=0;           
              }
            else
            	{            	   
            	   tClaimType2='30';            	   
            	}              
                  
				//0501.��ѯ�û����������Ȩ��
				    var tManageCom = fm.ManageCom.value.substring(0,2);
				  /*  var strSql0501 = " select checklevel, username,usercode from llclaimuser where comcode like '"+tManageCom+"%%' and StateFlag = '1' and checklevel = (select max(checklevel) from llclaimuser a , Lduser b where a.comcode like '"+tManageCom+"%%'"
								    //+" and a.StateFlag = '1'"
								    +" and a.usercode = b.usercode and b.userstate = '0') order by comcode" ;
				   */mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql9");
						mySql.addSubPara(tManageCom); 
						mySql.addSubPara(tManageCom); 
				   
				    var tSubReport2 = new Array;
				        tSubReport2 = easyExecSql(mySql.getString());
				    if(tSubReport2 != null)
				    {
				        var tChecklevel = tSubReport2[0][0];
				        var tUserName   = tSubReport2[0][1];
				        var tUserCode   = tSubReport2[0][2];
				    }else{
				       // alert("δ��ѯ���÷ֻ�������߼���������Ա!");
				        //return false;
				    }
				
				//0502.1��ѯ�û������Ȩ�޵������⸶���
				 //   var strSql0502 = "select basemax from LLClaimPopedom where claimtype = '"+tClaimType1+"' and Popedomlevel = '"+tChecklevel+"'";
				   mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql10");
						mySql.addSubPara(tClaimType1); 
						mySql.addSubPara(tChecklevel); 
				    var tBasemax1  = easyExecSql(mySql.getString());
				        tBasemax1  = tBasemax1*1;
				        tRealpay1  = tRealpay1*1;
				        
				//0502.2��ѯ�û������Ȩ�޵Ľ������⸶���
				 //   var strSql0503 = "select basemax from LLClaimPopedom where claimtype = '"+tClaimType2+"' and Popedomlevel = '"+tChecklevel+"'";
				   mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql11");
						mySql.addSubPara(tClaimType2); 
						mySql.addSubPara(tChecklevel); 
				    var tBasemax2  = easyExecSql(mySql.getString());
				        tBasemax2 = tBasemax2*1;
				        tRealpay2  = tRealpay2*1;				        
				//�����⸶��������������⼶���ж�
				   if(((tRealpay1 > tBasemax1)||(tRealpay2 > tBasemax2))&&(tUserCode != fm.Operator.value))
				    {
				        alert("�����⸶�������ֹ�˾������⼶��,�� "+tUserName+" ��¼�Ѱ����ϱ�~!");
				        return false;
				    }
			  }	//for tcustomerno
			}else if (tGiveType == '2'||tGiveType == '3')
       {   
            //var csql2="select customerno from llcase where caseno='"+tRgtNO+"' ";
            mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql12");
						mySql.addSubPara(tRgtNO);   
            var xcustomerno=new Array();
            xcustomerno=easyExecSql(mySql.getString());
       
				    var tManageCom = fm.ManageCom.value.substring(0,2);
				   // var strSql0501 = " select checklevel, username,usercode from llclaimuser where comcode like '"+tManageCom+"%%' and StateFlag = '1' and checklevel = (select max(checklevel) from llclaimuser a , Lduser b where a.comcode like '"+tManageCom+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0') order by comcode" ;
				    mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql13");
						mySql.addSubPara(tManageCom); 
						mySql.addSubPara(tManageCom); 
				    var tSubReport2 = new Array;
				        tSubReport2 = easyExecSql(mySql.getString());
				    if(tSubReport2 != null)
				    {
				        var tChecklevel = tSubReport2[0][0];
				        var tUserName   = tSubReport2[0][1];
				        var tUserCode   = tSubReport2[0][2];
				    }else{
				        alert("δ��ѯ���÷ֻ�������߼���������Ա!");
				        return false;
				    }
          //  var strSql0502 = "select distinct appendmax from LLClaimPopedom where Popedomlevel = '"+tChecklevel+"'";                 
           mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql14");
						mySql.addSubPara(tChecklevel);  
            var tBasemax3  = easyExecSql(mySql.getString());                 
            tBasemax3 = tBasemax3*1;
				           
           for (var k=0;k<xcustomerno.length;k++)
           {         		
            // var maxsql="select sum(realpay) from llclaimpolicy where clmno = '"+tRgtNO+"' and insuredno='"+xcustomerno[k]+"' ";
            mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql15");
						mySql.addSubPara(tRgtNO); 
						mySql.addSubPara(xcustomerno[k]); 
             var tRealpay=easyExecSql(mySql.getString());  
				     if((tRealpay > tBasemax3)&&(tUserCode != fm.Operator.value))
				     {
				        alert("����ͨ�ڡ�Э���⸶�������ֹ�˾������⼶��,�� "+tUserName+" ��¼�Ѱ����ϱ�~!");
				        return false;
				     }                    
       
           }
        }else if (tGiveType == '1')         
         {
				    var tManageCom = fm.ManageCom.value.substring(0,2);
				  //  var strSql0501 = " select checklevel, username,usercode from llclaimuser where comcode like '"+tManageCom+"%%' and StateFlag = '1' and checklevel = (select max(checklevel) from llclaimuser a , Lduser b where a.comcode like '"+tManageCom+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0') order by comcode" ;
				    mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimConfirmInputSql");
						mySql.setSqlId("LLGrpClaimConfirmSql16");
						mySql.addSubPara(tManageCom); 
						mySql.addSubPara(tManageCom); 
				    var tSubReport2 = new Array;
				        tSubReport2 = easyExecSql(mySql.getString());
				    if(tSubReport2 != null)
				    {
				        var tChecklevel = tSubReport2[0][0];
				        var tUserName   = tSubReport2[0][1];
				        var tUserCode   = tSubReport2[0][2];
				    }else{
				        alert("δ��ѯ���÷ֻ�������߼���������Ա!");
				        return false;
				    }         	
            if ((tChecklevel=="A"||tChecklevel=="B1"||tChecklevel=="B2"||tChecklevel=="B3")&&(tUserCode != fm.Operator.value))
            {
              alert("�����ܸ�Ȩ�޳������ֹ�˾������⼶��,�� "+tUserName+" ��¼�Ѱ����ϱ�~!");	
              return false;
            }             
         
         }  
					     fm.MissionID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 6);        
					     fm.SubMissionID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 7);    
					     fm.ActivityID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 8);        	    
					     fm.action = "./LLReportMissSave.jsp";
					     submitForm(); //�ύ
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