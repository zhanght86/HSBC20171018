<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWGErrInit.jsp
//�����ܣ�����˱�δ��ԭ���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
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

function initForm(tGrpContNo,tBqFlag)
{
  try
  {
	initUWGErrGrid();
	initUWErrGrid();
	initHide(tGrpContNo,tBqFlag);
	easyQueryClick();
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initUWGErrGrid()
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
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ������";    	//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;                      //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	

      iArray[4]=new Array();
      iArray[4][0]="�˱�˳���";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�˱����";         		//����
      iArray[5][1]="300px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�˱�����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWGErrGrid = new MulLineEnter( "document" , "UWGErrGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      UWGErrGrid.mulLineCount = 5;   
      UWGErrGrid.displayTitle = 1;
      UWGErrGrid.locked = 1;
      UWGErrGrid.loadMulLine(iArray);  
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initUWErrGrid()
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
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=200;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ������";    	//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=200;            			//�����ֵ
      iArray[2][3]=0;                      //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���ֱ�������";    	//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;                      //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�����������";    	//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;                      //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="���ֱ���";         		//����
      iArray[6][1]="120px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
            	

      iArray[7]=new Array();
      iArray[7][0]="�˱�˳���";         			//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�˱����";         		//����
      iArray[8][1]="300px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="�˱�����";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWErrGrid = new MulLineEnter( "document" , "UWErrGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      UWErrGrid.mulLineCount = 5;   
      UWErrGrid.displayTitle = 1;
      UWErrGrid.locked = 1;
      UWErrGrid.loadMulLine(iArray);  
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tGrpContNo,tBqFlag)
{
	document.all('GrpContNo').value=tGrpContNo;
  //alert(document.all('GrpContNo').value);\
  try{
     if(tBqFlag != null && tBqFlag == 1){
        document.all('BqFlag').value=tBqFlag;
        }else{
          return;
        }
     }catch(ex){} 
}

</script>


