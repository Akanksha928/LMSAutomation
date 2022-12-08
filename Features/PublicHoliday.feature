Feature: LMS Page
Description: This test is to navigate to the Holidays List

Scenario: User is able to perform given actions in LMS
Given User is on EY LMS page
When User navigates to the Holiday List Page
Then Validate if the public holiday count is equal to or greater than 10
And User is able to split the holiday details as per Holiday Type

