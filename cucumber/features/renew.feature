Feature: Renew Book

Scenario: Normal renew
	Given a user "borrower" has already borrowed the book with ISBN "book1"
	When "borrower" want to renew "book1" 
	Then "Renew success" finish
	
Scenario: Renew with no pay fee
	Given a user "fee" has already borrowed a book with ISBN "book2"
	When "fee" has 10$ overdue fee, and he want to renew "book2"
	Then cannot renew the book, because "Please pay the fee"
	
Scenario: Renew a book twice
	Given a user "confused" who has already borrowed and renewed "book3"
	When "confused" want to renew "book3" again
	Then the user cannot renew this book, because "You cannot renew twice"
	
	
	
	