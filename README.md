# KeyWordDrivenFramework-sample

## **Overview:**

Keyword-driven testing is a type of functional automation testing framework which is also known as table-driven testing or action word based testing.

In Keyword-driven testing, we use a table format, usually a spreadsheet, to define keywords or action words for each function that we would like to execute.

For Demo purpose all the test cases are created for [JPetStore Demo](https://jpetstore.aspectran.com/catalog) site.

**Components of a Keyword Driven Framework**

1. **Excel Sheet** :  It is used to store the data for test cases such as keywords, data values etc.
2. **Object Repository** :  This stores the locator values for web elements.
3. **Action Library**: It is used to create functions that perform actions specified by keywords.
4. **Test Executor**: This is the driver engine that interacts with action classes  and excel file to execute the test case.

###**Some of the key features of this framework:**

1. It generates Extent report with all the step details.
2. It support parallel execution of test cases.
3. It generates test execution log file.
4. Test execution can be triggered form command line.
5. Easy integration to CI/CD pipeline.
6. Framework uses Page Object Design Pattern, hence there is clean separation between test code and page specific code such as locators and layout.
7. Test cases to executed can be decided by passing Yes/No value in excel file.

## **Required Setup :**

- [Java](https://www.guru99.com/install-java.html) should be installed and configured.
- [Maven](https://mkyong.com/maven/how-to-install-maven-in-windows/) should be installed and configured.
- Download the files from Git repository either as zip file OR using [Git](https://phoenixnap.com/kb/how-to-install-git-windows).

## **Running Test:**

- Navigate to *src/test/resources/data/TestDataSheet.xlsx*  and  update the excel sheet with test cases to execute with Yes/No value. 

- Open the command prompt and navigate to the folder in which pom.xml file is present.

- Run the below Maven command.

        mvn clean verify

Once the execution completes **Extent Report** will be generated in below folder structure.

**Extent Report-->** */target/html-report/index.html*
