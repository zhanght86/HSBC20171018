<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%
   String tGrpContNo="";
   tGrpContNo=request.getParameter("GrpContNo");
%>                            
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="UWReason.js"></SCRIPT>
<script language="JavaScript">


// ������ĳ�ʼ��
function initForm(tGrpContNo)
{ 
  try
  {
  fm.GrpContNo.value=tGrpContNo;
  initUWReasonGrid();
	}
  catch(re)
  {
    alert("UWReasonInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// �Ժ˲�ͨ��ԭ���ĳ�ʼ��
function initUWReasonGrid()
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
      iArray[1][0]="���˺�ͬ��";    	//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="���˿ͻ���";    	//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���˿ͻ�����";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���ֱ���";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��������";         			//����
      iArray[5][1]="180px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�˱�˳���";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�˱����";         		//����
      iArray[7][1]="300px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�˱�����";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		  UWReasonGrid = new MulLineEnter( "fm" , "UWReasonGrid" );
		  //��Щ���Ա�����loadMulLineǰ
		  UWReasonGrid.mulLineCount = 0;
		  UWReasonGrid.displayTitle = 1;
		  UWReasonGrid.hiddenPlus = 1;
		  UWReasonGrid.hiddenSubtraction = 1;
	  	//UWReasonGrid.canSel=1; //�Ƿ���ʾ��ѡ��
		  UWReasonGrid.loadMulLine(iArray);
      }
      
      catch(ex)
      {
        alert(ex);
      }
}


</script>


