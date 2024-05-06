<?php
require("connection.php");
// Check if the required parameters are provided
if (isset($_POST['stdid']) && isset($_POST['status'])) {
    // Get the parameters
    $studentId = $_POST['stdid'];
    $status = $_POST['status'];
    $courseselected=$_POST['clubid'];

    // we need update in courses table as that course status is completed
    $sql = "UPDATE courses SET coursestatus = 'Completed' WHERE courseid='$courseselected'";
    $conn->query($sql);

    //Upgrading the grades of students in enrollatt table
    $sqll = "UPDATE enrolatt SET grade = '$status' WHERE studentid = $studentId AND courseid='$courseselected'";
    $conn->query($sqll);

    $conn->close();
} else {
    // Required parameters are missing
    echo "Missing parameters";
}

?>