<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*" %>
<%@page import="com.sinosoft.lis.vschema.*" %>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.VData"%>

<%
    /**
     * 从前台获取一些业务字段
     */
    String OperFlag = request.getParameter("OperFlag");
    boolean temp  =true;
    //修改
    if ("RE||Condation".equalsIgnoreCase(OperFlag))
    {   String ProcessID = request.getParameter("ProcessID");
        String Busitype1 = request.getParameter("Busitype");
        String Version = request.getParameter("Version");
        String Busitype = URLDecoder.decode(Busitype1, "UTF-8");
        LWCorrespondingSchema tLWCorrespondingSchema = new LWCorrespondingSchema();
        tLWCorrespondingSchema.setbusitype(Busitype);
        LWCorrespondingDB tLWCorrespondingDB = tLWCorrespondingSchema.getDB();
        boolean temp1 = tLWCorrespondingDB.getInfo();
        if(temp1){
            LWCorrespondingSchema tLWCorrespondingSchema1 = new LWCorrespondingSchema();
            tLWCorrespondingSchema1.setbusitype(Busitype);
            tLWCorrespondingSchema1.setProcessID(ProcessID);
            tLWCorrespondingSchema1.setVersion(Version);
           // LWCorrespondingDB tLWCorrespondingDB1 = tLWCorrespondingSchema1.getDB();
            
            LWProcessXMLSet tLWProcessXMLSet = new LWProcessXMLSet();
            LWProcessXMLDB tLWProcessXMLDB = new LWProcessXMLDB();
            String tSQL = "update lwprocessxml set ValuedFlag='0' where processid='"+ProcessID+"' and version !="+Version+" ";
            String tSQL2 = "update lwprocessxml set ValuedFlag='0' where busitype='"+Busitype+"' and processid!='"+ProcessID+"'";
            String tSQL1 = "update lwprocessxml set ValuedFlag='1' where processid='"+ProcessID+"' and version ="+Version+" ";

            //tLWProcessXMLSet = tLWProcessXMLDB.executeQuery("select * from lwprocessxml where processid='"+ProcessID+"' and busitype='"+Busitype+"' and version !="+Version+"");
            MMap map = new MMap();
            map.put(tLWCorrespondingSchema1, "UPDATE");
            map.put(tSQL,"UPDATE"); 
            map.put(tSQL2,"UPDATE"); 
            map.put(tSQL1,"UPDATE"); 
           
            PubSubmit tSubmit = new PubSubmit();
            VData tVData = new VData();
            tVData.add(map);
            if(!tSubmit.submitData(tVData,""))
            {
            	temp = false;
            }
            else
            {
            	temp = true;
            }
           // temp = tLWCorrespondingDB1.update();
        }else{
        	 LWCorrespondingSchema tLWCorrespondingSchema2 = new LWCorrespondingSchema();
             tLWCorrespondingSchema2.setbusitype(Busitype);
             tLWCorrespondingSchema2.setProcessID(ProcessID);
             tLWCorrespondingSchema2.setVersion(Version);
             LWCorrespondingDB tLWCorrespondingDB2 = tLWCorrespondingSchema2.getDB();
             
             MMap map = new MMap();
             map.put(tLWCorrespondingSchema2, "INSERT");
             String tSQL2 = "update lwprocessxml set ValuedFlag='0' where busitype='"+Busitype+"' and processid!='"+ProcessID+"'";
             String tSQL1 = "update lwprocessxml set ValuedFlag='1' where processid='"+ProcessID+"' and version ="+Version+" ";
             map.put(tSQL2,"UPDATE"); 
             map.put(tSQL1,"UPDATE"); 
            
             PubSubmit tSubmit = new PubSubmit();
             VData tVData = new VData();
             tVData.add(map);
             if(!tSubmit.submitData(tVData,""))
             {
             	temp = false;
             }
             else
             {
             	temp = true;
             }
             
            // temp = tLWCorrespondingDB2.insert();
        }
        String FlagStr="";      //操作结果
        String Content ="";    //控制台信息
  	   	if(temp==true){	
  	  	Content = " 发布成功! ";
    	FlagStr = "Succ"; 	   	
  	   	}else
  	     {
  	   	Content = " 发布失败! ";
  	   	FlagStr = "Fail";
      }
  	   response.getWriter().println(Content);
    }

  
%>
