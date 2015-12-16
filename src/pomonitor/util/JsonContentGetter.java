package pomonitor.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * ���ڷ���url������󲢻�ȡ�ִ�Json�ַ����
 * @author zhaolong
 * 2015��12��16�� ����4:41:18
 */
public class JsonContentGetter {
	 public static String getJsonContent(String urlStr)
	    {
	        try
	        {// ��ȡHttpURLConnection���Ӷ���
	           System.out.println(urlStr+"~~~~~~~~~~~~");
	        	URL url = new URL(urlStr);
	            HttpURLConnection httpConn = (HttpURLConnection) url
	                    .openConnection();
	            // ������������
	            httpConn.setConnectTimeout(3000);
	            httpConn.setDoInput(true);
	            httpConn.setRequestMethod("GET");
	            // ��ȡ��Ӧ��
	            int respCode = httpConn.getResponseCode();
	            if (respCode == 200)
	            {
	                return ConvertStream2Json(httpConn.getInputStream());
	            }
	        }
	        catch (MalformedURLException e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        catch (IOException e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return "";
	    }

	   
	    private static String ConvertStream2Json(InputStream inputStream)
	    {
	        String jsonStr = "";
	        // ByteArrayOutputStream�൱���ڴ������
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int len = 0;
	        // ��������ת�Ƶ��ڴ��������
	        try
	        {
	            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
	            {
	                out.write(buffer, 0, len);
	            }
	            // ���ڴ���ת��Ϊ�ַ�
	            jsonStr = new String(out.toByteArray(),"utf-8");
	        }
	        catch (IOException e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return jsonStr;
	    }
}
