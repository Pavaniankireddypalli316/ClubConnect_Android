<?php 
include 'connection.php';

$studentId=$_POST['stdid'];
$clubId=$_POST['clubid'];
$status=$_POST['status'];

// Perform the update query
$sql = "UPDATE enrolatt SET studentstatus = '$status' WHERE studentid = '$studentId' and courseid='$clubId'";

if ($conn->query($sql) === TRUE) {
    echo "Update successful";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

// Close the database connection
$conn->close();
?>