<?php
include("config.php");
require 'sendmail.php';

$db = new DB_Connect();
$con = $db->connect();

$email = $_POST['email'];
$doctorOrPatient = $_POST['doctorOrPatient'];

$otp = rand(10000, 100000);

function generateEmailMessage($userName, $otp){
    global $subject;
    global $message;

    $subject = "NIEPMD App Forgot Password OTP";

    $message = "Dear ".$userName.",<br/><br/>

    Looks like you have forgotten your password. Please enter<br/>
    the OTP below to authenticate yourself.<br/><br/>

    OTP - ".$otp."<br/><br/>

    Continue with NIEPMD App!<br/><br/>

    Regards,<br/>
    BlueBase Team";
}

$result = array("status"=>"false");

if($doctorOrPatient == 0){
    $sql_query = "SELECT count(*), fullName as username FROM users WHERE email = '".$email."'";

    $res = mysqli_query($con, $sql_query);

    while($row = mysqli_fetch_array($res)){
        if ($row[0] == 1) {

            generateEmailMessage($row['username'], $otp);

            $sql_query = "SELECT count(*) FROM forgotpassword WHERE email = '".$email."'";

            $res1 = mysqli_query($con, $sql_query);

            while($row1 = mysqli_fetch_array($res1)){
                if ($row1["0"] == 0) {
                    $sql_query = "INSERT INTO forgotpassword (email, otp, doctororpatient) VALUES ('".$email."', '".$otp."', '".$doctorOrPatient."')";
                } else {
                    $sql_query = "UPDATE forgotpassword SET `otp` = '".$otp."', `doctororpatient` = '".$doctorOrPatient."' WHERE email = '".$email."'";
                }
                
                $con->query($sql_query);
            }

            if(sendMail($email, $subject, $message)){
                $result = array("status"=>"true");
            }
        }
    }
}else{
    $sql_query = "SELECT count(*), doctorName FROM doctors WHERE docEmail = '".$email."'";

    $res = mysqli_query($con, $sql_query);

    while($row = mysqli_fetch_array($res)){
        if($row[0] == 1){
            generateEmailMessage($row['doctorName'], $otp);

            $sql_query = "SELECT count(*) FROM forgotpassword WHERE email = '".$email."'";

            $res1 = mysqli_query($con, $sql_query);

            while($row1 = mysqli_fetch_array($res1)){
                if ($row1["0"] == 0) {
                    $sql_query = "INSERT INTO forgotpassword (email, otp, doctororpatient) VALUES ('".$email."', '".$otp."', '".$doctorOrPatient."')";
                } else {
                    $sql_query = "UPDATE forgotpassword SET `otp` = '".$otp."', `doctororpatient` = '".$doctorOrPatient."' WHERE email = '".$email."'";
                }
                
                $con->query($sql_query);
            }
    
            if(sendMail($email, $subject, $message)){
                $result = array("status"=>"true");
            }
        }
    }
}

echo json_encode([$result]);

mysqli_close($con);
?>