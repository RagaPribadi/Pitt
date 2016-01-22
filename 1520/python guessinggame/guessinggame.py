#Adhyaksa Pribadi


# Define a function to retrieve the answer.
def get_answer(guess):

  # Initialize answer
  answer = ''
  # We only want to accept one of these values.
  while not answer in ['correct', 'higher', 'lower']:
    answer = raw_input("Is your age " + str(guess) + "? Enter correct, higher, or lower:\n")

    # Convert the value to lower case.    answer = answer.lower()
  return answer

print 'Welcome to guessing game!'

upper = 125
lower = 1
myGuess = 60
guessed = 0

while guessed != 1:
	input = get_answer(myGuess)
 	
	if input == 'lower':
		temp = myGuess
		myGuess = (temp + lower)/2
		upper = temp

	elif input == 'higher':
		temp = myGuess
		myGuess = (temp + upper)/2
		lower = temp

	elif input == 'correct':
		guessed = 1
	else:
		print 'type a valid response'
		
print 'Damn straight'




