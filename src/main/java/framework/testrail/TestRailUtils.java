package framework.testrail;

import framework.testrail.gurock.APIClient;
import framework.testrail.gurock.APIException;
import framework.utils.DateUtils;
import framework.utils.Logger;
import framework.utils.ReadProperties;
import framework.utils.enums.PropertiesEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestRailUtils {
    private static final String OUTPUT_PATH = "src/main/java/test/resources/";
    private static final String TEST_RAIL_URL = ReadProperties.readFromConfig(PropertiesEnum.TESTRAIL, "testRailUrl");
    private static final String USERNAME = ReadProperties.readFromConfig(PropertiesEnum.TESTRAIL, "username");
    private static final String PASSWORD = ReadProperties.readFromConfig(PropertiesEnum.TESTRAIL, "password");
    private static final String PROJECT_ID = ReadProperties.readFromConfig(PropertiesEnum.TESTRAIL, "testRailProjectID");
    private static final String SUITE_NAME = String.format("%s%s", ReadProperties.readFromConfig(PropertiesEnum.TESTRAIL, "suite_name"), DateUtils.getCurrentDate());
    private static final int SUITE_ID = Integer.valueOf(ReadProperties.readFromConfig(PropertiesEnum.TESTRAIL, "suite_id"));
    private String runId = "";
    APIClient client = new APIClient(TEST_RAIL_URL);

    public void signIn() {
        Logger.info("Logging in to TestRail");
        client.setUser(USERNAME);
        client.setPassword(PASSWORD);
    }

    public void addRun() {
        Logger.info("Adding new run");
        signIn();
        Map data = new HashMap();
        data.put("suite_id", SUITE_ID);
        data.put("name", SUITE_NAME);
        JSONArray c;
        try {
            JSONObject r = (JSONObject) client.sendPost("add_run/" + PROJECT_ID, data);
            c = (JSONArray) client.sendGet("get_runs/" + PROJECT_ID);
            for (int i = 0; i < c.size(); i++) {
                JSONObject object = (JSONObject) c.get(i);
                if (object.get("name").equals(SUITE_NAME)) {
                    runId = String.format(String.valueOf(object.get("id")));
                }
            }
        } catch (IOException e) {
            Logger.info("There was a problem with sending data");
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        }
        Logger.info(String.format("run with ID: %s was created", runId));
    }

    public void setResultForCase(String testCaseId, int status, String comment) throws APIException {
        Logger.info("Settings results for the case");
        Map data = new HashMap();
        data.put("status_id", status);
        data.put("comment", comment);
        try {
            JSONObject r = (JSONObject) client.sendPost(String.format("add_result_for_case/%s/%s", runId, testCaseId), data);
        } catch (IOException e) {
            Logger.info("There was a problem with sending data");
        }
    }

    public void setAttachmentToResult(String caseId, String filePath) throws APIException {
        Logger.info("Adding attachments to the test case result");
        JSONArray c;
        try {
            c = (JSONArray) client.sendGet("get_tests/" + runId);
            for (int i = 0; i < c.size(); i++) {
                JSONObject object = (JSONObject) c.get(i);
                if (String.valueOf(object.get("case_id")).equals(caseId)) {
                    String testId = String.valueOf(object.get("id")) ;
                    JSONArray k = (JSONArray) client.sendGet("get_results/" + testId);
                    JSONObject object1 = (JSONObject) k.get(0);
                    String resultID = String.valueOf(object1.get("id"));
                    JSONObject j = (JSONObject) client.sendPost("add_attachment_to_result/" + resultID,
                            String.format("%s%s.png", OUTPUT_PATH, filePath));
                }
            }
        } catch (IOException e) {
            Logger.info("There was a problem with sending data");
            e.printStackTrace();
        }
    }
}