package com.sina.sinaluncher.network;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.sina.sinaluncher.utils.StorageUtils;
import com.sina.sinaluncher.utils.Utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by sinash94857 on 2016/1/13.
 * 轻量级http访问，内核是HttpURLConnection
 */
public class ServerSimpleRequest {

    private static final String TAG = "ServerSimpleRequest";
    /**
     * post获取数据
     *
     * @param uri
     * @param params
     * @return
     * @throws Exception
     */
    public static String post(final String uri, final Map<String, String> params)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        if (!params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey())
                        .append('=')
                        .append(URLEncoder.encode(entry.getValue() == null ? ""
                                : entry.getValue(), "utf-8")).append('&');

            }
            sb.deleteCharAt(sb.length() - 1);
        }

        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(
                    conn.getOutputStream(), "utf-8");
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            Log.d(TAG, " response code : " + conn.getResponseCode());

            if (conn.getResponseCode() == 200) {
                byte[] data;
                data = readInputStream(conn.getInputStream());
                String json = new String(data);
                Log.d(TAG, url.toString() + "  " + sb.toString());
                return json;

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return "";
    }

    public static String post(final String uri,final Map<String, String> headers, final Map<String, String> params)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        if (!params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey())
                        .append('=')
                        .append(URLEncoder.encode(entry.getValue() == null ? ""
                                : entry.getValue(), "utf-8")).append('&');

            }
            sb.deleteCharAt(sb.length() - 1);
        }

        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            for (String head : headers.keySet()){
                conn.addRequestProperty(head,headers.get(head));
            }
            OutputStreamWriter writer = new OutputStreamWriter(
                    conn.getOutputStream(), "utf-8");
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            Log.d(TAG, " response code : " + conn.getResponseCode());

            if (conn.getResponseCode() == 200) {
                byte[] data;
                data = readInputStream(conn.getInputStream());
                String json = new String(data);
                Log.d(TAG, url.toString() + "  " + sb.toString());
                return json;

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return "";
    }


    public static String get(String theUrl,final Map<String, String> headers) throws IOException {
        URL url;
        Log.d(TAG, "url --> "+theUrl);
        HttpURLConnection urlConnection = null;
        byte[] bs = new byte[500];
        try {
            url = new URL(theUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            for (String head : headers.keySet()){
                urlConnection.addRequestProperty(head,headers.get(head));
            }
            urlConnection.setConnectTimeout(4000);
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
            int len = -1;
            StringBuffer buf = new StringBuffer();
            while ((len = in.read(bs, 0, bs.length)) != -1) {
                buf.append(new String(bs, 0, len, "utf8"));
            }
            return buf.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            bs = null;
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }

    public static String get(String theUrl) throws IOException {
        URL url;
        Log.d(TAG, "url --> "+theUrl);
        HttpURLConnection urlConnection = null;
        byte[] bs = new byte[500];
        try {
            url = new URL(theUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(4000);
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
            int len = -1;
            StringBuffer buf = new StringBuffer();
            while ((len = in.read(bs, 0, bs.length)) != -1) {
                buf.append(new String(bs, 0, len, "utf8"));
            }
            return buf.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            bs = null;
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }

    public static Drawable getDrawable(Context context,String theUrl) throws IOException {
        String cacheDir = StorageUtils.getCacheDir(context);
        int begin = theUrl.lastIndexOf('/');
        String name = theUrl.substring(begin);
        File picFile =  new File(cacheDir,name);
        String newPath = picFile.getAbsolutePath();
        if(picFile.exists()){
            return BitmapDrawable.createFromPath(newPath);
        }

        URL url;
        Log.d(TAG, "url --> "+theUrl);
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(theUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(4000);
            InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream());
            int statusCode = urlConnection.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                byte[] bs = new byte[1024];
                int len = -1;
                // 输出的文件流
                OutputStream os = new FileOutputStream(newPath);
                // 开始读取
                while ((len = in.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
                os.close();
            }
            in.close();
            return BitmapDrawable.createFromPath(newPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {

            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }

    public static String readStringFromStream(InputStream inStream)
            throws Exception {
        StringBuffer strBuf = new StringBuffer();
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inStream.read(buffer)) != -1) {
                strBuf.append(new String(buffer, 0, len, "utf8"));
            }
        } finally {
            buffer = null;
            if (inStream != null)
                inStream.close();
        }
        return strBuf.toString();
    }

    /**
     * 从输入流中获取数
     *
     * @param inStream
     *            输入
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
