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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  
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
    document.all('ContNo').value = ContNo; 
    document.all('subtype').value = '';
    document.all('subtypname').value = '';
} 

function initForm() {
  try {

	initAll();
	initImageGrid();  
	QueryImage();
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
      iArray[0][0]="���";         		  	//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px";            		//�п�
      iArray[0][2]=30;            		  	//�����ֵ
      iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֤��";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			  //�����ֵ
      iArray[1][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

  
      iArray[2]=new Array();
      iArray[2][0]="Ӱ�����";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=200;             		 	//�����ֵ
      iArray[2][3]=0;              		  	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ӱ������";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=200;            			  //�����ֵ
      iArray[3][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��֤���";         		//����
      iArray[4][1]="0px";            		  //�п�
      iArray[4][2]=200;            			  //�����ֵ
      iArray[4][3]=3;              			  //�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="ɨ�����κ�";         	//����
      iArray[5][1]="100px";            		  //�п�
      iArray[5][2]=200;            			  //�����ֵ
      iArray[5][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="ҳ��";         	//����
      iArray[6][1]="80px";            		  //�п�
      iArray[6][2]=200;            			  //�����ֵ
      iArray[6][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="ɨ������";         	//����
      iArray[7][1]="60px";            		  //�п�
      iArray[7][2]=200;            			  //�����ֵ
      iArray[7][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="ɨ��ʱ��";         	//����
      iArray[8][1]="60px";            		  //�п�
      iArray[8][2]=200;            			  //�����ֵ
      iArray[8][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="ɨ��Ա����";         	//����
      iArray[9][1]="80px";            		  //�п�
      iArray[9][2]=200;            			  //�����ֵ
      iArray[9][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
       
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

  
