<?php require "header_mine.php"; 

// let's establish a connection.
$conn = new mysqli("localhost", "", "", "test_db");
if ($conn->connect_error) {
  echo "There is a problem connecting to DB guy (or girl).";
} else {

  // once we've connected we'll retrieve the data from the DB.
  $sql = "SELECT c_color, c_text FROM t_survey";
  $result = $conn->query($sql);
  if ($result) {
  
    // the num_rows property identifies how many records were returned by the query.
    if ($result->num_rows > 0) {
  
      // for each result, we'll need to retrieve the underlying values.
      // when there are no more records, this will return null.
      while ($row = $result->fetch_assoc()) {
    
        // we'll build some HTML out of the record.
        echo "<br>";
        echo "Fave Color: " . $row['c_color'] . "<br>";
        echo "Fave Book: " . $row['c_text'] . "<br>";
      
              }
    } else {
      echo "There are no results.<br>";
    }
  } else {
    $error = $conn->errno . ' ' . $conn->error;
    echo "Error: " + $error;
  }
}

$conn->close();

?>
<a href="enter_message_mine.php">take survey</a>
<?php require "footer_mine.php"; ?>