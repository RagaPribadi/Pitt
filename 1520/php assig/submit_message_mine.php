<?php
session_start();

function log_error($message) {
  if (empty($_SESSION['message_error'])) {
    $_SESSION['message_error'] = '';
  }
  $_SESSION['message_error'] .= $message . "<br>";
}

# We'll clear the errors first.
$_SESSION['message_error'] = '';

# This will set the variables to the posted parameters.
$color = $_POST['color'];
$text = $_POST['text'];

// we'll connect to the database here.
$conn = mysqli_connect("localhost", "", "", "test_db");

if ($conn->connect_error) {
  log_error("can't connect to db. abandon all hope ye who enter here");
} else {

  # We could do this simply, but it would leave us open to SQL injection attacks.
  #$conn->query("INSERT INTO t_messages (c_name, c_subject, c_time, c_text) VALUES ('$name','$subject',$time,'$text')");

  // We're going to use a prepared statement, because it's a bit safer.
  // If there are no database errors, we'll create a prepared statement.
  $statement = $conn->prepare("INSERT INTO t_survey (c_color, c_text) VALUES (?,?)");
  if ($statement) {
    /*
    Note that we could also create a regular statement, but then we'd need to 
    handle the escaping of our text values so that we'd avoid SQL injection attacks.
    It's also worth noting that we're not doing anything to prevent JavaScript 
    attacks - a user could very easily insert JavasScript and have it display on the page.
    */
  
    // we bind these parameters so that we can execute the statement.
    // the "ssis" identifies string, string, integer, string.
    $statement->bind_param("ss", $a, $d);
  
    $a = $color;
    $d = $text;
  
    $statement->execute();
  } else {
    $error = $conn->errno . ' ' . $conn->error;
    echo $error;
    return;
  }
}

$conn->close();

// If there are no errors, we'll go back to the "show messages" screen.
if (!empty($_SESSION['message_error']) and strlen($_SESSION['message_error']) > 0) {
  header('location:enter_message_mine.php');
} else {
  header('location:show_messages_mine.php');
}

