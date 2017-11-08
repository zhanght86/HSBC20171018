package com.tencent.qcloud.file;

import java.io.FileInputStream;
import java.security.MessageDigest;

public class FileProcess {
	public static boolean isLegalFile(String filePath) {
        java.io.File file = new java.io.File(filePath);
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return false;
        }
        return true;
    }
	
	/**
	 * 获取文件长度，单位为字节
	 * @param filePath 文件的本地路径
	 * @return 文件长度,单位为字节
	 * @throws Exception 文件不存在或者是一个目录，则抛出异常
	 */
	public static long getFileLength(String filePath) throws Exception {
        if (!isLegalFile(filePath)) {
            String errorMsg = filePath + " is not file or not exist or can't be read!";
           throw new Exception(errorMsg);
        }
        java.io.File file = new java.io.File(filePath);
        return file.length();
    }
	
	/**
	 * 打开对应的文件，并返回文件输入流
	 * @param filePath 文件路径
	 * @return 文件输入流
	 * @throws Exception 如果文件不存在，则抛出异常
	 */
    public static java.io.FileInputStream getFileInputStream(String filePath) throws Exception {
        if (!isLegalFile(filePath)) {
            String errorMsg = filePath + " is not file or not exist or can't be read!";
            throw new Exception(errorMsg);
        }
        java.io.FileInputStream localFileInputStream = new java.io.FileInputStream(filePath);
        return localFileInputStream;
    }
    
    /**
     * 关闭对应的文件流
     * @param inputStream 待关闭的文件流
     * @param filePath 对应的文件名
     * @throws java.io.IOException 关闭时发生IO异常，则抛出
     */
    public static void closeFileStream(java.io.InputStream inputStream, String filePath)
            throws java.io.IOException {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (java.io.IOException e) {
            throw e;
        }
    }
    
    /**
     * 读取指定流从某处开始的内容，此函数有一定的风险，如果流对应的内容过大，则会造成OOM
     * @param inputStream
     * @param offset 读取的开始偏移
     * @param length 读取的长度
     * @return 读取的内容
     * @throws Exception
     */
    public static byte[] getFileContent(java.io.InputStream inputStream, long offset, int length)
            throws Exception {
        if (offset < 0 || length < 0) {
            throw new Exception("get file content param error");
        }

        byte[] fileContent = null;
        byte[] tempBuf = new byte[length];

        inputStream.skip(offset);
        int readLen = inputStream.read(tempBuf);
        if (readLen < 0) {
            fileContent = new byte[0];
            return fileContent;
        }
        if (readLen < length) {
            fileContent = new byte[readLen];
            System.arraycopy(tempBuf, 0, fileContent, 0, readLen);
        } else {
            fileContent = tempBuf;
        }
        return fileContent;
    }
    
    /**
     * 读取指定流的内容，此函数有一定的风险，如果流对应的内容过大，则会造成OOM
     * @param inputStream inputStream
     * @return 读取的内容
     * @throws Exception
     */
    public static byte[] getFileContent(java.io.InputStream inputStream) throws Exception {
        return getFileContent(inputStream, 0, inputStream.available());
    }
    
    /**
     * 获取文件的内容
     * @param filePath 文件路径
     * @param offset 偏移量，即从哪里开始读取，单位为字节
     * @param length 读取的长度,单位为字节
     * @return 返回读取的内容，实际读取的长度小于等于length
     * @throws Exception
     */
    public static byte[] getFileContent(String filePath, long offset, int length) throws Exception {
    	java.io.FileInputStream fileInputStream = null;
        try {
            fileInputStream = getFileInputStream(filePath);
            return getFileContent(fileInputStream, offset, length);
        } finally {
            closeFileStream(fileInputStream, filePath);
        }
    }

	public static String getFileSha1(String localPath) {
		// TODO Auto-generated method stub
		MessageDigest sha1 = null;
		FileInputStream fis = null;
		String fileHash = null;
		try{
		    sha1 = MessageDigest.getInstance("SHA1");
            fis = new FileInputStream(localPath);
              
            if(sha1 == null){
            	if(fis != null)
            	{
            		try{
            			fis.close();
            		}catch(Exception e1){
            			
            		}
            	}
            	return null;
            }
            byte[] data = new byte[1048576];
            int read = 0; 
            while ((read = fis.read(data)) != -1) {
                sha1.update(data, 0, read);
            };
            byte[] hashBytes = sha1.digest();
  
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < hashBytes.length; i++) {
              sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
         
            fileHash = sb.toString();
            try{
                fis.close();
            }catch(Exception efc){
            	
            }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
        return fileHash;
	}
}