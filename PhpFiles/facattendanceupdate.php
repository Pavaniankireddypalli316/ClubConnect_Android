<?php
require("connection.php");
// Check if the required parameters are provided
if (isset($_POST['stdid']) && isset($_POST['status'])) {
    // Get the parameters
    $studentId = $_POST['stdid'];
    $status = $_POST['status'];
    $courseselected=$_POST['clubid'];

    // Increment total_classes for all students
    $sql = "UPDATE enrolatt SET totalclasses = totalclasses + 1 WHERE studentid = $studentId AND courseid='$courseselected'";
    $conn->query($sql);

    // Increment classes_attended if student is present
    if ($status == 'Present') {
    $sqll = "UPDATE enrolatt SET attendedclasses = attendedclasses + 1 WHERE studentid = $studentId AND courseid='$courseselected'";
    $conn->query($sqll);
    }

    $conn->close();
} else {
    // Required parameters are missing
    echo "Missing parameters";
}

?>