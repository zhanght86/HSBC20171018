<%@include file="../i18n/language.jsp"%>
<%@ page contentType="text/html; charset=GBK" import="java.util.*,java.io.*"%> 


<% 

try{ 

String title="jsp生成静态jsp"+"文件"+""; 

String content=""+"小样，还搞不定你？"+""; 

String editer="hpsoft"; 

String filePath = ""; 

filePath = request.getRealPath("/")+"template.jsp"; 

//out.print(filePath); 

String templateContent=""; 

FileInputStream fileinputstream = new FileInputStream(filePath);//读取模块文件 

int lenght = fileinputstream.available(); 

byte bytes[] = new byte[lenght]; 

fileinputstream.read(bytes); 

fileinputstream.close(); 

templateContent = new String(bytes); 

out.print(templateContent); 

//templateContent=templateContent.replaceAll("###title###",title); 

//templateContent=templateContent.replaceAll("###content###",content); 

//templateContent=templateContent.replaceAll("###author###",editer); 

//替换掉模块中相应的方法 

out.print(templateContent); 

// 根据时间得文件名 

Calendar calendar = Calendar.getInstance(); 

String fileame = String.valueOf(calendar.getTimeInMillis()) +".jsp"; 

fileame = request.getRealPath("/")+fileame;//生成的html文件保存路径 

FileOutputStream fileoutputstream = new FileOutputStream(fileame);//建立文件输出流 

byte tag_bytes[] = templateContent.getBytes(); 

fileoutputstream.write(tag_bytes); 

fileoutputstream.close(); 

} 

catch(Exception e){ 

out.print(e.toString()); 

} 

%> 

