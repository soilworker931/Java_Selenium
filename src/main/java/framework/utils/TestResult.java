package framework.utils;

import org.testng.ITestResult;

public enum TestResult {
    PASSED(1, "This test was completed successfully!"),
    FAILED(5, "This test was failed!");

    TestResult(int statusID, String statusComment) {
        this.statusID = statusID;
        this.statusComment = statusComment;
    }

    public int getStatusID() {
        return statusID;
    }

    public String getStatusComment() {
        return statusComment;
    }

    private final int statusID;
    private final String statusComment;

    public static int getId(ITestResult result) {
        Logger.info("Getting test case result");
        int i = 0;
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                i = PASSED.getStatusID();
                break;
            case ITestResult.FAILURE:
                i = FAILED.getStatusID();
                break;
        }
        return i;
    }

    public static String getComment(ITestResult result) {
        Logger.info("Getting test case result");
        String i = "";
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                i = PASSED.getStatusComment();
                break;
            case ITestResult.FAILURE:
                i = FAILED.getStatusComment();
                break;
        }
        return i;
    }
}