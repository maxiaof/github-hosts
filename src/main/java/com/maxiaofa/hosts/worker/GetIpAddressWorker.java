package com.maxiaofa.hosts.worker;

import com.jd.platform.async.callback.IWorker;
import com.jd.platform.async.wrapper.WorkerWrapper;
import com.maxiaofa.hosts.constants.IpAddress;
import com.maxiaofa.hosts.utils.HtmlUtils;

import java.util.Map;

/**
 * @author MaXiaoFa
 */
public class GetIpAddressWorker implements IWorker<String, String> {

    @Override
    public String action(String url, Map<String, WorkerWrapper> map) {
        String htmlResource = HtmlUtils.getUrlHtml(IpAddress.SELECT_IP_ADDRESS_URL+url);
        return String.format("%s %s\n",HtmlUtils.parseHtmlGetIpAddress(htmlResource),url);
    }

    @Override
    public String defaultValue() {
        return null;
    }
}
