package framework.sql;

import framework.utils.ImageConverter;
import framework.utils.Logger;
import framework.utils.ReadProperties;
import framework.utils.FileUtils;
import framework.utils.enums.PropertiesEnum;
import pages.objects.SQLTestCase;

import java.io.File;
import java.sql.*;

public class SQL {
    private static final String URL = ReadProperties.readFromConfig(PropertiesEnum.SQL,"url");
    private static final String USERNAME = ReadProperties.readFromConfig(PropertiesEnum.SQL, "username");
    private static final String PASSWORD = ReadProperties.readFromConfig(PropertiesEnum.SQL, "password");

    Statement statement = null;
    Connection connection = null;

    public void connectToDataBase() throws SQLException {
        Logger.info("connect to SQL DB");
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        statement = connection.createStatement();
    }

    public int getProjectId(String projectName) throws SQLException {
        Logger.info("Getting project id");
        String projectIdQuery = String.format("select * from union_reporting.project where name = '%s'", projectName);
        ResultSet resultSet = statement.executeQuery(projectIdQuery);
        while(resultSet.next()) {
            if (resultSet.getString("name").equals(projectName)) {
                Logger.info("Project id: " + resultSet.getInt("id"));
                return resultSet.getInt("id");
            }
        }
        return 0;
    }

    public int generateLastTestCaseId(SQLTestCase testCase) throws SQLException {
        Logger.info("Getting test case id to create");
        String query = "SELECT COUNT(id) AS total FROM union_reporting.test";
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next()) {
            testCase.setCaseId(resultSet.getInt("total") + 1);
            return (resultSet.getInt("total") + 1);
        }
        return 0;
    }

    public int generateLastAttachmentId() throws SQLException {
        Logger.info("Getting attachment id to create");
        String query = "SELECT COUNT(id) AS total FROM union_reporting.attachment";
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next()) {
            return (resultSet.getInt("total") + 1);
        }
        return 0;
    }

    public int generateLastLogId() throws SQLException {
        Logger.info("Getting log od to create");
        String query = "SELECT COUNT(id) AS total FROM union_reporting.log";
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next()) {
            return (resultSet.getInt("total") + 1);
        }
        return 0;
    }

    public void closeDataBase() {
        Logger.info("Closing database");
        try {
            statement.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertTestResultsToProject(SQLTestCase testCase, String projectName) throws SQLException {
        Logger.info("Inserting test results to the project");
        String query = String.format("INSERT INTO union_reporting.test VALUES ('%d', '%s', %s, '%s', '%s', '%s', '%s', '%s', '%s', '%s', null)",
                generateLastTestCaseId(testCase), testCase.getTestName(), testCase.getStatusId(), testCase.getMethodName(),
                getProjectId(projectName), testCase.getSessionId(), testCase.getStartTime(), testCase.getEndTime(), testCase.getEnv(), testCase.getBrowser());
        statement.executeUpdate(query);
    }

    public void insertAttachmentsToResult(SQLTestCase testCase, String screenshotName) throws SQLException {
        Logger.info("Inserting attachments to the case");
        File attachment = new File(String.format("src/main/java/test/resources/%s", screenshotName) );
        String query = String.format("INSERT INTO union_reporting.attachment VALUES (%d, '%s', 'image/png', '%d')",
                generateLastAttachmentId(), ImageConverter.ImageToBase64(attachment.getAbsolutePath()), testCase.getCaseId());
        statement.executeUpdate(query);
    }

    public void insertLogsToResult(SQLTestCase testCase) throws SQLException {
        Logger.info("Inserting logs to the case");
        File log = new File("log.out");
        String query = String.format("INSERT INTO union_reporting.log VALUES (%d, '%s', '1', '%d')",
                generateLastLogId(), FileUtils.readFile(log.getAbsolutePath()), testCase.getCaseId());
        statement.executeUpdate(query);
    }

    public void fillTheResults(SQLTestCase testCase, String projectName, String screenshotName) throws SQLException {
        insertTestResultsToProject(testCase, projectName);
        insertAttachmentsToResult(testCase, screenshotName);
        insertLogsToResult(testCase);
    }
}
