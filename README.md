# ValidatorLibrary
## Lab-1
Added unit tests that can be found in the "tests" branch.
## Lab-2
Received unit tests from https://github.com/Kristupaz/PSP  
Implemented validator functionality using these tests.  
The validators and tests can be found in the "test-implementation" branch.  

Feedback:
- The unit tests were quite good and covered most of the edge cases, they were easy to use, clear and understandable.
- The only issue was that the tests never set any of the parameters for validation. For example, the PasswordChecker tests do not specify what is the minimum length for the password, however it is assumed that "Laba." is too short while "Labas." is not. The same can be found in PhoneValidator tests, where the country prefixes are assumed to be included by default, hence the tests would break if the default values are changed.
- The only way I can think of improving these tests would be to set the parameters of the validation inside the tests as mentioned previously, as well as adding some more complex EmailValidator tests (that have multiple labels).

