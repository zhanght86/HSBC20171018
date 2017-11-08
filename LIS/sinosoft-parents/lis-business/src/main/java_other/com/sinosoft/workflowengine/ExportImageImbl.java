package com.sinosoft.workflowengine;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

@SuppressWarnings("serial")
public class ExportImageImbl extends Frame implements BusinessService {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder;
	BufferedImage image;
	Element xmlElement ;
	Document Svgdoc;
	Document doc ;
	Element root ;
    Node Nroot ;
    String temp1;
    String temp2;
	Integer x1;
	Integer y1;
	Integer x2;
	Integer y2;
    String path = "";
    static String path1 = "";
    static String path2 = "";
    static String path3 = "";
    public ExportImageImbl(){
    	//setTitle("绘图实例");
    	//setSize(300,200);
    	//setVisible(true);
    }
	public CErrors getErrors() {
		return null;
	}

	public VData getResult() {
		VData rVData = new VData();
		String o = path.toString();
		int num = o.indexOf("temp");
		System.out.println(num);
		String url = path.substring(num);
		System.out.println(url);
	    rVData.add(url); 
	    System.out.println("+++===="+path);
		return rVData;
	}
	
	/*接受前台信息并转成svg格式DOC*/
	
	public boolean submitData(VData vData, String Operater) {
		//接受前台信息
		boolean blo = true;
		String xx = "";
		String yy = "";
		String x = "";
		String y = "";
		String z = "";
		String zr = "";
		String w = "";    
		String SvgXML  = "<?xml version=\"1.0\" standalone=\"no\"?>" +
				"<svg width=\"500%\" height=\"360%\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">" +
				"</svg>";
	    StringReader srr = new StringReader(SvgXML);
		InputSource iss = new InputSource(srr);	
		URLDecoder ui = new URLDecoder();
		String xml = "";
		try {
			xml = ui.decode((String)vData.get(0), "UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		StringReader sr = new StringReader(xml);
		InputSource is = new InputSource(sr);
		try {
			builder = factory.newDocumentBuilder();
			Svgdoc = builder.parse(iss);
			doc = builder.parse(is);
		} catch (SAXException e) {			
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		
	    root = Svgdoc.getDocumentElement();
	    Nroot = (Node)root;
	    Element arrowElement = Svgdoc.createElement("marker");
	    arrowElement.setAttribute("id","bigArrow");
	    arrowElement.setAttribute("viewBox","0 0 20 20");
	    arrowElement.setAttribute("refX","0");
	    arrowElement.setAttribute("refY","10");
	    arrowElement.setAttribute("markerUnits","strokeWidth");
	    arrowElement.setAttribute("markerWidth","3");
	    arrowElement.setAttribute("markerHeight","10");
	    arrowElement.setAttribute("orient","auto");
		Nroot.appendChild(arrowElement); 
		Node Nrootarr=(Node)arrowElement;
		Element arElement = Svgdoc.createElement("path");
		arElement.setAttribute("d","M 0 0 L 20 10 L 0 20 z");
		arElement.setAttribute("fill","purple");
		arElement.setAttribute("stroke","black");
		Nrootarr.appendChild(arElement); 
	   //遍历XML , node节点 
		 NodeList Nodes = doc.getElementsByTagName("node");
         if(Nodes!=null){
        	 for(int i = 0; i<Nodes.getLength();i++){
        		 Node node = Nodes.item(i);
        		 Element nodee = (Element)node;
        		 if(node.getNodeType()== Node.ELEMENT_NODE){
        			 NodeList a = nodee.getChildNodes();
        			 Node b = a.item(0);
        			 Element bb = (Element)b;
        			 String nodeType = bb.getAttribute("nodeType");
        			 String namea = bb.getAttribute("nodeName");
        			 if(nodeType =="oval"||nodeType.equals("oval")){
        				 Node c = a.item(1);
        				 Element cc = (Element)c;
        				 String width = cc.getAttribute("width").substring(0,cc.getAttribute("width").lastIndexOf("p"));
        				 z = String.valueOf(Integer.parseInt(width)/2);
        				 String xxx = cc.getAttribute("x").substring(0,cc.getAttribute("x").lastIndexOf("p"));
        				 x = String.valueOf(Integer.parseInt(xxx)+Integer.parseInt(z));
        				 String yyy = cc.getAttribute("y").substring(0,cc.getAttribute("y").lastIndexOf("p")); 
        				 y = String.valueOf(Integer.parseInt(yyy)+Integer.parseInt(z));
        				 Integer tx = Integer.parseInt(x)-15;
        				 xmlElement = Svgdoc.createElement("circle");
        				 xmlElement.setAttribute("cx", x);
        				 xmlElement.setAttribute("cy", y);
        				 xmlElement.setAttribute("r", z);
        				 xmlElement.setAttribute("stroke", "black");
        				 xmlElement.setAttribute("stroke-width","2");
        				 xmlElement.setAttribute("fill", "none");
        				 Nroot.appendChild(xmlElement);
        				 Element xmlElement1 = Svgdoc.createElement("text");
        				 Node text = Svgdoc.createTextNode(namea);
        				 xmlElement1.appendChild(text);
        				 xmlElement1.setAttribute("x", String.valueOf(tx));
        				 xmlElement1.setAttribute("y", y);
        			     xmlElement1.setAttribute("fount-size", "55");
        				 Nroot.appendChild(xmlElement1);   
        			 }  
        			 if(nodeType=="RoundRect"||nodeType.equals("RoundRect")){  
        				 String namea1 = "";
        				 if(namea.length()>5){
        					 namea1 = namea.substring(0, 6);
        				 }else if(namea.length()>4){
        				     namea1 = namea.substring(0, 5);
        				 }else{
        					 namea1 = namea.substring(0, 4);
        				 }
        				 Node c = a.item(1);
        				 Element cc = (Element)c;
        				 zr = cc.getAttribute("width").substring(0,cc.getAttribute("width").lastIndexOf("p"));
        				 z = String.valueOf(Integer.parseInt(zr)/4);
        				 w = cc.getAttribute("height").substring(0,cc.getAttribute("height").lastIndexOf("p"));
        				 String xxr = cc.getAttribute("x").substring(0,cc.getAttribute("x").lastIndexOf("p")); 
        				 x = String.valueOf(Integer.parseInt(xxr)+Integer.parseInt(z));
        				 Integer tx = Integer.parseInt(x)-20;
        				 String yyr = String.valueOf(Integer.parseInt(cc.getAttribute("y").substring(0,cc.getAttribute("y").lastIndexOf("p")))-Integer.parseInt(w)/2); 
        				 y = String.valueOf(Integer.parseInt(yyr)+Integer.parseInt(z));
        				 Element xmlElement = Svgdoc.createElement("rect");
        				 xmlElement.setAttribute("x", String.valueOf((Integer.valueOf(x)-Integer.valueOf(zr)/4)));
        				 xmlElement.setAttribute("y", y);
        				 xmlElement.setAttribute("width", zr);
        				 xmlElement.setAttribute("height", w);
        				 xmlElement.setAttribute("stroke-width", String.valueOf(5));
        				 xmlElement.setAttribute("stroke-opacity", String.valueOf(0.9));
        				 xmlElement.setAttribute("style","fill:none;stroke:black;stroke-width:2;" );
        				 //xmlElement.setAttribute("fill-opacity", String.valueOf(1));
        				 Nroot.appendChild(xmlElement);
        				 Element xmlElement3 = Svgdoc.createElement("text");

        				 
        				 Node text1 = Svgdoc.createTextNode(namea1);
        				 xmlElement3.appendChild(text1);
        				 xmlElement3.setAttribute("x", String.valueOf(tx));
        				 xmlElement3.setAttribute("y", String.valueOf(Integer.parseInt(y)+30));
        				 xmlElement3.setAttribute("fount-size", "1000");
        				 
        				
        				 

        				 Nroot.appendChild(xmlElement3);
        			 }
        		 }       
        	 }
        }
         //遍历Line节点
         NodeList Lines = doc.getElementsByTagName("line");
         if(Lines!=null){
        	 for(int i = 0; i<Lines.getLength();i++){
        		 Integer z1 = Integer.parseInt(z);
        		 Integer xf = 0;
        		 Integer xt = 0;
        		 Integer yf = 0;
        		 Integer yt = 0;
        		 Integer wf = 0;
        		 Integer hf = 0;
        		 Integer wt = 0;
        		 Integer ht = 0;
        		 Integer zf = 0;
        		 Integer zt = 0;
        		 Integer xxto = 0;
        		 Integer xxtr = 0;
        		 Integer xxfo =0;
        		 Integer xxfr = 0;
        		 Integer yyfo = 0;
        		 Integer yyfr = 0;
        		 Integer yyto = 0;
        		 Integer yytr = 0;
        		 String xf1 = "";
        		 String xt1 = "";
        		 String yf1 = "";
        		 String yt1 = "";
        		 String widthf = "";
        		 String widtht = "";
        		 String zz = "";
        		 Node line = Lines.item(i);
        		 Element linee = (Element)line;
        		 if(line.getNodeType()== Node.ELEMENT_NODE){
        			 NodeList a = linee.getChildNodes();
        			 Node b = a.item(0);
        			 Element bb = (Element)b;
        			 String from = bb.getAttribute("from"); 
        			 String to = bb.getAttribute("to");
        			 NodeList nodes = doc.getElementsByTagName("node");
        	         if(Nodes!=null){
        	        	 for(int k = 0; k<nodes.getLength();k++){
        	        		 Node node = nodes.item(k);
        	        		 Element nodee = (Element)node;
        	        		 if(node.getNodeType()== Node.ELEMENT_NODE){
        	        			 NodeList al = nodee.getChildNodes();
        	        			 Node bl = al.item(0);
        	        			 Element bbl = (Element)bl;
        	        			 String nodeid = bbl.getAttribute("id");
        	        			 String type = bbl.getAttribute("nodeType");
        	        			 if(nodeid.equals(from)&&type.equals("oval")){
        	        				 Node c = al.item(1);
        	        				 Element cc = (Element)c;
        	        				 xxfo = Integer.parseInt(cc.getAttribute("x").substring(0,cc.getAttribute("x").lastIndexOf("p")));
        	        				// xf = cc.getAttribute("x").substring(0,cc.getAttribute("x").lastIndexOf("p"));       	        				 
        	        				 yyfo = Integer.parseInt(cc.getAttribute("y").substring(0,cc.getAttribute("y").lastIndexOf("p"))); 
        	        				 widthf = cc.getAttribute("width").substring(0,cc.getAttribute("width").lastIndexOf("p"));
        	        				 zf = Integer.parseInt(widthf)/2;
        	        				 xf = xxfo+zf;
        	        				 yf = yyfo +zf;
        	        				 temp1 ="oval";
        	        			 }
        	        			 if(nodeid.equals(from)&&type.equals("RoundRect")){
        	        				 Node c = al.item(1);
        	        				 Element cc = (Element)c;
        	        				 xxfr = Integer.parseInt(cc.getAttribute("x").substring(0,cc.getAttribute("x").lastIndexOf("p"))); 
        	        				 yyfr = Integer.parseInt(cc.getAttribute("y").substring(0,cc.getAttribute("y").lastIndexOf("p"))); 
        	        				 wf = Integer.parseInt(cc.getAttribute("width").substring(0,cc.getAttribute("width").lastIndexOf("p")));
        	        				 hf = Integer.parseInt(cc.getAttribute("height").substring(0,cc.getAttribute("height").lastIndexOf("p")));
        	        				 xf = xxfr+z1;
        	        				 yf = yyfr +z1;
        	        				 temp1 = "Round";
        	        			 }
        	        			 if(nodeid.equals(to)&&type.equals("oval")){
        	        				 Node c = al.item(1);
        	        				 Element cc = (Element)c;
        	        				 xxto = Integer.parseInt(cc.getAttribute("x").substring(0,cc.getAttribute("x").lastIndexOf("p")));
        	        				// xt = Integer.parseInt(cc.getAttribute("x").substring(0,cc.getAttribute("x").lastIndexOf("p"))); 
        	        				 yyto = Integer.parseInt(cc.getAttribute("y").substring(0,cc.getAttribute("y").lastIndexOf("p")));   
        	        				 widtht = cc.getAttribute("width").substring(0,cc.getAttribute("width").lastIndexOf("p"));
        	        				 zt = Integer.parseInt(widtht)/2;
        	        				 xt = xxto+zt;
        	        				 yt = yyto+zt;
        	        				 temp2 = "oval";
        	        			 }
        	        			 if(nodeid.equals(to)&&type.equals("RoundRect")){
        	        				 Node c = al.item(1);
        	        				 Element cc = (Element)c;
        	        				 xxtr = Integer.parseInt(cc.getAttribute("x").substring(0,cc.getAttribute("x").lastIndexOf("p"))); 
        	        				 yytr = Integer.parseInt(cc.getAttribute("y").substring(0,cc.getAttribute("y").lastIndexOf("p")));  
        	        				 wt = Integer.parseInt(cc.getAttribute("width").substring(0,cc.getAttribute("width").lastIndexOf("p")));
        	        				 ht = Integer.parseInt(cc.getAttribute("height").substring(0,cc.getAttribute("height").lastIndexOf("p")));
        	        				 xt = xxtr+z1;
        	        				 yt = yytr+z1;
        	        				 temp2="Round";
        	        			 } 	        		               	        
        	        		   }
        	        	    }
        	        
        	        	 if(temp1.equals("oval")&&temp2.equals("oval")){
        	        		 if(xf.equals(xt)||yf.equals(yt)){
	        	        	 z1 = Integer.parseInt(z);
	        	        	 double lo = Math.hypot(Math.abs(xt-xf), Math.abs(yt-yf));
	        	        	 if(xt>xf){      	        		 
	        	        		 xf1 = String.valueOf((z1/lo*Math.abs(xt-xf))+xf);
	        	        		 xt1 = String.valueOf((lo-z1)/lo*Math.abs(xt-xf)+xf);
	        	        	 }else{
	        	        		 xf1 =  String.valueOf(xf-(z1/lo*Math.abs(xt-xf)));
	        	        		 xt1 =  String.valueOf(xf-(lo-z1)/lo*Math.abs(xt-xf));
	        	        	 }
	                         if(yt>yf){
	                        	 yf1 =  String.valueOf(z1/lo*Math.abs(yt-yf)+yf);
	                        	 yt1 =  String.valueOf((lo-z1)/lo*Math.abs(yt-yf)+yf);
	                         }else{
	                        	 yf1 =  String.valueOf(yf-z1/lo*Math.abs(yt-yf));
	                        	 yt1 =  String.valueOf(yf-(lo-z1)/lo*Math.abs(yt-yf));
	                         }
	                         Element xmlElement1 = Svgdoc.createElement("polyline");			 
    	        			 xmlElement1.setAttribute("points",xf1+","+yf1+" "+xt1+","+yt1);
            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
            				 Nroot.appendChild(xmlElement1); 	 
        	        		 }  	   
        	        		 if(Math.abs(xt-xf)<z1){
        	        			 z1 = Integer.parseInt(z);
        	        			 if(xt>xf){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("id",xf+","+(yf-z1)+" "+xf+","+(yt+z1));
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	     	        					 
        	        				 }else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        				 Element xmlElement1 = Svgdoc.createElement("polyline");			 
    	    	        			 xmlElement1.setAttribute("points",xf+","+(yf+z1)+" "+xf+","+(yt-z1));
    	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
    	            				 arr.appendChild(xmlElement1); 	  
        	        				 }
        	        				}
        	        			 if(xt<xf){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        				 Element xmlElement1 = Svgdoc.createElement("polyline");			 
    	    	        			 xmlElement1.setAttribute("points",xf+","+(yf-z1)+" "+xf+","+(yt+z1));
    	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
    	            				 arr.appendChild(xmlElement1); 	  
        	        				 }
        	        				 else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",xf+","+(yf+z1)+" "+xf+","+(yt-z1));
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	
        	        					 
        	        				 }
        	        			 }
        	        		 }
        	        		 if(Math.abs(yt-yf)<z1){
        	        			 z1 = Integer.parseInt(z);
        	        			 if(xt>xf){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf+z1)+","+yf+" "+(xt-z1)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	     	        					 
        	        				 }else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        				 Element xmlElement1 = Svgdoc.createElement("polyline");			 
    	    	        			 xmlElement1.setAttribute("points",(xf+z1)+","+yf+" "+(xt-z1)+","+yf);
    	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
    	            				 arr.appendChild(xmlElement1); 	  
        	        				 }
        	        				}
        	        			 if(xt<xf){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        				 Element xmlElement1 = Svgdoc.createElement("polyline");			 
    	    	        			 xmlElement1.setAttribute("points",(xf-z1)+","+yf+" "+(xt+z1)+","+yf);
    	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
    	            				 arr.appendChild(xmlElement1); 	  
        	        				 }
        	        				 else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf-z1)+","+yf+" "+(xt+z1)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	
        	        					 
        	        				 }
        	        			 }
        	        		 }
        	        		 if(xf>xt+z1&&Math.abs(yt-yf)>z1){
        	        			 z1 = Integer.parseInt(z);
        	        			 if(yf>yt){
    	        					 Element arrowElement1= Svgdoc.createElement("a");
    	        					 arrowElement1.setAttribute("id","chart");
    	        					 arrowElement1.setAttribute("stroke","black");
    	        					 arrowElement1.setAttribute("stroke-width","2");
    	        					 arrowElement1.setAttribute("fill","none");
    	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
    	        					 Nroot.appendChild(arrowElement1); 
    	        					 Node arr=(Node)arrowElement1;
        	        				 Element xmlElement1 = Svgdoc.createElement("polyline");			 
            	        			 xmlElement1.setAttribute("points",xf + ","+(yf-z1)+" "+xf+","+yt+" "+(xt+z1)+","+yt);
                    				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                    				 arr.appendChild(xmlElement1); 	 
        	        			 }else{
    	        					 Element arrowElement1= Svgdoc.createElement("a");
    	        					 arrowElement1.setAttribute("id","chart");
    	        					 arrowElement1.setAttribute("stroke","black");
    	        					 arrowElement1.setAttribute("stroke-width","2");
    	        					 arrowElement1.setAttribute("fill","none");
    	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
    	        					 Nroot.appendChild(arrowElement1); 
    	        					 Node arr=(Node)arrowElement1;
        	        				 Element xmlElement1 = Svgdoc.createElement("polyline");			 
            	        			 xmlElement1.setAttribute("points",xf + ","+(yf+z1)+" "+xf+","+yt+" "+(xt+z1)+","+yt);
                    				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                    				 arr.appendChild(xmlElement1); 	 
            	        			 
            	        		 }
        	        		 }
        	        		 else if(xt>xf+z1&&Math.abs(yt-yf)>z1){
        	        			 z1 =Integer.parseInt(z);
        	        			 if(yf>yt){
    	        					 Element arrowElement1= Svgdoc.createElement("a");
    	        					 arrowElement1.setAttribute("id","chart");
    	        					 arrowElement1.setAttribute("stroke","black");
    	        					 arrowElement1.setAttribute("stroke-width","2");
    	        					 arrowElement1.setAttribute("fill","none");
    	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
    	        					 Nroot.appendChild(arrowElement1); 
    	        					 Node arr=(Node)arrowElement1;
        	        				 Element xmlElement1 = Svgdoc.createElement("polyline");			 
            	        			 xmlElement1.setAttribute("points",xf + ","+(yf-z1)+" "+xf+","+yt+" "+(xt-z1)+","+yt);
                    				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                    				 arr.appendChild(xmlElement1); 	 
        	        			 }else{
    	        					 Element arrowElement1= Svgdoc.createElement("a");
    	        					 arrowElement1.setAttribute("id","chart");
    	        					 arrowElement1.setAttribute("stroke","black");
    	        					 arrowElement1.setAttribute("stroke-width","2");
    	        					 arrowElement1.setAttribute("fill","none");
    	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
    	        					 Nroot.appendChild(arrowElement1); 
    	        					 Node arr=(Node)arrowElement1;
        	        				 Element xmlElement1 = Svgdoc.createElement("polyline");			 
            	        			 xmlElement1.setAttribute("points",xf + ","+(yf+z1)+" "+xf+","+yt+" "+(xt-z1)+","+yt);
                    				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                    				 arr.appendChild(xmlElement1); 	 
            	        			 
            	        		 }      	        			 
        	        		 }
        	        		 }
        	        	 if(temp1.equals("Round")&&temp2.equals("oval")){
        	        		 if(xf.equals(xt)||yf.equals(yt)){
        	        		       z1 = Integer.parseInt(z);
	        	        	 double lo = Math.hypot(Math.abs(xt-xf), Math.abs(yt-yf));
	        	        	 if(xt>xf){      	        		 
	        	        		 xf1 = String.valueOf((z1/lo*Math.abs(xt-xf))+xf);
	        	        		 xt1 = String.valueOf((lo-z1)/lo*Math.abs(xt-xf)+xf);
	        	        	 }else{
	        	        		 xf1 =  String.valueOf(xf-(z1/lo*Math.abs(xt-xf)));
	        	        		 xt1 =  String.valueOf(xf-(lo-z1)/lo*Math.abs(xt-xf));
	        	        	 }
	                         if(yt>yf){
	                        	 yf1 =  String.valueOf(z1/lo*Math.abs(yt-yf)+yf);
	                        	 yt1 =  String.valueOf((lo-z1)/lo*Math.abs(yt-yf)+yf);
	                         }else{
	                        	 yf1 =  String.valueOf(yf-z1/lo*Math.abs(yt-yf));
	                        	 yt1 =  String.valueOf(yf-(lo-z1)/lo*Math.abs(yt-yf));
	                         }
        	        		 }
        	        		 if(Math.abs(xt-(xf+wf/4))<wf/2){
        	        			 z1 = Integer.parseInt(z);
        	        			 if(xt>xf+wf/4){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf+wf/4)+","+(yf-hf/2)+" "+(xf+wf/4)+","+(yt+z1));
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	     	        					 
        	        				 }else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        				 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	        				 xmlElement1.setAttribute("points",(xf+wf/4)+","+(yf+hf/2)+" "+(xf+wf/4)+","+(yt-z1));
    	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
    	            				 arr.appendChild(xmlElement1); 
        	        				 }
        	        				}
        	        			 if(xt<xf+wf/4){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf+wf/4)+","+(yf-hf/2)+" "+(xf+wf/4)+","+(yt+z1));
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	 
        	        				 }
        	        				 else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf+wf/4)+","+(yf+hf/2)+" "+(xf+wf/4)+","+(yt-z1));
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	 	
        	        					 
        	        				 }
        	        			 }
        	        			 
        	        		 }
        	        		 if(Math.abs(yt-yf)<hf/2){
        	        			 z1 = Integer.parseInt(z);
        	        			 if(xt>xf-wf/4){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf-wf/4+wf)+","+yf+" "+xt+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	     	        					 
        	        				 }else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf-wf/4+wf)+","+yf+" "+(xt-z1)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	
        	        				 }
        	        				}
        	        			 if(xt<xf-wf/4){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf-wf/4)+","+yf+" "+(xt+z1)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	 
        	        				 }
        	        				 else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",xf-wf/4+","+yf+" "+(xt+z1)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	 	 	
        	        					 
        	        				 }
        	        			 }
        	        			 
        	        		 }
        	        		 if(xf+wf/4-wf/2>xt&&Math.abs(yt-yf)>hf/2){
        	        			 z1 =Integer.parseInt(z);
        	        			 if(yf>yt){ 
    	        					 Element arrowElement1= Svgdoc.createElement("a");
    	        					 arrowElement1.setAttribute("id","chart");
    	        					 arrowElement1.setAttribute("stroke","black");
    	        					 arrowElement1.setAttribute("stroke-width","2");
    	        					 arrowElement1.setAttribute("fill","none");
    	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
    	        					 Nroot.appendChild(arrowElement1); 
    	        					 Node arr=(Node)arrowElement1;
        	        			 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	        			 xmlElement1.setAttribute("points",(xf+wf/4) + ","+(yf-hf/2)+" "+(xf+wf/4)+","+yt+" "+(xt+z1)+","+yt);
                				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                				 arr.appendChild(xmlElement1);      	        				 
        	        			 }      	        			 
        	        		 else{
	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
        	        			 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	        			 xmlElement1.setAttribute("points",(xf+wf/2) + ","+(yf+hf/2)+" "+(xf+wf/2)+","+yt+" "+(xt+z1)+","+yt);
                				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                				 arr.appendChild(xmlElement1);    
        	        		     }
        	        	    }
        	        		if(xf+wf/4+wf/2<xt&&Math.abs(yt-yf)>hf/2){
        	        			 z1 =Integer.parseInt(z);
        	        			if(yf>yt){ 
   	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
           	        			 Element xmlElement1 = Svgdoc.createElement("polyline");			 
           	        			 xmlElement1.setAttribute("points",(xf+wf/4) + ","+(yf-hf/2)+" "+(xf+wf/4)+","+yt+" "+(xt-z1)+","+yt);
                   				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                   				 arr.appendChild(xmlElement1);      	        				 
           	        			 }      	        			 
           	        		 else{
	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
           	        			 Element xmlElement1 = Svgdoc.createElement("polyline");			 
           	        			 xmlElement1.setAttribute("points",(xf+wf/4) + ","+(yf+hf/2)+" "+(xf+wf/4)+","+yt+" "+(xt-z1)+","+yt);
                   				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                   				 arr.appendChild(xmlElement1);    
           	        		     }
        	        			 
        	        			 
        	        		 }
        	        	 }
        	        	 if(temp1.equals("oval")&&temp2.equals("Round")){
        	        		 z1 = Integer.parseInt(z);
        	        		 if(Math.abs(xt+wt/4-xf)<wt/2){
        	        			 z1 = Integer.parseInt(z);
        	        			 if((xt-wt/4)>xf){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",xf+","+(yf-z1)+" "+xf+","+yt);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	     	        					 
        	        				 }else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",xf+","+(yf+z1)+" "+xf+","+yt);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1);
        	        				 }
        	        				}
        	        			 if((xt-wt/4)<xf){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",xf+","+(yf-z1)+" "+xf+","+(yt+ht/2));
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	 
        	        				 }
        	        				 else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",xf+","+(yf+z1)+" "+xf+","+(yt-ht/2));
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	 	
        	        					 
        	        				 }
        	        			 }
        	        			 
        	        		 }
        	        		 if(Math.abs(yt-yf)<ht/2){
        	        			 z1 = Integer.parseInt(z);
        	        			 if((xt-wt/4)>xf){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf+z1)+","+yf+" "+(xt-wt/4)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	     	        					 
        	        				 }else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf+z1)+","+yf+" "+(xt-wt/4)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	
        	        				 }
        	        				}
        	        			 if((xt-wt/4)<xf){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf-z1)+","+yf+" "+((xt-wt/4)+wt)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1);  	 
        	        				 }
        	        				 else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf-z1)+","+yf+" "+(xt-wt/4+wt)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	 	 	
        	        					 
        	        				 }
        	        			 }
        	        			 
        	        		 }
        	        		 if(xf>xt+wt/4+wt/2&&Math.abs(yt-yf)>ht/2){
        	        			 if(yf>yt){ 
    	        					 Element arrowElement1= Svgdoc.createElement("a");
    	        					 arrowElement1.setAttribute("id","chart");
    	        					 arrowElement1.setAttribute("stroke","black");
    	        					 arrowElement1.setAttribute("stroke-width","2");
    	        					 arrowElement1.setAttribute("fill","none");
    	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
    	        					 Nroot.appendChild(arrowElement1); 
    	        					 Node arr=(Node)arrowElement1;
        	        			 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	        			 xmlElement1.setAttribute("points",xf + ","+(yf-z1)+" "+xf+","+yt+" "+((xt-wt/4)+wt)+","+yt);
                				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                				 arr.appendChild(xmlElement1);      	        				 
        	        			 }      	        			 
        	        		 else{
	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
        	        			 z1 = Integer.parseInt(z);
        	        			 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	        			 xmlElement1.setAttribute("points",xf + ","+(yf+z1)+" "+xf+","+yt+" "+((xt-wt/4)+wt)+","+yt);
                				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                				 arr.appendChild(xmlElement1);    
        	        		     }
        	        	    }
        	        		else if(xf<xt-wt/4-wt/2&&Math.abs(yt-yf)>ht/2){
        	        			if(yf>yt){ 
   	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
           	        			 Element xmlElement1 = Svgdoc.createElement("polyline");			 
           	        			 xmlElement1.setAttribute("points",xf + ","+(yf-z1)+" "+xf+","+yt+" "+(xt-wt/4)+","+yt);
                   				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                   				 arr.appendChild(xmlElement1);      	        				 
           	        			 }      	        			 
           	        		 else{
	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
           	        			 Element xmlElement1 = Svgdoc.createElement("polyline");			 
           	        			 xmlElement1.setAttribute("points",xf + ","+(yf+z1)+" "+xf+","+yt+" "+(xt-wt/4)+","+yt);
                   				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                   				 arr.appendChild(xmlElement1);    
           	        		     }
        	        			 
        	        			 
        	        		 }
        	        	 }
        	        	 if(temp1.equals("Round")&&temp2.equals("Round")){
        	        		 if(xf.equals(xt)||yf.equals(yt)){
        	        		       z1 = Integer.parseInt(z);
	        	        	 if(xt>xf){      	
	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
	        	        		 Element xmlElement1 = Svgdoc.createElement("polyline");			 
	    	        			 xmlElement1.setAttribute("points",(xf+wf/4+wf/2)+","+yf+" "+(xt+wf/4-wf/2)+","+yt);
	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
	            				 arr.appendChild(xmlElement1);
	        	        	 }else if(xt<xf){
	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
	        	        		 Element xmlElement1 = Svgdoc.createElement("polyline");			 
	    	        			 xmlElement1.setAttribute("points",(xf+wf/4-wf/2)+","+yf+" "+(xf+wf/4+wf/2)+","+yt);
	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
	            				 arr.appendChild(xmlElement1);
	        	        	 }
	                         if(yt>yf){
	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
	                        	 Element xmlElement1 = Svgdoc.createElement("polyline");			 
	    	        			 xmlElement1.setAttribute("points",(xf+wf/4)+","+(yf+hf/2)+" "+(xf+wf/4)+","+(yt-hf/2));
	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
	            				 arr.appendChild(xmlElement1);
	                         }else if(yt<yf){
	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
	                        	 Element xmlElement1 = Svgdoc.createElement("polyline");			 
	    	        			 xmlElement1.setAttribute("points",(xf+wf/4)+","+(yf-hf/2)+" "+(xf+wf/4)+","+(yt+hf/2));
	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
	            				 arr.appendChild(xmlElement1);
	                         }
        	        		 }
        	        		 if(Math.abs(xt-xf)<wf/2){
        	        			 z1 = Integer.parseInt(z);
        	        			 if(xt>xf){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf+wf/4)+","+(yf-hf/2)+" "+(xf+wf/4)+","+(yt+z1));
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	     	        					 
        	        				 }else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        				 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	        				 xmlElement1.setAttribute("points",(xf+wf/4)+","+(yf+hf/2)+" "+(xf+wf/4)+","+(yt-z1));
    	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
    	            				 arr.appendChild(xmlElement1); 
        	        				 }
        	        				}
        	        			 if(xt<xf){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf+wf/4)+","+(yf-hf/2)+" "+(xf+wf/4)+","+(yt+z1));
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	 
        	        				 }
        	        				 else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf+wf/4)+","+(yf+hf/2)+" "+(xf+wf/4)+","+(yt-z1));
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	 	
        	        					 
        	        				 }
        	        			 }
        	        			 
        	        		 }
        	        		 if(Math.abs(yt-yf)<hf/2){
        	        			 z1 = Integer.parseInt(z);
        	        			 if(xt>xf){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf-wf/4+wf)+","+yf+" "+(xt-wf/4)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	     	        					 
        	        				 }else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf-wf/4+wf)+","+yf+" "+(xt-z1)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	
        	        				 }
        	        				}
        	        			 if(xt<xf){
        	        				 if(yf>yt){
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",(xf-wf/4)+","+yf+" "+(xt+wf/4+wf/2)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	 
        	        				 }
        	        				 else{
        	        					 Element arrowElement1= Svgdoc.createElement("a");
        	        					 arrowElement1.setAttribute("id","chart");
        	        					 arrowElement1.setAttribute("stroke","black");
        	        					 arrowElement1.setAttribute("stroke-width","2");
        	        					 arrowElement1.setAttribute("fill","none");
        	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
        	        					 Nroot.appendChild(arrowElement1); 
        	        					 Node arr=(Node)arrowElement1;
        	        					 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	    	        			 xmlElement1.setAttribute("points",xf-wf/4+","+yf+" "+(xt+wf/4+wf/2)+","+yf);
        	            				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
        	            				 arr.appendChild(xmlElement1); 	 	 	        	        					 
        	        				 }
        	        			 }       	        			 
        	        		 }
        	        		 if(xf>xt&&Math.abs(yt-yf)>hf/2&&Math.abs(xt-xf)>wf/2){
        	        			 z1 =Integer.parseInt(z);
        	        			 if(yf>yt){ 
    	        					 Element arrowElement1= Svgdoc.createElement("a");
    	        					 arrowElement1.setAttribute("id","chart");
    	        					 arrowElement1.setAttribute("stroke","black");
    	        					 arrowElement1.setAttribute("stroke-width","2");
    	        					 arrowElement1.setAttribute("fill","none");
    	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
    	        					 Nroot.appendChild(arrowElement1); 
    	        					 Node arr=(Node)arrowElement1;
        	        			 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	        			 xmlElement1.setAttribute("points",(xf+wf/4) + ","+(yf-hf/2)+" "+(xf+wf/4)+","+yt+" "+(xt+wf/4+wf/2)+","+yt);
                				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                				 arr.appendChild(xmlElement1);      	        				 
        	        			 }      	        			 
        	        		 else{
	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
        	        			 Element xmlElement1 = Svgdoc.createElement("polyline");			 
        	        			 xmlElement1.setAttribute("points",(xf+wf/4) + ","+(yf+hf/2)+" "+(xf+wf/4)+","+yt+" "+(xt+wf/2+wf/4)+","+yt);
                				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                				 arr.appendChild(xmlElement1);    
        	        		     }
        	        	    }
        	        		if(xf<xt&&Math.abs(yt-yf)>hf/2&&Math.abs(xt-xf)>wf/2){
        	        			 z1 =Integer.parseInt(z);
        	        			if(yf>yt){ 
   	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
           	        			 Element xmlElement1 = Svgdoc.createElement("polyline");			 
           	        			 xmlElement1.setAttribute("points",(xf+wf/4) + ","+(yf-hf/2)+" "+(xf+wf/4)+","+yt+" "+(xt-z1)+","+yt);
                   				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                   				 arr.appendChild(xmlElement1);      	        				 
           	        			 }      	        			 
           	        		 else{
	        					 Element arrowElement1= Svgdoc.createElement("a");
	        					 arrowElement1.setAttribute("id","chart");
	        					 arrowElement1.setAttribute("stroke","black");
	        					 arrowElement1.setAttribute("stroke-width","2");
	        					 arrowElement1.setAttribute("fill","none");
	        					 arrowElement1.setAttribute("marker-end","url(#bigArrow)");
	        					 Nroot.appendChild(arrowElement1); 
	        					 Node arr=(Node)arrowElement1;
           	        			 Element xmlElement1 = Svgdoc.createElement("polyline");			 
           	        			 xmlElement1.setAttribute("points",(xf+wf/4) + ","+(yf+hf/2)+" "+(xf+wf/4)+","+yt+" "+(xt-z1)+","+yt);
                   				 xmlElement1.setAttribute("style","fill:none;stroke:black;stroke-width:2");
                   				 arr.appendChild(xmlElement1);    
           	        		     }       	        			 
        	        		   }
        	        	    }
        	        	 }
        	         }
        		 }
            }
        this.saveXML();
        //this.SaveHtml();
        //XMLWriter wr = new XMLWriter(new FileOutputStream("c:liu.xml"));
        path = this.getBasePath();
        blo = this.DrawPicture();
		return blo;
	}
	
	public void saveXML(){
//		 将修改后的文档保存到文件    
        TransformerFactory transFactory = TransformerFactory.newInstance();   
        Transformer transFormer;
		try {
	    transFormer = transFactory.newTransformer();
        DOMSource domSource = new DOMSource(Svgdoc);   
        String name = "SVG";
	    URL url = ExportImageImbl.class.getResource("ExportImageImbl.class");  
	    String paths = url.getPath();  
	    path1 = paths.substring(1, paths.lastIndexOf("WEB-INF"));  
        path1 +="temp/"+name+".xml"; 
        File file = new File(path1);   
        if (file.exists()) {   
            file.delete();   
        }   
		        file.createNewFile();
			    FileOutputStream out;
			    out = new FileOutputStream(file);
		        StreamResult xmlResult = new StreamResult(out);       
				transFormer.transform(domSource, xmlResult);			 
		        System.out.println(file.getAbsolutePath());   
				} catch (FileNotFoundException e){
					e.printStackTrace();
				 } catch (IOException e) {		
					e.printStackTrace();
				}  catch (TransformerException e) {			
					e.printStackTrace();
				}          
    }   
	
	// 获取应用绝对路径。  
    public  String getBasePath() {  
        String name = "SVG";
	    URL url = ExportImageImbl.class.getResource("ExportImageImbl.class");  
	    String paths = url.getPath();  
	    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+paths);
	    path = paths.substring(1, paths.lastIndexOf("WEB-INF"));  
        path +="temp/"+name+".png"; 
	    return path;  
	    }  
	
    //XML文件转换HTML文件   
    public void Transform(String xmlFileName ,String htmlFileName){   
     try {   
    	   TransformerFactory tFac=TransformerFactory.newInstance();         
    	   Source xslSource= new StreamSource("d:\\jcode\\xsl4score.xsl");   
    	   Transformer t=tFac.newTransformer(xslSource);   
    	   File xmlFile=new File(xmlFileName);   
    	   File htmlFile=new File(htmlFileName);        
           Source source=new StreamSource(xmlFile);         
    	   Result result=new StreamResult(htmlFile);   
           t.transform(source, result);   
    	   } catch (TransformerConfigurationException e) {     
    	   e.printStackTrace();   
           } catch (TransformerException e) {    
    	    e.printStackTrace();   
    	   }   
    	 }  
    
    public void SaveHtml(){
        //设置输入源
    	System.out.println(path1);
        Source src = new StreamSource(path1);
        String name = "SVG";
	    URL url = ExportImageImbl.class.getResource("ExportImageImbl.class");  
	    String paths = url.getPath();  
	    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+paths);
	    path2 = paths.substring(1, paths.lastIndexOf("WEB-INF"));  
        path2 +="temp/"+name+".html"; 
        //设置输出结果
        Result rst = new StreamResult(path2);
        //获得转换器工厂对象
        TransformerFactory tFactory = TransformerFactory.newInstance();
        try {
            //获得转换器模板
            Templates ts = tFactory.newTemplates(src);
            //获得转换器
            Transformer tf = ts.newTransformer();
            //设置输出结果编码为gb2312
            tf.setOutputProperty(OutputKeys.ENCODING, "gb2312");
            //设置输出结果类型为html
            tf.setOutputProperty(OutputKeys.METHOD, "html");
            //设置输出结果允许使用空白进行缩进
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            //转换
            tf.transform(src, rst);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
  
	//根据SVG生成JPG保存到Path
	public boolean DrawPicture(){
		String realpath = this.path1;
		FileOutputStream ostream = null;
		TranscoderInput input = null;
		FileInputStream istream = null;
		try {
			/*ostream = new FileOutputStream(realpath);
			TransformerFactory   tf   =   TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			//t.setOutputProperty(/"encoding/",/"GB23121/");//解决中文问题，试过用GBK不行
			ByteArrayOutputStream   bos   =   new   ByteArrayOutputStream();
			t.transform(new DOMSource(Svgdoc), new StreamResult(bos));
			String xmlStr = bos.toString();
	        String name = "SVG";
		    URL url = ExportImageImbl.class.getResource("ExportImageImbl.class");  
		    String paths = url.getPath();  
		    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+paths);
		    path3 = paths.substring(1, paths.lastIndexOf("WEB-INF"));  
	        path3 +="temp/"+name+".html"; */
			PNGTranscoder   transcoder = new PNGTranscoder ();
			istream = new FileInputStream(realpath);
			input = new TranscoderInput(istream);
			ostream = new FileOutputStream(path);
			TranscoderOutput output = new TranscoderOutput(ostream);
			transcoder.transcode(input, output);		
			ostream.flush();
			ostream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExportImageImbl e = new ExportImageImbl();
	}
}
