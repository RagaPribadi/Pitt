Adhyaksa Pribadi
adp54@pitt.edu

$k1= saved time
$k0 = seconds start posiotion
$t9 = tens seconds
$t8 = minutes
$t7 = end time

$s0-s4 = markCard
$t1-$t4 temp

$s7 =current card position
$t5 = first card position

free registers
$t6

The main gameloop updated the current time, checked if it the time is up and checked whether each part of the timer needed to be updated or not.
This was done by adding the saved time by 1000 and see if the current time is greater than the sum. If it was I would call updateclock
where it would redraw each part of the clock as needed. Game ends when current time is greater than saved time plus 180000. After 
updating the timer, I checked for keypresses. I saved the initial cursor position (x,y) in ($s5,$s6) and value in $s7. Pressing the left and right keypress 
would add -7 or 7 respectively to the x position where pressing up or down would add -9 or 9 to the y position. If the b button was 
pressed it would call on the function flipCard. This would retrieve the current cursor position in $s7 which was used to get the value
of the card. I used the the drawSquare function from lab 5 to mark the card. To shuffle, I went through the each index of the
cardValue array and swapped it with a randomly generated index. My program does not implement the golden card, or saves any of the matching.
I commented out the instructions that would allow me to save two selected cards in an allocated array and compare the two cards because it
messed up my entire program. 
