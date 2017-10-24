Feature: Return Book

Scenario: Return book
	Given a user "user1" borrowed a book "book1" 3 days ago
	When "user1" return the book of ISBN "book1"
	Then "Return success!" with 0 fee
	
Scenario: Return book after 20 days
	Given a user "user2" borrowed book "book2" 20 days ago
	When "user1" return the book with ISBN "book2"
	Then "Return success!" and the fee is 15
	
	
	