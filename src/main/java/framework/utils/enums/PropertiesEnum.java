package framework.utils.enums;

public enum PropertiesEnum {

    CONFIG ("src/main/java/test/resources/config.properties"),
    TESTRAIL("src/main/java/test/resources/testrail.properties"),
    SQL("src/main/java/test/resources/sql.properties");

    private String directory;

    PropertiesEnum(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }
}
