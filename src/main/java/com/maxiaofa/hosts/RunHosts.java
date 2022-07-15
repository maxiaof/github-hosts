package com.maxiaofa.hosts;

import com.jd.platform.async.executor.Async;
import com.jd.platform.async.wrapper.WorkerWrapper;
import com.maxiaofa.hosts.config.GenContentTheadPoolExecutorConfig;
import com.maxiaofa.hosts.constants.GithubUrl;
import com.maxiaofa.hosts.utils.FileUtils;
import com.maxiaofa.hosts.worker.GetIpAddressWorker;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author MaXiaoFa
 */
public class RunHosts {

    private static final Logger log = Logger.getLogger(RunHosts.class.getName());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("正在获取最新IP Address...");

        File file = new File(System.getProperty("user.dir") + "/hosts");

        LocalDate updateTime = LocalDate.now();
        StringBuilder content = new StringBuilder();

        content.append("#Github Hosts Start\n");
        content.append(String.format("#Update Time: %s\n", updateTime));
        content.append("#Project Address: https://github.com/maxiaof/github-hosts\n");
        content.append("#Update URL: https://github.com/maxiaof/github-hosts/blob/master/hosts\n");

        List<WorkerWrapper<String,String>> workerWrapperList = new ArrayList<>();

        for (int i = 0; i < GithubUrl.GITHUB_URL.length; i++) {
            GetIpAddressWorker getIpAddressWorker = new GetIpAddressWorker();
            WorkerWrapper<String, String> build = new WorkerWrapper.Builder<String, String>()
                    .worker(getIpAddressWorker)
                    .param(GithubUrl.GITHUB_URL[i])
                    .build();
            workerWrapperList.add(build);
        }

        Async.beginWork(10000, GenContentTheadPoolExecutorConfig.threadPoolExecutor(), workerWrapperList.toArray(new WorkerWrapper[]{}));

        workerWrapperList.forEach(workerWrapper -> {
            String result = workerWrapper.getWorkResult().getResult();
            if(result != null){
                String pattern = "((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}";

                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(result);

                if(m.find()){
                    content.append(result);
                }
            }
        });

        Async.shutDown();

        content.append("#Github Hosts End\n");
        FileUtils.write(file,content.toString());

        log.info("正在更新README文件...");
        updateReadme(updateTime, content.toString());

        log.info("操作完成,正在关闭...");
    }

    private static void updateReadme(LocalDate data,String hosts){
        File readmeTemplate = new File(System.getProperty("user.dir") + "/README_TEMPLATE.md");
        String readmeTemplateContent = FileUtils.read(readmeTemplate);

        String updateReadmeContent = readmeTemplateContent.replace("{[update_time]}", data.toString()).replace("{[hosts]}", hosts);

        File readme = new File(System.getProperty("user.dir") + "/README.md");
        FileUtils.write(readme,updateReadmeContent);
    }
}
