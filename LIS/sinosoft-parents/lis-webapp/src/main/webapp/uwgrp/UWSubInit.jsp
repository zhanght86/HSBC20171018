<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWSubInit.jsp
//�����ܣ��˱��켣��ѯ
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
  String tContNo = "";
  String tPolNo = "";
  tContNo = request.getParameter("ContNo");
  tPolNo = request.getParameter("PolNo");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��UWSubInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tContNo, tPolNo)
{
  try
  {
	initUWSubGrid();
	initHide(tContNo, tPolNo);
	easyQueryClick();
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initUWSubGrid()
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
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�˱�˳���";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�˱�����";         			//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][10] = "End";
      iArray[3][11] = "0|^1|�ܱ�^2|����^3|�����б�^4|ͨ�ڳб�^5|�Զ��˱�^6|���ϼ��˱�^8|���˱�֪ͨ��^9|�����б�^a|����Ͷ����^b|���ռƻ����^z|�˱�����";
      iArray[3][12] = "3";
      iArray[3][13] = "1";


      iArray[4]=new Array();
      iArray[4][0]="�˱����";         		//����
      iArray[4][1]="240px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�˱���";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�˱�����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWSubGrid = new MulLineEnter( "fm" , "UWSubGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      UWSubGrid.mulLineCount = 10;   
      UWSubGrid.displayTitle = 1;
      UWSubGrid.locked = 1;
      UWSubGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //UWSubGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo, tPolNo)
{
	fm.all('ContNo').value = tContNo;
	fm.all('PolNo').value = tPolNo;
}

</script>

<%
	// ��ʼ��ʱ�Ĳ�ѯ���εĺ���
	//out.println("<script language=javascript>");
	//out.println("function queryUWSub()");
	//out.println("{");
	//out.println("}");
	//out.println("</script>");
%>

