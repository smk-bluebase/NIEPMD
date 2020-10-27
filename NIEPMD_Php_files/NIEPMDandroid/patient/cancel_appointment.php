<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$id = $_POST['id'];

$sql_query = "UPDATE appointment SET userStatus = 0 WHERE id = '".$id."'";

$res = mysqli_query($con, $sql_query);

$result = $res;

echo json_encode([["status"=>$result]]);

mysqli_close($con);
?>