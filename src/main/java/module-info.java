module com.tohoieva.mathtests {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.xml;

    exports com.tohoieva.mathtests;
    opens com.tohoieva.mathtests to javafx.fxml;

    exports com.tohoieva.mathtests.mainMenu;
    opens com.tohoieva.mathtests.mainMenu to javafx.fxml;

    exports com.tohoieva.mathtests.addTask;
    opens com.tohoieva.mathtests.addTask to javafx.fxml;

    exports com.tohoieva.mathtests.passTest;
    opens com.tohoieva.mathtests.passTest to javafx.fxml;

    exports com.tohoieva.mathtests.checkResult;
    opens com.tohoieva.mathtests.checkResult to javafx.fxml;

    exports com.tohoieva.mathtests.startTest;
    opens com.tohoieva.mathtests.startTest to javafx.fxml;

    exports com.tohoieva.mathtests.configuration;

}