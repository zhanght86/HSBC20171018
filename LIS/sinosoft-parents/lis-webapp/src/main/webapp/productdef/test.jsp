<%@include file="../i18n/language.jsp"%>
<%@ page contentType="text/html; charset=GBK" import="java.util.*,java.io.*"%> 


<% 

try{ 

String title="jsp���ɾ�̬jsp"+"�ļ�"+""; 

String content=""+"С�������㲻���㣿"+""; 

String editer="hpsoft"; 

String filePath = ""; 

filePath = request.getRealPath("/")+"template.jsp"; 

//out.print(filePath); 

String templateContent=""; 

FileInputStream fileinputstream = new FileInputStream(filePath);//��ȡģ���ļ� 

int lenght = fileinputstream.available(); 

byte bytes[] = new byte[lenght]; 

fileinputstream.read(bytes); 

fileinputstream.close(); 

templateContent = new String(bytes); 

out.print(templateContent); 

//templateContent=templateContent.replaceAll("###title###",title); 

//templateContent=templateContent.replaceAll("###content###",content); 

//templateContent=templateContent.replaceAll("###author###",editer); 

//�滻��ģ������Ӧ�ķ��� 

out.print(templateContent); 

// ����ʱ����ļ��� 

Calendar calendar = Calendar.getInstance(); 

String fileame = String.valueOf(calendar.getTimeInMillis()) +".jsp"; 

fileame = request.getRealPath("/")+fileame;//���ɵ�html�ļ�����·�� 

FileOutputStream fileoutputstream = new FileOutputStream(fileame);//�����ļ������ 

byte tag_bytes[] = templateContent.getBytes(); 

fileoutputstream.write(tag_bytes); 

fileoutputstream.close(); 

} 

catch(Exception e){ 

out.print(e.toString()); 

} 

%> 

