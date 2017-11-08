package com.sinosoft.lis.easyscan;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.bind.DatatypeConverter;

public class ImageToBase64Converter {
	private HashMap<String, byte[]> imageFileMagicNumbers = new HashMap<String, byte[]>();
	private void initializeImageFileMagicNumbers(){
		imageFileMagicNumbers.put("image/bmp", new byte[]{0x42, 0x4d});
    	imageFileMagicNumbers.put("image/x-bmp", new byte[]{0x42, 0x4d});
    	imageFileMagicNumbers.put("image/gif", new byte[]{0x47, 0x49, 0x46});
    	imageFileMagicNumbers.put("image/jpeg", new byte[]{-1, -40});
    	imageFileMagicNumbers.put("image/png", new byte[]{-119, 0x50, 0x4e, 0x47});
    	imageFileMagicNumbers.put("image/tiff", new byte[]{0x49, 0x49, 0x2a});
	}
	
	public ImageToBase64Converter(){
		initializeImageFileMagicNumbers();
	}
	private String getImageMimeType(byte[] data){
		if(data == null)
			return "";
		String result = "image/*";
		for(Entry<String, byte[]> e : imageFileMagicNumbers.entrySet()){
			byte[] target = e.getValue();
			if(data.length < target.length)
			{
				continue;
			}
			boolean isTarget = true;
			for(int i = 0; i < target.length; i++){
				if(target[i] != data[i]){
					isTarget = false;
					break;
				}
			}
			if(isTarget){
				result = e.getKey();
				return result;
			}
		}
		return result;
	}
	private String getBase64String(byte[] input){
    	String result = new String(DatatypeConverter.printBase64Binary(input));
    	return result;
	}
	
	public String getImageBase64String(java.io.InputStream is){
		if(is == null)
			return null;
		String mimeType = "image/png";
		String result = null;
		java.io.ByteArrayOutputStream baos = null;
		StringBuilder sb = null;
		try{
		    baos = new java.io.ByteArrayOutputStream();
		    byte[] buffer = new byte[1048576];
		    int readLength = 0;
		    while((readLength = is.read(buffer)) > 0){
			    baos.write(buffer, 0, readLength);
		    }
		    byte[] data = baos.toByteArray();
		    mimeType = getImageMimeType(data);
		    sb = new StringBuilder();
		    sb.append("data:");
		    sb.append(mimeType.trim());
		    sb.append(";base64,");
		    sb.append(getBase64String(data));
		    result = sb.toString();
		}catch(Exception e){
			
		}finally{
			if(is != null){
				try{is.close();}catch(Exception e){}				
			}
			if(baos != null){
				try{baos.close();}catch(Exception e){}
			}			
		}
		return result;
	}
}
