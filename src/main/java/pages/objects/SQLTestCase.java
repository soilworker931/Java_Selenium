package pages.objects;

import framework.utils.StringGenerator;

public class SQLTestCase {
    private int caseId = 0;
    private String testName = StringGenerator.textGeneration(20);
    private String statusId = "1";
    private String methodName = StringGenerator.textGeneration(40);
    private String sessionId = StringGenerator.integerNumberGeneration(1, 20);
    private String startTime = "2016-10-12 16:37:11";
    private String endTime = "2016-10-12 22:17:53";
    private String env = "KuznetsovN";
    private String browser = "firefox";

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getTestName() {
        return testName;
    }

    public String getStatusId() {
        return statusId;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getEnv() {
        return env;
    }

    public String getBrowser() {
        return browser;
    }
}
