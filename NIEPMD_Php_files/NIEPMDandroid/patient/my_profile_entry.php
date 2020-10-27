<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$id = $_POST['id'];
$name = $_POST['name'];
$email = $_POST['email'];
$gender = $_POST['gender'];
$address = $_POST['address'];
$city = $_POST['city'];

$sql_query = "UPDATE users SET fullName = '$name', address = '$address', city = '$city', gender = '$gender', email = '$email' WHERE id = '$id'";
 
$res = mysqli_query($con, $sql_query);
 
$result = $res;
 
echo json_encode([["status"=>$result]]);
 
mysqli_close($con);
?>