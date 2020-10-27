<?php
include("../config.php");

$db = new DB_Connect();
$con = $db->connect();

$docId = $_POST['docId'];

$sql_query = "SELECT * FROM tblpatient WHERE Docid = '$docId'";

$res = mysqli_query($con, $sql_query);

$result = [];

while($row = mysqli_fetch_array($res)){
    $result[] = array("id"=>$row['ID'],
        "patientName"=>$row['PatientName'],
        "contactNo"=>$row['PatientContno'],
        "patientGender"=>$row['PatientGender'],
        "creationDate"=>$row['CreationDate'],
        "updationDate"=>$row['UpdationDate']); 
}

echo json_encode($result);

mysqli_close($con);
?>