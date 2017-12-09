Some quick notes:

1. Decided to stick to the instructions given with little or any modifications to the spec
2. Didn't include any Task status (i.e whether it is PENDING or COMPLETE)
3. The code is mostly self explainatory, and therefore I didn't feel the need to add any additional comments or even Javadoc for that matter. Added comments wherever strictly necessary
4. Used Http Basic. A JWT implementation would have been time consuming and much more detailed, therefore more code and much more test cases
5. Missed out on a 4-5 integration tests for TaskController. Was facing some weird issue between H2 and Spring Boot and it was taking too much time to solve