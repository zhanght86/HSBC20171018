<%
//�������ƣ�SpecialQueryInit.jsp
//�����ܣ���Լ��ѯ
//�������ڣ�2005-09-29 11:10:36
//������  ��wuhao2
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
                       

 <SCRIPT src="../common/javascript/Common.js"></SCRIPT>

 <script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('ContNo').value= nullToEmpty("<%= tContNo %>");	
}	

//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}


function initForm()
{
  try
  {
	
		initSpecGrid();
		initParam();
		LLSpecGrid();
	
  }
  catch(re)
  {
    alert("SpecialQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


// ��Լ��Ϣ�б�ĳ�ʼ��
function initSpecGrid()
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
      iArray[1][0]="����";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="������";         		//����
      iArray[2][1]="30px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������         
      
      iArray[5]=new Array();
      iArray[5][0]="��Լ����";         		//����
      iArray[5][1]="220px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SpecGrid.mulLineCount = 0;   
      SpecGrid.displayTitle = 1;
      SpecGrid.locked = 1;
      SpecGrid.canSel = 0;
      SpecGrid.hiddenPlus=1;
      SpecGrid.hiddenSubtraction=1;
      SpecGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>   
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
                 
