/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.common;

/**
 *
 * @author tushar
 */
public class Utils {

    public static String getDomainName(String url) {

        if (url == null) {
            return "";
        }
        String domainName = url;

        try {
            if (url.startsWith("https://")) {
                domainName = url.substring(8);
            } else if (url.startsWith("http://")) {
                domainName = url.substring(7);
            }
            if (domainName.startsWith("www.")) {
                domainName = domainName.substring(4);
            }

            int domainLength = domainName.indexOf("/");

            if (domainLength == -1) {
                domainName = domainName.substring(0, domainName.length());
            } else {
                domainName = domainName.substring(0, domainLength);
            }
        } catch (Exception ex) {
            domainName = url;
        }
        return domainName.trim();
    }
}
