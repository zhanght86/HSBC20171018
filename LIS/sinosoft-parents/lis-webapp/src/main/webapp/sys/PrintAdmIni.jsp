<!--
*******************************************************
* �������ƣ�ProposalPrtIni.jsp
* �����ܣ����˵������շѼƻ������ʼ��ҳ
* �������ڣ�2002-07-17
* ���¼�¼��  ������    ��������     ����ԭ��/����
*             ��ǫ	  2002-07-17    �½�
*******************************************************
-->

<%@page import="com.sinosoft.utility.*"%>

<%@page import="java.util.*"%> 
<%
String Ip = request.getRemoteAddr();
String ip1=StrTool.decodeStr(Ip,".",1);
String ip2=StrTool.decodeStr(Ip,".",2);
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
function initInpBox()
{
try{
//�����ip1��ip2Ϊ��ʱ:��fm.Ipinput1.value= ����ҳ���ʼ������
fm.Ipinput1.value="<%=ip1%>";
fm.Ipinput2.value="<%=ip2%>";


 var iArray = new Array(); 
iArray[0]=new Array();
iArray[0][0]="���";         //����
iArray[0][1]="50px";            //�п�
iArray[0][2]=100;            //�����ֵ
iArray[0][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

iArray[1]=new Array();
iArray[1][0]="IP��Χ";         //����
iArray[1][1]="250px";            //�п�
iArray[1][2]=100;            //�����ֵ
iArray[1][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

iArray[2]=new Array();
iArray[2][0]="ģ������";         //����
iArray[2][1]="150px";            //�п�
iArray[2][2]=100;            //�����ֵ
iArray[2][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

iArray[3]=new Array();
iArray[3][0]="��ӡ������";         //����
iArray[3][1]="150px";            //�п�
iArray[3][2]=100;            //�����ֵ
iArray[3][3]=0;   

iArray[4]=new Array();
iArray[4][0]="��ӡ����";         //����
iArray[4][1]="150px";            //�п�
iArray[4][2]=100;            //�����ֵ
iArray[4][3]=0;    

   Iniinfo = new MulLineEnter( "fm" , "Iniinfo" ); 
      //��Щ���Ա�����loadMulLineǰ

      Iniinfo.mulLineCount = 0;   
      Iniinfo.displayTitle = 1;
      Iniinfo.canChk =1;
      Iniinfo.canSel = 0;
      Iniinfo.loadMulLine(iArray);  
      Iniinfo.locked = 0;

}
catch(ex)
      {
        alert(ex);
      }
fm.opt.value="new";

//parent.fraMain.rows = "0,0,50,82,*";
fm.submit();


}



function initForm()
{
  try
  {
    
    initInpBox();
   // initCheck();
    
  }
  catch(re)
  {
    alert("��ProposalPrtIni.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
  
}



function del()
{
	
  	
  var i = 0;
    
   
/*    if (Iniinfo.getSelNo() == 0)
    {
      alert("��ѡ��һ��������Ϣ����ɾ��!");
    }
   else
   { */
      
     try{
/*         i = Iniinfo.getSelNo();
         
     	 if (Iniinfo.mulLineCount == 1)
         { 
          
           fm.IniinfoDel.onclick();
         }
         else
         {
  	   
  	   fm.IniinfoDel[i-1].onclick();
  	 }*/
  	 	// Update by YaoYi for bug.
  	 	Iniinfo.delCheckTrueLine();
      }
      catch(ex)
      {
        alert(ex);      
      } 
 //   }
}
function resetForm()
{
  try
  {
	initForm();
  }
  catch(re)
  {
  	alert("��ProposalInpInfo.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

function add()
{var i=0;


	Iniinfo.addOne("Iniinfo",1);
	i=Iniinfo.mulLineCount;
	if (i==1)
	{
	fm.Iniinfo1.value="("+fm.Ipinput1.value+").("+fm.Ipinput2.value+").("+fm.Ipinput3.value+").("+fm.Ipinput4.value+")";
	fm.Iniinfo2.value=fm.Templeinput.value;
	fm.Iniinfo3.value=fm.Serverinput.value;
	fm.Iniinfo4.value=fm.Printinput.value;
	 Iniinfo.locked = 1;
	}
	else{
	fm.Iniinfo1[i-1].value="("+fm.Ipinput1.value+").("+fm.Ipinput2.value+").("+fm.Ipinput3.value+").("+fm.Ipinput4.value+")";
	fm.Iniinfo2[i-1].value=fm.Templeinput.value;
	fm.Iniinfo3[i-1].value=fm.Serverinput.value;
	fm.Iniinfo4[i-1].value=fm.Printinput.value;
	 Iniinfo.locked = 1;
	}
}
function saveForm()
{
fm.opt.value="save";
fm.submit();
}

</script>



