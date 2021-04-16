package com.github.idell.gitlabconnect.ui.issue

import com.github.idell.gitlabconnect.gitlab.Issue

interface IssueGenerator {
    fun generate(): MutableList<Issue>
}

object IssueStubGenerator : Runnable, IssueGenerator {

    override fun generate(): MutableList<Issue> =
        mutableListOf(
            Issue(
                "Ut lobortis elit non diam dictum efficitur sed nec dolor. Phasellus eros ligula, aliquam vitae.",
                "http://www.example.com/agreement/attack.html",
                listOf("great vengeance", "darkness"),
                "* add smoke test and e2e tests execution also into spinnaker deploy pipeline\n" +
                    "* start from Jenkins pipeline script team-resources/continuous-integration/projects/rumba/QA_rumba.Jenkinsfile"
            ),
            Issue(
                "Morbi tincidunt tempor rutrum. Donec accumsan odio.",
                "http://airport.example.com/account.php",
                listOf("weirdo techie"),
                description()
            ),
            Issue(
                "Nulla facilisi. Sed in lorem cursus, vehicula.",
                "http://bubble.example.com/?box=authority",
                listOf("evil men"),
                "We could have this starting AppFw application using starter database\n" +
                    "```\n" +
                    "WARN 10379 --- [           main] o.a.tomcat.jdbc.pool.ConnectionPool      : maxIdle is larger than maxActive, setting maxIdle to: 5\n" +
                    "```\n" +
                    "We are not managing directly this value with starter database.\n" +
                    "The default value is `100` so... we can do the same in AppFw.. setting its value to maxActive"
            )
        )
    override fun run() {
        generate()
    }

    private fun description(): String {
        return "# Summary" +
            "\n Actually it's possible to run integration test and unit test with the following goals:" +
            "\n - `mvn test` <- unit test" +
            "\n - `mnv integration-test` <- integration test " +
            "\n https://gitlab.lastminute.com/core-software/appfw-java/app-framework/issues/105" +
            "\n but surefire/failsafe segregate only this two type of tests which doesn't cover all the wide spectrum of modern tests like contract tests and container tests. but surefire/failsafe segregate only this two type of tests which doesn't cover all the wide spectrum of modern tests like contract tests and container tests." +
            "\n ## Expected benefit(s) / Improved feature(s)" +
            "\n 1. segregate per subtype tests and not only IT and unit which is more precise" +
            "\n 2. permits to run locally only a subset of tests" +
            "\n 3. easy to identify errors in test in CI suite that use this convention (to simplify a pipeline ballon per type of test that can be red or green)" +
            "\n 4. using profiles is more visibile to devs than `integration-test` goal" +
            "\n \n ### Details" +
            "\n we can introduce a kind of segregation guided by maven profiles:" +
            "\n - `mvn verify` <- run all tests" +
            "\n - `mvn verify -PcontractTest` <- run only contractTest" +
            "\n \n ### Example" +
            "\n```xml" +
            "\n <profiles>" +
            "\n <profile>" +
            "\n <id>contractTest</id>" +
            "\n <build>" +
            "\n <plugins>" +
            "\n <plugin>" +
            "\n <groupId>org.apache.maven.plugins</groupId>" +
            "\n <artifactId>maven-surefire-plugin</artifactId>" +
            "\n <configuration>" +
            "\n <argLine>-Dfile.encoding=UTF8</argLine>" +
            "\n <runOrder>random</runOrder>" +
            "\n <includes>" +
            "\n <include>**/*TestContract.java</include>         " +
            "\n <include>**/*TestContractKt.java</include>" +
            "\n </includes>" +
            "\n <excludes>" +
            "\n <exclude>**/*Test.java</exclude>" +
            "\n <exclude>**/*TestKt.java</exclude>" +
            "\n <exclude>**/*ContainerTest.java</exclude>" +
            "\n <exclude>**/*ContainerTestKt.java</exclude>" +
            "\n<exclude>**/*TestKt.java</exclude>" +
            "\n<exclude>**/*IT.java</exclude>" +
            "\n<exclude>**/*ITKt.java</exclude>" +
            "\n</excludes>" +
            "\n<useSystemClassLoader>false</useSystemClassLoader>" +
            "\n</configuration>" +
            "\n</plugin>" +
            "\n</plugins>" +
            "\n</build>" +
            "\n</profile>" +
            "\n```"
    }
}
