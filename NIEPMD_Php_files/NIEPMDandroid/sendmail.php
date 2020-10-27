<?php
require 'phpmailer/class.phpmailer.php';
require 'phpmailer/class.smtp.php';
require 'phpmailer/PHPMailerAutoload.php';

function sendMail($email, $subject, $message){
    $mail = new PHPMailer;
    $mail->isSMTP();
    $mail->Host = 'smtp.gmail.com';
    $mail->Port = 587;
    $mail->SMTPAuth = true;
    $mail->Pool = true;
    $mail->Username = 'bluebase2017@gmail.com';
    $mail->Password = 'nbbfmqcsmsnfxmhm';

    $mail->setFrom('bluebase2017@gmail.com', 'NIEPMD App');
    $mail->addReplyTo('bluebase2017@gmail.com', 'NIEPMD App');
    $mail->addAddress($email);

    $mail->isHTML(true);	
    $mail->Subject = $subject;
    $mail->Body = $message;

    if($mail->send()){
        return true;
    }else{
        return false;
    }

}

?>