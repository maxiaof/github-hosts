package com.maxiaofa.hosts.worker;

import com.maxiaofa.hosts.async.callback.IWorker;
import com.maxiaofa.hosts.async.wrapper.WorkerWrapper;
import com.maxiaofa.hosts.constants.IpAddress;
import com.maxiaofa.hosts.utils.HtmlUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author MaXiaoFa
 */
public class GetIpAddressWorker implements IWorker<String, String> {

    @Override
    public String action(String url, Map<String, WorkerWrapper> map) {
        String htmlResource = HtmlUtils.getUrlHtml(IpAddress.SELECT_IP_ADDRESS_URL+url);
        String format = String.format("IP address of %s is ", url);
        return String.format("%s %s\n", Objects.requireNonNull(HtmlUtils.parseHtmlGetIpAddress(htmlResource)).replace(format,""),url);
    }

    @Override
    public String defaultValue() {
        return null;
    }
}
