<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$id = $_POST['id'];
$doctorSpecialization = $_POST['doctorSpecialization'];
$doctorName = $_POST['doctorName'];
$clinicAddress = $_POST['clinicAddress'];
$consultancyFees = $_POST['consultancyFees'];
$contactNo = $_POST['contactNo'];
$doctorEmail = $_POST['doctorEmail'];

$sql_query = "UPDATE doctors SET specilization = '$doctorSpecialization', doctorName = '$doctorName', address = '$clinicAddress', docFees = '$consultancyFees', contactno = '$contactNo', docEmail = '$doctorEmail' WHERE id = '$id'";

$res = mysqli_query($con, $sql_query);

$result = $res;

echo json_encode([["status"=>$result]]);

mysqli_close($con);
?>