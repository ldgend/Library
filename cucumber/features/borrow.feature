Feature: Borrow Book

Scenario: Borrow no copy book
	Given a book with the title "No copy" the ISBN "Nocopy" with the copy 0
	When user "user1" borrow the "Nocopy"
	Then the user cannot borrow, because "No more books"

Scenario: Borrow too many books
	Given a user "too many" already borrow book with ISBN "book1", "book2", "book3", "book4" and "book5"
	When "too many" borrow the book with ISBN "book6"
	Then user cannot borrow, because "You cannot borrow more than 5 books"
	
Scenario: Borrow with a overdue fee
	Given a user "fee" which has 10$ overdue fee
	When "fee" borrow book with ISBN "test"
	Then this user cannot borrow, because "Please pay the fee."

Scenario: Borrow a book which is already borrowed
	Given a user "confused" which has borrowed book with ISBN "book1"
	When "confused" borrow "book1" before he return it
	Then cannot borrow, because "You already borrowed!"
	
Scenario: Borrow
	Given a normal user "common"
	When "common" borrow a book with ISBN "borrow me"
	Then "Borrow success!" done
	
	
	
	
	
	
	