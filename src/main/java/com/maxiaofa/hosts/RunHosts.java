package com.maxiaofa.hosts;

import com.maxiaofa.hosts.constants.GithubUrl;
import com.maxiaofa.hosts.utils.DnsUtils;
import com.maxiaofa.hosts.utils.FileUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author MaXiaoFa
 */
public class RunHosts {

    private static final Logger log = Logger.getLogger(RunHosts.class.getName());
    private static final int DNS_QUERY_TIMEOUT_SECONDS = 10;

    public static void main(String[] args) throws InterruptedException {
        log.info("正在获取最新 IP Address...");

        File file = new File(System.getProperty("user.dir") + "/hosts");

        LocalDate updateTime = LocalDate.now();
        StringBuilder content = new StringBuilder();

        content.append("#Github Hosts Start\n");
        content.append(String.format("#Update Time: %s\n", updateTime));
        content.append("#Project Address: https://github.com/maxiaof/github-hosts\n");
        content.append("#Update URL: https://raw.githubusercontent.com/maxiaof/github-hosts/master/hosts\n");

        List<Callable<String>> tasks = Arrays.stream(GithubUrl.GITHUB_URL)
                .map(host -> (Callable<String>) () -> DnsUtils.resolveHostLine(host).orElse(null))
                .toList();

        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> futures = executorService.invokeAll(tasks, DNS_QUERY_TIMEOUT_SECONDS, TimeUnit.SECONDS);

            futures.stream()
                    .filter(future -> !future.isCancelled())
                    .map(RunHosts::getResult)
                    .filter(Objects::nonNull)
                    .forEach(content::append);
        }

        content.append("#Github Hosts End\n");
        FileUtils.write(file, content.toString());

        log.info("正在更新 README 文件...");
        updateReadme(updateTime, content.toString());

        log.info("操作完成, 正在关闭...");
    }

    private static String getResult(Future<String> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } catch (ExecutionException e) {
            log.warning(e.getCause().getMessage());
            return null;
        }
    }

    private static void updateReadme(LocalDate data, String hosts) {
        File readmeTemplate = new File(System.getProperty("user.dir") + "/README_TEMPLATE.md");
        String readmeTemplateContent = FileUtils.read(readmeTemplate);

        String updateReadmeContent = readmeTemplateContent.replace("{[update_time]}", data.toString()).replace("{[hosts]}", hosts);

        File readme = new File(System.getProperty("user.dir") + "/README.md");
        FileUtils.write(readme, updateReadmeContent);
    }
}
