<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ImageQueryInit.jsp
//�����ܣ�Ӱ�����ϲ�ѯ
//�������ڣ�2005-07-07 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>                            
          
<script language="JavaScript">
	
function initAll() {
    fm.all('ContNo').value = ContNo; 
    fm.all('subtype').value = '';
    fm.all('subtypname').value = '';
} 

function initForm() {
  try {

	initAll();
	initImageGrid();  
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


//��������Ϣ��ʼ��
function initImageGrid()
  {                               
    var iArray = new Array();
       
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ҵ�����";         		//����
      iArray[1][1]="200px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  
      iArray[2]=new Array();
      iArray[2][0]="Ӱ�����";         		//����
      iArray[2][1]="180px";            		//�п�
      iArray[2][2]=200;             			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ӱ������";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��֤���";         		//����
      iArray[4][1]="250px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
       
      ImageGrid = new MulLineEnter( "fm" , "ImageGrid" );  
      //��Щ���Ա�����loadMulLineǰ
      ImageGrid.mulLineCount = 5;   
      ImageGrid.displayTitle = 1;
      ImageGrid.locked = 1; 
      ImageGrid.canSel = 1;
      ImageGrid.hiddenPlus = 1;
      ImageGrid.hiddenSubtraction = 1;
      ImageGrid.loadMulLine(iArray);       
      ImageGrid.selBoxEventFuncName = "showImage";  
      //��Щ����������loadMulLine����
      //ImageGrid.setRowColData(1,1,"asdf");  
      } 
      catch(ex)
      {
        alert(ex);
      }
} 

</script>

  
