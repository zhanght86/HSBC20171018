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

        //�����ɹ���ˢ��ҳ��
        //querySelfGrid();
        var i = SelfLLClaimSimpleGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = SelfLLClaimSimpleGrid.getRowColData(i,1);
            var tClmState = SelfLLClaimSimpleGrid.getRowColData(i,2);
            tClmState = tClmState.substring(0,2);
            var tMissionID = SelfLLClaimSimpleGrid.getRowColData(i,6);
            var tSubMissionID = SelfLLClaimSimpleGrid.getRowColData(i,7);
            
            //var tsql="select missionprop2 from lwmission where missionprop1='"+tClmNo+"' ";
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
			mySql.setSqlId("LLGrpClaimSimpleSql1");
			mySql.addSubPara(tClmNo); 
            var tclmstate=easyExecSql(mySql.getString());
            var isNew;
            if (tclmstate==10)   
            {
        	    isNew=0;
            }else
        	  {
        	    isNew=1;	
        	  }             
            
            location.href="LLGrpSimpleInput.jsp?RptNo="+tClmNo+"&isNew="+isNew+"&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
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
    var tManageCom = fm.ManageCom.value;
    var tCustomerName = fm.CustomerName.value;
    var tOperator = fm.Operator.value;
 /*δ�ӹ�����ǰ��SQL   
    strSQL = "select RgtNo,decode(ClmState,'60','�᰸','������'),"
           +" AppntNo,GrpName,Peoples2,RgtDate,Operator,MngCom "
           +" from LLRegister a where appntno is not null  and "
           +" rgtstate = '01' and ClmState != '40' and ClmState != '60'"
           + getWherePart( 'RgtNo' ,'RptNo')
           + getWherePart( 'AppntNo' ,'CustomerNo')
           + getWherePart( 'GrpContNo' ,'GrpContNo')
           if (tCustomerName!= '' && tCustomerName!= null)
           {
               strSQL = strSQL + " and GrpName like '%%" + tCustomerName + "%%'"
           }
           if(tManageCom != '%')
           {
               strSQL = strSQL + " and MngCom like '"+tManageCom+"%%'";
           }

    strSQL = strSQL + " order by RgtNo,RgtDate";
 */  
   /*strSQL = "select missionprop1,(case missionprop2 when '10' then '10-�ѱ���' when '20' then '20-������' end ),"
           + "missionprop3,missionprop4,missionprop6,missionid,submissionid,activityid,"           
           + "(select UserName from lduser where UserCode = lwmission.lastoperator),missionprop20, "
           + "(select case count(*) when 0 then '��' else '��' end from es_doc_main where doccode=lwmission.missionprop1), "
           + "(select case count(*) when 0 then '��' else '��' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0'), "
           + "makedate"
           + " from lwmission where 1=1 "
           + " and activityid = '0000009015'"
           + " and processid = '0000000009'"
           + " and DefaultOperator is null "
           + " and missionprop15 = '03' "*/
           + getWherePart( 'missionprop1' ,'RptNo')
           + getWherePart( 'missionprop3' ,'CustomerNo')
           + getWherePart( 'missionprop7' ,'GrpContNo')
           + getWherePart( 'missionprop6' ,'AccidentDate2')
           mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
			mySql.setSqlId("LLGrpClaimSimpleSql2");
			mySql.addSubPara(fm.RptNo.value); 
			mySql.addSubPara(fm.CustomerNo.value); 
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(fm.AccidentDate2.value); 
           if (tCustomerName!= '' && tCustomerName!= null)
           {
             //  strSQL = strSQL + " and missionprop4 like '%%" + tCustomerName + "%%'"
               mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
			mySql.setSqlId("LLGrpClaimSimpleSql3");
			mySql.addSubPara(fm.RptNo.value); 
			mySql.addSubPara(fm.CustomerNo.value); 
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(fm.AccidentDate2.value); 
			mySql.addSubPara(tCustomerName); 
           }
           if(tManageCom != '%')
           {
               //strSQL = strSQL + " and missionprop20 like '"+tManageCom+"%%'";
               mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
			mySql.setSqlId("LLGrpClaimSimpleSql4");
			mySql.addSubPara(fm.RptNo.value); 
			mySql.addSubPara(fm.CustomerNo.value); 
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(fm.AccidentDate2.value); 
			mySql.addSubPara(tCustomerName); 
			mySql.addSubPara(tManageCom); 
           }

   // strSQL = strSQL + " order by makedate,missionprop1,missionid";
    //prompt("����������ѯ",strSQL);
    turnPage.queryModal(mySql.getString(),SelfLLClaimSimpleGrid,1,1);
}

function queryGrid()
{
	 
    initLLClaimSimpleGrid();
    var strSQL = "";
    var tManageCom = fm.ManageCom.value;
    var tCustomerName = fm.CustomerName2.value;
    var tOperator = fm.Operator.value;

  /* strSQL = "select missionprop1,(case missionprop2 when '10' then '10-�ѱ���' when '20' then '20-������' end ),"
           + "missionprop3,missionprop4,missionprop6,missionid,submissionid,activityid,"
           + "(select UserName from lduser where UserCode = lwmission.lastoperator),missionprop20,"
           + "(select case count(*) when 0 then '��' else '��' end from es_doc_main where doccode=lwmission.missionprop1),"
           + "(select case count(*) when 0 then '��' else '��' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0'),"
           + "(select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 "
           + " and activityid = '0000009015'"
           + " and processid = '0000000009'"
           + " and DefaultOperator= '"+tOperator+"' "
           + " and missionprop15 = '03' "
           + getWherePart( 'missionprop1' ,'RptNo2')
           + getWherePart( 'missionprop3' ,'CustomerNo2')
           + getWherePart( 'missionprop7' ,'GrpContNo2')
           + getWherePart( 'missionprop6' ,'AccidentDate2')*/
           mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
			mySql.setSqlId("LLGrpClaimSimpleSql5");
			mySql.addSubPara(tOperator); 
			mySql.addSubPara(fm.RptNo2.value); 
			mySql.addSubPara(fm.CustomerNo2.value); 
			mySql.addSubPara(fm.GrpContNo2.value); 
			mySql.addSubPara(fm.AccidentDate2.value); 
			
           if (tCustomerName!= '' && tCustomerName!= null)
           {
               //strSQL = strSQL + " and missionprop4 like '%%" + tCustomerName + "%%'"
               mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
			mySql.setSqlId("LLGrpClaimSimpleSql6");
			mySql.addSubPara(tOperator); 
			mySql.addSubPara(fm.RptNo2.value); 
			mySql.addSubPara(fm.CustomerNo2.value); 
			mySql.addSubPara(fm.GrpContNo2.value); 
			mySql.addSubPara(fm.AccidentDate2.value); 
			mySql.addSubPara(tCustomerName); 
           }
           //zy 2009-9-8 17:50 �����ձ���һ�£������й��������У��
          // if(tManageCom != '%')
          // {
          //     strSQL = strSQL + " and missionprop20 like '"+tManageCom+"%%'";
          // }

   // strSQL = strSQL + " order by AcceptedDate,missionprop1,missionid";
    
    turnPage2.queryModal(mySql.getString(),LLClaimSimpleGrid,1,1);
    
    HighlightByRow();
}

//LLClaimSimpleGrid���ð������������������������Ѵﵽ20�ջ򳬹�20�յģ���ð�������ʾ��Ŀ��Ϊ��ɫ
function HighlightByRow(){
    for(var i=0; i<LLClaimSimpleGrid.mulLineCount; i++){
    	var accepteddate = LLClaimSimpleGrid.getRowColData(i,13); //��������
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//�������ڳ���20��
    	   {
    		   LLClaimSimpleGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}


function ApplyClaim2()
{
/************************20070824-liuyu-���������������*********************/
	  	
			var selno = SelfLLClaimSimpleGrid.getSelNo()-1;
			if (selno <0)
			{
				 alert("��ѡ��Ҫ���봦��ı�����");
				 return;
			}						

			if(document.all('ComCode').value.length==2)//��λ�������ܽ�����������
			{
				alert("�ܹ�˾���ܽ�����������!");
				return false;
			}
						//var oldornewsql="select flowflag from llclaimmanage where managecom='"+document.all('ComCode').value+"'";
						//2009-02-04 zhangzheng Ŀǰû��llclaimmanage����ʱ��ֵ����������
						//var oldornewflag=easyExecSql(oldornewsql);				
						//alert(oldornewflag);
						mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
						mySql.setSqlId("LLGrpClaimSimpleSql7");
						mySql.addSubPara(document.all('ComCode').value); 
						////var oldornewflag=easyExecSql(mySql.getString());
						//7û�б�ִ�У�����������䱻ע����
						var oldornewflag='1';
						if(oldornewflag=="1"||oldornewflag=='1')//��
						{
//								else
								//{							  
						       fm.MissionID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 6);
						       fm.SubMissionID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 7);
						       fm.ActivityID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 8);
						       
						       fm.action = "./LLSimpleReportMissSave.jsp";
						       submitForm(); //�ύ
						    // }
						}
						if(oldornewflag=="0"||oldornewflag=='0')//��
						{
								//if(document.all('ComCode').value.length>2)//��λ������
								//{						
						       fm.MissionID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 6);
						       fm.SubMissionID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 7);
						       fm.ActivityID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 8);
						       
						       fm.action = "./LLSimpleReportMissSave.jsp";
						       submitForm(); //�ύ			
						     //}
						     //else//��λ������������
							  //{
							  			//alert("�˲���ֻ������λ����������!");
							  			//return;							  			
							   //}									
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
						//var oldornewsql="select flowflag from llclaimmanage where managecom='"+document.all('ComCode').value+"'";
						//2009-02-04 zhangzheng Ŀǰû��llclaimmanage����ʱ��ֵ����������
						//var oldornewflag=easyExecSql(oldornewsql);
						//alert(oldornewflag);
						mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
						mySql.setSqlId("LLGrpClaimSimpleSql8");
						mySql.addSubPara(document.all('ComCode').value); 
						////var oldornewflag=easyExecSql(mySql.getString()); //�������Ҳ��ע���ˣ���ִ�У�
						if(document.all('ComCode').value.length==2)//�ܹ�˾���ܽ�����������
						{
							alert("�ܹ�˾���ܽ�����������!");
							return false;
						}
						var oldornewflag='1';
						if(oldornewflag=="1"||oldornewflag=='1')//��
						{
								//else
								//{			  		
						           var i = LLClaimSimpleGrid.getSelNo();
						           if (i != '0')
						           {
						               i = i - 1;
						               var tRptNo = LLClaimSimpleGrid.getRowColData(i,1); 
						               var tMissionID = LLClaimSimpleGrid.getRowColData(i,6);
						               var tSubMissionID = LLClaimSimpleGrid.getRowColData(i,7);    
						                       
						               //var tsql="select missionprop2 from lwmission where missionprop1='"+tRptNo+"' ";
						               mySql = new SqlClass();
										mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
										mySql.setSqlId("LLGrpClaimSimpleSql9");
										mySql.addSubPara(tRptNo); 
						               var tclmstate=easyExecSql(mySql.getString());
						               var isNew;
						               if (tclmstate==10)   
						               {
						               		isNew=0;
						               }
						               else
						               {
						               	  isNew=1;	
						               }
						               location.href="LLGrpSimpleInput.jsp?isNew="+isNew+"&RptNo="+tRptNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
						           }
								//}
			     }
			     if(oldornewflag=="0"||oldornewflag=='0')//��
						{
				        var i = LLClaimSimpleGrid.getSelNo();
				        if (i != '0')
				        {
				            i = i - 1;
				            var tRptNo = LLClaimSimpleGrid.getRowColData(i,1); 
				            var tMissionID = LLClaimSimpleGrid.getRowColData(i,6);
				            var tSubMissionID = LLClaimSimpleGrid.getRowColData(i,7);    
				                    
				            //var tsql="select missionprop2 from lwmission where missionprop1='"+tRptNo+"' ";
				             mySql = new SqlClass();
										mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
										mySql.setSqlId("LLGrpClaimSimpleSql10");
										mySql.addSubPara(tRptNo); 
				            var tclmstate=easyExecSql(mySql.getString());
				            var isNew;
				            if (tclmstate==10)   
				            {
				            		isNew=0;
				            }
				            else
				            {
				            	  isNew=1;	
				            }
				            location.href="LLGrpSimpleInput.jsp?isNew="+isNew+"&RptNo="+tRptNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
				        }
						}
			     
			     
}

//[����]��ť
function ApplyClaim()
{
     location.href="LLGrpSimpleInput.jsp?";
}

//������Ϣ����
function PrintReportClass()
{
     location.href="LLGrpReportSimpleInput.jsp?";
     //window.open("./LLGrpReportSimpleInput.jsp?");
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

function getin()
{

   var tFlag  = "TOSIM";
   var tRptNo = fm.RptNo.value;
//   var strUrl = "../claim/GrpCustomerDiskInput.jsp?grpcontno="+tRptNo+"&Flag="+tFlag;
   var strUrl = "../claim/GrpCustomerDiskMain.jsp?Flag="+tFlag;
   //showInfo=window.open(strUrl,"�������嵥����","");
   showInfo=window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}