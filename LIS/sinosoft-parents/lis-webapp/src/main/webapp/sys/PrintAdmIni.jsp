<!--
*******************************************************
* 程序名称：ProposalPrtIni.jsp
* 程序功能：个人单续期收费计划界面初始化页
* 创建日期：2002-07-17
* 更新记录：  更新人    更新日期     更新原因/内容
*             彭谦	  2002-07-17    新建
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
//如果当ip1或ip2为空时:则fm.Ipinput1.value= 导致页面初始化错误
fm.Ipinput1.value="<%=ip1%>";
fm.Ipinput2.value="<%=ip2%>";


 var iArray = new Array(); 
iArray[0]=new Array();
iArray[0][0]="序号";         //列名
iArray[0][1]="50px";            //列宽
iArray[0][2]=100;            //列最大值
iArray[0][3]=0;              //是否允许输入,1表示允许，0表示不允许

iArray[1]=new Array();
iArray[1][0]="IP范围";         //列名
iArray[1][1]="250px";            //列宽
iArray[1][2]=100;            //列最大值
iArray[1][3]=0;              //是否允许输入,1表示允许，0表示不允许

iArray[2]=new Array();
iArray[2][0]="模板类型";         //列名
iArray[2][1]="150px";            //列宽
iArray[2][2]=100;            //列最大值
iArray[2][3]=0;              //是否允许输入,1表示允许，0表示不允许

iArray[3]=new Array();
iArray[3][0]="打印服务器";         //列名
iArray[3][1]="150px";            //列宽
iArray[3][2]=100;            //列最大值
iArray[3][3]=0;   

iArray[4]=new Array();
iArray[4][0]="打印机名";         //列名
iArray[4][1]="150px";            //列宽
iArray[4][2]=100;            //列最大值
iArray[4][3]=0;    

   Iniinfo = new MulLineEnter( "fm" , "Iniinfo" ); 
      //这些属性必须在loadMulLine前

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
    alert("在ProposalPrtIni.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
  
}



function del()
{
	
  	
  var i = 0;
    
   
/*    if (Iniinfo.getSelNo() == 0)
    {
      alert("请选中一条配置信息进行删除!");
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
  	alert("在ProposalInpInfo.js-->resetForm函数中发生异常:初始化界面错误!");
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



