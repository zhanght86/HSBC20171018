<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%@page import="java.util.*"%> 
<%@page import="java.io.*"%>
<%@page import="javax.naming.*"%>


<%
String Ip = request.getRemoteAddr();
 String optsel = "";
   
  optsel = request.getParameter("opt");
  String Path= application.getRealPath("sys")+File.separator;
  String FileName = Path+"AppConfig.properties";
  String TargetPath=application.getRealPath("print")+File.separator;
  ConfigInfo.SetConfigPath(FileName);
  
  if (optsel.equals("new"))
  
  {  
    
	Vector Strvector=new Vector();
	Strvector=ConfigInfo.GetIniByIp(Ip);
	int tcount=Strvector.size();
	int intPosition=0;
	String tempStr="";
	String[] IpArry=new String[tcount];
	String[] MbArry=new String[tcount];
	String[] SeverArry=new String[tcount];
	String[] PrintArry=new String[tcount];
	for (int i=0;i<tcount;i++)
	{
  	tempStr=(String)Strvector.get(i); 
  	IpArry[i]=StrTool.decodeStr(tempStr,".",1)+"."+StrTool.decodeStr(tempStr,".",2)+"."+StrTool.decodeStr(tempStr,".",3)+"."+StrTool.decodeStr(tempStr,".",4);
  	int intIndex1=StrTool.getPos(tempStr,".",4);
  	int intIndex=StrTool.getPos(tempStr,"=",1);
  	MbArry[i]=tempStr.substring(intIndex1+2,intIndex-1);
  
  	tempStr=tempStr.substring(intIndex+1);
  	intIndex = StrTool.getPos(tempStr,SysConst.PACKAGESPILTER,intPosition,1 );
  	SeverArry[i]=tempStr.substring(intPosition,intIndex);
  	tempStr=tempStr.substring(intIndex+1);
  	loggerDebug("PrintAdmSave","FFFFFFFFtempStr=="+tempStr);
  	intIndex = StrTool.getPos(tempStr,SysConst.PACKAGESPILTER,intPosition,1 );
  	tempStr=tempStr.substring(intIndex+1);
  	loggerDebug("PrintAdmSave","GGGGGGGG==="+tempStr);
  	PrintArry[i]=tempStr;
	}
    if (tcount!=0){
	if (tcount==1) 
	{
	%>
	 <script language="JavaScript">
	
	  parent.fraInterface.Iniinfo.addOne("Iniinfo",1);
	
	  parent.fraInterface.fm.Iniinfo1.value="<%=IpArry[0]%>";
	  parent.fraInterface.fm.Iniinfo2.value="<%=MbArry[0]%>";
	  parent.fraInterface.fm.Iniinfo3.value="<%=SeverArry[0]%>";
	  parent.fraInterface.fm.Iniinfo4.value="<%=PrintArry[0]%>";
	  parent.fraInterface.Iniinfo.locked = 1; 
	 </script>
	<%
	} //end if
	else
	{     
	 %>  
	     <script language="JavaScript">
	     
	     	parent.fraInterface.Iniinfo.addOne("Iniinfo",<%=tcount%>);
		</script>	
	 <%	
 		for (int i=0;i<tcount;i++ )
 		{
 		
 	%>
 		<script language="JavaScript">
 		
 		
  	 	parent.fraInterface.fm.Iniinfo1[<%=i%>].value="<%=IpArry[i]%>";
  		parent.fraInterface.fm.Iniinfo2[<%=i%>].value="<%=MbArry[i]%>";
  		parent.fraInterface.fm.Iniinfo3[<%=i%>].value="<%=SeverArry[i]%>";
	  	parent.fraInterface.fm.Iniinfo4[<%=i%>].value="<%=PrintArry[i]%>";
	  	parent.fraInterface.Iniinfo.locked = 1; 
	  	</script>
		<% 
   		} //end for 

 	} //end else

	
  } //end tcount!=0 
 } //end new
  
  if (optsel.equals("save"))
  {
   try{
      String tempIp="("+StrTool.decodeStr(Ip,".",1)+").("+ StrTool.decodeStr(Ip,".",2)+")";
      loggerDebug("PrintAdmSave","tempIP=="+tempIp);
      boolean delfile=ConfigInfo.DeleteByStr(tempIp);
      String[] IpArea=request.getParameterValues("Iniinfo1"); //得到多行输入传入的值
      String[] Mb=request.getParameterValues("Iniinfo2");
      String[] Printsvr=request.getParameterValues("Iniinfo3");
      String[] Print=request.getParameterValues("Iniinfo4");
      int tcount=IpArea.length;
      String[] outstr=new String[tcount];
      GlobalInput tG = new GlobalInput();
      tG=(GlobalInput)session.getValue("GI");
      String tCom=tG.ManageCom;
      if (tCom.length()>4)
      {
      	tCom=tCom.substring(0,4);
      }
      
      String dirXml = TargetPath + tCom + File.separator;
      for(int i=0;i<tcount;i++)
       {
         outstr[i]=IpArea[i]+".("+Mb[i]+")="+Printsvr[i]+"|"+dirXml+Printsvr[i]+File.separator+"|"+Print[i];
        
         loggerDebug("PrintAdmSave","outstr=="+outstr[i]);
       }
      loggerDebug("PrintAdmSave","count1=="+tcount);
      BufferedWriter outfile=new BufferedWriter(new FileWriter(FileName,true));
      for(int i=0;i<tcount;i++)
       {
          outfile.write(outstr[i]);
           outfile.write('\n');
        }
      outfile.close();
  %>
    
  
   <script language="JavaScript">
	 alert("保存成功！");
	</script>
  <%
   } //end try
   catch(Exception exception)
   {
   %>
   <script language="JavaScript">
	 alert("保存失败！");
	</script>
   
   
   <%
     // UserLog.printException(exception);
   
   }
 }
  %>
  
