package com.maxiaofa.hosts.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author MaXiaoFa
 */
public class DnsUtils {

    private static final String DNS_QUERY_URL = "https://cloudflare-dns.com/dns-query?name=";
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(REQUEST_TIMEOUT)
            .build();
    private static final Pattern JSON_OBJECT = Pattern.compile("\\{[^{}]*}", Pattern.DOTALL);
    private static final Pattern A_RECORD_TYPE = Pattern.compile("\"type\"\\s*:\\s*1\\b");
    private static final Pattern DATA_FIELD = Pattern.compile("\"data\"\\s*:\\s*\"([^\"]+)\"");
    private static final Pattern IPV4_ADDRESS = Pattern.compile("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}");

    public static Optional<String> resolveHostLine(String host) {
        return resolveIpv4(host).map(ip -> String.format("%s %s\n", ip, host));
    }

    public static Optional<String> resolveIpv4(String host) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DNS_QUERY_URL + URLEncoder.encode(host, StandardCharsets.UTF_8)))
                .timeout(REQUEST_TIMEOUT)
                .header("Accept", "application/dns-json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() != 200) {
                return Optional.empty();
            }
            return parseIpv4Answer(response.body());
        } catch (IOException e) {
            return Optional.empty();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    private static Optional<String> parseIpv4Answer(String json) {
        int answerIndex = json.indexOf("\"Answer\"");
        if (answerIndex < 0) {
            return Optional.empty();
        }

        Matcher matcher = JSON_OBJECT.matcher(json.substring(answerIndex));
        while (matcher.find()) {
            String object = matcher.group();
            if (!A_RECORD_TYPE.matcher(object).find()) {
                continue;
            }

            Matcher dataMatcher = DATA_FIELD.matcher(object);
            if (dataMatcher.find() && IPV4_ADDRESS.matcher(dataMatcher.group(1)).matches()) {
                return Optional.of(dataMatcher.group(1));
            }
        }
        return Optional.empty();
    }
}
