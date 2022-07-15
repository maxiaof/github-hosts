package com.maxiaofa.hosts.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @author MaXiaoFa
 */
public class HtmlUtils {

    public static String parseHtmlGetIpAddress(String html){
        Document parseHtml = Jsoup.parse(html);

        Element ipAddress = parseHtml.getElementById("ip_address");
        return ipAddress!=null ? ipAddress.html() : null;
    }

    public static String getUrlHtml(String url) {
        StringBuilder buffer=new StringBuilder();
        InputStreamReader isr=null;
        try {
            URL urlObj = new URL(url);

            URLConnection uc = urlObj.openConnection();

            isr =new InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader reader =new BufferedReader(isr);

            String line;
            while ((line=reader.readLine())!=null) {
                buffer.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try{
                if(null!=isr)isr.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }
}
