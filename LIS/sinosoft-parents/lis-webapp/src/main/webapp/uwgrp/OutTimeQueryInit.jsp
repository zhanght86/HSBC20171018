<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�OutTimeQueryInit.jsp
//�����ܣ��߰쳬ʱ��ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%
  String tProposalNo = "";
  String tFlag = "";

  tProposalNo = request.getParameter("ProposalNo2");
  tFlag = request.getParameter("Flag");


  loggerDebug("OutTimeQueryInit","ProposalNo:"+tProposalNo);
  loggerDebug("OutTimeQueryInit","Flag:"+tFlag);


%>            
<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox(tProposalNo)
{ 
try
  {                                   
    fm.all('ProposalNo').value = tProposalNo;
  }
  catch(ex)
  {
    alert("��RReportQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

function initForm(tProposalNo)
{
  try
  {	
  	initInpBox(tProposalNo);
	initPolGrid();		
	initHide(tProposalNo);
	easyQueryClick();	
	

  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
  {                              
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="Ͷ������";    	//����
      iArray[1][1]="180px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="�߰�����";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][10] = "UrgeNoticeKind";
      iArray[2][11] = "0|^10|���ڽ���֪ͨ��^11|���֪ͨ��^12|�˱�֪ͨ��^15|�ɷѴ߰�֪ͨ��(ǩ��������)";
      iArray[2][12] = "2";
      iArray[2][19] = "0";

      iArray[3]=new Array();
      iArray[3][0]="�ظ�״̬";         			//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][10] = "ReplyState2";
      iArray[3][11] = "0|^0|δ��ӡ^1|�Ѵ�ӡ^2|�ѻظ�";
      iArray[3][12] = "3";
      iArray[3][19] = "0";
      
      
      iArray[4]=new Array();
      iArray[4][0]="�߰�����";         			//����
      iArray[4][1]="180px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                           
      iArray[5]=new Array();
      iArray[5][0]="ʧЧ����";         			//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��ǰ����";         			//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                  
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      PolGrid.mulLineCount = 1;
      PolGrid.displayTitle = 1;
      PolGrid.canSel = 0;
      PolGrid.loadMulLine(iArray);      
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tProposalNo)
{
	fm.all('ProposalNoHide').value=tProposalNo;		
}

</script>


