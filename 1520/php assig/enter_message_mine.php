<?php require "header_mine.php"; 
session_start();

// if there is anything in the session field, we'll display it as an error.
if (array_key_exists('message_error', $_SESSION)) {
  if (!empty($_SESSION['message_error']) or strlen($_SESSION['message_error']) > 0) {
    echo $_SESSION['message_error'];
  }
}

function writeFormField($id, $label, $type) {
  echo "<label for=\"$id\">$label</label><br>";
  if ($type == 'text') {
    echo "<input type=\"text\" name=\"$id\" id=\"$id\" required=\"true\">";
  } else if ($type == 'textarea') {
    echo "<textarea id=\"$id\" name=\"$id\"></textarea>";
  }
  echo "<br><br><br>";
}

?>

<h1>Answer these questions</h1>
<form method="post" action="submit_message_mine.php">

<?php 
writeFormField('color', 'What is your Favorite Color?', 'text');
writeFormField('text', 'What is your Favorite TV show?', 'textarea');
?>

<input type="submit">
</form>
<br><a href="show_messages_mine.php">back to survey list</a>
<?php require "footer_mine.php"; ?>






