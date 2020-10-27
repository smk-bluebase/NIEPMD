<?php
include('../config.php');

$db = new DB_Connect();
$con = $db->connect();

$id = $_POST['id'];

$sql_query = "SELECT * FROM tblpatient JOIN users ON users.email = tblpatient.PatientEmail WHERE users.id = '".$id."'";

$res = mysqli_query($con, $sql_query);

$result = [];

while($row = mysqli_fetch_row($res)){
    $result[] = array("id"=>$row[0],
        "patientName"=>$row[2], 
        "patientContactNo"=>$row[3],
        "patientGender"=>$row[5], 
        "creationDate"=>$row[9],
        "updationDate"=>$row[10]);
}

echo json_encode($result);

mysqli_close($con);
?>